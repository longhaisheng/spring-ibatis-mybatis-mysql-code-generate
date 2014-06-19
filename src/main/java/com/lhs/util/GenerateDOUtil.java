package com.lhs.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.lhs.domain.ColumnDO;
import com.lhs.domain.OneTableDomainDO;
import com.lhs.domain.OneTableSqlDO;
import com.lhs.domain.PackageDO;
import com.lhs.domain.ReadProperties;
import com.lhs.domain.TempateStringDO;

/**
 * @author haisheng.long 2012-6-8 13:23:09
 */
public class GenerateDOUtil {

	private static final String PARTITION_TABLE_PREFIX_SPLIT_NAME = "partition_table_prefix_split_name";

	private static final String PART_TABLE_SPLIT_CHAR = "@@@";

	private static final String PAGE_SIZE = "{pageSize}";

	private static final String START = "{start}";

	private static final String getConstant = "* 获取 ";

	private static final String setConstant = "* 设置 ";

	static final String PRIMARY_CONSTANT = "主键";

	static final String DB_SCHEMA = "db_schema";

	private static final String PRIMARY_KEY = "key";

	private static final String PRIMARY_PROPERTY = "property";

	private static final String PRIMARY_COLUMN_TYPE = "columnType";

	private static final String RN = "\r\n";

	private static final String TAB = "\t";

	private static Map<String, String> primitiveMap;

	static {
		primitiveMap = new HashMap<String, String>();
		primitiveMap.put("varchar", "String");
		primitiveMap.put("char", "String");
		primitiveMap.put("text", "String");

		primitiveMap.put("int", "Integer");
		primitiveMap.put("smallint", "Integer");
		primitiveMap.put("mediumint", "Integer");
		primitiveMap.put("integer", "Integer");

		primitiveMap.put("datetime", "Date");
		primitiveMap.put("timestamp", "Date");
		primitiveMap.put("date", "Date");
		primitiveMap.put("time", "Date");

		primitiveMap.put("bigint", "Long");
		primitiveMap.put("tinyint", "Boolean");

		primitiveMap.put("float", "Float");
		primitiveMap.put("double", "Double");

	}

	/**
	 * 根据表名生成类DO类名
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 */
	private static String generateDataObjectClassName(String tableName) {
		return generatePropertyName(tableName).substring(0, 1).toUpperCase() + generatePropertyName(tableName).substring(1)
				+ "DO";
	}

	/**
	 * 生成 delete sql 语句
	 * 
	 * <pre>
	 * delete from address where id=#id#
	 * </pre>
	 * 
	 * @param tableName
	 * @param columnList
	 * @return 2012-6-5 10:28:57
	 */
	public static String generateDeleteSql(String tableName, List<ColumnDO> columnList) {
		Map<String, String> primaryMap = getPrimaryMap(columnList);
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(tableName);
		sb.append(" WHERE ");
		sb.append(primaryMap.get(PRIMARY_KEY));
		sb.append("=#");
		sb.append(primaryMap.get(PRIMARY_PROPERTY));
		sb.append("#");
		return sb.toString();
	}

	public static OneTableDomainDO generateDomainDO(ReadProperties properties, Entry<String, String> tableEntry,
			PackageDO packgeDO) throws SQLException, IOException {
		String tableName = tableEntry.getKey();
		List<ColumnDO> columnList = MeteDataUtil.queryColumnDOList(properties, tableName);
		String packageName = packgeDO.getDomainPackageName();

		OneTableDomainDO domain = new OneTableDomainDO();
		List<String> propertyList = new ArrayList<String>();
		List<String> getMethodList = new ArrayList<String>();
		List<String> setMethodList = new ArrayList<String>();

		String dynamic_modified_time_field = properties.getValue("dynamic_modified_time_field");
		String dynamicUpdateFirstFieldName = "";
		String dynamicUpdateFirstProperty = "";
		String partition_table_prefix_split_name = properties.getValue(PARTITION_TABLE_PREFIX_SPLIT_NAME);
		List<ColumnDO> newList = new ArrayList<ColumnDO>();
		newList.addAll(columnList);
		if (isPartTable(partition_table_prefix_split_name, tableName)) {// 分表添加动态表名属性
			ColumnDO c = new ColumnDO();
			c.setComment("动态表名");
			c.setName("tableName");
			c.setType("varchar");
			newList.add(c);
		}

		if (newList != null && newList.size() > 0) {
			for (ColumnDO column : newList) {

				String propertyName = generatePropertyName(column.getName());
				String propertyType = getPropertyType(column.getType());
				if(column.isPrimary()){//主键一律使用Long类型
					propertyType="Long";
				}
				String comment = column.getComment();
				column.setPropertyName(propertyName);
				column.setPropertyType(propertyType);

				String propertyStr = generateProperty(propertyType, propertyName, comment);
				String setMethodStr = generateSetMethod(propertyType, propertyName, comment);
				String getMethodStr = generateGetMethod(propertyType, propertyName, comment);

				if (!domain.isHasDateType() && "Date".equals(propertyType)) {
					domain.setHasDateType(true);
				}
				if (domain.isHasDateType() && dynamic_modified_time_field.contains(column.getName())) {// 如果动态更新日期字段列表中有此字段
					dynamicUpdateFirstFieldName = column.getName();
					dynamicUpdateFirstProperty = propertyName;
				}

				propertyList.add(propertyStr);
				setMethodList.add(setMethodStr);
				getMethodList.add(getMethodStr);
			}
		}

		domain.setClassComment(tableEntry.getValue());
		domain.setPackageName(packageName);
		domain.setPropertyList(propertyList);
		domain.setGetMethods(getMethodList);

		domain.setSetMethods(setMethodList);

		domain.setClassName(generateDataObjectClassName(getPartTablePrefix(properties, tableName)));
		OneTableSqlDO generateOneTableSqlDO = generateOneTableSqlDO(properties, tableName, columnList);

		if (StringUtils.isBlank(dynamicUpdateFirstFieldName)) {// 设置动态更新sql语名的第一个字段及属性,如果没有时间修改字段,则设置为主键
			dynamicUpdateFirstFieldName = generateOneTableSqlDO.getPrimaryKey();
			dynamicUpdateFirstProperty = generateOneTableSqlDO.getPrimaryProperty();
		}

		generateOneTableSqlDO.setDynamicUpdateFirstFieldName(dynamicUpdateFirstFieldName);
		generateOneTableSqlDO.setDynamicUpdateFirstProperty(dynamicUpdateFirstProperty);
		domain.setTableSqlDO(generateOneTableSqlDO);

		return domain;
	}

	private static String getPartTablePrefix(ReadProperties properties, String table_name) throws IOException {
		String partition_table_prefix_split_name = properties.getValue(PARTITION_TABLE_PREFIX_SPLIT_NAME);
		if (isPartTable(partition_table_prefix_split_name, table_name)) {
			String partition_table_prefix_names = partition_table_prefix_split_name.replaceAll(PART_TABLE_SPLIT_CHAR, "");
			String[] partTables = partition_table_prefix_names.split(",");
			int j = 0;
			if (StringUtils.isNotBlank(table_name) && !ArrayUtils.isEmpty(partTables)) {
				for (int i = 0; i < partTables.length; i++) {// 针对分表的多表
					//if (table_name.startsWith(partTables[i])) {
					if (table_name.startsWith(partTables[i].split("_")[0]+"_") && table_name.length()==partTables[i].length()) {
						j = i;
						break;
					}
				}
			}
			String[] newStrs = partition_table_prefix_split_name.split(PART_TABLE_SPLIT_CHAR);
			return newStrs[j];// 返回分表的@@@前缀部分
		}
		return table_name;
	}

	public static String generateDomainString(OneTableDomainDO domain, PackageDO packgeDO) {
		StringBuilder sb = new StringBuilder();
		sb.append("package ");
		sb.append(domain.getPackageName());
		sb.append(";");
		sb.append(RN);
		sb.append(RN);
		sb.append("import ");
		sb.append(packgeDO.getDomainPackageName());
		sb.append(".BaseDO;");
		sb.append(RN);
		sb.append(RN);
		if (domain.isHasDateType()) {
			sb.append("import java.util.Date;");
			sb.append(RN);
			sb.append(RN);
		}
		sb.append("/**");
		sb.append(RN);
		sb.append("* ");
		sb.append(getDataObjectComment(domain));
		sb.append(RN);
		sb.append("* @author haisheng.long " + new Date());
		sb.append(RN);
		sb.append("*/");
		sb.append(RN);
		sb.append(RN);
		sb.append("public class ");
		sb.append(domain.getClassName());
		sb.append(" extends BaseDO {");
		sb.append(RN);
		sb.append(RN);

		for (String field : domain.getPropertyList()) {
			sb.append(field);
			sb.append(RN);
			sb.append(RN);
		}

		for (String setMethod : domain.getSetMethods()) {
			sb.append(setMethod);
			sb.append(RN);
		}

		for (String getMethod : domain.getGetMethods()) {
			sb.append(getMethod);
			sb.append(RN);
		}

		sb.append(RN);
		sb.append("}");
		return sb.toString();
	}

	private static String getDataObjectComment(OneTableDomainDO domain) {
		if (domain.getClassComment() != null && domain.getClassComment() != "") {
			return domain.getClassComment().replaceAll("表", "");
		} else {
			return domain.getClassName();
		}
	}

	private static String generateGetMethod(String propertyType, String propertyName, String comment) {
		StringBuilder sb = new StringBuilder();
		sb.append("/**");
		sb.append(RN);
		sb.append(getConstant);
		sb.append(comment);
		sb.append(RN);
		sb.append("* @return ");
		sb.append(propertyName);
		sb.append(RN);
		sb.append("*/");
		sb.append(RN);
		sb.append(TAB);
		sb.append("public ");
		sb.append(propertyType);
		sb.append(" ");
		sb.append("get");
		sb.append(propertyName.substring(0, 1).toUpperCase());
		sb.append(propertyName.substring(1));
		sb.append("()");
		sb.append(" ");
		sb.append("{");
		sb.append(RN);
		sb.append(TAB);
		sb.append("return ");
		sb.append(propertyName);
		sb.append(";");
		sb.append(RN);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 生成 insert sql 语句
	 * 
	 * <pre>
	 * insert into address(id,add_one,add_two) values(#id#,#addOne#,#addTwo#)
	 * </pre>
	 * 
	 * @param tableName
	 * @param columnList
	 */
	public static String generateInsertSql(String tableName, List<ColumnDO> columnList) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ");
		sb.append(tableName);
		sb.append("(");
		sb.append(getAllColumnString(columnList));
		sb.append(")");
		sb.append(" values(");
		sb.append(getAllInsertPropertyString(columnList));
		sb.append(")");
		return sb.toString();
	}

	private static OneTableSqlDO generateOneTableSqlDO(ReadProperties properties, String tableName, List<ColumnDO> columnList)
			throws IOException {
		OneTableSqlDO tableSqlDO = new OneTableSqlDO();

		String newTableName = "";
		if (isPartTable(properties.getValue(PARTITION_TABLE_PREFIX_SPLIT_NAME), tableName)) {
			newTableName = "${tableName}";
		} else {
			newTableName = tableName;
		}
		tableSqlDO.setTableName(newTableName);
		tableSqlDO.setAllColumnString(getAllColumnString(columnList));
		tableSqlDO.setInsertSql(generateInsertSql(newTableName, columnList));

		tableSqlDO.setSelectByIdSql(generateSelectByIdSql(newTableName, columnList));
		tableSqlDO.setUpdateSql(generateUpdateSql(newTableName, columnList));
		tableSqlDO.setDeleteSql(generateDeleteSql(newTableName, columnList));
		tableSqlDO.setColumnList(columnList);

		Map<String, String> primaryMap = getPrimaryMap(columnList);
		tableSqlDO.setPrimaryKey(primaryMap.get(PRIMARY_KEY));
		tableSqlDO.setPrimaryProperty(primaryMap.get(PRIMARY_PROPERTY));
		tableSqlDO.setPrimaryColumnType(primaryMap.get(PRIMARY_COLUMN_TYPE));

		tableSqlDO.setPageStartFieldName(START);
		tableSqlDO.setPageSizeFieldName(PAGE_SIZE);

		return tableSqlDO;
	}

	private static String generateProperty(String propertyType, String propertyName, String comment) {
		StringBuilder sb = new StringBuilder();
		sb.append("/** ");
		sb.append(comment);
		sb.append(" */");
		sb.append(RN);
		sb.append("private ");
		sb.append(propertyType);
		sb.append(" ");
		sb.append(propertyName);
		sb.append(";");
		return sb.toString();
	}

	private static String generatePropertyName(String columnName) {
		if (columnName.contains("_")) {
			String[] strs = columnName.split("_");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < strs.length; i++) {
				String string = strs[i];
				if (i == 0) {
					sb.append(string.substring(0, 1).toLowerCase());
				} else {
					sb.append(string.substring(0, 1).toUpperCase());
				}
				sb.append(string.substring(1));
			}
			return sb.toString();
		} else {
			return columnName.substring(0, 1).toLowerCase() + columnName.substring(1);
		}
	}

	/**
	 * <pre>
	 * SELECT id,add_one,add_two FROM address WHERE id=#id#
	 * </pre>
	 * 
	 * @param tableName
	 * @param columnList
	 */
	public static String generateSelectByIdSql(String tableName, List<ColumnDO> columnList) {
		Map<String, String> primaryMap = getPrimaryMap(columnList);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(getAllColumnString(columnList));
		sb.append(" FROM ");
		sb.append(tableName);
		sb.append(" WHERE ");
		sb.append(primaryMap.get(PRIMARY_KEY));
		sb.append("=#");
		sb.append(primaryMap.get(PRIMARY_PROPERTY));
		sb.append("#");
		return sb.toString();
	}

	private static String generateSetMethod(String propertyType, String propertyName, String comment) {
		StringBuilder sb = new StringBuilder();

		sb.append("/**");
		sb.append(RN);
		sb.append(setConstant);
		sb.append(comment);
		sb.append(RN);
		sb.append("* @param ");
		sb.append(propertyName);
		sb.append(RN);
		sb.append("*/");
		sb.append(RN);
		sb.append(TAB);
		sb.append("public ");
		sb.append("void ");
		sb.append("set");
		sb.append(propertyName.substring(0, 1).toUpperCase());
		sb.append(propertyName.substring(1));
		sb.append("(");
		sb.append(propertyType);
		sb.append(" ");
		sb.append(propertyName);
		sb.append(")");
		sb.append(" {");
		sb.append(RN);
		sb.append(TAB);
		sb.append("this.");
		sb.append(propertyName);
		sb.append(" = ");
		sb.append(propertyName);
		sb.append(";");
		sb.append(RN);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * <pre>
	 * update address set add_one=#addOne#,add_two=#addTwo# where id=#id#
	 * </pre>
	 * 
	 * @param tableName
	 * @param columnList
	 */
	public static String generateUpdateSql(String tableName, List<ColumnDO> columnList) {
		Map<String, String> primaryMap = getPrimaryMap(columnList);
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(tableName);
		sb.append(" SET ");
		int i = 0;
		int size = columnList.size();
		for (ColumnDO columnDO : columnList) {
			i++;
			if (!columnDO.isPrimary()) {
				sb.append(columnDO.getName());
				sb.append("=");
				sb.append("#");
				sb.append(columnDO.getPropertyName());
				sb.append("#");
				if (i != size) {
					sb.append(",");
				}
			}
		}

		sb.append(" WHERE");
		sb.append(" ");
		sb.append(primaryMap.get(PRIMARY_KEY));
		sb.append("=#");
		sb.append(primaryMap.get(PRIMARY_PROPERTY));
		sb.append("#");
		return sb.toString();
	}

	/**
	 * <pre>
	 * id,user_name,pass_word,status,create_time,modify_time
	 * </pre>
	 * 
	 * @param columnList
	 */
	public static String getAllColumnString(List<ColumnDO> columnList) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int size = columnList.size();
		for (ColumnDO columnDO : columnList) {
			i++;
			String columnName = columnDO.getName();
			sb.append(columnName);
			if (i != size) {
				sb.append(",");
			}

		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * #id#,#addOne#,#addTwo#
	 * </pre>
	 * 
	 * @param columnList
	 */
	public static String getAllInsertPropertyString(List<ColumnDO> columnList) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		int size = columnList.size();
		for (ColumnDO columnDO : columnList) {
			i++;
			String columnName = columnDO.getPropertyName();
			sb.append("#");
			sb.append(columnName);
			sb.append("#");
			if (i != size) {
				sb.append(",");
			}

		}
		return sb.toString();
	}

	public static Map<String, String> getPrimaryMap(List<ColumnDO> columnList) {
		Map<String, String> map = new HashMap<String, String>();
		for (ColumnDO columnDO : columnList) {
			if (columnDO.isPrimary()) {
				map.put(PRIMARY_KEY, columnDO.getName());
				map.put(PRIMARY_PROPERTY, columnDO.getPropertyName());
				map.put(PRIMARY_COLUMN_TYPE, columnDO.getPropertyType());
				break;
			}
		}
		return map;

	}

	private static String getPropertyType(String dataType) {
		String lowerKey = dataType.toLowerCase();
		if (primitiveMap.get(lowerKey) != null) {
			return primitiveMap.get(lowerKey);
		}
		return "String";
	}

	private static String getSqlMapPrefixFileName(OneTableDomainDO domain) {
		return domain.getClassName().substring(0, 1).toLowerCase()
				+ domain.getClassName().substring(1, domain.getClassName().indexOf("DO"));
	}

	/**
	 * 将包名替换为文件路径格式,如将
	 * 
	 * <pre>
	 * com.sina.ibatis.sqlmap 替换为: com/sina/ibatis/sqlmap
	 * </pre>
	 * 
	 * @param packgeName
	 * @return
	 */
	private static StringBuilder get_sql_map_config_xml_path(String packgeName) {
		String[] as = packgeName.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (String str : as) {
			sb.append(str);
			sb.append("/");
		}
		return sb;
	}

	/**
	 * 根据表名判断 此表是否是分表
	 * 
	 * @param partition_table_prefix_split_name
	 *            所有分表的前缀，用逗号隔开参见config.properties
	 * @param preTableName
	 * @return 非空 是分表，空不是分表
	 * @throws IOException
	 */
	public static boolean isPartTable(String partition_table_prefix_split_name, String preTableName) throws IOException {
		if (!partition_table_prefix_split_name.contains(PART_TABLE_SPLIT_CHAR)) {
			return false;
		}
		String partition_table_prefix_names = partition_table_prefix_split_name.replaceAll(PART_TABLE_SPLIT_CHAR, "");
		String[] partTables = partition_table_prefix_names.split(",");
		if (StringUtils.isNotBlank(preTableName) && !ArrayUtils.isEmpty(partTables)) {
			for (String t : partTables) {// 针对分表的多表
				if (preTableName.startsWith(t.split("_")[0]+"_") && preTableName.length()==t.length()) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String args[]) throws Exception {
		String projectPath=System.getProperty("user.dir");
		ReadProperties prop = new ReadProperties("config/config.properties");
		String package_name = prop.getValue("package_name");
		String templateType = prop.getValue("template_type");
		if (package_name == null || package_name == "") {
			return;
		}

		PackageDO packgeDO = new PackageDO(prop);

		String template_path = packgeDO.getTemplatePath();
		System.out.println(template_path); 
		String util_save_path = packgeDO.getUtilSavePath();

		String domain_save_path = packgeDO.getDomainSavePath();
		String dao_save_path = packgeDO.getDaoSavePath();

		String ibatis_sql_map_save_path = packgeDO.getIbatisSqlMapSavePath();
		String mybatis_sql_map_save_path = packgeDO.getMybatisSqlMapSavePath();

		String ibatis_dao_save_path = packgeDO.getIbatisDaoImplSavePath();
		String mybatis_dao_save_path = packgeDO.getMybatisDaoImplSavePath();

		String service_save_path = packgeDO.getServiceSavePath();
		String service_impl_save_path = packgeDO.getServiceImplSavePath();

		String page_template_name = "page.ftl";
		String base_do_template_name = "base_do.ftl";

		String dao_template_name = "dao_interface.ftl";
		String constant_template_name = "constant.ftl";

		String ibatis_sqlmap_template_name = "ibatis_sqlmap_template.ftl";
		String mybatis_sqlmap_template_name = "mybatis_sqlmap_template.ftl";

		String ibatis_dao_template_name = "ibatis_dao_impl.ftl";
		String mybatis_dao_template_name = "mybatis_dao_impl.ftl";

		String service_template_name = "service_interface.ftl";
		String service_impl_template_name = "service_impl.ftl";

		String service_exception_template_name = "service_exception.ftl";
		String dao_exception_template_name = "dao_exception.ftl";

		String ibatis_sqlmap_config_template_name = "ibatis_sqlmap_config.ftl";
		String mybatis_sqlmap_config_template_name = "mybatis_sqlmap_config.ftl";

		String controller_template_name = "controller.ftl";
		String add_template_name = "/" + templateType + "/add_template.ftl";
		String view_template_name = "/" + templateType + "/view_template.ftl";
		String edit_template_name = "/" + templateType + "/edit_template.ftl";
		String list_template_name = "/" + templateType + "/list_template.ftl";

		String service_exception_file_name = packgeDO.getExceptionSavePath() + File.separator + "ServiceException.java";
		String dao_exception_file_name = packgeDO.getExceptionSavePath() + File.separator + "DAOException.java";

		Map<String, Object> exception_root_map = new HashMap<String, Object>();
		exception_root_map.put("exceptionPackageName", packgeDO.getExceptionPackageName());
		FreeMarkerUtil.generateFile(template_path, service_exception_template_name, service_exception_file_name,
				exception_root_map);
		FreeMarkerUtil.generateFile(template_path, dao_exception_template_name, dao_exception_file_name, exception_root_map);

		Map<String, String> tables = MeteDataUtil.queryTables(prop);
		String bean_use_auto_wire = prop.getValue("bean_use_auto_wire");
		String useStatementNamespaces = prop.getValue("useStatementNamespaces");
		String sqlmap_orm = prop.getValue("sqlmap_orm");
		String partition_table_prefix_split_name = prop.getValue(PARTITION_TABLE_PREFIX_SPLIT_NAME);
		OneTableDomainDO domain;
		TempateStringDO tempateStringDO = new TempateStringDO();
		StringBuilder stringbuilder = null;
		if (PackageDO.IBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
			stringbuilder = get_sql_map_config_xml_path(packgeDO.getIbatisSqlMapPackageName());
		}
		if (PackageDO.MYBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
			stringbuilder = get_sql_map_config_xml_path(packgeDO.getMybatisSqlMapPackageName());
		}

		int j = 0;
		Iterator<String> iterator = tables.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (isPartTable(partition_table_prefix_split_name, key)) {
				j++;
				if (j > 1) {// 对于分表的表只保留一个
					iterator.remove();
				}
			}
		}

		Set<String> sql_map_config_xml_sets = new HashSet<String>();
		int i = 0;
		for (Entry<String, String> tableEntry : tables.entrySet()) {
			i++;
			domain = generateDomainDO(prop, tableEntry, packgeDO);
			String classCode = generateDomainString(domain, packgeDO);
			String className = domain.getClassName();
			String first_upper_class_name_except_do = className.substring(0, className.indexOf("DO"));
			String first_lower_class_name_except_do = first_upper_class_name_except_do.substring(0, 1).toLowerCase()
					+ first_upper_class_name_except_do.substring(1);

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("tempateStringDO", tempateStringDO);
			root.put("domain", domain);
			root.put("tableDO", domain.getTableSqlDO());
			root.put("packgeDO", packgeDO);
			root.put("data_object_comment", getDataObjectComment(domain));
			root.put("create_time", new Date());
			root.put("bean_is_auto_wire", bean_use_auto_wire);
			root.put("use_statement_name_spaces", useStatementNamespaces);

			FileUtil.createJaveSourceFile(domain_save_path, className + ".java", classCode);// 生成java类代码

			if (PackageDO.IBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
				String ibatis_sqlmap_xml_path_string = getSqlMapPrefixFileName(domain) + "-sqlmap.xml";
				sql_map_config_xml_sets.add(stringbuilder.toString() + ibatis_sqlmap_xml_path_string);

				String ibatis_sqlmap_file_name = ibatis_sql_map_save_path + File.separator + ibatis_sqlmap_xml_path_string;
				FreeMarkerUtil.generateFile(template_path, ibatis_sqlmap_template_name, ibatis_sqlmap_file_name, root);

				String ibatis_dao_impl_file_name = ibatis_dao_save_path + File.separator + "Ibatis"
						+ first_upper_class_name_except_do + "DAO.java";
				FreeMarkerUtil.generateFile(template_path, ibatis_dao_template_name, ibatis_dao_impl_file_name, root);
			}

			if (PackageDO.MYBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
				String mybatis_sqlmap_xml_path_string = getSqlMapPrefixFileName(domain) + "-mybatis-sqlmap.xml";
				sql_map_config_xml_sets.add(stringbuilder.toString() + mybatis_sqlmap_xml_path_string);

				String mybatis_sql_map_file_name = mybatis_sql_map_save_path + File.separator + getSqlMapPrefixFileName(domain)
						+ "-mybatis-sqlmap.xml";
				FreeMarkerUtil.generateFile(template_path, mybatis_sqlmap_template_name, mybatis_sql_map_file_name, root);

				String mybatis_dao_impl_file_name = mybatis_dao_save_path + File.separator + "Mybatis"
						+ first_upper_class_name_except_do + "DAO.java";
				FreeMarkerUtil.generateFile(template_path, mybatis_dao_template_name, mybatis_dao_impl_file_name, root);
			}

			String dao_file_name = dao_save_path + File.separator + first_upper_class_name_except_do + "DAO.java";
			FreeMarkerUtil.generateFile(template_path, dao_template_name, dao_file_name, root);

			String service_file_name = service_save_path + File.separator + first_upper_class_name_except_do + "Service.java";
			FreeMarkerUtil.generateFile(template_path, service_template_name, service_file_name, root);

			String service_impl_file_name = service_impl_save_path + File.separator + first_upper_class_name_except_do
					+ "ServiceImpl.java";
			FreeMarkerUtil.generateFile(template_path, service_impl_template_name, service_impl_file_name, root);

			String constant_file_name = packgeDO.getConstantSavePath() + File.separator + first_upper_class_name_except_do
					+ "Constant.java";
			FreeMarkerUtil.generateFile(template_path, constant_template_name, constant_file_name, root);

			String controller_file_name = packgeDO.getControllerSavePath() + File.separator + first_upper_class_name_except_do
					+ "Controller.java";
			FreeMarkerUtil.generateFile(template_path, controller_template_name, controller_file_name, root);

			String genarate_add_template_file_name = packgeDO.getTemplateSavePath() + File.separator + templateType
					+ File.separator + first_lower_class_name_except_do + File.separator + "add.ftl";

			String generate_template_save_folder = (packgeDO.getTemplateSavePath() + "." + templateType + "." + first_lower_class_name_except_do)
					.replace(".", File.separator);
			File add_template_file = new File(generate_template_save_folder);
			if (!(add_template_file.exists()) && !(add_template_file.isDirectory())) {
				add_template_file.mkdirs();
			}
			FreeMarkerUtil.generateFile(template_path, add_template_name, genarate_add_template_file_name, root);

			if (i == tables.size()) {// only one generate times
				if (PackageDO.IBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
					root.put("sql_map_config_xml_set", sql_map_config_xml_sets);
					String ibatis_sql_map_config_file = ibatis_sql_map_save_path + File.separator + "ibatis-sqlmap-config.xml";
					FreeMarkerUtil.generateFile(template_path, ibatis_sqlmap_config_template_name, ibatis_sql_map_config_file,
							root);
				}

				if (PackageDO.MYBATIS_SQLMAP_ORM.equals(sqlmap_orm)) {
					root.put("sql_map_config_xml_set", sql_map_config_xml_sets);
					String mybatis_sql_map_config_file = mybatis_sql_map_save_path + File.separator
							+ "mybatis-sqlmap-config.xml";
					FreeMarkerUtil.generateFile(template_path, mybatis_sqlmap_config_template_name,
							mybatis_sql_map_config_file, root);
				}

				String base_do_html_file = domain_save_path + File.separator + "BaseDO.java";
				String page_html_file = util_save_path + File.separator + "Page.java";

				FreeMarkerUtil.generateFile(template_path, page_template_name, page_html_file, root);
				FreeMarkerUtil.generateFile(template_path, base_do_template_name, base_do_html_file, root);
			}
		}

		System.out.println("genera all file is success! ");
	}
}

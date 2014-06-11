package com.lhs.domain;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author longhaisheng <code>Email:longhaisheng20@gmail.com</code>
 */
public class PackageDO {

	public static final String IBATIS_SQLMAP_ORM = "ibatis";

	public static final String MYBATIS_SQLMAP_ORM = "mybatis";

	/** domain 包名 */
	private String domainPackageName;

	/** domain 保存路径 */
	private String domainSavePath;

	/** dao 包名 */
	private String daoPackageName;

	/** dao 保存路径 */
	private String daoSavePath;

	/** ibatisSqlMap 包名 */
	private String ibatisSqlMapPackageName;

	/** ibatisSqlMap 保存路径 */
	private String ibatisSqlMapSavePath;

	/** mybatisSqlMap 包名 */
	private String mybatisSqlMapPackageName;

	/** mybatisSqlMap 保存路径 */
	private String mybatisSqlMapSavePath;

	/** ibatis 包名 */
	private String ibatisPackageName;

	/** ibatis dao实现类保存路径 */
	private String ibatisDaoImplSavePath;

	/** mybatis 包名 */
	private String mybatisPackageName;

	/** mybatis dao实现类保存路径 */
	private String mybatisDaoImplSavePath;

	/** service 接口包名 */
	private String servicePackageName;

	/** service 接口保存路径 */
	private String serviceSavePath;

	/** serviceImpl 包名 */
	private String serviceImplPackageName;

	/** serviceImpl 保存路径 */
	private String serviceImplSavePath;

	/** controller 包名 */
	private String controllerPackageName;

	/** controller 保存路径 */
	private String controllerSavePath;

	/** template 生成的模板包名 */
	private String templatePackageName;

	/** template 保存路径 */
	private String templateSavePath;

	/** 所需模板路径 */
	private String templatePath;

	/** 异常所在包名 */
	private String exceptionPackageName;

	/** 异常类保存路径 */
	private String exceptionSavePath;

	/** util 包名 */
	private String utilPackageName;

	/** util 类保存路径 */
	private String utilSavePath;

	/** 生成的sqlmap类型 */
	private String sqlmapOrm;

	/** 常量包名 */
	private String constantPackageName;

	/** 常量保存路径 */
	private String constantSavePath;

	public PackageDO(ReadProperties prop) throws Exception {
		String packageName = prop.getValue("package_name");
		String appName = prop.getValue("app_name");
		this.sqlmapOrm = prop.getValue("sqlmap_orm");
		init(packageName, appName);

	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public String getDaoSavePath() {
		return daoSavePath;
	}

	public String getDomainPackageName() {
		return domainPackageName;
	}

	public String getDomainSavePath() {
		return domainSavePath;
	}

	public String getExceptionPackageName() {
		return exceptionPackageName;
	}

	public String getExceptionSavePath() {
		return exceptionSavePath;
	}

	public String getIbatisPackageName() {
		return ibatisPackageName;
	}

	public String getIbatisDaoImplSavePath() {
		return ibatisDaoImplSavePath;
	}

	public String getIbatisSqlMapPackageName() {
		return ibatisSqlMapPackageName;
	}

	public String getIbatisSqlMapSavePath() {
		return ibatisSqlMapSavePath;
	}

	public String getMybatisPackageName() {
		return mybatisPackageName;
	}

	public String getMybatisDaoImplSavePath() {
		return mybatisDaoImplSavePath;
	}

	public String getMybatisSqlMapPackageName() {
		return mybatisSqlMapPackageName;
	}

	public String getMybatisSqlMapSavePath() {
		return mybatisSqlMapSavePath;
	}

	public String getServiceImplPackageName() {
		return serviceImplPackageName;
	}

	public String getServiceImplSavePath() {
		return serviceImplSavePath;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public String getServiceSavePath() {
		return serviceSavePath;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	private void init(String generatePackageName, String appName) throws Exception {
		if (generatePackageName.indexOf(".") == -1) {
			throw new Exception();
		}
		// String canonicalName = PackageDO.class.getCanonicalName();
		String[] arraysts = generatePackageName.split("\\.");
		StringBuilder sb = new StringBuilder();
		sb.append(arraysts[0]);
		sb.append(".");
		sb.append(arraysts[1]);
		if (appName != null && appName != "") {
			sb.append(".");
			sb.append(appName);
		}

		Set<String> set = new HashSet<String>();
		String pre_package_name = sb.toString();

		String projectAbsPath = System.getProperty("user.dir");// 用户项目的绝对路径
		String srcProjectAbsPath = projectAbsPath + File.separator + "src" + File.separator;

		String domain_package_name = pre_package_name + ".domain";
		String domain_save_path = domain_package_name.replace(".", File.separator);
		setDomainPackageName(domain_package_name);
		setDomainSavePath(srcProjectAbsPath + domain_save_path);
		set.add(srcProjectAbsPath + domain_save_path);

		String interface_dao_package_name = pre_package_name + ".dao";
		String dao_save_path = interface_dao_package_name.replace(".", File.separator);
		setDaoPackageName(interface_dao_package_name);
		setDaoSavePath(srcProjectAbsPath + dao_save_path);
		set.add(srcProjectAbsPath + dao_save_path);

		if (IBATIS_SQLMAP_ORM.equals(this.sqlmapOrm)) {
			String ibatis_package_name = pre_package_name + ".dao.ibatis";
			String ibatis_dao_impl_save_path = ibatis_package_name.replace(".", File.separator);
			setIbatisPackageName(ibatis_package_name);
			setIbatisDaoImplSavePath(srcProjectAbsPath + ibatis_dao_impl_save_path);
			set.add(srcProjectAbsPath + ibatis_dao_impl_save_path);

			String ibatis_sqlmap_package_name = pre_package_name + ".ibatis.sqlmap";
			String ibatis_sqlmap_save_path = ibatis_sqlmap_package_name.replace(".", File.separator);
			setIbatisSqlMapPackageName(ibatis_sqlmap_package_name);
			setIbatisSqlMapSavePath(srcProjectAbsPath + ibatis_sqlmap_save_path);
			set.add(srcProjectAbsPath + ibatis_sqlmap_save_path);
		}

		if (MYBATIS_SQLMAP_ORM.equals(this.sqlmapOrm)) {
			String mybatis_dao_impl_package_name = pre_package_name + ".dao.mybatis";
			String mybatis_dao_impl_save_path = mybatis_dao_impl_package_name.replace(".", File.separator);
			setMybatisPackageName(mybatis_dao_impl_package_name);
			setMybatisDaoImplSavePath(srcProjectAbsPath + mybatis_dao_impl_save_path);
			set.add(srcProjectAbsPath + mybatis_dao_impl_save_path);

			String mybatis_sqlmap_package_name = pre_package_name + ".mybatis.sqlmap";
			String mybatis_sqlmap_save_path = mybatis_sqlmap_package_name.replace(".", File.separator);
			setMybatisSqlMapPackageName(mybatis_sqlmap_package_name);
			setMybatisSqlMapSavePath(srcProjectAbsPath + mybatis_sqlmap_save_path);
			set.add(srcProjectAbsPath + mybatis_sqlmap_save_path);
		}
		String service_package_name = pre_package_name + ".service";
		String service_save_path = service_package_name.replace(".", File.separator);
		setServicePackageName(service_package_name);
		setServiceSavePath(srcProjectAbsPath + service_save_path);
		set.add(srcProjectAbsPath + service_save_path);

		String controller_package_name = pre_package_name + ".controller";
		String controller_save_path = controller_package_name.replace(".", File.separator);
		setControllerPackageName(controller_package_name);
		setControllerSavePath(srcProjectAbsPath + controller_save_path);
		set.add(srcProjectAbsPath + controller_save_path);

		String template_package_name = pre_package_name + ".template";
		String template_save_path = template_package_name.replace(".", File.separator);
		setTemplatePackageName(template_package_name);
		setTemplateSavePath(srcProjectAbsPath + template_save_path);
		set.add(srcProjectAbsPath + template_save_path);

		String service_impl_package_name = pre_package_name + ".service.impl";
		String service_impl_save_path = service_impl_package_name.replace(".", File.separator);
		setServiceImplPackageName(service_impl_package_name);
		setServiceImplSavePath(srcProjectAbsPath + service_impl_save_path);
		set.add(srcProjectAbsPath + service_impl_save_path);

		String exception_package_name = pre_package_name + ".exception";
		String exception_save_path = exception_package_name.replace(".", File.separator);
		setExceptionPackageName(exception_package_name);
		setExceptionSavePath(srcProjectAbsPath + exception_save_path);
		set.add(srcProjectAbsPath + exception_save_path);

		String util_package_name = pre_package_name + ".util";
		System.out.println(util_package_name);
		String util_save_path = util_package_name.replace(".", File.separator);
		setUtilPackageName(util_package_name);
		setUtilSavePath(srcProjectAbsPath + util_save_path);
		set.add(srcProjectAbsPath + util_save_path);

		String constant_package_name = pre_package_name + ".constant";
		String constant_save_path = constant_package_name.replace(".", File.separator);
		setConstantPackageName(constant_package_name);
		setConstantSavePath(srcProjectAbsPath + constant_save_path);
		set.add(srcProjectAbsPath + constant_save_path);

		String templatePath = projectAbsPath + File.separator + "webapp" + File.separator + "template";
		setTemplatePath(templatePath);

		for (String path : set) {
			File file = new File(path);
			if (!(file.exists()) && !(file.isDirectory())) {
				file.mkdirs();
			}
		}

	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public void setDaoSavePath(String daoSavePath) {
		this.daoSavePath = daoSavePath;
	}

	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}

	public void setDomainSavePath(String domainSavePath) {
		this.domainSavePath = domainSavePath;
	}

	public void setExceptionPackageName(String exceptionPackageName) {
		this.exceptionPackageName = exceptionPackageName;
	}

	public void setExceptionSavePath(String exceptionSavePath) {
		this.exceptionSavePath = exceptionSavePath;
	}

	public void setIbatisPackageName(String ibatisPackageName) {
		this.ibatisPackageName = ibatisPackageName;
	}

	public String getUtilPackageName() {
		return utilPackageName;
	}

	public void setUtilPackageName(String utilPackageName) {
		this.utilPackageName = utilPackageName;
	}

	public String getUtilSavePath() {
		return utilSavePath;
	}

	public void setUtilSavePath(String utilSavePath) {
		this.utilSavePath = utilSavePath;
	}

	public void setIbatisDaoImplSavePath(String ibatisDaoImplSavePath) {
		this.ibatisDaoImplSavePath = ibatisDaoImplSavePath;
	}

	public void setIbatisSqlMapPackageName(String ibatisSqlMapPackageName) {
		this.ibatisSqlMapPackageName = ibatisSqlMapPackageName;
	}

	public void setIbatisSqlMapSavePath(String ibatisSqlMapSavePath) {
		this.ibatisSqlMapSavePath = ibatisSqlMapSavePath;
	}

	public void setMybatisPackageName(String mybatisPackageName) {
		this.mybatisPackageName = mybatisPackageName;
	}

	public void setMybatisDaoImplSavePath(String mybatisDaoImplSavePath) {
		this.mybatisDaoImplSavePath = mybatisDaoImplSavePath;
	}

	public void setMybatisSqlMapPackageName(String mybatisSqlMapPackageName) {
		this.mybatisSqlMapPackageName = mybatisSqlMapPackageName;
	}

	public void setMybatisSqlMapSavePath(String mybatisSqlMapSavePath) {
		this.mybatisSqlMapSavePath = mybatisSqlMapSavePath;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public void setServiceImplSavePath(String serviceImplSavePath) {
		this.serviceImplSavePath = serviceImplSavePath;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}

	public String getControllerSavePath() {
		return controllerSavePath;
	}

	public void setControllerSavePath(String controllerSavePath) {
		this.controllerSavePath = controllerSavePath;
	}

	public String getTemplatePackageName() {
		return templatePackageName;
	}

	public void setTemplatePackageName(String templatePackageName) {
		this.templatePackageName = templatePackageName;
	}

	public String getTemplateSavePath() {
		return templateSavePath;
	}

	public void setTemplateSavePath(String templateSavePath) {
		this.templateSavePath = templateSavePath;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public void setServiceSavePath(String serviceSavePath) {
		this.serviceSavePath = serviceSavePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getConstantPackageName() {
		return constantPackageName;
	}

	public void setConstantPackageName(String constantPackageName) {
		this.constantPackageName = constantPackageName;
	}

	public String getConstantSavePath() {
		return constantSavePath;
	}

	public void setConstantSavePath(String constantSavePath) {
		this.constantSavePath = constantSavePath;
	}

}

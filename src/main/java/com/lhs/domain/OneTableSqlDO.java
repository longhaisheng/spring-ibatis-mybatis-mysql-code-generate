package com.lhs.domain;

import java.util.List;

/**
 * @author haisheng.long <code>Email:longhaisheng20@gmail.com</code>
 */
public class OneTableSqlDO {

	/** 所有列，用逗号分隔开 */
	private String allColumnString;

	/** 表名 */
	private String tableName;

	/** 根据主键找查找记录SQL语句 */
	private String selectByIdSql;

	/** 插入SQL语句 */
	private String insertSql;

	/** 根据主键更新SQL语名 */
	private String updateSql;

	/** 根据主键删除SQL语句 */
	private String deleteSql;

	/** 主键对应的column名称 */
	private String primaryKey;

	/** 主键对应的属性名称 */
	private String primaryProperty;

	/** 主键对应的类型 */
	private String primaryColumnType;

	/** 动态更新时 第一个字段名称即dynamic标签前的字段名 */
	private String dynamicUpdateFirstFieldName;

	/** 动态更新时 第一个字段名称即dynamic标签前的属性 */
	private String dynamicUpdateFirstProperty;

	/** 一个表的所有数据列对象 */
	private List<ColumnDO> columnList;

	/** 分页 开始记录 字段名称 用于mybatis中 */
	private String pageStartFieldName;

	/** 一次分页 取多少条记录 字段名称 用于mybatis中 */
	private String pageSizeFieldName;

	public String getAllColumnString() {
		return allColumnString;
	}

	public List<ColumnDO> getColumnList() {
		return columnList;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getPrimaryProperty() {
		return primaryProperty;
	}

	public String getSelectByIdSql() {
		return selectByIdSql;
	}

	public String getTableName() {
		return tableName;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setAllColumnString(String allColumnString) {
		this.allColumnString = allColumnString;
	}

	public void setColumnList(List<ColumnDO> columnList) {
		this.columnList = columnList;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setPrimaryProperty(String primaryProperty) {
		this.primaryProperty = primaryProperty;
	}

	public String getPrimaryColumnType() {
		return primaryColumnType;
	}

	public void setPrimaryColumnType(String primaryColumnType) {
		this.primaryColumnType = primaryColumnType;
	}

	public void setSelectByIdSql(String selectByIdSql) {
		this.selectByIdSql = selectByIdSql;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getDynamicUpdateFirstFieldName() {
		return dynamicUpdateFirstFieldName;
	}

	public void setDynamicUpdateFirstFieldName(String dynamicUpdateFirstFieldName) {
		this.dynamicUpdateFirstFieldName = dynamicUpdateFirstFieldName;
	}

	public String getDynamicUpdateFirstProperty() {
		return dynamicUpdateFirstProperty;
	}

	public void setDynamicUpdateFirstProperty(String dynamicUpdateFirstProperty) {
		this.dynamicUpdateFirstProperty = dynamicUpdateFirstProperty;
	}

	public String getPageStartFieldName() {
		return pageStartFieldName;
	}

	public void setPageStartFieldName(String pageStartFieldName) {
		this.pageStartFieldName = pageStartFieldName;
	}

	public String getPageSizeFieldName() {
		return pageSizeFieldName;
	}

	public void setPageSizeFieldName(String pageSizeFieldName) {
		this.pageSizeFieldName = pageSizeFieldName;
	}

	public String toString() {
		return "primaryKey:" + this.primaryKey + "\r\n primaryProperty:" + this.primaryProperty + "\r\n table_name:"
				+ this.tableName + "\r\n insert_sql:" + this.insertSql + "\r\n update_sql:" + updateSql + "\r\n select_sql:"
				+ this.selectByIdSql + "\r\n delete_sql:" + this.deleteSql + "\r\n allColumnString:" + this.allColumnString
				+ "\r\n";
	}
}

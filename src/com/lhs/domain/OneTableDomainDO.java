package com.lhs.domain;

import java.util.List;

/**
 * @author haisheng.long <code>Email:longhaisheng20@gmail.com</code>
 */
public class OneTableDomainDO {

	/** 类名上的注释对象名 */
	private String classComment;

	/** 所属包名 */
	private String packageName;

	/** 类名 */
	private String className;

	/** 是否有时间类型的字段 */
	private boolean hasDateType;

	/** 字段属性列表 */
	private List<String> propertyList;

	/** get方法列表 */
	private List<String> getMethods;

	/** set方法列表 */
	private List<String> setMethods;

	private OneTableSqlDO tableSqlDO;

	public String getClassComment() {
		return classComment;
	}

	public String getClassName() {
		return className;
	}

	public List<String> getGetMethods() {
		return getMethods;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<String> getPropertyList() {
		return propertyList;
	}

	public List<String> getSetMethods() {
		return setMethods;
	}

	public OneTableSqlDO getTableSqlDO() {
		return tableSqlDO;
	}

	public boolean isHasDateType() {
		return hasDateType;
	}

	public void setClassComment(String classComment) {
		this.classComment = classComment;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setGetMethods(List<String> getMethods) {
		this.getMethods = getMethods;
	}

	public void setHasDateType(boolean hasDateType) {
		this.hasDateType = hasDateType;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setPropertyList(List<String> fields) {
		this.propertyList = fields;
	}

	public void setSetMethods(List<String> setMethods) {
		this.setMethods = setMethods;
	}

	public void setTableSqlDO(OneTableSqlDO tableSqlDO) {
		this.tableSqlDO = tableSqlDO;
	}

}

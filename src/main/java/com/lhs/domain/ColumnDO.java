package com.lhs.domain;

/**
 * @author haisheng.long <code>Email:longhaisheng20@gmail.com</code>
 */
public class ColumnDO {

	/** 表字段注解 */
	private String comment;

	/** 是否是主键 */
	private boolean isPrimary;

	/** 表字段名称 */
	private String name;

	/** 对应的对象属性名称 */
	private String propertyName;

	/** 对象类型 */
	private String propertyType;

	/** 表字段类型 */
	private String type;

	public String getComment() {
		return comment;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public String getName() {
		return name;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public String getType() {
		return type;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public void setType(String type) {
		this.type = type;
	}

}

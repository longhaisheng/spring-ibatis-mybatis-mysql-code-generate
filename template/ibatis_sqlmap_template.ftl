<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">
<#assign len = domain.className?index_of("DO")>
<#assign complete_package_class_name="${domain.packageName}.${domain.className}">
<#assign ibatis_class_dao="Ibatis${domain.className?substring(0,len)}DAO">

<sqlMap namespace="${domain.packageName}.${domain.className?substring(0,len)}">

    <resultMap id="${domain.className}ResultMap" class="${complete_package_class_name}">
    <#list tableDO.columnList as col>
        <result column="${col.name}" property="${col.propertyName}"/>
	</#list>
    </resultMap>

	<sql id="${ibatis_class_dao}_all_column_fields">
	      ${tableDO.allColumnString}
 	</sql>

	<sql id="${ibatis_class_dao}_dynamic_where_fields"><!-- 这种动态查询OK WHERE AND IBATIS会做自动处理  xml转义字符需要 <![CDATA[   ]]> 标签  -->
		<dynamic prepend="WHERE"><!-- 过滤掉主键和时间字段  -->
	 <#list tableDO.columnList as col>
		<#if col.name!=tableDO.primaryKey && col.propertyType!="Date">
		<#if col.propertyType=="String">
		   <isNotEmpty  prepend="AND" property="${col.propertyName}">
		   		 ${col.name} = #${col.propertyName}#
		   </isNotEmpty>
		 <#else>
		   <isNotNull  prepend="AND" property="${col.propertyName}">
		   		 ${col.name} = #${col.propertyName}#
		   </isNotNull>
		 </#if>
	   </#if>
	 </#list>
		</dynamic>
 	</sql>

 	<select id="${ibatis_class_dao}.selectById" resultMap="${domain.className}ResultMap" parameterClass="long">
	     SELECT
	     	<include refid="${ibatis_class_dao}_all_column_fields" />
		 FROM
		 	${tableDO.tableName}
		 WHERE
		 	${tableDO.primaryKey} = #${tableDO.primaryProperty}#
 	</select>

 	<insert id="${ibatis_class_dao}.insert" parameterClass="${complete_package_class_name}" >
	     ${tableDO.insertSql}
 		<selectKey resultClass="long" keyProperty="${tableDO.primaryProperty}">
        	SELECT LAST_INSERT_ID() AS ${tableDO.primaryKey}
   		</selectKey>
 	</insert>

 	<update id="${ibatis_class_dao}.updateById" parameterClass="${complete_package_class_name}">
 			 ${tableDO.updateSql}
 	</update>

 	<delete id="${ibatis_class_dao}.deleteById" parameterClass="long">
 			${tableDO.deleteSql}
 	</delete>

 	<update id="${ibatis_class_dao}.update.dynamic" parameterClass="${complete_package_class_name}">
		 UPDATE ${tableDO.tableName}
		 SET ${tableDO.dynamicUpdateFirstFieldName} = #${tableDO.dynamicUpdateFirstProperty}#
		 <dynamic prepend=""><!-- dynamic前必须要有字段，否则将报错 -->
		 <#list tableDO.columnList as col>
			<#if col.name!=tableDO.primaryKey && col.propertyType!="Date">
			<#if col.propertyType=="String">
			   <isNotEmpty  prepend="," property="${col.propertyName}">
			   		 ${col.name} = #${col.propertyName}#
			   </isNotEmpty>
			<#else>
			   <isNotNull prepend="," property="${col.propertyName}">
			   		 ${col.name} = #${col.propertyName}#
			   </isNotNull>
			</#if>
		   </#if>
		 </#list>
		 </dynamic>
		 WHERE ${tableDO.primaryKey} = #${tableDO.primaryProperty}#
 	</update>

 	<select id="${ibatis_class_dao}.select.dynamic" resultMap="${domain.className}ResultMap" parameterClass="${complete_package_class_name}">
	     SELECT
	     	<include refid="${ibatis_class_dao}_all_column_fields" />
		 FROM
		 	${tableDO.tableName}
			<include refid="${ibatis_class_dao}_dynamic_where_fields" />
 	</select>

 	<select id="${ibatis_class_dao}.select.dynamic.count" resultClass="long" parameterClass="${complete_package_class_name}">
	     SELECT
	     	count(1)
		 FROM
		 	${tableDO.tableName}
	 	<include refid="${ibatis_class_dao}_dynamic_where_fields" />
 	</select>

 	<select id="${ibatis_class_dao}.select.dynamic.page.query" resultMap="${domain.className}ResultMap" parameterClass="${complete_package_class_name}">
	     SELECT
	     	<include refid="${ibatis_class_dao}_all_column_fields" />
		 FROM
		 	${tableDO.tableName}
	 	<include refid="${ibatis_class_dao}_dynamic_where_fields" />
	 	Limit ${tableDO.pageStartFieldName?replace('{','#')?replace('}','#')},${tableDO.pageSizeFieldName?replace('{','#')?replace('}','#')}
 	</select>

</sqlMap>
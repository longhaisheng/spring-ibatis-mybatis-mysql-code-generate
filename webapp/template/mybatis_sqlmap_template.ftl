<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign len = domain.className?index_of("DO")>
<#assign complete_package_class_name="${domain.packageName}.${domain.className}">
<#assign ibatis_class_dao="Mybatis${domain.className?substring(0,len)}DAO">
<#assign resultMap="${domain.className}ResultMap">
<#assign primaryProperty="{${tableDO.primaryProperty}}">

<mapper namespace="${domain.packageName}.${domain.className?substring(0,len)}Mapper">

	<resultMap type="${complete_package_class_name}" id="${resultMap}">
	<#if tableDO.primaryProperty?exists>
		<id column="${tableDO.primaryKey}" property="${tableDO.primaryProperty}" />
	</#if>
    <#list tableDO.columnList as col>
    	<#if col.name!=tableDO.primaryKey>
        <result column="${col.name}" property="${col.propertyName}"/>
    	</#if>
	</#list>
	</resultMap>

	<sql id="${ibatis_class_dao}_all_column_fields">
	      ${tableDO.allColumnString}
 	</sql>

	<sql id="${ibatis_class_dao}_dynamic_where_fields"><!-- xml转义字符需要 <![CDATA[   ]]> 标签-->
		<where>
	 <#list tableDO.columnList as col>
	 	<#assign aa="{${col.propertyName}}">
		<#if col.name!=tableDO.primaryKey  && col.propertyType!="Date">
		<#if col.propertyType=="String">
			<if test="${col.propertyName} != null  and ${col.propertyName} != '' "> AND ${col.name}=#${aa} </if>
		 <#else>
			<if test="${col.propertyName} != null  and ${col.propertyName} != '' "> AND ${col.name}=#${aa} </if>
		 </#if>
	   </#if>
	 </#list>
		</where>
 	</sql>

	<select id="${ibatis_class_dao}_selectById" parameterType="long" resultMap="${resultMap}">
	SELECT
		<include refid="${ibatis_class_dao}_all_column_fields" />
	FROM
		${tableDO.tableName}
	WHERE
		 ${tableDO.primaryKey} = #${primaryProperty}
	</select>

    <insert id="${ibatis_class_dao}_insert" parameterType="${complete_package_class_name}" useGeneratedKeys="true">
        <selectKey resultType="long" keyProperty="${tableDO.primaryProperty}" order="AFTER">
            SELECT LAST_INSERT_ID() AS ${tableDO.primaryKey}
        </selectKey>
        INSERT INTO ${tableDO.tableName}(
		<#assign j=0>
		<#list tableDO.columnList as col>
			<#assign j=j+1>
			<#assign aa="{${col.name}}">
			<#if tableDO.columnList?size!=j>
				${col.name},
			<#else>
				${col.name}
			</#if>
		</#list>
		)values(
		<#assign i=0>
		<#list tableDO.columnList as col>
			<#assign i=i+1>
			<#assign aa="{${col.propertyName}}">
			<#if tableDO.columnList?size!=i>
				#${aa},
			<#else>
				#${aa}
			</#if>
		</#list>
		)
    </insert>

	<update id="${ibatis_class_dao}_updateById" parameterType="long">
		UPDATE ${tableDO.tableName}
		SET
		<#assign k=0>
		<#list tableDO.columnList as col>
			<#assign k=k+1>
			<#assign aa="{${col.propertyName}}">
			<#if col.name!=tableDO.primaryKey>
			<#if tableDO.columnList?size!=k>
				${col.name} = #${aa},
			<#else>
				${col.name} = #${aa}
			</#if>
			</#if>
		</#list>
		WHERE
			${tableDO.primaryKey} = #${primaryProperty}
	</update>

	<delete id="${ibatis_class_dao}_deleteById" parameterType="long">
		DELETE FROM ${tableDO.tableName} WHERE ${tableDO.primaryKey} = #${primaryProperty}
	</delete>

	<update id="${ibatis_class_dao}_update_dynamic" parameterType="${complete_package_class_name}">
		UPDATE ${tableDO.tableName}
		<set>
		<#list tableDO.columnList as col>
			<#assign aa="{${col.propertyName}}">
			<#if col.name!=tableDO.primaryKey>
			<if test="${col.propertyName} != null  and ${col.propertyName} != '' ">${col.name}=#${aa},</if>
			</#if>
		</#list>
		</set>
		WHERE ${tableDO.primaryKey} = #${primaryProperty}
	</update>

 	<select id="${ibatis_class_dao}_select_dynamic" resultMap="${resultMap}" parameterType="${complete_package_class_name}">
	     SELECT
	     	<include refid="${ibatis_class_dao}_all_column_fields" />
		 FROM
		 	${tableDO.tableName}
			<include refid="${ibatis_class_dao}_dynamic_where_fields" />
 	</select>

 	<select id="${ibatis_class_dao}_select_dynamic_count" resultType="long" parameterType="${complete_package_class_name}">
	     SELECT
	     	count(1)
		 FROM
		 	${tableDO.tableName}
	 	<include refid="${ibatis_class_dao}_dynamic_where_fields" />
 	</select>

 	<select id="${ibatis_class_dao}_select_dynamic_page_query" resultMap="${resultMap}" parameterType="${complete_package_class_name}">
	     SELECT
	     	<include refid="${ibatis_class_dao}_all_column_fields" />
		 FROM
		 	${tableDO.tableName}
	 	<include refid="${ibatis_class_dao}_dynamic_where_fields" />
	 	Limit #${tableDO.pageStartFieldName},#${tableDO.pageSizeFieldName}
 	</select>

</mapper>
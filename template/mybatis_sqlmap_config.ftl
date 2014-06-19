<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!--typeAliases>
		<typeAlias alias="addressDO" type="com.sina.domain.AddressDO" />
	</typeAliases-->
	<mappers>
		<!-- sqlmap xml path config -->
	<#list sql_map_config_xml_set as sqlmp>
		<mapper resource="${sqlmp}" />
	</#list>
	</mappers>
</configuration>
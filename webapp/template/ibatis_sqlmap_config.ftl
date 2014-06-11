<?xml version="1.0" encoding="GB2312"?>
<!DOCTYPE sqlMapConfig PUBLIC "-//iBATIS.com//DTD SQL Map Config 2.0//EN"
        "http://www.ibatis.com/dtd/sql-map-config-2.dtd">
<sqlMapConfig>
	<settings cacheModelsEnabled="false" enhancementEnabled="false"
		lazyLoadingEnabled="false" maxRequests="3000" maxSessions="3000"
		maxTransactions="3000" <#if use_statement_name_spaces?string=="1">useStatementNamespaces="true"<#else>useStatementNamespaces="false"</#if> />
	
		<!-- sqlmap xml path config -->	
	<#list sql_map_config_xml_set as sqlmp>	
		<sqlMap resource="${sqlmp}" />
	</#list>
	
</sqlMapConfig>
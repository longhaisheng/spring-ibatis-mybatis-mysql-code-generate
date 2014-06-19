<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
<#assign viewParam="${param?substring(0,len)}">

<form name="addform" id="addform" commandName="${viewParam}" action="/${viewParam}/save" method="POST">
	<#list tableDO.columnList as col>
    	<div>
        	<form:label path="${col.propertyName}">${col.comment}:</form:label>
			<form:input path="${col.propertyName}" />
			<form:errors path="${col.propertyName}" cssClass="error" />
		</div>
    </#list>
    	<div><input type="submit" value="确定添加" /></div>
</form>

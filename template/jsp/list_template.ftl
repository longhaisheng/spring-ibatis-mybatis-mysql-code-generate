<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
<#assign viewParam="${param?substring(0,len)}">

<form name="addform" id="addform" action="/${viewParam}/save" method="POST">
    <dl>
    	<#list tableDO.columnList as col>
        <dt>Login:</dt>
            <dd><${tempateStringDO.formInput}  "${viewParam}.${col.propertyName}" />
            <dd><${tempateStringDO.showErrors} "<br>" />
        </#list>
        <br>
            <dd><input type="submit" value="搜索" />
    </dl>
</form>
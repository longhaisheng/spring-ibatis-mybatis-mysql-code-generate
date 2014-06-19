<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
<#assign viewParam="${param?substring(0,len)}">

<form name="addform" id="addform" action="/${viewParam}/save" method="POST">
    	<#list tableDO.columnList as col>
        <div>
        	${col.comment}:
            <${tempateStringDO.formInput}  "${viewParam}.${col.propertyName}" />
            <${tempateStringDO.showErrors} "<br>" />
        </div>
        </#list>
        <br>
        <div><input type="submit" value="确定添加" /></div>
</form>
<#assign len = domain.className?index_of("DO")>
<#assign param="${domain.className?uncap_first}">
<#assign viewParam="${param?substring(0,len)}">

<form name="addform" id="addform" action="/${viewParam}/save" method="POST">
    <dl>
    	<#list domain.propertyList as property>
        <dt>Login:</dt>
            <dd><@spring.formInput  "${viewParam}.${property}" />
            <dd><@spring.showErrors "<br>" />
        </#list>
        <br>
            <dd><input type="submit" value="确定添加" />
    </dl>
</form>
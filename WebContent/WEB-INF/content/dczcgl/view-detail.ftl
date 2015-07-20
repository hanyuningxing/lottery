<#assign contents=createForm.content />
<#if contents??>
	<#list contents?split(",") as content>
		${content}
	</#list>
</#if>


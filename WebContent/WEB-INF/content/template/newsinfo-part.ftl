<#if newsInfoDataList?? && newsInfoDataList?size gt 0>
<#list newsInfoDataList as data>
<li>
	<span> ${data.createTime?string("MM-dd")} </span>
	 <#if data.titleLink??>
		<a href="${data.titleLink}" target="_blank">${data.shortTitle!}</a>
	<#else>
		<a href="/info/${data.id!}.html" target="_blank">${data.shortTitle!}</a>
	 </#if>
</li>
</#list>
</#if>
    

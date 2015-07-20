<#if newsInfoDataList?? && newsInfoDataList?size gt 0>
<#list newsInfoDataList as data>
<li>
	<span> ${data.createTime?string("MM-dd")} </span>
	<a href="/info/${data.id!}.html" target="_blank">${data.shortTitle!}</a>
</li>
</#list>
</#if>
    

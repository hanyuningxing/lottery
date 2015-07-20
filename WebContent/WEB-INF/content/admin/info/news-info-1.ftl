<#--焦点新闻的模板-->
<ul class="li_row">
<#list data as data>
	  <li>
	   	   <#if data.titleTypeLink??>
                          <#if data.titleTypeLink=='#'>
                            	[${data.titleType!}]
                          	<#else>[<a href="${data.titleTypeLink!}">${data.titleType!}</a>]
                     	  </#if>
                          <#else>[${data.titleType!}]
           </#if>
		   <#if data.titleLink??>
                          <#if data.titleLink=='#'>
                            	<a href="${base}/info/info.action?id=${data.id!}" target="_blank">${data.title!}</a>
                          	<#else><a href="${data.titleLink}" target="_blank">${data.title!}</a>
                     	  </#if>
                          <#else><a href="${base}/info/info.action?id=${data.id!}" target="_blank">${data.title!}</a>
           </#if>
	  </li>
</#list>
</ul>
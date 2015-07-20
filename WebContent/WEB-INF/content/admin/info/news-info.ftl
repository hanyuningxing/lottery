<#--焦点新闻的模板-->
<ul class="li_row ul_id">
<#list data as data>
	  <li>
       <#if data.titleTypeLink??>
                          <#if data.titleTypeLink=='#'>
                            	[<span class="color_red weight700">${data.titleType!}</span>]
                          	<#else>[<a href="${data.titleTypeLink!}"><span class="color_red weight700">${data.titleType!}</span></a>]
                     	  </#if>
                          <#else>[<span class="color_red weight700">${data.titleType!}</span>]
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
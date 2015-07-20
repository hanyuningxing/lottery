  <#if adImagesList??&& adImagesList?size gt 0>
	  <#list adImagesList as data>
		 <li><a target="_blank" href="${data.link!}" title='${data.word!}'><img src="${base}${data.name!}" alt='${data.word!}'"></a></li>
	  </#list>
  </#if>
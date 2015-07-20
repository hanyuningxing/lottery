<#if newsIndexNeedList?? && newsIndexNeedList?size gt 0>
<#list newsIndexNeedList as data>
<li>
		<#if data.titleLink??>
                <#if data.titleLink=='#'>
                	<a href="/info/${data.id!}.html" target="_blank">              
                    <span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
						${data.shortTitle!} 
					</span>
                    </a>
              	<#else>
              		<a href="${data.titleLink}" target="_blank">
              		<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
						${data.shortTitle!} 
					</span>
              		</a>
         	    </#if>
         <#else>
          <a href="/info/${data.id!}.html" target="_blank">              
                 	<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
						${data.shortTitle!} 
					</span>
         		</a>
         </#if>
</li>
</#list>
</#if>
    
		
		
		
							
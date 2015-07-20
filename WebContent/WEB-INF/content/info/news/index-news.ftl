<#if newsIndexNeedList?? && newsIndexNeedList?size gt 0>
<#list newsIndexNeedList as data>
<#if data_index == 0 >
	<h2>
	<a  href="${base}/info/news-${data.id!}.html" target="_blank">
	 <#if data.titleLink??>
            <#if data.titleLink=='#'>
            	<a href="/info/${data.id!}.html" target="_blank">
				<#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
					<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
					${data.shortTitle!} 
					</span>
					</a>
					</h2>
          	<#else>
          		<a href="${data.titleLink}" target="_blank">
          		<#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
				<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
				${data.shortTitle!} 
				</span>
				</a>
				</h2>
     	    </#if>
     <#else>
	     <a href="/info/${data.id!}.html" target="_blank">
	     <#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
		<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
		${data.shortTitle!} 
		</span>
		</a>
		</h2>
     </#if>

<#else>
		<li>
		 <#if data.titleLink??>
                <#if data.titleLink=='#'>
                	<a href="/info/${data.id!}.html" target="_blank">
                	<#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
					<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
					${data.shortTitle!} 
					</span>
                	</a>
              	<#else>
              		<a href="${data.titleLink}" target="_blank">
              		<#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
					<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
					${data.shortTitle!} 
					</span>
              		</a>
         	    </#if>
         <#else><a href="/info/${data.id!}.html" target="_blank">
         
        	 <#if data.subType??>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>
			<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
			${data.shortTitle!} 
			</span>
         
         </a>
         </#if>
	</li>
</#if>
</#list>
</#if>
    

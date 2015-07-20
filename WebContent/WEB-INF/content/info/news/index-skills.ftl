 <div class="czzl_waikuang">
	<div class="czzl_tab">
		 <ul>
		 <#if lotteryList??&& (lotteryList![])?size gt 0>
		 	 <#assign index = 1>
			<#list lotteryList as lottery>
			<#if index==1>
			  <li><a href="javascript:void(0);" targer="_blank" class="graychar" onmousemove="showTabs(${index})" ><span>${lottery.lotteryName!}</span></a></li>
			<#else>
			 <li><a href="javascript:void(0);" targer="_blank" class="graychar" onmousemove="showTabs(${index})" >${lottery.lotteryName!}</a></li>
			</#if>
			<#assign index = index+1 /> 
			</#list>
		 </#if>
		 </ul>
	 </div>
	 
	 <#if ssqList?? && ssqList?size gt 0>
	 	<div class="czzl_s"  id="ListDiv_1">
		 <ul>
		   <#list ssqList as data>
			 <li>
			 <#if data.titleLink??>
			        <#if data.titleLink=='#'>
			        	<a href="/info/${data.id!}.html" target="_blank" class="graychar">
			        	<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>
			        	</a>
			      	<#else>
			      		<a href="${data.titleLink}" target="_blank"  class="graychar" >
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>      		
			      		</a>
			 	    </#if>
			 <#else><a href="/info/${data.id!}.html" target="_blank" class="graychar">
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span> </a>
			 </#if>
			 
			 </li>
			</#list> 
		  </ul>	  
		  </div>
	 </#if>
	  <#if dltList?? && dltList?size gt 0>
	 	<div class="czzl_s"  id="ListDiv_2" style="display:none;">
		 <ul>
		   <#list dltList as data>
			 <li>
			 <#if data.titleLink??>
			        <#if data.titleLink=='#'>
			        	<a href="/info/${data.id!}.html" target="_blank" class="graychar">
			        	<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>
			        	</a>
			      	<#else>
			      		<a href="${data.titleLink}" target="_blank"  class="graychar" >
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>      		
			      		</a>
			 	    </#if>
			 <#else><a href="/info/${data.id!}.html" target="_blank" class="graychar">
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span> </a>
			 </#if>
			 </li>
			</#list> 
		  </ul>	  
		  </div>
	 </#if>
	  <#if welfare3dList?? && welfare3dList?size gt 0>
	 	<div class="czzl_s" id="ListDiv_3" style="display:none;">
		 <ul>
		   <#list welfare3dList as data>
			 <li>
			 <#if data.titleLink??>
			        <#if data.titleLink=='#'>
			        	<a href="/info/${data.id!}.html" target="_blank" class="graychar">
			        	<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>
			        	</a>
			      	<#else>
			      		<a href="${data.titleLink}" target="_blank"  class="graychar" >
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>      		
			      		</a>
			 	    </#if>
			 <#else><a href="/info/${data.id!}.html" target="_blank" class="graychar">
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span> </a>
			 </#if>
			 
			 </li>
			</#list> 
		  </ul>	  
		  </div>
	 </#if>
	 
	 <#if qyhList?? && qyhList?size gt 0>
	 	<div class="czzl_s" id="ListDiv_4" style="display:none;">
		 <ul>
		   <#list qyhList as data>
			 <li>
			<#if data.titleLink??>
			        <#if data.titleLink=='#'>
			        	<a href="/info/${data.id!}.html" target="_blank" class="graychar">
			        	<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>
			        	</a>
			      	<#else>
			      		<a href="${data.titleLink}" target="_blank"  class="graychar" >
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>      		
			      		</a>
			 	    </#if>
			 <#else><a href="/info/${data.id!}.html" target="_blank" class="graychar">
						<#if data.subType??><span>[${data.subType.typeName!}]&nbsp;&nbsp;</span></#if>
						<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span> </a>
			 </#if>
			 </li>
			</#list> 
		  </ul>	  
		  </div>
	 </#if>
</div>
<script type="text/javascript">
function showTabs(n) {
  var tabsNumber = 5;
  for (i = 1; i < tabsNumber; i++) {
      if (i == n) {
          document.getElementById("ListDiv_" + i).style.display = "block";
      } else {
          document.getElementById("ListDiv_" + i).style.display = "none";
      }
  }
}
</script>




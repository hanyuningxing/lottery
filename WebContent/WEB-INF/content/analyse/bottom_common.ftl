
	<div class="moreabout">
	<!--<div class="more_title">关于的相关新闻</div>-->
	<div class="more">
			<#if hotNews??&& hotNews?size gt 0>
					<div class="gd_link">
		    		<ul>	    
		    		<#list hotNews as data>
		    			<#if data??&&data_index<5>  				
			                     <#if data.titleLink??>
			                            <#if data.titleLink=='#'>			                            
			                            	 <li><a href="/info/${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>	                           	
			                          	<#else>
			                          		<li><a href="${data.titleLink}" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>                           	                  		
			                     	    </#if>
			                     <#else>
			                     		<li>
			                     			<a href="/info/${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a>
			                     		</li>	                           			                     	                     
			                     </#if>   
			              </#if>                         
		               </#list>
		               </ul>
		               </div>
		            <div class="gd_link_fgx"></div>
		               
		            <div class="gd_link">
		    		<ul>	    
		    		<#list hotNews as data>
		    			<#if data??&&data_index gt 4>  				
			                     <#if data.titleLink??>
			                            <#if data.titleLink=='#'>
			                            	 <li><a href="/info/${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>	                           	
			                          	<#else>
			                          		<li><a href="${data.titleLink}" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>                           	                  		
			                     	    </#if>
			                     <#else>
			                     		<li>
			                     			<a href="/info/${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a>
			                     		</li>	                           			                     	                     
			                     </#if>   
			              </#if>                         
		               </#list>
		               </ul>
		               </div>
		    		                                    	    	
		    <#else> 
				   无记录
		    </#if>
				
		</div>
	</div>
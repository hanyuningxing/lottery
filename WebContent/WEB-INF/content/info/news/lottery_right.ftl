    
            <div class="rigbanbg"><span><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=${lottery}" target="_blank" class="charr">更多>></a></span>${lottery.lotteryName!}预测 </div>
		    <div class="all10px">
		     <ul class="riglist">
			      <#if RIGHT_FORECAST?? && RIGHT_FORECAST?size gt 0>
					   
							<#list RIGHT_FORECAST as data>
								<li><a href="${base}/info/news-${data.id!}.html" target="_blank">${data.titleString!}</a> </li>
							</#list>
							 
				  </#if>
             </ul>
		    </div>	
    
           <div class="rigbanbg"><span><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=${lottery}" target="_blank" class="charr">更多>></a></span>${lottery.lotteryName!}技巧 </div>
		    <div class="all10px">
		     <ul class="riglist">
			      <#if RIGHT_SKILLS?? && RIGHT_SKILLS?size gt 0>
					   
							<#list RIGHT_SKILLS as data>
								<li><a href="${base}/info/news-${data.id!}.html" target="_blank">${data.titleString!}</a> </li>
							</#list>
							 
				  </#if>
             </ul>
		    </div>	
		    
		    <div class="rigbanbg"><span><a href="${base}/info/news!infoList.action?type=RESULTNOTICE&lottery=${lottery}" target="_blank" class="charr">更多>></a></span>${lottery.lotteryName!}开奖公告 </div>
		    <div class="all10px">
		     <ul class="riglist">
			      <#if RIGHT_RESULTNOTICE?? && RIGHT_RESULTNOTICE?size gt 0>
					   
							<#list RIGHT_RESULTNOTICE as data>
								<li><a href="${base}/info/news-${data.id!}.html" target="_blank">${data.titleString!}</a> </li>
							</#list>
							 
				  </#if>
             </ul>
		    </div>	
<#if activityInfoDTOList?? && activityInfoDTOList?size gt 0>
<#list activityInfoDTOList as data>
<li>会员 ${data.registname!} 参与${data.userActivityType.activityTypeName!} 获得 <span> #{(data.amount!0);M2}</span>元 彩金</li>
</#list>
</#if>
    
		
		
		
							

<@override name="content">
	  <div class="tdbback">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
	      <tr class="v01tr">
	      	<td>方案名</td>
	        <td>用户名</td>
	        <td>已追金额</td>
	        <td>追号总奖金</td>
	        <td>起始倍数</td>
	        <td>期望回报率</td>
	        <td>创建时间</td>
	        <td>查看详情</td>
	      </tr>
	     <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
			<#list pagination.result as data>
		      <tr class="v02tr">
		      	 <td>${data.chasePlanName!""}</td>
		      	 <td>${data.userName}</td>
		       	 <td>${data.chasedCost!0}</td>
		         <td>${data.totalPrize!0}</td>
		         <td>${data.mutiple}</td>
		         	<#if data.returnRateLevel == 1>
			         	<td>110%~120%</td>
			         <#elseif data.returnRateLevel == 2>
			         	<td>110%~130%</td>
			         <#elseif data.returnRateLevel == 3>
			         	<td>120%~130%</td>
			         <#elseif data.returnRateLevel == 4>
			         	<td>130%~140%</td>
			         <#elseif data.returnRateLevel == 5>
			         	<td>140%~150%</td>
			         </#if>
		         <td>${data.createTime}</td>
		         <td><a href="${base}/jczq/chasePlanDetail!list.action?jczqChasePlanId=${data.id}" target="_blank">详情</a></td>
		      </tr>
		    </#list>
		 <#else>
			<tr>
			     <td colspan="12" align="center" >暂无记录.</td>
			</tr>
		</#if>
				 <tr>
                  <td height="50" colspan="12" align="center" bgcolor="#FFFFFF">
                  	<#import "../../macro/pagination.ftl" as b />
			    	<@b.page />
					</td>
                </tr>
	    </table>
	 </div>
</@override>

<@extends name="base.ftl"/> 
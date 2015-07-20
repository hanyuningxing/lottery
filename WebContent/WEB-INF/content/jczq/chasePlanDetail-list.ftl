
<@override name="content">
	  <div class="tdbback">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
	      <tr class="v01tr">
	        <td>序</td>
	        <td>累计投注金额</td>
	        <td>本次投注金额</td>
	        <td>预计中奖金额</td>
	        <td>回报率</td>
	        <td>预计盈利奖金</td>
	        <td>赔率</td>
	        <td>方案状态</td>
	        <td>投注时间</td>
	        <td>追投状态</td>
	        <td>操作</td>
	      </tr>
	     <#assign i=0 />
	     <#assign chasedCost = 0 />
	     <#assign schemeCost = 0 />
	     <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
	     	<#if pagination.result?size gt 1>
				<#list pagination.result as data>
					<#if data.schemeCost??>
						<#assign schemeCost = data.schemeCost />
					<#else>
						<#assign schemeCost = chasedCost*2 />
					</#if>
					<#if data.chasedCost??>
						<#assign chasedCost = data.chasedCost />
					<#else>
						<#assign chasedCost = chasedCost + schemeCost />
					</#if>
				  <#assign i=i+1 />
			      <tr class="v02tr">
			      	 <td>${i}</td>
			       	 <td>${chasedCost!0}</td>
			         <td>${schemeCost!0}<#if !data.hasPay?? || !data.hasPay><span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;(建议)</span></#if></td>
			         
			         <#if data.prizeAfterTax??>
			         	<td>${data.prizeAfterTax!0}</td>
			         	<td>${data.prizeAfterTax/chasedCost*100}%</td>
			         	<td style="color:red"><#if data.prizeAfterTax-chasedCost gt 0>${data.prizeAfterTax-chasedCost}<#else>0</#if></td>
			         	 <td><#if i gt 1>#{1.1*1.5;M2}~#{1.2*1.5;M2}<#else>1.1~1.2</#if></td>
			         <#elseif data.returnRateLevel == 1>
			         	<td>${chasedCost*1.1!0}~${chasedCost*1.2!0}</td>
			         	<td>110%~120%</td>
			         	<td style="color:red">#{0.1*chasedCost;M2}~#{0.2*chasedCost;M2}</td>
			         	 <td><#if i gt 1>#{1.1*1.5;M2}~#{1.2*1.5;M2}<#else>1.1~1.2</#if></td>
			         <#elseif data.returnRateLevel == 2>
			         	<td>${chasedCost*1.1!0}~${chasedCost*1.3!0}</td>
			         	<td>110%~130%</td>
			         	<td style="color:red">#{0.1*chasedCost;M2}~#{0.3*chasedCost;M2}</td>
			         	 <td><#if i gt 1>#{1.1*1.5;M2}~#{1.3*1.5;M2}<#else>1.1~1.3</#if></td>
			         <#elseif data.returnRateLevel == 3>
			        	 <td>${chasedCost*1.2!0}~${chasedCost*1.3!0}</td>
			         	<td>120%~130%</td>
			         	<td style="color:red">#{0.2*chasedCost;M2}~#{0.3*chasedCost;M2}</td>
			         	  <td><#if i gt 1>#{1.2*1.5;M2}~#{1.3*1.5;M2}<#else>1.2~1.3</#if></td>
			         <#elseif data.returnRateLevel == 4>
			        	 <td>${chasedCost*1.3!0}~${chasedCost*1.4!0}</td>
			         	<td>130%~140%</td>
			         	<td style="color:red">#{0.3*chasedCost;M2}~#{0.4*chasedCost;M2}</td>
			         	  <td><#if i gt 1>#{1.3*1.5;M2}~#{1.4*1.5;M2}<#else>1.3~1.4</#if></td>
			         <#elseif data.returnRateLevel == 5>
			        	 <td>${chasedCost*1.4!0}~${chasedCost*1.5!0}</td>
			         	<td>140%~150%</td>
			         	<td style="color:red">#{0.4*chasedCost;M2}~#{0.5*chasedCost;M2}</td>
			         	  <td><#if i gt 1>#{1.4*1.5;M2}~#{1.5*1.5;M2}<#else>1.4~1.5</#if></td>
			         </#if>
			         
			         <td><#if data.schemePrintState??>${data.schemePrintState.stateName!}</#if></td> 
			         <td><#if data.schemeCreateTime??>${data.schemeCreateTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			         <td><#if data.hasPay?? && data.hasPay>已追投<#else>未追投</#if></td>
			         <#if data.hasPay>
			         	<td><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />" target="_blank">方案详情</a></td>
			         <#elseif isAllWinningStatusUpdate>
			         	<td><a href="${base}/jczq/scheme!editNew.action?playTypeWeb=DGP&returnRateLevel=${data.returnRateLevel}&jczqChasePlanDetailId=${data.id}" target="_blank">赛事筛选</a></td>
			         <#else>
			         	<td></td>
			         </#if>
			      </tr>
			    </#list>
			  <#else>
			  	<#list pagination.result as data>
				  <#assign i=i+1 />
				  <#assign schemeCost = data.mutiple*4 />
				  <#assign chasedCost = data.mutiple*4 />
			      <tr class="v02tr">
			      	 <td>${i}</td>
			       	 <td>${data.mutiple*4}</td>
			         <td>${data.mutiple*4}<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;(建议)</span></td>
			         <#if data.returnRateLevel == 1>
			         	<td>${data.mutiple*4*1.1!0}~${data.mutiple*4*1.2!0}</td>
			         	<td>110%~120%</td>
			         	<td style="color:red">${data.mutiple*4*0.1!0}~${data.mutiple*4*0.2!0}</td>
			         	<td>1.1~1.2</td>
			         <#elseif data.returnRateLevel == 2>
			         	<td>${data.mutiple*4*1.1!0}~${data.mutiple*4*1.3!0}</td>
			         	<td>110%~130%</td>
			         	<td style="color:red">${data.mutiple*4*0.1!0}~${data.mutiple*4*0.3!0}</td>
			         	<td>1.1~1.3</td>
			         <#elseif data.returnRateLevel == 3>
			        	 <td>${data.mutiple*4*1.2!0}~${data.mutiple*4*1.3!0}</td>
			         	<td>120%~130%</td>
			         	<td style="color:red">${data.mutiple*4*0.2!0}~${data.mutiple*4*0.3!0}</td>
			         	 <td>1.2~1.3</td>
			         <#elseif data.returnRateLevel == 4>
			        	 <td>${data.mutiple*4*1.3!0}~${data.mutiple*4*1.4!0}</td>
			         	<td>130%~140%</td>
			         	<td style="color:red">${data.mutiple*4*0.3!0}~${data.mutiple*4*0.4!0}</td>
			         	 <td>1.3~1.4</td>
			         <#elseif data.returnRateLevel == 5>
			        	 <td>${data.mutiple*4*1.4!0}~${data.mutiple*4*1.5!0}</td>
			         	<td>140%~150%</td>
			         	<td style="color:red">${data.mutiple*4*0.4!0}~${data.mutiple*4*0.5!0}</td>
			         	 <td>1.4~1.5</td>
			         </#if>
			         <td></td>
			         <td></td>
			         <td>未追投</td>
			         <td><a href="${base}/jczq/scheme!editNew.action?playTypeWeb=DGP&returnRateLevel=${data.returnRateLevel}&jczqChasePlanDetailId=${data.id}&isFirstChased=1" target="_blank">赛事筛选</a></td>
			      </tr>
			    </#list>
			  </#if>
		</#if>
			
			<#list i+1..10 as count>
				<#assign schemeCost = chasedCost*2 />
				<#assign chasedCost = chasedCost + schemeCost />
				<tr class="v02tr">
			      	 <td>${count}</td>
			       	 <td>${chasedCost!0}</td>
			         <td>${schemeCost!0}<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;(建议)</span></td>
			          <#if returnRateLevel == 1>
			         	<td>${chasedCost*1.1!0}~${chasedCost*1.2!0}</td>
			         	<td>110%~120%</td>
			         	<td style="color:red">${chasedCost*0.1!0}~${chasedCost*0.2!0}</td>
			         	<td>#{(1.1*1.5);M2}~#{1.2*1.5;M2}</td>
			         <#elseif returnRateLevel == 2>
			         	<td>${chasedCost*1.1!0}~${chasedCost*1.3!0}</td>
			         	<td>110%~130%</td>
			         	<td style="color:red">${chasedCost*0.1!0}~${chasedCost*0.3!0}</td>
			         	<td>#{1.1*1.5;M2}~#{1.3*1.5;M2}</td>
			         <#elseif returnRateLevel == 3>
			        	<td>${chasedCost*1.2!0}~${chasedCost*1.3!0}</td>
			         	<td>120%~130%</td>
			         	<td style="color:red">${chasedCost*0.2!0}~${chasedCost*0.3!0}</td>
			         	 <td>#{1.2*1.5;M2}~#{1.3*1.5;M2}</td>
			         <#elseif returnRateLevel == 4>
			        	<td>${chasedCost*1.3!0}~${chasedCost*1.4!0}</td>
			         	<td>130%~140%</td>
			         	<td style="color:red">${chasedCost*0.3!0}~${chasedCost*0.4!0}</td>
			         	 <td>#{1.3*1.5;M2}~#{1.4*1.5;M2}</td>
			         <#elseif returnRateLevel == 5>
			        	<td>${chasedCost*1.4!0}~${chasedCost*1.5!0}</td>
			         	<td>140%~150%</td>
			         	<td style="color:red">${chasedCost*0.4!0}~${chasedCost*0.5!0}</td>
			         	 <td>#{1.4*1.5;M2}~#{1.5*1.5;M2}</td>
			         </#if>
			         <td></td>
			         <td></td>
			         <td>未追投</td>
			          <td></td>
			      </tr> 
			</#list>
		
	    </table>
	 </div>
</@override>

<@extends name="base.ftl"/> 
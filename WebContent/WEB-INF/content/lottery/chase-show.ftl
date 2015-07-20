<head>
	<title>我的追号</title>
	<meta name="decorator" content="trade" />
	<meta name="menu" content="chase" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'chase'/>
       <#include "../user/left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
     <form action="<@c.url value='/lottery/auto!list.action' />" method="get">
	    <div class="crighttop">
	        <strong>追号情况</strong><#include "/common/message.ftl" />
	   </div>
	 </form>
    <div class="kong10"></div>
    
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="28" height="28">编号</td>
       	<td>彩种</td>
		<td>状态</td>
		<td>追号总金额</td>
		<td>已追金额</td>
		<td>中奖停追</td>
		<td>开号停追[高频]</td>
		<td>创建时间</td>
      </tr>
	  <tr class="center_trhover" align="center"> 
			<td height="32">${chasePlan.id!}</td>
			<td><#if chasePlan.lotteryType??>${chasePlan.lotteryType.lotteryName!}</#if></td>
			<td>${chasePlan.state.stateName!}</td>
			<#assign frequent=chasePlan??&&chasePlan.lotteryType??&&chasePlan.lotteryType.category=='FREQUENT'!false>
				<td>
				    <#if frequent>
						<#if chasePlan.zoomTotalCost??>#{chasePlan.zoomTotalCost;M2}</#if>
                    <#else>
                    	${chasePlan.totalCost}
                    </#if>
				</td>
				<td>
				    <#if frequent>
						<#if chasePlan.zoomChasedCost??>#{chasePlan.zoomChasedCost;M2}</#if>
                    <#else>
                    	${chasePlan.chasedCost}
                    </#if>
                </td>
			<td><#if chasePlan.wonStop>是<#else>否</#if></td>
			<td><#if chasePlan.chasedCost.outNumStop??> <#if chasePlan.chasedCost.outNumStop>是<#else>否</#if></#if></td>
			<td><#if chasePlan.createTime??>${chasePlan.createTime?string("yy-MM-dd HH:mm")}</#if></td>
	  </tr>
	      </table>
	  <#if chasePlan.capacityProfitContent??>
	 <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
	  <tr class="center_tablegray" align="center">
       	<td width="18">追号期数</td>
		<td width="18">起始倍数</td>
		<td width="18">已经投入</td>
		<td width="18">单注奖金</td>
		<td width="25"></td>
		<td width="25"></td>
		<td width="25"></td>
		<td width="25"></td>
		<td width="25"></td>
		<td width="25"></td>
      </tr>
       <tr class="center_trhover" align="center"> 
			<td>${chasePlan.capacityProfitContent.periodSizeOfChase!}</td>
			<td>${chasePlan.capacityProfitContent.startMultiple!}</td>
			<td>${chasePlan.capacityProfitContent.hasInvested!}</td>
			<td>${chasePlan.capacityProfitContent.expectedHit!}</td>
			<td><#if chasePlan.capacityProfitContent.allafterlucre?length gte 1>全程收益累计>=${chasePlan.capacityProfitContent.allafterlucre!}</#if></td>
			<td><#if chasePlan.capacityProfitContent.befortermmember?length gte 1>前${chasePlan.capacityProfitContent.befortermmember!}期不低于${chasePlan.capacityProfitContent.beforelc!}</#if></td>
			<td><#if chasePlan.capacityProfitContent.aferlc?length gte 1>之后累计收益不低于${chasePlan.capacityProfitContent.aferlc!}</#if></td>
			<td><#if chasePlan.capacityProfitContent.all_lucrep_select?length gte 1>全程收益率>= ${chasePlan.capacityProfitContent.all_lucrep_select!}%</#if></td>
			<td><#if chasePlan.capacityProfitContent.befortermmemberp?length gte 1>前${chasePlan.capacityProfitContent.befortermmemberp!}期收益率${chasePlan.capacityProfitContent.before_lcp_select!}%</#if></td>
			<td><#if chasePlan.capacityProfitContent.aferlcp_select?length gte 1>之后收益率不低于${chasePlan.capacityProfitContent.aferlcp_select!}%</#if></td>
	  </tr>
    </table>
    </#if>
    <div class="kong10"></div>
    
    
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="28" height="28">已追方案 </td>
        <td width="15%">方案期号</td>
        <td width="14%">方案编号</td>
        <td width="11%">超级发起人</td>
        <td width="9%">总金额</td>
        <td width="8%">最低认购</td>
        <td width="9%">剩余金额</td>
        <td width="7%">进度</td>
        <td width="7%">状态</td>
        <td width="8%">查看</td>
      </tr>
	      <#if pagination.result?? && pagination.result?size gt 0>
	      
	             <#if frequent>
						<#assign remainingCost=chasePlan.zoomTotalCost-chasePlan.zoomChasedCost />
                 <#else>
                    	<#assign remainingCost=chasePlan.totalCost-chasePlan.chasedCost />
                 </#if>
				
				<#list pagination.result as data>
				<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
				 <tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
					<td height="32">${data_index+1}</td>
					<td>${data.periodNumber!}</td>
		            <td><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />" target="_blank">${data.schemeNumber}</a></td>
		            <td>${data.sponsorName}</td>
		            <td>
						<#if frequent>
	                    	￥<#if data.zoomSchemeCost??>#{data.zoomSchemeCost;M2}<#else>--</#if>
	                    <#else>
	                    	￥${data.schemeCost!'--'}
	                    </#if>
                    </td>
		            <td><#if data.minSubscriptionCost??>#{data.minSubscriptionCost;M2}<#else>不限</#if></td>
		            <td>#{(remainingCost!0);M2}</td>
		            <td>
						<#assign baodiProgressRate=data.baodiProgressRate!0 />
		            	#{data.progressRate!0;M0}%<#if baodiProgressRate gt 0>+#{baodiProgressRate;M0}%保</#if>
		            </td>
		            <td width="7%">${data.state.stateName}</td>
		            <td width="8%"><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />"  target="_blank">查看</a></td>
				</tr> 
				<#assign remainingCost=remainingCost+data.schemeCost />
				</#list>
			  <#else>
				<tr>
					<td height="32" align="center" colspan="11" >暂无记录.</td>
				</tr>
		  </#if>
		</table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
     <#if chasePlanDetailList?? && chasePlanDetailList?size gt 0>
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="10%" height="28">剩余方案 </td>
        <td width="15%">方案期号</td>
        <td width="11%">超级发起人</td>
        <td width="9%">注数</td>
        <td width="9%">倍数</td>
        <td width="9%">金额</td>
        <td width="7%">状态</td>
      </tr>
   
      <#list chasePlanDetailList as data>
				<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
				 <tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				 <td height="32">${data_index+1}</td>
				 <td>${data.curPeriodNum!}</td>
      		     <td>${chasePlan.userName!}</td>
            	 <td>${chasePlan.units!}</td>
            	 <td>${data.multiple!}</td>
            	 <td>
            	    <#if frequent>
	                    	￥<#if data.kenoCost??>#{data.kenoCost;M2}<#else>--</#if>
	                    <#else>
	                    	￥${data.cost!'--'}
	                    </#if>
            	 </td>
      		     <td>${data.state.stateName}</td>
      		     </tr>
       </#list>
    </table>
      </#if>
    <div class="kong10"></div>
  </div>
</div>






  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
	        <td>期号</td>
	        <td>方案编号 </td>
	        <td width="60">发起人 </td>
	        <td width="70">投注<br/>时间</td>
	        <td width="50">方案<br/>金额 </td>
	        <td width="50">购买<br/>金额 </td>
	        <td width="40">状态 </td>
	        <td width="50">出票 </td>
	        <td width="60">方案<br/>奖金 </td>
	        <td width="60">我的<br/>奖金</td>
	        <td width="30">操作</td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		  <td height="32">${data_index+1}</td>
	      <td>${data.periodNumber}</td>
	      <td><a href="<@c.url value="/${data.lotteryType.key}/scheme!show.action?schemeNumber=${data.schemeNumber}" />">${data.schemeNumber}</a></td>
	      <td><a href="<@c.url value='/user/scheme!findUserScheme.action?id=${data.sponsorId}' />" target="_blank">${data.sponsorName}</a></td> 
	      <td <#if data.createTime??>title="${data.createTime?string("yyyy-MM-dd HH:mm")}"</#if>><#if data.createTime??>${data.createTime?string("MM-dd HH:mm")}</#if></td>
	      <td>#{data.zoomSchemeCost;M2}</td>
	      <td>#{(data.zoomMyCost!0);M2}</td>
	      <td>${data.schemeState.stateName}</td>
	      <td><#if data.schemeState=="CANCEL"||data.schemeState=="REFUNDMENT">未出票<#else>${data.schemePrintState.stateName!}</#if></td>
	      <td>
	           <#if data.winningUpdateStatus??&&(data.winningUpdateStatus=='WINNING_UPDATED'||data.winningUpdateStatus=='PRICE_UPDATED')>
	                <#if data.prize??>#{data.prize;M2}<#else>0</#if>
	              <#else>
	                <#if data.prize??>#{data.prize;M2}<#else>&nbsp;</#if>
	           </#if>   
	      </td>
	      <td>
	          <#if data.winningUpdateStatus??&&(data.winningUpdateStatus=='WINNING_UPDATED'||data.winningUpdateStatus=='PRICE_UPDATED')>
	                <#if data.myBonus??>#{data.myBonus;M2}<#else>0</#if>
	              <#else>
	                <#if data.myBonus??>#{data.myBonus;M2}<#else>&nbsp;</#if>
	           </#if>   
	      </td>
	      <td><a href="<@c.url value="/${data.lotteryType.key}/scheme!show.action?schemeNumber=${data.schemeNumber}" />">查看</a></td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="12" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
</table>
 <div class="kong5"></div>
	        <!-- 翻页开始 -->
		            <#import "../../macro/pagination.ftl" as b />
			        <@b.page />
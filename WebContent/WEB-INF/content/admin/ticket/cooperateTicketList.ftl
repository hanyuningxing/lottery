<title>出票管理</title>
<meta name="menu" content="ticket"/>
<meta name="menuItem" content="cooperateTicketList"/>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<form name="queryForm" action="${base}/admin/ticket/ticket!cooperateTicketList.action" method="post">
     拆分方案编号:<input name="ticketId" value="<#if ticketId??>${ticketId!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
  订单号:<input name="orderNo" value="<#if orderNo??>${orderNo!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          期号：<input name="periodNumber" value="<#if periodNumber??>${periodNumber!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          方案编号： <input name="schemeNumber" value="<#if schemeNumber??>${schemeNumber!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
     <#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
              	<select name="lotteryType" onchange="document.forms['queryForm'].submit();return false;">
					<option value="">全部彩种</option>
					<#list lotteryTypeArr as type>
					<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
	 </select>
	 <#assign ticketStateArr=stack.findValue("@com.cai310.lottery.common.TicketSchemeState@values()") />
				 <select name="state" onchange="document.forms['queryForm'].submit();return false;">
				       <option value="">全部</option>
						<#list ticketStateArr as ticketState>
							<option <#if state?? && state==ticketState>selected="selected"</#if> value="${ticketState}">${ticketState.stateName}</option>
						</#list>
			      </select>
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form> 
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">序号</td>
      <td>所属方案号</td>
      <td>订单号</td>
      <td>期号</td>
      <td>彩种</td>
      <td>方案金额</td>
      <td>奖金</td>
      <td>出票状态</td>
      <td>电子票号</td>
      <td>投注时间</td>
      <td>出票时间</td>
      <td>结算时间</td>
      <td>最后修改时间</td>
    </tr>
  
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as ticket>
    	<#if ticket_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${ticket.id}</td>
	      <td><a href="${base}/admin/ticket/ticket!gotoScheme.action?schemeNumber=${ticket.schemeNumber}" target="_blank">${ticket.schemeNumber}</a></td>
	      <td><#if ticket??&&ticket.orderId??>${ticket.orderId}</#if></td>
	      <td>${ticket.periodNumber}</td>
	      <td>${ticket.lotteryType.lotteryName}</td>
	      <td>${ticket.schemeCost}</td>
	      <td><#if ticket.totalPrizeAfterTax??>${ticket.totalPrizeAfterTax}</#if></td>
	      <td>
	      	${ticket.state.stateName}
	      </td>
	      <td>${ticket.remoteTicketId!}</td>
	      <td>
	        <#if ticket.createTime??>${ticket.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
	      </td>
	      <td>
	        <#if ticket.ticketTime??>${ticket.ticketTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
	      </td>
	      <td>
	        <#if ticket.prizeTime??>${ticket.prizeTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
	      </td>   
	      <td>
	          <#if ticket.stateModifyTime??>${ticket.stateModifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
	       </td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="13">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
</body>
</html>
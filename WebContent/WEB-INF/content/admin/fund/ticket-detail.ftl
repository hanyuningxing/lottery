<title>出票明细</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="ticketDetail"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<form name="queryForm" action="${base}/admin/fund/fund!ticketDetail.action" method="POST">
        用户名：<input name="userName" value="<#if userName??>${userName}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
        <#assign lotteryTypeArr =stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
                   彩种：
		<select name="lotteryType" onchange="this.form.submit();">
		    <option value="">全部</option>
			<#list lotteryTypeArr as type>
				<#if type=='SFZC' || type=='JCZQ' || type=='JCLQ' || type=='DCZC'>
				<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}"><#if type=='SFZC'>传统足彩<#else>${type.lotteryName}</#if></option>
				</#if>
			</#list>
		</select>
		<#assign ticketSupporterArr =stack.findValue("@com.cai310.lottery.ticket.common.TicketSupporter@values()") />
		出票方:
		<select name="ticketSupporter" onchange="this.form.submit();">
		    <option value="">全部</option>
			<#list ticketSupporterArr as supporter>
				<option <#if ticketSupporter?? && ticketSupporter==supporter>selected="selected"</#if> value="${supporter}">${supporter.supporterName}</option>
			</#list>
		</select>
      	从<input id="dateStar" type="text" name="dateStar" onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateStar??>${dateStar?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				           到<input id="dateEnd" type="text" name="dateEnd" onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateEnd??>${dateEnd?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				  &nbsp;&nbsp;&nbsp;  格式：yyyy-MM-dd HH:mm
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>


<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
      <td>彩种</td>
      <td>出票方</td>
      <td>出票金额</td>
      <td>中奖金额</td>
    </tr>
    <#assign schemeCost=0 />
    <#assign totalPrizeAfterTax=0/>
    <#if ticketResultList?? && ticketResultList?size gt 0>
    	<#list ticketResultList as ticketInfo>
    	<#if ticketInfo_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${ticketInfo.lotteryType.lotteryName!}</td>
	      <td>${ticketInfo.ticketSupporter.supporterName!}</td>
	      <td>${ticketInfo.schemeCost!}</td>
	      <td><#if ticketInfo.totalPrizeAfterTax??><#assign totalPrizeAfterTax=totalPrizeAfterTax+ticketInfo.totalPrizeAfterTax />#{ticketInfo.totalPrizeAfterTax;M2}</#if></td>
		</tr>
	<#assign schemeCost=schemeCost+ticketInfo.schemeCost />
	</#list>
	<tr class="trlbro" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
		<td>合计</td>
		<td>-</td>
		<td>${schemeCost!}</td>
		<td>${(totalPrizeAfterTax!0)?string('0.##')}</td>
	</tr>
    	
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="10">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
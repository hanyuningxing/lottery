<title>统计</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="userSubscription"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<form name="queryForm" action="${base}/admin/fund/fund!userSubscription.action" method="POST">
        用户名：<input name="subscriptionForm.userName" value="<#if subscriptionForm??&&subscriptionForm.userName??>${subscriptionForm.userName}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
        <#assign lotteryTypeArr =stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
                   彩种：
		<select name="subscriptionForm.lotteryType" onchange="this.form.submit();">
		    <option value="">全部</option>
			<#list lotteryTypeArr as type>
				<option <#if subscriptionForm??&&subscriptionForm.lotteryType?? && subscriptionForm.lotteryType==type>selected="selected"</#if> value="${type}"><#if type=='SFZC'>传统足彩<#else>${type.lotteryName}</#if></option>
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
<tr class="trlbrown" height="30">
	  <td width="260">日期</td>
	  <td>用户名</td>
	  <td>彩种</td>
	  <td>交易总金额</td>
      <td>交易成功总金额</td>
      <td>中奖金额</td>
    </tr>
    <#assign bonusCount =0 />
    <#assign costCount =0 />
    <#assign costSuccessCount =0 />
    <#if saleAnalyseResultList??&& (saleAnalyseResultList![])?size gt 0>
    	<#list saleAnalyseResultList as saleAnalyse>
    	<#if saleAnalyse_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td width="260"><#if dateStar??&&dateEnd??>${dateStar?string('yyyy-MM-dd HH:mm')}~${dateEnd?string('yyyy-MM-dd HH:mm')}</#if></td>
    	  <td>${saleAnalyse.userName!}</td>
	      <td><#if saleAnalyse??&&saleAnalyse.lottery??>${saleAnalyse.lottery.lotteryName!}</#if></td>
	      <td><#if saleAnalyse.subscriptionCost??>#{(saleAnalyse.subscriptionCost!);M2}<#assign costCount=costCount+saleAnalyse.subscriptionCost /></#if></td>
	      <td><#if saleAnalyse.subscriptionSuccessCost??>#{(saleAnalyse.subscriptionSuccessCost!);M2}<#assign costSuccessCount=costSuccessCount+saleAnalyse.subscriptionSuccessCost /></#if></td>
	      <td><#if saleAnalyse.subscriptionSuccessWonPrize??>#{(saleAnalyse.subscriptionSuccessWonPrize!);M2}<#assign bonusCount=bonusCount+saleAnalyse.subscriptionSuccessWonPrize /></#if></td>
		</tr>
    	</#list>
    	<tr class="trlbro" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>合计</td>
	      <td>-</td>
	      <td>-</td>
	      <td>${costCount!}</td>
	      <td>#{(costSuccessCount);M2}</td>
	      <td>#{(bonusCount);M2}</td>
		</tr>
    </#if>
  </table>
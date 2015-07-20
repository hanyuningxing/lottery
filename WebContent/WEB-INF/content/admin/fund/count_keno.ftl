<title>统计</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="countKeno"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<form name="queryForm" action="${base}/admin/fund/fund!countKeno.action" method="POST">
          用户名：<input name="kenoForm.userName" value="<#if kenoForm??&&kenoForm.userName??>${kenoForm.userName}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
        <#assign lotteryTypeArr =stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
		  彩种：
		<select name="kenoForm.lottery" onchange="this.form.submit();">
		    <option value="">全部</option>
			<#list lotteryTypeArr as type>
			<#if type=='KLSF'||type=='EL11TO5'||type=='SSC'||type=='SSL'>
				<option <#if kenoForm??&&kenoForm.lottery?? && kenoForm.lottery==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
			</#if>
			</#list>
		</select>
		状态：
		<#assign schemeStateArr =stack.findValue("@com.cai310.lottery.common.keno.SchemeState@values()") />
		<select name="kenoForm.state" onchange="this.form.submit();">
		    <option value="">全部</option>
			<#list schemeStateArr as schemeState>
			<option <#if kenoForm??&&kenoForm.state?? && kenoForm.state==schemeState>selected="selected"</#if> value="${schemeState}">${schemeState.stateName}</option>
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
	  <td>日期</td>
	  <td>用户名</td>
	  <td>彩种</td>
      <td>状态</td>
      <td>交易金额</td>
      <td>中奖金额</td>
    </tr>
    <#assign returnPrizeCount =0 />
    <#assign betCostCount =0 />
    <#if kenoResultList??&& (kenoResultList![])?size gt 0>
    	<#list kenoResultList as keno>
    	<#if keno_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td><#if dateStar??&&dateEnd??>${dateStar?string('yyyy-MM-dd HH:mm')}~${dateEnd?string('yyyy-MM-dd HH:mm')}</#if></td>
    	  <td>${keno.userName!}</td>
	      <td><#if keno??&&keno.lottery??>${keno.lottery.lotteryName!}</#if></td>
	      <td><#if keno??&&keno.state??>${keno.state.stateName!}</#if></td>
	      <td>${keno.betCost!}</td>
	      <td>${keno.returnPrize!}</td>
	      <#if keno.betCost??><#assign betCostCount=betCostCount+keno.betCost /></#if>
	      <#if keno.returnPrize??><#assign returnPrizeCount=returnPrizeCount+keno.returnPrize /></#if>
		</tr>
    	</#list>
    	<tr class="trlbro" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>合计</td>
	      <td>-</td>
	      <td>-</td>
	      <td>-</td>
	      <td>${betCostCount!}</td>
	      <td>${returnPrizeCount!}</td>
		</tr>
    </#if>
  </table>
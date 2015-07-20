<head>
	<title>提款记录</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="drawing" />
	<!--<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'drawing'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
	<div class="yhzxright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/user/fund!drawingList.action' />" method="get" id="drawingForm">
	    <div class="crighttop">
		      <label><strong>查询</strong>:</label>
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			      <select name="timeFrame">
			          <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
			      </select>
		      </label>
		      &nbsp;&nbsp;
		      <label>交易类型：</label>
		      <label>
			    <#assign drawingOrderStateArr=stack.findValue("@com.cai310.lottery.common.DrawingOrderState@values()") />
              	<select name="drawingState">
					<option value="">全部</option>
					<#list drawingOrderStateArr as state>
					<option <#if drawingState?? && drawingState==state>selected="selected"</#if> value="${state}">${state.stateName}</option>
					</#list>
				</select>
		      </label>
		      <a href="javascript:void(0);" onclick="document.getElementById('drawingForm').submit();return false;"> <img src="<@c.url value='/pages/images/center_btcx.gif' />" align="absmiddle"/></a>
	   </div>
	 </form>
    <div class="kong10"></div>
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="5%" height="28">序 </td>
        <td width="8%">用户名</td>
        <td width="5%">金额</td>
        <td width="20%">银行账号</td>
        <td width="15%">状态</td>
        <td width="8%">资金序号</td>
        <td width="20%">时间</td>
        <td width="20%">处理结果</td>
      </tr>
      <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		     <td height="32">${data_index+1}</td>
	         <td>${data.userName}</td>
			 <td>#{(data.money!0);M2}</td>
			 <td>${data.bankAccounts!}</td>
			 <td><#if data??&&data.state??>${data.state.stateName!}</#if></td>
			 <td>${data.funddetailId!}</td>
			 <td>${data.sendtime!}</td>
			 <td>${data.adminMsg!}</td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="7" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	 </table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
    <div class="kong10"></div>
  </div>
</div>


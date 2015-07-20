<title>用户管理</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="user_drawingList"/>
<form name="queryForm" action="${base}/admin/user/user!drawingList.action" method="get">
      用户名：<input name="userName" value="<#if userName??>${userName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      <#assign drawingOrderStateArr=stack.findValue("@com.cai310.lottery.common.DrawingOrderState@values()") />
              	<select name="drawingState" onchange="document.forms['queryForm'].submit();return false;">
					<option value="">全部</option>
					<#list drawingOrderStateArr as state>
					<option <#if drawingState?? && drawingState==state>selected="selected"</#if> value="${state}">${state.stateName}</option>
					</#list>
	  </select>
	  <select name="timeFrame" onchange="document.forms['queryForm'].submit();return false;">
                  <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
                  <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
                  <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
                  <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
      </select>
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>用户名</td>
      <td>真实姓名</td>
      <td>提款金额</td>
      <td>手续费</td>
      <td>应付金额</td>
      <td>申请时间</td>
      <td>开户银行</td>
      <td>提款帐号</td>
      <td>银行省份</td>
      <td>银行城市</td>
      <td>支行名称</td>
      <td>状态</td>
      <td>资金订单号</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as drawingOrder>
    	<#if drawingOrder_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${drawingOrder.id!}</td>
	      <td>${drawingOrder.userName!}</td>
	      <td>${drawingOrder.realName!}</td>
	      <td>#{drawingOrder.money;M2}</td>
	      <td>#{drawingOrder.commission;M2}</td>
	      <td><font color="red">#{drawingOrder.drawingMoney;M2}</font></td>
	      <td>${drawingOrder.sendtime!}</td>
	      <td>${drawingOrder.bankName!}</td>
	      <td>${drawingOrder.bankAccounts!}</td>
	      <td>${drawingOrder.partBankProvince!}</td>
	      <td>${drawingOrder.partBankCity!}</td>
	      <td>${drawingOrder.partBankName!}</td>
	      <td><#if drawingOrder??&&drawingOrder.state??>${drawingOrder.state.stateName!}</#if></td>
	      <td>${drawingOrder.funddetailId!}</td>
	     
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="10">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
<title>用户管理</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="passDrawing"/>
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
      <td>支付平台</td>
      <td>操作</td>
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
	      <td><a href="https://auth.alipay.com/login/index.htm" target="_blank">支付宝</a>&nbsp;&nbsp;<a href="https://www.unspay.com/main.do"  target="_blank">银生宝</a></td>
	      <td>
		      <a href="${base}/admin/fund/fund!passkDrawing.action?id=${drawingOrder.id}" onclick="if(window.confirm('你是否确认确认订单')){return true;}return false;">确认订单</a> |
		      <a href="${base}/admin/fund/fund!deleteDrawing.action?id=${drawingOrder.id}" onclick="if(window.confirm('你是否确认退还订单')){return true;}return false;">退还订单</a>
	      </td>
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
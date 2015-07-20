<title>用户管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="checkDrawing"/>

 <script type="text/javascript">
	function backDrawing(id){   
		document.getElementById(id).submit();
	}
 </script>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>用户名</td>
      <td>用户资金明细</td>
      <td>姓名</td>
      <td>金额</td>
      <td>账号</td>
      <td conspan="3">银行资料</td>
      <td>状态</td>
      <td>时间</td>
         <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as drawingOrder>
    	<#if drawingOrder_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${drawingOrder.id!}_${drawingOrder.funddetailId!}</td>
	      <td>${drawingOrder.userName!}</td>
	      <td><a href="${base}/admin/fund/fund!fundetailList.action?userName=${drawingOrder.userName!}" target="_blank">查看用户资金明细</a></td>
	      <td>${drawingOrder.realName!}</td>
	      <td>#{drawingOrder.money;M2}</td>
	      <td>${drawingOrder.bankAccounts!}</td>
	      <td conspan="3" >${drawingOrder.partBankProvince!}_${drawingOrder.partBankCity!}_${drawingOrder.partBankName!}_${drawingOrder.bankName!}</td>
	      <td><#if drawingOrder??&&drawingOrder.state??>${drawingOrder.state.stateName!}</#if></td>
	      <td>${drawingOrder.sendtime!}</td>
	      <td>
		      <a href="${base}/admin/user/user!checkDrawing.action?id=${drawingOrder.id}" onclick="if(window.confirm('你是否确认确认订单')){return true;}return false;">确认</a> 
		      <form name="drawingOrderForm" id ="${drawingOrder.id}" action="${base}/admin/user/user!backDrawing.action?id=${drawingOrder.id}" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
		      	<a href="#" onclick="if(window.confirm('你是否确认退还订单')){ backDrawing(${drawingOrder.id});}return false;">退还</a>
	     	  </form>
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
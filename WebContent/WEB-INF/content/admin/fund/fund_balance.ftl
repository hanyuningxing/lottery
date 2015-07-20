<title>用户管理</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="fundBalance"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<form name="queryForm" action="${base}/admin/fund/fund!fundBalance.action" method="post">
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
      <td>充值总额</td>
      <td>投注总额</td>
      <td>中奖总额</td>
      <td>提款总额</td>
      <td>客服后台添加总金额</td>
      <td>客服后台扣除总金额</td>
    </tr>
    <tr class="trlbro">
	      <td><#if dateStar??&&dateEnd??>${dateStar?string('yyyy-MM-dd HH:mm')}~${dateEnd?string('yyyy-MM-dd HH:mm')}</#if></td>
	      <td><#if payTotal??>${payTotal!}</#if></td>
	      <td><#if betTotal??>${betTotal!}</#if></td>
	      <td><#if bounsTotal??>${bounsTotal!}</#if></td>
	      <td><#if drawingTotal??>${drawingTotal!}</#if></td>
	      <td><#if adminInTotal??>${adminInTotal!}</#if></td>
	      <td><#if adminOutTotal??>${adminOutTotal!}</#if></td>    
	</tr>
</table>

充值总额=("在线充值")+("后台通过充值")<br/>
投注总金额=("认购")+("保底")+("追号")-("撤销认购")-("撤销保底")-("方案撤单")-("方案退款")-("停止追号")<br/>
中奖总金额=("方案佣金")+("奖金分红")<br/>
 提款总金额=("用户提款")-("提款失败返还资金")<br/>
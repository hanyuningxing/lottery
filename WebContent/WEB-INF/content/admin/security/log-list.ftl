<#assign menu="lottryUser" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem="log" />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>日志管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script src="${base}/js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function() { 
	    $("#checkall").click(function() {
			 if ($(this).attr("checked") == true) { // 全选
			    $("input[name=operators]").each(function() {
			   		 $(this).attr("checked", true);
			   	});
			 } else { // 取消全选
			    $("input[name=operators]").each(function() {
			  		  $(this).attr("checked", false);
			    });
			 }
	    });
 	});
	</script>
</head>

<@com.displaySimpleNav menuObj menuItemObj true>日志管理</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">日志管理</span></div>
</div>

<form name="queryForm" action="<@c.url value='/admin/security/log.action' />" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
		<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
		<select name="lotteryType" onchange="this.form.submit();">
			<option value="">全部</option>
			<#list lotteryTypeArr as type>
				<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName!}</option>
			</#list>
		</select>
		<#assign eventLogTypeArr=stack.findValue("@com.cai310.lottery.common.EventLogType@values()") />
		<select name="logType" onchange="this.form.submit();">
			<option value="">全部</option>
			<#list eventLogTypeArr as type>
				<option <#if logType?? && logType==type>selected="selected"</#if> value="${type}">${type.text!}</option>
			</#list>
		</select>
		      用户名：<input name="operator" value="<#if operator??>${operator!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
		   IP：<input name="ip" value="<#if ip??>${ip!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
		  <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
		
</div>
</form>

<span class="ssq_tr_2">
 			<form name="userForm" action="${base}/admin/security/log!oprUser.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
			     <input name="id"  type="hidden" value="<#if user??&&user.id??>${user.id!}</#if>"/>
			     <select name="islocked" size="1">
				        <option value="1">停用</option>
				        <option value="0">可用</option>
				 </select>&nbsp;&nbsp;&nbsp;
				 <input type="image" src="${base}/images/comfirm.gif" />
			
</span>
<div>
  <table align="center" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td width="30" height="22"><input id="checkall" type="checkbox"/></td>
	  <td></td>
	  <td>彩种</td>
	  <td>期号</td>
      <td>类型</td>
      <td>时间</td>
      <td>操作员</td>
      <td>IP</td>
      <td>是否完成</td>
      <td>详情</td>
    </tr>
    <#if pagination?? && (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td><input name="operators" value="${data.operator!}" type="checkbox"/></td>
	      <td align="center" height="22">${data.id!}</td>
	      <td align="center" height="22"><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
	      <td align="center" height="22">${data.periodNumber!}</td>
	      <td align="center" height="22"><#if data.eventLogType??>${data.eventLogType.text!}</#if></td>
	      <td align="center" height="22">${data.startTime!}</td>
	      <td align="center" height="22">${data.operator!}</td>
	      <td align="center" height="22">${data.ip!}</td>
	      <td align="center" height="22"><#if data.normalFinish??&&data.normalFinish>是<#else>否</#if></td>
	      <td align="center" height="22"><a href="<@c.url value='/admin/security/log!show.action?id=${data.id!}' />">查看</a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="13">无记录</td>
	    </tr>
    </#if>
  </table>
</div>
</form>
<#import "../../../macro/pagination_admin.ftl" as b />
<@b.page />
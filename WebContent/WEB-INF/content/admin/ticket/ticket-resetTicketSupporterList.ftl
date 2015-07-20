<title>重置出票商日志管理</title>
<meta name="menu" content="ticket"/>
<meta name="menuItem" content="resetTicketSupporterList"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>

<form name="queryForm" action="${base}/admin/ticket/ticket!resetTicketSupporterLogList.action" method="post">
          操作员：<input name="operName" value="<#if operName??>${operName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          方案编号： <input name="schemeNumber" value="<#if schemeNumber??>${schemeNumber!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          出票编号：<input name="ticketId" value="<#if ticketId??>${ticketId!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
         从<input id="dateStar" type="text" name="dateStar" onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateStar??>${dateStar?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				           到<input id="dateEnd" type="text" name="dateEnd" onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateEnd??>${dateEnd?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				  &nbsp;&nbsp;&nbsp;(默认查3天数据)&nbsp;&nbsp;
    <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>

<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
      <td width="80">序列号</td>
	  <td width="100" height="22">拆分票号</td>
      <td width="150">所属方案号</td>
      <td width="200">重置前发票时间</td>
      <td width="100">重置前票状态码</td>
      <td width="180">重置前票状态信息</td>
      <td width="180">重置前票状态修改时间</td>
      <td width="100">重置前出票商</td>
      <td width="130">重置后出票商</td>
      <td width="180">重置时间</td>
      <td width="100">操作员</td>
      <td></td>
    </tr>
  
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as log>
    	<#if log_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">    	  
	      <td>${log.id}</td>
	      <td>${log.ticketId}</td>
	      <td>${log.schemeNumber}</td>
	      <td><#if log.sendTime??>${log.sendTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
	      <td>${log.stateCode!''}</td>
	      <td>${log.stateCodeMessage!''}</td>
	      <td><#if log.stateModifyTime??>${log.stateModifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>	      
	      <td style="text-align:right;"><#if log.ticketSupporter??>${log.ticketSupporter.supporterName!}</#if></td>
	      <td style="text-align:left;"><#if log.resetTikcetSupporter??>>> ${log.resetTikcetSupporter.supporterName!}<#else><font color="red">${log.remark!}</font></#if></td>	      
	      <td><#if log.createTime??>${log.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
	      <td>${log.operName!}</td>
	      <td></td>  
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="14">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
</body>
</html>
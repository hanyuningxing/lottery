<head>
	<title>彩票日志</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="logger" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'logger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/ticket/user!logger_list.action' />" method="get" id="schemeForm">
	    <div class="crighttop">
	    	<span style="float:left">
		      <label>彩种：</label>
		      <label>
			    <#assign lotteryTypeArr = webLotteryType />
				<select name="lotteryType" onchange="this.form.submit();">
					    <option value="">全部</option>
					<#list lotteryTypeArr as type>
						<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
				</select>
		      </label>
		     </span>
	   </div>
	 </form>
    <div class="kong10"></div>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable" style="table-layout:fixed;word-break: break-all; word-wrap: break-word;">
			<thead>
				<tr align="center">
				    <td width="20" height="20">序 </td>
				    <td width="50">彩种</td>
				    <td >请求时间</td>
			        <td width="250">请求内容</td>
			        <td>返回时间 </td>
			        <td width="250">返回内容 </td>
				</tr>
			</thead>
			<tbody>
		     <#if pagination.result?? && pagination.result?size gt 0>
				<#list pagination.result as data>
				<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
				<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				  <td height="32">${data_index+1}</td>
			      <td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
			      <td <#if data.createTime??>title="${data.createTime?string("yyyy-MM-dd HH:mm")}"</#if>><#if data.createTime??>${data.createTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			      <td width="250"><#if data.reqContent??>${data.reqContent!}</#if></td>
			      <td <#if data.respTime??>title="${data.respTime?string("yyyy-MM-dd HH:mm")}"</#if>><#if data.respTime??>${data.respTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			      <td width="250"><#if data.respContent??>${data.respContent!}</#if></td>
				</tr>
				</#list>
			  <#else>
				<tr>
				  <td colspan="6" align="center" >暂无记录.</td>
				</tr>
			  </#if>
			</tbody>
		</table>
		 <div class="kong5"></div>
			        <!-- 翻页开始 -->
				            <#import "../../macro/pagination.ftl" as b />
					        <@b.page />
	
    <div class="kong10"></div>
  </div>
</div>
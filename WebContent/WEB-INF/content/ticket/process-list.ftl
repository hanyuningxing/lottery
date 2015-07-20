<head>
	<title>彩票处理</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="process" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'process'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/ticket/user!process_list.action' />" method="get" id="schemeForm">
	    <div class="crighttop">
	    	<span style="float:left">
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			      <select name="timeFrame" onchange="this.form.submit();">
			         <option value="0" <#if timeFrame==0>selected="selected"</#if>>一天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>七天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>十五天</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>一个月</option>
			      </select>
		      </label>
		      &nbsp;&nbsp;
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
				    <td width="100">处理时间</td>
				    <td>订单号</td>
			        <td>处理结果</td>
			        <td>错误信息 </td>
			        <td width="250">错误详情 </td>
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
			      <td><#if data.orderId??>${data.orderId!}</#if></td>
			      <td><#if data.process??>${data.process!}</#if></td>
			      <td><#if data.errorMsg??>${data.errorMsg!}</#if></td>
			      <td width="250"><#if data.exceptionMsg??>${data.exceptionMsg!}</#if></td>
				</tr>
				</#list>
			  <#else>
				<tr>
				  <td colspan="7" align="center" >暂无记录.</td>
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
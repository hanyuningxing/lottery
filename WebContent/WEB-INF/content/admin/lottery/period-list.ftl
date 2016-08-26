<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>彩种管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
</head>

<@com.displaySimpleNav menuObj menuItemObj></@>

<#if menuItemObj.labelName??>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${menuItemObj.labelName}</span></div>
</div>
</#if>

<#if lotteryType??>
<div class="navgraybg" >
  <div class="choseban" style="float:left;"></div>
  <div style="float:right;padding:5px 5px 0px 0px;">
  	<a href="<@c.url value='/admin/lottery/${lotteryType.key}/period!editNew.action' />">添加当前期次</a>
  	<a href="<@c.url value='/admin/lottery/${lotteryType.key}/period!editNew.action?advance=true' />">添加预售期次</a>
  </div>
</div>
</#if>

<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22">编号</td>
		<td>期号</td>
		<td>系统开售时间</td>
		<td>官方截止时间</td>
		<td>销售状态</td>
		<td>操作</td>
	</tr>
	<#if pagination??&& (pagination.result![])?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			<td>${data.id}</td>
			<td>${data.periodNumber}</td>
			<td><#if data.startTime??>${data.startTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			<td><#if data.endedTime??>${data.endedTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			<td>${data.state.stateName}</td>
			<td>
				<a href="<@c.url value='/admin/lottery/${data.lotteryType.key}/period!edit.action?id=${data.id}' />">编辑</a>
				<a href="<@c.url value='/admin/lottery/${data.lotteryType.key}/sales-manage.action?id=${data.id}' />">销售管理</a>
		  		<a href="<@c.url value='/admin/lottery/${data.lotteryType.key}/scheme!list.action?queryForm.periodNumber=${data.periodNumber}&queryForm.order=ID&queryForm.descOrder=true&queryForm.ticketState=UNPRINT' />" >方案管理</a>
		  		<#if lotteryType.category=='JC'>
		  			<a href="<@c.url value='/admin/lottery/${lotteryType.key}/period!matchManage.action?id=${data.id}' />">对阵管理</a>
		  		</#if>
		  		<a href="<@c.url value='/admin/counter/${lotteryType.key}.action?periodNumber=${data.periodNumber}' />">统计报表</a>
			</td>
		</tr>
		</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="6">无记录</td>
	    </tr>
	</#if>
</table>
</div>

<#import "../../../macro/pagination_admin.ftl" as b />
<@b.page />
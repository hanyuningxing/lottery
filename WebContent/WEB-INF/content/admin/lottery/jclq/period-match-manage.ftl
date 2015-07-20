<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>${lotteryType.lotteryName}-对阵管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
</head>

<@com.displaySimpleNav menuObj menuItemObj true>对阵管理</@>

<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22" width="30%">时间</td>
		<td width="30%">周几</td>
		<td>操作</td>
	</tr>
	<#if pagination??&& (pagination.result![])?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			<td>${data.toString('yyyy-MM-dd')}</td>
			<td>${data.toString('E')?replace('星期','周')}</td>
			<td>
				<a href="<@c.url value='/admin/lottery/${lotteryType.key}/period!matchEdit.action?id=${period.id}&matchDate=${data.toString("yyyyMMdd")}' />">编辑</a>
			</td>
		</tr>
		</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="3">无记录</td>
	    </tr>
	</#if>
</table>
</div>

<#import pagination_path as b />
<@b.page />
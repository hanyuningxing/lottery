<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>${lotteryType.lotteryName}-编辑期数据</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
</head>

<@com.displaySimpleNav menuObj menuItemObj true>编辑期数据</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${period.periodNumber}期</span></div>
</div>
<div>
	<form id="periodForm" name="periodForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/period!update.action' />" method="post">
		<input type="hidden" name="id" value="${period.id!}" />
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
		  <#include "../baseEditData.ftl" />

		  <#if period.drawed>
			<#include "period-data-input.ftl" />
		  </#if>
		  <tr class="trlblue">
		    <td align="left" colspan="2"><input type="submit" value="更新数据" /></td>
		  </tr>
		</table>
	</form>
</div>

<div style="padding-top:5px;">
	
</div>
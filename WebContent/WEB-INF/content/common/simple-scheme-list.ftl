<#assign menu_type='scheme_list' />

<@override name="title">
	<title>${lotteryType.lotteryName}参与合买</title>
</@override>

<@override name="content">
	<div class="hemaint k3px">
		<@block name="query_top"></@block>
		<@block name="query">
			<form action="<@c.url value="/${lotteryType.key}/scheme!list.action" />" method="get" id="scheme_list_form">
		      	<#include "../common/scheme-list-query-menu.ftl"/>
		      	<input type="hidden" name="queryForm.orderType" value="<#if queryForm??&&queryForm.orderType??>${queryForm.orderType!}<#else>PROCESS_RATE_DESC</#if>" />
		        <#if salesMode??><input type="hidden" name="salesMode" value="${salesMode}" /></#if>
		        <#if playType??><input type="hidden" name="playType" value="${playType}" /></#if>
			</form>
		</@block>
		<@block name="list_table">
			<#include 'scheme-list-table.ftl' />
		</@block>
	</div>
</@override>


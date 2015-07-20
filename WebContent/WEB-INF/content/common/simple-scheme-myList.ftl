<#assign menu_type='scheme_myList' />

<@override name="title">
	<title>${lotteryType.lotteryName}我的投注</title>
</@override>

<@override name="content">
	<div class="hemaint k3px">
		<@block name="query">
			<form action="<@c.url value="/${lotteryType.key}/scheme!myList.action" />" method="get">
			    <#if playType??><input type="hidden" name="playType" value="${playType}" /></#if>
				<div class="bggray32" >
				    <div class="floatleft" style="padding:5px 0 0 8px;">
					  	<select name="queryForm.schemeState" onchange="this.form.submit();">
							<option value="">全部</option>
							<#assign schemeStateArr=stack.findValue("@com.cai310.lottery.common.SchemeState@values()") />
							<#list schemeStateArr as state>
					        	<option <#if ((queryForm.schemeState)!'')==state>selected="selected"</#if> value="${state}">${state.stateName}</option>
							</#list>
				      	</select>
				      	&nbsp;
				      	<label><input type="checkbox" name="queryForm.won" value="true" onclick="this.form.submit();" <#if (queryForm.won)!false>checked="checked"</#if> />中奖</label>
				    </div>
				    <div class=" floatrig rig10" style="padding-top:4px;"></div>
				</div>
			</form>
		</@block>
		<@block name="list_table">
			<#include 'scheme-my-list-table.ftl' />
		</@block>
	</div>
</@override>


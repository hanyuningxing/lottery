<#assign menu_type='scheme_list' />

<@override name="title">
	<title>北京单场足彩，北京单场推荐、购买投注 - ${webapp.webName}安全购彩平台 </title>
</@override> 
<@override name="head">
<meta name="Keywords" content="北单单场,单场足彩,网上买单场,单场投注,五大联赛单场,世界杯单场，欧洲杯单场，网上购买" /> 
<meta name="Description"content="${webapp.webName}北京单场足彩在线投注服务，为广大彩民在线提供北京单场网上投注，手机北京单场网上服务，强大的过滤软件和预测分析，让你投注轻松，享受购彩，天天中奖。"/>
</@override>
<@override name="content">
	<div class=" cleanboth dctwobg">
	  <div class="twowz">
	    <ul class="twotopnav">
	    	<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()")) />
	    	<#list playTypeArr as pt>
	        <li><a <#if playType?? && pt==playType>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!list.action?playType=${pt}" />">${pt.text}</a></li>
	    	</#list>
	    </ul>
	  </div>
	</div>
	<form action="<@c.url value="/${lotteryType.key}/scheme!list.action" />" method="get" id="scheme_list_form">
      	<#include "../common/scheme-list-query-menu.ftl"/>
      	<input type="hidden" name="queryForm.orderType" value="<#if queryForm??&&queryForm.orderType??>${queryForm.orderType!}<#else>PROCESS_RATE_DESC</#if>" />
        <#if salesMode??><input type="hidden" name="salesMode" value="${salesMode}" /></#if>
	</form>
	<div class="hemaint k3px">
		<#include '../common/scheme-list-table.ftl' />
	</div>
</@override>

<@extends name="base.ftl"/> 
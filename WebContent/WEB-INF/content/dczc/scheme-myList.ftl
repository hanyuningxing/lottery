<@override name="title">
	<title>北京单场足彩，北京单场推荐、购买投注 - ${webapp.webName}安全购彩平台</title>
</@override> 

<@override name="Description">
	<meta name="Description"content="${webapp.webName}北京单场足彩在线投注服务，为广大彩民在线提供北京单场网上投注，手机北京单场网上服务，强大的过滤软件和预测分析，让你投注轻松，享受购彩，天天中奖。"/>
</@override>
<@override name="contentHeader">
 	<div class=" cleanboth dctwobg">
	  <div class="twowz">
	    <ul class="twotopnav">
	    	<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()")) />
	    	<#list playTypeArr as pt>
	        <li><a <#if playType?? && pt==playType>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!myList.action?playType=${pt}" />">${pt.text}</a></li>
	    	</#list>
	    </ul>
	  </div>
	</div>
</@override>

<@extends name="/WEB-INF/content/common/simple-scheme-myList.ftl"/> 
<@extends name="base.ftl"/> 
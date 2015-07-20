<#assign menu_type='scheme_list' />

<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="head">
	<script type="text/javascript" src="<@c.url value="/js/timer.js" />"></script>
  	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
  	<script type="text/javascript" src="<@c.url value="/js/lottery/scheme-show.js" />"></script>
  	<@block name="showHead"></@block>
</@override>

<@override name="content">
	<div class="hemaint k3px">
		<div style="line-height: 32px;" class="cleanboth bggray32">
			此方案发起时间：<#if (scheme.createTime)??>${scheme.createTime?string("MM-dd HH:mm:ss")}</#if> 　认购截止时间：<#if (period.endedTime)??>${period.endedTime?string("MM-dd HH:mm:ss")}</#if>  　方案编号：<span class="bluelightchar">${scheme.schemeNumber}</span>
		</div>
		<@extends name="simple-scheme-detail.ftl"/> 
		<div align="center" class="all20px"><a href="<@c.url value="/${scheme.lotteryType.key}/scheme!list.action" />"><img src="<@c.url value="/pages/images/back.gif" />"></a></div>
	</div>
</@override>
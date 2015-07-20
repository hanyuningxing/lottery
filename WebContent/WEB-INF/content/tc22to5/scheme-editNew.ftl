<@override name="title">
	<title>体彩22选5网上购买投注、选号合买、投注计算、开奖结果查询 - ${webapp.webName}安全购彩平台</title>
</@override>
<#assign isSingle=isSingle!(salesMode=='SINGLE') />
<@override name="title">
	<title>${lotteryType.lotteryName}<#if !isSingle && playType=1>胆拖<#else>${salesMode.modeName}</#if>投注</title>
</@override>
<@override name="content.top">
	<div class="bggray32 cleanboth "> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&shareType=${shareType}" />" <#if isSingle>class="graydownmenunow"<#else>class="graydownmenu"</#if>>单式</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&playType=0&shareType=${shareType}" />" <#if !isSingle && playType==0>class="graydownmenunow"<#else>class="graydownmenu"</#if>>复式</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&playType=1&shareType=${shareType}" />" <#if !isSingle && playType==1>class="graydownmenunow"<#else>class="graydownmenu"</#if>>胆拖</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editSpinmatrix.action" />" class="graydownmenu">旋转矩阵</a>
	</div>
</@override>
<@override name="content.play_caption">
	<#if (playType!0)==1>
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：从01-22中选择1-4胆码、5-21个拖码进行投注（胆码和拖码不能重复）。一等奖金不固定，单注奖金最高可达500万元。</div>
	<#else>		
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：从01-22中选择5个号码（或以上）投注。一等奖金不固定，单注奖金最高可达500万元。示例 投注方案：01,02,03,04,05</div>
	</#if>
</@override>
<@extends name="/WEB-INF/content/common/number-scheme-editNew.ftl"/> 
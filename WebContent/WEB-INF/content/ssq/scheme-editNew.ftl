<@override name="title">
	<title>双色球网上购买，双色球网上购买投注 –${webapp.webName}安全购彩平台</title>
</@override>
<#assign isSingle=isSingle!(salesMode=='SINGLE') />
<@override name="title">
	<title>${lotteryType.lotteryName}<#if !isSingle && playType=1>胆拖<#else>${salesMode.modeName}</#if>投注</title>
</@override>
<@override name="content.top">
	<div class="bggray32 cleanboth "> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&shareType=${shareType}" />" <#if isSingle>class="graydownmenunow"<#else>class="graydownmenu"</#if>>单式上传</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&playType=0&shareType=${shareType}" />" <#if !isSingle && playType==0>class="graydownmenunow"<#else>class="graydownmenu"</#if>>复式</a> 
		<!--
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&playType=1&shareType=${shareType}" />" <#if !isSingle && playType==1>class="graydownmenunow"<#else>class="graydownmenu"</#if>>胆拖</a>
		-->
		<a href="<@c.url value="/${lotteryType.key}/scheme!editSpinmatrix.action" />" class="graydownmenu">旋转矩阵</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editFilter.action" />" class="graydownmenu">在线过滤</a>
  	</div>
</@override>
<@override name="content.play_caption">
	<#if (playType!0)!=2>
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：从01-33中选择6个号码（或以上）对红球投注，从01-16中选择1个号码（或以上）对蓝球投注。所选的6个红球号码和1个蓝球号码与开奖号码全部相同即中一等奖，一等奖金不固定，单注奖金最高可达1000万元。特点：大奖高，小奖多。示例 投注方案：01,02,03,04,05,06,01</div>
	</#if>
</@override>
<@extends name="/WEB-INF/content/common/number-scheme-editNew.ftl"/> 
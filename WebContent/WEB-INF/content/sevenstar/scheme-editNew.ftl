<@override name="title">
	<title>体彩七星彩网上购买投注、选号合买、投注计算、开奖结果查询 - ${webapp.webName}安全购彩平台</title>
</@override>
<#assign isSingle=isSingle!(salesMode=='SINGLE') />
<@override name="title">
	<title>${lotteryType.lotteryName}<#if !isSingle && playType=1>胆拖<#else>${salesMode.modeName}</#if>投注</title>
</@override>
<@override name="content.top">
	<div class="bggray32 cleanboth "> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&shareType=${shareType}" />" <#if isSingle>class="graydownmenunow"<#else>class="graydownmenu"</#if>>单式</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&playType=0&shareType=${shareType}" />" <#if !isSingle>class="graydownmenunow"<#else>class="graydownmenu"</#if>>复式</a> 
  	</div>
</@override>
<@override name="content.play_caption">
	<#if (playType!0)!=2>
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：七星彩投注区分为七位(第一、二、三、四、五、六、七位)，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为7位数。七星彩玩法即是竟猜7位开奖号码，且顺序一致。。示例 投注方案：01,02,03,04,05,06,07</div>
	</#if>
</@override>
<@extends name="/WEB-INF/content/common/number-scheme-editNew.ftl"/> 
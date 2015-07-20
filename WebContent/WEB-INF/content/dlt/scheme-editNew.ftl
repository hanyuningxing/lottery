<@override name="title">
	<title>体彩大乐透玩法，大乐透购买购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<#assign isSingle=isSingle!(salesMode=='SINGLE') />
<#if (createForm.playType)??><#assign salesPlayType=createForm.playType/><#else><#assign salesPlayType=''/></#if>
<@override name="content.top">
	<div class="bggray32"> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&shareType=${shareType}" />" <#if isSingle && salesPlayType=="General">class="graydownmenunow"<#else>class="graydownmenu"</#if>>大乐透单式上传</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&shareType=${shareType}&createForm.playType=Select12to2" />" <#if isSingle && salesPlayType=="Select12to2"> class="graydownmenunow"<#else> class="graydownmenu"</#if>>生肖乐单式上传</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&shareType=${shareType}" />" <#if !isSingle && salesPlayType=="General" && playType==0>class="graydownmenunow"<#else>class="graydownmenu"</#if>>大乐透复式</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&shareType=${shareType}&createForm.playType=Select12to2" />" <#if !isSingle && salesPlayType=="Select12to2">class="graydownmenunow"<#else>class="graydownmenu"</#if>>生肖乐复式</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&shareType=${shareType}&playType=1" />" <#if !isSingle && playType==1>class="graydownmenunow"<#else>class="graydownmenu"</#if>>大乐透胆拖</a> 
		<a href="<@c.url value="/${lotteryType.key}/scheme!editSpinmatrix.action" />" class="graydownmenu">旋转矩阵</a>
		<a href="<@c.url value="/${lotteryType.key}/scheme!editFilter.action" />" class="graydownmenu">在线过滤</a>
	</div>
</@override>
<@override name="content.play_caption">
  <#if playType!=2>
	<#if salesPlayType=="General" || salesPlayType=="GeneralAdditional">
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：从01-35中选择5个号码（或以上）对前区投注，从01-12中选择2个号码（或以上）对后区投注。所选的5个前区号码和2个后区号码与开奖号码全部相同即中一等奖，一等奖金不固定，追加后单注奖金最高可达<span class="rebchar">1600万</span>。特点：大奖高，小奖多。</div>
	<#else>
		<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;玩法说明：从01-12中选择2个号码（或以上）投注，所选号码与大乐透当期开奖号码的后区号码一致，即中奖；<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="rebchar">单注奖金60元。</span>示例 投注方案：01,02</div>
	</#if>
  </#if>
</@override>
<@override name="content.form.extra">
	<#if (createForm.playType)??>
		<#if salesPlayType=="General" || salesPlayType=="GeneralAdditional"><#assign canDltAdditional=true /></#if>
		<input type="hidden" name="createForm.playType" value="${createForm.playType}" />
	</#if>
</@override>
<@extends name="/WEB-INF/content/common/number-scheme-editNew.ftl"/> 
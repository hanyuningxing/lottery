<@override name="title">
	<title>福彩3d网上购买，3d网上购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<#assign isSingle=isSingle!(salesMode=='SINGLE') />
<#if isSingle>
	<#assign salesPlayTypeMap={'Direct':'直选','Group3':'组选3','Group6':'组选6'} />
<#else>
	 <!--<#assign salesPlayTypeMap={'Direct':'直选','Group3':'组选3','Group6':'组选6','DirectSum':'直选和值','GroupSum':'组选和值','DirectKuadu':'直选跨度','Group3Kuadu':'组三跨度','Group6Kuadu':'组六跨度'} />-->
<#assign salesPlayTypeMap={'Direct':'直选','Group3':'组选3','Group6':'组选6','GroupSum':'组选和值','DirectKuadu':'直选跨度','Group3Kuadu':'组三跨度','Group6Kuadu':'组六跨度'} />
</#if>
<#if (createForm.playType)??>
    <#assign salesPlayType = createForm.playType />
    <#if !(salesPlayTypeMap?keys)?seq_contains(salesPlayType)>
		<#assign salesPlayType='Direct' />
	</#if>
<#else>
	<#assign salesPlayType='Direct' />
</#if>
<@override name="style"><link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" /></@override>
<@override name="content.top">
  	<div class="bggray32"> 
		<#list salesPlayTypeMap?keys as salesPlayTypeKey>
     		<a <#if salesPlayType==salesPlayTypeKey>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=${salesMode}&createForm.playType=${salesPlayTypeKey}" />" >${salesPlayTypeMap[salesPlayTypeKey]}</a>
		</#list>
  		<#if isSingle>
	        <a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND" />"><image src="<@c.url value="/pages/images/fushibt.gif" />" class="qiehuan" /></a>
  		<#else>
	        <a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE" />"><image src="<@c.url value="/pages/images/danshibt.gif" />" class="qiehuan" /></a>
  		</#if>
  	</div>
</@override>
<@override name="content.play_caption">
	<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;</div>
</@override>
<@override name="content.form.extra">
	<input type="hidden" name="createForm.playType" value="${salesPlayType}" />
</@override>
<@extends name="/WEB-INF/content/common/number-scheme-editNew.ftl"/> 
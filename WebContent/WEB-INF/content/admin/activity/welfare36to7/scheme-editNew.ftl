<@override name="title">
	<title>福彩好彩1网上购买，广东南粤好彩1网上投注、36选7选号 - ${webapp.webName}安全购彩平台</title>
</@override>
<#assign salesPlayTypeMap={'Haocai1':'好彩一(数字)','Zodiac':'十二生肖(生肖)','Season':'春夏秋冬(季节)','Azimuth':'东南西北(方位)'} />

<#if (createForm.playType)??>
    <#assign salesPlayType = createForm.playType />
    <#if !(salesPlayTypeMap?keys)?seq_contains(salesPlayType)>
		<#assign salesPlayType='Haocai1' />
	</#if>
<#else>
	<#assign salesPlayType='Haocai1' />
</#if>
<@override name="style"><link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" /></@override>
<@override name="content.top">
  	<div class="bggray32"> 
		<#list salesPlayTypeMap?keys as salesPlayTypeKey>
     		<a <#if salesPlayType==salesPlayTypeKey>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="<@c.url value="/admin/activity/${lotteryType.key}/scheme!editNew.action?salesMode=${salesMode}&createForm.playType=${salesPlayTypeKey}" />" >${salesPlayTypeMap[salesPlayTypeKey]}</a>
		</#list>
  	</div>
</@override>
<@override name="content.play_caption">
	<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />&nbsp;&nbsp;</div>
</@override>
<@override name="content.form.extra">
	<input type="hidden" name="createForm.playType" value="${salesPlayType}" />
</@override>
<@extends name="/WEB-INF/content/admin/activity/number-scheme-editNew.ftl"/> 
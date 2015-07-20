<@override name="title">
	<title>体彩大乐透玩法，大乐透购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>
<@override name="content.select">
	<#if playType?? && playType==2>
		<#include 'editNew-compound-spinmatrix.ftl' /><#-- 旋转矩阵 -->
	<#elseif playType?? && playType==1>
		<#include 'editNew-compound-dantuo.ftl' /><#-- 胆拖 -->
	<#elseif salesPlayType=="General" || salesPlayType=="GeneralAdditional">
		<#include 'editNew-compound-simple.ftl' /><#-- 大乐透 -->
	<#else>
		<#include 'editNew-compound-shengxiao.ftl' /><#-- 生肖 -->
	</#if>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
 
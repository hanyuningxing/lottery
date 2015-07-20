<head>
	<@block name="title"></@block>
	<@block name="decorator">
		<meta name="decorator" content="tradeV1" />
	</@block>
	<@block name="style"></@block>
	<@block name="head"></@block>
	<meta name="menu" content="${webapp.hmdt}"/>
</head>

<@block name="info"></@block>

<div class="w1000">
	<@block name="top"></@block>
	<@block name="menu"></@block>
	<div class="k10"></div>
	<@block name="left">
		<@block name="contentHeader"></@block>
        <@block name="content"></@block>
	</@block>
	<@block name="right"></@block>
</div>

<#include "/common/schemeSuccessDialog.ftl" />
<#include "/common/userRechargeDialog.ftl" />
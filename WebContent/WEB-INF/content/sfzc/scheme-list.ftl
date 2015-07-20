<@override name="title">
	<#if playType=='SFZC14'>
		<title>足彩|胜负彩投注分布数据，网上投注购买以及合买代购 - ${webapp.webName}</title>
		<meta name="Keywords" content="足彩,胜负彩,胜负彩14场，足彩14场，老足彩，投注数据" /> 
		<meta name="Description" content="${webapp.webName}提供胜负彩网上合买代购服务，汇总了全国彩民的投注数据，并对比博彩公司数据，帮助用户找出最佳足彩投注模式。" />
	<#elseif playType=='SFZC9'>
		<title>足彩|任选9场投注分布数据,网上投注购买以及合买代购 - ${webapp.webName}</title>
		<meta name="Keywords" content="足彩,任九，任九玩法，世界杯投注，胜负彩,投注数据" /> 
		<meta name="Description" content="${webapp.webName}提供胜负彩任选9场网上合买代购服务，汇总了全国彩民的投注数据，并对比博彩公司数据，帮着用户找出最佳足彩投注模式。" />	
	</#if>
</@override>
<@extends name="/WEB-INF/content/common/simple-scheme-list.ftl"/> 
<@extends name="base.ftl"/>
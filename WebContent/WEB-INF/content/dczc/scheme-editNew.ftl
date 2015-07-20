<#if shareType?? && shareType == 'SELF'>
	<#assign menu_type='scheme_editNew_SELF' />
<#else>
	<#assign menu_type='scheme_editNew_TOGETHER' />
</#if>
<#assign passTypeArr=passTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PassType@values()")) />
<#assign hasHandicap=playType=='SPF' />
<@override name="title">
	<title>北京单场足彩，北京单场推荐 - ${webapp.webName}安全购彩平台</title>
</@override>  
<@override name="head">
	<meta name="Keywords" content="北单单场,单场足彩,网上买单场,单场投注,五大联赛单场,世界杯单场，欧洲杯单场，网上购买" /> 
	<meta name="Description"content="${webapp.webName}提供单场网上购买和在线选号以及赔率和数据分析，用户在${webapp.webName}可以购买和 参与合买单场胜负，比分，进球数，半全场以及大小单双。"/>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchLanguage.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilter.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/freshSp.js"/>"></script>
	<style>
		.dcchose{ background:#FF6600; color:#fff;}
	</style>
	<script type="text/javascript">
		window.maxPassMatchCount = ${playType.maxMatchSize};
		window.PassType = {};
		window.PassTypeArr = [];
		<#list passTypeArr as passType>
			window.PassTypeArr[${passType_index}] = PassType['${passType}'] = {
				key : '${passType}',
				units : ${passType.units},
				matchCount : ${passType.matchCount},
				passMatchs :[<#list passType.passMatchs as c><#if c_index gt 0>,</#if>${c}</#list>],
				text : '${passType.text}',
				value : ${passType.value}
			};
		</#list>
	</script>
	<@block name="editNewHead"></@block>
</@override>  

<@override name="content">
    <div class="tzleft">
		<!--对阵头部-->
		<@block name="topContent"></@block>		
		<!--对阵-->
		<@block name="matchContent">
			<div id="jz_match" style="float:left; width:710px;">
			<#if matchMap?? && matchMap?size gt 0>
				<#if playTypeWeb??>									
					<#include 'match-${playTypeWeb}.ftl' />
				<#else>
					<#include 'match-${playType}.ftl' />
				</#if>
			<#else>
				<div style="line-height:100px;text-align:center;height:100px;border:1px dashed #ccc;border-top:none;background:#F4F5F3;">暂无可投注的赛事.</div>
			</#if>
			</div>	
		</@block>
		<div class="k10"></div>	   	  				
			<div class="j9ts">
			  <div class="bro_af5529">北京单场足彩在线投注频道</div>
		      <div class="tzts bro_6D4F42"><span class="tztsl">1、</span><span class="tztsr">网上投注，手机北京单场网上服务，强大的过滤软件和预测分析，让你投注轻松，享受购彩，天天中奖。</span></div>
		      <div class="tzts bro_6D4F42"><span class="tztsl">2、</span><span class="tztsr">  足彩单场属竞技型彩票范畴。根据不同的玩法，分为让球胜平负、比分、上下盘单双、总进球数、半全场胜平负等5个玩法，玩法多，趣味性强，深的彩民的喜爱。</span></div>
		      <div class="tzts bro_6D4F42"><span class="tztsl">3、</span><span style="float:left; width:645px;">北京单场投注：从指定的N场比赛中，选择一场比赛，或者多个单场比赛进行投注，对于北京单场玩法来说，这样的投注方式并不可取，其成本和盈利兑换上并不划算。</span></div>
		      <div class="tzts bro_6D4F42"><span class="tztsl">4、</span><span style="float:left; width:645px;">北京单场过关投注：从指定的N场比赛中，同时对多个单场比赛进行投注，构成一注过关投注。对单个过关投注的场次，要求其必须要全对才算中奖。示例：让球胜平负3串1(投1注玩3场)：第3场(平)、第6场(胜)、第7场(平)，投注金额＝1(注)×2＝2(元)，三场结果全中即中奖。</span></div>
		   </div>				
	  
	</div>	
</@override>

<@override name="right">	
	<div class="tzrig">
	  <div id="info" style="width:280px">
		  <@block name="initContent"></@block>
		  <@block name="pageInfo">
			    <div style="padding-bottom:10px;"><a href="${base}/jczq/scheme!editNew.action?playTypeWeb=EXY" target="_blank"><img src="${base}/V1/images/guanggao.jpg" class="guanggao"/></a></div>
				<!--新闻资讯 begin-->
				<div class="tzgrayk">
			      <div class="tzgraybg">新闻资讯</div>
			      <div class="tznews_listwz">
			        <ul class="tznews_list">
			          <#if newsList?? && newsList?size gt 0>
			          <#assign i=0/>
						<#list newsList as data>
							<#assign i=i+1/>
							<#if i lt 4>
								<li><span class="iconred">${i}</span><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>
							<#else>
								<li><span class="iconty">${i}</span><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.title!}</a></li>					
							</#if>
						</#list>
					  </#if>		  		  	
			        </ul>
			      </div>
			    </div>
			    <!--新闻资讯 end-->
		  </@block>
	  </div>
   </div>
	  <script type="text/javascript" src="${base}/js/lottery/floatDiv.js"></script> 
</@override>
<@extends name="base.ftl"/> 

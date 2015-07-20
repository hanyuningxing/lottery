
<#assign passTypeArr=passTypeArr!(stack.findValue("@com.cai310.lottery.support.jczq.PassType@values()")) />
<#assign hasHandicap=playType=='SPF' />
<@override name="title">
	<title>${lotteryType.lotteryName}网上购买推荐，${lotteryType.lotteryName}${playType.text}在线购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>  
<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchLanguage.js"/>"></script>
	<script type="text/javascript">
		window.maxSingleMatchCount = 8;
		window.maxPassMatchCount = ${playType.maxMatchSize};
		window.PassType = {};
		window.PassTypeArr = [];
		<#list passTypeArr as passType>
			PassTypeArr[${passType_index}] = PassType['${passType}'] = {
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
		  <div class="bro_af5529">竞彩足球投注提示</div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">1、</span><span class="tztsr">竞猜全部玩法过关投注、及比分单关投注奖金为固定奖金，赛事选择界面显示仅为当前参考奖金。实际奖金以出票时刻固定奖金为准。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">2、</span><span class="tztsr">让球符号含义，"+"为客让主，"-"为主让客。让球数含义，即（主队得分±让球数）减客队得分，大于0为胜，等于0为平，小于0为负。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">3、</span><span style="float:left; width:645px;">竞彩足球的官方销售时间为：周一至周五09:00-00:00，周六至周日09:00-01:00。本平台的代购截止时间提前于官方彩票销售截止时间 6分钟。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">4、</span><span style="float:left; width:645px;">竞彩足球彩果，以比赛90分钟内比分（含伤停补时）结果为准。其中投注赛事取消、中断或改期，官方比赛彩果公布或确认取消将延后36小时，对应场次奖金派发或退款将同步延后处理；取消比赛的任何结果都算对，固定奖金按照1计算</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">5、</span><span style="float:left; width:645px;">普通过关让球胜平负玩法最多过8关，总进球数玩法最多过6关，比分和半全场胜平负玩法最多过4关。自由过关没限制。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">6、</span><span style="float:left; width:645px;">竞彩足球比分玩法是唯一可进行单关固定奖金投注的玩法，在比分玩法页面中可同时进行单关+过关的固定奖金投注。</span></div>
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

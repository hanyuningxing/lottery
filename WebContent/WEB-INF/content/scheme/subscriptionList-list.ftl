<head>
<meta name="decorator" content="tradeV1" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/qktz.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/thinkbox/thinkbox.js"></script>
<script type="text/javascript" src="${base}/js/ssologin.js"></script>
<script type="text/javascript" src="${base}/js/common.js"></script>
<script type="text/javascript" src="${base}/js/lottery-common.js"/>"></script>
<script type="text/javascript" src="${base}/js/lottery/index.js"></script>
<script type="text/javascript" src="${base}/js/subscribeReq.js"></script>
<meta name="menu" content="${webapp.hmdt}"/>
</head>
<script type="text/javascript">
$(document).ready(function() {
	//更改背景图片
	if('${lotteryType.key}'=="ssq"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_ssq.gif)");
	}else if('${lotteryType.key}'=="dlt"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_dlt.gif)");
	}else if('${lotteryType.key}'=="pl"){
		if('${playType!}'==0){
			$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_pl3.gif)");
		}else{
			$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_pl5.gif)");
		}
	}else if('${lotteryType.key}'=="welfare3d"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_3d.gif)");
	}else if('${lotteryType.key}'=="welfare36to7"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_hcy.gif)");
		$("#endInitTime").html("每天18:30分");		
	}else if('${lotteryType.key}'=="seven"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_qlc.gif)");	
	}else if('${lotteryType.key}'=="tc22to5"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_eexw.gif)");	
	}else if('${lotteryType.key}'=="lczc"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_6cb.gif)");
	}else if('${lotteryType.key}'=="sczc "){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_4c.gif)");
	}else if("${lotteryType.key}"=="sfzc"){
		if("${playType!}"=="SFZC14"){
			$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_14c.gif)");
		}else if("${playType!}"=="SFZC9"){
			$("#h4l").css("background-image","url(${base}/pages/hmdtimages/h4_r9.gif)");
		} 
	}else if("${lotteryType.key}"=="jclq"){
		$("#h4l").css("background-image","url(${base}/pages/hmdtimages/jclqlogo.gif)");
	}else{
		$("#endInitTime").html("单复式赛前20分钟");
	}
});

//玩法介绍
function introductLottery(playType){
	var url = '${base}'+"/info/rule/${lotteryType}.html";
	if('${lotteryType.key}'=="pl"){
		if('${playType!}'==0){
			url+="&type=0";
			window.open(url,"排列三");
		}else{
			url+="&type=1";
			window.open(url,"排列五");
		}
	}else{
	   window.open(url,"${lotteryType.lotteryName}");
	}
}
//发起合买
function startHM(){
	var lotteryType = '${lotteryType.key}';
	var url;
	if('${lotteryType.lotteryName}'=="排列3/5"){
		if('${playType!}'==0){
			url="${base}/${lotteryType.key}/scheme!editNew.action?playType=0";
		}else{
			url="${base}/${lotteryType.key}/scheme!editNew.action?playType=1";
		}
		window.open(url,"${lotteryType.lotteryName}");
	}else{
		window.open("${base}/${lotteryType.key}/","${lotteryType.lotteryName}");
	}
}
function myHM(){
	$.post(window.BASESITE + '/user/user_checkLogin.html',{					
	},function(data){
		if(data.success){	
			window.location = "${base}/${lotteryType.key}/scheme_myList.html";
		}else{
			$SSO.login_auth();
			return false;
		}
	},'json');
}
</script>
<!--All top end -->
<!--main-->
<div class="w1000">
  <div class="hm_left">
    <div class="hm_navtop"></div>
    <div class="hm_bg">
    <#if lotteryType.key!="jczq" && lotteryType.key!="jclq" && lotteryType.key!="dczc">
      <ul class="hm_list">
        <li><a href="${base}/jczq/scheme_subList.html">竞彩足球</a></li>
        <li><a href="${base}/jclq/scheme_subList.html">竞彩篮球</a></li>
        <li><a href="${base}/dczc/scheme_subList.html">北京单场</a></li>
      </ul>
    </#if>
    <#if lotteryType.key=="jczq">
      <ul class="hm_list">
        <li><a href="${base}/jczq/scheme_subList.html" class="now">竞彩足球</a></li>
        <li><a href="${base}/jclq/scheme_subList.html">竞彩篮球</a></li>
        <li><a href="${base}/dczc/scheme_subList.html">北京单场</a></li>
      </ul>
    <#elseif lotteryType.key=="jclq">
      <ul class="hm_list">
        <li><a href="${base}/jczq/scheme_subList.html">竞彩足球</a></li>
        <li><a href="${base}/jclq/scheme_subList.html" class="now">竞彩篮球</a></li>
        <li><a href="${base}/dczc/scheme_subList.html"">北京单场</a></li>
      </ul>
    <#elseif lotteryType.key=="dczc">
      <ul class="hm_list">
        <li><a href="${base}/jczq/scheme_subList.html">竞彩足球</a></li>
        <li><a href="${base}/jclq/scheme_subList.html">竞彩篮球</a></li>
        <li><a href="${base}/dczc/scheme_subList.html" class="now">北京单场</a></li>
      </ul> 
    </#if>
      <div class="hm_jg">足球彩票</div>
      <div class="hm_jgwz">
    <#if lotteryType.key!="sczc" && lotteryType.key!="sfzc" && lotteryType.key!="lczc">
        <ul class="hm_jgwzlist">
          <li><a href="${base}/sfzc/scheme_subList-SFZC14.html">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme_subList-SFZC9.html">任选9场</a></li>
          <li><a href="${base}/lczc/scheme_subList.html">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme_subList.html">4场进球</a></li>
        </ul> <div class="cb"></div>
     </#if> 
     
     <#if lotteryType.key=="sfzc" && playType=="SFZC14">
        <ul class="hm_jgwzlist">
          <li><a href="${base}/sfzc/scheme_subList-SFZC14.html" class="now">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme_subList-SFZC9.html">任选9场</a></li>
          <li><a href="${base}/lczc/scheme_subList.html">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme_subList.html">4场进球</a></li>
        </ul> <div class="cb"></div>
     </#if>
     
     <#if lotteryType.key=="sfzc" && playType=="SFZC9">
        <ul class="hm_jgwzlist">
          <li><a href="${base}/sfzc/scheme_subList-SFZC14.html">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme_subList-SFZC9.html" class="now">任选9场</a></li>
          <li><a href="${base}/lczc/scheme_subList.html">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme_subList.html">4场进球</a></li>
        </ul> <div class="cb"></div>
     </#if> 
     
     <#if lotteryType.key=="sczc">
        <ul class="hm_jgwzlist">
          <li><a href="${base}/sfzc/scheme_subList-SFZC14.html">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme_subList-SFZC9.html">任选9场</a></li>
          <li><a href="${base}/lczc/scheme_subList.html">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme_subList.html"  class="now">4场进球</a></li>
        </ul> <div class="cb"></div>
     </#if>
     
     <#if lotteryType.key=="lczc">
        <ul class="hm_jgwzlist">
          <li><a href="${base}/sfzc/scheme_subList-SFZC14.html">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme_subList-SFZC9.html">任选9场</a></li>
          <li><a href="${base}/lczc/scheme_subList.html" class="now">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme_subList.html">4场进球</a></li>
        </ul> <div class="cb"></div>
     </#if>
      </div>
    </div>
  </div>
  <!--hm left end-->
  
  <div class="hm_rig">
    <div class="hm_topsm"><span></span><a href="#" class="red">${webapp.webName}携手与您冲击大奖</a></div>
    <div class="hm_topbg">
      <div class="hm_logowz">
      <#if lotteryType.key=="jczq">
        <div class=" hm_logo hmlogo_jczq"></div>
      <#elseif lotteryType.key=="jclq">
      	<div class=" hm_logo hmlogo_jclq"></div>
      <#elseif lotteryType.key=="dczc">
      	<div class=" hm_logo hmlogo_bd"></div>
      <#elseif lotteryType.key=="sfzc">
         <#if playType=="SFZC14">
         	<div class=" hm_logo hmlogo_sfc"></div>
         <#else>
         	<div class=" hm_logo hmlogo_r9"></div>
         </#if>
      <#elseif lotteryType.key=="sczc">
      	<div class=" hm_logo hmlogo_goal4"></div>
      <#elseif lotteryType.key=="lczc">
      	<div class=" hm_logo hmlogo_half6"></div>
      </#if>
      </div>
	
  	  <#if playType?? && playType=="SFZC9">
  	  	<div class="hm_name">任9场</div>
  	  <#else>
  	  	<div class="hm_name">${lotteryType.lotteryName}</div>
  	  </#if>
      
      <div class="hm_btwz"> <a href="javascript:;" onclick="startHM();" class="hmtopbt">发起合买</a> <a href="javascript:;" onclick="startHM();" class="hmtopbt">发起代购</a> <a href="javascript:;" onclick="myHM();" class="hmtopbt">我的合买</a> </div>
      <div class="hm_time"> 截止时间：<span id="endInitTime" class="red">${endInitTime?string('yyyy-MM-dd hh:mm')}</span><br />
      <#if lotteryType.key=="jczq" || lotteryType.key=="jclq" || lotteryType.key=="dczc">
        <a href="${base}/${lotteryType.key}/scheme_review.html" target="_blank">开奖公告</a>&nbsp;<span class="gray">|</span>&nbsp;<a href="javascript:;" onclick="introductLottery('${lotteryType.lotteryName}');">玩法介绍</a> </div>
      <#else>
        <a href="${base}/passcount/index_index.html" target="_blank">开奖公告</a>&nbsp;<span class="gray">|</span>&nbsp;<a href="javascript:;" onclick="introductLottery('${lotteryType.lotteryName}');">玩法介绍</a> </div>      	
      </#if>
    </div>
    <div class="hm_h35">
      <form action="<@c.url value="/${lotteryType.key}/scheme_subList.html" />" method="post" id="scheme_list_form">
	      <#include "../common/subscription-list-query-menu.ftl"/>
	      <input type="hidden" name="queryForm.orderType" value="<#if queryForm??&&queryForm.orderType??>${queryForm.orderType!}<#else>PROCESS_RATE_DESC</#if>" />
          <#if salesMode??><input type="hidden" name="salesMode" value="${salesMode}" /></#if>
          <#if playType??><input type="hidden" name="playType" value="${playType!}" /></#if>
      </form>
    </div>
    <!--合买表格-->
    <#include "../common/subscription-list-table.ftl"/>
</div>
<!--main end-->
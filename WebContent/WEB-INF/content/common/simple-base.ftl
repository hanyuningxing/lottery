<head>
	<@block name="title"></@block>
	<@block name="decorator"><meta name="decorator" content="trade" /></@block>
	<@block name="style"></@block>
	<@block name="head"></@block>
</head>

<@block name="info"></@block>

<div class="main">
	<div class="dcleft">
		<@block name="top">
	    	<div class="${topClass!}">
		      <div class="sfct1"><#if period??><span class="ocf font14char boldchar">${lotteryType.lotteryName}第${period.periodNumber!}期</span> 开奖日期：${period.prizeTime?string('yyyy-MM-dd HH:mm (E)')}</#if></div>
		      <div class="sfct2">
		      	<#if singlePeriodSales??><span class="boldchar">单式投注</span>截止时间：<span class="ocf">${singlePeriodSales.shareEndInitTime?string('MM-dd HH:mm (E)')}</span>&nbsp;&nbsp;&nbsp;&nbsp;</#if>
		      	<#if compoundPeriodSales??><span class="boldchar">复式投注</span>截止时间：<span class="ocf">${compoundPeriodSales.shareEndInitTime?string('MM-dd HH:mm (E)')}</span></#if>
		       </div>
		    </div>
	    </@block>
		<div class="navweizhi">
			<div class="floatleft" style="width:570px;">
				<@block name="menu_left" >
					<ul class="tytopnav">
						<#assign menu_type=menu_type!'' />
			    		<li><a <#if menu_type=='scheme_editNew_SELF'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?shareType=SELF" />" >发起代购</a></li>
			    		<li><a <#if menu_type=='scheme_editNew_TOGETHER'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?shareType=TOGETHER" />">发起合买</a></li>
			    		<li><a <#if menu_type=='scheme_list'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!list.action" />" >参与合买</a></li>
			        	<li><a <#if menu_type=='scheme_myList'>class="now"</#if> href="#" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/${lotteryType.key}/scheme!myList.action"/>';});return false;">我的投注</a></li>					
			    	    <li><a href="<@c.url value="/info/rule/rule.action?lottery=${lotteryType}"/>" target="_blank" <#if menuType==4>class="now"</#if> rel="nofollow">玩法规则</a></li>
			    	</ul>
			    </@block>
			</div>
			<div class="floatrig rig5 top5px" id="show_count">
				<@block name="menu_right" >
					<input id="cur_time" type="hidden" value="${datetime().millis}" />
					<script type="text/javascript"></script>
					当前时间: <span id="count_m" class="timegreenchar">--</span>月<span id="count_d" class="timegreenchar">--</span>日<span id="count_h" class="timegreenchar">--</span>时<span id="count_i" class="timegreenchar">--</span>分
			      	<script type="text/javascript">
						function get_cur_time(){ 
							return document.getElementById('cur_time').value; 
						}
						function set_cur_time(cur_time){
							cur_time = parseInt(cur_time,10);
							var cur_time_d = new Date(cur_time);
							var m = (cur_time_d.getMonth()+1).toString();
							var d = (cur_time_d.getDate()).toString();
							var h = (cur_time_d.getHours()).toString();
							var i = (cur_time_d.getMinutes()).toString();
							$("#count_m").html(m);
							$("#count_d").html(d);
							$("#count_h").html(h);
							$("#count_i").html(i);
							next_time = cur_time + 60000;
							setTimeout("set_cur_time(next_time)",60000);
						}
						$(document).ready(function(){
							var cur_time = get_cur_time();
							set_cur_time(cur_time);
						});
					</script>
				</@block>
			</div>
		</div>
        <@block name="contentHeader"></@block>
        <@block name="content"></@block>
	</div>
	<div class="dcrig">
		<@block name="right"><#include "/common/lottery_navigation.ftl" /></@block>
	</div>
</div>

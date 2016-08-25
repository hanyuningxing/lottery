<head>
	<@block name="Keywords"></@block>
	<@block name="Description"></@block>
	<@block name="title"></@block>
	<@block name="decorator"><meta name="decorator" content="trade" /></@block>
	<@block name="style"><link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" /></@block>
	<@block name="style"><link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" /></@block>
	<@block name="head"></@block>
</head>

<#if salesMode?? && shareType??>
	<#if salesMode=='COMPOUND' && compoundPeriodSales??>
		<#assign currentPeriodSales=compoundPeriodSales />
	<#elseif salesMode=='SINGLE' && singlePeriodSales??>
		<#assign currentPeriodSales=compoundPeriodSales />
	</#if>
	<#if currentPeriodSales??>
		<#if shareType=='TOGETHER'>
			<#assign endInitTime = currentPeriodSales.shareEndInitTime />
		<#elseif shareType=='SELF'>
            <#assign endInitTime = currentPeriodSales.selfEndInitTime />
		</#if>
	</#if>
</#if>

<@block name="info"></@block>

<div class="main">
	<div class="main_czleft">
	    <@block name="top">
			<div id="${topId!}">	
		      <div class="czlogochar"><@block name="lotteryName">${lotteryType.lotteryName}</@block></div>
		      <div class="rightqu">
			      <@block name="top.sale_msg">
					<#if period??>
				        <div>
				        	<span class="heiti16">当前销售：第<span class="shuzhieng16">${period.periodNumber}</span>期</span><@block name="top.lottery_msg"></@block>	   
					        <#if endInitTime??><div class="dbrown top10px">投注截止时间：${endInitTime?string('MM-dd HH:mm')}&nbsp;&nbsp;&nbsp;&nbsp;开奖时间：${period.prizeTime?string('MM-dd HH:mm')}</div></#if>
				        </div> 
				         <!-- 公告标签 -->
				            <#if noticeNewsMap??&&noticeNewsMap[lotteryType]??>
				       	   <div class="kcwzgg">
						   <div class="kcwzgg_img"></div>
						   <div class="kcwzgg_zi">
									${noticeNewsMap[lotteryType]!}
							</div>
					       </div>
					       </#if> 
			    	</#if>			      
			      </@block>
		       </div>
		    </div>
		    
		    
	    </@block>
		<div class="navweizhi">
			<div class="floatleft" style="width:480px;">
				<@block name="menu_left" >
					<ul class="tytopnav">
						<#assign menu_type=menu_type!'' />
			    		<li><a <#if menu_type=='scheme_editNew'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action" />" >购买彩票</a></li>
			    		<li><a <#if menu_type=='scheme_list'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!list.action" />" rel="nofollow">参与合买</a></li>
			    		<li><a <#if menu_type=='result'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/result.action"/>" rel="nofollow">开奖号码</a></li>
			        	<li><a <#if menu_type=='scheme_myList'>class="now"</#if> href="#" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/${lotteryType.key}/scheme!myList.action"/>';});return false;" rel="nofollow">我的投注</a></li>					
			    		<li><a href="<@c.url value="/info/rule/rule.action?lottery=${lotteryType}"/>" target="_blank" <#if menuType==4>class="now"</#if> rel="nofollow">玩法规则</a></li>
		            </ul>
			    </@block>
			</div>
			<div class="floatrig rig5 top5px" id="show_count">
				<@block name="menu_right" >
					<#if period?? && endInitTime??>
			                                购买截止还有: <span id="count_d" class="timegreenchar">--</span>天<span id="count_h" class="timegreenchar">--</span>时<span id="count_i" class="timegreenchar">--</span>分<span id="count_s" class="timegreenchar">--</span>秒</span>
						<script type="text/javascript">
							var time = 0;
							var start_time = get_server_time();
							var end_time = new Date('${endInitTime?string('MM/dd/yyyy HH:mm:ss')}');
							var d_end_time = new Date('');
							var end_utc_time = Date.UTC(end_time.getYear(),end_time.getMonth(),end_time.getDate(),end_time.getHours(),end_time.getMinutes(),end_time.getSeconds());
							var start_utc_time = Date.UTC(start_time.getYear(),start_time.getMonth(),start_time.getDate(),start_time.getHours(),start_time.getMinutes(),start_time.getSeconds());
							var d_end_utc_time = Date.UTC(d_end_time.getYear(),d_end_time.getMonth(),d_end_time.getDate(),d_end_time.getHours(),d_end_time.getMinutes(),d_end_time.getSeconds());
							function jz_show_tips(){
							    $('#error_txt').show();
							}
							$(document).ready(function(){
								run(start_utc_time,end_utc_time,jz_show_tips);
							});
						</script>
					</#if>					
				</@block>
			</div>
		</div>
        <@block name="contentHeader"></@block>
        <@block name="content"></@block>
	</div>
	<div class="main_cz_right">
		<@block name="right"></@block>
	</div>
</div>

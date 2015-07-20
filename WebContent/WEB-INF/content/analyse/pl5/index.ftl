<head>
	<title>${lotteryType.lotteryName}综合走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/pl5_ball_data.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/pl5_miss_zx.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/pl5_miss_ch.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/chzs_5.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
		});
</script>
<style>
/* 球状态*/
.nballred{ background:url(<@c.url value= "/pages/images/newrball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.bluedot{ background:url(<@c.url value= "/pages/images/lblue.gif"/>) no-repeat; display:block;  width:15px; height:15px; margin:0 auto; margin-top:3px;}
.reddot{ background:url(<@c.url value= "/pages/images/lred.gif"/>) no-repeat; display:block;  width:15px; height:15px; margin:0 auto; margin-top:3px;}
.greendot{ background:url(<@c.url value= "/pages/images/lgreen.gif"/>) no-repeat; display:block;  width:15px; height:15px; margin:0 auto; margin-top:3px;}
.blackdot{ background:url(<@c.url value= "/pages/images/lblack.gif"/>) no-repeat; display:block;  width:15px; height:15px; margin:0 auto;  margin-top:3px;}
.ballred{ background:url(<@c.url value= "/pages/images/rball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.ballgreen{ background:url(<@c.url value= "/pages/images/ball_2.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.ballblue{ background:url(<@c.url value= "/pages/images/ball_3.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.bball{ background:url(<@c.url value= "/pages/images/bball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.rball{ background:url(<@c.url value= "/pages/images/rball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
.gball{ background:url(<@c.url value= "/pages/images/gball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
/* 球可点击状态*/
a.gball{ background:url(<@c.url value= "/pages/images/gball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}
a.gball:hover{ background:url(<@c.url value= "/pages/images/rball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px; text-decoration:none;}
a.nowball,a.nowball:hover{ background:url(<@c.url value= "/pages/images/rball.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px; text-decoration:none;}
.ballredone{ background:url(<@c.url value= "/pages/images/ball_1.gif"/>) no-repeat center center; display:block; color:#ffffff; text-align:center; width:15px; height:15px; line-height:15px;}

</style>




</head>
<div class="main980">
        <#assign type='zhonghe'/>
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
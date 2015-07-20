<head>
	<title>${lotteryType.lotteryName}直三走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/pl3_ball_data.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/pl3_dxjozh.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/pl/dxjozh.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
		});
</script>
</head>
<div class="main980">
<#assign type='zhixuan'/>
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
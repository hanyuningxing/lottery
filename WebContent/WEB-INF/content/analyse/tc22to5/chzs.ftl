<head>
	<title>${lotteryType.lotteryName}综合走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/tc22to5/tc22to5data.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/tc22to5/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/tc22to5/chzs.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			//initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
		});
	</script>
</head>
<#assign menu="chzs" />
<div class="main980">
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
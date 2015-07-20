<head>
	<title>${lotteryType.lotteryName}隔位号走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/sdel11to5/el11to5data.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/sdel11to5/el11to5_num.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/sdel11to5/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/sdel11to5/jghzs.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			//initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
		});
</script>
</head>
<div class="main980">
        <#assign type='jgh'/>
        <#include "top.ftl"/>
		<#include "../top_keno.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
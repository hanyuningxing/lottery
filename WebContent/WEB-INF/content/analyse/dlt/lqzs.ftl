<head>
	<title>${lotteryType.lotteryName}后区走势</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/dlt/dltdata.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/dlt/dlt_blue.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/dlt/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/dlt/lqzs.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
		});
	</script>
</head>
<#assign menu="lqzs" />
<div class="main980">
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
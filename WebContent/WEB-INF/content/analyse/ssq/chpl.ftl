<head>
	<title>${lotteryType.lotteryName}开奖号码平率柱状图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssq/ssqdata.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssq/chpl.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			chgDisplay('size_30', 30);
		});
	</script>
</head>
<#assign menu="chpl" />
<div class="main980">
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>

<#include "../bottom_common.ftl"/>
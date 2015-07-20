<head>
	<title>${lotteryType.lotteryName}出号频率</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/el11to5data.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/chpl.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			chgDisplay('size_30', 30);
		});
	</script>
</head>
<div class="main980">
        <#assign type='chpl'/>
        <#include "top.ftl"/>
		<#include "../top_keno.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
<head>
	<title>${lotteryType.lotteryName}综合走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/el11to5data.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/el11to5_num.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/base.js"/>"></script>
	<script type="text/javascript">
			<#if jo==0>TYPE_OBJ.jiNum=true;<#else>TYPE_OBJ.jiNum=false;</#if>
			<#if dx==0>TYPE_OBJ.xiaoNum=true;<#else>TYPE_OBJ.xiaoNum=false;</#if>
			<#if zh==0>TYPE_OBJ.zhiNum=true;<#else>TYPE_OBJ.zhiNum=false;</#if>
	</script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/chzs.js"/>"></script>
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
        <#assign type='zhonghe'/>
        <#include "top.ftl"/>
		<#include "../top_keno.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
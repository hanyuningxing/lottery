<head>
	<title>${lotteryType.lotteryName}位置走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/qyhdata.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/qyh_num.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/base.js"/>"></script>
	<script type="text/javascript" >
       	var weizhi = ${wz};
    </script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/weizhi.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
			chgDisplay('size_30', 30);
			//document.getElementById('miss_mode_' + MISS_MODE).checked = true;
			
		});
	</script>
</head>
<#assign menu="weizhi" />
<div class="main980">
        <#include "top.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
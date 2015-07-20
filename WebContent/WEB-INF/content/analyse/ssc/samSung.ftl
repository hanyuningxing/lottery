<head>
	<title>${lotteryType.lotteryName}三星综合走势图</title>
	<meta name="decorator" content="analyse" />
	
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/sscdata.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/samSung.js"/>"></script>
	<link href="<@c.url value= "/pages/css/ssc_zs.css"/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		window.addEvent('domready', function() {
			//initDataHash(30);// 初始化历史开奖数据		
			chgDisplay(50);
			$("MISS_EL").setStyle("display","none");
		});
	</script>
</head>
<#assign menu="samSung" />
<div class="main980">
        <#include "top.ftl"/>
 </div>       
		<div id="ga"></div>
<#include "../bottom_common.ftl"/>
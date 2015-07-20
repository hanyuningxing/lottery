<head>
	<title>${lotteryType.lotteryName}二星012路走势图</title>
	<meta name="decorator" content="analyse" />
	<style>
	.trendCharts{ margin:0px auto; padding:0px;width:980px;background:#FFFEF1;border:1px solid #444;border-collapse:separate;border-spacing:0;color:#666;font:12px "宋体",Arial,Tahoma;text-align:center;}
</style>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/sscdata.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/lyel.js"/>"></script>
	<link href="<@c.url value= "/pages/css/ssc_zs.css"/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		window.addEvent('domready', function() {
			//initDataHash(30);// 初始化历史开奖数据		
			chgDisplay(50);
			$("MISS_EL").setStyle("display","none");
		});
	</script>
</head>
<#assign menu="lyel" />
<div class="main980">
        <#include "top.ftl"/>
 </div>       
		

		        <div id="ga"></div>
<#include "../bottom_common.ftl"/>
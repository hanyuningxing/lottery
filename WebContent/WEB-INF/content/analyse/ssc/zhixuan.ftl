<head>
	<#if type??&&type='zx1'>
		<title>${lotteryType.lotteryName}一星直选遗漏</title>
	<#elseif type??&&type='zx2'>
		<title>${lotteryType.lotteryName}二星直选遗漏</title>
	<#elseif type??&&type='zx3'>
		<title>${lotteryType.lotteryName}三星直选遗漏</title>	
	</#if>
	<meta name="decorator" content="analyse" />
	<link href="<@c.url value= "/pages/css/assets/css/style.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-latest.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.tablesorter.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/ssc/sxjo.js"/>" /></script>
	<script type="text/javascript">
		$(document).ready(function(){ 
			
       		 $("#missTable").tablesorter(); 
			 $("#MISS_EL").css("display","none");
    	}); 
    	
    	 function chgPeriodNumber(count){
    		$("#count").val(count);
    		var weixuan_menu = document.getElementById("weixuan_menu");
			weixuan_menu.submit();
			return false;
    	}
	</script>
	
</head>
<#assign menu="zhixuan" />

<div class="main980">
        <#include "top.ftl"/>
</div>
<div align="center">
	<table id="missTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1"> 
		<thead> 
			<tr align="center"> 
		    	<th>号码</th>
				<th>出现次数</th>
				<th>理论次数</th>
				<th>最大遗漏</th>
				<th>最新遗漏</th>
				<th>平均遗漏</th>
				<th>欲出几率</th> 
				<th>上次遗漏</th>
				<th>投资价值</th>
				<th>回补几率</th>
			</tr> 
		</thead> 
		<tbody> 
		<#list datas as data>
			<tr align="center">		
				<td>${data[0]}</td>
				<td>${data[1]}</td>
				<td>#{(data[11]!0);M2}</td>
				<td>${data[3]}</td>
				<td>${data[4]}</td>
				<td>#{(data[5]!0);M2}</td>
				<td>#{(data[6]!0);M2}</td>		
				<td>${data[8]}</td>	
				<td>#{(data[9]!0);M2}</td>	
				<td>#{(data[10]!0);M2}</td>			
			</tr>
		</#list>
		
		</tbody> 
	</table> 
</div>
<#include "../bottom_common.ftl"/>
<head>
	<title>${lotteryType.lotteryName}五行遗漏</title>
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
<#assign wuxing=['金','木','水','火','土'] />

<div class="main980">
        <#include "top.ftl"/>
</div>
<div align="center">
	<table id="missTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1"> 
		<thead> 
			<tr align="center"> 
		    	<th>五行</th>
				<th>出现次数</th>
				<th>理论次数</th>
																<th>理论偏差</th>
				
				<th>最大遗漏</th>
				<th>当前遗漏</th>
								<th>上次遗漏</th>
				
				<th>平均遗漏</th>
				<th>欲出几率</th> 
				<th>投资价值</th>
				<th>回补几率</th>
			</tr> 
		</thead> 
		<tbody> 
		<#list datas as data>
			<tr align="center">		
				<td>${wuxing[data_index]}</td>
				<td>${data[1]}</td>
				<td>#{(data[11]!0);M2}</td>
																				<td>#{data[1]-(data[11]!0);M2}</td>
				
				<td>${data[3]}</td>
				<td>${data[4]}</td>
								<td>${data[8]}</td>	
				
				<td>#{(data[5]!0);M2}</td>
				<td>#{(data[6]!0);M2}</td>		
				<td>#{(data[9]!0);M2}</td>	
				<td>#{(data[10]!0);M2}</td>			
			</tr>
		</#list>
		
		</tbody> 
	</table> 
	
	<div class="kong10"></div>
  <div class="bor1"><strong>参数说明：</strong><br>
    [出现次数] 是指统计期内，某个号码一共开出的次数。<br/>
[理论次数] 是指统计期内，某个号码在理想情况下应该出现的次数，它是一个理论值。<br/>
[理论偏差] 指统计期内，实出次数-理论次数，负数表示少出，正数表示多出<br/>
[最大遗漏] 是指统计期内，某个号码遗漏的最大值<br/>
[当前遗漏] 指该号码自上次开出之后到现在的遗漏次数<br/>
[上次遗漏] 指该号码上次开出之前的遗漏次数<br/>
[平均遗漏] 指多期遗漏的平均值<br/>
[欲出几率] 本期遗漏/平均遗漏<br/>
[投资价值] 本期遗漏 ÷ 循环周期 <br/>
[回补几率] (上次遗漏-本期遗漏)/循环周期<br/>
	
	[五行] 中国古代五行学说认为宇宙万物，都由木火土金水五种基本物质的运行(运动)和变化所构成。<br>借助这一理论,我们将好彩1的36个号码进行五行分组:<br>
	金: 09 10 17 18 25 26 <br>
	木: 07 08 21 22 29 30 <br>
	水: 05 06 13 14 27 28 35 36<br> 
	火: 01 02 15 16 23 24 31 32 <br>
	土: 03 04 11 12 19 20 33 34<br>
</div>
	
</div>
<#include "../bottom_common.ftl"/>
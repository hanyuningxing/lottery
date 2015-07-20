<head>
	<title>${lotteryType.lotteryName}合数遗漏</title>
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
<#assign shengxiao=['合数1','合数2','合数3','合数4','合数5','合数6','合数7','合数8','合数9','合数10','合数11'] />

<div class="main980">
        <#include "top.ftl"/>
</div>
<div align="center">
	<table id="missTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1"> 
		<thead> 
			<tr align="center"> 
		    	<th>合数</th>
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
				<td>${shengxiao[data_index]}</td>		
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
	
	[合数定义] 是指号码的十位数和个位数的和值，例如19的合数为10、25合数为7、27的合数为9。<br>36个号码对应的合数：<br>
	1合：01、10 <br>
	2合：02、11、20<br> 
	3合：03、12、21、30<br> 
	4合：04、13、22、31<br> 
	5合：05、14、23、32<br> 
	6合：06、15、24、33<br> 
	7合：07、16、25、34<br> 
	8合：08、17、26、35<br> 
	9合：09、18、27、36<br> 
	10合：19、28 <br>
	11合：29。<br>
</div>
	
</div>
<#include "../bottom_common.ftl"/>
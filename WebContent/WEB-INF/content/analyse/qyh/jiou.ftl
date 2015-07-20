<head>
	<title>${lotteryType.lotteryName}奇偶遗漏</title>
	<meta name="decorator" content="analyse" />
	<link href="<@c.url value= "/pages/css/assets/css/style.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-latest.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.tablesorter.min.js"/>"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){ 
       		 $("#missTable").tablesorter(); 
			 $("#MISS_EL").css("display","none");						
    	}); 
    	
    	function chgPeriodNumber(count){
    		$("#count").val(count);
    		var weixuan_menu = document.getElementById("jiou_menu");
			weixuan_menu.submit();
			return false;
    	}
	</script>
	
</head>
<#assign menu="jiou" />

<div class="main980">
        <#include "top.ftl"/>
</div>
<div align="center">
	<table id="missTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1"> 
		<thead> 
			<tr align="center"> 
		    	<th>类型</th>
				<th>出现次数</th>
				<th>出现机率</th>
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
			<#if wz==3>
			<tr align="center">
				<#if minOddCount2??&&minOddCount2?size gt 0>
						<td>两小奇</td>		
						<td>${minOddCount2[0]}</td>
						<td>#{(minOddCount2[1]!0);M2}</td>
						<td>${minOddCount2[2]}</td>
						<td>${minOddCount2[3]}</td>
						<td>#{(minOddCount2[4]!0);M2}</td>
						<td>#{(minOddCount2[5]!0);M2}</td>
						<td>${minOddCount2[6]}</td>		
						<td>#{(minOddCount2[7]!0);M2}</td>	
						<td>#{(minOddCount2[8]!0);M2}</td>													
				</#if>
			</tr>	
			<tr align="center">
				<#if minEvenCount2??&&minEvenCount2?size gt 0>
						 <td>两小偶</td>
						<td>${minEvenCount2[0]}</td>
						<td>#{(minEvenCount2[1]!0);M2}</td>
						<td>${minEvenCount2[2]}</td>
						<td>${minEvenCount2[3]}</td>
						<td>#{(minEvenCount2[4]!0);M2}</td>
						<td>#{(minEvenCount2[5]!0);M2}</td>
						<td>${minEvenCount2[6]}</td>		
						<td>#{(minEvenCount2[7]!0);M2}</td>	
						<td>#{(minEvenCount2[8]!0);M2}</td>			
				</#if>
			</tr>
			<tr align="center">
				<#if maxOddCount2??&&maxOddCount2?size gt 0>
						<td>两大奇</td>
						<td>${maxOddCount2[0]}</td>
						<td>#{(maxOddCount2[1]!0);M2}</td>
						<td>${maxOddCount2[2]}</td>
						<td>${maxOddCount2[3]}</td>
						<td>#{(maxOddCount2[4]!0);M2}</td>
						<td>#{(maxOddCount2[5]!0);M2}</td>
						<td>${maxOddCount2[6]}</td>		
						<td>#{(maxOddCount2[7]!0);M2}</td>	
						<td>#{(maxOddCount2[8]!0);M2}</td>	
				
				</#if>	
			</tr>
			<tr align="center">
				<#if maxEvenCount2??&&maxEvenCount2?size gt 0>
						<td>两大偶</td>
						<td>${maxEvenCount2[0]}</td>
						<td>#{(maxEvenCount2[1]!0);M2}</td>
						<td>${maxEvenCount2[2]}</td>
						<td>${maxEvenCount2[3]}</td>
						<td>#{(maxEvenCount2[4]!0);M2}</td>
						<td>#{(maxEvenCount2[5]!0);M2}</td>
						<td>${maxEvenCount2[6]}</td>		
						<td>#{(maxEvenCount2[7]!0);M2}</td>	
						<td>#{(maxEvenCount2[8]!0);M2}</td>	
				</#if>	
			</tr>
			
			<#elseif wz==4>
				<tr align="center">
					<#if minOddCount3??&&minOddCount3?size gt 0>
							<td>三小奇</td>		
						<td>${minOddCount3[0]}</td>
						<td>#{(minOddCount3[1]!0);M2}</td>
						<td>${minOddCount3[2]}</td>
						<td>${minOddCount3[3]}</td>
						<td>#{(minOddCount3[4]!0);M2}</td>
						<td>#{(minOddCount3[5]!0);M2}</td>
						<td>${minOddCount3[6]}</td>		
						<td>#{(minOddCount3[7]!0);M2}</td>	
						<td>#{(minOddCount3[8]!0);M2}</td>	
					</#if>
				</tr>	
				<tr align="center">
					<#if minEvenCount3??&&minEvenCount3?size gt 0>
							 <td>三小偶</td>
						<td>${minEvenCount3[0]}</td>
						<td>#{(minEvenCount3[1]!0);M2}</td>
						<td>${minEvenCount3[2]}</td>
						<td>${minEvenCount3[3]}</td>
						<td>#{(minEvenCount3[4]!0);M2}</td>
						<td>#{(minEvenCount3[5]!0);M2}</td>
						<td>${minEvenCount3[6]}</td>		
						<td>#{(minEvenCount3[7]!0);M2}</td>	
						<td>#{(minEvenCount3[8]!0);M2}</td>	
						</#if>
				</tr>
				<tr align="center">
					<#if maxOddCount3??&&maxOddCount3?size gt 0>
							<td>三大奇</td>
						<td>${maxOddCount3[0]}</td>
						<td>#{(maxOddCount3[1]!0);M2}</td>
						<td>${maxOddCount3[2]}</td>
						<td>${maxOddCount3[3]}</td>
						<td>#{(maxOddCount3[4]!0);M2}</td>
						<td>#{(maxOddCount3[5]!0);M2}</td>
						<td>${maxOddCount3[6]}</td>		
						<td>#{(maxOddCount3[7]!0);M2}</td>	
						<td>#{(maxOddCount3[8]!0);M2}</td>	
					
					</#if>	
				</tr>
				<tr align="center">
					<#if maxEvenCount3??&&maxEvenCount3?size gt 0>
							<td>三大偶</td>
						<td>${maxEvenCount3[0]}</td>
						<td>#{(maxEvenCount3[1]!0);M2}</td>
						<td>${maxEvenCount3[2]}</td>
						<td>${maxEvenCount3[3]}</td>
						<td>#{(maxEvenCount3[4]!0);M2}</td>
						<td>#{(maxEvenCount3[5]!0);M2}</td>
						<td>${maxEvenCount3[6]}</td>		
						<td>#{(maxEvenCount3[7]!0);M2}</td>	
						<td>#{(maxEvenCount3[8]!0);M2}</td>	
					</#if>	
				</tr>
				
			<#elseif wz==5>
				<tr align="center">
					<#if minRandom3Odd??&&minRandom3Odd?size gt 0>
							<td>任三小奇</td>		
						<td>${minRandom3Odd[0]}</td>
						<td>#{(minRandom3Odd[1]!0);M2}</td>
						<td>${minRandom3Odd[2]}</td>
						<td>${minRandom3Odd[3]}</td>
						<td>#{(minRandom3Odd[4]!0);M2}</td>
						<td>#{(minRandom3Odd[5]!0);M2}</td>
						<td>${minRandom3Odd[6]}</td>		
						<td>#{(minRandom3Odd[7]!0);M2}</td>	
						<td>#{(minRandom3Odd[8]!0);M2}</td>	
					</#if>
				</tr>	
				<tr align="center">
					<#if minRandom3Even??&&minRandom3Even?size gt 0>
							 <td>任三小偶</td>
						<td>${minRandom3Even[0]}</td>
						<td>#{(minRandom3Even[1]!0);M2}</td>
						<td>${minRandom3Even[2]}</td>
						<td>${minRandom3Even[3]}</td>
						<td>#{(minRandom3Even[4]!0);M2}</td>
						<td>#{(minRandom3Even[5]!0);M2}</td>
						<td>${minRandom3Even[6]}</td>		
						<td>#{(minRandom3Even[7]!0);M2}</td>	
						<td>#{(minRandom3Even[8]!0);M2}</td>	
					</#if>
				</tr>
				<tr align="center">
					<#if maxRandom3Odd??&&maxRandom3Odd?size gt 0>
							<td>任三大奇</td>
						<td>${maxRandom3Odd[0]}</td>
						<td>#{(maxRandom3Odd[1]!0);M2}</td>
						<td>${maxRandom3Odd[2]}</td>
						<td>${maxRandom3Odd[3]}</td>
						<td>#{(maxRandom3Odd[4]!0);M2}</td>
						<td>#{(maxRandom3Odd[5]!0);M2}</td>
						<td>${maxRandom3Odd[6]}</td>		
						<td>#{(maxRandom3Odd[7]!0);M2}</td>	
						<td>#{(maxRandom3Odd[8]!0);M2}</td>		
					
					</#if>	
				</tr>
				<tr align="center">
					<#if maxRandom3Even??&&maxRandom3Even?size gt 0>
							<td>任三大偶</td>
						<td>${maxRandom3Even[0]}</td>
						<td>#{(maxRandom3Even[1]!0);M2}</td>
						<td>${maxRandom3Even[2]}</td>
						<td>${maxRandom3Even[3]}</td>
						<td>#{(maxRandom3Even[4]!0);M2}</td>
						<td>#{(maxRandom3Even[5]!0);M2}</td>
						<td>${maxRandom3Even[6]}</td>		
						<td>#{(maxRandom3Even[7]!0);M2}</td>	
						<td>#{(maxRandom3Even[8]!0);M2}</td>	
					</#if>	
				</tr>	
				
			</#if>
		</tbody> 
	</table> 
</div>
<#include "../bottom_common.ftl"/>
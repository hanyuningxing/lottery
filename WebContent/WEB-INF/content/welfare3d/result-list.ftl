<@override name="title">
	<title>中国福利彩票3D网上代购合买平台-福彩3D投注-网上购买3D - ${webapp.webName}</title>
</@override>
<@override name="list_table">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
		<thead>
			<tr>
	          	<td rowspan="2">期号</td>
	          	<td rowspan="2">开奖号码</td>
	          	<td rowspan="2">销售总额</td>
	          	<td colspan="2">直选</td>
	          	<td colspan="2">组三</td>
	          	<td colspan="2">组六</td>
	          	<td rowspan="2">开奖时间</td>
			</tr>
			<tr>
	          	<td>注数</td>
	          	<td>金额</td>
	          	<td>注数</td>
	          	<td>金额</td>
	          	<td>注数</td>
	          	<td>金额</td>
			</tr>
		</thead>
		<tbody>
	  		<#if pagination.result?? && pagination.result?size gt 0>
				<#list pagination.result as data>
	          		<tr class="row${(data_index+1)%2}"  onmouseout="this.className=''" onmouseover="this.className='trhover'">
			  			<td>${data.period.periodNumber!}</td>
	          			<td align="center">
	           				<#if data.periodData.result??>
	                			<#assign resultArr = data.periodData.resultFormat?split(",") />
	                			<ul class="kjball">
				                 	<li class="redballsingle">${resultArr[0]!}</li> 
				                 	<li class="redballsingle">${resultArr[1]!}</li> 
				                 	<li class="redballsingle">${resultArr[2]!}</li> 
				                </ul>
	               			</#if>
	          			</td>
	                  	<td>${data.periodData.totalSales!}元 </td>
			          	<td>${data.periodData.winUnit.winUnits!}</td>
			          	<td>${data.periodData.prize.unitPrize!}元 </td>
			          	<td>${data.periodData.winUnit.g3WinUnits!}</td>
			          	<td>${data.periodData.prize.g3UnitPrize}元 </td>
			          	<td>${data.periodData.winUnit.g6WinUnits!}</td>
			          	<td>${data.periodData.prize.g6UnitPrize!}元 </td>
			          	<td><#if data.period.prizeTime??>${data.period.prizeTime?string("yyyy-MM-dd")}</#if></td>
	           
	      		</#list>  	
	    	<#else>
				<tr class="row1">
					<td colspan="10">暂无记录.</td>
				</tr>
	  		</#if>								
      	</tbody>
   	</table>
	<div class="kong5"></div>
	<#import "/WEB-INF/macro/pagination.ftl" as b />
	<@b.page />
</@override>
<@extends name="/WEB-INF/content/common/number-result.ftl"/>
<@extends name="base.ftl"/>
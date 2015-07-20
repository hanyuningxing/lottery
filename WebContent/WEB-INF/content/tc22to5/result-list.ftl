<@override name="title">
	<title>体彩22选5网上购买投注、选号合买、投注计算、开奖结果查询 - ${webapp.webName}安全购彩平台</title>
</@override>
<@override name="list_table">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	  <thead>
		<tr align="center">
	      <td width="10%" nowrap="nowrap">期号</td>
	      <td width="25%" nowrap="nowrap">开奖号码</a></td>
	      <td width="20%" nowrap="nowrap">销售总额</td>
	      <td width="15%" nowrap="nowrap">一等奖</td>
	      <td width="10%" nowrap="nowrap">二等奖</td>
	      <td width="10%" nowrap="nowrap">三等奖</td>
	      <td width="25%" nowrap="nowrap">开奖时间</td>
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
	                	<#list 0..4 as r_index>
							<li class="redballsingle">${resultArr[r_index]!}</li> 
	                	</#list>
	                </ul>
				</#if>
	          </td>
	          <td><#if data.periodData.totalSales??>${data.periodData.totalSales}元</#if></td>
	          <td><#if data.periodData.prize.firstPrize??>${data.periodData.prize.firstPrize}元 </#if></td>
	          <td><#if data.periodData.prize.secondPrize??>${data.periodData.prize.secondPrize}元</#if></td>
	          <td><#if data.periodData.prize.thirdPrize??>${data.periodData.prize.thirdPrize}元</#if> </td>
	          <td><#if data.period.prizeTime??>${data.period.prizeTime?string("yyyy-MM-dd")}</#if></td>
			</tr>
		</#list>  	
	  <#else>
		<tr class="row1">
			<td colspan="7">暂无记录.</td>
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
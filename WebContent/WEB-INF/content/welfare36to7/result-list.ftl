<@override name="title">
	<title>网上购买，广东南粤好彩1网上投注、36选7选号 - ${webapp.webName}安全购彩平台</title>
</@override>
<@override name="list_table">
	<table width="98%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	  <thead>
		<tr align="center">
	      <td width="8%" nowrap="nowrap">期号</td>
	      <td width="50%" nowrap="nowrap">开奖号码</a></td>
	      <td width="10%" nowrap="nowrap">销售总额</td>
	      <td width="10%" nowrap="nowrap">一等奖</td>
	      <td width="10%" nowrap="nowrap">二等奖</td>
	      <td width="10%" nowrap="nowrap">三等奖</td>
	      <td width="18%" nowrap="nowrap">开奖时间</td>
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
	                 	<li class="blueballsingle">${resultArr[6]!}</li>
	                 	<li class="blueballsingle">${resultArr[7]!}</li>
	                 	<li class="blueballsingle">${resultArr[8]!}</li>
	                 	<li class="blueballsingle">${resultArr[9]!}</li>
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

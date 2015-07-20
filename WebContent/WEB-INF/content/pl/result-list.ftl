<#if playType==0>
		<title>中国体育彩票${lotteryType.lotteryName}网上代购合买平台-P3投注-网上购买${salesMode.modeName}投注- ${webapp.webName}</title>
	<#elseif playType==1>
		<title>中国体育彩票${lotteryType.lotteryName}网上代购合买平台-P5投注-网上购买${salesMode.modeName}投注- ${webapp.webName}</title>
	</#if>
	<@override name="list_table">
	 <table width="98%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
							  <thead>
							      <#if playType==0>
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
							        <#else>
								        <tr>
								          <td>期号</td>
								          <td>开奖号码</td>
								          <td>销售总额</td>
								          <td>排列五注数</td>
								          <td>排列五奖金</td>
								          <td>开奖时间</td>
								        </tr>
							      </#if>
							  </thead>
							  <tbody>
							  <#if pagination.result?? && pagination.result?size gt 0>
								<#list pagination.result as data>
							          <tr class="row${(data_index+1)%2}"  onmouseout="this.className=''" onmouseover="this.className='trhover'">
									  <td>${data.period.periodNumber!}</td>
							          <td align="center">
							            <#if data.periodData.result??>
							                <#assign resultArr = data.periodData.resultFormat?split(",")>
							                <ul class="kjball">
							                 <#if playType==0>
								                 <li class="redballsingle">${resultArr[0]!}</li> 
								                 <li class="redballsingle">${resultArr[1]!}</li> 
								                 <li class="redballsingle">${resultArr[2]!}</li> 
							                 <#else>
							                     <li class="redballsingle">${resultArr[0]!}</li> 
								                 <li class="redballsingle">${resultArr[1]!}</li> 
								                 <li class="redballsingle">${resultArr[2]!}</li> 
								                 <li class="redballsingle">${resultArr[3]!}</li>
								                 <li class="redballsingle">${resultArr[4]!}</li> 
							                 </#if>
							               </#if>
							          </td>
							          <#if playType==0>
							                  <td>${data.periodData.totalSales!}元 </td>
									          <td>${data.periodData.winUnit.p3WinUnits!}</td>
									          <td>${data.periodData.prize.p3UnitPrize!}元 </td>
									          <td>${data.periodData.winUnit.p3G3WinUnits!}</td>
									          <td>${data.periodData.prize.p3G3UnitPrize}元 </td>
									          <td>${data.periodData.winUnit.p3G6WinUnits!}</td>
									          <td>${data.periodData.prize.p3G6UnitPrize!}元 </td>
									          <td><#if data.period.prizeTime??>${data.period.prizeTime?string("yyyy-MM-dd")}</#if></td>
							            <#else>
							                 <td>${data.periodData.totalSales!}元 </td>
										     <td>${data.periodData.winUnit.p5WinUnits!}</td>
										     <td>${data.periodData.prize.p5UnitPrize!}元 </td>
										     <td><#if data.period.prizeTime??>${data.period.prizeTime?string("yyyy-MM-dd")}</#if></td>
							          </#if>
							      </#list>  	
							    <#else>
									<tr class="row1">
										<td colspan="<#if playType==0>10<#else>7</#if>">暂无记录.</td>
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




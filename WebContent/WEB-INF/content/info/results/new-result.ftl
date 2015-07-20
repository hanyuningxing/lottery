<#--最新开奖信息-->
<!--竞技彩 -->
<table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="978" border="1" >
       <#if sfzcPeriodData??&&sfzcPeriod??>
              <tr class="center_trw" align="center" >
		        <td width="80" height="25">胜负彩</td>
		        <td width="95" >${sfzcPeriod.periodNumber!}期 </td>
		        <td width="100" ><#if sfzcPeriod.prizeTime??>${sfzcPeriod.prizeTime?string("yyyy-MM-dd")}</#if></td>
		        <td width="320" style="line-height:30px;"><div class="floatleft"><span class="font14char boldchar graychar">${sfzcPeriodData.result!}</span></div>
		          <div class="floatrig" ><a href="javascript:void(0);" title="${sfzcPeriodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
		        <td width="120" ><#if sfzcPeriodData.totalSales_14??>${sfzcPeriodData.totalSales_14?string('###,###')}<#else>-</#if></td>
		        <td width="120"><#if sfzcPeriodData.prizePool_14??>${sfzcPeriodData.prizePool_14?string('###,###')}<#else>-</#if></td>
		        <td >不定期</td>
		        <td width="50" ><a href="${base}/${sfzcPeriod.lotteryType.key}/result!resultInfo.action?id=${sfzcPeriod.id!}&playType=SFZC14" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
		      </tr>
		      <tr class="center_trw" align="center" >
		        <td width="80" height="25">任选九场</td>
		        <td width="95" >${sfzcPeriod.periodNumber!}期 </td>
		        <td width="100" ><#if sfzcPeriod.prizeTime??>${sfzcPeriod.prizeTime?string("yyyy-MM-dd")}</#if></td>
		        <td width="320" style="line-height:30px;"><div class="floatleft"><span class="font14char boldchar graychar">${sfzcPeriodData.result!}</span></div>
		          <div class="floatrig" ><a href="javascript:void(0);" title="${sfzcPeriodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
		        <td width="120" ><#if sfzcPeriodData.totalSales??>${sfzcPeriodData.totalSales?string('###,###')}<#else>-</#if></td>
		        <td width="120"><#if sfzcPeriodData.prizePool??>${sfzcPeriodData.prizePool?string('###,###')}<#else>-</#if></td>
		        <td >不定期</td>
		        <td width="50" ><a href="${base}/${sfzcPeriod.lotteryType.key}/result!resultInfo.action?id=${sfzcPeriod.id!}&playType=SFZC9" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
		      </tr>
	        </#if>
	         <#if sczcPeriodData??&&sczcPeriod??>
	          <tr class="center_trw" align="center" >
		        <td width="80" height="25">四场进球</td>
		        <td width="95" >${sczcPeriod.periodNumber!}期 </td>
		        <td width="100" ><#if sczcPeriod.prizeTime??>${sczcPeriod.prizeTime?string("yyyy-MM-dd")}</#if></td>
		        <td width="320" style="line-height:30px;"><div class="floatleft"><span class="font14char boldchar graychar">${sczcPeriodData.result!}</span></div>
		          <div class="floatrig" ><a href="javascript:void(0);" title="${sczcPeriodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
		        <td width="120" ><#if sczcPeriodData.totalSales??>${sczcPeriodData.totalSales?string('###,###')}<#else>-</#if></td>
		        <td width="120"><#if sczcPeriodData.prizePool??>${sczcPeriodData.prizePool?string('###,###')}<#else>-</#if></td>
		        <td >不定期</td>
		        <td width="50" ><a href="${base}/${sczcPeriod.lotteryType.key}/result!resultInfo.action?id=${sczcPeriod.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
		      </tr>
	        </#if>
	        <#if lczcPeriodData??&&lczcPeriod??>
              <tr class="center_trw" align="center" >
		        <td width="80" height="25">六场半全</td>
		        <td width="95" >${lczcPeriod.periodNumber!}期 </td>
		        <td width="100" ><#if lczcPeriod.prizeTime??>${lczcPeriod.prizeTime?string("yyyy-MM-dd")}</#if></td>
		        <td width="320" style="line-height:30px;"><div class="floatleft"><span class="font14char boldchar graychar">${lczcPeriodData.result!}</span></div>
		          <div class="floatrig" ><a href="javascript:void(0);" title="${lczcPeriodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
		        <td width="120" ><#if lczcPeriodData.totalSales??>${lczcPeriodData.totalSales?string('###,###')}<#else>-</#if></td>
		        <td width="120"><#if lczcPeriodData.prizePool??>${lczcPeriodData.prizePool?string('###,###')}<#else>-</#if></td>
		        <td >不定期</td>
		        <td width="50" ><a href="${base}/${lczcPeriod.lotteryType.key}/result!resultInfo.action?id=${lczcPeriod.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
		      </tr>
	        </#if>
    </table>
    <!--竞技彩 end -->
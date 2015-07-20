<head>
	<title>七星彩开奖公告、号码详情_最新开奖_历史开奖 - ${webapp.webName}安全购彩平台</title>
	<meta name="decorator" content="result" />
</head>
<div class="main">
  <#include "../common/result-top.ftl" />
  <!--顶导航结束-->
  <div class="kong10"></div>
  <div class="kj_kb1">
    <div class="kj_t001">${lotteryType.lotteryName!}彩票最新开奖公告</div>
    <div class="kj_datewz bottomb1">
      <div class="floatleft top5px"><img src="${base}/pages/images/kj_date.gif" align="absmiddle" />&nbsp;&nbsp;今天：${dateFormat}</div>
      <form action="<@c.url value="/${lotteryType.key}/result!resultInfo.action" />" method="get">
      <div class="floatrig">历史期号：
        <label></label>
        <label>
	        <select name="id" size="1" onchange="this.form.submit();">
	          <#if periods??&&periods?size gt 0>
	                <#list periods as data>
	                    <option value="${data.id}" <#if data.id==period.id>selected</#if>>${data.periodNumber}</option>
	                </#list>
	          </#if>
	        </select>
        </label>
      </div>
      </form>
    </div>
    <div class="kj_dtwz">
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;" bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938" border="1" align="center">
        <tr class="center_trgray" align="center" >
          <td height="30" colspan="2" class="font14char boldchar"><div class="kj_jtitle"> ${lotteryType.lotteryName!}最新开奖公告  第<span class="redc">${period.periodNumber!}</span>期</div></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">开奖日期：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if> </td>
          <td >滚入下期奖金： <span class="redc"> <#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if> 元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">兑奖截止日期：<#if period.endPrizeTime??>${period.endPrizeTime?string("yyyy-MM-dd")}</#if> </td>
          <td >本期销量： <span class="redc"><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</span></td>
        </tr>
      </table>
      <div class="kj_number"> <span class="floatleft" style="padding-left:250px;">开奖号码：</span>
        <ul class="kjballn">
          <li class="ballrsingle">${periodData.rsultArr[0]}</li>
          <li class="ballrsingle">${periodData.rsultArr[1]}</li>
          <li class="ballrsingle">${periodData.rsultArr[2]}</li>
          <li class="ballrsingle">${periodData.rsultArr[3]}</li>
          <li class="ballrsingle">${periodData.rsultArr[4]}</li>
          <li class="ballrsingle">${periodData.rsultArr[5]}</li>
          <li class="ballrsingle">${periodData.rsultArr[6]}</li>
        </ul>
        <span style="display">
        <span class="floatleft" style="padding-left:20px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></span> </div>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trgray font14char boldchar" align="center"height="30"  >
          <td width="310" height="30">奖项</td>
          <td width="310" > 中奖注数 </td>
          <td  >每注奖金</td>
        </tr>
        <#if periodData.winUnit??&&periodData.prize??>
        <tr class="center_trw" align="center" >
          <td height="28">一等奖 </td>
          <td ><#if periodData.winUnit.firstWinUnits??>${periodData.winUnit.firstWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.firstPrize??>${periodData.prize.firstPrize?string('###,###')}</#if> 元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">二等奖</td>
          <td ><#if periodData.winUnit.secondWinUnits??>${periodData.winUnit.secondWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.secondPrize??>${periodData.prize.secondPrize?string('###,###')}</#if>元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">三等奖</td>
          <td ><#if periodData.winUnit.thirdWinUnits??>${periodData.winUnit.thirdWinUnits}</#if> </td>
          <td ><span class="redc"><#if periodData.prize.thirdPrize??>${periodData.prize.thirdPrize?string('###,###')}</#if> 元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">四等奖</td>
          <td ><#if periodData.winUnit.fourthWinUnits??>${periodData.winUnit.fourthWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.fourthPrize??>${periodData.prize.fourthPrize?string('###,###')}</#if>元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">五等奖</td>
          <td ><#if periodData.winUnit.fifthWinUnits??>${periodData.winUnit.fifthWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.fifthPrize??>${periodData.prize.fifthPrize?string('###,###')}</#if> 元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">六等奖</td>
          <td ><#if periodData.winUnit.sixthWinUnits??>${periodData.winUnit.sixthWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.sixthPrize??>${periodData.prize.sixthPrize?string('###,###')}</#if> 元</span></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">七等奖</td>
          <td ><#if periodData.winUnit.sevenWinUnits??>${periodData.winUnit.sevenWinUnits}</#if></td>
          <td ><span class="redc"><#if periodData.prize.sevenPrize??>${periodData.prize.sevenPrize?string('###,###')}</#if> 元</span></td>
        </tr>
        </#if>
      </table>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trw" align="center" >
          <td height="88" ><a href="${base}/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&shareType=SELF"><img src="${base}/pages/images/kj_btbuy.gif" border="0" /></a> </td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="kj_buybg"><a href="${base}/${lotteryType.key}/analyse.action" class="graychar">${lotteryType.lotteryName!}数据图表</a> | <a href="${base}/info/rule/rule.action?lottery=SEVEN" class="graychar">${lotteryType.lotteryName!}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=SEVEN" class="graychar">${lotteryType.lotteryName!}中奖细则</a></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="graychar">互联网上最全、最快的开奖公告，第一时间为您提供准确、全面的彩票开奖信息，预祝您中大奖！</td>
        </tr>
      </table>
    </div>
  </div>
</div>

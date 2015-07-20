<head>
	<title>广东11选5开奖公告、号码详情_最新开奖_历史开奖 - ${webapp.webName}安全购彩平台</title>
	<meta name="Description" content="${webapp.webName}广东11选5网上投注频道：为广大彩民提供广东11选5的预测分析，广东11选5的购彩技巧和最新的开奖公告及实时的山东11选5走势图，是可以信赖的11选5网上投注安全购彩平台。" />
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
      <form action="<@c.url value="/${lotteryType.key}/scheme!result.action" />" method="get">
      <div class="floatrig">历史期号：
        <label></label>
        <label>
	        <select name="resultDate" size="1" onchange="this.form.submit();">
	          <#if resultDates??&&resultDates?size gt 0>
	                <#list resultDates as data>
	                    <option value="${data}" <#if data==resultDate>selected</#if>>${data}</option>
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
          <td height="30" colspan="2" class="font14char boldchar"><div class="kj_jtitle"> ${lotteryType.lotteryName!}最新开奖公告  <span class="redc">${resultDate!}</span></div></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="28">${ruleString}</td>
        </tr>
      </table>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trgray font14char boldchar" align="center"height="30"  >
          <td width="25%" height="30">期号</td>
          <td width="25%" > 开奖时间</td>
          <td width="25%"  > 开奖号码</td>
          <td width="25%"  >复制结果</td>
        </tr>
        <#if periods??&&periods?size gt 0>
	         <#list periods as data>
	                <tr class="center_trw" align="center" >
			          <td height="28">${data.periodNumber!}期</td>
			          <td ><#if data.prizeTime??>${data.prizeTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			          <td class="font14char">${data.results}</td>
			          <td ><a href="#" title="${data.results}" onclick="copyResult(this);return false;">复制号码</a></td>
			        </tr>
	         </#list>
	    </#if>
      </table>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trw" align="center" >
          <td height="88" ><a href="${base}/${lotteryType.key}/scheme.action"><img src="${base}/pages/images/kj_btbuy.gif" border="0" /></a> </td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="kj_buybg"><a href="${base}/${lotteryType.key}/analyse.action" class="graychar">${lotteryType.lotteryName}数据图表</a> | <a href="${base}/info/rule/rule.action?lottery=GDEL11TO5" class="graychar">${lotteryType.lotteryName}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=GBEL11TO5" class="graychar">${lotteryType.lotteryName}中奖细则</a></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="graychar">互联网上最全、最快的开奖公告，第一时间为您提供准确、全面的彩票开奖信息，预祝您中大奖！</td>
        </tr>
      </table>
    </div>
  </div>
</div>

<head>
	<title>时时彩开奖公告、号码详情_最新开奖_历史开奖 - ${webapp.webName}安全购彩平台</title>
	<meta name="Keywords" content="彩票投注,时时彩购买,重庆时时彩,时时彩，时时彩预测，时时彩技巧，时时彩分析推荐" />
	<meta name="Description" content="${webapp.webName}提供重庆时时彩、时时彩开奖、走势图、预测分析以及网上购买服务，重庆时时彩是一种在线即开型彩票玩法，属于基诺型彩票，由重庆市福利彩票发行管理中心负责承销，安全诚信请放心购买。" />
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
          <td height="35" class="kj_buybg"><a href="${base}/${lotteryType.key}/analyse.action" class="graychar">${lotteryType.lotteryName}数据图表</a> | <a href="${base}/info/rule/rule.action?lottery=SSC" class="graychar">${lotteryType.lotteryName}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=SSC" class="graychar">${lotteryType.lotteryName}中奖细则</a></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="graychar">互联网上最全、最快的开奖公告，第一时间为您提供准确、全面的彩票开奖信息，预祝您中大奖！</td>
        </tr>
      </table>
    </div>
  </div>
</div>

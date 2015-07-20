<#assign lotteryName=lotteryType.lotteryName!"" />
<head>
	<title>四场足彩开奖公告、号码详情_最新开奖_历史开奖 - ${webapp.webName}安全购彩平台</title>
	<meta name="decorator" content="result" />
</head>
<div class="main">
  <!--顶导航结束-->
  <div class="kong10"></div>
  <div class="kj_kb1">
    <div class="kj_t001">${lotteryName!}彩票最新开奖公告</div>
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
      <div class="kj_number">
        <span class="floatleft" style="padding-left:250px;">开奖号码：<span class="rebchar font14char boldchar">${periodData.rsultSpiltString!}</span></span>
       </div>
        <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="trhover font14char boldchar" align="center" >
          <td width="80" height="22">场次</td>
          <td > 联赛</td>
          <td width="100"  > 时间</td>
          <td width="200"  > 主队 </td>
          <td width="200"  >客队 </td>
          <td  >比分</td>
          <td  >彩果</td>
        </tr>
        <#if matchArr??>
  			<#list matchArr as data>
  			<tr class="center_trw" align="center" >
	          <td height="20">${data.lineId + 1}&nbsp;</td>
	          <td >${data.gameName!} </td>
	          <td ><#if data.matchTime??>${data.matchTime?string("MM-dd HH:mm")}</#if>&nbsp;  </td>
	          <td >${data.homeTeamName!} </td>
	          <td >${data.guestTeamName!}</td>
	          <td >${data.homeScore!}-${data.guestScore!} </td>
	          <td class="redboldchar" >${data.homeResult!}-${data.guestResult!}</td>
	        </tr>
		  </#list>
		</#if>
      </table>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trgray font14char boldchar" align="center"height="30"  >
          <td width="310" height="30">奖项</td>
          <td width="310" > 中奖注数 </td>
          <td  >每注奖金</td>
        </tr>
         <#if periodData??>
		        <tr class="center_trw" align="center" >
		          <td height="28">一等奖</td>
		          <td > <#if periodData.firstWinUnits??>${periodData.firstWinUnits}<#else>-</#if></td>
		          <td class="redc" ><#if periodData.firstPrize??>${periodData.firstPrize?string('###,###')}元<#else>-</#if> </td>
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
          <td height="35" class="kj_buybg"><a href="javascript:void(0);" class="graychar">${lotteryName!}数据图表</a> | <a href="${base}/info/rule/rule.action?lottery=SCZC" class="graychar">${lotteryName!}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=SCZC" class="graychar">${lotteryName!}中奖细则</a></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="graychar">互联网上最全、最快的开奖公告，第一时间为您提供准确、全面的彩票开奖信息，预祝您中大奖！</td>
        </tr>
      </table>
    </div>
  </div>
</div>

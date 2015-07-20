<#if playType==0>
    <#assign lotteryName="排列三" />
    <#elseif playType==1>
    <#assign lotteryName="排列五" />
</#if>
<head>
	<title>${lotteryName}开奖公告、号码详情_最新开奖_历史开奖 - ${webapp.webName}安全购彩平台</title>
	<meta name="decorator" content="result" />
</head>
<div class="main"> 
  <#include "../common/result-top.ftl" />
  <!--顶导航结束-->
  <div class="kong10"></div>
  <div class="kj_kb1">
    <div class="kj_t001">${lotteryName!}彩票最新开奖公告</div>
    <div class="kj_datewz bottomb1">
      <div class="floatleft top5px"><img src="${base}/pages/images/kj_date.gif" align="absmiddle" />&nbsp;&nbsp;今天：${dateFormat}</div>
      <form action="<@c.url value="/${lotteryType.key}/result!resultInfo.action" />" method="get">
      <input type="hidden" name="playType" value="${playType}">
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
          <td height="30" colspan="3" class="font14char boldchar"><div class="kj_jtitle"> ${lotteryName!}最新开奖公告  第<span class="redc">${period.periodNumber!}</span>期</div></td>
        </tr>
        <tr class="center_trw" align="center" >
          <td width="310" height="28">开奖日期：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if> </td>
          <td width="310" >兑奖截止日期：<#if period.endPrizeTime??>${period.endPrizeTime?string("yyyy-MM-dd")}</#if> </td>
          <td >本期销量： <span class="redc"><#if playType==0><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if><#elseif playType==1><#if periodData.p5TotalSales??>${periodData.p5TotalSales?string('###,###')}</#if></#if> 元</span></td>
      </table>
       <div class="kj_number"> <span class="floatleft" style="padding-left:250px;">开奖号码：</span>
        <ul class="kjballn">
         <#if playType==0>
            <li class="redballsingle">${periodData.rsultArr[0]}</li>
            <li class="redballsingle">${periodData.rsultArr[1]}</li>
            <li class="redballsingle">${periodData.rsultArr[2]}</li>
          <#elseif playType==1>
            <li class="redballsingle">${periodData.rsultArr[0]}</li>
            <li class="redballsingle">${periodData.rsultArr[1]}</li>
            <li class="redballsingle">${periodData.rsultArr[2]}</li>
            <li class="redballsingle">${periodData.rsultArr[3]}</li>
            <li class="redballsingle">${periodData.rsultArr[4]}</li>
          </#if>
        </ul>
        <span style="display">
        <span class="floatleft" style="padding-left:20px;"><a href="javascript:void(0);" title="<#if playType==0>${periodData.rsultArr[0]},${periodData.rsultArr[1]},${periodData.rsultArr[2]}<#elseif playType==1>${periodData.result}</#if>" onclick="copyResult(this);return false;">复制号码</a></span> </div>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trgray font14char boldchar" align="center"height="30"  >
          <td width="310" height="30">奖项</td>
          <td width="310" > 中奖注数 </td>
          <td  >每注奖金</td>
        </tr>
         <#if playType==0>
            <#if periodData.winUnit??&&periodData.prize??>
                    <tr class="center_trw" align="center" >
				        <td>直选</td>
				        <td><#if periodData.winUnit.p3WinUnits??>${periodData.winUnit.p3WinUnits}</#if></td>
				        <td  class=" redc"><#if periodData.prize.p3UnitPrize??>${periodData.prize.p3UnitPrize}</#if>元</td>
				    </tr>
				
				    <tr class="center_trw" align="center" >
				        <td>组三</td>
				        <td><#if periodData.winUnit.p3G3WinUnits??>${periodData.winUnit.p3G3WinUnits}</#if></td>
				        <td  class=" redc"><#if periodData.prize.p3G3UnitPrize??>${periodData.prize.p3G3UnitPrize}</#if> 元</td>
				    </tr>
				    <tr class="center_trw" align="center" >
				        <td>组六</td>
				        <td><#if periodData.winUnit.p3G6WinUnits??>${periodData.winUnit.p3G6WinUnits}</#if></td>
				        <td class=" redc"><#if periodData.prize.p3G6UnitPrize??>${periodData.prize.p3G6UnitPrize}</#if> 元</td>
				    </tr>
           </#if>
          <#elseif playType==1>
             <#if periodData.winUnit??&&periodData.prize??>
                    <tr class="center_trw" align="center" >
				        <td>直选</td>
				        <td><#if periodData.winUnit.p5WinUnits??>${periodData.winUnit.p5WinUnits}</#if></td>
				        <td  class=" redc"><#if periodData.prize.p5UnitPrize??>${periodData.prize.p5UnitPrize}</#if>元</td>
				    </tr>
				
            </#if>
          </#if>
         
      </table>
      <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#dcdcdc" cellspacing="0" cellpadding="1" width="938"  
  border="1" align="center">
        <tr class="center_trw" align="center" >
          <td height="88" ><a href="${base}/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND&shareType=SELF&playType=${playType}"><img src="${base}/pages/images/kj_btbuy.gif" border="0" /></a> </td>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="kj_buybg">
          <#if playType==0>
               <a href="${base}/${lotteryType.key}3/analyse.action" class="graychar">${lotteryName}数据图表</a>|<a href="${base}/info/rule/rule.action?lottery=PL&type=${playType}" class="graychar">${lotteryName}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=PL&type=${playType}" class="graychar">${lotteryName}中奖细则</a></td>        
          <#elseif playType==1>
          <a href="${base}/${lotteryType.key}5/analyse.action" class="graychar">${lotteryName}数据图表</a>|<a href="${base}/info/rule/rule.action?lottery=PL&type=${playType}" class="graychar">${lotteryName}玩法介绍</a> | <a href="${base}/info/rule/rule.action?lottery=PL&type=${playType}" class="graychar">${lotteryName}中奖细则</a></td>
        	</#if>
        </tr>
        <tr class="center_trw" align="center" >
          <td height="35" class="graychar">互联网上最全、最快的开奖公告，第一时间为您提供准确、全面的彩票开奖信息，预祝您中大奖！</td>
        </tr>
      </table>
    </div>
  </div>
</div>

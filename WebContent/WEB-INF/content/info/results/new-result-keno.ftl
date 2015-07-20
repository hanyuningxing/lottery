<#if newResult??>
<tr class="center_trw" align="center" >
        <td height="25" >${newResult.lotteryType.lotteryName!}</td>
        <td >${newResult.periodNumber!}期 </td>
        <td ><#if newResult.prizeTime??>${newResult.prizeTime?string("yyyy-MM-dd HH:mm")}</#if></td>
        <td ><div class="floatleft">
            <ul class="kjballn">
              <#if newResult.lotteryType=='EL11TO5'||newResult.lotteryType=='SDEL11TO5'||newResult.lotteryType=='GDEL11TO5'||newResult.lotteryType=='QYH'||newResult.lotteryType=='SSC'>
	              <li class="ballrsingle">${newResult.rsultArr[0]}</li>
	              <li class="ballrsingle">${newResult.rsultArr[1]}</li>
	              <li class="ballrsingle">${newResult.rsultArr[2]}</li>
	              <li class="ballrsingle">${newResult.rsultArr[3]}</li>
	              <li class="ballrsingle">${newResult.rsultArr[4]}</li>
             </#if>
            </ul>
          </div>
          <div class="floatrig" style="line-height:30px;"><a href="#" title="${newResult.results}" onclick="copyResult(this);return false;">复制号码</a></div></td>
        <td >每${periodMinute}分钟</td>
        <td ><a href="${base}/${newResult.lotteryType.key!}/scheme!result.action" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
</tr>
</#if>
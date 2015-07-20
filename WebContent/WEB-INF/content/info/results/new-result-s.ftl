<#--最新开奖信息-->
 <!--数字彩 -->
       <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="978" border="1" >
	               <#if ssqPeriodData??&&ssqPeriod??>
                    <#assign period=ssqPeriod />
                    <#assign periodData=ssqPeriodData />
                        <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[5]}</li>
					              <li class="ballbsingle">${periodData.rsultArr[6]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120"><span class="redc"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if>元</span></td>
					        <td >二 四 日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				    <#if dltPeriodData??&&dltPeriod??>
				        <#assign period=dltPeriod />
                        <#assign periodData=dltPeriodData />
			            <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					              <li class="ballbsingle">${periodData.rsultArr[5]}</li>
					              <li class="ballbsingle">${periodData.rsultArr[6]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120"><span class="redc"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if>元</span></td>
					        <td >一 三 六</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				  
	               <#if welfare36to7PeriodData??&&welfare36to7Period??>
	                    <#assign period=welfare36to7Period />
                        <#assign periodData=welfare36to7PeriodData />
                        <#assign resultArr = periodData.resultFormat?split(",") />
                        
			            <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					            
					            	<li class="blueballsingle">${resultArr[6]!}</li>
				                 	<li class="blueballsingle">${resultArr[7]!}</li>
				                 	<li class="blueballsingle">${resultArr[8]!}</li>
				                 	<li class="blueballsingle">${resultArr[9]!}</li>
					           
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120"><span class="redc"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if>元</span></td>
					        <td >每日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
	               <#if plPeriodData??&&plPeriod??>
	                    <#assign period=plPeriod />
                        <#assign periodData=plPeriodData />
                          <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >排列三</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="330" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.rsultArr[0]},${periodData.rsultArr[1]},${periodData.rsultArr[2]}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >每日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}&playType=0" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
                          <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >排列五</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.p5TotalSales??>${periodData.p5TotalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >每日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}&playType=1" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				    <#if welfare3dPeriodData??&&welfare3dPeriod??>
	                    <#assign period=welfare3dPeriod />
                        <#assign periodData=welfare3dPeriodData />
                        <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >每日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				    
				    <#if sevenPeriodData??&&sevenPeriod??>
	                    <#assign period=sevenPeriod />
                        <#assign periodData=sevenPeriodData />
                        <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[5]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[6]}</li>
					              <li class="ballbsingle">${periodData.rsultArr[7]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >一 三 五</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				    
				    <#if tc22to5PeriodData??&&tc22to5Period??>
	                    <#assign period=tc22to5Period />
                        <#assign periodData=tc22to5PeriodData />
                        <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >每日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
				    <#if sevenstarPeriodData??&&sevenstarPeriod??>
	                    <#assign period=sevenstarPeriod />
                        <#assign periodData=sevenstarPeriodData />
                        <tr class="center_trw" align="center"  >
					        <td width="80" height="25" >${period.lotteryType.lotteryName!}</td>
					        <td width="95" >${period.periodNumber!}期 </td>
					        <td width="100" ><#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></td>
					        <td width="320" ><div class="floatleft">
					            <ul class="kjballn">
					              <li class="ballrsingle">${periodData.rsultArr[0]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[1]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[2]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[3]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[4]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[5]}</li>
					              <li class="ballrsingle">${periodData.rsultArr[6]}</li>
					            </ul>
					          </div>
					          <div class="floatrig" style="line-height:30px;"><a href="#" title="${periodData.result}" onclick="copyResult(this);return false;">复制号码</a></div></td>
					        <td width="120" ><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元</td>
					        <td width="120">&nbsp;</td>
					        <td >二 五 日</td>
					        <td width="50" ><a href="${base}/${period.lotteryType.key}/result!resultInfo.action?id=${period.id!}" target="_blank"><img src="${base}/pages/images/kj_btdetail.gif" border="0" /></a></td>
					      </tr>
				    </#if>
  
    </table>
  <!--数字彩 end -->
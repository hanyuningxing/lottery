<#--最新开奖信息-->
 <!--数字彩 -->
       <div class=" cleanboth cyinchang" id="num_result_div">
	               <#if ssqPeriodData??&&ssqPeriod??>
                    <#assign period=ssqPeriod />
                    <#assign periodData=ssqPeriodData />
			            <div id="Exten_0"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[3]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[4]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[5]}</span></li>
						          <li><span class="ballb">${periodData.rsultArr[6]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20"> 一等奖：<#if periodData.winUnit.firstWinUnits??>${periodData.winUnit.firstWinUnits}</#if>注 <#if periodData.prize.firstPrize??>${periodData.prize.firstPrize?string('###,###')}</#if> 元<br />
								          二等奖：<#if periodData.winUnit.secondWinUnits??>${periodData.winUnit.secondWinUnits}</#if>注 <#if periodData.prize.secondPrize??>${periodData.prize.secondPrize?string('###,###')}</#if>元<br />
								          奖池滚存：<span class="redboldchar"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if></span>元
								    <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank">立即投注</a>
							    </div>
						     </div>
			          </div>
				      <div class="r_two" _name="lottery_result" id="Base_0" style="display:block" onmouseover="ToggleKjMes(0)">
				                 <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					                 <span class="fontred">${periodData.rsultArr[3]}</span>
					                 <span class="fontred">${periodData.rsultArr[4]}</span>
					                 <span class="fontred">${periodData.rsultArr[5]}</span>
					                 <span class="fontblue">${periodData.rsultArr[6]}</span>
				                 </div>
				      </div>
				    </#if>
				    <#if dltPeriodData??&&dltPeriod??>
				        <#assign period=dltPeriod />
                        <#assign periodData=dltPeriodData />
			            <div id="Exten_1"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[3]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[4]}</span></li>
						          <li><span class="ballb">${periodData.rsultArr[5]}</span></li>
						          <li><span class="ballb">${periodData.rsultArr[6]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20"> 一等奖：<#if periodData.winUnit.firstWinUnits??>${periodData.winUnit.firstWinUnits}</#if>注 <#if periodData.prize.firstPrize??>${periodData.prize.firstPrize?string('###,###')}</#if> 元<br />
								          二等奖：<#if periodData.winUnit.secondWinUnits??>${periodData.winUnit.secondWinUnits}</#if>注 <#if periodData.prize.secondPrize??>${periodData.prize.secondPrize?string('###,###')}</#if>元<br />
								          奖池滚存：<span class="redboldchar"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if></span>元
								    <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank">立即投注</a>
							    </div>
						     </div>
			          </div>
				      <div class="r_two" _name="lottery_result" id="Base_1" style="display:block" onmouseover="ToggleKjMes(1)">
				                 <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					                 <span class="fontred">${periodData.rsultArr[3]}</span>
					                 <span class="fontred">${periodData.rsultArr[4]}</span>
					                 <span class="fontblue">${periodData.rsultArr[5]}</span>
					                 <span class="fontblue">${periodData.rsultArr[6]}</span>
					            </div>
				      </div>
				    </#if>
				  
	               <#if wel36PeriodData??&&wel36Period??>
	                    <#assign period=wel36Period />
                        <#assign periodData=wel36PeriodData />
			            <div id="Exten_2"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[3]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[4]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[5]}</span></li>
						          <li><span class="ballb">${periodData.rsultArr[6]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20"> 一等奖：<#if periodData.winUnit.firstWinUnits??>${periodData.winUnit.firstWinUnits}</#if>注 <#if periodData.prize.firstPrize??>${periodData.prize.firstPrize?string('###,###')}</#if> 元<br />
								          二等奖：<#if periodData.winUnit.secondWinUnits??>${periodData.winUnit.secondWinUnits}</#if>注 <#if periodData.prize.secondPrize??>${periodData.prize.secondPrize?string('###,###')}</#if>元<br />
								          奖池滚存：<span class="redboldchar"><#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if></span>元
								    <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank">立即投注</a>
							    </div>
						     </div>
			           </div>
				       <div class="r_two" _name="lottery_result" id="Base_2" style="display:block" onmouseover="ToggleKjMes(2)">
				                 <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					                 <span class="fontred">${periodData.rsultArr[3]}</span>
					                 <span class="fontred">${periodData.rsultArr[4]}</span>
					                 <span class="fontred">${periodData.rsultArr[5]}</span>
					                 <span class="fontblue">${periodData.rsultArr[6]}</span>
					            </div>
				       </div>
				    </#if>
	               <#if plPeriodData??&&plPeriod??>
	                    <#assign period=plPeriod />
                        <#assign periodData=plPeriodData />
                        <div id="Exten_3"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=0" target="_blank"><span class="redboldchar">排列三</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20">
						                                直选：<#if periodData.winUnit.p3WinUnits??>${periodData.winUnit.p3WinUnits}</#if>注 <#if periodData.prize.p3UnitPrize??>${periodData.prize.p3UnitPrize?string('###,###')}</#if> 元<br />                   
						                                销售总额：<span class="redboldchar"><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if></span>元
								    <a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=0" target="_blank">立即投注</a>
							    </div>
						     </div>
			           </div>
				       <div class="r_two" _name="lottery_result" id="Base_3" style="display:block" onmouseover="ToggleKjMes(3)">
				                 <a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=0" target="_blank"><span class="redboldchar">排列三</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					            </div>
				       </div>
                       
                       <div id="Exten_4"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=1" target="_blank"><span class="redboldchar">排列五</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[3]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[4]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20">
						                              一等奖：<#if periodData.winUnit.p5WinUnits??>${periodData.winUnit.p5WinUnits}</#if>注 <#if periodData.prize.p5UnitPrize??>${periodData.prize.p5UnitPrize?string('###,###')}</#if> 元<br />                   
						                                销售总额：<span class="redboldchar"><#if periodData.p5TotalSales??>${periodData.p5TotalSales?string('###,###')}</#if></span>元
								    <a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=1" target="_blank">立即投注</a>
							    </div>
						     </div>
			           </div>
				       <div class="r_two" _name="lottery_result" id="Base_4" style="display:block" onmouseover="ToggleKjMes(4)">
				                 <a href="${base}/pl/scheme!editNew?salesMode=COMPOUND&shareType=SELF&playType=1" target="_blank"><span class="redboldchar">排列五</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					                 <span class="fontred">${periodData.rsultArr[3]}</span>
					                 <span class="fontred">${periodData.rsultArr[4]}</span>
					            </div>
				       </div>
				    </#if>
				    <#if welfare3dPeriodData??&&welfare3dPeriod??>
	                    <#assign period=welfare3dPeriod />
                        <#assign periodData=welfare3dPeriodData />
                        <div id="Exten_5"  _name="lottery_result_div" style="display:none">
				            <div  class="r_two">
				          		<a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                &nbsp;&nbsp;开奖：<#if period.prizeTime??>${period.prizeTime?string("MM-dd HH:mm")}</#if></p>
				            </div>
				            <div class="balllistwz">
						        <ul class="balllist">
						          <li><span class="ballr">${periodData.rsultArr[0]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[1]}</span></li>
						          <li><span class="ballr">${periodData.rsultArr[2]}</span></li>
						        </ul>
						        <div class="cleanboth top5px lineh20">
						                                直选：<#if periodData.winUnit.winUnits??>${periodData.winUnit.winUnits}</#if>注 <#if periodData.prize.unitPrize??>${periodData.prize.unitPrize?string('###,###')}</#if> 元<br />                   
						                                销售总额：<span class="redboldchar"><#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if></span>元
								    <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank">立即投注</a>
							    </div>
						     </div>
			           </div>
				       <div class="r_two" _name="lottery_result" id="Base_5" style="display:block" onmouseover="ToggleKjMes(5)">
				                 <a href="${base}/${period.lotteryType.key!}/scheme!editNew?salesMode=COMPOUND&shareType=SELF" target="_blank"><span class="redboldchar">${period.lotteryType.lotteryName!}</span></a> 第${period.periodNumber!}期
				                 <div class="result_font">
					                 <span class="fontred">${periodData.rsultArr[0]}</span>
					                 <span class="fontred">${periodData.rsultArr[1]}</span>
					                 <span class="fontred">${periodData.rsultArr[2]}</span>
					            </div>
				       </div>
				    </#if>
    </div>
  <!--数字彩 end -->
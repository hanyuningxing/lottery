<#--最新开奖信息-->
<!--竞技彩 -->
      <div class="cleanboth cyinchang">
            <#if sfzcPeriodData??&&sfzcPeriodData.result??>
              <div class="r_two"><span class="redboldchar"><a target="_blank" href="${base}/sfzc/scheme!editNew.action?salesMode=COMPOUND&playType=SFZC14">胜负彩</a>/<a target="_blank" href="${base}/sfzc/scheme!editNew.action?salesMode=COMPOUND&playType=SFZC9">任选九场</a></span> 第${sfzcPeriod.periodNumber!}期</div>
	          <div class="balllistwz">
		          <ul class="balllist">
		             ${sfzcPeriodData.rsultString!}
		          </ul>
		          <div class="cleanboth top5px lineh20"> 
				            一等奖： <#if sfzcPeriodData.firstWinUnits_14??>${sfzcPeriodData.firstWinUnits_14}<#else>-</#if>注 <#if sfzcPeriodData.firstPrize_14??>${sfzcPeriodData.firstPrize_14?string('###,###')}<#else>-</#if>元<br />
				            二等奖： <#if sfzcPeriodData.secondWinUnits_14??>${sfzcPeriodData.secondWinUnits_14}<#else>-</#if>注<#if sfzcPeriodData.secondPrize_14??>${sfzcPeriodData.secondPrize_14?string('###,###')}<#else>-</#if>元<br />
				            任九场： <#if sfzcPeriodData.firstWinUnits??>${sfzcPeriodData.firstWinUnits}<#else>-</#if>注 <#if sfzcPeriodData.firstPrize??>${sfzcPeriodData.firstPrize?string('###,###')}<#else>-</#if> 元<br />
				            奖池滚存：<span class="redboldchar"><#if sfzcPeriodData.prizePool??>${sfzcPeriodData.prizePool?string('###,###')}<#else>-</#if></span>元 
				 </div>
		     </div>
	        </#if>
	         <#if sczcPeriodData??&&sczcPeriodData.result??>
	          <div class="r_two"><span class="redboldchar"><a target="_blank" href="${base}/sczc/scheme!editNew.action?salesMode=COMPOUND">四场入球</a></span> 第${sczcPeriodData.periodNumber!}期</div>
	          <div class="balllistwz">
		          <ul class="balllist">
		             ${sczcPeriodData.rsultString!}
		          </ul>
		          <div class="cleanboth top5px lineh20"> 
				            一等奖： <#if sczcPeriodData.firstWinUnits??>${sczcPeriodData.firstWinUnits}<#else>-</#if>注 <#if sczcPeriodData.firstPrize??>${sczcPeriodData.firstPrize?string('###,###')}<#else>-</#if> 元<br />
				            奖池滚存：<span class="redboldchar"><#if sczcPeriodData.prizePool??>${sczcPeriodData.prizePool?string('###,###')}<#else>-</#if></span>元 
				 </div>
		     </div>
	        </#if>
	        <#if lczcPeriodData??&&lczcPeriodData.result??>
              <div class="r_two"><span class="redboldchar"><a target="_blank" href="${base}/lczc/scheme!editNew.action?salesMode=COMPOUND">六场半全场</a></span> 第${lczcPeriodData.periodNumber!}期</div>
	          <div class="balllistwz">
		          <ul class="balllist">
		             ${lczcPeriodData.rsultString!}
		          </ul>
		          <div class="cleanboth top5px lineh20"> 
				            一等奖： <#if lczcPeriodData.firstWinUnits??>${lczcPeriodData.firstWinUnits}<#else>-</#if>注 <#if lczcPeriodData.firstPrize??>${lczcPeriodData.firstPrize?string('###,###')}<#else>-</#if> 元<br />
				            奖池滚存：<span class="redboldchar"><#if lczcPeriodData.prizePool??>${lczcPeriodData.prizePool?string('###,###')}<#else>-</#if></span>元 
				 </div>
		     </div>
	        </#if>
      </div>
    </div>
    <!--竞技彩 end -->
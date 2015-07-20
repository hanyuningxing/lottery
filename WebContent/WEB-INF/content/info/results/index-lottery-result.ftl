 <#if ssqPeriodData??&&ssqPeriod??>
<#assign period=ssqPeriod />
<#assign periodData=ssqPeriodData />   
<tr>
<td>${period.lotteryType.lotteryName!}</td>
<td>${period.periodNumber!}期</td>                 
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[0]}</td>
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[1]}</td>
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[2]}</td>
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[3]}</td>
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[4]}</td>
<td width="20" class="hongqiu">${ssqPeriodData.rsultArr[5]}</td>
<td width="20" class="lanqiu">${ssqPeriodData.rsultArr[6]}</td>  
<td width="20">&nbsp;</td>
<td width="5">&nbsp;</td>
<td width="94"><a href="javascript:void(0);" onclick="window.open('${base}/tzfb/tzfb!ssq_link.action?playType=1&period=${period.periodNumber}&result=${ssqPeriodData.result}&preriodId=${period.id}')" target="_blank">当前投注分布</a></td>
<td width="81"><a href="/ch/ZPD_SSQ_FORECAST.htm">最新猛料分析</a></td>
<td width="42"><a href="/ch/ssq.htm" title="双色球购买投注">买彩票</a></td>
<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/ssq/scheme!subList.action')">合买跟单</a></td>
</tr>
</#if>

<#if dltPeriodData??&&dltPeriod??>
<#assign period=dltPeriod />
<#assign periodData=dltPeriodData />
<tr>     
<td>${period.lotteryType.lotteryName!}</td>
<td>${period.periodNumber!}期</td>                 
<td width="20" class="hongqiu">${dltPeriodData.rsultArr[0]}</td>
<td width="20" class="hongqiu">${dltPeriodData.rsultArr[1]}</td>
<td width="20" class="hongqiu">${dltPeriodData.rsultArr[2]}</td>
<td width="20" class="hongqiu">${dltPeriodData.rsultArr[3]}</td>
<td width="20" class="hongqiu">${dltPeriodData.rsultArr[4]}</td>
<td width="20" class="lanqiu">${dltPeriodData.rsultArr[5]}</td>
<td width="20" class="lanqiu">${dltPeriodData.rsultArr[6]}</td>  
<td width="20">&nbsp;</td>
<td width="5">&nbsp;</td>
<td width="94"><a href="javascript:void(0);" onclick="window.open('${base}/tzfb/tzfb!dlt_link.action?playType=1&period=${period.periodNumber}&result=${dltPeriodData.result}&preriodId=${period.id}')" target="_blank">当前投注分布</a></td>
<td width="81"><a href="/ch/ZPD_DLT_FORECAST.htm">最新猛料分析</a></td>
<td width="42"><a href="/ch/dlt.htm" title="大乐透购买投注">买彩票</a></td>
<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/dlt/scheme!subList.action')">合买跟单</a></td>
</tr>
</#if>

<#if plPeriodData??&&plPeriod??>
<#assign period=plPeriod />
<#assign periodData=plPeriodData />     
<tr>
<td>排列三</td>
<td>${period.periodNumber!}期</td>                 
<td width="20" class="chengqiu">${plPeriodData.rsultArr[0]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[1]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[2]}</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td width="94"><a href="javascript:void(0);" onclick="window.open('${base}/tzfb/tzfb!sp3_link.action?playType=1&period=${period.periodNumber}&result=${plPeriodData.result}&preriodId=${period.id}')" target="_blank">当前投注分布</a></td>
<td width="81"><a href="/ch/ZPD_PL_FORECAST.htm">最新猛料分析</a></td>
<td width="42"><a href="/ch/pl-0.htm" title="排列三购买投注">买彩票</a></td>
<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/pl/scheme!subList.action?playType=0')">合买跟单</a></td>
</tr>
</#if>

<#if plPeriodData??&&plPeriod??>
<#assign period=plPeriod />
<#assign periodData=plPeriodData />     
<tr>
<td>排列五</td>
<td>${period.periodNumber!}期</td>                
<td width="20" class="chengqiu">${plPeriodData.rsultArr[0]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[1]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[2]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[3]}</td>
<td width="20" class="chengqiu">${plPeriodData.rsultArr[4]}</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td width="94"><a href="javascript:void(0);">当前投注分布</a></td>
<td width="81"><a href="/ch/ZPD_PL_FORECAST.htm">最新猛料分析</a></td>
<td width="42"><a href="/ch/pl-1.htm" title="排列五购买投注">买彩票</a></td>
<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/pl/scheme!subList.action?playType=1')">合买跟单</a></td>
</tr>
</#if>

<#if welfare3dPeriodData??&&welfare3dPeriod??>
<#assign period=welfare3dPeriod />
<#assign periodData=welfare3dPeriodData />
<tr>     
<td>${period.lotteryType.lotteryName!}</td>
<td>${period.periodNumber!}期</td>                 
<td width="20" class="hongqiu">${welfare3dPeriodData.rsultArr[0]}</td>
<td width="20" class="hongqiu">${welfare3dPeriodData.rsultArr[1]}</td>
<td width="20" class="hongqiu">${welfare3dPeriodData.rsultArr[2]}</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td width="94"><a href="javascript:void(0);" onclick="window.open('${base}/tzfb/tzfb!fc3d_link.action?playType=1&period=${period.periodNumber}&result=${welfare3dPeriodData.result}&preriodId=${period.id}')" target="_blank">当前投注分布</a></td>
<td width="81"><a href="/ch/ZPD_WELFARE3D_FORECAST.htm">最新猛料分析</a></td>
<td width="42"><a href="/ch/welfare3d.htm" title="福彩3D购买投注">买彩票</a></td>
<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/welfare3d/scheme!subList.action')">合买跟单</a></td>
</tr>
</#if>

<#if sevenPeriodData??&&sevenPeriod??>
<#assign period=sevenPeriod />
<#assign periodData=sevenPeriodData />
	<tr>
    <td>${period.lotteryType.lotteryName!}</td>
	<td>${period.periodNumber!}期</td>      
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[0]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[1]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[2]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[3]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[4]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[5]}</td>
    <td class=hongqiu width=20>${sevenPeriodData.rsultArr[6]}</td>
    <td class=lanqiu width=20>${sevenPeriodData.rsultArr[7]}</td>
    <td>&nbsp;</td>
    <td width=94><a href="#" target=_blank>当前投注分布</a></td>
    <td width=81><a href="/ch/ZPD_SEVEN_FORECAST.htm">最新猛料分析</a></td>
    <td width=42><a href="/ch/seven.htm" title="七乐彩购买投注">买彩票</a></td>
    <td width=56><a href="#">合买跟单</a></td>
   </tr>
</#if>

<#if tc22to5PeriodData??&&tc22to5Period??>
<#assign period=tc22to5Period />
<#assign periodData=tc22to5PeriodData />   
   <tr>
    <td>${period.lotteryType.lotteryName!}</td>
	<td>${period.periodNumber!}期</td>   
    <td class=hongqiu width=20>${tc22to5PeriodData.rsultArr[0]}</td>
    <td class=hongqiu width=20>${tc22to5PeriodData.rsultArr[1]}</td>
    <td class=hongqiu width=20>${tc22to5PeriodData.rsultArr[2]}</td>
    <td class=hongqiu width=20>${tc22to5PeriodData.rsultArr[3]}</td>
    <td class=hongqiu width=20>${tc22to5PeriodData.rsultArr[4]}</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width=94><a href="#" 
      target=_blank>当前投注分布</a></td>
    <td width=81><a href="/ch/ZPD_TC22TO5_FORECAST.htm">最新猛料分析</a></td>
    <td width=42><a href="/ch/tc22to5.htm" title="22选5购买投注">买彩票</a></td>
	<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/tc22to5/scheme!subList.action')">合买跟单</a></td>
   </tr>
</#if>

<#if welfare36to7PeriodData??&&welfare36to7Period??>
<#assign period=welfare36to7Period />
<#assign periodData=welfare36to7PeriodData />   
   <tr>
    <td>${period.lotteryType.lotteryName!}</td>
	<td>${period.periodNumber!}期</td>   
	<#assign resultArr = periodData.resultFormat?split(",") />
     <td class=lanqiu width=20>${resultArr[6]!}</td>
     <td class=lanqiu width=20>${resultArr[7]!}</td>
     <td class=lanqiu width=20>${resultArr[8]!}</td>
     <td class=lanqiu width=20>${resultArr[9]!}</td>
     <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td width=94><a href="#" 
      target=_blank>当前投注分布</a></td>
    <td width=81><a href="/ch/ZPD_WELFARE36TO7_FORECAST.htm">最新猛料分析</a></td>
    <td width=42><a href="/ch/welfare36to7.htm" title="好彩一购买投注">买彩票</a></td>
	<td width="56"><a href="javascript:void(0);" onclick="window.open('${base}/welfare36to7/scheme!subList.action')">合买跟单</a></td>
   </tr>
</#if>

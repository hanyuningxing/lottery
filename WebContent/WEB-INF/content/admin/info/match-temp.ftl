<table border="0" cellspacing="0" cellpadding="0" width="650px">
    <tr>
        <td style="text-align:left; width:170px;"><#if period??>${period.periodNumber!}</#if>期</td>
        <td>本站截止：复式</td>
        <td><span class="color_red"><#if compoundEndTime??>${compoundEndTime?string('yyyy-MM-dd HH:mm')}</#if></span></td>
        <td>单式</td>
        <td><span class="color_red"><#if singleEndTime??>${singleEndTime?string('yyyy-MM-dd HH:mm')}</#if></span></td>
        <td><span class="color_red">历史对阵</span></td>
    </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="change_color tabs_td" width="100%">
  <tr  class="height weight700 head">
    <td style="text-align:left; width:30px;">场次</td>
    <td>联赛</td>
    <td>开赛时间</td>
    <td>主队</td>
    <td>比分</td>
    <td>客队</td>
    <td>亚赔</td>
    <td style="width:110px;">平均欧赔</td>
    <td>数据分析</td>
    <td>投注统计</td>                     
  </tr>
  <#if period.lotteryType == 'DCZC'>
	  <#if dczcMatchList??>
		  <#list dczcMatchList as data>
		  <tr class="row${data_index%2}">
		    <td style="text-align:left; width:30px;">${data.lineId + 1}</td>
		    <td>${data.gameName!}</td>
		    <td><#if data.matchTime??>${data.matchTime?string("MM-dd HH:mm")}</#if></td>
		    <td>${data.homeTeamName!}</td>
		    <td>${data.fullHomeScore!} : ${data.fullGuestScore!}</td>
		    <td>${data.guestTeamName!}</td>
		    <td><#if data.oddsData??>${data.oddsData.assia?string("#.##")}</#if></td>
		    <td style="width:110px;">
		    <#if data.oddsData??>${data.oddsData.europWin?string("#.##")}</#if>&nbsp;&nbsp;
		    <#if data.oddsData??>${data.oddsData.europDraw?string("#.##")}</#if>&nbsp;&nbsp;
		    <#if data.oddsData??>${data.oddsData.europLost?string("#.##")}</#if>
		    </td>
		    <td>亚 欧 析</td>
		    <td><span class="border_4 ball">3</span>&nbsp;&nbsp;1&nbsp;&nbsp;0</td>
		  </tr>
		  </#list>
	  </#if>
  <#else>
  	<#if matchArr??>
  		<#list matchArr as data>
	  	  <tr class="row${data_index%2}">
		    <td style="text-align:left; width:30px;">${data.lineId + 1}</td>
		    <td>${data.gameName!}</td>
		    <td><#if data.matchTime??>${data.matchTime?string("MM-dd HH:mm")}</#if></td>
		    <td>${data.homeTeamName!}</td>
		    <td>${data.homeScore!} : ${data.guestScore!}</td>
		    <td>${data.guestTeamName!}</td>
		    <td><#if data.asiaOdd??>${data.asiaOdd?string("#.##")}</#if></td>
		    <td style="width:110px;">
		    <#if data.odds1??>${data.odds1?string("#.##")}</#if>&nbsp;&nbsp;
		    <#if data.odds2??>${data.odds2?string("#.##")}</#if>&nbsp;&nbsp;
		    <#if data.odds3??>${data.odds3?string("#.##")}</#if>
		    </td>
		    <td>亚 欧 析</td>
		    <td><span class="border_4 ball">3</span>&nbsp;&nbsp;1&nbsp;&nbsp;0</td>
		  </tr>
	   </#list>
  	</#if>
  </#if>
</table>
<div class="submit_r">
  <ul>
   <li><a href="#" class="border_4">&nbsp;购&nbsp;买&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>               
   <li><a href="#" class="border_4">&nbsp;清&nbsp;空&nbsp;</a>&nbsp;&nbsp;</li>
   <li style="margin-right:150px;">您选择了<a style="color:#FF0000>">0</a>场比赛,<a style="color:#FF0000>">0</a>注￥<a style="color:#FF0000>">0.0 0</a></li>
  </ul>
</div>
<div style="clear:both;">
	<ul style="list-style-type:none;">
	<#if prvPeriod??>
	 <li>开奖：<span class="color_red">${prvPeriod.periodNumber!}</span> 期<#if prvSsqPeriodData??><#if prvSsqPeriodData.result??><#assign resultArr = prvSsqPeriodData.result?split(",")><span class="color_red">&nbsp;&nbsp;${resultArr[0]?number?string("00")} ${resultArr[1]?number?string("00")} ${resultArr[2]?number?string("00")} ${resultArr[3]?number?string("00")} ${resultArr[4]?number?string("00")} ${resultArr[5]?number?string("00")}</span> <span class="color_blue">${resultArr[6]?number?string("00")}</span> &nbsp;&nbsp;</#if><#if prvSsqPeriodData.prize??>一等奖<span class="color_red"> ${prvSsqPeriodData.prize.firstPrize!}</span>元；</#if>彩池<span class="color_red"> ${prvSsqPeriodData.prizePool!}</span>元</#if></li>
	 </#if>
	 <#if curPeriod??>
	 <li>双色球第<span class="color_red">${curPeriod.periodNumber!}</span>期复式发起截止时间：<span class="color_red"><#if curPeriod.endedTime??>${curPeriod.endedTime?string("yyyy-MM-dd HH:mm")}</#if></span></li>
	 </#if>
	 <li>玩法说明：每注投注号码由6个红球号码和1个蓝球号码组成，单注最高奖500万。</li>
	</ul>
</div>   
<div class="l border_4">          
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs_ball" >
  <tr>
   <td colspan="11" class="center weight700">红球投注区</td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">1</span></td>
    <td class="ball_ball"><span class="mar-10">2</span></td>
    <td class="ball_ball"><span class="mar-10">3</span></td>
    <td class="ball_ball"><span class="mar-10">4</span></td>
    <td class="ball_ball"><span class="mar-10">5</span></td>
    <td class="ball_ball"><span class="mar-10">6</span></td>
    <td class="ball_ball"><span class="mar-10">7</span></td>
    <td class="ball_ball"><span class="mar-10">8</span></td>
    <td class="ball_ball"><span class="mar-10">9</span></td>
    <td class="ball_ball"><span class="mar-10">10</span></td>
    <td class="ball_ball"><span class="mar-10">11</span></td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">12</span></td>
    <td class="ball_ball"><span class="mar-10">13</span></td>
    <td class="ball_ball"><span class="mar-10">14</span></td>
    <td class="ball_ball"><span class="mar-10">15</span></td>
    <td class="ball_ball"><span class="mar-10">16</span></td>
    <td class="ball_ball"><span class="mar-10">17</span></td>
    <td class="ball_ball"><span class="mar-10">18</span></td>
    <td class="ball_ball"><span class="mar-10">19</span></td>
    <td class="ball_ball"><span class="mar-10">20</span></td>
    <td class="ball_ball"><span class="mar-10">21</span></td>
    <td class="ball_ball"><span class="mar-10">22</span></td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">23</span></td>
    <td class="ball_ball"><span class="mar-10">24</span></td>
    <td class="ball_ball"><span class="mar-10">25</span></td>
    <td class="ball_ball"><span class="mar-10">26</span></td>
    <td class="ball_ball"><span class="mar-10">27</span></td>
    <td class="ball_ball"><span class="mar-10">28</span></td>
    <td class="ball_ball"><span class="mar-10">29</span></td>
    <td class="ball_ball"><span class="mar-10">30</span></td>
    <td class="ball_ball"><span class="mar-10">31</span></td>
    <td class="ball_ball"><span class="mar-10">32</span></td>
    <td class="ball_ball"><span class="mar-10">33</span></td>
  </tr>
</table>                    
</div>
<div class="r border_4">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs_ball">
  <tr>
   <td colspan="11" class="center weight700">蓝球投注区</td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">1</span></td>
    <td class="ball_ball"><span class="mar-10">2</span></td>
    <td class="ball_ball"><span class="mar-10">3</span></td>
    <td class="ball_ball"><span class="mar-10">4</span></td>
    <td class="ball_ball"><span class="mar-10">5</span></td>
    <td class="ball_ball"><span class="mar-10">6</span></td>
    <td class="ball_ball"><span class="mar-10">7</span></td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">8</span></td>
    <td class="ball_ball"><span class="mar-10">9</span></td>
    <td class="ball_ball"><span class="mar-10">10</span></td>
    <td class="ball_ball"><span class="mar-10">11</span></td>
    <td class="ball_ball"><span class="mar-10">12</span></td>
    <td class="ball_ball"><span class="mar-10">13</span></td>
    <td class="ball_ball"><span class="mar-10">14</span></td>
  </tr>
  <tr>
    <td class="ball_ball"><span class="mar-10">15</span></td>
    <td class="ball_ball"><span class="mar-10">16</span></td>
  </tr>
</table>
</div>
<div class="change_b" >
  <div style="width:430px; height:120px; float:left;"><textarea rows="7" style="width:430px;"></textarea></div>
  <div style="width:265px; height:120px; float:left; margin-left:20px;">
 	<ul class="change">
	    <li><input type="button"  value="添加到投注列表"/></li>
	    <li><input type="button"  value="机选一注"/></li>
	    <li><input type="button"  value="机选五注"/></li>
	    <li><input type="button"  value="机选十注"/></li>
  	</ul>
  </div>
</div>
<div class="submit_r">
  <ul>
   <li><a href="#" class="border_4">&nbsp;购&nbsp;买&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>               
   <li><a href="#" class="border_4">&nbsp;清&nbsp;空&nbsp;</a>&nbsp;&nbsp;</li>
   <li style="margin-right:150px;">您选择了<a style="color:#FF0000">0</a>个红球，<a style="color:#FF0000">0</a>个蓝球，共<a style="color:#FF0000">0</a>注，金额<a style="color:#FF0000">0</a>元																</li>
  </ul>
</div>
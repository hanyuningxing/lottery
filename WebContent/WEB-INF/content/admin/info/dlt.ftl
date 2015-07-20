<#--大乐透-->
<div style="clear:both;">
	<ul style="list-style-type:none;">
	<#-- if prvPeriod??>
	 <li>开奖：<span class="color_red">${prvPeriod.periodNumber!}</span> 期<#if prvPeriodData??><#if prvPeriodData.result??><#assign resultArr = prvPeriodData.result?split(",")><span class="color_red">&nbsp;&nbsp;${resultArr[0]?number?string("00")} ${resultArr[1]?number?string("00")} ${resultArr[2]?number?string("00")} ${resultArr[3]?number?string("00")} ${resultArr[4]?number?string("00")} ${resultArr[5]?number?string("00")}</span> <span class="color_blue">${resultArr[6]?number?string("00")}</span> &nbsp;&nbsp;</#if><#if prvPeriodData.prize??>一等奖<span class="color_red"> ${prvPeriodData.prize.firstPrize!}</span>元；</#if>彩池<span class="color_red"> ${prvPeriodData.prizePool!}</span>元</#if></li>
	 </#if-->
	 <#if curPeriod??>
	 <li>大乐透第<span class="color_red">${curPeriod.periodNumber!}</span>期复式发起截止时间：<span class="color_red"><#if curPeriod.endedTime??>${curPeriod.endedTime?string("yyyy-MM-dd HH:mm")}</#if></span></li>
	 </#if>
	 <li>玩法说明：每注投注号码由6个红球号码和1个蓝球号码组成，单注最高奖500万。</li>
	</ul>
</div> 
 <div class="title">
      <div class="t1 weight700">前区投注(从35个号码中至少选取5个)</div>
      <div class="t2 weight700">后区12选2</div>
    </div>
            <div  class="l border_4">
              <table width="365" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="ball_red"><span style="color:#FFFFFF; font-weight:bold; padding-right:7px;">01</span></td>
                  <td class="ball_ball">02</td>
                  <td class="ball_ball">03</td>
                  <td class="ball_ball">04</td>
                  <td class="ball_ball">05</td>
                  <td class="ball_ball">06</td>
                  <td class="ball_ball">07</td>
                  <td class="ball_ball">08</td>
                  <td class="ball_ball">09</td>
                  <td class="ball_ball">10</td>
                  <td class="ball_ball">11</td>
                  <td class="ball_ball">12</td>
                </tr>
                <tr>
                  <td class="ball_ball">13</td>
                  <td class="ball_ball">14</td>
                  <td class="ball_ball">15</td>
                  <td class="ball_ball">16</td>
                  <td class="ball_ball">17</td>
                  <td class="ball_ball">18</td>
                  <td class="ball_ball">19</td>
                  <td class="ball_ball">20</td>
                  <td class="ball_ball">21</td>
                  <td class="ball_ball">22</td>
                  <td class="ball_ball">23</td>
                  <td class="ball_ball">24</td>
                </tr>
                <tr>
                  <td class="ball_ball">25</td>
                  <td class="ball_ball">26</td>
                  <td class="ball_ball">27</td>
                  <td class="ball_ball">28</td>
                  <td class="ball_ball">29</td>
                  <td class="ball_ball">30</td>
                  <td class="ball_ball">31</td>
                  <td class="ball_ball">32</td>
                  <td class="ball_ball">33</td>
                  <td class="ball_ball">34</td>
                  <td class="ball_ball">35</td>
                </tr>
              </table>
            </div>
            <div  class="r border_4">
              <table width="245" height="123" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="ball_blue"><span style="color:#FFFFFF; font-weight:bold; padding-bottom:-10px; padding-right:7px;">01</span></td>
                  <td class="ball_ball">02</td>
                  <td class="ball_ball">03</td>
                  <td class="ball_ball">04</td>
                  <td class="ball_ball">05</td>
                  <td class="ball_ball">06</td>
                  <td class="ball_ball">07</td>
                </tr>
                <tr>
                  <td class="ball_ball">08</td>
                  <td class="ball_ball">09</td>
                  <td class="ball_ball">10</td>
                  <td class="ball_ball">11</td>
                  <td class="ball_ball">12</td>
                </tr>
              </table>
            </div>
            <div class="bet">
      <div style="float:left; width:450px;padding-top:5px;">
        <textarea name="shuzixianshi" style="width:99%; height:125px;"></textarea>
      </div>
      <div style="padding-top:5px;float:left; margin-left:10px;">
        <table width="220" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="2"><input name="tianjia" type="button" value="添加到投注列表" class="btn color_fff margin3" /></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="机选一注" class="btn color_fff margin3"/></td>
            <td><input name="danjixuan1" type="button" value="定胆机选一注" class="btn color_fff margin3 padding_left5"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="机选一注" class="btn color_fff margin3"/></td>
            <td><input name="danjixuan1" type="button" value="定胆机选五注" class="btn color_fff margin3 padding_left5"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="机选一注" class="btn color_fff margin3"/></td>
            <td><input name="danjixuan1" type="button" value="定胆机选十注" class="btn color_fff margin3 padding_left5"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="清空列表" class="btn color_ffc margin3"/></td>
            <td></td>
          </tr>
        </table>
      </div>
    </div>

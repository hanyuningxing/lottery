<#--排列三-->
<div style="clear:both;">
	<ul style="list-style-type:none;">
	<#-- if prvPeriod??>
	 <li>开奖：<span class="color_red">${prvPeriod.periodNumber!}</span> 期<#if prvPeriodData??><#if prvPeriodData.result??><#assign resultArr = prvPeriodData.result?split(",")><span class="color_red">&nbsp;&nbsp;${resultArr[0]?number?string("00")} ${resultArr[1]?number?string("00")} ${resultArr[2]?number?string("00")} ${resultArr[3]?number?string("00")} ${resultArr[4]?number?string("00")} ${resultArr[5]?number?string("00")}</span> <span class="color_blue">${resultArr[6]?number?string("00")}</span> &nbsp;&nbsp;</#if><#if prvPeriodData.prize??>一等奖<span class="color_red"> ${prvPeriodData.prize.firstPrize!}</span>元；</#if>彩池<span class="color_red"> ${prvPeriodData.prizePool!}</span>元</#if></li>
	 </#if-->
	 <#if curPeriod??>
	 <li>排列三第<span class="color_red">${curPeriod.periodNumber!}</span>期复式发起截止时间：<span class="color_red"><#if curPeriod.endedTime??>${curPeriod.endedTime?string("yyyy-MM-dd HH:mm")}</#if></span></li>
	 </#if>
	 <li>玩法说明：每注投注号码由6个红球号码和1个蓝球号码组成，单注最高奖500万。</li>
	</ul>
</div> 
 <div class="title">
      <div class="radio">
        <form id="form1" name="form1" method="post" action="">
      		<p>
            <label>
              <input name="RadioGroup1" type="radio" id="RadioGroup1_0" value="Radio"  />
              直选</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" checked="checked" />
              组三包号</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组六包号</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组三组投</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组六组投</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组选合投</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              直选和值</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组三和值</label>
            <label>
              <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
              组六和值</label>
          </p>
        </form>
      </div>
    </div>
		<div style="height:30px; width:805px; border:#A7CAED 1px solid; line-height:30px; overflow:hidden"><span class="fontcolor weight700">说明：</span>当期摇出的中奖号码3位数中有任意两位数字相同，且投注号码与中奖号码的数字相同，顺序不限，即中得组选3奖。</div>
    <div class="l border_4">
      <table width="365" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="ball_ball padding_left5">0</td>
          <td class="ball_ball">01</td>
          <td class="ball_ball">02</td>
          <td class="ball_ball">03</td>
          <td class="ball_ball">04</td>
          <td class="ball_ball">05</td>
          <td class="ball_ball">06</td>
          <td class="ball_ball">07</td>
          <td class="ball_ball">08</td>
          <td class="ball_ball">09</td>
        </tr>
        <tr>
          <td class="ball_ball padding_left5">0</td>
          <td class="ball_ball">01</td>
          <td class="ball_ball">02</td>
          <td class="ball_ball">03</td>
          <td class="ball_ball">04</td>
          <td class="ball_ball">05</td>
          <td class="ball_ball">06</td>
          <td class="ball_ball">07</td>
          <td class="ball_ball">08</td>
          <td class="ball_ball">09</td>
        </tr>
        <tr>
          <td class="ball_ball padding_left5">0</td>
          <td class="ball_ball">01</td>
          <td class="ball_ball">02</td>
          <td class="ball_ball">03</td>
          <td class="ball_ball">04</td>
          <td class="ball_ball">05</td>
          <td class="ball_ball">06</td>
          <td class="ball_ball">07</td>
          <td class="ball_ball">08</td>
          <td class="ball_ball">09</td>
        </tr>
      </table>
    </div>
    <div class="r border_4">
      <table width="245" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="ball_ball"><span class="weight700 padding_left5">全</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">大</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">小</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">奇</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">偶</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">清</span></td>
        </tr>
        <tr>
          <td class="ball_ball"><span class="weight700 padding_left5">全</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">大</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">小</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">奇</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">偶</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">清</span></td>
        </tr>
        <tr>
          <td class="ball_ball"><span class="weight700 padding_left5">全</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">大</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">小</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">奇</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">偶</span></td>
          <td class="ball_ball"><span class="weight700 padding_left5">清</span></td>
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
            <td><input name="danjixuan1" type="button" value="机选二注" class="btn color_fff margin3"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="机选三注" class="btn color_fff margin3"/></td>
            <td><input name="danjixuan1" type="button" value="机选四注" class="btn color_fff margin3"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="机选五注" class="btn color_fff margin3"/></td>
            <td><input name="danjixuan1" type="button" value="机选十注" class="btn color_fff margin3"/></td>
          </tr>
          <tr>
            <td><input name="jixuan1" type="button" value="清空列表" class="btn color_ffc margin3"/></td>
          </tr>
        </table>
      </div>
    </div>
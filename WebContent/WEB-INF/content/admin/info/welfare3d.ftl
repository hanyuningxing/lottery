<#--福彩3D-->
<div style="clear:both;">
	<ul style="list-style-type:none;">
	<#-- if prvPeriod??>
	 <li>开奖：<span class="color_red">${prvPeriod.periodNumber!}</span> 期<#if prvPeriodData??><#if prvPeriodData.result??><#assign resultArr = prvPeriodData.result?split(",")><span class="color_red">&nbsp;&nbsp;${resultArr[0]?number?string("00")} ${resultArr[1]?number?string("00")} ${resultArr[2]?number?string("00")} ${resultArr[3]?number?string("00")} ${resultArr[4]?number?string("00")} ${resultArr[5]?number?string("00")}</span> <span class="color_blue">${resultArr[6]?number?string("00")}</span> &nbsp;&nbsp;</#if><#if prvPeriodData.prize??>一等奖<span class="color_red"> ${prvPeriodData.prize.firstPrize!}</span>元；</#if>彩池<span class="color_red"> ${prvPeriodData.prizePool!}</span>元</#if></li>
	 </#if-->
	 <#if curPeriod??>
	 <li>福彩3D第<span class="color_red">${curPeriod.periodNumber!}</span>期复式发起截止时间：<span class="color_red"><#if curPeriod.endedTime??>${curPeriod.endedTime?string("yyyy-MM-dd HH:mm")}</#if></span></li>
	 </#if>
	 <li>玩法说明：每注投注号码由6个红球号码和1个蓝球号码组成，单注最高奖500万。</li>
	</ul>
</div> 
 <div class="title" style="cursor:ponter;" onclick="window.alert('test')">
          <div class="radio">        
            <form id="form1" name="form1" method="post" action="">
              <p>
                <label>
                  <input name="RadioGroup1" type="radio" id="RadioGroup1_0" value="Radio" checked="checked" />
                  直选</label>
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
                  前一</label>
                <label>
                  <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
                  前二</label>
                <label>
                  <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
                  后一</label>
                <label>
                  <input type="radio" name="RadioGroup1" value="Radio" id="RadioGroup1_0" />
                  后二</label>
              </p>
            </form>
          </div>
        </div>
    <div class="l border_4">
      <table width="365" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="50"><span class="weight700">个位</span></td>
          <td class="ball_ball">0</td>
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
          <td width="50"><span class="weight700">十位</span></td>
          <td class="ball_ball">0</td>
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
          <td width="50"><span class="weight700">百位</span></td>
          <td class="ball_ball">0</td>
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
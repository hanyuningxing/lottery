<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="lc_t_t">
  <tr class="lc_t_td03">
    <td width="200" height="27" align="right" class="lc_t_left">AC值：</td>
    <td align="left">
      <#list 0..9 as ac>
		<label><input type="checkbox" name="shrinkBean.acList" value="${ac}">${ac}</label>
	  </#list>
	</td>
  </tr>
  <tr class="lc_t_td02">
    <td height="27" align="right" class="lc_t_left">首位号码的奇偶值：</td>
    <td align="left">
		<input type="radio" name="shrinkBean.firstType" value="ODD" />奇数 
		<input type="radio" name="shrinkBean.firstType" value="EVEN" />偶数
		<input type="radio" name="shrinkBean.firstType" value="" checked="checked" />不限 (即开奖号码中的最大号码) 
	</td>
  </tr>
  <tr class="lc_t_td03">
    <td height="27" align="right" class="lc_t_left">末位号码的奇偶值：</td>
    <td align="left">
		<input type="radio" name="shrinkBean.lastType" value="ODD" />奇数 
		<input type="radio" name="shrinkBean.lastType" value="EVEN" />偶数
		<input type="radio" name="shrinkBean.lastType" value="" checked="checked" />不限 (即开奖号码中的最大号码)
	</td>
  </tr>
  <tr class="lc_t_td02">
    <td height="27" align="right" class="lc_t_left">和值：</td>
    <td align="left">
		<input name="shrinkBean.minSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="3" size="10">
		至
		<input name="shrinkBean.maxSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="3" size="10">
		(请用键盘输入)
	</td>
  </tr>
  <#assign contrastArr = ['0:6','1:5','2:4','3:3','4:2','5:1','6:0'] />
  <tr class="lc_t_td03">
    <td height="27" align="right" class="lc_t_left">奇偶比：</td>
    <td align="left"><#list contrastArr as contrast><label><input type="checkbox" name="shrinkBean.oddEvenContrastList" value="${contrast}">${contrast}</label></#list></td>
  </tr>
  <tr class="lc_t_td02">
    <td height="27" align="right" class="lc_t_left">大小比：</td>
    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="shrinkBean.bigSmallContrastList" value="${contrast}">${contrast}</label></#list></td>
  </tr>
  <tr class="lc_t_td03">
    <td height="27" align="right" class="lc_t_left"> 质合比：</td>
    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="shrinkBean.primeCompositeContrastList" value="${contrast}">${contrast}</label></#list></td>
  </tr>
  <tr class="lc_t_td02">
    <td height="27" align="right" class="lc_t_left">连号组数：</td>
    <td align="left">
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="0"/>无连号 </label>
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="1"/>一连号 </label>
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="2"/>二连号 </label>
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="3"/>三连号 </label>
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="4"/>四连号 </label>
		<label><input type="checkbox" name="shrinkBean.consecutiveSizeList" value="5"/>五连号 </label>
    </td>
  </tr>
  <tr class="lc_t_td03">
    <td height="27" align="right" class="lc_t_left">每注中尾数不相同的投注号码 ：</td>
    <td align="left"><#list 2..6 as c><label><input type="checkbox" name="shrinkBean.unitDistinctList" value="${c}"/>${c} </label></#list></td>
  </tr>
</table>
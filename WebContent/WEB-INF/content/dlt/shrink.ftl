<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="550" valign="top"><table width="550" border="0" align="center" cellpadding="0" cellspacing="0" class="lc_t_t">
          <tr class="lc_t_td03">
            <td width="120" height="27" align="right" class="lc_t_left">AC值</td>
            <td align="left"><#list 0..9 as ac><label><input type="checkbox" name="dlt35to5ShrinkBean.acList" value="${ac}">${ac}</label></#list></td>
          </tr>
          <tr class="lc_t_td02">
            <td height="27" align="right" class="lc_t_left">首位号码的奇偶值</td>
            <td align="left">
				<input type="radio" name="dlt35to5ShrinkBean.firstType" value="ODD" />奇数 
				<input type="radio" name="dlt35to5ShrinkBean.firstType" value="EVEN" />偶数
				<input type="radio" name="dlt35to5ShrinkBean.firstType" value="" checked="checked" />不限 (即开奖号码中的最大号码) 
			</td>
          </tr>
          <tr class="lc_t_td03">
            <td height="27" align="right" class="lc_t_left">末位号码的奇偶值</td>
            <td align="left">
				<input type="radio" name="dlt35to5ShrinkBean.lastType" value="ODD" />奇数 
				<input type="radio" name="dlt35to5ShrinkBean.lastType" value="EVEN" />偶数
				<input type="radio" name="dlt35to5ShrinkBean.lastType" value="" checked="checked" />不限 (即开奖号码中的最大号码) 
            </td>
          </tr>
          <tr class="lc_t_td02">
            <td height="27" align="right" class="lc_t_left">和值</td>
            <td align="left">
	            <input name="dlt35to5ShrinkBean.minSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="3" size="10">
				至
				<input name="dlt35to5ShrinkBean.maxSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="3" size="10">
				(请用键盘输入)
            </td>
          </tr>
		  <#assign contrastArr = ['0:5','1:4','2:3','3:2','4:1','5:0'] />
		  <tr class="lc_t_td03">
		    <td height="27" align="right" class="lc_t_left">奇偶比：</td>
		    <td align="left"><#list contrastArr as contrast><label><input type="checkbox" name="dlt35to5ShrinkBean.oddEvenContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td02">
		    <td height="27" align="right" class="lc_t_left">大小比：</td>
		    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="dlt35to5ShrinkBean.bigSmallContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td03">
		    <td height="27" align="right" class="lc_t_left"> 质合比：</td>
		    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="dlt35to5ShrinkBean.primeCompositeContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td02">
		    <td height="27" align="right" class="lc_t_left">连号组数：</td>
		    <td align="left">
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="0"/>无连号 </label>
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="1"/>一连号 </label>
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="2"/>二连号 </label>
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="3"/>三连号 </label>
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="4"/>四连号 </label>
				<label><input type="checkbox" name="dlt35to5ShrinkBean.consecutiveSizeList" value="5"/>五连号 </label>
		    </td>
		  </tr>
		  <tr class="lc_t_td03">
		    <td height="27" align="right" class="lc_t_left">每注尾数不同号码 ：</td>
		    <td align="left"><#list 2..5 as c><label><input type="checkbox" name="dlt35to5ShrinkBean.unitDistinctList" value="${c}"/>${c} </label></#list></td>
		  </tr>		
        </table></td>
      <td>&nbsp;</td>
      <td width="330" valign="top"><table width="330" border="0" align="center" cellpadding="0" cellspacing="0" class="lc_t_t">
          <tr class="lc_t_td02">
            <td height="27" align="right" class="lc_t_left">首位号码奇偶值</td>
            <td align="left">
				<input type="radio" name="dlt12to2ShrinkBean.firstType" value="ODD" />奇数 
				<input type="radio" name="dlt12to2ShrinkBean.firstType" value="EVEN" />偶数
				<input type="radio" name="dlt12to2ShrinkBean.firstType" value="" checked="checked" />不限 
            </td>
          </tr>
          <tr class="lc_t_td03">
            <td height="27" align="right" class="lc_t_left">末位号码奇偶值</td>
            <td align="left">
				<input type="radio" name="dlt12to2ShrinkBean.lastType" value="ODD" />奇数 
				<input type="radio" name="dlt12to2ShrinkBean.lastType" value="EVEN" />偶数
				<input type="radio" name="dlt12to2ShrinkBean.lastType" value="" checked="checked" />不限 
            </td>
          </tr>
          <tr class="lc_t_td02">
            <td height="27" align="right" class="lc_t_left">和值</td>
            <td align="left">
            	<input name="dlt12to2ShrinkBean.minSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="2" size="5">
				至
				<input name="dlt12to2ShrinkBean.maxSum" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" onkeydown="number_check(this,event,0)" onkeyup="this.value=this.value.replace(/[^0-9]\D*$/,&quot;&quot;)" maxlength="2" size="5">
				(请用键盘输入)
            </td>
          </tr>
          <#assign contrastArr = ['0:2','1:1','2:0'] />
		  <tr class="lc_t_td03">
		    <td height="27" align="right" class="lc_t_left">奇偶比：</td>
		    <td align="left"><#list contrastArr as contrast><label><input type="checkbox" name="dlt12to2ShrinkBean.oddEvenContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td02">
		    <td height="27" align="right" class="lc_t_left">大小比：</td>
		    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="dlt12to2ShrinkBean.bigSmallContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td03">
		    <td height="27" align="right" class="lc_t_left"> 质合比：</td>
		    <td align="left"><#list contrastArr as contrast ><label><input type="checkbox" name="dlt12to2ShrinkBean.primeCompositeContrastList" value="${contrast}">${contrast}</label></#list></td>
		  </tr>
		  <tr class="lc_t_td02">
		    <td height="27" align="right" class="lc_t_left">连号组数：</td>
		    <td align="left">
				<label><input type="checkbox" name="dlt12to2ShrinkBean.consecutiveSizeList" value="0"/>无连号 </label>
				<label><input type="checkbox" name="dlt12to2ShrinkBean.consecutiveSizeList" value="1"/>一连号 </label>
				<label><input type="checkbox" name="dlt12to2ShrinkBean.consecutiveSizeList" value="2"/>二连号 </label>
		    </td>
		  </tr>
        </table></td>
    </tr>
</table>
	<script type="text/javascript" src="${base}/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${base}/js/admin/fetchWinningNumbers.js"></script>
  <tr class="trw">
    <td align="left" colspan="2">
      <input id="updatePeriodData" type="checkbox" checked="checked" name="updatePeriodData" value="true" /><label for="updatePeriodData">更新开奖数据</label>
     <input id="fecthWinningNumber" type="button" name="fecthWinningNumber" value="抓取开奖号码"/>
     </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">开奖号码:</td>
    <td align="left">
    	<select name="num1">
				<option value="">号码1</option>
				<#list 0..9 as c>
					<option <#if num1??&&num1==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num2">
				<option value="">号码2</option>
				<#list 0..9 as c>
					<option <#if num2??&&num2==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num3">
				<option value="">号码3</option>
				<#list 0..9 as c>
					<option <#if num3??&&num3==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num4">
				<option value="">号码4</option>
				<#list 0..9 as c>
					<option <#if num4??&&num4==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num5">
				<option value="">号码5</option>
				<#list 0..9 as c>
					<option <#if num5??&&num5==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
    </td>
  </tr>
  <tr class="trw">
    <td align="left" colspan="2">
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
			  <tr class="trw">
			    <td class="trhemaigray" align="right">排五直选奖金：</td>
			    <td align="left">
			    	<input type="text" name="periodData.prize.p5UnitPrize" value="${periodData.prize.p5UnitPrize!}" />
			    </td>
			    <td class="trhemaigray" align="right">排五直选注数：</td>
			    <td align="left">
			    	<input type="text" name="periodData.winUnit.p5WinUnits" value="${periodData.winUnit.p5WinUnits!}" />
			    </td>
			  </tr>
			   <tr class="trw">
			    <td class="trhemaigray" align="right">排三直选奖金：</td>
			    <td align="left">
			    	<input type="text" name="periodData.prize.p3UnitPrize" value="${periodData.prize.p3UnitPrize!}" />
			    </td>
			    <td class="trhemaigray" align="right">排三直选注数：</td>
			    <td align="left">
			    	<input type="text" name="periodData.winUnit.p3WinUnits" value="${periodData.winUnit.p3WinUnits!}" />
			    </td>
			  </tr>
			  <tr class="trw">
			    <td class="trhemaigray" align="right">排三组三奖金：</td>
			    <td align="left">
			    	<input type="text" name="periodData.prize.p3G3UnitPrize" value="${periodData.prize.p3G3UnitPrize!}" />
			    </td>
			     <td class="trhemaigray" align="right">排三组三注数：</td>
			    <td align="left">
			    	<input type="text" name="periodData.winUnit.p3G3WinUnits" value="${periodData.winUnit.p3G3WinUnits!}" />
			    </td>
			  </tr>
			  <tr class="trw">
			    <td class="trhemaigray" align="right">排三组六奖金：</td>
			    <td align="left">
			    	<input type="text" name="periodData.prize.p3G6UnitPrize" value="${periodData.prize.p3G6UnitPrize!}" />
			    </td>
			    <td class="trhemaigray" align="right">排三组六注数：</td>
			    <td align="left">
			    	<input type="text" name="periodData.winUnit.p3G6WinUnits" value="${periodData.winUnit.p3G6WinUnits!}" />
			    </td>
			  </tr>
			  
			  <tr class="trw">
			   <td class="trhemaigray" align="right">排三销售总额：</td>
			   <td align="left">
			    			<input type="text" name="periodData.totalSales" value="${periodData.totalSales!}" />
			    </td>
			     <td class="trhemaigray" align="right">排五销售总额：</td>
			   <td align="left">
			    		<input type="text" name="periodData.p5TotalSales" value="${periodData.p5TotalSales!}" />
			    </td>
			  </tr>
			  <tr class="trlblue">
		     	<td class="trhemaigray" align="right">&nbsp;</td>
			    <td align="left" class="trhemaigray">
			    	<font color="red">以上数字只能为正整数不能包含小数点</font>
			    </td>
			   <td class="trhemaigray" align="right">&nbsp;</td>
			    <td align="left" class="trhemaigray">
			    	<font color="red">以上数字只能为正整数不能包含小数点</font>
			    </td>
		  </tr>
			</table>
      </td>
  </tr>
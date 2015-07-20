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
    </td>
  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">直选奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.unitPrize" value="${periodData.prize.unitPrize!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">组三奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.g3UnitPrize" value="${periodData.prize.g3UnitPrize!}" />
		    </td>
		  </tr>
		  
		  <tr class="trw">
		    <td class="trhemaigray" align="right">组六奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.g6UnitPrize" value="${periodData.prize.g6UnitPrize!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">直选注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.winUnits" value="${periodData.winUnit.winUnits!}" />
		    </td>
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">组三注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.g3WinUnits" value="${periodData.winUnit.g3WinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">组六注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.g6WinUnits" value="${periodData.winUnit.g6WinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
   			<td class="trhemaigray" align="right">销售总额：</td>
    		<td align="left">
    			<input type="text" name="periodData.totalSales" value="${periodData.totalSales!}" />
    		</td>
  		  </tr>
  		  <tr class="trlblue">
		     	<td class="trhemaigray" align="right">&nbsp;</td>
			    <td align="left" class="trhemaigray">
			    	<font color="red">以上数字只能为正整数不能包含小数点</font>
			    </td>
		  </tr>
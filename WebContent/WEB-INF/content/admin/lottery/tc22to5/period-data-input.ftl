  <tr class="trw">
    <td align="left" colspan="2">
      <input id="updatePeriodData" type="checkbox" checked="checked" name="updatePeriodData" value="true" /><label for="updatePeriodData">更新开奖数据</label>
     </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">开奖号码:</td>
    <td align="left">
    	    红球：
        <select name="num1">
				<option value="">号码1</option>
				<#list 1..22 as c>
					<option <#if num1??&&num1==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num2">
				<option value="">号码2</option>
				<#list 1..22 as c>
					<option <#if num2??&&num2==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num3">
				<option value="">号码3</option>
				<#list 1..22 as c>
					<option <#if num3??&&num3==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num4">
				<option value="">号码4</option>
				<#list 1..22 as c>
					<option <#if num4??&&num4==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num5">
				<option value="">号码5</option>
				<#list 1..22 as c>
					<option <#if num5??&&num5==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		
    </td>
  </tr>
  
  
  
  <tr class="trw">
    <td align="left" colspan="2">
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
		  <tr class="trw">
		    <td class="trhemaigray" align="right">一等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国一等奖总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.firstWinUnits" value="${periodData.winUnit.firstWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">二等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.secondPrize" value="${periodData.prize.secondPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国二等奖总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.secondWinUnits" value="${periodData.winUnit.secondWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">三等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.thirdPrize" value="${periodData.prize.thirdPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国三等奖总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.thirdWinUnits" value="${periodData.winUnit.thirdWinUnits!}" />
		    </td>
		    </td>
		  </tr>
		  
		   <tr class="trw">
		    <td class="trhemaigray" align="right">销售总额：</td>
		    <td align="left">
		    	<input type="text" name="periodData.totalSales" value="${periodData.totalSales!}" />
		    </td>
		    <td class="trhemaigray" align="right">奖池奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prizePool" value="${periodData.prizePool!}" />
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
  

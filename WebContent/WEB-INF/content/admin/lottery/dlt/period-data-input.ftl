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
                     前区：
        <select name="num1">
				<option value="">号码1</option>
				<#list 1..35 as c>
					<option <#if num1??&&num1==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num2">
				<option value="">号码2</option>
				<#list 1..35 as c>
					<option <#if num2??&&num2==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num3">
				<option value="">号码3</option>
				<#list 1..35 as c>
					<option <#if num3??&&num3==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num4">
				<option value="">号码4</option>
				<#list 1..35 as c>
					<option <#if num4??&&num4==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num5">
				<option value="">号码5</option>
				<#list 1..35 as c>
					<option <#if num5??&&num5==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		
		&nbsp;后区：
		<select name="num6">
				<option value="">号码1</option>
				<#list 1..12 as c>
					<option <#if num6??&&num6==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num7">
				<option value="">号码2</option>
				<#list 1..12 as c>
					<option <#if num7??&&num7==c>selected="selected"</#if> value="${c}">${c!}</option>
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
		    <td class="trhemaigray" align="right">一等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.firstAddPrize" value="${periodData.firstAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国一等奖追加总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.firstAddWinUnits" value="${periodData.firstAddWinUnits!}" />
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
		    <td class="trhemaigray" align="right">二等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.secondAddPrize" value="${periodData.secondAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国二等奖追加总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.secondAddWinUnits" value="${periodData.secondAddWinUnits!}" />
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
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">三等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.thirdAddPrize" value="${periodData.thirdAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国三等奖追加总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.thirdAddWinUnits" value="${periodData.thirdAddWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">四等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.fourthPrize" value="${periodData.prize.fourthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国四等奖总注数：</td>
		    <td align="left">
		        <input type="text" name="periodData.winUnit.fourthWinUnits" value="${periodData.winUnit.fourthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">四等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.fourthAddPrize" value="${periodData.fourthAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国四等奖追加总注数：</td>
		    <td align="left">
		        <input type="text" name="periodData.fourthAddWinUnits" value="${periodData.fourthAddWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">五等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.fifthPrize" value="${periodData.prize.fifthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国五等奖总注数：</td>
		    <td align="left">
		        <input type="text" name="periodData.winUnit.fifthWinUnits" value="${periodData.winUnit.fifthWinUnits!}" />
		    </td>
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">五等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.fifthAddPrize" value="${periodData.fifthAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国五等奖追加总注数：</td>
		    <td align="left">
		        <input type="text" name="periodData.fifthAddWinUnits" value="${periodData.fifthAddWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">六等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.sixthPrize" value="${periodData.prize.sixthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国六等奖总注数：</td>
		    <td align="left">
		      <input type="text" name="periodData.winUnit.sixthWinUnits" value="${periodData.winUnit.sixthWinUnits!}" />
		    </td>
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">六等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.sixthAddPrize" value="${periodData.sixthAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国六等奖追加总注数：</td>
		    <td align="left">
		      <input type="text" name="periodData.sixthAddWinUnits" value="${periodData.sixthAddWinUnits!}" />
		    </td>
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">七等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.seventhPrize" value="${periodData.prize.seventhPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国七等奖总注数：</td>
		    <td align="left">
		       <input type="text" name="periodData.winUnit.seventhWinUnits" value="${periodData.winUnit.seventhWinUnits!}" />
		    </td>
		  </tr>
		    <tr class="trw">
		    <td class="trhemaigray" align="right">七等奖追加奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.seventhAddPrize" value="${periodData.seventhAddPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国七等奖追加总注数：</td>
		    <td align="left">
		       <input type="text" name="periodData.seventhAddWinUnits" value="${periodData.seventhAddWinUnits!}" />
		    </td>
		  </tr>
		   <tr class="trw">
		    <td class="trhemaigray" align="right">八等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.eighthPrize" value="${periodData.prize.eighthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国八等奖总注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.eighthWinUnits" value="${periodData.winUnit.eighthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">12选2奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.select12to2Prize" value="${periodData.prize.select12to2Prize!}" />
		    </td>
		    <td class="trhemaigray" align="right">全国12选2总注数：</td>
		    <td align="left">
		       <input type="text" name="periodData.winUnit.select12to2WinUnits" value="${periodData.winUnit.select12to2WinUnits!}" />
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

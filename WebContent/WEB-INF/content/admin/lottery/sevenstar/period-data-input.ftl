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
		
		&nbsp;
		<select name="num6">
				<option value="">号码6</option>
				<#list 0..9 as c>
					<option <#if num6??&&num6==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select name="num7">
				<option value="">号码7</option>
				<#list 0..9 as c>
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
		     <td class="trhemaigray" align="right">一等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.firstWinUnits" value="${periodData.winUnit.firstWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">二等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.secondPrize" value="${periodData.prize.secondPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">二等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.secondWinUnits" value="${periodData.winUnit.secondWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">三等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.thirdPrize" value="${periodData.prize.thirdPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">三等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.thirdWinUnits" value="${periodData.winUnit.thirdWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">四等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.fourthPrize" value="${periodData.prize.fourthPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">四等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.fourthWinUnits" value="${periodData.winUnit.fourthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">五等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.fifthPrize" value="${periodData.prize.fifthPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">五等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.fifthWinUnits" value="${periodData.winUnit.fifthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">六等奖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.sixthPrize" value="${periodData.prize.sixthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">六等奖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.sixthWinUnits" value="${periodData.winUnit.sixthWinUnits!}" />
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
  	    <table>
  	  </td>
  	</tr>

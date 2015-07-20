  <tr class="trw">
    <td align="left" colspan="2">
      <input id="updatePeriodData" type="checkbox" checked="checked" name="updatePeriodData" value="true" /><label for="updatePeriodData">更新开奖数据</label>
       <label for="updatePeriodData">
       		<INPUT  value="抓取开奖信息" type="button" onclick="fetchAwardData();">
       </label>
     </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">开奖号码:</td>
    <td align="left">
    	    基本号码：
        <select id="num1" name="num1">
				<option value="">号码1</option>
				<#list 1..36 as c>
					<option <#if num1??&&num1==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select id="num2" name="num2">
				<option value="">号码2</option>
				<#list 1..36 as c>
					<option <#if num2??&&num2==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select id="num3" name="num3">
				<option value="">号码3</option>
				<#list 1..36 as c>
					<option <#if num3??&&num3==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select id="num4" name="num4">
				<option value="">号码4</option>
				<#list 1..36 as c>
					<option <#if num4??&&num4==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;
		<select id="num5" name="num5">
				<option value="">号码5</option>
				<#list 1..36 as c>
					<option <#if num5??&&num5==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		
		&nbsp;
		<select id="num6" name="num6">
				<option value="">号码6</option>
				<#list 1..36 as c>
					<option <#if num6??&&num6==c>selected="selected"</#if> value="${c}">${c!}</option>
				</#list>				
		</select>
		&nbsp;特别号码：
		<select id="num7" name="num7">
				<option value="">特号</option>
				<#list 1..36 as c>
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
		    	<input type="text" id="Prize_1" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">一等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_1" name="periodData.winUnit.firstWinUnits" value="${periodData.winUnit.firstWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">二等奖奖金：</td>
		    <td align="left">
		    	<input type="text" id="Prize_2" name="periodData.prize.secondPrize" value="${periodData.prize.secondPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">二等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_2" name="periodData.winUnit.secondWinUnits" value="${periodData.winUnit.secondWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">三等奖奖金：</td>
		    <td align="left">
		    	<input type="text" id="Prize_3" name="periodData.prize.thirdPrize" value="${periodData.prize.thirdPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">三等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_3" name="periodData.winUnit.thirdWinUnits" value="${periodData.winUnit.thirdWinUnits!}" />
		    </td>
		  </tr>
	
		  <tr class="trw">
		    <td class="trhemaigray" align="right">四等奖奖金：</td>
		    <td align="left">
		    	<input type="text" id="Prize_4" name="periodData.prize.fourthPrize" value="${periodData.prize.fourthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">四等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_4" name="periodData.winUnit.fourthWinUnits" value="${periodData.winUnit.fourthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">五等奖奖金：</td>
		    <td align="left">
		    	<input type="text" id="Prize_5" name="periodData.prize.fifthPrize" value="${periodData.prize.fifthPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">五等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_5" name="periodData.winUnit.fifthWinUnits" value="${periodData.winUnit.fifthWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">六等奖奖金：</td>
		    <td align="left">
		    	<input type="text" id="Prize_6" name="periodData.prize.sixthPrize" value="${periodData.prize.sixthPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">六等奖注数：</td>
		    <td align="left">
		    	<input type="text" id="PrizeUnits_6" name="periodData.winUnit.sixthWinUnits" value="${periodData.winUnit.sixthWinUnits!}" />
		    </td>
		  </tr>
		  
		  
		  <tr class="trw">
		    <td class="trhemaigray" align="right">好彩一奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.haocai1Prize" value="${periodData.prize.haocai1Prize!}" />
		    </td>
		    <td class="trhemaigray" align="right">好彩一注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.haocai1WinUnits" value="${periodData.winUnit.haocai1WinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">好彩二奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.haocai2Prize" value="${periodData.prize.haocai2Prize!}" />
		    </td>
		    <td class="trhemaigray" align="right">好彩二注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.haocai2WinUnits" value="${periodData.winUnit.haocai2WinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">好彩三奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.haocai3Prize" value="${periodData.prize.haocai3Prize!}" />
		    </td>
		     <td class="trhemaigray" align="right">好彩三注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.haocai3WinUnits" value="${periodData.winUnit.haocai3WinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">好彩1生肖奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.zodiacPrize" value="${periodData.prize.zodiacPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">好彩1生肖注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.zodiacWinUnits" value="${periodData.winUnit.zodiacWinUnits!}" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">好彩1季节奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.seasonPrize" value="${periodData.prize.seasonPrize!}" />
		    </td>
		     <td class="trhemaigray" align="right">好彩1季节注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.seasonWinUnits" value="${periodData.winUnit.seasonWinUnits!}" />
		    </td>
		  </tr>
		    <tr class="trw">
		    <td class="trhemaigray" align="right">好彩1方位奖金：</td>
		    <td align="left">
		    	<input type="text" name="periodData.prize.azimuthPrize" value="${periodData.prize.azimuthPrize!}" />
		    </td>
		    <td class="trhemaigray" align="right">好彩1方位注数：</td>
		    <td align="left">
		    	<input type="text" name="periodData.winUnit.azimuthWinUnits" value="${periodData.winUnit.azimuthWinUnits!}" />
		    </td>
		  </tr>
		 <tr class="trw">
   			 <td class="trhemaigray" align="right">销售总额：</td>
    		<td align="left">
    			<input type="text" id="totalSales" name="periodData.totalSales" value="${periodData.totalSales!}" />
    		</td>
    		<td class="trhemaigray" align="right">奖池奖金：</td>
    		<td align="left">
    			<input type="text" id="prizePool" name="periodData.prizePool" value="${periodData.prizePool!}" />
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
  <script type='text/javascript'>
  		function fetchAwardData(){
  		  var url = "${base}/admin/lottery/welfare36to7/period!fetchAwardData.action?id=${period.id}";
		    var jsonRequest = new Request.JSON({
		    	url: url, 
			    onComplete: function(resultObj, text){
					var xmlDoc = new ActiveXObject("Msxml2.DOMDocument.3.0");
				    xmlDoc.async = false;
				    xmlDoc.loadXML(resultObj.awardData); 
				    if (xmlDoc.parseError.errorCode != 0) {
					   //判断是否出错
					   var myErr = xmlDoc.parseError; 
					 } 
				    var LuckNum = xmlDoc.getElementsByTagName("LuckNum")[0].childNodes[0].nodeValue;
				    var LuckNumString = LuckNum.split("-");
				    var Luck1_6Num = LuckNumString[0].split(" ");
				    for(var i=0;i<Luck1_6Num.length;i++){
				      var id = "num"+parseInt(i+1);
				      var num = parseInt(Luck1_6Num[i],10);
				     document.getElementById(id).value=num;
				    }
				    
				    var Luck7Num = LuckNumString[1];
				    document.getElementById("num7").value=parseInt(Luck7Num,10);
				     
				    var TotalBuyAmount = xmlDoc.getElementsByTagName("TotalBuyAmount")[0].childNodes[0].nodeValue;
				    var AwardPool = xmlDoc.getElementsByTagName("AwardPool")[0].childNodes[0].nodeValue;
				    var AwardDetail = xmlDoc.getElementsByTagName("AwardDetail");
				    
				    document.getElementById("totalSales").value=TotalBuyAmount;
				    document.getElementById("prizePool").value=AwardPool;
				    
					for(var i=0;i<AwardDetail.length;i++){
					   var Prize="Prize_"+parseInt(i+1);
					   var PrizeUnits="PrizeUnits_"+parseInt(i+1);
					   var x = AwardDetail[i];
   					   document.getElementById(Prize).value=x.getAttribute("AwardAmount");
				       document.getElementById(PrizeUnits).value=x.getAttribute("AwardTotalCount");
				   }
					
		     }
			}).get({ 
			    '__t': new Date().getTime()
			});
  		}
  		
  		
  </script>

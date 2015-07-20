<head>
<meta name="decorator" content="tradeV1" />
<meta name="menu" content="${webapp.kjjg}"/>
</head>
<!--main-->
 
<style>
 /*------------开奖结果-------------*/
.kaijiang {height:35px; background:#0ca3d2; line-height:35px; padding:0 15px;}
.kjjgtab {border:1px solid #abcae7; color:#333;}
.kjjgtab tr:hover {background:#dae8f5;}
.kjjgtab tr td {padding:6px 5px;}
.kjjgtd00 {border-bottom:1px solid #abcae7; border-right:1px solid #abcae7;}
.kjjgtd01 {border-bottom:1px solid #dae8f5; border-right:1px solid #dae8f5;}
.d_left1{width:120px; border-left:1px solid #ccc;  height:36px; line-height:36px; float:left; display:block;text-align:center;  }
.d_leftrig1{width:120px; border-left:1px solid #ccc; border-right:1px solid #ccc;  height:36px; line-height:36px;float:left; display:block;text-align:center;}
 
.ssxsan02 {background:url(../images/hhgg.png) no-repeat -0px -206px;width:76px; height:24px; padding:0;cursor:pointer;border:none;display:block;}
.odds_change{position:absolute;top:3px;left:30px;background-color:#ccc;}
</style>
<script type="text/javascript" src="${base}/js/lottery/jczq/match_odds.js"></script> 
<div class="w1000" style="border:1px solid #CCC;">
  
<div id="note" style="width:1000px">
  <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab" id="jz_match">
  <tbody  id="matchTbody">
      <tr align="center" class="kjjgtr01">
        <td width="240"  colspan="2" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>公司名</strong></td>
        <td style="width:369px" " colspan="3"  bgcolor="#d5f6fd" class="kjjgtd00">初赔</td>
       	<td style="width:369px"  colspan="3"   bgcolor="#d5f6fd" class="kjjgtd00">终赔</td>
      </tr>
      <tr align="center">
      		<td  width="123" bgcolor="#d5f6fd" class="kjjgtd00"><strong>胜</strong></td>
      		<td width="123" bgcolor="#d5f6fd" class="kjjgtd00"><strong>平</strong></td>
     		<td width="123"  bgcolor="#d5f6fd" class="kjjgtd00"><strong>负</strong></td>
			<td width="123" bgcolor="#d5f6fd" class="kjjgtd00"><strong>胜</strong></td>
      		<td width="123" bgcolor="#d5f6fd" class="kjjgtd00"><strong>平</strong></td>
     		<td width="123" bgcolor="#d5f6fd" class="kjjgtd00"><strong>负</strong></td>
      </tr>
      </tr>
    </tbody>
    </table>
 </div>
    <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab">
    <tbody>
     <#assign endCount=0 />
			<#if allOddsList??>
				<#list allOddsList as oddsData>
					<tr  align="center" >		                	 
		                	<td  width="240" height="36" align="center" class="kjjgtd01">
		                			${oddsData.company}
		                	</td>
		                	<td width="400"><div><span class="d_left1">${oddsData.firstOdds[0]}</span><span class="d_left1">${oddsData.firstOdds[1]}</span><span class="d_leftrig1">${oddsData.firstOdds[2]}</span></div></td>
		                	<td width="400" id="oddsDate_td_${oddsData_index}">
		                			<div  style="position:relative;">
		                				<span class="d_left1">${oddsData.currentOdds[1]}</span><span class="d_left1">${oddsData.currentOdds[2]}</span><span class="d_leftrig1">${oddsData.currentOdds[3]}</span>
		                				<div  id="odds_change_${oddsData_index}" class="odds_change"></div>
		                				<div  id="change_content_${oddsData_index}"  style="display:none">${oddsData.odds}</div>
		                				<div  style="display:none"  id="odds_first_content_${oddsData_index}">${oddsData.firstOdds[0]},${oddsData.firstOdds[1]},${oddsData.firstOdds[2]}</div>
		                			</div>		
		                	</td>
		                	<script>							
							 				
								showChangeOdds(${oddsData_index});																														
							</script>	
		         			<script>
		         				
		         			</script>
					    </tr>
				</#list>
			</#if>
  </tbody>
</table>
 
</div>

<script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
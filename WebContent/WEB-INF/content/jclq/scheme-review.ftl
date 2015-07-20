<head>
<meta name="decorator" content="tradeV1" />
<meta name="menu" content="${webapp.kjjg}"/>
</head>
<!--main-->
<script>
	$(function() {
		$( "#startTime" ).datepicker({
		    dateFormat: 'yy-mm-dd',
			monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    dayNamesMin: ['日','一','二','三','四','五','六'],
			defaultDate: "+0w",
			changeMonth: true,
			changeYear: true
		});
	});
	$(function() {
		$( "#endTime" ).datepicker({
		    dateFormat: 'yy-mm-dd',
			monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    dayNamesMin: ['日','一','二','三','四','五','六'],
			defaultDate: "+0w",
			changeMonth: true,
			changeYear: true
		});
	});
</script>
<style>
 /*------------开奖结果-------------*/
.kaijiang {height:35px; background:#0ca3d2; line-height:35px; padding:0 15px;}
.kjjgtab {border:1px solid #abcae7; color:#333;}
.kjjgtab tr:hover {background:#dae8f5;}
.kjjgtab tr td {padding:6px 5px;}
.kjjgtd00 {border-bottom:1px solid #abcae7; border-right:1px solid #abcae7;}
.kjjgtd01 {border-bottom:1px solid #dae8f5; border-right:1px solid #dae8f5;}
.kjjgtd02 {border-bottom:1px solid #dae8f5; border-right:1px solid #abcae7;}
.kjjgtd03 {border-right:1px solid #dae8f5;}
.kjjgtd04 {border-bottom:1px solid #dae8f5;}
.kjjgtd05 {border-right:1px solid #abcae7;}
.ssxsan02 {background:url(../images/hhgg.png) no-repeat -0px -206px;width:76px; height:24px; padding:0;cursor:pointer;border:none;display:block;}
</style>
<div class="w1000" style="border:1px solid #CCC;">
  <div class="kaijiang white_FFF_14"><strong>开奖结果</strong></div>
  <div style="margin:10px;">
      <div class="zjmxtit">
		  	<ul>
			  <a href="${base}/jczq/scheme_review.html">
				<li>竞彩足球</li>
			  </a>
			  <a href="${base}/jclq/scheme_review.html">
				<li class="zjmxli01">竞彩篮球</li>
			  </a>
			  <a href="${base}/dczc/scheme_review.html">
				<li>北京单场</li>
			  </a>
			    <a href="${base}/results!index.action">
				<li>传统足球</li>
			  </a>
			</ul>
    </div>
	  <div style="width:978px;">
	 <form action="<@c.url value='/${lotteryType.key}/scheme!review.action' />" method="get" id="reviewForm">
	<div style="height:40px; line-height:40px;"><span style="float:left;"><strong>竞彩开奖比赛日期选择：</strong>
        <input id="startTime" name="startTime" type="text" value="${startTime!}" class="zjmxrl"/>
		至
		<input id="endTime" name="endTime" onclick="this.value='';" type="text" value="${endTime!}" class="zjmxrl"/>
	    <input type="submit" name="Submit" onclick="this.value='';" value="开始查询" class="chaxun" onclick="document.getElementById('reviewForm').submit();return false;"/> 
		<select name="matchDate" onchange="document.getElementById('endTime').value='';document.getElementById('reviewForm').submit();return false;">
				<#list dateList as date>
					<option <#if matchDate?? && matchDate==(date.toString('yyyyMMdd'))>selected="selected"</#if> value="${date.toString('yyyyMMdd')}">${date.toString('yyyy-MM-dd')}</option>
			    </#list>
		</select>
</span>
      <span class="yel_F60" style="float:right;">注：竞彩开奖比赛日期选择范围，指比赛的实际开赛时间</span></div>
      
	</form>
<div id="note" style="width:980px">
  <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab" id="jz_match">
  <tbody  id="matchTbody">
      <tr align="center" class="kjjgtr01">
        <td colspan="2" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赛事</strong></td>
        <td width="84" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>比赛时间</strong></td>
        <td width="91" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>主队</strong></td>
        <td width="91" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>客队</strong></td>
        <td width="42" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>比分</strong></td>
      <td colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>胜负</strong></td>
      <td colspan="3" bgcolor="#d5f6fd" class="kjjgtd00"><strong>让分胜负</strong></td>
      <td colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>胜分差</strong></td>
      <td colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>大小分</strong></td>
    </tr>
    <tr align="center">
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>让分</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td width="42" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
    </tr>
    </tbody>
    </table>
</div>    
    <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab">
    </tbody>
     <#assign endCount=0 />
			<#if matchMap??>
			
				<#list matchMap.entrySet() as entry>
					<tr class="tdlist" align="center">
						<td class="kjjgtd01" class="black_000" height="35" colspan="18">
							<strong>${entry.getKey()}（11:00——次日11:00）</strong>
						</td>
					</tr>
					<#assign SF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SF") />
					<#assign RFSF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@RFSF") />
					<#assign SFC=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SFC") />
					<#assign DXF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@DXF") />
					<#list entry.getValue() as match>
						<#assign rs=match.getResult(playType)!'' />
					    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
						
						 <tr  align="center" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="1">
		                	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
		                	<td height="7%" align="center" class="kjjgtd01">
		                			<label for="hide_cbx_${match.matchKey}">${match.matchKeyText}</label>
		                	</td>
		                	
					      <td width="8%" class="kjjgtd01" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
		                  <td class="kjjgtd01">${match.matchTime?string('MM-dd HH:mm')}</td>
					      <td width="91" class="kjjgtd01"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
					      <td width="91" class="kjjgtd01"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
					      <#assign handicapClass=''/>
						    <#if match.singleHandicap??>
						    	<#if match.singleHandicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.singleHandicap lt 0>
						    		<#assign handicapClass='green'/>
						    	</#if>
						    </#if>
					      <#if match.cancel>
				        		 <td width="42" class="kjjgtd01">取消</td>
				        		 <td width="42" class="kjjgtd02">取消</td>
				        		 <td width="42" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="42" class="kjjgtd02">1.00</td>
							     <td width="42" class="kjjgtd01 rc1"></td>
							     <td width="42" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="42" class="kjjgtd02">1.00</td>
							     <td width="42" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="42" class="kjjgtd02">1.00</td>
							     <td width="42" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="42"  class="kjjgtd02">1.00</td>
				        	<#elseif match.ended>
				        		<td class="kjjgtd01">
				        		    <#if match.homeScore?? && match.guestScore??>
				        				<strong>${match.homeScore}:${match.guestScore}</strong>
				        			</#if>
				        		 </td>			        		 
				        		 <td width="42" class="kjjgtd01 rc1"><strong><#if match.getResult(SF)??>${match.getResult(SF).text}</#if></strong></td>
							     <td width="42" class="kjjgtd02"><#if match.getResultSp(SF)??>${match.getResultSp(SF)?string('0.00')}</#if></td>
							     <td width="42" class="kjjgtd01 ${handicapClass}">${match.singleHandicap!}</td>
							     <td width="42" class="kjjgtd01 rc1"><strong><#if match.getResult(RFSF)??>${match.getResult(RFSF).text}</#if></strong></td>
							     <td width="42" class="kjjgtd02"><#if match.getResultSp(RFSF)??>${match.getResultSp(RFSF)?string('0.00')}</#if></td>
							     <td width="42" class="kjjgtd01 rc1"><strong><#if match.getResult(SFC)??>${match.getResult(SFC).text}</#if></strong></td>
							     <td width="42" class="kjjgtd02"><#if match.getResultSp(SFC)??>${match.getResultSp(SFC)?string('0.00')}</#if></td>
							     <td width="42" class="kjjgtd01 rc1"><strong><#if match.getResult(DXF)??>${match.getResult(DXF).text}</#if></strong></td>
							     <td width="42" class="kjjgtd02"><#if match.getResultSp(DXF)??>${match.getResultSp(DXF)?string('0.00')}</#if></td>
							 <#else>
							     <td width="42" class="kjjgtd01">&nbsp;</td>
				        		 <td width="42" class="kjjgtd02">&nbsp;</td>
				        		 <td width="42" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="42" class="kjjgtd02">&nbsp;</td>
							     <td width="42" class="kjjgtd01 rc1"></td>
							     <td width="42" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="42" class="kjjgtd02">&nbsp;</td>
							     <td width="42" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td  width="42" class="kjjgtd02">&nbsp;</td>
							     <td width="42" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="42" class="kjjgtd02">&nbsp;</td>
				          </#if>
					    </tr>
					</#list>
				</#list>
			</#if>
  </tbody>
</table>
<div style="height:40px; line-height:40px;"><span class="yel_F60">注：&quot;过关终赔&quot;仅指比赛截止投注时的最终变化的过关赔率，仅供参考。方案派奖以方案详情中&quot;查看票样&quot;中的赔率为准。</span></div>
</div>
  </div>
</div>
<script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
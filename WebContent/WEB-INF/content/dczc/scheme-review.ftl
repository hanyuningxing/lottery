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
				<li>竞彩篮球</li>
			  </a>
			  <a href="${base}/dczc/scheme_review.html">
				<li class="zjmxli01">北京单场</li>
			  </a>
			    <a href="${base}/results_index.html">
				<li>传统足球</li>
			  </a>
			</ul>
    </div>
	  <div style="width:978px;">
	   <form action="<@c.url value='/${lotteryType.key}/scheme!review.action' />" method="get" id="reviewForm">
	<div style="height:40px; line-height:40px;"><span style="float:left;"><strong>北京单场：</strong>
		<select name="periodId" onchange="document.getElementById('reviewForm').submit();return false;">
			<#list periods ?sort_by(["periodNumber"])?reverse as p>
				<option <#if periodId==p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber}期</option>
			</#list>	
		</select>
		
</span>
      <span class="yel_F60" style="float:left;"><strong>&nbsp&nbsp开奖SP</strong></span></div>
      
	</form>
		
<div id="note" style="width:980px">
  <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab" id="jz_match">
  <tbody  id="matchTbody">
      <tr align="center" class="kjjgtr01">
        <td width="132" colspan="2" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赛事</strong></td>
        <td width="64" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>比赛时间</strong></td>
        <td width="75" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>主队</strong></td>
        <td width="75" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>客队</strong></td>
        <td width="41" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>半场<br />
比分</strong></td>
        <td width="39" rowspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>全场<br />
比分</strong></td>
      <td width="80"  colspan="3" bgcolor="#d5f6fd" class="kjjgtd00"><strong>让球胜平负</strong></td>
      <td width="100"  colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>总进球数</strong></td>
      <td width="73"  colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>上下单双</strong></td>
      <td width="82"  colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>比分</strong></td>
      <td style="width:80px" colspan="2" bgcolor="#d5f6fd" class="kjjgtd00"><strong>半全场胜平负</strong></td>
    </tr>
    <tr align="center">
      <td style="width:32px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td style="width:37px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td style="width:31px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>让球</strong></td>
      <td style="width:32px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td style="width:39px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td style="width:32px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td style="width:37px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td style="width:32px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td style="width:38px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
      <td style="width:34px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>彩果</strong></td>
      <td style="width:38px" bgcolor="#d5f6fd" class="kjjgtd00"><strong>赔率</strong></td>
    </tr>
    </tbody>
    </table>
 </div>
    <table width="100%" align="center" border="0" cellspacing="0" cellpadding="0" class="kjjgtab">
    <tbody>
     <#assign endCount=0 />
			<#if matchMap??>
				<#list matchMap.entrySet() as entry>
					<tr class="tdlist" align="center" >
						<td class="kjjgtd01" class="black_000" height="35" colspan="18">
							<strong>${entry.getKey()}（11:00——次日11:00）</strong>
						</td>
					</tr>
					<#assign SPF=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@SPF") />
					<#assign ZJQS=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@ZJQS") />
					<#assign SXDS=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@SXDS") />
					<#assign BF=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@BF") />
					<#assign BQQSPF=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@BQQSPF") />
					<#list entry.getValue() as match>
						<#assign rs=match.getResult(playType)!'' />
					    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
						
						 <tr  align="center" id="tr_${match.lineId}" _lid="${match.lineId}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="1">
		                	<input type="hidden" id="key_${match.lineId}" value=""/>
		                	<td width="60" height="7%" align="center" class="kjjgtd01">
		                			<label for="hide_cbx_${match.lineId}">${(match.lineId+1)?string('000')}</label>
		                	</td>
		                	
					      <td width="70" class="kjjgtd01" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
		                  <td width="73" class="kjjgtd01">${match.matchTime?string('MM-dd HH:mm')}</td>
					      <td width="92" class="kjjgtd01"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
					      <td width="92" class="kjjgtd01"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
					      <#assign handicapClass=''/>
						    <#if match.handicap??>
						    	<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green'/>
						    	</#if>
						    </#if>
					      <#if match.cancel>
				        		 <td width="48" class="kjjgtd01">取消</td>
				        		 <td width="48" class="kjjgtd02">取消</td>
				        		 <td width="48" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="48" class="kjjgtd02">1.00</td>
							     <td width="48" class="kjjgtd01 rc1"></td>
							     <td width="48" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="48" class="kjjgtd02">1.00</td>
							     <td width="48" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="48" class="kjjgtd02">1.00</td>
							     <td width="48" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="48" class="kjjgtd02">1.00</td>
							     <td width="48" class="kjjgtd01 rc1"><strong>取消</strong></td>
							     <td width="48" class="kjjgtd04">1.00</td>
				        	<#elseif match.ended>
				        		 <td width="48" class="kjjgtd01">
				        		    <#if match.halfHomeScore?? && match.halfHomeScore??>
				        				<strong>${match.halfHomeScore}:${match.halfGuestScore}</strong>
				        			</#if>
				        		 </td>
				        		 <td width="48" class="kjjgtd02">
				        			<#if match.fullHomeScore?? && match.fullGuestScore??>
				        				<strong>${match.fullHomeScore}:${match.fullGuestScore}</strong>
				        			</#if>
				        		 </td>
				        		 <td width="39" class="kjjgtd01 rc1"><strong><#if match.getResult(SPF)??>${match.getResult(SPF).text}</#if></strong></td>
							     <td width="39" class="kjjgtd01"><#if match.getResultSp(SPF)??>${match.getResultSp(SPF)?string('0.00')}</#if></td>
							     <td width="39" class="kjjgtd02 ${handicapClass}">${match.handicap!}</td>
							     <td width="39" class="kjjgtd01 rc1"><strong><#if match.getResult(ZJQS)??>${match.getResult(ZJQS).text}</#if></strong></td>
							     <td width="39" class="kjjgtd02"><#if match.getResultSp(ZJQS)??>${match.getResultSp(ZJQS)?string('0.00')}</#if></td>							     
							     <td width="39" class="kjjgtd01 rc1"><strong><#if match.getResult(SXDS)??>${match.getResult(SXDS).text}</#if></strong></td>
							     <td width="39" class="kjjgtd02"><#if match.getResultSp(SXDS)??>${match.getResultSp(SXDS)?string('0.00')}</#if></td>
							     <td width="39" class="kjjgtd01 rc1"><strong><#if match.getResult(BF)??>${match.getResult(BF).text}</#if></strong></td>
							     <td width="39" class="kjjgtd02"><#if match.getResultSp(BF)??>${match.getResultSp(BF)?string('0.00')}</#if></td>
							     <td width="39" class="kjjgtd01 rc1"><strong><#if match.getResult(BQQSPF)??>${match.getResult(BQQSPF).text}</#if></strong></td>
							     <td width="39" class="kjjgtd04"><#if match.getResultSp(BQQSPF)??>${match.getResultSp(BQQSPF)?string('0.00')}</#if></td>
							 <#else>
							     <td width="44" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
				        		 <td width="44" class="kjjgtd02">&nbsp;</td>
				        		 <td width="44" class="kjjgtd01 rc1"></td>
				        		 <td width="41" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="45" class="kjjgtd02">&nbsp;</td>							     
							     <td width="41" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="45" class="kjjgtd02">&nbsp;</td>
							     <td width="41" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="45" class="kjjgtd02">&nbsp;</td>
							     <td width="41" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="45" class="kjjgtd02">&nbsp;</td>
							     <td width="41" class="kjjgtd01 rc1"><strong>&nbsp;</strong></td>
							     <td width="45" class="kjjgtd04">&nbsp;</td>
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
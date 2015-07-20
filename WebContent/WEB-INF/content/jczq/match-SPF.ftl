<script type="text/javascript" src="${base}/js/art/artDialog.js?skin=twitter"></script>
<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<script type="text/javascript" src="${base}/js/lottery/jczq/odds.js"></script> 
<style type="text/css">
#zhishu {border:3px solid #ccc; color:#333; width:300px; font-family:Tahoma, '宋体'; font-size:12px; position:absolute;background:#FFF;}
.zstit {text-align:center; padding:8px 0; background:#1898ba; color:#FFF; font-size:14px; margin-bottom:1px;}
 
.mhtool .lishi{display:block;color:white;background:#66ccff;font-weight:bold;padding:3px;width:60px;margin-left:10px;margin-top:5px;text-align:center;}
.mhtool .btn{display:block;color:white;background:#ff6c6c;font-weight:bold;padding:3px;width:60px;margin-top:8px;margin-left:40px;text-align:center;}
.zhishutab {color:#333;}
.zhishutab tr td {border-bottom:1px solid #e4e4e4;}
.coverDiv {
    background-color: #000000;
    height: 1000px;
    left: 0;
    opacity: 0.5;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 7000;
}
.stepTipDiv {
    left: 50%;
    margin-left: -480px;
    position: absolute;
    top: 0;
    width: 960px;
    z-index: 7001;
}
.step1 {
	background-image: url("${base}/pages/guide/jczq/001.png");
    background-repeat: no-repeat;
    height: 200px;
    position: absolute;
    width: 480px;
    background-position: 0 0;
    left: 169px;
    top: 285px;
}
.step2 {
 	background-image: url("${base}/pages/guide/jczq/002.png");
    background-repeat: no-repeat;
    height: 200px;
    position: absolute;
    width: 480px;
    background-position: 0 0;
    left: 315px;
    top: 450px;
}
.step1 .guideClose {
 	right: 5px;
    top: 0px;
    cursor: pointer;
    display: block;
    height: 25px;
    position: absolute;
    width: 25px;
}
.step1 .guideBtn {
 	left: 319px;
    top: 103px;
    cursor: pointer;
    display: block;
    height: 40px;
    position: absolute;
    width: 90px;
}
.step2 .guideClose {
 	cursor: pointer;
    display: block;
    height: 25px;
    position: absolute;
    right: 32px;
    top: 0;
    width: 25px;
}
.step2 .guideBtn {
 	 cursor: pointer;
    display: block;
    height: 40px;
    left: 209px;
    position: absolute;
    top: 108px;
    width: 90px;
}
</style>
    <script type="text/javascript">
	       $(function() {
	       		 var showGuide=function(){
	       		     var showGuide_jczq = Cookies.get("showGuide_jczq")
	       		     if('1'===showGuide_jczq){
		        		  
		             }else{
		             	  $("#jq_guide_cover").css("height",$(document).height());
		                  $("#jq_guide_step1").show();
		                  $("#jq_guide_cover").show();
		                  Cookies.set({name: 'showGuide_jczq', value: '1', expires: '100000000000'});
		             }
	             }
	             var closeGuide=function(){
	                  $("#jq_guide_step1").hide();
	                  $("#jq_guide_step2").hide();
	                  $("#jq_guide_cover").hide();
	             }
	             $("#jq_guide_step1 .guideClose").bind("click", function(event) {
	                 //关闭
	                 closeGuide();
	             });
	              $("#jq_guide_step2 .guideClose").bind("click", function(event) {
	             	 //关闭
	             	 closeGuide();
	             });
	             $("#jq_guide_step1 .guideBtn").bind("click", function(event) {
	             		 $("#jq_guide_step1").hide();
	             		 $("#jq_guide_step2").show();
	             });
	             $("#jq_guide_step2 .guideBtn").bind("click", function(event) {
	             	  //关闭
	             	  closeGuide();
	             });
	             showGuide();
	       });
	</script>
	<!--table-->
	<div class="stepTipDiv">
		<div style="display: none;" class="step1" id="jq_guide_step1"><i class="guideClose"></i><i class="guideBtn"></i></div>
		<div style="display: none;" class="step2" id="jq_guide_step2"><i class="guideClose"></i><i class="guideBtn"></i></div>
	</div>
	<div id="note" style="width:710px;z-index:9999">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sssx02">
		<tbody>
	      <tr class="tdduilist" align="center">
	        <td rowspan="2" height="43" width="22">1场</td>
	        <td rowspan="2" width="85">赛事</td>
	        <td rowspan="2" width="50">开赛</br><span style="color:red">截止</span></td>
	        <td rowspan="2" width="110">主队</td>
	        <td rowspan="2" width="28">让球 </td>
	        <td rowspan="2" width="96"  class="tdr1px">客队</td>
	       
	        <td colspan="3" width="154" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">非让球即时赔率/投注区</td>
	        <td colspan="3" width="150" class="tdr1px" style="background:#e4f7ff;">让球即时赔率/投注区</td>
	       <#if !matchDate??> <td rowspan="2" width="23" class="tdr1px">赔</br>率</td></#if>
	        </tr>
	      <tr class="tdduilist" align="center">
	        <td style="background:#f2e2b7; color:#000;" width="50">胜</td>
	        <td style="background:#f2e2b7; color:#000;" width="50">平</td>
	        <td class="tdr1px" style="background:#f2e2b7; color:#000;" width="50">负</td>
	        <td style="background:#D5F6FD; color:#000;" width="50">胜</td>
	        <td style="background:#D5F6FD; color:#000;" width="50">平</td>
	        <td class="tdr1px" style="background:#D5F6FD; color:#000;" width="50">负</td>
	      </tr>
	      
	       
	    </tbody> 
    </table>
    </div>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="sssx02">
      <tbody id="matchTbody">      
		  <!--对阵开始-->
		  <#assign endCount=0 />
			<#if matchMap??>				
				<#assign SPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@SPF") />
				<#assign RQSPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@RQSPF") />
				<#assign i=-1>
				<#list matchMap.entrySet() as entry>				
					<#assign i=i+1>	
					<tr class="tdlist" align="center">
						<td class="black_000" height="35" colspan="13">
							<strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
						</td>
					</tr>
					
					<#list entry.getValue() as match>
						<#if match_index%2==0>
							<#assign trClass='tdlist' />
						<#else>
							<#assign trClass='tdlist1' />
						</#if>
						<#if spfRateData??>
							<#assign spfRateMap=spfRateData.get(match.matchKey)!'' />
						<#else>
							<#assign spfRateMap='' />
						</#if>
						<#if rqspfRateData??>
							<#assign rqspfRateMap=rqspfRateData.get(match.matchKey)!'' />
						<#else>
							<#assign rqspfRateMap='' />
						</#if>
						<#if 'SINGLE'==passMode>
							<#assign rateMapTemp=spfRateMap>
							<#assign rqspfRateMap=rqspfRateMap>
							<#assign spfRateMap=rateMapTemp>
						</#if>						
						
						<#assign saleEnd_SPF=match.isNotDisplay(SPF,passMode)/>
						<#assign saleEnd_RQSPF=match.isNotDisplay(RQSPF,passMode)/>
						<#assign open_SPF=match.isOpen(SPF,passMode)/>
						<#assign open_RQSPF=match.isOpen(RQSPF,passMode)/>
						
					    <!--onmouseover="this.className='trhover'" onmouseout="this.className='${trClass}'" -->
					   
					    <tr class="${trClass}" align="center" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd_SPF && saleEnd_RQSPF>1<#else>0</#if>">		                	
		                	
		                	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
		                	<#if saleEnd_SPF && saleEnd_RQSPF><#assign endCount=endCount+1 /></#if>
		                	<td id="td_id_${match.matchKey}" width="22" height="38">${match.lineId?string('000')}<span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
		                	<td width="85" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
		                	<td width="50" id="td_t_${match.matchKey}">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getWebOfficialEndTime()?string('HH:mm')}</span></td>
		                	<td width="110" id="td_h_${match.matchKey}" class="dzrig char14"  onmouseover="showMatchHisTool(this)">
		                			<div style="position:relative;display: inline;">
		                			  <div class="mhtool" style=" display:none;width:100px; height:80px;margin: 0 auto;position:absolute;bottom:0px;right:30px;">
										  <div style="position:relative;">
										    <div style="position:absolute; top:25px; left:10px;"> <a href="#" class="round_a03" onclick="showMatchHisTips('${match.matchKey}','home','home');return false;">主场</a> </div>
										    <div  style="position:absolute; top:25px; left:50px;"> <a href="#" class="round_a03"  onclick="showMatchHisTips('${match.matchKey}','home','guest');return false;">往绩</a> </div>
										    <div  style="position:absolute; top:25px; left:90px;"> <a href="#" class="round_a03" onclick="showMatchHisTips('${match.matchKey}','all','all');return false;">交锋</a> </div>
								 
										    <div  style="position:absolute; top:60px; left:58px;">
										      <div class="round_add"  onclick="hideMatchHisTips();return false;"></div>
										    </div>
										  </div>
										</div>
		                			 <span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span>
		                			</div>
		                		</td>
						    <#assign handicapClass=''/>
						    <#if match.handicap??>
						    	<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green'/>
						    	</#if>
						    </#if>
						    <td width="28" id="td_hd_${match.matchKey}" class="${handicapClass}"><strong><span id="handicap_${match.matchKey}">${match.handicap!}</span></strong></td>
						    <td width="110" id="td_g_${match.matchKey}" class="dzleft char14" onmouseover="showMatchHisTool(this)" style="position:relative;z-index:1;">
						    	 <div class="mhtool" style=" display:none;width:100px; height:80px;margin: 0 auto;position:absolute;bottom:0px;right:20px;">
												  <div style="position:relative;">
												    <div style="position:absolute; top:25px; left:10px;"> <a href="#" class="round_a03" onclick="showMatchHisTips('${match.matchKey}','guest','home');return false;">客场</a> </div>
												    <div style="position:absolute; top:25px; left:50px;"> <a href="#" class="round_a03" onclick="showMatchHisTips('${match.matchKey}','guest','guest');return false;">往绩</a> </div>
												    <div style="position:absolute; top:25px; left:90px;"> <a href="#" class="round_a03" onclick="showMatchHisTips('${match.matchKey}','all','all');return false;">交锋</a> </div>
												 
												    <div style="position:absolute; top:60px; left:58px;">
												      <div class="round_add" onclick="hideMatchHisTips();return false;"></div>
												    </div>
												  </div>
											</div>
						    	 		<span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span>
						   		</div>
						    </td>
		                	
		                	
		                	<#--非让球胜平负-->		                	
							<#assign rs=match.getResult(SPF)!'' />
						    <#if rs!=''><#assign rs_sp=match.getResultSp(SPF)!'' /><#else><#assign rs_sp='' /></#if>
						    <#if open_SPF>
					            <#list itemArr as item>
					            	<td width="50" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_SPF>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');"</#if>>
					            	 <#assign spId='SPF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='SPF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if spfRateMap?? && spfRateMap!=''>
											<#assign rateItem=spfRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd_SPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>						               	 	
					            	   </#if>
					            	</td>
					            </#list>
				            <#else>
				            	<td class="tdyelnor" colspan="${itemArr?size}">未开售</td>
				            </#if>
						    <#--让球胜平负-->
						    <#assign rs=match.getResult(RQSPF)!'' />
						    <#if rs!=''><#assign rs_sp=match.getResultSp(RQSPF)!'' /><#else><#assign rs_sp='' /></#if>
						    <#if open_RQSPF>
					            <#list itemArr as item>
					            	<td  width="50" class="tdnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_RQSPF>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');"</#if>>
					            	 <#assign spId='RQSPF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='RQSPF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if rqspfRateMap?? && rqspfRateMap!=''>
											<#assign rateItem=rqspfRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd_RQSPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>					               	 		
					            	   </#if>
					            	</td>
					            </#list>
					        <#else>
				            	<td class="tdnor" colspan="${itemArr?size}">未开售</td>
				            </#if>
				            <#if !matchDate??><td class="tdnor" style="font-size:12px" width="25" id="_${match.matchDate}_${match.lineId}_allodds_pos" ><a onmouseover="showAsiaOddsTips(this, ${match.lineId}, ${match.matchDate})" href="${base}/jczq/matchhistory!allOdds.action?period=${match.matchDate}&matchLine=${match.lineId}&t=1">亚</a><br/><a   href="${base}/jczq/matchhistory!allOdds.action?period=${match.matchDate}&matchLine=${match.lineId}" onmouseover="showOddsTips(this, ${match.lineId},${match.matchDate})"  >欧</a></td></#if>
							
						</tr>	
						<tr align="center" ><td   colspan="14" id="_${match.matchKey}_matchHis"></td></tr>					
						<script>							
							var tr_id = "tr_${match.matchKey}";						
							changeBgColor(tr_id);																																
						</script>						
					</#list>					
				</#list>
			</#if>
		</tbody>
	</table>


<script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
 
<script type="text/javascript" src="${base}/js/lottery/jczq/all_odds.js"></script> 
<script>
	function loadAsiaOddsJs(){
			var scriptTag = document.getElementById("jczq_asia_odds_js"); 
			var oHead = document.getElementsByTagName('HEAD').item(0); 
			var oScript= document.createElement("script"); 
			if(scriptTag){
			    oHead.removeChild(scriptTag); 
			}
			oScript.id = "jczq_asia_odds_js"; 
			oScript.type = "text/javascript"; 
			var oScript_src="${base}/js/odds/jczq/jczq_asia_odds.js?t="+new Date();
			oScript.src=oScript_src; 
			oHead.appendChild(oScript);	
	}
		
  
	function loadOddsJs(){
		var scriptTag = document.getElementById("jczq_odds_js"); 
		var oHead = document.getElementsByTagName('HEAD').item(0); 
		var oScript= document.createElement("script"); 
		if(scriptTag){
		    oHead.removeChild(scriptTag); 
		}
		oScript.id = "jczq_odds_js"; 
		oScript.type = "text/javascript"; 
		var oScript_src="${base}/js/odds/jczq/jczq_odds.js?t="+new Date();
		oScript.src=oScript_src; 
		oHead.appendChild(oScript);	
	}
	
	function loadOddsChangeJs(){
		var scriptTag = document.getElementById("jczq_odds_change_js"); 
		var oHead = document.getElementsByTagName('HEAD').item(0); 
		var oScript= document.createElement("script"); 
		if(scriptTag){
		    oHead.removeChild(scriptTag); 
		}
		oScript.id = "jczq_odds_change_js"; 
		oScript.type = "text/javascript"; 
		var oScript_src="http://61.147.127.238:8090/js/odds/jczq/odds_change.js?t="+new Date();
		oScript.src=oScript_src; 
		oHead.appendChild(oScript);	
		changeOddsDataMap();
	}

	
	//赔率有变化  改变Map里变化了的数据
	function changeOddsDataMap() {
		if(typeof odds_change_data != 'undefined' && odds_change_data.length > 0) {
			for(var i=0, maxSize=odds_change_data.length; i<maxSize; i++) {
				var date = odds_change_data[i][0];
				var lineid = odds_change_data[i][1];
				var company = odds_change_data[i][2];
				if(typeof odds_data_map != 'undefined')
					odds_data_map[date + "_" + lineid + "_" + company] = odds_change_data[i];
				else {
					buildMap();
					odds_data_map[date + "_" + lineid + "_" + company] = odds_change_data[i];
				}
					
			}
		}
	}
		
	$(function(){
		 loadOddsJs();	
		 setInterval(loadOddsChangeJs, 20000);	
		loadAsiaOddsJs();
	})
</script>

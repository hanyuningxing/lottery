<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<style type="text/css">
#zhishu {border:3px solid #ccc; color:#333; width:300px; font-family:Tahoma, '宋体'; font-size:12px; position:absolute;background:#FFF;}
.zstit {text-align:center; padding:8px 0; background:#1898ba; color:#FFF; font-size:14px; margin-bottom:1px;}
.zhishutab {color:#333;}
.zhishutab tr td {border-bottom:1px solid #e4e4e4;}
</style>
	<!--table-->
	<div id="note" style="width:710px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sssx02">
		<tbody>
	      <tr class="tdduilist" align="center">
	        <td  height="43" width="22">场</td>
	        <td  width="85">赛事</td>
	        <td  width="50">开赛</br><span style="color:red">截止</span></td>
	        <td  width="110">主队</td>
	        <td  width="28"></td>
	        <td  class="tdr1px" width="103">客队</td>
	        <td  width="300" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">投注区</td>
	    <#if !matchDate??><td rowspan="2" width="23" class="tdr1px">赔</br>率</td></#if>
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
				<#list matchMap.entrySet() as entry>	
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
						<#assign saleEnd_SPF=match.isNotDisplay(SPF,passMode)/>
						<#assign saleEnd_RQSPF=match.isNotDisplay(RQSPF,passMode)/>
						<#assign open_SPF=match.isOpen(SPF,passMode)/>
						<#assign open_RQSPF=match.isOpen(RQSPF,passMode)/>
						
					    <!--onmouseover="this.className='trhover'" onmouseout="this.className='${trClass}'" -->
					    <#if match.handicap==1 || match.handicap==-1>
					    <tr class="${trClass}" align="center" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd_SPF && saleEnd_RQSPF>1<#else>0</#if>">
		                	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
		                	<#if saleEnd_SPF && saleEnd_RQSPF><#assign endCount=endCount+1 /></#if>
		                	<td id="td_id_${match.matchKey}" width="22" height="38">${match.lineId?string('000')}<span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
		                	<td width="85" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
		                	<td width="50" id="td_t_${match.matchKey}">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getWebOfficialEndTime()?string('HH:mm')}</span></td>
		                	<td width="110" id="td_h_${match.matchKey}" class="dzrig char14"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
						    <#assign handicapClass=''/>
						    <#if match.handicap??>
						    	<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green'/>
						    	</#if>
						    </#if>
						    <td width="28" id="td_hd_${match.matchKey}" class="${handicapClass}"><strong><span id="handicap_${match.matchKey}" style="display:none">${match.handicap!}</span></strong></td>
						    <td width="110" id="td_g_${match.matchKey}" class="dzleft char14"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
		                	<#if match.handicap?? && match.handicap==-1>
			                	<#--非让球胜平负-->		                	
								<#assign rs=match.getResult(SPF)!'' />
							    <#if rs!=''><#assign rs_sp=match.getResultSp(SPF)!'' /><#else><#assign rs_sp='' /></#if>
							    <#if open_SPF>
					            <#list itemArr as item>
					            		<#if item!="WIN">
					            			<#assign isNone="none" >
					            		<#else>
					            			<#assign isNone="" >
					            		</#if>
						            	<td width="150" style='display:${isNone}' class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_SPF>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF', true);"</#if>>
						            	 <#assign spId='SPF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='SPF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if spfRateMap?? && spfRateMap!=''>
												<#assign rateItem=spfRateMap.get(item.name())!'' />
											<#else>
												<#assign rateItem='' />
											</#if>
											
											<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
							        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span><span>&nbsp&nbsp&nbsp主胜</span></#if>
							        		<#else>
							               	 	<#if !saleEnd_SPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF' ,true);" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}</span><span>&nbsp&nbsp&nbsp主胜<#else>0.00</#if></span>
						            	   </#if>
						            	  
						            	</td>
					            	
					            </#list>
					             <#else>
					            	<td class="tdyelnor">未开售</td>
					            </#if>
							    <#--让球胜平负-->
							    <#assign rs=match.getResult(RQSPF)!'' />
							    <#if rs!=''><#assign rs_sp=match.getResultSp(RQSPF)!'' /><#else><#assign rs_sp='' /></#if>
							    <#if open_RQSPF>
					           		 <#list itemArr as item>
					            		<#if item!="LOSE">
					            			<#assign isNone="none" >
					            		<#else>
					            			<#assign isNone="" >
					            		</#if>
						            	<td width="150" style='display:${isNone}' class="tdnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_RQSPF>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF', true);"</#if>>
						            	 <#assign spId='RQSPF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='RQSPF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if rqspfRateMap?? && rqspfRateMap!=''>
												<#assign rateItem=rqspfRateMap.get(item.name())!'' />
											<#else>
												<#assign rateItem='' />
											</#if>
											
											<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
							        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span><span>&nbsp&nbsp&nbsp客不败</span></#if>
							        		<#else>
							               	 	<#if !saleEnd_RQSPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF', true);" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}</span><span>&nbsp&nbsp&nbsp客不败<#else>0.00</#if></span>
						            	   </#if>
						            	</td>
					            	
					            </#list>
					            <#else>
					            	<td class="tdnor">未开售</td>
					            </#if>
					          </#if>  
					          
					          <#if match.handicap?? && match.handicap==1>			                
							    <#--让球胜平负-->
							    <#assign rs=match.getResult(RQSPF)!'' />
							    <#if rs!=''><#assign rs_sp=match.getResultSp(RQSPF)!'' /><#else><#assign rs_sp='' /></#if>
							    <#if open_RQSPF>
					           	 <#list itemArr as item>
					            		<#if item!="WIN">
					            			<#assign isNone="none" >
					            		<#else>
					            			<#assign isNone="" >
					            		</#if>
						            	<td width="150" style='display:${isNone}' class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_RQSPF>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF', true);"</#if>>
						            	 <#assign spId='RQSPF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='RQSPF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if rqspfRateMap?? && rqspfRateMap!=''>
												<#assign rateItem=rqspfRateMap.get(item.name())!'' />
											<#else>
												<#assign rateItem='' />
											</#if>
											
											<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
							        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span><span>&nbsp&nbsp&nbsp主不败</span></#if>
							        		<#else>
							               	 	<#if !saleEnd_RQSPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF', true);" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}</span><span>&nbsp&nbsp&nbsp主不败</span><#else>0.00</#if></span>
						            	   </#if>
						            	</td>
					            	
					            </#list>
					            <#else>
					            	<td class="tdnor">未开售</td>
					            </#if>
					            	<#--非让球胜平负-->		                	
								<#assign rs=match.getResult(SPF)!'' />
							    <#if rs!=''><#assign rs_sp=match.getResultSp(SPF)!'' /><#else><#assign rs_sp='' /></#if>
							    <#if open_SPF>
					            <#list itemArr as item>
					            		<#if item!="LOSE">
					            			<#assign isNone="none"/>
					            		<#else>
					            			<#assign isNone=""/>
					            		</#if>
						            	<td width="150" style='display:${isNone}' class="tdnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_SPF>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF', true);"</#if>>
						            	 <#assign spId='SPF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='SPF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if spfRateMap?? && spfRateMap!=''>
												<#assign rateItem=spfRateMap.get(item.name())!'' />
											<#else>
												<#assign rateItem='' />
											</#if>
											
											<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
							        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span><span>&nbsp&nbsp&nbsp客胜</span></#if>
							        		<#else>
							               	 	<#if !saleEnd_SPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF',true);" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}</span><span>&nbsp&nbsp&nbsp客胜</span><#else>0.00</#if></span>
						            	   </#if>
						            	</td>					            	
					            </#list>
					            <#else>
					            	<td class="tdyelnor">未开售</td>
					            </#if>
					          </#if>  
					         <#if !matchDate??> <td class="tdnor" style="font-size:12px" width="25"><a onmouseover="showAsiaOddsTips(this, ${match.lineId}, ${match.matchDate})">亚</a><br/><a onmouseover="showOddsTips(this, ${match.lineId}, ${match.matchDate})">欧</a></td></#if>
					      
						</tr>
						</#if>
						<script>							
							var tr_id = "tr_${match.matchKey}";						
							changeBgColor(tr_id);																																
						</script>						
					</#list>						
				</#list>
			</#if>
		</tbody>
	</table>

<input type="hidden" id="is2C1"/>
<script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
<script type="text/javascript" src="${base}/js/lottery/jczq/odds.js"></script> 
<script>
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
	})
</script>
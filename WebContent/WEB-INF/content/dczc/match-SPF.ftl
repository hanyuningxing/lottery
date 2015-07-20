<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<style type="text/css">
#zhishu {border:3px solid #ccc; color:#333; width:300px; font-family:Tahoma, '宋体'; font-size:12px; position:absolute;background:#FFF;}
.zstit {text-align:center; padding:8px 0; background:#1898ba; color:#FFF; font-size:14px; margin-bottom:1px;}
.zhishutab {color:#333;}
.zhishutab tr td {border-bottom:1px solid #e4e4e4;}
</style>
<#assign isSingle=salesMode?? && salesMode=='SINGLE' />
<!--table-->
	<div id="note" style="width:710px;z-index:9999">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sssx02">
		<tbody>
	      <tr class="tdduilist" align="center">
	          <td width="35" rowspan="2">序</td>
	          <td width="65" rowspan="2">赛事</td>
	          <td width="38" rowspan="2">开赛</br><span style="color:red">截止</span></td>
	          <!--<td width="45" rowspan="2">截止</td>-->
	          <td width="120" rowspan="2">主队</td>
	          <td width="35" rowspan="2">让球</td>
	          <td width="88" rowspan="2">客队</td>
	          <td width="45" rowspan="2">比分</td>
	          <td width="31" rowspan="2">亚</td>
	          <td width="31" rowspan="2">欧</td>
	          <td  colspan="3" >即时SP</td>
	          <td width="31" rowspan="2"><#if isSingle>选<#else>全</#if></td>
          </tr>
          <tr align="center" class="dctry" id="op">
	          <#list itemArr as item>
	          <td width="71"  bgcolor="#FFFFFF"><input id="column_${item.ordinal()}" onclick="columnBatch(${item.ordinal()});" type="checkbox" disabled="disabled" style="display:none;" /><label  for="column_${item.ordinal()}">${item.text}(${item.value})</label></td>
	          </#list>
          </tr>	
          <tr align="center" class="dctry" id="yp" style="display:none;">
	          <td height="15" width="40">主队</td>
	          <td width="50">盘口</td>
	          <td width="40">客队</td>
	          <#list itemArr as item>
	          <td width="73" bgcolor="#FFFFFF"><input id="column_${item.ordinal()}" onclick="columnBatch(${item.ordinal()});" type="checkbox" disabled="disabled" style="display:none;" /><label  for="column_${item.ordinal()}">${item.text}(${item.value})</label></td>
	          </#list>
          </tr>	
        </tbody> 
    </table>
    </div>   
          
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="sssx02">
      <tbody id="matchTbody">   
	      <#assign endCount=0 />
	      <#if matchMap??>
	  		<#list matchMap.entrySet() as entry>
		  	  <tr class="tdlist" align="center" id="sale_time_${entry.getKey()}">
						<td class="black_000" height="35" colspan="22">
							<strong>${entry.getKey()}（09:00——次日05:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
						</td>
					</tr>
			  <script>
			  	var isAllMatchEnd = true;
			  </script>	
			  <#list entry.getValue() as match>			  		
				  <#if match_index%2==0>
				  	<#assign trClass='tdlist1' />
				  <#else>
				    <#assign trClass='tdlist' />
				  </#if>
				  <#assign saleEnd=match.checkSaleEnd(aheadMinuteEnd) />
				  <#if !saleEnd>
				  	 <script>
					  	isAllMatchEnd = false;
					 </script>
				  </#if>
				  <#assign rs=match.getResult(playType)!'' />
				  <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
			      <#assign spinfo=infoMap.get(match.lineId)!'' />
			      <#if spinfo!=''><#assign itemspinfo=spinfo.getContent() /><#else><#assign itemspinfo='' /></#if>
				  <#if saleEnd><#assign endCount=endCount+1 /></#if>
				   <#assign endSaleTime=match.getEndSaleTime(salesMode) />				   
				  <tr id="tr_${match.lineId}" _lid="${match.lineId}" class="${trClass}" align="center"  _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>" <#if saleEnd && period.current>style="display:none"</#if>>
			        <td  width="30" height="38" id="text_${match.lineId}">${(match.lineId+1)?string('000')}</td>
			        <td width="60" bgcolor="<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultDczcGameColor!}</#if>" class="whitechar">${match.gameName!}</td>
			        <td width="33" title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getEndSaleTime()?string('HH:mm')}</span></td>
			        <!--<td><#if endSaleTime??>${endSaleTime?string('HH:mm')}</#if></td>-->
			        <td width="95" id="td_h_${match.lineId}" class="dzrig char14"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			        		<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green'/>
						    	<#elseif match.handicap == 0>
						    		<#assign handicapClass=''/>	
						    	</#if>
			        
			        <td width="32" class="${handicapClass}"><strong><span id="handicap_${match.lineId}">${match.handicap!}</span></strong></td>
			        <td width="98" id="td_g_${match.lineId}" class="dzleft char14"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>		           
		          		           
		           
		           	<td width="40" class="char14"> 
			        	<#if match.cancel>
			        		取消
			        	<#elseif match.ended>
			        		<#if match.fullHomeScore?? && match.fullGuestScore??>
			        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
			        		</#if>
			        	</#if>
			        </td>
			        <#if !saleEnd>
			        	<td width="31" onmouseover="showAsiaOddsTips(this, ${match.lineId})" class="tdnor" style="font-size:12px"><a >亚</a></td>
			        <#else>
			        	<td width="31" class="tdnor" style="font-size:12px"><a >亚</a></td>
			        </#if>
	         		<#if !saleEnd>
	         			<td width="31" onmouseover="showOddsTips(this, ${match.lineId})" class="tdnor" style="font-size:12px"><a >欧</a></td>
	         		<#else>
	         			<td width="31" width="31" class="tdnor" style="font-size:12px"><a >欧</a></td>
	         		</#if>
			        <#assign resultItem = match.getResult(playType)!''/>
			        <#list itemArr as item>
			        <td class="tdyelnor" width="65" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="td_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" style="cursor: pointer;"</#if> >
			        	<#if !saleEnd && !isSingle><input style="display:none" id="chk_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" class="inputcheckbox" type="checkbox" autocomplete="off" /></#if>
			        	<#assign spId='sp_lid'+match.lineId+'_'+item.ordinal()?string />
			        	<#if match.cancel>
			        		<span id="${spId}" _g="sp" class="redredchar">1.00</span>
			        	<#elseif match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
			        		<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
			        	<#elseif itemspinfo?? && itemspinfo!=''>
			        		<span id="${spId}" _g="sp">${(itemspinfo.get(item.name())!0)?string('0.00')}</span>
			        	<#else>
			        		<span id="${spId}" _g="sp">0.00</span>
			        	</#if>
			        </td>
			        </#list>
			        <td width="34" class="tdnor">
			        	<#if !saleEnd>
				        	<#if isSingle>
				        		<input id="chb_select_${match.lineId}" type="checkbox" value="${match.lineId}" onclick="selectMatch(this);" autocomplete="off" />
				        	<#else>		
				        		<img style="cursor:pointer;" onclick="rowBatch(${match.lineId},0,event)" height="20" width="21" border="0" src="<@c.url value="/pages/images/dc_all.gif" />" />
			        			<input id="bao_0_${match.lineId}" type="checkbox" onclick="rowBatch(${match.lineId},0,event)" style="display:none;" autocomplete="off" />
			        		</#if>
			        	<#else>
			        		&nbsp;
			        	</#if>
			        </td>
			      </tr>
			     		 <script>							
							var tr_id = "tr_${match.lineId}";						
							changeBgColor(tr_id);
							if(isAllMatchEnd) {
								document.getElementById("sale_time_${entry.getKey()}").style.display="none";
							}																																
						</script>
		      </#list>
	  		</#list>
	  	  </#if>
		</tbody>
	  </table>
	  <script type="text/javascript">
	  	$(document).ready(function() {
	  		var el = document.getElementById('m_e_c');
	  		if(el != null)
				el.innerHTML = '${endCount}';
        });
	  </script>
	  <script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
	  <script type="text/javascript" src="${base}/js/lottery/dczc/odds.js"></script> 
<script>
	function loadOddsJs(){
		var scriptTag = document.getElementById("dczc_odds_js"); 
		var oHead = document.getElementsByTagName('HEAD').item(0); 
		var oScript= document.createElement("script"); 
		if(scriptTag){
		    oHead.removeChild(scriptTag); 
		}
		oScript.id = "dczc_odds_js"; 
		oScript.type = "text/javascript"; 
		var oScript_src="${base}/js/odds/dczc/dczc_odds.js?t="+new Date();
		oScript.src=oScript_src; 
		oHead.appendChild(oScript);
	}
	
	function loadOddsChangeJs(){
		var scriptTag = document.getElementById("dczc_odds_change_js"); 
		var oHead = document.getElementsByTagName('HEAD').item(0); 
		var oScript= document.createElement("script"); 
		if(scriptTag){
		    oHead.removeChild(scriptTag); 
		}
		oScript.id = "dczc_odds_change_js"; 
		oScript.type = "text/javascript"; 
		var oScript_src="http://61.147.127.238:8090/js/odds/dczc/odds_change.js?t="+new Date();
		oScript.src=oScript_src; 
		oHead.appendChild(oScript);	
		changeOddsDataMap();
	}

	//赔率有变化  改变Map里变化了的数据
	function changeOddsDataMap() {
		if(typeof odds_change_data != 'undefined' && odds_change_data.length > 0) {
			for(var j=0, length=odds_change_data.length; j<length; j++) {
				var lineid = odds_change_data[j][1];
				var company = odds_change_data[j][2];
				if(typeof odds_data_map != 'undefined')
					odds_data_map[lineid + "_" + company] = odds_change_data[j];
				else
					buildMap();
					odds_data_map[lineid + "_" + company] = odds_change_data[j];
			}
		}
	}
		
	$(function(){
		loadOddsJs();
		setInterval(loadOddsChangeJs, 20000);	
	})
</script>
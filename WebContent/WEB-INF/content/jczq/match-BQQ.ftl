<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script> 						
<div id="note" style="width:710px;">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="ggtztab">
      <tr align="center" class="tdduilist">
        <td width="45" height="43">序号</td>
        <td width="90">赛事<br />比赛时间</td>
        <td width="85">主队</td>
        <td width="85">客队</td>
        <td width="35" class="tdr1px">比分</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">胜胜</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">胜平</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">胜负</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">平胜</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">平平</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">平负</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">负胜</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">负平</td>
        <td width="38" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">负负</td>
        <td width="16" style="background:#FEF5D2; color:#CC4212;">全</td>
      </tr>
</table>
 
</div>      
<div>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="ggtztab">
      <tbody id="matchTbody">
      <!--对阵开始-->
		  <#assign endCount=0 />
			<#if matchMap??>
				<#list matchMap.entrySet() as entry>
					<tr class="tdlist" align="center">
						<td height="35" colspan="15" bgcolor="#FFFFFF" class="black_000">
							<strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
						</td>						
					</tr>
					<#list entry.getValue() as match>
						<#if match_index%2==0>
							<#assign trClass='tdlist' />
						<#else>
							<#assign trClass='tdlist1' />
						</#if>
						<#if rateData??>
							<#assign rateMap=rateData.get(match.matchKey)!'' />
						<#else>
							<#assign rateMap='' />
						</#if>
						<#assign saleEnd=match.isNotDisplay(playType,passMode)/>
						
						<#assign rs=match.getResult(playType)!'' />
					    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
					    
					    <tr class="${trClass}" align="center" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>">
		                	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
		                	<#if saleEnd><#assign endCount=endCount+1 /></#if>
		                	<td id="td_id_${match.matchKey}" width="45" height="38" align="center" class="padb01"><input id="hide_cbx_${match.matchKey}" onclick="hideRow(this);return false" type="checkbox" value="${match.matchKey}" checked="checked" autocomplete="off" /><label for="hide_cbx_${match.matchKey}">${match.lineId?string('000')}</label><span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
		                	<td width="90" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}<br />${match.matchTime?string('MM-dd HH:mm')}</td>
		                	<td width="85" id="td_h_${match.matchKey}" class="black_222_14"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
						    <#assign handicapClass=''/>
						    <#if match.handicap??>
						    	<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green01'/>
						    	</#if>
						    </#if>
						   <td width="85" id="td_g_${match.matchKey}" class="black_222_14"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
		                   
		                   <td id="td_bf_${match.matchKey}" width="35" class="red_F00_14">
			                    <#if match.cancel>
					        		取消
					        	<#elseif match.ended>
					        		<#if match.fullHomeScore?? && match.fullGuestScore??>
					        			<strong>${match.fullHomeScore}:${match.fullGuestScore}</strong>
					        		</#if>
					        	</#if>
				        	</td>
				        	
				            <#list itemArr as item>
				            	<td width="37" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="BQQ_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BQQ');"</#if>>
				            	 <#assign spId='BQQ_sp_${match.matchKey}_${item.ordinal()}' />
				               	 	<#assign chkId='BQQ_chk_${match.matchKey}_${item.ordinal()}' />
				               	 	<#if rateMap?? && rateMap!=''>
										<#assign rateItem=rateMap.get(item.name())!'' />
									<#else>
										<#assign rateItem='' />
									</#if>
									
									<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
					        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
					        		<#else>
					               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BQQ');" type="checkbox" autocomplete="off" /></#if><#rt/>
					               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
				            	   </#if>
				            	</td>
				            </#list>
				            <td width="15" class="tdyelnor" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'BQQ')"</#if>><#if !saleEnd><input id="BQQ_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'BQQ')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
						</tr>
						
						<script>					
							var tr_id = "tr_${match.matchKey}";						
							changeBgColor(tr_id);																																
						</script>
						
					</#list>
				</#list>
			</#if>
	</tbody>  
</table>
</div>
 <script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
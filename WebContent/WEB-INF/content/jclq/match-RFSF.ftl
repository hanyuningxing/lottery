<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<div id="note" style="width:710px;">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; border-top:none; color:#666; line-height:16px; font-family:Arial, Helvetica, sans-serif;">
      <tr align="center" class="tdduilist">
        <td width="45" height="43">序号</td>
        <td>赛事</td>
        <td width="120">比赛时间</td>
        <td width="100">客队</td>
        <td width="100">主队</td>
        <td width="80">主队让分</td>
        <#list itemArr as item>
       	 	<td width="75">${item.text}</td>
        </#list>
        <td width="32" style="background:#FEF5D2; color:#CC4212;">全</td>
      </tr>
   </table>  
</div>

<div>	    
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dce7f4" style="background-color:#D7E2E6; border:1px solid #ccc; border-top:none; color:#333; float:left;">
    <tbody id="matchTbody">
    	  <#assign RFSF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@RFSF") />
		  <!--对阵开始-->
		  <#assign endCount=0 />
			<#if matchMap??>
				<#list matchMap.entrySet() as entry>
					<tr class="tdlist" align="center">
						<td height="35" colspan="14" bgcolor="#FFFFFF" class="black_000">
							<strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
						</td>						
					</tr>
					<#list entry.getValue() as match>
						<#assign open_RFSF=match.isOpen(RFSF,passMode)/>
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
						<#assign handicap=match.singleHandicap!0 />
						
						
						<#assign saleEnd=match.isNotDisplay(playType,passMode)/>
						
						<#assign rs=match.getResult(playType)!'' />
					    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
					    
					    <tr class="${trClass}" align="center" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>">
		                	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
		                	<#if saleEnd><#assign endCount=endCount+1 /></#if>
		                	<td id="td_id_${match.matchKey}" width="45" height="38" align="center" class="padb01"><input id="hide_cbx_${match.matchKey}" onclick="hideRow(this);return false" type="checkbox" value="${match.matchKey}" checked="checked" autocomplete="off" /><label for="hide_cbx_${match.matchKey}">${match.lineId?string('000')}</label><span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
		                	<td style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
		                	<td id="td_t_${match.matchKey}" width="120">${match.matchTime?string('MM-dd HH:mm')}</td>
						    <td width="100" id="td_g_${match.matchKey}" class="black_222_14"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
		                    <td width="100" id="td_h_${match.matchKey}" class="black_222_14"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
						    <td id="td_reduced_${match.matchKey}" width="80"><#if handicap?? && handicap!=0><#if handicap gt 0>+</#if>${handicap?string('0.0')}<#else>--</#if></td>
						    
						    <#if open_RFSF>
					            <#list itemArr as item>
					            	<td width="75" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="RFSF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RFSF');"</#if>>
					            	 <#assign spId='RFSF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='RFSF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if rateMap?? && rateMap!=''>
											<#assign rateItem=rateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RFSF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
					            	   </#if>
					            	</td>
					            </#list>
					            <td width="32" class="tdyelnor" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RFSF')"</#if>><#if !saleEnd><input id="RFSF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RFSF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
							<#else>
				            	<td class="tdyelnor" colspan=3>未开售</td>
				            </#if>
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
  <script type="text/javascript" src="${base}/js/lottery/jclq/navigator.js"></script>  
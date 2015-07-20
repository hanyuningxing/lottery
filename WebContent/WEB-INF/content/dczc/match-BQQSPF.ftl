<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<#assign isSingle=salesMode?? && salesMode=='SINGLE' />
<#if isSingle><#assign col=9/><#else><#assign col=8/></#if>
	<div id="note" style="width:710px;z-index:9999">
	  <table width="710" border="0" cellspacing="0" cellpadding="0" class="sssx02">
		<thead>
		  <tr align="center" class="tdduilist">
	          <td height="25" width="30">序</td>
	          <td width="60">赛事</td>
	          <td width="33">开赛</br><span style="color:red">截止</span></td>
	          <!--<td width="33">截止</td>-->
	          <td width="85">主队</td>
	           <td width="48">比分</td>
	          <td width="85">客队</td>
	          <#list itemArr as item>
	        	<td width="<#if isSingle>37<#else>43</#if>"><label for="column_${item.ordinal()}">${item.text}</label></td>
	          </#list>	         	          	        
	          <#if isSingle><td width="30">选</td></#if>
          </tr>
	    </thead>
	 </table> 
	 </div>
	 <table width="710" border="0" cellspacing="0" cellpadding="0" class="sssx02">  
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
			        <td width="33" title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getEndSaleTime()?string('HH:mm')}</td>
			       <!--<td><#if endSaleTime??>${endSaleTime?string('HH:mm')}</#if></td>-->
			        <td class="dzrig char14" width="85" id="td_h_${match.lineId}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			        
			        <td width="48" class="char14">
			        	<#if match.cancel>
			        		取消
			        	<#elseif match.ended>
			        		<#if match.fullHomeScore?? && match.fullGuestScore??>
			        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
			        		</#if>
			        	</#if>
			        </td>
			        <td class="dzleft char14" width="85" id="td_g_${match.lineId}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
			         <#assign resultItem = match.getResult(playType)!''/>
			        <#list itemArr as item>
			        <td style="font-size:12px" width="40" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="td_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" style="cursor: pointer;"</#if> >
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
			        <#if isSingle>
			        <td>
			        	<#if !saleEnd>
				        	<input style="display:none" id="chb_select_${match.lineId}" type="checkbox" value="${match.lineId}" onclick="selectMatch(this);" autocomplete="off" />
			        	<#else>
			        		&nbsp;
			        	</#if>
			        </td>
			        </#if>
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
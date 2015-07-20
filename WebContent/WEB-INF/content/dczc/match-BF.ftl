<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<#assign isSingle=salesMode?? && salesMode=='SINGLE' />
<#if isSingle>
  <#assign colspan=11 />
<#else>
  <#assign colspan=10 />
</#if>
	<div id="note" style="width:710px;z-index:9999">
	  <table width="710" border="0" cellspacing="0" cellpadding="0" class="sssx02">
		<thead>
		  <tr align="center" class="tdduilist">
	          <td height="25" width="30">序</td>
	          <td width="150">赛事</td>
	          <td width="90">开赛</br><span style="color:red">截止</span></td>
	          <!--<td width="33">截止</td>-->
	          <td width="120">主队</td>
	          <td width="48">比分</td>
	          <td width="120">客队</td>	          
	          <td width="102">操作</td>
	          <#if isSingle><td>选</td></#if>
          </tr>
	    </thead>
	    </table>
	 </div>   
	    <table width="710" border="0" cellspacing="0" cellpadding="0" class="sssx02">
	    <tbody id="matchTbody">
	      <#assign endCount=0 />
	      <#if matchMap??>
	      	<#assign firstNoEnd=-1/>
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
					<td  width="30" class="char14" height="38" id="text_${match.lineId}">${(match.lineId+1)?string('000')}</td>
			        <td width="150" bgcolor="<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultDczcGameColor!}</#if>" class="whitechar">${match.gameName!}</td>
			        <td width="90" class="char14">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getEndSaleTime()?string('HH:mm')}</td>
			        <!--<td><#if endSaleTime??>${endSaleTime?string('HH:mm')}</#if></td>-->
			        <td width="120" class="dzrig char14" id="td_h_${match.lineId}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			         <td width="48" class="char14">
			        	<#if match.cancel>
			        		取消
			        	<#elseif match.ended>
			        		<#if match.fullHomeScore?? && match.fullGuestScore??>
			        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
			        		</#if>
			        	</#if>
			        </td>
			        <td width="120" class="dzleft char14" id="td_g_${match.lineId}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>			        		           		            			        			       
			        <td width="102"><img id="img_${match.lineId}_detail" onclick="showOrHideDetail(${match.lineId});" height="24" width="90" src="<@c.url value="/pages/images/zk.gif" />" style="cursor:pointer;" /></td>
			        <#if isSingle>
				        <td>
				        	<#if !saleEnd>
					        	<input id="chb_select_${match.lineId}" type="checkbox" value="${match.lineId}" onclick="selectMatch(this);" autocomplete="off" />
				        	<#else>
				        		&nbsp;
				        	</#if>
				        </td>
			        </#if>
			      </tr>
			      
			    		<script>							
							var tr_id = "tr_${match.lineId}";						
							changeBgColor(tr_id);																																
						</script>
			      <tr id="tr_${match.lineId}_detail" class="${trClass}" align="center" style="display:none">
					<td colspan="${colspan}">
				      <table width="100%" cellspacing="1" cellpadding="0" border="0" class="tbbge5">
				    	<tbody>
				    	  <#list itemMap.entrySet() as entry>
					        <#assign itemArr=entry.getValue() />
					        <#assign itemArrSize=itemArr?size />
					        <tr class="trw" align="center" >
					          <td width="50" height="42" class="tdyelnor" onclick="rowBatch(${match.lineId},${entry_index},event)"><#if !saleEnd><div><input  style="display:none" id="bao_${entry_index}_${match.lineId}" type="checkbox" /></#if>${entry.getKey()}</div></td>
					          <#assign resultItem = match.getResult(playType)!''/>
					          <#list itemArr as item>
					          <td  width="50" height="42" class="tdyelnor" <#if !saleEnd>id="td_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" style="cursor: pointer;"</#if> >
					          	<#if !saleEnd && !isSingle><input id="chk_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" class="inputcheckbox" type="checkbox" autocomplete="off" style="display:none;" /></#if><span class="redboldchar">${item.text}</span>
					            
					            <#assign spId='sp_lid'+match.lineId+'_'+item.ordinal()?string />
					        	<#if match.cancel>
					        		<span id="${spId}" _g="sp" class="redredchar">1.00</span>
					        	<#elseif match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
					        		<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.000000')}</span></#if>
					        	<#elseif itemspinfo?? && itemspinfo!=''>
					        		<span id="${spId}" _g="sp">${(itemspinfo.get(item.name())!0)?string('0.00')}</span>
					        	<#else>
					        		<span id="${spId}" _g="sp">0.00</span>
					        	</#if>
					          </td>
					         
					          </#list>
					          <#if itemColspan gt itemArrSize>
					          <#list (itemArrSize+1)..itemColspan as c>
					          <td class="tdyelnor">&nbsp;</td>
					          </#list>					        
					          </#if>				          
					        </tr>
				    	  </#list>
				        </tbody>
				      </table>				      
				    </td>
				    
				  </tr>
				  
					         <script>
					         	 if(isAllMatchEnd) {
									document.getElementById("sale_time_${entry.getKey()}").style.display="none";
								 }
								</script>
				  <#if !saleEnd && firstNoEnd=-1><#assign firstNoEnd=match.lineId /></#if>
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
<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script> 						
<script type="text/javascript">
	function showOrHiddenDetail(objId){
		var obj = $('#'+objId);
		var objDisplay = obj.css('display');
		if(objDisplay=='none'){
			obj.css("display","");
		}else{
			obj.css("display","none");
		}
	}
</script>
<div id="note" style="width:710px">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dce7f4" class="ggtztab" style="font-size:14px;">
      <tr class="tdduilist">
        <td width="8%" height="43" align="center" bgcolor="#eef9f3" class="black01">序号</td>
        <td width="24%" align="center" bgcolor="#eef9f3" class="black01">赛事</td>
        <td width="19%" align="center" bgcolor="#eef9f3" class="black01">开赛</br><span style="color:red">截止</span></td>
        <td width="14%" align="center" bgcolor="#eef9f3" class="black01">主队</td>
        <td width="14%" align="center" bgcolor="#eef9f3" class="black01">客队</td>
        <td width="7%" align="center" bgcolor="#eef9f3" class="black01">比分</td>
        <td align="center" bgcolor="#eef9f3" class="black01">显示投注选项</td>
      </tr>
 </table>
 
</div>      
<div>	
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dce7f4" class="ggtztab" style="font-size:14px;">
      <tbody id="matchTbody">
      <!--对阵开始-->
	  <#assign endCount=0 />
		<#if matchMap??>
			<#list matchMap.entrySet() as entry>
		      <tr align="center">
				<td height="35" colspan="7" align="center" bgcolor="#FFFFFF" class="padb01 black_000">
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
					    <tr class="${trClass}" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>">
					    	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
					    	<#if saleEnd><#assign endCount=endCount+1 /></#if>
					    	<td id="td_id_${match.matchKey}" width="8%" height="38" align="center" class="padb01"><input id="hide_cbx_${match.matchKey}" onclick="hideRow(this);return false" type="checkbox" value="${match.matchKey}" checked="checked" autocomplete="off" /><label for="hide_cbx_${match.matchKey}">${match.lineId?string('000')}</label><span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
					    	<td width="24%" align="center" bgcolor="<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>" class="padb01 white_FFF_14">${match.gameName}</td>
					    	<td width="19%" align="center" class="padb01" id="td_t_${match.matchKey}">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getWebOfficialEndTime()?string('HH:mm')}</span></td>
					    	<td width="14%" align="center" class="padb01" id="td_h_${match.matchKey}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
					        <td width="14%" align="center" class="padb01" id="td_g_${match.matchKey}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
					      
					        <td id="td_bf_${match.matchKey}" width="7%" align="center" class="padb01">
				        	<#if match.cancel>
				        		取消
				        	<#elseif match.ended>
				        		<#if match.fullHomeScore?? && match.fullGuestScore??>
				        			<strong>${match.fullHomeScore}:${match.fullGuestScore}</strong>
				        		</#if>
				        	</#if>
				            </td>
				            
					        <td width="14%" align="center" class="padb01">
						        <table width="100%" height="38" border="0" cellpadding="0" cellspacing="0">
						            <tr>
						              <td align="center" class="hhggan" onclick="showOrHiddenDetail('BF_tr_${match.matchKey}_detail');">展开投注</td>
						            </tr>
						        </table>
					        </td>
				        </tr>
				        
				        
				        <!--比分-->
					   <tr id="BF_tr_${match.matchKey}_detail" style="display:none;">
				        <td colspan="7" align="center" bgcolor="#FFFFFF" class="padb01">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="border03">
					          <tr>
					            <td>
					            
						            <#list bfItemMap.entrySet() as entry>
									    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
									    	<#assign itemArr=entry.getValue() />
								        	<#assign itemArrSize=itemArr?size />
								        	
								        	<#assign item=itemArr[itemArrSize-1] />
					               	 	    <#if rateMap?? && rateMap!=''>
											  <#assign rateItem=rateMap.get(item.name())!'' />
										    <#else>
											  <#assign rateItem='' />
										    </#if>
										    
										    <!--标题：bgcolor="#E9FCE4" class="hhggxz" ====  bgcolor="#f63510" class="hhggxz3"-->
									    	<!--第一格以及赔率：bgcolor="#D9F2FF" class="hhggxz" ===  bgcolor="#f63510" class="hhggxz1"-->
						              		<tr>
									    		<td width="6%" rowspan="2" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'BF')"</#if>><#if !saleEnd><input id="BF_bao_${entry_index}_${match.matchKey}" type="checkbox" style="display:none" onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'BF')" /></#if>${entry.getKey()}</label></td>
									    		<td width="10%" height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>id="BF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if>>${item.text}</td>
									    		<#list 0..(itemArrSize-2) as i>
										          <#assign item=itemArr[i] />									  
								    			  <td width="7%" align="center" bgcolor="#E9FCE4" class="hhggxz" <#if !saleEnd>id="BF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if> >
									          		  <input id="BF_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" type="checkbox" />
										          	  ${item.text}
									          	  </td>
									    		</#list>
									    		<#if itemColspan gt itemArrSize>
										          <#list (itemArrSize+1)..itemColspan as c>
										          	<td width="7%" align="center" bgcolor="#E9FCE4">&nbsp;</td>
										          </#list>
										        </#if>
									    	</tr>
									    	
									    	<tr>
						              			<#assign item=itemArr[itemArrSize-1] />
						               	 	    <#if rateMap?? && rateMap!=''>
												  <#assign rateItem=rateMap.get(item.name())!'' />
											    <#else>
												  <#assign rateItem='' />
											    </#if>
						              			<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz2" <#if !saleEnd>id="BF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if>>
							              			<#assign spId='BF_sp_${match.matchKey}_${item.ordinal()}' />
										          	<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
											        	<#if item=rs><span id="${spId}" _g="sp" class="redredchar" style="color:red">${rs_sp?string('0.00')}</span></#if>
										          	<#else>
										          		<input id="BF_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" type="checkbox" />
										          		<span class="colorred" id="${spId}" _g="sp"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
										          	</#if>
									          	</td>
						                		<#list 0..(itemArrSize-2) as i>
										          <#assign item=itemArr[i] />
							               	 	  <#if rateMap?? && rateMap!=''>
													<#assign rateItem=rateMap.get(item.name())!'' />
												  <#else>
													<#assign rateItem='' />
												  </#if>
												  <#assign spId='BF_sp_${match.matchKey}_${item.ordinal()}' />
										          <td width="7%" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>id="BF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if> >
									          		 <#if !saleEnd><input id="BF_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" type="checkbox" />							          	  
											          	  <span class="colorred" id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
										          	 <#else>
										          	    <#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
											        	<#if item=rs><span id="${spId}" _g="sp" class="redredchar" style="color:red">${rs_sp?string('0.00')}</span></#if>
											            </#if>
										         	 </#if>
										          </td>
										         </#list>
									            <#if itemColspan gt itemArrSize>
									              <#list (itemArrSize+1)..itemColspan as c>
									          	    <td width="7%" align="center" bgcolor="#D9F2FF">&nbsp;</td>
									              </#list>
									            </#if>
						              		</tr>
						              	</table>
							         </#list>
						        
					            </td>
					          </tr>
					       </table>
				        </td>
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
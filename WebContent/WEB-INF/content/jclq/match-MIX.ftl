<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<script type="text/javascript">
	function choosePlayType(matchKey,playType) {
	      var chooseFlag = true;
	      var choosedPlayType;
	      for(var key in playTypeItem){
		        if(!canChoose(matchKey,key)){
		            chooseFlag= false;
		        }else{
		            choosedPlayType = key;
		        }
		  }
		  if(chooseFlag){
		     //可以选
		     choosePlayTypeMethod(matchKey,playType);
		  }else{
		     ///该场次已经被选择
		     if(choosedPlayType!=playType){
			     if (confirm("场次["+matchKey+"]已经有选择其他玩法，是否清空选项")) {
		        	removeMatch(matchKey);
		        	choosePlayTypeMethod(matchKey,playType);
			   	 }
			 }else{
			    choosePlayTypeMethod(matchKey,playType);
			 }
		  }
	}	
	
	var playSelectedClass = "hhggan1";
	var playNotSelectedClass = "hhggan";
	function choosePlayTypeMethod(matchKey,playType) {
		 for(var key in playTypeItem){
		 	  if(key==playType)continue;
		      var detailEl = $$(key+'_tr_' + matchKey + '_detail');
			  detailEl.style.display = 'none';
			  var playButtonObj = $("#"+key+"_input_"+matchKey+"_button");
			  playButtonObj.removeClass(playSelectedClass);
		 }
		 
		 var playButtonObj = $("#"+playType+"_input_"+matchKey+"_button");
		 var detailEl = $$(playType+'_tr_' + matchKey + '_detail');
		 var hashMatchKey = playType+matchKey;
		 var hashObj = getSelectedItems();
		 if(hashObj[hashMatchKey]==null && playButtonObj.hasClass(playSelectedClass)){
		 	 playButtonObj.removeClass(playSelectedClass);
		 	 detailEl.style.display = "none";
		 }else{
			 playButtonObj.addClass(playSelectedClass);
			 detailEl.style.display = "";
		 }
	}
	
</script>
<!--table-->
<div id="note" style="width:710px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dce7f4" style="background-color:#D7E2E6; border:1px solid #ccc; border-top:none; color:#333; float:left;">
    	<tr class="tdduilist">
	        <td width="35" height="43" align="center" bgcolor="#eef9f3" class="black01">序号</td>
	        <td align="center" bgcolor="#eef9f3" class="black01">赛事</td>
	        <td width="68" align="center" bgcolor="#eef9f3" class="black01">比赛时间</td>
	        <td width="90" align="center" bgcolor="#eef9f3" class="black01">主队</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">主让分</td>
	        <td width="90" align="center" bgcolor="#eef9f3" class="black01">客队</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">总分</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">胜负</td>
	        <td width="80" align="center" bgcolor="#eef9f3" class="black01">让分胜负</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">胜分差</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">大小分</td>
	     </tr>
	</table> 
</div>	

<div>	    
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dce7f4" style="background-color:#D7E2E6; border:1px solid #ccc; border-top:none; color:#333; float:left;">
    <tbody id="matchTbody">
    <#assign endCount=0 />
	<#if matchMap??>
		<#list matchMap.entrySet() as entry>
			<tr>
				<td height="35" colspan="12" align="center" bgcolor="#FFFFFF" class="padb01 black_000">
					<strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
				</td>
			</tr>
			<#list entry.getValue() as match>
				<#if match_index%2==0>
						<#assign trClass='trw' />
					<#else>
						<#assign trClass='dctro1' />
					</#if>
					<#if SFC_rateMap??>
						<#assign sfcRateMap=SFC_rateMap.get(match.matchKey)!'' />
					<#else>
						<#assign sfcRateMap='' />
					</#if>
					<#if DXF_rateMap??>
						<#assign DXFRateMap=DXF_rateMap.get(match.matchKey)!'' />
					    <#if DXFRateMap?? && DXFRateMap!=''>
							<#assign dxf_handicapItem=DXFRateMap.get('REDUCED_VALUE')!'' />
					    </#if>
						<#if dxf_handicapItem?? && dxf_handicapItem!='' && dxf_handicapItem.value?? && dxf_handicapItem.value!=0>
							<#assign dxf_handicap=dxf_handicapItem.value!0 />
						<#else>
							<#assign dxf_handicap=0 />
						</#if>
					<#else>
						<#assign DXFRateMap='' />
						<#assign dxf_handicap=0 />
					</#if>
					<#if RFSF_rateMap??>
						<#assign RFSFRateMap=RFSF_rateMap.get(match.matchKey)!'' />
						 <#if RFSFRateMap?? && RFSFRateMap!=''>
							<#assign rfsf_handicapItem=RFSFRateMap.get('REDUCED_VALUE')!'' />
						</#if>
						<#if rfsf_handicapItem?? && rfsf_handicapItem!='' && rfsf_handicapItem.value?? && rfsf_handicapItem.value!=0>
							<#assign rfsf_handicap=rfsf_handicapItem.value!0 />
						<#else>
							<#assign rfsf_handicap=0 />
						</#if>
					<#else>
						<#assign RFSFRateMap='' />
						<#assign rfsf_handicap=0 />
					</#if>
					<#if SF_rateMap??>
						<#assign SFRateMap=SF_rateMap.get(match.matchKey)!'' />
					<#else>
						<#assign SFRateMap='' />
					</#if>
										
					<#assign passModeOfSale=stack.findValue("@com.cai310.lottery.support.jclq.PassMode@PASS") />
					<#assign playTypeOfSF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SF") />
					<#assign saleEndOfSF=match.isNotDisplay(playTypeOfSF,passModeOfSale)/>
					<#assign playTypeOfRFSF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@RFSF") />
					<#assign saleEndOfRFSF=match.isNotDisplay(playTypeOfRFSF,passModeOfSale)/>
					<#assign playTypeOfSFC=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SFC") />
					<#assign saleEndOfSFC=match.isNotDisplay(playTypeOfSFC,passModeOfSale)/>
					<#assign playTypeOfDXF=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@DXF") />
					<#assign saleEndOfDXF=match.isNotDisplay(playTypeOfDXF,passModeOfSale)/>
					<#assign open_SF=match.isOpen(playTypeOfSF,passModeOfSale)/>
					<#assign open_RFSF=match.isOpen(playTypeOfRFSF,passModeOfSale)/>
					<#assign open_SFC=match.isOpen(playTypeOfSFC,passModeOfSale)/>
					<#assign open_DXF=match.isOpen(playTypeOfDXF,passModeOfSale)/>
					
										
					<#assign saleEnd=saleEndOfSF && saleEndOfRFSF && saleEndOfSFC && saleEndOfDXF/>
					
					<#assign rs='' />
				    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
			    
			    <!--行样式-->
			    <#if match_index%2==0>
					<#assign trClass='tdlist' />
				<#else>
					<#assign trClass='tdlist1' />
				</#if>
			    <tr class="${trClass}" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>">
	                <input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
	                <#if saleEnd><#assign endCount=endCount+1 /></#if>
                	<td id="td_id_${match.matchKey}" width="35" height="39" align="center" class="padb01"><input id="hide_cbx_${match.matchKey}" onclick="hideRow(this);return false" type="checkbox" value="${match.matchKey}" checked="checked" autocomplete="off" /><label for="hide_cbx_${match.matchKey}">${match.lineId?string('000')}</label><span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
                    <td align="center" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
                    <td id="td_t_${match.matchKey}" width="68" align="center" class="padb01">${match.matchTime?string('MM-dd HH:mm')}</td>
                    <td width="90" align="center" class="padb01" id="td_h_${match.matchKey}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			        <td id="td_rfsf_hd_${match.matchKey}" width="50" align="center" class="padb01"><span id="rfsf_handicap_${match.matchKey}"><#if rfsf_handicap?? && rfsf_handicap!=0><#if rfsf_handicap gt 0>+</#if>${rfsf_handicap?string('0.0')}<#else>--</#if></span></td>
			        <td width="90" align="center" class="padb01" id="td_g_${match.matchKey}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
			      	<td id="td_dxf_hd_${match.matchKey}" width="50" align="center" class="padb01"><span id="dxf_handicap_${match.matchKey}"><#if dxf_handicap?? && dxf_handicap!=0>${dxf_handicap?string('0.0')}<#else>--</#if></span></td>
			      				      
			         <#if playMap?exists&&playMap?has_content>
			         	<#assign playSize = playMap.entrySet().size()>
						<#list playMap.entrySet() as entry>
							<#assign item=entry.getValue() />
						     <#assign key=entry.getKey() />
						     <#if item=='RFSF' >
						     	<#assign tdWidth="80">
						     <#else>
						     	<#assign tdWidth="50">	
						     </#if>
						     <td width=${tdWidth} align="center" bgcolor="#FFFFFF" class="padb01 padr1">
						        <table width="100%" height="38" border="0" cellpadding="0" cellspacing="0">
						          <tr><td align="center" class="hhggan" id="${key}_input_${match.matchKey}_button" onclick="choosePlayTypeMethod('${match.matchKey}','${key}');">${item.text!}</td></tr>
						        </table>        
					        </td>
						</#list>
		            </#if>
				</tr>
				
				<#assign showDetail=false />
				<!--胜负-->
				<#assign saleEnd=saleEndOfSF>
				<tr id="SF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>								
			        <td colspan="7" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			        <td colspan="2" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <#if SF_itemArr?exists&&SF_itemArr?has_content>
								 <#list SF_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="49" <#if !saleEnd>id="SF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SF');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SF')"</#if>>全</td>
				            </tr>
				            
				            <#if open_SF>				            
					            <#if SF_itemArr?exists&&SF_itemArr?has_content>
									<tr>								 
									 <#list SF_itemArr as item>
								 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="SF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SF');" style="cursor: pointer;"</#if>>
						               	 	<#assign spId='SF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='SF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if SFRateMap?? && SFRateMap!=''>
												<#assign rateItem=SFRateMap.get(item.name())!'' />
											<#else>
												<#assign rateItem='' />
											</#if>
											
											<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
							        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
							        		<#else>
							               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SF');" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
						            	   </#if>
						               	</td>
				                    </#list>
				                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SF')"</#if>><#if !saleEnd><input id="SF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
						            
						            </tr>
								</#if>
							<#else>
								<tr><td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${SF_itemArr?size+1}">未开售</td></tr>
							</#if>
				        </table>
			        </td>
			        <td colspan="2" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			   </tr>
			   
			   <!--让分胜负-->
			   <#assign saleEnd=saleEndOfRFSF>
				<tr id="RFSF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>				
			        <td colspan="7" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			        <td colspan="4" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <td width="55" align="center" bgcolor="#D9F2FF" class="hhggxz">主让分</td>
				              <#if RFSF_itemArr?exists&&RFSF_itemArr?has_content>
								 <#list RFSF_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="63" <#if !saleEnd>id="RFSF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RFSF');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RFSF')"</#if>>全</td>
				            </tr>
				            
				            <#if open_RFSF>			            
					            <#if RFSF_itemArr?exists&&RFSF_itemArr?has_content>
									<tr>
									 <td id="RFSF_td_reduced_${match.matchKey}" align="center" bgcolor="#D9F2FF" class="hhggxz"><#if rfsf_handicap?? && rfsf_handicap!=0><#if rfsf_handicap gt 0>+</#if>${rfsf_handicap?string('0.0')}<#else>--</#if></td>
									 <#list RFSF_itemArr as item>
								 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="RFSF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RFSF');" style="cursor: pointer;"</#if>>
						               	 	<#assign spId='RFSF_sp_${match.matchKey}_${item.ordinal()}' />
						               	 	<#assign chkId='RFSF_chk_${match.matchKey}_${item.ordinal()}' />
						               	 	<#if RFSFRateMap?? && RFSFRateMap!=''>
												<#assign rateItem=RFSFRateMap.get(item.name())!'' />
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
				                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RFSF')"</#if>><#if !saleEnd><input id="RFSF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RFSF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
						            </tr>
								</#if>
							<#else>
								<tr><td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${RFSF_itemArr?size+1}">未开售</td></tr>
							</#if>
				        </table>
			        </td>
			   </tr>
			   
			   
			   <!--胜分差-->
			   <#assign saleEnd=saleEndOfSFC>
			   <tr id="SFC_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>
			    <td colspan="4" align="center" bgcolor="#FFFFFF" class="padb01"></td>
		        <td colspan="8" align="center" bgcolor="#FFFFFF" class="padb01">
			        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="border03">
			          <tr>
			            <td>
			            <#list sfcItemMap.entrySet() as entry>				        	
						    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
						    	<#assign itemArr=entry.getValue() />
					        								    
							    <!--标题：bgcolor="#E9FCE4" class="hhggxz" ====  bgcolor="#f63510" class="hhggxz3"-->
						    	<!--第一格以及赔率：bgcolor="#D9F2FF" class="hhggxz" ===  bgcolor="#f63510" class="hhggxz1"-->
			              		<tr>
						    		<td rowspan="2" align="center" bgcolor="#D9F2FF" class="hhggxz" width="41" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'SFC')"</#if>><#if !saleEnd><input id="SFC_bao_${entry_index}_${match.matchKey}" type="checkbox" style="display:none" onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'SFC')" /></#if>${entry.getKey()}</label></td>
						    		<#list itemArr as item>
				               	 	  <#if sfcRateMap?? && sfcRateMap!=''>
										<#assign rateItem=sfcRateMap.get(item.name())!'' />
									  <#else>
										<#assign rateItem='' />
									  </#if>									  
					    			  <td align="center" bgcolor="#E9FCE4" class="hhggxz" width="48" <#if !saleEnd>id="SFC_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SFC');" style="cursor: pointer;"</#if> >
						          		  <input id="SFC_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SFC');" type="checkbox" />
							          	  ${BALANCES[item_index]}
						          	  </td>
						    		</#list>
						    	</tr>
						    	
						    	<#if open_SFC>
				              		<tr>
				                		<#list itemArr as item>
					               	 	  <#if sfcRateMap?? && sfcRateMap!=''>
											<#assign rateItem=sfcRateMap.get(item.name())!'' />
										  <#else>
											<#assign rateItem='' />
										  </#if>
										  <#assign spId='SFC_sp_${match.matchKey}_${item.ordinal()}' />
								          <td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>id="SFC_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SFC');" style="cursor: pointer;"</#if> >
							          		 <#if !saleEnd><input id="SFC_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SFC');" type="checkbox" />							          	  
									          	  <span class="colorred" id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
								          	 <#else>
								          	    <#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
									        	<#if item=rs><span id="${spId}" _g="sp" class="redredchar" style="color:red">${rs_sp?string('0.00')}</span></#if>
									            </#if>
								         	 </#if>
								          </td>
							          </#list>						          
				              		</tr>
			              	  <#else>
								<tr><td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${itemArr?size+1}">未开售</td></tr>
							  </#if>
			              	</table>
				        </#list>
			            </td>
			          </tr>
			       </table>
		        </td>
		      </tr>
			   
			 <!--大小分-->
			 <#assign saleEnd=saleEndOfDXF>
			 <tr id="DXF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>				
		        <td colspan="7" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
		        <td colspan="4" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
			        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
			            <tr>
			              <td width="55" align="center" bgcolor="#D9F2FF" class="hhggxz">总分</td>
			              <#if DXF_itemArr?exists&&DXF_itemArr?has_content>
							 <#list DXF_itemArr as item>
					           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="63" <#if !saleEnd>id="DXF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'DXF');"</#if>>${item.text}</td>
					         </#list>
						  </#if>
			              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'DXF')"</#if>>全</td>
			            </tr>
			            
			            <#if open_DXF>			            
				            <#if DXF_itemArr?exists&&DXF_itemArr?has_content>
								<tr>
								 <td id="DXF_td_reduced_${match.matchKey}" align="center" bgcolor="#D9F2FF" class="hhggxz"><#if dxf_handicap?? && dxf_handicap!=0>${dxf_handicap?string('0.0')}<#else>--</#if></td>
								 <#list DXF_itemArr as item>
							 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="DXF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'DXF');" style="cursor: pointer;"</#if>>
					               	 	<#assign spId='DXF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='DXF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if DXFRateMap?? && DXFRateMap!=''>
											<#assign rateItem=DXFRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'DXF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
					            	   </#if>
					               	</td>
			                    </#list>
			                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'DXF')"</#if>><#if !saleEnd><input id="DXF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'DXF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
					            </tr>
							</#if>
						<#else>
							<tr><td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${DXF_itemArr?size+1}">未开售</td></tr>
						</#if>
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
<script type="text/javascript" src="${base}/js/lottery/jclq/navigator.js"></script> 

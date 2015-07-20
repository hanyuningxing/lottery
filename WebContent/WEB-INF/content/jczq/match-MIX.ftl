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

		  choosePlayTypeMethod(matchKey,playType);
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
	        <td width="104" align="center" bgcolor="#eef9f3" class="black01">赛事</td>
	        <td width="68" align="center" bgcolor="#eef9f3" class="black01">开赛</br><span style="color:red">截止</span></td>
	        <td width="74" align="center" bgcolor="#eef9f3" class="black01">主队</td>
	        <td width="30" align="center" bgcolor="#eef9f3" class="black01">让球</td>
	        <td width="74" align="center" bgcolor="#eef9f3" class="black01">客队</td>
	        <td width="30" align="center" bgcolor="#eef9f3" class="black01">比分</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">胜平负</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">进球数</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">比分</td>
	        <td width="50" align="center" bgcolor="#eef9f3" class="black01">半全场</td>
	        <td width="93" align="center" bgcolor="#eef9f3" class="black01">让球胜平负</td>
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
				<#if BF_rateMap??>
					<#assign bfRateMap=BF_rateMap.get(match.matchKey)!'' />
				<#else>
					<#assign bfRateMap='' />
				</#if>
				<#if SPF_rateMap??>
					<#assign spfRateMap=SPF_rateMap.get(match.matchKey)!'' />
				<#else>
					<#assign spfRateMap='' />
				</#if>
				<#if RQSPF_rateMap??>
					<#assign rqspfRateMap=RQSPF_rateMap.get(match.matchKey)!'' />
				<#else>
					<#assign rqspfRateMap='' />
				</#if>
				<#if JQS_rateMap??>
					<#assign jqsRateMap=JQS_rateMap.get(match.matchKey)!'' />
				<#else>
					<#assign jqsRateMap='' />
				</#if>
				<#if BQQ_rateMap??>
					<#assign bqqRateMap=BQQ_rateMap.get(match.matchKey)!'' />
				<#else>
					<#assign bqqRateMap='' />
				</#if>
				
				<#assign passModeOfSale=stack.findValue("@com.cai310.lottery.support.jczq.PassMode@PASS") />
				<#assign playTypeOfSPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@SPF") />
				<#assign saleEndOfSPF=match.isNotDisplay(playTypeOfSPF,passModeOfSale)/>
				<#assign open_SPF=match.isOpen(playTypeOfSPF,passModeOfSale)/>
				<#assign playTypeOfRQSPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@RQSPF") />
				<#assign saleEndOfRQSPF=match.isNotDisplay(playTypeOfRQSPF,passModeOfSale)/>
				<#assign open_RQSPF=match.isOpen(playTypeOfRQSPF,passModeOfSale)/>
				<#assign playTypeOfBF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@BF") />
				<#assign saleEndOfBF=match.isNotDisplay(playTypeOfBF,passModeOfSale)/>				
				<#assign playTypeOfJQS=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@JQS") />
				<#assign saleEndOfJQS=match.isNotDisplay(playTypeOfJQS,passModeOfSale)/>
				<#assign playTypeOfBQQ=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@BQQ") />
				<#assign saleEndOfBQQ=match.isNotDisplay(playTypeOfBQQ,passModeOfSale)/>
				
				<#assign saleEnd=saleEndOfSPF && saleEndOfRQSPF && saleEndOfBF && saleEndOfJQS && saleEndOfBQQ />
				
				<#assign rs='' />
			    <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
			    
			    <!--行样式-->
			    <#if match_index%2==0>
					<#assign trClass='tdlist' />
				<#else>
					<#assign trClass='tdlist1' />
				</#if>
			    <tr id="tr_${match.matchKey}" class="${trClass}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>">
	                <input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
	                <#if saleEnd><#assign endCount=endCount+1 /></#if>
                	<td id="td_id_${match.matchKey}" width="35" height="39" align="center" class="padb01"><input id="hide_cbx_${match.matchKey}" onclick="hideRow(this);return false" type="checkbox" value="${match.matchKey}" checked="checked" autocomplete="off" /><label for="hide_cbx_${match.matchKey}">${match.lineId?string('000')}</label><span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
                    <td width="104" align="center" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
                    <td id="td_t_${match.matchKey}" width="68" align="center" class="padb01">${match.matchTime?string('HH:mm')}</br><span style="color:red">${match.getWebOfficialEndTime()?string('HH:mm')}</span></td>
                    <td width="74" align="center" class="padb01" id="td_h_${match.matchKey}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			        <td id="td_hd_${match.matchKey}" width="30" align="center" class="padb01"><span id="handicap_${match.matchKey}">${match.handicap!}</span></td>
			        <td width="74" align="center" class="padb01" id="td_g_${match.matchKey}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
			      
			        <td id="td_bf_${match.matchKey}" width="30" align="center" class="padb01">
		        	<#if match.cancel>
		        		取消
		        	<#elseif match.ended>
		        		<#if match.fullHomeScore?? && match.fullGuestScore??>
		        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
		        		</#if>
		        	</#if>
		            </td>
			         <#if playMap?exists&&playMap?has_content>
			         	<#assign playSize = playMap.entrySet().size()>
						<#list playMap.entrySet() as entry>
							<#assign item=entry.getValue() />
						     <#assign key=entry.getKey() />
						     <#assign tdWidth=''>
						     <#if entry_index+1 lt playSize>
						     	<#assign tdWidth="7%">
						     </#if>
						     <td width=${tdWidth} align="center" class="padb01 padr1">
						        <table width="100%" height="38" border="0" cellpadding="0" cellspacing="0">
						          <tr><td align="center" class="hhggan" id="${key}_input_${match.matchKey}_button" onclick="choosePlayTypeMethod('${match.matchKey}','${key}');">${item.text!}</td></tr>
						        </table>        
					        </td>
						</#list>
		            </#if>
				</tr>
				
				<#assign showDetail=false />
				<!--胜平负-->
				<#assign saleEnd=saleEndOfSPF>
				<tr id="SPF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>				
			        <td colspan="7" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			        <td colspan="4" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <#if SPF_itemArr?exists&&SPF_itemArr?has_content>
								 <#list SPF_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="49" <#if !saleEnd>id="SPF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SPF')"</#if>>全</td>
				            </tr>
				            
							<#if open_SPF>
					            <#if SPF_itemArr?exists&&SPF_itemArr?has_content>
									<tr>
									 <#list SPF_itemArr as item>
								 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');" style="cursor: pointer;"</#if>>
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
							               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
						            	   </#if>
						               	</td>
				                    </#list>
				                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SPF')"</#if>><#if !saleEnd><input id="SPF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'SPF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
						            </tr>
								</#if>
							<#else>
								<tr><td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${SPF_itemArr?size+1}">未开售</td></tr>
							</#if>
				        </table>
			        </td>
			        <td align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			   </tr>
			   
			   <!--让球胜平负-->
			   <#assign saleEnd=saleEndOfRQSPF>
			   <tr id="RQSPF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>
			        <td height="36" colspan="8" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
        			<td colspan="4" align="center" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" height="40" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz">让球</td>
				              <#if RQSPF_itemArr?exists&&RQSPF_itemArr?has_content>
								 <#list RQSPF_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="48" <#if !saleEnd>id="RQSPF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RQSPF')">全</td>
				            </tr>
				            
							<#if open_RQSPF>
								<#if RQSPF_itemArr?exists&&RQSPF_itemArr?has_content>
									<tr>
									 <td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz"><strong><span id="handicap_${match.matchKey}">${match.handicap!}</span></strong></td>
									 <#list RQSPF_itemArr as item>
								 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');" style="cursor: pointer;"</#if>>
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
							               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
							               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
						            	   </#if>
						               	</td>
				                    </#list>
				                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RQSPF')"</#if>><#if !saleEnd><input id="RQSPF_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'RQSPF')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
						            </tr>
								</#if>
							<#else>
								<tr>
									<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz"><strong><span id="handicap_${match.matchKey}">${match.handicap!}</span></strong></td>
									<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" colspan="${RQSPF_itemArr?size+1}">未开售</td>
								</tr>
							</#if>
				        </table>
			        </td>
			   </tr>
			   
			   <!--进球数-->
			   <#assign saleEnd=saleEndOfJQS>
			   <tr id="JQS_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>
			        <td colspan="4" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			        <td colspan="8" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <#if JQS_itemArr?exists&&JQS_itemArr?has_content>
								 <#list JQS_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="48" <#if !saleEnd>id="JQS_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'JQS');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'JQS')"</#if>>全</td>
				            </tr>
				            				            
				            <#if JQS_itemArr?exists&&JQS_itemArr?has_content>
								<tr>
								 <#list JQS_itemArr as item>
							 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="JQS_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'JQS');" style="cursor: pointer;"</#if>>
					               	 	<#assign spId='JQS_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='JQS_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if jqsRateMap?? && jqsRateMap!=''>
											<#assign rateItem=jqsRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'JQS');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>
					            	   </#if>
					               	</td>
			                    </#list>
			                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz"><#if !saleEnd><input id="JQS_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'JQS')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
					            </tr>
							</#if>
				        </table>
			        </td>
			   </tr>
			   
			   <!--半全场-->
			   <#assign saleEnd=saleEndOfBQQ>
			   <tr id="BQQ_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>
			        <td colspan="3" align="center" bgcolor="#FFFFFF" class="padb01">&nbsp;</td>
			        <td colspan="9" align="center" valign="top" bgcolor="#FFFFFF" class="padb01">
				        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" class="border03">
				            <tr>
				              <#if BQQ_itemArr?exists&&BQQ_itemArr?has_content>
								 <#list BQQ_itemArr as item>
						           	  <td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="49" <#if !saleEnd>id="BQQ_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BQQ');"</#if>>${item.text}</td>
						         </#list>
							  </#if>
				              <td align="center" bgcolor="#E9FCE4" class="hhggxz2" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'BQQ')"</#if>>全</td>
				            </tr>
				            				            
				            <#if BQQ_itemArr?exists&&BQQ_itemArr?has_content>
								<tr>
								 <#list BQQ_itemArr as item>
							 	   	<td height="20" align="center" bgcolor="#D9F2FF" class="hhggxz" <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="BQQ_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BQQ');" style="cursor: pointer;"</#if>>
					               	 	<#assign spId='BQQ_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='BQQ_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if bqqRateMap?? && bqqRateMap!=''>
											<#assign rateItem=bqqRateMap.get(item.name())!'' />
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
			                    	<td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'BQQ')"</#if>><#if !saleEnd><input id="BQQ_bao_0_${match.matchKey}" type="checkbox" onclick="rowBatch(${match_index},'${match.matchKey}',0,event,'BQQ')" align="absmiddle" autocomplete="off" /><#else>&nbsp;</#if></td>
					            </tr>
							</#if>
				        </table>
			        </td>
			   </tr>
			   
			   <!--比分-->
			   <#assign saleEnd=saleEndOfBF>
			   <tr id="BF_tr_${match.matchKey}_detail" <#if !showDetail>style="display:none"</#if>>
		        <td colspan="12" align="center" bgcolor="#FFFFFF" class="padb01">
			        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="border03">
			          <tr>
			            <td>
			            <#list bfItemMap.entrySet() as entry>				        	
						    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
						    	<#assign itemArr=entry.getValue() />
					        	<#assign itemArrSize=itemArr?size />
					        	
					        	<#assign item=itemArr[itemArrSize-1] />
		               	 	    <#if bfRateMap?? && bfRateMap!=''>
								  <#assign rateItem=bfRateMap.get(item.name())!'' />
							    <#else>
								  <#assign rateItem='' />
							    </#if>
							    
							    <!--标题：bgcolor="#E9FCE4" class="hhggxz" ====  bgcolor="#f63510" class="hhggxz3"-->
						    	<!--第一格以及赔率：bgcolor="#D9F2FF" class="hhggxz" ===  bgcolor="#f63510" class="hhggxz1"-->
			              		<tr>
						    		<td rowspan="2" align="center" bgcolor="#D9F2FF" class="hhggxz" width="41" <#if !saleEnd>onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'BF')"</#if>><#if !saleEnd><input id="BF_bao_${entry_index}_${match.matchKey}" type="checkbox" style="display:none" onclick="rowBatch(${match_index},'${match.matchKey}',${entry_index},event,'BF')" /></#if>${entry.getKey()}</label></td>
						    		<td height="20" align="center" bgcolor="#E9FCE4" class="hhggxz2" width="69" <#if !saleEnd>id="BF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if>>${item.text}</td>
						    		<#list 0..(itemArrSize-2) as i>
							          <#assign item=itemArr[i] />
				               	 	  <#if bfRateMap?? && bfRateMap!=''>
										<#assign rateItem=bfRateMap.get(item.name())!'' />
									  <#else>
										<#assign rateItem='' />
									  </#if>									  
					    			  <td align="center" bgcolor="#E9FCE4" class="hhggxz" width="48" <#if !saleEnd>id="BF_td_title_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if> >
						          		  <input id="BF_chk_${match.matchKey}_${item.ordinal()}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" type="checkbox" />
							          	  ${item.text}
						          	  </td>
						    		</#list>
						    		<#if itemColspan gt itemArrSize>
							          <#list (itemArrSize+1)..itemColspan as c>
							          	<td align="center" bgcolor="#E9FCE4" width="48">&nbsp;</td>
							          </#list>
							        </#if>
						    	</tr>
						    	
						    	
			              		<tr>
			              			<#assign item=itemArr[itemArrSize-1] />
			               	 	    <#if bfRateMap?? && bfRateMap!=''>
									  <#assign rateItem=bfRateMap.get(item.name())!'' />
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
				               	 	  <#if bfRateMap?? && bfRateMap!=''>
										<#assign rateItem=bfRateMap.get(item.name())!'' />
									  <#else>
										<#assign rateItem='' />
									  </#if>
									  <#assign spId='BF_sp_${match.matchKey}_${item.ordinal()}' />
							          <td align="center" bgcolor="#D9F2FF" class="hhggxz" <#if !saleEnd>id="BF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'BF');" style="cursor: pointer;"</#if> >
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
						          	  <td align="center" bgcolor="#D9F2FF">&nbsp;</td>
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
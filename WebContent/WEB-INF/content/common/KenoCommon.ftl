<SCRIPT LANGUAGE="JavaScript"> 
 function openCapacityChaseHelp() {		
			$floater({
				width :480,
				height :360,
				src : window.BASESITE + '/html/chase/capacity-chase-help.htm',
				title : '',
				fix : 'true'
			});
		}
  var oldPrize =parseFloat('${betType.prize!}');
  function setWonType(){
      
       var wonType=document.getElementsByName("createForm.wonType");
	   var wonTypeValue;
	   for(var i=0;i<wonType.length;i++){
	     if(wonType[i].checked)
	     	wonTypeValue=wonType[i].alt;
	   }
       var zoomType=document.getElementsByName("createForm.zoomType");
	   var zoomTypeValue;
	   for(var i=0;i<zoomType.length;i++){
	     if(zoomType[i].checked)
	     	zoomTypeValue=zoomType[i].alt;
	   }
	  wonTypeValue = parseFloat(wonTypeValue);  
       zoomTypeValue = parseFloat(zoomTypeValue);  
      var betType_prize =  document.getElementById('betType_prize');
      var prizeObj = betType_prize.options[betType_prize.selectedIndex];
      var prize = toWonTypeDecimal(oldPrize*wonTypeValue*zoomTypeValue);
      prizeObj.value= ''+prize;
      prizeObj.innerHTML= ''+prize;
  }
  function toWonTypeDecimal(x) {  
            var f = parseFloat(x);  
            if (isNaN(f)) {  
                return;  
            }  
            f = Math.round(x*100)/100;  
            return f;  
        }  
</SCRIPT>
<#assign canChase=canChase!false /><#-- 是否有追号功能 -->

   			 <div class=" cleanboth ssqjtd">
		       <div class="textcenter" style="padding-top:15px;">
		       
		       
		                   <#assign wonTypeArr=wonTypeArr!(stack.findValue("@com.cai310.lottery.common.keno.WonType@values()")) />
					    	<#list wonTypeArr as wonType>
					           <input type="radio" name="createForm.wonType" value="${wonType}" alt="#{(wonType.zoom!1);M2}" onclick="setWonType()"  <#if wonType =='ADD0PER'>checked</#if>/>${wonType.typeName}
					    	</#list>
					    	<br/>
		                   <#assign zoomTypeArr=zoomTypeArr!(stack.findValue("@com.cai310.lottery.common.keno.ZoomType@values()")) />
					    	<#list zoomTypeArr as zoom>
					           <input type="radio" name="createForm.zoomType" value="${zoom}" alt = "#{(zoom.zoom!1);M2}" onclick="setWonType()" <#if zoom =='original'>checked</#if>/>${zoom.typeName}
					    	</#list>
		                              您选择<span class="redc" _name="createForm_units">0</span>注 ，
		                           倍投<input class="kuanginput" id="createForm_multiple" size="6" name="createForm.multiple" type="text"  value="1"  onblur="if(this.value<=0){this.value=1;updateBetCost();}"  onkeyup="updateBetCost()" maxlength="5" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/>               
		                            倍，共<span class="redc"><span _name="createForm_schemeCost">0</span></span>元  ，
		            <#if canChase> 
		               <input onclick="chgChase(this.checked,'<@c.url value= "/${lotteryType.key}/scheme!canChaseIssue.action"/>');" id="createForm_NormalChase" type="checkbox" name="createForm.chase" value="true" /><label for="createForm_chase" style="font-weight:bold; font-size:14px; color:#F00; padding-bottom:5px;">普通追号</label>
	               	  <input style="margin-left:8px;" onclick="chgCapacityChase(this.checked,'<@c.url value= "/${lotteryType.key}/scheme!canChaseIssue.action"/>');" id="createForm_CapacityChase" type="checkbox" name="createForm.chase" value="true"><label for="createForm_chase" style="font-weight:bold; font-size:14px; color:#F00; padding-bottom:5px; position:absolute;_ position:relative;*+position:relative;">智能追号<a href="#">
	               	  <img style="POSITION: absolute; TOP: -22px;_TOP: -8px;*+TOP: -6px; LEFT: 33px;" alt="" src="<@c.url value="/pages/images/bz.gif"/>"  onclick="openCapacityChaseHelp();"  ></a></label>
	                </#if>
		       </div>
	         </div>
  				<#if canChase> 
			       <div class="hemaiq" id="tab_chase" style="display:none">
				        <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" class="b1">
				          <tr>
				            <td width="65" height="30" align="right" valign="bottom" class="tdboldbggray">追号期数： </td>
				            <td valign="bottom" class="tddown1px"><span class="right_rig">
				              <select size="1" id="createForm_normalChase_periodSizeOfChase" name="createForm.periodSizeOfChase" onchange="chgChase(true,'<@c.url value= "/${lotteryType.key}/scheme!canChaseIssue.action"/>');" disabled="disabled">
				                  <option value="2">追2期 </option>
					              <option value="5" SELECTED>追5期 </option>
					              <option value="10">追10期 </option>
					              <option value="20">追20期 </option>
					              <option value="30">追30期 </option>
					              <option value="50">追50期 </option>
					              <option value="80">追80期 </option>
					              <option value="100">追100期 </option>
					              <option value="120">追120期 </option>
					              
				              </select>
				              </span>
				            	  共<span id="span_normalChase_Cost" style="color:#F00">0</span>元&nbsp;&nbsp;
				              </td>
				          </tr>
				          <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td class="tddown1px">
					            <div class="zhuihao_list">
				                    <ul id="ul_chase_detail"></ul>
				               </div>
				            </td>
				          </tr>
				          <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td  align="left">
					            <input type="hidden" id="createForm_normalChase_totalCostOfChase" name="createForm.totalCostOfChase" value="0" disabled="disabled" />
					            <input type="checkbox" id="createForm_normalChase_wonStopOfChase" name="createForm.wonStopOfChase" onclick="setwonStop(this.checked)" value="true"   checked="checked" disabled="disabled" />
							              单期中奖金额≥<input type="text" id="createForm_normalChase_prizeForWonStopOfChase" size="6"  value="0" name="createForm.prizeForWonStopOfChase" onkeydown="number_check(this,event,0)" disabled="disabled"  />元，停止追号任务。
						    </td>
				          </tr>
				        </table>
		          </div>
		          </#if>
		          
		          
		          <#if canChase> 
			         <div class="hemaiq" id="tab_capacityChase" style="display:none">
			          <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" class="b1">
				          <tbody>
						   <tr>
						     <td height="15" align="right" valign="bottom" class="tdboldbggray">基本设置：</td>
					         <td width="192" valign="bottom" class="">
							      起始期号
								  <span class="right_rig">
								  <select id="startChasePeriod" name="createForm.startChasePeriodId" class="zhqs_select">
                            	  </select>期
								  </span>
							  </td>
						     <td width="356" class="zhhsz">默认为当前期,当选择了其他期号时,系统将从你选择的期号开始追号</td>
						   </tr>
				          <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td colspan="2">
							     追号期数								
								 <select id="createForm_capacityChase_periodSizeOfChase" name="createForm.periodSizeOfChase" class="zhqs_select" disabled="disabled">
                              	  <option value="5" SELECTED>5</option>
                              	  <option value="2">2</option>
					              <option value="10">10</option>
					              <option value="20">20</option>
					              <option value="30">30</option>
					              <option value="50">50</option>
					              <option value="80">80</option>
					              <option value="100">100</option>
					              <option value="120">120</option>
                                </select>
                         	          共<span id="span_capacityChase_Cost" style="color:#F00">0</span>元&nbsp;&nbsp;
                         	     <input type="hidden" id="createForm_capacityChase_totalCostOfChase" name="createForm.totalCostOfChase" value="0" disabled="disabled" disabled="disabled"/>
								</td>
				          </tr>
						  <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td>
							     起始倍数								
								<input  class="zhqs_rig" type="text" name="createForm.startMultiple" id="startbeishu" value="1" maxLength="4"/>倍</td>
							</td>
				            <td>&nbsp;</td>
				          </tr>
						  <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td>
							     单注奖金	
								  <span class="right_rig">
								  <select class="zhqs_select" id="betType_prize" name="betType.prize">
								   	<option value="${betType.prize!}" SELECTED>${betType.prize!}</option>					    
								  </select>
							     </span>						   
							  </td>
				            <td class="zhhsz">默认为玩法最高奖金,当选择了子玩法的奖金,系统会自动判断</td>
				          </tr>
						  <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td class="tddown1px">
							     已经投入	
							      <input type="text" id="hasInvested" name="createForm.hasInvested" value="0" maxLength="4" class="zhqs_rig" />元
							</td>
				            <td class="tddown1px zhhsz">如果你前面已经购买了彩票未中奖,填入后系统自动帮你记为成本</td>
				          </tr>
						
						 <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td colspan="2">
							     预计命中注数							
								 <select id="mzzs_select"  name="createForm.expectedHit" class="zhqs_select">
                         		 </select>
						  	</td>
				            <td class="tddown1px zhhsz"></td>
				          </tr>
						
				          <tr>
				            <td align="right" class="tdboldbggray">收益设置：</td>
				            <td colspan="2" >
				                 <input type="checkbox" id="lucre" class="checkbox" value="" checked="checked"/>累计收益
							 </td>
				          </tr>
				          
				           <tr>
				            <td align="right" class="tdboldbggray"></td>
				            <td>
							     <input name="lucreradio" type="radio" value="0" id="alllucre" class="checkbox"   />
							           全程收益>=							
								 <input class="zhqs_rig_s" type="text" name="createForm.allafterlucre" id="allafterlucre" value="10"  maxLength="6"/>元
							 </td>
							<td class=" zhhsz">在追号期数内开出最少赢利不低于该金额</td>
				          </tr>
						  <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td colspan="2">
				            <input name="lucreradio" id="lucret" type="radio" value="1" class="checkbox" />
							  前								
							<input class="zhqs_rig_mi" type="text" name="createForm.befortermmember" id="befortermmember"   value="5" maxLength="3"/>
							 期累计收益不低于       
							<input class="zhqs_rig_mi" id="beforelc" type="text" name="createForm.beforelc"  value="10" maxLength="6"/>元
							， 之后累计收益不低于					
							 <input class="zhqs_rig_mi" type="text" id="aferlc"  name="createForm.aferlc"   value="10" maxLength="6"/>元							
							</td>
						  </tr>
						  
						   <tr>
				            <td align="right" class="tdboldbggray"></td>
				            <td colspan="2" >
				                 <input  id="lucrep" type="checkbox" class="checkbox" checked="checked"/>收益率
							 </td>
				          </tr>
						  
						  <tr>
				            <td align="right" class="tdboldbggray"></td>
				            <td>
				               <input name="lucrepradio" type="radio" id="alllucrep" name="createForm.alllucrep" value="0" class="checkbox"/>
							     全程利润率>=								
						      <select name="createForm.all_lucrep_select" style="width:50px;" id="all_lucrep_select">
                                <option>5</option>
                                <option SELECTED>10</option>
                                <option>20</option>
                                <option>30</option>
                                <option>50</option>
                                <option>80</option>
                                <option>100</option>
                                <option>150</option>
                                <option>200</option>
                                <option>300</option>
                                <option>500</option>
                              </select>%						   
							</td>
							<td class=" zhhsz">在追号期数内开出最少赢利不低于中奖期时的累计投入百分比</td>
				          </tr>
						  <tr>
				            <td align="right" class="tdboldbggray">&nbsp;</td>
				            <td colspan="2">
				             <input name="lucrepradio" id="lucrept" type="radio" value="1" class="checkbox"/>
							     前		
							  <input class="zhqs_rig_mi" name="createForm.befortermmemberp"   type="text" id="befortermmemberp" value="5" maxLength="3"/>   						
								 期,利润率不低于            
						       <select  style="width:50px;" id="before_lcp_select"  name="createForm.before_lcp_select" >
                            	    <option>5</option>
	                                <option SELECTED>10</option>
	                                <option>20</option>
	                                <option>30</option>
	                                <option>50</option>
	                                <option>80</option>
	                                <option>100</option>
	                                <option>150</option>
	                                <option>200</option>
	                                <option>300</option>
	                                <option>500</option>
                              </select>%							
							     ，之后收益率不低于  						
							 <select  id="aferlcp_select" name="createForm.aferlcp_select">
                              <option selected="selected">5</option>
                              <option>10</option>
                              <option>20</option>
                              <option>30</option>
                              <option>50</option>
                              <option>80</option>
                              <option>100</option>
                              <option>150</option>
                              <option>200</option>
                              <option>300</option>
                              <option>500</option>
                             </select>%							
                            </td>
						  </tr>
						  <tr>
						    <td>&nbsp;</td>
				            <td colspan="2" height="25px;">
				              <input id="createForm_capacityChase_wonStopOfChase" type="checkbox" name="createForm.wonStopOfChase"  onclick="setcapacityWonStop(this.checked)" value="true" checked="checked" disabled="disabled"/>
				              <input id="createForm_capacityChase_PrizeForWonStopOfChase" type="hidden"  value="0" name="createForm.prizeForWonStopOfChase" disabled="disabled"/>
				              
				       			中奖后停止:投注多期时，当某期中奖后，自动放弃后面几期投注操作。
                            </td>
				          </tr>
						  <tr>
						    <td>&nbsp;</td>
				            <td colspan="2" height="25px;">
                            <input id='createForm_capacityChase_outNumStop'   name='createForm.outNumStop' type='checkbox' class='checkbox' value='true' checked='checked' disabled="disabled" />出号后放弃:延后投注时，投注号码提前开出，自动放弃后面几期投注操作。
                            </td>
				          </tr>
				        </tbody>
					 </table>	
					 <div align="center" class="ksjs_anniu"><img src="<@c.url value="/pages/images/ksjs.gif"/>"  style="cursor:pointer;"  onclick="manage();"></div>					
				     <div id= "showtbody">
				     <table class="znzh_table" width="100%" border="0" cellspacing="0" cellpadding="0" >
						  <tr>
							<th width="7%">序号</th>
							<th width="18%">期号</th>
							<th width="10%">倍数</th>
							<th width="12%">本期投入</th>
							<th width="13%">累计投入</th>
							<th width="13%">本期收益</th>
							<th width="14%">盈利收益</th>
							<th width="13%">利润率</th>
						  </tr>
					</table>
					 </div>			  
		             </div>
		          </#if>
		          
		          
          <div align="center" class=" top5px">
	        <input id="agree" type="checkbox" checked />
	        <label class="inputcheckbox graychar">我已阅读了《<a href="#"   onclick="openPurchaseAgreement();return false;">用户合买代购协议</a>》并同意其中条款</label>
	      </div>
	      <!-- 购买按钮-->
	      <div align="center" class="all8px">
	               <span id="span_createForm_submit"><a href="#" onclick="submitKenoCreateForm();return false;" id="formSubmit"><img src="<@c.url value= "/pages/images/bt_goumai.gif"/>" id="span_createForm_submit_button"/></a></span>
      	           <span id="span_createForm_waiting" style="display:none;">正在提交,请稍等...</span>
	      </div>
	      <input type="hidden" name="createForm.periodId" id="createFormPeriodId" value="<#if issueData??>${issueData.id!}</#if>" />
		  <input type="hidden" id="computezhushu" name="createForm.units" value="0" />
		  <input type="hidden" id="schemeCost" name="createForm.schemeCost" value="0" />
	      <input type="hidden" name="createForm.salesMode" value="COMPOUND"/>
	      <input type="hidden" id="content" name="createForm.content" value=""/>
          <input type="hidden" name="createForm.secretType"  value="FULL_SECRET"/>
	      <input type="hidden" id="lotteryType_key" name="lotteryType_key" value="${lotteryType.key}"/>
          <input type="hidden" id="isCapacity" value="false"/>



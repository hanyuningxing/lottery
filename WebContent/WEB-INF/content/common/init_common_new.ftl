 <SCRIPT>
 function openCapacityChaseHelp() {		
			$floater({
				width :480,
				height :360,
				src : window.BASESITE + '/html/chase/capacity-chase-help.htm',
				title : '',
				fix : 'true'
			});
		}
</SCRIPT>

<#assign canChase=canChase!false />
		          <div class=" cleanboth ssqjtd">
				       <div class="textcenter" style="padding-top:15px;">
				                              您选择<span class="redc" _name="createForm_units">0</span>注 ，
				                           倍投<input class="kuanginput"  id="createForm_multiple" size="6" name="createForm.multiple" type="text"  value="1"  onblur="if(this.value<=0){this.value=1;updateBetCost();}"  onkeyup="updateBetCost()" maxlength="5" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/>               
				                            倍，共<span class="redc"><span _name="createForm_schemeCost">0</span></span>元  ，
				            <input type="hidden" value="SELF" name="createForm.shareType"/>
			                <label><input type="radio" value="SELF" name="createForm_shareType" onclick="numChgShareType(this);updateBetCost();"  checked="checked"/>自购</label>
							<label><input type="radio" value="TOGETHER" name="createForm_shareType" onclick="numChgShareType(this);updateBetCost();"/>合买</label>
							<#if canChase&&lotteryType!="WELFARE36To7"> 
							   <label><input type="radio" value="CAHSE" name="createForm_shareType" onclick="numChgShareType(this);chgChase(this.checked);"/>普通追号</label>
							</#if>
							<#if lotteryType=="WELFARE36To7"> 
								 <label><input type="radio" id="cahseRadio" value="CAHSE" name="createForm_shareType" onclick="numChgShareType(this);chgChase(this.checked);"/>普通追号</label>
			               	     <label>
			               	      <input type="radio" id="cahseCapacitychase" value="CAPACITYCHASE" name="createForm_shareType" onclick="numChgShareType(this);chgCapacityChase(this.checked);"/>
			               	      <label for="createForm_chase" style="font-weight:bold; font-size:14px; color:#F00; padding-bottom:5px; position:absolute;_ position:relative;*+position:relative;">智能追号
			               	      <a href="#"><img style="POSITION: absolute; TOP: -22px;_TOP: -8px;*+TOP: -6px; LEFT: 33px;" alt="" src="<@c.url value="/pages/images/bz.gif"/>"  onclick="openCapacityChaseHelp();"  ></a></label>
			                 </#if>
							
				       </div>
			      </div>
	             <div class="hemaiq">
			        <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" class="b1">
			          <#if canDltAdditional?? && canDltAdditional>
			          <tr>
			            <td align="right" class="tdboldbggray">是否追加：</td>
			            <td>
			                 <input id="createForm_betMoney" type="hidden" value="2"/><input name="createForm.dltAdditional" type="hidden" value="false"/><input  type="checkbox" onclick="dltAdditional(this)"/>我要追加 
			            </td>
			          </tr>
			          </#if>
			         <#if canChase> 
			          <tr  style="display:none">
			            <td align="right" class="tdboldbggray">是否追号：</td>
			            <td>
			                  <input onclick="chgChase(this.checked);" id="createForm_chase" type="checkbox" name="createForm.chase" value="true" /><label for="createForm_chase" style="font-weight:bold; font-size:14px; color:#F00; padding-bottom:5px;">我要追号</label>
			            </td>
			          </tr>
			          </#if>
			          <tr>
			            <td align="right" class="tdboldbggray">方案描述：</td>
			            <td>
			                 <textarea name="createForm.description" cols="45" rows="3" class="kuangb1gray"></textarea>
			            </td>
			          </tr>
			          <tr  _name="TOGETHER_TR"  style="display:none">
			            <td align="right" class="tdboldbggray">最低认购： </td>
			            <td>
			               <input name="createForm.minSubscriptionCost" onkeydown="number_check(this,event,0);" type="text" id="textfield2" size="7" class="kuanginput" value="1"/>元
			            </td>
			          </tr>
			          
			          <tr  _name="TOGETHER_TR"  style="display:none">
			            <td align="right" class="tdboldbggray">我要认购：</td>
			            <td>
			              <input name="createForm.subscriptionCost" onkeydown="number_check(this,event,0);" type="text" size="7" class="kuanginput" onkeyup="updateSubscriptionPer();"/>
			                                     元，所占比例<span class="redboldchar"><span id="subscriptionPerSpan">0.00</span>%</span>
			            </td>
			          </tr>
			          
			          <tr  _name="TOGETHER_TR"  style="display:none">
			            <td align="right" class="tdboldbggray">发起人提成：</td>
			            <td>
			            	<#assign commissionRate=(createForm.commissionRate)!0.05 />
				            <select name="createForm.commissionRate" size="1" style="width:70px;">
				              <option value="0">无佣金</option>
				              <#list 1..5 as c>
				              	<#assign rate=c/100 />
				              	<option value="${rate?string('0.00')}" <#if commissionRate=rate>selected="selected"</#if> >${c}%佣金</option>
				              </#list>
				            </select>
			            </td>
			          </tr>
			          <tr  _name="TOGETHER_TR"  style="display:none">
			            <td align="right" class="tdboldbggray">我要保底：</td>
			            <td><input name="createForm.baodiCost" type="text" onkeydown="number_check(this,event,0);" size="7" class="kuanginput" onkeyup="updateBaodiCostPer();"/>
			                                            元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span>
			            </td>
			          </tr>
			          <tr  _name="TOGETHER_TR"  style="display:none">
			            <td align="right" class="tdboldbggray">是否公开：</td>
			            <td>
			                <#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
							<#assign defalutType=secretTypeArr[2] />
							<#list secretTypeArr as type>
							  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
							</#list>
			            </td>
			          </tr>
			        </table>
			      </div>
			       <#if canChase> 
			       <div class="hemaiq" id="tab_chase" style="display:none">
				        <table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" class="b1">
				          <tr>
				            <td width="65" height="30" align="right" valign="bottom" class="tdboldbggray">追号期数： </td>
				            <td valign="bottom" class="tddown1px"><span class="right_rig">
				              <select size="1" id="createForm_normalChase_periodSizeOfChase" name="createForm.periodSizeOfChase" onchange="chgChase(true);" disabled="disabled">
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
					            <input type="hidden" id="createForm_normalChase_totalCostOfChase" name="createForm.totalCostOfChase" value="0" cdisabled="disabled" />
					            <input type="checkbox" id="createForm_normalChase_wonStopOfChase" name="createForm.wonStopOfChase" onclick="setwonStop(this.checked)" checked="checked"   value="true"  disabled="disabled" />
							              单期中奖金额≥<input type="text" id="createForm_normalChase_prizeForWonStopOfChase" size="6"  value="0" name="createForm.prizeForWonStopOfChase" onkeydown="number_check(this,event,0)" disabled="disabled"  />元，停止追号任务。
						    </td>
				          </tr>
				        </table>
		          </div>
		          </#if>
		          <!--智能追号-->
		          
		          
		          
		            
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
								     <option value="${period.id}" SELECTED>${period.periodNumber}</option>
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
								 <select id="mzzs_select" name="createForm.expectedHit"  class="zhqs_select">
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
							<input class="zhqs_rig_mi" type="text" id="befortermmember"   name="createForm.befortermmember"  value="5" maxLength="3"/>
							 期累计收益不低于       
							<input class="zhqs_rig_mi" id="beforelc" type="text" name="createForm.beforelc"  value="10" maxLength="6"/>元
							， 之后累计收益不低于					
							 <input class="zhqs_rig_mi" type="text" id="aferlc" name="createForm.aferlc"  value="10" maxLength="6"/>元							
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
				               <input name="lucrepradio" type="radio" id="alllucrep" value="0" class="checkbox"/>
							     全程利润率>=								
						      <select style="width:50px;" id="all_lucrep_select" name="createForm.all_lucrep_select">
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
							  <input class="zhqs_rig_mi"  type="text" id="befortermmemberp" name="createForm.befortermmemberp" value="5" maxLength="3"/>   						
								 期,利润率不低于            
						       <select  style="width:50px;" id="before_lcp_select" name="createForm.before_lcp_select">
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
				              <input id="createForm_capacityChase_wonStopOfChase" type="checkbox" name="createForm.wonStopOfChase"  onclick="setcapacityWonStop(this.checked)" checked value="true"disabled="disabled"/>
				              <input id="createForm_capacityChase_PrizeForWonStopOfChase" type="hidden"  value="0" name="createForm.prizeForWonStopOfChase" disabled="disabled"/>
				              
				       			中奖后停止:投注多期时，当某期中奖后，自动放弃后面几期投注操作。
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
	        <input id="agree" type="checkbox" checked="checked"/>
	        <label class="inputcheckbox graychar">我已阅读了《<a href="#"  onclick="openPurchaseAgreement();return false;">用户合买代购协议</a>》并同意其中条款</label>
	      </div>
	      <!-- 购买按钮-->
	      <div align="center" class="all8px">
	               <span id="span_createForm_submit"> <a href="#" onclick="submitCreateForm();return false;" id="formSubmit"><img src="<@c.url value= "/pages/images/bt_goumai.gif"/>" /></a></span>
      	           <span id="span_createForm_waiting" style="display:none;">正在提交,请稍等...</span>
	      </div>
	      <input type="hidden" name="request_token" value="${datetime().millis}" />
	      <input type="hidden" name="createForm.periodId" id="createFormPeriodId" value="${period.id}" />
		  <input type="hidden" id="computezhushu" name="createForm.units" value="0" />
		  <input type="hidden" id="schemeCost" name="createForm.schemeCost" value="0" />
	      <input type="hidden" name="createForm.mode" value="${salesMode}"/>
	      <input type="hidden" id="content" name="createForm.content" value=""/>
	      <input type="hidden" id="lotteryType_key" name="lotteryType_key" value="${lotteryType.key}"/>
          <input type="hidden" id="isCapacity" value="false"/>
	      
	      
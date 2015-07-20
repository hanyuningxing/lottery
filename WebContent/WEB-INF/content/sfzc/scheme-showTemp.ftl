<#assign scheme=schemeTemp>
<@override name="title"></@override>
<@override name="addHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
</@override>
<script type="text/javascript">
	/**
	 * 更改购买方式
	 * 
	 * @param obj
	 */
	function chgShareType(obj) {
		var createForm = getCreateForm();
		var hemaiTable = $$('hemaiTable');
		var schemeCost = createForm.elements['createForm.schemeCost'].value;
		if (obj.value == 'TOGETHER') {
			if(schemeCost!=0){
				createForm.elements['createForm.subscriptionCost'].value=schemeCost/2;
				updateSubscriptionPer();
			}
			hemaiTable.style.display = '';
			fadeon("hemaiTable");
		} else {
			hemaiTable.style.display = 'none';
		}
	};
</script>

<@override name="progress"></@override>

<@override name="content">
	<#if canViewDetail>
		<#if scheme.mode=='COMPOUND'>
			<#assign hasDan=scheme.playType=='SFZC9' />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
		        <tr class="v01tr">
				  <td>场次</td>
				  <td>主客队</td>
				  <td>比赛时间</td>
				  <td>比分</td>
				  <td>彩果</td>
				  <#if hasDan><td>设胆</td></#if>
				  <td>复式投注</td>
				</tr>
			    <#list items as betItem>
			    	<#assign match=matchs[betItem_index] />
					<tr class="v02tr">
					  <td>${(match.lineId+1)?string('00')}</td>
					  <td>${match.homeTeamName!} <font color="red">VS</font> ${match.guestTeamName!}</td>
					  <td>${match.matchTime?string("MM-dd HH:mm (E)")}</td>
					  <td><#if match.cancel>取消<#elseif match.homeScore?? && match.guestScore??>${match.homeScore}:${match.guestScore}<#else>&nbsp;</#if></td>
					  <td class="redc">${match.result!}&nbsp;</td>
					  <#if hasDan><td><#if betItem.shedan>√<#else>&nbsp;</#if></td></#if>
					  <td>${betItem.toBetString()}</td>
					</tr>
			    </#list>
			</table>
		<#elseif scheme.mode=='SINGLE'>
			<textarea name="content" cols="106" rows="10" readonly="readonly" style="border:1px solid #A7CAED;font-size:12px;">${(scheme.contentText)!}</textarea>
		</#if>
	<#else>
      	${scheme.secretType.secretName}
      </#if>	
</@override>

<@override name="operSale">
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">		
		<tr class="fd_trw" >
	        <td align=" center" class="fd_trg">我要认购</td>
	        <td  style="line-height:26px;"><table width="90%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td style="border-bottom:none;">
	              		<div id="common_logined" style="display:none">
	              			<span class="gc0" id="common_login_name"></span> 您的帐户余额：<strong> <span class="red" id="common_remainMoney"></span></strong> 元 [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action">帐户充值</a> ]
		              	</div>
		              	<#if loginUser??>
		              		<div>
			              		<span class="gc0"></span> 您的帐户余额：<strong><span class="red">${loginUser.remainMoney?string.currency}</span></strong> 元 [ <a id="confirm_tz_payPer_a_" href="${base}/user/fund!payPer.action">帐户充值</a> ]
			              	</div>	       
			        	<#else>
			        		<div id="common_unlogin">未登录 【<a href="javascript:void(0)" onclick="$('#userquickLogin').dialog('open'); return false;" class="blue_0091d4">登录</a>】</div>		        	              	
			        	</#if>
	              		
	              		<div style="padding-left:30px;background-color:#0FA5D4;color:#fff;">
	              			<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">代购&nbsp;</label>
			          		<input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">合买&nbsp;</label>
				        </div>
	              		
		              	<table id="hemaiTable" width="100%" cellspacing="0" cellpadding="1" border="0" style="background-color:#ccc;display:none;">
				           	<tr align="center" class="tdwhitelist"  height="24">
					          	<td style="text-align:right" width="20%">最低认购： </td>
					          	<td style="text-align:left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="inputw" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
					        </tr>
					        <tr align="center" class="tdwhitelist"  height="24">
					          	<td style="text-align:right" width="20%">我要认购：</td>
					          	<td style="text-align:left">
					              <input name="createForm.subscriptionCost" type="text" size="7" class="inputw" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="subscriptionPerSpan">0.00</span>%</span>
					          	</td>
					        </tr>
					        <tr align="center" class="tdwhitelist"  height="24">
					            <td style="text-align:right" width="20%">发起人提成：</td>
					            <td style="text-align:left">
						            <#assign commissionRate=(createForm.commissionRate)!0.00 />
						            <select id="createForm.commissionRate" name="createForm.commissionRate" size="1" style="width:70px;height:22px; line-height:22px; color:#555; margin:0; padding:0;border:1px solid #ccc;">
						              <option value="0">无佣金</option>
						              <#list 1..5 as c>
						              	<#assign rate=c/100 />
						              	<option value="${rate?string('0.00')}" <#if commissionRate=rate>selected="selected"</#if> >${c}%佣金</option>
						              </#list>
						            </select>
					            </td>
					        </tr>
					        <tr align="center" class="tdwhitelist"  height="24">
					            <td style="text-align:right" width="20%">我要保底：</td>
					            <td style="text-align:left"><input name="createForm.baodiCost" type="text" size="7" class="inputw" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="baodiCostPerSpan">0.00</span>%</span></td>
					        </tr>
					        <tr align="center" class="tdwhitelist"  height="24">
					            <td style="text-align:right" width="20%">方案描述：</td>
					            <td style="text-align:left"><textarea style="border:solid 1px #BABABA; color:#787878; padding:1px;" rows="5" cols="45" name="createForm.description"></textarea></td>
					        </tr>
			          </table>
		          
	              </td>
	              <td width="180" valign="bottom" style="border-bottom:none;"> </td>
	            </tr>
	            <tr height="24">
		            <td colspan="2" style="padding-left:30px;text-align:left"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label></td>
		        </tr>
	            <tr>
	            	<td colspan="2" style="padding-left:30px;text-align:left">
	            		<a href="javascript:;" class="btbuy" onclick="submitCreateForm('buy');return false;"></a><span id="span_createForm_submit"></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span>
	            	</td>
	            </tr>
	          </table></td>
	        <td>&nbsp;</td>
	      </tr>
	       <input type="hidden" name="tempSchemeId" value="${scheme.id}" />
  		   <input type="hidden" name="createFormTempOfSingle" value="true" />
  		   <input type="hidden" name="createForm.playType" value="${scheme.playType!}" />
  		   <input type="hidden" name="playType" value="${scheme.playType!}" />
	       <input type="hidden" name="createForm.periodId" value="${period.id}" />
	       <input type="hidden" name="request_token" value="${datetime().millis}" />
	       <input type="hidden" name="createForm.schemeCost" value="${scheme.schemeCost!}" />
	       <input type="hidden" name="createForm.units" value="${scheme.units!}" />
	       <input type="hidden" name="createForm.multiple" value="${scheme.multiple!}" />
	       <input type="hidden" name="createForm.mode" value="${scheme.mode}"/>
	       <input type="hidden" name="createForm.secretType" value="${scheme.secretType}"/>
	       <input type="hidden" name="createForm.fromFreeSave" value="true"/>
	</form>   
</@override>
<@override name="schemeShare"></@override>
<@override name="schemeSaleInfo"></@override>

<#include "/common/userRechargeDialog.ftl" />

<@extends name="/WEB-INF/content/common/simple-scheme-showV1.ftl"/>
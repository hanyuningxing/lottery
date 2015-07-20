<head>
	<@block name="title"></@block>
	<@block name="decorator"><meta name="decorator" content="tradeV1" /></@block>
	<@block name="style"></@block>
	<@block name="head"></@block>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
</head>

<!--main-->
<div class="w1000">
  <div class="k10" <#if playTypeWeb?? && (playTypeWeb="DGP" || playTypeWeb=="YP")>style="display:none"</#if>></div>
  <div class="jczqjjyh">
    <form id="optimizeForm" action="${base}/${lotteryType.key}/optimize!bonusOptimize.action" method="post" autocomplete="off">
      <input type="hidden" name="initMoney" value="${schemeCost}"/>      
      <div class="jczqjjyhtit"  <#if playTypeWeb?? && (playTypeWeb="DGP" || playTypeWeb=="YP")>style="display:none"</#if>>
	  	<span class="black_20">${playTypeWeb.text}</span><br />
	    <span class="blue_14"><strong>奖金优化</strong>（<a href="#" style="text-decoration:underline;" onmouseover="tip.show2(this);" tips="<div style='width:500px;border:1px solid #EB6502;background-color:#FEEDDD;padding:8px;line-height:20px;'><p><b>功能说明： </b><br /><p>奖金优化就是针对投注的复式倍投方案，用户可以根据自己的需求，自行对单注注数进行分配的功能。网站提供了三种优化方式，一种是“平衡优化”，一种是“保守优化”，一种是“博冷优化”。<br /><b>优化方式： </b><br />1、平衡优化是使投注的每个单注奖金都趋于一致（根据选项赔率不同不一定都很平均），这样中奖奖金都会相差不多，可以保证一定比例的盈利。<br />2、保守型优化是使投注的单注方案中，概率最大的那一注奖金最大化（赔率值最低的选项组合的单注），其它单注奖金相对保本。<br />3、博冷型优化是使投注的单注方案中，奖金最大的那一注奖金最大化（赔率值最高的选项组合的单注），其它单注奖金相对保本。<br /><b>优化支持： </b></p>只支持2串1 - 8串1的复式倍投方案，最高支持注数1000000注，不限倍投。</div>">功能说明</a> <a href="${base}/jczq/scheme!wonList.action?playType=MIX" style="text-decoration:underline;">中奖查询</a>）</span>	
	  </div>
      <div class="border04 marginb20" <#if playTypeWeb?? && (playTypeWeb="DGP" || playTypeWeb=="YP")>style="display:none"</#if>>
	  	<div class="jjyhtit white_FFF_14">选择优化方式</div>
	    <div class="youhua01">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="22%" rowspan="3"><strong class="red_F00_16">计划投注:<input id="planSchemeCost" name="createForm.schemeCost" type="text" value="${schemeCost}" size="5" class="jhtzinp" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/> 元</strong></td>
              <td width="48%" height="26"><strong>
                <input type="radio" id="optimizeType_PINGJUN" name="optimizeType" <#if optimizeType=="PINGJUN">checked="checked"</#if> value="PINGJUN" />
                <span class="yel_F60"><label id="label_optimizeType_PINGJUN" for="optimizeType_PINGJUN">平衡优化</label></span></strong>&nbsp;&nbsp;&nbsp;使每个单注的奖金趋于一致。<br /></td>
              <td rowspan="3" align="left"><a href="javascript:;" onclick="doOptimize();return false;" class="youhua"></a></td>
            </tr>
            <tr>
              <td height="26"><strong>
                <input type="radio" id="optimizeType_BAOSHOU" name="optimizeType" <#if optimizeType=="BAOSHOU">checked="checked"</#if> value="BAOSHOU" />
                <span class="yel_F60"><label id="label_optimizeType_BAOSHOU" for="optimizeType_BAOSHOU">保守优化</label></span></strong>&nbsp;&nbsp;&nbsp;使概率最高的单注奖金最大化，其它单注的奖金保本。</td>
            </tr>
            <tr>
              <td height="26"><strong>
                <input type="radio" id="optimizeType_BOLENG" name="optimizeType" <#if optimizeType=="BOLENG">checked="checked"</#if> value="BOLENG" />
                <span class="yel_F60"><label id="label_optimizeType_BOLENG" for="optimizeType_BOLENG">博冷优化</label></span></strong>&nbsp;&nbsp;&nbsp;使奖金最高的单注奖金最大化，其它单注的奖金保本。</td>
            </tr>
          </table>
	    </div>
		<div style="color:#333;">
		  <table width="996" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="jjyhtab">
            <tr class="bgD5F6FD">
              <td width="7%" height="30" align="center">场次</td>
              <td width="8%" align="center">赛事</td>
              <td width="10%" align="center">开赛时间</td>
              <td width="10%" align="center">主队</td>
              <td width="10%" align="center">客队</td>
              <td width="11%" align="center">玩法（让球）</td>
              <td align="center">投注详情</td>
            </tr>
            <#list selectedMatchList as matchInfo>
            	<#assign match=matchInfo[0]>
            	<#assign matchItems=matchInfo[1]>
            	<tr>
	              <td height="30" align="center" bgcolor="#FFFFFF">${match.matchKeyText}</td>
	              <td align="center" bgcolor="#a07f1c" class="whitechar">${match.gameName}</td>
	              <td align="center" bgcolor="#FFFFFF" title="开赛时间：${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('MM-dd HH:mm')}</td>
	              <td align="center" bgcolor="#FFFFFF">${match.homeTeamName!}</td>
	              <td align="center" bgcolor="#FFFFFF">${match.guestTeamName!}</td>
	              <td align="center" bgcolor="#FFFFFF"><#if matchItems?size gt 1>混合投注<#else>${matchItems.get(0).playType.text}</#if></td>
	              <td align="center" bgcolor="#FFFFFF">
	              <#list matchItems as matchItem>
	                <#assign items=matchItem.buildSelectedItemList()>
	                <#list items as item>
	              		<a href="javascript:;" class="tzxqjg button2">${item}</a>
	              	</#list>
	              </#list>
	              </td>
	            </tr>
            </#list>            
          </table>
		</div>
    </div>
    <#list createForm.passTypes as passType>
    	<input type="hidden" name="createForm.passTypes" value="${passType}" />
    </#list>
    <#list createForm.items as item>
    	<input type="hidden" name="createForm.items[${item_index}].matchKey" value="${item.matchKey}" />
    	<input type="hidden" name="createForm.items[${item_index}].value" value="${item.value}" />
    	<input type="hidden" name="createForm.items[${item_index}].dan" value="${item.isDan()?string}" />
    	<input type="hidden" name="createForm.items[${item_index}].playType" value="${item.playType}" />
    	<input type="hidden" name="createForm.items[${item_index}].spStr" value="${item.buildSpStr()}" />
    </#list>
    </form>
    
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
	<div class="border04 marginb20">
	  	  <div class="jjyhtit white_FFF_14">手动调节</div>
	      <div style="color:#333;">
	        <table id="TableOptimize" width="996" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="jjyhtab">
              <tr class="bgD5F6FD">
                <td width="5%" height="30" align="center">序</td>
                <td width="65%" align="center">单注</td>
                <td width="12%" align="center">注数</td>
                <td align="center">理论奖金</td>
              </tr>
              <#if results??>
				<#list results as item>
					<tr id="tr_${item_index}" prize="${item[2]?string('0.00')}">
						<span style="display:none">
							<input name="createForm.contents[${item_index}]" type="hidden" value="${item[3]}"/>
						    <input name="createForm.playTypes[${item_index}]" type="hidden" value="${item[4]}"/>
						    <input name="itemPrizes[${item_index}]" type="hidden" value="${item[2]?string('0.00')}">
						</span>
                		<td height="30" align="center" bgcolor="#FFFFFF">${item_index+1}</td>
                		<td align="left" bgcolor="#FFFFFF">                			
							<#list item[0] as contentItems>
								${contentItems[0]}${contentItems[1]} [<strong class="yel_F60">${contentItems[2]}<#if contentItems[4]??>(<span class="rc1">${contentItems[4]}</span>)</#if> ${contentItems[3]?string('0.00')}</strong>] ×
							</#list> 2 = <strong class="yel_F60">${item[2]?string('0.00')}</strong>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<table width="80" height="21" border="0" cellpadding="0" cellspacing="1" bgcolor="#bbbbbb" class="jjyhtab2">
			                  <tr>
			                    <td width="19"><a class="jian button3" href="javascript:;" onclick="AddItemMutiple(-1,${item_index});"></a></td>
					            <td align="center" bgcolor="#FFFFFF"><input onkeyup="AddItemMutiple(0,'${item_index}');" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiples[${item_index}]" type="text" value="${item[1]}" size="9" maxLength="9" style="width:93px;height:18px;text-align:center;font-weight:bold;" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/></td>
					            <td width="19"><a class="jia button3" href="javascript:;" onclick="AddItemMutiple(1,${item_index});"></a></td>
			                  </tr>
			                </table>
			            </td>
						<td align="center" bgcolor="#FFFFFF" class="rc1"><strong><span id="prize_${item_index}"> ${(item[2]*item[1])?string('0.00')}</span></strong></td>					    
					 </tr>
				</#list>
			  </#if>           
            </table>
        </div>
    </div>
	<div class="border04 marginb20">
	  	  <div class="jjyhtit white_FFF_14">中奖预测</div>
	      <div style="color:#333;" id="forcastDataHtml">
	        <table width="100%" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" style="color:#333;" class="jjyhtab">
              <tr class="bgD5F6FD">
                <td width="13%" height="30" align="center" bgcolor="#D5F6FD">命中场数</td>
                <td width="18%" height="30" align="center" bgcolor="#D5F6FD">中出奖金范围</td>
                <td width="20%" height="30" align="center" bgcolor="#D5F6FD">回报率</td>
                <td height="30" align="left" bgcolor="#D5F6FD" id="updateForecastOper"><a id="updateForecastBtn" class="butjjyc button3" href="javascript:;" onclick="updateForecast();return false;" <#if prizeForecasts??>style="display:none;"</#if>></a></td>
              </tr>
              <#if prizeForecasts??>            
	              <#list prizeForecasts as prizeForecast>
	              	<tr>
	                	<td height="30" colspan="4" align="center" bgcolor="#FFFFFF">
	                		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                    		<tr>
	                    			<td width="13%" align="center">中${prizeForecast.key}场</td>
			                      	<td width="18%" align="center" class="red" id="minMaxPrize_${prizeForecast_index}">${prizeForecast.value.minPrize?string('0.00')} - ${prizeForecast.value.maxPrize?string('0.00')}</td>
			                      	<td width="20%" align="center" id="minMaxPercent_${prizeForecast_index}">${prizeForecast.value.minPercent} - ${prizeForecast.value.maxPercent}</td>
			                      	<td align="center">&nbsp;</td>
			                    </tr>
	                		</table>
	                	</td>			  	
					</tr>
				  </#list>
			  </#if>
			</table>
	      </div>
    </div>
	<div class="border04 marginb20">
	  	  <div class="jjyhtit white_FFF_14">确认投注信息</div>
	      <div style="color:#333;">
	        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="jjyhtab">
              <tr>
                <td width="11%" height="30" align="center" class="bgD5F6FD"><strong>方案金额</strong></td>
                <td bgcolor="#FFFFFF"><strong><span class="rc1" id="schemeCost">${schemeCost}</span></strong> 元</td>
              </tr>              
              <tr <#if loginUser?? && (loginUser.userId==415 || loginUser.userId==418 || loginUser.userId==356 || loginUser.userId==428)><#else>style="display:none;"</#if>>
                <td width="11%" height="30" align="center" class="bgD5F6FD"><strong>方案倍投</strong></td>
                <td bgcolor="#FFFFFF"><input onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" type="text" value="1" size="9" maxLength="9" style="width:93px;height:22px;text-align:center;font-weight:bold;border:1px solid #999999" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/></td>
              </tr>
               <tr>
			        <td height="30" align="center" class="bgD5F6FD"><strong>我的余额</strong></td>
			        <td bgcolor="#FFFFFF" class="pad010">
			        <#if loginUser??>
			        	<div><strong><span class="rc1">${loginUser.remainMoney?string.currency}</span></strong> [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action" target="_blank">帐户充值</a> ]</div>
			        <#else>
			        	<div id="common_unlogin">未登录 【<a href="javascript:void(0)" onclick="$SSO.login_auth(); return false;" class="blue_0091d4">登录</a>】</div>
			        </#if>
			       		<div id="common_logined" style="display:none"><strong><span class="rc1" id="common_remainMoney"></span></strong> [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action" target="_blank">帐户充值</a> ]</div>	
			        </td>
			        	      	
			   </tr>                
              <tr>
                <td height="30" align="center" class="bgD5F6FD"><strong>购彩类型</strong></td>
                <td bgcolor="#FFFFFF">
                	<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">代购&nbsp;</label>
	          		<input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">合买&nbsp;</label>
	          		<input id="freeSave" type="radio" value="true" name="freeSave" onclick="chgShareType(this);"/><label for="freeSave">免费保存</label>
	          		<a href="javascript:;"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0" tips="<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>" onmouseover="SaveTips(this)"/></a>
	          	</td>
              </tr>
              <tr id="tr_TOGETHER_content" style="display:none">
                <td height="30" align="center" class="bgD5F6FD">&nbsp;</td>
		      	<td bgcolor="#FFFFFF">
		      		<table width="100%" cellspacing="0" cellpadding="1" border="0" style="background-color:#ccc;">
			            <tr align="left" class="tdwhitelist"  height="24">
				          	<td width="11%" style="text-align:right">最低认购： </td>
				          	<td style="text-align:left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="inputw" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
				        </tr>
				        <tr align="left" class="tdwhitelist"  height="24">
				          	<td style="text-align:right">我要认购：</td>
				          	<td style="text-align:left">
				              <input name="createForm.subscriptionCost" type="text" size="7" class="inputw" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="subscriptionPerSpan">0.00</span>%</span>
				          	</td>
				        </tr>
				        <tr align="left" class="tdwhitelist"  height="24">
				            <td style="text-align:right">发起人提成：</td>
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
				        <tr align="left" class="tdwhitelist"  height="24">
				            <td style="text-align:right">我要保底：</td>
				            <td style="text-align:left"><input name="createForm.baodiCost" type="text" size="7" class="inputw" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="baodiCostPerSpan">0.00</span>%</span></td>
				        </tr>
				        <tr align="left" class="tdwhitelist"  height="24">
				            <td style="text-align:right">方案描述：</td>
				            <td style="text-align:left"><textarea style="border:solid 1px #BABABA; color:#787878; padding:1px;" rows="3" cols="35" name="createForm.description"></textarea></td>
				        </tr>
		          </table>
		      	</td>
		      </tr>
              <tr>
                <td height="30" align="center" class="bgD5F6FD"><strong>保密设置</strong></td>
                <td bgcolor="#FFFFFF">
                	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
					<#assign defalutType=secretTypeArr[2] />
					<#list secretTypeArr as type>
					  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName} </label>
					</#list>
				</td>
              </tr>
              <tr height="30">
              	<td height="30" align="center" class="bgD5F6FD"><strong>购买协议</strong></td>
		      	<td  bgcolor="#FFFFFF"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label></td>
		      </tr>
              <tr>
                <td height="50" colspan="2" bgcolor="#FFFFFF" style="padding-left:180px;"><span id="span_createForm_submit"><a id="submitButton" href="javascript:;" onclick="submitCreateForm();return false;" class="yydaigou"></a></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span></td>
              </tr>
            </table>
	      </div>
    </div>
    <#list createForm.items as item>
    	<input type="hidden" name="createForm.items[${item_index}].matchKey" value="${item.matchKey}" />
    	<input type="hidden" name="createForm.items[${item_index}].value" value="${item.value}" />
    	<input type="hidden" name="createForm.items[${item_index}].dan" value="${item.isDan()?string}" />
    	<input type="hidden" name="createForm.items[${item_index}].playType" value="${item.playType}" />
    	<input type="hidden" name="createForm.items[${item_index}].spStr" value="${item.buildSpStr()}" />
    </#list>
    <input type="hidden" id="createForm.schemeCost" name="createForm.schemeCost" value="${schemeCost}" />
	<input type="hidden" name="createForm.schemeType" value="SIMPLE_PASS"/>
	<input type="hidden" name="createForm.passTypes" value="${passType}"/>
	<#if playTypeWeb??>
    	<input type="hidden" name="playTypeWeb" value="${playTypeWeb}" />
    </#if>
	<input type="hidden" name="playType" value="${playType}"/>
	<input type="hidden" name="createForm.mode" value="${salesMode}"/>
	<input type="hidden" name="createForm.passMode" value="${passMode}"/>
	<input type="hidden" name="request_token" value="${datetime().millis}" />
	<input type="hidden" name="createForm.periodId" value="${period.id}" />
	<input type="hidden" id="createForm.units" name="createForm.units" value="${schemeCost/2}" />
	<input type="hidden" name="createForm.optimize" value="true" />
	<input type="hidden" name="createForm.matchKeys" value="${matchKeys}" />
	<input type="hidden" id="createForm.bestMinPrize" name="createForm.bestMinPrize" value="${bestMinPrize!}" />
	<input type="hidden" id="createForm.bestMaxPrize" name="createForm.bestMaxPrize" value="${bestMaxPrize!}" />
	<#if jczqChasePlanDetailId??>
     	<input type="hidden" name="createForm.jczqChasePlanDetailId" value="${jczqChasePlanDetailId}" />
    </#if>
    </form>
  </div>
  <!--mleft end -->
  <!--mright begin -->
  <!--main end -->
</div>
<#include "/common/schemeSuccessDialog.ftl" />

<script language="javascript" type="text/javascript">
	function updateBetCost(){
		var multiple = getMultiple();
		if(!isNaN(multiple)){
			$$("schemeCost").innerHTML=$$("createForm.units").value*multiple*2;
			$$("createForm.schemeCost").value=$$("createForm.units").value*multiple*2;
		}else{
			alert("倍投只能是数字，请正确填写!");
		}
	}

	function AddItemMutiple(value,num){
		var form=document.forms["createForm"];
		var times=form.elements["createForm.multiples["+ num +"]"];
		var multiple=parseInt(times.value)+value;
		if(multiple<0){
		    setCommonResult("注数至少为0..", false);
		    return;
		}
		if (!isNaN(multiple))
		    times.value = multiple;
		else
		    multiple = 0;
	
	    var tr=$$("tr_"+num);
	    $$("prize_"+num).innerHTML=(parseFloat(tr.getAttribute("prize")) * multiple).toFixed(2);
	    	
		var multipleCount=0;
		for(var k=1;k<$$("TableOptimize").rows.length;k++) {
		    var theInt = parseInt(form.elements["createForm.multiples["+ (k-1) +"]"].value);
		    if (isNaN(theInt)) theInt = 0;
		    multipleCount += theInt;
	    }
	    $$("createForm.units").value=multipleCount;
	    $$("schemeCost").innerHTML=multipleCount*2;
	    $$("createForm.schemeCost").value=multipleCount*2;
	    $("#updateForecastBtn").css("display","block");
	}
	
	function doOptimize(){
		var v=document.getElementsByName('optimizeType');
		var optimizeTypeItem;
		for (var i=0;i<v.length;i++){
		 var optimizeTypeItemT = v.item(i);
		 if(optimizeTypeItemT.checked){
		   optimizeTypeItem=optimizeTypeItemT;
		   break;
		 }
		}
		
		var planSchemeCost = parseInt($$("planSchemeCost").value);
		if(optimizeTypeItem.id=='optimizeType_BAOSHOU' || optimizeTypeItem.id=='optimizeType_BOLENG'){
		    var multipleCount=0;
			for(var k=1;k<$$("TableOptimize").rows.length;k++) {
				var tr=$$("tr_"+(k-1));
			    var itemPrize = parseFloat(tr.getAttribute("prize"));
			    var theInt = Math.ceil(planSchemeCost/itemPrize);
			    multipleCount += theInt;
		    }
		    var minPlanSchemeCost = multipleCount*2;
		    if(minPlanSchemeCost>planSchemeCost){
		    	var optimizeTypeStr = $$('label_'+optimizeTypeItem.id).innerHTML;
		    	setCommonResult("当前计划金额不足以执行："+optimizeTypeStr, false);
		    	return;
		    }
		}		
		
		if(!isNaN(planSchemeCost)){
			var minCost = ($$("TableOptimize").rows.length-1)*2;
			if(planSchemeCost<minCost){
				setCommonResult("优化最少的金额为 "+minCost+"元", false);
				return;
			}
			if(planSchemeCost%2!=0){
				setCommonResult("金额必须是 2 的倍数", false);
				return;
			}
		}else{
			setCommonResult("方案金额有误，请重新填写！", false);
			return;
		}
		
		$$("optimizeForm").submit();		
	}
	
	
	var updateForecastOper_default = '';
	var updateForecastOper_default_error = '';
	function updateForecast(){
		var createForm = getCreateForm();
		var url = createForm.action.replace('/scheme!create.action', '/optimize!prizeForecastOfAjax.action');
		if(updateForecastOper_default==''){
			updateForecastOper_default = $("#updateForecastOper").html();
		}
		if(updateForecastOper_default_error==''){
			updateForecastOper_default_error = updateForecastOper_default+' (可能存在网络异常，请重新执行...)';
		}
		var options = {
			url : url,
			async : false,
			type : 'POST',
			cache : false,
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				if (jsonObj.success == true) {
					forecastData = jsonObj.prizeForecast;
					var forcastDataHtml = '';
					forcastDataHtml+='<table width="100%" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" style="color:#333;" class="jjyhtab">';
					forcastDataHtml+='    <tr class="bgD5F6FD">';
					forcastDataHtml+='		<td width="13%" height="30" align="center" bgcolor="#D5F6FD">命中场数</td>';
					forcastDataHtml+='		<td width="18%" height="30" align="center" bgcolor="#D5F6FD">中出奖金范围</td>';
					forcastDataHtml+='		<td width="20%" height="30" align="center" bgcolor="#D5F6FD">回报率</td>';
					forcastDataHtml+='		<td height="30" align="left" bgcolor="#D5F6FD" id="updateForecastOper"><a id="updateForecastBtn" class="butjjyc button3" href="javascript:;" onclick="updateForecast();return false;"></a></td>';
					forcastDataHtml+='	  </tr>';
					for(var i=0;i<forecastData.length;i++){
						forcastDataHtml+='<tr>';
		                forcastDataHtml+='	<td height="30" colspan="4" align="center" bgcolor="#FFFFFF">';
		                forcastDataHtml+='		<table width="100%" border="0" cellspacing="0" cellpadding="0">';
		                forcastDataHtml+='    		<tr>';
		                forcastDataHtml+='    			<td width="13%" align="center">中'+forecastData[i][0]+'场</td>';
				        forcastDataHtml+='              	<td width="18%" align="center" class="red">'+forecastData[i][1]+'-'+forecastData[i][2]+'</td>';
				        forcastDataHtml+='             	    <td width="20%" align="center">'+forecastData[i][3]+'-'+forecastData[i][4]+'</td>';
				        forcastDataHtml+='              	<td align="center">&nbsp;</td>';
				        forcastDataHtml+='            </tr>';
		                forcastDataHtml+='		</table>';
		                forcastDataHtml+='	</td>';
						forcastDataHtml+='</tr>';
					}
					forcastDataHtml+='<table>';
					$("#forcastDataHtml").html(forcastDataHtml);
					$("#updateForecastOper").html(updateForecastOper_default);
					$("#updateForecastBtn").css("display","none");
					$$("createForm.bestMinPrize").value=jsonObj.bestMinPrize;
					$$("createForm.bestMaxPrize").value=jsonObj.bestMaxPrize;
				} else {
					var msg = getCommonMsg(jsonObj);
					setCommonResult(msg, false);
					$("#updateForecastOper").html(updateForecastOper_default_error);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#updateForecastOper").html(updateForecastOper_default_error);
			},
			beforeSend : function(XMLHttpRequest) {
				$("#updateForecastOper").html("请稍后...奖金预测计算中.....");
			},
			complete : function(XMLHttpRequest, textStatus) {		
			}
		};
		$(createForm).ajaxSubmit(options);		
	}
	
	/**
	 * 更改购买方式
	 * 
	 * @param obj
	 */
	window.chgShareType = function(obj) {
		var createForm = getCreateForm();
		var tr_TOGETHER_content = $$('tr_TOGETHER_content');
		var schemeCost = createForm.elements['createForm.schemeCost'].value;
		if (obj.value == 'TOGETHER') {
			if(schemeCost!=0){
				createForm.elements['createForm.subscriptionCost'].value=schemeCost/2;
				updateSubscriptionPer();
			}
			tr_TOGETHER_content.style.display = '';
			fadeon("tr_TOGETHER_content");
			$$('freeSave').checked=false;
			$$('submitButton').className='qrhmdd';
			$$("createForm_secretType_FULL_PUBLIC").checked=true;
		}else if (obj.value == 'true') {
			tr_TOGETHER_content.style.display = 'none';
			$$('createForm_shareType_SELF').checked=false;
			$$('createForm_shareType_TOGETHER').checked=false;
		}else if (obj.value == 'SELF'){
			tr_TOGETHER_content.style.display = 'none';
			$$('freeSave').checked=false;
			$$('submitButton').className='qrhmdd';
			$$("createForm_secretType_FULL_SECRET").checked=true;
		}
	};
	
</script>
<@override name="editNewHead">
	<script type="text/javascript">
		$(function() {
			window.PassTypeUtil = {};
			window.PassTypeUtil.getSinglePassType = function(matchCount){
				if (matchCount < 1 || matchCount > 15) 
			      	return [];
			    if(matchCount == 1)
			    	return [PassTypeArr[0]];
			      
			    var types = [];
			    for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
			      	var type = PassTypeArr[i];
			      	if (type.matchCount <= matchCount && type.units == 1 && type.matchCount <= window.maxPassMatchCount) {
			        	if(matchCount > 1 && i == 0)
			          	continue;
			        	types.push(type);
			      	}else if (type.matchCount > matchCount || type.matchCount > window.maxPassMatchCount){
			        	break;
			      	}
			    }
			    return types;
			};
		});		
		<#-- 注：此JS必须在compoundInit.js之前 -->
	</script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/singleInit.js"/>"></script>
</@override>

<@override name="initContent">
	<div class="kong10"></div>
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
		<table width="805" cellspacing="1" cellpadding="3" border="0" class="dctbg">
	      <tbody>
	        <tr align="center" class="trw">
	          <td width="100" class="dctro">已选场次：</td>
	          <td height="24" align="left" id="selected_match_container"></td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">过关方式：</td>
	          <td height="24" align="left" id="pass_container">&nbsp;</td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">格式说明：</td>
	          <td height="24" align="left">
	          	<#if itemMap??>
				  <table cellspacing="1" cellpadding="2" border="0" class="dctbg">
			       	<tbody>
			          <#list itemMap.entrySet() as entry>
			          	<#assign arrSize=entry.getValue()?size />
			          	<tr class="trlightblue boldchar" align="center">
				          <td width="120">选项</td>
				          <#list entry.getValue() as item>
				          <td width="75">${item.text}</td>
				          </#list>
				          <td <#if arrSize lt itemColspan >colspan="${itemColspan-arrSize+1}"</#if>>&nbsp;</td>
			          	</tr>
			          	<tr class="trw">
				          <td>上传字符</td>
				          <#list entry.getValue() as item>
				            <td><input maxlength="1" size="5" name="createForm.codes" _d="${item.value}" value="${item.value}" /></td>
				          </#list>
				          <td <#if arrSize lt itemColspan >colspan="${itemColspan-arrSize+1}"</#if>><input type="button" value="默认" onclick="resetCodes();" /></td>
			          	</tr>
			          </#list>
			      	</tbody>
				  </table>
				<#else>
				  <table cellspacing="1" cellpadding="2" border="0" class="dctbg">
			        <tbody>
			          <tr class="trlightblue boldchar" align="center">
				      	<td width="120">选项 </td>
				        <#list itemArr as item>
				          <td width="75">${item.text}</td>
				        </#list>
				        <td>&nbsp;</td>
			          </tr>
			          <tr class="trw">
				        <td>上传字符</td>
				        <#list itemArr as item>
				          <td><input maxlength="1" size="5" name="createForm.codes" _d="${item.value}" value="${item.value}" /></td>
				        </#list>
				        <td><input type="button" value="默认" onclick="resetCodes();" /></td>
			          </tr>
			      	</tbody>
			      </table>
			 	</#if>
	          </td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">文件上传：</td>
	          <td height="24" align="left">
	          	<input type="hidden" name="createForm.fileUpload" value="true" />
           		<input type="file" name="createForm.upload" id="createForm_upload" onchange="chgUploadFile(this);" disabled="disabled" /><input type="button" id="createForm_autoCount" value="计算注数" onclick="chgUploadFile(document.getElementById('createForm_upload'));" disabled="disabled" class="btn" /><span id="upload_waiting" style="display:none">正在计算注数...</span>
				&nbsp;&nbsp;&nbsp;<a href="<@c.url value='/html/dczc/single-format-${playType}.html' />" target="_blank">查看文件格式说明 </a>
	          </td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">投注结果：</td>
	          <td height="24" align="left" class="redc">
	          	<span id="span_msg">共<span class="rebchar" _name="createForm_units">0</span>注，<span class="rebchar"><input type="text" style="width: 40px;IME-MODE: disabled;" value="1" onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" maxLength="3" class="txtbox color_fff" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" /></span>倍，金额<span class="redboldchar">￥<span _name="createForm_schemeCost">0</span></span></span>
	          	<span id="span_err" class="rebchar"></span>
	          </td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">购买方式：</td>
	          <td height="24" align="left">
	          	<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="singleChgShareType(this);"/><label for="createForm_shareType_SELF">我要自购</label>
	            <input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="singleChgShareType(this);" checked="checked"/><label for="createForm_shareType_TOGETHER">发起合买</label>
	          </td>
	        </tr>
	        <tr align="center" class="trw" id="tr_minSubscriptionCost">
	          <td class="dctro">最低认购：</td>
	          <td height="24" align="left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="kuanginput" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
	        </tr>
	        <tr align="center" class="trw" id="tr_subscriptionCost">
	          <td class="dctro">我要认购：</td>
	          <td height="24" align="left"><input name="createForm.subscriptionCost" type="text" size="7" class="kuanginput" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="subscriptionPerSpan">0.00</span>%</span></td>
	        </tr>
	        <tr align="center" class="trw" id="tr_commissionRate">
	          <td class="dctro">发起人提成：</td>
	          <td height="24" align="left">
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
	        <tr align="center" class="trw" id="tr_baodiCost">
	          <td class="dctro">我要保底：</td>
	          <td height="24" align="left"><input name="createForm.baodiCost" type="text" size="7" class="kuanginput" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span></td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">是否公开：</td>
	          <td height="24" align="left">
	          	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[0] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
				</#list>
	          </td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">方案描述：</td>
	          <td height="24" align="left"><textarea class="kuangb1gray" rows="2" cols="45" name="createForm.description"></textarea></td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">&nbsp;</td>
	          <td height="24" align="left"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label></td>
	        </tr>
	        <tr align="center" class="trw">
	          <td class="dctro">&nbsp;</td>
	          <td height="24" align="left">
	          	<span id="span_createForm_submit"><a id="formSubmit" onclick="submitCreateForm();return false;" href="#"><img height="55" width="170" src="<@c.url value="/pages/images/bt_goumai.gif" />"></a></span>
	          	<span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span>
	          </td>
	        </tr>
	      </tbody>
	    </table>
	    <input type="hidden" name="createForm.passMode" value="NORMAL"/>
	    <input type="hidden" name="playType" value="${playType}"/>
	    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
	    <input type="hidden" name="request_token" value="${datetime().millis}" />
	    <input type="hidden" name="createForm.units" value="0" />
	    <input type="hidden" name="createForm.schemeCost" value="0" />
	</form>
	<script type="text/javascript">
		$(function(){
			var obj = document.getElementById('createForm_shareType_${shareType!'TOGETHER'}');
			obj.checked = true;
			singleChgShareType(obj);
		});
	</script>
</@override>

<@extends name="scheme-editNew.ftl"/> 
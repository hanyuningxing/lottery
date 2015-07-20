<head>
	<meta name="decorator" content="trade" />
	<title>北京单场足彩,北京单场推荐 - ${webapp.webName}安全购彩平台</title>
	<link href="<@c.url value="/pages/css/glq.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchLanguage.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilter.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/freshSp.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/singleInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/leeDialog/dialog.js"/>"></script>
	
</head>
   <#include "/common/message.ftl" />

<div align="center">
	<div align="center" class="Buytitle"><strong style=" float:left;"> ${playType.text!""}&nbsp;<b style="font-size:20px;">第</b><span>${period.periodNumber!""}</span><b style="font-size:20px;">期</b></strong>
        </div>
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
	<table width="1000" border="0" cellspacing="1" cellpadding="0" align="center" class="BuyContfirmbox" style="margin:0 auto;">
				<tbody><tr style="border:none;">
			<th align="right" valign="middle" width="12%">投注信息</th>
			<td align="left">方案倍数 <b>${createForm.multiple!0}</b> 倍,方案注数<b>${createForm.units}</b>,总金额 <strong class="redtxt2">${createForm.schemeCost!0}</strong> 元。</td>
		</tr>	
	  		<tr id="Tr_ConsignType" style="display:">
			<th align="right" valign="center">认购设置</th>
			<td align="left" valign="top">
				<div class="szbox">
				<p>
		          	<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="singleChgShareType(this);" checked="checked"/><label for="createForm_shareType_SELF"><label for="ConsignTypeSave"><strong>我要自购</strong></label></label>
		            <input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="singleChgShareType(this);" /><label for="ConsignTypeSave"><strong>发起合买</strong></label>
				</p>
			    </div>
                <div class="Clear"></div>
		       		  </td>
		</tr>
		
		<tr align="center" class="trw" id="tr_minSubscriptionCost">
			<th align="right">最低认购</th>
	        <td height="24" align="left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="kuanginput" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
	    </tr>
	    <tr align="center" class="trw" id="tr_subscriptionCost">
	         <th align="right">我要认购</th>
	         <td height="24" align="left"><input name="createForm.subscriptionCost" type="text" size="7" class="kuanginput" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="subscriptionPerSpan">0.00</span>%</span></td>
	    </tr>
        <tr align="center" class="trw" id="tr_commissionRate">
          <th align="right">发起人提成</th>
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
          <th align="right">我要保底</th>
          <td height="24" align="left"><input name="createForm.baodiCost" type="text" size="7" class="kuanginput" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span></td>
        </tr>
        <tr align="center" class="trw">
          <th align="right">是否公开</th>
          <td height="24" align="left">
          	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
			<#assign defalutType=secretTypeArr[0] />
			<#list secretTypeArr as type>
			  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
			</#list>
          </td>
	    </tr>
			  
        <tr>
	     	 <th></th>
	         <td><div align="center" id="SubmitMode" class="SubmitMode">
	         <span id="span_createForm_submit"><a id="formSubmit" href="javascript:void(0);" onclick="submitCreateForm();return false;" ><img height="55" width="170" src="<@c.url value="/pages/images/bt_goumai.gif" />"></a></span>
		          	<input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label>
		     </div>
		     	          	<span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span>
	          </td>
      	</tr>
      	
	  	<tr>
			<th align="right">投注详情</th>
			<td align="center">
				<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="BuyContfirmbox02">
                						<tbody><tr align="center" style="color:#333;">
						<th width="10%">场次</th>
						<th width="25%">主队(让球值)<em class="tcbox" onmouseover="showDiv2(&#39;lotterybox06&#39;,this);" style=" float:none;"></em><div align="left" id="lotterybox06" style="display: none;margin:-55px 0px 0px 10px;">主队后的数字为让球值，负数表示主让客，正数表示客让主。</div></th>
                                                <th width="25%">客队</th>						<th>选项</th>
					</tr>
					<#if matchDatas??&&matchDatas?size gt 0>
					   		<#list matchDatas as datas>
					   			<#list datas as data>
								<#assign temp=data.split(",")/>					   			
					   			<tr align="center" class="WhiteBg">
					   				<input type="hidden" name="createForm.lineIds[${datas_index}]" id="createForm.lineIds[${datas_index}]" value="${temp[0]}">
									<td>${temp[0]}</td>
									<td>${temp[1]}</td>
	                                 <td>${temp[3]}</td>
									<td>
										${temp[4]!""}&nbsp;${temp[5]!""}&nbsp;${temp[6]!""}
									</td>
								</tr>
								</#list>
					   		</#list>
					</#if>  
					
					<#if itemMap??>
						<#list itemMap.entrySet() as entry>
				          <#list entry.getValue() as item>
				          	<input type="hidden" name="createForm.codes[${item_index}]" value="${item.value}"/>
				          </#list>
			         	 </#list>
			         <#else>
			         	<#list itemArr as item>
				          	<input type="hidden" name="createForm.codes[${item_index}]" value="${item.value}"/>
				        </#list>	 
					 </#if>			
					</tbody></table>
			</td>
		</tr>
		<tr align="center" class="trw">
          <th align="right">单式列表</th>
          <td height="24" align="left">
          	<a href="javascript:void(0);" onclick="viewDetail();">查看内容</a>
          </td>
	    </tr>			
		</tbody></table>
	<br>
	  				<input type="hidden" name="playType" value="${playType}"/>	
		    	    <input type="hidden" id="size" name="size" value="${size!0}"/>	
		    	    <input type="hidden" name="createForm.units" value="${createForm.units!0}" id="createForm_units"/>	
		    	    <input type="hidden" name="createForm.schemeCost" value="${createForm.schemeCost!0}" id="createForm_schemeCost"/>	
		    	    <input type="hidden" name="createForm.mode" value="SINGLE"/>	
		    	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
		    	    <input type="hidden" name="createForm.content" value="${createForm.content?replace(",","\r\n")}" id="createForm_content"/>
		    	    <input type="hidden" name="createForm.passTypes[0]" value="${createForm.passTypes[0]}" id="createForm_passTypes"/>
		    	    <input type="hidden" name="createForm.passMode" value="NORMAL" id="createForm_passMode"/>
		    	    <input type="hidden" name="createForm.shareType" value="SELF" id="createForm_shareType"/>
		    	   	<input type="hidden" name="request_token" value="${datetime().millis}" />
		    	   	<input type="hidden" name="createForm.secretType" value="FULL_PUBLIC" id="createForm_secretType"/>
		    	   	<input type="hidden" name="createForm.multiple" value="${createForm.multiple!0}" id="createForm_multiple"/>
	</form>

<div style="display:none" id="content_detail">
   <#include "view-detail.ftl" />
</div>

<script type="text/javascript">
		$(function(){
			var obj = document.getElementById('createForm_shareType_${shareType!'SELF'}');
			obj.checked = true;
			singleChgShareType(obj);
		});
		
		function viewDetail(){
			dialog("单式内容","id:content_detail","680px","auto","/pages/css/fz.css");
			//$("#content_detail").dialog();
		}
	</script>
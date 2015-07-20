<#if !playType?exists && scheme?? && scheme.playType??><#assign playType=scheme.playType></#if>

<@override name="title">
	<title>${lotteryType.lotteryName}<#if playType??>${playType.text}</#if>${salesMode.modeName}投注</title>
</@override>
 
<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<@block name="editNewHead"></@block>
</@override>

<@override name="menu">
	<!-- nav menu begin-->
	<div class="zcmenu">
		<ul>
		  <li class="<#if lotteryType=='SFZC' && playType=='SFZC14'>zcmenulinow<#else>zcmenuli01</#if>"> <a href="${base}/sfzc/SFZC14.html">14场胜负彩</a></li>
		  <li class="zcmenulishuline"></li>
		  <li class="<#if lotteryType=='SFZC' && playType=='SFZC9'>zcmenulinow<#else>zcmenuli01</#if>"><a href="${base}/sfzc/SFZC9.html">胜负任九场</a></li>
		  <li class="zcmenulishuline"></li>
		  <li class="<#if lotteryType=='LCZC'>zcmenulinow<#else>zcmenuli01</#if>"><a href="${base}/lczc/">六场半全场</a></li>
		  <li class="zcmenulishuline"></li>
		  <li class="<#if lotteryType=='SCZC'>zcmenulinow<#else>zcmenuli01</#if>"><a href="${base}/sczc/">四场进球</a></li>
		</ul>
	</div>
	<!--nav menu end-->
</@override>

<@override name="top">
	<#if lotteryType=='SCZC'>
		<#assign topClass='zc_4goaltztop1'/>
	<#elseif lotteryType=='LCZC'>
		<#assign topClass='zc_6halftztop1'/>
	<#elseif lotteryType=='SFZC' && playType=='SFZC9'>
		<#assign topClass='zc_r9tztop1'/>
	<#elseif lotteryType=='SFZC' && playType=='SFZC14'>
		<#assign topClass='zc_r14tztop1'/>
	</#if>
	<div class="${topClass}">
	<#-->
    <div class="zc_choseselect">
      <select name="select" size="1" id="select">
        <option>选择玩法</option>
        <option>14场胜负彩</option>
        <option>四场入球</option>
        <option>六场半全场</option>
        <option>任九场</option>
      </select>
    </div>-->
    <div class="tztopnews">
      <ul class="tztopnew">
         <#if gonggaoList?? && gonggaoList?size gt 0>
			<#list gonggaoList as data>
				<li><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
			</#list>
		  </#if>
      </ul>
    </div>
    <div class="tztopbt"> <a href="${base}/info/rule/${lotteryType.key?upper_case}.html" target="_blank" class="tztopbt1">玩法介绍</a><br />
      <a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45" class="tztopbt1">申请票样</a> </div>
    <div class="tzday1"><b>单式截止</b><br />
      ${singleEndTime?string('MM-dd HH:mm')}</div>
    <div class="tzday2"><b>复式截止</b><br />
      ${compoundEndTime?string('MM-dd HH:mm')}</div>
    <div class="tzday3"></div>
  </div>
</@override>

<@override name="main">
	<div class="">		
		<@block name="mainContent">
			<@block name="playTop"></@block>
			<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
				<@block name="content"></@block>
				<!--投注信息-->
				<@block name="initContent">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableb1 char14">
				      <tr>
				        <td colspan="2">
				        <div class="rigbgblue">投注信息及购买</div></td>
				      </tr>
				      <@block name="initContentOfSingle"></@block>
				      <tr>
				        <td width="120" height="30" class="zc_tdlblue" >方案注数</td>
				        <td class="zc_tdlwhite">
				        	<span id="text_createForm_units"><span class="reb" _name="createForm_units">0</span>注</span>
					        <@block name="initUnitSpan"></@block>
			            </td>
				      </tr>
				      <tr>
				        <td height="30" class="zc_tdlblue">方案倍数</td>
				        <td class="zc_tdlwhite">
				          <#list [1,2,3,5,8,10] as m>
				          	<label><input <#if m_index==0>checked="checked"</#if> type="radio" value="${m}" name="t_mult" onclick="chgMultiple(this);" />${m}倍</label>&nbsp;
				          </#list>
				          <label><input type="radio" name="t_mult" onclick="chgMultiple(this);"/>其它</label> <span id="span_other_multiple" style="display:none;"><input type="text" style="width: 40px;IME-MODE: disabled;" value="1" onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" maxLength="3" class="txtbox color_fff" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" />倍(最高999倍)</span>
				        </td>
				      </tr>			      
				      <tr>
				        <td height="30" class="zc_tdlblue">方案金额</td>
				        <td class="zc_tdlwhite"><span class="red">￥<span _name="createForm_schemeCost">0.0</span></span></td>
				      </tr>
				      <tr>
				        <td height="30" class="zc_tdlblue">购买方式</td>
				        <td class="zc_tdlwhite">
				          	<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">我要自购&nbsp;</label>
							<input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">发起合买</label>
							<input id="freeSave" type="radio" value="true" name="freeSave" onclick="chgShareType(this);"/><label for="freeSave">免费保存</label>
							<a href="javascript:;"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0" tips="<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>" onmouseover="SaveTips(this)"/></a>
				        </td>
				      </tr>
				      <tr id="tr_TOGETHER_content" style="display:none;">
				      	<td colspan="2">
				      		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableb1 char14">
				      		  <tr>
						        <td colspan="2"><div class="rigbgblue">合买信息</div></td>
						      </tr>
						      <tr>
						        <td height="100" class="zc_tdlyelhm" width="120">方案描述：</td>
						        <td class="zc_tdlyelhemai"><textarea name="createForm.description" cols="45" rows="5" style="height:76px;"></textarea></td>
						      </tr>
						      <tr>
						        <td height="30" class="zc_tdlyelhm">最低认购</td>
						        <td class="zc_tdlyelhemai"><input name="createForm.minSubscriptionCost" type="text" size="8" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
						      </tr>
						      <tr>
						        <td height="30" class="zc_tdlyelhm">我要认购</td>
						        <td class="zc_tdlyelhemai"><input name="createForm.subscriptionCost" type="text" size="8" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="red"><span id="subscriptionPerSpan">0.00</span>%</span></td>
						      </tr>
						      <tr>
						        <td height="30" class="zc_tdlyelhm">发起人提成</td>
						        <td class="zc_tdlyelhemai">
						        	<#assign commissionRate=(createForm.commissionRate)!0 />
						            <select id="createForm.commissionRate" name="createForm.commissionRate" size="1" style="width:70px;">
						              <option value="0">无佣金</option>
						              <#list 1..5 as c>
						              	<#assign rate=c/100 />
						              	<option value="${rate?string('0.00')}" <#if commissionRate=rate>selected="selected"</#if> >${c}%佣金</option>
						              </#list>
						            </select>
						        </td>
						      </tr>
						      <tr>
						        <td height="30" class="zc_tdlyelhm">我要保底</td>
						        <td class="zc_tdlyelhemai"><input name="createForm.baodiCost" type="text" size="8" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span> <a href="#">全包</a></td>
						      </tr>
				      		</table>
				      	</td>
				      </tr>
				      <tr>
				        <td height="30" class="zc_tdlblue">是否公开</td>
				        <td class="zc_tdlwhite">
					        <#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
							<#assign defalutType=secretTypeArr[0] />
							<#list secretTypeArr as type>
							  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
							</#list>
						</td>
				      </tr>			
				      <tr>
				        <td class="zc_tdlblue" height="28" >&nbsp;</td>
				        <td class="zc_tdlwhite"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label></td>
				      </tr>
				      <tr>
				        <td height="60"  class="zc_tdlblue">&nbsp;</td>
				        <td class="zc_tdlwhite"><@block name="submitAct"><a id="formSubmit" onclick="confirmDialog();return false;" href="javascript:;" class="btbuy"></a></@block></td>
				      </tr>			            
				    </table>
				</@block>
				<input type="hidden" name="request_token" value="${datetime().millis}" />
			    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
			    <input type="hidden" name="createForm.periodId" value="${period.id}" />
			    <input type="hidden" name="createForm.units" value="0" />
			    <input type="hidden" name="createForm.schemeCost" value="0" />
			    <#if playType??><input type="hidden" name="playType" value="${playType}"/></#if>
				<@block name="playExplain"></@block>
				<@block name="extraData"></@block>
				<div class="k10"></div>
			</form>		
		</@block>
		<@block name="explain"></@block>
	</div>
</@override>

<!--投注信息确认对话框-->
<@block name="dialogConfirm">
	<div id="dialogDiv" style="display:none">
		<div class="w730">
		  <div class="w730title">
		    <div class="close" id="close" onclick="closeDialog('dialogDiv');return false;"></div>
		    	${lotteryType.lotteryName}投注方案${currDate?string("yyyy")}年${currDate?string("MM")}月${currDate?string("dd")}日发起
		    </div>
		  <div class="tdbback">
		    <table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">
		      <tr class="v01tr">
		        <td>总金额</td>
		        <td>倍数</td>
		        <td>份数</td>
		        <td>每份</td>
		        <td>发起人提成</td>
		        <td>彩票标识</td>
		        <td>保底金额</td>
		        <td>购买进度</td>
		      </tr>
		      <tr class="v02tr">
		        <td>￥<span class="red eng" id="confirmCost">X</span>元</td>
		        <td><span id="confirmMultiple">X</span>倍</td>
		        <td><span id="confirmQuantity">X</span>份</td>
		        <td>￥<span id="confirmMinCost">X</span>元</td>
		        <td><span class="red eng" id="confirmCommissionRate">X%</span></td>
		        <td>暂未出票</td>
		        <td><span id="confirmBaodi">X元</span></td>
		        <td><span class="red eng" id="subscriptionPer">X%</span></td>
		      </tr>
		    </table>
		  </div>
		  <div class="tdbback" id="selectedContentDiv">
		    <table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">
		      <tr class="v01tr">
		        <td>场次 </td>
		        <td>主队 VS 客队 </td>
		        <td>您的选择</td>
		        <td>胆码</td>
		      </tr>
		  	<tr class="v02tr">
		        <td>周六006 </td>
		        <td>爱媛FC VS 鸟取希望</td>
		        <td>胜(3.05)，平(3.45) </td>
		        <td>×</td>
		      </tr>
		    </table>
		  </div>
		  <!--end-->
		  <div class="k10"></div>
		  <div align="center" class="a10px"> 
		  	<a onclick="$('#dialogDiv').dialog('close');return false;" href="javascript:;" class="btback"></a>&nbsp;&nbsp;
		  	<span id="span_createForm_submitbuy"><a onclick="submitCreateForm('buy');return false;" href="javascript:;" class="btbuy"></a></span><span style="display: none;" id="span_createForm_waitingbuy"><a href="#" id="loading_tzbuy"></a></span>&nbsp;&nbsp;
		  	<span id="span_createForm_submitsave"><a onclick="submitCreateForm('save');return false;" href="javascript:;" class="btsave"></a></span><span style="display: none;" id="span_createForm_waitingsave"><a href="#" id="loading_tzsave"></a></span>	  	 
	  	  </div>
		</div>
	</div>
</@block>

<script type="text/javascript">
	window.onload = function() {
		createDialog_header('#dialogDiv', '提示信息', 780);		
	}
</script>
<@extends name="simple-base-ud-V1.ftl"/>


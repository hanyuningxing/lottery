<#if shareType?? && shareType == 'SELF'>
	<#assign menu_type='scheme_editNew_SELF' />
<#else>
	<#assign menu_type='scheme_editNew_TOGETHER' />
</#if>
<#assign isSingle=salesMode=='SINGLE' />

<@override name="title">
	<title>${lotteryType.lotteryName}<#if playType??>${playType.text}</#if>${salesMode.modeName}投注</title>
</@override>
 
<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<#if isSingle><script type="text/javascript" src="<@c.url value= "/js/lottery/singleInit.js"/>"></script></#if>
	<@block name="editNewHead"></@block>
</@override>
  
<@override name="content">
	<#if playType??>
		<#assign playTypeStr='playType=${playType}&' />
	<#else>
		<#assign playTypeStr='' />
	</#if>
	
	<div class="cleanboth dctwobg">
      <div class="twowz">
        <ul class="twotopnav">
          <li><a <#if isSingle>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?${playTypeStr}salesMode=SINGLE&shareType=${shareType!}"/>">单式投注上传</a></li>
          <li><a <#if !isSingle>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?${playTypeStr}salesMode=COMPOUND&shareType=${shareType!}"/>">复式投注</a></li>
        </ul>
      </div>
    </div>
    <table height="30" width="805" cellspacing="0" cellpadding="0" border="0" background="<@c.url value="/pages/images/dc_twobg.gif" />" class="lrb1"><tbody>
      <tr height="30">
        <td class="left10">
           <form action="<@c.url value="/${lotteryType.key}/scheme!editNew.action" />" method="get">
				<#list periods as p>
					   <#if p.periodNumberDisplay??>
						    <#if 1==p.periodNumberDisplay>
						               <input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
						                 <label class="inputcheckbox orgchar">(第${p.periodNumber!}期)当前期</label>
						    <#elseif 2==p.periodNumberDisplay>
						                 <input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
						                 <label class="inputcheckbox">(第${p.periodNumber!}期)预售期</label>
		                   	</#if>
		                   	<#else>
		                   	<input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
		                   	<label class="inputcheckbox">(第${p.periodNumber!}期)</label>
	                   </#if>
				</#list>
			<input type="hidden" name="salesMode" value="${salesMode}" />
			<#if playType??>
			<input type="hidden" name="playType" value="${playType}" />
			</#if>
			<#if shareType??>
			<input type="hidden" name="shareType" value="${shareType}" />
			</#if>
         </form>
		</td>
        <td width="170" align="right" class="rig10">&nbsp;</td>
      </tr>
    </tbody></table>
    
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
		<@block name="selectContent"></@block>
		
		<table width="805" cellspacing="1" cellpadding="0" border="0" class="dctbg"><tbody>
		  <col width="110" />
		  <tr height="22" class="dctro">
	        <td height="27" align="left" class="left10" colspan="2"><div style="line-height: 27px;" class="floatleft">投注信息及购买</div></td>
	      </tr>
	      <#if isSingle>
		      <tr height="24" class="trw">
		        <td height="24" align="center" class="boldchar">上传类型</td>
		        <td align="left">
		        	<label><input name="singleUploadType" checked="checked" onclick="chooseSingleUploadType(0);" type="radio" autocomplete="off"/>文件上传</label>
			    	&nbsp;&nbsp;&nbsp;&nbsp;
			    	<label><input name="singleUploadType" onclick="chooseSingleUploadType(1);" type="radio" autocomplete="off"/>发起后再上传方案</label>
			    	<input type="hidden" name="createForm.fileUpload" value="true" />
			    	<input type="hidden" name="createForm.aheadOfUploadContent" value="false"/>
		         </td>
		      </tr>
		      <tr height="24" class="trw" id="tr_uploadFile">
		        <td height="24" align="center" class="boldchar">文件上传</td>
		        <td align="left">
		        	<input type="file" id="upfile" name="createForm.upload" onkeydown="return false;" onchange="chgUploadFile(this)" />
		            <input type="button" id="calcSubmit_uploadFile" class="btn" value="自动计算金额" disabled="disabled" onclick="countUploadFileMoney()" />
					<span id="span_calUploadFileMoney" style="display:none;">正在计算，请稍等...</span>
					<a href="#nogo" class="colorBlack" onclick="$floatHTML({width:250,height:100,html:document.getElementById('content_format_demo').textContent,title:'格式样本'});return false;">查看标准格式样本</a><br>		            
		        </td>
		      </tr>
	      </#if>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar">方案注数</td>
	        <td align="left">
	        	<span id="text_createForm_units"><span class="rebchar" _name="createForm_units">0</span>注</span>
	        	<#if isSingle>
	        	  <span id="input_createForm_units" style="display:none"><input id="ahead_createForm_units" onkeyup="if(/\d+/.test(this.value)){updateBetUnits(parseInt(this.value));}else{updateBetUnits(0);}" class="kuanginput" size="10" maxLength="5" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" style="IME-MODE: disabled;" />注</span>
	            </#if>
	        </td>
	      </tr>
	      <tr bgcolor="#ffffff">
	        <td height="24" align="center" class="boldchar">方案倍数</td>
	        <td>
	          <#list [1,2,3,5,8,10] as m>
	          	<label><input <#if m_index==0>checked="checked"</#if> type="radio" value="${m}" name="t_mult" onclick="chgMultiple(this);" />${m}倍</label>&nbsp;
	          </#list>
	          <label><input type="radio" name="t_mult" onclick="chgMultiple(this);"/>其它</label><span id="span_other_multiple" style="display:none;"><input type="text" style="width: 40px;IME-MODE: disabled;" value="1" onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" maxLength="3" class="txtbox color_fff" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" />倍(最高999倍)</span>
	        </td>
	      </tr>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar">方案金额</td>
	        <td align="left"><span class="redboldchar">￥<span _name="createForm_schemeCost">0</span></span></td>
	      </tr>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar">购买方式</td>
	        <td align="left">
	          	<label><input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"/>我要自购</label>
				<label><input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);" checked="checked"/>发起合买</label>
		    </td>
	      </tr>
	      <tr height="22" class="dctro" id="tr_TOGETHER_title">
	        <td height="27" align="left" class="left10" colspan="2"><div style="line-height: 27px;" class="floatleft">合买信息</div></td>
	      </tr>
	      <tr height="24" class="trw" id="tr_TOGETHER_content">
	        <td height="24" colspan="2">
	          <table width="100%" cellspacing="0" cellpadding="1" border="0" align="center" class="b1">
	            <tr>
	              	<td width="110" align="right" class="tdboldbggray">方案描述：</td>
	              	<td align="left"><textarea class="kuangb1gray" rows="3" cols="30" name="createForm.description"></textarea></td>
	            </tr>
		        <tr>
		          	<td align="right" class="tdboldbggray">最低认购： </td>
		          	<td align="left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="kuanginput" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
		        </tr>
		        <tr>
		          	<td align="right" class="tdboldbggray">我要认购：</td>
		          	<td align="left">
		              <input name="createForm.subscriptionCost" type="text" size="7" class="kuanginput" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="subscriptionPerSpan">0.00</span>%</span>
		          	</td>
		        </tr>
		        <tr>
		            <td align="right" class="tdboldbggray">发起人提成：</td>
		            <td align="left">
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
		        <tr>
		            <td align="right" class="tdboldbggray">我要保底：</td>
		            <td align="left">
		            	<input name="createForm.baodiCost" type="text" size="7" class="kuanginput" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span>
		            	<span style="color:blue">（网站合买方案保底10%）</span>
		            </td>
		        </tr>
	          </table>
	        </td>
	      </tr>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar"><span class="tdboldbggray">是否公开</span></td>
	        <td align="left">
	        	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[0] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
				</#list>
	        </td>
	      </tr>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar">&nbsp;</td>
	        <td align="left"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label> </td>
	      </tr>
	      <tr height="24" class="trw">
	        <td height="24" align="center" class="boldchar">&nbsp;</td>
	        <td height="80" align="left">
	          <span id="span_createForm_submit"><a id="formSubmit" onclick="submitCreateForm();return false;" href="#"><img src="<@c.url value="/pages/images/bt_goumai.gif" />"></a></span>
		      <span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span>
	        </td>
	      </tr>
	    </tbody></table>
	    <input type="hidden" name="request_token" value="${datetime().millis}" />
	    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
	    <input type="hidden" name="createForm.units" value="0" />
	    <input type="hidden" name="createForm.schemeCost" value="0" />
	    <#if playType??><input type="hidden" name="playType" value="${playType}"/></#if>
	    <@block name="extraData"></@block>
	</form>
	<script type="text/javascript">
		$(function(){
			var obj = document.getElementById('createForm_shareType_${shareType!'TOGETHER'}');
			obj.checked = true;
			chgShareType(obj);
		});
	</script>
</@override>

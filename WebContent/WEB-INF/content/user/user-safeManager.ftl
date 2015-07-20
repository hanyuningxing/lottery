<head>
	<title>用户中心</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<script type="text/javascript" src="<@c.url value= "/js/user/dialog.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/user/bindMobile.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/city.js"/>"></script>
</head>

<script>
	$(function(){
		var partBankProvince = $("#drawingForm_partBankProvince").val();
		var partBankCity = $("#drawingForm_partBankCity").val();
		if(null!=partBankProvince) {
			 getCity(partBankProvince);
			 $("#selProvince").val(partBankProvince);
			 $("#selCity").val(partBankCity);
		}	
		addSubmitEventForUpdateBankInfoForm();		
	});	
		
	//短信验证后执行此方法
	function msgValidate(data) {
			document.getElementById('yanzhengma').value="";
			if(data.success) {
				$("#msgValidateDialog").dialog('close');						
				setCommonResult(data.msg,data.success);	
				if("showTkVldDlog" in window && showTkVldDlog==true) {
					setTimeout(
							function(){
								$("#commonResultDialog").dialog('close');
								$("#tkValidateDialog").dialog('open');
								tkValidateFormAddSubmitEvent();
							}
					,2000);
				} else if("showBindBankCardDlog" in window && showBindBankCardDlog==true) {
					setTimeout(
							function(){
								$("#commonResultDialog").dialog('close');
								$("#bankInfoUpdatedialog").dialog('open');
								updateBankInfoFormAddSubmitEvent();
							}
					,2000);
				} else{
					document.getElementById("isValidatePhoneNO").innerHTML = "已绑定";
					setTimeout(
						function() {
							$("#commonResultDialog").dialog('close');
						}
					,2000)	
				}
			} else {
				document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
				setTimeout(
						function(){
							document.getElementById("error").innerHTML = "";
						}
				, 3000);
			}
			
		
	}
</script>

<div style="display:none">
<!--银行卡绑定对话框 -->
<div id="bankInfoUpdatedialog">  	
	<form action="${base}/user/user!updateBankInfo.action" method="post" id="updateBankInfo" style="margin:20px;">				
		<table width="400" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		<td colspan="2"><div style="border:1px solid #D6C07F;background:#FFFDE5; font-size:12px; color:#9E3D12; padding:6px 10px 4px 10px; padding-top:4px; line-height:18px;  text-align:left;">
<div class="tishiicon"></div>欢迎您进行银行绑定，绑定银行卡后能方便您进行提款购彩等操作</div></td>
		   <tr>
              <td width="120" height="30" align="right">*<strong>开户银行：</strong></td>
              <td valign="center" bgcolor="#FFFFFF">
              	  <select id="bankName" name="bankName" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
	                  <option selected="selected" value="">-----请选择银行-----</option>
	                  <option value="中国工商银行">中国工商银行</option>
	                  <option value="中国建设银行">中国建设银行</option>
	                  <option value="中国农业银行">中国农业银行</option>
	                  <option value="中国银行">中国银行</option>
	                  <option value="招商银行">招商银行</option>
	                  <option value="交通银行">交通银行</option>
	                  <option value="广发银行">广发银行</option>
	                  <option value="中信银行">中信银行</option>
	                  <option value="中国民生银行">中国民生银行</option>
	                  <option value="中国平安银行">中国平安银行</option>
	                  <option value="华夏银行">华夏银行</option>
	                  <option value="兴业银行">兴业银行</option>
	                  <option value="农村信用合作社">农村信用合作社</option>
	                  <option value="上海浦东发展银行">上海浦东发展银行</option>
	                  <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
	                  <option value="东亚银行">东亚银行</option>
                  </select>
                </td>
            </tr>
            <tr>
              <td height="30" align="right" valign="center">*<strong>开户省份：</strong></td>
              <td bgcolor="#FFFFFF">
              		<select id="selProvince" name="partBankProvince" onChange = "getCity(this.options[this.selectedIndex].value)" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
				         <option value="">---请选择---</option>
				         <option value="北京市"  >北京市</option>
				         <option value="上海市"  >上海市</option>
				         <option value="天津市"  >天津市</option>
				         <option value="重庆市"  >重庆市</option>
				         <option value="河北省"  >河北省</option>
				         <option value="山西省"  >山西省</option>
				         <option value="内蒙古自治区"  >内蒙古自治区</option>
				         <option value="辽宁省"  >辽宁省</option>
				         <option value="吉林省"  >吉林省</option>
				         <option value="黑龙江省"  >黑龙江省</option>
				         <option value="江苏省"  >江苏省</option>
				         <option value="浙江省"  >浙江省</option>
				         <option value="安徽省"  >安徽省</option>
				         <option value="福建省"  >福建省</option>
				         <option value="江西省"  >江西省</option>
				         <option value="山东省"  >山东省</option>
				         <option value="河南省"  >河南省</option>
				         <option value="湖北省"  >湖北省</option>
				         <option value="湖南省"  >湖南省</option>
				         <option value="广东省"  >广东省</option>
				         <option value="广西壮族自治区"  >广西壮族自治区</option>
				         <option value="海南省"  >海南省</option>
				         <option value="四川省"  >四川省</option>
				         <option value="贵州省"  >贵州省</option>
				         <option value="云南省"  >云南省</option>
				         <option value="西藏自治区"  >西藏自治区</option>
				         <option value="陕西省"  >陕西省</option>
				         <option value="甘肃省" >甘肃省</option>
				         <option value="宁夏回族自治区"  >宁夏回族自治区</option>
				         <option value="青海省"  >青海省</option>
				         <option value="新疆维吾尔自治区"  >新疆维吾尔自治区</option>
				         <option value="香港特别行政区"  >香港特别行政区</option>
				         <option value="澳门特别行政区"  >澳门特别行政区</option>
				         <option value="台湾省"  >台湾省</option>
		    	 </select>    
              </td>
            </tr>
            <tr>
              <td height="30" align="right" valign="center"><strong>开户城市：</strong></td>
              <td bgcolor="#FFFFFF">             
                 <select id="selCity" name="partBankCity" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
			         <option value="">---城市---</option>
			     </select>             
                </td>
            </tr>
            <tr id="trBankAddr">
              <td height="30" align="right"><strong>开户行名称：</strong></td>
              <td bgcolor="#FFFFFF"><input name="partBankName" id="partBankName" type="text" class="tkxdbd" size="25" /> 
                </td>
            </tr>
            <tr>
              <td height="30" align="right">*<strong>银行账号：</strong></td>
              <td bgcolor="#FFFFFF"><input name="bankCard" id="bankCard" type="text" class="tkxdbd" size="25" /> 
               </td>
            </tr>
            <tr>
              <td height="30" align="right">*<strong>核对真实姓名：</strong></td>
              <td bgcolor="#FFFFFF"><input id="userRealName" name="userRealName" type="text" class="tkxdbd" size="25" /></td>
            </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#updateBankInfo').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" onclick="$('#bankInfoUpdatedialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>
	 
	 </form>
</div>

<!--修改密码对话框 -->
<div id="dialogResetPassword">  	
	<form action="${base}/user/user!updatePasswd.action" method="post" id="xiugaimima" style="margin:20px;">			 
		<table width="460" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
			    <td colspan="3"><div style="border:1px solid #D6C07F;background:#FFFDE5; font-size:12px; color:#9E3D12; padding:6px 10px 4px 10px; padding-top:4px; line-height:18px;  text-align:left;">
<div class="tishiicon"></div>欢迎您使用修改密码，不定期修改密码更能保障你的账号安全</div></td>
		    </tr>
		  <tr height="44">
		    <td style="width:70px" align="right" class="tdright10" >旧密码</td>
		    <td><input type="password" name="oldPassword" id="oldPassword" class="input1" /></td>
		    <td></td>		    
		  </tr>
		  <tr height="44">
		    <td align="right" class="tdright10">新密码</td>
		    <td><input type="password" name="newPassword" id="newPassword" class="input1" /></td>
		    <td><div id="newPasswordTip" style="width:160px"></div></td>
		  </tr>
		  <tr height="44">
		    <td align="right" class="tdright10">新密码</td>
		    <td><input type="password" name="confirmPassword" id="confirmPassword" class="input1" /></td>
		    <td><div id="confirmPasswordTip" style="width:160px"></div></td>		    
		  </tr>
		  <tr height="44">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:xiugaimimaSubmit(); return false;">确定修改</a>&nbsp;
		    <a href="javascript:;" onclick="$('#dialogResetPassword').dialog('close');" class="xiugaibg">返回修改</a></td>
		  </tr>
		</table>
	 </form>
</div> 
<!--手机验证对话框 -->
<div id="msgValidateDialog" style="margin:20px;font-size:14px;">
	<div><span id="content"></span></div></br>	
	<form id="msgValidateform" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td align="right" width="45" class="tdright10">验证码</td>
		    <td><input type="text" style="width:60px" name="yanzhengma" id="yanzhengma"/>
		    <input style="height:25px"  type="button" id="getMsgCode" value="获取验证码" /><span id="error" style="color:red"></span></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#msgValidateform').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" id="return" onclick="reInputMobile();" class="xiugaibg">返回</a></td>
		   
		  </tr>
		</table>		
	</form>
</div> 
<!--手机号码输入对话框 -->
<div id="mobileWriteDialog" style="margin:20px;">
	<span><p>请输入需要绑定的手机号码！</p></span>	</br>	
	<form id="mobileWriteForm" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td width="60" align="right" class="tdright10">手机号码</td>
		    <td><input type="text" name="mobile" id="mobile" class="input1" /></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#mobileWriteForm').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" onclick="$('#mobileWriteDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>		
	</form>
</div> 

<!--身份信息认证对话框 -->
<div id="setIdCardDialog">	
	<form id="setIdCardForm" action="${base}/user/user!setIdCard.action" style="margin:30px;">
		<table width="460" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		<td colspan="3"><div style="border:1px solid #D6C07F;background:#FFFDE5; font-size:12px; color:#9E3D12; padding:6px 10px 4px 10px; padding-top:4px; line-height:18px;  text-align:left;">
<div class="tishiicon"></div>欢迎您进行身份验证，身份验证后更能保障你的账号安全</div></td>
			<tr height="44">
			    <td width="60" align="right" class="tdright10">身份证号</td>
			    <td><input type="text" name="idCard" id="idCard" class="input1" /></td>
			    <td><div id="idCardTip" style="width:160px"></div></td>
		    </tr>
		    <tr height="44">
			    <td align="right" class="tdright10">确认号码</td>
			    <td><input type="text" name="confirmIdCard" id="confirmIdCard" class="input1" /></td>
			    <td><div id="confirmIdCardTip" style="width:160px"></div></td>
		    </tr>
			<tr height="33">
			    <td align="right"></td>
			    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:setIdCardFormSubmit(); return false;">确定</a>&nbsp;
			    <a href="javascript:;" onclick="$('#setIdCardDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>
	
	</form>
</div> 

<!--是否需要提款验证 -->
<div id="tkValidateDialog">	
	<form id="tkValidateForm" >
		<table align="center"> 
			<tr>
				<td><input type="radio" name="needTkValidate" value="1">是</input></td>
				<td><input type="radio" name="needTkValidate" value="0">否</input></td>
			</tr>
		</table>
		
		<table border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66">
			<tr height="33">
			    <td align="right"></td>
			    <td><a href="javascript:" class="xiugaibg" onclick="$('#tkValidateForm').submit(); return false;">确定</a></td>
		 	 </tr>
		</table>	
	</form>
</div>
<!--是否需要设置支付密码 -->
<div id="needPayPasswordDialog">	
		
		
	<form id="needPayPasswordUpdateForm" style="display:none">
		<table style="display:none"> 
			<tr>				
				<td><input type="radio" id="yes_update" name="needPayPassword_update" value="1">是</input></td>
				<td><input type="radio" id="no_update" name="needPayPassword_update" value="0" checked="checked">否</input></td>
			</tr>
		</table>
		<table id="updatePayPassword" width="460" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  
		  <tr height="44">
		    <td align="right" class="tdright10">原支付密码</td>
		    <td><input type="password" name="oldPayPassword" id="oldPayPassword" class="input1" /></td>
		  </tr>
		  
		  <tr height="44">
		    <td align="right" class="tdright10">新支付密码</td>
		    <td><input type="password" name="payPassword_update" id="payPassword_update" class="input1" /></td>
		    <td><div id="payPassword_updateTip" style="width:120px"></div></td>
		  </tr>
		  <tr height="44">
		    <td align="right" class="tdright10">确认新密码</td>
		    <td><input type="password" name="confirmPayPassword_update" id="confirmPayPassword_update" class="input1" /></td>
		    <td><div id="confirmPayPassword_updateTip" style="width:120px"></div></td>		    
		  </tr>
		  
		</table>
		
		<table border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66">
			<tr height="33">
			    <td align="right"></td>
			    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:needPayPasswordUpdateFormSubmit(); return false;">确定</a>&nbsp;
			    <a href="javascript:;" onclick="$('#needPayPasswordDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>
	</form>
	
	<form id="needPayPasswordFirstSetForm" style="display:none">
		<table style="display:none"> 
			<tr>				
				<td><input type="radio" id="yes_firstSet" name="needPayPassword_firstSet" value="1">是</input></td>
				<td><input type="radio" id="no_firstSet" name="needPayPassword_firstSet" value="0" checked="checked">否</input></td>
			</tr>
		</table>
		
		<table id="inputPayPassword" width="460" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		 
		  <tr height="44">
		    <td align="right" class="tdright10">支付密码</td>
		    <td><input type="password" name="payPassword" id="payPassword" class="input1" /></td>
		    <td><div id="payPasswordTip" style="width:120px"></div></td>
		  </tr>
		  <tr height="44">
		    <td align="right" class="tdright10">确认密码</td>
		    <td><input type="password" name="confirmPayPassword" id="confirmPayPassword" class="input1" /></td>
		    <td><div id="confirmPayPasswordTip" style="width:120px"></div></td>		    
		  </tr>
		  
		</table>
		
		<table border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66">
			<tr height="33">
			    <td align="right"></td>
			    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:needPayPasswordFirstSetFormSubmit(); return false;">确定</a>&nbsp;
			    <a href="javascript:;" onclick="$('#needPayPasswordDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>	
	</form>
</div> 
</div>
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
  	 <#include "user-loginInfo.ftl"/>
	  <div class="yhzxaq">
	  	  <div class="yhzxaql">	  	    
	  	  	  <#assign securityLevel=loginUser.securityLevel/>	  	  	
	  	  		 	  
		  	  <div class="yhzxaql01"><div style="float:left;"><span class="black_666_14"><strong>您的账户安全级别：</strong></span><span class="red_F00_22">${securityLevel.stateName}</span></div><div class="yhzxaql02"><div class="progress_bar"><div class="progress_bar_blue${securityLevel.ordinalValue}"></div></div></div></div>			
			
			  <div class="black_666_12">为保障您账户的资金安全，建议充值购彩前先提升账户安全级别。<br />
		      （绑定越多安全级别越高）</div>
		  </div>
	  	  <div class="yhzxaqr"></div>
	  </div>
	  <div class="border04">
	  	  <div class="yhzxdivtit white_FFF_14">账户保护</div>
		  <div class="yhzxdiv01">
		  	  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>用户密码</strong></li>
				  	  <li class="yhzxli02">账户登录和支付密码，建议您定期进行更换。</li>
				  	  <li class="yhzxli03">已设置</li>
				  	  <li><a id="resetPassword" href="javascript:;" class="yhzxli04">修改</a></li>
		  	  	  </ul>
	      	  </div>
			  
			  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>手机绑定</strong></li>
				  	  <li class="yhzxli02 rc1">！非常重要：用于密码找回、银行卡绑定/修改和提款验证；以及接收短信通知等。</li>
				  	  <li class="yhzxli03">
				  	  		<span id="isValidatePhoneNO"><#if loginUser.validatedPhoneNo??&&loginUser.validatedPhoneNo>已绑定<#else>未绑定</#if></span>
				  	  </li>
				  	  <li><a id="msgValidate" href="javascript:;" class="yhzxli04">修改</a></li>
		  	  	  </ul>
      	    </div>
		  </div>
    </div>
		  <div class="border04">
	  	  <div class="yhzxdivtit white_FFF_14">资金保护</div>
		  <div class="yhzxdiv01">
		  	  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>身份信息认证</strong></li>
				  	  <li class="yhzxli02 rc1"> ！非常重要：领奖及提款的唯一身份识别依据，提交后不能修改；以及用于修改重要信息。</li>
				  	  <li class="yhzxli03">
				  			<span id="isValidateIdCard"><#if loginUser.info.idCard?? && loginUser.info.idCard != "">已认证<#else>未认证</#if></span>
				  	  </li>
				  	  <li><a id="setIdCard" href="javascript:;" class="yhzxli04">设置</a></li>
		  	  	  </ul>
      	    </div>
			  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>银行卡绑定</strong></li>
				  	  <li class="yhzxli02">设置绑定您的银行卡，以便正常提款。</li>
				  	  <li class="yhzxli03">
				  	 		<span id="isBindBankInfo"><#if loginUser.bank.bankCard?? && loginUser.bank.bankCard != "">已设置<#else>未设置</#if></span>
				  	  </li>
				  	  <li><a id="bankInfoUpdate" href="javascript:;" class="yhzxli04">修改</a></li>
		  	  	  </ul>
      	    </div>
			  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>提款验证</strong></li>
				  	  <li class="yhzxli02">提款申请必须通过手机动态码验证，账户资金更加安全。</li>
				  	  <li class="yhzxli03">
				  	 		<span id="isNeedValidateWhileTk"><#if loginUser.needValidateWhileTk??&&loginUser.needValidateWhileTk>已开启<#else>未开启</#if></span>
				  	  </li>
				  	  <li><a id="tkValidate" href="javascript:;" class="yhzxli04">设置</a></li>
		  	  	  </ul>
      	    </div>
      	<!--    
			  <div class="yhzxdiv">
		  	  	  <ul>
		  	      	  <li class="yhzxli01"><strong>支付密码</strong></li>
				  	  <li class="yhzxli02">可以设置购彩、提款时验证支付密码，账户资金更加安全。<!-- <a href="javascript:;" class="blue_0091d4">忘记支付密码？</a>--> </li>
		<!--		  	  <li class="yhzxli03">
				  	  		<span id="isNeedPayPassword"><#if loginUser.needPayPassword?? && loginUser.needPayPassword>已开启<#else>未开启</#if></span>
				  	  </li>
				  	  <li><a id="needPayPassword" href="javascript:;" class="yhzxli04">设置</a></li>
		  	  	  </ul>
      	    </div>
      	    -->
		  </div>
	      </div>
		  <div class="border04">
	  	 
	      </div>
		  <div class="border04">
	  	  <div class="yhzxdivtit2 white_FFF_14">特殊情况处理</div>
		  <div class="yhzxdiv01">
		  	  <div style="display:block; overflow:hidden; padding-bottom:15px;"><span style="float:left; padding-right:15px;">特殊情况处理申请</span> <a href="http://chat.live800.com/live800/chatClient/chatbox.jsp?companyID=291999&jid=2659558135&enterurl" class="yhzxli07" target="_blank">在线客服</a></div>
		  </div>
	      </div>
  </div>
  <!--mleft end -->
  <!--mright begin -->
  <!--main end -->
</div>
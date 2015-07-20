<head>
	<title>帐号设置</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<script type="text/javascript">window.BASESITE='${base}';</script>
	
	<script type="text/javascript">
		$(function(){
			whileClick('bindEmail', 'emailDiv', 'email');
			whileClick('bindQQ', 'qqDiv', 'qq');
			whileClick('bindPostcode', 'postcodeDiv', 'postcode');
			whileClick('bindAddress', 'addressDiv', 'address');
						
			whileClick('updateAddressCancel', 'address', 'addressDiv');
			whileClick('updatePostcodeCancel', 'postcode', 'postcodeDiv');
			whileClick('updateEmailCancel', 'email', 'emailDiv');
			whileClick('updateQQCancel', 'qq', 'qqDiv');
		});
		
		function whileClick(id, display, unDisplay) {
			$("#" + id).click(function(){
				document.getElementById(display).style.display = "";
				document.getElementById(unDisplay).style.display = "none";
			});
		}
		
		
		$(function(){
			$("#updateEmail").click(function(){
				var email = document.getElementById("emailInput").value;
				updateByPostRequest(window.BASESITE + '/user/user!updateEmail.action', {email: email}, 'email', 'emailDiv');
			});
		});
		
		$(function(){
			$("#updateAddress").click(function(){
				var address = document.getElementById("addressInput").value;
				updateByPostRequest(window.BASESITE + '/user/user!updateAddress.action', {address: address}, 'address', 'addressDiv');
			});
		});
		
		$(function(){
			$("#updateQQ").click(function(){
				var qq = document.getElementById("qqInput").value;							
				updateByPostRequest(window.BASESITE + '/user/user!updateQQ.action', {qq: qq}, 'qq', 'qqDiv');
			});
		});
		
		$(function(){
			$("#updatePostcode").click(function(){
				var postcode = document.getElementById("postcodeInput").value;							
				updateByPostRequest(window.BASESITE + '/user/user!updatePostcode.action', {postcode: postcode}, 'postcode', 'postcodeDiv');
			});
		});
		
		function updateByPostRequest(url, object, display, unDisplay) {
			$.post(url,object,function(data) {
					if(data.success) {
						document.getElementById(display).style.display = "";
						document.getElementById(unDisplay).style.display = "none";
						document.getElementById(display).innerHTML = object[display];
					}
				},'json');
		}
	</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'account'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
   <#include "/common/message.ftl" />
  		 <div class="yhzxright">
  	  	<#include "user-loginInfo.ftl"/>
    <div class="border04">
      <div class="yhzxdivtit white_FFF_14">修改个人资料</div>
	  <div style="color:#333; padding:10px 0;">
	    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5">
          <tr>
            <td align="center" valign="middle" bgcolor="#FFFFFF" style="padding:20px 0;"><table width="86%" border="0" cellpadding="0" cellspacing="0" style="font-family:Tahoma, '宋体';">
              <tr>
                <td width="19%" height="30" align="right" class="black_222_14">真实姓名：</td>
                <#if user?? && user.info?? && user.info.realName??>
	                <#assign size = user.info.realName?length>
	                <#if size gt 2>
	                	<td width="37%" align="left" class="black_222_14">${user.info.realName?substring(0,1)}*${user.info.realName?substring(2,size)}</td>	
	                <#else>
	                	<td width="37%" align="left" class="black_222_14">${user.info.realName?substring(0,1)}*</td>
	                </#if>
	            <#else>
	            	<td width="37%" align="left" class="black_222_14"></td>
	            </#if>
                <td align="left">&nbsp;</td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">身份证号：</td>
                <td align="left" class="black_222_14">
                	<#if user.info?? && user.info.idCard?? && user.info.idCard!="">
	                	${user.info.idCard?substring(0,10)}**********
	                	<input id="idCardInput" type="hidden" name="regForm.idCard"  value="${user.info.idCard!}"> 
                	<#else>
                		${user.info.idCard!}
                	</#if>              	
                </td>
                
                <td align="left">&nbsp;</td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">手机号码：</td>
                <td align="left" class="black_222_14">
                	 <#if user.info??&&user.info.mobile??&&user.info.mobile!="">
                	  	 ${user.info.mobile?substring(0,3)}****${user.info.mobile?substring(7)}
				     	 <input id="phoneInput" type="hidden" name="regForm.phoneNumber"  value="${user.info.mobile!}">
				      <#else>
				      	 ${user.phoneNo!}
				      </#if>	 
                </td>
                <td align="left"><div class="yhzxli05"><#if user.validatedPhoneNo?? && user.validatedPhoneNo>已绑定<#else>未绑定</#if></div></td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">电子邮箱：</td>
                
                 	<td align="left" class="black_222_14">
                 	 <#if user.info??&&user.info.email??&&user.info.email!="">
                 		<span class="bro_af5529" id="email">${user.info.email?substring(0,3)}****${user.info.email?substring(7)}</span>
	                <#else>	
	                	<span class="bro_af5529" id="email">你还没有绑定邮箱</span>
	                </#if>
	                	<div id="emailDiv" style="display:none"><input id="emailInput" width="70" type="text" name="email"  value="${user.info.email!}">
	                	<input id="updateEmail" type="button" value="确认">
	                	<input id="updateEmailCancel" type="button" value="取消"></div>
                	</td>
                
                
                <td align="left"><a id="bindEmail" href="#" class="yhzxli04">绑定</a></td>
              </tr>
             
              <tr>
                <td height="30" align="right" class="black_222_14">QQ：</td>
                <td align="left" class="black_222_14"><span class="black_222_14" id="qq">${user.info.qq!""}</span>
                	<div id="qqDiv" style="display:none">
                		<input id="qqInput" class="black_222_14" width="70" type="text" name="qq"  value=${user.info.qq!""}>
	                	<input id="updateQQ" type="button" value="确认">
	                	<input id="updateQQCancel" type="button" value="取消">
	                </div>
                </td>
                <td align="left"><a id="bindQQ" href="#" class="yhzxli04">修改</a></td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">联系地址：</td>
                <td align="left" class="black_222_14"><span id="address" class="black_222_14">${user.info.address!""}</span>
                	<div id="addressDiv" style="display:none">
                		<input id="addressInput" class="black_222_14" width="70" type="text" name="address"  value=${user.info.address!""}>
	                	<input id="updateAddress" type="button" value="确认">
	                	<input id="updateAddressCancel" type="button" value="取消">
	                </div>
                </td>
                <td align="left"><a id="bindAddress" href="#" class="yhzxli04">修改</a></td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">邮政编码：</td>
                <td align="left" class="black_222_14"><span id="postcode" class="black_222_14">${user.info.postcode!""}</span>
                	<div id="postcodeDiv" style="display:none">
                		<input id="postcodeInput" class="black_222_14" width="70" type="text" name="postcode"  value=${user.info.postcode!""}>
	                	<input id="updatePostcode" type="button" value="确认">
	                	<input id="updatePostcodeCancel" type="button" value="取消">
	                </div>
                </td>
                <td align="left"><a id="bindPostcode" href="#" class="yhzxli04">修改</a></td>
              </tr>
              <tr>
                <td height="30" align="right" class="black_222_14">注册时间：</td>
                <td align="left" class="black_222_14"><span class="black_888_14" id="lbRegTime">${user.info.createTime!""}</span></td>
                <td align="left">&nbsp;</td>
              </tr>
            </table>
              <br />
              <br />
              <br />
              <br />
              <br />
            <br />
            <br />
            <br />
            <br />
            <br /></td>
          </tr>
        </table>
	  </div>
    </div>
  </div>
  
  
  
  
  
   
 
  
  

  
        
  
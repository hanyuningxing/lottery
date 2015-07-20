   //用于创建dialog
   function createDialog(id_, title_, width_, height_) {
		$(id_).dialog({  
			autoOpen:false,
			title:title_,
			width:width_, 
			height:height_, 
			modal:true
		});
	}
   
  //当输入验证码点击返回时，调用此方法
   function reInputMobile() {
	   var mobile = Cookies.get('mobile');
	   $('#msgValidateDialog').dialog('close');
	   $("#mobileWriteDialog").dialog('open');
	   document.getElementById("mobile").value = mobile;
   }
	//创建所需dialog
   	$(function(){ 
		createDialog('#bankInfoUpdatedialog', '银行卡信息', 445);
		createDialog('#msgValidateDialog', '手机验证', 420);
		createDialog('#mobileWriteDialog', '手机填写', 420);
		createDialog('#dialogResetPassword', '修改密码',515);
		createDialog('#setIdCardDialog','设置身份认证信息', 515);
		createDialog('#tkValidateDialog','设置提款认证', 515);
		createDialog('#needPayPasswordDialog','设置是否需要支付密码', 480);
		
	});	
function addSubmitEventForUpdateBankInfoForm() {
   	$('#updateBankInfo').off('submit').submit(function() {
		var bankName = $("#bankName").val();
		var partBankProvince = $("#selProvince").val();
		var partBankCity = $("#selCity").val();
		var partBankName = $("#partBankName").val();
		var bankCard = $("#bankCard").val();
		var userRealName = $("#userRealName").val();
		
		$.post(window.BASESITE + '/user/user!bindBankCardOfDialog.action',{
				'bankInfoForm.bankName': bankName,
				'bankInfoForm.partBankProvince': partBankProvince,
				'bankInfoForm.partBankCity': partBankCity,
				'bankInfoForm.partBankName': partBankName,
				'bankInfoForm.partBankName': partBankName,
				'bankInfoForm.bankCard': bankCard,
				'bankInfoForm.userRealName': userRealName
					
		},function(data){
			$("#bankInfoUpdatedialog").dialog('close');
			setCommonResult(data.msg,data.success);
			if(data.needValidateIdCard!=null && data.needValidateIdCard) {
				showBindBankCardDlog = true;
				setTimeout(
					function(){
						//$("#setIdCard").click();
						$("#header_setIdCardDialog").dialog('open');
						$("#commonResultDialog").dialog('close');
					}
				,2000);
			}
			if(data.success) {
				document.getElementById("isBindBankInfo").innerHTML = "已设置";
				setTimeout(
					function(){
						$("#commonResultDialog").dialog('close');
					}
				, 2000);
			}
		},'json');										 					 		
	
		return false;
   	});		
}

function addSubmitEventForTkValidateForm() {
	$('#tkValidateForm').submit(function() {
		var needTkValidate = $('#tkValidateForm [name="needTkValidate"]').filter(':checked').val();
		$.post(window.BASESITE + '/user/user!setNeedValidateWhileTk.action', {
			needTkValidate: needTkValidate
		}, function(data) {
				$("#tkValidateDialog").dialog('close');
				setCommonResult(data.msg,data.success);
				if(data.isNeedValidateWhileTk) {
					document.getElementById("isNeedValidateWhileTk").innerHTML = "已开启";
				}else {
					document.getElementById("isNeedValidateWhileTk").innerHTML = "未开启";
				}
				setTimeout(
					function(){
						$("#commonResultDialog").dialog('close');
					}
				,2000);
		}, 'json');					
		return false;  //阻止表单提交
		
	});	
}
function AddClickEventForGetMsgCode() {
	$("#getMsgCode").off('click').click(function(){
		$.post(window.BASESITE + '/user/user!sendMsg.action',{
		},function(data){
			if(data.success){
				document.getElementById('getMsgCode').setAttribute("disabled", true);				
				var i = 60;
				document.getElementById("getMsgCode").value = i-- + "秒后可点击重发"; 
			    var h = setInterval((function(i){
			        return function() {			        	
			        	document.getElementById("getMsgCode").value = i-- + "秒后可点击重发";
			        	if(i <= 0) {
			        		clearInterval(h);
			        		document.getElementById("getMsgCode").value="获取短信验证码";
			        		document.getElementById("getMsgCode").removeAttribute("disabled");	        		
			        	}
			        }
			    }(i)), 1000);						
				
			} else {
				setCommonResult(data.msg,data.success);
			}	
		},'json');
	});						
}

function msgValidateFormAddSubmitEventFor(callback){
	$('#msgValidateform').off('submit').submit(function() {
		var msgCode = $('#msgValidateform [name="yanzhengma"]').val();
		if(strIsNull(msgCode)) {
			setCommonResult('验证码不能为空！', false);
			return false;
		}	
		requestByPost(window.BASESITE + '/user/user!msgValid_.action', {msgCode: msgCode}, callback);
		return false; 
	 });	 
}

function updateBankInfo(data) {
	document.getElementById('yanzhengma').value="";							

	if(data.success) {
		$("#msgValidateDialog").dialog('close');
		$("#bankInfoUpdatedialog").dialog('open');
		addSubmitEventForUpdateBankInfoForm();
	} else {
		document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
		setTimeout(function(){document.getElementById("error").innerHTML = "";}, 3000);
	}		
}

function updateTkValidate(data) {
	document.getElementById('yanzhengma').value="";							
	if(data.success) {
		$("#msgValidateDialog").dialog('close');
		$("#tkValidateDialog").dialog('open');
		addSubmitEventForTkValidateForm();
	} else {
		document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
		setTimeout(function(){document.getElementById("error").innerHTML = "";}, 3000);
	}		
}
//修改密码
$(function(){    
	$("#resetPassword").click(function(){  		
		$("#dialogResetPassword").dialog('open');  
		$('#xiugaimima').off('submit').submit(function() {
			var oldPassword = $('#xiugaimima [name="oldPassword"]').val();
			var password = $('#xiugaimima [name="newPassword"]').val();
			var confirmPassword = $('#xiugaimima [name="confirmPassword"]').val();
				
				
			$.post(window.BASESITE + '/user/user!updatePasswd.action', {
				oldPassword: oldPassword,
				password: password,
				confirmPassword: confirmPassword
			}, function(data) {
				document.getElementById('oldPassword').value="";
				document.getElementById('newPassword').value="";
				document.getElementById('confirmPassword').value="";
				$("#dialogResetPassword").dialog('close');
				setCommonResult(data.msg,data.success);													
			}, 'json');					
			return false;  //阻止表单提交					 
		});				
	}); 			
}); 
//若未进行手机绑定，则绑定银行卡前会先要求绑定手机，
//此时设定showBindBankCardDlog为true,则绑定手机后会弹出绑定银行卡对话框
var showBindBankCardDlog = false;
//银行卡绑定
$(function(){
	$("#bankInfoUpdate").click(function(){
//		$.post(window.BASESITE + '/user/user!checkMobileExist.action', {   					 
//		}, function(data) {
//			if(data.exist) {
//				$("#msgValidateDialog").dialog('open');
//				document.getElementById("content").innerHTML = '你的手机号码为' + data.mobile + ',绑定银行卡需要手机验证';
//				document.getElementById("return").onclick = function() {$("#msgValidateDialog").dialog('close');};
//				AddClickEventForGetMsgCode();				
//				msgValidateFormAddSubmitEventFor(updateBankInfo);
//			} else {
//				setCommonResult(data.msg,data.exist);
//				showBindBankCardDlog = true;
//				setTimeout(
//					function(){
//						$("#msgValidate").click();
//						$("#commonResultDialog").dialog('close');
//					}
//				, 2000);
//			}	
//		},'json');
		herder_checkIdCardAndRealNameExist();
		showBindBankCardDlog = true;
		
	}); 			
});

function xiugaimimaSubmit() {	
	if($.formValidator.pageIsValid("1")) {
		$('#xiugaimima').submit();
	}
	
}

function needPayPasswordFirstSetFormSubmit() {	
	if($.formValidator.pageIsValid("4")) {
		$('#needPayPasswordFirstSetForm').submit();
	}
	
}

function needPayPasswordUpdateFormSubmit() {	
	if($.formValidator.pageIsValid("3")) {
		$('#needPayPasswordUpdateForm').submit();
	}
	
}

function setIdCardFormSubmit() {
	if($.formValidator.pageIsValid("2")) {
		$('#setIdCardForm').submit();
	}
}
//设置身份验证
$(function(){
	$("#setIdCard").click(function(){	
		$.post(window.BASESITE + '/user/user!checkIdCardIsExist.action', {
		}, function(data) {
			if(data.success) {
				$("#setIdCardDialog").dialog('open');
				$('#setIdCardForm').submit(function() {
					var idCard = $('#setIdCardForm [name="idCard"]').val();
					var confirmIdCard = $('#setIdCardForm [name="confirmIdCard"]').val();
					
					$.post(window.BASESITE + '/user/user!setIdCard.action', {
						idCard: idCard,
						confirmIdCard: confirmIdCard
					}, function(data) {
							$("#setIdCardDialog").dialog('close');
							setCommonResult(data.msg,data.success);
							if(showBindBankCardDlog) {
								setTimeout(function(){$("#commonResultDialog").dialog('close');
								$("#bankInfoUpdatedialog").dialog('open');},2000);								
							} else {
								document.getElementById("isValidateIdCard").innerHTML = "已认证";
								setTimeout(
									function() {
										$("#commonResultDialog").dialog('close');
									}
								,2000)
							}
					}, 'json');					
					 return false;  //阻止表单提交					 
				});				
			} else {
				setCommonResult(data.msg,!data.success);
			}
		},'json');	
	});
});
//设置提款验证前需要先进行手机绑定，
//若未进行手机绑定，则先弹出手机绑定对话框，此时showTkDlog设为true,则绑定手机后会弹出提款验证对话框
var showTkVldDlog = false;
//设置是否需要提款验证
$(function(){
	$("#tkValidate").click(function(){
		
		$.post(window.BASESITE + '/user/user!checkIsValidatePhoneNo.action', {
		}, function(data) {
			if(data.isValidate) {			
				$("#msgValidateDialog").dialog('open');
				document.getElementById("content").innerHTML = '你的手机号码为' + data.mobile + ',更改提款验证设置需要手机短信验证';
				document.getElementById("return").onclick = function() {$("#msgValidateDialog").dialog('close');};
				
				AddClickEventForGetMsgCode();
				msgValidateFormAddSubmitEventFor(updateTkValidate);	
			} else {
				setCommonResult(data.msg, data.isValidate);
				//设置手机验证后是否进行提款验证
				showTkVldDlog = true;
				setTimeout(function(){$("#msgValidate").click();$("#commonResultDialog").dialog('close');}, 2000);
			}
		},'json');
		
	});
});

//设置是否需要支付密码
$(function(){
	$("#yes").click(function() {
		document.getElementById("whileNoNeedPayPswd").style.display = "none";
		$.post(window.BASESITE + '/user/user!isFirstTimeSetPayPassword.action', {			
		}, function(data) {
			if(data.isFirstTimeSetPayPassword) {
				document.getElementById("needPayPasswordFirstSetForm").style.display = "";
				document.getElementById("yes_firstSet").checked = "checked";			
			} else {
				document.getElementById("needPayPasswordUpdateForm").style.display = "";
				document.getElementById("yes_update").checked = "checked";
			
			}
		},'json');
	});
	
	$("#no").click(function() {
		document.getElementById("whileNoNeedPayPswd").style.display = "none";
		document.getElementById("needPayPasswordFirstSetForm").style.display = "none";
		document.getElementById("needPayPasswordUpdateForm").style.display = "none";
		document.getElementById("whileNoNeedPayPswd").style.display = "";
		document.getElementById("no_update").checked = "checked";
		
	});
	
	$("#needPayPassword").click(function(){
		
		$.post(window.BASESITE + '/user/user!isNeedPayPassword.action', {			
		}, function(data) {
			if(data.needPayPassword) {
				document.getElementById("needPayPasswordUpdateForm").style.display = "";				
				document.getElementById("yes").checked = "checked";
				document.getElementById("yes_update").checked = "checked";
				
			} else {
				document.getElementById("whileNoNeedPayPswd").style.display = "";
				document.getElementById("needPayPasswordFirstSetForm").style.display = "none";
				document.getElementById("needPayPasswordUpdateForm").style.display = "none";
				document.getElementById("no").checked = "checked";
				document.getElementById("no_update").checked = "checked";
				
			}
		},'json');
		
		$("#needPayPasswordDialog").dialog('open');
	});	
});

$(function(){
	$('#needPayPasswordUpdateForm').off('submit').submit(function() {
		var needPayPassword = $('#needPayPasswordUpdateForm [name="needPayPassword_update"]').filter(':checked').val();
		var oldPayPassword = $('#needPayPasswordUpdateForm [name="oldPayPassword"]').val();
		var payPassword =  $('#needPayPasswordUpdateForm [name="payPassword_update"]').val();
		var confirmPayPassword =  $('#needPayPasswordUpdateForm [name="confirmPayPassword_update"]').val();			
		
		$.post(window.BASESITE + '/user/user!setNeedPayPassword.action', {
			needPayPassword: needPayPassword,
			oldPayPassword: oldPayPassword,
			payPassword: payPassword,
			confirmPayPassword: confirmPayPassword
		}, function(data) {
				$("#needPayPasswordDialog").dialog('close');
				setCommonResult(data.msg,data.success);
				document.getElementById("oldPayPassword").value = "";
				document.getElementById("payPassword_update").value = "";
				document.getElementById("confirmPayPassword_update").value = "";
				if(data.isNeedPayPassword){
					document.getElementById("isNeedPayPassword").innerHTML = "已开启";
				} else{
					document.getElementById("isNeedPayPassword").innerHTML = "未开启";
				}	
				setTimeout(
					function(){
						$("#commonResultDialog").dialog('close');
					}
				,2000);
			}, 'json');			
	});	
});

$(function(){
	$('#needPayPasswordFirstSetForm').off('submit').submit(function() {
		var needPayPassword = $('#needPayPasswordFirstSetForm [name="needPayPassword_firstSet"]').filter(':checked').val();
		var payPassword =  $('#needPayPasswordFirstSetForm [name="payPassword"]').val();
		var confirmPayPassword =  $('#needPayPasswordFirstSetForm [name="confirmPayPassword"]').val();			
		
		$.post(window.BASESITE + '/user/user!setNeedPayPassword.action', {
			needPayPassword: needPayPassword,
			payPassword: payPassword,
			confirmPayPassword: confirmPayPassword
		}, function(data) {
				$("#needPayPasswordDialog").dialog('close');
				setCommonResult(data.msg,data.success);
				document.getElementById("needPayPasswordFirstSetForm").style.display = "none";
				document.getElementById("payPassword").value = "";
				document.getElementById("confirmPayPassword").value = "";
				if(data.isNeedPayPassword){
					document.getElementById("isNeedPayPassword").innerHTML = "已开启";
				} else{
					document.getElementById("isNeedPayPassword").innerHTML = "未开启";
				}	
				setTimeout(
					function(){
						$("#commonResultDialog").dialog('close');
					}
				,2000);
			}, 'json');					
		 return false;  //阻止表单提交
		
	});
});

function setNoNeedPayPassword() {
	$.post(window.BASESITE + '/user/user!setNeedPayPassword.action', {
		needPayPassword: 0
	}, function(data) {
			$("#needPayPasswordDialog").dialog('close');
			setCommonResult(data.msg,data.success);
			if(data.isNeedPayPassword){
				document.getElementById("isNeedPayPassword").innerHTML = "已开启";
			} else{
				document.getElementById("isNeedPayPassword").innerHTML = "未开启";
			}	
			setTimeout(
				function(){
					$("#commonResultDialog").dialog('close');
				}
			,2000);
	}, 'json');		
}

$(document).ready(function(){
	$.formValidator.initConfig({validatorgroup:"1",formid:"xiugaimima",onerror:function(msg){ },onsuccess:function(){ return true;}});			
	checkFormOfxiugaimima();
	
	$.formValidator.initConfig({validatorgroup:"2",formid:"setIdCardForm",onerror:function(msg){ },onsuccess:function(){ return true;}});			
	checkFormOfSetIdCard();
	
	$.formValidator.initConfig({validatorgroup:"4",formid:"needPayPasswordFirstSetForm",onerror:function(msg){ },onsuccess:function(){ return true;}});			
	checkFormOfNeedPayPasswordFirstSet();
	
	$.formValidator.initConfig({validatorgroup:"3",formid:"needPayPasswordUpdateForm",onerror:function(msg){ },onsuccess:function(){ return true;}});			
	checkFormOfNeedPayPasswordUpdate();
});

function checkFormOfxiugaimima(){	
	$("#newPassword").formValidator({validatorgroup:"1",onshow:"请输入6-15位数字和字母组成的密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:15,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"请输入6-15位字母和数字组成的密码"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"密码格式有误，请输入6-15位数字和字母组成的密码"});
		
	$("#confirmPassword").formValidator({validatorgroup:"1",onshow:"请重复您输入的密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"重复密码不能为空,请确认"}).compareValidator({desid:"newPassword",operateor:"=",onerror:"两次输入的密码不一致,请确认"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"密码格式有误，请输入6-15位数字和字母组成的密码"});	
}

function checkFormOfSetIdCard(){
	$("#idCard").formValidator({validatorgroup:"2",onshow:"请输入15或18位的身份证号码",onfocus:"身份证号不能为空",oncorrect:"身份证号合法"}).inputValidator({empty:{leftempty:false,rightempty:false,emptyerror:"身份证号两边不能有空符号"},onerror:"请输入正确的身份证号码"})			
	.regexValidator({regexp:"(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)",onerror:"格式有误，身份证号码由15或18位的数字或字母组成"});
	$("#confirmIdCard").formValidator({validatorgroup:"2",onshow:"请输入15或18位的身份证号码",onfocus:"两次输入必须一致哦",oncorrect:"身份证号一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"身份证号两边不能有空符号"},onerror:"身份证号不能为空"}).compareValidator({desid:"idCard",operateor:"=",onerror:"两次输入的身份证号不一致,请确认"})
	.regexValidator({regexp:"(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)",onerror:"格式有误，身份证号码由15或18位的数字或字母组成"});
}

function checkFormOfNeedPayPasswordUpdate(){	
	$("#payPassword_update").formValidator({validatorgroup:"3",onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:15,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"请输入6-15位字母和数字组成的密码"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位的密码"});
		
	$("#confirmPayPassword_update").formValidator({validatorgroup:"3",onshow:"请重复密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"重复密码不能为空,请确认"}).compareValidator({desid:"payPassword_update",operateor:"=",onerror:"两次输入的密码不一致,请确认"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位的密码"});	
}

function checkFormOfNeedPayPasswordFirstSet(){	
	$("#payPassword").formValidator({validatorgroup:"4",onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:15,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"请输入6-15位字母和数字组成的密码"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位的密码"});
		
	$("#confirmPayPassword").formValidator({validatorgroup:"4",onshow:"请重复密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"重复密码不能为空,请确认"}).compareValidator({desid:"payPassword",operateor:"=",onerror:"两次输入的密码不一致,请确认"})
	.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位的密码"});	
}



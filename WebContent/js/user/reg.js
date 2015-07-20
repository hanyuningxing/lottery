//判断检查项是否完整
var isNameOk = false;
var isPassOk = false;
var isQpassOk = false;
var isPhoneOk = false;
var isEmailOk = false;
var isCodeOk = false;
var isIdCardOk = false;
var isRealNameOk = false;
var isOldPassOk = false;
$(document).ready(function() {
	$("#userNameInput").val("");
	$("#passwordInput").val("");
	$("#confirmPswInput").val("");
	$("#phoneInput").val("");
	$("#emailInput").val("");
	$("#codeInput").val("");
	$("#realNameInput").val("");
	$("#idCardInput").val("");
	$("#oldpasswordInput").val("");
	
	
	$("#msgCodeInput").css("display","none");
	$("#registerbtn").removeAttr("disabled");
	
	$("#userNameInput").focus(function(){
		$("#userName_list_alert_div").removeClass("error_num");
		$("#userName_list_alert_div").removeClass("right_name");
		$("#userName_list_div").addClass("list_div_ys");
		$("#userNameInput").addClass("text_inp_yanse");
		$("#userName_list_alert_div").html("由4～12位字母、汉字，数字组合<br />注册后不可修改，不区分大小写");
	})
	
	$("#userNameInput").blur(function (){
		var name_inp = this.value;
		if(name_inp.length<4||name_inp.length>12){
			isNameOk = false;
			$("#userName_list_alert_div").addClass("error_num");
			$("#userName_list_alert_div").html("用户名的长度为4-12位");
			$("#userName_list_div").removeClass("list_div_ys");
			$("#userNameInput").removeClass("text_inp_yanse");
			$("#userName_list_alert_div").removeClass("right_name");
			return false;
		}else if(/^\d{4,12}$/.test(name_inp)){
			isNameOk = false;
			$("#userName_list_alert_div").addClass("error_num");
			$("#userName_list_alert_div").html("用户名不允许全部为数字");
			$("#userName_list_div").removeClass("list_div_ys");
			$("#userNameInput").removeClass("text_inp_yanse");
			$("#userName_list_alert_div").removeClass("right_name");
			return false;
		}else if(!/^[a-zA-Z0-9\u4E00-\u9FBF_]{4,12}$/.test(name_inp)){
			isNameOk = false;
			$("#userName_list_alert_div").addClass("error_num");
			$("#userName_list_alert_div").html("用户名不允许包含逗号、分号等等");
			$("#userName_list_div").removeClass("list_div_ys");
			$("#userNameInput").removeClass("text_inp_yanse");
			$("#userName_list_alert_div").removeClass("right_name");
			return false;
		}else if(name_inp.indexOf('深圳米域高科技有限公司')!=-1||name_inp.indexOf('客服')!=-1||name_inp.indexOf('网管')!=-1||name_inp.indexOf('管理员')!=-1||
				name_inp.indexOf('system')!=-1||name_inp.indexOf('administrator')!=-1||name_inp.indexOf('webmaster')!=-1||name_inp.indexOf('master')!=-1){
			isNameOk = false;
			$("#userName_list_alert_div").addClass("error_num");
			$("#userName_list_alert_div").html("请输入其他用户名");
			$("#userName_list_div").removeClass("list_div_ys");
			$("#userNameInput").removeClass("text_inp_yanse");
			$("#userName_list_alert_div").removeClass("right_name");
			return false;
		}
		
		validUnique(name_inp);
	})
	
	$("#passwordInput").focus(function(){
		$("#password_list_alert_div").removeClass("error_num");
		$("#password_list_alert_div").removeClass("right_name");
		$("#password_list_div").addClass("list_div_ys");
		$("#passwordInput").addClass("text_inp_yanse");
		$("#password_list_alert_div").html("由6～15位字母和数字组成。<br />为了您的帐户安全，建议使用大小写字母和数字混合的密码。");
	
	});
	
	$("#passwordInput").blur(function (){
		var pass_inp = this.value;
		var patrn=/^([a-zA-Z0-9]){6,15}$/;
		if(pass_inp.length < 6 || pass_inp.length >15){
			isPassOk = false;	
			$("#password_list_alert_div").addClass("error_num");
			$("#password_list_alert_div").html("请输入6-15位密码");	
			$("#password_list_div").removeClass("list_div_ys");
			$("#passwordInput").removeClass("text_inp_yanse");
			$("#password_list_alert_div").removeClass("right_name");
			return false;
		}else if(!patrn.exec(pass_inp)) {
			isPassOk = false;	
			$("#password_list_alert_div").addClass("error_num");
			$("#password_list_alert_div").html("请输入6-15位字母和数字组成的密码,字母区分大小写");	
			$("#password_list_div").removeClass("list_div_ys");
			$("#passwordInput").removeClass("text_inp_yanse");
			$("#password_list_alert_div").removeClass("right_name");
			return false;
		}else{
			$("#password_list_alert_div").removeClass("error_num");	
			$("#password_list_div").removeClass("list_div_ys");
			$("#passwordInput").removeClass("text_inp_yanse");
			$("#password_list_alert_div").addClass("right_name");
			$("#password_list_alert_div").html("");
			isPassOk = true;			
			return true;
		}
	});
	
	
	$("#confirmPswInput").focus(function(){
		$("#confirm_password_list_alert_div").removeClass("error_num");
		$("#confirm_password_list_alert_div").removeClass("right_name");
		$("#confirm_password_list_div").addClass("list_div_ys");
		$("#confirmPswInput").addClass("text_inp_yanse");
		$("#confirm_password_list_alert_div").html("请再次输入登录密码");
	
	});
	
	$("#confirmPswInput").blur(function (){
		var patrn=/^([a-zA-Z0-9]){6,15}$/;
		var confirm_pass_inp = this.value;
		var pass_inp = $("#passwordInput").val();
		if(confirm_pass_inp == pass_inp && patrn.exec(confirm_pass_inp)){
			$("#confirm_password_list_alert_div").removeClass("error_num");	
			$("#confirm_password_list_div").removeClass("list_div_ys");
			$("#confirmPswInput").removeClass("text_inp_yanse");
			$("#confirm_password_list_alert_div").addClass("right_name");
			$("#confirm_password_list_alert_div").html("");
			isQpassOk = true;
			isPassOk = true;
		}else{
			$("#confirm_password_list_alert_div").addClass("error_num");
			$("#confirm_password_list_div").removeClass("list_div_ys");
			$("#confirmPswInput").removeClass("text_inp_yanse");
			$("#confirm_password_list_alert_div").removeClass("right_name");
			$("#confirm_password_list_alert_div").html("您两次输入的密码不一致");
			isQpassOk = false;
			isPassOk = false;
			return false;
		}
	});	
	
	$("#phoneInput").focus(function(){
		$("#phone_list_alert_div").removeClass("error_num");
		$("#phone_list_alert_div").removeClass("right_name");
		$("#phone_list_div").addClass("list_div_ys");
		$("#phoneInput").addClass("text_inp_yanse");
		$("#phone_list_alert_div").html("请输入以13,15,18开头的手机号码");
	});
	
	$("#phoneInput").blur(function (){
		var phone_inp = this.value;
		var validMobile = valiedateMobile(phone_inp);
		if(validMobile != 2){
			isPhoneOk = false;
			if(validMobile == 0){ //大奖电话为空
				isPhoneOk = false;		
				$("#phone_list_alert_div").addClass("error_num");
				$("#phone_list_alert_div").html("您输入的电话号码为空");	
				$("#phone_list_div").removeClass("list_div_ys");
				$("#phoneInput").removeClass("text_inp_yanse");
				$("#phone_list_alert_div").removeClass("right_name");
				return false;
			}
			if(validMobile == 1){ //大奖电话为空
				isPhoneOk = false;
				$("#phone_list_alert_div").addClass("error_num");
				$("#phone_list_alert_div").html("手机格式错误！<br>如：13682566740");	
				$("#phone_list_div").removeClass("list_div_ys");
				$("#phoneInput").removeClass("text_inp_yanse");
				$("#phone_list_alert_div").removeClass("right_name");
				return false;
			}
		}else{
			$("#phone_list_alert_div").removeClass("error_num");	
			$("#phone_list_div").removeClass("list_div_ys");
			$("#phoneInput").removeClass("text_inp_yanse");
			$("#phone_list_alert_div").addClass("right_name");
			$("#phone_list_alert_div").html("");
			isPhoneOk = true;
		}
		validPhoneUnique(phone_inp);
	});	

	$("#emailInput").focus(function(){
		$("#email_list_alert_div").removeClass("error_num");
		$("#email_list_alert_div").removeClass("right_name");
		$("#email_list_div").addClass("list_div_ys");
		$("#emailInput").addClass("text_inp_yanse");
		$("#email_list_alert_div").html("请输入正确格式的邮箱。示例：caipiao@sina.cn");
	});
	
	$("#emailInput").blur(function (){
		var email_inp = this.value;
		var validEmail = checkEmailAllIn(email_inp);
		if(validEmail == 2 && email_inp != ""){
			$("#email_list_alert_div").removeClass("error_num");	
			$("#email_list_div").removeClass("list_div_ys");
			$("#emailInput").removeClass("text_inp_yanse");
			$("#email_list_alert_div").addClass("right_name");
			$("#email_list_alert_div").html("");
			isEmailOk=true;
		}else{
			isEmailOk = false;		
			$("#email_list_alert_div").addClass("error_num");
			$("#email_list_alert_div").html("请输入正确格式的邮箱。示例：caipiao@sina.cn,推荐使用 <a id='mail_tuijian' href='http://login.sina.com.cn/signup/signup.php?entry=freemail' target='_blank'>新浪邮箱</a>");	
			$("#email_list_div").removeClass("list_div_ys");
			$("#emailInput").removeClass("text_inp_yanse");
			$("#email_list_alert_div").removeClass("right_name");
			return false;
		}
		validEmailUnique(email_inp);
	});		

	$("#codeInput").focus(function(){
		$("#code_list_alert_div").removeClass("error_num");
		$("#code_list_alert_div").removeClass("right_name");
		$("#code_list_div").addClass("list_div_ys");
		$("#codeInput").addClass("text_inp_yanse");
		$("#code_list_alert_div").html("");
	});
	
	$("#codeInput").blur(function (){
		var code_inp = this.value;
		checkValidCodeAllIn(code_inp);
	});			
	
	$("#realNameInput").focus(function(){
		$("#realName_alert_list_div").removeClass("error_num");
		$("#realName_alert_list_div").removeClass("right_name");
		$("#realName_list_div").addClass("list_div_ys");
		$("#realNameInput").addClass("text_inp_yanse");
		$("#realName_alert_list_div").html("请输入真实姓名");
	});
	
	$("#realNameInput").blur(function (){
		var realName_inps = this.value;
		if(realName_inps==""){
			$("#realName_alert_list_div").addClass("error_num");	
			$("#realName_list_div").removeClass("list_div_ys");
			$("#realNameInput").removeClass("text_inp_yanse");
			$("#realName_alert_list_div").removeClass("right_name");
			$("#realName_alert_list_div").html("请输入真实姓名");
			isRealNameOk = false;
			return false;
		}else{
			$("#realName_alert_list_div").removeClass("error_num");	
			$("#realName_list_div").removeClass("list_div_ys");
			$("#realNameInput").removeClass("text_inp_yanse");
			$("#realName_alert_list_div").addClass("right_name");
			$("#realName_alert_list_div").html("");
			isRealNameOk = true;
		}

	});
	
	$("#idCardInput").focus(function(){
		$("#idCard_alert_list_div").removeClass("error_num");
		$("#idCard_alert_list_div").removeClass("right_name");
		$("#idCard_list_div").addClass("list_div_ys");
		$("#idCardInput").addClass("text_inp_yanse");
		$("#idCard_alert_list_div").html("请输入身份证号");
	});
	
	$("#idCardInput").blur(function (){
		var idCard_inps = this.value;
		isIdCard(idCard_inps);
	});
	//旧密码
	$("#oldpasswordInput").focus(function(){
		$("#oldpassword_list_alert_div").removeClass("error_num");
		$("#oldpassword_list_alert_div").removeClass("right_name");
		$("#oldpassword_list_div").addClass("list_div_ys");
		$("#oldpasswordInput").addClass("text_inp_yanse");
		$("#oldpassword_list_alert_div").html("由6～15位字母和数字组成。<br />为了您的帐户安全，建议使用大小写字母和数字混合的密码。");
	
	});
	
	$("#oldpasswordInput").blur(function (){
		var pass_inp = this.value;
		var patrn=/^([a-zA-Z0-9]){6,15}$/;
		if(pass_inp.length < 6){
			isOldPassOk = false;		
			$("#oldpassword_list_alert_div").addClass("error_num");
			$("#oldpassword_list_alert_div").html("请输入6-15位密码");	
			$("#oldpassword_list_div").removeClass("list_div_ys");
			$("#oldpasswordInput").removeClass("text_inp_yanse");
			$("#oldpassword_list_alert_div").removeClass("right_name");
			return false;
		}else if(pass_inp.length >15){
			isOldPassOk = false;		
			$("#oldpassword_list_alert_div").addClass("error_num");
			$("#oldpassword_list_alert_div").html("请输入6-15位密码");	
			$("#oldpassword_list_div").removeClass("list_div_ys");
			$("#oldpasswordInput").removeClass("text_inp_yanse");
			$("#oldpassword_list_alert_div").removeClass("right_name");
			return false;			
		}else if(!patrn.exec(pass_inp)) {
			isOldPassOk = false;
			$("#oldpassword_list_alert_div").addClass("error_num");
			$("#oldpassword_list_alert_div").html("请输入6-15位字母和数字组成的密码,字母区分大小写");	
			$("#oldpassword_list_div").removeClass("list_div_ys");
			$("#oldpasswordInput").removeClass("text_inp_yanse");
			$("#oldpassword_list_alert_div").removeClass("right_name");
			return false;
		}
		validOldPassword(pass_inp);
	});	
	
	
	//手机验证
	function valiedateMobile(number){
		if(number.lenght <= 0 || number == ""){
			return 0;
		}else{
			if(isMobile(number)){
				return 2;
			}else{
				return 1;
			}
		}
	}

	function trimStr(str){
		var m = str.match(/^\s*(\S+(\s+\S+)*)\s*$/);  
		return (m == null) ? "" : m[1];  
	}

	function isMobile(number){
		if(! /^[0-9]{11}$/.test( number )){
			return false;
		}
		return (/^[1][3458][0-9]{9}$/.test(trimStr(number)));  
	}
	
	function isTel(number){
		if(! /^[0-9-]{12,20}$/.test( number )){
			return false;
		}
	    //"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	    //return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(this.Trim()));
	    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(trimStr(number)));
	}	
	//身份验证
	function isIdCard(number){
		if(! /(^\d{15}$)|(^\d{17}([0-9]|[x,X])$)/.test(trimStr(number))){
			$("#idCard_list_alert_div").addClass("error_num");
			$("#idCard_list_alert_div").html("请输入正确的身份证号");
			$("#idCard_list_div").removeClass("list_div_ys");
			$("#idCard_list_alert_div").removeClass("right_name");
			$("#idCardInput").removeClass("text_inp_yanse");
			isIdCardOk = false;
			return false;
		}
		validIdCardUnique(number);		
	}
	
	//邮件格式检查
	function checkEmailAllIn(email)
	{
	   // var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	    var reg_email = /^([\.a-zA-Z0-9_-]){1,}@([a-zA-Z0-9_-]){2,}(\.([a-zA-Z0-9]){2,}){1,4}$/;
	    if(email.length <= 0){
			return 0;
	    }else{
	    	if(!reg_email.test(email) || email.length<3 || email.length>50){
				return 1;
		    }else{
				return 2;
		    }
	    }
	}	
	
	//验证码式检查
	function checkValidCodeAllIn(validCode){
	  	if(validCode.length <= 0){	
	  		$("#code_list_alert_div").addClass("error_num");
			$("#code_list_alert_div").html("请输入正确的验证码。");
			$("#code_list_div").removeClass("list_div_ys");
			$("#code_list_alert_div").removeClass("right_name");
			$("#codeInput").removeClass("text_inp_yanse");
			isCodeOk = false;
			return false;
	  	}
	  	if(validCode.length > 4 || (validCode.length<4 && validCode.length>0) ){
	  		$("#code_list_alert_div").addClass("error_num");
			$("#code_list_alert_div").html("请输入正确的验证码。");
			$("#code_list_div").removeClass("list_div_ys");
			$("#codeInput").removeClass("text_inp_yanse");
			$("#code_list_alert_div").removeClass("right_name");
			isCodeOk = false;
			return false;
	  	}
	  	validCodeEqual(validCode);
	}
	
	
});

//测试旧密码是否正确
function validOldPassword(str){
	
			var url = window.BASESITE + '/user/user!checkOldPasswordAble.action?regForm.oldpassword='+str;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#oldpassword_list_alert_div").removeClass("error_num");	
					$("#oldpassword_list_div").removeClass("list_div_ys");
					$("#oldpasswordInput").removeClass("text_inp_yanse");
					$("#oldpassword_list_alert_div").addClass("right_name");
					$("#oldpassword_list_alert_div").html("");
					isOldPassOk = true;
				} else {		
					$("#oldpassword_list_div").removeClass("list_div_ys");
					$("#oldpasswordInput").removeClass("text_inp_yanse");
					$("#oldpassword_list_alert_div").removeClass("right_name");
					$("#oldpassword_list_alert_div").addClass("error_num");
					$("#oldpassword_list_alert_div").html("该密码有误!");
					isOldPassOk = false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
}
//测试电话号码是否唯一
function validPhoneUnique(str){
			var url = window.BASESITE + '/user/user!checkPhoneRegAble.action?phoneInput='+str;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#phone_list_alert_div").removeClass("error_num");	
					$("#phone_list_div").removeClass("list_div_ys");
					$("#phoneInput").removeClass("text_inp_yanse");
					$("#phone_list_alert_div").addClass("right_name");
					$("#phone_list_alert_div").html("");
					isPhonelOk = true;
				} else {		
					$("#phone_list_div").removeClass("list_div_ys");
					$("#phoneInput").removeClass("text_inp_yanse");
					$("#phone_list_alert_div").removeClass("right_name");
					$("#phone_list_alert_div").addClass("error_num");
					$("#phone_list_alert_div").html("该电话号码已被注册验证!");
					isPhoneOk = false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
}

//测试身份证号是否唯一
function validIdCardUnique(str){
			var url = window.BASESITE + '/user/user!checkIdCardRegAble.action?idCardInput='+str;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#idCard_list_alert_div").removeClass("error_num");	
					$("#idCard_list_div").removeClass("list_div_ys");
					$("#idCardInput").removeClass("text_inp_yanse");
					$("#idCard_list_alert_div").addClass("right_name");
					$("#idCard_list_alert_div").html("");
					isIdCardOk = true;
				} else {		
					$("#idCard_list_div").removeClass("list_div_ys");
					$("#idCardInput").removeClass("text_inp_yanse");
					$("#idCard_list_alert_div").removeClass("right_name");
					$("#idCard_list_alert_div").addClass("error_num");
					$("#idCard_list_alert_div").html("该身份证号已被注册!");
					isIdCardOk = false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
}

//测试邮箱是否唯一
function validEmailUnique(str){
			var url = window.BASESITE + '/user/user!checkEmailRegAble.action?emailInput='+str;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#email_list_alert_div").removeClass("error_num");
					$("#email_list_alert_div").addClass("right_name");
					$("#email_list_div").removeClass("list_div_ys");
					$("#emailInput").removeClass("text_inp_yanse");
					$("#email_list_alert_div").html("");
					isEmailOk = true;
				} else {		
					$("#email_list_div").removeClass("list_div_ys");
					$("#emailInput").removeClass("text_inp_yanse");
					$("#email_list_alert_div").removeClass("right_name");
					$("#email_list_alert_div").addClass("error_num");
					$("#email_list_alert_div").html("该Email已被注册!");
					isEmailOk = false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
}

//测试用户名是否唯一
function validUnique(str){
	
			var url = window.BASESITE + '/user/user!checkUserRegAble.action?regForm.userName='+encodeURI(str);
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#userName_list_alert_div").removeClass("error_num");	
					$("#userName_list_div").removeClass("list_div_ys");
					$("#userNameInput").removeClass("text_inp_yanse");
					$("#userName_list_alert_div").addClass("right_name");	
					$("#userName_list_alert_div").html("");
					isNameOk = true;
				} else {
					$("#userName_list_div").removeClass("list_div_ys");
					$("#userNameInput").removeClass("text_inp_yanse");
					$("#userName_list_alert_div").removeClass("right_name");
					$("#userName_list_alert_div").addClass("error_num");
					if(jsonObj.msg=="forbitten"){
						$("#userName_list_alert_div").html("用户名包含本站关键字。请修改!");
					}else{
						$("#userName_list_alert_div").html("该用户名已被注册!");
					}
					isNameOk = false;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
}
//判断验证码是否相等
function validCodeEqual(str){
	var url = window.BASESITE + '/user/user!checkCodeRegAble.action?code='+str;
	$.ajax({
	type : 'GET',
	cache : false,
	dataType : 'json',
	url : url,
	success : function(jsonObj) {
		if (jsonObj.success == true) {
			$("#code_list_alert_div").removeClass("error_num");	
			$("#code_list_alert_div").removeClass("list_div_ys");
			$("#code_list_alert_div").addClass("right_name");
			$("#codeInput").removeClass("text_inp_yanse");
			$("#code_list_alert_div").html("");
		  	isCodeOk = true;		
		} else {		
			$("#code_list_div").removeClass("list_div_ys");
			$("#codeInput").addClass("text_inp_yanse");
			$("#code_list_alert_div").removeClass("right_name");
			$("#code_list_alert_div").addClass("error_num");
			$("#code_list_alert_div").html("输入验证码有误!");
			isCodeOk = false;
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
	}
});	
}


function ajaxSubmit(){
	var pawd = $("#passwordInput").val();
	var confrimPawd = $("#confirmPswInput").val();
	if(!isNameOk){

		$("#userName_list_alert_div").addClass("error_num");
		$("#userName_list_alert_div").html("请按提示输入正确的用户名");	
		$("#userName_list_div").removeClass("list_div_ys");
		$("#userNameInput").removeClass("text_inp_yanse");
		$("#userName_list_alert_div").removeClass("right_name");
		return false;
	}else if(pawd.length < 6 || pawd.length >15){

		isPassOk = false;	
		$("#password_list_alert_div").addClass("error_num");
		$("#password_list_alert_div").html("请输入6-15位密码");	
		$("#password_list_div").removeClass("list_div_ys");
		$("#passwordInput").removeClass("text_inp_yanse");
		$("#password_list_alert_div").removeClass("right_name");
		return false;
	}else if(pawd!=confrimPawd){

		$("#password_list_alert_div").addClass("error_num");
		$("#password_list_alert_div").html("请输入6-15位密码");	
		$("#password_list_div").removeClass("list_div_ys");
		$("#passwordInput").removeClass("text_inp_yanse");
		$("#password_list_alert_div").removeClass("right_name");
		
		$("#confirm_password_list_alert_div").addClass("error_num");
		$("#confirm_password_list_div").removeClass("list_div_ys");
		$("#confirmPswInput").removeClass("text_inp_yanse");
		$("#confirm_password_list_alert_div").removeClass("right_name");
		$("#confirm_password_list_alert_div").html("您两次输入的密码不一致");
		isQpassOk = false;
		isPassOk = false;
		return false;
	}else if(!isPassOk){

		$("#password_list_alert_div").addClass("error_num");
		$("#password_list_alert_div").html("密码不合法，请检查！");	
		$("#password_list_div").removeClass("list_div_ys");
		$("#passwordInput").removeClass("text_inp_yanse");
		$("#password_list_alert_div").removeClass("right_name");
		return false;
	}else if(!isQpassOk){

		$("#confirm_password_list_alert_div").addClass("error_num");
		$("#confirm_password_list_div").removeClass("list_div_ys");
		$("#confirmPswInput").removeClass("text_inp_yanse");
		$("#confirm_password_list_alert_div").removeClass("right_name");
		$("#confirm_password_list_alert_div").html("您两次输入的密码不一致");
		return false;
	}else if(!isPhoneOk){

		$("#phone_list_alert_div").addClass("error_num");
		$("#phone_list_alert_div").html("电话号码不合法，请检查！");	
		$("#phone_list_div").removeClass("list_div_ys");
		$("#phoneInput").removeClass("text_inp_yanse");
		$("#phone_list_alert_div").removeClass("right_name");
		return false;		
	}else if(!isEmailOk){

		$("#email_list_alert_div").addClass("error_num");
		$("#email_list_alert_div").html("请输入正确格式的邮箱");	
		$("#email_list_div").removeClass("list_div_ys");
		$("#emailInput").removeClass("text_inp_yanse");
		$("#email_list_alert_div").removeClass("right_name");
		return false;	
	}else if(!isCodeOk){

		$("#code_list_alert_div").addClass("error_num");
		$("#code_list_alert_div").html("请输入正确的验证码。");
		$("#code_list_div").removeClass("list_div_ys");
		$("#codeInput").removeClass("text_inp_yanse");
		$("#code_list_alert_div").removeClass("right_name");
		return false;
	}else{

		
	  if(pawd==confrimPawd){

			$("#confirm_password_list_alert_div").removeClass("error_num");	
			$("#confirm_password_list_div").removeClass("list_div_ys");
			$("#confirmPswInput").removeClass("text_inp_yanse");
			$("#confirm_password_list_alert_div").addClass("right_name");
			$("#confirm_password_list_alert_div").html("");
			
			$("#password_list_alert_div").removeClass("error_num");	
			$("#password_list_div").removeClass("list_div_ys");
			$("#passwordInput").removeClass("text_inp_yanse");
			$("#password_list_alert_div").addClass("right_name");
			$("#password_list_alert_div").html("");
			isQpassOk = true;
			isPassOk = true;
			
		}

		var param = [];//提交参数
		param.push("regForm.userName=" + encodeURI($("#userNameInput").val()));
		param.push("&regForm.password=" + $("#passwordInput").val());
		param.push("&regForm.phoneNumber=" + $("#phoneInput").val());
		param.push("&regForm.email=" + $("#emailInput").val());
		param.push("&regForm.confirmPassword=" + $("#confirmPswInput").val());
		param.push("&code="+ $("#codeInput").val());
		var url = window.BASESITE + '/user/user!create.action?'+param.join("");
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			beforeSend : function() {

				$("#span_register").show();
				$("#registerbtn").attr("disabled",true);
			},
			success : function(jsonObj) {
				if(jsonObj.success==true){

					$("#span_register").hide();
					$("#registerbtn").removeAttr("disabled");
					var redirectUrl = window.BASESITE + "/user/user!"+jsonObj.redirectUrl+".action";
					window.location.href=redirectUrl;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#registerbtn").removeAttr("disabled");
				$("#span_register").html("注册失败");
			}
		});	
	}
};	







function ajaxUpdate(){	
	
	if($("#chkPhone").val()!=1){
		if(!isPhoneOk){
			$("#phone_list_alert_div").addClass("error_num");
			$("#phone_list_alert_div").html("您输入的电话号码有误");	
			$("#phone_list_div").removeClass("list_div_ys");
			$("#phoneInput").removeClass("text_inp_yanse");
			$("#phone_list_alert_div").removeClass("right_name");
			return false;
		}	
	}
	if($("#chkEmail").val()!=1){
		if(!isEmailOk){
			$("#email_list_alert_div").addClass("error_num");
			$("#email_list_alert_div").html("请输入正确格式的邮箱。示例：caipiao@sina.cn,推荐使用 <a id='mail_tuijian' href='http://login.sina.com.cn/signup/signup.php?entry=freemail' target='_blank'>新浪邮箱</a>");	
			$("#email_list_div").removeClass("list_div_ys");
			$("#emailInput").removeClass("text_inp_yanse");
			$("#email_list_alert_div").removeClass("right_name");
			return false;		
		}			
	}
	
	if(!isIdCardOk){	
		$("#idCard_list_alert_div").addClass("error_num");
		$("#idCard_list_alert_div").html("您输入的身份证有误，请正确输入");	
		$("#idCard_list_div").removeClass("list_div_ys");
		$("#idCardInput").removeClass("text_inp_yanse");
		$("#idCard_list_alert_div").removeClass("right_name");
		return false;		
	}
	if(!isRealNameOk){
		$("#realName_list_alert_div").addClass("error_num");
		$("#realName_list_alert_div").html("您输入的真实姓名有误，请正确输入");	
		$("#realName_list_div").removeClass("list_div_ys");
		$("#realNameInput").removeClass("text_inp_yanse");
		$("#realName_list_alert_div").removeClass("right_name");
		return false;		
	}else{
		var param = [];//提交参数
		param.push("regForm.userName=" + $("#userNameInput").val());
		param.push("&regForm.password=" + $("#passwordInput").val());
		param.push("&regForm.phoneNumber=" + $("#phoneInput").val());
		param.push("&regForm.email=" + $("#emailInput").val());
		param.push("&regForm.idCard=" + $("#idCardInput").val());
		param.push("&regForm.realName=" + encodeURI($("#realNameInput").val()));
		var url = window.BASESITE + '/user/user!updateInfo.action?'+param.join("");
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				alert('资料修改成功');
				window.location.reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});	
	}
};	



function updatePasswd(){
	if(!isOldPassOk){
		$("#oldpassword_list_div").removeClass("list_div_ys");
		$("#oldpasswordInput").removeClass("text_inp_yanse");
		$("#oldpassword_list_alert_div").removeClass("right_name");
		$("#oldpassword_list_alert_div").addClass("error_num");
		$("#oldpassword_list_alert_div").html("该密码有误!");
		return false;
	}else if(!isPassOk){
		$("#password_list_alert_div").addClass("error_num");
		$("#password_list_alert_div").html("请输入6-15位密码");	
		$("#password_list_div").removeClass("list_div_ys");
		$("#passwordInput").removeClass("text_inp_yanse");
		$("#password_list_alert_div").removeClass("right_name");
		return false;
	}else if(!isQpassOk){
		$("#confirm_password_list_alert_div").addClass("error_num");
		$("#confirm_password_list_div").removeClass("list_div_ys");
		$("#confirmPswInput").removeClass("text_inp_yanse");
		$("#confirm_password_list_alert_div").removeClass("right_name");
		$("#confirm_password_list_alert_div").html("您两次输入的密码不一致");
		return false;
	}else{
		var param = [];//提交参数
		param.push("regForm.password=" + $("#passwordInput").val());
		param.push("&regForm.oldpassword=" + $("#oldpasswordInput").val());
		var url = window.BASESITE + '/user/user!updatePwd.action?'+param.join("");
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {		
					alert(jsonObj.msg);			
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(jsonObj.msg);
				window.location.href=jsonObj.url;
			}
		});	
	}	
};

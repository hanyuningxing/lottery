function emailVal(){
	if(jQuery.formValidator.pageIsValid()){

	if(confirm("确定要发送邮件","注意")){
		var email =$("#emailInput").val();
		var validEmail = checkEmailAllIn(email);
		if(validEmail == 2 && email != ""){
			var url = window.BASESITE + '/user/user!toEmailValid.action?regForm.email='+email;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					$("#emailVal").attr("disabled",true);
					if(confirm(jsonObj.info)){
						window.open(jsonObj.emailAddr,"邮箱登陆");
					}
					window.setTimeout(function(){
						$("#emailVal").removeAttr("disabled");
						window.clearTimeout();
					},30000);
				} else {		
					alert(jsonObj.info);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}});	
		}else{
			alert('输入的邮箱格式有误,请重新输入');
			return false;
		}
	}
	}
}


//邮件格式检查
function checkEmailAllIn(email)
{
   // var reg_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    var reg_email = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;
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
/**获取短信验证码*/
function sendSMSPwd(){
	var phoneNo =$("#phoneInput").val();
	if(phoneNo==null){
		alert("验证电话号码不能为空");
		return false;
	}
	if(jQuery.formValidator.pageIsValid()){

	if(confirm("确定要用此号码发送短信","注意")){
		var validPhone = valiedateMobile(phoneNo);
		if(validPhone == 2){
			var url = window.BASESITE + '/user/user!toMsgValid.action?regForm.phoneNumber='+phoneNo;
			$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : url,
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					alert('短信发送成功,请稍等!发送短信的时间间隔为3分钟!');
					$("#getMsg").removeAttr("onclick");
					$("#getMsg").html("");
					window.setTimeout(function(){
							$("#getMsg").html("<a href=\"javascript:void(0)\" onclick=\"sendSMSPwd()\" id=\"getMsg\">点击获取短信验证码</a>");
							window.clearTimeout();
					},30000);
				} else {	
					alert('短信发送失败');
				} 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}});	
		}else{
			alert('输入的号码有误,请重新输入');
			return false;
		}
	}
	}
	
}

/**验证短信内容*/
function msgVal(){
	var code =$("#msgCodeInput").val().trim();
	var userName = $("#userNameInput").val();
	if(code != ""){
		var url = window.BASESITE + '/user/user!msgValid.action?emailValForm.code='+code;
		$.ajax({
		type : 'GET',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
				alert(jsonObj.msg);
				window.location.reload();
			} else {		
				alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}});	
	}else{
		alert('请输入短信验证码！');
		return false;
	}
}


//手机和电话号码验证
function valiedateMobile(number){
	if(number.lenght <= 0 || number == ""){
		return 0;
	}else{
		if( isMobile(number)){
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
	return (/^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(trimStr(number)));  
}

function isTel(number){
	if(! /^[0-9-]{12,20}$/.test( number )){
		return false;
	}
    //"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
    //return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(this.Trim()));
    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(trimStr(number)));
}	





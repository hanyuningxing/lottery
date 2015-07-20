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
//手机绑定
$(function(){   
	createDialog('#msgValidateDialog', '手机验证', 580);
	createDialog('#mobileWriteDialog', '手机填写', 550);
	
	$("#msgValidate").click(function(){
		checkMobileExist();
	});
			
});
//查询手机是否已经验证，若未验证则弹出手机验证对话框
function checkMobileExist(){
	var mobile = "";			
	requestByPost(window.BASESITE + '/user/user!checkMobileExist.action', {}, toMsgValidate);
}


function toMsgValidate(data) {
	if(!data.exist) {
		writeMobileAndValidate();
	} else {
		setCommonResult(data.msg,!data.exist);
	}
}
//输入手机号码进行验证
function writeMobileAndValidate() {	
	$("#mobileWriteDialog").dialog('open');
	$('#mobileWriteForm').submit(function() {
		mobile  = $('#mobileWriteForm [name="mobile"]').val();
		Cookies.set({name: 'mobile', value: mobile});
		
		$("#mobileWriteDialog").dialog('close');
		$("#msgValidateDialog").dialog('open');
		document.getElementById("content").innerHTML = '你的手机号码为' + mobile + '，点击获取验证码进行验证';
	
		$('#msgValidateform').off('submit').submit(function() {
			var msgCode = $('#msgValidateform [name="yanzhengma"]').val();
			
			if(strIsNull(msgCode)) {
				setCommonResult('验证码不能为空！', false);
				return false;
			}
			
			requestByPost(window.BASESITE + '/user/user!msgValid.action', {msgCode: msgCode, mobile: mobile}, msgValidate);				
			return false; 
		 });
		
		$("#getMsgCode").off('click').click(function(){
			requestByPost(window.BASESITE + '/user/user!toMsgValid.action', {mobile: mobile}, sendMsgCode);				

		});
		return false; 
	});	
}

//用于重新发送验证码
function sendMsgCode(data) {
	if(data.success) {		
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
}
	

//当输入验证码点击返回时，调用此方法
function reInputMobile() {
	var mobile = Cookies.get('mobile');
	$('#msgValidateDialog').dialog('close');
	$("#mobileWriteDialog").dialog('open');
	document.getElementById("mobile").value = mobile;
}
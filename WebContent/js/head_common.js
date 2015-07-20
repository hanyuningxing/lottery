   //发送post请求
	function requestByPost(action, params, callback) {
	    $.post(action,params,function(data){
	        callback(data);
	    },'json');
	}


   //判断字符串是否相同
   	function isTheSameString(str1, str2) {
   		if(str1.trim() == str2.trim()) {
   			return true;
   		} else {
   			return false;
   		}
   	}
   	
   	//判断字符串是否为空
   	function strIsNull(str) {
   		if(str.trim()=="") {
   			return true;
   		} else {
   			return false;
   		}
   	}
   	//set返回信息
   	function setCommonResult(msg,success) {
		$("#commonResultDialogData").empty();
		$("#commonResultDialogData").html(msg);
		if(success) {
			$("#tckico").addClass("tckico01");
   		} else {
			$("#tckico").addClass("tckico02");
   		}
		$("#commonResultDialog").dialog('open');
	//	setTimeout(function() {$("#commonResultDialog").dialog('close');}, 3000);
		
   	}
   
	//用于创建dialog
   function createDialog_header(id_, title_, width_, height_) {
		$(id_).dialog({  
			autoOpen:false,
			title:title_,
			width:width_, 
			height:height_, 
			modal:true
		});
	}
	function header_checkFormOfSetIdCard(){	
		$("#head_realName").formValidator({validatorgroup:"10",onshow:"请输入您的真实姓名",onfocus:"真实姓名由中文组成",oncorrect:"真实姓名合法"}).inputValidator({empty:{leftempty:false,rightempty:false,emptyerror:"内容两边不能空格"},onerror:"真实姓名由中文组成"})			
		.regexValidator({regexp:"[^\x00-\xff]",onerror:"真实姓名不能为空且由中文组成"});
	}
	function header_checkFormOfForgotPwd(){	
		$("#header_fp_userName").formValidator({validatorgroup:"head_pw",onshow:"请输入您的用户名",onfocus:"用户名不能为空",oncorrect:"用户名合法"}).inputValidator({empty:{leftempty:false,rightempty:false,emptyerror:"内容两边不能空格"},onerror:"用户名格式错误"})			
		.functionValidator({
		    fun:function(value,elem){
				  if(/^\d{3,12}$/.test(value)){
						return "用户名不能全为数字.";
				 }
				 if(!/^[a-zA-Z0-9\u4E00-\u9FBF_]{3,12}$/.test(value)){
				   return "用户名不合法";
				 }
				 return true;
		    	}
		});
		$("#header_fp_phone").formValidator({validatorgroup:"head_pw",onshow:"请输入手机号码",onfocus:"手机号码不能为空",oncorrect:"手机号码合法"}).inputValidator({empty:{leftempty:false,rightempty:false,emptyerror:"手机号码两边不能有空符号"},onerror:"请输入正确的手机号码"})			
		.regexValidator({regexp:"^([0-9]){11}$",onerror:"请输入11位手机号码"});
	}
	function header_setIdCardFormSubmit() {
			if($.formValidator.pageIsValid("10")) {
				        var head_realName = $('#header_setIdCardForm [name="head_realName"]').val();
						$.post(window.BASESITE + '/user/user!setRealName.action',{
								realName: head_realName
							},function(data) {
							    if(data.success){
							    	$("#header_setIdCardDialog").dialog('close');
							    	afterSetRealName(mobileValidated);				
							  
							    }else{
							    	$("#header_setIdCardDialog").dialog('close');
							        setCommonResult(data.msg,data.success);
							    }
							},"json");					
						return false;  //阻止表单提交				
			}
	}
	function header_forgotPwdSubmit() {
		if($.formValidator.pageIsValid("head_pw")) {
			        var userName = $('#header_fp_userName').val();
					var phone = $('#header_fp_phone').val();
					
					if(strIsNull(phone) || strIsNull(userName)) {
						setCommonResult('输入不能为空！', false);
						return false;
					}
					$.post(window.BASESITE + '/user/user!resetPasswordByPhone.action',{
						userName: userName,
						phone: phone
						},function(data) {
						    if(data.success){
						        $("#dialogForgotPassword").dialog('close');
								setCommonResult(data.msg,data.success);
								setTimeout(function(){
									$("#commonResultDialog").dialog('close');
									$('#header_forgotPwd [name="header_fp_userName"]').val("");
									$('#header_forgotPwd [name="header_fp_phone"]').val("");
								},2000);
						    }else{
						        setCommonResult(data.msg,data.success);
						    }
						},"json");					
					return false;  //阻止表单提交				
		}
}
	var mobileValidated = "";
	//设置身份验证
	function herder_checkIdCardAndRealNameExist(returnUrl){
			$.post(
				window.BASESITE + '/user/user!checkRealNameAndMobileIsExist.action', 
				{},
				function(data) {
					mobileValidated = data.mobileValidated;
					if(data.success) {
						$("#header_setIdCardDialog").dialog('open');
			   			 setId_returnUrl = returnUrl;
					} else {
						setId_returnUrl = returnUrl;
					    afterSetRealName(mobileValidated);
					}
			   },
			  'json'
		  );
	}
	
	function afterSetRealName(mobileValidated){
		if(mobileValidated) {
			
			if("showBindBankCardDlog" in window && showBindBankCardDlog == true) {
	    		$("#bankInfoUpdatedialog").dialog('open');	
	    		showBindBankCardDlog = false;
			} else {
				window.location = setId_returnUrl;
			}
		} else {
			setCommonResult("你还没绑定手机，请先进行绑定！", mobileValidated);
			setTimeout(function(){checkMobileExist();$("#commonResultDialog").dialog('close');}, 2000);
		}
	}
	
	var setId_returnUrl=window.BASESITE+"/";
	//设置身份验证
	function herder_checkIdCardAndRealNameExistForChongZhi(){
			$.post(
				window.BASESITE + '/user/user!checkRealNameAndMobileIsExist.action', 
				{},
				function(data) {
					mobileValidated = data.mobileValidated;
					if(data.success) {
						$("#header_setIdCardDialog").dialog('open');
					} else {
					    chongZhiAfterSetRealName(mobileValidated);
					}
			   },
			  'json'
		  );
	}
	
	function chongZhiAfterSetRealName(mobileValidated){
		if(mobileValidated) {
			beginChongZhi();
		} else {
			setCommonResult("你还没绑定手机，请进行绑定后再充值！", mobileValidated);
			setTimeout(function(){checkMobileExist();$("#commonResultDialog").dialog('close');}, 2000);
		}
	}
	
	function beginChongZhi(){
		$.post(window.BASESITE + '/user/fund!payPer.action', {					
		}, function(data) {							
			document.getElementById("chongzhi").innerHTML = data;
			$('#chongzhi').dialog('open');
		});	
	}
	
	function confirmChongZhi(){
		var bankName = "";
		var bankNames = document.getElementsByName("commTraceForm.bankName");
		for(var i = 0, size = bankNames.length; i<size; i++) {
			if(bankNames[i].checked == true) {
				bankName = bankNames[i].value;
			}
		}
		var amount = document.getElementsByName("commTraceForm.amount")[0].value;	
		$.post(window.BASESITE + '/user/fund!commTraceMarker.action', {
			'commTraceForm.bankName': bankName,
			'commTraceForm.amount': amount,
			payWay: 1
			
		}, function(data) {							
			document.getElementById("chongzhi").innerHTML = data;
			setTimeout(function(){
				if(document.getElementById("error_msg"))
					document.getElementById("error_msg").innerHTML = "";
			}, 3000);
			
		});	
	}
	
	function toPay(){
		$('#chongzhi').dialog('close');
		$('#payPer').dialog('open');
	}
	
	//短信验证后执行此方法
	function msgValidate(data) {
			document.getElementById('yanzhengma').value="";
			if(data.success) {
				$("#msgValidateDialog").dialog('close');						
				setCommonResult(data.msg,data.success);
				setTimeout(function(){
					$("#commonResultDialog").dialog('close');
					if(setId_returnUrl!=window.BASESITE+"/"){
						window.location = setId_returnUrl;
					} else {
						beginChongZhi();
					}
					
				},1000);
			} else {
				document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
				setTimeout(
						function(){
							document.getElementById("error").innerHTML = "";
						}
				, 3000);
			}
		
	}
	
	function chkit(elm) { 
		var bank = document.getElementById(elm); 
		bank.checked = "checked"; 
		var hatImage = document.getElementById("bank_img");
		var img = "images/czym/"+bank.value+".gif";
        hatImage.setAttribute("src",img);
	} 
	function changePayWay(elm,value) { 
		var payArr = ["YEEPAY","ALIPAY"];
		for(var i=0; i<payArr.length; i++) {
			var a = document.getElementById(payArr[i]+"_a");
			var div = document.getElementById(payArr[i]+"_bank_div");
			if(elm==payArr[i]){
				a.className = "now";
				div.style.display="";
			}else{
				a.className = "";
				div.style.display="none";
			}
		}
		var commTraceMarker_form = document.getElementById("commTraceMarker_form");
		var payWay = commTraceMarker_form.elements['payWay'];
		payWay.value=value;
		chkit('ICBC');
	} 
	

	function tradeQuery(){
	   var payWay =$("#payWay").val().trim();
	   if(0==payWay){
	      yeepaySingleTradeQuery();
	   }else if(1==payWay){
	      alipaySingleTradeQuery();
	   }
	}
	function alipaySingleTradeQuery(){
				var customOrderNo =$("#customOrderNo").val().trim();
				
				if(customOrderNo == ""){
					alert('订单号不能为空');
					return false;
				}
				var url = window.BASESITE + '/user/fund!alipaySingleTradeQuery.action?orderNo='+customOrderNo;
				$.ajax({
				type : 'get',
				cache : false,
				dataType : 'json',
				url : url,
				success : function(jsonObj) {
					if (jsonObj.success == true) {
						alert(jsonObj.msg);
						window.location.href = window.BASESITE + '/user/fund!list.action';
					} else {		
						alert(jsonObj.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("网络不通");
				}});	
			}
	     function yeepaySingleTradeQuery(){
				var customOrderNo =$("#customOrderNo").val().trim();
				
				if(customOrderNo == ""){
					alert('订单号不能为空');
					return false;
				}
				var url = window.BASESITE + '/user/fund!yeepaySingleTradeQuery.action?orderNo='+customOrderNo;
				$.ajax({
				type : 'get',
				cache : false,
				dataType : 'json',
				url : url,
				success : function(jsonObj) {
					if (jsonObj.success == true) {
						alert(jsonObj.msg);
						window.location.href = window.BASESITE + '/user/fund!list.action';
					} else {		
						alert(jsonObj.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("网络不通");
				}});	
			}
	
	var setId_returnUrl=window.BASESITE+"/";
	$(function(){
		$('#userLoginForm').submit(function() {
		     var userName = $('#userLoginForm [name="userName"]').val();
			 var password = $('#userLoginForm [name="password"]').val();
			 
			  if(userName==null || (userName.trim && userName.trim()=="")){		 	
			 	
			 	setCommonResult('用户名不能为空或全为空格!', false);
			 	return false; 
			 
			 }  else if(password==null || (password.trim && password.trim()=="")){
			 	
			 	setCommonResult('登录密码不能为空或全为空格!', false);
			 	return false; 
			 }
			
			 $.post(window.BASESITE+'/user/user!login.action', {
	    		 userName: userName,
	    		 password: password
			 }, function(data) {     		 
		     		if(data && data.success) { 
		     			$SSO.forum_login(data);		
		     			$SSO.refresh_login_info();	     			
					} else {
						if(data.need_captcha == true){
							document.getElementById("yanzhengma_quick").style.display = "";
							$('#userquickLogin').dialog('open'); 
						}						
						var msg = getCommonMsg(data);
						setCommonResult(msg,false);
					}	
			}, 'json');
			 
		     return false;  //阻止表单提交
		});
		
		$(document).ready(function(){
			$.formValidator.initConfig({validatorgroup:"10",formid:"header_setIdCardForm",onerror:function(msg){ },onsuccess:function(){ return true;}});
			header_checkFormOfSetIdCard();
			$.formValidator.initConfig({validatorgroup:"head_pw",formid:"header_forgotPwd",onerror:function(msg){ },onsuccess:function(){ return true;}});
			
			header_checkFormOfForgotPwd();
		    createDialog_header('#commonResultDialog', '提示信息', 350, 219);
		    createDialog_header('#header_setIdCardDialog','设置身份认证信息', 568);
		    createDialog_header('#dialogForgotPassword','忘记密码', 500);
		    createDialog_header('#chongzhi', '用户充值', 790);
		    createDialog_header('#payPer', '付款', 380);
		    
		});			
		$("#header_payPer_a").click(function(){
			$SSO.login_auth(
					herder_checkIdCardAndRealNameExistForChongZhi(window.BASESITE+'/user/fund!payPer.action')
			);
		    return false;   
		});
		
		if(document.getElementById("confirm_tz_payPer_a")){	
			
			$("#confirm_tz_payPer_a").click(function(){
				$SSO.login_auth(
						herder_checkIdCardAndRealNameExistForChongZhi(window.BASESITE+'/user/fund!payPer.action')
				);
			    return false;   
			});
		}
		
		if(document.getElementById("confirm_tz_payPer_a_")){	
			
			$("#confirm_tz_payPer_a_").click(function(){
				$SSO.login_auth(
						herder_checkIdCardAndRealNameExistForChongZhi(window.BASESITE+'/user/fund!payPer.action')
				);
			    return false;   
			});
		}
		
		$("#header_payPer_a_1").click(function(){
			$SSO.login_auth(
					herder_checkIdCardAndRealNameExistForChongZhi(window.BASESITE+'/user/fund!payPer.action')
			);
		    return false;   
		});
		$("#header_drawingPer_a_1").click(function(){
			$SSO.login_auth(
				herder_checkIdCardAndRealNameExist(window.BASESITE+'/user/fund!drawingPer.action')
			);
		    return false;   
		});
		$("#header_forgot_pwd").click(function(){
			$("#dialogForgotPassword").dialog('open');
		    return false;   
		});
		$("#header_forgot_pwd_quick").click(function(){
			$("#dialogForgotPassword").dialog('open');
		    return false;   
		});
	});
	
	
$SSO = {
	refresh_agent_login_info : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
					document.getElementById('index_login_money').innerHTML = jsonObj.info.defaultAccountRemainMoney;
				} 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},	
	refresh_login_info : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
					document.getElementById('_s_2').innerHTML = "现金:"+jsonObj.info.defaultAccountRemainMoney+
					"&nbsp;隐藏";
					
					if(document.getElementById('login_ed_div'))document.getElementById('login_ed_div').style.display = "";
					if(document.getElementById('un_login_div'))document.getElementById('un_login_div').style.display = "none";										
					
					if(document.getElementById("common_logined")) {
						document.getElementById('common_logined').style.display = "";
						if(document.getElementById("user_logined")) document.getElementById("user_logined").style.display = "none";
						if(document.getElementById('common_unlogin')) document.getElementById('common_unlogin').style.display = "none";
						if(document.getElementById('rengou'))document.getElementById('rengou').style.display = "";
						if(document.getElementById('common_login_name')) document.getElementById('common_login_name').innerHTML = jsonObj.info.userName;
						if(document.getElementById('common_remainMoney')) document.getElementById('common_remainMoney').innerHTML ='￥' + jsonObj.info.defaultAccountRemainMoney;										
					}
				} 
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	forum_login : function(jsonObj) {
		if (jsonObj.success == true) {
			if (jsonObj.forumLoginReturn != "") {	
				var reloadUrl =jsonObj.forumLoginReturn; 						
				var jsIframe = document.createElement("iframe");
				jsIframe.style.display = "none";// 把jsIframe隐藏起来
				document.body.appendChild(jsIframe);
				with (window.frames[window.frames.length - 1]) {
					document.open();
					document.write(reloadUrl); // 执行JS代码
					document.close();
				}
				
			}
		} 					
	},	
	
	forum_logout : function(jsonObj) {		
		if (jsonObj.success == true) {			
			if (jsonObj.forumLogoutReturn != "") {	
				var reloadUrl =jsonObj.forumLogoutReturn; 						
				var jsIframe = document.createElement("iframe");
				jsIframe.style.display = "none";// 把jsIframe隐藏起来
				document.body.appendChild(jsIframe);
				
				with (window.frames[window.frames.length - 1]) {
					document.open();
					document.write(reloadUrl); // 执行JS代码
					document.close();					
				}
				
			}
			
		} 					
	},	
	
	
	refresh_login_info_index : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				if(jsonObj!=null) {		
					if (jsonObj.success == true) {
						document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
						document.getElementById('_s_2').innerHTML = "现金:"+jsonObj.info.defaultAccountRemainMoney+
						"&nbsp;彩金:"+jsonObj.info.activityAccountRemainMoney+"&nbsp;隐藏";
						
						document.getElementById('login_ed_div').style.display = "";
						document.getElementById('un_login_div').style.display = "none";
						//zhuhui motify 2011-0331 增加隐藏、显示 联合登陆窗口
						document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
						document.getElementById('index_welcome_login_name').innerHTML = jsonObj.info.userName;
						document.getElementById('login_ed_byNetwork').style.display = "";
						document.getElementById('un_login_byNetwork').style.display = "none";
						//更新上次登录时间
						document.getElementById('index_welcome_lastlogin_time').innerHTML = "您上次登录时间："+jsonObj.lastlogin_time;
					
						
						
						
						
					} else {
						document.getElementById('login_ed_div').style.display = "none";
						document.getElementById('un_login_div').style.display = "";
						//zhuhui motify 2011-0331 增加隐藏、显示 联合登陆窗口
						document.getElementById('login_ed_byNetwork').style.display = "none";
						document.getElementById('un_login_byNetwork').style.display = "";
						
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	refresh_login_info_register : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	login_auth : function(call) {			
		var self = this;			
		self.loginSuccessCall = function(remainMoney) {
			if (call != null && typeof call == 'function') {
				call(remainMoney);
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					var remainMoney = jsonObj.info.defaultAccountRemainMoney;
					self.loginSuccessCall(remainMoney);
				} else {
					$('#userquickLogin').dialog('open');
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	userInfo_auth : function(call) {			
		var userSelf = this;
		userSelf.userInfoSuccessCall = function() {
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkUserInfo.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					userSelf.userInfoSuccessCall();
				} else {
					$floater({
						width : 780,
						height : 400,
						src : window.BASESITE + '/user/user!toCompleteInfo.action',
						title : '用户资料完善验证',
						fix : 'true'
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	login_auth_index : function(call) {		
		var self = this;
		self.loginSuccessCall = function() {
			self.refresh_login_info_index();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			success : function(jsonObj) {
				
				if (jsonObj.success == true) {
					self.loginSuccessCall();
				} else {
					$floater({
						width : 420,
						height : 260,
						src : window.BASESITE + '/user/user!login.action?type=quick_login',
						title : '用户登录',
						fix : 'true'
					});
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	logout_agent : function(call) {		
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!logout.action',
			success : function(jsonObj) {
				
				if (jsonObj.success == true && jsonObj.logoutNotifyUrl != null) {
					window.location.href=window.BASESITE + '/user/user!login.action';
					
				}else{
					window.location.href=window.BASESITE + '/user/user!login.action';
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	logout : function(call) {	
		var self = this;
		var logoutSuccessCall = function(jsonObj){
			self.forum_logout(jsonObj);
			//这里过0.1秒再刷新页面的原因是：要向论坛发送一段JS代码，需要时间，不然论坛不能实现同步退出
			setTimeout(function(){window.location.href = window.BASESITE + "/";}, 100);
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!logout.action',
			success : function(jsonObj) {

				if (jsonObj.success == true && jsonObj.forumLogoutReturn != null) {										
					logoutSuccessCall(jsonObj);										
				}else{ 
					
					logoutSuccessCall();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	logout_index : function(call) {		
		var self = this;
		var logoutSuccessCall = function(){
			self.refresh_login_info_index();
			//退出登录
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!logout.action',
			success : function(jsonObj) {
				
				if (jsonObj.success == true ) {
				 	var sloUrl = jsonObj.sloUrl;
				 	if(sloUrl!=undefined && sloUrl!=null){
						$.getJSON(sloUrl, {format: "json"}); //, function(data) {}
					}   	
				}
				
				logoutSuccessCall();
				
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	toPasswordByEmail : function() {
		$floater({
			width : 420,
			height : 260,
			src : window.BASESITE + '/user/user!resetPasswordByEmail.action?type=no_login',
			title : '用户登录',
			fix : 'true'
		});
	}
	
	
};
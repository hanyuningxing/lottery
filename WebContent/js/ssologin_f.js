$SSO = {
	refresh_login_info : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/football/user!checkLogin.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
					document.getElementById('login_ed_div').style.display = "";
					document.getElementById('un_login_div').style.display = "none";
				} else {
					document.getElementById('login_ed_div').style.display = "none";
					document.getElementById('un_login_div').style.display = "";
				}
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	
	
	refresh_login_info_index : function() {
		var self = this;	
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/football/user!checkLogin.action',
			success : function(jsonObj) {
				if(jsonObj!=null) {		
					if (jsonObj.success == true) {
						document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
						
						document.getElementById('login_ed_div').style.display = "";
						document.getElementById('un_login_div').style.display = "none";
						
					} else {
						document.getElementById('login_ed_div').style.display = "none";
						document.getElementById('un_login_div').style.display = "";
						
						
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
			url : window.BASESITE + '/football/user!checkLogin.action',
			success : function(jsonObj) {
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},
	login_auth : function(call) {			
		var self = this;
		self.loginSuccessCall = function() {
			self.refresh_login_info();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/football/user!checkLogin.action',
			success : function(jsonObj) {
				if (jsonObj.success == true) {
					self.loginSuccessCall();
				} else {
					$floater({
						width : 420,
						height : 260,
						src : window.BASESITE + '/football/user!login.action?type=quick_login',
						title : '用户登录',
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
			url : window.BASESITE + '/football/user!checkLogin.action',
			success : function(jsonObj) {
				
				if (jsonObj.success == true) {
					self.loginSuccessCall();
				} else {
					$floater({
						width : 420,
						height : 260,
						src : window.BASESITE + '/football/user!login.action?type=quick_login',
						title : '用户登录',
						fix : 'true'
					});
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	},

	logout : function(call) {		
		var self = this;
		var logoutSuccessCall = function(){
			self.refresh_login_info();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/football/user!logout.action',
			success : function(jsonObj) {
				
				if (jsonObj.success == true && jsonObj.logoutNotifyUrl != null) {
					logoutSuccessCall();
					// TODO 调用接口通知退出登录
					//退出刷新论坛
					
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
			url : window.BASESITE + '/football/user!logout.action',
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
			src : window.BASESITE + '/football/user!resetPasswordByEmail.action?type=no_login',
			title : '用户登录',
			fix : 'true'
		});
	},
	
	loginCallback:function(data) {
		//var forumSite = "forum.ppp.com";
		var forumSite = "forum.球客";
		if (data.result == "success") {
			if (data.dzurl != "") {
				var idx = data.dzurl.indexOf("/", 7);
				var url = "http://" + forumSite + data.dzurl.substr(idx);
				var index = url.indexOf("/api");
				var baseUrl = url.substring(url,index,0);
	
				var reloadUrl = "<script language=\"javascript\" src=\"" + url + "\" reload=\"1\" ><\/script>";		
				var jsIframe = document.createElement("iframe");
				jsIframe.style.display = "none";// 把jsIframe隐藏起来
				document.body.appendChild(jsIframe);
				with (window.frames[window.frames.length - 1]) {
					document.open();
					document.write(reloadUrl); // 执行JS代码
					document.close();
				}
				//document.body.removeChild(jsIframe);
			}
		} else {

		}
	}
	
};
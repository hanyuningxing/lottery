$SSO = {
	refresh_login_info : function() {
		var self = this;	
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			onSuccess : function(jsonObj) {
				if (jsonObj.success == true) {
					document.getElementById('index_login_name').innerHTML = jsonObj.info.userName;
					document.getElementById('_s_2').innerHTML = "现金:"+jsonObj.info.defaultAccountRemainMoney+
					"&nbsp;彩金:"+jsonObj.info.activityAccountRemainMoney+"&nbsp;隐藏";
					document.getElementById('login_ed_div').style.display = "";
					document.getElementById('un_login_div').style.display = "none";
					//同步登录论坛
					self.forum_login();
				} else {
					document.getElementById('login_ed_div').style.display = "none";
					document.getElementById('un_login_div').style.display = "";
					//同步退出论坛
					self.forum_loginout();
				}
				
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	
	
	refresh_login_info_index : function() {
		var self = this;	
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			onSuccess : function(jsonObj) {
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
						//同步登录论坛
						self.forum_login();
					} else {
						document.getElementById('login_ed_div').style.display = "none";
						document.getElementById('un_login_div').style.display = "";
						//zhuhui motify 2011-0331 增加隐藏、显示 联合登陆窗口
						document.getElementById('login_ed_byNetwork').style.display = "none";
						document.getElementById('un_login_byNetwork').style.display = "";
						self.forum_loginout();
						
					}
				}
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	refresh_login_info_register : function() {
		var self = this;	
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			onSuccess : function(jsonObj) {
				if (jsonObj.success == true) {			
					//同步登录论坛
					self.forum_login();
				} else {			
					//同步退出论坛
					self.forum_loginout();
				}
				
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	login_auth : function(call) {			
		var self = this;
		self.loginSuccessCall = function() {
			self.refresh_login_info();
			//同步登录论坛
			//self.forum_login();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			onSuccess : function(jsonObj) {
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
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	
	login_auth_index : function(call) {		
		var self = this;
		self.loginSuccessCall = function() {
			self.refresh_login_info_index();
			//同步登录论坛
			//self.forum_login();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!checkLogin.action',
			onSuccess : function(jsonObj) {
				
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
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},

	logout : function(call) {		
		var self = this;
		var logoutSuccessCall = function(){
			self.refresh_login_info();
			
			//self.forum_loginout();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!logout.action',
			onSuccess : function(jsonObj) {
				
				if (jsonObj.success == true && jsonObj.logoutNotifyUrl != null) {
					logoutSuccessCall();
					// TODO 调用接口通知退出登录
					//退出刷新论坛
					
				}else{
					logoutSuccessCall();
				}
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	
	logout_index : function(call) {		
		var self = this;
		var logoutSuccessCall = function(){
			self.refresh_login_info_index();
			//退出登录
			//self.forum_loginout();
			if (call != null && typeof call == 'function') {
				call();
			}
		};
		new Request.JSON({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : window.BASESITE + '/user/user!logout.action',
			onSuccess : function(jsonObj) {
				
				if (jsonObj.success == true ) {
				 	var sloUrl = jsonObj.sloUrl;
				 	if(sloUrl!=undefined && sloUrl!=null){
						$.getJSON(sloUrl, {format: "json"}); //, function(data) {}
					}   	
				}
				
				logoutSuccessCall();
				
				
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();
	},
	
	toPasswordByEmail : function() {
		$floater({
			width : 420,
			height : 260,
			src : window.BASESITE + '/user/user!resetPasswordByEmail.action?type=no_login',
			title : '用户登录',
			fix : 'true'
		});
	},
	//论坛登录
	forum_login : function() {
		var self = this;
		//var url = "http://www.ppp.com:8080/lottery-gz/dzc.do?action=login";
		var url = "http://www.cai310.com/forum/forum!login.action";
		new Request.JSON({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : url,
			onSuccess : function(data) {
				if(data!=null&&data.msg=="login"){
					self.loginCallback(data);
				}
						
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();	
		
	},	
	//退出登录
	forum_loginout:function() {
		var self = this;
		//var url = "http://www.ppp.com:8080/lottery-gz/dzc.do?action=loginout";
		var url = "http://www.cai310.com/forum/forum!loginout.action";	
		new Request.JSON({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : url,
			onSuccess : function(data) {
				if(data!=null&&data.msg=="loginout"){
					self.logoutCallback(data);	
				}			
			},
			onFailure : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}).send();	
	},
	
	loginCallback:function(data) {
		//var forumSite = "forum.ppp.com";
		var forumSite = "forum.hclotto";
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
	},
	//退出同时刷新论坛
	logoutCallback:function(data) {
		//var forumSite = "forum.ppp.com";
		var forumSite = "forum.hclotto";
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

$(document).ready=function(func){
	window.addEvent('domready', function() {
	    func.run();
	});
};

$.cookie=function(name,value,option){
	if(value){
		Cookie.write(name, value, option);
	}else{
		return Cookie.read(name);
	}
};

$.getJSON=function(url,data,func){
	new Request.JSON({
		type : 'post',
		cache : false,
		dataType : 'json',
		url : url,
		onSuccess : func
	}).send();	
};

var jQuery={};
jQuery.trim=function(a){
	return(a||"").replace(Wa,"");
};
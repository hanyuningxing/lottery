$().ready(function() {
		var name = $( "#name" );
		var password = $( "#password" );
		var confirm_password = $( "#confirm_password" );
		var login_name = $( "#login_name" );
		var login_password = $( "#login_password" );
		var allFields = $( [] ).add( name ).add( password ).add( confirm_password );
		var tips = $( ".validateTips" );
		function updateTips( t ) {
					tips.text( t ).addClass( "ui-my-state-highlight" );
					setTimeout(function() {
						tips.removeClass( "ui-my-state-highlight", 3000 );
					}, 1000 );
		}
		
		function checkLength( o, n, min, max ) {
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-my-state-error" );
				updateTips( "" + n + "的长度必须在" +
				min + "个字符到" + max + "个字符之间." );
				o.focus();
				return false;
			} else {
				return true;
			}
		}
		function checkRegexp( o, regexp, n ) {
			if ( !( regexp.test( o.val() ) ) ) {
				o.addClass( "ui-my-state-error" );
				updateTips( n );
				o.focus();
				return false;
			} else {
				return true;
			}
		}
		$( "#reg-form" ).dialog({
					autoOpen: false,
					height: 380,
					width: 450,
					modal: true,
					resizable:false,
					buttons: {
						"确认注册": function() {
							var bValid = true;
							allFields.removeClass( "ui-my-state-error" );
							bValid = bValid && checkLength( name, "用户名", 3, 16 );
							bValid = bValid && checkLength( password, "密码", 6, 16 );
							bValid = bValid && checkLength( confirm_password, "确认密码", 6, 16 );
							
							bValid = bValid && checkRegexp( name, /^([0-9a-zA-Z\u4e00-\u9fa5])+$/i, "用户名只能包含数字,字母和中文");
							bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "密码只可以包含数字和字母" );
							bValid = bValid && checkRegexp( confirm_password, /^([0-9a-zA-Z])+$/, "确认密码只可以包含数字和字母" );
							if ( bValid ) {
								var url = window.BASESITE + '/city/user!create.action';
								$.ajax({
									type : 'POST',
									cache : false,
									dataType : 'json',
									url : url,
									data: {
					                      userName:name.val(),
					                      password:password.val(),
					                      confirm_password:confirm_password.val()
					                },
									beforeSend : function() {
										$(".ui-my-button").eq(0).hide(); //隐藏指定的button
									},
									success : function(jsonObj) {
										if(jsonObj.success==true){
											///登录成功后
											$("#login_username_span").text(jsonObj.msg);
											$("#login_user_span").show();
											$("#unlogin_user_span").hide();
											$("#reg-form" ).dialog( "close" );
											$("#alert_dialog").dialog({
											    	  height: 100,
													  width: 250,
													  resizable:false,
											    	  open: function() {
															$("#alert_dialog_value_p").html("注册成功,欢迎您的加入!<br/>本提示在3秒后自动关闭");
															setTimeout(function() {
																	 $("#alert_dialog").dialog( "close" );
															}, 3000 );
													  }
											});
											$("#alert_dialog").dialog("open");
										}else{
											$(".ui-my-button").eq(0).show(); //隐藏指定的button
											updateTips(jsonObj.msg);
											
										}
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										$(".ui-my-button").eq(0).show(); //隐藏指定的button
									}
								});	
							}
						},
						"取消": function() {
							$( this ).dialog( "close" );
						}
					},
					close: function() {
						allFields.val( "" ).removeClass( "ui-my-state-error" );
					}
	});
	
	$( "#login-form" ).dialog({
					autoOpen: false,
					height: 380,
					width: 450,
					modal: true,
					resizable:false,
					buttons: {
						"登录": function() {
							var bValid = true;
							allFields.removeClass( "ui-my-state-error" );
							bValid = bValid && checkLength( login_name, "用户名", 3, 16 );
							bValid = bValid && checkLength( login_password, "密码", 6, 16 );
							bValid = bValid && checkRegexp( login_name, /^([0-9a-zA-Z\u4e00-\u9fa5])+$/i, "用户名只能包含数字,字母和中文");
							bValid = bValid && checkRegexp( login_password, /^([0-9a-zA-Z])+$/, "密码只可以包含数字和字母" );
							if ( bValid ) {
								var url = window.BASESITE + '/city/user!login.action';
								$.ajax({
									type : 'POST',
									cache : false,
									dataType : 'json',
									url : url,
									data: {
					                      userName:login_name.val(),
					                      password:login_password.val()
					                },
									beforeSend : function() {
										$(".ui-my-button").eq(0).hide(); //隐藏指定的button
									},
									success : function(jsonObj) {
										if(jsonObj.success==true){
											///登录成功后
											$("#login_username_span").text(jsonObj.msg);
											$("#login_user_span").show();
											$("#unlogin_user_span").hide();
											$("#login-form" ).dialog( "close" );
											$("#alert_dialog").dialog({
											    	  height: 100,
													  width: 250,
													  resizable:false,
											    	  open: function() {
															$("#alert_dialog_value_p").html("登录成功,欢迎您的到来!<br/>本提示在3秒后自动关闭");
															setTimeout(function() {
																	 $("#alert_dialog").dialog( "close" );
															}, 3000 );
													  }
											});
											$("#alert_dialog").dialog("open");
										}else{
											$(".ui-my-button").eq(0).show(); //隐藏指定的button
											updateTips(jsonObj.msg);
											
										}
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										$(".ui-my-button").eq(0).show(); //隐藏指定的button
									}
								});	
							}
						},
						"取消": function() {
							$( this ).dialog( "close" );
						}
					},
					close: function() {
						allFields.val( "" ).removeClass( "ui-my-state-error" );
					}
	});
	
	$("#create-user" )
			.click(function() {
				$( "#reg-form" ).dialog( "open" );
	});
	$("#login-user" )
			.click(function() {
				$( "#login-form" ).dialog( "open" );
	});
	$("#logout-user" )
			.click(function() {
				               var url = window.BASESITE + '/city/user!logout.action';
								$.ajax({
									type : 'POST',
									cache : false,
									dataType : 'json',
									url : url,
									success : function(jsonObj) {
										if(jsonObj.success==true){
											///登录成功后
											$("#login_username_span").text("");
											$("#login_user_span").hide();
											$("#unlogin_user_span").show();
											$("#login-form" ).dialog( "close" );
											$("#alert_dialog").dialog({
											    	  height: 100,
													  width: 250,
													  resizable:false,
											    	  open: function() {
															$("#alert_dialog_value_p").html("退出登录成功!<br/>本提示在3秒后自动关闭");
															setTimeout(function() {
																	 $("#alert_dialog").dialog( "close" );
															}, 3000 );
													  }
											});
											$("#alert_dialog").dialog("open")
										}else{
											$("#alert_dialog").dialog({
											    	  height: 100,
													  width: 250,
													  resizable:false,
											    	  open: function() {
															$("#alert_dialog_value_p").html("退出登录失败,请稍后再试!<br/>本提示在3秒后自动关闭");
															setTimeout(function() {
																	 $("#alert_dialog").dialog( "close" );
															}, 3000 );
													  }
											});
											
										}
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
									}
								});	
				
	});
	
	$("#info-user" )
			.click(function() {
				               var url = window.BASESITE + '/city/user!userManeger.action';
								$.ajax({
									type : 'get',
									cache : false,
									url : url,
									success : function(jsonObj) {
										    $("#display_dialog").dialog({
												      title:"个人中心",
											    	  height: 800,
													  width: 820,
													  resizable:true,
											    	  open: function() {
															$("#extra-data-container").html(jsonObj);
													  }
											});
											 $("#display_dialog").dialog( "open" );
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
									}
								});	
				
	});
	$("#alert_dialog").dialog({
		    autoOpen: false,
			height: 100,
			width: 250,
			resizable:false
	});
	$("#display_dialog").dialog({
		    autoOpen: false,
			height: 850,
			width: 750,
			resizable:false
	});
	
});
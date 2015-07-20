<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.webName}-注册页面</title>
<meta name="decorator" content="trade" />
<meta name="menu" content="index" />
<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/web_hyzx.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/index_content.css"/>" rel="stylesheet" type="text/css" />
<link href="css/cyyzc.css" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value="/js/jquery/jquery.cookie.js"/>"> </script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>

<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript"> 
$(document).ready(function(){

		$.formValidator.initConfig({formid:"userCreateForm",onerror:function(msg){ },onsuccess:function(){ return true;}});
		
		
		checkForm();
		
	    //保存
		$("#userCreateFormSubmit").click(function(){
				$("#span_register").show();
				$("#userCreateFormSubmit").attr("disabled",true);
				if(jQuery.formValidator.pageIsValid()){
					$("#userCreateForm").submit();
				} else {
				    $("#span_register").hide();
					$("#userCreateFormSubmit").removeAttr("disabled");
				}
		});
		
});

function checkForm(){
		$("#userName").formValidator({onshow:"请输入用户名",onfocus:"用户名4-12个字符,不能全为数字",oncorrect:"该用户名可以注册"}).inputValidator({min:4,max:24,onerror:"你输入的用户名非法,请确认"})
		.functionValidator({
		    fun:function(value,elem){
				  if(/^\d{4,12}$/.test(value)){
						return "用户名不能全为数字.";
				 }
				 if(!/^[a-zA-Z0-9\u4E00-\u9FBF_]{4,12}$/.test(value)){
				   return "用户名4-12个字符,不允许包含逗号、分号等";
				 }
				 
			     if(value.indexOf('客服')!=-1||value.indexOf('网管')!=-1||value.indexOf('管理员')!=-1||
					value.indexOf('system')!=-1||value.indexOf('administrator')!=-1||value.indexOf('webmaster')!=-1||value.indexOf('master')!=-1){
			      return "用户名不能包含敏感词.";
			     }
			     var mesg = '';
				 $.ajax({
					type : "post",
					url : window.BASESITE + '/user/user!checkUserRegAble.action',
					data:"regForm.userName=" + encodeURI(value),
					async: false,
					error: function(){alert("服务器没有返回数据,可能服务器忙，请重试");},
					success : function(data){
						mesg = data.success;
					}
				});
				if (mesg) {
				  	return true;		
				} else {
			    return "该用户名已被注册!请您再换一个";
			    }
			    return true;
			 }
		});
		

		
	    $("#captcha").formValidator({onshow:"请输入验证码",onfocus:"验证码不能为空",oncorrect:"验证码合法",onerror:"验证码不能为空,请确认"})
	    .functionValidator({
    			fun:function(value, element) {
    					if(value!=""){
	    					var mesg = '';
							$.ajax({
								type : "post",
	        					url : window.BASESITE + '/user/user!checkCodeRegAble.action',
	        					data:"captcha=" + value,
	        					async: false,
	        					error: function(){alert("服务器没有返回数据,可能服务器忙，请重试");},
	        					success : function(data){
	        						mesg = data.success;
	        					}
							});
							if (mesg) {
							  	return true;		
							}
						} else {
					    return "请输入验证码";
					    }
    			}
    		});
    }
	   
	
function openWind(url){
	var top=(document.body.clientHeight-420)/2;
	var left=(document.body.clientWidth-520)/2;
	window.open(url,'window', 'height=420, width=520, toolbar =no, menubar=no, scrollbars=yes, resizable=no,top='+top+',left='+left+', location=no, status=no');
}
</SCRIPT>
<body>
 <!--左边开始-->
	<div id="center_main">
		<!--left-->
		 <div class="cleft">
	        <#assign left_menu = 'register'/>
	      <#include "left.ftl" />
  	     </div>	 
	<div id="cright">
	   <div class="crighttop">
	     <div class="list_div">
	        <a id="i_link" href="<@c.url value="/user/user!reg.action?i=${loginUser.id}" />"></a>
	     	推广地址：<input id="i_link_input" type="text" size="65"  value=""/>
	     	<script type="text/javascript">document.getElementById("i_link_input").value=document.getElementById('i_link').href;</script>
	    	<input onclick="copyToClipboard(document.getElementById('i_link_input').value);" type="button" value="复制地址"/>
	     </div>
	     <form action="<@c.url value='/user/user!create.action' />" method="post" id="userCreateForm">
	     <input type="hidden" name="request_token" value="${datetime().millis}" />
	     <input type="hidden" name="register_type" value="0" />
	     <#include "/common/message.ftl" />
	        <div class="list_div">
			      <font>用户名：</font>
			      <div><input class=text_inp type=text id="userName" name="regForm.userName"></div>
			      <div id="userNameTip" style="width:250px"></div>	
			  </div>
			  <div class="list_div" id="password_list_div">
			      <font>登录密码：</font>
			      <div><font color="red">默认是123456</font></div>
			      <div id="passwordTip" style="width:250px"></div>	
			  </div>
			  <div class="list_div">
			      <font>返点：</font>
			      <div>
			          <select name="regForm.rebate1">
				           <#list 0..20 as a>
				              <option value="${a}">${a}</option>
				           </#list>
				      </select>
				               点
				      <select name="regForm.rebate2">
				           <#list 0..9 as a>
				              <option value="${a}">${a}</option>
				           </#list>
				      </select>
			      </div>
			  </div>
	     
	     
	     
	      <div class="list_div" id="captcha_list_div">
		      <font>验证码：</font>
		       <div><input class=text_inp_short type=text id="captcha" name="captcha"></div>
			  <div><img class="act_list_div" src="${base}/vcode.jpg" id="RIC"/></div>
			  <div id="captchaTip" style="width:85px"></div>
			  <div class="cyy_hyz">看不清？<A href="#" onclick="javascript:document.getElementById('RIC').src='${base}/vcode.jpg?t'+new Date().getTime();">换一张</A></div>		
			  <div class="cznr_wzts" id="captcha_list_alert_div"></div>  		  
		  </div>
		  <div align="center"><span id="span_register" style="display:none;">正在提交注册信息，请稍等...</span></div>
		  <div class=list_div>
		  <INPUT type="image" id="userCreateFormSubmit" src="images/cyy_anniu.gif" value="同意以下协议，提交注册"/>
		  </div>
		   </form>
	   </div>
		
	   </div>
	
	<#include "/common/footer.ftl" />
	<script type="text/javascript">
       var redirectUrl = document.referrer;
       Cookies.set({name:"redirectUrl",value:redirectUrl,path: "/"}); 
       $SSO.refresh_login_info_register();
	</script>
</body>
</html>

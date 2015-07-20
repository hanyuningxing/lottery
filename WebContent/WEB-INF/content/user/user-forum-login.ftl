<head>
<meta name="decorator" content="trade" />
<title>用户登录</title>
<!--登录content -->
	<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript">window.BASESITE='${base}';</script>
	
	<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
    <script type="text/javascript" src=<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
			$.formValidator.initConfig({formid:"forumLoginForm",onerror:function(msg){},onsuccess:function(){return true;}});
			checkForm();
 			var redirectUrl = document.referrer;
       		Cookies.set({name:"redirectUrl",value:redirectUrl,path: "/"});
			$("#forumFormSubmit").click(function(){
				if(jQuery.formValidator.pageIsValid()){
					$("#forumLoginForm").submit();
				}
			});
	});		
					
	function checkForm(){
		$("#userNameForum").formValidator({onshow:"请输入4-12位用户名",onfocus:"用户名不能为空,由4～12位字母、汉字,数字组合,不区分大小写",oncorrect:"用户名合法"}).inputValidator({min:4,max:12,empty:{leftempty:false,rightempty:false,emptyerror:"用户名两边不能有空符号"},onerror:"请输入4-12位用户名"});
		$("#passwordForum").formValidator({onshow:"请入6～15位密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:15,empty:{leftempty:false,rightempty:false,emptyerror:"密码不能有空符号"},onerror:"请入6～15位密码"})
	}
	</script>
</head>	
	   <div class="cyy_zcnr" align="center">
	   	  <form action="<@c.url value="/user/user!login.action" />" method="post" id="forumLoginForm">
		    <div class="list_div" id="userName_list_div">		    	 
			     <div style="width:250px"><font>用户名：</font><input class=text_inp type=text id="userNameForum" name="userName"></div>	
			     <div id="userNameForumTip" style="width:250px" align="right"></div>					 		   
			</div>
			<div class="list_div" id="password_list_div">
			     <div style="width:250px"><font>密码：</font><input class=text_inp type=password id="passwordForum" name="password"></div>		
			     <div id="passwordForumTip" style="width:250px" align="right"></div>
			</div>	 
		  	<div class=cyy_tyzc_anniu><INPUT type="image" src="./../images/login.gif" value="登录" id="forumFormSubmit"/></div>
	   	   </form>
	   </div>
<#include "/common/footer.ftl" />

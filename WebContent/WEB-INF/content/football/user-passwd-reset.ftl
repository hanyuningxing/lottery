<head>
	<title>密码重置</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="scheme" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<link href="css/cyyzc.css" rel="stylesheet" type="text/css" />
	<link href="css/cyyzc.css" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript">window.BASESITE='${base}';</script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/count.js"/>"></script>	
	<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>		
	<script type="text/javascript" src="<@c.url value= "/js/lottery/luck.js"/>"></script>	
	<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/user/reg.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/user/validated.js"/>"></script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'resetPasswd'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  
  <div class="cyy_zcnr">
    	<#include "/common/message.ftl" />
    	<div class="list_div" id="oldpassword_list_div">
		      <font>旧密码：</font>
		      <div><input class=text_inp type=password id="oldpasswordInput" name="regForm.oldpassword"></div>
			  <div class="cznr_wzts" id="oldpassword_list_alert_div">由6～15位字母和数字组成。<br />为了您的帐户安全，建议使用大小写字母和数字混合的密码。</div>
		 </div>
	    <div class="list_div" id="password_list_div">
		      <font>新密码：</font>
		      <div><input class=text_inp type=password id="passwordInput" name="regForm.password"></div>
			  <div class="cznr_wzts" id="password_list_alert_div">由6～15位字母和数字组成。<br />为了您的帐户安全，建议使用大小写字母和数字混合的密码。</div>
		  </div>
		  <div class="list_div" id="confirm_password_list_div">
		      <font>确认密码：</font>
		      <div><input class=text_inp type=password id="confirmPswInput" name="regForm.confirmPassword"></div>
			  <div class="cznr_wzts" id="confirm_password_list_alert_div">请再次输入登录密码。</div>
		  </div>
		  
		  <div class=cyy_tyzc_anniu align="center"><INPUT type="image" src="./../images/modify.gif" value="重置密码" onclick="javascript:updatePasswd();"/> </div>
 </div>
</div>
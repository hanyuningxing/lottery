<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代理登陆__${webapp.webName}彩票网</title>
<link href="<@c.url value= "/pages/agent/css/login.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
</head>
<body>
    <div class="main">  
      <div class="login">
        <div class="login_t"><h4>用户登录</h4><#include "/common/message.ftl" /></div>
   			<form action="<@c.url value="/user/user!login.action" />" method="post" id="userLoginForm">
   				<input type="hidden" id="agent" name="agent" value="1"/>
           	    <table width="230" border="0" cellpadding="0" cellspacing="0">
                   <tr>
                     <td width="61" height="30" class="t_r"> 用户名&nbsp;</td>
                     <td width="169"><input class="inputsytle" type="text" id="userName" name="userName"/></td>
                   </tr>
                   <tr>
                     <td height="35" class="t_r">密  码&nbsp;</td>
                     <td  width="169"><input class="inputsytle" type="password" id="password" name="password"/></td>
                 </tr>
                  <!-- <tr>
                     <td>&nbsp;</td>
                     <td><table width="100%" border="0">
                          <tr>
                            <td width="20"><input type="checkbox" /></td>
                            <td width="139">记住密码</td>
                          </tr>
                        </table>
                        </td>
                   </tr>-->
                 <#if needCaptcha!false>
                  <tr  id="captcha_list_div">
                 	 <td height="35" class="t_r">验证码&nbsp;</td>
                     <td width="169">
                     		 <input type="text" id="captcha" name="captcha" maxlength="4" class="inputsytle" style="width:60px"  value="" />	
                   		     <img alt="验证码" title="验证码" src="${base}/vcode.jpg" id="loginVcodeImg" style="cursor: pointer;" align="absmiddle" onclick="this.src='${base}/vcode.jpg?t'+new Date().getTime();" />
			         </td>
                  </tr>
			    </#if>
			    
                 </table>
	 			<div class="button">
                    <table width="300" border="0">
                          <tr>
                           <td width="30" height="50"></td>
                            <td width="90" height="30">
                            	<input type="submit" class="login_button" value="登录"/>
                            </td>
                            <td width="168"><input type="reset" class="login_button" value="取消" /></td>
                          </tr>
                        </table>
                 </div>
                 </form>
      </div>
    </div>
</body>
</html>
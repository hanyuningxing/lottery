<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<head>
  <title>登录页</title>
  <link rel="icon" href="${ctx}/favicon.ico" />
<style type="text/css">
body {margin:0px;padding:0px;font-size:12px;color:#333333;font-family:"宋体";background:#126089 url(images/loginbg.jpg) repeat-x center;height:100%;position:relative;}
div,form,img,ul,li,dl,dt,dd {margin: 0; padding: 0; border: 0;}
input.text, input.number, input.url, input.email, input.password, input.file,
textarea.textarea, select.select{font-size: 1.2em;border-top: 1px solid #7c7c7c;border-left: 1px solid #c3c3c3;border-right: 1px solid #c3c3c3;border-bottom: 1px solid #ddd;background: #fff url(../images/fieldbg.gif) repeat-x top;color: #333;}  
.login{background:url(images/login.jpg) no-repeat;height:554px;left:50%;margin:-248px 0 0 -310px;overflow:auto;padding:0;position:absolute;text-align:left;top:50%;width:605px;}
.login_table{margin-top:194px;width:296px;margin-left:160px;}
.login_table td{height:30px;line-height:30px;vertical-align:middle;font-size:12px;}
.login_table td.t_r{text-align:right;width:60px;color:#FFF;}
.login_table div.error{padding:0;margin:0;padding-left:5px;color:red;}
.login_table input.txt{height:24px;line-height:24px;border:1px solid #153865;}
.login_table input.button{background:url(images/loginbtn.png) no-repeat;width:82px;height:66px;border:0;cursor:pointer;}
form .verySmall{width: 20px;}form .small{width: 70px;}form .medium{ width: 120px;}

/* ----- Buttons ----- */
input.button, button { width: 6em; padding: 2px 2px 0 0; /* fix for IE */}
/* revert to normal for Firefox */
li>input.button, li>button, input.button>input.button, button>button, span>input.button, div>input.button {padding: 2px;}
.header_searchinput{ border:solid 1px #153865; color:#153865;height:24px;}
.header_searchclick{ border:solid 1px #153865; color:#153865;height:24px;background-color:#87ADBF;}
</style>
  <script type="text/javascript">
    window.onload = function(){
      document.getElementById('j_username').focus();
    };
  </script>
</head>
      <div class="login">
       <form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
         <table class="login_table" cellpadding="0" cellspacing="0" border="0">
          <tr>
           <td colspan="4">
             <c:if test="${param.error != null}">
              <div class="error">
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                      <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
              </div>
             </c:if>
           </td>
          </tr>

           <tr>
             <td class="t_r"><label for="j_username" style="width:60px;text-align:right">用户名：</label></td>
             <td colspan="2"><input class="txt" maxlength="20" value=""  type="text" class="text medium" name="j_username" id="j_username" tabindex="1" class="header_searchinput" onblur="this.className='header_searchinput';" onfocus="this.className='header_searchclick'" style="width:120px;" /></td>
             <td rowspan="3"><input type="submit" class="button" name="login" value="" tabindex="4" /></td>
           </tr>
           <tr>
             <td class="t_r"><label for="j_password" style="width:60px;text-align:right">密码：</label></td>
             <td colspan="2"><input class="txt"  maxlength="20"  value="" type="password" class="text medium" name="j_password" id="j_password" tabindex="2" class="header_searchinput" onblur="this.className='header_searchinput';" onfocus="this.className='header_searchclick'" style="width:120px;" /></td>
           </tr>
           <tr>
             <td class="t_r"><label for="j_captcha" style="width:60px;text-align:right">验证码：</label></td>
             <td style="text-align:left;width:58px;"><input  maxlength="10" class="txt" type="text" class="text medium" name="j_captcha" id="j_captcha" tabindex="3" class="header_searchinput" onblur="this.className='header_searchinput';" onfocus="this.className='header_searchclick'" style="width:44px;" /></td>
             <td style="text-align:left;"><img alt="验证码" title="验证码" src="${ctx}/vcode.jpg" id="loginVcodeImg" style="cursor: pointer;display:none;" onload="this.style.display='';" align="absmiddle" onclick="this.src='${ctx}/vcode.jpg?t'+new Date().getTime();" /></td>
            </tr>
         </table>
      </form>
    </div>

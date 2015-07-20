<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>很抱歉你没有访问该页面的权限...</title>
<meta name="decorator" content="trade" />
<style type="text/css">
.login_main{margin:90px auto 0 auto;width:320px;height:220px;line-height:150%;}
.login_main dt{float:left;padding-right:10px;}
.login_main a{color:#960001;text-decoration:underline;}
</style>
</head>
<body>
<div class="login_main">
	<h1>很抱歉你没有访问该页面的权限...</h1>
	<h2><a href="javascript:void(0);" onClick="history.back();return true;">返回上一页</a> <a href="<c:url value="/j_spring_security_logout"/>">退出登录</a></h2>
</div>
</body>
</html>
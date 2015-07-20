<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作出错</title>
<meta name="decorator" content="tradeV1" />
<style type="text/css">
.login_main{margin:90px auto 0 auto;width:420px;height:320px;line-height:150%;}
.login_main dt{float:left;padding-right:10px;}
.login_main a{color:#960001;text-decoration:underline;}
</style>
</head>
<body>
<div class="login_main">
	<b>对不起，您的操作出现异常.</b>
	
	<c:if test="${actionErrors != null && fn:length(actionErrors) > 0}">
	<div class="error" id="errors">
		<c:forEach items="${actionErrors}" var="err">
			<img src="<c:url value="/images/iconWarning.gif"/>" alt="icon.warning" class="icon" onerror="this.outerHTML='';" />
			${err}<br />
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${fieldErrors != null && fn:length(fieldErrors) > 0}">
	<div class="error" id="errors">
		<c:forEach items="${fieldErrors}" var="entry">
			<img src="<c:url value="/images/iconWarning.gif"/>" alt="icon.warning" class="icon" onerror="this.outerHTML='';" />
			<strong>${entry.key}：</strong><br />
			<c:if test="${entry.value != null && fn:length(entry.value) > 0}">
		  	<div style="padding-left:30px;">
		  	<c:forEach items="${entry.value}" var="err">
		  		--${err}<br/>
		  	</c:forEach>
		  	</div>
			</c:if>
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${actionMessages != null && fn:length(actionMessages) > 0}">
	<div class="message" id="messages">
		<c:forEach items="${actionMessages}" var="msg">
			<img src="<c:url value="/images/iconInformation.gif"/>" alt="icon.information" class="icon" onerror="this.outerHTML='';" />
			${msg}<br />
		</c:forEach>
	</div>
	</c:if>
</div>

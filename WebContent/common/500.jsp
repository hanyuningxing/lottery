<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../user/css/cyyzc.css" rel="stylesheet" type="text/css" />
<link href="../user/css/cg_sb.css" rel="stylesheet" type="text/css" />
<link href="../pages/css/main.css" rel="stylesheet" type="text/css" />
<link href="../pages/css/sitety.css" rel="stylesheet" type="text/css" />
<link href="../pages/css/index.css" rel="stylesheet" type="text/css" />
<link href="../pages/css/topdownpublic.css" rel="stylesheet" type="text/css" />
<title>操作出错</title>
<body>
	<div id="wrapper">
	    <div class="cyy_zcnr">
	      <div class="cyy_zcxxwaikang">
		     <div class="cyy_biaoqing"><img src="../pages/images/cyy_kulian.png" /></div>
		     <div class="bigfont">
		     <span> 
		     </span>十分抱歉,您的操作有误
		     </div>
		     <br>请<a href="javascript:history.back(-1)" ;>返回</a>或联系在线客服为您服务！
		  </div>
	   </div>
	   
    </div>
</body>
</html>







<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="application/msexcel; charset=GBK" />
</head>
<%
   String fileName=request.getParameter("id");
   String data=request.getParameter("data");
   response.setHeader("Content-disposition","inline; filename=123.xls");
   //以上这行设定传送到前端浏览器时的档名为test1.xls
   //就是靠这一行，让前端浏览器以为接收到一个excel档 
%>  
<body>
 <%=data%>
</body>
</html>
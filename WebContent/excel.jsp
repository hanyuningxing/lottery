<%@ page language="java" contentType="application/msexcel; charset=GBK" pageEncoding="GBK"%>
<html>
<%
   String fileName=request.getParameter("id");
   String data = (String)request.getAttribute("data");
   response.setHeader("Content-disposition","inline; filename="+fileName+".xls");
   //以上这行设定传送到前端浏览器时的档名为test1.xls
   //就是靠这一行，让前端浏览器以为接收到一个excel档 
%>  
<body>
 <%=data%>
</body>
</html>
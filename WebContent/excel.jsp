<%@ page language="java" contentType="application/msexcel; charset=GBK" pageEncoding="GBK"%>
<html>
<%
   String fileName=request.getParameter("id");
   String data = (String)request.getAttribute("data");
   response.setHeader("Content-disposition","inline; filename="+fileName+".xls");
   //���������趨���͵�ǰ�������ʱ�ĵ���Ϊtest1.xls
   //���ǿ���һ�У���ǰ���������Ϊ���յ�һ��excel�� 
%>  
<body>
 <%=data%>
</body>
</html>
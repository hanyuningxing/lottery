<%@page import="com.cai310.utils.DateUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %><%out.print(DateUtil.dateToStr(new java.util.Date(),"MM/dd/yyyy HH:mm:ss"));%>
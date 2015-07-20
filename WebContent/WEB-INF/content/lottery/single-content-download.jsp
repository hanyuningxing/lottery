<%@ page contentType="application/vnd.ms-txt; charset=gb2312" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%String zdyffmc = (String)request.getAttribute("scheme.schemeNumber");
 String bbmc = zdyffmc + ".txt";
 response.setHeader("Content-disposition","attachment;filename=" +new String(bbmc.getBytes("GBK"),"ISO8859_1"));//txt
 String lotteryType = (String)request.getAttribute("scheme.lotteryType.key");
 if("jczq".equals(lotteryType) || "jclq".equals(lotteryType) || "dczc".equals(lotteryType)){
%><c:out value="${scheme.singleContent.content}"></c:out><%}else{%><c:out value="${scheme.content}"></c:out><%}%>
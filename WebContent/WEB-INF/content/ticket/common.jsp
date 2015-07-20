<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<processResult>
   <wMsgID><c:out value="${wMsgID}" default="" escapeXml="false"/></wMsgID>
   <processId><c:out value="${processId}" default="" escapeXml="false"/></processId>
   <errorMsg><c:out value="${errorMsg}" default="" escapeXml="false"/></errorMsg>
   <c:out value="${ticketXml}" default="" escapeXml="false"/>
</processResult>
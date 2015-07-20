<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../../../pages/css/analyse.css" rel="stylesheet" type="text/css" />
<link href="../../../../pages/css/news_mainuse.css" rel="stylesheet" type="text/css" />
<link href="../../../../pages/css/pdzx.css" rel="stylesheet" type="text/css" />
<link href="../../../../pages/css/ssc_zs.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
<%
	String type = (String)request.getAttribute("type");
%>
<div class="kong5"></div>
<div class="topbgty1" >
	<table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogohcy"></div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">好彩一走势图表</div>
			<div class="kong5"></div>
			
			<ul class="navt_menu">
			
			<%
				if("index".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7zs.action" class="now">综合走势图</a></li>
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7zs.action">综合走势图</a></li>
			<%} %>
			<li><span>|</span></li>	
			<%
				if("szzst".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=szzst" class="now">数字走势图</a></li>
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=szzst">数字走势图</a></li>
			<%} %>
			<li><span>|</span></li>	
			<%
				if("sxzst".equals(type)){
			%>
			<li><a href="/welfare36to7/analyse!w36to7zs.action?type=sxzst" class="now">生肖走势图</a></li>
			<%}else{ %>
			<li><a href="/welfare36to7/analyse!w36to7zs.action?type=sxzst">生肖走势图</a></li>
			<%} %>
			<li><span>|</span></li>	
			<%
				if("jjfwzst".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=jjfwzst" class="now">季节方位走势图</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=jjfwzst">季节方位走势图</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("wszst".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=wszst" class="now">尾数走势图</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=wszst">尾数走势图</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("wxzst".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=wxzst" class="now">五行走势图</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7zs.action?type=wxzst">五行走势图</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("hmyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=hmyl" class="now">号码遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=hmyl">号码遗漏</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("sxyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=sxyl" class="now">生肖遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=sxyl">生肖遗漏</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("wsyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=wsyl" class="now">尾数遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=wsyl">尾数遗漏</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("hsyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=hsyl" class="now">合数遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=hsyl">合数遗漏</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("wxyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=wxyl" class="now">五行遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=wxyl">五行遗漏</a></li>	
			<%} %>
			<li><span>|</span></li>	
			<%
				if("xtyl".equals(type)){
			%>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=xtyl" class="now">形态遗漏</a></li>	
			<%}else{ %>
				<li><a href="/welfare36to7/analyse!w36to7yl.action?type=xtyl">形态遗漏</a></li>	
			<%} %> 
			
			</ul>	
			</div>
			</td>
		  </tr>
	</table>
	<div class="kong5"></div>
       <div id="div_line"></div>
</div>
<%

	if("index".equals(type)||"szzst".equals(type)||"sxzst".equals(type)||"jjfwzst".equals(type)||"wszst".equals(type)||"wxzst".equals(type)){
%>
<div class="tybanbg1">
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="${base}/welfare36to7/analyse!w36to7zs.action" method="post">
			    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="30" id="count">
			    <input type="hidden" name="sort" value="${sort }" id="sort">
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示30期" onclick="chgDisplay(30);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示50期" onclick="chgDisplay(50);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示100期" onclick="chgDisplay(100);" />
			    </div>
			    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" value="${start }"/> -  结束期数<input class="inputh18" id="end" name="end" size="12" value="${end }"/>
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="/pages/images/tybtsearchnew.gif" width="85" height="20" border="0" /></a></div>			    
				</form>
	   </div>
<%
	}else{
%> 	   
		<div class="tybanbg1">
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="${base}/welfare36to7/analyse!w36to7yl.action" method="post">
			    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="100" id="count">
			    <input type="hidden" name="sort" value="${sort }" id="sort">
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示100期" onclick="chgDisplay(50);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示200期" onclick="chgDisplay(100);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示1000期" onclick="chgDisplay(200);" />
				  <input id="size_80" type="button" class="btgray" value="全部" onclick="chgDisplay(0);" />				
			    </div>
			   </form>
	   </div>
<%
	}
%> 	   
	
<div class="kong5"></div>
     <div id="div_line"></div>

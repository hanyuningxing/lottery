<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="../banner.jsp" />

<%
	String type = (String)request.getAttribute("type");
%>
<div class="kong5"></div>
<div class="topbgty1" >
	<table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogotcssc"></div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">时时彩走势图表</div>
			<div class="kong5"></div>
						<div style="width:850px;">			
			
			<ul class="navt_menu">
			<li><a href="/ch/ssc-analyse!generalAward.htm">二星综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="/ch/ssc-analyse!lyel.htm">二星012路走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="/ch/ssc-analyse!samSung.htm">三星综合走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="/ch/ssc-analyse!sxlyel.htm">三星012路走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="/ch/ssc-analyse!dxds.htm">大小单双走势图</a></li>	
			<li><span>|</span></li>	
			<%
				if("wxzs".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!ssczs-wxzs.htm"" class="now">五星走势图</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!ssczs-wxzs.htm">五星走势图</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("exhz".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!ssczs-exhz.htm" class="now">二星和值走势图</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!ssczs-exhz.htm">二星和值走势图</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("sxhz".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!ssczs-sxhz.htm" class="now">三星和值走势图</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!ssczs-sxhz.htm">三星和值走势图</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("sxjo".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!ssczs-sxjo.htm" class="now">三星奇偶走势图</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!ssczs-sxjo.htm">三星奇偶走势图</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("wnzs".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!ssczs-wnzs.htm" class="now">万能六码走势图</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!ssczs-wnzs.htm">万能六码走势图</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				Integer wz = (Integer)request.getAttribute("wz");
				if("zx1".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!zhixuan-zx1.htm" class="now">一星直选遗漏</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!zhixuan-zx1.htm">一星直选遗漏</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("zx2".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!zhixuan-zx2.htm" class="now">二星直选遗漏</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!zhixuan-zx2.htm">二星直选遗漏</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("exhzyl".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!hezhi-exhzyl.htm" class="now">二星和值遗漏</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!hezhi-exhzyl.htm">二星和值遗漏</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("zx3".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!zhixuan-zx3.htm" class="now">三星直选遗漏</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!zhixuan-zx3.htm">三星直选遗漏</a></li>		
			<%} %>
			<li><span>|</span></li>	
			<%
				if("sxhzyl".equals(type)){
			%>
				<li><a href="/ch/ssc-analyse!hezhi-sxhzyl.htm" class="now">三星和值遗漏</a></li>	
			<%}else{ %>
				<li><a href="/ch/ssc-analyse!hezhi-sxhzyl.htm">三星和值遗漏</a></li>		
			<%} %>
			</ul>	
			</div>
			</div>
			</td>
		  </tr>
	</table>
	<div class="kong5"></div>
       <div id="div_line"></div>
</div>
<%

	if("wxzs".equals(type)||"exhz".equals(type)||"sxhz".equals(type)||"sxjo".equals(type)||"sxjo".equals(type)||"wnzs".equals(type)){
%>
<div class="tybanbg1">
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="/ssc/analyse!ssczs.action" method="post">
			    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="50" id="count">
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示50期" onclick="chgDisplay(50);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示100期" onclick="chgDisplay(100);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" />
			    </div>
			    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="/pages/images/tybtsearchnew.gif" width="85" height="20" border="0" /></a></div>
			    
				</form>
	   </div>
<%
	}else if("zx1".equals(type)||"zx2".equals(type)||"gx2".equals(type)||"zx3".equals(type)){
%> 	   
	<div class="tybanbg1">
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="/ssc/analyse!zhixuan.action" method="post">
			    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="50" id="count">
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示100期" onclick="chgDisplay(100);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示500期" onclick="chgDisplay(500);" />
				  <input id="size_80" type="button" class="btgray" value="显示1000期" onclick="chgDisplay(1000);" />				
			      <input id="size_80" type="button" class="btgray" value="全部" onclick="chgDisplay(0);" />	
			    </div>
			    <!-- <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="/pages/images/tybtsearchnew.gif" width="85" height="20" border="0" /></a></div>
			     -->
				</form>
	   </div>
	<%
	}else if("exhzyl".equals(type)||"sxhzyl".equals(type)){
	%>
	<div class="tybanbg1">
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="/ssc/analyse!hezhi.action" method="post">
			    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="50" id="count">
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示100期" onclick="chgDisplay(100);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示500期" onclick="chgDisplay(500);" />
				  <input id="size_80" type="button" class="btgray" value="显示1000期" onclick="chgDisplay(1000);" />				
			      <input id="size_80" type="button" class="btgray" value="全部" onclick="chgDisplay(0);" />	
			    </div>
			    <!-- <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="/pages/images/tybtsearchnew.gif" width="85" height="20" border="0" /></a></div>
			    -->
				</form>
	   </div>
<%
	}
%>
<div class="kong5"></div>
       <div id="div_line"></div>
</body>
</html>
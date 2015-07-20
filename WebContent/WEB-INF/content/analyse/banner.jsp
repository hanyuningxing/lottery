<%@ page language="java" import="java.util.*,com.cai310.lottery.common.Lottery" pageEncoding="utf-8" %>
<div class="main_wz">
<div class="shaix_cuti_btwz">您所在的位置:<a href="/">球客彩票网 </a>
<%
	Lottery lotteryType = (Lottery)request.getAttribute("lotteryType"); 
	if(null!=lotteryType&&lotteryType.getKey().equals("sdel11to5")){
%>
		> <a href="/ch/sde111to5.htm"><%=lotteryType.getLotteryName() %></a> > <%=lotteryType.getLotteryName() %>走势图
<%		
	}else{
		%>
		> <a href="/ch/<%=lotteryType.getKey() %>.htm"><%=lotteryType.getLotteryName() %></a> > <%=lotteryType.getLotteryName() %>走势图
		<%
	}
%>
</div>
</div>
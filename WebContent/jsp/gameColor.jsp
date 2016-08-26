<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cai310.lottery.service.lottery.GameManager"%>
<%@ page import="com.cai310.spring.SpringContextHolder"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择赛事颜色</title>
<style type="text/css">
body {
	font-size: 12px;
}

ul {
	border: 0 none;
	margin: 0;
	padding: 0;
	display: inline-block;
	float: left;
}

ul li {
	padding-left: 5px;
	font-size: 14px;
	height: 25px;
	line-height: 25px;
	text-align: left;
	color: #fff;
	width: 550px;
	height: 25px;
	font-size: 14px;
}
</style>

<%
	String game_name = request.getParameter("gameName");
	String searchValue = request.getParameter("searchValue");
	String ctx = request.getContextPath();
%>
<script type="text/javascript">
	function updateColor(color){
		var P = window.parent;
		P.updateColor('<%=game_name%>', color);
		P._tipsHandle.close();
	}
</script>
</head>
<body>
	<ul>
		<li><form
				action="<%=ctx + "/jsp/gameColor.jsp?gameName=" + game_name%>">
				<input name="searchValue"
					value="<%=(searchValue == null) ? "" : searchValue%>" /><input
					type="submit" value="搜索" />（点击选择颜色）
			</form></li>
		<%
			GameManager manager = SpringContextHolder
					.getBean(GameManager.class);
			Map<String, String> map = manager.getGameColor();
			Map<String, List<String>> mapList = new HashMap<String, List<String>>();
			for (Entry<String, String> entry : map.entrySet()) {
				String gameName = entry.getKey();
				String gameColor = entry.getValue();
				if (searchValue == null
						|| gameName.indexOf(searchValue.trim()) >= 0) {
					List<String> list = mapList.get(gameColor);
					if (list == null)
						list = new ArrayList<String>();
					list.add(gameName);
					mapList.put(gameColor, list);
				}
			}
			for (Entry<String, List<String>> entry : mapList.entrySet()) {
		%>
		<li style="cursor: pointer;background-color: <%=entry.getKey()%>"
			onclick="updateColor('<%=entry.getKey()%>');"><%=StringUtils.join(entry.getValue(), "，")%></li>
		<%
			}
		%>
	</ul>
</body>
</html>
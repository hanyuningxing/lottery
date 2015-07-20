<%@ page contentType="application/vnd.ms-txt; charset=GBK" language="java"%><%
	if (request.getMethod().equalsIgnoreCase("post")) {
		String file_name = request.getParameter("file_name");
		if (file_name == null || file_name.trim().length() == 0) {
			file_name = "download";
		}
		String file_content = request.getParameter("file_content");
		file_name += ".txt";
		response.setHeader("Content-disposition", "attachment;filename="
				+ new String(file_name.getBytes("GBK"), "ISO8859_1"));
		out.print(file_content);
	}
%>
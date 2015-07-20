<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="com.cai310.lottery.fetch.dczc.DczcContextHolder,
	com.cai310.lottery.support.dczc.PlayType,
	org.apache.commons.lang.StringUtils,
	java.util.List,
	java.util.Map,
	com.google.common.collect.Lists,
	org.codehaus.jackson.map.ObjectMapper,
	com.cai310.lottery.fetch.ChangeHistory,
	com.cai310.lottery.fetch.FetchDataBean"%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

	String periodNumber = request.getParameter("periodNumber");
	String playType = request.getParameter("playType");
	if (StringUtils.isNotBlank(playType) && StringUtils.isNotBlank(periodNumber)) {
		playType = playType.trim();
		PlayType pt = null;
		for(PlayType type : PlayType.values()){
			if(type.name().equals(playType)){
				pt = type;
				break;
			}
		}
		if(pt == null){
			return;
		}
		FetchDataBean bean = DczcContextHolder.getRateData(periodNumber,pt);
		if (bean != null) {
			String type = request.getParameter("type");
			ObjectMapper mapper = new ObjectMapper();
			if (StringUtils.isNotBlank(type) && "chg".equals(type.trim())) {
				out.print(mapper.writeValueAsString(bean.getChgHistory()));
			} else {
				out.print(mapper.writeValueAsString(bean.getDataBlock()));
			}
		}
	}
%>

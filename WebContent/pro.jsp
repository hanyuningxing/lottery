<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.commons.lang.xwork.StringUtils" %>
<%@ page import="com.cai310.lottery.common.Lottery" %>
<% 
String wAction = request.getParameter("wAction");
String wParam = request.getParameter("wParam");
String wLotteryId = null;
if(org.apache.commons.lang.StringUtils.isNotBlank(wParam)){
	java.util.Map<String, Object> map = com.cai310.utils.JsonUtil.getMap4Json(wParam);
	if(null!=map){
		 wLotteryId = String.valueOf(map.get("wLotteryId"));
	}
}
StringBuffer url = new StringBuffer();

try{
	
	if(StringUtils.isNotBlank(wAction)){
		if("201".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!login.action");
		}else if("202".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!reg.action");
		}else if("203".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!ipsOrder.action");
		}else if("204".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!comfirmIpsOrder.action");
		}else if("205".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!schemeList.action");
		}else if("206".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!chaseList.action");
		}else if("207".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!findCurrentPeriods.action");
		}else if("208".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!fundList.action");
		}else if("209".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!userInfo.action");
		}else if("210".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!drawing.action");
		}else if("211".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!drawing.action");
		}else if("212".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!userDetails.action");
		}else if("213".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!ipsList.action");
		}else if("214".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!newver.action");
		}else if("107".equals(wAction.trim())){
			url.append(request.getSession().getServletContext().getContextPath()+"/external/user!resultList.action");
		}else{
			url.append(request.getSession().getServletContext().getContextPath()+"/ticket/then/");
			if(StringUtils.isNotBlank(wLotteryId)){
					Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId.trim())];
					if(null!=lottery){
						url.append(lottery.getKey());
						if(StringUtils.isNotBlank(wAction)){
							if("101".equals(wAction.trim())){
								url.append("!create.action");
							}else if("102".equals(wAction.trim())){
								url.append("!query.action");
							}else if("103".equals(wAction.trim())){
								url.append("!award.action");
							}else if("104".equals(wAction.trim())){
								url.append("!match.action");
							}else if("105".equals(wAction.trim())){
								url.append("!result.action");
							}else if("106".equals(wAction.trim())){
								url.append("!schemeInfo.action");
							}else if("108".equals(wAction.trim())){
								url.append("!resultList.action");
							}else{
								throw new Exception();
							}
						}else{
							///没有请求ID
							throw new Exception();
						}
					}else{
						///没有平台
						throw new Exception();
					}
				}else{
					///没有平台
					throw new Exception();
				}
			}
		}
}catch(Exception e){
	RequestDispatcher rd = request.getRequestDispatcher(request.getSession().getServletContext().getContextPath()+"/ticket/then/ticket!index.action");
	rd.forward(request,response);
}
if(StringUtils.isBlank(url.toString())){
	RequestDispatcher rd = request.getRequestDispatcher(request.getSession().getServletContext().getContextPath()+"/ticket/then/ticket!index.action");
	rd.forward(request,response);
}
RequestDispatcher rd = request.getRequestDispatcher(url.toString());
rd.forward(request,response);
%>
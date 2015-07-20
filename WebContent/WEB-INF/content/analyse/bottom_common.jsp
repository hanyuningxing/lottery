<%@ page language="java" import="java.util.*,com.cai310.lottery.entity.info.NewsInfoData" pageEncoding="utf-8"%>
	
	<div class="moreabout">
	
	<div class="more">
		
	<%
		ArrayList<NewsInfoData> hotNews = (ArrayList<NewsInfoData>)request.getAttribute("hotNews");
		int index =0;
		if(hotNews.size()>0){
	%>
		<div class="gd_link">
		    		<ul>
	<%		
			for(NewsInfoData data:hotNews){
				if(index<=4){	
					if(null!=data.getTitleLink()){
						if("#".equals(data.getTitleLink())){
	%>
	    						<li><a href="/info/<%=data.getId() %>.html" target="_blank">
							    <%
							    	if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){ 
							    %>
									    	[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;<%=data.getTitle() %></a></li>	
							    <%
							    	}else{
							    %>
							                <li><a href="<%=data.getTitleLink() %>" target="_blank">
							                <%
							                	if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){ 
							                %>
							                	<%=data.getTitle() %>
							                <%
							                	}
							                %>
							     </a></li>
							    <%
							    	} 
	    				}else{
	%> 					 					
	    				<li>
                 			<a href="/info/<%=data.getId() %>.html" target="_blank">
                 			<% 
					    		if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){                 			
                 			%>
                 				[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;
                 			<%
					    		}
                 			%>	
                 				<%=data.getTitle() %></a></li> 
                 				           				            			
    <%  			    		
	    				}				
					}else{
					%> 					 					
    				<li>
             			<a href="/info/<%=data.getId() %>.html" target="_blank">
             			<% 
				    		if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){                 			
             			%>
             				[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;
             			<%
				    		}
             			%>	
             				<%=data.getTitle() %></a></li>             				            			
	<%  				
				}
					index++;

			}

			}
	%>
		</ul>
		               </div>
		            <div class="gd_link_fgx"></div>
		               
		            <div class="gd_link">
		    		<ul>
<%		
			for(int i=5;i<hotNews.size();i++){
				NewsInfoData data = hotNews.get(i);
					if(null!=data.getTitleLink()){
						if("#".equals(data.getTitleLink())){
	%>
	    						<li><a href="/info/<%=data.getId() %>.html" target="_blank">
							    <%
							    	if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){ 
							    %>
									    	[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;<%=data.getTitle() %></a></li>	
							    <%
							    	}else{
							    %>
							                <li><a href="<%=data.getTitleLink() %>" target="_blank">
							                <%
							                	if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){ 
							                %>
							                	<%=data.getTitle() %>
							                <%
							                	}
							                %>
							     </a></li>
							    <%
							    	} 
	    				}else{
	%> 					 					
	    				<li>
                 			<a href="/info/<%=data.getId() %>.html" target="_blank">
                 			<% 
					    		if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){                 			
                 			%>
                 				[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;
                 			<%
					    		}
                 			%>	
                 				<%=data.getTitle() %></a></li> 
                 				           				            			
    <%  			    		
	    				}				
					}else{
	%> 					 					
    				<li>
             			<a href="/info/<%=data.getId() %>.html" target="_blank">
             			<% 
				    		if(null!=data.getSubType()&&!"NONE".equals(data.getSubType())){                 			
             			%>
             				[<%=data.getSubType().getTypeName() %>]&nbsp;&nbsp;
             			<%
				    		}
             			%>	
             				<%=data.getTitle() %></a></li>             				            			
	<%  				
					}
			}
	%>		    		
		    		
	<%
		}
	%>
		 </ul>
		 </div>
			
	</div>
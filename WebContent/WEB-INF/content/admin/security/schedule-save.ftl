<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="fetch"/>
<script>
function fetchData(){
			var leagueForm = document.getElementById('leagueForm');
			
		    var match_button_span = document.getElementById("match_button_span");
		    var buttonHtml = match_button_span.innerHTML
		    var loaddingHtml = "<img src='${base}/images/data-loading.gif'/>更新中";
		    match_button_span.innerHTML = loaddingHtml;
		    
		    var sclass_out =  leagueForm.elements['sclass_out'].value;
		    
		    var url = "${base}/admin/security/schedule!fetchMatch.action?id="+sclass_out;
		    var jsonRequest = new Request.JSON({
		    	url: url, 
			    onComplete: function(resultObj, text){
			        match_button_span.innerHTML = buttonHtml;
			        if(resultObj != null){
			        	if(resultObj.success == true){
			        	    leagueForm.elements['name'].value=resultObj.name;
			        	    leagueForm.elements['starSeason'].options[(resultObj.startYear-1995)].selected=true;
			        	    leagueForm.elements['endSeason'].options[(resultObj.endYear-1995)].selected=true;
			        	    leagueForm.elements['count_round'].value=resultObj.lun;
			        	    window.alert("更新成功");
			        	}else{
			        		window.alert(resultObj.msg);
			        		 window.location.reload();
			        	}
			        }
			    }
			}).get({ 
			    '__t': new Date().getTime()
			});
		}

</script>
<form id="leagueForm" action="${base}/admin/security/schedule!save.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
<input name="id"  type="hidden" value="<#if league??&&league.id??>${league.id!}</#if>"/>
<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="left">
					    <tr bgcolor="#F5f5f5">
			              <td bgcolor="#F5f5f5" align="center" width="20%">澳客GroupId：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <input name="sclass_out"  type="text" value="<#if league??&&league.sclass_out??>${league.sclass_out!}</#if>"/>
			              </span>&nbsp;&nbsp;输入后按 <span id="match_button_span"><a href="#" onclick="fetchData();return false;">更新</a></span></td>
			            </tr>
			             <tr bgcolor="#F5f5f5">
			              <td bgcolor="#F5f5f5" align="center" width="20%">联赛名称：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <input name="name"  type="text" value="<#if league??&&league.name??>${league.name!}</#if>"/>
			              </span></td>
			            </tr>
			            
			            <tr bgcolor="#F5f5f5">
			              <td align="center">总轮数：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="count_round" size="1">
			                          <#list 1..60 as a>
			                                  <option value="${a}" <#if league?? && league.count_round?? && league.count_round==a>selected</#if>>${a}</option>
			                          </#list>
     						     </select>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">赛季结束月份：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <select name="endMonth" size="1">
			                          <#list 1..12 as a>
			                                  <option value="${a}" <#if league?? && league.endMonth?? && league.endMonth==a>selected<#else><#if a==6>selected</#if></#if>>${a}</option>
			                          </#list>
     						</select>
			              </span></td>
			            </tr>
						<tr bgcolor="#F5f5f5">
			              <td align="center">赛季开始年份：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="starSeason" size="1">
			                          <#list 1995..2020 as a>
			                                  <option value="${a}" <#if league?? && league.starSeason?? && league.starSeason==a>selected</#if>>${a}</option>
			                          </#list>
     						     </select>
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">赛季开始年份：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="endSeason" size="1">
			                          <#list 1995..2020 as a>
			                                  <option value="${a}" <#if league?? && league.endSeason?? && league.endSeason==a>selected</#if>>${a}</option>
			                          </#list>
     						     </select>
			             </span></td>
			            </tr>
			           
			             <tr bgcolor="#F5f5f5">
			              <td align="center"> 提交：</td>
			              <td bgcolor="#F5f5f5">
			                 <span class="ssq_tr_2">
     									 <input type="image" src="${base}/images/comfirm.gif" />
				           </span></td>
			            </tr>
			            
					    
</table>
</form>

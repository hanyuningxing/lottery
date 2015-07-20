<title>媒体管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="mediaList"/>
<script src="${base}/js/admin/user.js" type="text/javascript"></script>
<script src="${base}/js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() { 
$("#checkall").click(function() {
		var idstr = "";
		if(this){
			$("input[name='ids']").each(function(i) { 
				idstr+=$("#c_i").val()+",";
				$(this).attr("checked", true); 
			});
		}
				alert(idstr);
		
		if(!this.checked){
			$("input[name='ids']").each(function() { 
				ids = "";
				$(this).attr("checked", false); 
			}); 
		}
				alert(idstr);
		
	});
	})
</script>


<form name="queryForm" action="${base}/admin/user/user!mediaList.action" method="get">
      媒体名称：<input name="mediaName" value="<#if mediaName??>${mediaName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
     <tr class="trlbrown" >
	 <td height="22">编号</td>
      <td>媒体名称</td>
      <td>联系方式</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>
	      <td>${data.name!}</td>
	      <td>${data.contact!}</td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="7">无记录</td>
	    </tr>
    </#if>
  </table>
  <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
  
  <form name="userForm" action="${base}/admin/user/user!saveMedia.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
		<table width="80%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
				        <input name="entity.id"  type="hidden" value="<#if entity??&&entity.id??>${entity.id!}</#if>"/>
				        <tr bgcolor="#F5f5f5">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#F5f5f5">
			                 新增媒体
			              </td>
			            </tr>
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">媒体名称：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input name="mediaName" value="" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td width="85" align="center" bgcolor="#F5f5f5">媒体联系方式：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                <input name="contact" value="" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#FFFFFF">
			                 <input type="image" src="${base}/images/comfirm.gif" />
			              </td>
			            </tr>
			          </table>
   </form>
</body>
</html>
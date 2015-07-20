<title>用户管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="user"/>
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



<form name="queryForm" action="${base}/admin/user/user!userList.action" method="get">
      <select name="islocked" size="1" onchange="document.forms['queryForm'].submit();return false;">
        <option value="">是否可用</option>
        <option value="true" <#if islocked?? && islocked?string == 'true'>selected</#if>>停用</option>
        <option value="false" <#if islocked?? && islocked?string == 'false'>selected</#if>>可用</option>
      </select>
      &nbsp;
      	身份证：<input name="idCard" value="<#if idCard??>${idCard!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
           邮箱：<input name="email" value="<#if email??>${email!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
                 电话：<input name="phoneNo" value="<#if phoneNo??>${phoneNo!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      用户名：<input name="userName" value="<#if userName??>${userName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
     <tr class="trlbrown">
	 <td height="22">编号</td>
      <td>用户名</td>
      <td>真实姓名</td>
      <td>用户余额</td>
      <td>消费余额</td>
      <td>是否可用</td>
      <td>来源媒体</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>
	      <td><a href="${base}/admin/fund/fund!userInfo.action?id=${data.id}" target="_blank">${data.userName!}</a></td>
	      <td>${data.realName!}</td>
	      <td>${data.remainMoney!}</td>
	      <td>${data.consumptionMoney!}</td>
	    <td><#if data.locked??&&data.locked><font color="gray">否</font><#else><font color="blue">是</font></#if></td>
	    <td>
	    	<#if mediaList??>
	    	   <#list mediaList as media>
	    	       <#if data.mid?? && media.id?? && media.id==data.mid>
	    	           ${media.name!}
	    	       </#if>
	    	   </#list>
	    	</#if>
	    </td>
	      <td>
	     	<a href="${base}/admin/user/user!userInfo.action?id=${data.id}" target="_blank">查看详细</a>
	      </td>
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
</body>
</html>
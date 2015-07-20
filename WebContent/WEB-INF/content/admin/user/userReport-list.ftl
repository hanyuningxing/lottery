<title>意见管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="userReportList"/>
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
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
     <tr class="trlbrown" >
	 <td height="22">编号</td>
      <td>用户id</td>
      <td>客户端类型</td>
      <td>意见</td>
      <td>时间</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>${data.id}</td>
	      <td>${data.userid!}</td>
	      <td><#if data.agentInfo??>${data.agentInfo.typeName!}</#if></td>
	      <td>${data.report!}</td>
	      <td>${data.createTime!}</td>
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
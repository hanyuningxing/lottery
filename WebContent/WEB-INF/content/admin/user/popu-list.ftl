<title>推广管理</title>
<meta name="menu" content="popu"/>
<meta name="menuItem" content="popuList"/>
<script src="${base}/js/admin/user.js" type="text/javascript"></script>
<script src="${base}/js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<#assign typeArr=stack.findValue("@com.cai310.lottery.common.PopuType@values()") />
<form name="queryForm" action="${base}/admin/user/user!popuList.action" method="get">
		<select name="popuType" onchange="document.forms['queryForm'].submit();return false;">					                      
					<option value="">请选择类型</option>
					<#list typeArr as type>
							<option <#if popuType?exists&&popuType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
					</#list>												
		</select>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
     <tr class="trlbrown" >
	 <td height="22">编号</td>
      <td>用户id</td>
      <td>用户名</td>
      <td>媒体id</td>
      <td>媒体名称</td>
      <td>ip</td>
      <td>类型</td>
      <td>时间</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>${data.id}</td>
	      <td>${data.userId!}</td>
	      <td>${data.name!}</td>
	      <td>${data.mid!}</td>
	      <td>${data.mediaName!}</td>
	      <td>${data.ip!}</td>
	      <td><#if data.popuType??>${data.popuType.typeName!}</#if></td>
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


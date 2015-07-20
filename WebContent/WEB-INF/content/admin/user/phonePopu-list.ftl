<title>推广管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="phonePopuList"/>
<script src="${base}/js/admin/user.js" type="text/javascript"></script>
<script src="${base}/js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>


<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
     <tr class="trlbrown" >
	 <td height="22">编号</td>
      <td>手机类型</td>
      <td>SDK</td>
      <td>手机号码</td>
      <td>imei</td>
      <td>媒体id</td>
      <td>媒体名称</td>
      <td>时间</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>${data.id}</td>
	      <td>${data.model!}</td>
	      <td>${data.sdk!}</td>
	      <td>${data.number!}</td>
	      <td>${data.imei!}</td>
	      <td>${data.mid!}</td>
	      <td>${data.mediaName!}</td>
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
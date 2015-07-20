<title>用户管理</title>
<script type="text/javascript">
		function confirmIps(id){
		  var confirmIpsUrl="${base}/admin/user/user!confirmIps.action?id="+id;
		  if (confirm("您确定手动通过该订单吗？")) {
		            new Request({
				    url: confirmIpsUrl,
					onSuccess: function(responseText, responseXML){
					   var jsonObj = JSON.decode(responseText);
					   if(jsonObj["success"]){
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					      window.location.reload();
					   }else{
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					   }
					}
			}).get({'__t=':new Date().getTime()});
		  }
		  return false;
		}
</script>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="ipsList"/>
<form name="queryForm" action="${base}/admin/fund/fund!ipsList.action" method="get">
      <input name="userName" value="<#if userName??>${userName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      <#assign ipsorderStateArr=stack.findValue("@com.cai310.lottery.common.DrawingOrderState@values()") />
      <select name="ifcheck" onchange="document.forms['queryForm'].submit();return false;">
					<option value="">全部</option>
					<option value="1" <#if ifcheck??><#if ifcheck==1>selected="selected"</#if></#if>>已通过</option>
					<option value="0" <#if ifcheck??><#if ifcheck==0>selected="selected"</#if></#if>>未通过</option>
	  </select>
	  <select name="timeFrame" onchange="document.forms['queryForm'].submit();return false;">
                  <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
                  <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
                  <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
                  <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
      </select>
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">订单编号</td>
      <td>用户名</td>
      <td>真实姓名</td>
      <td>金额</td>
      <td>状态</td>
      <td>描述</td>
      <td>时间</td>
        <td>来源</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as ipsorder>
    	<#if ipsorder_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${ipsorder.id!}</td>
	      <td>${ipsorder.userName!}</td>
	      <td>${ipsorder.realName!}</td>
	      <td>${ipsorder.amount!}</td>
	      <td><#if ipsorder.ifcheck??><#if ipsorder.ifcheck==0>未通过</#if><#if ipsorder.ifcheck==1>已通过</#if></#if></td>
	      <td>${ipsorder.opration!}</td>
	      <td>${ipsorder.createTime!}</td>
	       <td><#if ipsorder.userWay??>${ipsorder.userWay.typeName!}<#else>网站</#if></td>
	      <td><#if ipsorder.ifcheck??><#if ipsorder.ifcheck==0><a href="#" onclick="confirmIps('${ipsorder.id!}');">手动通过订单</a></#if></#if></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="9">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
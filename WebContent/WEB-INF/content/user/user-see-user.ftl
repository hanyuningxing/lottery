<head>
	<title>会员管理</title>
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/pdzx.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript">window.BASESITE='${base}';</script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>	
	<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
	<script>
	  function seeUser(userId){
		    $floater1({
				width : 748,
				height : 448,
				src : window.BASESITE + '/user/user!seeUser.action?userId='+userId,
				title : '${userName}的下属',
				fix : 'true'
			});
	   }
	</script>
</head>

  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/user/user!userManeger.action' />" method="get" id="userForm">
	    <div class="crighttop">
	    	<span style="float:left"></span>
	   </div>
	 </form>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>用户ID</td>
	        <td>用户名</td>
	        <td>是否锁定 </td>
	        <td width="60">账户余额 </td>
	        <td width="70">消费总额</td>
	        <td width="50">用户返点 </td>
	        <td>注册时间 </td>
	        <td>操作 </td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		  <td height="32">${data_index+1}</td>
	      <td>${data.id}</td>
	      <td><a href="<@c.url value='/blog/${data.id}/' />" target="_blank">${data.userName}</a></td>
	      <td><#if data.locked??&&data.locked>是<#else>否</#if></td> 
	      <td>#{data.remainMoney;M2}</td>
	      <td>#{(data.consumptionMoney!0);M2}</td>
	      <td>#{(data.rebate!0);M2}</td>
	      <td <#if data.createTime??>title="${data.createTime?string("yyyy-MM-dd HH:mm")}"</#if>><#if data.createTime??>${data.createTime?string("MM-dd HH:mm")}</#if></td>
	      <td><a href="#" onclick = "seeUser(${data.id});return false;">查看下属</a></td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="12" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
</table>
 <div class="kong5"></div>
	        <!-- 翻页开始 -->
		            <#import "../../macro/pagination.ftl" as b />
			        <@b.page />
	  
	  
	  
	  
    <div class="kong10"></div>
</div>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.webName}-资讯频道-展开</title>
<meta name="keywords" content="网上购彩" />
<meta name="description" content="合买彩票" />
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/zxlb.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/zxpd.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/thinkbox.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/visit.js"/>"></script>
	<script src="${base}/js/common.js" type="text/javascript" ></script>

</head>
<body>
<!-- 头开始 -->
	<#include "/common/header.ftl" />
<!-- 头结束 -->

<!-- 内容开始 -->
<div id="index_center">
  
   <div class="zx_dqwz">
      <div class="zxzk_dqwz"><span>您当前的位置<a href="/">：${webapp.webName}彩票网</a>><span class="zx_ls">专题频道</span></span> </div>
   </div>
   <div class="shaxiang10"></div>
  <div id="zxlb_left">
   	
	<div class="zxlb_lb">
		    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; width:100%;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" align="center" border="1" >
			  <tr class="center_tablegray_zxlb" align="center">
				<td width="68%" height="28">活动专题 </td>
				
				<td width="32%">日期</td>
			  </tr>
			  <#if topicsList??&&topicsList?size gt 0>
			  		<#list topicsList as topic>
						<#if topic??>
					    	<tr class="center_trw_zxlb" align="center" onMouseOver="this.className='center_trhover_zxlb'" onMouseOut="this.className='center_trw_zxlb'">
								<td height="32" ><a href="${topic.titleLink!""}" target="_blank">${topic.shortTitle}</a></td>
								<td>${topic.createTime?string("yyyy-MM-dd")}</td>
						  	</tr>
	          			<#else>
	          				<tr class="center_trw_zxlb" align="center" onMouseOver="this.className='center_trhover_zxlb'" onMouseOut="this.className='center_trw_zxlb'">
								<td height="32" ><a href="/info/${topic.id!}.html" target="_blank">${topic.shortTitle}</a></td>
								<td>${topic.createTime?string("yyyy-MM-dd")}</td>
						  	</tr>
	     	    		</#if>
			  		</#list>
			  <#else>
			  		<tr  class="center_trgray_zxlb" align="center" onMouseOver="this.className='center_trhover_zxlb'" onMouseOut="this.className='center_trgray_zxlb'">
						暂无记录
			  		</tr>		
			  </#if>
			</table>
			<#import "../../../macro/pagination.ftl" as b />
  		   <@b.page />
	</div>
  </div>
  <div id="zxlb_right">
     <#include "../results/right-new-result.ftl" />
</div>
</div>
<!-- 内容结束 -->

<!-- 底部开始 -->
	   	<#include "/common/footer.ftl" />
</body>
</html>

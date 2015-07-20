<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh_cn">
	<head>
		<#include "/common/base.ftl" >
		<title>${title}</title>
		<link href="${base}/styles/adminDefault/theme.css" rel="stylesheet" type="text/css" />
		<script src="${base}/js/common.js" type="text/javascript" ></script>
		<script src="${base}/js/mootools-1.2.4-core-yc.js" type="text/javascript" ></script>
		${head}
	</head>
	<body>
		<#include "/common/adminHeader.ftl">
		
		<div id="content">
  			<@com.displayItem page.getProperty('meta.menu')!'' page.getProperty('meta.menuItem')!'' />
			<div id="content_right">
				<#if (page.getProperty('meta.error')!'') != 'true'>
					<#include "/common/message.ftl">
				</#if>
  				${body}
			</div>
		</div>
		
		<!-- <#include "/common/adminFooter.ftl"> -->
	</body>
</html>

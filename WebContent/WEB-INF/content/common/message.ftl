<meta name="decorator" content="trade" />
<style type="text/css">
.login_main{margin:90px auto 0 auto;width:320px;height:220px;line-height:150%;}
.login_main dt{float:left;padding-right:10px;}
.login_main a{color:#960001;text-decoration:underline;}
</style>
<link href="<@c.url value="/pages/css/dlsb.css"/>" rel="stylesheet" type="text/css" />
	
     <#if redirectURL??>
         <#assign redirectURLString=redirectURL?string />
		<#else>
		 <#assign redirectURLString='/' />   
     </#if>

     
	<div class="dlsb_main">
		<div class="dlsb_content">
		<div class="dlsb_left"><img src="<@c.url value="/pages/images/gth.gif"/>" /></div>
		<div class="dlsb_right"><div class="txt"><#if msgString??>${msgString!}</#if><br />
		                         如果页面长时间没有刷新，<br />
		                         <span>请点击。</span></div>
								<div class="dlsb_img"><a href="${redirectURLString}"><img src="<@c.url value="/pages/images/an_03.gif"/>" border="0"/>
		                         </a></div></div>
		</div>
	</div>
      <script language="javascript">
		  window.setTimeout(function(){window.location.href='${redirectURLString}';},2000);
	  </script>

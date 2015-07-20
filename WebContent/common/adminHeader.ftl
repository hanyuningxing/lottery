<div id="header">
  <div class="header_logo">
    <div class="logo"><img src="${base}/pages/index/images/logo.gif" style="margin-top:-2px" width="240px" height="60px"/ ></div>
    <!--<div class="title">
      <h1>管理后台</h1>
    </div>-->
  </div>
  <div class="header_menu">
    <div class="header_login_menu">
    	<#if adminUser??&&adminUser.loginName?? >
      	<span style="color:red;font-weight:bold">${adminUser.loginName!}</span>，欢迎您使用管理后台！
    	</#if>
	</div>
	<@com.displayMenu page.getProperty('meta.menu')!'' />
  </div>
</div>
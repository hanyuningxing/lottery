<!--<div class="topban">会员中心</div>
    <ul class="cleftmenu">
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/scheme!list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='scheme'>class="now"</#if>>购彩记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/lottery/chase!list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='chase'>class="now"</#if>>追号记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/lottery/auto!list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='auto'>class="now"</#if>>跟单记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund!list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>帐户明细查询</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund!payPer.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='payPer'>class="now"</#if>>充值</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund!drawingPer.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='drawingPer'>class="now"</#if>>提款</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund!drawingList.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='drawing'>class="now"</#if>>提款记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user!toValidateAccount.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='account'>class="now"</#if>>账号设置</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user!resetPasswd.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='resetPasswd'>class="now"</#if>>密码重置</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user!register.action"/>';});return false;"  href="#" <#if left_menu??&&left_menu=='register'>class="now"</#if>>成员注册</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user!userManeger.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='userManeger'>class="now"</#if>>成员管理</a></li>
    </ul> 
-->   
<div class="w1000">
  <div class="k10"></div>
  <div class="yhzxleft">
  	  <div class="padb10">
	  	  <div class="yhzxmenutit">
		  	  <span class="yhmenutit01"></span>
			  <span class="yhmenutitwz">账户安全</span>
			  <span class="yhmenutit"></span>
		  </div>
		  <div class="yhzxmenu">
		  	  <ul>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user_userSafeManager.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>安全中心</a></li>
			  </ul>			  
	    </div>
	  </div>
	  <div class="padb10">
	  	  <div class="yhzxmenutit">
		  	  <span class="yhmenutit02"></span>
			  <span class="yhmenutitwz">账户管理</span>
			  <span class="yhmenutit"></span>
		  </div>
		  <div class="yhzxmenu">
		  	  <ul>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund_list.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>资金明细</a></li>
      			  <li><a onclick="$SSO.login_auth(herder_checkIdCardAndRealNameExistForChongZhi('<@c.url value="/user/fund_payPer.html"/>'));return false;" href="#" <#if left_menu??&&left_menu=='payPer'>class="now"</#if>>我要充值</a></li>
     			  <li><a onclick="$SSO.login_auth(herder_checkIdCardAndRealNameExist('<@c.url value="/user/fund_drawingPer.html"/>'));return false;" href="#" <#if left_menu??&&left_menu=='drawingPer'>class="now"</#if>>我要提款</a></li>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/fund_drawingList.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='drawing'>class="now"</#if>>提款记录</a></li>
		          <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/user_toValidateAccount.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='account'>class="now"</#if>>个人资料</a></li>			  	  
			  </ul>
	    </div>
	  </div>
	  <div class="padb10">
	  	  <div class="yhzxmenutit">
		  	  <span class="yhmenutit03"></span>
			  <span class="yhmenutitwz">投注管理</span>
			  <span class="yhmenutit"></span>		  </div>
		  <div class="yhzxmenu">
		  	  <ul>
		      	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/scheme_list.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='scheme'>class="now"</#if>>投注记录</a><img src="${base}/V1/images/star.gif"></img></li>
			  	  <li><a href="#" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/user/scheme-temp!list.action"/>';});return false;">保存的方案</a></li>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/lottery/chase_list.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='chase'>class="now"</#if>>追号记录</a></li>
		          <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/lottery/auto_list.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='auto'>class="now"</#if>>跟单记录</a></li>
			  </ul>
	    </div>
	  </div>
	  <div class="padb10">
	  	  <div class="yhzxmenutit">
		  	  <span class="yhmenutit04"></span>
			  <span class="yhmenutitwz">网站服务</span>
			  <span class="yhmenutit"></span>		  </div>
		  <div class="yhzxmenu">
		  	  <ul>
			  	  <li><a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45">彩票邮寄/扫描</a></li>
			  	  <li><a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=52">我有话说</a></li>
			  </ul>
	    </div> 
	  </div>
	  <#if loginUser?? && (loginUser.userWay?? && loginUser.userWay=="AGENT")>
	   <div class="padb10">
	  	  <div class="yhzxmenutit">
		  	  <span class="yhmenutit04"></span>
			  <span class="yhmenutitwz">会员服务</span>
			  <span class="yhmenutit"></span>		  </div>
		  <div class="yhzxmenu">
		  	  <ul>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/agent/index_reg.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>会员管理</a></li>
			  </ul>
			  
			   <ul>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/agent/index_reg.html?type=link"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>生成注册链接</a></li>
			  </ul>
			  
			   <ul>
			  	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/agent/index_findAgentGroupInfoSum.html"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>团队总报表</a></li>
			  </ul>
	    </div> 
	  </div>
	</#if>	  
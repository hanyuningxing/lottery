<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value="/js/forum.js"/>"> </script>
<link rel="shortcut icon" href="<@c.url value="/favicon.ico"/>" type="image/x-icon" /> 
<script type="text/javascript">
	function openWind(url){
				var top=(document.body.clientHeight-420)/2;
				var left=(document.body.clientWidth-520)/2;
				window.open(url,'kaixinwindow', 'height=420, width=520, toolbar =no, menubar=no, scrollbars=yes, resizable=no,top='+top+',left='+left+', location=no, status=no');
			}
</script>
<!-- 头开始 -->
<div id="header">
  <div class="topbg">
    <div class="w980">
    
      <div class="tleft" id="login_ed_div" style="display:none"> 
         	您好，<span id="index_login_name"></span>
	        <a href="<@c.url value="/football/user.action"/>"  class="orangec">我的用户</a>
	        <a id="goto_logout" href="<@c.url value= "/football/user!logout.action"/>" onclick="$SSO.logout();return false;">[退出]</a>
       </div>
       <div class="tleft" id="un_login_div"> 
                         您好，欢迎来到-我的用户！&nbsp;&nbsp;<a href="<@c.url value= "/football/user!login.action"/>" onclick="$SSO.login_auth();return false;" class="orangec">登录</a><span>|</span>
                         
       </div>
    </div>
  </div>
</div>
<!-- 头结束 -->
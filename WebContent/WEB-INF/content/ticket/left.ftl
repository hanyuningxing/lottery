<div class="topban">出票中心</div>
    <ul class="cleftmenu">
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!logger_list.action"/>';});return false;"  href="#" <#if left_menu??&&left_menu=='logger'>class="now"</#if>>日志记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!process_list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='process'>class="now"</#if>>处理记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='scheme'>class="now"</#if>>出票记录</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!countList.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='count'>class="now"</#if>>出票统计</a></li>
      <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!fund_list.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund'>class="now"</#if>>资金明细</a></li>
   	  <li><a onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/ticket/user!fund_count.action"/>';});return false;" href="#" <#if left_menu??&&left_menu=='fund_count'>class="now"</#if>>资金统计</a></li>
    </ul>
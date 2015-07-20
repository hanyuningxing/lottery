<head>
	<title>会员管理</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="userManeger" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'userManeger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
    <div class="kong10"></div>
        
	  
    <div class="kong10"></div>
  </div>
</div>
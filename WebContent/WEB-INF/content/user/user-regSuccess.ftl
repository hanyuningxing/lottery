<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.webName}-注册页面</title>
<meta name="decorator" content="trade" />
<link href="css/cyyzc.css" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/lottery/count.js"/>"></script>	
<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>		
<script type="text/javascript" src="<@c.url value= "/js/lottery/luck.js"/>"></script>	
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/user/reg.js"/>"></script>

</head>

<body>
	<div id="wrapper">
	   <div class="line_process"></div>
	   <ul class=ul_process>
		  <li><strong>1</strong>填写注册信息</li>
		  <li class="act_process"><strong>2</strong>注册成功</li>
		  <li><strong>3</strong>充值购买彩票</li>
		  <li><strong>4</strong>中大奖</li>
		  <li class="last_process"><strong>5</strong>提取奖金</li>
	   </ul>
	   <div class="cyy_zcnr">
	      <div class="cyy_xinxik">
		     <div class="bigfont">恭喜您!<span>${msgString!}</span>注册成功，<br />祝您在${webapp.webName}网中大奖！请先完善您的资料</div>
			 <div class="cyy_chongzhi">
			 <INPUT type="image" src="images/cyy_ljcz_anniu.gif" value="" onclick="window.location.href='${base}/user/fund!payPer.action'"/>
			 <INPUT type="image" src="<@c.url value='/pages/images/cyy_yhzz_sjjhzscj.gif'/>" value="" onclick="window.location.href='${base}/user/user!toValidateAccount.action'"/>
			  </div>
			 <div class="xline"></div>
			 <div class="fzxx">·请及时完善您的 <a href="<@c.url value='/user/user!toValidateAccount.action'/>">提款资料</a>·您还可以 <a href="${base}/index.html>回首页看看</a>· <a href="${base}/user/scheme!list.action">去用户中心</a>·<a href="${base}/user/fund!payPer.action">充值预存款</a>·<a href="${base}/ssq/scheme!subList.action">去参加合买</a></div>
		  </div>
	   </div>
	   
    </div>
    <script language="javascript">
		  window.setTimeout(function(){window.location.href='<@c.url value="/user/user!userSafeManager.action"/>';},5000);
</script>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta name="decorator" content="passcountV1"/>
<meta name="menu" content="${webapp.ggtj}"/>
<title>${webapp.webName}-福利彩票|体育彩票|足球彩票网上合买代购平台,双色球,福彩3D，大乐透，排列三，排列五</title>
</head>
<body>
 
<div id="gg_main">
   <!--左边开始-->
  <div class="cleft">
    <!--左边开始-->
    <div class="ggtopban">过关统计</div>
    <div class="ggt01">足球彩票</div>
    <ul class="ggleftmenu">
      <li><a href="${base}/passcount/index.action?lottery=SFZC&pt=0" <#if lottery??&&lottery=='SFZC'&&pt??&&pt==0>class="now"</#if>>足彩胜负过关统计</a></li>
      <li><a href="${base}/passcount/index.action?lottery=SFZC&pt=1" <#if lottery??&&lottery=='SFZC'&&pt??&&pt==1>class="now"</#if>>任选9场过关统计</a></li>
      <li><a href="${base}/passcount/index.action?lottery=LCZC" <#if lottery??&&lottery=='LCZC'>class="now"</#if>>6场半全过关统计</a></li>
      <li><a href="${base}/passcount/index.action?lottery=SCZC" <#if lottery??&&lottery=='SCZC'>class="now"</#if>>4场进球过关统计</a></li>
    </ul>
  </div>
  <#assign includeFile="${lottery.key!}.ftl" />
   <!--右边开始-->
  <div class="cright">
    <script type="text/javascript">
             var isLogin=false;
             <#if user??>isLogin=true;</#if>
             function checkIsLogin(){
                   if(!isLogin){
                       window.alert("您还没登录。请先登录");
                   }
                   return isLogin;
             }
     </script>
	 <#include '${includeFile}' />
  </div>
</div>

</body>
</html>


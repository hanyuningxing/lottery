<link href="${base}/js/thinkbox/thinkbox.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" /> 
<link href="${base}/pages/index/css/lotto_head.css" rel="stylesheet" type="text/css" />
<!-- top -->
<!-- top -->
<div class="w990">
  <div class="wlogo"><a href="${base}/"><img src="${base}/pages/index/images/logo.gif" /></a></div>
    <div class="wlogor"> 
        <div id="un_login_div"> 
          您好，欢迎来到${webapp.webName}！
          <a href="${base}/user/user!login.action" onclick="$SSO.login_auth();return false;">登录</a>
          <em>|</em>
          <a href="javascript:void(0);" onclick="window.open('${base}/user/user!reg.action')"><span class="rc1">免费注册</span></a> 
          <em>|</em>
          <a href="javascript:void(0)" onclick="window.open('${base}/user/user!resetPasswordByEmail.action')" ><span class="gc0">忘记密码</span></a><br />
        </div>
        <div id="login_ed_div" style="display:none"> 
            您好，<span class="gc0" id="index_login_name"></span>
	        账户余额：<span class="rc1" id="_s_1" onclick="showMoney();" title="点击显示">******* 显示</span> 
	        <span class="rc1" id="_s_2" onclick="hiddenMoney();" title="点击隐藏" style="display:none"></span>
	        <a id="goto_logout" href="${base}/user/user!logout.action" onclick="$SSO.logout();return false;" class="blue">[退出]</a>
        </div>   
    	<a href="javascript:void(0)" onclick="$SSO.login_auth(function(){window.location.href = '${base}/user/scheme!list.action';});return false;">用户中心</a><em>|</em>
    	<a href="javascript:void(0)" onclick="$SSO.login_auth(function(){window.location.href = '${base}/user/fund!payPer.action';});return false;">充值</a><em>|</em>
    	<a href="javascript:void(0)" onclick="$SSO.login_auth(function(){window.location.href = '${base}/user/fund!drawingPer.action';});return false;">提款</a><em>|</em>
    	<a href="javascript:void(0)" onclick="$SSO.login_auth(function(){window.location.href = '${base}/user/scheme!list.action';});return false;">投注记录</a>
    </div>
  <div class="wkefu"><a href="#"><img src="${base}/pages/index/images/topright.gif" /></a></div>
</div>
<script type="text/javascript">
	       $SSO.refresh_login_info();
</script>
<!-- menu begin-->
<div id="topmenuall">
  <div id="topmenu">
    <ul class="tmenu">
        <li><a href="${base}/" class="now">首 页</a></li> <li><span></span></li>
        <li><a href="${base}/jczq/scheme!editNew.action">购买彩票</a></li> <li><span></span></li>
	    <li><a href="${base}/jczq/scheme!subList.action">合买大厅</a></li> <li><span></span></li>
	    <li><a href="${base}/results!index.action">开奖公告</a></li> <li><span></span></li>
	    <li><a href="${base}/info/news.action">彩票资讯</a></li> <li><span></span></li>
	    <li><a href="${base}/passcount/index!index.action">过关统计</a></li> <li><span></span></li>
	    <li><a href="${base}/info/analyseindex!index.action">图表走势</a></li> <li><span></span></li>
	    <li><a href="${base}/info/assist/assist.action?type=bzzx-1cjwt">帮助中心</a></li> <li><span></span></li>
    </ul>
    </ul>
  </div>
</div>
<!-- menu end-->
<!-- twonav begin -->
<div class="twonav">
  <!-- 足彩 -->
  <div class="twoicon twoicon01"></div>
  <div class="char1wz">
    <a href="${base}/sfzc/scheme!editNew.action?playType=SFZC14">胜负彩</a>&nbsp;&nbsp;
    	<a href="${base}/sfzc/scheme!editNew.action?playType=SFZC9">任选九场</a><br />
   		<a href="${base}/sczc/scheme!editNew.action">四场进球</a>&nbsp;&nbsp;
      	<a href="${base}/lczc/scheme!editNew.action">六场半全</a>
  </div>
  <div class="tline"></div>
  <!-- 北单 -->
  <div class="twoicon twoicon02"></div>
  <div class="char2wz">
    <a href="${base}/dczc/scheme!editNew.action?playType=SPF">胜平负</a>&nbsp;&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=BF">比分</a>&nbsp;&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=ZJQS">总进球</a><br />
    <a href="${base}/dczc/scheme!editNew.action?playType=BQQSPF">半全场</a>&nbsp;&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=SXDS">上下单双</a></div>
  <div class="tline"></div>
  <!-- 竞彩 -->
  <div class="twoicon twoicon03"></div>
  <div class="char3wz">
    <a href="${base}/jczq/scheme!editNew.action">竞彩足球</a> <br />
      	<a href="${base}/jclq/scheme!editNew.action">竞彩篮球</a>
   
  </div>
  <div class="tline"></div>
  <!-- 福彩 -->
  <div class="twoicon twoicon04"></div>
  <div class="char4wz">
    	<a href="${base}/ssq/scheme!editNew.action">双色球</a>
	    <a href="${base}/welfare3d/scheme!editNew.action">福彩3D</a><br />
    	<!--<a href="${base}/welfare36to7/scheme!editNew.action" >好彩1</a><br />-->
    	<a href="${base}/seven/scheme!editNew.action">七乐彩</a>
    </div>
  <div class="tline"></div>
  <!-- 体彩 -->
  <div class="twoicon twoicon05"></div>
  <div class="char5wz">
   		<a href="${base}/dlt/scheme!editNew.action">大乐透</a>&nbsp;&nbsp;
        <a href="${base}/pl/scheme!editNew.action?playType=0">排列三</a><br />
        <a href="${base}/pl/scheme!editNew.action?playType=1">排列五</a>&nbsp;&nbsp;
        <!--<a href="${base}/tc22to5/scheme!editNew.action" >22选5</a>-->
  </div>
  <div class="tline"></div>
  <!-- 快彩 -->
  <div class="twoicon twoicon06"></div>
  <div class="char6wz">
 	 <a href="${base}/sdel11to5/scheme.action">山东11选5</a><br />
    	<!--<a href="${base}/el11to5/scheme.action"   style="color:#cccccc"  onclick="javascript:return false;">江西11选5</a> -->
    	<!--<a href="${base}/gdel11to5/scheme.action" >广东11选5</a><br /> -- >
    	<!--<a href="${base}/qyh/scheme.action" >山东群英会</a>-->
    	<a href="${base}/ssc/scheme.action">重庆时时彩</a>
  </div>
</div>
<div class="k10"></div>
<script type="text/javascript">
     function showMoney(){ 
	      document.getElementById('_s_2').style.display="";
          document.getElementById('_s_1').style.display="none";
	 }
	 function hiddenMoney(){ 
	      document.getElementById('_s_1').style.display="";
          document.getElementById('_s_2').style.display="none";
	 }
  </script>  
<!-- 头结束 -->
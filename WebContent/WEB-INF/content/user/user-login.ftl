<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>球客彩票网</title>
<meta name="Keywords" content="球客彩票网" />
<meta name="Description" content="球客彩票网" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="${base}/jquery/jquery-1.8.3.js"></script>
<script src="${base}/jquery/ui/js/jquery-ui-1.9.2.custom.js"/></script>
<link href="${base}/jquery/ui/css/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/ssologin.js"></script> 
<link href="${base}/V1/css/indexuse.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/jczq.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/js/head_common.js"></script>
<style>
   .time_new{width:200px;position:absolute;left:798px;top:70px;height:30px;line-height:30px;font-weight:bold;color:#DBF5FB;}
   .timegreenchar{ display:inline-block;padding:1px 3px; margin:0px 1px; text-align:center; font-family: Arial, Helvetica,"宋体"; color:#00FF33; font-weight:bold;}

</style>


<script>
window.BASESITE='${base}';

	//set返回信息
   	function setCommonResult(msg,success) {
		$("#commonResultDialogData").empty();
		$("#commonResultDialogData").html(msg);
		if(success) {
			$("#tckico").addClass("tckico01");
   		} else {
			$("#tckico").addClass("tckico02");
   		}
		$("#commonResultDialog").dialog('open');
		
   	}
   
	//用于创建dialog
   function createDialog_header(id_, title_, width_, height_) {
		$(id_).dialog({  
			autoOpen:false,
			title:title_,
			width:width_, 
			height:height_, 
			modal:true
		});
	}

$(function(){
	createDialog_header('#commonResultDialog', '返回信息', 350, 219);
	$('#userLoginForm').submit(function() {
	     var userName = $('#userLoginForm [name="userName"]').val();
		 var password = $('#userLoginForm [name="password"]').val();
		 var captcha = $('#userLoginForm [name="captcha"]').val();
		  if(userName==null || (userName.trim && userName.trim()=="")){		 	
		 	setCommonResult('用户名不能为空或全为空格!', false);
		 	return false; 
		 
		 }  else if(password==null || (password.trim && password.trim()=="")){
		 	setCommonResult('登录密码不能为空或全为空格!', false);
		 	return false; 
		 }
		
		 $.post('${base}/user/user!login.action', {
    		 userName: userName,
    		 password: password,
    		 captcha: captcha
		 }, function(data) {    		 
	     		if(data && data.success) { 
	     			$SSO.forum_login(data);	     			
	     			if(data.needRedirect) {
	     				window.location = data.redirectUrl;
	     			}else {    			
	     				window.location = window.BASESITE + '/';
	     			}	     			
				} else if(data.captcha_error){
					setCommonResult('验证码不正确!', false);
					if(data.need_captcha) {
						document.getElementById('captcha').style.display = "";
					}
				} else {
					setCommonResult('用户名或密码不正确!', false);
					if(data.need_captcha) {
						document.getElementById('captcha').style.display = "";
					}
				}		
		}, 'json');
		 
	     return false;  //阻止表单提交
	});
});
</script>
</head>

<!--公共结果对话框 -->
<div id="commonResultDialog" style="padding: 0px;display:none"> 
	<div class="tckbg">	
			<div class="tcknr">
				<table width="400" border="0" cellspacing="0" cellpadding="0" align="center">
	       			<tr>
						<td width="50"><span id="tckico" class="tckico01"></span></td>
						<td><span class="tckwz" id="commonResultDialogData"></span></td>						
					</tr>	
					<tr align="center">
						<td>&nbsp;</td>
						<td><div style="padding: 0px 150px 0px 25px;"><a href="javascript:;" onclick="$('#commonResultDialog').dialog('close');" class="tckan"></a></div>	</td>						
					</tr>	
				</table>
			</div>
		
	</div>
</div>  
</div>
<!-- top -->
<#--<div class="w1000"><a href="javascript:;"><img src="${base}/V1/images/topad.jpg"/></a><div class="k10"></div></div>-->
<div class="top100">
  <div class="t1000wz"> <a href="javascript:;" class="qklogo"></a>
    <div class="qklogoc">足球彩票<br />
      投资专家</div>
 	
	<!--
	<div class="qkkefubtwz"> <a href="http://chat.live800.com/live800/chatClient/chatbox.jsp?companyID=291999&jid=2659558135&enterurl" target="_blank" class="qkkefubt"></a></div>
    <div class="toptelicon_new"></div>
    <div class="toptel_new">客服热线:4006884944</div>-->
   <div class="qklogin_kefu">
	<div class="qklogin_kefuicon"></div>
	<div class="qklogin_kefuname">客服热线</div>
	<span class="qklogin_kefutel">400-688-4944</span>
  </div>
    <div class="qkkefubtwz"> <a href="http://chat.live800.com/live800/chatClient/chatbox.jsp?companyID=291999&jid=2659558135&enterurl" target="_blank" class="qkkefubt"></a></div>
  </div>
  <div class="w1000" style="z-index:9999">
  	<div id="top_windowChange" style="z-index:9998">
  		<div class="topwindowwz"></div>
	    <div id="top_window" class="ty_topwindow" style="display:none">
	      <!-- jing cai begin -->
	      <div class="ty_buylottery_icon01"></div>
	      <div class="fl l5"> <a href="${base}/jczq/scheme!editNew.action">胜平负</a>&nbsp;&nbsp;<a href="${base}/jczq/scheme!editNew.action">让球胜平负</a><br />
	        <a href="${base}/jczq/scheme!editNew.action?passMode=SINGLE&playType=BF">单关比分</a>&nbsp;&nbsp;<a href="${base}/jczq/scheme!editNew.action?passMode=SINGLE&playType=JQS">总进球</a><br />
	        <a href="${base}/jczq/scheme!editNew.action?passMode=PASS&playType=BF">过关比分</a>&nbsp;&nbsp;<a href="${base}/jczq/scheme!editNew.action?passMode=PASS&playType=BQQ">半全场</a>&nbsp;&nbsp;&nbsp;<img src="${base}/V1/images/new3a.gif"/><br />
	        <a href="${base}/jczq/scheme!editNew.action?passMode=MIX_PASS">混合过关</a>&nbsp;&nbsp;<a href="${base}/jczq/scheme!editNew.action?playTypeWeb=EXY">盘口(生死盘)</a></div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- jing cai end -->
	      <!-- basketball -->
	      <div class="ty_buylottery_icon02"></div>
	      <div class="fl l5"> <a href="${base}/jclq/scheme!editNew.action?passMode=SINGLE&playType=RFSF">让分胜负</a>&nbsp;&nbsp;<a href="${base}/jclq/scheme!editNew.action?passMode=SINGLE&playType=SFC">单关胜分差</a><br />
	        <a href="${base}/jclq/scheme!editNew.action?passMode=PASS&playType=DXF">大小分</a>&nbsp;&nbsp;<a href="${base}/jclq/scheme!editNew.action?passMode=PASS&playType=SFC">过关胜分差</a><br />
	        <a href="${base}/jclq/scheme!editNew.action?passMode=PASS">胜负</a>&nbsp;&nbsp;<a href="${base}/jclq/scheme!editNew.action?passMode=MIX_PASS">混合过关</a> </div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- basketball end-->
	      <!-- sfc -->
	      <div class="ty_buylottery_icon03"></div>
	      <div class="fl l5 t5"> <a href="${base}/sfzc/scheme!editNew.action?playType=SFZC14">胜负彩</a>&nbsp;&nbsp;<a href="${base}/sfzc/scheme!editNew.action?playType=SFZC9">任选九</a><br />
	        <a href="${base}/sczc/scheme!editNew.action">四场进球</a>&nbsp;&nbsp;<a href="${base}/lczc/scheme!editNew.action">六场半全场</a><br />
	      </div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- sfc end-->
	      <!-- dc -->
	      <div class="ty_buylottery_icon04"></div>
	     <div class="fl l5 t5"> <a href="${base}/dczc/scheme!editNew.action">胜平负</a>&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=BF&shareType=SELF">比分</a>&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=ZJQS&shareType=SELF">总进球</a><br />
	        <a href="${base}/dczc/scheme!editNew.action?playType=BQQSPF&shareType=SELF">半全场</a>&nbsp;&nbsp;<a href="${base}/dczc/scheme!editNew.action?playType=SXDS&shareType=SELF">上下单双</a><br />
	      </div>
	       <!-- dc end-->
	    </div>
    </div>
    <!-- 弹出彩种菜单 end-->
    
    <script>
    		$(function(){
    			$("#top_windowChange").hover(		   								
					function () {
						document.getElementById("top_window").style.display = "";										   
					},
					function () {
						document.getElementById("top_window").style.display = "none";							   									  
					}
				 );
    		});
    			
    </script>
    <div id="topmenu">
      <ul id="navs" class="tmenu">
        <li><a id="navs0" href="${base}/" class="now" >首页 </a></li>
        <li><a id="navs1" href="${base}/jczq/scheme!subList.action">合买大厅</a></li>
        <li><a id="navs2" href="${base}/phone/phone!index.action">手机购彩</a></li>
        <li><a id="navs3" href="${base}/passcount/index!index.action">过关统计</a></li>
        <li><a id="navs4" href="${base}/jczq/scheme!wonList.action?playType=MIX">中奖查询</a></li>
        <li><a id="navs5" href="${base}/results!index.action">开奖结果</a></li>
        <li><a id="navs6" href="http://61.147.127.247:8012/forum.php" target="_blank">彩民论坛</a></li>
        
      </ul>
    </div>
    <#--<div class="toptelicon"></div>-->
    <div class="time_new">
          		网站时间: <span id="count_m" class="timegreenchar">--</span>月<span id="count_d" class="timegreenchar">--</span>日<span id="count_h" class="timegreenchar">--</span>时<span id="count_i" class="timegreenchar">--</span>分
			      	<script type="text/javascript">
						function get_cur_time(){ 
						    $.ajax({ 
								type: 'GET',
								cache : false,
								url:  window.BASESITE+'/serviceTime.jsp',
								success : function(data, textStatus) {
								    set_cur_time(data);
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									set_cur_time(new Date().getTime());
								}
							});
						}
						function set_cur_time(cur_time){
							cur_time = parseInt(cur_time,10);
							var cur_time_d = new Date(cur_time);
							var m = (cur_time_d.getMonth()+1).toString();
							var d = (cur_time_d.getDate()).toString();
							var h = (cur_time_d.getHours()).toString();
							var i = (cur_time_d.getMinutes()).toString();
							$("#count_m").html(m);
							$("#count_d").html(d);
							$("#count_h").html(h);
							$("#count_i").html(i);
							next_time = cur_time + 60000;
							setTimeout("set_cur_time(next_time)",60000);
						}
						$(document).ready(function(){
							var cur_time = get_cur_time();
						});
					</script></div>
  </div>
  <!-- two menu begin -->
  <div class="w1000">
    <div class="twomenuwz">
      <!-- 01 begin -->
      <a href="${base}/jczq/scheme!matchNine.action">
      <div class="i01wz">
        <div class="iwzline"></div>
        <div class=" bgall i01"></div>
        <div class="bgall i01name1"></div>
        <div class="i01name1char">中了8场奖金更高</div>
      </div>
      </a>
      <!-- 02 begin -->
      <a href="${base}/jczq/scheme!editNew.action">
      <div class="i02wz">
        <div class="iwzline"></div>
        <div class=" bgall i02"></div>
        <div class="bgall i02name1"></div>
        <div class="bgall i02icon"></div>
        <div class="i02name1char"><span>如何在竞足得到高额盈利回报?</span></div>
      </div>
      </a>
      <!-- 03 begin -->
      <a href="${base}/jclq/scheme!editNew.action">
      <div class="i03wz">
        <div class="iwzline"></div>
        <div class=" bgall i03"></div>
        <div class="bgall i03name1"></div>
        <div class="i03name1char">NBA季后赛激战</div>
      </div>
      </a>
      <!-- 04 begin -->
      <a href="${base}/dczc/scheme!editNew.action">
      <div class="i04wz">
        <div class="iwzline"></div>
        <div class=" bgall i04"></div>
        <div class="bgall i04name1"></div>
        <div class="i04name1char">赔率或许更高</div>
      </div>
      </a>
      <!-- 05 begin -->
      <a href="${base}/sfzc/scheme!editNew.action?playType=SFZC14">
      <div class="i05wz">
        <div class="bgall i05"></div>
        <div class="bgall i05name1"></div>
        <div class="i05name1char">能中500万 </div>
      </div>
      </a> </div>
  </div>
</div>
<div class="k10"></div>   
<!-- 头结束 -->
<script type="text/javascript">
	//菜单当前页显示统一管理  各个模板页定义menu_id 唯一值
	var menu_id= $("meta[name=menu]").attr('content');
	try{
		$("#navs li a").removeClass();
		var idx=(typeof menu_id =="undefined")?0:menu_id;
	    $("#navs"+idx).addClass('now');	    
	}catch(e){}
</script>
   <div class="loginbg">
	<div class="logindiv">
		<div style="padding:28px 25px;">
			<div class="logintit black_666_14"><strong>用户登陆</strong></div>
			<div style="padding:30px 20px;">
			<form action="<@c.url value="/user/user!login.action" />" method="post" id="userLoginForm">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tr>
	                  <td width="21%" align="left" class="black_666_14">用户名</td>
	                  <td colspan="3" align="left"><input name="userName" id="userName" type="text" value="" class="logininput" /></td>
	                </tr>
	                <tr>
	                  <td height="10" colspan="4"></td>
	                </tr>
	                <tr>
	                  <td align="left" class="black_666_14">密&nbsp;&nbsp;&nbsp;码</td>
	                  <td colspan="3" align="left"><input name="password" type="password" id="password" class="logininput" /></td>
	                </tr>
	                <tr>
	                  <td height="10" colspan="4"></td>
	                </tr>	                
	                <tr id="captcha" style="display:none">
	                  <td align="left" class="black_666_14">验证码</td>
	                  <td width="108" align="left"><input name="captcha" id="captcha" type="text" class="logininput2" /></td>
	               	          
	                  <td align="left"><img id="yanzhengma" alt="验证码" title="验证码" src="${base}/vcode.jpg" id="loginVcodeImg" style="cursor: pointer;" align="absmiddle"  width="90" height="33"/></td>
	                  <td align="right"><a href="#" class="loginsx button3" onclick="document.getElementById('yanzhengma').src='${base}/vcode.jpg?t'+new Date().getTime();return false;"></a></td>
	                </tr>	                
	                <tr>
	                  <td height="18" colspan="4"></td>
	                </tr>
	                <tr>
	                  <td align="center"></td>
	                  <td colspan="2" align="center"><a href="javascript:void(0);" onclick="javascript:$('#userLoginForm').submit(); return false;" class="loginbut button3"></a></td>
	                  <td align="center">&nbsp;</td>
	                </tr>
	                <tr>
	                  <td height="25" align="center" valign="bottom">&nbsp;</td>
	                  <td height="25" colspan="2" align="center" valign="bottom"><a href="${base}/user/user!resetPasswordByEmail.action" class="black_666_12">忘记密码</a> <a href="${base}/user/user!reg.action" class="black_666_12">免费注册</a></td>
	                  <td height="25" align="center" valign="bottom">&nbsp;</td>
	                </tr>
	              </table>
              </form>
			</div>
		</div>
	</div>
</div>
<#include "/V1/common/footer.ftl" />
</body>
</html>
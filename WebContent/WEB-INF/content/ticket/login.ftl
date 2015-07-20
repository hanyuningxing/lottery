<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户登录</title>
<meta name="decorator" content="none" />
<style type="text/css">
body{margin:0;padding:0;font-size:12px}
div{font-size:12px;font-family:Arial, Helvetica, sans-serif;line-height:150%;}
form,li,ul,dl{padding:0;margin:0}
a{text-decoration:none;color:#000}
a:hover{text-decoration:underline;color:#960000}
.clear{clear:both;}
.btn_send{background:url(${base}/pages/images/btn_common.jpg) no-repeat;border:0;color:#FFF;width:88px;height:30px;line-height:30px;cursor:pointer}
.error{padding-top:10px;color:red}
.login_title{background:url(${base}/pages/images/uc_login_title.png) no-repeat;}
.login_title ul{width:358px;padding:0;margin:0;clear:both}
.login_title li{float:left;height:28px;line-height:28px;text-align:left;font-size:14px;list-style-type:none}
.login_title #nor{text-indent:25px;font-weight:bold;width:117px;}
.login_title #ten_pay a{padding-left:35px;width:70px;}
.login_title #ali_pay a{padding-left:70px;width:30px;}
.login_title a{color:#000;display:inline-block;}
.login_title a:hover{color:#960000}

.login_table{border:1px solid #CCC;border-top:0;padding:20px;text-align:center;background:#FFF}
.login_table td{padding-bottom:5px;}
.login_table .input_txt{border:1px solid #CCC;height:22px;line-height:22px;padding-left:3px;width:170px;font-family:Arial, Helvetica, sans-serif}

#userquickLogin{width:360px;margin:20px auto 0 auto;}
.quick_link{text-align:center;padding-top:10px;}
.quick_link a{display:inline-block;width:110px}
</style>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	<#if needCaptcha!false >top.$(".thinkcontent iframe").css('height',324);</#if>
	$("#userName").focus();
	$("#login_form").submit(function(){
        $("#submit").attr('disabled',true);
        if(check_form()){
            var userName = encodeURIComponent($("input[name=userName]").val());
            var password = $("input[name=password]").val();
            var captcha = $("input[name=captcha]").val();
            var hasVcode = document.getElementById('captcha') != null;
            $.ajax({
                type: "post",
                url : "<@c.url value= "/ticket/user!login.action"/>",
                dataType:'json',
                async:false,
                data: {userName:userName,password:password,captcha:captcha},
                success:function(json){
					if(json.success == true){
						 try{
						 	window.location.href = json.redirectUrl;
						}catch( ee ){}
					}else if(json.need_captcha == true && !hasVcode){
						window.location.reload();
					}else{
						var msg = getCommonMsg(json);
						alert(msg);
                    	reload_vercode_img();
                    	$("#submit").attr('disabled',false);
					}
                },
                error:function(){
                    alert('网络错误，请重试');
                    $("#submit").attr('disabled',false);
                }
            });
        }else{
            $("#submit").attr('disabled',false);
        }
        return false;
    });
    $("#submit").attr('disabled',false);
});

function check_form(){
    var userName = document.getElementById('userName');
    var password = document.getElementById('password');
    var captcha = document.getElementById('captcha');
    
    if(!check_input_is_null(userName,"用户名") || !check_input_is_null(password,"密码"))
		return false;
    else
    	return true;
}

function check_input_is_null(input, msg){
    if(input.value==""){
        alert("请填写"+msg);
        input.focus();
        return false;
    }else{
        return true;
    }
}

function reload_vercode_img(){
	$("#captcha_img").attr("src","${base}/vcode.jpg?seed="+Math.random().toString());
}

</script>
</head>
<body>

<div id="userquickLogin">
	<div class="login_title">
		<ul>
			<li id="nor">用户登录</li>
	        <li id="ten_pay"></li>
	        <li id="ali_pay"></li>
		</ul>
	    <ul class="login_table">  
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <form id="login_form" action="" method="post" autocomplete="off">
		            <tr>
		            	<td colspan="2" align="center" class="error">您还没登录您的帐号</td>
		            </tr>
		            <tr>
		                <td width="25%">用户名：</td>
		                <td width="75%" align="left"><input name="userName" id="userName" tabindex="1" type="text" class="input_txt" maxlength="16" size="30"/></td>
		            </tr>
		            <tr>
		                <td>密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
		                <td align="left"><input type="password" name="password" id="password" tabindex="2" class="input_txt" size="30"/></td>
		            </tr>
		            
		            ${needCaptcha!}
		            <#if needCaptcha!false >
		            <tr>
		                <td>验证码：</td>
		                <td align="left"><input id="captcha" name="captcha" type="text" style="width: 60px;" maxlength="4" size="4" class="input_txt"  tabindex="3" /></td>
		            </tr>
		            <tr>
                	  <td></td>
                	  <td style="padding: 0pt;" align="left">输入下图中的字符，不区分大小写<br>
                		<img title="看不清？点击换一个" id="captcha_img" anme onclick="reload_vercode_img();" src="${base}/vcode.jpg" complete="complete"  />
                		<script type="text/javascript">reload_vercode_img();</script><br>
                		<a href="javascript:reload_vercode_img();" tabindex="-1">看不清楚，换一个！</a>
            		  </td>
            	    </tr>
            	    </#if>
		            <tr>
		                <td></td>
		                <td align="left"><input id="submit" type="submit" class="btn_send" value="登录" /></td>
		            </tr>
		        </form>
		    </table>
		</ul>
	</div>
	<div class="quick_link">
	</div>
</div>
</body>
</html>
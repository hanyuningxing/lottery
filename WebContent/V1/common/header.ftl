<head>
<link rel="shortcut icon" href="${base}/favicon.ico" type="image/x-icon" /> 
<link href="${base}/V1/css/indexuse.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/yhzx.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/tlogin.css" rel="stylesheet" type="text/css" />
<meta property="qc:admins" content="146062547261153106375" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="${base}/js/popup.js"></script>
<script type="text/javascript" src="${base}/js/cookie.js"></script>
<script type="text/javascript" src="${base}/js/head_common.js"></script>
<script type="text/javascript" src="<@c.url value= "/js/user/bindMobile.js"/>"></script>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="100563038"  charset="utf-8"></script>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-callback="true" charset="utf-8"></script>
<link href="<@c.url value= "/pages/css/payper.css"/>" rel="stylesheet" type="text/css" />
<style>
.h30{  line-height:29px; height:29px; font-size:12px; padding-left:10px; }
.topxialawz{ padding:8px 18px 8px 10px;  background:#fff; line-height:24px; font-size:12px;display:inline-block;_float:left;}
.topxialawz a,.topxialawz a:visited{ color:#666; text-decoration:none;}
.topxialawz a:hover{ color:#666; color:#ff6600; text-decoration:underline;}
.topxialabg{background: url(xiala_icon.png) no-repeat;}
.topxiala_alipay{ background-position:0 0; display:inline-block; width:14px; height:16px;}
.topxiala_qq{ background-position:0 -25px; display:inline-block; width:16px; height:16px;}
.topxiala_top{ background-position:0 -95px; display:inline-block; width:7px; height:5px;}
.topxiala_bottom{ background-position:-9px -95px; display:inline-block; width:7px; height:5px;}
.cb{ clear:both;}
</style> 
<style>
   .toptel_new{width:180px;position:absolute;left:730px;top:22px;height:30px;line-height:30px;font-weight:bold;font-size:14px;color:#EAF3FC;}
   .toptelicon_new{width:27px;position:absolute;left:700px;background:url(${base}/V1/images/allbg.png) -110px 0;top:20px;height:30px;}
   
   .time_new{width:200px;position:absolute;left:798px;top:70px;height:30px;line-height:30px;font-weight:bold;color:#DBF5FB;}
   .timegreenchar{ display:inline-block;padding:1px 3px; margin:0px 1px; text-align:center; font-family: Arial, Helvetica,"宋体"; color:#00FF33; font-weight:bold;}
	.xiugaibg {
		width:80px;
		height:30px;
		line-height:30px;
		color:#fff;
		text-align:center;
		background:url(${base}/V1/images/xiugaibg.png) no-repeat;
		display:inline-block;
		text-decoration:none;
		cursor:pointer;
		margin:0 auto;
	}
	a.xiugaibg, a.xiugaibg:visited, a.xiugaibg:hover {
		color:#fff;
		text-decoration:none;
	}
	.input1 {
		width:200px;
		height:20px;
		line-height:20px;
		color:#999;
		border:1px solid #A4ACB4;
		margin:0;
		padding:0;
	}
	.tdright10 {
		padding-right:10px;
	}
	.tishiicon{ width:18px; height:15px; background:url(${base}/V1/images/b.gif) no-repeat; display:inline-block; float:left; }
	
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
	
	.quick_link{text-align:center;padding-top:10px;}
	.quick_link a{display:inline-block;width:110px}
</style>

</head>
<div style="display:none">
<!--手机验证对话框 -->
<div class="tlogin" id="msgValidateDialog" >
  <div class="tlogintwbg2">
    <div style="width:448px" class="mobilechar" id="content"></div>
    <form id="msgValidateform" >
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr height="50">
	        <td class="tdr8px" style="width:128px">验证码</td>
	        <td width="210px"><input name="yanzhengma" id="yanzhengma" type="text" class="tlogininput" value="输入验证码" onclick='if(this.value=="输入验证码")this.value="";'/></td>
	        <td width="140px"><input type="button" id="getMsgCode" value="获取验证码" class="sendyzm"/><span id="error" style="color:red"></span></td>
	      </tr>
	      <tr height="50">
	        <td>&nbsp;</td>
	        <td><a  href="javascript:;" class="redloginbt" onclick="javascript:$('#msgValidateform').submit(); return false;">确定</a>&nbsp;<a href="javascript:;" id="return" onclick="reInputMobile();"  class="redloginbt">返回</a></td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	</form>
  </div>
</div>
<!--手机号码输入对话框 -->
<div class="tlogin" id="mobileWriteDialog">
  <div class="tlogintwbg1">
    <div class="mobilechar">请输入需要绑定的手机号码</div>
    <form id="mobileWriteForm" >
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr height="50">
	        <td class="tdr8px" style="width:128px">手机号码</td>
	        <td width="320"><input name="mobile" id="mobile" type="text" class="tlogininput" value="输入手机号码" onclick='if(this.value=="输入手机号码")this.value="";'/></td>
	      </tr>
	      <tr height="50">
	        <td  >&nbsp;</td>
	        <td><a href="javascript:;"  onclick="javascript:$('#mobileWriteForm').submit(); return false;" class="redloginbt">确定</a>&nbsp;<a  href="javascript:;" onclick="$('#mobileWriteDialog').dialog('close');" class="redloginbt">返回</a></td>
	      </tr>
	    </table>
    </form>
  </div>
</div>
<!-- 充值-->
<div id="chongzhi" style="display:none">
  	 <!--右边开始-->
   <div class="yhzxright">
    <div class="border04">
      
	  <div style="padding:20px 10px; font-size:14px;">
	  <form action="<@c.url value="/user/fund!commTraceMarker.action" />" method="post" id="commTraceMarker_form"> 	  
	     <input name="payWay" value="1" type="hidden"/>
      	<input name="checkSpan" value="false" type="hidden"/>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px dashed #ccc;">
          <tr>
            <td width="11%" height="40" align="right" class="black_222_14">用户名：</td>
            <td class="rc1"><#if loginUser??>${loginUser.userName}</#if></td>
          </tr>
        </table>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="40" colspan="2" class="black_222_14"><strong>请输入充值金额及选择网上银行进行充值</strong></td>
          </tr>
          <tr>
            <td width="11%" height="40" align="right"><span class="rc1">*</span>充值金额：</td>
            <td><input name="commTraceForm.amount" type="text" value="100.00" style="border:1px solid #CCC; width:98px; height:22px; padding:0 5px;"  onclick="if('请输入充值金额'==this.value){this.value='';return false;}"/> 
              元<span class="blue_0091d41" style="font-size:12px;">（金额格式：100.00，如要参加充值赠送活动，请务必完善手机、邮箱、身份证且手机号已经过验证） </span></td>
         	<span id="error_msg"><#include "/common/message.ftl" /><span>
          </tr>
          <tr>
            <td height="10" colspan="2"></td>
          </tr>
          <tr>
            <td align="right" valign="top"><span class="rc1">*</span>选择银行：</td>
            <td><table border="0" cellspacing="0" cellpadding="0" class="chongzhi" style="border-bottom:1px dashed #CCC; border-right:1px dashed #CCC;">
              <tr>                           
                <td width="149" height="45" onclick="chkit('ICBC')">
	                <a href="javascript:void(0);" class="czyh01 czdiv">
	                	<div class="czyhon">
	               		<input name="commTraceForm.bankName" type="radio" value="ICBC" id="ICBC" checked="checked" />
	                	</div>
	                </a>
                </td>
                
                <td width="149" height="45" onclick="chkit('ALIPAY_ABC')">
	                <a href="javascript:void(0);" class="czyh02 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="ABC" id="ALIPAY_ABC" />
	                	</div>
	                </a>
                </td>
                
               <td width="149" height="45" onclick="chkit('ALIPAY_CCB')">
	                <a href="javascript:void(0);" class="czyh03 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="CCB" id="ALIPAY_CCB" onclick="chkit('ALIPAY_CCB')"/>
	                	</div>
	                </a>
                </td>
               
               <td width="149" height="45" onclick="chkit('ALIPAY_CMBC')">
	                <a href="javascript:void(0);" class="czyh04 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="CMBC" id="ALIPAY_CMBC" onclick="chkit('ALIPAY_CMBC')"/>
	                	</div>
	                </a>
                </td>
              </tr>
              <tr>                
                <td width="149" height="45" onclick="chkit('ALIPAY_BOC')">
	                <a href="javascript:void(0);" class="czyh05 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="BOC" id="ALIPAY_BOC"/>
	                	</div>
	                </a>
                </td>
                
                <td width="149" height="45" onclick="chkit('ALIPAY_CMBBANK')">
	                <a href="javascript:void(0);" class="czyh06 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="CMBBANK" id="ALIPAY_CMBBANK"  />
	                	</div>
	                </a>
                </td>
                
                 <td width="149" height="45" onclick="chkit('ALIPAY_SPDB')" >
	                <a href="javascript:void(0);" class="czyh07 czdiv">
	                	<div class="czyhon">
	                		<input name="commTraceForm.bankName" type="radio" value="SPDB" id="ALIPAY_SPDB" />
	                	</div>
	                </a>
                </td>
               
                 <td width="149" height="45" onclick="chkit('ALIPAY_SHBANK')">
	                <a href="javascript:void(0);" class="czyh08 czdiv">
	                	<div class="czyhon">
							<input name="commTraceForm.bankName" type="radio" value="SHBANK" id="ALIPAY_SHBANK" />
	                	</div>
	                </a>
                </td>
              </tr>
              <tr>
	                <td width="149" height="45" onclick="chkit('ALIPAY_COMM')" >
		                <a href="javascript:void(0);" class="czyh09 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="COMM" id="ALIPAY_COMM" onclick="chkit('ALIPAY_COMM')" />
		                	</div>
		                </a>
	                </td>
                
              		<td width="149" height="45" onclick="chkit('ALIPAY_CEB')">
		                <a href="javascript:void(0);" class="czyh10 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="CEB" id="ALIPAY_CEB" />
		                	</div>
		                </a>
	                </td>
               
               		<td width="149" height="45" onclick="chkit('ALIPAY_CITIC')">
		                <a href="javascript:void(0);" class="czyh11 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="CITIC" id="ALIPAY_CITIC" />
		                	</div>
		                </a>
	                </td>
               
                	<td width="149" height="45" onclick="chkit('ALIPAY_SPABANK')">
		                <a href="javascript:void(0);" class="czyh12 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="SPABANK" id="ALIPAY_SPABANK" />
		                	</div>
		                </a>
	                </td>
              </tr>
              <tr>
              		<td width="149" height="45" onclick="chkit('ALIPAY_CIB')">
		                <a href="javascript:void(0);" class="czyh13 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="CIB" id="ALIPAY_CIB" />
		                	</div>
		                </a>
	                </td>
                
               		<td width="149" height="45" onclick="chkit('ALIPAY_SDB')" >
		                <a href="javascript:void(0);" class="czyh14 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="SDB" id="ALIPAY_SDB"/>
		                	</div>
		                </a>
	                </td>
                
               		<td width="149" height="45" onclick="chkit('ALIPAY_GDB')" >
		                <a href="javascript:void(0);" class="czyh15 czdiv">
		                	<div class="czyhon">
								<input name="commTraceForm.bankName" type="radio" value="GDB" id="ALIPAY_GDB" />
		                	</div>
		                </a>
	                </td>
                
	                <td width="149" height="45" onclick="chkit('ALIPAY_ALIPAY')">
	                <a href="#" class="czyh16 czdiv">
	                  <div class="czyhoff">
	                    <input type="radio" name="commTraceForm.bankName" value="ALIPAY" id="ALIPAY_ALIPAY"/>
	                  </div>
	                </a></td>
              </tr>
            </table>            </td>
          </tr>
          <tr>
            <td height="20" colspan="2"></td>
          </tr>
          
          <tr>
            <td align="right" valign="top">&nbsp;</td>
            <td><a id="rechargeLotteryCard" onclick="javascript:confirmChongZhi();" style="width:147px; height:34px; background:url(${base}/V1/images/chongzhi.jpg) 0 0 no-repeat; display:block;"></a></td>
          </tr>
        </table>
        </from>
	    <br />
	    <br />
	    <br />
	    <br />
	    <br />
	    <br />
	  </div>
    </div>
  </div>
</div>
<!-- 跟踪充值支付-->
<div id="payPer" style="display:none" class="zftsk">
		<div class="zfts_bg">
		   <div class="dlfk">登录支付宝付款</div>
		</div>
		<div class="zftsnr">
		  <div class="wcfk_tu"></div>
		  <div class="wcfk_zi">请您在新打开的页面上完成付款。</div>
		  <div class="zfsm">付款完成前请不要关闭此窗口。<br />
		       完成付款后请根据您的情况点击下面的按钮：
		  </div>
		  <div class="fkanjg">
		    <a class="wcfk_an" href="javascript:tradeQuery();">已完成付款</a>
			<a class="fkywt_an" href="<@c.url value= "/info/assist/assist.action?type=bzzx-3czzn"/>">付款遇到问题</a>
		  </div>
		</div>
		 <br />
	    <br />
	    <br />
	    <br />
   </div>
<!--忘记密码对话框 -->
<div id="dialogForgotPassword">  	
	<form action="${base}/user/user!updatePasswd.action" method="post" id="header_forgotPwd">			 
		<table width="480px" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;margin:2px 12px 10px 12px;">
           <tr height="33">
			    <td colspan="3"><div style="width:460px;border:1px solid #D6C07F;background:#FFFDE5; font-size:12px; color:#9E3D12; padding:6px 10px 4px 10px; padding-top:4px; line-height:18px;  text-align:left;">
<div class="tishiicon"></div>欢迎您使用忘记密码，请输入用户名和该用户的注册手机</div></td>
		    </tr>	
		  <tr height="44">
		   <td width="60" align="right" class="tdright10">用户名</td>
		    <td><input type="text" name="header_fp_userName" id="header_fp_userName" class="input1" /></td>
		    <td><div id="header_fp_userNameTip" style="width:160px"></div></td>		    
		  </tr>
		  <tr height="44">
		    <td width="60" align="right" class="tdright10">手机</td>
		    <td><input type="text" name="header_fp_phone" id="header_fp_phone" class="input1" /></td>
		    <td><div id="header_fp_phoneTip" style="width:160px"></div></td>
		  </tr>
		  <tr height="44">
		    <td align="right"></td>
		    <td>
		        <a href="javascript:;" class="xiugaibg" onclick="javascript:header_forgotPwdSubmit(); return false;">发送短信</a>
		    </td>
		    <td>&nbsp;</td>
		  </tr>
		</table>
	 </form>
</div>
<!--身份信息认证对话框 -->
<div class="tlogin" id="header_setIdCardDialog">
  <div class="tlogintwbg2">
    <div class="loginbackyel"  style="width:448px"><img src="${base}/V1/images/icon_wenhao.gif" width="12" height="13" /> 操作前请先完成真实姓名验证，以便中奖后核对信息，保护帐号安全。</div>
    <form id="header_setIdCardForm">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr height="50">
	        <td class="tdr8px" width="128">真实姓名</td>
	        <td width="220"><input name="head_realName" id="head_realName" type="text" class="tlogininput" value="真实姓名" onclick='if(this.value=="真实姓名")this.value="";'/></td>
	        <td width="150" class="char12"><div id="head_realNameTip" style="width:120px"></div></td>
	      </tr>
	      <tr height="50">
	        <td  >&nbsp;</td>
	        <td><a href="javascript:;" onclick="header_setIdCardFormSubmit(); return false;" class="redloginbt">确定</a>&nbsp;<a href="javascript:;" onclick="$('#header_setIdCardDialog').dialog('close');" class="redloginbt">返回</a></td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	 </form>
  </div>
</div>
<!--快速登录对话框 -->
<div id="userquickLogin">
	<!--
	<div class="login_title" style="padding-bottom: 5px;margin-top: -10px;">
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
		                <td width="50%" align="left"><input name="userName_quick" id="userName_quick" tabindex="1" type="text" class="input_txt" maxlength="16" size="30"/></td>
		                <td><a style="color:#0091D4" href="${base}/user/user!reg.action">免费注册</a></td>
		            </tr>
		            <tr>
		                <td>密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
		                <td align="left"><input type="password" name="password_quick" id="password_quick" tabindex="2" class="input_txt" size="30"/></td>
		                <td><a style="color:#0091D4" href="javascript:;" id="header_forgot_pwd_quick">忘记密码</a></td>
		            </tr>
		            
		            <tr id="yanzhengma" style="display:none">
		                <td>验证码：</td>
		                <td align="left"><input id="captcha_quick" name="captcha_quick" type="text" style="width: 60px;" maxlength="4" size="4" class="input_txt"  tabindex="3" /></td>
		            </tr>
		            <tr id="yanzhengmaImg" style="display:none">
                	  <td></td>
                	  <td style="padding: 0pt;" align="left">输入下图中的字符，不区分大小写<br>
                		<img title="看不清？点击换一个" id="captcha_img" anme onclick="reload_vercode_img();" src="${base}/vcode.jpg" complete="complete"  />
                		<a href="javascript:reload_vercode_img();" tabindex="-1">看不清楚，换一个！</a>
            		  </td>
            	    </tr>
            	   
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
	-->
	<div class="tlogin">
	  <div class="tlogintwbg">
	  <form id="login_form" action="" method="post" autocomplete="off">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	      <tr height="50">
	        <td class="tdr8px"><div class="loginusericon"></div></td>
	        <td width="220"><input name="userName_quick" id="userName_quick" type="text" class="tlogininput" value="用户名"/></td>
	        <td><a href="${base}/user/user!reg.action" class="tloginlink">免费注册</a></td>
	      </tr>
	      <tr height="50">
	        <td class="tdr8px"><div class="loginpassicon"></div></td>
	        <td><input type="text" name="password_quick" id="password_quick" class="tlogininput"  value="密码"/></td>
	        <td><a href="javascript:;" id="header_forgot_pwd_quick" class="tloginlink">忘记密码</a></td>
	      </tr>
	      <tr height="50" id="yanzhengma_quick" style="display:none">
	        <td class="tdr8px"></td>
	        <td colspan="2"><input type="text" value="验证码" id="captcha_quick" name="captcha_quick" style="width:65px" class="tlogininput" maxlength="4" size="4" tabindex="3"/>&nbsp;&nbsp;&nbsp;&nbsp;<img title="看不清？点击换一个" id="captcha_img" anme onclick="reload_vercode_img();" src="${base}/vcode.jpg" complete="complete"  />
	        	<a href="javascript:reload_vercode_img();" tabindex="-1">看不清楚，换一个！</a>
	        </td>
	        
	      </tr>     
	      <tr height="50">
	        <td class="tdr8px">&nbsp;</td>
	        <td><a href="#" class="logintbt" onclick="$('#login_form').submit();"> </a></td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	  </form>
	  </div>
	</div>
</div>

<!--公共结果对话框 -->
<div id="commonResultDialog" style="padding: 0px;"> 
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
<div id="header" class="top100">
  <div class="t1000wz"> <a href="${base}/" class="qklogo"></a>
    <div class="qklogoc">足球彩票<br />
      投资专家</div>

 	<div class="qklogin_middle" id="login_ed_div" style="display:none">
	     <div class="qk_logined">   
			       您好，<span id="index_login_name"></span>&nbsp;&nbsp;&nbsp;&nbsp; 
			       账户余额：<span id="_s_1" onclick="showMoney();" title="点击显示">******* 显示</span> &nbsp;&nbsp;
			       <span id="_s_2" onclick="hiddenMoney();" title="点击隐藏" style="display:none"></span>
			       [<a id="goto_logout" href="${base}/user/user_logout.html" onclick="$SSO.logout();return false;" class="whitechar">退出</a>] <br />
			<a href="${base}/user/user_userSafeManager.html" class="whitechar">用户中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${base}/user/fund_payPer.html" class="whitechar" id="header_payPer_a">充值</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${base}/user/fund_drawingPer.html" class="whitechar" id="header_drawingPer_a">提款</a>&nbsp;&nbsp;|&nbsp;&nbsp;
			<a href="${base}/user/scheme_list.html" class="whitechar">投注记录</a>
		</div>
 	</div>
 	
 	  
	
    <#if loginUser??>
    <div class="qklogin_middle">
	     <div class="qk_logined">
	                   您好，<span>${loginUser.userName}</span>&nbsp;&nbsp;&nbsp;&nbsp; 
	                   账户余额：<span id="_s_1_" onclick="showMoney_();" title="点击显示">******* 显示</span> &nbsp;&nbsp;  
		        <span id="_s_2_" onclick="hiddenMoney_();" title="点击隐藏" style="display:none">现金:${loginUser.remainMoney?string.currency}&nbsp;隐藏</span>
		        [<a id="goto_logout" href="${base}/user/user_logout.html" onclick="$SSO.logout();return false;" class="whitechar">退出</a>] <br />		     
		    	<a href="${base}/user/user_userSafeManager.html" class="whitechar">用户中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${base}/user/fund_payPer.html" class="whitechar" id="header_payPer_a_1">充值</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${base}/user/fund_drawingPer.html" class="whitechar" id="header_drawingPer_a_1">提款</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${base}/user/scheme_list.html" class="whitechar">投注记录</a>
	      </div>
    </div>
    
      <#else>    
      
      <div class="qklogin_middle" id="un_login_div">
	     <form action="${base}/user/user_login.html" method="post" id="userLoginForm">
    		<input type="hidden" name="loginflag" value="1">   		
		      <table width="100%" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="25"><img src="${base}/V1/images/top_login_left.gif" align="left"/></td>
			          <td width="110"><input type="text"  name="userName" id="loginUserName" value="用户名" class="qklogininput"/>&nbsp;</td>
			          <td></td>
			          <td width="110"><input name="password" id="loginPassword" type="text"  value="密码" class="qklogininput"/>&nbsp;</td>
			          <td><a class="qkloginbt" href="javascript:void(0);" onclick="javascript:$('#userLoginForm').submit(); return false;"/></td>
			          <td class="l10" width="300"> 
			          <a href="${base}/user/user!reg.action">免费注册</a>&nbsp;|&nbsp;
			          <a href="javascript:;" id="header_forgot_pwd">忘记密码</a>&nbsp;|&nbsp;
			          </td>
			          <td>
    					<div class="qkkefubtwz" align="center"> 
    						<span id="qqLoginBtn"><img src="${base}/V1/images/Connect_logo_7.png"/>
    						</span>
    					</div>			          
			          </td>
			          <td width="20"><img src="${base}/V1/images/top_login_rig.gif" align="right"/></td>
			          
			        </tr>
	      	</table> 
      	 </form>     	      	 
    	</div>  	
    	<script type="text/javascript">
		    QC.Login({
		       btnId:"qqLoginBtn"    //插入按钮的节点id
			});
			</script>
			<script type="text/javascript">
		
				var paras = {};
				QC.api("get_user_info", paras)
		  		.success(function(s){
		    		var nickName=s.data.nickname;
		  			QC.Login.getMe(function(openId, accessToken){
		  				$.ajax({
		 				type:'POST',
						cache:'false',
						dataType:'json',
						data:{'nickName':nickName,'qqId':openId},
						url:window.BASESITE+'/user/user!qqLogin.action',
						success:function(jsonObj){
							QC.Login.signOut();
							$SSO.refresh_login_info();
						}
						});
		  			});
		  		})
		   		.error(function(f){
		
		 		 })
		  		.complete(function(c){
					
		  		});
		</script>
	</#if>
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
  <div class="w1000"  style="z-index:999">
  	<div id="top_windowChange" style="z-index:998">
  		<div class="topwindowwz"></div>
	    <div id="top_window" class="ty_topwindow" style="display:none">
	      <!-- jing cai begin -->
	      <div class="ty_buylottery_icon01"></div>
	      <div class="fl l5"> <a href="${base}/jczq/PASS.html">胜平负</a>&nbsp;&nbsp;<a href="${base}/jczq/PASS.html">让球胜平负</a><br />
	        <a href="${base}/jczq/BF-SINGLE.html">单关比分</a>&nbsp;&nbsp;<a href="${base}/jczq/JQS-SINGLE.html">总进球</a><br />
	        <a href="${base}/jczq/BF-PASS.html">过关比分</a>&nbsp;&nbsp;<a href="${base}/jczq/BQQ-PASS.html">半全场</a>&nbsp;&nbsp;&nbsp;<img src="${base}/V1/images/new3a.gif"/><br />
	        <a href="${base}/jczq/MIX_PASS.html">混合过关</a>&nbsp;&nbsp;<a href="${base}/jczq/new/EXY.html">盘口(生死盘)</a>
	      </div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- jing cai end -->
	      <!-- basketball -->
	      <div class="ty_buylottery_icon02"></div>
	      <div class="fl l5"> <a href="${base}/jclq/RFSF-SINGLE.html">让分胜负</a>&nbsp;&nbsp;<a href="${base}/jclq/SFC-SINGLE.html">单关胜分差</a><br />
	        <a href="${base}/jclq/DXF-PASS.html">大小分</a>&nbsp;&nbsp;<a href="${base}/jclq/SFC-PASS.html">过关胜分差</a><br />
	        <a href="${base}/jclq/PASS.html">胜负</a>&nbsp;&nbsp;<a href="${base}/jclq/MIX_PASS.html">混合过关</a> </div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- basketball end-->
	      <!-- sfc -->
	      <div class="ty_buylottery_icon03"></div>
	      <div class="fl l5 t5"> <a href="${base}/sfzc/SFZC14.html">胜负彩</a>&nbsp;&nbsp;<a href="${base}/sfzc/SFZC9.html">任选九</a><br />
	        <a href="${base}/sczc/">四场进球</a>&nbsp;&nbsp;<a href="${base}/lczc/">六场半全场</a><br />
	      </div>
	      <div class="cb"></div>
	      <div class="ty_line"></div>
	      <!-- sfc end-->
	      <!-- dc -->
	      <div class="ty_buylottery_icon04"></div>
	      <div class="fl l5 t5"> <a href="${base}/dczc/">胜平负</a>&nbsp;&nbsp;<a href="${base}/dczc/BF-SELF.html">比分</a>&nbsp;&nbsp;<a href="${base}/dczc/ZJQS-SELF.html">总进球</a><br />
	        <a href="${base}/dczc/BQQSPF-SELF.html">半全场</a>&nbsp;&nbsp;<a href="${base}/dczc/SXDS-SELF.html">上下单双</a><br />
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
    		
    		$("#head_realName").keydown(function(e) {
				  if(e.keyCode == 13) {
				       header_setIdCardFormSubmit();
				       return false;
				 }
			});
    </script>
    
    <div id="topmenu" style="z-index:0">
      <ul id="navs" class="tmenu">
        <li><a id="navs0" href="${base}/" class="now" >首页 </a></li>
        <li><a id="navs1" href="${base}/jczq/PASS.html">购买彩票</a></li>
        <li><a id="navs2" href="${base}/phone/phone_index.html">手机购彩</a></li>
        <li><a id="navs3" href="${base}/passcount/index_index.html">过关统计</a></li>
        <li><a id="navs4" href="${base}/jczq/wonList-MIX.html">中奖查询</a></li>
        <li><a id="navs5" href="${base}/jczq/scheme_review.html">开奖结果</a></li>
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
      <a href="${base}/jczq/RENJIU.html">
      <div class="i01wz">
        <div class="iwzline"></div>
        <div class=" bgall i01"></div>
        <div class="bgall i01name1"></div>
        <div class="i01name1char">中了8场奖金更高</div>
      </div>
      </a>
      <!-- 02 begin -->
      <a href="${base}/jczq/PASS.html">
      <div class="i02wz">
        <div class="iwzline"></div>
        <div class=" bgall i02"></div>
        <div class="bgall i02name1"></div>
        <div class="bgall i02icon"></div>
        <div class="i02name1char"><span>如何在竞足得到高额盈利回报?</span></div>
      </div>
      </a>
      <!-- 03 begin -->
      <a href="${base}/jclq/PASS.html">
      <div class="i03wz">
        <div class="iwzline"></div>
        <div class=" bgall i03"></div>
        <div class="bgall i03name1"></div>
        <div class="i03name1char">NBA季后赛激战</div>
      </div>
      </a>
      <!-- 04 begin -->
      <a href="${base}/dczc/">
      <div class="i04wz">
        <div class="iwzline"></div>
        <div class=" bgall i04"></div>
        <div class="bgall i04name1"></div>
        <div class="i04name1char">赔率或许更高</div>
      </div>
      </a>
      <!-- 05 begin -->
      <a href="${base}/sfzc/SFZC14.html">
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

      		var userName = document.getElementById("loginUserName");
      		var password =  document.getElementById("loginPassword");
      		var userName_quick = document.getElementById("userName_quick");
      		var password_quick = document.getElementById("password_quick");
      		var captcha_quick = document.getElementById("captcha_quick");
      		
      		captcha_quick.onfocus = function() {
      			captchaOnfocus(this);
      		}
      		
      		userName_quick.onfocus = function(){
      			userNameOnfocus(this);
      		};
      		password_quick.onfocus = function(){
      			passwordOnfocus(this);
      		};
      		
      		if(userName){
      			userName.onfocus = function(){
	      			userNameOnfocus(this);
	      		};
      		}
      		
      		if(password) {
      			password.onfocus = function(){
	      			passwordOnfocus(this);
	      		};
      		}
      		
      		function captchaOnfocus(captcha) {     			
      			var captchaVal = captcha.value;     		
      			if(captchaVal == "验证码") {
      				captcha.value = "";
      			}
      			
      			captcha.onblur = function() {     			
	      			var captchaVal = captcha.value;     		
	      			if(captchaVal == "") {
	      				captcha.value = "验证码";
	      			}
      			}
      		}
      		
      		function userNameOnfocus(userName) {     			
      			var userNameVal = userName.value;     		
      			if(userNameVal == "用户名") {
      				userName.value = "";
      			}
      			
      			userName.onblur = function() {     			
	      			var userNameVal = userName.value;     		
	      			if(userNameVal == "") {
	      				userName.value = "用户名";
	      			}
      			}
      		}
      		
      		function passwordOnfocus(password) { 
      			var passwordVal = password.value;      		     			
      			if(passwordVal == "密码") {
      				password.value = "";
      				var clone_password = password.cloneNode();
      				clone_password.onblur = function() {
      					if(clone_password.value == "") {     						
	      					password.value = "密码";
	      					clone_password.parentNode.insertBefore(password, clone_password);
							clone_password.parentNode.removeChild(clone_password);
						}
      				}
      				
					clone_password.setAttribute('type', 'password');
					password.parentNode.insertBefore(clone_password, password);
					clone_password.focus();
					password.parentNode.removeChild(password);
					
					$("#loginPassword").keydown(function(e) {
				    	if(e.keyCode == 13) {
				        	$('#userLoginForm').submit();
					    }
					});
					
					$("#password_quick").keydown(function(e) {
				    	if(e.keyCode == 13) {
				        	$('#login_form').submit();
					    }
					});
      			}
      		}     		     		
      		

$(document).ready(function(){
createDialog_header('#userquickLogin','欢迎登录球客彩票网-祝您中大奖', 410);
	$("#login_form").submit(function(){
        if(check_form()){
            var userName = document.getElementById('userName_quick').value;
            var password = document.getElementById('password_quick').value;
            var captcha = document.getElementById('captcha_quick').value;
            $.ajax({
                type: "post",
                url : "${base}/user/user!login.action?type=quick_login",
                dataType:'json',
                async:false,
                data: {userName:userName,password:password,captcha:captcha},
                success:function(json){               	
					if(json.success == true){
						//同步登录论坛
						 parent.$SSO.forum_login(json);
						if(parent != null && parent.$SSO != null && parent.$SSO.loginSuccessCall != null){							 	
							 parent.$SSO.loginSuccessCall();
						}	 	
						$('#userquickLogin').dialog('close');
						parent.$SSO.refresh_login_info();
					}else {
						if(json.need_captcha == true){
							document.getElementById("yanzhengma_quick").style.display = "";
						}						
						var msg = getCommonMsg(json);
						setCommonResult(msg,false);
 					}
                },
                error:function(){
                    setCommonResult('网络错误，请重试', false);
                }
            });
        }
        return false;
    });
});

function check_form(){
    var userName = document.getElementById('userName_quick');
    var password = document.getElementById('password_quick');
    var captcha = document.getElementById('captcha');
    
    if(!check_input_is_null(userName,"用户名") || !check_input_is_null(password,"密码"))
		return false;
    else
    	return true;
}

function check_input_is_null(input, msg){
    if(input.value==""){
        setCommonResult("请填写"+msg, false);
        input.focus();
        return false;
    }else{
        return true;
    }
}

function reload_vercode_img(){
	$("#captcha_img").attr("src","${base}/vcode.jpg?seed="+Math.random().toString());
}

 function showMoney(){ 
	  document.getElementById('_s_2').style.display="";
      document.getElementById('_s_1').style.display="none";
}

 function showMoney_(){ 
	  document.getElementById('_s_2_').style.display="";
      document.getElementById('_s_1_').style.display="none";
}
 function hiddenMoney(){ 
	  document.getElementById('_s_1').style.display="";
      document.getElementById('_s_2').style.display="none";
}
 function hiddenMoney_(){ 
	  document.getElementById('_s_1_').style.display="";
      document.getElementById('_s_2_').style.display="none";
}

</script>


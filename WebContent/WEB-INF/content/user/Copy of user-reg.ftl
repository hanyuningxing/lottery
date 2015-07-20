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
<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value="/js/jquery/jquery.cookie.js"/>"> </script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript"> 
$(document).ready(function(){

		$.formValidator.initConfig({formid:"userCreateForm",onerror:function(msg){ },onsuccess:function(){ return true;}});
		
		
		checkForm();
		
	    //保存
		$("#userCreateFormSubmit").click(function(){
				$("#span_register").show();
				$("#userCreateFormSubmit").attr("disabled",true);
				if(jQuery.formValidator.pageIsValid()){
					$("#userCreateForm").submit();
				} else {
				    $("#span_register").hide();
					$("#userCreateFormSubmit").removeAttr("disabled");
				}
		});
		
});

function checkForm(){
		$("#userName").formValidator({onshow:"请输入用户名",onfocus:"用户名3-12个字符,不能全为数字",oncorrect:"该用户名可以注册"}).inputValidator({min:3,max:24,onerror:"你输入的用户名非法,请确认"})
		.functionValidator({
		    fun:function(value,elem){
				  if(/^\d{3,12}$/.test(value)){
						return "用户名不能全为数字.";
				 }
				 if(!/^[a-zA-Z0-9\u4E00-\u9FBF_]{3,12}$/.test(value)){
				   return "用户名3-12个字符,不允许包含逗号、分号等";
				 }
				 
			     if(value.indexOf('${webapp.webName}')!=-1||value.indexOf('客服')!=-1||value.indexOf('网管')!=-1||value.indexOf('管理员')!=-1||
					value.indexOf('system')!=-1||value.indexOf('administrator')!=-1||value.indexOf('webmaster')!=-1||value.indexOf('master')!=-1){
			      return "用户名不能包含敏感词.";
			     }
			     var mesg = '';
				 $.ajax({
					type : "post",
					url : window.BASESITE + '/user/user!checkUserRegAble.action',
					data:"regForm.userName=" + encodeURI(value),
					async: false,
					error: function(){alert("服务器没有返回数据,可能服务器忙，请重试");},
					success : function(data){
						mesg = data.success;
					}
				});
				if (mesg) {
				  	return true;		
				} else {
			    return "该用户名已被注册!请您再换一个";
			    }
			    return true;
			 }
		});
		

		$("#password").formValidator({onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:6,max:15,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"请输入6-15位字母和数字组成的密码"})
		.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位字母和数字组成的密码,字母区分大小写"});
		
		
		$("#confirmPassword").formValidator({onshow:"请输入重复密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"重复密码不能为空,请确认"}).compareValidator({desid:"password",operateor:"=",onerror:"两次输入的密码不一致,请确认"})
		.regexValidator({regexp:"^([a-zA-Z0-9]){6,15}$",onerror:"请输入6-15位字母和数字组成的密码,字母区分大小写"});
		
		$("#captcha").formValidator({onshow:"请输入验证码",onfocus:"验证码不能为空",oncorrect:"验证码合法",onerror:"验证码不能为空,请确认"})
	    .functionValidator({
    			fun:function(value, element) {
    					if(value!=""){
	    					var mesg = '';
							$.ajax({
								type : "post",
	        					url : window.BASESITE + '/user/user!checkCodeRegAble.action',
	        					data:"captcha=" + value,
	        					async: false,
	        					error: function(){alert("服务器没有返回数据,可能服务器忙，请重试");},
	        					success : function(data){
	        						mesg = data.success;
	        					}
							});
							if (mesg) {
								  	return true;		
								}else{
								   	return false;		
								}
						} else {
					    return "请输入验证码";
					    }
    			}
    		});
    }
	   
	
function openWind(url){
	var top=(document.body.clientHeight-420)/2;
	var left=(document.body.clientWidth-520)/2;
	window.open(url,'window', 'height=420, width=520, toolbar =no, menubar=no, scrollbars=yes, resizable=no,top='+top+',left='+left+', location=no, status=no');
}
</SCRIPT>
<body>
	<div id="wrapper">
	   <div class="line_process"></div>
	   <ul class=ul_process>
		  <li class="act_process"><strong>1</strong>填写注册信息</li>
		  <li><strong>2</strong>注册成功</li>
		  <li><strong>3</strong>充值购买彩票</li>
		  <li><strong>4</strong>中大奖</li>
		  <li class="last_process"><strong>5</strong>提取奖金</li>
	   </ul>
	   <div class="cyy_zcnr">
	   	  <!-- right_name cznr_wzts error_num   list_div_ys  text_inp_yanse-->
	     <form action="<@c.url value='/user/user!create.action' />" method="post" id="userCreateForm">
	     <input type="hidden" name="request_token" value="${datetime().millis}" />
	     <#include "/common/message.ftl" />
	      <div class="list_div">
		      <font>用户名：</font>
		      <div><input class=text_inp type=text id="userName" name="regForm.userName"></div>
		      <div id="userNameTip" style="width:250px"></div>	
		  </div>
		  <div class="list_div" id="password_list_div">
		      <font>登录密码：</font>
		      <div><input class=text_inp type=password id="password" name="regForm.password"></div>
		      <div id="passwordTip" style="width:250px"></div>	
		  </div>
		  <div class="list_div" id="confirm_password_list_div">
		      <font>确认密码：</font>
		      <div><input class=text_inp type=password id="confirmPassword" name="regForm.confirmPassword"></div>
		      <div id="confirmPasswordTip" style="width:250px"></div>	
		  </div>
		  <div class="list_div" id="captcha_list_div">
		      <font>验证码：</font>
		      <div><input class=text_inp_short type=text id="captcha" name="captcha"></div>
			  <div><img class="act_list_div" src="${base}/vcode.jpg" id="RIC"/></div>
		      <div id="captchaTip" style="width:85px"></div>
		              看不清？<A href="#" onclick="javascript:document.getElementById('RIC').src='${base}/vcode.jpg?t'+new Date().getTime();">换一张</A>	
		  </div>
		  <div align="center"><span id="span_register" style="display:none;">正在提交注册信息，请稍等...</span></div>
		  <div class=cyy_tyzc_anniu>
		  <INPUT type="image" id="userCreateFormSubmit" src="images/cyy_anniu.gif" value="同意以下协议，提交注册"/>
		  </div>
		   </form>
	   </div>
	   <div class="box_xieyi">
	      <div class=block_xieyi>${webapp.webName}网服务协议
		     <div>一．服务协议的确定 
               <P>　　${webapp.webName}彩票网（www.cai310.com）提供的各项基于互联网以及通信网的相关服务（以下称"服务"）的所有权及经营运作权归肇庆优盛科技(以下简称 “${webapp.webName}”)所有。依照本协议以下条款本网登记注册的高级用户（以下简称“用户”），方有资格享受${webapp.webName}提供的代购彩票服务，当用户使用某一或若干${webapp.webName}提供的服务时，您和本网站应受针对并适用于该服务且已发布的相应的指引和规定的约束。
               </P><br />

			   二．服务协议的修订和新增

               <P>　　“${webapp.webName}”有权在必要时修改服务条款，服务条款一旦发生变动，将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的服务。如果用户继续享用服务，则视为接受服务条款的变动。 
               <br />　　“${webapp.webName}”将不时提供其它服务，而这些其它服务可能是受不同的服务条款和条件约束的，若新服务未发布服务条款和条件时，则适用于本条款，其未尽事宜，“${webapp.webName}”拥有最终解释权利。
               </P><br />

			   三．用户信息的提供 

               <P>　　为保障用户的合法权益，避免在中奖时因用户注册资料与真实情况不符而发生纠纷，请用户注册时务必按照真实、全面、准确的原则填写。对因用户自身原因而造成的不能兑奖情况，${webapp.webName}概不负责。如果用户提供的资料包含有不正确的信息，本网保留结束该用户使用服务资格的权利。
               </P><br />

			   四．用户资料隐私和用户资料安全 
               <P>　　“${webapp.webName}”承诺对用户注册之隐私信息绝对保密。未经用户授权或同意，不得擅自将用户信息用于处理用户委托代购行为以外的其他活动，涉及相关法律事件除外。
			   <br />　　“${webapp.webName}”对用户注册信息提供最大限度的安全保障。同时，用户务必对其用户密码、个人帐号等信息自行保密，免被盗用或篡改。另外每个用户都要对其帐户中的所有活动和事件负全责。“用户”同意若发现任何非法使用用户帐号或安全漏洞的情况，用户如发现请立即与${webapp.webName}联系。 
              </P><br />

			  五．用户享有的权利 
              <P>　　1、用户有权随时对个人登记资料进行查询、修改。为保护用户资金安全，所有注册用户名称，身份证以及相关银行帐户账号不能更改。 
              <br />　　2、用户可免费使用网站的网上分析系统，进行彩票分析，或查询历史投注情况和帐户资金管理。 
              <br />　　3、委托个人代购的用户，有权在购买彩票三个月内，得到其购买的彩票原件（中奖票除外），邮政快递费用自理。
			  </P><br />

			  六．用户管理 
              <P>　　用户不得利用本网危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本网制作、复制和传播下列信息： 
            　<br />　　（一）煽动抗拒、破坏宪法和法律、行政法规实施的； 　
			  <br />　　（二）煽动颠覆国家政权，推翻社会主义制度的； 
　            <br />　　（三）煽动分裂国家、破坏国家统一的； 　
              <br />　　（四）煽动民族仇恨、民族歧视，破坏民族团结的； 
            　<br />　　（五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的； 　
			  <br />　　（六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的； 
　            <br />　　（七）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的； 　
              <br />　　（八）损害国家机关信誉的； 
　            <br />　　（九）其他违反宪法和法律行政法规的； 　
              <br />　　（十）进行商业广告行为的。 
　            <br />　　若用户的行为违反以上承诺，${webapp.webName}将做出独立判断，立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在${webapp.webName}上散布和传播反动、色情或其他违反国家法律的信息，${webapp.webName}的系统记录有可能作为用户违反法律的证据。 
              </P><br />

			  七．用户提款须知 
              <P>　　为了保障您在本站的资金安全，您的取款要求首先要经过我们财务人员核对，核对无误后通过网上办理汇款，对有明显套现或洗钱意图（充值不消费即提款）的用户本站将采用原路退回的原则处理，由于受银行处理时间的制约到帐时间将有可能超过半月。
			  </P><br />

			  八．服务的信息储存及担保 

              <P>　　“${webapp.webName}”将在为用户提供服务的过程中，竭尽可能地维护和完善${webapp.webName}提供的各种服务。用户在使用${webapp.webName}络过程中自行承担风险，${webapp.webName}不担保服务一定能满足用户的要求，也不担保服务不会中断。${webapp.webName}同时不对用户所发布信息的删除或储存失败负责。
			  </P><br />

			  九．免责条款 

              <P>　　发生下列情况时，${webapp.webName}不予承担任何法律责任：
			  <br />　　1、用户资料泄露。由于用户将密码告知他人或与他人共享注册帐户，或由于用户的疏忽，由此导致的任何个人资料泄露，以及由此产生的纠纷。
			  <br />　　无论何种原因导致的用户资料未授权使用、修改，用户密码、个人帐号或注册信息被未授权篡改、盗用而产生的一切后果。
			  <br />　　2、不可抗力及不可预见的情势发生。不可抗力和不可预见情势包括：自然灾害、政府行为、罢工、战争等；黑客攻击、计算机病毒侵入或发作、政府管制、彩票发行和销售机构的原因、彩票出票机或服务的故障、网络故障、国家政策变化、法律法规之变化等。因不可抗力和不可预见情势造成：暂时性关闭，用户资料或代购、合买委托指令泄露、丢失、被盗用、被篡改，委托代购、合买失败，${webapp.webName}网未能收到委托指令等，以及由此产生的纠纷。
			  <br />　　因不可抗力和不可预见的情势造成本协议不能履行的。
			  <br />　　3、用户原因或第三方原因造成损失。由于用户自身原因、或违反法律法规，以及第三方的原因，造成用户无法使用彩票服务或产生其他损失的。
			  <br />　　4、用户代购、合买的彩票而产生的损失。用户根据本协议进行代购、合买委托投注而发生的直接、间接、偶然、特殊及继起的损害。
			  <br />　　5、用户使用链接或下载资料产生的损害。由于使用与${webapp.webName}链接的其它网站，或者使用通过${webapp.webName}下载或取得的资料，造成用户资料泄露、用户电脑系统损坏等情况及由此而导致的任何争议和后果。
			  </P><br />

			  十.终止服务 

              <P>　　1、任何一方未能履行本协议规定的任何义务，且在收到另一方发出更正通知15日内未能改正错误的，均被视为自动放弃本协议，另一方将有权就因对方放弃协议而造成的经济损失进行追偿； 
              <br />　　2、${webapp.webName}有权以E-mail的方式通知用户终止本协议，自通知发出之日起15日内本协议终止。${webapp.webName}须在协议终止后30日内将用户帐号内的余额以用户认可的方式退还予用户。 
              <br />　　3、${webapp.webName}对任何因用户根据本协议购买彩票而发生的直接、间接、偶然、特殊及继起的损害不负责任。 
			  <br /><br />

			  十一．法律 
              <P>　　本协议适用中华人民共和国法律，因任何一方出现违反法律的行为而造成协议条款的不能执行，都应由责任方自行负责并补偿由此而给对方造成的损失。 
              </P>　<br />

			  十二.未尽事宜 
              <P>　　本服务条款的未尽事宜，${webapp.webName}在不违背有关法律、法规，不违反公共道德准则的情况下，由${webapp.webName}作出最终解释、补充，或另行约定附则。</P>
              <P>　　本协议最终解释权属于肇庆优盛科技
			  <br />　　电话：4008-828-863
              <br />　　传真：86-0755-83521666<br />
			  </P>
			  </div>
		  </div>
		  <div class="block_xieyi">电话投注服务协议 
			   <div>一、电话投注委托协议的确定 
                 <P>　　${webapp.webName}电话投注客户服务平台，根据相关法律、法规开展电话投注代购国家发行的体育彩票和福利彩票业务。您阅读并同意以下服务条款，并登记注册为本平台会员（以下简称 “ 用户 ” ），方有资格享受本平台提供的电话投注彩票等服务，并受本协议条款的约束。 
                </P><br />

				二、对于用户使用${webapp.webName}网电话投注服务的约定 
                <P>　　（1）${webapp.webName}网根据彩票行业相关主管部门的政策法规，合法给用户提供彩票的电话投注业务；
				<br />　　（2）用户根据自愿的原则，使用${webapp.webName}网电话投注业务，并自己对自己提供的注册信息的真实性负责；
				<br />　　（3）${webapp.webName}网努力使用户的每一次电话投注委托成功，但是并不能保证成功。成功与否的标志是彩票的打印状态，打印状态“已出票”表明用户的投注委托成功，打印状态“出票作废”表明由于客观原因用户的委托失败。
				<br />　　（4）由于电话投注的特殊性，${webapp.webName}网并不为电话投注的委托失败承担任何责任，但是委托失败后的委托资金必须足额返还给用户。 
用户追号的投注委托方式如遇客观原因委托失败，${webapp.webName}网将终止用户的本次追号投注，剩余资金返还用户账号。
                <br />　　（5）国家明令禁止一切洗钱行为，${webapp.webName}网积极响应国家政策，严格控制。因此会员“当次储值后交易未达到当次储值额30%的提款”将不被受理。如果会员要求提出，网站将收取10%的异常提款处理费用，银行汇款费用另行收取。 
                </P><br />

				三、用户信息的提供 
                <P>　　为保障用户的合法权益，避免因投注手机必须使用手机号码确认投注的限制，无法投注的情况发生。请用户注册时务必按照真实、全面、准确的原则填写手机号码。如果用户提供的资料包含有不正确的信息，本平台保留结束该用户使用服务资格的权利。 
                </P><br />

				四、用户享有的权利 
                <P>　　用户有权随时对自己的绑定手机状态进行查询、修改和删除。 
				</P><br />

				五、用户享有的服务 <br />
                <P>　　1 、用户免费使用本平台的电话投注系统进行选号和提交彩票购买申请。 
				<br />　　2、用户免费使用本平台专用号码激活投注服务。 
				</P><br />

				六、服务条款修改和服务 
                <P>　　本平台有权在必要时修改服务条款，如果不同意所改动的内容，用户可以主动取消服务。如果用户继续享用服务，则视为接受服务条款的变动。本平台保留随时修改或中断服务而不需知照用户的权利。 
                </P><br />

				七、终止协议 
                <P>　　1 、任何一方未能履行本协议规定的任何义务，且在收到另一方发出更正通知 15 日内未能改正错误的，均被视为自动放弃本协议，另一方将有权就因对方放弃协议而造成的经济损失进行追偿。
                <br />　　2 、本平台有权以 E-mail 的方式通知用户终止本协议，自通知发出之日起 15 日内本协议终止。本平台须在协议终止后 10 日内将用户账号内的余额以用户认可的方式退还予用户。 
				<br />　　3 、本平台对任何因用户根据本协议购买彩票而发生的间接损害不负责任。 
				</P>
                <P>　　本协议适用中华人民共和国法律，因任何一方出现违反法律的行为而造成协议条款的不能执行，都应由责任方自行负责并补偿由此而给对方造成的损失。 
                <br /><br />　　本协议最终解释权属于肇庆优盛科技
             </div> 
		  </div>		  
		</div>
		
	   </div>
	<script type="text/javascript">
       var redirectUrl = document.referrer;
       Cookies.set({name:"redirectUrl",value:redirectUrl,path: "/"}); 
       $SSO.refresh_login_info_register();
	</script>
</body>
</html>

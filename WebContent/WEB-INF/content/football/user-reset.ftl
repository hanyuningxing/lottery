<head>
	<title>帐号设置</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="scheme" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/user/css/cyyzc.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/user-validate.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">window.BASESITE='${base}';</script>
	<script type="text/javascript" src="<@c.url value="/js/jquery/formValidator/formValidator.js"/>"></script>
	<script type="text/javascript" src="<@c.url value="/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
    <script type="text/javascript" src="<@c.url value="/js/jquery/jquery.form.js"/>"></script>
    <script type="text/javascript" src="<@c.url value="/js/jquery/popup_layer.js"/>"></script>
    <script type="text/javascript" src="<@c.url value="/js/user/validated.js"/>"></script>
	
	<script type="text/javascript">
	var PopupLayer;
	$(document).ready(function(){
			//初始化手机验证弹框组件
		   PopupLayer = new PopupLayer({usePopupIframe:true,trigger:"#openValidate",popupBlk:"#validate",closeBtn:"#close",offsets:{x:-400,y:-300},
		      //发送短信验证
		      onBeforeStart:function(){ 
		      	 $("#repeatSend").html("");
			 	 $("#aotuImg").html("<a href=\"javascript:void(0)\" onclick=\"sendMsg()\" id=\"send\">点击获取短信验证码</a>");
		       }
		    });
			//初始化校验组件
			$.formValidator.initConfig({formid:"regForm",onerror:function(msg){},onsuccess:function(){return true;}});
			//校验
			checkForm();
			//保存
			$("#regFormSubmit").click(function(){
			   var confirmflag = "你确定要修改用户基本资料信息吗？";
				if (confirm(confirmflag)) {
					if(jQuery.formValidator.pageIsValid()){
						$("#regForm").submit();
					}
				}
			});
	});		
    
  //短信倒计时时间
	var maxtime = 300 //一个小时，按秒计算，自己调整!  
	function CountDown(){  
	  if(maxtime>0){  
		 $("#timer").html("["+maxtime+"]");
		--maxtime;  
	  }
	}  
    
       
    function isMobile(number){
		if(! /^[0-9]{11}$/.test( number )){
			return false;
		}
		return (/^[1][3458][0-9]{9}$/.test(trimStr(number)));  
	}
    
    function setPhone(){
    	if(jQuery.formValidator.pageIsValid()){
         var phone = $("#phoneInput").val();
         $("#validateMsg").html("");
         var msg = "手机号码："+"<span id=\'validatePhone\'>"+phone+"</span>";
		 $("#validateMsg").html(msg);
        } else {
         $("#validateMsg").html("");
         var msg = "手机号码："+"<span id=\'validatePhone\'>"+"请先完善账户信息  保存后再进行手机验证"+"</span>";
		 $("#validateMsg").html(msg);
        }
         
    }
 
	
    function sendMsg(){
		    	var phone = $("#phoneInput").val();
				if(!isMobile(phone)){
					alert('请输入正确的手机号码 !');
					return false;
				}
    		    $("#repeatSend").html("如果您5分钟之内没有收到校验码，请");
            	var phoneNo = $("#phoneInput").val();
     			var url = window.BASESITE + '/user/user!toMsgValid.action?regForm.phoneNumber='+phoneNo;
				$.ajax({
				type : 'POST',
				cache : false,
				dataType : 'json',
				url : url,
				success : function(jsonObj) {
				    var msg = "手机号码："+"<span id=\'validatePhone\'>"+phoneNo+"</span>";
				    $("#send").html("");
				    $("#send").html("重新获取短信验证码<span id=\"timer\"></span>");
					if (jsonObj.success == true) {
					    msg = msg+"<span class=\yfs\>已向此号码发送免费的校验码短信。</span>";
					 	$("#send").removeAttr("onclick");
						window.setTimeout(function(){
								$("#aotuImg").html("");
								$("#aotuImg").html("<a href=\"javascript:void(0)\" onclick=\"sendMsg()\" id=\"send\">立刻与客服联系<span id=\"timer\"></span></a>");
								window.clearTimeout();
						},180000);
						timer = setInterval("CountDown()",1000);   
					} else {
					  alert(jsonObj.msg);	
					  msg=msg+"<span class=\yfs\>短信发送失败</span>";
					} 
					 $("#validateMsg").html("");
					 $("#validateMsg").html(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}});	
    
    }
				
				
	/**验证短信内容*/
	function msgVal(){
		var code =$("#msgCodeInput").val().trim();
		var phone = $("#phoneInput").val();
		if(phone==""){
			alert('请输入手机号！');
			return false;
		}
		
		if(code == ""){
			alert('请输入短信验证码！');
			return false;
		}
		var url = window.BASESITE + '/user/user!msgValid.action?emailValForm.code='+code+'&emailValForm.phoneNo='+phone;
		$.ajax({
		type : 'GET',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
				alert(jsonObj.msg);
				window.location.reload();
			} else {		
				alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}});	
	}
				
		
	function trimStr(str){
		var m = str.match(/^\s*(\S+(\s+\S+)*)\s*$/);  
		return (m == null) ? "" : m[1];  
	}
				
					
	function checkForm(){
		$("#realNameInput").formValidator({onshow:"请输入真实姓名，最长为10位",onfocus:"真实姓名不能为空，最长为10位，一个汉字占两位",oncorrect:"真实姓名合法"}).inputValidator({min:1,max:10,empty:{leftempty:false,rightempty:false,emptyerror:"真实姓名两边不能有空符号"},onerror:"请输入真实姓名"});
		
		$("#idCardInput").formValidator({onshow:"请输入15或18位真实的身份证",onfocus:"输入15或18位真实的身份证",oncorrect:"输入正确"})
		.functionValidator({
	    fun:function(val,elem){
		        if(/(^\d{15}$)|(^\d{17}([0-9]|[x,X])$)/.test(trimStr(val))){ 
				    return true;
			    }else{
				    return "身份证格式不正确,请检查"
				}
			}
		});
		
	    		
		$("#emailInput").formValidator({onshow:"请输入真实有效且经常使用的E-mail",onfocus:"邮箱至少6个字符   注：作为密码取回工具",oncorrect:"邮箱合法"}).inputValidator({min:6,max:100,onerror:"你输入的邮箱长度非法,请确认"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"你输入的邮箱格式不正确"});
		
		$("#phoneInput").formValidator({onshow:"请输入真实手机号，以便中大奖后通知您",onfocus:"手机号码为11位，不能为空",oncorrect:"手机号码合法"})
		.inputValidator({min:11,max:11,empty:{leftempty:false,rightempty:false,emptyerror:"手机号码两边不能有空符号"},onerror:"手机号码必须是11位的,请确认"})
		.regexValidator({regexp:"mobile",datatype:"enum",onerror:"你输入的手机号码格式不正确"});
	    	
	}
	</script>
</head>
<style>
.cyy_tyzc_anniu{
    margin:0px;
	padding:0px;
	margin-left:230px;
	margin-top:15px;
	margin-bottom:25px;
	}
.cyy_tyzc_anniu1{
    margin:0px;
	padding:0px;
	margin-left:230px;
	margin-top:15px;
	margin-bottom:25px;
	}
	.cyy_tyzc_anniu2{
    margin:0px;
	padding:0px;
	margin-left:370px;
	margin-top:15px;
	margin-bottom:25px;
	}
</style>
<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'account'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
   <#include "/common/message.ftl" />
  		<div class="cyy_zcnr">
       	    <form action="<@c.url value='/user/user!updateInfo.action' />" method="post" id="regForm" name="regForm">
		   	<#if infoForm??&&infoForm.from??><input type="hidden" value="${infoForm.from!}" name="infoForm.from"></#if>	
		    <div class="list_div" id="userName_list_div">
				      <font>用户名：</font>
				      <div><input class=text_inp type=text id="userNameInput" name="regForm.userName" value="${user.userName!}" disabled="disabled"></div>		
			</div>
			<div class="list_div" id="realName_list_div">
				      <font>真实姓名：</font>
				      <div><input class=text_inp type=text id="realNameInput" name="regForm.realName" <#if user.info??&&user.info.realName??&&user.info.realName!="">disabled="disabled"  </#if> value="<#if user.info??&&user.info.realName??>${user.info.realName!}</#if>"></div>(注:为了校验安全,真实姓名补充完后不给修改)
				       <div id="realNameInputTip" style="width:280px"></div>	
			</div>
			<div class="list_div" id="idCard_list_div">
				      <font>身份证号：</font>
				      <#if user.info??&&user.info.idCard??&&user.info.idCard!=""&&user.info.idCard?length gte 10>
				         <div>
				      	 <input class=text_inp type=text disabled="disabled"  value="${user.info.idCard?substring(0,10)}**********"></div>(注:为了校验安全,身份证号补充完后不给修改)
				     	 <input id="idCardInput" type="hidden" name="regForm.idCard"  value="${user.info.idCard!}">
				      <#else>
				         <div><input class=text_inp type=text id="idCardInput" name="regForm.idCard"></div>(注:为了校验安全,身份证号补充完后不给修改)
				      </#if>
				      <div id="idCardInputTip" style="width:280px"></div>	
			</div>
			<div class="list_div" id="email_list_div">
				      <font>邮箱：</font>
				       <#if user.info??&&user.info.email??&&user.info.email!="">
				         <div>
				      	 <input class=text_inp type=text disabled="disabled"  value="${user.info.email?substring(0,3)}****${user.info.email?substring(7)}"></div>(注:为了校验安全,补充完后不给修改)
				     	 <input id="phoneInput" type="hidden" name="regForm.email"  value="${user.info.email!}">
				      <#else>
				     	<input class=text_inp type=text id="emailInput" name="regForm.email" <#if user.validatedEmail??&&user.validatedEmail=1>disabled="disabled"</#if> value="${user.email!}">			 
				      </#if>
				        
				      <div id="emailInputTip" style="width:280px"></div>
			</div>
			<div class="list_div" id="phone_list_div">
				      <font>手机号码：</font>
				      <#if user.info??&&user.info.mobile??&&user.info.mobile!="">
				         <div>
				      	 <input class=text_inp type=text disabled="disabled"  value="${user.info.mobile?substring(0,3)}****${user.info.mobile?substring(7,11)}"></div>(注:为了校验安全,手机号码补充完后不给修改)
				     	 <input id="phoneInput" type="hidden" name="regForm.phoneNumber"  value="${user.info.mobile!}">
				      <#else>
				         <div><input class=text_inp type=text id="phoneInput" name="regForm.phoneNumber" value="${user.phoneNo!}"></div>(注:为了校验安全,手机号码补充完后不给修改)
				      </#if>
				     <div id="phoneInputTip" style="width:280px"></div>	 
			</div>
			</form>	
			
			<#if user.validatedPhoneNo??&&user.validatedPhoneNo !=1>
	        <div class=cyy_tyzc_anniu align=left style="padding-left:80px;"><FONT size=+0><INPUT id="openValidate" class=ButtonStyle_max value="点击手机验证" onclick ="setPhone()" type=button>
		     <div> 
		     <FONT style="COLOR: red ;  font-size:12px;line-height:22px;margin-left:10px;">请先完善资料并保存后，再进行手机验证。验证成功立刻获取2元彩金！</FONT><br>
			 <FONT style="COLOR: #000; font-size:12px;line-height:22px;margin-left:10px;">验证后该手机号码将不可修改
			 </div>
    	    </div> 
    	    </#if>
    	    
           <div class=cyy_tyzc_anniu2 align="left">
		    <input type="button" value="确认保存" id="regFormSubmit" class="ButtonStyle_max" />
	      </div>
      </div>
      
    <div class="mian_content" id="validate" style="display:none">
	<div id="xiao_login">
		<div class="xiao_menu">
		    <div class="top_font">验证手机号码</div>
			<div class="x_tc"><img src="<@c.url value= "/pages/images/xxk_cha.gif"/>" id="close"/></div>
		</div>
		<div class="x_nr">
		   <div class="yz" id="validateMsg">
				手机号码：<span id="validatePhone"></span>
		   </div>
		   <div class="t3_suk">
				<div class="wz1">短信校验码：</div>
				<div class="suk"><input type="text" id="msgCodeInput"></div>
				<div class="wz2">
				       <div class="wzimg"><img src="<@c.url value= "/pages/images/onShow.gif"/>"  /></div>
					   <div class="wz3" style=" line-height:22px;">注意:区分大小写</div>
			    </div>
		   </div>
		   <div class="x_buttom">
			   <div class="x_buttom1"><input type="image" src="<@c.url value= "/pages/images/c_anniu.gif"/>"  onclick="javascript:msgVal();"/></div>
		  </div>
		
		   <div class="x_line"></div>
		   <div class="qtdl2" id="repeatSend">如果您1分钟之内没有收到校验码，请</div>
		   <div class="qtdl">
                    <div  id="aotuImg"> <a href="javascript:void(0)" onclick="sendMsg()" id="send">点击获取短信验证码</a></div>
			</div>	
	</div>
</div>
	 
      
</div>
  
  
  
  
  
   
 
  
  

  
        
  
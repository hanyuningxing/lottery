<head>
	<title>提现</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="payPer" />
	<link href="css/cz_center.css" rel="stylesheet" type="text/css" /> 
	<!--<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />-->
	<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/drawingper.css"/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="<@c.url value= "/js/city.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
	<script type="text/javascript">
	$(document).ready(function(){

			$("#drawingFormSubmit").click(function(){					
			   var confirmflag = "你确定要提现："+$("#drawingMoney").val()+"元吗？";
				  if (confirm(confirmflag)) {
				  	$("#drawingForm").submit();
				  }
			});
	
	});
	
	$(document).ready(function(){
		$("#qinghu").click(function(){
				if($("#qinghu").val() == 0) {
					document.getElementById("drawingMoney").value = #{(drawingForm.resultMoney!0);M2};
					document.getElementById("qinghu").value = 1;

				} else if($("#qinghu").val() == 1) {
					document.getElementById("drawingMoney").value = "";
					document.getElementById("qinghu").value = 0;
				}
			});
	});
	
	$(function(){ 
		createDialog('#msgValidateDialog', '手机验证', 420);
		$("#btnBmVfyCd").click(function(){
				$.post(window.BASESITE + '/user/user!sendMsg.action',
				{},function(data){
					if(data.success){
						document.getElementById('btnBmVfyCd').setAttribute("disabled", false);
						document.getElementById('sendResult').innerHTML = "已向你的手机发送验证码，一分钟后如果还没有收到请点击重发！";
						setTimeout(function(){document.getElementById('sendResult').innerHTML = "";},3000);						
						var i = 60;
					    var h = setInterval((function(i){
					        return function() {			        	
					        	document.getElementById("btnBmVfyCd").value = i-- + "秒后点击重发";
					        	if(i <= 0) {
					        		clearInterval(h);
					        		document.getElementById("btnBmVfyCd").value="获取短信验证码";
					        		document.getElementById("btnBmVfyCd").removeAttribute("disabled");
					        		document.getElementById("btnBmVfyCd").setAttribute("abled", true);			        		
					        	}
					        }
					    }(i)), 1000);						
						
					} else {
						document.getElementById('sendResult').innerHTML = data.msg;
						setTimeout(function(){document.getElementById('sendResult').innerHTML = "";},3000);
					}	
				},'json');
			});												 					 		
	});
	
	function createDialog(id_, title_, width_, height_) {
		$(id_).dialog({  
			autoOpen:false,
			title:title_,
			width:width_, 
			height:height_, 
			modal:true
		});
	}
	
		//set返回信息
   	function setCommonResult(msg,success) {
		$("#commonResultDialogData").empty();
		$("#commonResultDialogData").html(msg);
		if(success) {
			document.getElementById("tckico").setAttribute("class", "tckico01");
   		} else {
			document.getElementById("tckico").setAttribute("class", "tckico02");   			
   		}
		$("#commonResultDialog").dialog('open');
		
   	}
   	
   	//向手机发送验证码，手机已验证
function sendMsgCodeToValidatedNo() {

	$.post(window.BASESITE + '/user/user!sendMsg.action', {
	}, function(data) {
		if(data.success) {
			document.getElementById("sendMsgAgain").setAttribute("disabled", false);			
			var i = 60;
			   var h = setInterval((function(i){
			        return function() {			        	
			        	document.getElementById("sendMsgAgain").value="等待" + i-- + "秒后可点击重发";
			        	if(i <= 0) {
			        		clearInterval(h);
			        		document.getElementById("sendMsgAgain").value="重新获取验证码";
			        		document.getElementById("sendMsgAgain").removeAttribute("disabled");
			        		document.getElementById("sendMsgAgain").setAttribute("abled", true);			        		
			        	}
			        }
			    }(i)), 1000);		
		}
	},'json');
}	
</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'drawingPer'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
  	  <#include "user-loginInfo.ftl">
    <div class="border04">
      <div class="zjmxtit">
		  	<ul>
				<a href="${base}/user/fund!tkNavigator.action">
				<li>提款向导</li>
			  </a>
				<a href="#">
				<li class="zjmxli01">申请提款</li>
			  </a>
			  <a href="${base}/user/fund!tkManager.action">
				<li>提款管理</li>
				</a>
				<a href="${base}/user/fund!drawingList.action">
				<li>提款订单</li>
				</a>
		  	</ul>
      </div>
	  <div style="color:#333; padding:10px 0;">
	  <form action="<@c.url value='/user/fund!drawingWithTheDefaultBank.action' />" method="post" id="drawingForm">
	  <input type="hidden" name="request_token" value="${datetime().millis}" />
         <input type="hidden" id="defaultAccountRemainMoney" value="#{(drawingForm.resultMoney!0);M2}" />
        <input   name="drawingForm.drawingWay" id="drawingWay" class="text_inp" type="hidden" value="BANK"/> 
         <input id="drawingForm_partBankProvince" type="hidden" value="${(drawingForm.partBankProvince)?if_exists}">
			<input id="drawingForm_partBankCity"  type="hidden"  value="${(drawingForm.partBankCity)?if_exists}">
	    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab" style="font-size:14px;">
          <tr>
            <td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">可提款金额：</td>
            <td valign="middle" bgcolor="#FFFFFF"><span id="labMoney">#{(drawingForm.resultMoney!0);M2}</span> 元</td>
          </tr>
          <#include "${base}/common/message.ftl" />
          <tr>
            <td height="40" align="right" valign="middle" bgcolor="#D5F6FD">提款金额：</td>
            <td valign="middle" bgcolor="#FFFFFF"><input name="drawingMoney" id="drawingMoney" type="text" class="tkxdbd" size="25" />              &nbsp;&nbsp;
              <input type="checkbox" name="checkbox" id="qinghu" value="0" align="absmiddle" /> 清户
  <span class="black_888_14">(提款金额至少10元) </span> </td>
          </tr>
          <tr>
            <td height="80" align="right" valign="middle" bgcolor="#D5F6FD">提款银行：</td>
            <td valign="middle" bgcolor="#FFFFFF"><span class="yel_F60"><strong>${drawingForm.bankName!}</strong><br />
                <strong>开户行：${drawingForm.partBankName!}<br />
            银行账号：${drawingForm.bankCard!}</strong></span> <a href="${base}/user/fund!tkManager.action">修改</a></td>
          </tr>
          <tr>
            <td height="40" align="right" valign="middle" bgcolor="#D5F6FD">核对真实姓名：</td>
            <td valign="middle" bgcolor="#FFFFFF"><input name="userRealName" type="text" class="tkxdbd" size="25" /></td>
          </tr>
          <#if loginUser.needPayPassword?? && loginUser.needPayPassword>
          <tr>
            <td height="40" align="right" valign="middle" bgcolor="#D5F6FD">请输入支付密码：</td>
            <td valign="middle" bgcolor="#FFFFFF"><input name="payPassword" type="password" class="tkxdbd" size="25" /></td>
          </tr>
          </#if>
           <#if loginUser.needValidateWhileTk?? && loginUser.needValidateWhileTk>
           <tr>
              <td height="30" align="right" valign="middle" bgcolor="#D5F6FD">短信验证码：</td>
              <td bgcolor="#FFFFFF"><input id="yanzhengma" name="yanzhengma" type="text" class="tkxdbd" size="6" />
              <input id="btnBmVfyCd" value="获取短信验证码" type="button" name="btnBmVfyCd" class="tkxdyzm" />
              <span id="sendResult" style="color:red"></span>
              </td>
            </tr>
          </#if>
          <tr>
            <td height="50" valign="middle" bgcolor="#FFFFFF">&nbsp;</td>
            <td valign="middle" bgcolor="#FFFFFF"><a id="drawingFormSubmit" href="#" class="sqtkan"/></a></td>
          </tr>
        </table>
        </form>
      </div>
    </div>
    <div style="padding:20px 16px; line-height:24px;"><strong class="bro_af5529">提款说明： </strong><br />
        <span class="bro_6D4F42">1、周一至周日每天处理提款申请，每日下午16:00以后的提款申请，会在次日早9：00后得到处理；法定节假日的提款申请会顺延至节后。<br />
      2、每笔提款申请不能少于10元。当天提款在20万或以上，处理时间需时3个工作日，请耐心等候，不便之处请见谅。<br />
      3、您的提款申请需要先经过我们工作人员核对，核对无误后通过人工办理汇款，我们将在受理后<span style="color:red">1个小时内将款项汇出</span>。（<a href="#"><U>查询费率及到账时间</U></a>）<br />
      4、如果因为您填写的注册资料不完善，无法使用网上取款功能，请与本网客服人员联系。<br />
      5、提款后银行汇款手续费我们网站帮您承担，网站不会收取您任何提款费用。所以<span style="color:red">提款是0手续费</span>。<br />
    6、办理提现时涉嫌洗钱行为(充值预付款购彩不到80%)，本网将拒绝办理并上报发卡银行，您可通过“退款申请”把充值资金退回原支付银行卡或支付账号。</span></div>
  </div>

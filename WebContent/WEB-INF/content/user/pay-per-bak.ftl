<head>
	<title>充值</title>
	<meta name="decorator" content="trade" />
	<meta name="menu" content="payPer" />
	<link href="css/cz_center.css" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/drawingper.css"/>" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/artDialog.3.0.6/artDialog.min.js"/>"></script>
	<script type="text/javascript"> 
	$(document).ready(function(){
	  	$("#aliPayDiv").css("display","");
     	$("#cardPayDiv").css("display","none");
     	
     	//保存
		$("#rechargeLotteryCard").click(function(){		
				var lotteryNo =$("#lotteryNo").val().trim();
				var captcha =$("#captcha").val().trim();
				if(lotteryNo == ""){
					alert('请输入彩金卡号');
					return false;
				}
				if(captcha == ""){
					alert('请输入验证码');
					return false;
				}
				var url = window.BASESITE + '/user/fund!rechargeLotteryCard.action?lotteryNo='+lotteryNo+'&&captcha='+captcha;
				$.ajax({
				type : 'get',
				cache : false,
				dataType : 'json',
				url : url,
				success : function(jsonObj) {
					if (jsonObj.success == true) {
						alert(jsonObj.msg);
						window.location.href = window.BASESITE + '/user/fund!list.action';
					} else {		
						alert(jsonObj.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("网络不通");
				}});	
			});
     	
	
	});
	
	
	
	(function(){
	    var d = art.dialog.defaults;
	    // 按需加载要用到的皮肤，数组第一个为默认皮肤
	    // 如果只使用默认皮肤，可以不填写skin
	    d.skin = ['default', 'chrome', 'facebook', 'aero'];
	    // 支持拖动
	    d.drag = true;
	    // 超过此面积大小的对话框使用替身拖动
	    d.showTemp = 100000;
	    setTimeout("canActivity()",1000);
	    
    
	})();
	
	function canActivity() { 
	 	var canActivity = $("#canActivity").val();
	 	var hasRecharge = $("#hasRecharge").val();
	 	
	 	if(hasRecharge!="true"&&canActivity=="false"){
		 	var msg="很抱歉，您本次充值将不能参与充值赠送活动，<br>原因：手机号码未进行校验或者信息资料存在重复注册记录。<br>"
			art.dialog({
			left: 700,
   		    top: 270,
			width: '30em',
	  		height: 85,
			limit: false,
			time: 2,
			content: msg
		    });
	    }
	}
	
	function reload_vercode_img(){
	$("#captcha_img").attr("src","${base}/vcode.jpg?seed="+Math.random().toString());
	}
	
	function rightNetworkType() { 
		 	var msg="很抱歉，只有通过支付宝快捷登录注册的用户才允许享受此优惠活动。<br>"
			art.dialog({
			left: 700,
   		    top: 270,
			width: '30em',
	  		height: 85,
			limit: false,
			time: 2,
			content: msg
		    });
	}
	
	
	function chkit(elm) { 
		var bank = document.getElementById(elm); 
		bank.checked = "checked"; 
		var hatImage = document.getElementById("bank_img");
		var img = "images/czym/"+bank.value+".gif";
        hatImage.setAttribute("src",img);
	} 
	
	function changePayWay(value){
		if(value=="ALIPAY"){
			$("#aliPay").attr("class","now");
			$("#cardPay").attr("class","");
			
			$("#aliPayDiv").css("display","");
			$("#cardPayDiv").css("display","none");
		}else {
			var rightType = $("#rightType").val();
		    if(rightType=="false") {
		   	 rightNetworkType();
		    }
			$("#cardPay").attr("class","now");
			$("#aliPay").attr("class","");
			
			$("#aliPayDiv").css("display","none");
			$("#cardPayDiv").css("display","");	
		}
	}
	
	</script> 
</head>
<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'payPer'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->

  <div class="cright">
    <div class="crighttop">
      <div class="zhcz_bt"><span>给本账户充值</span>｜<a href="#">给其他账户充值</a></div>
    </div>
    <div class="kong10"></div>
	<div class="czyq">
		<ul>
		   <li><a href="javascript:changePayWay('ALIPAY');" id="aliPay" class="now">网上充值</a></li>
		   <li><a href="javascript:changePayWay('CARDPAY');" id="cardPay">彩金卡充值</a></li>
		</ul>
		<div class="clear"></div>
	</div>
	<div class="cz_cznr" id="aliPayDiv">
	  <form action="<@c.url value="/user/fund!commTraceMarker.action" />" method="post"> 
      <input id="canActivity" name="canActivity" value="${(canActivity)?string("true","false")}" type="hidden"/>
      <input id="hasRecharge" name="hasRecharge" value="${(hasRecharge)?string("true","false")}" type="hidden"/>
      <input id="rightType" name="rightType" value="${(rightNetworkType)?string("true","false")}" type="hidden"/>
      <input name="checkSpan" value="false" type="hidden"/>
		   <div class="cz_cznr_jbxx" >
			   <div class="jbxx_wk">
				  <div class="jbxx_wz">用户名：</div>
				  <div class="jbxx_sunr">${userName?default("")}</div>
			   </div>
			   <div class="jbxx_wk ">
				  <div class="jbxx_wz">充值金额：</div>
				  <div class="jbxx_sunr">  
					 <input type="text" name="commTraceForm.amount" value="请输入充值金额" size="15" onclick="if('请输入充值金额'==this.value){this.value='';return false;}"  />元 <span>（金额格式：100.00，如要参加充值赠送活动，请务必完善手机、邮箱、身份证且手机号已经过验证）</span>
				     <#include "/common/message.ftl" />
				  </div>
		       </div>
	        </div>
      
		<div class="cyy_lm">选择充值方式：</div>
		<div class="cyy_bank">
		   <ul class="cyy_gh">
		     <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ICBC" id="ICBC" checked="checked" onclick="chkit('ICBC')"/><img src="images/czym/ICBC.gif" /></a></li>
		   </ul>
		   <div class="cyy_bankline1"></div>
		   <ul class="cyy_morebank">
		     <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ALIPAY" id="ALIPAY"  onclick="chkit('ALIPAY')"/><img src="images/czym/ALIPAY.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ABC" id="ABC" onclick="chkit('ABC')"/><img src="images/czym/ABC.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CCB" id="CCB" onclick="chkit('CCB')"/><img src="images/czym/CCB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBC" id="CMBC" onclick="chkit('CMBC')" /><img src="images/czym/CMBC.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="BOC" id="BOC" onclick="chkit('BOC')" /><img src="images/czym/BOC.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBBANK" id="CMBBANK" onclick="chkit('CMBBANK')" /><img src="images/czym/CMBBANK.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPDB" id="SPDB" onclick="chkit('SPDB')" /><img src="images/czym/SPDB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SHBANK" id="SHBANK" onclick="chkit('SHBANK')" /><img src="images/czym/SHBANK.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="COMM" id="COMM" onclick="chkit('COMM')" /><img src="images/czym/COMM.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CEB" id="CEB" onclick="chkit('CEB')" /><img src="images/czym/CEB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CITIC" id="CITIC" onclick="chkit('CITIC')" /><img src="images/czym/CITIC.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPABANK" id="SPABANK" onclick="chkit('SPABANK')" /><img src="images/czym/SPABANK.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CIB" id="CIB" onclick="chkit('CIB')" /><img src="images/czym/CIB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SDB" id="SDB" onclick="chkit('SDB')" /><img src="images/czym/SDB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="GDB" id="GDB" onclick="chkit('GDB')" /><img src="images/czym/GDB.gif" /></a></li>
			 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="HZCM" id="HZCM" onclick="chkit('HZCM')" /><img src="images/czym/HZCM.gif" /></a></li>	   
		   </ul>
		   <div class="cyy_bankline2"></div>
		   <div class="cyy_yxz">
			   <div class="cyy_yxz1"><img src="images/czym/ICBC.gif" id="bank_img"/></div>
			   <div class="cyy_yxz2">
			   <br /><span>请确保您已经开通该银行网上支付功能！</span>
			   </div>
		   </div>
		   
		</div>
		<div class="cyy_ljcz_anniu"><INPUT type="image" src="images/cyy_ljcz_button.gif" value="" onclick="javascript:ajaxSubmit();"/> </div>
	</div>
    </from>
    
	   <DIV style="DISPLAY: none" id="cardPayDiv" class="cz_cznr">
		<DIV class=kong20></DIV>
		<DIV class=list_div>
		     <FONT>彩金卡号：</FONT> 
		     <DIV><INPUT id="lotteryNo" class=text_inp type=text> </DIV>
		</DIV>
		<DIV class=list_div>
		     <FONT>验证码：</FONT> 
		     <DIV><INPUT style="WIDTH: 60px;" id="captcha" class=input_txt tabIndex=3 
		maxLength=4 size=4 type=text name=captcha> 
		 <img title="看不清？点击换一个" id="captcha_img" anme onclick="reload_vercode_img();" src="${base}/vcode.jpg" complete="complete"/>
	      <script type="text/javascript">reload_vercode_img();</script>
		 <A tabIndex=-1 href="javascript:reload_vercode_img();">看不清楚，换一个！</A> 
		     </DIV>
		</DIV>
		<DIV class=kong20></DIV>
		
		<DIV class=cyy_cardPay_anniu>
		<INPUT id="rechargeLotteryCard" class="ButtonStyle_max" value="立即充值" type="button">
		<a href="<@c.url value= "/html/lotterycard/cyy_tyksm.html"/>" target="_blank"><font color="red">《彩金卡使用说明》 </font></a>
		</DIV>
		<DIV class=kong20></DIV>
		</DIV></DIV>
	</DIV>
	<DIV class=kong_8ge></DIV>
    
</div>










	

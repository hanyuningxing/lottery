<head>
	<title>充值</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="payPer" />
	<link href="css/cz_center.css" rel="stylesheet" type="text/css" /> 
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
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
	function changePayWay(elm,value) { 
		var payArr = ["YEEPAY","ALIPAY"];
		for(var i=0; i<payArr.length; i++) {
			var a = document.getElementById(payArr[i]+"_a");
			var div = document.getElementById(payArr[i]+"_bank_div");
			if(elm==payArr[i]){
				a.className = "now";
				div.style.display="";
			}else{
				a.className = "";
				div.style.display="none";
			}
		}
		var commTraceMarker_form = document.getElementById("commTraceMarker_form");
		var payWay = commTraceMarker_form.elements['payWay'];
		payWay.value=value;
		chkit('ICBC');
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

  <div class="yhzxright">
    <div class="crighttop">
      <div class="zhcz_bt"><span>给本账户充值</span></div>
    </div>
    <div class="kong10"></div>
	<div class="czyq">
		<ul>
		   <!--<li><a href="javascript:changePayWay('YEEPAY',0);" id="YEEPAY_a" class="now" id="YEEPAY_a">易宝充值</a></li>-->
		   <li><a href="javascript:changePayWay('ALIPAY',1);"  class="now" id="ALIPAY_a">支付宝充值</a></li>
		</ul>
		<div class="clear"></div>
	</div>
	<div class="cz_cznr" id="aliPayDiv">
	  <form action="<@c.url value="/user/fund!commTraceMarker.action" />" method="post" id="commTraceMarker_form"> 
	  <input name="payWay" value="1" type="hidden"/>
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
		     <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ICBC" id="ICBC" checked="checked" onclick="chkit('ICBC')"/><img src="images/czym/ICBC.gif"  onclick="chkit('ICBC')"/></a></li>
		   </ul>
		   <div class="cyy_bankline1"></div>
		   <div id="ALIPAY_bank_div">
			   <ul class="cyy_morebank">
			     <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ALIPAY" id="ALIPAY_ALIPAY"  onclick="chkit('ALIPAY_ALIPAY')"/><img src="images/czym/ALIPAY.gif" onclick="chkit('ALIPAY_ALIPAY')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ABC" id="ALIPAY_ABC" onclick="chkit('ALIPAY_ABC')"/><img src="images/czym/ABC.gif" onclick="chkit('ALIPAY_ABC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CCB" id="ALIPAY_CCB" onclick="chkit('ALIPAY_CCB')"/><img src="images/czym/CCB.gif" onclick="chkit('ALIPAY_CCB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBC" id="ALIPAY_CMBC" onclick="chkit('ALIPAY_CMBC')" /><img src="images/czym/CMBC.gif" onclick="chkit('ALIPAY_CMBC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="BOC" id="ALIPAY_BOC" onclick="chkit('ALIPAY_BOC')" /><img src="images/czym/BOC.gif" onclick="chkit('ALIPAY_BOC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBBANK" id="ALIPAY_CMBBANK" onclick="chkit('ALIPAY_CMBBANK')" /><img src="images/czym/CMBBANK.gif" onclick="chkit('ALIPAY_CMBBANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPDB" id="ALIPAY_SPDB" onclick="chkit('ALIPAY_SPDB')" /><img src="images/czym/SPDB.gif" onclick="chkit('ALIPAY_SPDB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SHBANK" id="ALIPAY_SHBANK" onclick="chkit('ALIPAY_SHBANK')" /><img src="images/czym/SHBANK.gif" onclick="chkit('ALIPAY_SHBANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="COMM" id="ALIPAY_COMM" onclick="chkit('ALIPAY_COMM')" /><img src="images/czym/COMM.gif" onclick="chkit('ALIPAY_COMM')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CEB" id="ALIPAY_CEB" onclick="chkit('ALIPAY_CEB')" /><img src="images/czym/CEB.gif" onclick="chkit('ALIPAY_CEB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CITIC" id="ALIPAY_CITIC" onclick="chkit('ALIPAY_CITIC')" /><img src="images/czym/CITIC.gif" onclick="chkit('ALIPAY_CITIC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPABANK" id="ALIPAY_SPABANK" onclick="chkit('ALIPAY_SPABANK')" /><img src="images/czym/SPABANK.gif" onclick="chkit('ALIPAY_SPABANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CIB" id="ALIPAY_CIB" onclick="chkit('ALIPAY_CIB')" /><img src="images/czym/CIB.gif" onclick="chkit('ALIPAY_CIB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SDB" id="ALIPAY_SDB" onclick="chkit('ALIPAY_SDB')" /><img src="images/czym/SDB.gif" onclick="chkit('ALIPAY_SDB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="GDB" id="ALIPAY_GDB" onclick="chkit('ALIPAY_GDB')" /><img src="images/czym/GDB.gif" onclick="chkit('ALIPAY_GDB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="HZCM" id="ALIPAY_HZCM" onclick="chkit('ALIPAY_HZCM')" /><img src="images/czym/HZCM.gif" onclick="chkit('ALIPAY_HZCM')"/></a></li>	   
			   </ul>
		   </div>
		    <div id="YEEPAY_bank_div"  style="display:none;">
			   <ul class="cyy_morebank">
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="ABC" id="YEEPAY_ABC" onclick="chkit('YEEPAY_ABC')"/><img src="images/czym/ABC.gif" onclick="chkit('YEEPAY_ABC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CCB" id="YEEPAY_CCB" onclick="chkit('YEEPAY_CCB')"/><img src="images/czym/CCB.gif" onclick="chkit('YEEPAY_CCB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBC" id="YEEPAY_CMBC" onclick="chkit('YEEPAY_CMBC')" /><img src="images/czym/CMBC.gif" onclick="chkit('YEEPAY_CMBC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="BOC" id="YEEPAY_BOC" onclick="chkit('YEEPAY_BOC')" /><img src="images/czym/BOC.gif" onclick="chkit('YEEPAY_BOC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CMBBANK" id="YEEPAY_CMBBANK" onclick="chkit('YEEPAY_CMBBANK')" /><img src="images/czym/CMBBANK.gif" onclick="chkit('YEEPAY_CMBBANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPDB" id="YEEPAY_SPDB" onclick="chkit('YEEPAY_SPDB')" /><img src="images/czym/SPDB.gif" onclick="chkit('YEEPAY_SPDB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SHBANK" id="YEEPAY_SHBANK" onclick="chkit('YEEPAY_SHBANK')" /><img src="images/czym/SHBANK.gif" onclick="chkit('YEEPAY_SHBANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="COMM" id="YEEPAY_COMM" onclick="chkit('YEEPAY_COMM')" /><img src="images/czym/COMM.gif" onclick="chkit('YEEPAY_COMM')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CEB" id="YEEPAY_CEB" onclick="chkit('YEEPAY_CEB')" /><img src="images/czym/CEB.gif" onclick="chkit('YEEPAY_CEB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CITIC" id="YEEPAY_CITIC" onclick="chkit('YEEPAY_CITIC')" /><img src="images/czym/CITIC.gif" onclick="chkit('YEEPAY_CITIC')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SPABANK" id="YEEPAY_SPABANK" onclick="chkit('YEEPAY_SPABANK')" /><img src="images/czym/SPABANK.gif" onclick="chkit('YEEPAY_SPABANK')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="CIB" id="YEEPAY_CIB" onclick="chkit('YEEPAY_CIB')" /><img src="images/czym/CIB.gif" onclick="chkit('YEEPAY_CIB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="SDB" id="YEEPAY_SDB" onclick="chkit('YEEPAY_SDB')" /><img src="images/czym/SDB.gif" onclick="chkit('YEEPAY_SDB')"/></a></li>
				 <li style="margin-right:23px;"><a href="javascript:void(0);"><input name="commTraceForm.bankName" type="radio" value="GDB" id="YEEPAY_GDB" onclick="chkit('YEEPAY_GDB')" /><img src="images/czym/GDB.gif" onclick="chkit('YEEPAY_GDB')"/></a></li>
			   </ul>
		   </div>
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










	

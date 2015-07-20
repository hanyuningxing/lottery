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
		$("#bank").css("display","block");
		$("#ali").css("display","none");

			
		$.formValidator.initConfig({formid:"drawingForm",onerror:function(msg){ },onsuccess:function(){ return true;}});
		
		
		/**
		* @作者:朱辉
		* @时间：2011-03-15
		* @方法描述：省市级联回显
		*/
		var partBankProvince = $("#drawingForm_partBankProvince").val();
		var partBankCity = $("#drawingForm_partBankCity").val();
		if(null!=partBankProvince) {
			 getCity(partBankProvince);
			 $("#selProvince").val(partBankProvince);
			 $("#selCity").val(partBankCity);
		}
		    checkForm();
		    aliUnChk();
			$("#bankName").focus();
			//去掉空格 
			function removeBlank(){
				$("input[type=text]").each(function(){
					$(this).val($.trim($(this).val()));
				});
				$("textarea").each(function(){
					$(this).val($.trim($(this).val()));
				});
			}
			
			//保存
			$("#drawingFormSubmit").click(function(){					
			   var confirmflag = "你确定要提现："+$("#drawingMoney").val()+"元吗？";
				  if (confirm(confirmflag)) {
						removeBlank();
						if(!$("#agree").attr("checked")==true){
							alert("请阅读提款说明 并同意其中条款");
						}
						if(jQuery.formValidator.pageIsValid()){
							$("#drawingForm").submit();
						}
				}
			});
	
	});
	</script>
	
	<script type="text/javascript">
	function checkForm(){
		$("#bankName").formValidator({onshow:"请输入提款银行",onfocus:"提款银行不能为空",oncorrect:"提款银行合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"提款银行两边不能有空符号"},onerror:"提款银行不能为空,请确认"});
		$("#userRealName").formValidator({onshow:"请输入开户名称",onfocus:"开户名称不能为空",oncorrect:"开户名称合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"开户名称两边不能有空符号"},onerror:"开户名称不能为空,请确认"});
		$("#selProvince").formValidator({onshow:"请选择省份",onfocus:"省份不能为空",oncorrect:"省份合法"}).inputValidator({min:1,onerror: "请选择省份!"}).defaultPassed();
	    $("#selCity").formValidator({onshow:"请选择城市",onfocus:"城市不能为空",oncorrect:"城市合法"}).inputValidator({min:1,onerror: "请选择城市!"}).defaultPassed();
	    $("#partBankName").formValidator({onshow:"请输入支行名称",onfocus:"支行名称不能为空",oncorrect:"支行名称合法"}).inputValidator({min:1,onerror: "请输入支行名称!"});
		$("#bankCard").formValidator({onshow:"请输入银行卡号",onfocus:"银行卡号不能为空",oncorrect:"银行卡号合法"}).inputValidator({min:1,onerror: "请输入银行卡号!"}).regexValidator({regexp:"num",datatype:"enum",onerror:"你输入的银行卡号不正确"});;
		
		$("#drawingMoney").formValidator({onshow:"请输入提款金额，格式100.00",onfocus:"提款金额不能为空",oncorrect:"提款金额合法"})
				.inputValidator({min:1,onerror: "请输入提款金额!"})
				.regexValidator({regexp:"^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$",onerror:"提款金额不正确，格式100.00"})
				.functionValidator({fun:function(val,elem){
					var defaultAccountRemainMoney = parseInt($("#defaultAccountRemainMoney").val());
					var drawMoney= parseInt(val);
					if(drawMoney<10) {
						return "提款金额必须大于10元";
					}
					if(drawMoney>defaultAccountRemainMoney) {
						return "提款金额不能超过账户可提金额";
					}
					var shouxufei=0;
					//银行提现手续费
					if($("#drawingWay").val()=="BANK"){
						if(drawMoney<800){
							shouxufei = 4;
						} else if (800<=drawMoney&&drawMoney<6000){
							shouxufei = drawMoney * parseFloat(5/1000);
						} else {
							shouxufei = 30;
						}
					//支付宝提现手续费
					} else {
						if(drawMoney<20000){
							shouxufei = 1;
						} else if (20000<=drawMoney&&drawMoney<50000){
							shouxufei = 2;
						} else {
							shouxufei = 20;
						}
					}
					//if(drawMoney+parseInt(shouxufei)>defaultAccountRemainMoney) {
					//	return "提款金额+手续费:"+shouxufei+"元   超过 可提金额:"+defaultAccountRemainMoney+"元";
					//}
				    return true;
				 }
			});
			
		//支付宝
		$("#threePartyNum").formValidator({onshow:"请输入支付宝账号",onfocus:"提款支付宝账号不能为空",oncorrect:"提款支付宝账号合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"提款支付宝账号两边不能有空符号"},onerror:"提款支付宝账号不能为空,请确认"});
	}
	
	function chgWay(value){
		if(value=="BANK"){
			$("#bankper").attr("class","now");
			$("#alipayper").attr("class","");
			
			
			$("#bank").css("display","block");
			$("#ali").css("display","none");
			aliUnChk();
			bankChk();	
			
		}else {
			$("#bankper").attr("class","");
			$("#alipayper").attr("class","now");
			
			$("#bank").css("display","none");
			$("#ali").css("display","block");	
			
			bankUnChk();
			aliChk();
			$("#drawingWay").val(value);
		}
	}
	
	function aliUnChk(){
		$("#threePartyNum").attr("disabled",true).unFormValidator(true);
	}
	function aliChk(){
		$("#threePartyNum").attr("disabled",false).unFormValidator(false);
		$("#drawingMoney").attr("disabled",false).unFormValidator(false);
	}
	function bankUnChk(){
		$("#bankName").attr("disabled",true).unFormValidator(true);
		$("#userRealName").attr("disabled",true).unFormValidator(true);
		$("#selProvince").attr("disabled",true).unFormValidator(true);
		$("#selCity").attr("disabled",true).unFormValidator(true);
		
		$("#partBankName").attr("disabled",true).unFormValidator(true);
		$("#bankCard").attr("disabled",true).unFormValidator(true);
	}
	function bankChk(){
		$("#bankName").attr("disabled",false).unFormValidator(false);
		$("#userRealName").attr("disabled",false).unFormValidator(false);
		$("#selProvince").attr("disabled",false).unFormValidator(false);
		$("#selCity").attr("disabled",false).unFormValidator(false);
		
		$("#partBankName").attr("disabled",false).unFormValidator(false);
		$("#bankCard").attr("disabled",false).unFormValidator(false);
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
  <DIV class="yhzxright">
		<DIV class=tb-txt>
		<DIV class=thj>通过转账将您账户中的资金提取到银行卡中。</DIV>
		<DIV class=thj>工作日下午16点集中处理(<FONT 
		color=red>当天16点后的提款申请顺延到第二天处理</FONT>)。</DIV></DIV>
		<div class="czyq">
       	<ul>
		   <li><a href="javascript:chgWay('BANK');" id="bankper"class="now">银行提款</a></li>
		</ul>
		</div>
		<div class="clear"></div>
        <form action="<@c.url value='/user/fund!drawing.action' />" method="post" id="drawingForm">
         <input type="hidden" name="request_token" value="${datetime().millis}" />
         <input type="hidden" id="defaultAccountRemainMoney" value="#{(drawingForm.resultMoney!0);M2}" />
        <input   name="drawingForm.drawingWay" id="drawingWay" class="text_inp" type="hidden" value="BANK"/> 
		   <div class="cyy_zcnr">
	       <#include "/common/message.ftl" /> 
		   <div class="list_div" >
			      <font size=+0>帐户余额：</font>
			      <div class="zhye"> 
			       	现金：#{(drawingForm.resultMoney!0);M2}  &nbsp; 可提余额：#{(drawingForm.resultMoney!0);M2} 元
			      </div> 
		   </div>
		   <div id="bank">
		    <input id="drawingForm_partBankProvince" type="hidden" value="${(drawingForm.partBankProvince)?if_exists}">
			<input id="drawingForm_partBankCity"  type="hidden"  value="${(drawingForm.partBankCity)?if_exists}">	   
		   <div class="list_div" >
			      <font>提款银行：</font>
			       <div><input   name="drawingForm.bankName" id="bankName" class="text_inp" type="text" value="${drawingForm.bankName!}"/>  </div> 
		           <div id="bankNameTip" style="width:250px"></div>	
		   </div>
		   <div class="list_div" >
			      <font>开户名称：</font>
			       <div><#if drawingForm.userRealNameString??>${drawingForm.userRealNameString}</#if></div> 
		           <div id="userRealNameTip" style="width:250px"></div>	
		   </div>
		    <div class="list_div" >
			      <font>开户支行：</font>
			       <div>
			       <select style="width:140px; height:27px;" class="select_l" id="selProvince" name="drawingForm.partBankProvince" onChange = "getCity(this.options[this.selectedIndex].value)">
				         <option value="">---请选择---</option>
				         <option value="北京市"  >北京市</option>
				         <option value="上海市"  >上海市</option>
				         <option value="天津市"  >天津市</option>
				         <option value="重庆市"  >重庆市</option>
				         <option value="河北省"  >河北省</option>
				         <option value="山西省"  >山西省</option>
				         <option value="内蒙古自治区"  >内蒙古自治区</option>
				         <option value="辽宁省"  >辽宁省</option>
				         <option value="吉林省"  >吉林省</option>
				         <option value="黑龙江省"  >黑龙江省</option>
				         <option value="江苏省"  >江苏省</option>
				         <option value="浙江省"  >浙江省</option>
				         <option value="安徽省"  >安徽省</option>
				         <option value="福建省"  >福建省</option>
				         <option value="江西省"  >江西省</option>
				         <option value="山东省"  >山东省</option>
				         <option value="河南省"  >河南省</option>
				         <option value="湖北省"  >湖北省</option>
				         <option value="湖南省"  >湖南省</option>
				         <option value="广东省"  >广东省</option>
				         <option value="广西壮族自治区"  >广西壮族自治区</option>
				         <option value="海南省"  >海南省</option>
				         <option value="四川省"  >四川省</option>
				         <option value="贵州省"  >贵州省</option>
				         <option value="云南省"  >云南省</option>
				         <option value="西藏自治区"  >西藏自治区</option>
				         <option value="陕西省"  >陕西省</option>
				         <option value="甘肃省" >甘肃省</option>
				         <option value="宁夏回族自治区"  >宁夏回族自治区</option>
				         <option value="青海省"  >青海省</option>
				         <option value="新疆维吾尔自治区"  >新疆维吾尔自治区</option>
				         <option value="香港特别行政区"  >香港特别行政区</option>
				         <option value="澳门特别行政区"  >澳门特别行政区</option>
				         <option value="台湾省"  >台湾省</option>
		    	 </select> 
			     <select  id="selCity" name="drawingForm.partBankCity" style="height:27px;" class="select_l">
			         <option value="">---城市---</option>
			     </select>
			    </div> 
			     <div id="selProvinceTip" style="width:80px"></div>
			     <div id="selCityTip" style="width:80px"></div>
		   </div>
		   
		   
		      <div class="list_div" >
			      <font>支行名称：</font>
			       <div><input   name="drawingForm.partBankName" id="partBankName" class="text_inp" type="text" value="${drawingForm.partBankName!}"/>  </div> 
		           <div id="partBankNameTip" style="width:250px"></div>
		   </div>
		   
		   <div class="list_div" >
			      <font>银行卡号：</font>
			       <div><input name="drawingForm.bankCard" id="bankCard" class="text_inp" type="text" value="${drawingForm.bankCard!}"/>  </div> 
		           <div id="bankCardTip" style="width:250px"></div>
		   </div>
		  	
	  </div> 
	  </div>
	  
	  
	  <!--支付宝-->
	  <div id="ali">
	  
		   <div class="list_div" >
			      <font>支付宝账号：</font>
			       <div><input   name="drawingForm.threePartyNum" id="threePartyNum" class="text_inp" type="text" value="${drawingForm.threePartyNum!}"/>  </div> 
		           <div id="threePartyNumTip" style="width:250px"></div>	
		   </div> 	
		</div>
		  <div class="list_div" >
			      <font>提款金额：</font>
			       <div><input name="drawingForm.drawingMoney" id="drawingMoney" class="text_inp" type="text" value="100.00"/> </div> 
		           <div id="drawingMoneyTip" style="width:250px"></div>
		   </div>   
	  <!--支付宝-->
	  <div class="tb-ty">
	      <p style="margin-top: 2px; margin-bottom: 2px;"><input name="agree" id="agree" checked="checked" value="1" type="checkbox">
	        我已阅读了<a href="<@c.url value= "${base}/info/assist/assist.action?type=bzzx-6tkzn"/>" target="_blank"><font color="red"> 用户提款说明 </font></a>并同意其中条款。</p>
	      <p style="margin-top: 13px; margin-bottom: 45px;">
		    <input type="button" value="确认提交申请" id="drawingFormSubmit" class="ButtonStyle_max" />
	      </p>
	       <div id="drawingdialog" style="display: none;">   
            <p style="line-height: 25px;">为了防止少数用户利用信用卡套现和洗钱行为，保证正常用户的资金安全，本站针对提款做出以下规定:</p>
            <p style="line-height: 25px;"><font color="red">1、</font>其累计消费金额（购彩彩票成功的累计数）大于累计存入金额（不包含奖金）的30%的账户，可正常办理提款。</p>
            <p style="line-height: 25px;"><font color="red">2、</font>针对累计消费金额（购彩彩票成功的累计数）小于累计存入金额（不包含奖金）的30%的账户提款申请，本站将加收10%的异常提款处理费用，同时提款到账日自提出申请之日起，不得少于15日。<a style="color: red;" href="http://sns.bctime.cc/space.php?uid=123&amp;do=blog&amp;id=632" target="_blank">“首次充值送15%”</a>活动期间，必须消费充值金额加赠送奖金总和的60%以上方可提款。</p>
            <p style="line-height: 25px; text-align: right; font-weight: 700;">**********************<br>
               2010年4月26日
            </p> 
		  </div>
		 </div> 
</div>
</div>

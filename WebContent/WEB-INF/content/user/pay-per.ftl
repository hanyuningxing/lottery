<head>
	<title>充值</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="payPer" />
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
	<link href="<@c.url value= "/pages/css/drawingper.css"/>" rel="stylesheet" type="text/css" />

</head>
<script>
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

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'payPer'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
   <div class="yhzxright">
    <div style="height:30px;"><div style="width:34px; height:30px; background:url(${base}/V1/images/chongzhi.jpg) -317px 0 no-repeat; float:left;"></div>
    <span class="black_333_14" style="float:left; padding:6px 0 0 5px;">给本账户充值</span></div>
    <div class="border04">
      <div class="zjmxtit">
		  	<ul>
				<li style="color:#000; background:url(images/zjmx.png) 0 -46px; width:117px; text-align:center; font-size:14px; font-weight:bold; height:30px; line-height:30px;">购彩充值</li>
			</ul>
      </div>
      
	  <div style="padding:20px 10px; font-size:14px;">
	  <form action="<@c.url value="/user/fund!commTraceMarker.action" />" method="post" id="commTraceMarker_form"> 	  
	     <input name="payWay" value="1" type="hidden"/>
      	<input name="checkSpan" value="false" type="hidden"/>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px dashed #ccc;">
          <tr>
            <td width="11%" height="40" align="right" class="black_222_14">用户名：</td>
            <td class="rc1">${userName?default("")}</td>
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
         	<#include "/common/message.ftl" />
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
            <td><a href="#" id="rechargeLotteryCard" onclick="javascript:$('#commTraceMarker_form').submit(); return false;" style="width:147px; height:34px; background:url(${base}/V1/images/chongzhi.jpg) 0 0 no-repeat; display:block;"></a></td>
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










	

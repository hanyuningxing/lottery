<head>
	<title>网上银行支付充值</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="payPer" />

	<link href="<@c.url value= "/pages/css/payper.css"/>" rel="stylesheet" type="text/css" />
	<style>
	.cyy_czbg td {
	    border: 1px solid #D9D9D9;
	    color: #000000;
	    font-size: 13px;
	    height: 35px;
	    line-height: 35px;
	    padding-left: 8px;
	    text-align: left;	 
	}
	
	
	#center_main .crighttop .zhcz_bt {
	    margin: 0;
	    padding: 4px 0 0 7px;
	}
	.cyy_ljcz_anniu{
     margin:0px auto;
	 padding:0px;
	 margin-top:7px;
	 margin-bottom:15px;
	 }

.cyy_ljcz_anniu p{
     margin-top:5px;
	 font-size:12px;
	 }
	</style>
	<script type="text/javascript"> 
	var PopupLayer;
	$(document).ready(function(){
			//初始化弹框组件
		   PopupLayer = new PopupLayer({usePopupIframe:true,trigger:"#openPay",popupBlk:"#parper",closeBtn:"#closePay",offsets:{x:-100,y:-300}
		    });
		   
		   
		
		   
		   
	});
	function chkit(elm) { 
		var bank = document.getElementById(elm); 
		bank.checked = "checked"; 
		var hatImage = document.getElementById("bank_img");
		var img = "images/czym/"+bank.value+".gif";
        hatImage.setAttribute("src",img);
	} 
	function tradeQuery(){
	   var payWay =$("#payWay").val().trim();
	   if(0==payWay){
	      yeepaySingleTradeQuery();
	   }else if(1==payWay){
	      alipaySingleTradeQuery();
	   }
	}
	function alipaySingleTradeQuery(){
				var customOrderNo =$("#customOrderNo").val().trim();
				
				if(customOrderNo == ""){
					alert('订单号不能为空');
					return false;
				}
				var url = window.BASESITE + '/user/fund!alipaySingleTradeQuery.action?orderNo='+customOrderNo;
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
			}
	     function yeepaySingleTradeQuery(){
				var customOrderNo =$("#customOrderNo").val().trim();
				
				if(customOrderNo == ""){
					alert('订单号不能为空');
					return false;
				}
				var url = window.BASESITE + '/user/fund!yeepaySingleTradeQuery.action?orderNo='+customOrderNo;
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
		<div style="height:30px;">
		<div style="width:34px; height:30px; background:url(/lottery/V1/images/chongzhi.jpg) -317px 0 no-repeat; float:left;"></div>
		<span class="black_333_14" style="float:left; padding:6px 0 0 5px;">给本账户充值</span>
		</div>
		<div class="kong20"></div>
		<input id="customOrderNo" name="customOrderNo" value="${commTraceForm.customOrderNo!}" type="hidden"/>
		<input id="payWay" name="payWay" value="${commTraceForm.payWay!}" type="hidden"/>
	     <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab" style="font-size:14px;">
	     	  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">订单流水号：</td>
				<td width="587" valign="middle" bgcolor="#FFFFFF">${orderNoFormat!}</td>
			  </tr>	
			  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">${webapp.webName}帐户名：</td>
				<td width="587" valign="middle" bgcolor="#FFFFFF">${commTraceForm.userName}</td>
			  </tr>
			  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">充值金额：</td>
				<td valign="middle" bgcolor="#FFFFFF"><span>${commTraceForm.amount!}</span> 元</td>
			  </tr>
			  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">支付渠道：</td>
				<td valign="middle" bgcolor="#FFFFFF"><img src="images/czym/${commTraceForm.bankPic!}.gif" width="125" height="30" /></td>
			  </tr>
			  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">确认充值：</td>
				<td valign="middle" bgcolor="#FFFFFF">
				<div class="cyy_ljcz_anniu"><a  href="${commTraceForm.alipayUrl}" target="_blank"><image id="openPay" src="images/cyy_qrcz_anniu.gif"/></a> 
					<span>将会在新窗口中打开网上银行页面</span>
				</div>
				</td>
			  </tr>
			</table>
  </div>
</div>


<div id="parper" style="display:none" class="zftsk">
		<div class="zfts_bg">
		   <div class="dlfk">登录支付宝付款</div>
		   <div class="ajbg"><a href="#"><img id="closePay" src="<@c.url value= "/pages/images/zfts_05.gif"/>" border="0" /></a></div>
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
   </div>
   
 





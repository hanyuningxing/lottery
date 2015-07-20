<head>
	<title>提款记录</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="drawing" />
	<!--<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
</head>
<style type="text/css">
<!--
#Layer1 {
	position:absolute;
	width:200px;
	height:115px;
	z-index:1;
	left: 1px;
	top: 253px;
}
#Layer2 {
	position:absolute;
	width:200px;
	height:115px;
	z-index:1;
}
.white {color:#FFF;}
.black {color:#333;}
.blue_0091d41 {color:#0091d4; text-decoration:none;}
.green {color:#009b02;}
.red {color:#F00;}
.STYLE1 {color: #F00; font-weight: bold; }
-->
</style>
<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'drawing'/>
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
			  <a href="${base}/user/fund!drawingPer.action">
				<li>申请提款</li>
			  </a>
			  <a href="${base}/user/fund!tkManager.action">
				<li>提款管理</li>
			  </a>
				<a href="#">
				<li class="zjmxli01">提款订单</li>
				</a>
		  	</ul>
      </div>
	  <div id="extra-data-container" style="color:#333; padding:10px 0;">
	    	<script>
             	$(function(){
		         		$.post(window.BASESITE + '/user/fund!drawingListTable.action', {
		         			
		         		}, function(data){
		         			document.getElementById('extra-data-container').innerHTML = data;
		         		});
		        
		         });
		      
		       </script>
             	<#import "../../macro/pagination.ftl" as b />
			    <@b.page {'ajaxContainer':'extra-data-container'} />
       </div>
    </div>
    <div style="padding:20px 16px; line-height:24px;"><strong class="bro_af5529">订单状态说明： </strong><br />
      <span class="bro_6D4F42">【未处理】订单尚未处理，请耐心等待审核，预计30分钟内审核；<br />
【已审核】提款订单已经通过工作人员审核，请耐心等待财务汇款，预计2-4小时内汇款；<br />
【已汇出】提款成功汇出，各银行到账时间请参考提款向导；<br />
【取   消】用户自行取消此次提款；<br />
【拒   绝】因提款资金或资金有问题时，工作人员拒绝了提款申请。</span></div>
  </div>


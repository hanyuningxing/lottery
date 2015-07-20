 <head>
	<title>提款管理</title>
	<meta name="decorator" content="tradeV1" />

</head>
<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'drawingPer'/>
      <#include "left.ftl" />
  </div> 
 <div class="yhzxright">
  	   <#include "user-loginInfo.ftl">
    <div class="border04">
      <div class="zjmxtit">
		  	<ul>
				<a href="yhzx04-01.htm">
				<li class="zjmxli01">提款向导</li>
			  </a>
			  <a href="${base}/user/fund!drawingPer.action">
				<li>申请提款</li>
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
	    <table width="100%" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
	      <tr>
            <td width="15%" height="30" align="center" bgcolor="#d5f6fd"><strong>银行名称</strong> </td>
	        <td width="37%" align="center" bgcolor="#d5f6fd"><strong>到账时间</strong> </td>
	        <td align="center" bgcolor="#d5f6fd"><strong>手续费为银行实收，非广东肇庆地区为异地</strong> </td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF" class="tkxdjian">工商银行</td>
            <td align="center" bgcolor="#FFFFFF">汇出后24小时内到账</td>
            <td align="center" bgcolor="#FFFFFF">0 </td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF" class="tkxdjian">建设银行</td>
            <td align="center" bgcolor="#FFFFFF">汇出后24小时内到账</td>
            <td align="center" bgcolor="#FFFFFF">0</td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF" class="tkxdjian">农业银行</td>
            <td align="center" bgcolor="#FFFFFF">汇出后24小时内到账（银行系统正常）</td>
            <td align="center" bgcolor="#FFFFFF">0</td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF">其他银行</td>
            <td align="center" bgcolor="#FFFFFF">汇出后2-4个工作日内到账</td>
            <td align="center" bgcolor="#FFFFFF">0</td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#d5f6fd"><strong>支付平台</strong> </td>
            <td align="center" bgcolor="#d5f6fd"><strong>到账时间</strong> </td>
            <td align="center" bgcolor="#d5f6fd"><strong>手续费</strong> </td>
          </tr>
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF">支付宝</td>
            <td align="center" bgcolor="#FFFFFF">汇出后即时到账 </td>
            <td align="center" bgcolor="#FFFFFF">0</td>
          </tr>
        </table>
      </div>
    </div>
    <div style="padding:20px 16px; line-height:24px;"><strong class="bro_af5529">提款说明： </strong><br />
      <span class="bro_6D4F42">1.   周一至周日每天处理提款申请，每日下午16:00以后的提款申请，会在次日早9：00后得到处理；法定节假日的提款申请会顺延至节后。 <br />
2.每笔提款申请不能少于10元。当天提款在20万或以上，处理时间需时3个工作日，请耐心等候，不便之处请见谅。<br />
3.您的提款申请需要先经过我们工作人员核对，核对无误后通过人工办理汇款，我们将在一个工作日内将款项汇出。<br />
4.如果因为您填写的注册资料不完善，无法使用网上取款功能，请与本网客服人员联系。<br />
5.提款手续费是根据不同银行的标准资费由银行扣除，网站不会收取您任何提款费用。<br />
6.办理提现时涉嫌洗钱行为(充值预付款购彩不到80%)，本网将拒绝办理并上报发卡银行，您可通过“退款申请”把充值资金退回原支付银行卡或支付账号。</span></div>
  </div>
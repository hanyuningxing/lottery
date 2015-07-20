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
				<td valign="middle" bgcolor="#FFFFFF"><img src="${base}/user/images/czym/${commTraceForm.bankPic!}.gif" width="125" height="30" /></td>
			  </tr>
			  <tr>
				<td width="25%" height="40" align="right" valign="middle" bgcolor="#D5F6FD">确认充值：</td>
				<td valign="middle" bgcolor="#FFFFFF">
				<div class="cyy_ljcz_anniu"><a href="${commTraceForm.alipayUrl}" target="_blank" onclick="toPay();"><image id="openPay" src="${base}/user/images/cyy_qrcz_anniu.gif"/></a> 
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
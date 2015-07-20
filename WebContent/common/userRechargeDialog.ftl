<script type="text/javascript">
	function doRecharge(obj){
		closeDialog('userRechargeDialogDiv');
		obj.target="_blank";
		obj.href = "${base}/user/fund!payPer.action";
	}
</script>

<div class="twindowsWarp" id="userRechargeDialogDiv" style="display:none">
	<div class="twindows">
	  <div class="twindowstop">
	    <div class="close" onclick="closeDialog('userRechargeDialogDiv');return false;"></div>
	    ${webapp.webName}网
	  </div>
	  <div class="twbg">
	    <div class="twbgyel">
	      <div class="rightgou"></div>
	      <span class="worgchar">您当前账户余额不足,请充值后购买</span>
	      <a href="javascript:;" onclick="doRecharge(this);" class="tbt1">我要充值</a> <a href="javascript:;" onclick="closeDialog('userRechargeDialogDiv');return false;" class="tbt1">我知道了</a> </div>
	  </div>
	</div>
</div>
<head>
	<title>提款管理</title>
	<meta name="decorator" content="tradeV1" />
	<script type="text/javascript" src="<@c.url value= "/js/city.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/user/bindMobile.js"/>"></script>
</head>
<style>
.xiugaibg {
	width:80px;
	height:30px;
	line-height:30px;
	color:#fff;
	text-align:center;
	background:url(${base}/V1/images/xiugaibg.png) no-repeat;
	display:inline-block;
	text-decoration:none;
	cursor:pointer;
	margin:0 auto;
}
a.xiugaibg, a.xiugaibg:visited, a.xiugaibg:hover {
	color:#fff;
	text-decoration:none;
}
.input1 {
	width:200px;
	height:20px;
	line-height:20px;
	color:#999;
	border:1px solid #A4ACB4;
	margin:0;
	padding:0;
}
.tdright10 {
	padding-right:10px;
}
</style>
<script>
$(function(){
	createDialog('#msgValidateDialog', '手机验证', 420);
});	
	
	$(function(){
		$("#delete").click(function(){
			if(confirm('确定要删除吗？')) {
				
				$.post(window.BASESITE + '/user/user!checkMobileExist.action', {   					 
		}, function(data) {
			if(data.exist) {
				$("#msgValidateDialog").dialog('open');
				document.getElementById("content").innerHTML = '你的手机号码为' + data.mobile + ',需要先进行手机验证';
				document.getElementById("return").onclick = function() {$("#msgValidateDialog").dialog('close');};
				
				$("#getMsgCode").off('click').click(function(){
					$.post(
						window.BASESITE + '/user/user!sendMsg.action',
					{},function(data){
						if(data.success){
							document.getElementById('getMsgCode').setAttribute("disabled", true);				
							var i = 60;
							document.getElementById("getMsgCode").value = i-- + "秒后可点击重发"; 
						    var h = setInterval((function(i){
						        return function() {			        	
						        	document.getElementById("getMsgCode").value = i-- + "秒后可点击重发";
						        	if(i <= 0) {
						        		clearInterval(h);
						        		document.getElementById("getMsgCode").value="获取短信验证码";
						        		document.getElementById("getMsgCode").removeAttribute("disabled");	        		
						        	}
						        }
						    }(i)), 1000);						
							
						} else {
							setCommonResult(data.msg,data.success);
						}	
					},'json');
				});								
			} else {
				setCommonResult(data.msg,data.exist);
				showBindBankCardDlog = true;
				setTimeout(function(){$("#msgValidate").click();$("#commonResultDialog").dialog('close');}, 2000);
			}
			
			$('#msgValidateform').off('submit').submit(function() {
				var msgCode = $('#msgValidateform [name="yanzhengma"]').val();							
				$.post(window.BASESITE + '/user/user!msgValid_.action', {
					 msgCode: msgCode
				}, function(data) {
						document.getElementById('yanzhengma').value="";							

						if(data.success) {
							$("#msgValidateDialog").dialog('close');
							$.post(window.BASESITE + '/user/user!deleteBankInfo.action', {
							}, function(data) {
								setCommonResult(data.msg,data.success);
								setTimeout(function(){window.loaction.reload();},2000);
							},'json');			
						} else {
							document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
							setTimeout(function(){document.getElementById("error").innerHTML = "";}, 3000);
						}															
				}, 'json');
				
				 return false;  //阻止表单提交
			 });	 
		},'json');
					
			}					
		});												 					 		
	});

	
	$(function(){
		$("#btnBmVfyCd").click(function(){
				$.post(window.BASESITE + '/user/user!sendMsg.action',
				{},function(data){
					if(data.success){
						document.getElementById('btnBmVfyCd').setAttribute("disabled", true);
						document.getElementById('sendResult').innerHTML = "已向你的手机发送验证码，一分钟后如果还没有收到请点击重发！";
						setTimeout(function(){document.getElementById('sendResult').innerHTML = "";},3000);						
						var i = 60;
						document.getElementById("btnBmVfyCd").value = i-- + "秒后可点击重发";
					    var h = setInterval((function(i){
					        return function() {			        	
					        	document.getElementById("btnBmVfyCd").value = i-- + "秒后可点击重发";
					        	if(i <= 0) {
					        		clearInterval(h);
					        		document.getElementById("btnBmVfyCd").value="获取短信验证码";
					        		document.getElementById("btnBmVfyCd").removeAttribute("disabled");
					        		document.getElementById("btnBmVfyCd").setAttribute("abled", true);			        		
					        	}
					        }
					    }(i)), 1000);						
						
					} else {
						document.getElementById('sendResult').innerHTML = '你还未绑定手机，请先绑定手机';
						checkMobileExist();
					}	
				},'json');
			});												 					 		
	});
	
	$(function(){
		var partBankProvince = $("#drawingForm_partBankProvince").val();
		var partBankCity = $("#drawingForm_partBankCity").val();
		if(null!=partBankProvince) {
			 getCity(partBankProvince);
			 $("#selProvince").val(partBankProvince);
			 $("#selCity").val(partBankCity);
		}			
	});	
		
	$(function(){ 
		createDialog('#commonResultDialog', '返回信息', 350, 219);
		$("#bind").click(function(){
			var bankName = $("#bankName").val();
			var partBankProvince = $("#selProvince").val();
			var partBankCity = $("#selCity").val();
			var partBankName = $("#partBankName").val();
			var bankCard = $("#bankCard").val();
			var userRealName = $("#userRealName").val();
			var yanzhengma = $("#yanzhengma").val();
				$.post(window.BASESITE + '/user/user!bindBankCard.action',{
					'bankInfoForm.bankName': bankName,
					'bankInfoForm.partBankProvince': partBankProvince,
					'bankInfoForm.partBankCity': partBankCity,
					'bankInfoForm.partBankName': partBankName,
					'bankInfoForm.bankCard': bankCard,
					'bankInfoForm.userRealName': userRealName,
					'bankInfoForm.yanzhengma': yanzhengma
				},function(data){
					setCommonResult(data.msg,data.success);
					if(data.needValidateIdCard!=null && data.needValidateIdCard) {
						setTimeout(function(){window.location = window.BASESITE + '/user/user!userSafeManager.action' ;}, 2000);
					}
					if(data.success) {
						setTimeout(function(){window.location = window.BASESITE + '/user/fund!tkManager.action';}, 2000);
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
	//绑定手机
	$(function(){
		$("#bindMobile").click(function(){
			writeMobileAndValidate();
		});
		
   	});
   	
	//短信验证后执行此方法
	function msgValidate(data) {
			document.getElementById('yanzhengma').value="";
			if(data.success) {
				$("#msgValidateDialog").dialog('close');						
				setCommonResult(data.msg,data.success);
				setTimeout(
					function(){
						window.location.reload();
					}
				,2000);
			} else {
				document.getElementById("error").innerHTML = '验证码不正确，请重新输入验证码！';
				setTimeout(
						function(){
							document.getElementById("error").innerHTML = "";
						}
				, 3000);
			}
		
	}
</script>

<!--手机验证对话框 -->
<div id="msgValidateDialog" style="margin:20px;display:none">
	<div><span id="content"></span></div></br>	
	<form id="msgValidateform" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td align="right" width="45" class="tdright10">验证码</td>
		    <td><input type="text" style="width:60px" name="yanzhengma" id="yanzhengma"/>
		    <input style="height:25px"  type="button" id="getMsgCode" value="获取验证码" /><span id="error" style="color:red"></span></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#msgValidateform').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" id="return" onclick="reInputMobile();" class="xiugaibg">返回</a></td>
		   
		  </tr>
		</table>		
	</form>
</div> 
<!--手机号码输入对话框 -->
<div id="mobileWriteDialog" style="margin:20px;display:none">
	<span><p>请输入需要绑定的手机号码！</p></span>	</br>	
	<form id="mobileWriteForm" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td width="60" align="right" class="tdright10">手机号码</td>
		    <td><input type="text" name="mobile" id="mobile" class="input1" /></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#mobileWriteForm').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" onclick="$('#mobileWriteDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>		
	</form>
</div> 

<div id="commonResultDialog" style="padding: 0px;display:none"> 
	<div class="tckbg">	
			<div class="tcknr">
				<table width="90%" border="0" cellspacing="0" cellpadding="0" align="center">
	       			<tr>
						<td width="50"><span id="tckico"></span></td>
						<td><span class="tckwz" id="commonResultDialogData"></span></td>						
					</tr>	
				</table>
			</div>
		<div style="padding:0 25px;"><a href="javascript:;" onclick="$('#commonResultDialog').dialog('close');" class="tckan"></a></div>	
	</div>
</div>

<div id="msgValidateDialog" style="margin:20px;display:none">
	<div><span id="content"></span></div></br>	
	<form id="msgValidateform" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td align="right" width="45" class="tdright10">验证码</td>
		    <td><input type="text" style="width:60px" name="yanzhengma" id="yanzhengma"/>
		    <input style="height:25px"  type="button" id="getMsgCode" value="获取验证码" /><span id="error" style="color:red"></span></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#msgValidateform').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" id="return" onclick="reInputMobile();" class="xiugaibg">返回</a></td>
		   
		  </tr>
		</table>		
	</form>
</div> 

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
				<a href="${base}/user/fund!tkNavigator.action">
				<li>提款向导</li>
			  </a>
			  <a href="${base}/user/fund!drawingPer.action">
				<li>申请提款</li>
			  </a>
			  <a href="javascript:;">
				<li class="zjmxli01">提款管理</li>
			  </a>
				<a href="${base}/user/fund!drawingList.action">
				<li>提款订单</li>
				</a>
		  	</ul>
      </div>
	  <div style="color:#333; padding:10px 0;">
	    <table width="100%" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab" style="margin-bottom:20px;">
	      <tr>
            <td width="10%" height="30" align="center" bgcolor="#D5F6FD"><strong>修改默认</strong></td>
	        <td width="17%" align="center" bgcolor="#D5F6FD"><strong>银行/平台</strong></td>
	        <td width="36%" align="center" bgcolor="#D5F6FD"><strong>开户行名称</strong></td>
	        <td width="23%" align="center" bgcolor="#D5F6FD"><strong>银行账号</strong></td>
	        <td align="center" bgcolor="#D5F6FD"><strong>操作</strong></td>
          </tr>
          <#if drawingForm.bankCard?? && drawingForm.bankCard != "">
          <tr>
            <td height="30" align="center" bgcolor="#FFFFFF"><input name="radiobutton" type="radio" value="radiobutton" checked="checked" /></td>
            <td align="center" bgcolor="#FFFFFF" class="black_222_14">${drawingForm.bankName!}</td>
            <td align="center" bgcolor="#FFFFFF" class="black_222_14">${drawingForm.partBankName!}</td>
            <td align="center" bgcolor="#FFFFFF" class="black_222_14">${drawingForm.bankCard!}</td>
            <td align="center" bgcolor="#FFFFFF" class="black_222_14"><a id="delete" href="javascript:;">删除</a></td>
          <tr>
          <#else>
          <tr>
          	<td colspan="5" align="center" style="color:red">请先绑定银行账号！</td>
          </tr>
          </#if>
        </table>
        <span class="black_333_14"><strong>绑定银行账号</strong>        </span>   
        <table width="98%" cellpadding="7" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
          <tbody>
            <tr>
              <td width="120" height="30" align="right" bgcolor="#D5F6FD">*<strong>开户银行：</strong></td>
              <td valign="center" bgcolor="#FFFFFF">
              	  <select id="bankName" name="bankName" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
	                  <option selected="selected" value="">-----请选择银行-----</option>
	                  <option value="中国工商银行">中国工商银行</option>
	                  <option value="中国建设银行">中国建设银行</option>
	                  <option value="中国农业银行">中国农业银行</option>
	                  <option value="中国银行">中国银行</option>
	                  <option value="招商银行">招商银行</option>
	                  <option value="交通银行">交通银行</option>
	                  <option value="广发银行">广发银行</option>
	                  <option value="中信银行">中信银行</option>
	                  <option value="中国民生银行">中国民生银行</option>
	                  <option value="中国平安银行">中国平安银行</option>
	                  <option value="华夏银行">华夏银行</option>
	                  <option value="兴业银行">兴业银行</option>
	                  <option value="农村信用合作社">农村信用合作社</option>
	                  <option value="上海浦东发展银行">上海浦东发展银行</option>
	                  <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
	                  <option value="东亚银行">东亚银行</option>
                  </select>
                </td>
            </tr>
            <tr>
              <td height="30" align="right" valign="center" bgcolor="#D5F6FD">*<strong>开户省份：</strong></td>
              <td bgcolor="#FFFFFF">
              		<select id="selProvince" name="partBankProvince" onChange = "getCity(this.options[this.selectedIndex].value)" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
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
              </td>
            </tr>
            <tr>
              <td height="30" align="right" valign="center" bgcolor="#D5F6FD"><strong>开户城市：</strong></td>
              <td bgcolor="#FFFFFF">             
                 <select id="selCity" name="partBankCity" style="height:25px; border:1px solid #afbbc7; color:#333; font-size:14px; padding:3px 0;">
			         <option value="">---城市---</option>
			     </select>             
                <span class="gray">如果找不到所在城市，可以选择所在地区或者上级城市 </span></td>
            </tr>
            <tr id="trBankAddr">
              <td height="30" align="right" bgcolor="#D5F6FD"><strong>开户行名称：</strong></td>
              <td bgcolor="#FFFFFF"><input name="partBankName" id="partBankName" type="text" class="tkxdbd" size="25" /> 
                <span class="gray">XX分行（支行或营业部） </span></td>
            </tr>
            <tr>
              <td height="30" align="right" bgcolor="#D5F6FD">*<strong>银行账号：</strong></td>
              <td bgcolor="#FFFFFF"><input name="bankCard" id="bankCard" type="text" class="tkxdbd" size="25" /> 
                <span class="gray">该账号的开户人姓名必须与您的账号真实姓名一致，否则提款申请会被取消 </span></td>
            </tr>
            <tr>
              <td height="30" align="right" bgcolor="#D5F6FD">*<strong>核对真实姓名：</strong></td>
              <td bgcolor="#FFFFFF"><input id="userRealName" name="userRealName" type="text" class="tkxdbd" size="25" /></td>
            </tr>
            <tr>
              <td height="30" align="right" bgcolor="#D5F6FD">*<strong>手机号码：</strong></td>
              <td bgcolor="#FFFFFF"><#if loginUser.info?? && loginUser.info.mobile?? && loginUser.info.mobile != "">${loginUser.info.mobile!""}<#else><a id="bindMobile" style="color:red" >你还没绑定手机，请先进行绑定！</a></#if></td>
              <input type="hidden" name="mobile" value=${loginUser.info.mobile!""}>
            </tr>            
            <tr>
              <td height="30" align="right" bgcolor="#D5F6FD"><strong>短信验证码：<></td>
              <td bgcolor="#FFFFFF"><input id="yanzhengma" name="yanzhengma" type="text" class="tkxdbd" size="6" />
              <input id="btnBmVfyCd" value="获取短信验证码" type="button" name="btnBmVfyCd" class="tkxdyzm" />
              <span id="sendResult" style="color:red"></span>
              </td>
            </tr>
            <tr>
              <td height="30" bgcolor="#FFFFFF">&nbsp;</td>
              <td height="45" bgcolor="#FFFFFF"><a id="bind" href="javascript:;" class="qrbdan"/></a></td>
            </tr>
          </tbody>
        </table>
	  </div>
    </div>
    <div style="padding:20px 16px; line-height:24px;"><strong class="bro_af5529">说明： </strong><br />
      <span class="bro_6D4F42">1、添加的银行账户开户人姓名必须与注册的真实姓名一致，否则提款申请将被退回。<br />
2、提款信息填写有误会导致提款会被银行退单，产生的手续费由用户承担。</span></div>
  </div>
function getCreateForm() {
	return document.getElementById('createForm');
}

function chgMultiple(obj) {
	var createForm = getCreateForm();
	var multipleEl = createForm.elements['createForm.multiple'];
	var span_other_multiple = document.getElementById('span_other_multiple');
	if (/\d+/.test(obj.value)) {
		span_other_multiple.style.display = 'none';
		multipleEl.value = obj.value;
		updateBetCost();
	} else {
		span_other_multiple.style.display = '';
		multipleEl.focus();
	}
	
}

/**
 * 
 * @param obj
 * @return
 */
function chgSubscrLicense(obj) {
	var createForm = getCreateForm();
	var spanObj = document.getElementById("span_createForm_subscriptionPassword");
	var pwdObj = createForm.elements['createForm.subscriptionPassword'];
	if (obj.value == 'PASSWORD_LICENSE') {
		spanObj.style.display = '';
		pwdObj.value = '';
		pwdObj.disabled = false;
		pwdObj.focus();
	} else {
		spanObj.style.display = 'none';
		pwdObj.value = '';
		pwdObj.disabled = true;
	}
}

/**
 * 更改购买方式(数字彩。要更新追号)
 * 
 * @param obj
 * @return
 */
function numChgShareType(obj) {
	var createForm = getCreateForm();
	var TOGETHER_TR = $(createForm).find('tr[_name="TOGETHER_TR"]');
	var SELF_TR = $(createForm).find('tr[_name="SELF_TR"]');
	var createForm_chase = document.getElementById("createForm_chase");
	if (obj.value == 'TOGETHER') {
		for ( var i = 0; i < TOGETHER_TR.length; i++) {
			TOGETHER_TR[i].style.display = '';
		}
		for ( var i = 0; i < SELF_TR.length; i++) {
			SELF_TR[i].style.display = 'none';
		}
		createForm_chase.checked=false;
		chgChase(false);
		createForm.elements['createForm.shareType'].value = 'TOGETHER';
		document.getElementById("createForm_secretType_FULL_PUBLIC").checked=true;
	}else if (obj.value == 'SELF') {
		for ( var i = 0; i < TOGETHER_TR.length; i++) {
			TOGETHER_TR[i].style.display = 'none';
		}
		for ( var i = 0; i < SELF_TR.length; i++) {
			SELF_TR[i].style.display = '';
		}
		createForm_chase.checked=false;
		chgChase(false);
		createForm.elements['createForm.shareType'].value = 'SELF';
		document.getElementById("createForm_secretType_FULL_SECRET").checked=true;
	}else if (obj.value == 'CAHSE') {
		for ( var i = 0; i < TOGETHER_TR.length; i++) {
			TOGETHER_TR[i].style.display = 'none';
		}
		for ( var i = 0; i < SELF_TR.length; i++) {
			SELF_TR[i].style.display = '';
		}
		createForm_chase.checked=true;
		createForm.elements['createForm.shareType'].value = 'SELF';
		chgChase(true);
		document.getElementById("createForm_secretType_FULL_SECRET").checked=true;
	}else if (obj.value == 'CAPACITYCHASE') {
		for ( var i = 0; i < TOGETHER_TR.length; i++) {
			TOGETHER_TR[i].style.display = 'none';
		}
		for ( var i = 0; i < SELF_TR.length; i++) {
			SELF_TR[i].style.display = '';
		}
		createForm_chase.checked=true;
		createForm.elements['createForm.shareType'].value = 'SELF';
		document.getElementById("createForm_secretType_FULL_SECRET").checked=true;
	}
}
function dltAdditional(box) {
	var createForm = getCreateForm();
	var unit = createForm.elements['createForm.units'].value;
	var createForm_betMoney = document.getElementById("createForm_betMoney");
	if (box.checked) {
		// /选择追加
		createForm_betMoney.value = 3;
		createForm.elements['createForm.dltAdditional'].value = true;
	} else {
		createForm_betMoney.value = 2;
		createForm.elements['createForm.dltAdditional'].value = false;
	}
	updateBetUnits(unit);
}

/**
 * 更新注数
 * 
 * @param createForm 方案表单对象
 * @param units 注数
 */
function updateBetUnits(units) {
	var createForm = getCreateForm();
	createForm.elements['createForm.units'].value = units;
	$(createForm).find('span[_name="createForm_units"]').text(units);
	updateBetCost();
}
/**
 * 更新购买比例
 * 
 */
function updateSubscriptionPer() {
	var createForm = getCreateForm();
	var schemeCost = createForm.elements['createForm.schemeCost'].value;
	var subscriptionPerSpan = document.getElementById("subscriptionPerSpan");
	var subscriptionCost = createForm.elements['createForm.subscriptionCost'].value;
	if (null == schemeCost || 0 == schemeCost || null == subscriptionCost || 0 == subscriptionCost) {
		subscriptionPerSpan.innerHTML = "0.00";
		return;
	}
	var per = subscriptionCost / schemeCost * 100;
	subscriptionPerSpan.innerHTML = formatNumFor_(per);
}
/**
 * 更新保底比例
 * 
 */
function updateBaodiCostPer() {
	var createForm = getCreateForm();
	var schemeCost = createForm.elements['createForm.schemeCost'].value;
	var baodiCostPerSpan = document.getElementById("baodiCostPerSpan");
	var baodiCost = createForm.elements['createForm.baodiCost'].value;
	if (null == schemeCost || 0 == schemeCost || null == baodiCost || 0 == baodiCost) {
		baodiCostPerSpan.innerHTML = "0.00";
		return;
	}
	var per = baodiCost / schemeCost * 100;
	baodiCostPerSpan.innerHTML = formatNumFor_(per);
}
function formatNumFor_(num) {
	if (null == num)
		return "0.00";
	return num.toFixed(2);
}

/**
 * @param createForm 方案表单对象
 * @return 每注彩票金额
 */
function getBetMoney() {
	// var createForm = getCreateForm();
	var betMoneyObj = document.getElementById('createForm_betMoney');
	if (betMoneyObj != null)
		return betMoneyObj.value;
	else
		return 2;
}

function getMultiple() {
	var createForm = getCreateForm();
	var multipleObj = createForm.elements['createForm.multiple'];
	if (multipleObj != null && !multipleObj.disabled)
		return multipleObj.value;
	else
		return 1;
}

/**
 * 
 * 倍数加减1
 *
 * @param value
 * @param index
 */
function AddMutiple(value)
{
	var createForm = getCreateForm();
	var multiple=parseInt(createForm.elements["createForm.multiple"].value);
	multiple+=value;
	if(typeof playTypeWeb != "undefined" && playTypeWeb == "DGP") {
		if(multiple<=9) multiple=10;
	}
	else if(typeof playTypeWeb != "undefined" && playTypeWeb == "YP") {
		if(multiple<=124) multiple=125;
	}else {
		if(multiple<=0) multiple=1;
	}
	
	createForm.elements["createForm.multiple"].value = multiple;
	updateBetCost();
}

	
/**
 * 显示投注对话框
 */
window.confirmDialog = function(){
	var isOk = buildConfirmData();
	if(typeof(isOk)!="undefined" && !isOk){return;}
	//$("#dialogDiv").css("display","block");
	//fixedBox("dialogDiv", 'center','center');
	//fadeon("dialogDiv");
	
	$("#dialogDiv").dialog('open');
};

/**
 * 更新方案金额
 * 
 * @param createForm 方案表单对象
 */
function updateBetCost() {
	var createForm = getCreateForm();
	var units = createForm.elements['createForm.units'].value;
	var betMoney = getBetMoney();
	var multiple = getMultiple(createForm);
	var schemeCost = units * multiple * betMoney;
	if (createForm.elements['schemeCost']) {
		createForm.elements['schemeCost'].value = schemeCost;
	}
	createForm.elements['createForm.schemeCost'].value = schemeCost;
	$(createForm).find('span[_name="createForm_schemeCost"]').text(schemeCost);
	var shareTypeElArr = $(createForm).find('input[type="radio"][name="createForm.shareType"]:checked');
	if (shareTypeElArr != null && shareTypeElArr.length == 1 && shareTypeElArr[0].value == 'TOGETHER') {
		updateSubscriptionPer();
		updateBaodiCostPer();
	}
	//计算最小奖金
	if(typeof countMinPrize === 'function'){
		countMinPrize();
		
	}
	
	//计算最大奖金
	if(typeof countMaxPrize === 'function'){
		countMaxPrize();
	}
	
}



function getConfirmMsg() {
	var createForm = getCreateForm();
	// TODO 生成确认信息
	return '您确认要提交吗？';
}

// 检查表单数据,通过 throw '错误信息' 这样的方式抛出错误信息;
function checkCreateForm() {
	var createForm = getCreateForm();
	var units = createForm.elements['createForm.units'].value;
	units = parseInt(units, 10);
	if (units <= 0)
		throw '方案注数必须大于零！';

	var schemeCost = createForm.elements['createForm.schemeCost'].value;
	schemeCost = (/\d/.test(schemeCost)) ? parseInt(schemeCost, 10) : 0;
	if (schemeCost <= 0)
		throw '方案金额必须大于零！';

	var multiple = getMultiple(createForm);
	if (multiple <= 0)
		throw '倍数必须大于0！';

	var shareTypeEl = createForm.elements['createForm.shareType'];
	if (shareTypeEl != null && shareTypeEl.value == 'TOGETHER') {
		var subscriptionCost = createForm.elements['createForm.subscriptionCost'].value;
		subscriptionCost = (/\d/.test(subscriptionCost)) ? parseInt(subscriptionCost) : 0;
		if (subscriptionCost <= 0)
			throw '认购金额必须大于零！';
		else if (subscriptionCost > schemeCost)
			throw '认购金额不能大于方案金额！';

		var baodiCost = createForm.elements['createForm.baodiCost'].value;
		baodiCost = (/\d/.test(baodiCost)) ? parseInt(baodiCost) : 0;
		if (baodiCost + subscriptionCost > schemeCost)
			throw '保底金额与认购金额的总和不能大于方案金额！';
	}

	var agree = document.getElementById('agree');
	if (agree == null || agree.checked == false)
		throw '必须同意《用户合买代购协议》才能发起方案！';
}

/**
 * 检查余额是否够发单金额
 * @param remainMoney
 * @returns {Boolean}
 */
function checkRemainMoneyOK(remainMoney){
	var remainMoneyUser = remainMoney;
	var saleCost = parseFloat(createForm.elements['createForm.schemeCost'].value);
	var shareTypeSelected=null;
	var shareTypes = document.getElementsByName("createForm.shareType");
	for (var i=0; i<shareTypes.length; i++){
        if(shareTypes[i].checked){
        	shareTypeSelected = shareTypes[i].value;
        }
    }
	if(shareTypeSelected=='TOGETHER'){
		saleCost = parseFloat(createForm.elements['createForm.subscriptionCost'].value) + parseFloat(createForm.elements['createForm.baodiCost'].value);
	}	
	if(typeof(remainMoneyUser)=='undefined'){		 
		remainMoneyUser=getUserRemainMoney();
	}
	if(remainMoneyUser<saleCost){
		return false;
	}
	return true;
}

/**
 * 关闭方案投注信息确认框
 */
function closeSchemeConfirmDialog(){
	var dialog_obj=$("#dialogDiv");
	if (dialog_obj[0]){
		dialog_obj.dialog('close');
	}
}

/**
 * 获取用户余额
 * @returns
 */
function getUserRemainMoney(){
	var remainMoney=null;
	$.ajax({
		type : 'GET',
		cache : false,
		async : false,
		dataType : 'json',
		url : window.BASESITE + '/user/user!checkLogin.action',
		success : function(jsonObj) {
			if (jsonObj.success == true) {
				remainMoney = jsonObj.info.defaultAccountRemainMoney;
			} 
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	return remainMoney;
}

/**
 * 更改购买方式
 * 
 * @param obj
 */
window.chgShareType = function(obj) {
	var createForm = getCreateForm();
	var tr_TOGETHER_content = $$('tr_TOGETHER_content');
	var schemeCost = createForm.elements['createForm.schemeCost'].value;
	if (obj.value == 'TOGETHER') {
		if(schemeCost!=0){
			createForm.elements['createForm.subscriptionCost'].value=schemeCost/2;
			updateSubscriptionPer();
		}
		tr_TOGETHER_content.style.display = '';
		fadeon("tr_TOGETHER_content");
		$$('createForm_secretType_FULL_PUBLIC').checked=true;
		$$('freeSave').checked=false;
	}else if (obj.value == 'true') {
		tr_TOGETHER_content.style.display = 'none';
		$$('createForm_shareType_SELF').checked=false;
		$$('createForm_shareType_TOGETHER').checked=false;
	}else if (obj.value == 'SELF'){
		tr_TOGETHER_content.style.display = 'none';
		$$('createForm_secretType_FULL_SECRET').checked=true;
		$$('freeSave').checked=false;
	}
};

function submitCreateForm(type) {
	if(!type)type="";
	
	//判断是否为智能追号
	var isCapacity = document.getElementById('isCapacity');
	if(isCapacity!=null) {
		if("true"==document.getElementById('isCapacity').value) {
			var totalCostOfChase = document.getElementById("createForm_capacityChase_totalCostOfChase").value;
			if(totalCostOfChase<=0) {
				setCommonResult("请先计算方案倍数，再进行投注！", false);
				return false;
			} else {
				document.getElementById('createForm_multiple').readOnly=false;
			}
		}
	}
	var createForm = getCreateForm();
	try {
		checkCreateForm();
	} catch (e) {
		var errType = typeof e;
		if (errType == 'string') {
			setCommonResult(e, false);
			return false;
		} else if (errType == 'object' && e.message != null) {
			setCommonResult(e.message, false);
			return false;
		} else {
			throw e;
		}
	}
	
	$SSO.login_auth(function(remainMoney) {		
		if(type=='buy' && !checkRemainMoneyOK(remainMoney)){//真实购买
			closeSchemeConfirmDialog();
			$("#userRechargeDialogDiv").css("display","block");
			fixedBox("userRechargeDialogDiv", 'center','center');
			fadeon("userRechargeDialogDiv");
			return;
		}else if(type=='save'){//免费保存
			var freeSaveObj = $$('freeSave');
			freeSaveObj.checked=true;
			chgShareType(freeSaveObj);
		}
		var options = {
			type : 'POST',
			cache : false,
			data : {
				ajax : 'true'
			},
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				var msg = getCommonMsg(jsonObj);
				if (jsonObj.success == true) {					
					closeSchemeConfirmDialog();					
					if(jsonObj.schemeTemp == true){//免费保存方案处理
						$('#dialogDiv').dialog('close');
						setCommonResult('方案保存成功，页面跳转中...', true);
						if (jsonObj.redirectURL != null){
							setTimeout(function(){window.location.href = jsonObj.redirectURL;},2000);
						}					
					}else{//购买成功方案处理
						//显示方案成功信息对话框
						$('#dialogDiv').dialog('close');
						$("#schemeSuccessDialogDiv").css("display","block");
						fixedBox("schemeSuccessDialogDiv", 'center','center');
						fadeon("schemeSuccessDialogDiv");
						//设置方案详情链接地址
						$("#schemeDetail_a").attr("href", jsonObj.redirectURL);
					}
				} else {
					setCommonResult(msg, false);
					refreshRequestToken();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				setCommonResult('提交请求失败.', false);
				refreshRequestToken();
			},
			beforeSend : function(XMLHttpRequest) {
				$("#loading_tz"+type).addClass("btload");
				document.getElementById('span_createForm_submit'+type).style.display = 'none';
				document.getElementById('span_createForm_waiting'+type).style.display = '';
			},
			complete : function(XMLHttpRequest, textStatus) {
				$("#loading_tz"+type).removeClass("btload");
				document.getElementById('span_createForm_submit'+type).style.display = '';
				document.getElementById('span_createForm_waiting'+type).style.display = 'none';
			}
		};
		$(createForm).ajaxSubmit(options);
	});
}
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
} 
/**
 * 往UL里添加LI
 */
function selectAddOption(ulObj, text, value) {
	var generateNum =new Date().getTime()+""+generateMixed(7);
	var valueTemp = ulObj.innerHTML;
	
	var innerValue = "<li onclick='on_select_li(this)' id="+generateNum+" title="+value+">";
	    innerValue+="<input class=\"delCurrNum\" onclick=\"clearSelect('"+generateNum+"');\" value=\"删除\" type=\"button\"/>";
	    innerValue+="<span  class=\"code_one_li\">"+text+"</span>";
	    innerValue+="</li>";
	
	ulObj.innerHTML = valueTemp+innerValue;
}

function refreshRequestToken() {
	var request_token_el = getCreateForm().elements['request_token'];
	if (request_token_el != null)
		request_token_el.value = new Date().getTime();
}
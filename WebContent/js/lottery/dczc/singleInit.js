$(function() {
	var selectedMatchs = [];
	var currentXHR = null;

	/**
	 * 选择场次
	 */
	window.selectMatch = function(el) {
		cancelCurrentAjaxReq();
		var lineId = parseInt(el.value, 10);
		if (el.checked)
			selectedMatchs.push(lineId);
		else
			selectedMatchs.erase(lineId);
		
		var id = 'chb_select_'+lineId;
		if(el.id != id){
			document.getElementById(id).checked = el.checked;
		}
		
		displaySelectedMatch();
		displayPassType();
		updateBetUnits(0);
	};

	/**
	 * 切换过关方式
	 */
	window.chgPassType = function(obj) {
		cancelCurrentAjaxReq();
		var text = obj.getAttribute('_text');
		var el = document.getElementById('current_passType');
		el.innerHTML = '<span style="font-size: 12px; color: rgb(255, 0, 0); font-weight: bold;">注:您将要上传的方案为['
				+ selectedMatchs.length + '场' + text + '的普通过关方式]</span>';
		updateBetUnits(0);
		resetUploadFile();
		ableUploadFile();
	};

	window.chgUploadFile = function(el) {
		if (el.value == '') {
			setCommonResult('请选择您要上传的文件.', false);
			return;
		}
		cancelCurrentAjaxReq();
		var createForm = getCreateForm();

		var url = createForm.action.replace(/![a-zA-Z]+(\.action)?/ig, '!countSingleUnits$1');
		var options = {
			url : url,
			type : 'POST',
			cache : false,
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				if (jsonObj.success == true) {
					updateBetUnits(jsonObj.units);
				} else {
					setCommonResult(jsonObj.message, jsonObj.success);
					resetUploadFile();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				setCommonResult('提交请求失败.', false);
				resetUploadFile();
			},
			beforeSend : function(XMLHttpRequest) {
				updateBetUnits(0);
				ajaxHelpFn(true);
			},
			complete : function(XMLHttpRequest, textStatus) {
				ajaxHelpFn(false);
			}
		};
		currentXHR = $(createForm).ajaxSubmit(options);
	};

	window.clearCodes = function() {
		var createForm = getCreateForm();
		var codeArr = createForm.elements['createForm.codes'];
		for ( var i = 0; i < codeArr.length; i++) {
			codeArr[i].value = '';
		}
	};

	window.resetCodes = function() {
		var createForm = getCreateForm();
		var codeArr = createForm.elements['createForm.codes'];
		for ( var i = 0; i < codeArr.length; i++) {
			var el = codeArr[i];
			el.value = el.getAttribute('_d');
		}
	};
	
	window.showOrHideEndMatch = function() {
		var el = document.getElementById('show_end');
		var display = el.checked ? '' : 'none';
		var createForm = getCreateForm();
		$(createForm).find('li[_e=1]').css('display', display);
	};

	function ajaxHelpFn(startOrEnd) {
		var createForm = getCreateForm();
		var uploadEl = createForm.elements['createForm.upload'];
		document.getElementById('span_createForm_submit').style.display = startOrEnd ? 'none' : '';
		document.getElementById('upload_waiting').style.display = startOrEnd ? '' : 'none';
		uploadEl.readOnly = startOrEnd;
		document.getElementById('createForm_autoCount').disabled = startOrEnd;
		if (!startOrEnd)
			currentXHR = null;
	}

	/**
	 * 取消注数计算请求
	 */
	function cancelCurrentAjaxReq() {
		if (currentXHR != null) {
			currentXHR.abort();
			ajaxHelpFn();
		}
	}

	/**
	 * 重置文件上传标签
	 */
	function resetUploadFile() {
		var createForm = getCreateForm();
		var uploadEl = createForm.elements['createForm.upload'];
		if (typeof document.all !== "undefined") // IE
			uploadEl.outerHTML = uploadEl.outerHTML;
		else
			uploadEl.value = "";
	}

	/**
	 * 禁用文件上传标签
	 */
	function disableUploadFile() {
		var createForm = getCreateForm();
		var uploadEl = createForm.elements['createForm.upload'];
		uploadEl.disabled = true;
		document.getElementById('createForm_autoCount').disabled = true;
	}

	/**
	 * 启用文件上传标签
	 */
	function ableUploadFile() {
		var createForm = getCreateForm();
		var uploadEl = createForm.elements['createForm.upload'];
		uploadEl.disabled = false;
		document.getElementById('createForm_autoCount').disabled = false;
	}

	/**
	 * 更新过关方式显示
	 */
	function displayPassType() {
		var el = document.getElementById('pass_container');
		var seletedMatchCount = selectedMatchs.length;
		if (seletedMatchCount.length == 0) {
			el.innerHTML = '&nbsp;';
			return;
		}

		typeArr = PassTypeUtil.getSinglePassType(seletedMatchCount);

		var html = '';
		for ( var i = 0, len = typeArr.length; i < len; i++) {
			var type = typeArr[i];
			var inputId = 'passType_' + type.key;
			html += '<input type="radio" id="' + inputId + '" value="' + type.key + '" _text="' + type.text
					+ '" name="createForm.passTypes" onclick="chgPassType(this);"><label for="' + inputId + '">'
					+ type.text + '</label>&nbsp;&nbsp;';
		}
		html += '<br><span id="current_passType"></span>';
		el.innerHTML = html;

		resetUploadFile();
		disableUploadFile();
	}
	
	function displaySelectedMatch(){
		var html = '';
		selectedMatchs.sort();
		for ( var i = 0; i < selectedMatchs.length; i++) {
			var lineId = selectedMatchs[i];
			var inputId = 'createForm_lineId_'+lineId;
			var matchIndexStr;
			if(lineId > 8){
				matchIndexStr = ''+(lineId+1);
			}else{
				matchIndexStr = '0'+(lineId+1);
			}
			html += '<input id="'+inputId+'" type="checkbox" name="createForm.lineIds" value="'+lineId+'" onclick="selectMatch(this);" autocomplete="off" checked="checked" /><label for="'+inputId+'">'+matchIndexStr+'</label>&nbsp;';
		}
		document.getElementById('selected_match_container').innerHTML = html;
	}
	
	/**
	 * 更改购买方式
	 * 
	 * @param obj
	 */
	window.singleChgShareType = function(obj) {
		var createForm = getCreateForm();
		var trArr = [ 'tr_commissionRate', 'tr_minSubscriptionCost', 'tr_subscriptionCost', 'tr_baodiCost'];
		if (obj.value == 'TOGETHER') {
			for ( var i = 0, l = trArr.length; i < l; i++) {
				var el = document.getElementById(trArr[i]);
				el.style.display = '';
			}
		} else {
			for ( var i = 0, l = trArr.length; i < l; i++) {
				var el = document.getElementById(trArr[i]);
				el.style.display = 'none';
			}
		}
	};
});
$(function() {
	var selectedMatchs = [];
	var currentXHR = null;
	var defaultHtml = '请选择竞赛场次';

	/**
	 * 选择场次
	 */
	window.selectMatch = function(el) {
		cancelCurrentAjaxReq();
		var matchKey = el.value;
		if (el.checked)
			selectedMatchs.push(matchKey);
		else
			selectedMatchs.erase(matchKey);
		
		var id = 'chb_select_'+matchKey;
		if(el.id != id){
			document.getElementById(id).checked = el.checked;
		}
		
		displaySelectedMatch();
		displayPassType();
		updateBetUnits(0);
	};
	
	/**
	 * 清除一个选项
	 */
	window.removeItem = function(matchKey){
		cancelCurrentAjaxReq();
		selectedMatchs.erase(matchKey);
		var id = 'chb_select_'+matchKey;
		document.getElementById(id).checked = false;		
		displaySelectedMatch();
		displayPassType();
		updateBetUnits(0);
	};
	
	/**
	 * 清空所有选择
	 */
	window.cleanSelectedMatch = function(){
		cancelCurrentAjaxReq();
		for(var k=0;k<selectedMatchs.length;k++){
			var matchKey = selectedMatchs[k];
			var id = 'chb_select_'+matchKey;
			document.getElementById(id).checked = false;
		}
		selectedMatchs = [];
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
		el.innerHTML = '<span style="font-size: 12px; color: rgb(96, 96, 96);">注:您将要上传的方案为['
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

		var url = createForm.action.replace(/![a-zA-Z]+(\.action)?/ig, '!calcSingleBetUnits$1');
		var options = {
			url : url,
			type : 'POST',
			cache : false,
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				if (jsonObj.success == true) {
					updateBetUnits(jsonObj.units);
					if(getUploadType()=='CONTAIN_MATCH'){//上传文件包含场次
						var matchKeys = jsonObj.matchKeys;
						if(matchKeys!=''){
							var matchKeysHtml = '';
							var matchKeyArr = matchKeys.split(',');
							for(var i=0;i<matchKeyArr.length;i++){
								matchKeysHtml += '<input style="display:none" type="checkbox" name="createForm.matchKeys" value="'+matchKeyArr[i]+'" autocomplete="off" checked="checked" />';
							}
							document.getElementById('selected_match_container').innerHTML = matchKeysHtml;
						}
					}
				} else {
					setCommonResult(jsonObj.message, false);
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
		var seletedMatchCount;
		if(getUploadType()=='CONTAIN_MATCH'){
			seletedMatchCount=maxSingleMatchCount;
		}else{
			seletedMatchCount = selectedMatchs.length;
			if (seletedMatchCount == 0 || seletedMatchCount==1) {
				el.innerHTML = defaultHtml;
				disableUploadFile();
				return;
			}
		}		

		typeArr = PassTypeUtil.getSinglePassType(seletedMatchCount);

		var html = '';
		var  checkedFlag = '';
		for (var i = 0, len = typeArr.length; i < len; i++) {
			if(i==len-1){
				checkedFlag='checked';
			}
			var type = typeArr[i];
			var inputId = 'passType_' + type.key;
			html += '<input type="radio" '+checkedFlag+' id="' + inputId + '" value="' + type.key + '" _text="' + type.text
					+ '" name="createForm.passTypes" onclick="chgPassType(this);"><label for="' + inputId + '">'
					+ type.text + '</label>&nbsp;&nbsp;';
		}
		html += '<br><span id="current_passType"></span>';
		
		el.innerHTML = html;

		resetUploadFile();
		ableUploadFile();
	}
	
	function displaySelectedMatch(){
		var html = '';
		selectedMatchs.sort();
		for (var i = 0; i < selectedMatchs.length; i++) {
			var matchKey = selectedMatchs[i];
			var matchKey_text = $('#' + matchKey).attr('text_'+matchKey);
			var inputId = 'createForm_lineId_'+matchKey;
			html += '<input id="'+inputId+'" style="display:none" type="checkbox" name="createForm.matchKeys" value="'+matchKey+'" autocomplete="off" checked="checked" /><a href="javascript:;" onclick="removeItem(\''+matchKey+'\');" class="tzxqjg button2">'+matchKey_text+'</a>';
		}
		if(html=='')html=defaultHtml;
		document.getElementById('selected_match_container').innerHTML = html;
	}
	
	/**
	 * 获取当前的操作类型
	 */
	function getUploadType(){
		return $("input[name='uploadType']:checked").val();
	}
	
	window.chgUploadType = function(el) {		
		var uploadType = el.value;
		switch (uploadType) {
		case 'SELECT_MATCH':
			$('#stepOneOper').show();
			$('#stepOneMatchs').show();
			$('#tr_itemShow').show();
			displayPassType();
			break;
		case 'CONTAIN_MATCH':
			$('#stepOneOper').hide();
			$('#stepOneMatchs').hide();
			$('#tr_itemShow').hide();
			displayPassType();
			break;
		case 'AHEAD':
			$('#stepOneOper').hide();
			$('#stepOneMatchs').hide();
			$('#tr_itemShow').hide();
			break;
		default:
			return;
		}
	};
	
});
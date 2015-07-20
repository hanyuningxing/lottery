function getUploadForm() {
	return document.getElementById('uploadForm');
}

function submitUploadForm() {
	var uploadForm = getUploadForm();
	if (confirm('您确认要上传吗？')) {
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
					setCommonResult(msg, jsonObj.success);
					if (jsonObj.redirectURL != null)
						window.location.href = jsonObj.redirectURL;
				} else {
					setCommonResult(msg, jsonObj.success);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				setCommonResult('提交请求失败.', false);
			},
			beforeSend : function(XMLHttpRequest) {
				document.getElementById('span_uploadForm_submit').style.display = 'none';
				document.getElementById('span_uploadForm_waiting').style.display = '';
			},
			complete : function(XMLHttpRequest, textStatus) {
				document.getElementById('span_uploadForm_submit').style.display = '';
				document.getElementById('span_uploadForm_waiting').style.display = 'none';
			}
		};
		$(uploadForm).ajaxSubmit(options);
	}
}

/**
 * 计算上传文件的注数、金额
 */
function countUploadFileMoney() {
	var createForm = getCreateForm();
	var uploadFileEle = createForm.elements["createForm.upload"];
	var formActionUrl = createForm.action;
	var formSubmit = document.getElementById("formSubmit");
	var calcSubmit = document.getElementById("calcSubmit_uploadFile");
	var spanObj = document.getElementById('span_calUploadFileMoney');
	var url = formActionUrl.replace(/![a-zA-Z]+\./ig, '!calcSingleBetUnits.');
	var fileUpload = createForm.elements["createForm.fileUpload"];
	fileUpload.value = true;
	var uploadOptions = {
		url : url,
		type : 'POST',
		cache : false,
		data : {
			ajax : 'true'
		},
		beforeSend : function() {
			formSubmit.disabled = true;
			calcSubmit.style.display = 'none';
			uploadFileEle.readOnly = true;
		},
		success : function(data, textStatus) {
			var jsonObj = toJsonObject(data);
			if (jsonObj.success == true) {
				updateBetUnits(jsonObj.betUnits);
			} else {
				setCommonResult(jsonObj.msg, jsonObj.success);
				updateBetUnits(0);
			}
		},
		error : function(instance) {
			setCommonResult('计算注数失败！', false);
			updateBetUnits(0);
		},
		complete : function() {
			formSubmit.disabled = false;
			calcSubmit.style.display = '';
			spanObj.style.display = 'none';
			uploadFileEle.readOnly = false;
		}
	};
	$(createForm).ajaxSubmit(uploadOptions);
}

/**
 * 竞彩足球计算上传文件的注数、金额
 */
function countUploadFileMoney_Jc() {
	var createForm = getCreateForm();
	var formActionUrl = createForm.action;
	var calcSubmit = document.getElementById("calcSubmit_uploadFile");
	var spanObj = document.getElementById('span_calUploadFileMoney');
	var url = formActionUrl.replace(/![a-zA-Z]+\./ig, '!calcSingleBetUnits.');
	var fileUpload = createForm.elements["createForm.fileUpload"];
	fileUpload.value = true;
	var uploadOptions = {
		url : url,
		type : 'POST',
		cache : false,
		data : {
			ajax : 'true'
		},
		beforeSend : function() {
			calcSubmit.style.display = 'none';
		},
		success : function(data, textStatus) {
			var jsonObj = toJsonObject(data);
			if (jsonObj.success == true) {
				setCommonResult("上传成功，请操作发起出票操作", jsonObj.success);
				window.location.reload();
			} else {
				setCommonResult(jsonObj.msg, jsonObj.success);
			}
		},
		error : function(instance) {
			setCommonResult('计算注数失败！', false);
		},
		complete : function() {
			calcSubmit.style.display = '';
			spanObj.style.display = 'none';
		}
	};
	$(createForm).ajaxSubmit(uploadOptions);
}
/**
 * 切换上传文件
 */
function chgUploadFile_Jc(el) {
	var uploadFile = el.value;
	if (uploadFile != '') {
		var arr = uploadFile.split('.');
		if (arr.length != 2) {
			setCommonResult('只允许上传txt类型的文件！', false);
			return;
		}
		if (arr[1].toLowerCase() != 'txt') {
			setCommonResult('只允许上传txt类型的文件！', false);
			return;
		}
		document.getElementById("calcSubmit_uploadFile").disabled = false;
		this.countUploadFileMoney_Jc();
	}
}
function chooseSingleUploadType(type) {
	var createForm = getCreateForm();
	var tr_uploadFile = document.getElementById("tr_uploadFile");
	var text_createForm_units = document.getElementById("text_createForm_units");
	var input_createForm_units = document.getElementById("input_createForm_units");
	var ahead_createForm_units = document.getElementById('ahead_createForm_units');
	var aheadOfUploadContent = createForm.elements['createForm.aheadOfUploadContent'];
	if(ahead_createForm_units != null)
		ahead_createForm_units.value = '';
	updateBetUnits(0);
	if (0 == type) {
		createForm.elements['createForm.fileUpload'].value = true;
		if(aheadOfUploadContent != null)
			aheadOfUploadContent.value = false;
		input_createForm_units.style.display = 'none';
		text_createForm_units.style.display = '';
		tr_uploadFile.style.display = '';
	} else if (1 == type) {
		createForm.elements['createForm.fileUpload'].value = false;
		if(aheadOfUploadContent != null)
			aheadOfUploadContent.value = true;
		tr_uploadFile.style.display = 'none';
		text_createForm_units.style.display = 'none';
		input_createForm_units.style.display = '';
	}
}

function chooseSingleUploadType2(type){
	var file_upload_div = document.getElementById("file_upload_div");
	var text_write_div = document.getElementById("text_write_div");
    if(0==type){
    	file_upload_div.style.display = '';
  	  	text_write_div.style.display = 'none';
    }else if(1==type){
    	file_upload_div.style.display = 'none';
    	text_write_div.style.display = '';
    }
	 updateBetUnits(0);
}

/**
 * 手工录入的注数、金额
 */
function countTextAreaMoney() {
	var createForm = getCreateForm();
	var createForm_content = document.getElementById("createForm_content");
	var content = createForm.elements["createForm.content"];
	content.value = createForm_content.value;
	if ('' == content.value) {
		return false;
	}
	var formActionUrl = createForm.action;
	var fileUpload = createForm.elements["createForm.fileUpload"];
	fileUpload.value = false;
	var formSubmit = document.getElementById("formSubmit");
	var calcSubmit = document.getElementById("calcSubmit_uploadFile");
	var url = formActionUrl.replace(/![a-zA-Z]+\./ig, '!calcSingleBetUnits.');
	var uploadOptions = {
		url : url,
		type : 'POST',
		cache : false,
		data : {
			ajax : 'true'
		},
		beforeSend : function() {
			formSubmit.disabled = true;
			calcSubmit.style.display = 'none';
		},
		success : function(data, textStatus) {
			var jsonObj = toJsonObject(data);
			if (jsonObj.success == true) {
				updateBetUnits(jsonObj.betUnits);
			} else {
				setCommonResult(jsonObj.msg, jsonObj.success);
				updateBetUnits(0);
			}
		},
		error : function(instance) {
			updateBetUnits(0);
		},
		complete : function() {
			formSubmit.disabled = false;
			calcSubmit.style.display = '';
		}
	};
	$(createForm).ajaxSubmit(uploadOptions);
}

/**
 * 切换上传文件
 */
function chgUploadFile(el) {
	var uploadFile = el.value;
	if (uploadFile != '') {
		var arr = uploadFile.split('.');
		if (arr.length != 2) {
			setCommonResult('只允许上传txt类型的文件！', false);
			return;
		}
		if (arr[1].toLowerCase() != 'txt') {
			setCommonResult('只允许上传txt类型的文件！', false);
			return;
		}
		document.getElementById("calcSubmit_uploadFile").disabled = false;
		this.countUploadFileMoney();
	}
}
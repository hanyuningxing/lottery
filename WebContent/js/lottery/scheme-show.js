$(function() {
	function checkSubscribeForm() {
		// var subscribeForm = document.getElementById('subscribeForm');
		// TODO:检查认购、保底数据
	}

	function getSubscribeConfirmMsg() {
		// var subscribeForm = document.getElementById('subscribeForm');
		// TODO 生成确认信息
		return '您确认要提交吗？';
	}

	window.submitSubscribeForm = function() {
		var subscribeForm = document.getElementById('subscribeForm');
		try {
			checkSubscribeForm();
		} catch (e) {
			var errType = typeof e;
			if (errType == 'string') {
				alert(e);
				return false;
			} else if (errType == 'object' && e.message != null) {
				alert(e.message);
				return false;
			} else {
				throw e;
			}
		}

		$SSO.login_auth(function(){
			if (confirm(getSubscribeConfirmMsg())) {
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
							alert(msg);
							if (jsonObj.redirectURL != null)
								window.location.href = jsonObj.redirectURL;
						} else {
							alert(msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert('提交请求失败.');
					},
					beforeSend : function(XMLHttpRequest) {
						document.getElementById('span_subscribeForm_submit').style.display = 'none';
						document.getElementById('span_subscribeForm_wait').style.display = '';
					},
					complete : function(XMLHttpRequest, textStatus) {
						document.getElementById('span_subscribeForm_submit').style.display = '';
						document.getElementById('span_subscribeForm_wait').style.display = 'none';
					}
				};
				$(subscribeForm).ajaxSubmit(options);
			}
		});
	};
	var selectedClass = 'redboldchar';
	var unSelectedClass = '';
	window.chgExtraDataMenu = function(obj) {
		$('#extra-data-container').load(obj.href,{_t:new Date().getTime()}, function() {
			$('#extra-data-menu  a[class="' + selectedClass + '"]').attr('class', unSelectedClass);
			obj.className = selectedClass;
			document.getElementById('extra-data-tr').style.display="";
			var count = document.getElementById('extra-count').innerHTML;
			// TODO:如果需要显示记录数，设置一下
		});
	};
	window.hiddenExtraDataMenu = function() {
		   $('#extra-data-menu  a[class="' + selectedClass + '"]').attr('class', unSelectedClass);
			document.getElementById('extra-data-tr').style.display="none";
	};
	window.commonSendAjaxReq = function(obj) {
		var tempFn = obj.onclick;
		var options = {
			url : obj.href,
			type : 'POST',
			cache : false,
			data : {
				ajax : 'true'
			},
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				var msg = getCommonMsg(jsonObj);
				if (jsonObj.success == true) {
					alert(msg);
					if (jsonObj.redirectURL != null)
						window.location.href = jsonObj.redirectURL;
				} else {
					alert(msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('提交请求失败.');
			},
			beforeSend : function(XMLHttpRequest) {
				obj.onclick = function() {
					return false;
				};
			},
			complete : function(XMLHttpRequest, textStatus) {
				obj.onclick = tempFn;
			}
		};
		$.ajax(options);
	};
	
	window.showOrHide = function(obj, lineId) {
		var detailEl = document.getElementById('detail_' + lineId);
		if(obj.value == '显示选项'){
			obj.value = '隐藏选项';
			detailEl.style.display = '';
		}else{
			obj.value = '显示选项';
			detailEl.style.display = 'none'	;
		}
	};
});
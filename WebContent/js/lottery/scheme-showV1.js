$(function() {
	
	/**
	 * 跨浏览器“复制到粘贴板”
	 */	
	(function(){
		if(!document.getElementById("copyTo"))
			return;
		var clip = new ZeroClipboard.Client(); // 新建一个对象	
		clip.setHandCursor( true ); // 设置鼠标为手型
		clip.addEventListener('complete', function () {
			setCommonResult("复制成功！", true);
		});
		clip.addEventListener( "mouseOver", function(client) { 
			var text = document.getElementById('scheme_link').href; 
			client.setText( text ); // 设置要复制的值 
		}); 
		clip.glue("copyTo"); 
	})();
	
	
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
							setCommonResult(msg ,true);
							if (jsonObj.redirectURL != null)
								setTimeout(window.location.href = jsonObj.redirectURL,2000);
						} else {
							setCommonResult(msg, false);
						}
					}
				};
				$(subscribeForm).ajaxSubmit(options);
			}
		});
	};
	
	window.showTicketInfo = function(obj) {
		$('#extra-ticket-container').load(obj.href,{_t:new Date().getTime()}, function() {});
	}; 
		
	var selectedClass = 'fdlistbtnow';
	var unSelectedClass = 'fdlistbtnor';
	window.chgExtraDataMenu = function(obj) {
		$('#extra-data-container').load(obj.href,{_t:new Date().getTime()}, function() {
			$('#extra-data-menu  a[class="' + selectedClass + '"]').attr('class', unSelectedClass);
			obj.className = selectedClass;
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
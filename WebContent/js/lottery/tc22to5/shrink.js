$(function() {
	var redSelectedClass = 'red';
	var redUnSelectedClass = '';
	var __shrinkForm = null;
	var numArr = [];

	function getShrinkForm() {
		if (__shrinkForm == null)
			__shrinkForm = document.getElementById('shrinkForm');
		return __shrinkForm;
	}

	$('#red_box a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == redSelectedClass) {
			this.className = redUnSelectedClass;
			numArr.erase(val);
		} else {
			if (numArr.length >= 18) {
				window.alert("最多只能选择18个基号.");
				return;
			}
			this.className = redSelectedClass;
			numArr.push(val);
		}
	});

	$('#spinmatrix_submit').bind("click", function(event) {
		if (numArr.length < 7 || numArr.length > 18) {
			window.alert("请选定7至18个基号.");
			return;
		}
		numArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		var shrinkForm = getShrinkForm();
		shrinkForm.elements['shrinkNums'].value = numArr.join(',');
		shrinkForm.submit();
	});

	$('#spinmatrix_reset').bind("click", function(event) {
		$('#red_box a[_name="red_ball"][class="' + redSelectedClass + '"]').attr("class", redUnSelectedClass);
		numArr.length = 0;
		getShrinkForm().elements['shrinkNums'].value = '';
	});

	$('#shrink_submit').bind("click", function(event) {
		var shrinkForm = getShrinkForm();
		shrinkForm.action = this.getAttribute('_action');
		shrinkForm.target = '';
		document.getElementById('shrinkBean_content').name = 'shrinkBean.content';
		shrinkForm.submit();
	});

	$('#shrink_output').bind("click", function(event) {
		var shrinkForm = getShrinkForm();
		shrinkForm.action = this.getAttribute('_action');
		shrinkForm.target = '_blank';
		document.getElementById('shrinkBean_content').name = 'file_content';
		shrinkForm.submit();
	});

	$('#shrink_buy').bind("click", function(event) {
		var shrinkForm = getShrinkForm();
		shrinkForm.action = this.getAttribute('_action');
		shrinkForm.target = '';
		document.getElementById('shrinkBean_content').name = 'shrinkBean.content';
		shrinkForm.submit();
	});

	var filterBaseArr = [];
	var filterGallArr = [];
	var filterBaseSelectedClass = 'red';
	var filterGallSelectedClass = 'blue';
	var filterUnSelectedClass = '';

	var filterHelpObj = {};
	filterHelpObj['gall_0'] = {
		maxSize : 13,
		errMsg : '不设置胆码时，基本号不能超过13个'
	};
	filterHelpObj['gall_1'] = {
		maxSize : 15,
		errMsg : '设置1个胆码时，基本号不能超过15个'
	};
	filterHelpObj['gall_2'] = {
		maxSize : 17,
		errMsg : '设置2个胆码时，基本号不能超过17个'
	};

	$('#filter_red_box a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == filterBaseSelectedClass) {
			this.className = filterUnSelectedClass;
			filterBaseArr.erase(val);
		} else {
			if ($.inArray(val, filterGallArr) >= 0) {
				window.alert('基本号码和胆码不能重复。');
				return;
			}
			var gallSize = filterGallArr.length;
			var helpObj = filterHelpObj['gall_' + gallSize];
			if (helpObj == null) {
				window.alert('①不设置胆码时，基本号不能超过13个；\r\n②设置1个胆码时，基本号不能超过15个；\r\n③设置2个胆码时，基本号不能超过17个。');
				return;
			}
			if (filterBaseArr.length >= helpObj.maxSize) {
				window.alert(helpObj.errMsg);
				return;
			}
			this.className = filterBaseSelectedClass;
			filterBaseArr.push(val);
		}
		filterShowSelect();
	});

	$('#filter_red_box_galls a[_name="red_ball"]').bind(
			"click",
			function(event) {
				var val = parseInt(this.innerHTML, 10);
				if (this.className == filterGallSelectedClass) {
					var gallSize = filterGallArr.length - 1;
					var helpObj = filterHelpObj['gall_' + gallSize];
					if (filterBaseArr.length > helpObj.maxSize) {
						if (window.confirm(helpObj.errMsg + '，点击[确定]将清空基本号码的选择。')) {
							$('#filter_red_box a[_name="red_ball"][class="' + filterBaseSelectedClass + '"]').attr(
									"class", filterUnSelectedClass);
							filterBaseArr.length = 0;
						} else {
							return;
						}
					}
					this.className = filterUnSelectedClass;
					filterGallArr.erase(val);
				} else {
					if ($.inArray(val, filterBaseArr) >= 0) {
						window.alert('基本号码和胆码不能重复。');
						return;
					}
					if (filterGallArr.length >= 2) {
						window.alert("最多只能设置2个胆码。");
						return;
					}
					this.className = filterGallSelectedClass;
					filterGallArr.push(val);
				}
				filterShowSelect();
			});

	$('#filter_submit').bind("click", function(event) {
		if (filterBaseArr.length < 7) {
			window.alert("至少设置7个基本号码。");
			return;
		}
		if (filterGallArr.length > 2) {
			window.alert("最多只能设置2个胆码。");
			return;
		}
		var gallSize = filterGallArr.length;
		var helpObj = filterHelpObj['gall_' + gallSize];
		if (helpObj == null) {
			window.alert('①不设置胆码时，基本号不能超过13个；\r\n②设置1个胆码时，基本号不能超过15个；\r\n③设置2个胆码时，基本号不能超过17个。');
			return;
		}
		if (filterBaseArr.length > helpObj.maxSize) {
			window.alert(helpObj.errMsg);
			return;
		}
		filterGallArr.sort(ascSort);
		filterBaseArr.sort(ascSort);

		var shrinkForm = getShrinkForm();
		shrinkForm.action = this.getAttribute('_action');
		shrinkForm.target = '';
		var contentEl = document.getElementById('shrinkBean_content');
		contentEl.name = 'shrinkBean.content';
		contentEl.value = filterGallArr.join(',') + '_' + filterBaseArr.join(',');
		shrinkForm.submit();
	});
	
	$('#filter_reset').bind("click", function(event) {
		$('#filter_red_box a[_name="red_ball"][class="' + filterBaseSelectedClass + '"]').attr("class", filterUnSelectedClass);
		$('#filter_red_box_galls a[_name="red_ball"][class="' + filterGallSelectedClass + '"]').attr("class", filterUnSelectedClass);
		filterBaseArr.length = 0;
		filterGallArr.length = 0;
		var shrinkForm = getShrinkForm();
		shrinkForm.elements['shrinkBean.content'].value = '';
		shrinkForm.reset();
	});

	function ascSort(num1, num2) {
		return num1 - num2;
	}

	function filterShowSelect() {
		filterBaseArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		filterGallArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		var content = '';
		content += '基本号码：';
		for ( var i = 0, num; i < filterBaseArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterBaseArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		content += '\r\n选择胆码：';
		for ( var i = 0, num; i < filterGallArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterGallArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		document.getElementById('filter_select_show').value = content;
	}

});
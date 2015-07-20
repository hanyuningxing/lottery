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

	var filterRedBaseArr = [];
	var filterRedGallArr = [];
	var filterBlueBaseArr = [];
	var filterBlueGallArr = [];
	var filterBaseSelectedClass = 'red';
	var filterGallSelectedClass = 'blue';
	var filterUnSelectedClass = '';

	var filterRedHelpObj = {};
	filterRedHelpObj['gall_0'] = {
		maxSize : 12,
		errMsg : '不设置前区胆码时，前区基本号码不能超过12个'
	};
	filterRedHelpObj['gall_1'] = {
		maxSize : 13,
		errMsg : '设置1个前区胆码时，前区基本号码不能超过13个'
	};
	filterRedHelpObj['gall_2'] = {
		maxSize : 14,
		errMsg : '设置2个前区胆码时，前区基本号码不能超过14个'
	};
	var filterBlueHelpObj = {};
	filterBlueHelpObj['gall_0'] = {
		maxSize : 4,
		errMsg : '不设置后区胆码时，后区基本号码不能超过4个'
	};
	filterBlueHelpObj['gall_1'] = {
		maxSize : 5,
		errMsg : '设置1个后区胆码时，后区基本号码不能超过5个'
	};

	$('#filter_red_area_box a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == filterBaseSelectedClass) {
			this.className = filterUnSelectedClass;
			filterRedBaseArr.erase(val);
		} else {
			if ($.inArray(val, filterRedGallArr) >= 0) {
				window.alert('前区基本号码和前区胆码不能重复。');
				return;
			}
			var gallSize = filterRedGallArr.length;
			var helpObj = filterRedHelpObj['gall_' + gallSize];
			if (helpObj == null) {
				window.alert('①不设置前区胆码时，前区基本号码不能超过12个；\r\n②设置1个前区胆码时，前区基本号码不能超过13个；\r\n③设置2个前区胆码时，前区基本号码不能超过14个。');
				return;
			}
			if (filterRedBaseArr.length >= helpObj.maxSize) {
				window.alert(helpObj.errMsg);
				return;
			}
			this.className = filterBaseSelectedClass;
			filterRedBaseArr.push(val);
		}
		filterRedShowSelect();
	});

	$('#filter_red_area_box_galls a[_name="red_ball"]').bind(
			"click",
			function(event) {
				var val = parseInt(this.innerHTML, 10);
				if (this.className == filterGallSelectedClass) {
					var gallSize = filterRedGallArr.length - 1;
					var helpObj = filterRedHelpObj['gall_' + gallSize];
					if (filterRedBaseArr.length > helpObj.maxSize) {
						if (window.confirm(helpObj.errMsg + '，点击[确定]将清空基本号码的选择。')) {
							$('#filter_red_area_box a[_name="red_ball"][class="' + filterBaseSelectedClass + '"]')
									.attr("class", filterUnSelectedClass);
							filterRedBaseArr.length = 0;
						} else {
							return;
						}
					}
					this.className = filterUnSelectedClass;
					filterRedGallArr.erase(val);
				} else {
					if ($.inArray(val, filterRedBaseArr) >= 0) {
						window.alert('前区基本号码和前区胆码不能重复。');
						return;
					}
					if (filterRedGallArr.length >= 2) {
						window.alert("最多只能设置2个前区胆码。");
						return;
					}
					this.className = filterGallSelectedClass;
					filterRedGallArr.push(val);
				}
				filterRedShowSelect();
			});

	$('#filter_blue_area_box a[_name="blue_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == filterBaseSelectedClass) {
			this.className = filterUnSelectedClass;
			filterBlueBaseArr.erase(val);
		} else {
			if ($.inArray(val, filterBlueGallArr) >= 0) {
				window.alert('后区基本号码和后区胆码不能重复。');
				return;
			}
			var gallSize = filterBlueGallArr.length;
			var helpObj = filterBlueHelpObj['gall_' + gallSize];
			if (helpObj == null) {
				window.alert('①不设置后区胆码时，后区基本号码不能超过4个；\r\n②设置1个后区胆码时，后区基本号码不能超过5个。');
				return;
			}
			if (filterBlueBaseArr.length >= helpObj.maxSize) {
				window.alert(helpObj.errMsg);
				return;
			}
			this.className = filterBaseSelectedClass;
			filterBlueBaseArr.push(val);
		}
		filterBlueShowSelect();
	});

	$('#filter_blue_area_box_galls a[_name="blue_ball"]').bind(
			"click",
			function(event) {
				var val = parseInt(this.innerHTML, 10);
				if (this.className == filterGallSelectedClass) {
					var gallSize = filterBlueGallArr.length - 1;
					var helpObj = filterBlueHelpObj['gall_' + gallSize];
					if (filterBlueBaseArr.length > helpObj.maxSize) {
						if (window.confirm(helpObj.errMsg + '，点击[确定]将清空基本号码的选择。')) {
							$('#filter_red_area_box a[_name="red_ball"][class="' + filterBaseSelectedClass + '"]')
									.attr("class", filterUnSelectedClass);
							filterBlueBaseArr.length = 0;
						} else {
							return;
						}
					}
					this.className = filterUnSelectedClass;
					filterBlueGallArr.erase(val);
				} else {
					if ($.inArray(val, filterBlueBaseArr) >= 0) {
						window.alert('后区基本号码和后区胆码不能重复。');
						return;
					}
					if (filterBlueGallArr.length >= 1) {
						window.alert("最多只能设置1个后区胆码。");
						return;
					}
					this.className = filterGallSelectedClass;
					filterBlueGallArr.push(val);
				}
				filterBlueShowSelect();
			});

	$('#filter_submit').bind("click", function(event) {
		if (filterRedBaseArr.length < 6) {
			window.alert("至少设置6个前区基本号码。");
			return;
		}
		if (filterRedGallArr.length > 2) {
			window.alert("最多只能设置2个前区胆码。");
			return;
		}
		var gallSize = filterRedGallArr.length;
		var helpObj = filterRedHelpObj['gall_' + gallSize];
		if (helpObj == null) {
			window.alert('①不设置前区胆码时，前区基本号码不能超过12个；\r\n②设置1个前区胆码时，前区基本号码不能超过13个；\r\n③设置2个前区胆码时，前区基本号码不能超过14个。');
			return;
		}
		if (filterRedBaseArr.length >= helpObj.maxSize) {
			window.alert(helpObj.errMsg);
			return;
		}
		filterRedGallArr.sort(ascSort);
		filterRedBaseArr.sort(ascSort);

		if (filterBlueBaseArr.length + filterBlueGallArr.length < 2) {
			window.alert("后区号码不能少于2个。");
			return;
		}
		if (filterBlueGallArr.length > 1) {
			window.alert("最多只能设置1个后区胆码。");
			return;
		}
		var gallSize = filterBlueGallArr.length;
		var helpObj = filterBlueHelpObj['gall_' + gallSize];
		if (helpObj == null) {
			window.alert('①不设置后区胆码时，后区基本号码不能超过4个；\r\n②设置1个后区胆码时，后区基本号码不能超过5个。');
			return;
		}
		if (filterBlueBaseArr.length > helpObj.maxSize) {
			window.alert(helpObj.errMsg);
			return;
		}
		filterBlueGallArr.sort(ascSort);
		filterBlueBaseArr.sort(ascSort);

		var shrinkForm = getShrinkForm();
		shrinkForm.action = this.getAttribute('_action');
		shrinkForm.target = '';
		shrinkForm.elements['dlt35to5ShrinkBean.content'].value = filterRedGallArr.join(',') + '_' + filterRedBaseArr.join(',');
		shrinkForm.elements['dlt12to2ShrinkBean.content'].value = filterBlueGallArr.join(',') + '_' + filterBlueBaseArr.join(',');
		shrinkForm.submit();
	});
	
	$('#filter_reset').bind("click", function(event) {
		$('#filter_red_area_box a[_name="red_ball"][class="' + filterBaseSelectedClass + '"]').attr("class", filterUnSelectedClass);
		$('#filter_red_area_box_galls a[_name="red_ball"][class="' + filterGallSelectedClass + '"]').attr("class", filterUnSelectedClass);
		$('#filter_blue_area_box a[_name="blue_ball"][class="' + filterBaseSelectedClass + '"]').attr("class", filterUnSelectedClass);
		$('#filter_blue_area_box_galls a[_name="blue_ball"][class="' + filterGallSelectedClass + '"]').attr("class", filterUnSelectedClass);
		filterRedBaseArr.length = 0;
		filterRedGallArr.length = 0;
		filterBlueBaseArr.length = 0;
		filterBlueGallArr.length = 0;
		var shrinkForm = getShrinkForm();
		shrinkForm.elements['dlt35to5ShrinkBean.content'].value = '';
		shrinkForm.elements['dlt12to2ShrinkBean.content'].value = '';
		shrinkForm.reset();
	});

	function ascSort(num1, num2) {
		return num1 - num2;
	}

	function filterRedShowSelect() {
		filterRedBaseArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		filterRedGallArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		var content = '';
		content += '前区号码：';
		for ( var i = 0, num; i < filterRedBaseArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterRedBaseArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		content += '\r\n前区胆码：';
		for ( var i = 0, num; i < filterRedGallArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterRedGallArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		document.getElementById('filter_red_select_show').value = content;
	}

	function filterBlueShowSelect() {
		filterBlueBaseArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		filterBlueGallArr.sort(function(num1, num2) {
			return num1 - num2;
		});
		var content = '';
		content += '后区号码：';
		for ( var i = 0, num; i < filterBlueBaseArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterBlueBaseArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		content += '\r\n后区胆码：';
		for ( var i = 0, num; i < filterBlueGallArr.length; i++) {
			if (i > 0)
				content += ',';
			num = filterBlueGallArr[i];
			if (num < 10)
				content += '0';
			content += num;
		}
		document.getElementById('filter_blue_select_show').value = content;
	}

});
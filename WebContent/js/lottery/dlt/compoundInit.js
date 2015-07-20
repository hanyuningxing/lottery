$(function() {
	var selectedRebClass = 'red';
	var selectedBlueClass = 'blue';
	var unSelectedClass = '';
	var RED_MAX_HITS = 5;
	var BLUE_MAX_HITS = 2;
	var currentContentObj = {
		redDans : [], // 红球胆码
		reds : [], // 红球拖码
		blueDans : [], // 蓝球胆码
		blues : [], // 蓝球拖码
		select12to2Dans : [], // 12选2
		select12to2s : [], // 12选2
		units : 0
	};
	var clearCurrentContent = function() {
		$('#red_area_box a[_name="red_ball"]').attr("class", unSelectedClass);
		$('#blue_area_box a[_name="blue_ball"]').attr("class", unSelectedClass);
		$('#red_area_box_galls a[_name="red_ball"]').attr("class", unSelectedClass);
		$('#blue_area_box_galls a[_name="blue_ball"]').attr("class", unSelectedClass);
		$('#select12to2_area_box a[_name="12to2_ball"]').attr("class", unSelectedClass);
		$('#shrink_blue_box a[_name="blue_ball"]').attr("class", unSelectedClass);
		currentContentObj.redDans.length = 0;
		currentContentObj.reds.length = 0;
		currentContentObj.blueDans.length = 0;
		currentContentObj.blues.length = 0;
		currentContentObj.select12to2Dans.length = 0;
		currentContentObj.select12to2s.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};
	window.clearCurrentSelect = function() {
		clearCurrentContent();
	};
	var calcCurrentUnits = function() {
		var redDansSize = currentContentObj.redDans.length;
		var redsSize = currentContentObj.reds.length;
		var blueDansSize = currentContentObj.blueDans.length;
		var bluesSize = currentContentObj.blues.length;
		var select12to2DanSize = currentContentObj.select12to2Dans.length;
		var select12to2Size = currentContentObj.select12to2s.length;
		var units = 0;
		if ((redDansSize + redsSize) >= 5 && (blueDansSize + bluesSize) >= 2) {
			// ///选的是大乐透
			units = comp(RED_MAX_HITS - redDansSize, redsSize) * comp(BLUE_MAX_HITS - blueDansSize, bluesSize);
		} else if (select12to2DanSize + select12to2Size >= 2) {
			// ///选生肖乐
			units = comp(BLUE_MAX_HITS - select12to2DanSize, select12to2Size);
		} else {
			if (units != 0) {
				window.alert("选择玩法出错");
				this.clearCurrentContent();
			}
		}
		currentContentObj.units = units;
		updateCurrentMsg();
	};

	var updateCurrentMsg = function() {
		// 更新页面显示
		if (currentContentObj.units > 0) {
			document.getElementById("selectUnitsSpan").innerHTML = "" + currentContentObj.units;
			document.getElementById("selectAmountSpan").innerHTML = "" + (currentContentObj.units * 2);
		} else {
			document.getElementById("selectUnitsSpan").innerHTML = "0";
			document.getElementById("selectAmountSpan").innerHTML = "0";
		}
	};

	var getBallStr = function(val) {
		if (val < 10)
			return '0' + val;
		else
			return '' + val;
	};
	var addOrSubBetUnits = function(chgUnits) {
		var createForm = getCreateForm();
		var units = createForm.elements["createForm.units"].value;
		if (units == "" || !/^\d/.test(units)) {
			units = 0;
		}
		units = parseInt(units, 10) + chgUnits;
		updateBetUnits(units);
		changePolyContent();
	};

	var changePolyContent = function() {
		var createForm = getCreateForm();
		var content = '';
		var liObj = $('#createForm_select_content').find('li');
		for ( var i = 0, len = liObj.length; i < len; i++) {
			if (i > 0)
				content += '\r\n';
			content += liObj[i].title;
		}
		createForm.elements["createForm.content"].value = content;
	};
	var numSplitWord = ',';
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';

		var redDansSize = contentObj.redDans.length;
		var redsSize = contentObj.reds.length;
		var blueDansSize = contentObj.blueDans.length;
		var bluesSize = contentObj.blues.length;
		var select12to2DanSize = contentObj.select12to2Dans.length;
		var select12to2Size = contentObj.select12to2s.length;

		if ((redDansSize + redsSize) >= 5 && (blueDansSize + bluesSize) >= 2) {
			// ///选的是大乐透
			for ( var i = 0, ball, l = contentObj.redDans.length; i < l; i++) {
				ball = getBallStr(contentObj.redDans[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.reds.length; i < l; i++) {
				ball = getBallStr(contentObj.reds[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				} else {
					text += ' | ';
					value += '|';
				}
			}
			for ( var i = 0, ball, l = contentObj.blueDans.length; i < l; i++) {
				ball = getBallStr(contentObj.blueDans[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.blues.length; i < l; i++) {
				ball = getBallStr(contentObj.blues[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				}
			}
		} else if (select12to2DanSize + select12to2Size >= 2) {
			// ///选生肖乐
			for ( var i = 0, ball, l = contentObj.select12to2Dans.length; i < l; i++) {
				ball = getBallStr(contentObj.select12to2Dans[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.select12to2s.length; i < l; i++) {
				ball = getBallStr(contentObj.select12to2s[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += ',';
					value += ',';
				}
			}
		} else {
			if (units != 0) {
				window.alert("选择玩法出错");
				this.clearCurrentContent();
			}
		}
		selectAddOption(selectObj, text, value);// 往select里添加option
		addOrSubBetUnits(contentObj.units);// 更新方案注数
	};

	window.pushCurrentContent = function() {
		if (document.getElementById('shrink_content') != null) {
			if (currentContentObj.units <= 0) {
				alert('您选择的号码还不够组成1注或者选择的号码不正确');
				return;
			}
			shrinkPushContent(currentContentObj);
		} else {
			if (currentContentObj.units <= 0) {
				alert('您选择的号码还不够组成1注');
				return;
			}
			pushContent(currentContentObj);
		}
		clearCurrentContent();
	};

	var GENERAL_RED_BALL_ARR = [];
	for ( var i = 0; i < 35; i++) {
		GENERAL_RED_BALL_ARR[i] = i + 1;
	}

	var GENERAL_BLUE_BALL_ARR = [];
	for ( var i = 0; i < 12; i++) {
		GENERAL_BLUE_BALL_ARR[i] = i + 1;
	}
	var randomSort = function() {
		var randomNum = Math.random();
		if (randomNum > 0.5)
			return 1;
		else if (randomNum < 0.5)
			return -1;
		else
			return 0;
	};
	window.luckRandomSelect = function(redsArr, bluesArr) {
		var randomContent = [];
		var contentObj = {
			reds : redsArr,
			redDans : [], // 红球胆码
			blueDans : [], // 蓝球胆码
			blues : bluesArr,
			select12to2Dans : [], // 12选2
			select12to2s : [], // 12选2
			units : 1
		};
		randomContent.push(contentObj);
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};

	window.GeneralRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyRedBallArr = GENERAL_RED_BALL_ARR.slice(0, GENERAL_RED_BALL_ARR.length);
		var copyBlueBallArr = GENERAL_BLUE_BALL_ARR.slice(0, GENERAL_BLUE_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
				redDans : [], // 红球胆码
				reds : copyRedBallArr.sort(randomSort).slice(0, 5).sort(asc), // 红球拖码
				blueDans : [], // 蓝球胆码
				blues : copyBlueBallArr.sort(randomSort).slice(0, 2).sort(asc), // 蓝球拖码
				select12to2Dans : [], // 12选2
				select12to2s : [], // 12选2
				units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.Select12to2RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyBlueBallArr = GENERAL_BLUE_BALL_ARR.slice(0, GENERAL_BLUE_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
				redDans : [], // 红球胆码
				reds : [], // 红球拖码
				blueDans : [], // 蓝球胆码
				blues : [], // 蓝球拖码
				select12to2Dans : [], // 12选2
				select12to2s : copyBlueBallArr.sort(randomSort).slice(0, 2).sort(asc), // 12选2
				units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.clearAll = function() {
		var createForm = getCreateForm();
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = $('#createForm_select_content').find('li');
		for ( var i = 0; i < lLi.length; i++) {
			selectObj.removeChild(lLi[i]);
		}
		updateBetUnits(0);
		update_set_blueDan();
		update_set_redDan();
		update_set_select12to2Dan();
		createForm.elements['createForm.content'].value = '';
	};
	window.on_select_li = function(li) {
		var li_list = $('#createForm_select_content > li');
		li_list.removeClass();
		li.className = 'select_codes_li';

		var isShrink = document.getElementById('shrink_content') != null;
		if (isShrink)
			return;

		var arr = li.title.split(':');
		var units = parseInt(arr[0], 10);
		var redGallArr;
		var redArr;
		var blueGallArr;
		var blueArr;
		var select12to2Arr;
		if (arr[1].indexOf('|') > 0) {
			select12to2Arr = [];
			arr = arr[1].split('|');
			if (arr[0].indexOf(';') > 0) {
				var red_all = arr[0].split(';');
				redGallArr = red_all[0].split(',');
				redArr = red_all[1].split(',');
			} else {
				redGallArr = [];
				redArr = arr[0].split(',');
			}
			if (arr[1].indexOf(';') > 0) {
				var blue_all = arr[1].split(';');
				blueGallArr = blue_all[0].split(',');
				blueArr = blue_all[1].split(',');
			} else {
				blueGallArr = [];
				blueArr = arr[1].split(',');
			}
		} else {
			select12to2Arr = arr[1].split(',');
			redGallArr = [];
			redArr = [];
			blueGallArr = [];
			blueArr = [];
		}

		currentContentObj.redDans.length = 0;
		currentContentObj.reds.length = 0;
		currentContentObj.blueDans.length = 0;
		currentContentObj.blues.length = 0;
		currentContentObj.select12to2Dans.length = 0;
		currentContentObj.select12to2s.length = 0;
		$('#red_area_box_galls a[_name="red_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, redGallArr) >= 0) {
				item.className = selectedRebClass;
				currentContentObj.redDans.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = unSelectedClass;
			}
		});
		$('#red_area_box a[_name="red_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, redArr) >= 0) {
				item.className = selectedRebClass;
				currentContentObj.reds.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = unSelectedClass;
			}
		});
		$('#blue_area_box_galls a[_name="blue_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, blueGallArr) >= 0) {
				item.className = selectedBlueClass;
				currentContentObj.blueDans.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = unSelectedClass;
			}
		});
		$('#blue_area_box a[_name="blue_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, blueArr) >= 0) {
				item.className = selectedBlueClass;
				currentContentObj.blues.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = unSelectedClass;
			}
		});
		$('#select12to2_area_box a[_name="12to2_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, select12to2Arr) >= 0) {
				item.className = selectedBlueClass;
				currentContentObj.select12to2s.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = unSelectedClass;
			}
		});
		currentContentObj.redDans.sort(asc);
		currentContentObj.reds.sort(asc);
		currentContentObj.blueDans.sort(asc);
		currentContentObj.blues.sort(asc);
		currentContentObj.select12to2Dans.sort(asc);
		currentContentObj.select12to2s.sort(asc);
		update_set_redDan();
		update_set_blueDan();
		update_set_select12to2Dan();
		calcCurrentUnits();
	};
	window.clearSelect = function(generateNum) {
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0 - parseInt(arr[0]));
	};

	$('#red_area_box a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedRebClass) {
			this.className = unSelectedClass;
			currentContentObj.reds.erase(val);
		} else {
			this.className = selectedRebClass;
			currentContentObj.reds.push(val);
			if (checkRepeat(val, $('#red_area_box_galls a[_name="red_ball"]'), selectedRebClass)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.reds.erase(val);
				return;
			}
		}
		currentContentObj.reds.sort(asc);
		update_set_redDan();
		calcCurrentUnits();
	});

	$('#red_area_box_galls a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedRebClass) {
			this.className = unSelectedClass;
			currentContentObj.redDans.erase(val);
		} else {
			this.className = selectedRebClass;
			currentContentObj.redDans.push(val);
			if (currentContentObj.redDans.length > 4) {
				window.alert("前区设胆至多4个");
				this.className = unSelectedClass;
				currentContentObj.redDans.erase(val);
				return;
			}
			if (checkRepeat(val, $('#red_area_box a[_name="red_ball"]'), selectedRebClass)) {
				this.className = unSelectedClass;
				currentContentObj.redDans.erase(val);
				return;
			}
		}
		currentContentObj.redDans.sort(asc);
		update_set_redDan();
		calcCurrentUnits();
	});
	$('#blue_area_box a[_name="blue_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedBlueClass) {
			this.className = unSelectedClass;
			currentContentObj.blues.erase(val);
		} else {
			this.className = selectedBlueClass;
			currentContentObj.blues.push(val);
			if (checkRepeat(val, $('#blue_area_box_galls a[_name="blue_ball"]'), selectedBlueClass)) {
				this.className = unSelectedClass;
				currentContentObj.blues.erase(val);
				return;
			}
		}
		currentContentObj.blues.sort(asc);
		update_set_blueDan();
		calcCurrentUnits();
	});
	$('#blue_area_box_galls a[_name="blue_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedBlueClass) {
			this.className = unSelectedClass;
			currentContentObj.blueDans.erase(val);
		} else {
			this.className = selectedBlueClass;
			currentContentObj.blueDans.push(val);
			if (currentContentObj.blueDans.length > 1) {
				window.alert("后区设胆至多1个");
				this.className = unSelectedClass;
				currentContentObj.blueDans.erase(val);
				return;
			}
			if (checkRepeat(val, $('#blue_area_box a[_name="blue_ball"]'), selectedBlueClass)) {
				this.className = unSelectedClass;
				currentContentObj.blueDans.erase(val);
				return;
			}
		}
		currentContentObj.blueDans.sort(asc);
		update_set_blueDan();
		calcCurrentUnits();
	});
	var checkRepeat = function(val, ballArr, className) {
		for ( var i = 0; i < ballArr.length; i++) {
			if (ballArr[i].className == className && val == parseInt(ballArr[i].innerHTML, 10)) {
				return true;
			}
		}
		return false;
	};

	$('#select12to2_area_box a[_name="12to2_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedBlueClass) {
			this.className = unSelectedClass;
			currentContentObj.select12to2s.erase(val);
		} else {
			this.className = selectedBlueClass;
			currentContentObj.select12to2s.push(val);
		}
		currentContentObj.select12to2s.sort(asc);
		update_set_select12to2Dan();
		calcCurrentUnits();
	});
	var RED_BALL_INDEX = [];
	for ( var i = 0; i < 35; i++) {
		RED_BALL_INDEX[i] = i;
	}
	var BLUE_BALL_INDEX = [];
	for ( var i = 0; i < 12; i++) {
		BLUE_BALL_INDEX[i] = i;
	}
	window.randomSelectBall = function(type) {
		var select = document.getElementById(type + "_select");
		var value = select.options[select.options.selectedIndex].value;
		if ('red' == type) {
			var red_box = $('#red_area_box a[_name="red_ball"]');// /取到所有红球
			red_box.attr("class", unSelectedClass); // 初始化红球
			currentContentObj.reds.length = 0;// 初始化红球
			var copyRedBallArr = RED_BALL_INDEX.slice(0, RED_BALL_INDEX.length);
			var randomNumArr = randomOrder(copyRedBallArr).slice(0, value).sort(asc);
			var val;
			for ( var i = 0; i < randomNumArr.length; i++) {
				red_box[randomNumArr[i]].className = selectedRebClass;
				val = parseInt(red_box[randomNumArr[i]].innerHTML, 10);
				currentContentObj.reds.push(val);
			}
			currentContentObj.reds.sort(asc);
			calcCurrentUnits();
		} else if ('blue' == type) {
			var blue_box = $('#blue_area_box a[_name="blue_ball"]');// /取到所有蓝球
			blue_box.attr("class", unSelectedClass); // 初始化蓝球
			currentContentObj.blues.length = 0;// 初始化蓝球
			var copyBlueBallArr = BLUE_BALL_INDEX.slice(0, BLUE_BALL_INDEX.length);
			var randomNumArr = copyBlueBallArr.sort(randomSort).slice(0, value).sort(asc);
			var val;
			for ( var i = 0; i < randomNumArr.length; i++) {
				blue_box[randomNumArr[i]].className = selectedBlueClass;
				val = parseInt(blue_box[randomNumArr[i]].innerHTML, 10);
				currentContentObj.blues.push(val);
			}
			currentContentObj.blues.sort(asc);
			calcCurrentUnits();
		} else {

		}

	};

	window.clearRandomSelectBall = function(type) {
		if ('red' == type) {
			var red_box = $('#red_area_box a[_name="red_ball"]');// /取到所有红球
			red_box.attr("class", unSelectedClass); // 初始化红球
			currentContentObj.reds.length = 0;// 初始化红球
			calcCurrentUnits();
		} else if ('blue' == type) {
			var blue_box = $('#blue_area_box a[_name="blue_ball"]');// /取到所有蓝球
			blue_box.attr("class", unSelectedClass); // 初始化蓝球
			currentContentObj.blues.length = 0;// 初始化蓝球
			calcCurrentUnits();
		} else {

		}

	};
	window.update_set_redDan = function() {
		var obj_red_gall = $("#select_red_gall");
		obj_red_gall.empty();// 清空内容
		var temp = "";
		var alias = this;
		for ( var i = 0; i < currentContentObj.redDans.length; i++) {
			temp = "<input type='checkbox' checked='checked' value='" + currentContentObj.redDans[i]
					+ "' onclick='select_redDan(this)' /><span style='color:red;'>" + currentContentObj.redDans[i]
					+ "</span>";
			obj_red_gall.append(temp);
		}
		var disabled = "";
		if (currentContentObj.redDans.length >= 4
				|| currentContentObj.reds.length + currentContentObj.redDans.length <= 5) {
			disabled = "disabled='disabled'";
		}
		for ( var j = 0; j < currentContentObj.reds.length; j++) {
			if ((j + currentContentObj.redDans.length) % 10 == 0 && j != 0) {
				obj_red_gall.append('<br />');
			}
			temp = "<input type='checkbox' " + disabled + " value='" + currentContentObj.reds[j]
					+ "' onclick='select_redDan(this)' /><span>" + currentContentObj.reds[j] + "</span>";
			obj_red_gall.append(temp);
		}
	};

	window.update_set_blueDan = function() {
		var obj_blue_gall = $("#select_blue_gall");
		obj_blue_gall.empty();// 清空内容
		var temp = "";
		var alias = this;
		for ( var i = 0; i < currentContentObj.blueDans.length; i++) {
			temp = "<input type='checkbox' checked='checked' value='" + currentContentObj.blueDans[i]
					+ "' onclick='select_blueDan(this)' /><span style='color:blue;'>" + currentContentObj.blueDans[i]
					+ "</span>";
			obj_blue_gall.append(temp);
		}
		var disabled = "";
		if (currentContentObj.blueDans.length >= 1
				|| currentContentObj.blues.length + currentContentObj.blueDans.length <= 2) {
			disabled = "disabled='disabled'";
		}
		for ( var j = 0; j < currentContentObj.blues.length; j++) {
			if ((j + currentContentObj.blueDans.length) % 10 == 0 && j != 0) {
				obj_blue_gall.append('<br />');
			}
			temp = "<input type='checkbox' " + disabled + " value='" + currentContentObj.blues[j]
					+ "' onclick='select_blueDan(this)' /><span>" + currentContentObj.blues[j] + "</span>";
			obj_blue_gall.append(temp);
		}
	};
	window.update_set_select12to2Dan = function() {
		var obj_select12to2_gall = $("#select_select12to2_gall");
		obj_select12to2_gall.empty();// 清空内容
		var temp = "";
		var alias = this;
		for ( var i = 0; i < currentContentObj.select12to2Dans.length; i++) {
			temp = "<input type='checkbox' checked='checked' value='" + currentContentObj.select12to2Dans[i]
					+ "' onclick='select_select12to2Dan(this)' /><span style='color:blue;'>"
					+ currentContentObj.select12to2Dans[i] + "</span>";
			obj_select12to2_gall.append(temp);
		}
		var disabled = "";
		if (currentContentObj.select12to2Dans.length >= 1
				|| currentContentObj.select12to2s.length + currentContentObj.select12to2Dans.length <= 2) {
			disabled = "disabled='disabled'";
		}
		for ( var j = 0; j < currentContentObj.select12to2s.length; j++) {
			temp = "<input type='checkbox' " + disabled + " value='" + currentContentObj.select12to2s[j]
					+ "' onclick='select_select12to2Dan(this)' /><span>" + currentContentObj.select12to2s[j]
					+ "</span>";
			obj_select12to2_gall.append(temp);
		}
	};

	window.select_redDan = function(obj) {
		var value = parseInt(obj.value, 10);
		if (obj.checked) {
			currentContentObj.redDans.push(value);
			currentContentObj.redDans.sort(asc);
			currentContentObj.reds.erase(value);
		} else {
			currentContentObj.redDans.erase(value);
			currentContentObj.reds.push(value);
			currentContentObj.reds.sort(asc);
		}
		update_set_redDan();
		calcCurrentUnits();
	};

	window.select_blueDan = function(obj) {
		var value = parseInt(obj.value, 10);
		if (obj.checked) {
			currentContentObj.blueDans.push(value);
			currentContentObj.blueDans.sort(asc);
			currentContentObj.blues.erase(value);
		} else {
			currentContentObj.blueDans.erase(value);
			currentContentObj.blues.push(value);
			currentContentObj.blues.sort(asc);
		}
		update_set_blueDan();
		calcCurrentUnits();
	};

	window.select_select12to2Dan = function(obj) {
		var value = parseInt(obj.value, 10);
		if (obj.checked) {
			currentContentObj.select12to2Dans.push(value);
			currentContentObj.select12to2Dans.sort(asc);
			currentContentObj.select12to2s.erase(value);
		} else {
			currentContentObj.select12to2Dans.erase(value);
			currentContentObj.select12to2s.push(value);
			currentContentObj.select12to2s.sort(asc);
		}
		update_set_select12to2Dan();
		calcCurrentUnits();
	};
	window.danRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		if (currentContentObj.reds.length ==0&&currentContentObj.blues.length ==0) {
			alert('请至少选择1个胆码');
			return;
		}
		if (currentContentObj.reds.length >= 5) {
			alert('前区最多只能设置4个胆码');
			return;
		}
		if (currentContentObj.blues.length > 2) {
			alert('后区最多只能设置2个胆码');
			return;
		}

		var copyRedBallArr = GENERAL_RED_BALL_ARR.slice(0, GENERAL_RED_BALL_ARR.length);
		if (currentContentObj.reds.length > 0) {
			for ( var i = 0, len = currentContentObj.reds.length; i < len; i++) {
				copyRedBallArr.erase(currentContentObj.reds[i]);
			}
		}
		var copyBlueBallArr = GENERAL_BLUE_BALL_ARR.slice(0, GENERAL_BLUE_BALL_ARR.length);
		if (currentContentObj.blues.length > 0) {
			for ( var i = 0, len = currentContentObj.blues.length; i < len; i++) {
				copyBlueBallArr.erase(currentContentObj.blues[i]);
			}
		}
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var reds = copyRedBallArr.sort(randomSort).slice(0, 5 - currentContentObj.reds.length);
			reds = reds.concat(currentContentObj.reds);
			var blues;
			if (currentContentObj.blues.length > 0) {
				blues = copyBlueBallArr.sort(randomSort).slice(0, 2 - currentContentObj.blues.length);
				blues = blues.concat(currentContentObj.blues);
			} else {
				blues = copyBlueBallArr.sort(randomSort).slice(0, 2);
			}
			var contentObj = {
				redDans : [], // 红球胆码
				reds : reds.sort(asc), // 红球拖码
				blueDans : [], // 蓝球胆码
				blues : blues.sort(asc), // 蓝球拖码
				select12to2Dans : [], // 12选2
				select12to2s : [], // 12选2
				units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	// ////////////////////////////////////////////////////////////////////////////
	$('#select12to2_choose_box a[_name="all"]').bind("click", function(event) {
		chooseBallMethod("all");
	});
	$('#select12to2_choose_box a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod("odd");
	});
	$('#select12to2_choose_box a[_name="even"]').bind("click", function(event) {
		chooseBallMethod("even");
	});
	$('#select12to2_choose_box a[_name="big"]').bind("click", function(event) {
		chooseBallMethod("big");
	});
	$('#select12to2_choose_box a[_name="small"]').bind("click", function(event) {
		chooseBallMethod("small");
	});
	$('#select12to2_choose_box a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod("clear");
	});

	var chooseBallMethod = function(oprType) {
		var ballArr = $('#select12to2_area_box a[_name="12to2_ball"]');
		chooseMethod(ballArr, oprType, currentContentObj.select12to2s);
	};
	var chooseMethod = function(ballArr, oprType, obj) {
		for (i = 0; i < ballArr.length; i++) {
			ballUnSelected(ballArr[i], obj);
		}
		for (i = 0; i < ballArr.length; i++) {
			var val = parseInt(ballArr[i].innerHTML, 10);
			if ("all" == oprType) {
				ballSelected(ballArr[i], obj);
			} else if ("odd" == oprType) {
				if (val % 2 == 1) {
					ballSelected(ballArr[i], obj);
				}
			} else if ("even" == oprType) {
				if (val % 2 == 0) {
					ballSelected(ballArr[i], obj);
				}
			} else if ("big" == oprType) {
				if (val >= 7) {
					ballSelected(ballArr[i], obj);
				}
			} else if ("small" == oprType) {
				if (val < 7) {
					ballSelected(ballArr[i], obj);
				}
			}
		}
	};
	var ballSelected = function(ball, content) {
		if (ball.className == selectedBlueClass)
			return;
		var val = parseInt(ball.innerHTML, 10);
		for ( var i = 0, ball, l = content.length; i < l; i++) {
			if (content[i] == val) {
				return;
			}
		}
		ball.className = selectedBlueClass;
		content.push(val);
		content.sort(asc);
		calcCurrentUnits();
	};
	var ballUnSelected = function(ball, content) {
		if (ball.className == unSelectedClass)
			return;
		var val = parseInt(ball.innerHTML, 10);
		ball.className = unSelectedClass;
		content.erase(val);
		content.sort(asc);
		calcCurrentUnits();
	};

	$('#shrink_blue_box a[_name="blue_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedBlueClass) {
			this.className = unSelectedClass;
			currentContentObj.blues.erase(val);
		} else {
			this.className = selectedBlueClass;
			currentContentObj.blues.push(val);
		}
		currentContentObj.blues.sort(asc);

		shrinkCalcCurrentUnits();
	});

	var convertNumArr = function(redLine) {
		var numArr = redLine.replace(/^\s*(.+?)\s*$/, '$1').split(/[^\d]+/);
		if (numArr.length != 5)
			throw '前区号码格式不正确';
		var tempArr = [];
		for ( var j = 0; j < numArr.length; j++) {
			var num = numArr[j];
			if (!/\d+/.test(num))
				throw '前区号码只能从1-35中选择';
			var numInt = parseInt(num, 10);
			if (numInt < 1 || numInt > 35)
				throw '前区号码只是从1-35中选择';
			if ($.inArray(numInt, tempArr) >= 0)
				throw '每注的前区号码不能重复';
			tempArr.push(numInt);
		}
		return tempArr;
	};

	var shrinkCalcCurrentUnits = function() {
		var shrink_content = document.getElementById('shrink_content').value;
		shrink_content = $.trim(shrink_content);
		var units = 0;
		if (shrink_content != '') {
			var redSize = 0;
			var redArr = shrink_content.split(/\r\n|\n/);
			for ( var i = 0; i < redArr.length; i++) {
				var redLine = redArr[i];
				if (!/^\s*$/.test(redLine)) {
					try {
						convertNumArr(redLine);
					} catch (e) {
						alert(e);
						redSize = 0;
						break;
					}
					redSize++;
				}
			}
			if (redSize > 0) {
				var blueSize = currentContentObj.blues.length;
				if (blueSize >= 2) {
					units = redSize * comp(2, blueSize);
				}
			}
		}
		currentContentObj.units = units;
		updateCurrentMsg();
	};

	$('#shrink_content').bind("blur", shrinkCalcCurrentUnits);

	var shrinkPushContent = function(contentObj) {
		var shrink_content = document.getElementById('shrink_content').value;
		shrink_content = $.trim(shrink_content);
		if (shrink_content != '') {
			var blueText = ' | ';
			var blueValue = '|';
			for ( var i = 0, ball, l = contentObj.blues.length; i < l; i++) {
				ball = getBallStr(contentObj.blues[i]);
				blueText += ball;
				blueValue += ball;
				if (i != l - 1) {
					blueText += ',';
					blueValue += ',';
				}
			}
			var selectObj = document.getElementById('createForm_select_content');
			var redArr = shrink_content.split(/\r\n|\n/);
			var lineUnits = comp(2, currentContentObj.blues.length);
			for ( var i = 0, text, value; i < redArr.length; i++) {
				var redStr = '';
				try {
					var redNumArr = convertNumArr(redArr[i]);
					redNumArr.sort(ascSort);
					for ( var j = 0; j < redNumArr.length; j++) {
						var redNumInt = redNumArr[j];
						if (j > 0)
							redStr += ',';
						if (redNumInt < 10)
							redStr += '0';
						redStr += '' + redNumInt;
					}
				} catch (e) {
					alert(e);
					return;
				}
				var text = redStr + blueText;
				var value = lineUnits + ':' + redStr + blueValue;
				selectAddOption(selectObj, text, value);
			}
			addOrSubBetUnits(contentObj.units);
		}
	};

	function ascSort(num1, num2) {
		return num1 - num2;
	}

});
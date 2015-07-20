$(function() {
	var redSelectedClass = 'red';
	var redUnSelectedClass = '';

	var currentContentObj = {
		reds : [], // 选球数
		redGalls : [],// 选球胆码数
		units : 0
	}; 

	var clearCurrentContent = function() {
		$('#red_box a[_name="red_ball"]').attr("class", redUnSelectedClass);
		$('#red_box1 a[_name="red_ball"]').attr("class", redUnSelectedClass);
		$('#red_box_galls a[_name="red_ball"]').attr("class", redUnSelectedClass);
		currentContentObj.reds.length = 0;
		currentContentObj.redGalls.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};
	window.clearCurrentSelect = function() {
		clearCurrentContent();
	};
	var calcCurrentUnits = function() {
		var units = 0;
		var redSize = currentContentObj.reds.length;
		var redGallSize = currentContentObj.redGalls.length;
		if (redSize + redGallSize >= 5) {
			units = comp(5 - redGallSize, redSize);
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

	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';
		for ( var i = 0, ball, l = contentObj.redGalls.length; i < l; i++) {
			ball = getBallStr(contentObj.redGalls[i]);

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

	var RED_BALL_ARR = [];
	for ( var i = 0; i < 22; i++) {
		RED_BALL_ARR[i] = i + 1;
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

	window.randomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyRedBallArr = RED_BALL_ARR.slice(0, RED_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
				reds : copyRedBallArr.sort(randomSort).slice(0, 5).sort(asc),
				redGalls : [],
				units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.luckRandomSelect = function(redsArr) {
		var randomContent = [];
		var contentObj = {
			reds : redsArr,
			redGalls : [],
			units : 1
		};
		randomContent.push(contentObj);
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.danRandomSelect = function(randomUnits) {		
		if (randomUnits <= 0)
			return;
		if (currentContentObj.reds.length == 0) {
			alert('至少设置1个胆码');
			return;
		}
		if (currentContentObj.reds.length >= 5) {
			alert('最多只能设置4个胆码');
			return;
		}

		var copyRedBallArr = RED_BALL_ARR.slice(0, RED_BALL_ARR.length);
		if (currentContentObj.reds.length > 0) {
			for ( var i = 0, len = currentContentObj.reds.length; i < len; i++) {
				copyRedBallArr.erase(currentContentObj.reds[i]);
			}
		}
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var reds = copyRedBallArr.sort(randomSort).slice(0, 5 - currentContentObj.reds.length);
			reds = reds.concat(currentContentObj.reds);			
			var contentObj = {
				reds : reds.sort(asc),
				redGalls : [],
				units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.danRandomSelect_1 = function(randomUnits) {		
		if (randomUnits <= 0)
			return;
		if (currentContentObj.redGalls.length == 0) {
			alert('至少设置1个胆码');
			return;
		}
		if (currentContentObj.redGalls.length >= 5) {
			alert('最多只能设置4个胆码');
			return;
		}

		var copyRedBallArr = RED_BALL_ARR.slice(0, RED_BALL_ARR.length);
		if (currentContentObj.redGalls.length > 0) {
			for ( var i = 0, len = currentContentObj.redGalls.length; i < len; i++) {
				copyRedBallArr.erase(currentContentObj.redGalls[i]);
			}
		}
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var reds = copyRedBallArr.sort(randomSort).slice(0, 5 - currentContentObj.redGalls.length);
			reds = reds.concat(currentContentObj.redGalls);			
			var contentObj = {
				reds : reds.sort(asc),
				redGalls : [],
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
		if (arr[1].indexOf(';') > 0) {
			arr = arr[1].split(';');
			redGallArr = arr[0].split(',');
			redArr = arr[1].split(',');
		} else {
			redGallArr = [];
			redArr = arr[1].split(',');
		}

		currentContentObj.redGalls.length = 0;
		currentContentObj.reds.length = 0;
		$('#red_box_galls a[_name="red_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, redGallArr) >= 0) {
				item.className = redSelectedClass;
				currentContentObj.redGalls.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = redUnSelectedClass;
			}
		});
		$('#red_box a[_name="red_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, redArr) >= 0) {
				item.className = redSelectedClass;
				currentContentObj.reds.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = redUnSelectedClass;
			}
		});
		$('#red_box1 a[_name="red_ball"]').each(function(index, item) {
			if (jQuery.inArray(item.innerHTML, redArr) >= 0) {
				item.className = redSelectedClass;
				currentContentObj.reds.push(parseInt(item.innerHTML, 10));
			} else {
				item.className = redUnSelectedClass;
			}
		});
		currentContentObj.redGalls.sort(asc);
		currentContentObj.reds.sort(asc);
		update_setredgall();
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

	$('#red_box_galls a[_name="red_ball"]').bind("click", function(event) {

		var val = parseInt(this.innerHTML, 10);
		if (this.className == redSelectedClass) {
			this.className = redUnSelectedClass;
			currentContentObj.redGalls.erase(val);
		} else {
			this.className = redSelectedClass;
			currentContentObj.redGalls.push(val);
			if (currentContentObj.redGalls.length > 4) {
				window.alert("选球设胆至多4个");
				this.className = redUnSelectedClass;
				currentContentObj.redGalls.erase(val);
				return;
			}
			if (checkDanRepeat(val)) {
				window.alert("胆码和拖码选号重复");
				this.className = redUnSelectedClass;
				currentContentObj.redGalls.erase(val);
				return;
			}
		}
		currentContentObj.redGalls.sort(asc);
		update_setredgall();
		calcCurrentUnits();
	});

	$('#red_box a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == redSelectedClass) {
			this.className = redUnSelectedClass;
			currentContentObj.reds.erase(val);
		} else {
			this.className = redSelectedClass;
			currentContentObj.reds.push(val);
			if (checkNormalRepeat(val)) {
				window.alert("胆码和拖码选号重复");
				this.className = redUnSelectedClass;
				currentContentObj.reds.erase(val);
				return;
			}
		}
		currentContentObj.reds.sort(asc);
		update_setredgall();
		calcCurrentUnits();
	});
	$('#red_box1 a[_name="red_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == redSelectedClass) {
			this.className = redUnSelectedClass;
			currentContentObj.reds.erase(val);
		} else {
			this.className = redSelectedClass;
			currentContentObj.reds.push(val);
		}
		currentContentObj.reds.sort(asc);
		update_setredgall();
		calcCurrentUnits();
	});
	var checkDanRepeat = function(val) {
		var normalArr = $('#red_box a[_name="red_ball"]');
		for ( var i = 0; i < normalArr.length; i++) {
			if (normalArr[i].className == redSelectedClass && val == parseInt(normalArr[i].innerHTML, 10)) {
				return true;
			}
		}
		return false;
	};
	var checkNormalRepeat = function(val) {
		var danArr = $('#red_box_galls a[_name="red_ball"]');
		for ( var i = 0; i < danArr.length; i++) {
			if (danArr[i].className == redSelectedClass && val == parseInt(danArr[i].innerHTML, 10)) {
				return true;
			}
		}
		return false;
	};
	var RED_BALL_INDEX = [];
	for ( var i = 0; i < 11; i++) {
		RED_BALL_INDEX[i] = i;
	}
	window.randomSelectBall = function(type) {
		var select = document.getElementById(type + "_select");
		var value = select.options[select.options.selectedIndex].value;
		if ('red' == type) {
			var red_box = $('#red_box a[_name="red_ball"]');// /取到所有红球
			red_box.attr("class", redUnSelectedClass); // 初始化红球
			var red_box1 = $('#red_box1 a[_name="red_ball"]');// /取到所有红球
			red_box1.attr("class", redUnSelectedClass); // 初始化红球
			currentContentObj.reds.length = 0;// 初始化红球
			var copyRedBallArr = RED_BALL_INDEX.slice(0, RED_BALL_INDEX.length);
			var randNum = Math.floor(Math.random()*RED_BALL_INDEX.length+1);			
			while(randNum>value || randNum<value-RED_BALL_INDEX.length){
				randNum = Math.floor(Math.random()*RED_BALL_INDEX.length+1);
			}
			var randomNumArr = randomOrder(copyRedBallArr).slice(0, randNum).sort(asc);
			var val;
			for ( var i = 0; i < randomNumArr.length; i++) {
				red_box[randomNumArr[i]].className = redSelectedClass;
				val = parseInt(red_box[randomNumArr[i]].innerHTML, 10);
				currentContentObj.reds.push(val);
			}
			
			var randomNumArr = randomOrder(copyRedBallArr).slice(0, value-randNum).sort(asc);
			var val;
			for ( var i = 0; i < randomNumArr.length; i++) {
				red_box1[randomNumArr[i]].className = redSelectedClass;
				val = parseInt(red_box1[randomNumArr[i]].innerHTML, 10);
				currentContentObj.reds.push(val);
			}
			currentContentObj.reds.sort(asc);
			calcCurrentUnits();
		}else {

		}

	};

	window.clearRandomSelectBall = function(type) {
		if ('red' == type) {
			var red_box = $('#red_box a[_name="red_ball"]');// /取到所有红球
			var red_box1 = $('#red_box1 a[_name="red_ball"]');// /取到所有红球
			red_box.attr("class", redUnSelectedClass); // 初始化红球
			red_box1.attr("class", redUnSelectedClass); // 初始化红球
			currentContentObj.reds.length = 0;// 初始化红球
			update_setredgall();
			calcCurrentUnits();
		}else {

		}

	};

	// 更新红球设胆
	window.update_setredgall = function() {
		var obj_red_gall = $("#select_red_gall");
		obj_red_gall.empty();// 清空内容
		var temp = "";
		var alias = this;
		for ( var i = 0; i < currentContentObj.redGalls.length; i++) {
			temp = "<input type='checkbox' checked='checked' value='" + currentContentObj.redGalls[i]
					+ "' onclick='select_redgallball(this)' /><span style='color:red;'>"
					+ currentContentObj.redGalls[i] + "</span>";
			obj_red_gall.append(temp);
		}
		var disabled = "";
		if (currentContentObj.redGalls.length >= 5
				|| currentContentObj.reds.length + currentContentObj.redGalls.length <= 6) {
			disabled = "disabled='disabled'";
		}
		for ( var j = 0; j < currentContentObj.reds.length; j++) {
			if ((j + currentContentObj.redGalls.length) % 10 == 0 && j != 0) {
				obj_red_gall.append('<br />');
			}
			temp = "<input type='checkbox' " + disabled + " value='" + currentContentObj.reds[j]
					+ "' onclick='select_redgallball(this)' /><span>" + currentContentObj.reds[j] + "</span>";
			obj_red_gall.append(temp);
		}
	};

	window.select_redgallball = function(obj) {
		var value = parseInt(obj.value, 10);
		if (obj.checked) {
			currentContentObj.redGalls.push(value);
			currentContentObj.redGalls.sort(asc);
			currentContentObj.reds.erase(value);
		} else {
			currentContentObj.redGalls.erase(value);
			currentContentObj.reds.push(value);
			currentContentObj.reds.sort(asc);
		}
		update_setredgall();
		calcCurrentUnits();
	};

	var convertNumArr = function(redLine) {
		var numArr = redLine.replace(/^\s*(.+?)\s*$/, '$1').split(/[^\d]+/);
		if (numArr.length != 5)
			throw '选球号码格式不正确';
		var tempArr = [];
		for ( var j = 0; j < numArr.length; j++) {
			var num = numArr[j];
			if (!/\d+/.test(num))
				throw '选球号码只能从1-22中选择';
			var numInt = parseInt(num, 10);
			if (numInt < 1 || numInt > 22)
				throw '选球号码只是从1-22中选择';
			if ($.inArray(numInt, tempArr) >= 0)
				throw '每注的选球号码不能重复';
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
				units = redSize;
			}
		}
		currentContentObj.units = units;
		updateCurrentMsg();
	};

	$('#shrink_content').bind("blur", shrinkCalcCurrentUnits);
	$('#shrink_counter').bind("click", shrinkCalcCurrentUnits);

	var shrinkPushContent = function(contentObj) {
		var shrink_content = document.getElementById('shrink_content').value;
		shrink_content = $.trim(shrink_content);
		if (shrink_content != '') {
			var selectObj = document.getElementById('createForm_select_content');
			var redArr = shrink_content.split(/\r\n|\n/);
			for ( var i = 0; i < redArr.length; i++) {
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
				var value = 1 + ':' + redStr;
				selectAddOption(selectObj, redStr, value);
			}
			addOrSubBetUnits(contentObj.units);
		}
	};

	function ascSort(num1, num2) {
		return num1 - num2;
	}
	
	$('#tr_ball a[_name="all"]').bind("click", function(event) {
		chooseBallMethod("all",1);
	});
	$('#tr_ball a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod("odd",1);
	});
	$('#tr_ball a[_name="even"]').bind("click", function(event) {
		chooseBallMethod("even",1);
	});
	$('#tr_ball a[_name="big"]').bind("click", function(event) {
		chooseBallMethod("big",1);
	});
	$('#tr_ball a[_name="small"]').bind("click", function(event) {
		chooseBallMethod("small",1);
	});
	$('#tr_ball a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod("clear",1);
	});
	
	
	$('#tr1_ball a[_name="all"]').bind("click", function(event) {
		chooseBallMethod("all",2);
	});
	$('#tr1_ball a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod("odd",2);
	});
	$('#tr1_ball a[_name="even"]').bind("click", function(event) {
		chooseBallMethod("even",2);
	});
	$('#tr1_ball a[_name="big"]').bind("click", function(event) {
		chooseBallMethod("big",2);
	});
	$('#tr1_ball a[_name="small"]').bind("click", function(event) {
		chooseBallMethod("small",2);
	});
	$('#tr1_ball a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod("clear",2);
	});
	
	var chooseBallMethod=function(oprType,line){
		if(line==1){
			var ballArr=$('#red_box a[_name="red_ball"]');
			chooseMethod(ballArr,oprType,currentContentObj.reds,line);
		}else if(line==2){
			var ballArr=$('#red_box1 a[_name="red_ball"]');
			chooseMethod(ballArr,oprType,currentContentObj.reds,line);
		}
	};
	var chooseMethod = function (ballArr,oprType,obj,line){
		for(i=0;i<ballArr.length;i++){
			ballUnSelected(ballArr[i], obj);
		}
		for(i=0;i<ballArr.length;i++){
			var val = parseInt(ballArr[i].innerHTML, 10);
			if("all"==oprType){
				ballSelected(ballArr[i], obj);
			}else if("odd"==oprType){
				if(val%2==1){
					ballSelected(ballArr[i], obj);
				}
			}else if("even"==oprType){
				if(val%2==0){
					ballSelected(ballArr[i], obj);
				}
			}else if("big"==oprType){
				if(line=="1"){
					if(val>=6){
						ballSelected(ballArr[i], obj);
					}
				}else{
					if(val>=17){
						ballSelected(ballArr[i], obj);
					}
				}
				
			}else if("small"==oprType){
				if(line=="1"){
					if(val<6){
						ballSelected(ballArr[i], obj);
					}
				}else{
					if(val<17){
						ballSelected(ballArr[i], obj);
					}
				}
			}
		}
	};
	var ballSelected=function(ball,content){
		if(ball.className == redSelectedClass) return;
		var val = parseInt(ball.innerHTML, 10);
		for ( var i = 0, ball, l = content.length; i < l; i++) {
			if(content[i]==val){
				return;
			}
		}
		ball.className = redSelectedClass;
		currentContentObj.reds.push(val);
		currentContentObj.reds.sort(asc);
		update_setredgall();
		calcCurrentUnits();
	};
	var ballUnSelected=function(ball,content){
		if(ball.className == redUnSelectedClass) return;
		var val = parseInt(ball.innerHTML, 10);
		ball.className = redUnSelectedClass;
		currentContentObj.reds.erase(val);
		currentContentObj.reds.sort(asc);
		update_setredgall();
		calcCurrentUnits();
	};
});
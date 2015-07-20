$(function() {
	var selectedClass = 'red';
	var unSelectedClass = '';

	var currentContentObj = {
		area1 : [], // 第一区间
		area2 : [], // 第二区间
		area3 : [], // 第三区间
		area4 : [], // 第四区间
		area5 : [], // 第五区间
		area6 : [], // 第六区间
		area7 : [], // 第七区间
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('#area_box a[_name="area1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area4"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area5"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area6"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="area7"]').attr("class",unSelectedClass); 
		currentContentObj.area1.length = 0;
		currentContentObj.area2.length = 0;
		currentContentObj.area3.length = 0;
		currentContentObj.area4.length = 0;
		currentContentObj.area5.length = 0;
		currentContentObj.area6.length = 0;
		currentContentObj.area7.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};

	var calcCurrentUnits = function() {
		var units = 0;
		var area1Size = currentContentObj.area1.length;
		var area2Size = currentContentObj.area2.length;
		var area3Size = currentContentObj.area3.length;
		var area4Size = currentContentObj.area4.length;
		var area5Size = currentContentObj.area5.length;
		var area6Size = currentContentObj.area6.length;
		var area7Size = currentContentObj.area7.length;
		
		if (area1Size!=0&&area2Size!=0&&area3Size!=0&&area4Size!=0&&area5Size!=0&&area6Size!=0&&area7Size!=0) {
			units = area1Size * area2Size * area3Size * area4Size * area5Size * area6Size * area7Size;
		}
		currentContentObj.units = units;
		updateCurrentMsg();
	};

	var updateCurrentMsg = function() {
		// 更新页面显示
		if(currentContentObj.units>0){
			document.getElementById("selectUnitsSpan").innerHTML=""+currentContentObj.units;
			document.getElementById("selectAmountSpan").innerHTML=""+(currentContentObj.units*2);
		}else{
			document.getElementById("selectUnitsSpan").innerHTML="0";
			document.getElementById("selectAmountSpan").innerHTML="0";
		}
	};
	
	var getBallStr = function(val) {
			return '' + val;
	};
	
	var addOrSubBetUnits = function(chgUnits) {
		var createForm = getCreateForm();
		var units = createForm.elements["createForm.units"].value;
		if(units == "" || !/^\d/.test(units)){
			units = 0;
		}
		units = parseInt(units,10) + chgUnits;
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
	var areaSplitWord='-';
	var numSplitWord=',';
	var endSplitWord=';';
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';
		for ( var i = 0, ball, l = contentObj.area1.length; i < l; i++) {
			ball = getBallStr(contentObj.area1[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area2.length; i < l; i++) {
			ball = getBallStr(contentObj.area2[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area3.length; i < l; i++) {
			ball = getBallStr(contentObj.area3[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area4.length; i < l; i++) {
			ball = getBallStr(contentObj.area4[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area5.length; i < l; i++) {
			ball = getBallStr(contentObj.area5[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area6.length; i < l; i++) {
			ball = getBallStr(contentObj.area6[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} else {
				text += areaSplitWord;
				value += areaSplitWord;
			}
		}
		for ( var i = 0, ball, l = contentObj.area7.length; i < l; i++) {
			ball = getBallStr(contentObj.area7[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			}
		}
		selectAddOption(selectObj, text, value);// 往select里添加option
		addOrSubBetUnits(contentObj.units);// 更新方案注数
	};

	window.pushCurrentContent = function() {
		if (currentContentObj.units <= 0) {
			alert('您选择的号码还不够组成1注');
			return;
		}
		pushContent(currentContentObj);
		clearCurrentContent();
		clearChooseBallArea();
	};
	
	var BALL_ARR = [];
	for ( var i = 0; i < 10; i++) {
		BALL_ARR[i] = i;
	}
	var randomSort = function(){
		var randomNum = Math.random();
		if(randomNum > 0.5)
			return 1;
		else if(randomNum < 0.5)
			return -1;
		else
			return 0;
	};
	
	window.randomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyballArr = BALL_ARR.slice(0, BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
				area1 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第一区间
				area2 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第二区间
				area3 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第三区间
				area4 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第四区间
				area5 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第五区间
				area6 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第六区间
				area7 : copyballArr.sort(randomSort).slice(0, 1).sort(asc), // 第七区间
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
	
	window.clearSelect = function(generateNum){
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0-parseInt(arr[0]));
	};
	
	$('#area_box a[_name="area1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area1.push(val);
		}
		currentContentObj.area1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area2.push(val);
		}
		currentContentObj.area2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area3.push(val);
		}
		currentContentObj.area3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area4.push(val);
		}
		currentContentObj.area4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area5.push(val);
		}
		currentContentObj.area5.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area6"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area6.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area6.push(val);
		}
		currentContentObj.area6.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="area7"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.area7.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.area7.push(val);
		}
		currentContentObj.area7.sort(asc);
		calcCurrentUnits();
	});
	$('#area1ChooseUl a[_name="all"]').bind("click", function(event) {
		
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"all",currentContentObj.area1,1);
	});
	$('#area1ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"odd",currentContentObj.area1,1);
	});
	$('#area1ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"even",currentContentObj.area1,1);
	});
	$('#area1ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"big",currentContentObj.area1,1);
	});
	$('#area1ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"small",currentContentObj.area1,1);
	});
	$('#area1ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area1"]');
		chooseMethod(ballArr,"clear",currentContentObj.area1,1);
	});
	
	$('#area2ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"all",currentContentObj.area2,2);
	});
	$('#area2ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"odd",currentContentObj.area2,2);
	});
	$('#area2ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"even",currentContentObj.area2,2);
	});
	$('#area2ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"big",currentContentObj.area2,2);
	});
	$('#area2ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"small",currentContentObj.area2,2);
	});
	$('#area2ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area2"]');
		chooseMethod(ballArr,"clear",currentContentObj.area2,2);
	});
	
	$('#area3ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"all",currentContentObj.area3,3);
	});
	$('#area3ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"odd",currentContentObj.area3,3);
	});
	$('#area3ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"even",currentContentObj.area3,3);
	});
	$('#area3ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"big",currentContentObj.area3,3);
	});
	$('#area3ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"small",currentContentObj.area3,3);
	});
	$('#area3ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area3"]');
		chooseMethod(ballArr,"clear",currentContentObj.area3,3);
	});
	
	
	$('#area4ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"all",currentContentObj.area4,4);
	});
	$('#area4ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"odd",currentContentObj.area4,4);
	});
	$('#area4ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"even",currentContentObj.area4,4);
	});
	$('#area4ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"big",currentContentObj.area4,4);
	});
	$('#area4ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"small",currentContentObj.area4,4);
	});
	$('#area4ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area4"]');
		chooseMethod(ballArr,"clear",currentContentObj.area4,4);
	});
	
	
	$('#area5ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"all",currentContentObj.area5,5);
	});
	$('#area5ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"odd",currentContentObj.area5,5);
	});
	$('#area5ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"even",currentContentObj.area5,5);
	});
	$('#area5ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"big",currentContentObj.area5,5);
	});
	$('#area5ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"small",currentContentObj.area5,5);
	});
	$('#area5ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area5"]');
		chooseMethod(ballArr,"clear",currentContentObj.area5,5);
	});
	
	
	$('#area6ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"all",currentContentObj.area6,6);
	});
	$('#area6ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"odd",currentContentObj.area6,6);
	});
	$('#area6ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"even",currentContentObj.area6,6);
	});
	$('#area6ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"big",currentContentObj.area6,6);
	});
	$('#area6ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"small",currentContentObj.area6,6);
	});
	$('#area6ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area6"]');
		chooseMethod(ballArr,"clear",currentContentObj.area6,6);
	});
	
	
	
	$('#area7ChooseUl a[_name="all"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"all",currentContentObj.area7,7);
	});
	$('#area7ChooseUl a[_name="odd"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"odd",currentContentObj.area7,7);
	});
	$('#area7ChooseUl a[_name="even"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"even",currentContentObj.area7,7);
	});
	$('#area7ChooseUl a[_name="big"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"big",currentContentObj.area7,7);
	});
	$('#area7ChooseUl a[_name="small"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"small",currentContentObj.area7,7);
	});
	$('#area7ChooseUl a[_name="clear"]').bind("click", function(event) {
		var ballArr=$('#area_box a[_name="area7"]');
		chooseMethod(ballArr,"clear",currentContentObj.area7,7);
	});
	var clearChooseBallArea =  function(){
		for ( var int = 1; int <= 7; int++) {
			var all = $('#area'+int+'ChooseUl').find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
	};
	var chooseMethod = function (ballArr,oprType,obj,line){
		var all = $('#area'+line+'ChooseUl').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		if("all"==oprType){
			all[0].className = selectedClass;
		}else if("odd"==oprType){
			all[3].className = selectedClass;
		}else if("even"==oprType){
			all[4].className = selectedClass;
		}else if("big"==oprType){
			all[1].className = selectedClass;
		}else if("small"==oprType){
			all[2].className = selectedClass;
		}
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
				if(val>=5){
					ballSelected(ballArr[i], obj);
				}
			}else if("small"==oprType){
				if(val<5){
					ballSelected(ballArr[i], obj);
				}
			}else if("clear"==oprType){
				ballUnSelected(ballArr[i], obj);
			}
		}
	};
	var ballSelected=function(ball,content){
		if(ball.className == selectedClass) return;
		var val = parseInt(ball.innerHTML, 10);
		for ( var i = 0, ball, l = content.length; i < l; i++) {
			if(content[i]==val){
				return;
			}
		}
		ball.className = selectedClass;
		content.push(val);
		content.sort(asc);
		calcCurrentUnits();
	};
	var ballUnSelected=function(ball,content){
		if(ball.className == unSelectedClass) return;
		var val = parseInt(ball.innerHTML, 10);
		ball.className = unSelectedClass;
		content.erase(val);
		content.sort(asc);
		calcCurrentUnits();
	};
});
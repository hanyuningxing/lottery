$(function() {
	var selectedClass = 'red';
	var selectedClass_blue = 'blue';
	var unSelectedClass = '';
	var currentContentObj = {
		NormalOne : [], // 数投1
		RandomTwo : [], // 任选2
		ForeTwoGroup : [], // 连组2
		ForeTwoDirect1 : [], // 前二直2_1
		ForeTwoDirect2 : [], // 前二直2_2
		RandomThree : [], // 任选3
		ForeThreeGroup : [], // 前3租
		ForeThreeDirect1 : [], // 前3直_1
		ForeThreeDirect2 : [], // 前3直_2
		ForeThreeDirect3 : [], // 前3直_3
		RandomFour : [], // 任选4
		RandomFive : [], //  任选5
		RandomSix : [], // 任选6
		RandomSeven : [], //  任选7
		RandomEight : [], // 任选8
		
		Dan : [], //  
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('#NormalOneball_area_box a[_name="NormalOneball"]').attr("class",unSelectedClass); 
		$('#RandomTwoball_area_box a[_name="RandomTwoball"]').attr("class",unSelectedClass); 
		$('#ForeTwoGroupball_area_box a[_name="ForeTwoGroupball"]').attr("class",unSelectedClass); 
		$('#ForeTwoDirectball_area_box_1 a[_name="ForeTwoDirectball1"]').attr("class",unSelectedClass); 
		$('#ForeTwoDirectball_area_box_2 a[_name="ForeTwoDirectball2"]').attr("class",unSelectedClass); 
		$('#RandomThreeball_area_box a[_name="RandomThreeball"]').attr("class",unSelectedClass); 
		$('#ForeThreeGroupball_area_box a[_name="ForeThreeGroupball"]').attr("class",unSelectedClass); 
		$('#ForeThreeDirectball_area_box_1 a[_name="ForeThreeDirectball1"]').attr("class",unSelectedClass); 
		$('#ForeThreeDirectball_area_box_2 a[_name="ForeThreeDirectball2"]').attr("class",unSelectedClass); 
		$('#ForeThreeDirectball_area_box_3 a[_name="ForeThreeDirectball3"]').attr("class",unSelectedClass); 
		$('#RandomFourball_area_box a[_name="RandomFourball"]').attr("class",unSelectedClass); 
		$('#RandomFiveball_area_box a[_name="RandomFiveball"]').attr("class",unSelectedClass); 
		$('#RandomSixball_area_box a[_name="RandomSixball"]').attr("class",unSelectedClass); 
		$('#RandomSevenball_area_box a[_name="RandomSevenball"]').attr("class",unSelectedClass); 
		$('#RandomEightball_area_box a[_name="RandomEightball"]').attr("class",unSelectedClass); 
		$('#ball_area_box_dan a[_name="bet_ball"]').attr("class",unSelectedClass); 
		currentContentObj.NormalOne.length = 0;
		currentContentObj.RandomTwo.length = 0;
		currentContentObj.ForeTwoGroup.length = 0;
		currentContentObj.ForeTwoDirect1.length = 0;
		currentContentObj.ForeTwoDirect2.length = 0;
		currentContentObj.RandomThree.length = 0;
		currentContentObj.ForeThreeGroup.length = 0;
		currentContentObj.ForeThreeDirect1.length = 0;
		currentContentObj.ForeThreeDirect2.length = 0;
		currentContentObj.ForeThreeDirect3.length = 0;
		currentContentObj.RandomFour.length = 0;
		currentContentObj.RandomFive.length = 0;
		currentContentObj.RandomSix.length = 0;
		currentContentObj.RandomSeven.length = 0;
		currentContentObj.RandomEight.length = 0;
		currentContentObj.Dan.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};
	window.clearCurrentSelect=function(){
    	clearCurrentContent();
    };
	var calUnit= function(r,betSize,danSize){
		 if(danSize > 0){
			 if(betSize + danSize<r){
				 return 0;
			 }
			 return comp(r-danSize,betSize);
		 }else{
			 if(betSize<r){
				 return 0;
			 }
			 return comp(r,betSize);
		 }
    };
	var calcCurrentUnits = function() {
		var units = 0;
		var normalOneSize = currentContentObj.NormalOne.length;
		var randomTwoSize = currentContentObj.RandomTwo.length;
		var connectTwoGroupSize = currentContentObj.ForeTwoGroup.length;
		var foreTwoDirectSize1 = currentContentObj.ForeTwoDirect1.length;
		var foreTwoDirectSize2 = currentContentObj.ForeTwoDirect2.length;
		var randomThreeSize = currentContentObj.RandomThree.length;
		var foreThreeGroupSize = currentContentObj.ForeThreeGroup.length;
		var foreThreeDirectSize1 = currentContentObj.ForeThreeDirect1.length;
		var foreThreeDirectSize2 = currentContentObj.ForeThreeDirect2.length;
		var foreThreeDirectSize3 = currentContentObj.ForeThreeDirect3.length;
		var randomFourSize = currentContentObj.RandomFour.length;
		var randomFiveSize = currentContentObj.RandomFive.length;
		var randomSixSize = currentContentObj.RandomSix.length;
		var randomSevenSize = currentContentObj.RandomSeven.length;
		var randomEightSize = currentContentObj.RandomEight.length;
		var danSize = currentContentObj.Dan.length;
		if (normalOneSize!=0) {
			units = normalOneSize;
		}else if(randomTwoSize!=0){
			units = calUnit(2 , randomTwoSize , danSize);
		}else if(connectTwoGroupSize!=0){
			units = calUnit(2 , connectTwoGroupSize , danSize);
		}else if((foreTwoDirectSize1+foreTwoDirectSize2)>=2){
			//选二连直
			for(var i=0;i<foreTwoDirectSize1;i++){
	            for(var j=0;j<foreTwoDirectSize2;j++){
	                	if(currentContentObj.ForeTwoDirect1[i]!=currentContentObj.ForeTwoDirect2[j]){
	                		    units += 1;
	                	}
	            }
	        }
		}else if(randomThreeSize!=0){
			units = calUnit(3 , randomThreeSize , danSize);
		}else if(foreThreeGroupSize!=0){
			units = calUnit(3 , foreThreeGroupSize , danSize);
		}else if((foreThreeDirectSize1+foreThreeDirectSize2+foreThreeDirectSize3)>=3){
		        	for(var i=0;i<foreThreeDirectSize1;i++){
			            for(var j=0;j<foreThreeDirectSize2;j++){
			                for(var k=0;k<foreThreeDirectSize3;k++){
			                	if(currentContentObj.ForeThreeDirect1[i]!=currentContentObj.ForeThreeDirect2[j]&&
			                			currentContentObj.ForeThreeDirect1[i]!=	currentContentObj.ForeThreeDirect3[k]&&
			                			currentContentObj.ForeThreeDirect2[j]!=currentContentObj.ForeThreeDirect3[k]){
			                		    units += 1;
			                	}
			                }
			            }
			        }
		}else if(randomFourSize!=0){
			units = calUnit(4 , randomFourSize , danSize);
		}else if(randomFiveSize!=0){
			units = calUnit(5 , randomFiveSize , danSize);
		}else if(randomSixSize!=0){
			units = calUnit(6 , randomSixSize , danSize);
		}else if(randomSevenSize!=0){
			units = calUnit(7 , randomSevenSize , danSize);
		}else if(randomEightSize!=0){
			units = calUnit(8 , randomEightSize , danSize);
		}else{
			if(units!=0){
				window.alert("选择玩法出错");
				this.clearCurrentContent();
			}
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
		//判断是否为智能追号
		if("true"==document.getElementById('isCapacity').value) {
			document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr></table>";
		}
	};
	var numSplitWord=',';
	var areaSplitWord='-';
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';
		var normalOneSize = contentObj.NormalOne.length;
		var randomTwoSize = contentObj.RandomTwo.length;
		var connectTwoGroupSize = contentObj.ForeTwoGroup.length;
		var connectTwoDirectSize1 = contentObj.ForeTwoDirect1.length;
		var connectTwoDirectSize2 = contentObj.ForeTwoDirect2.length;
		var randomThreeSize = contentObj.RandomThree.length;
		var foreThreeGroupSize = contentObj.ForeThreeGroup.length;
		var foreThreeDirectSize1 = contentObj.ForeThreeDirect1.length;
		var foreThreeDirectSize2 = contentObj.ForeThreeDirect2.length;
		var foreThreeDirectSize3 = contentObj.ForeThreeDirect3.length;
		var randomFourSize = contentObj.RandomFour.length;
		var randomFiveSize = contentObj.RandomFive.length;
		var randomSixSize = contentObj.RandomSix.length;
		var randomSevenSize = contentObj.RandomSeven.length;
		var randomEightSize = contentObj.RandomEight.length;
		var danSize = contentObj.Dan.length;
		
		
		if (normalOneSize!=0) {
			text += '[前一任选]:';
			for ( var i = 0, ball, l = contentObj.NormalOne.length; i < l; i++) {
				ball = getBallStr(contentObj.NormalOne[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(randomTwoSize!=0){
			text += '[任选二]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomTwo.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomTwo[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(connectTwoGroupSize!=0){
			text += '[前二组选]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.ForeTwoGroup.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeTwoGroup[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if((connectTwoDirectSize1+connectTwoDirectSize2)>=2){
			//选二连直
			text += '[前二连直]:';
			for ( var i = 0, ball, l = contentObj.ForeTwoDirect1.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeTwoDirect1[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += areaSplitWord;
					value += areaSplitWord;
				}
			}
			
			for ( var i = 0, ball, l = contentObj.ForeTwoDirect2.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeTwoDirect2[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomThreeSize!=0){
			text += '[任选三]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomThree.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomThree[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(foreThreeGroupSize!=0){
			text += '[前三组选]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.ForeThreeGroup.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeThreeGroup[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if((foreThreeDirectSize1+foreThreeDirectSize2+foreThreeDirectSize3)>=3){
			text += '[前三直选]:';
			for ( var i = 0, ball, l = contentObj.ForeThreeDirect1.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeThreeDirect1[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += areaSplitWord;
					value += areaSplitWord;
				}
			}
			for ( var i = 0, ball, l = contentObj.ForeThreeDirect2.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeThreeDirect2[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += areaSplitWord;
					value += areaSplitWord;
				}
			}
			for ( var i = 0, ball, l = contentObj.ForeThreeDirect3.length; i < l; i++) {
				ball = getBallStr(contentObj.ForeThreeDirect3[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomFourSize!=0){
			text += '[任选四]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomFour.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomFour[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomFiveSize!=0){
			text += '[任选五]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomFive.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomFive[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomSixSize!=0){
			text += '[任选六]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomSix.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomSix[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomSevenSize!=0){
			text += '[任选七]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomSeven.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomSeven[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomEightSize!=0){
			text += '[任选八]:';
			for ( var i = 0, ball, l = contentObj.Dan.length; i < l; i++) {
				ball = getBallStr(contentObj.Dan[i]);

				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} else {
					text += '_';
					value += ';';
				}
			}
			for ( var i = 0, ball, l = contentObj.RandomEight.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomEight[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else{
			if(units!=0){
				window.alert("选择玩法出错");
				this.clearCurrentContent();
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
	var randomSort = function(){
		var randomNum = Math.random();
		if(randomNum > 0.5)
			return 1;
		else if(randomNum < 0.5)
			return -1;
		else
			return 0;
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
		//判断是否为智能追号
		if("true"==document.getElementById('isCapacity').value) {
			document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr></table>";
		}
	};
	window.on_select_li =function(li){
		
	};
	window.clearSelect = function(generateNum){
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0-parseInt(arr[0]));
	};   
	
	var checkDanRepeat = function(val) {
		var betType_input = document.getElementById("betType_input");
		var betType = betType_input.value;
		var normalArr = $('#'+betType+'ball_area_box a[_name="'+betType+'ball"]');
		for ( var i = 0; i < normalArr.length; i++) {
			if(normalArr[i].className == selectedClass&&val==parseInt(normalArr[i].innerHTML, 10)){
				return true;
			}
		}
		return false;
	}; 
	var checkNormalRepeat = function(val) {
		var danArr = $('#ball_area_box_dan a[_name="bet_ball"]');
		for ( var i = 0; i < danArr.length; i++) {
			if(danArr[i].className == selectedClass_blue&&val==parseInt(danArr[i].innerHTML, 10)){
				return true;
			}
		}
		return false;
	}; 
	$('#ball_area_box_dan a[_name="bet_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass_blue) {
			this.className = unSelectedClass;
			currentContentObj.Dan.erase(val);
		} else {
			this.className = selectedClass_blue;
			currentContentObj.Dan.push(val);
			if (checkDanRepeat(val)) {
	    		//window.alert("胆码和拖码选号重复");
	    		this.className = unSelectedClass;
	    		currentContentObj.Dan.erase(val);
	    		return;
	    	}
			var betType_lineLimit = document.getElementById("betType_lineLimit");
			betType_lineLimit = parseInt(betType_lineLimit.value, 10);
			if (currentContentObj.Dan.length > (betType_lineLimit-1)) {
	    		window.alert("设胆至多"+(betType_lineLimit-1)+"个");
	    		this.className = unSelectedClass;
	    		currentContentObj.Dan.erase(val);
	    		return;
	    	}
		}
		currentContentObj.Dan.sort(asc);
		calcCurrentUnits();
	});
	$('#NormalOneball_area_box a[_name="NormalOneball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.NormalOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.NormalOne.push(val);
		}
		currentContentObj.NormalOne.sort(asc);
		calcCurrentUnits();
	});
	$('#RandomTwoball_area_box a[_name="RandomTwoball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomTwo.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomTwo.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomTwo.erase(val);
				return;
			}
		}
		currentContentObj.RandomTwo.sort(asc);
		calcCurrentUnits();
	});
	
	$('#ForeTwoGroupball_area_box a[_name="ForeTwoGroupball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeTwoGroup.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeTwoGroup.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.ForeTwoGroup.erase(val);
				return;
			}
		}
		currentContentObj.ForeTwoGroup.sort(asc);
		calcCurrentUnits();
	});
	
	$('#ForeTwoDirectball_area_box_1 a[_name="ForeTwoDirectball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeTwoDirect1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeTwoDirect1.push(val);
		}
		currentContentObj.ForeTwoDirect1.sort(asc);
		calcCurrentUnits();
	});
	$('#ForeTwoDirectball_area_box_2 a[_name="ForeTwoDirectball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeTwoDirect2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeTwoDirect2.push(val);
		}
		currentContentObj.ForeTwoDirect2.sort(asc);
		calcCurrentUnits();
	});
	$('#RandomThreeball_area_box a[_name="RandomThreeball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomThree.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomThree.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomThree.erase(val);
				return;
			}
		}
		currentContentObj.RandomThree.sort(asc);
		calcCurrentUnits();
	});
	$('#ForeThreeGroupball_area_box a[_name="ForeThreeGroupball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeGroup.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeGroup.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.ForeThreeGroup.erase(val);
				return;
			}
		}
		currentContentObj.ForeThreeGroup.sort(asc);
		calcCurrentUnits();
	});
	
	$('#ForeThreeDirectball_area_box_1 a[_name="ForeThreeDirectball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect1.push(val);
		}
		currentContentObj.ForeThreeDirect1.sort(asc);
		calcCurrentUnits();
	});
	$('#ForeThreeDirectball_area_box_2 a[_name="ForeThreeDirectball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect2.push(val);
		}
		currentContentObj.ForeThreeDirect2.sort(asc);
		calcCurrentUnits();
	});
	$('#ForeThreeDirectball_area_box_3 a[_name="ForeThreeDirectball3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect3.push(val);
		}
		currentContentObj.ForeThreeDirect3.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	$('#RandomFourball_area_box a[_name="RandomFourball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomFour.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomFour.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomFour.erase(val);
				return;
			}
		}
		currentContentObj.RandomFour.sort(asc);
		calcCurrentUnits();
	});
	$('#RandomFiveball_area_box a[_name="RandomFiveball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomFive.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomFive.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomFive.erase(val);
				return;
			}
		}
		currentContentObj.RandomFive.sort(asc);
		calcCurrentUnits();
	});
    
	$('#RandomSixball_area_box a[_name="RandomSixball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomSix.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomSix.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomSix.erase(val);
				return;
			}
		}
		currentContentObj.RandomSix.sort(asc);
		calcCurrentUnits();
	});
	
	$('#RandomSevenball_area_box a[_name="RandomSevenball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomSeven.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomSeven.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomSeven.erase(val);
				return;
			}
		}
		currentContentObj.RandomSeven.sort(asc);
		calcCurrentUnits();
	});
	
	$('#RandomEightball_area_box a[_name="RandomEightball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomEight.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomEight.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomEight.erase(val);
				return;
			}
		}
		currentContentObj.RandomEight.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	
	
	var clearChooseBallArea =  function(){
		var all = $('#compound_select_content_div a[class="reb_ball"]');
		for ( var i = 0; i < all.length; i++) {
			all[i].className = unSelectedClass;
		}
	};
	window.chooseBallMethod=function(betType,oprType,line){
		if('NormalOne'==betType){
			//任1
			var ballArr=$('#NormalOneball_area_box a[_name="NormalOneball"]');
			chooseMethod(ballArr,oprType,currentContentObj.NormalOne,null);
		}else if('RandomTwo'==betType){
			//任2
			var ballArr=$('#RandomTwoball_area_box a[_name="RandomTwoball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomTwo,null);
		}else if('ForeTwoGroup'==betType){
			//组2
			var ballArr=$('#ForeTwoGroupball_area_box a[_name="ForeTwoGroupball"]');
			chooseMethod(ballArr,oprType,currentContentObj.ForeTwoGroup,null);
		}else if('ForeTwoDirect'==betType){
			//直2
			if(1==line){
				var ballArr=$('#ForeTwoDirectball_area_box_1 a[_name="ForeTwoDirectball1"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeTwoDirect1,null);
			}else if(2==line){
				var ballArr=$('#ForeTwoDirectball_area_box_2 a[_name="ForeTwoDirectball2"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeTwoDirect2,null);
			}
		}else if('RandomThree'==betType){
			//任3
			var ballArr=$('#RandomThreeball_area_box a[_name="RandomThreeball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomThree,null);
		}else if('ForeThreeGroup'==betType){
			//组3
			var ballArr=$('#ForeThreeGroupball_area_box a[_name="ForeThreeGroupball"]');
			chooseMethod(ballArr,oprType,currentContentObj.ForeThreeGroup,null);
		}else if('ForeThreeDirect'==betType){
			//直3
			if(1==line){
				var ballArr=$('#ForeThreeDirectball_area_box_1 a[_name="ForeThreeDirectball1"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect1,null);
			}else if(2==line){
				var ballArr=$('#ForeThreeDirectball_area_box_2 a[_name="ForeThreeDirectball2"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect2,null);
			}else if(3==line){
				var ballArr=$('#ForeThreeDirectball_area_box_3 a[_name="ForeThreeDirectball3"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect3,null);
			}
		}else if('RandomFour'==betType){
			//任四
			var ballArr=$('#RandomFourball_area_box a[_name="RandomFourball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomFour,null);
		}else if('RandomFive'==betType){
			//任五
			var ballArr=$('#RandomFiveball_area_box a[_name="RandomFiveball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomFive,null);
		}else if('RandomSix'==betType){
			//任六
			var ballArr=$('#RandomSixball_area_box a[_name="RandomSixball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomSix,null);
		}else if('RandomSeven'==betType){
			//任7
			var ballArr=$('#RandomSevenball_area_box a[_name="RandomSevenball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomSeven,null);
		}else if('RandomEight'==betType){
			//任8
			var ballArr=$('#RandomEightball_area_box a[_name="RandomEightball"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomEight,null);
		}else if('Dan'==betType){
			//任8
			var ballArr=$('#ball_area_box_dan a[_name="bet_ball"]');
			chooseMethod(ballArr,oprType,currentContentObj.Dan,selectedClass_blue);
		}
	};
	
	var chooseMethod = function (ballArr,oprType,obj,className){
		for(i=0;i<ballArr.length;i++){
			ballUnSelected(ballArr[i], obj);
		}
		for(i=0;i<ballArr.length;i++){
			var val = parseInt(ballArr[i].innerHTML, 10);
			if("all"==oprType){
				ballSelected(ballArr[i], obj,className);
			}else if("odd"==oprType){
				if(val%2==1){
					ballSelected(ballArr[i], obj,className);
				}
			}else if("even"==oprType){
				if(val%2==0){
					ballSelected(ballArr[i], obj,className);
				}
			}else if("big"==oprType){
				if(val>=7){
					ballSelected(ballArr[i], obj,className);
				}
			}else if("small"==oprType){
				if(val<7){
					ballSelected(ballArr[i], obj,className);
				}
			}
		}
	};
	
	var ballSelected=function(ball,content,className){
		var selectedClass_name = selectedClass;
		if(null!=className){
			selectedClass_name=className;
		}
		if(ball.className == selectedClass_name) return;
		var val = parseInt(ball.innerHTML, 10);
		for ( var i = 0, ball, l = content.length; i < l; i++) {
			if(content[i]==val){
				return;
			}
		}
		ball.className = selectedClass_name;
		content.push(val);
		if(className==selectedClass_blue){
			if (checkDanRepeat(val)) {
	    		//window.alert("胆码和拖码选号重复");
	    		ball.className = unSelectedClass;
	    		content.erase(val);
	    		return;
	    	}
			var betType_lineLimit = document.getElementById("betType_lineLimit");
			betType_lineLimit = parseInt(betType_lineLimit.value, 10);
			if (content.length > (betType_lineLimit-1)) {
	    		window.alert("设胆至多"+(betType_lineLimit-1)+"个");
	    		ball.className = unSelectedClass;
	    		content.erase(val);
	    		return;
	    	}
		}else{
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				ball.className = unSelectedClass;
				content.erase(val);
				return;
			}
		}
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
	var GENERAL_BALL_ARR = [];
	for ( var i = 0; i < 11; i++) {
		GENERAL_BALL_ARR[i] = i + 1;
	}
	
	
	window.NormalOneRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : copyBallArr.sort(randomSort).slice(0, 1).sort(asc), // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup : [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(1,randomUnits);
		}
	};
	
	
	window.RandomTwoRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 任选2
						ForeTwoGroup : [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits);
		}
	};
	
	window.ForeTwoGroupRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits);
		}
	};
	
	window.ForeTwoDirectRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			var num1;
			var num2;
			while (randomContent.length < randomUnits) {
			    num1=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    num2=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    var chooseAg=false;
			    for ( var i = 0; i < num1.length; i++) {
			    	for ( var j = 0; j < num2.length; j++) {
			    			chooseAg=num1[i]==num2[j];
					}
				}
			    while(chooseAg){
			    	num2=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    	 for ( var i = 0; i < num1.length; i++) {
					    	for ( var j = 0; j < num2.length; j++) {
					    			chooseAg=num1[i]==num2[j];
							}
					}
			    }
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : num1, // 前二直2_1
						ForeTwoDirect2 : num2, // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits);
		}
	};
	
	
	window.ForeThreeDirectRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			var num1;
			var num2;
			var num3;
			while (randomContent.length < randomUnits) {
			    num1=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    num2=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    num3=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    var chooseAg=false;
			    for ( var i = 0; i < num1.length; i++) {
			    	for ( var j = 0; j < num2.length; j++) {
			    		for ( var k = 0; k < num3.length; k++) {
			    			chooseAg=num1[i]==num2[j]||num2[j]==num3[k]||num1[i]==num3[k];
						}
					}
				}
			    while(chooseAg){
			    	num2=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    	num3=copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
			    	for ( var i = 0; i < num1.length; i++) {
					    	for ( var j = 0; j < num2.length; j++) {
					    		for ( var k = 0; k < num3.length; k++) {
					    			chooseAg=num1[i]==num2[j]||num2[j]==num3[k]||num1[i]==num3[k];
								}
							}
					}
			    }
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : num1, // 前3直_1
						ForeThreeDirect2 : num2, // 前3直_2
						ForeThreeDirect3 : num3, // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(3,randomUnits);
		}
	};
	
	
	window.RandomThreeRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(3,randomUnits);
		}
		
	};
	
	window.ForeThreeGroupRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(3,randomUnits);
		}
	};
	
	
	window.RandomFourRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup :  [],// 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : copyBallArr.sort(randomSort).slice(0, 4).sort(asc), // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(4,randomUnits);
		}
	};
	
	window.RandomFiveRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup :  [],// 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour :  [],// 任选4
						RandomFive : copyBallArr.sort(randomSort).slice(0, 5).sort(asc), //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(5,randomUnits);
		}
	};
	
	
	window.RandomSixRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup :  [],// 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix : copyBallArr.sort(randomSort).slice(0, 6).sort(asc), // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(6,randomUnits);
		}
	};
	
	
	window.RandomSevenRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
				if (randomUnits <= 0)
					return;
				var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
				var randomContent = [];
				while (randomContent.length < randomUnits) {
					var contentObj = {
							NormalOne : [], // 数投1
							RandomTwo : [], // 任选2
							ForeTwoGroup :  [], // 连组2
							ForeTwoDirect1 : [], // 前二直2_1
							ForeTwoDirect2 : [], // 前二直2_2
							RandomThree : [], // 任选3
							ForeThreeGroup :  [],// 前3租
							ForeThreeDirect1 :  [], // 前3直_1
							ForeThreeDirect2 : [], // 前3直_2
							ForeThreeDirect3 : [], // 前3直_3
							RandomFour :  [],// 任选4
							RandomFive :  [],//  任选5
							RandomSix :  [],// 任选6
							RandomSeven : copyBallArr.sort(randomSort).slice(0, 7).sort(asc), //  任选7
							RandomEight : [], // 任选8
							Dan : [], //  
							units : 1
					};
					randomContent.push(contentObj);
				}
				for ( var i = 0, l = randomContent.length; i < l; i++) {
					pushContent(randomContent[i]);
				}
		}else{
			singleRandomSelect(7,randomUnits);
		}
	};
	
	window.RandomEightRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne : [], // 数投1
						RandomTwo : [], // 任选2
						ForeTwoGroup :  [], // 连组2
						ForeTwoDirect1 : [], // 前二直2_1
						ForeTwoDirect2 : [], // 前二直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup :  [],// 前3租
						ForeThreeDirect1 :  [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix :  [],// 任选6
						RandomSeven : [], //  任选7
						RandomEight : copyBallArr.sort(randomSort).slice(0, 8).sort(asc), // 任选8
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(8,randomUnits);
		}
	};
	
	var isCompoundSalesMode = function (){
		var createForm = getCreateForm();
		var salesMode = createForm.elements["createForm.salesMode"];
		if(salesMode.value=="COMPOUND"){
			return true;
		}else if(salesMode.value="SINGLE"){
			return false;
		}
		return false;
	};
	
	var singleRandomSelect = function (betNum,randomUnits){
		var single_createForm_content = document.getElementById("single_createForm_content");
		var valueTemp=single_createForm_content.value;
		var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
		for ( var i = 0; i < randomUnits; i++) {
			var betArr=copyBallArr.sort(randomSort).slice(0, betNum).sort(asc);
			var bet="";
			for ( var j = 0; j < betArr.length; j++) {
					bet+=betArr[j]+" ";
			}
			valueTemp+=bet+"\r\n";
		}
		single_createForm_content.value=valueTemp;
		countTextAreaMoney();
	};
	
	window.singleClearAll = function() {
		var createForm = getCreateForm();
		var single_createForm_content = document.getElementById("single_createForm_content");
		single_createForm_content.value='';
		updateBetUnits(0);
		createForm.elements['createForm.content'].value = '';
		//判断是否为智能追号
		if("true"==document.getElementById('isCapacity').value) {
			document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr></table>";
		}
	};
	
	window.SalesModeSelect = function(type) {
		var createForm = getCreateForm();
		var salesMode = createForm.elements["createForm.salesMode"];
		var single_select_content_div = document.getElementById("single_select_content_div");
		var compound_select_content_div = document.getElementById("compound_select_content_div");
		var compound_select_content_div_dan = document.getElementById("compound_select_content_div_dan");
		var compound_txt = document.getElementById("compound_txt");
		
		if(0==type){
			salesMode.value="COMPOUND";
			single_select_content_div.style.display="none";
			compound_select_content_div_dan.style.display="none";
			compound_select_content_div.style.display="";
			clearCurrentContent();
			clearAll();
			singleClearAll();
			clearChooseBallArea();
			compound_txt.innerHTML="复式投注区";
		}else if(1==type){
			salesMode.value="SINGLE";
			compound_select_content_div.style.display="none";
			single_select_content_div.style.display="";
			clearCurrentContent();
			clearAll();
			singleClearAll();
			clearChooseBallArea();
		}else if(2==type){
			salesMode.value="COMPOUND";
			single_select_content_div.style.display="none";
			compound_select_content_div.style.display="";
			compound_select_content_div_dan.style.display="";
			clearCurrentContent();
			clearAll();
			singleClearAll();
			clearChooseBallArea();
			compound_txt.innerHTML="拖码投注区";
		}
		
	};
	
	
	/**
	 * 手工录入的注数、金额
	 */
	window.countTextAreaMoney = function(){
	    var createForm = getCreateForm();
	    var single_createForm_content = document.getElementById("single_createForm_content");
	    var content = createForm.elements["createForm.content"];
	    content.value = single_createForm_content.value;
	    if(''==content.value){
	    	 return false;
	    }
		var formActionUrl = createForm.action;
		var url=formActionUrl.replace(/![a-zA-Z]+./ig,'!calcSingleBetUnits.');
		var uploadOptions = {
			url: url,
			type : 'POST',
			cache : false,
			data : {
				ajax : 'true'
			},
			beforeSend : function(){
			},
			success : function(data, textStatus){
				var jsonObj = toJsonObject(data);
				if(jsonObj.success == true){
    			updateBetUnits(jsonObj.betUnits);
    		}else{
    			window.alert(jsonObj.msg);
    			updateBetUnits(0);
    		}
			},
			error : function(instance){
				updateBetUnits(0);
			},
			complete : function(){
			}
		};
		$(createForm).ajaxSubmit(uploadOptions);
	};
	
});
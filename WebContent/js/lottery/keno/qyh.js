$(function() {
	var selectedClass = 'red';
	var selectedClass_blue = 'blue';
	var unSelectedClass = '';
	var currentContentObj = {
		RandomOne : [], // 任选1
		RandomTwo : [], // 任选2
		RandomThree : [], // 任选3
		RandomFour : [], // 任选4
		RandomFive : [], //  任选5
		RandomSix : [], // 任选6
		RandomSeven : [], //  任选7
		RandomEight : [], // 任选8
		RandomNine : [], // 任选8
		RandomTen : [], // 任选8
		
		DirectOne : [],  //顺一
		
		DirectTwo1 : [], // 顺二直2_1
		DirectTwo2 : [], // 顺二直2_2
		
		DirectThree1 : [], // 顺3直_1
		DirectThree2 : [], // 顺3直_2
		DirectThree3 : [], // 顺3直_3

		RoundOne : [], // 围选1
		RoundTwo : [], // 围选2
		RoundThree : [], // 围选3
		RoundFour : [], // 围选4
		
		Dan : [], //  
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('a[_name="RandomOneball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomTwoball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomThreeball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomFourball"]').attr("class",unSelectedClass); 
		$('#RandomFiveball_area_box a[_name="RandomFiveball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomSixball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomSevenball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomEightball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomNineball"]').attr("class",unSelectedClass); 
		$('a[_name="RandomTenball"]').attr("class",unSelectedClass); 

		$('a[_name="DirectTwoball1"]').attr("class",unSelectedClass); 
		$('a[_name="DirectTwoball2"]').attr("class",unSelectedClass); 
		$('a[_name="DirectThreeball1"]').attr("class",unSelectedClass); 
		$('a[_name="DirectThreeball2"]').attr("class",unSelectedClass); 
		$('a[_name="DirectThreeball3"]').attr("class",unSelectedClass); 

		$('a[_name="RoundOneball"]').attr("class",unSelectedClass); 
		$('a[_name="RoundTwoball"]').attr("class",unSelectedClass); 
		$('a[_name="RoundThreeball"]').attr("class",unSelectedClass); 
		$('a[_name="RoundFourball"]').attr("class",unSelectedClass); 
		
		$('a[_name="bet_ball"]').attr("class",unSelectedClass); 
		
		currentContentObj.RandomOne.length = 0;
		currentContentObj.RandomTwo.length = 0;
		currentContentObj.RandomThree.length = 0;
		currentContentObj.RandomFour.length = 0;
		currentContentObj.RandomFive.length = 0;
		currentContentObj.RandomSix.length = 0;
		currentContentObj.RandomSeven.length = 0;
		currentContentObj.RandomEight.length = 0;
		currentContentObj.RandomNine.length = 0;
		currentContentObj.RandomTen.length = 0;
		
		currentContentObj.RoundOne.length = 0;
		currentContentObj.RoundTwo.length = 0;
		currentContentObj.RoundThree.length = 0;
		currentContentObj.RoundFour.length = 0;
		
		currentContentObj.DirectOne.length = 0;
		currentContentObj.DirectTwo1.length = 0;
		currentContentObj.DirectTwo2.length = 0;
		currentContentObj.DirectThree1.length = 0;
		currentContentObj.DirectThree2.length = 0;
		currentContentObj.DirectThree3.length = 0;
		
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
		var randomOneSize = currentContentObj.RandomOne.length;
		var randomTwoSize = currentContentObj.RandomTwo.length;
		var randomThreeSize = currentContentObj.RandomThree.length;
		var randomFourSize = currentContentObj.RandomFour.length;
		var randomFiveSize = currentContentObj.RandomFive.length;
		var randomSixSize = currentContentObj.RandomSix.length;
		var randomSevenSize = currentContentObj.RandomSeven.length;
		var randomEightSize = currentContentObj.RandomEight.length;
		var randomNineSize = currentContentObj.RandomNine.length;
		var randomTenSize = currentContentObj.RandomTen.length;
		
		var directOneSize = currentContentObj.DirectOne.length;
		var directTwoSize1 = currentContentObj.DirectTwo1.length;
		var directTwoSize2 = currentContentObj.DirectTwo2.length;
		var directThreeSize1 = currentContentObj.DirectThree1.length;
		var directThreeSize2 = currentContentObj.DirectThree2.length;
		var directThreeSize3 = currentContentObj.DirectThree3.length;

		var roundOneSize = currentContentObj.RoundOne.length;
		var roundTwoSize = currentContentObj.RoundTwo.length;
		var roundThreeSize = currentContentObj.RoundThree.length;
		var roundFourSize = currentContentObj.RoundFour.length;
		
		var danSize = currentContentObj.Dan.length;
		if (randomOneSize!=0) {
			units = randomOneSize;
		}else if(randomTwoSize!=0){
			units = calUnit(2 , randomTwoSize , danSize);
		}else if(randomThreeSize!=0){
			units = calUnit(3 , randomThreeSize , danSize);
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
		}else if(randomNineSize!=0){
			units = calUnit(9 , randomNineSize , danSize);
		}else if(randomTenSize!=0){
			units = calUnit(10 , randomTenSize , danSize);
		}else if(directOneSize!=0){
			units = directOneSize;
		}else if((directTwoSize1+directTwoSize2)>=2){
			//选二连直
			for(var i=0;i<directTwoSize1;i++){
	            for(var j=0;j<directTwoSize2;j++){
	                	if(currentContentObj.DirectTwo1[i]!=currentContentObj.DirectTwo2[j]){
	                		    units += 1;
	                	}
	            }
	        }
		}else if((directThreeSize1+directThreeSize2+directThreeSize3)>=3){
		        	for(var i=0;i<directThreeSize1;i++){
			            for(var j=0;j<directThreeSize2;j++){
			                for(var k=0;k<directThreeSize3;k++){
			                	if(currentContentObj.DirectThree1[i]!=currentContentObj.DirectThree2[j]&&
			                			currentContentObj.DirectThree1[i]!=	currentContentObj.DirectThree3[k]&&
			                			currentContentObj.DirectThree2[j]!=currentContentObj.DirectThree3[k]){
			                		    units += 1;
			                	}
			                }
			            }
			        }
		}else if (roundOneSize!=0) {
			units = roundOneSize;
		}else if(roundTwoSize!=0){
			units = calUnit(2 , roundTwoSize , danSize);
		}else if(roundThreeSize!=0){
			units = calUnit(3 , roundThreeSize , danSize);
		}else if(roundFourSize!=0){
			units = calUnit(4 , roundFourSize , danSize);
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
		var randomOneSize = contentObj.RandomOne.length;
		var randomTwoSize = contentObj.RandomTwo.length;
		var randomThreeSize = contentObj.RandomThree.length;
		var randomFourSize = contentObj.RandomFour.length;
		var randomFiveSize = contentObj.RandomFive.length;
		var randomSixSize = contentObj.RandomSix.length;
		var randomSevenSize = contentObj.RandomSeven.length;
		var randomEightSize = contentObj.RandomEight.length;
		var randomNineSize = contentObj.RandomNine.length;
		var randomTenSize = contentObj.RandomTen.length;
		
		var directOneSize = contentObj.DirectOne.length;
		var directTwoSize1 = contentObj.DirectTwo1.length;
		var directTwoSize2 = contentObj.DirectTwo2.length;
		var directThreeSize1 = contentObj.DirectThree1.length;
		var directThreeSize2 = contentObj.DirectThree2.length;
		var directThreeSize3 = contentObj.DirectThree3.length;

		var roundOneSize = contentObj.RoundOne.length;
		var roundTwoSize = contentObj.RoundTwo.length;
		var roundThreeSize = contentObj.RoundThree.length;
		var roundFourSize = contentObj.RoundFour.length;
		
		var danSize = contentObj.Dan.length;
		
		
		if (randomOneSize!=0) {
			text += '[任选一]:';
			for ( var i = 0, ball, l = contentObj.RandomOne.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomOne[i]);
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
		}else if(randomNineSize!=0){
			text += '[任选九]:';
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
			for ( var i = 0, ball, l = contentObj.RandomNine.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomNine[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(randomTenSize!=0){
			text += '[任选十]:';
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
			for ( var i = 0, ball, l = contentObj.RandomTen.length; i < l; i++) {
				ball = getBallStr(contentObj.RandomTen[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(directOneSize!=0){
			text += '[顺选一]:';
			for ( var i = 0, ball, l = contentObj.DirectOne.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectOne[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if((directTwoSize1+directTwoSize2)>=2){
			//选二连直
			text += '[顺选二]:';
			for ( var i = 0, ball, l = contentObj.DirectTwo1.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectTwo1[i]);
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
			
			for ( var i = 0, ball, l = contentObj.DirectTwo2.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectTwo2[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if((directThreeSize1+directThreeSize2+directThreeSize3)>=3){
			text += '[顺选三]:';
			for ( var i = 0, ball, l = contentObj.DirectThree1.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectThree1[i]);
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
			for ( var i = 0, ball, l = contentObj.DirectThree2.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectThree2[i]);
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
			for ( var i = 0, ball, l = contentObj.DirectThree3.length; i < l; i++) {
				ball = getBallStr(contentObj.DirectThree3[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if (roundOneSize!=0) {
			text += '[围选一]:';
			for ( var i = 0, ball, l = contentObj.RoundOne.length; i < l; i++) {
				ball = getBallStr(contentObj.RoundOne[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(roundTwoSize!=0){
			text += '[围选二]:';
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
			for ( var i = 0, ball, l = contentObj.RoundTwo.length; i < l; i++) {
				ball = getBallStr(contentObj.RoundTwo[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(roundThreeSize!=0){
			text += '[围选三]:';
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
			for ( var i = 0, ball, l = contentObj.RoundThree.length; i < l; i++) {
				ball = getBallStr(contentObj.RoundThree[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(roundFourSize!=0){
			text += '[围选四]:';
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
			for ( var i = 0, ball, l = contentObj.RoundFour.length; i < l; i++) {
				ball = getBallStr(contentObj.RoundFour[i]);
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
		var normalArr = $('a[_name="'+betType+'ball"]');
		for ( var i = 0; i < normalArr.length; i++) {
			if(normalArr[i].className == selectedClass&&val==parseInt(normalArr[i].innerHTML, 10)){
				return true;
			}
		}
		return false;
	}; 
	var checkNormalRepeat = function(val) {
		var danArr = $('a[_name="bet_ball"]');
		for ( var i = 0; i < danArr.length; i++) {
			if(danArr[i].className == selectedClass_blue&&val==parseInt(danArr[i].innerHTML, 10)){
				return true;
			}
		}
		return false;
	}; 
	$('a[_name="bet_ball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass_blue) {
			this.className = unSelectedClass;
			currentContentObj.Dan.erase(val);
		} else {
			this.className = selectedClass_blue;
			currentContentObj.Dan.push(val);
			if (checkDanRepeat(val)) {
	    		window.alert("胆码和拖码选号重复");
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
	$('a[_name="RandomOneball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomOne.push(val);
		}
		currentContentObj.RandomOne.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="RandomTwoball"]').bind("click", function(event) {
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
	$('a[_name="RandomThreeball"]').bind("click", function(event) {
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
	
	
	
	$('a[_name="RandomFourball"]').bind("click", function(event) {
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
	$('a[_name="RandomFiveball"]').bind("click", function(event) {
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
    
	$('a[_name="RandomSixball"]').bind("click", function(event) {
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
	
	$('a[_name="RandomSevenball"]').bind("click", function(event) {
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
	
	$('a[_name="RandomEightball"]').bind("click", function(event) {
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
	
	$('a[_name="RandomNineball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomNine.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomNine.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomNine.erase(val);
				return;
			}
		}
		currentContentObj.RandomNine.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="RandomTenball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RandomTen.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RandomTen.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RandomTen.erase(val);
				return;
			}
		}
		currentContentObj.RandomTen.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="DirectOneball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectOne.push(val);
			if (checkNormalRepeat(val)) {
				window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.DirectOne.erase(val);
				return;
			}
		}
		currentContentObj.DirectOne.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="DirectTwoball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwo1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectTwo1.push(val);
		}
		if (checkDirectRepeat(val,"DirectTwoball2",null)) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwo2.erase(val);
			return;
		}
		currentContentObj.DirectTwo1.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="DirectTwoball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwo2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectTwo2.push(val);
		}
		if (checkDirectRepeat(val,"DirectTwoball1",null)) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwo2.erase(val);
			return;
		}
		currentContentObj.DirectTwo2.sort(asc);
		calcCurrentUnits();
	});
	var checkDirectRepeat = function(val,name,name1) {
		var arr = $('a[_name="'+name+'"]');
		for ( var i = 0; i < arr.length; i++) {
			if (arr[i].className == selectedClass && val == parseInt(arr[i].innerHTML, 10)) {
				return true;
			}
		}
		var arr1 = $('a[_name="'+name1+'"]');
		if(null!=arr1){
			for ( var i = 0; i < arr1.length; i++) {
				if (arr1[i].className == selectedClass && val == parseInt(arr1[i].innerHTML, 10)) {
					return true;
				}
			}
		}
		return false;
	};
	$('a[_name="DirectThreeball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree1.push(val);
		}
		if (checkDirectRepeat(val,"DirectThreeball2","DirectThreeball3")) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree1.erase(val);
			return;
		}
		currentContentObj.DirectThree1.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="DirectThreeball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree2.push(val);
		}
		if (checkDirectRepeat(val,"DirectThreeball1","DirectThreeball3")) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree2.erase(val);
			return;
		}
		currentContentObj.DirectThree2.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="DirectThreeball3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree3.push(val);
		}
		if (checkDirectRepeat(val,"DirectThreeball1","DirectThreeball2")) {
			this.className = unSelectedClass;
			currentContentObj.DirectThreeball3.erase(val);
			return;
		}
		currentContentObj.DirectThree3.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="RoundOneball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RoundOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RoundOne.push(val);
		}
		currentContentObj.RoundOne.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="RoundTwoball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RoundTwo.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RoundTwo.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RoundTwo.erase(val);
				return;
			}
		}
		currentContentObj.RoundTwo.sort(asc);
		calcCurrentUnits();
	});
	$('a[_name="RoundThreeball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RoundThree.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RoundThree.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RoundThree.erase(val);
				return;
			}
		}
		currentContentObj.RoundThree.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	$('a[_name="RoundFourball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RoundFour.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RoundFour.push(val);
			if (checkNormalRepeat(val)) {
				//window.alert("胆码和拖码选号重复");
				this.className = unSelectedClass;
				currentContentObj.RoundFour.erase(val);
				return;
			}
		}
		currentContentObj.RoundFour.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	
	
	var clearChooseBallArea =  function(){
		var betType_input = document.getElementById("betType_input");
		var betType = betType_input.value;
		var all = $('a[_name="'+betType+'ball"]');
		//var all = $('a[class="reb_ball"]');
		for ( var i = 0; i < all.length; i++) {
			all[i].className = unSelectedClass;
		}
	};
	window.chooseBallMethod=function(betType,oprType,numType,line){
		if('RandomOne'==betType){
			//任1
			var ballArr=$('a[_name="RandomOneball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomOne,null);
		}else if('RandomTwo'==betType){
			//任2
			var ballArr=$('a[_name="RandomTwoball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomTwo,null);
		}else if('RandomThree'==betType){
			//任3
			var ballArr=$('a[_name="RandomThreeball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomThree,null);
		}else if('RandomFour'==betType){
			//任四
			var ballArr=$('a[_name="RandomFourball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomFour,null);
		}else if('RandomFive'==betType){
			//任五
			var ballArr=$('a[_name="RandomFiveball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomFive,null);
		}else if('RandomSix'==betType){
			//任六
			var ballArr=$('a[_name="RandomSixball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomSix,null);
		}else if('RandomSeven'==betType){
			//任7
			var ballArr=$('a[_name="RandomSevenball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomSeven,null);
		}else if('RandomEight'==betType){
			//任8
			var ballArr=$('a[_name="RandomEightball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomEight,null);
		}else if('RandomNine'==betType){
			//任9
			var ballArr=$('a[_name="RandomNineball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomNine,null);
		}else if('RandomTen'==betType){
			//任10
			var ballArr=$('a[_name="RandomTenball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RandomTen,null);
		}else if('DirectOne'==betType){
			//组2
			var ballArr=$('a[_name="DirectOneball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.DirectOne,null);
		}else if('DirectTwo'==betType){
			//直2
			if(1==line){
				var ballArr=$('a[_name="DirectTwoball1"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.DirectTwo1,null);
			}else if(2==line){
				var ballArr=$('a[_name="DirectTwoball2"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.DirectTwo2,null);
			}
		}else if('DirectThree'==betType){
			//直3
			if(1==line){
				var ballArr=$('a[_name="DirectThreeball1"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.DirectThree1,null);
			}else if(2==line){
				var ballArr=$('a[_name="DirectThreeball2"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.DirectThree2,null);
			}else if(3==line){
				var ballArr=$('a[_name="DirectThreeball3"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.DirectThree3,null);
			}
		}else if('RoundOne'==betType){
			//任1
			var ballArr=$('a[_name="RoundOneball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RoundOne,null);
		}else if('RoundTwo'==betType){
			//任2
			var ballArr=$('a[_name="RoundTwoball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RoundTwo,null);
		}else if('RoundThree'==betType){
			//任3
			var ballArr=$('a[_name="RoundThreeball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RoundThree,null);
		}else if('RoundFour'==betType){
			//任四
			var ballArr=$('a[_name="RoundFourball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.RoundFour,null);
		}else if('Dan'==betType){
			//任8
			var ballArr=$('a[_name="bet_ball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.Dan,selectedClass_blue);
		}
	};
	
	var chooseMethod = function (ballArr,oprType,obj,className){
		for(i=0;i<ballArr.length;i++){
			ballUnSelected(ballArr[i], obj);
		}
		//var ballMid=ballArr[Math.round(ballArr.length/2)].innerHTML;
		var ballMid=ballArr.length/2+1;
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
				if(val>ballMid){
					ballSelected(ballArr[i], obj,className);
				}
			}else if("small"==oprType){
				if(val<=ballMid){
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
	for ( var i = 0; i < 23; i++) {
		GENERAL_BALL_ARR[i] = i + 1;
	}
	
	
	window.RandomOneRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : copyBallArr.sort(randomSort).slice(0, 1).sort(asc), // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne : [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
						RandomOne : [], // 数投1
						RandomTwo : copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne : [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	
	
	window.RandomThreeRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : copyBallArr.sort(randomSort).slice(0, 4).sort(asc), // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour :  [],// 任选4
						RandomFive : copyBallArr.sort(randomSort).slice(0, 5).sort(asc), //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix : copyBallArr.sort(randomSort).slice(0, 6).sort(asc), // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
							RandomOne : [], // 数投1
							RandomTwo : [], // 任选2
							RandomThree : [], // 任选3
							RandomFour :  [],// 任选4
							RandomFive :  [],//  任选5
							RandomSix :  [],// 任选6
							RandomSeven : copyBallArr.sort(randomSort).slice(0, 7).sort(asc), //  任选7
							RandomEight : [], // 任选8
							RandomNine : [], // 任选8
							RandomTen : [], // 任选8
							
							DirectOne :  [],  //顺一
							DirectTwo1 : [], // 顺二直2_1
							DirectTwo2 : [], // 顺二直2_2

							DirectThree1 : [], // 前3直_1
							DirectThree2 : [], // 前3直_2
							DirectThree3 : [], // 前3直_3

							RoundOne : [], // 围选1
							RoundTwo : [], // 围选2
							RoundThree : [], // 围选3
							RoundFour : [], // 围选4
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix :  [],// 任选6
						RandomSeven : [], //  任选7
						RandomEight : copyBallArr.sort(randomSort).slice(0, 8).sort(asc), // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	window.RandomNineRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix :  [],// 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : copyBallArr.sort(randomSort).slice(0, 9).sort(asc), // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(9,randomUnits);
		}
	};
	window.RandomTenRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour :  [],// 任选4
						RandomFive :  [],//  任选5
						RandomSix :  [],// 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : copyBallArr.sort(randomSort).slice(0, 10).sort(asc), // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
						Dan : [], //  
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(10,randomUnits);
		}
	};
	
	window.DirectOneRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  copyBallArr.sort(randomSort).slice(0, 1).sort(asc),  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	
	window.DirectTwoRandomSelect = function(randomUnits) {
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						DirectOne :  [], // 连组2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : num1, // 顺二直2_1
						DirectTwo2 : num2, // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	
	
	window.DirectThreeRandomSelect = function(randomUnits) {
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
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : num1, // 前3直_1
						DirectThree2 : num2, // 前3直_2
						DirectThree3 : num3, // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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

	window.RoundOneRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne : [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : copyBallArr.sort(randomSort).slice(0, 1).sort(asc), // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	
	
	window.RoundTwoRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne : [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2
						
						DirectThree1 : [], // 顺3直_1
						DirectThree2 : [], // 顺3直_2
						DirectThree3 : [], // 顺3直_3

						RoundOne : [], // 围选1
						RoundTwo : copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 围选2
						RoundThree : [], // 围选3
						RoundFour : [], // 围选4
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
	
	
	window.RoundThreeRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree :copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 围选3
						RoundFour : [], // 围选4
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
	
	
	window.RoundFourRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						RandomOne : [], // 数投1
						RandomTwo : [], // 任选2
						RandomThree : [], // 任选3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
						RandomSix : [], // 任选6
						RandomSeven : [], //  任选7
						RandomEight : [], // 任选8
						RandomNine : [], // 任选8
						RandomTen : [], // 任选8
						
						DirectOne :  [],  //顺一
						DirectTwo1 : [], // 顺二直2_1
						DirectTwo2 : [], // 顺二直2_2

						DirectThree1 : [], // 前3直_1
						DirectThree2 : [], // 前3直_2
						DirectThree3 : [], // 前3直_3

						RoundOne : [], // 围选1
						RoundTwo : [], // 围选2
						RoundThree : [], // 围选3
						RoundFour : copyBallArr.sort(randomSort).slice(0, 4).sort(asc), // 围选4
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
			$("#span_bet_memo").show();
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
			$("#span_bet_memo").hide();
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
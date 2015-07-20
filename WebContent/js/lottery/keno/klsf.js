$(function() {
	var selectedClass = 'red';
	var selectedClass_blue = 'blue';
	var unSelectedClass = '';
	
	var currentContentObj = {
		NormalOne : [], // 数投1
		RedOne : [], // 红投1
		RandomTwo : [], // 任选2
		ConnectTwoGroup : [], // 连组2
		ConnectTwoDirect1 : [], // 连直2_1
		ConnectTwoDirect2 : [], // 连直2_2
		RandomThree : [], // 任选3
		ForeThreeGroup : [], // 前3租
		ForeThreeDirect1 : [], // 前3直_1
		ForeThreeDirect2 : [], // 前3直_2
		ForeThreeDirect3 : [], // 前3直_3
		RandomFour : [], // 任选4
		RandomFive : [], //  任选5
		Dan : [], //  
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('a[_name="bet_ball"]').attr("class",unSelectedClass); 

		$('#NormalOneball_area_box a[_name="NormalOneball"]').attr("class",unSelectedClass); 
		$('#RedOneball_area_box a[_name="RedOneball"]').attr("class",unSelectedClass); 
		$('#RandomTwoball_area_box a[_name="RandomTwoball"]').attr("class",unSelectedClass); 
		
		$('#ConnectTwoGroupball_area_box a[_name="ConnectTwoGroupball"]').attr("class",unSelectedClass); 
		
		$('a[_name="ConnectTwoDirectball1"]').attr("class",unSelectedClass); 
		$('a[_name="ConnectTwoDirectball2"]').attr("class",unSelectedClass); 

		$('#RandomThreeball_area_box a[_name="RandomThreeball"]').attr("class",unSelectedClass); 
		
		$('#ForeThreeGroupball_area_box a[_name="ForeThreeGroupball"]').attr("class",unSelectedClass); 
		
		$('a[_name="ForeThreeDirectball1"]').attr("class",unSelectedClass); 
		$('a[_name="ForeThreeDirectball2"]').attr("class",unSelectedClass); 
		$('a[_name="ForeThreeDirectball3"]').attr("class",unSelectedClass); 

	 	$('#RandomFourball_area_box a[_name="RandomFourball"]').attr("class",unSelectedClass); 
		$('#RandomFiveball_area_box a[_name="RandomFiveball"]').attr("class",unSelectedClass);
		
		currentContentObj.NormalOne.length = 0;
		currentContentObj.RedOne.length = 0;
		currentContentObj.RandomTwo.length = 0;
		currentContentObj.ConnectTwoGroup.length = 0;
		currentContentObj.ConnectTwoDirect1.length = 0;
		currentContentObj.ConnectTwoDirect2.length = 0;
		currentContentObj.RandomThree.length = 0;
		currentContentObj.ForeThreeGroup.length = 0;
		currentContentObj.ForeThreeDirect1.length = 0;
		currentContentObj.ForeThreeDirect2.length = 0;
		currentContentObj.ForeThreeDirect3.length = 0;
		currentContentObj.RandomFour.length = 0;
		currentContentObj.RandomFive.length = 0;
		currentContentObj.Dan.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
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
    }
	
	
	var calcCurrentUnits = function() {
		var units = 0;
		var normalOneSize = currentContentObj.NormalOne.length;
		var redOneSize = currentContentObj.RedOne.length;
		var randomTwoSize = currentContentObj.RandomTwo.length;
		var connectTwoGroupSize = currentContentObj.ConnectTwoGroup.length;
		var connectTwoDirectSize1 = currentContentObj.ConnectTwoDirect1.length;
		var connectTwoDirectSize2 = currentContentObj.ConnectTwoDirect2.length;
		var randomThreeSize = currentContentObj.RandomThree.length;
		var foreThreeGroupSize = currentContentObj.ForeThreeGroup.length;
		var foreThreeDirectSize1 = currentContentObj.ForeThreeDirect1.length;
		var foreThreeDirectSize2 = currentContentObj.ForeThreeDirect2.length;
		var foreThreeDirectSize3 = currentContentObj.ForeThreeDirect3.length;
		var randomFourSize = currentContentObj.RandomFour.length;
		var randomFiveSize = currentContentObj.RandomFive.length;
		var danSize = currentContentObj.Dan.length;
		
		if (normalOneSize!=0) {
			units = normalOneSize;
		}else if(redOneSize!=0){
			units = redOneSize;
		}else if(randomTwoSize!=0){
			units = calUnit(2 , randomTwoSize , danSize);
		}else if(connectTwoGroupSize!=0){
			units = calUnit(2 , connectTwoGroupSize , danSize);
		}else if((connectTwoDirectSize1+connectTwoDirectSize2)>=2){
			//选二连直
			var repeatCount=0
			for ( var i = 0; i < currentContentObj.ConnectTwoDirect1.length; i++) {
				for ( var j = 0; j < currentContentObj.ConnectTwoDirect2.length; j++) {
					if(currentContentObj.ConnectTwoDirect1[i]==currentContentObj.ConnectTwoDirect2[j]){
						repeatCount++;
					}
				}
			}
			units= connectTwoDirectSize1 * connectTwoDirectSize2 - repeatCount;
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
	
	
	window.chooseBallMethod=function(betType,oprType,numType,line){
		if('NormalOne'==betType){
			//任1
			var ballArr=$('a[_name="NormalOneball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.NormalOne,null);
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
		} else if('ConnectTwoGroup'==betType){
			var ballArr=$('a[_name="ConnectTwoGroupball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.ConnectTwoGroup,null);
		}else if('ForeThreeGroup'==betType){
			var ballArr=$('a[_name="ForeThreeGroupball"][_line="'+numType+'"]');
			chooseMethod(ballArr,oprType,currentContentObj.ForeThreeGroup,null);
		} 
		else if('ConnectTwoDirect'==betType){
			if(1==numType){
				var ballArr=$('a[_name="ConnectTwoDirectball1"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.ConnectTwoDirect1,null);
			}
			if(2==numType){
				var ballArr=$('a[_name="ConnectTwoDirectball2"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.ConnectTwoDirect2,null);
			}
		} 
		else if('ForeThreeDirect'==betType){
			
			if(1==numType){
				var ballArr=$('a[_name="ForeThreeDirectball1"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect1,null);
			}
			if(2==numType){
				var ballArr=$('a[_name="ForeThreeDirectball2"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect2,null);
			}
			if(3==numType){
				var ballArr=$('a[_name="ForeThreeDirectball3"][_line="'+numType+'"]');
				chooseMethod(ballArr,oprType,currentContentObj.ForeThreeDirect3,null);
			}
		} 

	};
	
	
	var getBallStr = function(val) {
		if (parseInt(val) < 10)
			return '0' + parseInt(val);
		else
			return '' + parseInt(val);
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
		var normalOneSize = contentObj.NormalOne.length;
		var redOneSize = contentObj.RedOne.length;
		var randomTwoSize = contentObj.RandomTwo.length;
		var connectTwoGroupSize = contentObj.ConnectTwoGroup.length;
		var connectTwoDirectSize1 = contentObj.ConnectTwoDirect1.length;
		var connectTwoDirectSize2 = contentObj.ConnectTwoDirect2.length;
		var randomThreeSize = contentObj.RandomThree.length;
		var foreThreeGroupSize = contentObj.ForeThreeGroup.length;
		var foreThreeDirectSize1 = contentObj.ForeThreeDirect1.length;
		var foreThreeDirectSize2 = contentObj.ForeThreeDirect2.length;
		var foreThreeDirectSize3 = contentObj.ForeThreeDirect3.length;
		var randomFourSize = contentObj.RandomFour.length;
		var randomFiveSize = contentObj.RandomFive.length;
		var danSize = contentObj.Dan.length;
		
		
		if (normalOneSize!=0) {
			text += '[数投一]:';
			for ( var i = 0, ball, l = contentObj.NormalOne.length; i < l; i++) {
				ball = getBallStr(contentObj.NormalOne[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(redOneSize!=0){
			text += '[红投一]:';
			for ( var i = 0, ball, l = contentObj.RedOne.length; i < l; i++) {
				ball = getBallStr(contentObj.RedOne[i]);
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
			text += '[连二组选]:';
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
			for ( var i = 0, ball, l = contentObj.ConnectTwoGroup.length; i < l; i++) {
				ball = getBallStr(contentObj.ConnectTwoGroup[i]);
				text += ball;
				value += ball;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if((connectTwoDirectSize1+connectTwoDirectSize2)>=2){
			for ( var i = 0; i < currentContentObj.ConnectTwoDirect1.length; i++) {
				for ( var j = 0; j < currentContentObj.ConnectTwoDirect2.length; j++) {
					if(currentContentObj.ConnectTwoDirect1[i]==currentContentObj.ConnectTwoDirect2[j]){
						alert("该玩法投注不能有重复号码，请重新选择");
						return false;
					}
				}
			}
			
			//选二连直
			text += '[选二连直]:';
			for ( var i = 0, ball, l = contentObj.ConnectTwoDirect1.length; i < l; i++) {
				ball = getBallStr(contentObj.ConnectTwoDirect1[i]);
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
			
			for ( var i = 0, ball, l = contentObj.ConnectTwoDirect2.length; i < l; i++) {
				ball = getBallStr(contentObj.ConnectTwoDirect2[i]);
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
			for(var i=0;i<foreThreeDirectSize1;i++){
	            for(var j=0;j<foreThreeDirectSize2;j++){
	                for(var k=0;k<foreThreeDirectSize3;k++){
	                	if(contentObj.ForeThreeDirect1[i]==contentObj.ForeThreeDirect2[j]||
	                	contentObj.ForeThreeDirect1[i]==contentObj.ForeThreeDirect3[k]||
	                	contentObj.ForeThreeDirect2[j]==contentObj.ForeThreeDirect3[k]){
	                		alert("该玩法投注不能有重复号码，请重新选择");
							return false;
	                	}
	                }
	            }
			}
			
			
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
		
		if(pushContent(currentContentObj)!=false) {
		clearCurrentContent();
		clearChooseBallArea();
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
	var clearChooseBallArea =  function(){
		var betType_input = document.getElementById("betType_input");
		var betType = betType_input.value;
		var all = $('a[_name="'+betType+'ball"]');
		//var all = $('a[class="reb_ball"]');
		for ( var i = 0; i < all.length; i++) {
			all[i].className = unSelectedClass;
		}
	};
	
	
	var GENERAL_BALL_ARR = [];
	for ( var i = 0; i < 20; i++) {
		GENERAL_BALL_ARR[i] = i + 1;
	}
	var NormalOne_BALL_ARR = []; 
	for ( var i = 0; i <= 17; i++) {
		NormalOne_BALL_ARR[i] = i + 1;
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
	
	window.NormalOneRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = NormalOne_BALL_ARR.slice(0, NormalOne_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne :  copyBallArr.sort(randomSort).slice(0,1).sort(asc), // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
	
	window.ConnectTwoGroupRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : copyBallArr.sort(randomSort).slice(0, 2).sort(asc), // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
	
	window.ConnectTwoDirectRandomSelect = function(randomUnits) {
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : num1, // 连直2_1
						ConnectTwoDirect2 : num2, // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : copyBallArr.sort(randomSort).slice(0, 3).sort(asc), // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
			    num2=copyBallArr.sort(randomSort).slice(1, 2).sort(asc);
			    num3=copyBallArr.sort(randomSort).slice(2, 3).sort(asc);
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
			    	num3=copyBallArr.sort(randomSort).slice(1, 2).sort(asc);
			    	for ( var i = 0; i < num1.length; i++) {
					    	for ( var j = 0; j < num2.length; j++) {
					    		for ( var k = 0; k < num3.length; k++) {
					    			chooseAg=num1[i]==num2[j]||num2[j]==num3[k]||num1[i]==num3[k];
								}
							}
					}
			    }
 				var contentObj = {
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : num1, // 前3直_1
						ForeThreeDirect2 : num2, // 前3直_2
						ForeThreeDirect3 : num3, // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : [], //  任选5
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : copyBallArr.sort(randomSort).slice(0, 4).sort(asc), // 任选4
						RandomFive : [], //  任选5
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
						NormalOne :  [], // 数投1
						RedOne : [], // 红投1
						RandomTwo : [], // 任选2
						ConnectTwoGroup : [], // 连组2
						ConnectTwoDirect1 : [], // 连直2_1
						ConnectTwoDirect2 : [], // 连直2_2
						RandomThree : [], // 任选3
						ForeThreeGroup : [], // 前3租
						ForeThreeDirect1 : [], // 前3直_1
						ForeThreeDirect2 : [], // 前3直_2
						ForeThreeDirect3 : [], // 前3直_3
						RandomFour : [], // 任选4
						RandomFive : copyBallArr.sort(randomSort).slice(0, 5).sort(asc), //  任选5
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
	 
	
	var getCurrentContentObjBy = function (betType){
		if('NormalOne'==betType){
			return currentContentObj.NormalOne;
		}else if('RedOne'==betType){
			return currentContentObj.RedOne;
		}else if('RandomTwo'==betType){
			return currentContentObj.RandomTwo;
		}else if('ConnectTwoGroup'==betType){
			return currentContentObj.ConnectTwoGroup;
		}else if('RandomThree'==betType){
			return currentContentObj.RandomThree;
		}else if('ForeThreeGroup'==betType){
			return currentContentObj.ForeThreeGroup;
		}else if('RandomFour'==betType){
			return currentContentObj.RandomFour;
		}else if('RandomFive'==betType){
			return currentContentObj.RandomFive;
		}
		
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
	
	$('a[_name="NormalOneball"]').bind("click", function(event) {
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
	
	$('a[_name="RedOneball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RedOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RedOne.push(val);
		}
		currentContentObj.RedOne.sort(asc);
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
				this.className = unSelectedClass;
				currentContentObj.RandomTwo.erase(val);
				return;
			}
		}
		currentContentObj.RandomTwo.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="ConnectTwoGroupball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ConnectTwoGroup.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ConnectTwoGroup.push(val);
			if (checkNormalRepeat(val)) {
				this.className = unSelectedClass;
				currentContentObj.ConnectTwoGroup.erase(val);
				return;
			}
		}
		currentContentObj.ConnectTwoGroup.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="ConnectTwoDirectball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ConnectTwoDirect1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ConnectTwoDirect1.push(val);
			for ( var j = 0; j < currentContentObj.ConnectTwoDirect2.length; j++) {
				if(val==currentContentObj.ConnectTwoDirect2[j]){
					this.className = unSelectedClass;
					currentContentObj.ConnectTwoDirect1.erase(val);
					return;
 				}
			}
		}
		currentContentObj.ConnectTwoDirect1.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="ConnectTwoDirectball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ConnectTwoDirect2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ConnectTwoDirect2.push(val);
			for ( var j = 0; j < currentContentObj.ConnectTwoDirect1.length; j++) {
				if(val==currentContentObj.ConnectTwoDirect1[j]){
					this.className = unSelectedClass;
					currentContentObj.ConnectTwoDirect2.erase(val);
					return;
 				}
			}
		}
		currentContentObj.ConnectTwoDirect2.sort(asc);
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
				this.className = unSelectedClass;
				currentContentObj.RandomFive.erase(val);
				return;
			}
		}
		currentContentObj.RandomFive.sort(asc);
		calcCurrentUnits();
	});
	 
	$('a[_name="ForeThreeGroupball"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeGroup.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeGroup.push(val);
		}
		currentContentObj.ForeThreeGroup.sort(asc);
		calcCurrentUnits();
	});
	
	$('a[_name="ForeThreeDirectball1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect1.push(val);
			for ( var j = 0; j < currentContentObj.ForeThreeDirect2.length; j++) {
				if(val==currentContentObj.ForeThreeDirect2[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect1.erase(val);
					return;
 				}
			}
			
			for ( var j = 0; j < currentContentObj.ForeThreeDirect3.length; j++) {
				if(val==currentContentObj.ForeThreeDirect3[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect1.erase(val);
					return;
 				}
			}
			
		}
		currentContentObj.ForeThreeDirect1.sort(asc);
		calcCurrentUnits();
	});
	

	$('a[_name="ForeThreeDirectball2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect2.push(val);
			for ( var j = 0; j < currentContentObj.ForeThreeDirect1.length; j++) {
				if(val==currentContentObj.ForeThreeDirect1[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect2.erase(val);
					return;
 				}
			}
			
			for ( var j = 0; j < currentContentObj.ForeThreeDirect3.length; j++) {
				if(val==currentContentObj.ForeThreeDirect3[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect2.erase(val);
					return;
 				}
			}
			
		}
		currentContentObj.ForeThreeDirect2.sort(asc);
		calcCurrentUnits();
	});
	

	$('a[_name="ForeThreeDirectball3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ForeThreeDirect3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ForeThreeDirect3.push(val);
			for ( var j = 0; j < currentContentObj.ForeThreeDirect1.length; j++) {
				if(val==currentContentObj.ForeThreeDirect1[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect3.erase(val);
					return;
 				}
			}
			
			for ( var j = 0; j < currentContentObj.ForeThreeDirect2.length; j++) {
				if(val==currentContentObj.ForeThreeDirect2[j]){
					this.className = unSelectedClass;
					currentContentObj.ForeThreeDirect3.erase(val);
					return;
 				}
			}
		}
		currentContentObj.ForeThreeDirect3.sort(asc);
		calcCurrentUnits();
	});
 
	
	
	var chooseMethod = function (ballArr,oprType,obj){
		for(i=0;i<ballArr.length;i++){
			ballUnSelected(ballArr[i], obj);
		}
		var star = parseInt(ballArr[0].innerHTML, 10);
		var end = parseInt(ballArr[ballArr.length-1].innerHTML, 10);
		var ban=((end-star)/2)+star;
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
				if(val>ban){
					ballSelected(ballArr[i], obj);
				}
			}else if("small"==oprType){
				if(val<=ban){
					ballSelected(ballArr[i], obj);
				}
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
				var code;
				if (betArr[j] < 10) {
					code= '0' +betArr[j];
				} else { 
					code = '' + betArr[j];
				}
				bet+=code+" ";
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
$(function() {
	var selectedClass = 'red';
	var unSelectedClass = '';
	var UNITS_DIRECT_SUM = [
	                        1, //0
	                        3, //1
	                        6, //2
	                        10, //3
	                        15, //4
	                        21, //5
	                        28, //6
	                        36, //7
	                        45, //8
	                        55, //9
	                        63, //10
	                        69, //11
	                        73, //12
	                        75, //13
	                        75, //14
	                        73, //15
	                        69, //16
	                        63, //17
	                        55, //18
	                        45, //19
	                        36, //20
	                        28, //21
	                        21, //22
	                        15, //23
	                        10, //24
	                        6, //25
	                        3, //26
	                        1   //27
	        			   ];
	var UNITS_GROUP_SUM = [
	                         0, //   0
					        1, //   1
					        2, //   2
					        2, //   3
					        4, //   4
					        5, //   5
					        6, //   6
					        8, //   7
					        10, //   8
					        11, //   9
					        13, //  10
					        14, //  11
					        14, //  12
					        15, //  13
					        15, //  14
					        14, //  15
					        14, //  16
					        13, //  17
					        11, //  18
					        10, //  19
					        8, //  20
					        6, //  21
					        5, //  22
					        4, //  23
					        2, //  24
					        2, //  25
					        1   //  26
	        			  ];
	///2010 .12月更新
	var UNITS_P3_KUADU = [
		10, // 0
		54, // 1 
		96, // 2 
		126, // 3
		144, // 4
		150, // 5 
		144, // 6
		126, // 7
		96, // 8 
		54, // 9 
    ];
	var UNITS_P3_G3_KUADU =[ 
		0, // 0
		18, // 1 
		16, // 2 
		14, // 3
		12, // 4
		10, // 5 
		8, // 6
		6, // 7
		4, // 8 
		2, // 9 
		];
	var UNITS_P3_G6_KUADU = [ 
		0, // 0
		0, // 1 
		8, // 2 
		14, // 3
		18, // 4
		20, // 5 
		20, // 6
		18, // 7
		14, // 8 
		8, // 9 
	];
	///2010 .12月更新
	var currentContentObj = {
		P5Direct1: [], // 直选
		P5Direct2: [], // 直选
		P5Direct3: [], // 直选
		P5Direct4: [], // 直选
		P5Direct5: [], // 直选
		P3Direct1 : [], // 直选
		P3Direct2 : [], // 直选
		P3Direct3 : [], // 直选
		Group3 : [], // 组3
		Group6 : [], // 组6
		DirectSum : [], // 直选和值
		GroupSum : [], // 直选和值
		///2010 .12月更新
		BaoChuan : [],
		P3DirectKuadu : [],
		P3Group3Kuadu : [],
		P3Group6Kuadu : [],
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('#area_box a[_name="P5Direct1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P5Direct2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P5Direct3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P5Direct4"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P5Direct5"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="P3Direct1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P3Direct2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P3Direct3"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="Group3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="Group6"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectSum"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="GroupSum"]').attr("class",unSelectedClass);
		///2010 12
		$('#area_box a[_name="BaoChuan"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P3DirectKuadu"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P3Group3Kuadu"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="P3Group6Kuadu"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="units"]').attr("class",unSelectedClass); 
		//
		currentContentObj.P5Direct1.length = 0;
		currentContentObj.P5Direct2.length = 0;
		currentContentObj.P5Direct3.length = 0;
		currentContentObj.P5Direct4.length = 0;
		currentContentObj.P5Direct5.length = 0;
		currentContentObj.P3Direct1.length = 0;
		currentContentObj.P3Direct2.length = 0;
		currentContentObj.P3Direct3.length = 0;
		currentContentObj.Group3.length = 0;
		currentContentObj.Group6.length = 0;
		currentContentObj.DirectSum.length = 0;
		currentContentObj.GroupSum.length = 0;
		///2010 12
		currentContentObj.BaoChuan.length = 0;
		currentContentObj.P3DirectKuadu.length = 0;
		currentContentObj.P3Group3Kuadu.length = 0;
		currentContentObj.P3Group6Kuadu.length = 0;
		//
		currentContentObj.units = 0;
		updateCurrentMsg();
	};
    window.clearCurrentSelect=function(){
    	clearCurrentContent();
    };
	var calcCurrentUnits = function() {
		var units = 0;
		var p5Direct1Size = currentContentObj.P5Direct1.length;
		var p5Direct2Size = currentContentObj.P5Direct2.length;
		var p5Direct3Size = currentContentObj.P5Direct3.length;
		var p5Direct4Size = currentContentObj.P5Direct4.length;
		var p5Direct5Size = currentContentObj.P5Direct5.length;
		
		var p3Direct1Size = currentContentObj.P3Direct1.length;
		var p3Direct2Size = currentContentObj.P3Direct2.length;
		var p3Direct3Size = currentContentObj.P3Direct3.length;
		
		
		var group3Size = currentContentObj.Group3.length;
		var group6Size = currentContentObj.Group6.length;
		var directSumSize = currentContentObj.DirectSum.length;
		var groupSumSize = currentContentObj.GroupSum.length;
	    /////2010 12
		var baoChuanSize = currentContentObj.BaoChuan.length;
		var p3DirectSize = currentContentObj.P3DirectKuadu.length;
		var p3Group3KuaduSize = currentContentObj.P3Group3Kuadu.length;
		var p3Group6KuaduSize = currentContentObj.P3Group6Kuadu.length;
		if (p5Direct1Size!=0&&p5Direct2Size!=0&&p5Direct3Size!=0&&p5Direct4Size!=0&&p5Direct5Size!=0) {
			/////选的是排五直选玩法
			units = p5Direct1Size*p5Direct2Size*p5Direct3Size*p5Direct4Size*p5Direct5Size;
		}else if (p3Direct1Size!=0&&p3Direct2Size!=0&&p3Direct3Size!=0) {
			/////选的是排三直选玩法
			units = p3Direct1Size*p3Direct2Size*p3Direct3Size;
		}else if(group3Size>=2){
			/////选的是组3玩法
			units = group3Size*(group3Size-1);
		}else if(group6Size>=3){
		    /////选的是组6玩法
			units = comp(3 , group6Size);
		}else if(directSumSize!=0){
		    /////选的是直选和值玩法
			for ( var i = 0; i < currentContentObj.DirectSum.length; i++) {
				units += UNITS_DIRECT_SUM[parseInt(currentContentObj.DirectSum[i])];
			}
		}else if(groupSumSize!=0){
		    /////选的是直选和值玩法
			for ( var i = 0; i < currentContentObj.GroupSum.length; i++) {
				units += UNITS_GROUP_SUM[parseInt(currentContentObj.GroupSum[i])];
			}
		}
		
		/////2010 12
		else if(baoChuanSize!=0){
		    /////选的是包串
			units = comp(1, baoChuanSize)*comp(1, baoChuanSize)*comp(1, baoChuanSize);
		}else if(p3DirectSize!=0){
		    /////选的直选跨度
			for ( var i = 0; i < currentContentObj.P3DirectKuadu.length; i++) {
				units += UNITS_P3_KUADU[parseInt(currentContentObj.P3DirectKuadu[i])];
			}
		}else if(p3Group3KuaduSize!=0){
		    /////选的组选3跨度
			for ( var i = 0; i < currentContentObj.P3Group3Kuadu.length; i++) {
				units += UNITS_P3_G3_KUADU[parseInt(currentContentObj.P3Group3Kuadu[i])];
			}
		}else if(p3Group6KuaduSize!=0){
		    /////选的组选6跨度
			for ( var i = 0; i < currentContentObj.P3Group6Kuadu.length; i++) {
				units += UNITS_P3_G6_KUADU[parseInt(currentContentObj.P3Group6Kuadu[i])];
			}
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
	};
	var numSplitWord=',';
	var endNumSplitWord='-';
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';

		var p5Direct1Size = contentObj.P5Direct1.length;
		var p5Direct2Size = contentObj.P5Direct2.length;
		var p5Direct3Size = contentObj.P5Direct3.length;
		var p5Direct4Size = contentObj.P5Direct4.length;
		var p5Direct5Size = contentObj.P5Direct5.length;
		
		var p3Direct1Size = contentObj.P3Direct1.length;
		var p3Direct2Size = contentObj.P3Direct2.length;
		var p3Direct3Size = contentObj.P3Direct3.length;
		
		var group3Size = contentObj.Group3.length;
		var group6Size = contentObj.Group6.length;
		var directSumSize = contentObj.DirectSum.length;
		var groupSumSize = contentObj.GroupSum.length;
		 /////2010 12
		var baoChuanSize = currentContentObj.BaoChuan.length;
		var p3DirectSize = currentContentObj.P3DirectKuadu.length;
		var p3Group3KuaduSize = currentContentObj.P3Group3Kuadu.length;
		var p3Group6KuaduSize = currentContentObj.P3Group6Kuadu.length;
		
		if (p5Direct1Size!=0&&p5Direct2Size!=0&&p5Direct3Size!=0&&p5Direct4Size!=0&&p5Direct5Size!=0) {
		    /////选的是排五直选玩法
			text += "直选：";
			for ( var i = 0, ball, l = p5Direct1Size; i < l; i++) {
				ball = getBallStr(contentObj.P5Direct1[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p5Direct2Size; i < l; i++) {
				ball = getBallStr(contentObj.P5Direct2[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p5Direct3Size; i < l; i++) {
				ball = getBallStr(contentObj.P5Direct3[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p5Direct4Size; i < l; i++) {
				ball = getBallStr(contentObj.P5Direct4[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p5Direct5Size; i < l; i++) {
				ball = getBallStr(contentObj.P5Direct5[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if (p3Direct1Size!=0&&p3Direct2Size!=0&&p3Direct3Size!=0) {
			/////选的是排三直选玩法
			text += "直选：";
			for ( var i = 0, ball, l = p3Direct1Size; i < l; i++) {
				ball = getBallStr(contentObj.P3Direct1[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p3Direct2Size; i < l; i++) {
				ball = getBallStr(contentObj.P3Direct2[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}else{
					text += endNumSplitWord;
					value += endNumSplitWord;
				}
			}
			for ( var i = 0, ball, l = p3Direct3Size; i < l; i++) {
				ball = getBallStr(contentObj.P3Direct3[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else if(group3Size>=2){
			text += "组三：";
			/////选的是组3玩法
			for ( var i = 0, ball, l = group3Size; i < l; i++) {
				ball = getBallStr(contentObj.Group3[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(group6Size>=3){
		    /////选的是组6玩法
			text += "组六：";
			for ( var i = 0, ball, l = group6Size; i < l; i++) {
				ball = getBallStr(contentObj.Group6[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(directSumSize!=0){
			text += "直选和值：";
		    /////选的是直选和值玩法
			for ( var i = 0, ball, l = directSumSize; i < l; i++) {
				ball = getBallStr(contentObj.DirectSum[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(groupSumSize!=0){
		    /////选的是组选和值玩法
			text += "组选和值：";
			for ( var i = 0, ball, l = groupSumSize; i < l; i++) {
				ball = getBallStr(contentObj.GroupSum[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}	/////2010 12
		else if(baoChuanSize!=0){
		    /////选的是包串
			text += "包串：";
			for ( var i = 0, ball, l = baoChuanSize; i < l; i++) {
				ball = getBallStr(contentObj.BaoChuan[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(p3DirectSize!=0){
		    /////选的直选跨度
			text += "直选跨度：";
			for ( var i = 0, ball, l = p3DirectSize; i < l; i++) {
				ball = getBallStr(contentObj.P3DirectKuadu[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(p3Group3KuaduSize!=0){
		    /////选的组选3跨度
			text += "组三跨度：";
			for ( var i = 0, ball, l = p3Group3KuaduSize; i < l; i++) {
				ball = getBallStr(contentObj.P3Group3Kuadu[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(p3Group6KuaduSize!=0){
		    /////选的组选6跨度
			text += "组六跨度：";
			for ( var i = 0, ball, l = p3Group6KuaduSize; i < l; i++) {
				ball = getBallStr(contentObj.P3Group6Kuadu[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}
		else{
			if(contentObj.units!=0){
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
	
	var DIRECT_BALL_ARR = [];
	for ( var i = 0; i < 10; i++) {
		DIRECT_BALL_ARR[i] = i;
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
	
	window.P5DirectRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					P5Direct1: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P5Direct2: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P5Direct3: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P5Direct4: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P5Direct5: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P3Direct1 : [], // 直选
					P3Direct2 : [], // 直选
					P3Direct3 : [], // 直选
					Group3 : [], // 组3
					Group6 : [], // 组6
					DirectSum : [], // 直选和值
					GroupSum : [], // 直选和值
					///2010 .12月更新
					BaoChuan : [],
					P3DirectKuadu : [],
					P3Group3Kuadu : [],
					P3Group6Kuadu : [],
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.P3DirectRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					P5Direct1: [], // 直选
					P5Direct2: [], // 直选
					P5Direct3: [], // 直选
					P5Direct4: [], // 直选
					P5Direct5: [], // 直选
					P3Direct1 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P3Direct2 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					P3Direct3 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					Group3 : [], // 组3
					Group6 : [], // 组6
					DirectSum : [], // 直选和值
					GroupSum : [], // 直选和值
					///2010 .12月更新
					BaoChuan : [],
					P3DirectKuadu : [],
					P3Group3Kuadu : [],
					P3Group6Kuadu : [],
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.luckP3RandomSelect = function(num1,num2,num3) {
		var randomContent = [];
		var contentObj = {
				P5Direct1: [], // 直选
				P5Direct2: [], // 直选
				P5Direct3: [], // 直选
				P5Direct4: [], // 直选
				P5Direct5: [], // 直选
				P3Direct1 : num1, // 直选
				P3Direct2 : num2, // 直选
				P3Direct3 : num3, // 直选
				Group3 : [], // 组3
				Group6 : [], // 组6
				DirectSum : [], // 直选和值
				GroupSum : [], // 直选和值
				///2010 .12月更新
				BaoChuan : [],
				P3DirectKuadu : [],
				P3Group3Kuadu : [],
				P3Group6Kuadu : [],
				units : 1
		};
		randomContent.push(contentObj);
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	window.luckP5RandomSelect = function(num1,num2,num3,num4,num5) {
		var randomContent = [];
		var contentObj = {
				P5Direct1: num1, // 直选
				P5Direct2: num2, // 直选
				P5Direct3: num3, // 直选
				P5Direct4: num4, // 直选
				P5Direct5: num5, // 直选
				P3Direct1 : [], // 直选
				P3Direct2 : [], // 直选
				P3Direct3 : [], // 直选
				Group3 : [], // 组3
				Group6 : [], // 组6
				DirectSum : [], // 直选和值
				GroupSum : [], // 直选和值
				///2010 .12月更新
				BaoChuan : [],
				P3DirectKuadu : [],
				P3Group3Kuadu : [],
				P3Group6Kuadu : [],
				units : 1
		};
		randomContent.push(contentObj);
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.Group3RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					P5Direct1: [], // 直选
					P5Direct2: [], // 直选
					P5Direct3: [], // 直选
					P5Direct4: [], // 直选
					P5Direct5: [], // 直选
					P3Direct1 : [], // 直选
					P3Direct2 : [], // 直选
					P3Direct3 : [], // 直选
					Group3 : copyDirectBallArr.sort(randomSort).slice(0, 2).sort(asc), // 组3
					Group6 : [], // 组6
					DirectSum : [], // 直选和值
					GroupSum : [], // 直选和值
					///2010 .12月更新
					BaoChuan : [],
					P3DirectKuadu : [],
					P3Group3Kuadu : [],
					P3Group6Kuadu : [],
					units : 2
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.Group6RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					P5Direct1: [], // 直选
					P5Direct2: [], // 直选
					P5Direct3: [], // 直选
					P5Direct4: [], // 直选
					P5Direct5: [], // 直选
					P3Direct1 : [], // 直选
					P3Direct2 : [], // 直选
					P3Direct3 : [], // 直选
					Group3 :  [],// 组3
					Group6 :  copyDirectBallArr.sort(randomSort).slice(0, 3).sort(asc), // 组6
					DirectSum : [], // 直选和值
					GroupSum : [], // 直选和值
					///2010 .12月更新
					BaoChuan : [],
					P3DirectKuadu : [],
					P3Group3Kuadu : [],
					P3Group6Kuadu : [],
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
	window.on_select_li =function(li){
		var li_list = $('#createForm_select_content > li');
		li_list.removeClass();
		li.className = 'select_codes_li';

		var value = li.title.split(':')[1];

		var createForm = getCreateForm();
		var playType = createForm.elements['playType'].value;
		var createForm_playType = createForm.elements['createForm.playType'].value;
		if(playType == '1'){
			var arr = value.split('-');
			var arrs = [];
			for ( var i = 0; i < arr.length; i++) {
				arrs.push(arr[i].split(','));
			}
			var directArr = [ 'P5Direct1', 'P5Direct2', 'P5Direct3', 'P5Direct4', 'P5Direct5' ];
			$.each(arrs,function(index, curArr) {
				var type = directArr[index];
				currentContentObj[type].length = 0;
				$('#area_box a[_name="' + type + '"]').each(function(item_index, item) {
					if (jQuery.inArray(item.innerHTML, curArr) >= 0) {
						item.className = selectedClass;
						currentContentObj[type].push(parseInt(item.innerHTML, 10));
					} else {
						item.className = unSelectedClass;
					}
				});
				currentContentObj[type].sort(asc);
			});
			calcCurrentUnits();
		}else if (createForm_playType == 'P3Direct') {
			var arr = value.split('-');
			var arrs = [];
			for ( var i = 0; i < arr.length; i++) {
				arrs.push(arr[i].split(','));
			}
			var directArr = [ 'P3Direct1', 'P3Direct2', 'P3Direct3' ];
			$.each(arrs,function(index, curArr) {
				var type = directArr[index];
				currentContentObj[type].length = 0;
				$('#area_box a[_name="' + type + '"]').each(function(item_index, item) {
					if (jQuery.inArray(item.innerHTML, curArr) >= 0) {
						item.className = selectedClass;
						currentContentObj[type].push(parseInt(item.innerHTML, 10));
					} else {
						item.className = unSelectedClass;
					}
				});
				currentContentObj[type].sort(asc);
			});
			calcCurrentUnits();
		} else {
			var arr = value.split(',');
			var type = createForm_playType;
			currentContentObj[type].length = 0;
			$('#area_box a[_name="' + type + '"]').each(function(index, item) {
				if (jQuery.inArray(item.innerHTML, arr) >= 0) {
					item.className = selectedClass;
					currentContentObj[type].push(parseInt(item.innerHTML, 10));
				} else {
					item.className = unSelectedClass;
				}
			});
			currentContentObj[type].sort(asc);
			calcCurrentUnits();
		}
	};
	window.clearSelect = function(generateNum){
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0-parseInt(arr[0]));
	};
	
	$('#area_box a[_name="P5Direct1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P5Direct1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P5Direct1.push(val);
		}
		currentContentObj.P5Direct1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P5Direct2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P5Direct2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P5Direct2.push(val);
		}
		currentContentObj.P5Direct2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P5Direct3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P5Direct3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P5Direct3.push(val);
		}
		currentContentObj.P5Direct3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P5Direct4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P5Direct4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P5Direct4.push(val);
		}
		currentContentObj.P5Direct4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P5Direct5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P5Direct5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P5Direct5.push(val);
		}
		currentContentObj.P5Direct5.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	$('#area_box a[_name="P3Direct1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3Direct1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3Direct1.push(val);
		}
		currentContentObj.P3Direct1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P3Direct2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3Direct2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3Direct2.push(val);
		}
		currentContentObj.P3Direct2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="P3Direct3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3Direct3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3Direct3.push(val);
		}
		currentContentObj.P3Direct3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="Group3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.Group3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.Group3.push(val);
		}
		currentContentObj.Group3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="Group6"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.Group6.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.Group6.push(val);
		}
		currentContentObj.Group6.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectSum.push(val);
		}
		currentContentObj.DirectSum.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="GroupSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.GroupSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.GroupSum.push(val);
		}
		currentContentObj.GroupSum.sort(asc);
		calcCurrentUnits();
	});
	///2010 12
	$('#area_box a[_name="BaoChuan"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.BaoChuan.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.BaoChuan.push(val);
		}
		currentContentObj.BaoChuan.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="P3DirectKuadu"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3DirectKuadu.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3DirectKuadu.push(val);
		}
		currentContentObj.P3DirectKuadu.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="P3Group3Kuadu"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3Group3Kuadu.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3Group3Kuadu.push(val);
		}
		currentContentObj.P3Group3Kuadu.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="P3Group6Kuadu"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.P3Group6Kuadu.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.P3Group6Kuadu.push(val);
		}
		currentContentObj.P3Group6Kuadu.sort(asc);
		calcCurrentUnits();
	});
	$('#_pl3ball_1 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(0,"all",1);
	});
	$('#_pl3ball_1 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(0,"odd",1);
	});
	$('#_pl3ball_1 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(0,"even",1);
	});
	$('#_pl3ball_1 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(0,"big",1);
	});
	$('#_pl3ball_1 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(0,"small",1);
	});
	$('#_pl3ball_1 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(0,"clear",1);
	});
	
	$('#_pl3ball_2 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(0,"all",2);
	});
	$('#_pl3ball_2 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(0,"odd",2);
	});
	$('#_pl3ball_2 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(0,"even",2);
	});
	$('#_pl3ball_2 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(0,"big",2);
	});
	$('#_pl3ball_2 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(0,"small",2);
	});
	$('#_pl3ball_2 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(0,"clear",2);
	});
	
	
	
	$('#_pl3ball_3 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(0,"all",3);
	});
	$('#_pl3ball_3 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(0,"odd",3);
	});
	$('#_pl3ball_3 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(0,"even",3);
	});
	$('#_pl3ball_3 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(0,"big",3);
	});
	$('#_pl3ball_3 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(0,"small",3);
	});
	$('#_pl3ball_3 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(0,"clear",3);
	});
	
	
	
	
	$('#Group3Tr a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(1,"all",null);
	});
	$('#Group3Tr a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(1,"odd",null);
	});
	$('#Group3Tr a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(1,"even",null);
	});
	$('#Group3Tr a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(1,"big",null);
	});
	$('#Group3Tr a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(1,"small",null);
	});
	$('#Group3Tr a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(1,"clear",null);
	});
	
	
	
	
	$('#Group6Tr a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(2,"all",null);
	});
	$('#Group6Tr a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(2,"odd",null);
	});
	$('#Group6Tr a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(2,"even",null);
	});
	$('#Group6Tr a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(2,"big",null);
	});
	$('#Group6Tr a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(2,"small",null);
	});
	$('#Group6Tr a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(2,"clear",null);
	});
	
	//////////////////////////////////////////////////////////////////////////////////
	$('#_pl5ball_1 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(3,"all",1);
	});
	$('#_pl5ball_1 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(3,"odd",1);
	});
	$('#_pl5ball_1 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(3,"even",1);
	});
	$('#_pl5ball_1 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(3,"big",1);
	});
	$('#_pl5ball_1 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(3,"small",1);
	});
	$('#_pl5ball_1 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(3,"clear",1);
	});
	
	$('#_pl5ball_2 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(3,"all",2);
	});
	$('#_pl5ball_2 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(3,"odd",2);
	});
	$('#_pl5ball_2 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(3,"even",2);
	});
	$('#_pl5ball_2 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(3,"big",2);
	});
	$('#_pl5ball_2 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(3,"small",2);
	});
	$('#_pl5ball_2 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(3,"clear",2);
	});
	
	
	
	$('#_pl5ball_3 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(3,"all",3);
	});
	$('#_pl5ball_3 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(3,"odd",3);
	});
	$('#_pl5ball_3 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(3,"even",3);
	});
	$('#_pl5ball_3 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(3,"big",3);
	});
	$('#_pl5ball_3 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(3,"small",3);
	});
	$('#_pl5ball_3 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(3,"clear",3);
	});
	
	
	
	$('#_pl5ball_4 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(3,"all",4);
	});
	$('#_pl5ball_4 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(3,"odd",4);
	});
	$('#_pl5ball_4 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(3,"even",4);
	});
	$('#_pl5ball_4 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(3,"big",4);
	});
	$('#_pl5ball_4 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(3,"small",4);
	});
	$('#_pl5ball_4 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(3,"clear",4);
	});
	
	
	
	$('#_pl5ball_5 a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(3,"all",5);
	});
	$('#_pl5ball_5 a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(3,"odd",5);
	});
	$('#_pl5ball_5 a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(3,"even",5);
	});
	$('#_pl5ball_5 a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(3,"big",5);
	});
	$('#_pl5ball_5 a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(3,"small",5);
	});
	$('#_pl5ball_5 a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(3,"clear",5);
	});
	//////////////////////////////////////////////////////////////////////////////
	var clearChooseBallArea =  function(){
		var all;
		for ( var int = 1; int <= 5; int++) {
		    all = $('#_pl5ball_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		for ( var int = 1; int <= 3; int++) {
		    all = $('#_pl3ball_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
	    all = $('#Group3Tr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
	    all = $('#Group6Tr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#BaoChuanTr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#DirectKuaduTr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#Group3KuaduTr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#Group6KuaduTr').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		
	};
	var chooseBallMethod=function(betType,oprType,line){
		if(0==betType){
			///直选
			if(line==1){
				var ballArr=$('#area_box a[_name="P3Direct1"]');
				chooseMethod(ballArr,oprType,currentContentObj.P3Direct1);
			}else if(line==2){
				var ballArr=$('#area_box a[_name="P3Direct2"]');
				chooseMethod(ballArr,oprType,currentContentObj.P3Direct2);
			}else if(line==3){
				var ballArr=$('#area_box a[_name="P3Direct3"]');
				chooseMethod(ballArr,oprType,currentContentObj.P3Direct3);
			}
		}else if(1==betType){
			//组三
			var ballArr=$('#Group3Tr a[_name="Group3"]');
			chooseMethod(ballArr,oprType,currentContentObj.Group3);
		}else if(2==betType){
			//组六
			var ballArr=$('#Group6Tr a[_name="Group6"]');
			chooseMethod(ballArr,oprType,currentContentObj.Group6);
		}else if(3==betType){
			if(line==1){
				var ballArr=$('#area_box a[_name="P5Direct1"]');
				chooseMethod(ballArr,oprType,currentContentObj.P5Direct1);
			}else if(line==2){
				var ballArr=$('#area_box a[_name="P5Direct2"]');
				chooseMethod(ballArr,oprType,currentContentObj.P5Direct2);
			}else if(line==3){
				var ballArr=$('#area_box a[_name="P5Direct3"]');
				chooseMethod(ballArr,oprType,currentContentObj.P5Direct3);
			}else if(line==4){
				var ballArr=$('#area_box a[_name="P5Direct4"]');
				chooseMethod(ballArr,oprType,currentContentObj.P5Direct4);
			}else if(line==5){
				var ballArr=$('#area_box a[_name="P5Direct5"]');
				chooseMethod(ballArr,oprType,currentContentObj.P5Direct5);
			}
		}
	};
	var chooseMethod = function (ballArr,oprType,obj){
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
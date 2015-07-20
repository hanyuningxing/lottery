$(function() {
	
	var Haocai1CodeList = new Array();	
	var ZodiacCodeList = new Array();	
	var SeasonCodeList = new Array();	
	var AzimuthCodeList = new Array();	

	var selectedClass = 'red';
	var unSelectedClass = '';
    var zodiacTextArr = ["鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡","狗", "猪"];
    var seasonTextArr = ["春", "夏", "秋", "冬"];
    var azimuthTextArr = ["东", "南", "西", "北"];
	var currentContentObj = {
		General : [], // 常规
		Haocai1 : [], // 好彩1
		Haocai2 : [], // 好彩2
		Haocai3 : [], // 好彩3
		Zodiac : [], // 生肖
		Season : [], // 季节
		Azimuth : [], // 方位
		units : 0
	};
	
	var clearCurrentContent = function(){
		$('#area_box td[_name="General"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="Haocai1"]').attr("class",unSelectedClass); 

		
		$('#area_box td[_name="Haocai2"]').attr("class",unSelectedClass); 
		$('#area_box td[_name="Haocai3"]').attr("class",unSelectedClass); 
		$('#area_box td[_name="Zodiac"]').attr("class",unSelectedClass); 
		$('#area_box td[_name="Season"]').attr("class",unSelectedClass); 
		$('#area_box td[_name="Azimuth"]').attr("class",unSelectedClass); 
		currentContentObj.General.length = 0;
		currentContentObj.Haocai1.length = 0;
		currentContentObj.Haocai2.length = 0;
		currentContentObj.Haocai3.length = 0;
		currentContentObj.Zodiac.length = 0;
		currentContentObj.Season.length = 0;
		currentContentObj.Azimuth.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};

	var calcCurrentUnits = function() {
		var units = 0;
		var generalSize = currentContentObj.General.length;
		var haocai1Size = currentContentObj.Haocai1.length;
		var haocai2Size = currentContentObj.Haocai2.length;
		var haocai3Size = currentContentObj.Haocai3.length;
		var zodiacSize = currentContentObj.Zodiac.length;
		var seasonSize = currentContentObj.Season.length;
		var azimuthSize = currentContentObj.Azimuth.length;
		if (generalSize!=0) {
			/////选的是常规玩法
			units = comp(7 , generalSize);
		}else if(haocai1Size!=0){
			/////选的是好彩1玩法
			units = haocai1Size;
		}else if(haocai2Size!=0){
		    /////选的是好彩2玩法
			units = comp(2 , haocai2Size);
		}else if(haocai3Size!=0){
		    /////选的是好彩3玩法
			units = comp(3 , haocai3Size);
		}else if(zodiacSize!=0){
		    /////选的是好彩1生肖玩法
			units = zodiacSize;
		}else if(seasonSize!=0){
		    /////选的是好彩1季节玩法
			units = seasonSize;
		}else if(azimuthSize!=0){
		    /////选的是好彩1方位玩法
			units = azimuthSize;
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
		if (val < 10)
			return '0' + val;
		else
			return '' + val;
	};
	var getZodiacBallStr = function(val) {
		  if(val<1){
			  return;
		  }
          return zodiacTextArr[parseInt(val)-1];
	};
	var getSeasonBallStr = function(val) {
		if(val<1){
			  return;
		  }
        return seasonTextArr[parseInt(val)-1];
	};
	var getAzimuthBallStr = function(val) {
		if(val<1){
			  return;
		  }
        return azimuthTextArr[parseInt(val)-1];
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
	
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';
		var generalSize = contentObj.General.length;
		var haocai1Size = contentObj.Haocai1.length;
		var haocai2Size = contentObj.Haocai2.length;
		var haocai3Size = contentObj.Haocai3.length;
		var zodiacSize = contentObj.Zodiac.length;
		var seasonSize = contentObj.Season.length;
		var azimuthSize = contentObj.Azimuth.length;
		if (generalSize!=0) {
			/////选的是常规玩法
			for ( var i = 0, ball, l = contentObj.General.length; i < l; i++) {
				ball = getBallStr(contentObj.General[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(haocai1Size!=0){
			/////选的是好彩1玩法
			for ( var i = 0, ball, l = contentObj.Haocai1.length; i < l; i++) {
				ball = getBallStr(contentObj.Haocai1[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(haocai2Size!=0){
		    /////选的是好彩2玩法
			for ( var i = 0, ball, l = contentObj.Haocai2.length; i < l; i++) {
				ball = getBallStr(contentObj.Haocai2[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(haocai3Size!=0){
		    /////选的是好彩3玩法
			for ( var i = 0, ball, l = contentObj.Haocai3.length; i < l; i++) {
				ball = getBallStr(contentObj.Haocai3[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(zodiacSize!=0){
		    /////选的是好彩1生肖玩法
			for ( var i = 0, ballText,ball, l = contentObj.Zodiac.length; i < l; i++) {
				ball = getBallStr(contentObj.Zodiac[i]);
				ballText = getZodiacBallStr(contentObj.Zodiac[i]);
				value += ball;
				text += ballText;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(seasonSize!=0){
		    /////选的是好彩1季节玩法
			for ( var i = 0, ballText,ball, l = contentObj.Season.length; i < l; i++) {
				ball = getBallStr(contentObj.Season[i]);
				ballText = getSeasonBallStr(contentObj.Season[i]);
				value += ball;
				text += ballText;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else if(azimuthSize!=0){
		    /////选的是好彩1方位玩法
			for ( var i = 0, ballText,ball, l = contentObj.Azimuth.length; i < l; i++) {
				ball = getBallStr(contentObj.Azimuth[i]);
				ballText = getAzimuthBallStr(contentObj.Azimuth[i]);
				value += ball;
				text += ballText;
				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				} 
			}
		}else{
			if(contentObj.units!=0){
				window.alert("选择玩法出错");
				this.clearCurrentContent();
			}
		}
		
		
		for(var i=0;i<haocai1Size;i++){
			Haocai1CodeList.push(contentObj.Haocai1[i]);
		}
		for(var i=0;i<zodiacSize;i++){
			ZodiacCodeList.push(contentObj.Zodiac[i]);
		}
		for(var i=0;i<seasonSize;i++){
			SeasonCodeList.push(contentObj.Season[i]);
		}
		for(var i=0;i<azimuthSize;i++){
			AzimuthCodeList.push(contentObj.Azimuth[i]);
		}

		selectAddOption(selectObj, text, value);// 往select里添加option
		addOrSubBetUnits(contentObj.units);// 更新方案注数
	};

	
	window.on_select_li = function(li) {
		var li_list = $('#createForm_select_content > li');
		li_list.removeClass();
		li.className = 'select_codes_li';

		var value = li.title.split(':')[1];

		var createForm = getCreateForm();
		var createForm_playType = createForm.elements['createForm.playType'].value;
		
		var arr = value.split(',');
		var type = createForm_playType;
		
		if(type=='Haocai1') {
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
	
	
	window.pushCurrentContent = function() {
		if (currentContentObj.units <= 0) {
			alert('您选择的号码还不够组成1注');
			return;
		}
		pushContent(currentContentObj);
		clearCurrentContent();
		clearHaocai1Tu();
	};
	
	var GENERAL_BALL_ARR = [];
	for ( var i = 0; i < 36; i++) {
		GENERAL_BALL_ARR[i] = i + 1;
	}
	
	var ZODIAC_BALL_ARR = [];
	for ( var i = 0; i < 12; i++) {
		ZODIAC_BALL_ARR[i] = i + 1;
	}
	
	var SEASON_AZIMUTH_BALL_ARR = [];
	for ( var i = 0; i < 4; i++) {
		SEASON_AZIMUTH_BALL_ARR[i] = i + 1;
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
	
	window.GeneralRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyGeneralBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General : copyGeneralBallArr.sort(randomSort).slice(0, 7).sort(asc), // 常规
					Haocai1 : [], // 好彩1
					Haocai2 : [], // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : [], // 生肖
					Season : [], // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.Haocai1RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyGeneralBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
		
		//清好彩一
		var newHaocai1CodeList = new Array();
		for(var j=0;j<copyGeneralBallArr.length;j++){
			var contant =1;
			for(var i=0;i<Haocai1CodeList.length;i++){
				if(Haocai1CodeList[i]==copyGeneralBallArr[j]){
					contant = 0;
				}
			}
			if(contant==1){
				newHaocai1CodeList.push(copyGeneralBallArr[j]);
			}

		}
		copyGeneralBallArr =newHaocai1CodeList;

		if(copyGeneralBallArr.length<=0){
			alert("机选号码与投注列表里号码重复");
			return;
		}
		
		
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var randomCodeList = copyGeneralBallArr.sort(randomSort).slice(0, 1).sort(asc);
			
			var newHaocai1CodeList = new Array();
			for(var j=0;j<copyGeneralBallArr.length;j++){
				var contant =1;
				for(var i=0;i<randomCodeList.length;i++){
					if(randomCodeList[i]==copyGeneralBallArr[j]){
						contant = 0;
					}
				}
				if(contant==1){
					newHaocai1CodeList.push(copyGeneralBallArr[j]);
				}

			}
			copyGeneralBallArr =newHaocai1CodeList;
			
			
			var contentObj = {
					General :[],
					Haocai1 :  randomCodeList, // 好彩1
					Haocai2 : [], // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : [], // 生肖
					Season : [], // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
			for(var j=0;j<randomContent[i].Haocai1.length;j++){
				Haocai1CodeList.push(randomContent[i].Haocai1[j]);
			}
		}
	};
	
	window.Haocai2RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyGeneralBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General :[],
					Haocai1 : [], // 好彩1
					Haocai2 :  copyGeneralBallArr.sort(randomSort).slice(0, 2).sort(asc), // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : [], // 生肖
					Season : [], // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.Haocai3RandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;

		var copyGeneralBallArr = GENERAL_BALL_ARR.slice(0, GENERAL_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General :[],
					Haocai1 : [], // 好彩1
					Haocai2 :  [], // 好彩2
					Haocai3 : copyGeneralBallArr.sort(randomSort).slice(0, 3).sort(asc), // 好彩3
					Zodiac : [], // 生肖
					Season : [], // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	window.ZodiacRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyBallArr = ZODIAC_BALL_ARR.slice(0, ZODIAC_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General :[],
					Haocai1 : [], // 好彩1
					Haocai2 :  [], // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : copyBallArr.sort(randomSort).slice(0, 1).sort(asc), // 生肖
					Season : [], // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
			ZodiacCodeList.push(randomContent[i]);

		}
	};
	window.SeasonRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		
		var copyBallArr = SEASON_AZIMUTH_BALL_ARR.slice(0, SEASON_AZIMUTH_BALL_ARR.length);
		
		
		for(var i=0;i<SeasonCodeList.length;i++){
			alert(SeasonCodeList[i]);
			copyBallArr.erase(SeasonCodeList[i]);
		}
		alert(copyBallArr.length);
		if(copyBallArr.length<=0){
			alert("机选号码与投注列表里号码重复！");
			return;
		}
		var randomContent = [];
		var random = copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General :[],
					Haocai1 : [], // 好彩1
					Haocai2 :  [], // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : [], // 生肖
					Season : random, // 季节
					Azimuth : [], // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
			SeasonCodeList.push(random);
		}
	};
	window.AzimuthRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyBallArr = SEASON_AZIMUTH_BALL_ARR.slice(0, SEASON_AZIMUTH_BALL_ARR.length);
		for(var i=0;i<AzimuthCodeList.length;i++){
			copyBallArr.erase(AzimuthCodeList[i]);
		}
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					General :[],
					Haocai1 : [], // 好彩1
					Haocai2 :  [], // 好彩2
					Haocai3 : [], // 好彩3
					Zodiac : [], // 生肖
					Season : [], // 季节
					Azimuth : copyBallArr.sort(randomSort).slice(0, 1).sort(asc), // 方位
				    units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
			AzimuthCodeList.push(random);
		}
	};

	
	
	window.luckRandomSelect = function(num1) {
		var randomContent = [];
		var contentObj = {
				General :[],
				Haocai1 : num1, // 好彩1
				Haocai2 :  [], // 好彩2
				Haocai3 : [], // 好彩3
				Zodiac : num1, // 生肖
				Season : num1, // 季节
				Azimuth : num1, // 方位
			    units : 1
		};
		randomContent.push(contentObj);
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
		
		Haocai1CodeList.length = 0;	
		ZodiacCodeList.length = 0;	
		SeasonCodeList.length = 0;	
		AzimuthCodeList.length = 0;	
		
		
	};
	
	
	window.clearSelect = function(generateNum){
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0-parseInt(arr[0]));
		var codelist = arr[1].split(",");
		//清好彩一
		var newHaocai1CodeList = new Array();
		for(var j=0;j<Haocai1CodeList.length;j++){
			var contant =1;
			for(var i=0;i<codelist.length;i++){
				if(codelist[i]==Haocai1CodeList[j]){
					contant = 0;
				}
			}
			if(contant==1){
				newHaocai1CodeList.push(Haocai1CodeList[j]);
			}

		}
		Haocai1CodeList =newHaocai1CodeList;
		
		//清生肖
		var newZodiacCodeList = new Array();
		for(var j=0;j<ZodiacCodeList.length;j++){
			var contant =1;
			for(var i=0;i<codelist.length;i++){
				if(codelist[i]==ZodiacCodeList[j]){
					contant = 0;
				}
			}
			if(contant==1){
				newZodiacCodeList.push(ZodiacCodeList[j]);
			}

		}
		ZodiacCodeList =newZodiacCodeList;
		
		//清季节
		var newSeasonCodeList = new Array();
		for(var j=0;j<SeasonCodeList.length;j++){
			var contant =1;
			for(var i=0;i<codelist.length;i++){
				if(codelist[i]==SeasonCodeList[j]){
					contant = 0;
				}
			}
			if(contant==1){
				newSeasonCodeList.push(SeasonCodeList[j]);
			}

		}
		SeasonCodeList =newSeasonCodeList;
		
		//清方位
		var newAzimuthCodeList = new Array();
		for(var j=0;j<AzimuthCodeList.length;j++){
			var contant =1;
			for(var i=0;i<codelist.length;i++){
				if(codelist[i]==AzimuthCodeList[j]){
					contant = 0;
				}
			}
			if(contant==1){
				newAzimuthCodeList.push(AzimuthCodeList[j]);
			}
		}
		
		AzimuthCodeList =newSeasonCodeList;

	};
	
	$('#area_box td[_name="General"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			
			
			
			currentContentObj.General.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.General.push(val);
		}
		currentContentObj.General.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="Haocai1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if(jQuery.inArray(val, Haocai1CodeList)>=0){
			return;
		}

		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.Haocai1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.Haocai1.push(val);
		}
		currentContentObj.Haocai1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box td[_name="Haocai2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.Haocai2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.Haocai2.push(val);
		}
		currentContentObj.Haocai2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box td[_name="Haocai3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.Haocai3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.Haocai3.push(val);
		}
		currentContentObj.Haocai3.sort(asc);
		calcCurrentUnits();
	});
	
	window.clearHaocai1Tu = function() {
		var zodiac = $('input[type="checkbox"][name="Zodiac"]');
		for ( var i = 1; i <= zodiac.length; i++) {
			var zodiacClassId ="Zodiac_class_"+i;
			var zodiacId ="Zodiac_"+i;
			$("#"+zodiacClassId).attr("class","hc1_sx1");
			$("#"+zodiacId).attr("checked","");
		}
		
		var season = $('input[type="checkbox"][name="Season"]');
		for ( var i = 1; i <= season.length; i++) {
			var seasonClassId ="Season_class_"+i;
			var seasonId ="Season_"+i;
			$("#"+seasonClassId).attr("class","hc1_jijie");
			$("#"+seasonId).attr("checked","");
		}
		
		var azimuth = $('input[type="checkbox"][name="Azimuth"]');
		for ( var i = 1; i <= azimuth.length; i++) {
			var azimuthClassId ="Azimuth_class_"+i;
			var azimuthId ="Azimuth_"+i;
			$("#"+azimuthClassId).attr("class","hc1_jijie");
			$("#"+azimuthId).attr("checked","");
		}
	};
	
	//生肖
	for(var i=0;i<=12;i++){ 
		$("#Zodiac_class_"+i).bind("click", function(event) {
			    var idVal=this.id.split("_")[2];
				var val = parseInt(idVal, 10);
				var zodiacClassId ="Zodiac_class_"+val;
				var checkboxId ="Zodiac_"+val;
				if(jQuery.inArray(val, ZodiacCodeList)>=0){
					return;
				}
				if ($("#"+zodiacClassId).attr("class") == "hc1_sx1") {
					currentContentObj.Zodiac.push(val);
					$("#"+zodiacClassId).attr("class","hc1_sx1a");
					$("#"+checkboxId).attr("checked","checked");
				} else {
					currentContentObj.Zodiac.erase(val);
					$("#"+zodiacClassId).attr("class","hc1_sx1");
					$("#"+checkboxId).attr("checked","");
				}
				currentContentObj.Season.sort(asc);
				calcCurrentUnits();
		});
	}
	
	
	
	
	
	//季节
	for(var i=0;i<=4;i++){ 
		$("#Season_class_"+i).bind("click", function(event) {
			    var idVal=this.id.split("_")[2];
				var val = parseInt(idVal, 10);
				var seasonClassId ="Season_class_"+val;
				var checkboxId ="Season_"+val;
				if(jQuery.inArray(val, SeasonCodeList)>=0){
					return;
				}
				if ($("#"+seasonClassId).attr("class") == "hc1_jijie") {
					currentContentObj.Season.push(val);
					$("#"+seasonClassId).attr("class","hc1_jijie2");
					$("#"+checkboxId).attr("checked","checked");
				} else {
					currentContentObj.Season.erase(val);
					$("#"+seasonClassId).attr("class","hc1_jijie");
					$("#"+checkboxId).attr("checked","");
				}
				currentContentObj.Season.sort(asc);
				calcCurrentUnits();
		});
	}
	
	
	//方位
	for(var i=0;i<=4;i++){ 
		$("#Azimuth_class_"+i).bind("click", function(event) {
			    var idVal=this.id.split("_")[2];
				var val = parseInt(idVal, 10);
				var azimuthClassId ="Azimuth_class_"+val;
				var checkboxId ="Azimuth_"+val;
				if(jQuery.inArray(val, AzimuthCodeList)>=0){
					return;
				}
				if ($("#"+azimuthClassId).attr("class") == "hc1_jijie") {
					currentContentObj.Azimuth.push(val);
					$("#"+azimuthClassId).attr("class","hc1_jijie2");
					$("#"+checkboxId).attr("checked","checked");
				} else {
					currentContentObj.Azimuth.erase(val);
					$("#"+azimuthClassId).attr("class","hc1_jijie");
					$("#"+checkboxId).attr("checked","");
				}
				currentContentObj.Azimuth.sort(asc);
				calcCurrentUnits();
		});
	}
	
	
	
	
	
	//选择球
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
	
	//取消选择球
	var ballUnSelected=function(ball,content){
		if(ball.className == unSelectedClass) return;
		var val = parseInt(ball.innerHTML, 10);
		ball.className = unSelectedClass;
		content.erase(val);
		content.sort(asc);
		calcCurrentUnits();
	};
	
	
	
	
	
	//全大小奇偶选择
	var chooseMethod = function (ballArr,oprType,obj){
		for(i=0;i<ballArr.length;i++){
			ballUnSelected(ballArr[i], obj);
		}
		for(i=0;i<ballArr.length;i++){
			var val = parseInt(ballArr[i].innerHTML, 10);
			if(jQuery.inArray(val, Haocai1CodeList)>=0){
				continue;
			}
			
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
				if(val>=19){
					ballSelected(ballArr[i], obj);
				}
			}else if("small"==oprType){
				if(val<19){
					ballSelected(ballArr[i], obj);
				}
			}else if("zeroRoad"==oprType){
				if(val%3==0){
					ballSelected(ballArr[i], obj);
				}
			}else if("oneRoad"==oprType){
				if(val%3==1){
					ballSelected(ballArr[i], obj);
				}
			}
			else if("twoRoad"==oprType){
				if(val%3==2){
					ballSelected(ballArr[i], obj);
				}
			}
		}
	};
	
	
	//全 大小奇偶清
	
	//全大小奇偶选择
	var chooseBallMethod=function(betType,oprType,line){
		var ballArr=$('#area_box a[_name="Haocai1"]');
		chooseMethod(ballArr,oprType,currentContentObj.Haocai1);
	};
	
	
	$('#Haocai1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(1,"all",1);});
	$('#Haocai1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(1,"odd",1);});
	$('#Haocai1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(1,"even",1);});
	$('#Haocai1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(1,"big",1);});
	$('#Haocai1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(1,"small",1);});
	$('#Haocai1 a[_name="zeroRoad"]').bind("click", function(event) {chooseBallMethod(1,"zeroRoad",1);});
	$('#Haocai1 a[_name="oneRoad"]').bind("click", function(event) {chooseBallMethod(1,"oneRoad",1);});
	$('#Haocai1 a[_name="twoRoad"]').bind("click", function(event) {chooseBallMethod(1,"twoRoad",1);});
	$('#Haocai1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(1,"clear",1);});
	
	
	
	
});

$(function() {
	var selectedClass = 'red';
	var selectedClass_blue = 'blue';
	var unSelectedClass = '';
	var BigSmallDoubleSingleText = ['','小','大','','双','单'];//大小双单 2 1 4 5
	var Direct_Two_Sum = [
	                        1, //0
	                        2, //1
	                        3, //2
	                        4, //3
	                        5, //4
	                        6, //5
	                        7, //6
	                        8, //7
	                        9, //8
	                        10, //9
	                        9, //10
	                        8, //11
	                        7, //12
	                        6, //13
	                        5, //14
	                        4, //15
	                        3, //16
	                        2, //17
	                        1, //18
	        			   ];
	var Direct_Three_Sum = [
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
	                        36,//20
	                        28,//21
	                        21,//22
	                        15,//23
	                        10,//24
	                        6,//25
	                        3,//26
	                        1  //27
	        			   ];
	
	
	var Group_Two_Sum = [
	                        1, //0
	                        1, //1
	                        2, //2
	                        2, //3
	                        3, //4
	                        3, //5
	                        4, //6
	                        4, //7
	                        5, //8
	                        5, //9
	                        5, //10
	                        4, //11
	                        4, //12
	                        3, //13
	                        3, //14
	                        2, //15
	                        2, //16
	                        1, //17
	                        1, //18
	        			   ];
	
	
	var Group_Three_Sum = [
	                        1, //0
	                        1, //1
	                        2, //2
	                        3, //3
	                        4, //4
	                        5, //5
	                        7, //6
	                        8, //7
	                        10, //8
	                        12, //9
	                        13, //10
	                        14, //11
	                        15, //12
	                        15, //13
	                        15, //14
	                        15, //15
	                        14, //16
	                        13, //17
	                        12, //18
	                        10, //19
	                        8,//20
	                        7,//21
	                        5,//22
	                        4,//23
	                        3,//24
	                        2,//25
	                        1,//26
	                        1  //27
	        			   ];
	
	var currentContentObj = {
		DirectOne:[], // 一星 直选 个位
		
		DirectTwo1:[], //二星 直选 个位
		DirectTwo2:[], //二星 直选  十位
		
		DirectThree1:[],  // 三星 直选 个位
		DirectThree2:[],  // 三星 直选 十位
		DirectThree3:[], // 三星 直选 百位
		
		
		DirectFive1:[],// 五星 直选 个位  
		DirectFive2:[],// 五星 直选 十位  
		DirectFive3:[],// 五星 直选 百位  
		DirectFive4:[],// 五星 直选 千位  
		DirectFive5:[],// 五星 直选 万位  
		
		DirectFour1:[],// 四星 直选 个位  
		DirectFour2:[],// 四星 直选 十位  
		DirectFour3:[],// 四星 直选 百位  
		DirectFour4:[],// 四星 直选 千位  
		
		AllFive1:[],// 五星 通选 个位
		AllFive2:[],// 五星 通选 十位
		AllFive3:[],// 五星 通选 百位
		AllFive4:[],// 五星 通选 千位
		AllFive5:[],// 五星 通选 万位
		
		
		ThreeGroup3:[],// 三星组三 
		ThreeGroup6:[],// 三星组六 

		DirectThreeSum: [], // 三星直选和值
		GroupThreeSum: [], // 三星组选和值
		GroupThree: [], // 三星组选
		
		DirectTwoSum: [], // 二星直选和值
		GroupTwoSum: [], // 二星组选和值
		GroupTwo: [], // 二星组选
		

		BigSmallDoubleSingle1:[],// 大小双单 十位
		BigSmallDoubleSingle2:[],// 大小双单 各位
		
		RANDOMONE1:[],//  个位  //任选1 2
		RANDOMONE2:[],//  十位  //任选1 2
		RANDOMONE3:[],//  百位  //任选1 2
		RANDOMONE4:[],//  千位  //任选1 2
		RANDOMONE5:[],//  万位  //任选1 2
		
		RANDOMTWO1:[],//  个位  //任选2
		RANDOMTWO2:[],//  十位  //任选2
		RANDOMTWO3:[],//  百位  //任选2
		RANDOMTWO4:[],//  千位  //任选2
		RANDOMTWO5:[],//  万位  //任选2
		
		Dan : [], //胆码
		units : 0
	};
	
	//球绑定选择事件
	$('#area_box a[_name="DirectFour1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFour1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFour1.push(val);
		}
		currentContentObj.DirectFour1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFour2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFour2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFour2.push(val);
		}
		currentContentObj.DirectFour2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFour3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFour3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFour3.push(val);
		}
		currentContentObj.DirectFour3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFour4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFour4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFour4.push(val);
		}
		currentContentObj.DirectFour4.sort(asc);
		calcCurrentUnits();
	});
	
	
	//球绑定选择事件
	$('#area_box a[_name="DirectFive1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFive1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFive1.push(val);
		}
		currentContentObj.DirectFive1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFive2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFive2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFive2.push(val);
		}
		currentContentObj.DirectFive2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFive3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFive3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFive3.push(val);
		}
		currentContentObj.DirectFive3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFive4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFive4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFive4.push(val);
		}
		currentContentObj.DirectFive4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectFive5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectFive5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectFive5.push(val);
		}
		currentContentObj.DirectFive5.sort(asc);
		calcCurrentUnits();
	});
	
	
	$('#area_box a[_name="AllFive1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.AllFive1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.AllFive1.push(val);
		}
		currentContentObj.AllFive1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="AllFive2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.AllFive2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.AllFive2.push(val);
		}
		currentContentObj.AllFive2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="AllFive3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.AllFive3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.AllFive3.push(val);
		}
		currentContentObj.AllFive3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="AllFive4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.AllFive4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.AllFive4.push(val);
		}
		currentContentObj.AllFive4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="AllFive5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.AllFive5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.AllFive5.push(val);
		}
		currentContentObj.AllFive5.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMONE1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMONE1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMONE1.push(val);
		}
		currentContentObj.RANDOMONE1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMONE2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMONE2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMONE2.push(val);
		}
		currentContentObj.RANDOMONE2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMONE3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMONE3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMONE3.push(val);
		}
		currentContentObj.RANDOMONE3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMONE4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMONE4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMONE4.push(val);
		}
		currentContentObj.RANDOMONE4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMONE5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMONE5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMONE5.push(val);
		}
		currentContentObj.RANDOMONE5.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="RANDOMTWO1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMTWO1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMTWO1.push(val);
		}
		currentContentObj.RANDOMTWO1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMTWO2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMTWO2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMTWO2.push(val);
		}
		currentContentObj.RANDOMTWO2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMTWO3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMTWO3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMTWO3.push(val);
		}
		currentContentObj.RANDOMTWO3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMTWO4"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMTWO4.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMTWO4.push(val);
		}
		currentContentObj.RANDOMTWO4.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="RANDOMTWO5"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.RANDOMTWO5.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.RANDOMTWO5.push(val);
		}
		currentContentObj.RANDOMTWO5.sort(asc);
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="ThreeGroup3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ThreeGroup3.erase(val);
		} else {
				this.className = selectedClass;
				currentContentObj.ThreeGroup3.push(val);
				if (checkNormalRepeat(val)) {
					this.className = unSelectedClass;
					currentContentObj.ThreeGroup3.erase(val);
					return;
				}				
		}
		currentContentObj.ThreeGroup3.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="ThreeGroup6"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.ThreeGroup6.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.ThreeGroup6.push(val);
			if (checkNormalRepeat(val)) {
				this.className = unSelectedClass;
				currentContentObj.ThreeGroup6.erase(val);
				return;
			}		
		}
		currentContentObj.ThreeGroup6.sort(asc);
		calcCurrentUnits();
	});
	
	
	
	//三星 球绑定选择事件
	$('#area_box a[_name="DirectThree1"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree1.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree1.push(val);
		}
		currentContentObj.DirectThree1.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectThree2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree2.push(val);
		}
		currentContentObj.DirectThree2.sort(asc);
		calcCurrentUnits();
	});
	$('#area_box a[_name="DirectThree3"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThree3.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThree3.push(val);
		}
		currentContentObj.DirectThree3.sort(asc);
		calcCurrentUnits();
	});
	
	
	//两星
	$('#area_box a[_name="DirectTwo1"]').bind("click", function(event) {
	var val = parseInt(this.innerHTML, 10);
	if (this.className == selectedClass) {
		this.className = unSelectedClass;
		currentContentObj.DirectTwo1.erase(val);
	} else {
		this.className = selectedClass;
		currentContentObj.DirectTwo1.push(val);
	}
	currentContentObj.DirectTwo1.sort(asc);
	calcCurrentUnits();
	});
	
	$('#area_box a[_name="DirectTwo2"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwo2.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectTwo2.push(val);
		}
		currentContentObj.DirectTwo2.sort(asc);
		calcCurrentUnits();
		});
	
	//一星
	
	$('#area_box a[_name="DirectOne"]').bind("click", function(event) {
		
		$('#area_box a[_name="DirectOne"]').className = unSelectedClass;
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectOne.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectOne.push(val);
		}
		currentContentObj.DirectOne.sort(asc);
		calcCurrentUnits();
		});
	
	//二星直选和值
	$('#area_box a[_name="DirectTwoSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectTwoSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectTwoSum.push(val);
		}
		currentContentObj.DirectTwoSum.sort(asc);
		calcCurrentUnits();
	});
	
	
	//三星直选和值
	$('#area_box a[_name="DirectThreeSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.DirectThreeSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.DirectThreeSum.push(val);
		}
		currentContentObj.DirectThreeSum.sort(asc);
		calcCurrentUnits();
	});
	
	
	//二星组选和值
	$('#area_box a[_name="GroupTwoSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.GroupTwoSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.GroupTwoSum.push(val);
		}
		currentContentObj.GroupTwoSum.sort(asc);
		calcCurrentUnits();
	});
	
	
	//三星组选和值
	$('#area_box a[_name="GroupThreeSum"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.GroupThreeSum.erase(val);
		} else {
			this.className = selectedClass;
			currentContentObj.GroupThreeSum.push(val);
		}
		currentContentObj.GroupThreeSum.sort(asc);
		calcCurrentUnits();
	});
	//两星组选
	$('#area_box a[_name="GroupTwo"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.GroupTwo.erase(val);
		} else {
			//if(currentContentObj.GroupTwo.length>=7) {
			//	 alert("二星组选选号最多不能超过7个")
			//} else {
				this.className = selectedClass;
				currentContentObj.GroupTwo.push(val);
				if (checkNormalRepeat(val)) {
					this.className = unSelectedClass;
					currentContentObj.GroupTwo.erase(val);
					return;
				}				
			//}
		}
		currentContentObj.GroupTwo.sort(asc);
		calcCurrentUnits();
	});
	
	//三星直选组合
	$('#area_box a[_name="GroupThree"]').bind("click", function(event) {
		var val = parseInt(this.innerHTML, 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.GroupThree.erase(val);
		} else {
				this.className = selectedClass;
				currentContentObj.GroupThree.push(val);
		}
		currentContentObj.GroupThree.sort(asc);
		calcCurrentUnits();
	});
	
	
	//大小双单
	
	$('#area_box a[_name="BigSmallDoubleSingle1"]').bind("click", function(event) {
		var id = this.id;
		var val = parseInt(id.split("_")[1], 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.BigSmallDoubleSingle1.erase(val);
		} else {
				this.className = selectedClass;
				currentContentObj.BigSmallDoubleSingle1.push(val);
		}
		calcCurrentUnits();
	});
	
	$('#area_box a[_name="BigSmallDoubleSingle2"]').bind("click", function(event) {
		var id = this.id;
		var val = parseInt(id.split("_")[1], 10);
		if (this.className == selectedClass) {
			this.className = unSelectedClass;
			currentContentObj.BigSmallDoubleSingle2.erase(val);
		} else {
				this.className = selectedClass;
				currentContentObj.BigSmallDoubleSingle2.push(val);
		}
		calcCurrentUnits();
	});
	
	
	
	var clearCurrentContent = function(){
		$('#area_box a[_name="DirectOne"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="DirectTwo1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectTwo2"]').attr("class",unSelectedClass);
		
		$('#area_box a[_name="DirectThree1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectThree2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectThree3"]').attr("class",unSelectedClass); 

		$('#area_box a[_name="DirectFive1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFive2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFive3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFive4"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFive5"]').attr("class",unSelectedClass); 

		
		$('#area_box a[_name="DirectFour1"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFour2"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFour3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="DirectFour4"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="ThreeGroup3"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="ThreeGroup6"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="DirectThreeSum"]').attr("class",unSelectedClass); 
		$('#area_box a[_name="GroupThreeSum"]').attr("class",unSelectedClass);
		$('#area_box a[_name="GroupThree"]').attr("class",unSelectedClass); 
		
		
		$('#area_box a[_name="DirectTwoSum"]').attr("class",unSelectedClass);
		$('#area_box a[_name="GroupTwoSum"]').attr("class",unSelectedClass);
		$('#area_box a[_name="GroupTwo"]').attr("class",unSelectedClass);
		
		$('#area_box a[_name="AllFive1"]').attr("class",unSelectedClass);
		$('#area_box a[_name="AllFive2"]').attr("class",unSelectedClass);
		$('#area_box a[_name="AllFive3"]').attr("class",unSelectedClass);
		$('#area_box a[_name="AllFive4"]').attr("class",unSelectedClass);
		$('#area_box a[_name="AllFive5"]').attr("class",unSelectedClass);

		
		$('#area_box a[_name="RANDOMONE1"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMONE2"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMONE3"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMONE4"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMONE5"]').attr("class",unSelectedClass);
		
		
		$('#area_box a[_name="RANDOMTWO1"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMTWO2"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMTWO3"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMTWO4"]').attr("class",unSelectedClass);
		$('#area_box a[_name="RANDOMTWO5"]').attr("class",unSelectedClass);
		
		$('#area_box a[_name="BigSmallDoubleSingle1"]').attr("class",unSelectedClass);
		$('#area_box a[_name="BigSmallDoubleSingle2"]').attr("class",unSelectedClass);



		$('a[_name="bet_ball"]').attr("class",unSelectedClass); 
		
		$('#area_box a[_name="units"]').attr("class",unSelectedClass); 
		
		currentContentObj.DirectOne.length = 0;
		
		currentContentObj.DirectTwo1.length = 0;
		currentContentObj.DirectTwo2.length = 0;
		
		currentContentObj.DirectThree1.length = 0;
		currentContentObj.DirectThree2.length = 0;
		currentContentObj.DirectThree3.length = 0;
		
		currentContentObj.DirectFive1.length = 0;
		currentContentObj.DirectFive2.length = 0;
		currentContentObj.DirectFive3.length = 0;
		currentContentObj.DirectFive4.length = 0;
		currentContentObj.DirectFive5.length = 0;
		
		currentContentObj.DirectFour1.length = 0;
		currentContentObj.DirectFour2.length = 0;
		currentContentObj.DirectFour3.length = 0;
		currentContentObj.DirectFour4.length = 0;
		
		currentContentObj.ThreeGroup3.length = 0;
		currentContentObj.ThreeGroup6.length = 0;
		
		currentContentObj.DirectThreeSum.length = 0;
		currentContentObj.GroupThreeSum.length = 0;
		currentContentObj.GroupThree.length = 0;

		currentContentObj.DirectTwoSum.length = 0;
		currentContentObj.GroupTwoSum.length = 0;
		currentContentObj.GroupTwo.length = 0;
		
		currentContentObj.AllFive1.length = 0;
		currentContentObj.AllFive2.length = 0;
		currentContentObj.AllFive3.length = 0;
		currentContentObj.AllFive4.length = 0;
		currentContentObj.AllFive5.length = 0;
		
		currentContentObj.RANDOMONE1.length = 0;
		currentContentObj.RANDOMONE2.length = 0;
		currentContentObj.RANDOMONE3.length = 0;
		currentContentObj.RANDOMONE4.length = 0;
		currentContentObj.RANDOMONE5.length = 0;
		
		currentContentObj.RANDOMTWO1.length = 0;
		currentContentObj.RANDOMTWO2.length = 0;
		currentContentObj.RANDOMTWO3.length = 0;
		currentContentObj.RANDOMTWO4.length = 0;
		currentContentObj.RANDOMTWO5.length = 0;

		currentContentObj.BigSmallDoubleSingle1.length = 0;
		currentContentObj.BigSmallDoubleSingle2.length = 0;

		currentContentObj.Dan.length = 0;
		currentContentObj.units = 0;
		updateCurrentMsg();
	};
    window.clearCurrentSelect=function(){
    	clearCurrentContent();
    };
	var calcCurrentUnits = function() {
		var units = 0;
		
		var directOneSize = currentContentObj.DirectOne.length;
		
		var directTwo1Size = currentContentObj.DirectTwo1.length;
		var directTwo2Size = currentContentObj.DirectTwo2.length;

		var directThree1Size = currentContentObj.DirectThree1.length;
		var directThree2Size = currentContentObj.DirectThree2.length;
		var directThree3Size = currentContentObj.DirectThree3.length;

		var directFive1Size = currentContentObj.DirectFive1.length;
		var directFive2Size = currentContentObj.DirectFive2.length;
		var directFive3Size = currentContentObj.DirectFive3.length;
		var directFive4Size = currentContentObj.DirectFive4.length;
		var directFive5Size = currentContentObj.DirectFive5.length;

		var directFour1Size = currentContentObj.DirectFour1.length;
		var directFour2Size = currentContentObj.DirectFour2.length;
		var directFour3Size = currentContentObj.DirectFour3.length;
		var directFour4Size = currentContentObj.DirectFour4.length;
		
		var threeGroup3Size = currentContentObj.ThreeGroup3.length;
		var threeGroup6Size = currentContentObj.ThreeGroup6.length;
		var directThreeSumSize = currentContentObj.DirectThreeSum.length;
		var groupThreeSumSize = currentContentObj.GroupThreeSum.length;
		var groupThreeSize = currentContentObj.GroupThree.length;

		var directTwoSumSize = currentContentObj.DirectTwoSum.length;
		var groupTwoSumSize = currentContentObj.GroupTwoSum.length;
		var groupTwoSize = currentContentObj.GroupTwo.length;

		var allFive1Size = currentContentObj.AllFive1.length;
		var allFive2Size = currentContentObj.AllFive2.length;
		var allFive3Size = currentContentObj.AllFive3.length;
		var allFive4Size = currentContentObj.AllFive4.length;
		var allFive5Size = currentContentObj.AllFive5.length;
		
		
		var random1Size = currentContentObj.RANDOMONE1.length;
		var random2Size = currentContentObj.RANDOMONE2.length;
		var random3Size = currentContentObj.RANDOMONE3.length;
		var random4Size = currentContentObj.RANDOMONE4.length;
		var random5Size = currentContentObj.RANDOMONE5.length;
		
		var random2_1Size = currentContentObj.RANDOMTWO1.length;
		var random2_2Size = currentContentObj.RANDOMTWO2.length;
		var random2_3Size = currentContentObj.RANDOMTWO3.length;
		var random2_4Size = currentContentObj.RANDOMTWO4.length;
		var random2_5Size = currentContentObj.RANDOMTWO5.length;

		var bigSmallDoubleSingleSize1 = currentContentObj.BigSmallDoubleSingle1.length;
		var bigSmallDoubleSingleSize2 = currentContentObj.BigSmallDoubleSingle2.length;

		var danSize = currentContentObj.Dan.length;

		
		if (directFive1Size!=0&&directFive2Size!=0&&directFive3Size!=0&&directFive4Size!=0&&directFive5Size!=0) {
			/////选的是五星直选玩法
			units = directFive1Size*directFive2Size*directFive3Size*directFive4Size*directFive5Size;
		}else if (directFour1Size!=0&&directFour2Size!=0&&directFour3Size!=0&&directFour4Size!=0) {
			/////选的是四星直选玩法
			units = directFour1Size*directFour2Size*directFour3Size*directFour4Size;
		} else if (allFive1Size!=0&&allFive2Size!=0&&allFive3Size!=0&&allFive4Size!=0&&allFive5Size!=0) {
			/////选的是五星通选
			units = allFive1Size*allFive2Size*allFive3Size*allFive4Size*allFive5Size;
		} else if (directThree1Size!=0&&directThree2Size!=0&&directThree3Size!=0) {
			/////选的是三星直选玩法
			units = directThree1Size*directThree2Size*directThree3Size;
		} else if (directTwo1Size!=0&&directTwo2Size!=0) {
			/////选的是二星直选玩法
			units = directTwo1Size*directTwo2Size;
		} else if (directOneSize!=0) {
			/////选的是一星直选玩法
			units = directOneSize;
		} else if(directTwoSumSize!=0) {
		   //选的是二星直选和值
			for ( var i = 0; i < currentContentObj.DirectTwoSum.length; i++) {
				units += Direct_Two_Sum[parseInt(currentContentObj.DirectTwoSum[i])];
			}
		} else  if(directThreeSumSize!=0) {
			//选的是三星直选和值
			for ( var i = 0; i < currentContentObj.DirectThreeSum.length; i++) {
				units += Direct_Three_Sum[parseInt(currentContentObj.DirectThreeSum[i])];
			}
		}else if(groupTwoSumSize!=0) {
			   //选的是二星组选和值
				for ( var i = 0; i < currentContentObj.GroupTwoSum.length; i++) {
					units += Group_Two_Sum[parseInt(currentContentObj.GroupTwoSum[i])];
				}
		} else if(groupThreeSumSize!=0) {
			//选的是三星组选和值
			for ( var i = 0; i < currentContentObj.GroupThreeSum.length; i++) {
				units += Group_Three_Sum[parseInt(currentContentObj.GroupThreeSum[i])];
			}
					
		}else  if(threeGroup3Size!=0){
				/////选的是组3玩法
			  if(danSize==1) {
				  units = threeGroup3Size;
			  } else {
				units = threeGroup3Size*(threeGroup3Size-1);
			  }
		}else if(threeGroup6Size>0){
			    /////选的是组6玩法
			 if(danSize>0) {
				  units = calUnit(3,threeGroup6Size,danSize);
			  } else {
				units = comp(3 , threeGroup6Size);
			  }
		}else if(groupTwoSize>0){
				/////两星组选复试
			  if(danSize>0) {
				  units = calUnit(2,groupTwoSize,danSize);
			  } else {
				    if(groupTwoSize<2)units=0;
					else if(groupTwoSize==2)units=1;
					else units=comp(groupTwoSize,2); 
			  }
		}else if(bigSmallDoubleSingleSize1!=0&&bigSmallDoubleSingleSize2!=0){
			/////大小双单
			 units = bigSmallDoubleSingleSize1*bigSmallDoubleSingleSize2;
		}else if (random1Size!=0||random2Size!=0||random3Size!=0||random4Size!=0||random5Size!=0) {
			/////选的是任选1
			units = random1Size+random2Size+random3Size+random4Size+random5Size;
		}
		else if (random2_1Size!=0||random2_2Size!=0||random2_3Size!=0||random2_4Size!=0||random2_5Size!=0) {
			/////选的是任选2
			var bets = [];
			if(random2_1Size!=0){
				bets.push(random2_1Size);
			}
			if(random2_2Size!=0){
				bets.push(random2_2Size);
			}
			if(random2_3Size!=0){
				bets.push(random2_3Size);
			}
			if(random2_4Size!=0){
				bets.push(random2_4Size);
			}
			if(random2_5Size!=0){
				bets.push(random2_5Size);
			}
			bets.sort();
			var count = 0;
			C3(bets.length, 2, function(comb, n, m) {
				var c = 1;
				var pos = 0;
				for ( var i = 0; i < n; i++) {
					if (comb[i] == true) {
						c *= bets[i];
						pos++;
						if (pos == m)
							break;
					}
				}
				count += c;
			});
			
			
			units = count;
		}
		//TODO 需加玩法
		else {
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
	
	
	//增减注数
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
	
	//更新方案内容
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
	var endNumSplitWord='-';
	
	
	var pushContent = function(contentObj) {
		var selectObj = document.getElementById('createForm_select_content');
		var text = '';
		var value = contentObj.units + ':';

		var directOneSize = contentObj.DirectOne.length;
		
		var directTwo1Size = contentObj.DirectTwo1.length;
		var directTwo2Size = contentObj.DirectTwo2.length;

		var directThree1Size = contentObj.DirectThree1.length;
		var directThree2Size = contentObj.DirectThree2.length;
		var directThree3Size = contentObj.DirectThree3.length;

		var directFive1Size = contentObj.DirectFive1.length;
		var directFive2Size = contentObj.DirectFive2.length;
		var directFive3Size = contentObj.DirectFive3.length;
		var directFive4Size = contentObj.DirectFive4.length;
		var directFive5Size = contentObj.DirectFive5.length;

		var directFour1Size = contentObj.DirectFour1.length;
		var directFour2Size = contentObj.DirectFour2.length;
		var directFour3Size = contentObj.DirectFour3.length;
		var directFour4Size = contentObj.DirectFour4.length;
		
		var threeGroup3Size = contentObj.ThreeGroup3.length;
		var threeGroup6Size = contentObj.ThreeGroup6.length;
		var directThreeSumSize = contentObj.DirectThreeSum.length;
		var groupThreeSumSize = contentObj.GroupThreeSum.length;
		var groupThreeSize = contentObj.GroupThree.length;

		var directTwoSumSize = contentObj.DirectTwoSum.length;
		var groupTwoSumSize = contentObj.GroupTwoSum.length;
		var groupTwoSize = contentObj.GroupTwo.length;

		var allFive1Size = contentObj.AllFive1.length;
		var allFive2Size = contentObj.AllFive2.length;
		var allFive3Size = contentObj.AllFive3.length;
		var allFive4Size = contentObj.AllFive4.length;
		var allFive5Size = contentObj.AllFive5.length;

		
		var random1Size = contentObj.RANDOMONE1.length;
		var random2Size = contentObj.RANDOMONE2.length;
		var random3Size = contentObj.RANDOMONE3.length;
		var random4Size = contentObj.RANDOMONE4.length;
		var random5Size = contentObj.RANDOMONE5.length;
		
		var random2_1Size = contentObj.RANDOMTWO1.length;
		var random2_2Size = contentObj.RANDOMTWO2.length;
		var random2_3Size = contentObj.RANDOMTWO3.length;
		var random2_4Size = contentObj.RANDOMTWO4.length;
		var random2_5Size = contentObj.RANDOMTWO5.length;
		
		var bigSmallDoubleSingleSize1 = contentObj.BigSmallDoubleSingle1.length;
		var bigSmallDoubleSingleSize2 = contentObj.BigSmallDoubleSingle2.length;

		var danSize = contentObj.Dan.length;
		
		if (directFive1Size!=0&&directFive2Size!=0&&directFive3Size!=0&&directFive4Size!=0&&directFive5Size!=0) {
		    /////选的是五星直选玩法
			text += "[五星直选]：";
			for ( var i = 0, ball, l = directFive1Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFive1[i]);
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
			for ( var i = 0, ball, l = directFive2Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFive2[i]);
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
			for ( var i = 0, ball, l = directFive3Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFive3[i]);
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
			for ( var i = 0, ball, l = directFive4Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFive4[i]);
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
			for ( var i = 0, ball, l = directFive5Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFive5[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		} 
		else if (directFour1Size!=0&&directFour2Size!=0&&directFour3Size!=0&&directFour4Size!=0) {
		    /////选的是四星直选玩法
			text += "[四星直选]：";
			for ( var i = 0, ball, l = directFour1Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFour1[i]);
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
			for ( var i = 0, ball, l = directFour2Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFour2[i]);
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
			for ( var i = 0, ball, l = directFour3Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFour3[i]);
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
			for ( var i = 0, ball, l = directFour4Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectFour4[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
			
		} 
		
		else if (allFive1Size!=0&&allFive2Size!=0&&allFive3Size!=0&&allFive4Size!=0&&allFive5Size!=0) {
			text += "[五星通选]：";
			for ( var i = 0, ball, l = allFive1Size; i < l; i++) {
				ball = getBallStr(contentObj.AllFive1[i]);
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
			for ( var i = 0, ball, l = allFive2Size; i < l; i++) {
				ball = getBallStr(contentObj.AllFive2[i]);
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
			for ( var i = 0, ball, l = allFive3Size; i < l; i++) {
				ball = getBallStr(contentObj.AllFive3[i]);
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
			for ( var i = 0, ball, l = allFive4Size; i < l; i++) {
				ball = getBallStr(contentObj.AllFive4[i]);
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
			for ( var i = 0, ball, l = allFive5Size; i < l; i++) {
				ball = getBallStr(contentObj.AllFive5[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}
		
		
		
		
		else if(directThree1Size!=0&&directThree2Size!=0&&directThree3Size!=0) {
				text += "[三星直选]：";
				for ( var i = 0, ball, l = directThree1Size; i < l; i++) {
					ball = getBallStr(contentObj.DirectThree1[i]);
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
				for ( var i = 0, ball, l = directThree2Size; i < l; i++) {
					ball = getBallStr(contentObj.DirectThree2[i]);
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
				for ( var i = 0, ball, l = directThree3Size; i < l; i++) {
					ball = getBallStr(contentObj.DirectThree3[i]);
					text += ball;
					value += ball;

					if (i != l - 1) {
						text += numSplitWord;
						value += numSplitWord;
					}
				}
		} else if(directTwo1Size!=0&&directTwo2Size!=0) {
			text += "[两星直选]：";
			for ( var i = 0, ball, l = directTwo1Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectTwo1[i]);
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
			for ( var i = 0, ball, l = directTwo2Size; i < l; i++) {
				ball = getBallStr(contentObj.DirectTwo2[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
	} else if(directOneSize!=0) {
		
		text += '[一星]:';
		for ( var i = 0, ball, l = contentObj.DirectOne.length; i < l; i++) {
			ball = getBallStr(contentObj.DirectOne[i]);
			text += ball;
			value += ball;
			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	} 
	
	else if(directTwoSumSize!=0){
		text += "[二星直选]：";
	    /////选的是直选和值玩法
		for ( var i = 0, ball, l = directTwoSumSize; i < l; i++) {
			ball = getBallStr(contentObj.DirectTwoSum[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
		
	else if(directThreeSumSize!=0){
		text += "[三星直选]：";
	    /////选的是直选和值玩法
		for ( var i = 0, ball, l = directThreeSumSize; i < l; i++) {
			ball = getBallStr(contentObj.DirectThreeSum[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
		
	else if(groupTwoSumSize!=0){
		text += "[两星组选]：";
	    /////选的是直选和值玩法
		for ( var i = 0, ball, l = groupTwoSumSize; i < l; i++) {
			ball = getBallStr(contentObj.GroupTwoSum[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
		
	else if(groupThreeSumSize!=0){
		text += "[三星组选]：";
	    /////选的是直选和值玩法
		for ( var i = 0, ball, l = groupThreeSumSize; i < l; i++) {
			ball = getBallStr(contentObj.GroupThreeSum[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}	
	else if(threeGroup3Size>=2||(contentObj.Dan.length==1&&threeGroup3Size>=1)){
			text += "[三星组三]：";
			if(contentObj.Dan.length==1) {
				ball = getBallStr(contentObj.Dan[0]);
				text += ball;
				value += ball;
				text += numSplitWord;
				value += numSplitWord;
				//重号
				text += ball;
				value += ball;
				text += '_';
				value += ';';
			}
		
		for ( var i = 0, ball, l = threeGroup3Size; i < l; i++) {
			ball = getBallStr(contentObj.ThreeGroup3[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
	
	
	else if(threeGroup6Size>=3||(contentObj.Dan.length>0&&threeGroup6Size>0)){
	    /////选的是组6玩法
		text += "[三星组六]：";
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
		
		for ( var i = 0, ball, l = threeGroup6Size; i < l; i++) {
			ball = getBallStr(contentObj.ThreeGroup6[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
	
	else if(groupTwoSize>0){
		text += "[两星组选]：";
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
		
		/////选的是两星组选复试玩法
		for ( var i = 0, ball, l = groupTwoSize; i < l; i++) {
			ball = getBallStr(contentObj.GroupTwo[i]);
			text += ball;
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
		
	else if(groupThreeSize>2){
		text += "[三星组选]：";
		/////选的是两星组选复试玩法
		for ( var i = 0, ball, l = groupThreeSize; i < l; i++) {
			ball = getBallStr(contentObj.GroupThree[i]);
			text += ball;
			value += ball;
			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			} 
		}
	}
		
	else if(bigSmallDoubleSingleSize1!=0&&bigSmallDoubleSingleSize2!=0) {
	    /////选的是三星直选玩法
		text += "[大小单双]：";
		for ( var i = 0, ball, l = bigSmallDoubleSingleSize1; i < l; i++) {
			ball = getBallStr(contentObj.BigSmallDoubleSingle1[i]);
			text += BigSmallDoubleSingleText[parseInt(ball)];
			value += ball;
			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			}else{
				text += endNumSplitWord;
				value += endNumSplitWord;
			}
		}
		
		for ( var i = 0, ball, l = bigSmallDoubleSingleSize2; i < l; i++) {
			ball = getBallStr(contentObj.BigSmallDoubleSingle2[i]);
			text += BigSmallDoubleSingleText[parseInt(ball)];
			value += ball;

			if (i != l - 1) {
				text += numSplitWord;
				value += numSplitWord;
			}
		}
	}	
		
	else if (random1Size!=0||random2Size!=0||random3Size!=0||random4Size!=0||random5Size!=0) {
		text += "[任选一]：";
		if(random1Size!=0){
			for ( var i = 0, ball, l = random1Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMONE1[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random2Size!=0){
			for ( var i = 0, ball, l = random2Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMONE2[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random3Size!=0){
			for ( var i = 0, ball, l = random3Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMONE3[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random4Size!=0){
			for ( var i = 0, ball, l = random4Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMONE4[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random5Size!=0){
			for ( var i = 0, ball, l = random5Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMONE5[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else{
			text += "*";
			value += "99";
		}
		
		
	}	
	else if (random2_1Size!=0||random2_2Size!=0||random2_3Size!=0||random2_4Size!=0||random2_5Size!=0) {
		text += "[任选二]：";
		if(random2_1Size!=0){
			for ( var i = 0, ball, l = random2_1Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMTWO1[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random2_2Size!=0){
			for ( var i = 0, ball, l = random2_2Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMTWO2[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random2_3Size!=0){
			for ( var i = 0, ball, l = random2_3Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMTWO3[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random2_4Size!=0){
			for ( var i = 0, ball, l = random2_4Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMTWO4[i]);
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
		}else{
			text += "*"+endNumSplitWord;
			value += "99"+endNumSplitWord;
		}
		if(random2_5Size!=0){
			for ( var i = 0, ball, l = random2_5Size; i < l; i++) {
				ball = getBallStr(contentObj.RANDOMTWO5[i]);
				text += ball;
				value += ball;

				if (i != l - 1) {
					text += numSplitWord;
					value += numSplitWord;
				}
			}
		}else{
			text += "*";
			value += "99";
		}
		
		
	}	
	else {
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
	
	var TWO_SUM_BALL_ARR = [];
	for ( var i = 0; i < 19; i++) {
		TWO_SUM_BALL_ARR[i] = i;
	}
	
	var THREE_SUM_BALL_ARR = [];
	for ( var i = 0; i < 28; i++) {
		THREE_SUM_BALL_ARR[i] = i;
	}
	
	var BIG_SMALL_DOUBLE_SINGLE_BALL_ARR = [1,2,4,5];
	
	var randomSort = function(){
		var randomNum = Math.random();
		if(randomNum > 0.5)
			return 1;
		else if(randomNum < 0.5)
			return -1;
		else
			return 0;
	};
	//四星直选 机选
	window.DirectFourRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					DirectOne : [], // 一星 直选 个位
					DirectTwo1 : [], //二星 直选 个位
					DirectTwo2 : [], //二星 直选  十位
					DirectThree1 : [],  // 三星 直选 个位
					DirectThree2 : [],  // 三星 直选 十位
					DirectThree3 : [], // 三星 直选 百位
					
					DirectFive1: [], // 直选
					DirectFive2: [], // 直选
					DirectFive3: [], // 直选
					DirectFive4: [], // 直选
					DirectFive5: [], // 直选
					
					DirectFour1:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),// 四星 直选 个位  
					DirectFour2:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),// 四星 直选 十位  
					DirectFour3:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),// 四星 直选 百位  
					DirectFour4:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),// 四星 直选 千位   
					
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive3 : [],// 五星 通选 百位
					AllFive4 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	//五星直选 机选
	window.DirectFiveRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					DirectOne : [], // 一星 直选 个位
					DirectTwo1 : [], //二星 直选 个位
					DirectTwo2 : [], //二星 直选  十位
					DirectThree1 : [],  // 三星 直选 个位
					DirectThree2 : [],  // 三星 直选 十位
					DirectThree3 : [], // 三星 直选 百位
					
					DirectFive1: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					DirectFive2: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					DirectFive3: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					DirectFive4: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					DirectFive5: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位   
					
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive3 : [],// 五星 通选 百位
					AllFive4 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	//五星通选 机选
	window.AllFiveRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					DirectOne : [], // 一星 直选 个位
					DirectTwo1 : [], //二星 直选 个位
					DirectTwo2 : [], //二星 直选  十位
					DirectThree1 : [],  // 三星 直选 个位
					DirectThree2 : [],  // 三星 直选 十位
					DirectThree3 : [], // 三星 直选 百位
					
					DirectFive1 : [],
					DirectFive2 : [],
					DirectFive3 : [],
					DirectFive4 : [],
					DirectFive5 : [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive3 : [],// 五星 通选 百位
					AllFive4 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位
					
					AllFive1: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					AllFive2: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					AllFive3: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					AllFive4: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					AllFive5: copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
				
					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					
					Dan : [], //胆码
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	
	
	//三星直选 机选
	window.DirectThreeRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						DirectOne : [], // 一星 直选 个位
						DirectTwo1 : [], //二星 直选 个位
						DirectTwo2 : [], //二星 直选  十位
						DirectThree1 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
						DirectThree2 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
						DirectThree3 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
						
						DirectFive1: [],
						DirectFive2: [],
						DirectFive3: [],
						DirectFive4: [],
						DirectFive5: [],
						DirectFour1:[],// 四星 直选 个位  
						DirectFour2:[],// 四星 直选 十位  
						DirectFour3:[],// 四星 直选 百位  
						DirectFour4:[],// 四星 直选 千位  
						ThreeGroup3 : [],// 三星组三 
						ThreeGroup6 : [],// 三星组六 
	
						DirectThreeSum : [], // 三星直选和值
						GroupThreeSum : [], // 三星组选和值
						GroupThree: [], // 三星组选
						
						DirectTwoSum : [], // 二星直选和值
						GroupTwoSum : [], // 二星组选和值
						GroupTwo: [], // 二星组选
						
						AllFive1 : [],// 五星 通选 个位
						AllFive2 : [],// 五星 通选 十位
						AllFive3 : [],// 五星 通选 百位
						AllFive4 : [],// 五星 通选 千位
						AllFive5 : [],// 五星 通选 万位
	
						BigSmallDoubleSingle1:[],// 大小双单 十位
						BigSmallDoubleSingle2:[],// 大小双单 各位
						RANDOMONE1:[],//  个位  //任选1 2
						RANDOMONE2:[],//  十位  //任选1 2
						RANDOMONE3:[],//  百位  //任选1 2
						RANDOMONE4:[],//  千位  //任选1 2
						RANDOMONE5:[],//  万位  //任选1 2
						
						RANDOMTWO1:[],//  个位  //任选2
						RANDOMTWO2:[],//  十位  //任选2
						RANDOMTWO3:[],//  百位  //任选2
						RANDOMTWO4:[],//  千位  //任选2
						RANDOMTWO5:[],//  万位  //任选2
						Dan : [], //胆码
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(3,randomUnits,false);
		}
	};
	
	window.DirectTwoRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						DirectOne : [], // 一星 直选 个位
						
						DirectTwo1 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
						DirectTwo2 : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
						
						DirectThree1 : [],
						DirectThree2 : [],
						DirectThree3 : [],
						
						DirectFive1: [],
						DirectFive2: [],
						DirectFive3: [],
						DirectFive4: [],
						DirectFive5: [],
						DirectFour1:[],// 四星 直选 个位  
						DirectFour2:[],// 四星 直选 十位  
						DirectFour3:[],// 四星 直选 百位  
						DirectFour4:[],// 四星 直选 千位  
						ThreeGroup3 : [],// 三星组三 
						ThreeGroup6 : [],// 三星组六 
	
						DirectThreeSum : [], // 三星直选和值
						GroupThreeSum : [], // 三星组选和值
						GroupThree: [], // 三星组选
						
						DirectTwoSum : [], // 二星直选和值
						GroupTwoSum : [], // 二星组选和值
						GroupTwo: [], // 二星组选
						
						AllFive1 : [],// 五星 通选 个位
						AllFive2 : [],// 五星 通选 十位
						AllFive4 : [],// 五星 通选 百位
						AllFive3 : [],// 五星 通选 千位
						AllFive5 : [],// 五星 通选 万位
	
						BigSmallDoubleSingle1:[],// 大小双单 十位
						BigSmallDoubleSingle2:[],// 大小双单 各位
						RANDOMONE1:[],//  个位  //任选1 2
						RANDOMONE2:[],//  十位  //任选1 2
						RANDOMONE3:[],//  百位  //任选1 2
						RANDOMONE4:[],//  千位  //任选1 2
						RANDOMONE5:[],//  万位  //任选1 2
						
						RANDOMTWO1:[],//  个位  //任选2
						RANDOMTWO2:[],//  十位  //任选2
						RANDOMTWO3:[],//  百位  //任选2
						RANDOMTWO4:[],//  千位  //任选2
						RANDOMTWO5:[],//  万位  //任选2
						Dan : [], //胆码
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits,false);
		}
	};
	//任选1 机选
	window.RANDOMONERandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var num = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var contentObj = {
					DirectOne : [], // 直选
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : 1
			};
			var mod5=parseInt(num)%5;
		    eval("contentObj.RANDOMONE"+(mod5+1)+".push("+parseInt(num)+")");
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	//任选2 机选
	window.RANDOMTWORandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var num = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var num1 = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var contentObj = {
					DirectOne : [], // 直选
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : 1
			};
			var mod5=parseInt(num)%5;
			var mod5_1=parseInt(num1)%5;
			if(mod5_1==mod5){
				if(mod5_1==0){
					mod5_1 = mod5_1+1;
				}else{
					mod5_1 = mod5_1-1;
				}
			}
		    eval("contentObj.RANDOMTWO"+(mod5+1)+".push("+parseInt(num)+")");
		    eval("contentObj.RANDOMTWO"+(mod5_1+1)+".push("+parseInt(num1)+")");
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	//一星直选 机选
	window.DirectOneRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					DirectOne : copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc), // 直选
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [], // 二星直选和值
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					
					Dan : [], //胆码
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	//和值机选
	window.DirectTwoSumRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = TWO_SUM_BALL_ARR.slice(0, TWO_SUM_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var value = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var unit = Direct_Two_Sum[parseInt(value)];
			var contentObj = {
					DirectOne : [], 
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : value , 
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					
					Dan : [], //胆码
					units : unit
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
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
	
	window.GroupTwoSumRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = TWO_SUM_BALL_ARR.slice(0, TWO_SUM_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var value = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var unit = Group_Two_Sum[parseInt(value)];
			var contentObj = {
					DirectOne : [], 
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [],
					GroupTwoSum : value, 
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					
					Dan : [], //胆码
					units : unit
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	window.GroupThreeSumRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = THREE_SUM_BALL_ARR.slice(0, THREE_SUM_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var value = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var unit = Group_Three_Sum[parseInt(value)];
			var contentObj = {
					DirectOne : [], 
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : value, 
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [],
					GroupTwoSum : [],
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : unit
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	window.DirectThreeSumRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = THREE_SUM_BALL_ARR.slice(0, THREE_SUM_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var value = copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc);
			var unit = Direct_Three_Sum[parseInt(value)];
			var contentObj = {
					DirectOne : [], 
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : value, 
					GroupThreeSum : [],
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [],
					GroupTwoSum : [],
					GroupTwo: [], // 二星组选
					
					AllFive1 : [],// 五星 通选 个位
					AllFive2 : [],// 五星 通选 十位
					AllFive4 : [],// 五星 通选 百位
					AllFive3 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:[],// 大小双单 十位
					BigSmallDoubleSingle2:[],// 大小双单 各位
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : unit
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	 
	
	
	window.GroupTwoRandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						DirectOne : [], 
						
						DirectTwo1 : [],
						DirectTwo2 : [],
						
						DirectThree1 : [],
						DirectThree2 : [],
						DirectThree3 : [],
						
						DirectFive1: [],
						DirectFive2: [],
						DirectFive3: [],
						DirectFive4: [],
						DirectFive5: [],
						DirectFour1:[],// 四星 直选 个位  
						DirectFour2:[],// 四星 直选 十位  
						DirectFour3:[],// 四星 直选 百位  
						DirectFour4:[],// 四星 直选 千位  
						ThreeGroup3 : [],// 三星组三 
						ThreeGroup6 : [],// 三星组六 
	
						DirectThreeSum : [], // 三星直选和值
						GroupThreeSum : [], // 三星组选和值
						GroupThree: [], // 三星组选
						
						DirectTwoSum : [],
						GroupTwoSum : [], // 二星组选和值
						GroupTwo: copyDirectBallArr.sort(randomSort).slice(0, 2).sort(asc), 
						
						AllFive1 : [],// 五星 通选 个位
						AllFive2 : [],// 五星 通选 十位
						AllFive4 : [],// 五星 通选 百位
						AllFive3 : [],// 五星 通选 千位
						AllFive5 : [],// 五星 通选 万位
	
						BigSmallDoubleSingle1:[],// 大小双单 十位
						BigSmallDoubleSingle2:[],// 大小双单 各位
						RANDOMONE1:[],//  个位  //任选1 2
						RANDOMONE2:[],//  十位  //任选1 2
						RANDOMONE3:[],//  百位  //任选1 2
						RANDOMONE4:[],//  千位  //任选1 2
						RANDOMONE5:[],//  万位  //任选1 2
						
						RANDOMTWO1:[],//  个位  //任选2
						RANDOMTWO2:[],//  十位  //任选2
						RANDOMTWO3:[],//  百位  //任选2
						RANDOMTWO4:[],//  千位  //任选2
						RANDOMTWO5:[],//  万位  //任选2
						Dan : [], //胆码
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits,false);
		}
	};
	
	window.BigSmallDoubleSingleRandomSelect = function(randomUnits) {
		if (randomUnits <= 0)
			return;
		var copyDirectBallArr = BIG_SMALL_DOUBLE_SINGLE_BALL_ARR.slice(0, BIG_SMALL_DOUBLE_SINGLE_BALL_ARR.length);
		var randomContent = [];
		while (randomContent.length < randomUnits) {
			var contentObj = {
					DirectOne : [], 
					
					DirectTwo1 : [],
					DirectTwo2 : [],
					
					DirectThree1 : [],
					DirectThree2 : [],
					DirectThree3 : [],
					
					DirectFive1: [],
					DirectFive2: [],
					DirectFive3: [],
					DirectFive4: [],
					DirectFive5: [],
					DirectFour1:[],// 四星 直选 个位  
					DirectFour2:[],// 四星 直选 十位  
					DirectFour3:[],// 四星 直选 百位  
					DirectFour4:[],// 四星 直选 千位  
					ThreeGroup3 : [],// 三星组三 
					ThreeGroup6 : [],// 三星组六 

					DirectThreeSum : [], // 三星直选和值
					GroupThreeSum : [], // 三星组选和值
					GroupThree: [], // 三星组选
					
					DirectTwoSum : [],
					GroupTwoSum : [], // 二星组选和值
					GroupTwo: [], 
					
					AllFive1 :[],
					AllFive2 :[],
					AllFive3 : [],// 五星 通选 百位
					AllFive4 : [],// 五星 通选 千位
					AllFive5 : [],// 五星 通选 万位

					BigSmallDoubleSingle1:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),
					BigSmallDoubleSingle2:copyDirectBallArr.sort(randomSort).slice(0, 1).sort(asc),
					RANDOMONE1:[],//  个位  //任选1 2
					RANDOMONE2:[],//  十位  //任选1 2
					RANDOMONE3:[],//  百位  //任选1 2
					RANDOMONE4:[],//  千位  //任选1 2
					RANDOMONE5:[],//  万位  //任选1 2
					
					RANDOMTWO1:[],//  个位  //任选2
					RANDOMTWO2:[],//  十位  //任选2
					RANDOMTWO3:[],//  百位  //任选2
					RANDOMTWO4:[],//  千位  //任选2
					RANDOMTWO5:[],//  万位  //任选2
					Dan : [], //胆码
					units : 1
			};
			randomContent.push(contentObj);
		}
		for ( var i = 0, l = randomContent.length; i < l; i++) {
			pushContent(randomContent[i]);
		}
	};
	
	
	 //组三 组六 机选
	window.ThreeGroup3RandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){	
			if (randomUnits <= 0)
				return;
			var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						DirectOne : [], 
						
						DirectTwo1 : [],
						DirectTwo2 : [],
						
						DirectThree1 : [],
						DirectThree2 : [],
						DirectThree3 : [],
						
						DirectFive1: [],
						DirectFive2: [],
						DirectFive3: [],
						DirectFive4: [],
						DirectFive5: [],
						DirectFour1:[],// 四星 直选 个位  
						DirectFour2:[],// 四星 直选 十位  
						DirectFour3:[],// 四星 直选 百位  
						DirectFour4:[],// 四星 直选 千位  
						ThreeGroup3 : copyDirectBallArr.sort(randomSort).slice(0, 2).sort(asc),
						ThreeGroup6 : [],// 三星组六 
	
						DirectThreeSum : [], // 三星直选和值
						GroupThreeSum : [], // 三星组选和值
						GroupThree: [], // 三星组选
						
						DirectTwoSum : [],
						GroupTwoSum : [], // 二星组选和值
						GroupTwo: [], 
						
						AllFive1 :[],
						AllFive2 :[],
						AllFive3 : [],// 五星 通选 百位
						AllFive4 : [],// 五星 通选 千位
						AllFive5 : [],// 五星 通选 万位
	
						BigSmallDoubleSingle1:[],
						BigSmallDoubleSingle2:[],
						RANDOMONE1:[],//  个位  //任选1 2
						RANDOMONE2:[],//  十位  //任选1 2
						RANDOMONE3:[],//  百位  //任选1 2
						RANDOMONE4:[],//  千位  //任选1 2
						RANDOMONE5:[],//  万位  //任选1 2
						
						RANDOMTWO1:[],//  个位  //任选2
						RANDOMTWO2:[],//  十位  //任选2
						RANDOMTWO3:[],//  百位  //任选2
						RANDOMTWO4:[],//  千位  //任选2
						RANDOMTWO5:[],//  万位  //任选2
						Dan : [], //胆码
						units : 2
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(2,randomUnits,true);
		}
	};
	
	window.ThreeGroup6RandomSelect = function(randomUnits) {
		if(isCompoundSalesMode()){
			if (randomUnits <= 0)
				return;
			var copyDirectBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
			var randomContent = [];
			while (randomContent.length < randomUnits) {
				var contentObj = {
						DirectOne : [], 
						
						DirectTwo1 : [],
						DirectTwo2 : [],
						
						DirectThree1 : [],
						DirectThree2 : [],
						DirectThree3 : [],
						
						DirectFive1: [],
						DirectFive2: [],
						DirectFive3: [],
						DirectFive4: [],
						DirectFive5: [],
						DirectFour1:[],// 四星 直选 个位  
						DirectFour2:[],// 四星 直选 十位  
						DirectFour3:[],// 四星 直选 百位  
						DirectFour4:[],// 四星 直选 千位  
						ThreeGroup3 : [],
						ThreeGroup6 : copyDirectBallArr.sort(randomSort).slice(0, 3).sort(asc),
	
						DirectThreeSum : [], // 三星直选和值
						GroupThreeSum : [], // 三星组选和值
						GroupThree: [], // 三星组选
						
						DirectTwoSum : [],
						GroupTwoSum : [], // 二星组选和值
						GroupTwo: [], 
						
						AllFive1 :[],
						AllFive2 :[],
						AllFive3 : [],// 五星 通选 百位
						AllFive4 : [],// 五星 通选 千位
						AllFive5 : [],// 五星 通选 万位
	
						BigSmallDoubleSingle1:[],
						BigSmallDoubleSingle2:[],
						RANDOMONE1:[],//  个位  //任选1 2
						RANDOMONE2:[],//  十位  //任选1 2
						RANDOMONE3:[],//  百位  //任选1 2
						RANDOMONE4:[],//  千位  //任选1 2
						RANDOMONE5:[],//  万位  //任选1 2
						
						RANDOMTWO1:[],//  个位  //任选2
						RANDOMTWO2:[],//  十位  //任选2
						RANDOMTWO3:[],//  百位  //任选2
						RANDOMTWO4:[],//  千位  //任选2
						RANDOMTWO5:[],//  万位  //任选2
						Dan : [], //胆码
						units : 1
				};
				randomContent.push(contentObj);
			}
			for ( var i = 0, l = randomContent.length; i < l; i++) {
				pushContent(randomContent[i]);
			}
		}else{
			singleRandomSelect(3,randomUnits,false);
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
		//判断是否为智能追号
		if("true"==document.getElementById('isCapacity').value) {
			document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr></table>";
		}
	};
	
	
	
	window.on_select_li =function(li){
		return;
		
	};
	
	
	
	//清除方案内容
	window.clearSelect = function(generateNum){
		var selectObj = document.getElementById('createForm_select_content');
		var lLi = document.getElementById(generateNum);
		var value = lLi.title;
		var arr = value.split(':');
		selectObj.removeChild(lLi);
		addOrSubBetUnits(0-parseInt(arr[0]));
	};
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////
	
	//一星直选
	$('#_directOne a[_name="all"]').bind("click", function(event) {
		chooseBallMethod(1,"all",1);
	});
	$('#_directOne a[_name="odd"]').bind("click", function(event) {
		chooseBallMethod(1,"odd",1);
	});
	$('#_directOne a[_name="even"]').bind("click", function(event) {
		chooseBallMethod(1,"even",1);
	});
	$('#_directOne a[_name="big"]').bind("click", function(event) {
		chooseBallMethod(1,"big",1);
	});
	$('#_directOne a[_name="small"]').bind("click", function(event) {
		chooseBallMethod(1,"small",1);
	});
	$('#_directOne a[_name="clear"]').bind("click", function(event) {
		chooseBallMethod(1,"clear",1);
	});
	
	  //二星直选
		$('#_directTwo_1 a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(2,"all",1);
		});
		$('#_directTwo_1 a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(2,"odd",1);
		});
		$('#_directTwo_1 a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(2,"even",1);
		});
		$('#_directTwo_1 a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(2,"big",1);
		});
		$('#_directTwo_1 a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(2,"small",1);
		});
		$('#_directTwo_1 a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(2,"clear",1);
		});
		
		$('#_directTwo_2 a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(2,"all",2);
		});
		$('#_directTwo_2 a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(2,"odd",2);
		});
		$('#_directTwo_2 a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(2,"even",2);
		});
		$('#_directTwo_2 a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(2,"big",2);
		});
		$('#_directTwo_2 a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(2,"small",2);
		});
		$('#_directTwo_2 a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(2,"clear",2);
		});
		
		
		
	
		$('#_directFive_1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(5,"all",1);});
		$('#_directFive_1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(5,"odd",1);});
		$('#_directFive_1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(5,"even",1);});
		$('#_directFive_1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(5,"big",1);});
		$('#_directFive_1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(5,"small",1);});
		$('#_directFive_1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(5,"clear",1);});
		
		$('#_directFive_2 a[_name="all"]').bind("click", function(event) {chooseBallMethod(5,"all",2);});
		$('#_directFive_2 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(5,"odd",2);});
		$('#_directFive_2 a[_name="even"]').bind("click", function(event) {chooseBallMethod(5,"even",2);});
		$('#_directFive_2 a[_name="big"]').bind("click", function(event) {chooseBallMethod(5,"big",2);});
		$('#_directFive_2 a[_name="small"]').bind("click", function(event) {chooseBallMethod(5,"small",2);});
		$('#_directFive_2 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(5,"clear",2);});
		
		$('#_directFive_3 a[_name="all"]').bind("click", function(event) {chooseBallMethod(5,"all",3);});
		$('#_directFive_3 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(5,"odd",3);});
		$('#_directFive_3 a[_name="even"]').bind("click", function(event) {chooseBallMethod(5,"even",3);});
		$('#_directFive_3 a[_name="big"]').bind("click", function(event) {chooseBallMethod(5,"big",3);});
		$('#_directFive_3 a[_name="small"]').bind("click", function(event) {chooseBallMethod(5,"small",3);});
		$('#_directFive_3 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(5,"clear",3);});
		
		$('#_directFive_4 a[_name="all"]').bind("click", function(event) {chooseBallMethod(5,"all",4);});
		$('#_directFive_4 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(5,"odd",4);});
		$('#_directFive_4 a[_name="even"]').bind("click", function(event) {chooseBallMethod(5,"even",4);});
		$('#_directFive_4 a[_name="big"]').bind("click", function(event) {chooseBallMethod(5,"big",4);});
		$('#_directFive_4 a[_name="small"]').bind("click", function(event) {chooseBallMethod(5,"small",4);});
		$('#_directFive_4 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(5,"clear",4);});
		
		$('#_directFive_5 a[_name="all"]').bind("click", function(event) {chooseBallMethod(5,"all",5);});
		$('#_directFive_5 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(5,"odd",5);});
		$('#_directFive_5 a[_name="even"]').bind("click", function(event) {chooseBallMethod(5,"even",5);});
		$('#_directFive_5 a[_name="big"]').bind("click", function(event) {chooseBallMethod(5,"big",5);});
		$('#_directFive_5 a[_name="small"]').bind("click", function(event) {chooseBallMethod(5,"small",5);});
		$('#_directFive_5 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(5,"clear",5);});
		
		
		$('#_RANDOMONE_1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(16,"all",1);});
		$('#_RANDOMONE_1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(16,"odd",1);});
		$('#_RANDOMONE_1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(16,"even",1);});
		$('#_RANDOMONE_1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(16,"big",1);});
		$('#_RANDOMONE_1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(16,"small",1);});
		$('#_RANDOMONE_1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(16,"clear",1);});
		
		$('#_RANDOMONE_2 a[_name="all"]').bind("click", function(event) {chooseBallMethod(16,"all",2);});
		$('#_RANDOMONE_2 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(16,"odd",2);});
		$('#_RANDOMONE_2 a[_name="even"]').bind("click", function(event) {chooseBallMethod(16,"even",2);});
		$('#_RANDOMONE_2 a[_name="big"]').bind("click", function(event) {chooseBallMethod(16,"big",2);});
		$('#_RANDOMONE_2 a[_name="small"]').bind("click", function(event) {chooseBallMethod(16,"small",2);});
		$('#_RANDOMONE_2 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(16,"clear",2);});
		
		$('#_RANDOMONE_3 a[_name="all"]').bind("click", function(event) {chooseBallMethod(16,"all",3);});
		$('#_RANDOMONE_3 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(16,"odd",3);});
		$('#_RANDOMONE_3 a[_name="even"]').bind("click", function(event) {chooseBallMethod(16,"even",3);});
		$('#_RANDOMONE_3 a[_name="big"]').bind("click", function(event) {chooseBallMethod(16,"big",3);});
		$('#_RANDOMONE_3 a[_name="small"]').bind("click", function(event) {chooseBallMethod(16,"small",3);});
		$('#_RANDOMONE_3 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(16,"clear",3);});
		
		$('#_RANDOMONE_4 a[_name="all"]').bind("click", function(event) {chooseBallMethod(16,"all",4);});
		$('#_RANDOMONE_4 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(16,"odd",4);});
		$('#_RANDOMONE_4 a[_name="even"]').bind("click", function(event) {chooseBallMethod(16,"even",4);});
		$('#_RANDOMONE_4 a[_name="big"]').bind("click", function(event) {chooseBallMethod(16,"big",4);});
		$('#_RANDOMONE_4 a[_name="small"]').bind("click", function(event) {chooseBallMethod(16,"small",4);});
		$('#_RANDOMONE_4 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(16,"clear",4);});
		
		$('#_RANDOMONE_5 a[_name="all"]').bind("click", function(event) {chooseBallMethod(16,"all",5);});
		$('#_RANDOMONE_5 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(16,"odd",5);});
		$('#_RANDOMONE_5 a[_name="even"]').bind("click", function(event) {chooseBallMethod(16,"even",5);});
		$('#_RANDOMONE_5 a[_name="big"]').bind("click", function(event) {chooseBallMethod(16,"big",5);});
		$('#_RANDOMONE_5 a[_name="small"]').bind("click", function(event) {chooseBallMethod(16,"small",5);});
		
		
		$('#_RANDOMTWO_1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(17,"all",1);});
		$('#_RANDOMTWO_1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(17,"odd",1);});
		$('#_RANDOMTWO_1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(17,"even",1);});
		$('#_RANDOMTWO_1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(17,"big",1);});
		$('#_RANDOMTWO_1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(17,"small",1);});
		$('#_RANDOMTWO_1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(17,"clear",1);});
		
		$('#_RANDOMTWO_2 a[_name="all"]').bind("click", function(event) {chooseBallMethod(17,"all",2);});
		$('#_RANDOMTWO_2 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(17,"odd",2);});
		$('#_RANDOMTWO_2 a[_name="even"]').bind("click", function(event) {chooseBallMethod(17,"even",2);});
		$('#_RANDOMTWO_2 a[_name="big"]').bind("click", function(event) {chooseBallMethod(17,"big",2);});
		$('#_RANDOMTWO_2 a[_name="small"]').bind("click", function(event) {chooseBallMethod(17,"small",2);});
		$('#_RANDOMTWO_2 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(17,"clear",2);});
		
		$('#_RANDOMTWO_3 a[_name="all"]').bind("click", function(event) {chooseBallMethod(17,"all",3);});
		$('#_RANDOMTWO_3 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(17,"odd",3);});
		$('#_RANDOMTWO_3 a[_name="even"]').bind("click", function(event) {chooseBallMethod(17,"even",3);});
		$('#_RANDOMTWO_3 a[_name="big"]').bind("click", function(event) {chooseBallMethod(17,"big",3);});
		$('#_RANDOMTWO_3 a[_name="small"]').bind("click", function(event) {chooseBallMethod(17,"small",3);});
		$('#_RANDOMTWO_3 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(17,"clear",3);});
		
		$('#_RANDOMTWO_4 a[_name="all"]').bind("click", function(event) {chooseBallMethod(17,"all",4);});
		$('#_RANDOMTWO_4 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(17,"odd",4);});
		$('#_RANDOMTWO_4 a[_name="even"]').bind("click", function(event) {chooseBallMethod(17,"even",4);});
		$('#_RANDOMTWO_4 a[_name="big"]').bind("click", function(event) {chooseBallMethod(17,"big",4);});
		$('#_RANDOMTWO_4 a[_name="small"]').bind("click", function(event) {chooseBallMethod(17,"small",4);});
		$('#_RANDOMTWO_4 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(17,"clear",4);});
		
		$('#_RANDOMTWO_5 a[_name="all"]').bind("click", function(event) {chooseBallMethod(17,"all",5);});
		$('#_RANDOMTWO_5 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(17,"odd",5);});
		$('#_RANDOMTWO_5 a[_name="even"]').bind("click", function(event) {chooseBallMethod(17,"even",5);});
		$('#_RANDOMTWO_5 a[_name="big"]').bind("click", function(event) {chooseBallMethod(17,"big",5);});
		$('#_RANDOMTWO_5 a[_name="small"]').bind("click", function(event) {chooseBallMethod(17,"small",5);});
		
		$('#_allFive_1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(6,"all",1);});
		$('#_allFive_1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(6,"odd",1);});
		$('#_allFive_1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(6,"even",1);});
		$('#_allFive_1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(6,"big",1);});
		$('#_allFive_1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(6,"small",1);});
		$('#_allFive_1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(6,"clear",1);});
		
		$('#_allFive_2 a[_name="all"]').bind("click", function(event) {chooseBallMethod(6,"all",2);});
		$('#_allFive_2 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(6,"odd",2);});
		$('#_allFive_2 a[_name="even"]').bind("click", function(event) {chooseBallMethod(6,"even",2);});
		$('#_allFive_2 a[_name="big"]').bind("click", function(event) {chooseBallMethod(6,"big",2);});
		$('#_allFive_2 a[_name="small"]').bind("click", function(event) {chooseBallMethod(6,"small",2);});
		$('#_allFive_2 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(6,"clear",2);});
		
		$('#_allFive_3 a[_name="all"]').bind("click", function(event) {chooseBallMethod(6,"all",3);});
		$('#_allFive_3 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(6,"odd",3);});
		$('#_allFive_3 a[_name="even"]').bind("click", function(event) {chooseBallMethod(6,"even",3);});
		$('#_allFive_3 a[_name="big"]').bind("click", function(event) {chooseBallMethod(6,"big",3);});
		$('#_allFive_3 a[_name="small"]').bind("click", function(event) {chooseBallMethod(6,"small",3);});
		$('#_allFive_3 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(6,"clear",3);});
		
		$('#_allFive_4 a[_name="all"]').bind("click", function(event) {chooseBallMethod(6,"all",4);});
		$('#_allFive_4 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(6,"odd",4);});
		$('#_allFive_4 a[_name="even"]').bind("click", function(event) {chooseBallMethod(6,"even",4);});
		$('#_allFive_4 a[_name="big"]').bind("click", function(event) {chooseBallMethod(6,"big",4);});
		$('#_allFive_4 a[_name="small"]').bind("click", function(event) {chooseBallMethod(6,"small",4);});
		$('#_allFive_4 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(6,"clear",4);});
		
		$('#_allFive_5 a[_name="all"]').bind("click", function(event) {chooseBallMethod(6,"all",5);});
		$('#_allFive_5 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(6,"odd",5);});
		$('#_allFive_5 a[_name="even"]').bind("click", function(event) {chooseBallMethod(6,"even",5);});
		$('#_allFive_5 a[_name="big"]').bind("click", function(event) {chooseBallMethod(6,"big",5);});
		$('#_allFive_5 a[_name="small"]').bind("click", function(event) {chooseBallMethod(6,"small",5);});
		$('#_allFive_5 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(6,"clear",5);});
		
		
		$('#_directFour_1 a[_name="all"]').bind("click", function(event) {chooseBallMethod(18,"all",1);});
		$('#_directFour_1 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(18,"odd",1);});
		$('#_directFour_1 a[_name="even"]').bind("click", function(event) {chooseBallMethod(18,"even",1);});
		$('#_directFour_1 a[_name="big"]').bind("click", function(event) {chooseBallMethod(18,"big",1);});
		$('#_directFour_1 a[_name="small"]').bind("click", function(event) {chooseBallMethod(18,"small",1);});
		$('#_directFour_1 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(18,"clear",1);});
		
		$('#_directFour_2 a[_name="all"]').bind("click", function(event) {chooseBallMethod(18,"all",2);});
		$('#_directFour_2 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(18,"odd",2);});
		$('#_directFour_2 a[_name="even"]').bind("click", function(event) {chooseBallMethod(18,"even",2);});
		$('#_directFour_2 a[_name="big"]').bind("click", function(event) {chooseBallMethod(18,"big",2);});
		$('#_directFour_2 a[_name="small"]').bind("click", function(event) {chooseBallMethod(18,"small",2);});
		$('#_directFour_2 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(18,"clear",2);});
		
		$('#_directFour_3 a[_name="all"]').bind("click", function(event) {chooseBallMethod(18,"all",3);});
		$('#_directFour_3 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(18,"odd",3);});
		$('#_directFour_3 a[_name="even"]').bind("click", function(event) {chooseBallMethod(18,"even",3);});
		$('#_directFour_3 a[_name="big"]').bind("click", function(event) {chooseBallMethod(18,"big",3);});
		$('#_directFour_3 a[_name="small"]').bind("click", function(event) {chooseBallMethod(18,"small",3);});
		$('#_directFour_3 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(18,"clear",3);});
		
		$('#_directFour_4 a[_name="all"]').bind("click", function(event) {chooseBallMethod(18,"all",4);});
		$('#_directFour_4 a[_name="odd"]').bind("click", function(event) {chooseBallMethod(18,"odd",4);});
		$('#_directFour_4 a[_name="even"]').bind("click", function(event) {chooseBallMethod(18,"even",4);});
		$('#_directFour_4 a[_name="big"]').bind("click", function(event) {chooseBallMethod(18,"big",4);});
		$('#_directFour_4 a[_name="small"]').bind("click", function(event) {chooseBallMethod(18,"small",4);});
		$('#_directFour_4 a[_name="clear"]').bind("click", function(event) {chooseBallMethod(18,"clear",4);});
		
	
		$('#_directThree_1 a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(3,"all",1);
		});
		$('#_directThree_1 a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(3,"odd",1);
		});
		$('#_directThree_1 a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(3,"even",1);
		});
		$('#_directThree_1 a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(3,"big",1);
		});
		$('#_directThree_1 a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(3,"small",1);
		});
		$('#_directThree_1 a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(3,"clear",1);
		});
		
		
		$('#_directThree_2 a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(3,"all",2);
		});
		$('#_directThree_2 a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(3,"odd",2);
		});
		$('#_directThree_2 a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(3,"even",2);
		});
		$('#_directThree_2 a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(3,"big",2);
		});
		$('#_directThree_2 a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(3,"small",2);
		});
		$('#_directThree_2 a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(3,"clear",2);
		});
		
		
		$('#_directThree_3 a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(3,"all",3);
		});
		$('#_directThree_3 a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(3,"odd",3);
		});
		$('#_directThree_3 a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(3,"even",3);
		});
		$('#_directThree_3 a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(3,"big",3);
		});
		$('#_directThree_3 a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(3,"small",3);
		});
		$('#_directThree_3 a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(3,"clear",3);
		});
		
		
		$('#GroupTwoTr a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(13,"all",1);
		});
		$('#GroupTwoTr a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(13,"odd",1);
		});
		$('#GroupTwoTr a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(13,"even",1);
		});
		$('#GroupTwoTr a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(13,"big",1);
		});
		$('#GroupTwoTr a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(13,"small",1);
		});
		$('#GroupTwoTr a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(13,"clear",1);
		});
		
		
		$('#ThreeGroup3Tr a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(14,"all",1);
		});
		$('#ThreeGroup3Tr a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(14,"odd",1);
		});
		$('#ThreeGroup3Tr a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(14,"even",1);
		});
		$('#ThreeGroup3Tr a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(14,"big",1);
		});
		$('#ThreeGroup3Tr a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(14,"small",1);
		});
		$('#ThreeGroup3Tr a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(14,"clear",1);
		});
		
		
		$('#ThreeGroup6Tr a[_name="all"]').bind("click", function(event) {
			chooseBallMethod(15,"all",1);
		});
		$('#ThreeGroup6Tr a[_name="odd"]').bind("click", function(event) {
			chooseBallMethod(15,"odd",1);
		});
		$('#ThreeGroup6Tr a[_name="even"]').bind("click", function(event) {
			chooseBallMethod(15,"even",1);
		});
		$('#ThreeGroup6Tr a[_name="big"]').bind("click", function(event) {
			chooseBallMethod(15,"big",1);
		});
		$('#ThreeGroup6Tr a[_name="small"]').bind("click", function(event) {
			chooseBallMethod(15,"small",1);
		});
		$('#ThreeGroup6Tr a[_name="clear"]').bind("click", function(event) {
			chooseBallMethod(15,"clear",1);
		});
		
		
	
	
	


	
	
	//////////////////////////////////////////////////////////////////////////////
	var clearChooseBallArea =  function(){
		var all;
		for (var int = 1; int <= 5; int++) {
		    all = $('#_directFive_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		for (var int = 1; int <= 5; int++) {
		    all = $('#_directFour_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		for (var int = 1; int <= 5; int++) {
		    all = $('#_allFive_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		
		
		for (var int = 1; int <= 5; int++) {
		    all = $('#_allFive_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		
		
		for (var int = 1; int <= 3; int++) {
		    all = $('#_directThree_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		for (var int = 1; int <= 2; int++) {
		    all = $('#_directTwo_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		//一星
	    all = $('#_directOne').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		
		all = $('#DirectTwoSum').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
			

		all = $('#GroupTwoSum').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		
		all = $('#DirectThreeSum').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#GroupThreeSum').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#ThreeGroup6').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#ThreeGroup3').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#GroupTwo').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		all = $('#GroupThree').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}

		all = $('#_bigSmallDoubleSingle').find('a');
		for ( var i = 0; i < all.length; i++) {
				all[i].className = unSelectedClass;
		}
		
		for (var int = 1; int <= 5; int++) {
		    all = $('#_RANDOMONE_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
		for (var int = 1; int <= 5; int++) {
		    all = $('#_RANDOMTWO_'+int).find('a');
			for ( var i = 0; i < all.length; i++) {
					all[i].className = unSelectedClass;
			}
		}
	};
	
	
	//全大小奇偶选择
	var chooseBallMethod=function(betType,oprType,line){
		  if(betType==5) {
				if(line==1){
					var ballArr=$('#area_box a[_name="DirectFive1"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFive1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="DirectFive2"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFive2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="DirectFive3"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFive3);
				}else if(line==4){
					var ballArr=$('#area_box a[_name="DirectFive4"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFive4);
				}else if(line==5){
					var ballArr=$('#area_box a[_name="DirectFive5"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFive5);
				}
		  } 
		  else   if(betType==18) {
				if(line==1){
					var ballArr=$('#area_box a[_name="DirectFour1"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFour1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="DirectFour2"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFour2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="DirectFour3"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFour3);
				}else if(line==4){
					var ballArr=$('#area_box a[_name="DirectFour4"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectFour4);
				}
		  } 
		  else if(betType==6) {
				if(line==1){
					var ballArr=$('#area_box a[_name="AllFive1"]');
					chooseMethod(ballArr,oprType,currentContentObj.AllFive1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="AllFive2"]');
					chooseMethod(ballArr,oprType,currentContentObj.AllFive2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="AllFive3"]');
					chooseMethod(ballArr,oprType,currentContentObj.AllFive3);
				}else if(line==4){
					var ballArr=$('#area_box a[_name="AllFive4"]');
					chooseMethod(ballArr,oprType,currentContentObj.AllFive4);
				}else if(line==5){
					var ballArr=$('#area_box a[_name="AllFive5"]');
					chooseMethod(ballArr,oprType,currentContentObj.AllFive5);
				}
		  } 
		  
		  else if(betType==3) {
			  if(line==1){
					var ballArr=$('#area_box a[_name="DirectThree1"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectThree1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="DirectThree2"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectThree2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="DirectThree3"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectThree3);
				}
			  
		  } 
		  
		  else if(betType==2) {
			    if(line==1){
					var ballArr=$('#area_box a[_name="DirectTwo1"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectTwo1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="DirectTwo2"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectTwo2);
				}
		  }
		  
		  else if(betType==13) {
			  var ballArr=$('#area_box a[_name="GroupTwo"]');
			  chooseMethod(ballArr,oprType,currentContentObj.GroupTwo);
			  
		  }
		  
		  else if(betType==14) {
			  var ballArr=$('#area_box a[_name="ThreeGroup3"]');
			  chooseMethod(ballArr,oprType,currentContentObj.ThreeGroup3);
			  
		  }
		  
		  else if(betType==15) {
			  var ballArr=$('#area_box a[_name="ThreeGroup6"]');
			  chooseMethod(ballArr,oprType,currentContentObj.ThreeGroup6);
			  
		  }
		  else if(betType==16) {
				if(line==1){
					var ballArr=$('#area_box a[_name="RANDOMONE1"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMONE1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="RANDOMONE2"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMONE2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="RANDOMONE3"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMONE3);
				}else if(line==4){
					var ballArr=$('#area_box a[_name="RANDOMONE4"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMONE4);
				}else if(line==5){
					var ballArr=$('#area_box a[_name="RANDOMONE5"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMONE5);
				}
		  } 
		  else if(betType==17) {
				if(line==1){
					var ballArr=$('#area_box a[_name="RANDOMTWO1"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMTWO1);
				}else if(line==2){
					var ballArr=$('#area_box a[_name="RANDOMTWO2"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMTWO2);
				}else if(line==3){
					var ballArr=$('#area_box a[_name="RANDOMTWO3"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMTWO3);
				}else if(line==4){
					var ballArr=$('#area_box a[_name="RANDOMTWO4"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMTWO4);
				}else if(line==5){
					var ballArr=$('#area_box a[_name="RANDOMTWO5"]');
					chooseMethod(ballArr,oprType,currentContentObj.RANDOMTWO5);
				}
		  } 
		  else  {
					var ballArr=$('#area_box a[_name="DirectOne"]');
					chooseMethod(ballArr,oprType,currentContentObj.DirectOne);
		  }
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
	var singleRandomSelect = function (betNum,randomUnits,isGroup3){
		var single_createForm_content = document.getElementById("single_createForm_content");
		var valueTemp=single_createForm_content.value;
		var copyBallArr = DIRECT_BALL_ARR.slice(0, DIRECT_BALL_ARR.length);
		for ( var i = 0; i < randomUnits; i++) {
			var betArr=copyBallArr.sort(randomSort).slice(0, betNum).sort(asc);
			var bet="";
			for ( var j = 0; j < betArr.length; j++) {
				if(isGroup3){
					if(j==betArr.length-1){
						bet+=betArr[j]+""+betArr[j]+"";
					}else{
						bet+=betArr[j]+"";
					}
				}else{
					bet+=betArr[j]+"";
				}
					
			}
			valueTemp+=bet+" ";
		}
		single_createForm_content.value=valueTemp;
		countTextAreaMoney();
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
	//全大小奇偶选择
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
	
	
	var checkDanRepeat = function(val) {
		var betType_input = document.getElementById("betType_input");
		var betType = betType_input.value;
		var normalArr = $('a[_name="'+betType+'"]');
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
});
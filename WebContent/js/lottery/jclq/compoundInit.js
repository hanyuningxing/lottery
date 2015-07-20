$(function() {
	var countCache = new Cache(100);// 缓存注数计算结果
	var hashObj = {};// 存放选择的选项
	var selectedMatchkey = [];//已选择的场次
	var seletedMatchCount = 0; // 当前选中的场次数
	var MAX_BETUNITS = 10000;
	var MAX_BETUNITS_ERROR_MSG = '单倍注数不能超过' + MAX_BETUNITS + '注。';
	var selected_class = 'tdchosed';
	var div_mohushedan_default='';
	var MAX_OPTIMIZE_MATCHS = 15;
	var MAX_OPTIMIZE_UNITS = 1000;

	if (maxPassMatchCount == null) {
		alert('本玩法最大的过关场次数未设置！');
		return;
	}
	
	window.getSelectedItems = function() {
		return hashObj;
	};
	
	var updateBetUnits_parent = updateBetUnits;
	window.updateBetUnits = function(units) {
		var msgEl = $$('span_msg');
		var errEl = $$('span_err');
		if (units == MAX_BETUNITS_ERROR_MSG) {
			errEl.innerHTML = MAX_BETUNITS_ERROR_MSG;
			msgEl.style.display = 'none';
			errEl.style.display = '';
			updateBetUnits_parent(0);
		} else {
			errEl.style.display = 'none';
			errEl.innerHTML = '';
			msgEl.style.display = '';
			updateBetUnits_parent(units);
		}
	};

	window.clickItem = function(matchIndex, matchKey, itemIndex, event, playType) {
		//需要判断一个场次只能有一种玩法
//		var checked=false;
//		var chkEl = $$(playType+'_chk_' + matchKey + '_' + itemIndex);
//		if (event != null) {
//			Event_stopPropagation(event);// event.stopPropagation();
//			var target = Event_target(event);// event.target
//			if (target.id != chkEl.id)
//				checked = !chkEl.checked;
//		} else {
//			checked = !chkEl.checked;
//		}
//		if(checked==true){
//			var isCanChoose = canChoose(matchKey,playType);
//			if(!isCanChoose){
//				setCommonResult("一场赛事暂时只支持一个玩法！", false);
//				return;
//			}
//		}
		
				
		if(div_mohushedan_default==''){
			div_mohushedan_default = $$('div_mohushedan').innerHTML;
		}
		var hashMatchKey = playType + matchKey;
		clickItemFn(hashMatchKey, matchIndex, matchKey, itemIndex, event, playType);
		resetSelectedMatchkey();
		displayOption();
		countBetUnits();
	};
	
	function clickItemFn(hashMatchKey, matchIndex, matchKey, itemIndex, event, playType) {
		var chkEl = $$(playType+'_chk_' + matchKey + '_' + itemIndex);
		if (event != null) {
			Event_stopPropagation(event);// event.stopPropagation();
			var target = Event_target(event);// event.target
			if (target.id != chkEl.id)
				chkEl.checked = !chkEl.checked;
		} else {
			chkEl.checked = !chkEl.checked;
		}
		handleItemChg(hashMatchKey, matchIndex,matchKey, itemIndex, chkEl.checked, playType);
		var tdEl = $$(playType+'_td_' + matchKey + '_' + itemIndex);
		handleCheckedOfTd(tdEl, chkEl.checked,playType,matchKey,itemIndex);
	}
	
	
	function handleCheckedOfTd(tdEl,checked,playType,matchKey,itemIndex){
		//标题：bgcolor="#E9FCE4" class="hhggxz"   bgcolor="#f63510" class="hhggxz3"
    	//赔率：bgcolor="#D9F2FF" class="hhggxz"   bgcolor="#f63510" class="hhggxz1"	
		if(!tdEl)return;
		if(isMix()){
			var selected_title_class='hhggxz3';
			var no_selected_title_class='hhggxz';
			var selected_title_bgcolor='#f63510';
			var no_selected_title_bgcolor='E9FCE4';
			
			var selected_sp_class='hhggxz1';
			var no_selected_sp_class='hhggxz';
			var selected_sp_bgcolor='#f63510';
			var no_selected_sp_bgcolor='D9F2FF';
			
			var tdEl_title = $$(playType+'_td_title_' + matchKey + '_' + itemIndex);
			if (checked) {
				$(tdEl_title).attr("bgcolor",selected_title_bgcolor);
				$(tdEl_title).removeClass(no_selected_title_class); 
				$(tdEl_title).addClass(selected_title_class);
				
				$(tdEl).attr("bgcolor",selected_sp_bgcolor);
				$(tdEl).removeClass(no_selected_sp_class); 
				$(tdEl).addClass(selected_sp_class);
			} else {
				$(tdEl_title).attr("bgcolor",no_selected_title_bgcolor);
				$(tdEl_title).removeClass(selected_title_class); 
				$(tdEl_title).addClass(no_selected_title_class);
				
				$(tdEl).attr("bgcolor",no_selected_sp_bgcolor);
				$(tdEl).removeClass(selected_sp_class); 
				$(tdEl).addClass(no_selected_sp_class);
			}
		}else{
			if (checked) {
				$(tdEl).addClass(selected_class); 
			} else {
				$(tdEl).removeClass(selected_class); 
			}
		}
	}

	function handleItemChg(hashMatchKey, matchIndex, matchKey, itemIndex, checked, playType) {
		var r = handleItemChgFn(hashMatchKey, matchIndex, matchKey, itemIndex, checked, playType);
		
		//可以细致到有场次变化的时候再对过关方式进行操作
		if (r != 0) {}
		
		for(var hashMatchKey in hashObj){
			var obj = hashObj[hashMatchKey];
			if (obj != null && obj.dan == true){
				obj.dan = false;
			}
		}
		displayPassType();//有场次增减则重绘过关方式
	}

	/**
	 * 处理选项变化的函数
	 * 
	 * @param matchIndex 页面场次索引
	 * @param matchKey 场次序号
	 * @param itemIndex 选项序号
	 * @param checked 是否选中
	 * @return 1表示选中场次增加,-1表示选中场次减少,0表示选中场次数目无变化
	 */
	function handleItemChgFn(hashMatchKey, matchIndex, matchKey, itemIndex, checked, playType) {
		var chgFlag = 0;
		if (hashMatchKey in hashObj) {
			var obj = hashObj[hashMatchKey];
			var v = 1 << itemIndex;
			if (checked == true) {
				obj.value |= v;
				obj.count += 1;
				chgFlag = 0;
			} else {
				obj.value ^= v;
				if (obj.value == 0) {
					hashObj_remove(hashMatchKey);
					chgFlag = -1;
				} else {
					obj.count -= 1;
					chgFlag = 0;
				}
			}

		} else if (checked == true) {
			var hashObjTemp={};
			for(var k in hashObj){
				obj = hashObj[k];				
				if(matchIndex<obj.matchIndex){
					hashObjTemp[k]=obj;
				}else{
					continue;
				}
			}
			for(var v in hashObjTemp){
				hashObj_remove(v);
			}
			hashObj_push(hashMatchKey, {
				matchIndex : matchIndex,
				matchKey : matchKey,
				value : 1 << itemIndex,
				count : 1,
				playType : playType,
				dan : false
			});
			for(var t in hashObjTemp){
				obj = hashObjTemp[t];
				hashObj_push(t,obj);
			}
			chgFlag = 1;
		}		
		checkBao(playType,matchKey,0);
		return chgFlag;
	}
	
	function hashObj_push(hashMatchKey, obj) {
		var objOfHash;
		var isContain=false;
		for(var k in hashObj){
			objOfHash = hashObj[k];				
			if(objOfHash.matchKey==obj.matchKey){
				isContain=true;
			}
		}
		if(!isContain){
			seletedMatchCount++;
		}
		hashObj[hashMatchKey] = obj;
	}
	
	function hashObj_remove(hashMatchKey) {
		var objOfHash;
		var obj = hashObj[hashMatchKey];
		if(obj==null)return;
		delete hashObj[hashMatchKey];
		var isContain=false;
		for(var k in hashObj){
			objOfHash = hashObj[k];				
			if(objOfHash.matchKey==obj.matchKey){
				isContain=true;
			}
		}
		if(!isContain){
			seletedMatchCount--;
		}
	}
	
	/**
	 * 计算最小奖金
	 * @author zhongshixing
	 */
	window.countMinPrize = function (){
		var minSpArr = new Array(); //由各场次可能出现的最小SP值组成的数组(被设为胆的场次不包含在此数组)		
		var danMinSpArr = new Array(); //由被设为胆的场次可能出现的最小SP值组成的数组
		var minPrize = 0.00;
		
		//取得minSpArr和danMinSpArr
		getMinSpArrAndDanMinSpArr(minSpArr, danMinSpArr);
		
		//获得最小奖金
		minPrize = getMinPrize(minSpArr, danMinSpArr);
		
		//显示最小奖金
		showMinPirze(minPrize);
		
		//设置表单最小奖金的值
		//setMinPrizeForCreateForm(minPrize);
	}
	
	/**
	 * 设置表单最小奖金的值
	 */
	function setMinPrizeForCreateForm(minPrize){
		document.getElementById("bestMinPrize").value = toDecimal(minPrize);
	}
	
	/**
	 * 显示最小奖金
	 * @author zhongshixing
	 */
	function showMinPirze(minPrize){
		document.getElementById("minPrize").innerHTML  = toDecimal(minPrize);
	}
	
	/**
	 * 取得由各场次可能出现的最小SP值组成的数组(被设为胆的场次不包含在此数组)
	 * 并
	 * 取得由被设为胆的场次可能出现的最小SP值组成的数组
	 * @author zhongshixing
	 */
	function getMinSpArrAndDanMinSpArr(minSpArr, danMinSpArr){
		for(var i=0, maxSize=selectedMatchkey.length;i<maxSize;i++){
			var matchKey = selectedMatchkey[i];
			
			//求出某个场次选中的最小SP并求得本场次是否为胆
			var minSpAndDan = getMinSpAndDan(matchKey);
			
			if(minSpAndDan.isDan){
				danMinSpArr[danMinSpArr.length] = minSpAndDan.minSp;
			} else {
				minSpArr[minSpArr.length] = minSpAndDan.minSp;
			}
		}
	}
	
	/**
	 * 求出某个场次选中的最小SP并判断是否为胆
	 * @param matchKey 场次
	 * @author zhongshixing
	 */
	function getMinSpAndDan(matchKey){
		var minSp = -1;
		var minSpAndDan = {};//包含minSp和本场次是否为胆的对象
		//sp, playType, obj, itemOrdinal均为选中项的信息
		var callback = function(sp, playType, obj, itemOrdinal){
			if(minSp == -1 || sp < minSp){
				minSp = sp;
				minSpAndDan['minSp'] = minSp;
				minSpAndDan['isDan'] = obj.dan;
			}	
		};	
		
		//遍历某个场次所有玩法，求得选中项的相关信息后执行回调函数
		forEachPlayTypesAndCallBack(matchKey, callback);				
		
		return minSpAndDan;
	}
	
	/**
	 * 遍历某个场次所有玩法，求得选中项的相关信息后执行回调函数
	 * @author zhongshixing
	 */
	function forEachPlayTypesAndCallBack(matchKey, callback, obj){
		
		if(typeof playTypeWeb != 'undefined' && playTypeWeb == "DGP" && typeof matchKeyOfMaxFanjianglv != "undefined" && matchKeyOfMaxFanjianglv == matchKey)
			return;
		
		for(var j=0;j<playTypes.length;j++){				
			var playType = playTypes[j];
			var obj = hashObj[playType+matchKey];
			if(obj==null){//某场某个玩法没有选值
				continue;
			}else{
				objOfMatch = obj;
			}
			var playItemArr = play2playItem[playType];
			for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
				if((obj.value & 1<<itemOrdinal)>0){//选中
					var spid = playType+'_sp_'+matchKey+'_'+itemOrdinal;
					var sp = parseFloat($$(spid).innerHTML);					
					callback(sp, playType, obj, itemOrdinal);					
				}
			}				
		}
	}

	
	/**
	 * 构建特定过关方式构成最小奖金的SP值数组
	 * @author zhongshixing
	 */
	function buildMinSpArrForSpecialPassType(minSpArr, danMinSpArr, type){
		var arr = new Array();
		var danMinElValue = danMinSpArr.length;
		var danMinEl = createForm.elements['createForm.danMinHit'];
		if(danMinEl){
			danMinElValue = parseInt(danMinEl.value);
		}
		danMinSpArr.sort(function(a,b){return a>b?1:-1});
		var danMinHitArr = [];
		
		for(var i=0; i<danMinElValue; i++){
			danMinHitArr[i] = danMinSpArr[i];
		}
		
		var notDanMinHitAndMinSpArr = [];
		for(var i=0,maxSize=danMinSpArr.length-danMinElValue; i<maxSize; i++){
			notDanMinHitAndMinSpArr[i] = danMinSpArr[i+danMinElValue];
		}
		for(var i=0, maxSize=minSpArr.length; i<maxSize; i++){
			notDanMinHitAndMinSpArr[danMinSpArr.length-danMinElValue + i] = minSpArr[i];
		}
		notDanMinHitAndMinSpArr.sort(function(a,b){return a>b?1:-1});
		
		var arr = danMinHitArr;
		danMinHitArrLength = danMinHitArr.length;
		
		for(var i=danMinHitArrLength; i<type.passMatchs[0]; i++){
			arr[i] = notDanMinHitAndMinSpArr[i-danMinHitArrLength];
		}
		return arr;
	}
	
	/**
	 * 计算一个数组所有元素的积
	 * @author zhongshixing
	 */
	function countArrayElementsProduct(arr){
		var product = 1;
		for(var i = 0, maxSize = arr.length; i<maxSize; i++){
			product = product*arr[i];
		}
		return product;
	}
	
	/**
	 * 计算一个数组所有元素的和
	 * @author zhongshixing
	 */
	function countArrayElementsSum(arr){
		var sum = 0;
		for(var i = 0, maxSize = arr.length; i<maxSize; i++){
			sum = sum + arr[i];
		}
		return sum;
	}
	
	/**
	 * 获得最小奖金
	 * @param minSpArr 由各场次可能出现的最小SP值组成的数组(被设为胆的场次不包含在此数组)
	 * @param danMinSpArr 由被设为胆的场次可能出现的最小SP值组成的数组
	 * @param typeArr 由过关方式组成的数组，有什么的过关方式由投注内容决定
	 * @author zhongshixing
	 */
	function getMinPrize(minSpArr, danMinSpArr){
		var minPrize = 0.00;
		var type = null; 
		var typeArr = createForm.elements['createForm.passTypes'];
		
		//过关方式只有2串1的情况下typeArr不是一个数组
		if(typeArr && (typeArr.value=="P2_1" || typeArr.value=="P1") ){
			typeEl = typeArr;
			if(typeEl.checked)
				type = PassType[typeEl.value];
		} else {
			if(typeArr){
				for ( var j = 0, maxSize = typeArr.length; j < maxSize; j++) {
					typeEl = typeArr[j];			
					if (typeEl.checked) {
						//结束循环
						j = maxSize;
						type = PassType[typeEl.value];
					} 
				}
			}
			
		}
		//type不为null则代表有勾选过关方式
		if(type){ 
			
			//取得特定过关方式构成最小奖金的SP值数组
			var arr = buildMinSpArrForSpecialPassType(minSpArr, danMinSpArr, type);
			
			//计算出构成最小奖金的所有SP的乘积
			var minSpProduct = countArrayElementsProduct(arr);
			
			//取得投注倍数
			var multiple = createForm.elements['createForm.multiple'].value;
			
			//根据minSpProduct和倍数计算最小奖金
			minPrize = minSpProduct*2*multiple;
		}
		
		return minPrize;
	}
		
	/**
	 * 计算最大奖金
	 * @author zhongshixing
	 */
	window.countMaxPrize = function(){
		var maxSpArray = [];//由各场次可能出现的最大sp值组成的数组(被设为胆的场次不在此数组)
		var danSpArray = [];//由被设为胆的的场次可能出现的最大sp值组成的数组
		var maxPrize = 0.00;
				
		//求得maxSpArray, danSpArray
		getMaxSpArrAndDanMaxSpArr(maxSpArray, danSpArray);
		
		//求出最大奖金
		maxPrize = getMaxPrize(maxSpArray, danSpArray);
		
		//显示最大奖金
		showMaxPrize(maxPrize);
		
		//设置表单最大奖金的值
		//setMaxPrizeForCreateForm(maxPrize);
	}
	
	/**
	 * 设置表单最大奖金的值
	 */
	function setMaxPrizeForCreateForm(maxPrize){
		document.getElementById("bestMaxPrize").value = toDecimal(maxPrize);
	}
	
	
	/**
	 * 求出最大奖金
	 * @author zhongshixing
	 */
	function getMaxPrize(maxSpArray, danSpArray){
		var typeArr = createForm.elements['createForm.passTypes'];
		var spSum = 0;
		var multiple = createForm.elements['createForm.multiple'].value;
		var danMinElValue = danSpArray.length;
		var danMinEl = createForm.elements['createForm.danMinHit'];
		if(danMinEl){
			danMinElValue = parseInt(danMinEl.value);
		}
		//只有2串1一种过关方式的时候typeArr不是一个数组
		if(typeArr && (typeArr.value=="P2_1" || typeArr.value=="P1")){
			if (typeArr.checked && typeArr.value=="P2_1") {
				spSum = spSum + sumAllCombinationSpForSpecialPassType(maxSpArray, 2);
			} else if(typeArr.checked && typeArr.value=="P1") {
				spSum = spSum + countArrayElementsSum(maxSpArray);
			}
		}else {
			if(typeArr){
				for ( var j = 0, l1 = typeArr.length; j < l1; j++) {
					var typeEl = typeArr[j];	
					if (typeEl.checked) {
						var type = PassType[typeEl.value];	
						var spSumWithDan = 0;
						if(danSpArray.length > 0){	
							for(var i=0, maxSize=type.passMatchs.length; i<maxSize; i++){
								if(type.passMatchs[i]-danSpArray.length > 0){
									for(var i=danMinElValue, maxSize=danSpArray.length; i<=maxSize; i++){
										spSumWithDan = spSumWithDan + sumAllCombinationSpForSpecialPassType(danSpArray, i)*sumAllCombinationSpForSpecialPassType(maxSpArray, type.passMatchs[0] - i);
									}
								} else {
									spSumWithDan = spSumWithDan + countArrayElementsProduct(danSpArray);
								}
							}
														
							spSum = spSum + spSumWithDan;
						} else {
							for(var i=0, maxSize=type.passMatchs.length; i<maxSize; i++){
								spSum = spSum + sumAllCombinationSpForSpecialPassType(maxSpArray, type.passMatchs[i]);
							}		
						}					
					}
				}
			}
			
		}
		
		var maxPrize = spSum*2*multiple;
		
		return maxPrize;
	}
	
	/**
	 * 显示最大奖金
	 * @author zhongshixing
	 */
	function showMaxPrize(maxPrize){
		document.getElementById("maxPrize").innerHTML = toDecimal(maxPrize);
	}

	/**
	 * 取得由各场次可能出现的最大SP值组成的数组(被设为胆的场次不包含在此数组)
	 * 并
	 * 取得由被设为胆的场次可能出现的最大SP值组成的数组
	 */
	function getMaxSpArrAndDanMaxSpArr(maxSpArray, danSpArray){
		for(var i=0,maxSize = selectedMatchkey.length;i<maxSize;i++){
			var matchKey = selectedMatchkey[i];
			
			//取得本场次选中项可能出现的最大SP和判断是否为胆
			var maxSpAndDan = getMaxSpAndDan(matchKey);
						
			if(maxSpAndDan.isDan){
				danSpArray[danSpArray.length] = maxSpAndDan.maxSp;
			} else {
				maxSpArray[maxSpArray.length] = maxSpAndDan.maxSp;
			}					
		}
	}
	
	/**
	 * 取得某场次可能出现的最大SP值和此场次是否设置为胆
	 * @returns maxSpAndDan={maxSp,isDan}
	 * @author zhongshixing
	 */
	function getMaxSpAndDan(matchKey){
		var maxSp = 0;
		var maxSpAndDan = {};
		var callback = function(sp, playType, obj, itemOrdinal){
			if(maxSp==0 || maxSp<sp){
				maxSp = sp; 
			}
			maxSpAndDan.isDan = obj.dan;
		}	
		
		//遍历某个场次所有玩法，求得选中项的相关信息后执行回调函数
		forEachPlayTypesAndCallBack(matchKey, callback);				
		
		maxSpAndDan.maxSp = maxSp;
		return maxSpAndDan;
	}
	
	var allCombinationSpSumResult = 0;
	
	/**
	 * 计算特定过关方式的所有可能组合的SP值的和
	 * @param arr 由每个场次可能出现的最大SP值组成的数组
	 * @param passType 过关方式（四串一的话传进来的就是4）	 
	 * @author zhongshixing
	 * 如传进的SP数组arr为[1.2, 1.5, 1.8],passType为2的话，返回值为1.2*1.5+1.2*1.8+1.5*1.8
	 */
	function sumAllCombinationSpForSpecialPassType(arr, passType){
		allCombinationSpSumResult = 0;
		//调用辅助函数计算，参数index,sp,count传-1,1,0即可
		spHelper(arr, -1, 1, passType, 0);
		return allCombinationSpSumResult;
	}
	
	/**
	 * 计算特定过关方式的所有可能组合的SP值的和的辅助函数，采用递归方式计算
	 * @param arr 由每个场次可能出现的最大SP值组成的数组
	 * @param index 当前相乘的数组元素的下标
	 * @param sp 当前组合元素相乘的结果作为参数传给下一次递归
	 *          （如四串一,arr[1]*arr[2]*arr[3]*arr[4]就是其中一种组合,其实是这样计算的,sp = 1;sp = arr[1]*sp;sp = arr[2]*sp,sp = arr[3]*sp,sp = arr[4]*sp.所以最后sp其实等于arr[1]*arr[2]*arr[3]*arr[4].其他情况以此类推）
	 * @param passType 过关方式（四串一的话传进来的就是4）
	 * @param times 当前递归方法调用的次数，也是递归终止条件，当times=passType的时候终止递归
	 * @author zhongshixing
	 */
	function spHelper(arr, index, sp, passType, times){
		var i = index+1;
		var timesNow = times;
		if(timesNow<passType) {
			timesNow = timesNow + 1;
		}
		var sp0 = sp;
		
		for(i=i,maxSize=arr.length-passType+timesNow; i<maxSize; i++) {			
			if(timesNow<passType){
				sp = arr[i]*sp0;
				spHelper(arr, i, sp, passType, timesNow);
			} else {
				allCombinationSpSumResult = allCombinationSpSumResult + arr[i]*sp0;
			}			
		}
	}
	
	/**
	 * 移除一个场次
	 */
	window.removeMatch = function(matchKey) {
		for(var j=0;j<playTypes.length;j++){
			var playType = playTypes[j];
			hashObj_remove(playType+matchKey);
			var playItemArr = play2playItem[playType];
			for(var i=0;i<playItemArr.length;i++){
				var chkEl = $$(playType+'_chk_' + matchKey + '_' + i);
				if(chkEl==null)continue;
				chkEl.checked = false;
				var tdEl = $$(playType+'_td_' + matchKey + '_' + i);
				handleCheckedOfTd(tdEl, false,playType,matchKey,i);
			}
			checkBao(playType,matchKey,0);
		}
		resetSelectedMatchkey();
		updateBetUnits(0);
		displayOption();
		updateShedan();
		displayPassType();
	};
	
	/**
	 * 移除一个选项
	 */
	window.removeItem = function(matchKey,playType,itemIndex){		
		var chkEl = $$(playType+'_chk_' + matchKey + '_' + itemIndex);
		chkEl.checked = false;
		var tdEl = $$(playType+'_td_' + matchKey + '_' + itemIndex);
		handleCheckedOfTd(tdEl, chkEl.checked,playType,matchKey,itemIndex);
				
		var hashMatchKey = playType+matchKey;
		var obj = hashObj[hashMatchKey];
		var v = 1 << itemIndex;
		obj.value ^= v;
		if (obj.value == 0) {
			hashObj_remove(hashMatchKey);
		}else{
			obj.count -= 1;
		}
		resetSelectedMatchkey();
		displayOption();
		updateShedan();
		displayPassType();
		countBetUnits();
		checkBao(playType,matchKey,0);
	};
	
	/**
	 * 清空所有选择
	 */
	window.cleanSelectedMatch = function(){
		for(var k=0;k<selectedMatchkey.length;k++){
			var matchKey = selectedMatchkey[k];
			for(var j=0;j<playTypes.length;j++){
				var playType = playTypes[j];
				var playItemArr = play2playItem[playType];
				hashObj_remove(playType+matchKey);
				for(var i=0;i<playItemArr.length;i++){
					var chkEl = $$(playType+'_chk_' + matchKey + '_' + i);
					if(!chkEl)continue;
					chkEl.checked = false;
					var tdEl = $$(playType+'_td_' + matchKey + '_' + i);
					handleCheckedOfTd(tdEl, false,playType,matchKey,i);
				}
				checkBao(playType,matchKey,0);
			}
		}
		resetSelectedMatchkey();
		displayOption();
		updateShedan();
		displayPassType();
		countBetUnits();
	};
		
	
	function resetSelectedMatchkey(){
		selectedMatchkey = [];
		for(var kk in hashObj){
			//取选中的场次key
			var isContained = false;//是否包含
			var obj = hashObj[kk];
			for(var jj in selectedMatchkey){
				if(selectedMatchkey[jj]==obj.matchKey){
					isContained = true;
					break;
				}
			}
			if(!isContained){
				selectedMatchkey.push(obj.matchKey);
			}
		}
	}


	function isMix(){
		return playTypeOfGobal=='MIX';
	}
	
	/**
	 * 显示选中项
	 */
	function displayOption() {
		var canSetDan = isMultiplePass();
		var canAddDan = false;//
		var html = '';
		html += '<table width="100%" height="26" border="0" cellpadding="0" cellspacing="1" bgcolor="#dce7f4">';
		
		for(var index=0;index<selectedMatchkey.length;index++){
			matchKey = selectedMatchkey[index];
			html += getItemShowHtml(matchKey, index, canSetDan, canAddDan);
		}
		var minSelected = 2;
		if(isSinglePass()){
			minSelected = 1;
		}
		
		if(seletedMatchCount<minSelected){
			var colspan=2;
			if(canSetDan){
				colspan=3;
			}
			html += '<tr class="tdwhitelist1"><td colspan="'+ colspan +'" width="30%" height="30" align="center" bgcolor="#FFFFFF">请在投注区选择至少'+ minSelected +'场赛果</td></tr>';
		}
		html += '</table>';
		
		$$('div_show').innerHTML = html;
		
		displayHiddenObj();
		
		$$('createForm_selectedMatchCount').innerHTML = seletedMatchCount;
		
	}
	
	function displayHiddenObj(){
		var itemsHtml = '';
		//记录每个场次的选项Item
		var index = 0;
		for(var k in hashObj){
			var objOfHash = hashObj[k];
			itemsHtml+=getItemHtml(objOfHash,index);
			index++;
		}
		$$('div_items').innerHTML = itemsHtml;
	}
	
	/**
	 * 隐藏域选项
	 */
	function getItemHtml(obj, index) {
		var html = '';
		//组合某个玩法某场sp
		var playItemArr = play2playItem[obj.playType];
		var sps='';
		for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
			var spid = obj.playType+'_sp_'+obj.matchKey+'_'+itemOrdinal;
			sps += $$(spid).innerHTML+',';
		}
		sps = sps.substring(0, sps.length-1);
		html += '  <input type="hidden" name="createForm.items[' + index
				+ '].matchKey" value="' + obj.matchKey + '" /><input type="hidden" name="createForm.items[' + index
				+ '].value" value="' + obj.value + '" /><input type="hidden" name="createForm.items[' + index
				+ '].playType" value="' + obj.playType + '" /><input type="hidden" name="createForm.items[' + index
				+ '].dan" value="'+ obj.dan +'"/><input type="hidden" name="createForm.items[' + index
				+ '].spStr" value="'+ sps +'"/>';
		return html;
	}
	
	

	/**
	 * 选项显示
	 */
	function getItemShowHtml(matchKey, index, canSetDan, canAddDan) {
		var obj=null;
		var hashMatchKey;
		for(var i=0;i<playTypes.length;i++){
			hashMatchKey = playTypes[i] + matchKey;
			obj = hashObj[hashMatchKey];
			if(obj!=null)break;
		}
		
		var danInputStr;
		if (canSetDan) {
			danStyleStr = '';
			if (obj!=null && obj.dan)
				danInputStr = 'checked="checked"';
			else
				danInputStr = canAddDan ? '' : 'disabled="disabled"';
		} else {
			danStyleStr = 'style="display:none"';
			danInputStr = 'disabled="disabled"';
		}
		var html = '';
		html += '<tbody>';
		html += '<tr>';
		html += '  <td width="30%" height="30" align="center" bgcolor="#FFFFFF"><a href="javascript:;" onclick="removeMatch(\''+ matchKey +'\')"><img src="'+ window.BASESITE +'/V1/images/chacha.gif" border="0" /></a> '+ $$('key_'+matchKey).value +'</td>';
		html += '  <td align="center" bgcolor="#FFFFFF" width="58%">'+ $$('td_h_'+matchKey).innerHTML +'  VS  '+ $$('td_g_'+matchKey).innerHTML +'</td>';
		html += '  <td align="center" bgcolor="#FFFFFF" class="blue_0091d4" '+ danStyleStr +'><input _matchKey="'+ matchKey +'" name="matchs['+ index +'].dan" '+ danInputStr +' type="checkbox" value="true" onclick="clickShedan(this);"/></td>';
		html += '</tr>';
		html += '<tr>';
		html += '  <td colspan="3" class="pad010" align="left" bgcolor="#FFFFFF" height="30">';
		html += '   <table cellpadding="0" cellspacing="3" border="0" width="100%">';
		html += '      <tbody><tr>';
		html += '           <td rowspan="5" valign="top"><span style="float:left;">投注项：</span></td>';
		html += '           <td width="80%">';
		var objOfMatch=null;
		var selectedItemsOfMatch='';
		for(var j=0;j<playTypes.length;j++){
			var playType = playTypes[j];
			var playItemArr = play2playItem[playType];
			var obj = hashObj[playType+matchKey];
			if(obj==null){;//某场某个玩法没有选值
				continue;
			}else{
				objOfMatch = obj;
			}
			for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
				if((obj.value & 1<<itemOrdinal)>0){//选中
					var itemClassName;
					if(playTypeOfGobal=='MIX'){
						itemClassName=selectedItemClass[j];
					}else{
						itemClassName=selectedItemClass[0];
					}
					//增加让分显示还有title以便用户查看
					var rf='';
					var title = '';
					try{
					if(playType=='RFSF'||playType=='DXF'){
						var  handicap_ = $$('td_reduced_' + matchKey);
						if(handicap_&&handicap_.innerHTML){
							rf = '('+handicap_.innerHTML+')';
						}
					}
					var playTypeIt = playTypeItem[playType];
					if(playTypeIt){
						title=title+"玩法类型("+playTypeIt.name+").投注选项["+rf+playItemArr[itemOrdinal]+"]";
					}
					}catch(e){
					  //保守	
					}
					selectedItemsOfMatch+='<a  title ="'+title+'" href="javascript:;" onclick="removeItem(\''+matchKey+"\',\'"+playType+"\',\'"+itemOrdinal+'\')" class="'+ itemClassName +'">'+rf+playItemArr[itemOrdinal]+'</a>';
				}
			}
		}		
		html += selectedItemsOfMatch;
		html += '                    </td>';
		html += '                 </tr>';
		html += '           </tbody>';
		html += '       </table>';
		html += '    </td>';
		html += '  </tr>';
		html += '</tbody>';
		return html;
	}
	
	/**
	 * 切换过关模式
	 */
	window.chgPassMode = function(schemeType) {
		$$("createForm.schemeType").value=schemeType;
		var multPass = isMultiplePass();
		var singlePass = isSinglePass();
		var fn;
		var danmaHeadEl = document.getElementById('td_danma');
		var multPassObj=$("#createForm_schemeType_m");
		var simpPassObj=$("#createForm_schemeType_s");
		if (!multPass) {
			simpPassObj.addClass("ggfsli01");
			multPassObj.removeClass("ggfsli01");			
			$$(simpPassObj).style.background="url(" + window.BASESITE + "/V1/images/guoguan.jpg)";
			$$(simpPassObj).style.color="#000";
			$$(simpPassObj).style.fontWeight='bold';			
			$$(multPassObj).style.background="#D5F6FD url(" + window.BASESITE + "/V1/images/guoguanbg.jpg) left bottom repeat-x";
			$$(multPassObj).style.color="#000";
			$$(multPassObj).style.fontWeight='normal';
			
			$$('div_mohushedan').style.display = 'none';
			document.getElementById('div_mohushedan').innerHTML = div_mohushedan_default;

			for(var hashMatchKey in hashObj){
				var obj = hashObj[hashMatchKey];
				if (obj != null)
					obj.dan = false;
			}
			fn = function(el) {
				el.checked = false;
				el.disabled = true;
				el.parentNode.style.display = 'none';
			};
			danmaHeadEl.style.display = 'none';
		} else {
			multPassObj.addClass("ggfsli01");
			simpPassObj.removeClass("ggfsli01");
			$$(multPassObj).style.background="url(" + window.BASESITE + "/V1/images/guoguan.jpg)";
			$$(multPassObj).style.color="#000";
			$$(multPassObj).style.fontWeight='bold';			
			$$(simpPassObj).style.background="#D5F6FD url(" + window.BASESITE + "/V1/images/guoguanbg.jpg) left bottom repeat-x";
			$$(simpPassObj).style.color="#000";
			$$(simpPassObj).style.fontWeight='normal';
			
			$$('div_mohushedan').style.display = '';

			fn = function(el) {
				el.checked = false;
				el.disabled = true;
				el.parentNode.style.display = '';
			};
			danmaHeadEl.style.display = '';
		}
		var createForm = getCreateForm();
		for ( var i = 0; i < seletedMatchCount; i++) {
			var danmaEl = createForm.elements['matchs[' + i + '].dan'];
			fn(danmaEl);
		}
		if(!singlePass)
			updateBetUnits(0);
		displayPassType();
	};


	/**
	 * 更新过关方式显示
	 */
	function displayPassType() {
		var el = document.getElementById('pass_container');
		if (seletedMatchCount == 0) {
			el.innerHTML = '&nbsp;';
			return;
		}

		if(div_mohushedan_default==''){
			div_mohushedan_default = $$('div_mohushedan').innerHTML;
		}
		document.getElementById('div_mohushedan').innerHTML = div_mohushedan_default;

		var multPass = isMultiplePass();
		var singlePass = isSinglePass();
		var typeArr;
		var inputType;
		if (multPass) {//多选
			typeArr = PassTypeUtil.getMultiplePassType((seletedMatchCount > maxPassMatchCount) ? maxPassMatchCount : seletedMatchCount);
			inputType = 'checkbox';
		} else if(singlePass){//单关
			if (seletedMatchCount > window.maxSingleMatchCount) {
				el.innerHTML = '<font color="red">单关最多只能选择' + maxPassMatchCount + '场赛事</font>';
				return;
			}
			var type = PassTypeUtil.getSinglePassType(seletedMatchCount);
			el.innerHTML = '<input type="radio" checked="checked" name="createForm.passTypes" value="' + type.key + '" />'+ type.text;
			countBetUnits();
			return;
		} else {//普通
			if (seletedMatchCount > maxPassMatchCount) {
				el.innerHTML = '<font color="red">最多只能选择' + maxPassMatchCount + '场赛事</font>';
				return;
			}
			inputType = 'radio';
			typeArr = PassTypeUtil.getNormalPassType(seletedMatchCount);
		}

		var html = '';
		for (var i = 0, len = typeArr.length; i < len; i++) {
			var type = typeArr[i];
			var passType_li_class='ggfs02li02';
			if(passType_recommend!=null && multPass && type.key==passType_recommend){
				passType_li_class='ggfs02li01';
			}
			var inputId = 'passType_' + type.key;
			html += '<li class="'+ passType_li_class +'"><input type="' + inputType + '" id="' + inputId + '" value="' + type.key
					+ '" name="createForm.passTypes" onclick="chgPassType(this);"><label for="' + inputId + '">'
					+ type.text + '</label></li>';
		}
		el.innerHTML = html;
	}
	

	/**
	 * 切换过关方式
	 * 
	 * @param obj
	 */
	window.chgPassType = function(obj) {
		var multPass = isMultiplePass();
		if (multPass) {
			updateShedan();
			displayHiddenObj();
		}
		countBetUnits();
		checkOptimizeOper(obj);
	};
	
	
	/**
	 * 检查优化操作是否合法
	 */
	var canOptimize=null;
	function checkOptimizeOper(obj){
		canOptimize = true;
		var multPass = isMultiplePass();
		if(multPass){
			var checkbox = document.getElementsByName("createForm.passTypes");
		    var j = 0;
		    for(var i=0;i<checkbox.length;i++){
		       if(checkbox[i].checked){
		          j++;
		      }
		      if(j>1){
		    	 canOptimize = false;
		         break;
		      }
		   }
		}else{
			var passTo = PassType[obj.value+''].units;
			if(passTo>1){
				canOptimize = false;
			}
		}		
	}
	
	/**
	 * 组合确认框投注信息
	 */
	window.buildConfirmData = function (){		
		var createForm = getCreateForm();
		//验证投注数据的正确性
		try {
			checkCreateForm();
		} catch (e) {
			var errType = typeof e;
			if (errType == 'string') {
				setCommonResult(e, false);
				return false;
			} else if (errType == 'object' && e.message != null) {
				setCommonResult(e.message, false);
				return false;
			} else {
				throw e;
			}
		}
		//组装确认框数据
		var schemeCost = createForm.elements['createForm.schemeCost'].value;
		$$('confirmCost').innerHTML = schemeCost;//方案总金额
		$$('confirmMultiple').innerHTML = createForm.elements['createForm.multiple'].value;
		var minCost = createForm.elements['createForm.minSubscriptionCost'].value;//每份金额
		$$('confirmQuantity').innerHTML = (parseInt(schemeCost)/parseInt(minCost)).toFixed(0);//份数不能整除可留小数点两位 toFixed(2)
		$$('confirmMinCost').innerHTML = minCost;
		var commissionRate = $$('createForm.commissionRate');
		$$('confirmCommissionRate').innerHTML = parseFloat(commissionRate.options[commissionRate.selectedIndex].value)*100+"%";
		var baodi = createForm.elements['createForm.baodiCost'].value+"元";
		if(baodi.trim()==''){
			baodi = "未保底";
		}
		$$('confirmBaodi').innerHTML = baodi;
		var shareType = getRadioVal(createForm, "createForm.shareType");
		var subscriptionPer = $$('subscriptionPerSpan').innerHTML+"%";
		if(shareType=='SELF'){//代购方案进度为百分百
			subscriptionPer = '100%';
		}
		$$('subscriptionPer').innerHTML = subscriptionPer;
		
		//投注选项内容展示
		var matchKey;
		var itemsHtml='<table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">';
			itemsHtml+='<tr class="v01tr"><td>赛事编号 </td><td>主队 VS 客队 </td><td>让球数 </td><td>您的选择</td><td>胆码</td></tr>';
		for(var i=0;i<selectedMatchkey.length;i++){
			matchKey = selectedMatchkey[i];
			var itemHtmlOfMatch = '<tr class="v02tr">';
			itemHtmlOfMatch +='<td>'+ $$('text_'+matchKey).innerHTML +'</td>';
			itemHtmlOfMatch +='<td>'+ $$('td_h_'+matchKey).innerHTML +' VS '+$$('td_g_'+matchKey).innerHTML+'</td>';
			
			var handicapObj = $$('handicap_'+matchKey);
			if(handicapObj){
				var handicap_value = $$('handicap_'+matchKey).innerHTML;
				var handicap_css = (parseInt(handicap_value)>0)?'red':'blue';
				itemHtmlOfMatch +='<td><span class="'+ handicap_css +'">'+handicap_value+'</span></td>';
			}else{
				itemHtmlOfMatch +='<td>&nbsp;</td>';
			}
			
			itemHtmlOfMatch +='<td>';
			var objOfMatch=null;
			for(var j=0;j<playTypes.length;j++){
				var playType = playTypes[j];
				var obj = hashObj[playType+matchKey];
				if(obj==null){;//某场某个玩法没有选值
					continue;
				}else{
					objOfMatch = obj;
				}
				var playItemArr = play2playItem[playType];
				for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
					if((obj.value & 1<<itemOrdinal)>0){//选中
						var spid = playType+'_sp_'+matchKey+'_'+itemOrdinal;
						itemHtmlOfMatch += playItemArr[itemOrdinal]+'('+ $$(spid).innerHTML +')，';
						if((itemOrdinal+1)>=5 && (itemOrdinal+1)%5 == 0) {
							itemHtmlOfMatch += '</br>';
						}
					}
				}
			}
			itemHtmlOfMatch=itemHtmlOfMatch.substr(0, itemHtmlOfMatch.length-1);  
			itemHtmlOfMatch +='</td>';
			var dan = (objOfMatch!=null && objOfMatch.dan)?'√':'×';
			itemHtmlOfMatch +='<td>'+ dan +'</td>';			
			itemHtmlOfMatch += '</tr>';
			itemsHtml+=itemHtmlOfMatch;
		}
		itemsHtml+='</table>';
		$$('selectedContentDiv').innerHTML = itemsHtml;
		
		//获取相关的奖金预测信息
		
	};
	
	

	/**
	 * 统计注数
	 */
	function countBetUnits() {
		var multPass = isMultiplePass();
		if (multPass)
			countMultiplePassUnits();
		else
			countNormalPassUnits();
	}
	

	function isMultiplePass() {
		var obj = $$('createForm.schemeType');
		return obj.value=='MULTIPLE_PASS';
	}

	function isSinglePass() {
		var obj = $$('createForm.schemeType');
		return obj.value=='SINGLE';
	}
	

	window.clickShedan = function(el) {
		var matchKey = el.getAttribute('_matchKey');
		for(var j=0;j<playTypes.length;j++){
			var obj = hashObj[playTypes[j]+matchKey];
			if(obj==null){;//某场某个玩法没有选值
				continue;
			}
			obj.dan = el.checked;
		}
		updateShedan();
		displayHiddenObj();
		countBetUnits();
	};

	/**
	 * 更新设胆和模糊设胆
	 */
	function updateShedan() {
		var createForm = getCreateForm();
		var minPassType=null;// 最小的过关方式
		var passTypeCount = 0;// 过关方式数目
		var arr = createForm.elements['createForm.passTypes'];
		if (arr != null) {
			if (arr.length == null) {
				arr = [ arr ];
			}
			for (var i = 0, len = arr.length; i < len; i++) {
				var typeEl = arr[i];
				if (typeEl.checked) {
					var type = PassType[typeEl.value];
					if (minPassType == null)
						minPassType = type;
					passTypeCount++;
				}
			}
		}

		var canShedanNum = 0; // 可以设胆的数目
		if (minPassType != null) {
			if (seletedMatchCount > minPassType.matchCount) {
				canShedanNum = minPassType.matchCount;
				if (passTypeCount == 1) {//只一种过关方式，则可设胆数即为当前选择场次数减去一个场次
					canShedanNum--;
				}
			}
		}

		var danSize = 0; // 胆码数目
		var unDanSize = 0; // 非胆码数目
		if (canShedanNum < 1) { // 可设胆数目小于1，即不能设胆，所有设胆置为不可用
			for ( var i = 0; i < seletedMatchCount; i++) {
				var danmaEl = createForm.elements['matchs[' + i + '].dan'];
				danmaEl.checked = false;
				danmaEl.disabled = true;
			}
			cleanSelectedItemDan();
		} else {
			var danArr = [];
			var unDanArr = [];
			for ( var i = 0; i < seletedMatchCount; i++) {
				var danmaEl = createForm.elements['matchs[' + i + '].dan'];
				if (danmaEl.checked) {
					danArr.push(danmaEl);
				} else {
					unDanArr.push(danmaEl);
				}
			}
			danSize = danArr.length;
			unDanSize = unDanArr.length;

			if (danSize > canShedanNum) {// 当前设胆数目大于可以设胆的数目，取消所有设胆，其他不可用设胆的场次重置为可用
				for ( var i = 0; i < danArr.length; i++) {
					danArr[i].checked = false;
				}
				for ( var i = 0; i < unDanArr.length; i++) {
					unDanArr[i].disabled = false;
				}
				unDanSize += danSize;
				danSize = 0;
				cleanSelectedItemDan();
			} else if (danSize == canShedanNum) {// 当前设胆数目等于可以设胆的数目，把其他未设胆的场次的设胆置为不可用
				for ( var i = 0; i < unDanArr.length; i++) {
					unDanArr[i].disabled = true;
				}
			} else {// 当前设胆数目小于可以设胆的数目，把其他未设胆的场次的设胆置为可用
				for ( var i = 0; i < unDanArr.length; i++) {
					unDanArr[i].disabled = false;
				}
			}
		}

		// 更新模糊设胆
		var html = '';
		if (minPassType != null && danSize > 0) {
			var danMinHit = minPassType.matchCount - unDanSize;//当非胆的个数多于最小过关数，则只能设置一个胆
			if (danMinHit < 1)
				danMinHit = 1;
			var danMaxHit = danSize;
			html += '<select id="createForm_danMinHit" name="createForm.danMinHit" onchange="chgMohuSheDan(this);">';
			for (var i = danMaxHit; i >= danMinHit; i--) {
				html += '<option value=' + i + '>至少对' + i + '场</option>';
			}
			html += '</select>';
		}
		if(html!=''){
			$$('mohushedan').innerHTML = html;
		}else{
			$$('div_mohushedan').innerHTML = div_mohushedan_default;
		}
		
	}
	
	/**
	 * 清除选中项所有的胆
	 */
	function cleanSelectedItemDan(){
		for(var hashMatchKey in hashObj){
			var obj = hashObj[hashMatchKey];
			if (obj != null && obj.dan == true)
				obj.dan = false;
		}
	}

	/**
	 * 切换模糊设胆
	 */
	window.chgMohuSheDan = function(obj) {
		countBetUnits();
	};
	
	
	
	/**
	 * 检查是否全部项选中
	 */
	function checkBao(playType,matchKey,rowIndex){		
		var hashMatchKey = playType+matchKey;
		if (hashMatchKey in hashObj) {
			var obj = hashObj[hashMatchKey];
			for ( var i = 0; i < getRowItemValues(playType).length; i++) {
				var rowVal = getRowItemValues(playType)[i];
				var baoObj = $$(playType+'_bao_' + i + '_' + matchKey);
				if(baoObj)
					baoObj.checked = (obj.value & rowVal) == rowVal;
			}
		} else {
			for ( var i = 0; i < getRowItemValues(playType).length; i++) {
				var baoObj = $$(playType+'_bao_' + i + '_' + matchKey);
				if(baoObj)
					baoObj.checked = false;
			}
		}
	}
	
	/**
	 * 普通过关计算注数
	 * 
	 */
	function countNormalPassUnits() {
		var type=null;// 当前的过关方式
		var createForm = getCreateForm();
		var typeArr = createForm.elements['createForm.passTypes'];
		if (typeArr != null) {
			if (typeArr.length == null) {
				typeArr = [ typeArr ];
			}
			for ( var i = 0, len = typeArr.length; i < len; i++) {
				var typeEl = typeArr[i];
				if (typeEl.checked) {
					type = PassType[typeEl.value];
					break;
				}
			}
		}

		var betUnits = 0;
		if (type != null) {
			try {
				var bets = [];
				for(var i=0;i<selectedMatchkey.length;i++){
					var matchKey = selectedMatchkey[i];
					var countOfMatch=0;
					for(var j=0;j<playTypes.length;j++){
						var playType = playTypes[j];
						var obj = hashObj[playType+matchKey];
						if (obj != null) {
							countOfMatch+=obj.count;
						}
					}
					if(countOfMatch!=0){
						bets.push(countOfMatch);
					}
				}
				bets.sort();
				for ( var i = 0, len = type.passMatchs.length; i < len; i++) {
					betUnits += simpleCountFn(bets, type.passMatchs[i]);
					if (betUnits > MAX_BETUNITS) {
						throw MAX_BETUNITS_ERROR_MSG;
					}
				}
			} catch (e) {
				if (e == MAX_BETUNITS_ERROR_MSG) {
					updateBetUnits(e);
					return;
				}
			}
		}
		updateBetUnits(betUnits);
	}

	/**
	 * 计算多选过关注数
	 */
	function countMultiplePassUnits() {
		var betUnits = 0;

		var danBets = [];
		var unDanBets = [];
		for(var i=0;i<selectedMatchkey.length;i++){
			var matchKey = selectedMatchkey[i];
			var countOfMatch=0;
			var dan=false;
			for(var j=0;j<playTypes.length;j++){
				var playType = playTypes[j];
				var obj = hashObj[playType+matchKey];
				if (obj != null) {
					countOfMatch+=obj.count;
					dan=obj.dan;
				}
			}
			if(countOfMatch!=0){
				if(dan)
					danBets.push(countOfMatch);
				else
					unDanBets.push(countOfMatch);
			}
		}
		danBets.sort();
		unDanBets.sort();
		var danSize = danBets.length;

		var createForm = getCreateForm();
		var typeArr = createForm.elements['createForm.passTypes'];
		if (typeArr != null) {
			if (typeArr.length == null) {
				typeArr = [ typeArr ];
			}
			try {
				if (danSize > 0) {
					var danMinEl = createForm.elements['createForm.danMinHit'];
					var danMinHit;// 胆码最小命中
					if (danMinEl != null)
						danMinHit = parseInt(danMinEl.value, 10);
					else
						danMinHit = danSize;

					var danMaxHit = danSize;// TODO:胆码最大命中
					for ( var i = 0, l1 = typeArr.length; i < l1; i++) {
						var typeEl = typeArr[i];
						if (typeEl.checked) {
							var type = PassType[typeEl.value];
							for ( var j = 0, l2 = type.passMatchs.length; j < l2; j++) {
								var needs = type.passMatchs[j];
								for ( var danHit = danMinHit; danHit <= danMaxHit; danHit++) {
									betUnits += shedanCountFn(danBets, unDanBets, danHit, needs);
									if (betUnits > MAX_BETUNITS) {
										throw MAX_BETUNITS_ERROR_MSG;
									}
								}
							}
						}
					}
				} else {
					for ( var i = 0, l1 = typeArr.length; i < l1; i++) {
						var typeEl = typeArr[i];
						if (typeEl.checked) {
							var type = PassType[typeEl.value];
							for ( var j = 0, l2 = type.passMatchs.length; j < l2; j++) {
								betUnits += simpleCountFn(unDanBets, type.passMatchs[j]);
								if (betUnits > MAX_BETUNITS) {
									throw MAX_BETUNITS_ERROR_MSG;
								}
							}
						}
					}
				}
			} catch (e) {
				if (e == MAX_BETUNITS_ERROR_MSG) {
					updateBetUnits(e);
					return;
				}
			}
		}
		updateBetUnits(betUnits);
	}

	/**
	 * 缓存注数的计算结果
	 * 
	 * @param key 缓存的键
	 * @param result 缓存的值
	 * @return 缓存的值
	 */
	function cacheCountResult(key, result) {
		var cacheOption;
		if (result > 5000) {
			cacheOption = {
				expirationSliding : 1200,
				priority : CachePriority.High
			};
		} else if (result > 1000) {
			cacheOption = {
				expirationSliding : 600,
				priority : CachePriority.Normal
			};
		} else {
			cacheOption = {
				expirationSliding : 300,
				priority : CachePriority.Low
			};
		}
		countCache.setItem(key, result, cacheOption);
		return result;
	}

	/**
	 * 有设胆的注数计算
	 * 
	 * @param danBets 设胆的数组
	 * @param unDanBets 非设胆的数组
	 * @param danHit 胆码对几场
	 * @param needs 需要的场次数目（过几关）
	 * @return 注数
	 */
	function shedanCountFn(danBets, unDanBets, danHit, needs) {
		var key = "C3_" + danBets + "_" + unDanBets + "_" + danHit + "_" + needs;// 用于缓存计算结果的KEY
		var rs = countCache.getItem(key);// 从 缓存查找是否已经有计算结果

		if (rs == null) {// 无缓存，进行计算
			var count = 0;
			C3(danBets.length, danHit, function(comb, n, m) {
				var c = 1;// 胆码的组合计算结果
					var pos = 0;
					for ( var i = 0; i < n; i++) {
						if (comb[i] == true) {
							c *= danBets[i];
							if (c > MAX_BETUNITS) {
								throw MAX_BETUNITS_ERROR_MSG;
							}
							pos++;
							if (pos == m)
								break;
						}
					}

					if (m < needs) {// 需要拖码
						var c2 = simpleCountFn(unDanBets, needs - m);
						count += c * c2;
					} else {// 不需要拖码
						count += c;
					}
					if (count > MAX_BETUNITS) {
						throw MAX_BETUNITS_ERROR_MSG;
					}
				});

			rs = count;
			cacheCountResult(key, rs);// 缓存计算结果
		}

		return rs;
	}

	/**
	 * 无设胆的注数计算
	 * 
	 * @param bets 可选数组
	 * @param needs 需要的场次数目（过几关）
	 * @return 注数
	 */
	function simpleCountFn(bets, needs) {
		var key = "C3_" + bets + "_" + needs;// 用于 缓存计算结果的KEY
		var rs = countCache.getItem(key);// 从 缓存查找是否已经有计算结果

		if (rs == null) {
			var count = 0;
			C3(bets.length, needs, function(comb, n, m) {
				var c = 1;
				var pos = 0;
				for ( var i = 0; i < n; i++) {
					if (comb[i] == true) {
						c *= bets[i];
						if (c > MAX_BETUNITS) {
							throw MAX_BETUNITS_ERROR_MSG;
						}
						pos++;
						if (pos == m)
							break;
					}
				}
				count += c;
				if (count > MAX_BETUNITS) {
					throw MAX_BETUNITS_ERROR_MSG;
				}
			});

			rs = count;
			cacheCountResult(key, rs);// 缓存计算结果
		}

		return rs;
	}
	
	
	
	/**
	 * 检查是否可选择(单玩)
	 */
	function canChoose(matchKey,playType) {
		var hashMatchKey = playType + matchKey;
		if (hashMatchKey in hashObj) {
			return true;
		 }else{
			 for(var i=0;i<playTypes.length;i++){
				 var obj = hashObj[playTypes[i] + matchKey];
				 if(typeof(obj) != "undefined" || obj!=null){
					 return false;
				 }
			 }
		 }
		return true;
	};
	
	window.canChoose = function(matchKey,playType){
		return canChoose(matchKey,playType);
	};
	
	window.getRowItemValues = function(playType) {
    	if('SF' == playType){
    		return  window.rowItemValues_SF;
    	}else if('RFSF' == playType){
    		return  window.rowItemValues_RFSF;
    	}else if('SFC' == playType){
    		return  window.rowItemValues_SFC;
    	}else if('DXF' == playType){
    		return window.rowItemValues_DXF;
    	}
    };
	
	window.rowBatch = function(matchIndex, matchKey, rowIndex, event,playType) {
		var baoEl = $$(playType+'_bao_' + rowIndex + '_' + matchKey);
		if (event != null) {
			var target = Event_target(event);// event.target
			if (target.id != baoEl.id)
				baoEl.checked = !baoEl.checked;
		}
		var rowVal = getRowItemValues(playType)[rowIndex];
		rowSelected(matchIndex, matchKey, rowVal, baoEl.checked,playType);
	};
	
	function rowSelected(matchIndex, matchKey, rowVal, checked, playType) {
		var hashMatchKey = playType+matchKey;
		var chgCount = 0;
		var playItem = play2playItem[playType];
		for (var k=0;k<playItem.length;k++) {
			if ((rowVal & (1 << k)) > 0) {
				var chkEl = $$(playType+'_chk_' + matchKey + '_' + k);
				if (chkEl.checked != checked) {
					$$(playType+'_chk_' + matchKey + '_' + k).checked = checked;
					var tdEl = $$(playType+'_td_' + matchKey + '_' + k);
					handleCheckedOfTd(tdEl, checked, playType,matchKey,k);
					var r = handleItemChgFn(hashMatchKey, matchIndex, matchKey, k, checked, playType);
					chgCount += r;
				}
			}
		}
		resetSelectedMatchkey();
		displayOption();
		if (chgCount != 0) {
			updateBetUnits(0);
			displayPassType();
		} else {
			countBetUnits();
		}
	}
	

	window.prizeForecast = function(){
		var createForm = getCreateForm();
		var units = createForm.elements['createForm.units'].value;
		if (units == 0) {
			setCommonResult("请选择过关类型！", false);
			return false;
		}
		var formActionUrl = createForm.action;
		var formTarget = createForm.target;
		createForm.action = formActionUrl.replace(/![a-zA-Z]+./ig, '!prizeForecast.');
		createForm.target = '_blank';
		createForm.submit();
		createForm.action = formActionUrl;
		createForm.target = formTarget;
	};
	
	
	window.bonusOptimize = function(url){
		if(seletedMatchCount>MAX_OPTIMIZE_MATCHS){
			setCommonResult("奖金优化最多不能超过"+MAX_OPTIMIZE_MATCHS+"场！", false);
			return false;
		}
		var createForm = getCreateForm();
		var units = createForm.elements['createForm.units'].value;
		if (units == 0) {
			setCommonResult("请选择过关类型！", false);
			return false;
		}
		if(units>MAX_OPTIMIZE_UNITS){
			setCommonResult("奖金优化最大不能超"+MAX_OPTIMIZE_UNITS+"注！", false);
			return false;
		}
		if (canOptimize!=null && !canOptimize){
			setCommonResult("只支持2串1 - 8串1的复式倍投方案！", false);
			return false;
		}
		var formActionUrl = createForm.action;
		var formTarget = createForm.target;
		createForm.action = url;
		createForm.target = '_blank';
		createForm.submit();
		createForm.action = formActionUrl;
		createForm.target = formTarget;
	};
	
});
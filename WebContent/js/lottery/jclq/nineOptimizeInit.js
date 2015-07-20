$(function() {
	var hashObj = {};// 存放选择的选项
	var prizeForecasts = [];//奖金预测结果
	var selectedMatchkey = [];//已选择的场次
	var maxPassMatchCount = 2; //九场优化为2串1
	var selected_class = 'tdchosed';
	var danCount=0;
	var prizeForecast_content_default='';//奖金预测原始表格
	var contentCount = 0;//优化后的内容数
	var multiples = [];//保存优化后的倍投数组
	var playTypes=['SPF','RQSPF'];//玩法
	var items=['胜','平','负'];//玩法选项
	var itemCount=items.length;//玩法选项
	var lastItem=new Object();//记录最后一个选择对象

	window.maxPassMatchCount = maxPassMatchCount;
	
	/**
	 * 提交奖金优化form
	 */
	function submitOptimizeForm(){
		var optimizeForm = $$('optimizeForm');
		var options = {
			type : 'POST',
			cache : false,
			data : {
				ajax : 'true'
			},
			success : function(data, textStatus) {
				var jsonObj = toJsonObject(data);
				var msg = getCommonMsg(jsonObj);
				if (jsonObj.success == true) {	
					submitSuccess(jsonObj);
				} else {
					//计算后可能出现注数超出（需要从原有的选择对象中移除最后一个选择项）
					removeLastItem();
					setCommonResult(msg, false);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				setCommonResult('提交请求失败.', false);
			}
		};
		$(optimizeForm).ajaxSubmit(options);
	}
	
	/**
	 * 从原有的选择对象中移除最后一个选择项
	 */
	function removeLastItem(){		
		var hashMatchKey = lastItem.playType + lastItem.matchKey;
		var operObj=hashObj[hashMatchKey];
		var v = 1 << lastItem.itemIdex;
		operObj.value ^= v;
		if (operObj.value == 0) {
			hashObj_remove(hashMatchKey);
		} else {
			operObj.count -= 1;
		}
		
		var tdEl = $$(lastItem.playType + '_td_' + lastItem.matchKey + '_' + lastItem.itemIdex);
		$(tdEl).removeClass(selected_class); 
	}
	
	/**
	 * 提交成功后的相关操作
	 */
	function submitSuccess(jsonObj){
		displayPrizeOptimize(jsonObj);
		buildPrizeForecast(jsonObj.prizeForecast);
		updateMultiple();
		setMatchSp(jsonObj);
		$$('playType').value=jsonObj.playType;
		$$('playTypeWeb').value=jsonObj.playTypeWeb;
	}
	
	/**
	 * 设置场次赔率
	 */
	function setMatchSp(jsonObj){
		var sps = jsonObj.sps;
		var spsCount = sps.length;
		var html = '';
		for(var i=0;i<spsCount;i++){
			html+='<input type="hidden" name="createForm.sps[' + i + ']" value="' + sps[i] + '" />';
		}
		$$('div_sps').innerHTML = html;
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
			var handicap_value = $$('handicap_'+matchKey).innerHTML;
			var handicap_css = (parseInt(handicap_value)>0)?'red':'blue';
			itemHtmlOfMatch +='<td><span class="'+ handicap_css +'">'+handicap_value+'</span></td>';
			itemHtmlOfMatch +='<td>';
			var objOfMatch=null;
			for(var j=0;j<playTypes.length;j++){
				var obj = hashObj[playTypes[j]+matchKey];
				if(obj==null){;//某场某个玩法没有选值
					continue;
				}else{
					objOfMatch = obj;
				}
				for(var itemOrdinal=0;itemOrdinal<itemCount;itemOrdinal++){
					if((obj.value & 1<<itemOrdinal)>0){//选中
						var spid = playTypes[j]+'_sp_'+matchKey+'_'+itemOrdinal;
						itemHtmlOfMatch +=items[itemOrdinal]+'('+ $$(spid).innerHTML +')，';
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
	 * 展示优化的信息
	 */
	function displayPrizeOptimize(jsonObj){
		$$('schemeCost').innerHTML = jsonObj.schemeCost;
		$$('units').innerHTML = jsonObj.units;
		$$('createForm.schemeCost').value=jsonObj.schemeCost;
		$$('createForm.units').value=jsonObj.units;
		var html = '';
		multiples = [];
		var results = jsonObj.results;
		contentCount = results.length;
		for(var i=0;i<contentCount;i++){
			html+=getCreateFormDataHtml(results[i], i);
			multiples.push(results[i][1]);
		}
		html+= '<input type="hidden" name="createForm.matchKeys" value="' + jsonObj.matchKeys + '" />';
		$$('schemeFormData').innerHTML = html;
		$$('createForm.bestMinPrize').value=jsonObj.bestMinPrize;
		$$('createForm.bestMaxPrize').value=jsonObj.bestMaxPrize;
	}
	
	/**
	 * 为createForm组合相关的方案数据
	 */
	function getCreateFormDataHtml(obj, index) {
		var html = '';
		html += '<input type="hidden" name="createForm.contents[' + index
				+ ']" value="' + obj[3] + '" /><input type="hidden" name="createForm.multiples[' + index
				+ ']" value="' + obj[1] + '" /><input type="hidden" name="createForm.playTypes[' + index
				+ ']" value="' + obj[4] + '" />';
		return html;
	}
	
	/**
	 * 保存提交的奖金预测结果，供修改倍投使用(不需要再次提交)
	 */
	function buildPrizeForecast(prizeForecast){
		prizeForecasts=[];
		for(var i=0;i<prizeForecast.length;i++){
			prizeForecasts.push({passMatchCount:prizeForecast[i][0],
								 prizeMin:prizeForecast[i][1],
								 prizeMax:prizeForecast[i][2],
								 wonMin:prizeForecast[i][3],
								 wonMax:prizeForecast[i][4]});
		}
		displayPrizeForecast(prizeForecasts);
	}
	
	/**
	 * 展示奖金预测信息
	 */
	function displayPrizeForecast(prizeForecasts){
		if(prizeForecasts.length==0)return;
		var multiple = $$('multiple').value;
		var html = '<table width="278" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#ccc;" class="tableb1">'
				+'<tr align="center" class="tdgraylist"  height="24">'
				+'<td>命中场数</td>'
				+'<td>中出奖金范围</td>'
				+'<td>回报率</td>'
				+'</tr>';
		
		var htmlDialog = '<table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">'
			+'<tr align="center" class="tdetailtr">'
			+'<td colspan="6">方案奖金预测详情</td>'
			+'</tr>'
			+'<tr align="center" class="v01tr">'
			+'<td>命中场数</td>'
			+'<td>中出奖金范围（小-大） </td>'
			+'<td>奖金回报率 </td>'
			+'</tr>';
		
		for(var i=0;i<prizeForecasts.length;i++){
			var obj = prizeForecasts[i];
			html += '<tr align="center" class="tdwhitelist" height="28">'
				+'<td>中'+obj.passMatchCount+'场</td>'
				+'<td class="rc0">'+parseFloat(obj.prizeMin).toFixed(0)*multiple+'~'+parseFloat(obj.prizeMax).toFixed(0)*multiple+'</td>'
				+'<td>'+obj.wonMin+'~'+obj.wonMax+'</td>'
				+'</tr>';
			
			htmlDialog += '<tr align="center" class="v02tr">'
				+'<td>中'+obj.passMatchCount+'场</td>'
				+'<td><span class="red eng">'+parseFloat(obj.prizeMin).toFixed(0)*multiple+'~'+parseFloat(obj.prizeMax).toFixed(0)*multiple+'</span></td>'
				+'<td><span class="red eng">'+obj.wonMin+'~'+obj.wonMax+'</span></td>'
				+'</tr>';
		}
		html +='</table>';
		htmlDialog +='</table>';
		$$('prizeForecast_content').innerHTML = html;
		$$('prizeForecastOfDialog').innerHTML = htmlDialog;
	}

	/**
	 * 场次设胆操作(针对让球，不让球)
	 */
	window.clickShedan = function(matchKey,el) {
		var obj = hashObj['SPF'+matchKey];
		if(obj!=null){
			obj.dan = el.checked;
		}		
		obj = hashObj['RQSPF'+matchKey];
		if(obj!=null){
			obj.dan = el.checked;
		}
		displayOption();
		submitOptimizeForm();
		
		if(el.checked==true){
			setDanCheckbox(true);
			obj = $$('dan_'+matchKey);
			obj.disabled=false;
			danCount++;
		}else{
			setDanCheckbox(false);
			danCount--;
		}
	};
	
	/**
	 * 选项操作
	 */
	window.clickItem = function(matchIndex, matchKey, itemIndex, event, playType) {
		remenberLastItem(matchKey, itemIndex, playType);
		
		if(prizeForecast_content_default==''){
			prizeForecast_content_default = $$('prizeForecast_content').innerHTML;
		}
		var hashMatchKey = playType + matchKey;
		clickItemFn(hashMatchKey, matchIndex, matchKey, itemIndex, event,playType);
		displayOption();
		var matchCount = resetSelectedMatchkey();		
		if(matchCount>maxPassMatchCount){
			submitOptimizeForm();
			if(danCount==0){
				setDanCheckbox(false);
			}else{
				var selectedLength=selectedMatchkey.length;
				for(var j=0;j<selectedLength;j++){
					var matchKey = selectedMatchkey[j];
					if($$('dan_'+matchKey).checked){
						$('#dan_'+matchKey).attr("disabled",false);
					}
				}
			}				
		}else{
			resetValue();
		}
	};
	
	/**
	 * 重置方案投注相关信息
	 */
	function resetValue(){
		$$('schemeCost').innerHTML = 0;
		$$('createForm.schemeCost').value=0;
		$$('units').innerHTML = 0;
		$$('createForm.units').value = 0;
		$$('prizeForecast_content').innerHTML =prizeForecast_content_default;
		closeDialog('dialogDiv');
		prizeForecasts=[];
		setDanCheckbox(true);		
	}
	
	function resetSelectedMatchkey(){
		var matchCount = 0;
		selectedMatchkey = [];
		for(var kk in hashObj){
			matchCount++;
			//取选中的场次key
			var isContained = false;//是否包含
			var obj = hashObj[kk];
			var selectedLength=selectedMatchkey.length;
			for(var j=0;j<selectedLength;j++){
				if(selectedMatchkey[j]==obj.matchKey){
					isContained = true;
					break;
				}
			}
			if(!isContained){
				selectedMatchkey.push(obj.matchKey);
			}
		}
		return matchCount;
	}
	
	/**
	 * 记录最后一个操作对象
	 */
	function remenberLastItem(matchKey, itemIndex, playType){
		lastItem.matchKey = matchKey;
		lastItem.itemIdex = itemIndex;
		lastItem.playType = playType;
	}
	
	function setDanCheckbox(disabled){
		var objs=document.getElementsByName("dans");
		var objLength = objs.length;
		if(!disabled){
			var selectedLength=selectedMatchkey.length;
			for(var i=0;i<objLength;i++){
				var obj = objs[i];
				var matchKeyOfObj = obj.id.replace('dan_', '');
				var isExists=false;
				for(var j=0;j<selectedLength;j++){
					if(matchKeyOfObj==selectedMatchkey[j]){
						isExists=true;
						break;
					}
				}
				if(isExists){
					obj.disabled=disabled;
				}else{
					obj.disabled=!disabled;
				}
			}
		}else{
			for(var i=0;i<objLength;i++){
				var obj = objs[i];
				obj.disabled=disabled;
			}
		}
	}
	
	function clickItemFn(hashMatchKey, matchIndex, matchKey, itemIndex, event,playType) {
		var chkEl = $$(playType+'_chk_' + matchKey + '_' + itemIndex);
		if (event != null) {
			Event_stopPropagation(event);// event.stopPropagation();
			var target = Event_target(event);// event.target
			if (target.id != chkEl.id)
				chkEl.checked = !chkEl.checked;
		} else {
			chkEl.checked = !chkEl.checked;
		}
		var tdEl = $$(playType+'_td_' + matchKey + '_' + itemIndex);
		handleCheckedOfTd(tdEl, chkEl.checked);		
		handleItemChg(hashMatchKey, matchIndex, matchKey, itemIndex, chkEl.checked, playType);
	}	
	
	function handleCheckedOfTd(tdEl,checked){
		if (checked) {
			$(tdEl).addClass(selected_class); 
		} else {
			$(tdEl).removeClass(selected_class); 
		}
	}
	
	function handleItemChg(hashMatchKey, matchIndex, matchKey, itemIndex, checked,playType) {
		var r = handleItemChgFn(hashMatchKey, matchIndex, matchKey, itemIndex, checked, playType);		
		if (r != 0) {}
	}
	

	/**
	 * 处理选项变化的函数
	 * 
	 * @param matchKey 场次序号
	 * @param itemIndex 选项序号
	 * @param checked 是否选中
	 * @param playType 玩法
	 * @return 1表示选中场次增加,-1表示选中场次减少,0表示选中场次数目无变化
	 */
	function handleItemChgFn(hashMatchKey, matchIndex, matchKey, itemIndex, checked, playType) {
		if (hashMatchKey in hashObj) {
			var obj = hashObj[hashMatchKey];
			var v = 1 << itemIndex;
			if (checked == true) {
				obj.value |= v;
				obj.count += 1;
				return 0;
			} else {
				obj.value ^= v;
				if (obj.value == 0) {
					hashObj_remove(hashMatchKey);
					var objSpf = hashObj['SPF'+matchKey];
					var objRqspf = hashObj['RQSPF'+matchKey];
					if(objSpf==null && objRqspf==null){
						if($$('dan_'+matchKey).checked){
							$('#dan_'+matchKey).attr("checked",false);							
							danCount--;
							setDanCheckbox(false);
						}						
						resetSelectedMatchkey();
					}		
					return -1;
				} else {
					obj.count -= 1;
					return 0;
				}
			}
		} else if (checked == true) {
			var hashObjTemp={};//生成一个新的hash对原先的场次进行调整
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
			return 1;
		}
	}

	function hashObj_push(hashMatchKey, obj) {
		hashObj[hashMatchKey] = obj;
	}
	function hashObj_remove(hashMatchKey) {
		delete hashObj[hashMatchKey];
	}

	/**
	 * 组装优化form
	 */
	function displayOption() {
		var html = '';
		var index = 0;
		for(var hashMatchKey in hashObj){
			var obj = hashObj[hashMatchKey];
			if (obj != null) {
				html += getItemHtml(obj, index);
				index++;
			}
		}
		$$('div_op').innerHTML = html;
		$$('div_item').innerHTML = html;
	}
	
	/**
	 * 为form组合相关的选项数据
	 */
	function getItemHtml(obj, index) {
		var html = '';
		html += '  <input type="hidden" name="createForm.items[' + index
				+ '].matchKey" value="' + obj.matchKey + '" /><input type="hidden" name="createForm.items[' + index
				+ '].value" value="' + obj.value + '" /><input type="hidden" name="createForm.items[' + index
				+ '].playType" value="' + obj.playType + '" /><input type="hidden" name="createForm.items[' + index
				+ '].dan" value="'+ obj.dan +'"/>';
		return html;
	}
		
	
	/**
	 * 修改倍投
	 */
	window.updateMultiple = function(){
		var createForm = getCreateForm();
		var multiple = $$('multiple').value;
		for(var i=0;i<contentCount;i++){
			var objectName = 'createForm.multiples['+i+']';
			var mobj = createForm.elements[objectName];
			mobj.value = multiples[i]*multiple;
		}
  		updateBetCost();
  		displayPrizeForecast(prizeForecasts);
  		buildConfirmData();
  	};
	
	
});
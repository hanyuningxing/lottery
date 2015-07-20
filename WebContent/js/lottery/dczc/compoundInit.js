$(function() {
	var countCache = new Cache(100);// 缓存注数计算结果
	var hashArray = {};// 存放选择的选项
	var selectedMatchkey = [];//已选择的场次
	var seletedMatchCount = 0; // 当前选中的场次数
	var MAX_BETUNITS = 10000;
	var MAX_BETUNITS_ERROR_MSG = '单倍注数不能超过' + MAX_BETUNITS + '注。';
	var div_mohushedan_default='';
	
	var selected_class = 'tdchosed';
	var unselected_class = 'tdyelnor';

	if (maxPassMatchCount == null) {
		alert('本玩法最大的过关场次数未设置！');
		return;
	}

	var updateBetUnits_parent = updateBetUnits;
	window.updateBetUnits = function(units) {
		var msgEl = document.getElementById('span_msg');
		var errEl = document.getElementById('span_err');
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

	function hashArray_push(lineId, obj) {
		var objOfHash;
		var isContain=false;
		for(var k in hashArray){
			objOfHash = hashArray[k];				
			if(objOfHash.lineId==obj.lineId){
				isContain=true;
			}
		}
		if(!isContain){
			seletedMatchCount++;
		}
		hashArray[lineId] = obj;
	}
	

	
	function hashArray_remove(lineId) {
		var objOfHash;
		var obj = hashArray[lineId];
		if(obj==null)return;
		delete hashArray[lineId];
		var isContain=false;
		for(var k in hashArray){
			objOfHash = hashArray[k];				
			if(objOfHash.lineId==obj.lineId){
				isContain=true;
			}
		}
		if(!isContain){
			seletedMatchCount--;
		}
	}


	window.clickItem = function(lineId, itemIndex, event) {
		if(div_mohushedan_default==''){
			div_mohushedan_default = $$('div_mohushedan').innerHTML;
		}
		clickItemFn(lineId, itemIndex, event);
		resetSelectedMatchkey();
		displayOption();
		countBetUnits();
	};
	
	function resetSelectedMatchkey(){
		selectedMatchkey = [];
		for(var kk in hashArray){
			
			//取选中的场次key
			var isContained = false;//是否包含			
			var obj = hashArray[kk];
			
			for(var jj in selectedMatchkey){				
				if(selectedMatchkey[jj]==obj.lineId){
					
					isContained = true;
					break;
				}
			}
			if(!isContained){
				selectedMatchkey.push(obj.lineId);
			}
		}
		
	}

	function clickItemFn(lineId, itemIndex, event) {
		var chkEl = document.getElementById('chk_' + lineId + '_' + itemIndex);
		if (event != null) {
			Event_stopPropagation(event);// event.stopPropagation();
			var target = Event_target(event);// event.target
			if (target.id != chkEl.id)
				chkEl.checked = !chkEl.checked;
		} else {
			chkEl.checked = !chkEl.checked;
		}

		var tdEl = document.getElementById('td_' + lineId + '_' + itemIndex);
		if (chkEl.checked) {
			tdEl.className = selected_class;
		} else {
			tdEl.className = unselected_class;
		}
		handleItemChg(lineId, itemIndex, chkEl.checked);
	}

	function handleItemChg(lineId, itemIndex, checked) {
		var r = handleItemChgFn(lineId, itemIndex, checked);
		if (lineId in hashArray) {
			var obj = hashArray[lineId];
			for ( var i = 0; i < rowItemValues.length; i++) {
				var rowVal = rowItemValues[i];
				var baoEl = document.getElementById('bao_' + i + '_' + lineId);
				if(baoEl != null)
					baoEl.checked = (obj.value & rowVal) == rowVal;
			}
		} else {
			for ( var i = 0; i < rowItemValues.length; i++) {
				var baoEl = document.getElementById('bao_' + i + '_' + lineId);
				if(baoEl != null)
					baoEl.checked = false;
			}
		}
		if (r != 0) {
			// 清空设胆
			for ( var i = 0; i < hashArray.length; i++) {
				var obj = hashArray[i];
				if (obj != null && obj.dan == true)
					obj.dan = false;
			}

			displayPassType();
		}
	}

	/**
	 * 处理选项变化的函数
	 * 
	 * @param lineId 场次序号
	 * @param itemIndex 选项序号
	 * @param checked 是否选中
	 * @return 1表示选中场次增加,-1表示选中场次减少,0表示选中场次数目无变化
	 */
	function handleItemChgFn(lineId, itemIndex, checked) {
		if (lineId in hashArray) {
			var obj = hashArray[lineId];
			var v = 1 << itemIndex;
			if (checked == true) {
				obj.value |= v;
				obj.count += 1;
				return 0;
			} else {
				obj.value ^= v;
				if (obj.value == 0) {
					hashArray_remove(lineId);
					return -1;
				} else {
					obj.count -= 1;
					return 0;
				}
			}

		} else if (checked == true) {
			hashArray_push(lineId, {
				lineId : lineId,
				home : document.getElementById('td_h_' + lineId).innerHTML,
				guest : document.getElementById('td_g_' + lineId).innerHTML,
				value : 1 << itemIndex,
				count : 1,
				dan : false
			});
			return 1;
		}
	}

	window.deleteLine = function(lineId) {
		var obj = hashArray[lineId];
		var k = 0;
		for ( var p in PlayItem) {
			if ((obj.value & (1 << k)) > 0) {
				document.getElementById('chk_' + lineId + '_' + k).checked = false;
				var tdEl = document.getElementById('td_' + lineId + '_' + k);
				tdEl.className = unselected_class;
			}
			k++;
		}
		for ( var i = 0; i < rowItemValues.length; i++) {
			var baoEl = document.getElementById('bao_' + i + '_' + lineId);
			if(baoEl != null)
				baoEl.checked = false;
		}
		hashArray_remove(lineId);
		displayOption();
		displayPassType();
		updateBetUnits(0);
	};

window.rowBatch = function(lineId, rowIndex, event) {
		
		for(var k=0; k<1; k++){
			for(var j=0;j<playTypes.length;j++){
				var playType = playTypes[j];
				var preSize = 0;
				if(playType == "BF"){										
					if(rowIndex==0){
						var playItemArr = play2playItem["胜"]; 
					} else if(rowIndex==1){
						preSize = play2playItem["胜"].length;
						var playItemArr = play2playItem["平"]; 
					} else if(rowIndex==2){
						preSize = play2playItem["胜"].length + play2playItem["平"].length;
						var playItemArr = play2playItem["负"];
					}
						
				} else {
					var playItemArr = play2playItem[playType];
				}
				var maxSize = playItemArr.length;
				var isRowCh = true;
				for(var i=0;i<maxSize;i++){
					if(document.getElementById("td_" + lineId + "_" + (preSize + i)).className!=selected_class){
						isRowCh = false;
						break;
					}
				}
				
				if(isRowCh) {
					for(var i=0;i<maxSize;i++){
						clickItem(lineId, (preSize + i));
					}
				} else {				
					for(var i=0;i<maxSize;i++){
						if(document.getElementById("td_" + lineId + "_" + (preSize + i)).className!=selected_class)
						clickItem(lineId, (preSize + i));
					}
				}
			}
		}
	};

	window.halfAndFullSelect = function(lineId) {
		var fullVal = document.getElementById('full_select_' + lineId).value;
		var halfVal = document.getElementById('half_select_' + lineId).value;
		var rowVal = fullVal & halfVal;
		;
		var k = 0;
		var chgCount = 0;
		for ( var p in PlayItem) {
			var checked = (rowVal & (1 << k)) > 0;
			var chkEl = document.getElementById('chk_' + lineId + '_' + k);
			if (chkEl.checked != checked) {
				chkEl.checked = checked;
				var tdEl = document.getElementById('td_' + lineId + '_' + k);
				if (checked) {
					tdEl.className = selected_class;
				} else {
					tdEl.className = unselected_class;
				}
				var r = handleItemChgFn(lineId, k, checked);
				chgCount += r;
			}
			k++;
		}
		displayOption();
		if (chgCount != 0) {
			displayPassType();
			updateBetUnits(0);
			countBetUnits();
		} else {
			countBetUnits();
		}
	};

	function rowSelected(lineId, rowVal, checked) {
		var k = 0;
		var chgCount = 0;
		for ( var p in PlayItem) {
			if ((rowVal & (1 << k)) > 0) {
				var chkEl = document.getElementById('chk_' + lineId + '_' + k);
				if (chkEl.checked != checked) {
					document.getElementById('chk_' + lineId + '_' + k).checked = checked;
					var tdEl = document.getElementById('td_' + lineId + '_' + k);
					if (checked) {
						tdEl.className = selected_class;
					} else {
						tdEl.className = unselected_class;
					}
					var r = handleItemChgFn(lineId, k, checked);
					chgCount += r;
				}
			}
			k++;
		}
		displayOption();
		if (chgCount != 0) {
			displayPassType();
			updateBetUnits(0);
			countBetUnits();
		} else {
			countBetUnits();
		}
	}

	window.columnBatch = function(itemIndex, event) {
		var columnEl = document.getElementById('column_' + itemIndex);
		if (event != null) {
			var target = Event_target(event);// event.target
			if (target.id != columnEl.id)
				columnEl.checked = !columnEl.checked;
		}
		var checked = columnEl.checked;
		var matchTbody = document.getElementById('matchTbody');
		var rows = matchTbody.rows;
		var chgCount = 0;
		for ( var i = 1, len = rows.length; i < len; i++) {
			var row = rows[i];
			if (row.getAttribute('_m') != null && row.getAttribute('_e') == '0' && row.style.display != 'none') {
				var lineId = parseInt(row.getAttribute('_lid'), 10);
				var chkEl = document.getElementById('chk_' + lineId + '_' + itemIndex);
				if (chkEl.checked != checked) {
					document.getElementById('chk_' + lineId + '_' + itemIndex).checked = checked;
					var tdEl = document.getElementById('td_' + lineId + '_' + itemIndex);
					if (checked) {
						tdEl.className = selected_class;
					} else {
						tdEl.className = unselected_class;
					}
					var r = handleItemChgFn(lineId, itemIndex, checked);
					chgCount += r;
				}
			}
		}
		displayOption();
		if (chgCount != 0) {
			displayPassType();
			updateBetUnits(0);
			countBetUnits();
		} else {
			countBetUnits();
		}
	};

	function getItemHtml(obj, index, canSetDan, canAddDan) {
		var danInputStr;
		if (canSetDan) {
			styleStr = '';
			if (obj.dan)
				danInputStr = 'checked="checked"';
			else
				danInputStr = canAddDan ? '' : 'disabled="disabled"';
		} else {
			styleStr = 'style="display:none"';
			danInputStr = 'disabled="disabled"';
		}
		var html = '';
		html += '<tr style="display:none" class="trw" height="24">';
		var op_handicap = document.getElementById('op_handicap');
		html += '  <td class="option_show"><input type="hidden" name="createForm.items[' + index
				+ '].lineId" value="' + obj.lineId + '" /><input type="hidden" name="createForm.items[' + index
				+ '].value" value="' + obj.value + '" />';
		var k = 0;
		var valueIndex = 0;
		var spStr = '';
		for ( var p in PlayItem) {
			if ((obj.value & (1 << k)) > 0) {
				var item = PlayItem[p];
				html += '<li><input type="checkbox" checked="checked" onclick="clickItem(' + obj.lineId + ',' + k
						+ ',event);" />' + item.text+"</li>";
				var spSpan = document.getElementById('sp_lid' + obj.lineId + '_' + k);
				var sp;
				if (spSpan != null)
					sp = spSpan.innerHTML;
				else
					sp = 0.00;
				if (spStr != '')
					spStr += ',';
				spStr += sp;
				valueIndex++;
			}
			k++;
		}
		html += '<input type="hidden" name="spss[' + index + ']" value="' + spStr + '" />';
		html += '  </td>';
		html += '</tr>';
		return html;
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
		
		var index = 0;
		for ( var k in hashArray) {
			var obj = hashArray[k];
			if (obj != null) {
				html += getItemHtml(obj, index, canSetDan, canAddDan);
				index++;
			}
		}		
		
		html += '</table>';
		
		$$('div_show').innerHTML = html;
		
		$$('createForm_selectedMatchCount').innerHTML = seletedMatchCount;
	}
	
	/**
	 * 选项显示
	 */
	function getItemShowHtml(matchKey, index, canSetDan, canAddDan) {
		var obj=null;
		var hashMatchKey;
		for(var i=0;i<playTypes.length;i++){
			hashMatchKey = playTypes[i] + matchKey;
			obj = hashArray[hashMatchKey];
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
		
		html += '  <td width="30%" height="30" align="center" bgcolor="#FFFFFF"><a href="javascript:;" onclick="removeMatch(\''+ matchKey +'\')"><img src="'+ window.BASESITE +'/V1/images/chacha.gif" border="0" /></a> '+ $$('text_'+matchKey).innerHTML +'</td>';		
		html += '  <td align="center" bgcolor="#FFFFFF" width="58%">'+ $$('td_h_'+matchKey).innerHTML +'  VS  '+ $$('td_g_'+matchKey).innerHTML +'</td>';
		html += '  <td align="center" bgcolor="#FFFFFF" class="blue_0091d4" '+ danStyleStr +'><input _matchKey="'+ matchKey +'" name="createForm.items[' + index
		+ '].dan" '+ danInputStr +' type="checkbox" value="true" onclick="clickShedan(this);"/></td>';
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
			if(playType == "BF"){	
				var playItemArr1 = play2playItem["胜"];				
				var playItemArr2 = play2playItem["平"];
				var playItemArr3 = play2playItem["负"];
				var playItemArr = new Array();
				var arr1length = playItemArr1.length;
				var arr2length = playItemArr2.length;
				var arr3length = playItemArr3.length;
				for(j=0; j<arr1length; j++) {
					playItemArr[j] = playItemArr1[j];
				} 								
				
				for(j=0; j<arr2length; j++) {
					playItemArr[arr1length  + j] = playItemArr2[j];
				} 
				
				for(j=0; j<arr3length; j++) {
					playItemArr[arr1length + arr2length + j] = playItemArr3[j];
				} 
				
			} else {
				var playItemArr = play2playItem[playType];
			}
			var obj = hashArray[matchKey];
			if(obj==null){;//某场某个玩法没有选值
				continue;
			}else{
				objOfMatch = obj;
			}
			for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
				if((obj.value & 1<<itemOrdinal)>0){//选中
					var itemClassName;
					if(selectedItemClass.length>1){
						itemClassName=selectedItemClass[itemOrdinal];
					}else{
						itemClassName=selectedItemClass[0];
					}
					//增加让分显示还有title以便用户查看
					var rf='';
					var title = '';
					try{
					if(playType=='RQSPF'){
						var  handicap_ = $$('handicap_' + matchKey);
						if(handicap_&&handicap_.innerHTML){
							rf = '('+parseInt(handicap_.innerHTML)+')';
						}
					}
					var playTypeIt = playTypeItem[playType];
					if(playTypeIt){
						title=title+"玩法类型("+playTypeIt.name+").投注选项["+rf+playItemArr[itemOrdinal]+"]";
					}
					}catch(e){
					  //保守	
					}
					//selectedItemsOfMatch+='<a id=xz_' + matchKey + '_' + itemOrdinal +'title ="'+title+'" href="javascript:;" onclick="removeItem(\''+matchKey+"\',\'"+playType+"\',\'"+itemOrdinal+'\')" class="'+ itemClassName +'">'+rf+playItemArr[itemOrdinal]+'</a>';
					selectedItemsOfMatch+='<a' +  ' id=xz_' + matchKey +  '_' + itemOrdinal + ' title ="'+title+'" href="javascript:;" onclick="removeItem(\''+matchKey+"\',\'"+playType+"\',\'"+itemOrdinal+'\')" class="'+ itemClassName +'">'+rf+playItemArr[itemOrdinal]+'</a>';
				
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
	
	
	function isMultiplePass() {
		var obj = $$('createForm.schemeType');
		return obj.value=='MULTIPLE_PASS';
	}

	window.clickShedan = function(el) {		
		var matchKey = el.getAttribute('_matchKey');
		for(var j=0;j<playTypes.length;j++){
			var obj = hashArray[matchKey];
			if(obj==null){;//某场某个玩法没有选值
				continue;
			}
			obj.dan = el.checked;
		}
		updateShedan();
		countBetUnits();
	};

	/**
	 * 移除一个场次
	 */
	window.removeMatch = function(matchKey) {
		for(var j=0;j<playTypes.length;j++){
			var playType = playTypes[j];
			hashArray_remove(matchKey);
			if(playType == "BF"){	
				var playItemArr1 = play2playItem["胜"];				
				var playItemArr2 = play2playItem["平"];
				var playItemArr3 = play2playItem["负"];
				var playItemArr = new Array();
				var arr1length = playItemArr1.length;
				var arr2length = playItemArr2.length;
				var arr3length = playItemArr3.length;
				for(j=0; j<arr1length; j++) {
					playItemArr[j] = playItemArr1[j];
				} 								
				
				for(j=0; j<arr2length; j++) {
					playItemArr[arr1length  + j] = playItemArr2[j];
				} 
				
				for(j=0; j<arr3length; j++) {
					playItemArr[arr1length + arr2length + j] = playItemArr3[j];
				} 
				
			} else {
				var playItemArr = play2playItem[playType];
			}
			for(var i=0;i<playItemArr.length;i++){
				var chkEl = $$('chk_' + matchKey + '_' + i);
				chkEl.checked = false;
				var tdEl = $$('td_' + matchKey + '_' + i);
				handleCheckedOfTd(tdEl, false,playType,matchKey,i);
			}
		}
		resetSelectedMatchkey();
		updateBetUnits(0);
		displayOption();
		updateShedan();
		displayPassType();
	};
	
	/**
	 * 更新设胆和模糊设胆
	 */
	function updateShedan() {
		// 更新设胆和模糊设胆
		var createForm = getCreateForm();
		var minPassType;// 最小的过关方式
		var passTypeCount = 0;// 过关方式数目
		var arr = createForm.elements['createForm.passTypes'];
		if (arr != null) {
			if (arr.length == null) {
				arr = [ arr ];
			}
			for ( var i = 0, len = arr.length; i < len; i++) {
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
				if (passTypeCount == 1) {
					canShedanNum--;
				}
			}
		}

		var danSize = 0; // 胆码数目
		var unDanSize = 0; // 非胆码数目
		if (canShedanNum < 1) { // 可设胆数目小于1，即不能设胆，所有设胆置为不可用
			for ( var i = 0; i < seletedMatchCount; i++) {
				var danmaEl = createForm.elements['createForm.items[' + i + '].dan'];
				danmaEl.checked = false;
				danmaEl.disabled = true;
			}
		    ////把所有都设为非胆
			for ( var i = 0; i < hashArray.length; i++) {
				var obj = hashArray[i];
				if (obj != null && obj.dan == true)
					obj.dan = false;
			}
		} else {
			var danArr = [];
			var unDanArr = [];
			for ( var i = 0; i < seletedMatchCount; i++) {
				var danmaEl = createForm.elements['createForm.items[' + i + '].dan'];
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
			var danMinHit = minPassType.matchCount - unDanSize;
			if (danMinHit < 1)
				danMinHit = 1;
			var danMaxHit = danSize;
			html += '<select id="createForm_danMinHit" name="createForm.danMinHit" onchange="chgMohuSheDan(this);">';
			for ( var i = danMaxHit; i >= danMinHit; i--) {
				html += '<option value=' + i + '>至少对' + i + '场</option>';
			}
			html += '</select>';
		}
		document.getElementById('mohushedan').innerHTML = html;
	}

	/**
	 * 切换模糊设胆
	 */
	window.chgMohuSheDan = function(obj) {
		countBetUnits();
	};

	function isSinglePass() {
		var obj = $$('createForm.schemeType');
		return obj.value=='SINGLE';
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
			$$("passMode").value = "NORMAL";
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

			for(var hashMatchKey in hashArray){
				var obj = hashArray[hashMatchKey];
				if (obj != null)
					obj.dan = false;
			}
			fn = function(el) {
				if( typeof el === 'object' ) {
					el.checked = false;
					el.disabled = true;
					el.parentNode.style.display = 'none';
				}
			};
									
			danmaHeadEl.style.display = 'none';
		} else {
			$$("passMode").value = "MULTIPLE";
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
				if( typeof el === 'object' ) {
					el.checked = false;
					el.disabled = true;
					el.parentNode.style.display = 'none';
				}
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
		document.getElementById("minPrize").innerHTML = "0.00";
		document.getElementById("maxPrize").innerHTML = "0.00";
	};
	
	/**
	 * 移除一个选项
	 */
	window.removeItem = function(matchKey,playType,itemIndex){	
		clickItem(matchKey, itemIndex);
		
	};
	

	/**
	 * 清空所有选择
	 */
	window.cleanSelectedMatch = function(){
		var maxSize = selectedMatchkey.length;
		var selectedMatchkey_ = selectedMatchkey;
		for(var k=0; k<maxSize; k++){
			var matchKey = selectedMatchkey_[k];
			for(var j=0;j<playTypes.length;j++){
				var playType = playTypes[j];
				if(playType == "BF"){	
					var playItemArr1 = play2playItem["胜"];				
					var playItemArr2 = play2playItem["平"];
					var playItemArr3 = play2playItem["负"];
					var playItemArr = new Array();
					var arr1length = playItemArr1.length;
					var arr2length = playItemArr2.length;
					var arr3length = playItemArr3.length;
					for(j=0; j<arr1length; j++) {
						playItemArr[j] = playItemArr1[j];
					} 								
					
					for(j=0; j<arr2length; j++) {
						playItemArr[arr1length  + j] = playItemArr2[j];
					} 
					
					for(j=0; j<arr3length; j++) {
						playItemArr[arr1length + arr2length + j] = playItemArr3[j];
					} 
					
				} else {
					var playItemArr = play2playItem[playType];
				}
				
				for(var k=0;k<selectedMatchkey.length;k++){
					var matchKey = selectedMatchkey[k];
					for(var j=0;j<playTypes.length;j++){
						var playType = playTypes[j];
						hashArray_remove(matchKey);
						for(var i=0;i<playItemArr.length;i++){
							var chkEl = $$('chk_' + matchKey + '_' + i);
							chkEl.checked = false;
							var tdEl = $$('td_' + matchKey + '_' + i);
							handleCheckedOfTd(tdEl, false,playType,matchKey,i);
						}
					}
				}
				resetSelectedMatchkey();
				displayOption();
				updateShedan();
				displayPassType();
				countBetUnits();
			}
		}
	};
	
	function isMix(){
		return playType=='MIX';
	}
	
	function handleCheckedOfTd(tdEl,checked,playType,matchKey,itemIndex){
		//标题：bgcolor="#E9FCE4" class="hhggxz"   bgcolor="#f63510" class="hhggxz3"
    	//赔率：bgcolor="#D9F2FF" class="hhggxz"   bgcolor="#f63510" class="hhggxz1"		
		
			if (checked) {
				$(tdEl).removeClass(unselected_class)
				$(tdEl).addClass(selected_class); 
			} else {
				$(tdEl).removeClass(selected_class); 
				$(tdEl).addClass(unselected_class); 
			}
		
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
		}
		countBetUnits();
	};

	function displayHiddenObj(){
		var itemsHtml = '';
		//记录每个场次的选项Item
		var index = 0;
		for(var k in hashArray){
			var objOfHash = hashArray[k];
			itemsHtml+=getItemHtml(objOfHash,index);
			index++;
		}
		$$('div_items').innerHTML = itemsHtml;
	}
	

	
	/**
	 * 组合确认框投注信息
	 */
	window.buildConfirmData = function (){
	//	displayHiddenObj();
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
				SetCommonResult(e.message, false);
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
				var obj = hashArray[matchKey];
				if(obj==null){;//某场某个玩法没有选值
					continue;
				}else{
					objOfMatch = obj;
				}
				if(playType == "BF"){	
					var playItemArr1 = play2playItem["胜"];				
					var playItemArr2 = play2playItem["平"];
					var playItemArr3 = play2playItem["负"];
					var playItemArr = new Array();
					var arr1length = playItemArr1.length;
					var arr2length = playItemArr2.length;
					var arr3length = playItemArr3.length;
					for(j=0; j<arr1length; j++) {
						playItemArr[j] = playItemArr1[j];
					} 								
					
					for(j=0; j<arr2length; j++) {
						playItemArr[arr1length  + j] = playItemArr2[j];
					} 
					
					for(j=0; j<arr3length; j++) {
						playItemArr[arr1length + arr2length + j] = playItemArr3[j];
					} 
					
				} else {
					var playItemArr = play2playItem[playType];
				}
				for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
					if((obj.value & 1<<itemOrdinal)>0){//选中
						var spid = 'td_'+matchKey+'_'+itemOrdinal;
						var preStr = "";
						
						if(playType == "SPF") {
							preStr = "(" + handicap_value + ")" + playItemArr[itemOrdinal];
						} else if(playType="SXDS") {
							preStr = playItemArr[itemOrdinal];	
						} else if(playType="ZJQS") {
							preStr = "总进球数" + playItemArr[itemOrdinal];							
						} else if(BF) {
							preStr = "比分" + playItemArr[itemOrdinal];	
						} else if(BQQSPF) {
							preStr = "半全场胜平负" + playItemArr[itemOrdinal];	
						}					
							itemHtmlOfMatch += preStr +'('+ $$(spid).innerHTML +')，';
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
	
	function countBetUnits() {
		var multPass = isMultiplePass();
		if (multPass)
			countMultiplePassUnits();
		else
			countNormalPassUnits();
	}

	/**
	 * 计算最小奖金
	 * @author zhongshixing
	 */
	window.countMinPrize = function (){
		var minSpArr = new Array();//由各场次可能出现的最小SP值组成的数组(被设为胆的场次不包含在此数组) 		
		var danMinSpArr = new Array(); //由被设为胆的场次可能出现的最小SP值组成的数组
		var minPrize = 0.00;
		
		//取得minSpArr和danMinSpArr
		getMinSpArrAndDanMinSpArr(minSpArr, danMinSpArr);
		
		//获得最小奖金
		minPrize = getMinPrize(minSpArr, danMinSpArr);
		
		//显示最小奖金
		showMinPirze(minPrize);
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
	 */
	function getMinSpArrAndDanMinSpArr(minSpArr, danMinSpArr){
		for(var i=0, maxSize=selectedMatchkey.length;i<maxSize;i++){
			var matchKey = selectedMatchkey[i];
			var minSpAndDan = getMinSpAndDan(matchKey);//求出某个场次选中的最小SP并求得本场次是否为胆
			
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
		var callback = function(sp, playType, obj, itemOrdinal){
			if(minSp == -1 || sp < minSp){
				minSp = sp;
				minSpAndDan['minSp'] = minSp;
				minSpAndDan['isDan'] = obj.dan;
			}	
		};	//sp, playType, obj, itemOrdinal均为选中项的信息
		
		//遍历某个场次所有玩法，求得选中项的相关信息后执行回调函数
		forEachPlayTypesAndCallBack(matchKey, callback);				
		
		return minSpAndDan;
	}
	
	/**
	 * 遍历某个场次所有玩法，求得选中项的相关信息后执行回调函数
	 * @author zhongshixing
	 */
	function forEachPlayTypesAndCallBack(matchKey, callback, obj){
		for(var j=0;j<playTypes.length;j++){				
			var playType = playTypes[j];
			var obj = hashArray[matchKey];
			if(obj==null){//某场某个玩法没有选值
				continue;
			}else{
				objOfMatch = obj;
			}
			if(playType == "BF"){	
				var playItemArr1 = play2playItem["胜"];				
				var playItemArr2 = play2playItem["平"];
				var playItemArr3 = play2playItem["负"];
				var playItemArr = new Array();
				var arr1length = playItemArr1.length;
				var arr2length = playItemArr2.length;
				var arr3length = playItemArr3.length;
				for(j=0; j<arr1length; j++) {
					playItemArr[j] = playItemArr1[j];
				} 								
				
				for(j=0; j<arr2length; j++) {
					playItemArr[arr1length  + j] = playItemArr2[j];
				} 
				
				for(j=0; j<arr3length; j++) {
					playItemArr[arr1length + arr2length + j] = playItemArr3[j];
				} 
				
			} else {
				var playItemArr = play2playItem[playType];
			}
			for(var itemOrdinal=0;itemOrdinal<playItemArr.length;itemOrdinal++){
				if((obj.value & 1<<itemOrdinal)>0){//选中
					var spid = 'sp_lid'+matchKey+'_'+itemOrdinal;
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
		
		if(typeArr && typeArr.id=="passType_P1"){
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
			minPrize = minSpProduct*2*multiple*0.65;
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
		
		var maxPrize = spSum*2*multiple*0.65;
		
		return maxPrize;
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
	 * @author zhongshixing
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
		var spfSp = {0:0,1:0,2:0};
		var rqspfSp = {0:0,1:0,2:0};
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
				var obj = hashArray[matchKey];
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
						var obj = hashArray[matchKey];
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
	
	window.prizeForecast = function(){
		var createForm = getCreateForm();
		var units = createForm.elements['createForm.units'].value;
		if (units == 0) {
			setCommonResult("请选择过关类型！", false);
			return false;
		}
		var formActionUrl = createForm.action;
		var formTarget = createForm.target;
		createForm.action = formActionUrl.replace(/![a-zA-Z]+./ig, '!prizeForecastOfInit.');
		createForm.target = '_blank';
		createForm.submit();
		createForm.action = formActionUrl;
		createForm.target = formTarget;
	};
	
	var cur_playmode = 0;//0表示快速玩法，1表示高级玩法;
	window.chgPlayMode = function(mode){
		cur_playmode = mode != 0 ? 1 : 0;
		
		var playmode_0 = document.getElementById("playmode_0");
		var playmode_1 = document.getElementById("playmode_1");
		var tr_passmode_title = document.getElementById("tr_passmode_title");
		var tr_passmode_content = document.getElementById("tr_passmode_content");
		var tr_passtype_title = document.getElementById("tr_passtype_title");
		if(cur_playmode == 0){
			playmode_0.className = 'now';
			playmode_1.className = '';
			tr_passmode_title.style.display = 'none';
			tr_passmode_content.style.display = 'none';
			tr_passtype_title.style.display = 'none';
		}else{
			playmode_0.className = '';
			playmode_1.className = 'now';
			tr_passmode_title.style.display = '';
			tr_passmode_content.style.display = '';
			tr_passtype_title.style.display = '';
		}
		
		var createForm_passMode_0 = document.getElementById("createForm_passMode_0");
		createForm_passMode_0.checked = true;
		displayPassType();
		countBetUnits();
	};
	

	/**
	 * 更改购买方式
	 * 
	 * @param obj
	 */
	window.compoundChgShareType = function(obj) {
		var tr_TOGETHER_title = document.getElementById('tr_TOGETHER_title');
		var tr_TOGETHER_content = document.getElementById('tr_TOGETHER_content');
		if (obj.value == 'TOGETHER') {
			tr_TOGETHER_title.style.display = '';
			tr_TOGETHER_content.style.display = '';
		} else {
			tr_TOGETHER_title.style.display = 'none';
			tr_TOGETHER_content.style.display = 'none';
		}
	};
	
	/**
	 * 高级过滤
	 */
	window.filterSubmit = function(){
		var params='';
		var size = 0;
		for(var i = 0; i < hashArray.length; i++) {
			var obj = hashArray[i];
			if(null!=obj){
				var win = document.getElementById('sp_lid'+i+'_WIN').innerHTML;
				var draw = document.getElementById('sp_lid'+i+'_DRAW').innerHTML;
				var lose = document.getElementById('sp_lid'+i+'_LOSE').innerHTML;							
				params+=win+","+draw+","+lose+"#";
				size++;
			}
		}
		if(size<2){
			alert("至少选择两场比赛");
			return false;
		}else if(size>15){
			alert("最多只选择15比赛");
			return false;
		}
		$("#params").val(params.substring(0,params.length-1));
		document.getElementById('createForm').action = '/dczc/filter!spfIndex.action';
		$("#createForm").submit();	

		//window.open(document.getElementById('createForm').action,"北京单场过滤");
		return false;
	}
});
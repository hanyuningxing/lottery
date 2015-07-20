$(function() {
	// 注window.xxx是全局变量，外面可以访问，其他是内部变量，外面访问不了
	var item_arr = [ 'homeWin', 'draw', 'guestWin' ];
	var item_text_arr = ['胜','平','负'];
	var selectedArr = [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ];// 注：在页面定义场次数目，这里在根据场次数目生成数组，可灵活适用于足彩其他几个玩法
	var selected_class = 'tdchosed';
	var unselected_class = 'tdyelnor';
	
	window.selectAt = function(matchIndex, item, event) {
		var createForm = getCreateForm();
		var el = createForm.elements['createForm.items[' + matchIndex + '].' + item];
		var liEl = document.getElementById('li_' + matchIndex + '_' + item);
		if (event != null) {
			Event_stopPropagation(event);
			var target = Event_target(event);
			if (target.id != el.id)
				el.checked = !el.checked;
		} else {
			el.checked = !el.checked;
		}
		if (!el.checked) {
			selectedArr[matchIndex] -= 1;
			liEl.className = unselected_class;
		} else {
			selectedArr[matchIndex] += 1;
			liEl.className = selected_class;
		}
		
		checkBao(matchIndex);
		countUnits();
	};
	
	function checkBao(matchIndex){
		var baoEl = $$('bao_' + matchIndex);
		if(selectedArr[matchIndex]==item_arr.length){
			baoEl.checked=true;
		}else{
			baoEl.checked=false;
		}
	}

	window.rowSelect = function(matchIndex,event) {
		var baoEl = $$('bao_' + matchIndex);
		if (event != null) {
			var target = Event_target(event);// event.target
			if (target.id != baoEl.id)
				baoEl.checked = !baoEl.checked;
		}
		var createForm = getCreateForm();
		var fn;
		if (!baoEl.checked) {
			fn = function(el, item) {
				if (el.checked == true) {
					selectedArr[matchIndex] -= 1;
					el.checked = false;
					document.getElementById('li_' + matchIndex + '_' + item).className = unselected_class;
				}
			};			
			//baoEl.checked = false;
		} else {
			fn = function(el, item) {
				if (el.checked == false) {
					selectedArr[matchIndex] += 1;
					el.checked = true;
					document.getElementById('li_' + matchIndex + '_' + item).className = selected_class;
				}
			};
			//baoEl.checked = true;
		}
		for ( var i = 0; i < item_arr.length; i++) {
			var item = item_arr[i];
			var el = createForm.elements['createForm.items[' + matchIndex + '].' + item];
			fn(el,item);
		}
		countUnits();
	};

	window.columnSelect = function(obj, item) {
		var createForm = getCreateForm();
		var checked = obj.checked;
		var className = checked ? selected_class : unselected_class;
		for ( var i = 0; i < selectedArr.length; i++) {
			var el = createForm.elements['createForm.items[' + i + '].' + item];
			if (el.checked != checked) {
				if (checked) {
					selectedArr[i] += 1;
				} else {
					selectedArr[i] -= 1;
				}
				el.checked = checked;
				document.getElementById('li_' + i + '_' + item).className = className;
			}
		}
		countUnits();
	};

	function countUnits() {
		var units = 1;
		for ( var i = 0, len = selectedArr.length; i < len; i++) {
			units *= selectedArr[i];
			if (units == 0)
				break;
		}
		updateBetUnits(units);
	}

	// *****************************************************************//
	// selectedObjs是14场比赛选择的内容
	// selectedCount是该场比赛选择的选项数
	// selectedCount是该场比赛是否设胆
	var selectedArrSf9 = [ {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	}, {
		selectedCount : 0,
		shedan : false
	} ];

	window.clickItemFn = function(matchIndex, item, event) {
		var createForm = getCreateForm();
		var el = createForm.elements['createForm.items[' + matchIndex + '].' + item];
		var liEl = document.getElementById('li_' + matchIndex + '_' + item);
		var selectedCountChg = false;// 选择的场次的数目是否变化了
		if (event != null) {
			Event_stopPropagation(event);
			var target = Event_target(event);
			if (target.id != el.id)
				el.checked = !el.checked;
		} else {
			el.checked = !el.checked;
		}
		if (!el.checked) {
			selectedArrSf9[matchIndex].selectedCount -= 1;
			liEl.className = unselected_class;
			if (selectedArrSf9[matchIndex].selectedCount == 0)
				selectedCountChg = true;
		} else {
			selectedArrSf9[matchIndex].selectedCount += 1;
			liEl.className = selected_class;
			if (selectedArrSf9[matchIndex].selectedCount == 1)
				selectedCountChg = true;
		}

		if (selectedCountChg == true)
			updateSheDan();

		countUnitsSf9();
	};
	
	window.columnClickFn = function(obj, item){
		var createForm = getCreateForm();
		var checked = obj.checked;
		var className = checked ? selected_class : unselected_class;
		var selectedCountChg = false;// 选择的场次的数目是否变化了
		for ( var i = 0; i < selectedArrSf9.length; i++) {
			var el = createForm.elements['createForm.items[' + i + '].' + item];
			if (el.checked != checked) {
				if (checked) {
					selectedArrSf9[i].selectedCount += 1;
					if (selectedArrSf9[i].selectedCount == 1)
						selectedCountChg = true;
				} else {
					selectedArrSf9[i].selectedCount -= 1;
					if (selectedArrSf9[i].selectedCount == 0)
						selectedCountChg = true;
				}
				el.checked = checked;
				document.getElementById('li_' + i + '_' + item).className = className;
			}
		}
		if (selectedCountChg == true)
			updateSheDan();
		countUnitsSf9();
	};
	

	/**
	 * 更新设胆
	 */
	function updateSheDan() {
		var countSelectedSize = 0;// 共选择了几场
		var danSize = 0;// 设胆的场次数
		for ( var i = 0; i < selectedArrSf9.length; i++) {
			var obj = selectedArrSf9[i];
			if (obj.selectedCount > 0) {
				countSelectedSize++;
				if (obj.shedan == true)
					danSize++;
			}
		}

		var createForm = getCreateForm();
		if (countSelectedSize <= 9) {
			danSize = 0;
			for ( var i = 0; i < selectedArrSf9.length; i++) {
				var obj = selectedArrSf9[i];
				obj.shedan = false;
				var el = createForm.elements['createForm.items[' + i + '].shedan'];
				if (el.disabled == false) {
					el.checked = false;
					el.disabled = true;
				}
			}
		} else {
			var disabled = danSize == 8;
			for ( var i = 0; i < selectedArrSf9.length; i++) {
				var obj = selectedArrSf9[i];
				var el = createForm.elements['createForm.items[' + i + '].shedan'];
				if (obj.selectedCount > 0) {
					if (obj.shedan == false && el.disabled != disabled)
						el.disabled = disabled;
				}else if(el.disabled != true){
					el.checked = false;
					el.disabled = true;
				}
			}
		}

		// 更新模糊设胆
		var html = '';
		if (danSize > 0) {
			var unDanSize = countSelectedSize - danSize;
			var danMinHit = 9 - unDanSize;
			if (danMinHit < 1)
				danMinHit = 1;
			if (danMinHit > danSize)
				danMinHit = danSize;
			html += '<select id="createForm_danMinHit" name="createForm.danMinHit" onchange="chgMohuSheDan(this);">';
			for ( var i = danSize; i >= danMinHit; i--) {
				html += '<option value=' + i + '>胆至少对' + i + '场</option>';
			}
			html += '</select>';
		}
		document.getElementById('mohushedan').innerHTML = html;
	}

	/**
	 * 切换模糊设胆
	 */
	window.chgMohuSheDan = function(obj) {
		countUnitsSf9();
	};

	window.clickDanFn = function(matchIndex, item) {
		var createForm = getCreateForm();
		var el = createForm.elements['createForm.items[' + matchIndex + '].' + item];
		if (el.checked == true) {
			selectedArrSf9[matchIndex].shedan = true;
		} else {
			selectedArrSf9[matchIndex].shedan = false;
		}
		updateSheDan();
		countUnitsSf9();
	};

	window.countUnitsSf9 = function() {
		var danBets = [];// 胆码场次集合
		var unDanBets = [];// 非胆码场次集合
		var countSelectedSize = 0;// 共选择了几场

		for ( var i = 0; i < selectedArrSf9.length; i++) {
			var obj = selectedArrSf9[i];
			if (obj.selectedCount > 0) {
				countSelectedSize++;
				if (obj.shedan)
					danBets.push(obj.selectedCount);
				else
					unDanBets.push(obj.selectedCount);
			}
		}
		var units = 0;// 注数
		if (countSelectedSize >= 9) {
			var danSize = danBets.length;// 设胆的场次数

			var danMinHit;// 模糊设置，胆码最小命中数
			var createForm = getCreateForm();
			var danMinEl = createForm.elements['createForm.danMinHit'];
			if (danMinEl != null)
				danMinHit = parseInt(danMinEl.value, 10);
			else
				danMinHit = danSize;

			var danMaxHit = 0;// 模糊设置，胆码最大命中数
			if (danMaxHit <= 0)
				danMaxHit = danSize;

			for ( var danHit = danMinHit; danHit <= danMaxHit; danHit++) {
				units += shedanCountFn(danBets, unDanBets, danHit, 9);// 胜负9场（9串1）
			}
		}
		updateBetUnits(units);// 调用更新注数的函数（已在公共发起的JS中定义）
	};

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
		var count = 0;
		C3(danBets.length, danHit, function(comb, n, m) {
			var c = 1;// 胆码的组合计算结果
			var pos = 0;
			for ( var i = 0; i < n; i++) {
				if (comb[i] == true) {
					c *= danBets[i];
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
		});
		return count;
	}

	/**
	 * 无设胆的注数计算
	 * 
	 * @param bets 可选数组
	 * @param needs 需要的场次数目（过几关）
	 * @return 注数
	 */
	function simpleCountFn(bets, needs) {
		var count = 0;
		C3(bets.length, needs, function(comb, n, m) {
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

		return count;
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
		var tdDan = '';
		if(isSFZC9)tdDan='<td>胆码</td>';
		var itemsHtml='<table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">';
			itemsHtml+='<tr class="v01tr"><td>场次 </td><td>主队 VS 客队 </td><td>您的选择</td>'+tdDan+'</tr>';
		for(var i=0;i<selectedArrSf9.length;i++){
			var selectedItemStr = '';
			for (var j = 0; j < item_arr.length; j++) {
				var item = item_arr[j];
				var el = createForm.elements['createForm.items[' + i + '].' + item];
				if (el.checked){
					selectedItemStr+=item_text_arr[j]+',';
				}
			}
			if(selectedItemStr=='')continue;
			var itemHtmlOfMatch = '<tr class="v02tr">';
			itemHtmlOfMatch +='<td>'+ $$('match_'+i).innerHTML +'</td>';
			itemHtmlOfMatch +='<td>'+ $$('home_'+i).innerHTML +' VS '+$$('guest_'+i).innerHTML+'</td>';
			itemHtmlOfMatch +='<td>';
			
			selectedItemStr=selectedItemStr.substr(0, selectedItemStr.length-1); 
			itemHtmlOfMatch +=selectedItemStr;
			itemHtmlOfMatch +='</td>';
			
			if(isSFZC9){
				var obj = selectedArrSf9[i];
				var dan = (obj!=null && obj.shedan)?'√':'×';
				itemHtmlOfMatch +='<td>'+ dan +'</td>';
			}			
			itemHtmlOfMatch += '</tr>';
			itemsHtml+=itemHtmlOfMatch;
		}
		itemsHtml+='</table>';
		$$('selectedContentDiv').innerHTML = itemsHtml;		
	};
});
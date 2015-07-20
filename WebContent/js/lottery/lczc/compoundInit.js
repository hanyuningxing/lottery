$(function() {
	// 注window.xxx是全局变量，外面可以访问，其他是内部变量，外面访问不了
	var item_arr = [ 'homeWin', 'draw', 'guestWin' ];
	var item_text_arr = ['胜','平','负'];
	var selectedArr = [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ];// 注：在页面定义场次数目，这里在根据场次数目生成数组，可灵活适用于足彩其他几个玩法
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
		var itemsHtml='<table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">';
			itemsHtml+='<tr class="v01tr"><td>场次 </td><td>主队 VS 客队 </td><td colspan="2">您的选择</td></tr>';
		for(var i=0;i<selectedArr.length;i++){
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
			var hfStr = '';
			if(i%2==0){
				var index = Math.floor(i/2);
				itemHtmlOfMatch +='<td rowspan="2">'+ $$('match_'+ index).innerHTML +'</td>';
				itemHtmlOfMatch +='<td rowspan="2">'+ $$('home_'+ index).innerHTML +' VS '+$$('guest_'+ index).innerHTML+'</td>';
				hfStr = '半场';
			}else{
				hfStr = '全场';
			}
			itemHtmlOfMatch +='<td>'+hfStr+'</td>';
			itemHtmlOfMatch +='<td width="200">';			
			selectedItemStr=selectedItemStr.substr(0, selectedItemStr.length-1); 
			itemHtmlOfMatch +=selectedItemStr;
			itemHtmlOfMatch +='</td>';
			itemHtmlOfMatch += '</tr>';
			itemsHtml+=itemHtmlOfMatch;
		}
		itemsHtml+='</table>';
		$$('selectedContentDiv').innerHTML = itemsHtml;		
	};
});
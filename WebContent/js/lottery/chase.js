$(function() {
	var chgShareType_parent = chgShareType;
	var updateBetUnits_parent = updateBetUnits;
	var updateBetCost_parent = updateBetCost;
	var limit_num=100;
	/**
	 * 更新注数
	 * @param units 注数
	 */
	window.updateBetUnits = function(units) {
		updateBetUnits_parent(units);
		if (isChase()) {
			updateChase();
		}
	};
	
	/**
	 * 更新方案金额
	 */
	window.updateBetCost = function() {
		updateBetCost_parent();
		var createForm = getCreateForm();
		var multiple = getMultiple(createForm);
		if (isChase()) {
			var tempObj = document.getElementById('multiple_chase_item_0');
			if(tempObj!=null) {
			if (tempObj.value != multiple) {
				tempObj.value = multiple;
				chgChaseMultiple(tempObj);
			}
			}
		}
	};
	
	window.chgChase = function(isChase){
		var createForm = getCreateForm();
		
	  	//停掉智能追号
    	stopCapacityChase()
    	document.getElementById('createForm_multiple').readOnly=false;
		document.getElementById('createForm_normalChase_periodSizeOfChase').disabled = false;

		if(isChase){
			document.getElementById('tab_chase').style.display = '';
			var periodSizeOfChase = document.getElementById('createForm_normalChase_periodSizeOfChase');
			periodSizeOfChase.disabled = false
			var chaseSize = periodSizeOfChase.value;
			if(null==limit_num||0==limit_num){
				return;
			}
			if(chaseSize>limit_num){
				window.alert("追号期数超过预售期的总期数"+limit_num+",请更改");
				return;
			}
			
			document.getElementById('createForm_normalChase_prizeForWonStopOfChase').disabled = false;
			document.getElementById('createForm_normalChase_wonStopOfChase').disabled = false;
			document.getElementById('span_normalChase_Cost').innerHTML = 0;
			var totalCostOfChase =document.getElementById('createForm_normalChase_totalCostOfChase');
			totalCostOfChase.value = 0;
			totalCostOfChase.disabled = false;
			chgChaseSize(chaseSize);
			
		}else{
			//停掉普通追号
			stopNormalChase();
		}
	};

	
	  //停止普通追号
    function stopNormalChase() {
    	var createForm = getCreateForm();
    	document.getElementById('tab_chase').style.display = 'none';
		document.getElementById('ul_chase_detail').innerHTML = '';
		document.getElementById('createForm_normalChase_periodSizeOfChase').disabled = true;;
		document.getElementById('createForm_normalChase_wonStopOfChase').disabled = true;;
		var prizeForWonStopOfChase = document.getElementById('createForm_normalChase_prizeForWonStopOfChase');
		prizeForWonStopOfChase.value = 0;
		prizeForWonStopOfChase.disabled = true;
		
		var totalCostOfChase = document.getElementById('createForm_normalChase_totalCostOfChase');
		totalCostOfChase.value = 0;
		totalCostOfChase.disabled = true;
    }
    
    //停止智能追号
    function stopCapacityChase() {
    	document.getElementById('isCapacity').value = false;
    	document.getElementById('createForm_multiple').readOnly=false;
    	document.getElementById('tab_capacityChase').style.display = 'none';
		document.getElementById('ul_chase_detail').innerHTML = '';
		document.getElementById('createForm_capacityChase_periodSizeOfChase').disabled = true;;
		document.getElementById('createForm_capacityChase_wonStopOfChase').disabled = true;;

		var prizeForWonStopOfChase = document.getElementById('createForm_capacityChase_PrizeForWonStopOfChase');
		prizeForWonStopOfChase.value = 0;
		prizeForWonStopOfChase.disabled = true;
		
		var totalCostOfChase = document.getElementById('createForm_capacityChase_totalCostOfChase');
		totalCostOfChase.value = 0;
		totalCostOfChase.disabled = true;
		document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr></table>";
    }
    
	
	
	
	
	window.chgChaseSize = function(num) {
		if(num>limit_num){
			window.alert("追号期数不能超过"+limit_num+",请更改");
			return;
		}
		if(num<2){
			num=2;
		}
		var createForm = getCreateForm();

		var multiple = getMultiple();// 倍数
		if (multiple <= 0)
			multiple = 1;

		var cost = createForm.elements['createForm.schemeCost'].value;

		var createForm = getCreateForm();
		var formActionUrl = createForm.action;
		var periodId = createForm.elements['createForm.periodId'].value;
		
		var chaseUrl=formActionUrl.replace(/![a-zA-Z]+./ig,'!canChasePeriod.');
		chaseUrl = chaseUrl+"?id="+periodId+"&cost="+cost+"&multiple="+multiple+"&count="+num;
		$.ajax({ 
				type: 'GET',
				cache : false,
				url: chaseUrl,
				success : function(data, textStatus) {
					
					var jsonObj = toJsonObject(data);
					try {
						if (jsonObj.chase_ul != null){
							document.getElementById('ul_chase_detail').innerHTML = jsonObj.chase_ul;
							chgChaseSizeBackCall();
						}else{
							chgChaseSizeErrorMethod(num,multiple,cost);
							chgChaseSizeBackCall();
						}
					} catch (e) {
						chgChaseSizeErrorMethod(num,multiple,cost);
						chgChaseSizeBackCall();
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					chgChaseSizeErrorMethod(num,multiple,cost);
					chgChaseSizeBackCall();
				}
		});
		
	};
	 function chgChaseSizeBackCall(){
	    	var createForm = getCreateForm();
	    	$("input[id^='checkbox_chase_item']").bind('click', function(e){
		    	if(this.id=='checkbox_chase_item_0'){
		    		return false;
		    	}
		    	var multipleId=this.id.replace("checkbox","multiple");
		    	var spanCostId=this.id.replace("checkbox","cost");
		    	
		    	var cost;
		    	var countNums = parseInt(document.getElementById('createForm_normalChase_periodSizeOfChase').value);
				var totalCost = parseInt(document.getElementById('createForm_normalChase_totalCostOfChase').value);
				
				if(this.checked){
					var multiple=getMultiple();
					document.getElementById(multipleId).value = multiple;
		    		document.getElementById(multipleId).readOnly=false;
					cost=createForm.elements['createForm.units'].value*getBetMoney()*multiple;
					document.getElementById(spanCostId).innerHTML=cost;
		    		countNums++;
		    		totalCost += cost;
		    	}else{
		    		document.getElementById(multipleId).value = 0;
		    		document.getElementById(multipleId).readOnly=true;
		    		cost=parseInt(document.getElementById(spanCostId).innerHTML);
		    		document.getElementById(spanCostId).innerHTML=0;
		    		countNums--;
		    		totalCost -= cost;
		    	}
		    	updateChaseMsg(countNums,totalCost);
		    	}
			);
		
			$("input[id^='multiple_chase_item']").bind('blur', function(e){
				if(this.value <= 0){
		    		this.value=1;
		    		updateChase();
		    	}
			});
			updateChase();
	}
	var chgChaseSizeErrorMethod = function(num,multiple,cost){
		var html = '';
		for ( var i = 0; i < num; i++) {
			html += '<li>';
			var itemId = 'chase_item_' + i;
			if (i < 9)
				html += '0';
			html += (i + 1);
			html += '&nbsp;<input id="checkbox_' + itemId + '" type="checkbox" checked="checked" />';
			html += '&nbsp;<input id="multiple_' + itemId + '" type="text" name="createForm.multiplesOfChase" value="' + multiple + '" size="2" style="IME-MODE: disabled;" onkeydown="number_check(this,event,0)" onkeyup="chgChaseMultiple(this)" oncontextmenu="return false;" autocomplete="off"/>倍';
			html += '&nbsp;<span id="cost_' + itemId + '" style="color:#F00">' + cost + '</span>元';
		}
		
		document.getElementById('ul_chase_detail').innerHTML = html;
	};
	window.chgShareType = function(obj) {
		chgShareType_parent(obj);
		
		var createForm = getCreateForm();
		window.chgChase(false);
		if (obj.value == 'TOGETHER') {
			document.getElementById('tr_chase').style.display = 'none';
			createForm.elements['createForm.chase'].disabled = true;
		} else {
			var el = createForm.elements['createForm.chase'];
			el.checked = false;
			el.disabled = false;
			document.getElementById('tr_chase').style.display = '';
		}
	};
	//切换追号倍数
	window.chgChaseMultiple = function(el){
    	var multiple = getMultiple();//倍数
    	var createForm = getCreateForm();
		if(multiple <= 0) multiple = 1;
		var betUnits = createForm.elements['createForm.units'].value;//方案注数
		if(!/^\d/.test(betUnits) || betUnits <= 0){
			betUnits = 0;
		}
		var betCostPerMul = betUnits * getBetMoney();//单倍金额
		
		var firstChase=(el.id=='multiple_chase_item_0');
    	if(firstChase && el.value != multiple&&0!=el.value){
   			// 第一个追号期的倍数改变时，同时改变当前方案的倍数
   			createForm.elements["createForm.multiple"].value = el.value;
   			updateBetCost();
    	}
    	var betCostObj = document.getElementById(el.id.replace('multiple','cost'));
    	var cost1 = parseInt(betCostObj.innerHTML);
    	var cost2 = el.value * betCostPerMul;
		var totalCost = parseInt(document.getElementById('createForm_normalChase_totalCostOfChase').value);
		totalCost += cost2 - cost1;
		
    	betCostObj.innerHTML = cost2;
    	updateNormalChaseTotalCost(totalCost);
    };
	
	/**
	 * 更新追号信息
	 */
    window.updateChase = function (){
    	var createForm = getCreateForm();
    	var num=document.getElementById('createForm_normalChase_periodSizeOfChase').value;
    	
    	
		var multiple = getMultiple();//倍数
		if(multiple <= 0) multiple = 1;
		
		var betUnits = createForm.elements['createForm.units'].value;//方案注数
		if(!/^\d/.test(betUnits) || betUnits <= 0){
			betUnits = 0;
		}
		
		var betCostPerMul = betUnits * getBetMoney();//单倍金额
		
		var isChecked;
		var chaseCheckboxObj;
		var chaseMultipleObj;
		var betCostObj;
		var cost;
		var countNums = 0;
		var totalCost = 0;
		var group;
		for(var i=0,len=num;i<len;i++){
			chaseCheckboxObj=document.getElementById('checkbox_chase_item_'+i);
			if(chaseCheckboxObj!=null) {
			isChecked = chaseCheckboxObj.checked;
			chaseMultipleObj = document.getElementById('multiple_chase_item_'+i);
			betCostObj = document.getElementById('cost_chase_item_'+i);
			cost = chaseMultipleObj.value * betCostPerMul;
			betCostObj.innerHTML = cost;
			if(isChecked){
				countNums++;
				totalCost += cost;
			}
			}
		}
		
		updateChaseMsg(countNums,totalCost);
	};
	
	var updateChaseMsg = function(countNums,totalCost){
		var createForm = getCreateForm();
		//createForm.elements['chaseIssueNums'].value = countNums;
		//createForm.getElement('span[_name=span_chaseIssueNums]').innerHTML = countNums;
		updateNormalChaseTotalCost(totalCost);
	};
	
	
	var updateNormalChaseTotalCost = function(totalCost){
		document.getElementById("createForm_normalChase_totalCostOfChase").value = totalCost;
		document.getElementById('span_normalChase_Cost').innerHTML = totalCost;
	};
	
	var updateCapacityChaseTotalCost = function(totalCost){
		document.getElementById("createForm_capacityChase_totalCostOfChase").value = totalCost;
		document.getElementById('span_capacityChase_Cost').innerHTML = totalCost;
	};
	
	
	/*
	 * 设置中后停追
	 */
	window.setwonStop=function(isWonStop){
		if(isWonStop){
			document.getElementById('createForm_normalChase_prizeForWonStopOfChase').disabled=false;
		}else{
			document.getElementById('createForm_normalChase_prizeForWonStopOfChase').disabled=true;
		}
	};
	
	window.setcapacityWonStop=function(isWonStop){
		if(isWonStop){
			document.getElementById('createForm_capacityChase_prizeForWonStopOfChase').disabled=false;
		}else{
			document.getElementById('createForm_capacityChase_prizeForWonStopOfChase').disabled=true;
		}
	};
	
	/**
	 * @return 是否有追号
	 */
	function isChase(){
		var createForm = getCreateForm();
		var chase = createForm.elements["createForm.chase"];
		if(chase == null) return false;
		return !chase.disabled&&chase.checked;
	}
	
	
	
	
	
	
	//智能追号
    window.chgCapacityChase = function(isChase){
		if($('.code_one_li').size()>1){
			alert("暂不支持复试或胆拖多组号码计算，请选择单组号码进行计算！");
			document.getElementById("cahseRadio").checked=true;
			document.getElementById("cahseCapacitychase").checked=false;
			chgChase(isChase)
			return false;
		}
    	if(null==document.getElementById("content").value||""==document.getElementById("content").value) {
    			alert("请选择投注号码");
    			document.getElementById("cahseRadio").checked=true;
    			document.getElementById("cahseCapacitychase").checked=false;
    			chgChase(isChase)
    			chgChase(isChase)
    		return false;
    	}
    	
    	
		//停掉普通追号
    	stopNormalChase()
    	
    	//不允许翻倍智能追号
		document.getElementById('createForm_multiple').value=1;
    	updateBetCost();
    	document.getElementById('createForm_multiple').readOnly=true;
    	document.getElementById('isCapacity').value = true;
    	

		chgCapacityChaseSizePer(isChase);
	};
	//智能追号
    function chgCapacityChaseSizePer(isChase){
		if(isChase){
			document.getElementById('tab_capacityChase').style.display = '';

			var periodSizeOfChase = document.getElementById('createForm_capacityChase_periodSizeOfChase');
			periodSizeOfChase.disabled = false
			var chaseSize = periodSizeOfChase.value;
			if(null==limit_num||0==limit_num){
				return;
			}
			if(chaseSize>limit_num){
				window.alert("追号期数超过预售期的总期数"+limit_num+",请更改");
				return;
			}
			
			document.getElementById('createForm_capacityChase_wonStopOfChase').disabled = false;
			document.getElementById('createForm_capacityChase_PrizeForWonStopOfChase').disabled = false;
			document.getElementById('span_capacityChase_Cost').innerHTML = 0;
			var totalCostOfChase =document.getElementById('createForm_capacityChase_totalCostOfChase');
			totalCostOfChase.value = 0;
			totalCostOfChase.disabled = false;
			comshow();
		}else{
			stopCapacityChase();
		}
    }
	
	
	
	
});
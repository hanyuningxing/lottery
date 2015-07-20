var __FlagArr = [ '_gh', '_dh', '_rh' ];

function flagIndexOf(flag) {
	for ( var i = 0; i < __FlagArr.length; i++) {
		if (__FlagArr[i] == flag)
			return i;
	}
	return null;
}

function getGamesValue() {
	var v = 0;
	$('#sg input[type="checkbox"]:checked').each(function(i, item) {
		var val = parseInt(item.value, 10);
		v |= (1 << val);
	});
	return v;
}

function defaultFilterFn(item) {
	for ( var i = 0; i < __FlagArr.length; i++) {
		if (item.getAttribute(__FlagArr[i]) != null)
			return false;
	}
	item.style.display = '';
	return true;
}

function itemShowOrHide(item, show) {
	if (show == true) {
		item.style.display = '';
	} else {
		item.style.display = 'none';
	}
}


function matchFilter(filterFn) {
	var showHandicap = document.getElementById('s_hdp1').checked;// 是否显示让球赛事
	var showUnHandicap = document.getElementById('s_hdp0').checked;// 是否显示不让球赛事
	var showEnd = false;
	var countHide = 0;// 统计隐藏赛事数目，让球、不让球和截止隐藏的赛事不计算在内

	if (filterFn == null)
		filterFn = defaultFilterFn;
	
	$(".dsscaa li").each(function (i,item) {
		if (item.getAttribute('_m') != null) {
			var h = item.getAttribute('_h');
			if (h != '0') {
				if (!showHandicap) {
					if (item.style.display != 'none')
						itemShowOrHide(item, false);
					return true; 
				}
			} else {
				if (!showUnHandicap) {
					if (item.style.display != 'none')
						itemShowOrHide(item, false);
					return true; 
				}
			}
			if (!showEnd) {
				var saleEnd = item.getAttribute('_e');
				if (saleEnd == '1') {
					if (item.style.display != 'none')
						itemShowOrHide(item, false);
					return true; 
				}
			}
			var show = filterFn(item);
			if (show == false)
				countHide++;
		}
	 });
	
	document.getElementById('c_h').innerHTML = countHide;
}

function matchFilterReset() {
	var filterFn = function(item) {
		for ( var i = 0; i < __FlagArr.length; i++) {
			item.removeAttribute(__FlagArr[i]);
		}
		if (item.style.display == 'none')
			itemShowOrHide(item, true);
		return true;
	};
	matchFilter(filterFn);
	gameFilterReset();
}

function gameFilterReset() {
	$('#sg input[type="checkbox"]').each(function(i, item) {
		if (item.checked == false)
			item.checked = true;
	});
}

function batchSelectGame(mode){
	var fn;
	if(mode == 1){
		fn = function(item){
			if (item.checked == false)
				item.checked = true;
		};
	}else{
		fn = function(item){
			if (item.checked == true)
				item.checked = false;
		};
	}
	$('#sg input[type="checkbox"]').each(function(i, item) {
		fn(item);
	});
	gameFilter();
}

function _filterHandleFn(item, flagIndex, show) {
	var currentFlag = __FlagArr[flagIndex];
	if (show) {
		item.removeAttribute(currentFlag);
		if (item.style.display == 'none') {
			for ( var i = 0; i < __FlagArr.length; i++) {
				if (i != flagIndex) {
					if (item.getAttribute(__FlagArr[i]) != null)
						return false;
				}
			}
			itemShowOrHide(item, true);
		}
		return true;
	} else {
		item.setAttribute(currentFlag, '1');
		if (item.style.display == '')
			itemShowOrHide(item, false);
		return false;
	}
}

function gameFilter() {
	var flagIndex = flagIndexOf('_gh');
	var gameValue = getGamesValue();
	var filterFn = function(row) {
		var gameIndex = parseInt(row.getAttribute('_g'), 10);
		var show = (gameValue & (1 << gameIndex)) > 0;
		return _filterHandleFn(row, flagIndex, show);
	};
	matchFilter(filterFn);
}

function dayFilter(obj) {
	var flagIndex = flagIndexOf('_dh');
	var index = obj.getAttribute('_di');
	var show = obj.innerHTML == '显示';
	var filterFn = function(item) {
		var dayIndex = item.getAttribute('_d');
		if (index != dayIndex)
			return item.style.display == '';
		return _filterHandleFn(item, flagIndex, show);
	};
	matchFilter(filterFn);
	obj.innerHTML = show ? '隐藏' : '显示';
}


function matchFilterOfSingle() {
	var showHandicap = document.getElementById('s_hdp1').checked;// 是否显示让球赛事
	var showUnHandicap = document.getElementById('s_hdp0').checked;// 是否显示不让球赛事
	var showEnd = false;
	var countHide = 0;// 统计隐藏赛事数目，让球、不让球和截止隐藏的赛事不计算在内
	
	 $("#matchsDiv li").each(function (i) {
		 alert(i);
	 });

	for ( var i = 1, len = rows.length; i < len; i++) {
		var row = rows[i];
		if (row.getAttribute('_m') != null) {
			var h = row.getAttribute('_h');
			if (h != '0') {
				if (!showHandicap) {
					if (row.style.display != 'none')
						rowShowOrHide(row, false);
					continue;
				}
			} else {
				if (!showUnHandicap) {
					if (row.style.display != 'none')
						rowShowOrHide(row, false);
					continue;
				}
			}
			if (!showEnd) {
				var saleEnd = row.getAttribute('_e');
				if (saleEnd == '1') {
					if (row.style.display != 'none')
						rowShowOrHide(row, false);
					continue;
				}
			}
			var show = filterFn(row);
			if (show == false)
				countHide++;
		}
	}
	document.getElementById('c_h').innerHTML = countHide;
}

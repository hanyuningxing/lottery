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

function defaultFilterFn(row) {
	for ( var i = 0; i < __FlagArr.length; i++) {
		if (row.getAttribute(__FlagArr[i]) != null)
			return false;
	}
	row.style.display = '';
	return true;
}

function rowShowOrHide(row, show) {
	var lineIdStr = row.getAttribute('_lid');
	var detailEl = document.getElementById('tr_' + lineIdStr + '_detail');
	var inputEl = document.getElementById('input_' + lineIdStr + '_detail');
	if (show == true) {
		row.style.display = '';
		if (detailEl != null && inputEl != null) {
			if (detailEl.style.display != '' && inputEl.value == '隐藏SP值')
				detailEl.style.display = '';
		}
	} else {
		row.style.display = 'none';
		if (detailEl != null && inputEl != null)
			detailEl.style.display = 'none';
	}
}

function showOrHideDetail(lineId) {
	var detailEl = document.getElementById('tr_' + lineId + '_detail');
	var imgEl = document.getElementById('img_' + lineId + '_detail');
	if (imgEl.src.indexOf('yc.gif') > 0) {
		detailEl.style.display = 'none';
		imgEl.src = imgEl.src.replace('yc.gif','zk.gif');
	} else {
		detailEl.style.display = '';
		imgEl.src = imgEl.src.replace('zk.gif','yc.gif');
	}
}

function matchFilter(filterFn) {
	var matchTbody = document.getElementById('matchTbody');
	var rows = matchTbody.rows;
	var showHandicap = document.getElementById('s_hdp1').checked;// 是否显示让球赛事
	var showUnHandicap = document.getElementById('s_hdp0').checked;// 是否显示不让球赛事
	var showEnd = false;
	var countHide = 0;// 统计隐藏赛事数目，让球、不让球和截止隐藏的赛事不计算在内

	if (filterFn == null)
		filterFn = defaultFilterFn;

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


function matchFilterReset() {
	var filterFn = function(row) {
		for ( var i = 0; i < __FlagArr.length; i++) {
			row.removeAttribute(__FlagArr[i]);
		}
		if (row.style.display == 'none')
			rowShowOrHide(row, true);
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

function _filterHandleFn(row, flagIndex, show) {
	var currentFlag = __FlagArr[flagIndex];
	if (show) {
		row.removeAttribute(currentFlag);
		if (row.style.display == 'none') {
			for ( var i = 0; i < __FlagArr.length; i++) {
				if (i != flagIndex) {
					if (row.getAttribute(__FlagArr[i]) != null)
						return false;
				}
			}
			rowShowOrHide(row, true);
		}
		return true;
	} else {
		row.setAttribute(currentFlag, '1');
		if (row.style.display == '')
			rowShowOrHide(row, false);
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

function dayFilter(obj) {
	var flagIndex = flagIndexOf('_dh');
	var index = obj.getAttribute('_di');
	var show = obj.innerHTML == '显示';
	var filterFn = function(row) {
		var dayIndex = row.getAttribute('_d');
		if (index != dayIndex)
			return row.style.display == '';
		return _filterHandleFn(row, flagIndex, show);
	};
	matchFilter(filterFn);
	obj.innerHTML = show ? '隐藏' : '显示';
}

function hideRow(obj) {
	var row = document.getElementById('tr_' + obj.id.substr(9));
	row.setAttribute('_rh', '1');
	rowShowOrHide(row, false);
	var countHide = parseInt(document.getElementById('c_h').innerHTML, 10);
	document.getElementById('c_h').innerHTML = countHide + 1;
}
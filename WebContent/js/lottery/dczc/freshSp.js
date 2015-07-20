function freshSp(periodNumber, playType, repeatInterval) {
	if (repeatInterval == null)
		repeatInterval = 10000;

	var chgUrl = window.BASESITE + '/jsp/dczc/sp.jsp?periodNumber=' + periodNumber + '&playType=' + playType
			+ '&type=chg';
	var allUrl = window.BASESITE + '/jsp/dczc/sp.jsp?periodNumber=' + periodNumber + '&playType=' + playType;

	var sp_version_el = document.getElementById('sp_version');
	var sp_version = parseInt(sp_version_el.innerHTML, 10);

	var up_class = 'rebchar';
	var down_class = 'dc_g_c';
	
	var sp_cur_obj = {};// 正在更新
	var sp_wait_obj = {};// 等待更新
	var displayInterval_1 = 5000;
	var displayInterval_2 = 20;
	
	var clearChgColorFn = function() {
		$('#matchTbody span[_g="sp"][class]').attr('class', '');
	};

	var formatSpFn = function(value) {
		var zero_str = ".00";
		var sp_str = value.toString();
		var p = sp_str.indexOf('.');
		var m;
		if (p == -1) {
			m = 0;
		} else {
			m = sp_str.length - p;
		}
		if (m < zero_str.length) {
			return sp_str + zero_str.substring(m);
		} else {
			return sp_str;
		}
	};

	var buildDataFn = function(data, version) {
		return {
			data : data,
			version : version
		};
	};

	var updateFn = function(updateData) {
		sp_version = updateData.version;
		sp_version_el.version = updateData.version;
		var data = updateData.data;
		for ( var lineKey in data) {
			var lineData = data[lineKey];
			for ( var itemKey in lineData) {
				var itemObj = lineData[itemKey];
				var spId = 'sp_' + lineKey + '_' + itemKey;
				sp_wait_obj[spId] = {
					value : formatSpFn(itemObj.value),
					chg : itemObj.chg
				};
			}
		}
	};
	

	var displaySpChg = function() {
		var key;
		for ( var p in sp_cur_obj) {
			key = p;
			break;
		}
		if (key == null) {
			sp_cur_obj = sp_wait_obj;
			sp_wait_obj = {};
			window.setTimeout(clearChgColorFn, displayInterval_1);
			window.setTimeout(displaySpChg, displayInterval_1);
		}else{
			var data = sp_cur_obj[key];
			delete sp_cur_obj[key];
			var spEl = document.getElementById(key);
			if (spEl != null) {
				spEl.innerHTML = data.value;
				if (data.chg > 0) {
					spEl.className = up_class;
				} else if (data.chg < 0) {
					spEl.className = down_class;
				}
			}
			window.setTimeout(displaySpChg, displayInterval_2);
		}
	};
	

	var reqAllFn = function() {
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : allUrl,
			success : function(jsonObj) {
				if (jsonObj != null) {
					var updateData = buildDataFn(jsonObj.data, jsonObj.version);
					updateFn(updateData);
					window.setTimeout(reqChgFn, repeatInterval);
				} else {
					window.setTimeout(reqAllFn, repeatInterval);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.setTimeout(reqAllFn, repeatInterval);
			}
		});
	};

	var reqChgFn = function() {
		$.ajax({
			type : 'GET',
			cache : false,
			dataType : 'json',
			url : chgUrl,
			success : function(jsonObj) {
				if (jsonObj != null) {
					var next_version = sp_version + 1;
					if (next_version >= jsonObj.minVersion && sp_version <= jsonObj.maxVersion) {
						var chgList = jsonObj.chgList;
						for ( var i = (next_version - jsonObj.minVersion), ver = next_version; i < chgList.length; i++, ver++) {
							var chgObj = chgList[i];
							var updateData = buildDataFn(chgObj.chgData, ver);
							updateFn(updateData);
						}
						window.setTimeout(reqChgFn, repeatInterval);
					} else {
						reqAllFn();
					}
				} else {
					window.setTimeout(reqChgFn, repeatInterval);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.setTimeout(reqChgFn, repeatInterval);
			}
		});
	};

	window.setTimeout(reqChgFn, repeatInterval);
	window.setTimeout(displaySpChg, repeatInterval+1000);
}

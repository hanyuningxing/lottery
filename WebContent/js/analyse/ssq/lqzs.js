BLUES = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16' ];

AREA_MISS = [];
(function() {
	var arr = [ 4, 8, 12, 16 ];
	var prev = 0;
	for ( var i = 0; i < arr.length; i++) {
		var cur = arr[i];
		AREA_MISS.push( {
			start : prev,
			end : cur,
			value : -1,
			miss : 0
		});
		prev = cur + 1;
	}
})();

TAB_HEADER = '<table width="980" border="0" cellpadding="1" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td width="25" rowspan="2">蓝<br />球 </td><td width="25" rowspan="2">AC<br />值</td><td colspan="16" class="b1redpx" >蓝球走势</td><td colspan="2" class="b1redpx">奇偶走势</td><td colspan="2" class="b1redpx" >大小走势</td><td colspan="4" class="b1redpx" >四区分布</td><td rowspan="2" class="b1redpx">红球</td></tr><tr class=" zsttrpinklig "><td width="30" class="b1redpx">01</td><td width="30">02</td><td width="30">03</td><td width="30">04</td><td width="30">05</td><td width="30">06</td><td width="30">07</td><td width="30">08</td><td width="30">09</td><td width="30">10</td><td width="30">11</td><td width="30" >12</td><td width="30">13</td><td width="30" >14</td><td width="30" >15</td><td width="30">16</td><td width="25" class="b1redpx">奇</td><td width="25">偶</td><td width="25" class="b1redpx">大</td><td width="25">小</td><td width="25" class="b1redpx">1<br />|<br />4</td><td width="25">5<br />|<br />8</td><td width="25">9<br />|<br />12</td><td width="25">13<br />|<br />16</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">@SELECT@</td><td rowspan="2"   class="trtitlebg">蓝球</td><td rowspan="2"  class="trtitlebg">AC值</td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td >12</td><td>13</td><td >14</td><td >15</td><td>16</td><td  class="b1redpx" >奇</td><td>偶</td><td class="b1redpx" >大 </td><td>小</td><td class="b1redpx"  >1<br />|<br />4</td><td>5<br />|<br />8</td><td   >9<br />|<br />12</td><td   >13<br />|<br />16</td><td rowspan="2"  class="zsttrpink b1redpx">红球</td></tr><tr class="trtitlebg"><td colspan="16" >蓝球走势</td><td colspan="2"   class="b1redpx">奇偶走势</td><td colspan="2"   >大小走势</td><td colspan="4"   >四区分布</td></tr></table>';

function ASC(numStr1, numStr2) {
	var numInt1 = numStr1.toInt();
	var numInt2 = numStr2.toInt();
	if (numInt1 > numInt2)
		return 1;
	else if (numInt1 < numInt2)
		return -1;
	else
		return 0;
}

function getDrawedData() {
	return dataShuangseqiu;
}

/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 * @param issue - (<em>string</em>) 期号
 * @param prevResult - (<em>object</em>) 上一期开奖结果对象
 * @returns {object} - 开奖结果对象
 */
function genResult(numStr, issue, prevResult) {
	var numArr = numStr.split(/[^\d]+/);
	var redNumsUnSort = [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5] ];
	var blueNum = numArr.pop();
	numArr.sort(ASC);
	var result = {
		redNumsUnSort : redNumsUnSort,
		redNums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5] ],
		blueNum : blueNum,
		odd : blueNum % 2 != 0,
		big : blueNum > 8,
		ac : -5
	};
	var redNumIntArr = [];
	result.redNums.each(function(redNum) {
		var redNumInt = redNum.toInt();
		redNumIntArr.push(redNumInt);
	});
	var acObj = {};
	while (redNumIntArr.length > 1) {
		var maxInt = redNumIntArr.pop();
		for ( var i = 0, len = redNumIntArr.length; i < len; i++) {
			var key = (maxInt - redNumIntArr[i]).toString();
			acObj[key] = key;
		}
	}
	for ( var key in acObj) {
		result.ac += 1;
	}
	return result;
}

/**
 * 计算一期遗漏数据
 * 
 * @param issue - (<em>string</em>) 期号
 * @param result - (<em>object</em>) 开奖结果
 * @param prevMissItem - (<em>object</em>) 上一期的遗漏数据
 * @returns {object} - 该期的遗漏数据
 */
function buildMissItem(issue, result, prevMissItem) {
	var missItem = {
		result : result
	};
	if (prevMissItem != null) {
		missItem.blueMissHash = prevMissItem.blueMissHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.blueMissHash = new Hash();
		BLUES.each(function(num) {
			missItem.blueMissHash.set(num, 1);
		});
	}
	missItem.blueMissHash.set(result.blueNum, 0);

	missItem.html = buildMissItemHTML(issue, missItem);

	return missItem;
}

/**
 * 构建一期遗漏数据的HTML代码
 * 
 * @param issue - (<em>string</em>) 期号
 * @param missItem - (<em>object</em>) 遗漏数据
 * @returns {string} - 遗漏数据的HTML代码
 */
function buildMissItemHTML(issue, missItem) {
	var html = '<td class="graychar333" >' + issue + '</td>';
	html += '<td class="bluecharnor">' + missItem.result.blueNum + '</td>';
	html += '<td class="graychar333">' + missItem.result.ac + '</td>';

	var i = 0;
	BLUES.each(function(ball) {
		var miss = missItem.blueMissHash.get(ball);
		i++;
		var classHtml = (i == 1) ? 'class="b1redpx"' : '';
		var ballHtml = (miss == 0) ? '<span class="bball" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});

	html += '<td class="bluecharnor b1redpx">'
			+ (missItem.result.odd ? '<span class="bluedot" _cancas="cancas" _group="odd"></span>' : '&nbsp;')
			+ '</td>';
	html += '<td class="bluecharnor">'
			+ (!missItem.result.odd ? '<span class="bluedot" _cancas="cancas" _group="odd"></span>' : '&nbsp;')
			+ '</td>';
	html += '<td class="bluecharnor b1redpx">'
			+ (missItem.result.big ? '<span class="greendot" _cancas="cancas" _group="big"></span>' : '&nbsp;')
			+ '</td>';
	html += '<td class="bluecharnor">'
			+ (!missItem.result.big ? '<span class="greendot" _cancas="cancas" _group="big"></span>' : '&nbsp;')
			+ '</td>';

	AREA_MISS.each(function(missObj) {
		if (missItem.result.blueNum >= missObj.start && missItem.result.blueNum <= missObj.end) {
			html += '<td><span _cancas="cancas" _group="area">' + missItem.result.blueNum + '</span></td>';
		} else {
			html += '<td>&nbsp;</td>';
		}
	});
	html += '<td class="redredchar b1redpx">' + missItem.result.redNumsUnSort.join(',') + '</td>';
	return html;
}

/**
 * 生成遗漏数据的HTML代码
 * 
 * @param key - (<em>string</em>) 期号
 * @param checkFn - (<em>function</em>) 检查是否需要统计该期的函数
 *            <ul>
 *            <h3>语法：<code>fn(issue)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            </ol>
 *            </ul>
 * @param desc - (<em>boolean</em>) 是否倒序输出
 * @returns {string} - 遗漏数据HTML代码
 */
function genMissHTML(key, checkFn, desc) {
	var missHTML = '';
	var index = 0;
	var itemIandleFn = function(issue, missItem) {
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + TAB_FOOTER.replace(/@SELECT@/, selectHTML);
	return html;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var blueMissData = Shuangseqiublue;
	for ( var issue in blueMissData) {
		var missObj = {
			blueMissObj : blueMissData[issue]
		};
		HISTORY_MISS_HASH.set(issue, buildHistoryMissItem(issue, missObj));
	}
}

/**
 * 构建一期的历史遗漏数据
 * 
 * @param issue - (<em>string</em>) 期号
 * @param missObj - (<em>object</em>) 历史遗漏数据对象
 * @returns {object} - 该期的历史遗漏数据
 */
function buildHistoryMissItem(issue, missObj) {
	var missItem = {
		result : DATA_HASH.get(issue),
		blueMissHash : new Hash()
	};
	for ( var ball in missObj.blueMissObj) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.blueMissHash.set(ballNum, missObj.blueMissObj[ball]);
	}
	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	[ 'blue', 'odd', 'big', 'area' ].each(function(group) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="' + group + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
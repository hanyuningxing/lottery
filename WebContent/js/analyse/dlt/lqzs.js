BLUES = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12' ];
KUADU=['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'];
DAXIAO=['小小','小大','大大'];
QIOU=['奇奇','奇偶','偶奇','偶偶'];
ZHIHE=['质质','质合','合质','合合'];

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

TAB_HEADER = '<table width="980" border="0" cellpadding="1" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td width="25" rowspan="2">号码</td><td colspan="12" class="b1redpx" >分布图</td><td colspan="11" class="b1redpx">间距</td><td colspan="3" class="b1redpx" >大小组合</td><td colspan="4" class="b1redpx" >奇偶组合</td><td colspan="4" class="b1redpx">质合组合</td></tr><tr class=" zsttrpinklig "><td width="30">01</td><td width="30">02</td><td width="30">03</td><td width="30">04</td><td width="30">05</td><td width="30">06</td><td width="30">07</td><td width="30">08</td><td width="30">09</td><td width="30">10</td><td width="30">11</td><td width="30" >12</td><td width="25">1</td><td width="25">2</td><td width="25">3</td><td width="25">4</td><td width="25">5</td><td width="25">6</td><td width="25">7</td><td width="25">8</td><td width="25">9</td><td width="30">10</td><td width="30">11</td><td width="35">小小</td><td width="35">小大</td><td width="35">大大</td><td width="35">奇奇</td><td width="35">奇偶</td><td width="35">偶奇</td><td width="35">偶偶</td><td width="35">质合</td><td width="35">质质</td><td width="35">合质</td><td width="35">合合</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td width="60" class="trtitlebg" rowspan="2">@SELECT@</td><td class="trtitlebg" width="25" rowspan="2">号码</td><td width="30">01</td><td width="30">02</td><td width="30">03</td><td width="30">04</td><td width="30">05</td><td width="30">06</td><td width="30">07</td><td width="30">08</td><td width="30">09</td><td width="30">10</td><td width="30">11</td><td width="30" >12</td><td width="30">1</td><td width="30">2</td><td width="30">3</td><td width="30">4</td><td width="30">5</td><td width="25">6</td><td width="25">7</td><td width="25">8</td><td width="25">9</td><td width="25">10</td><td width="25">11</td><td width="35">小小</td><td width="35">小大</td><td width="35">大大</td><td width="35">奇奇</td><td width="35">奇偶</td><td width="35">偶奇</td><td width="35">偶偶</td><td width="35">质合</td><td width="35">质质</td><td width="35">合质</td><td width="35">合合</td></tr><tr class="trtitlebg"><td colspan="12" class="b1redpx" >分布图</td><td colspan="11" class="b1redpx">间距</td><td colspan="3" class="b1redpx" >大小组合</td><td colspan="4" class="b1redpx" >奇偶组合</td><td colspan="4" class="b1redpx">质合组合</td></tr></table>';

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
	return Dltdata;
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
	var blueNum1 = numArr.pop();
	var blueNum2 = numArr.pop();
	var blueNumArr=[blueNum1,blueNum2];
	numArr.sort(ASC);
	blueNumArr.sort(ASC);
	var result = {
		redNumsUnSort : redNumsUnSort,
		redNums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4] ],
		blueNums : [blueNumArr[0],blueNumArr[1]],
		odd : (blueNumArr[0] % 2 == 0?'偶':'奇')+(blueNumArr[1] % 2 == 0?'偶':'奇'),
		big : (blueNumArr[0] > 6?'大':'小')+(blueNumArr[1] > 6?'大':'小'),
		zh : (isPrimeNum(blueNumArr[0])?'质':'合')+(isPrimeNum(blueNumArr[1])?'质':'合'),
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
		missItem.kdMissHash = prevMissItem.kdMissHash.map(function(value) {
			return value + 1;
		});
		missItem.dxMissHash = prevMissItem.dxMissHash.map(function(value) {
			return value + 1;
		});
		missItem.qoMissHash = prevMissItem.qoMissHash.map(function(value) {
			return value + 1;
		});
		missItem.zhMissHash = prevMissItem.zhMissHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.blueMissHash = new Hash();
		missItem.kdMissHash = new Hash();
		missItem.dxMissHash = new Hash();
		missItem.qoMissHash = new Hash();
		missItem.zhMissHash = new Hash();
		BLUES.each(function(num) {
			missItem.blueMissHash.set(num, 1);
		});
		KUADU.each(function(num) {
			missItem.kdMissHash.set(num, 1);
		});
		DAXIAO.each(function(num) {
			missItem.dxMissHash.set(num, 1);
		});
		QIOU.each(function(num) {
			missItem.qoMissHash.set(num, 1);
		});
		ZHIHE.each(function(num) {
			missItem.zhMissHash.set(num, 1);
		});
	}
	missItem.blueMissHash.set(result.blueNums[0], 0);
	missItem.blueMissHash.set(result.blueNums[1], 0);
	missItem.kdMissHash.set(result.blueNums[1]-result.blueNums[0], 0);
	missItem.dxMissHash.set(result.big, 0);
	missItem.qoMissHash.set(result.odd, 0);
	missItem.zhMissHash.set(result.zh, 0);

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
	html += '<td class="bluecharnor">' + missItem.result.blueNums[0]+'&nbsp;'+missItem.result.blueNums[1] + '</td>';
//	html += '<td class="graychar333">' + missItem.result.ac + '</td>';

	var i = 0;
	BLUES.each(function(ball) {
		var miss = missItem.blueMissHash.get(ball);
		i++;
		var classHtml = (i == 1) ? '' : '';
		var ballHtml = (miss == 0) ? '<span class="bball" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
	KUADU.each(function(ball) {
		var miss = missItem.kdMissHash.get(ball);
		i++;
		var classHtml = (miss == 0) ? ' class="cabg1"' : '';
		var ballHtml = (miss == 0) ? '<span class="cabg1" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
	DAXIAO.each(function(ball) {
		var miss = missItem.dxMissHash.get(ball);
		i++;
		var classHtml = (miss == 0) ? ' class="cabg2"' : '';
		var ballHtml = (miss == 0) ? '<span class=" cabg2" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
	QIOU.each(function(ball) {
		var miss = missItem.qoMissHash.get(ball);
		i++;
		var classHtml = (miss == 0) ? ' class="cabg1"' : '';
		var ballHtml = (miss == 0) ? '<span _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
	ZHIHE.each(function(ball) {
		var miss = missItem.zhMissHash.get(ball);
		i++;
		var classHtml = (miss == 0) ? ' class="cabg2"' : '';
		var ballHtml = (miss == 0) ? '<span class="cabg2" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
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
	var blueMissData = Dltblue;
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
		blueMissHash : new Hash(),
		kdMissHash : new Hash(),
		dxMissHash : new Hash(),
		qoMissHash : new Hash(),
		zhMissHash : new Hash()
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

//function drawLine() {
//	$("div_line").innerHTML = "";
//	[ 'blue', 'odd', 'big', 'area' ].each(function(group) {
//		var tagArr1 = $$('span[_cancas="cancas"][_group="' + group + '"]');
//		var myfun = function() {
//			drawCancas(tagArr1, 1);
//		};
//		myfun.delay(10);
//	});
//}
NUMS = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ];
HashNameArr = [ 'wanHash', 'qianHash' , 'hundredHash', 'tenHash', 'unitHash' ];

TAB_HEADER = '<table width="100%" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td colspan="10" class="b1redpx">万位</td><td colspan="10" class="b1redpx">千位</td><td colspan="10" class="b1redpx">百位</td><td colspan="10" class="b1redpx">十位</td><td colspan="10" class="b1redpx">个位</td></tr><tr class="zsttrpinklig"><td width="10" class="b1redpx">0</td><td width="10">1</td><td width="10">2</td><td width="10">3</td><td width="10">4</td><td width="10">5</td><td width="10">6</td><td width="10">7</td><td width="10">8</td><td width="10">9</td><td width="10" class="b1redpx">0</td><td width="10">1</td><td width="10">2</td><td width="10">3</td><td width="10">4</td><td width="10">5</td><td width="10">6</td><td width="10">7</td><td width="10">8</td><td width="10">9</td><td width="10" class="b1redpx">0</td><td width="10">1</td><td width="10">2</td><td width="10">3</td><td width="10">4</td><td width="10">5</td><td width="10">6</td><td width="10">7</td><td width="10">8</td><td width="10">9</td><td width="10" class="b1redpx">0</td><td width="10">1</td><td width="10">2</td><td width="10">3</td><td width="10">4</td><td width="10">5</td><td width="10">6</td><td width="10">7</td><td width="10">8</td><td width="10">9</td><td width="10" class="b1redpx">0</td><td width="10">1</td><td width="10">2</td><td width="10">3</td><td width="10">4</td><td width="10">5</td><td width="10">6</td><td width="10">7</td><td width="10">8</td><td width="10">9</td></tr>';
TAB_FOOTER = '<tr class="zsttrpinklig"><td rowspan="2" class="trtitlebg">@SELECT@</td><td class="b1redpx">0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td class="b1redpx">0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td class="b1redpx">0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td class="b1redpx">0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td><td class="b1redpx">0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td><td>8</td><td>9</td></tr><tr class="zsttrpink"><td colspan="10" class="b1redpx trtitlebg">万位</td><td colspan="10" class="b1redpx trtitlebg">千位</td><td colspan="10" class="b1redpx trtitlebg">百位</td><td colspan="10" class="b1redpx trtitlebg">十位</td><td colspan="10" class="b1redpx trtitlebg">个位</td></tr></table>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td><td class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false;" onclick="ball_click(this);return false;">0</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">1</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">2</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">3</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">4</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">5</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">6</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">7</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">8</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">9</a></td><td class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false;">0</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">1</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">2</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">3</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">4</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">5</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">6</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">7</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">8</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">9</a></td><td class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false;">0</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">1</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">2</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">3</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">4</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">5</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">6</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">7</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">8</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">9</a></td><td class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false;">0</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">1</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">2</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">3</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">4</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">5</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">6</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">7</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">8</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">9</a></td><td class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false;">0</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">1</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">2</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">3</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">4</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">5</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">6</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">7</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">8</a></td><td><a href="#" class="gball" onclick="ball_click(this);return false;">9</a></td>';

function getDrawedData() {
	return pl5_ball_data;
}

/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 * @returns {object} - 开奖结果对象
 */
function genResult(numStr) {
	var numArr = numStr.split(/[^\d]+/);
	var result = {
		nums : [ numArr[0], numArr[1], numArr[2] ,numArr[3], numArr[4] ],
		sum : 0
	};
	result.nums.each(function(num, index) {
		result.sum += num.toInt();
	});
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
		HashNameArr.each(function(hashName) {
			missItem[hashName] = prevMissItem[hashName].map(function(value) {
				return value + 1;
			});
		});
	} else {
		HashNameArr.each(function(hashName) {
			missItem[hashName] = new Hash();
			NUMS.each(function(num) {
				missItem[hashName].set(num, 1);
			});
		});
	}
	result.nums.each(function(num, index) {
		missItem[HashNameArr[index ]].set(num, 0);
	});

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

	var classArr = [ 'ballredone', 'ballblue','ballredone', 'ballblue', 'ballredone' ];
	var missItem1=missItem;
	HashNameArr.each(function(hashName, index) {
		var className = classArr[index];
		var k = 0;
		NUMS.each(function(num) {
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0)
				classStr += 'b1redpx ';
			var ballStr;
			if (miss == 0) {
				if (index == 0) {
					classStr += className;
				}
				ballStr = '<span class="' + className + '" _cancas="cancas" _group="' + hashName + '">' + num
						+ '</span>';
			} else {
				ballStr = miss;
			}
			html += '<td class="' + classStr + '">' + ballStr + '</td>';
			k++;
		});
	});
	return html;
}

/**
 * 构建合计数据的HTML代码
 * 
 * @param statText - (<em>string</em>) 合计名称
 * @param missItem - (<em>object</em>) 合计数据
 * @returns {string} - 合计数据的HTML代码
 */
function buildStatItemHTML(statText, missItem) {
	var html = '<td class="graychar333" >' + statText + '</td>';

	var classArr = [  'ballredone', 'ballblue','ballredone', 'ballblue', 'ballredone' ];
	HashNameArr.each(function(hashName, index) {
		var k = 0;
		NUMS.each(function(miss, num) {
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0)
				classStr += 'b1redpx ';
			var ballStr;
			if (miss == 0) {
				ballStr = num;
			} else {
				ballStr = miss;
			}
			html += '<td class="' + classStr + '">' + ballStr + '</td>';
			k++;
		});
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
	var maxMissItem = {
		wanHash : new Hash(),
		qianHash : new Hash(),
		hundredHash : new Hash(),
		tenHash : new Hash(),
		unitHash : new Hash()
	};
	var outMissItem = {
		wanHash : new Hash(),
		qianHash : new Hash(),
		hundredHash : new Hash(),
		tenHash : new Hash(),
		unitHash : new Hash()
	};
	var lastMissItem = null;
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';

		HashNameArr.each(function(hashName) {
			missItem[hashName].each(function(miss, ball) {
				var maxMiss = maxMissItem[hashName].get(ball);
				if (maxMiss == null || miss > maxMiss)
					maxMissItem[hashName].set(ball, miss);

				var outCount = outMissItem[hashName].get(ball);
				if (outCount == null)
					outCount = 0;
				if (miss == 0)
					outCount++;
				outMissItem[hashName].set(ball, outCount);
			});
		});
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
			wanHash : new Hash(),
			qianHash : new Hash(),
			hundredHash : new Hash(),
			tenHash : new Hash(),
			unitHash : new Hash()
	};
	HashNameArr.each(function(hashName) {
		outMissItem[hashName].each(function(out, ball) {
			var avgMiss;
			if (out > 0) {
				avgMiss = Math.floor(issueSize / out);
			} else if (lastMissItem != null) {
				avgMiss = lastMissItem[hashName].get(ball);
			} else {
				avgMiss = 0;
			}
			avgMissItem[hashName].set(ball, avgMiss);
		});
	});

	var maxMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('最大遗漏', maxMissItem) + '</tr>';
	var avgMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('平均遗漏', avgMissItem) + '</tr>';
	var outMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('出现总数', outMissItem) + '</tr>';

	var moniHTML = '';
	for ( var i = 0; i < 3; i++) {
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + MONI_SELECT + '</tr>';
	}

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML + maxMissItemHTML + avgMissItemHTML
			+ outMissItemHTML + TAB_FOOTER.replace(/@SELECT@/, selectHTML);
	return html;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var data = pl5_miss_zx;
	var number_data = pl5_miss_ch;
	for ( var issue in data) {
		var missObj = {
			wanMissObj : data[issue].w,
			qianMissObj : data[issue].q,
			hundredMissObj : data[issue].b,
			tenMissObj : data[issue].s,
			unitMissObj : data[issue].g
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
		wanHash : new Hash(missObj.wanMissObj),
		qianHash : new Hash(missObj.qianMissObj),
		hundredHash : new Hash(missObj.hundredMissObj),
		tenHash : new Hash(missObj.tenMissObj),
		unitHash : new Hash(missObj.unitMissObj)
	};

	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	[ 'wanHash', 'qianHash','hundredHash', 'tenHash', 'unitHash' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
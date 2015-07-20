SUM_ARR = [];
for ( var sum = 0; sum < 28; sum++) {
	SUM_ARR.push(sum.toString());
}

TAB_HEADER = '<table width="936" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60">@SELECT@</td><td  > 试机</td><td >出号</td><td >和值</td><td width="20"    >0</td><td width="20"  >1</td><td width="20"  >2</td><td width="20"  >3</td><td width="20"  >4</td><td width="20"  >5</td><td width="20"  >6</td><td width="20"  >7</td><td width="20"  >8</td><td width="20"  >9</td><td width="20" >10</td><td width="20"  >11</td><td width="20"  >12</td><td width="20"  >13</td><td width="20"  >14</td><td width="20"  >15</td><td width="20"  >16</td><td width="20"  >17</td><td width="20"  >18</td><td width="20" >19</td><td width="20"  >20</td><td width="20"  >21</td><td width="20"  >22</td><td width="20"  >23</td><td width="20"  >24</td><td width="20"  >25</td><td width="20"  >26</td><td width="20"  >27</td><td width="40" >奇数<br />和值</td><td width="40"  >偶数<br />和值</td><td width="40"  >大数<br />和值</td><td width="40"  >小数<br />和值</td></tr>';
TAB_FOOTER = '<tr class="trtitlebg"><td>@SELECT@</td><td  > 试机</td><td >出号</td><td >和值</td><td    >0</td><td  >1</td><td  >2</td><td  >3</td><td  >4</td><td  >5</td><td  >6</td><td  >7</td><td  >8</td><td  >9</td><td >10</td><td  >11</td><td  >12</td><td  >13</td><td  >14</td><td  >15</td><td  >16</td><td  >17</td><td  >18</td><td >19</td><td  >20</td><td  >21</td><td  >22</td><td  >23</td><td  >24</td><td  >25</td><td  >26</td><td  >27</td><td >奇数<br />和值</td><td  >偶数<br />和值</td><td  >大数<br />和值</td><td  >小数<br />和值</td></tr></table>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td><td  >&nbsp;</td><td  >&nbsp;</td><td  >&nbsp;</td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">0</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">1</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">2</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">3</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">4</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">5</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">6</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">7</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">8</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">9</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">10</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">11</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">12</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">13</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">14</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">15</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">16</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">17</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">18</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">19</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">20</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">21</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">22</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">23</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">24</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">25</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">26</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">27</a></td><td  >&nbsp;</td><td  >&nbsp;</td><td  >&nbsp;</td><td  >&nbsp;</td>';

function getDrawedData() {
	return w3d_ball_data;
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
		nums : [ numArr[0], numArr[1], numArr[2] ],
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
		missItem.sumHash = prevMissItem.sumHash.map(function(value) {
			return value + 1;
		});
		missItem.oddevenHash = prevMissItem.oddevenHash.map(function(value) {
			return value + 1;
		});
		missItem.bigsmallHash = prevMissItem.bigsmallHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.sumHash = new Hash();
		SUM_ARR.each(function(num) {
			missItem.sumHash.set(num, 1);
		});
		missItem.oddevenHash = new Hash( {
			odd : 1,
			even : 1
		});
		missItem.bigsmallHash = new Hash( {
			big : 1,
			small : 1
		});
	}
	missItem.sumHash.set(result.sum.toString(), 0);
	if (result.sum % 2 != 0) {
		missItem.oddevenHash.set('odd', 0);
	} else {
		missItem.oddevenHash.set('even', 0);
	}
	if (result.sum >= 14) {
		missItem.bigsmallHash.set('big', 0);
	} else {
		missItem.bigsmallHash.set('small', 0);
	}

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
	html += '<td class="graychar333"></td>';
	html += '<td><span class="redredbchar">' + missItem.result.nums.join() + '</span></td>';
	html += '<td class="graychar333">' + missItem.result.sum + '</td>';

	SUM_ARR.each(function(key, index) {
		var miss = missItem.sumHash.get(key);
		var classStr = '';
		if (index % 10 == 0)
			//classStr += 'b1redpx ';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="ballredone" _cancas="cancas" _group="sum">' + key + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});

	html += '<td>'
			+ ((missItem.oddevenHash.get('odd') == 0) ? '<span class="bluedot" _cancas="cancas" _group="oddeven"></span>'
					: '&nbsp;') + '</td>';
	html += '<td>'
			+ ((missItem.oddevenHash.get('even') == 0) ? '<span class="bluedot" _cancas="cancas" _group="oddeven"></span>'
					: '&nbsp;') + '</td>';
	html += '<td>'
			+ ((missItem.bigsmallHash.get('big') == 0) ? '<span class="reddot" _cancas="cancas" _group="bigsmall"></span>'
					: '&nbsp;') + '</td>';
	html += '<td>'
			+ ((missItem.bigsmallHash.get('small') == 0) ? '<span class="reddot" _cancas="cancas" _group="bigsmall"></span>'
					: '&nbsp;') + '</td>';

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
	html += '<td class="graychar333"></td>';
	html += '<td class="graychar333"></td>';
	html += '<td class="graychar333"></td>';

	SUM_ARR.each(function(key, index) {
		var miss = missItem.sumHash.get(key);
		var classStr = '';
		if (index % 10 == 0)
			classStr += '';
		var ballStr = miss;
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});

	html += '<td>' + missItem.oddevenHash.get('odd') + '</td>';
	html += '<td>' + missItem.oddevenHash.get('even') + '</td>';
	html += '<td>' + missItem.bigsmallHash.get('big') + '</td>';
	html += '<td>' + missItem.bigsmallHash.get('small') + '</td>';

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
		sumHash : new Hash(),
		oddevenHash : new Hash(),
		bigsmallHash : new Hash()
	};
	var outMissItem = {
		sumHash : new Hash(),
		oddevenHash : new Hash(),
		bigsmallHash : new Hash()
	};
	var lastMissItem = null;
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var HASH_NAME_ARR = [ 'sumHash', 'oddevenHash', 'bigsmallHash' ];
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';

		HASH_NAME_ARR.each(function(hashName) {
			missItem[hashName].each(function(miss, key) {
				var maxMiss = maxMissItem[hashName].get(key);
				if (maxMiss == null || miss > maxMiss)
					maxMissItem[hashName].set(key, miss);

				var outCount = outMissItem[hashName].get(key);
				if (outCount == null)
					outCount = 0;
				if (miss == 0)
					outCount++;
				outMissItem[hashName].set(key, outCount);
			});
		});
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		sumHash : new Hash(),
		oddevenHash : new Hash(),
		bigsmallHash : new Hash()
	};
	HASH_NAME_ARR.each(function(hashName) {
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

	var moniHTML = '';
	for ( var i = 0; i < 1; i++) {
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + MONI_SELECT + '</tr>';
	}

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML + maxMissItemHTML + avgMissItemHTML
			+ TAB_FOOTER.replace(/@SELECT@/, selectHTML);
	return html;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var sumData = w3d_he;
	for ( var issue in w3d_he) {
		var missObj = {
			sumMissObj : sumData[issue]
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
		sumHash : new Hash(missObj.sumMissObj),
		oddevenHash : new Hash( {
			odd : 1,
			even : 1
		}),
		bigsmallHash : new Hash( {
			big : 1,
			small : 1
		})
	};

	// TODO:和值奇偶和大小遗漏未提供

	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	[ 'sum', 'oddeven', 'bigsmall' ].each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
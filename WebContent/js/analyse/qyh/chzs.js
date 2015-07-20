NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23' ];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td rowspan="2"  width="100">@SELECT@</td><td rowspan="2"  width="100">开奖号码</td><td colspan="23">开奖号码</td></tr><tr class=" zsttrpinklig "><td width="18" class="b1redpx">01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18">05</td><td width="18">06</td><td width="18">07</td><td width="18">08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18" class="b1redpx">12</td><td width="18">13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td><td width="18">17</td><td width="18">18</td><td width="18">19</td><td width="18">20</td><td width="18">21</td><td width="18">22</td><td width="18">23</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">@SELECT@</td><td rowspan="2" class="trtitlebg"  width="100">开奖号码</td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td class="b1redpx">12</td><td>13</td><td>14</td><td>15</td><td>16</td><td  >17</td><td  >18</td><td>19</td><td>20</td><td  >21</td><td  >22</td><td>23</td></tr><tr class="trtitlebg"><td colspan="23" class="b1redpx">开奖号码</td></tr></table>';
MONI_SELECT = '<td align="center" class="trgray graychar333">模拟选号</td><td align="center"></td><td align="center" class="b1redpx"><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td><td align="center" class="b1redpx"><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td><td align="center"><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td>';

function getDrawedData() {
	return dataQyh;
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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4]]
	};
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
	var missItem = {result : result};
	if (prevMissItem != null) {
		missItem.missHash = prevMissItem.missHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.missHash = new Hash();
		NUMS.each(function(num) {
			missItem.missHash.set(num, 1);
		});
	}
	result.nums.each(function(num) {
		missItem.missHash.set(num, 0);
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
    html += '<td class="redredbchar" align="center">' + missItem.result.nums + '</td>';
	var i = 0;
	NUMS.each(function(ball) {
		var miss = missItem.missHash.get(ball);
		i++;
		var classHtml = (i==1||i==12) ? 'class="b1redpx"' : '';
		var ballHtml = (miss == 0) ? '<span class="ballred">' + ball + '</span>' : miss;
		style="";
		html += '<td align="center"' + classHtml + ' '+style+'>' + ballHtml + '</td>';
	});
	i = 0;
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
	html += '<td class="graychar333" ></td>';
	var i = 0;
	NUMS.each(function(ball) {
		var miss = missItem.missHash.get(ball);
		i++;
		var classHtml = (i==1||i==12) ? 'class="b1redpx"' : '';
		html += '<td align="center"' + classHtml + ' '+style+'>' + miss + '</td>';
	});
	i = 0;
	return html;
}


/**
 * 显示遗漏数据
 * 
 * @param size - (<em>number</em>) 期数
 * @param desc - (<em>boolean</em>) 是否倒序输出
 */
function displayMissOfSize(size, desc) {
	CURRENT_FN = function(descSort) {
		displayMissOfSize(size, descSort);
	};

	if (desc == null)
		desc = false;
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;

	var keyOfHTML = 'HTML_' + MISS_MODE + '_' + size + '_' + desc;
	var html = getFromCache(keyOfHTML);
	if (html == null) {
		var key = 'MISS_DATA_' + MISS_MODE + '_' + size;
		var p = issueArr.length - size - 1;
		var checkFn;
		if (p > 0) {
			var issueInt = issueArr[p].replace("-","").toInt();
			checkFn = function(issue) {
				issue = issue.replace("-","");
				return issue.toInt() > issueInt;
			};
		} else {
			checkFn = function(issue) {
				return true;
			};
		}
		html = genMissHTML(key, checkFn, desc);

		cacheSet(keyOfHTML, html);
	}
	getMissElement().innerHTML = html;
	drawLine();
	setOnclick();
}
/**
 * 显示遗漏数据
 * 
 * @param startIssue - (<em>string</em>) 开始期号
 * @param endIssue - (<em>string</em>) 结束期号
 * @param desc - (<em>boolean</em>) 是否倒序输出
 */
function displayMissOfSearch(startIssue, endIssue, desc) {
	CURRENT_FN = function(descSort) {
		displayMissOfSearch(startIssue, endIssue, descSort);
	};

	if (desc == null)
		desc = false;
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;

	var keyOfHTML = 'HTML_' + MISS_MODE + '_' + startIssue + '_' + endIssue + '_' + desc;
	var html = getFromCache(keyOfHTML);
	if (html == null) {
		var key = 'MISS_DATA_' + MISS_MODE + '_' + startIssue + '_' + endIssue;
		var checkFn;
		var searchMaxSize = getSearchMaxSize();
		if (isBlank(startIssue) && isBlank(endIssue)) {
			alert('请输入查询条件.');
			return;
		} else if (isBlank(startIssue) && !isBlank(endIssue)) {
			var endIndex = issueArr.indexOf(endIssue);
			if (endIndex < 0) {
				alert('[' + endIssue + ']不在可供查询的范围内.');
				return;
			}
			var startIndex = (endIndex >= searchMaxSize) ? (endIndex + 1 - searchMaxSize) : 0;
			startIssue = issueArr[startIndex];
		} else if (!isBlank(startIssue) && isBlank(endIssue)) {
			var startIndex = issueArr.indexOf(startIssue);
			if (startIndex < 0) {
				alert('[' + startIssue + ']不在可供查询的范围内.');
				return;
			}
			var endIndex = startIndex + searchMaxSize - 1;
			if (endIndex >= issueArr.length)
				endIndex = issueArr.length - 1;
			endIssue = issueArr[endIndex];
		} else {
			var startIndex = issueArr.indexOf(startIssue);
			if (startIndex < 0) {
				alert('[' + startIssue + ']不在可供查询的范围内.');
				return;
			}
			var endIndex = issueArr.indexOf(endIssue);
			if (endIndex < 0) {
				alert('[' + endIssue + ']不在可供查询的范围内.');
				return;
			}
			if (endIndex - startIndex >= searchMaxSize) {
				alert('最大只允许查询' + searchMaxSize + '期.');
				return;
			} else if (startIndex > endIndex) {
				alert('查询条件有误.');
				return;
			}
		}
		var startIssueInt = startIssue.replace("-","").toInt();
		var endIssueInt = endIssue.replace("-","").toInt();
		checkFn = function(issue) {
			issue = issue.replace("-","");
			var issueInt = issue.toInt();
			return issueInt >= startIssueInt && issueInt <= endIssueInt;
		};
		html = genMissHTML(key, checkFn, desc);

		cacheSet(keyOfHTML, html);
	}
	getMissElement().innerHTML = html;
	drawLine();
	setOnclick();
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
		missHash : new Hash()
	};
	var outObj = {
		missHash : new Hash()
	};
	var lastMissItem = null;
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';
		missItem.missHash.each(function(miss, ball) {
				var maxMiss = maxMissItem.missHash.get(ball);
				if (maxMiss == null || miss > maxMiss)
					maxMissItem.missHash.set(ball, miss);

				var outCount = outObj.missHash.get(ball);
				if (outCount == null)
					outCount = 0;
				if (miss == 0)
					outCount++;
				outObj.missHash.set(ball, outCount);
		});
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		missHash : new Hash()
	};
	outObj.missHash.each(function(out, ball) {
			var avgMiss;
			if (out > 0) {
				avgMiss = Math.floor(issueSize / out);
			} else if (lastMissItem != null) {
				avgMiss = lastMissItem.missHash.get(ball);
			} else {
				avgMiss = 0;
			}
			avgMissItem.missHash.set(ball, avgMiss);
	});

	var maxMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('最大遗漏', maxMissItem) + '</tr>';
	var avgMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('平均遗漏', avgMissItem) + '</tr>';

	var moniHTML = '';
	for ( var i = 0; i < 3; i++) {
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
	var missData = qyhNum;
	for ( var issue in missData) {
		var missObj = {
			missObj : missData[issue]
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
		missHash : new Hash()
	};
	for ( var ball in missObj.missObj) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.missHash.set(ballNum, missObj.missObj[ball]);
	}
	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	
}
/**
 * 切换显示的期数目
 * 
 * @param elId - (<em>string</em>) 当前切换的标签ID
 * @param size - (<em>number</em>) 显示的期数目
 */
function chgDisplay(elId, size) {
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = (elId == buttonEl.id) ? 'btnow' : 'btgray';
	});
	displayMissOfSize(size);
}

/**
 * 查询遗漏数据
 */
function searchDisplay() {
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = 'btgray';
	});
	var startIssue = document.getElementById('startIssueNo').value;
	var endIssue = document.getElementById('endIssueNo').value;
	displayMissOfSearch(startIssue, endIssue);
}

// 华丽的分隔线 ======================================================================

DATA_HASH = new Hash();// 历史开奖数据
MISS_EL = null;// 遗漏数据展示的页面标签对象
MAX_HEIGHT = 320;
MAX_TIMES = 150;

REDS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33' ];
BLUES = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16' ];

TAB_HEADER = '<table width="988" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg"><tr class="trtitlebg"><td width="60" rowspan="2">次数</td><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11"  class="b1redpx">三区</td><td colspan="16" class="b1redpx">蓝球</td></tr><tr class=" zsttrpinklig "><td width="18" class="b1redpx">01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18">05</td><td width="18">06</td><td width="18">07</td><td width="18">08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18" class="b1redpx">12</td><td width="18">13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td><td width="18"  >17</td><td width="18"  >18</td><td width="18">19</td><td width="18">20</td><td width="18"  >21</td><td width="18"  >22</td><td width="18" class="b1redpx">23</td><td width="18">24</td><td width="18" >25</td><td width="18" >26</td><td width="18">27</td><td width="18">28</td><td width="18">29</td><td width="18" >30</td><td width="18" >31</td><td width="18">32</td><td width="18">33</td><td width="18" class="b1redpx">01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18">05</td><td width="18">06</td><td width="18">07</td><td width="18">08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18">12</td><td width="18">13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td></tr>';
TR_ROWSPAN = '<tr class="trgray"><td height="20" class="trgray graychar333">140</td></tr><tr class="trw"><td height="20" class="trw graychar333">130</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">120</td></tr><tr class="trw"><td height="20" class="trw graychar333">110</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">100</td></tr><tr class="trw"><td height="20" class="trw graychar333">90</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">80</td></tr><tr class="trw"><td height="20" class="trw graychar333" >70</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">60</td></tr><tr class="trw"><td height="20" class="trw graychar333">50</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">40</td></tr><tr class="trw"><td height="20" class="trw graychar333">30</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">20</td></tr><tr class="trw"><td height="20" class="trw graychar333">10</td></tr><tr class="trgray"><td height="20" class="trgray graychar333">0</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">次数</td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td class="b1redpx">12</td><td>13</td><td>14</td><td>15</td><td>16</td><td  >17</td><td  >18</td><td>19</td><td>20</td><td  >21</td><td  >22</td><td class="b1redpx">23</td><td>24</td><td >25</td><td >26</td><td>27</td><td>28</td><td>29</td><td >30</td><td >31</td><td>32</td><td>33</td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td></tr><tr class="trtitlebg"><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11"  class="b1redpx">三区</td><td colspan="16" class="b1redpx">蓝球</td></tr></table>';

/**
 * 获取显示遗漏数据的标签对象
 * 
 * @returns {element} - 显示遗漏数据的标签对象
 */
function getMissElement() {
	if (MISS_EL == null)
		MISS_EL = document.getElementById('MISS_EL');
	return MISS_EL;
}

/**
 * 初始化DATA_HASH,将历史开奖数据转为Hash存储，方便访问
 */
function initDataHash() {
	var dataObj = dataShuangseqiu;
	for ( var issue in dataObj) {
		DATA_HASH.set(issue, genResult(dataObj[issue]));
	}
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
		redNums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5] ],
		blueNum : numArr[6]
	};
	return result;
}

/**
 * @returns {number} - 查询的最大期数限制
 */
function getSearchMaxSize() {
	return 300;
}


/**
 * 检查字符串是否为NULL或为空字符串
 * 
 * @param str 字符串
 * @returns {boolean} 是否为NULL或为空字符串
 */
function isBlank(str) {
	return str == null || str.trim().length == 0;
}

/**
 * 显示遗漏数据
 * 
 * @param size - (<em>number</em>) 期数
 */
function displayMissOfSize(size) {
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;
	var p = issueArr.length - size - 1;
	var checkFn;
	if (p > 0) {
		var issueInt = issueArr[p].toInt();
		checkFn = function(issue) {
			return issue.toInt() > issueInt;
		};
	} else {
		checkFn = function(issue) {
			return true;
		};
	}
	displayHandler(checkFn);
}

/**
 * 显示遗漏数据
 * 
 * @param startIssue - (<em>string</em>) 开始期号
 * @param endIssue - (<em>string</em>) 结束期号
 */
function displayMissOfSearch(startIssue, endIssue) {
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;
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
	var startIssueInt = startIssue.toInt();
	var endIssueInt = endIssue.toInt();
	checkFn = function(issue) {
		var issueInt = issue.toInt();
		return issueInt >= startIssueInt && issueInt <= endIssueInt;
	};
	displayHandler(checkFn);
}

/**
 * 显示处理函数
 * 
 * @param checkFn - (<em>function</em>) 检查是否需要统计该期的函数
 *            <ul>
 *            <h3>语法：<code>fn(issue)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            </ol>
 *            </ul>
 */
function displayHandler(checkFn) {
	var outItem = {
		redHash : new Hash(),
		blueHash : new Hash()
	};
	REDS.each(function(ball) {
		outItem.redHash.set(ball, 0);
	});
	BLUES.each(function(ball) {
		outItem.blueHash.set(ball, 0);
	});
	var issueSize = 0;
	DATA_HASH.each(function(result, issue) {
		if (checkFn(issue)) {
			result.redNums.each(function(ball) {
				outItem.redHash.set(ball, outItem.redHash.get(ball) + 1);
			});
			outItem.blueHash.set(result.blueNum, outItem.blueHash.get(result.blueNum) + 1);
			issueSize++;
		}
	});
	outItem.issueSize = issueSize;

	getMissElement().innerHTML = genHTML(outItem);
}

/**
 * 生成要显示的HTML代码
 * 
 * @param outItem - (<em>object</em>) 统计数据
 * @returns {string} 要显示的HTML代码
 */
function genHTML(outItem) {
	var topRowHTML = '<tr class="trw"><td height="20" class="graychar333">150</td>';
	var timesHTML = '<tr class="trw"><td height="20" class="graychar333"><span class="graychar333">出现<br />次数</span></td>';
	var rateHTML = '<tr class="trgray"><td class="graychar333">出现<br />频率<br />(%)</td>';
	var i = 0;
	REDS.each(function(ball) {
		var outTimes = outItem.redHash.get(ball);
		i++;
		var classHtml = (i % 11 == 1) ? 'class="b1redpx"' : '';
		var heigth = getMissHeigth(outTimes);
		topRowHTML += '<td ' + classHtml + ' valign="bottom" rowspan="16"><span class="c10arail">' + outTimes
				+ '</span><br><img height="' + heigth + '" width="7" src="'+BASESITE+'/images/zhubg.gif"></td>';
		timesHTML += '<td ' + classHtml + '>' + outTimes + '</td>';
		var rate = outTimes * 100 / (outItem.issueSize * 6);
		rate = (rate > 9) ? rate.round() : rate.round(1);
		rateHTML += '<td ' + classHtml + '>' + rate + '</td>';
	});
	i = 0;
	BLUES.each(function(ball) {
		var outTimes = outItem.blueHash.get(ball);
		i++;
		var classHtml = (i == 1) ? 'class="zsttrblue b1redpx"' : 'class="zsttrblue"';
		var heigth = getMissHeigth(outTimes);
		topRowHTML += '<td ' + classHtml + ' valign="bottom" rowspan="16"><span class="c10arail">' + outTimes
				+ '</span><br><img height="' + heigth + '" width="7" src="'+BASESITE+'/images/zhubgblue.gif"></td>';
		timesHTML += '<td ' + classHtml + '>' + outTimes + '</td>';
		var rate = outTimes * 100 / outItem.issueSize;
		rate = (rate > 9) ? rate.round() : rate.round(1);
		rateHTML += '<td ' + classHtml + '>' + rate + '</td>';
	});
	topRowHTML += '</tr>';
	timesHTML += '</tr>';
	rateHTML += '</tr>';

	return TAB_HEADER + topRowHTML + TR_ROWSPAN + timesHTML + rateHTML + TAB_FOOTER;
}

/**
 * 获取要显示的柱形高度
 * 
 * @param times - (<em>number</em>) 出现的次数
 * @returns {number} - 柱形高度
 */
function getMissHeigth(times) {
	if (times == 0)
		return 0;
	else if (times >= MAX_TIMES)
		return MAX_HEIGHT;
	else
		return (times * MAX_HEIGHT / MAX_TIMES).round();
}
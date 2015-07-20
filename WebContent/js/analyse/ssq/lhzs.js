REDS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33' ];
BLUES = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16' ];

TAB_HEADER = '<table width="980" border="0" cellpadding="1" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11"  class="b1redpx">三区</td><td colspan="5"   class="b1redpx">红球分析</td><td rowspan="2"   class="b1redpx">蓝球 </td><td rowspan="2"  >AC值</td></tr><tr class=" zsttrpinklig"><td width="18" class="b1redpx">01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18">05</td><td width="18">06</td><td width="18">07</td><td width="18">08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18" class="b1redpx">12</td><td width="18">13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td><td width="18">17</td><td width="18">18</td><td width="18">19</td><td width="18">20</td><td width="18">21</td><td width="18">22</td><td width="18" class="b1redpx">23</td><td width="18">24</td><td width="18">25</td><td width="18">26</td><td width="18">27</td><td width="18">28</td><td width="18">29</td><td width="18">30</td><td width="18">31</td><td width="18">32</td><td width="18">33</td><td class="b1redpx">重号</td><td width="25" >连号<br />个数</td><td>和值</td><td width="40">三区比</td><td width="40">奇偶比</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg"><select name="select2" size="1" id="select2" style="width:50px;" ><option>期号</option><option>大到小</option><option>小到大</option></select></td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td class="b1redpx">12</td><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td><td>18</td><td>19</td><td>20</td><td>21</td><td>22</td><td class="b1redpx">23</td><td>24</td><td>25</td><td>26</td><td>27</td><td>28</td><td>29</td><td>30</td><td>31</td><td>32</td><td>33</td><td class="b1redpx">重号 </td><td>连号<br />个数</td><td>和值</td><td>三区比</td><td>奇偶比</td><td rowspan="2" class="b1redpx zsttrpink">蓝球</td><td rowspan="2" class="trtitlebg">AC值</td></tr><tr class="trtitlebg"><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11" class="b1redpx">三区</td><td colspan="5" class="b1redpx">红球分析</td></tr></table>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td><td class="b1redpx"><a href="#" onclick="red_click(this);return false;" class="gball">01</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">02</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">03</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">04</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">05</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">06</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">07</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">08</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">09</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">10</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">11</a></td><td class="b1redpx" ><a href="#" onclick="red_click(this);return false;" class="gball">12</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">13</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">14</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">15</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">16</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">17</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">18</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">19</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">20</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">21</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">22</a></td><td class="b1redpx"><a href="#" onclick="red_click(this);return false;" class="gball">23</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">24</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">25</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">26</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">27</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">28</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">29</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">30</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">31</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">32</a></td><td><a href="#" onclick="red_click(this);return false;" class="gball">33</a></td><td class="zsttrpinklig b1redpx ">&nbsp;</td><td class=" zsttrpinklig ">&nbsp;</td><td class=" zsttrpinklig ">&nbsp;</td><td class=" zsttrpinklig ">&nbsp;</td><td class=" zsttrpinklig ">&nbsp;</td><td class="zsttrpinklig b1redpx ">&nbsp;</td><td class=" zsttrpinklig ">&nbsp;</td>';

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
	var blueNum = numArr.pop();
	numArr.sort(ASC);
	var result = {
		redNums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5] ], // 红球开奖号码
		blueNum : blueNum, // 蓝球开奖号码
		sum : 0, // 和值
		repeat : 0, // 重号
		continuous : 0, // 连号对数
		intervalCompare : [ 0, 0, 0 ], // 三区比
		oddeven : [ 0, 0 ], // 奇偶比
		sameMantissa : 0, // 同尾对数
		ac : -5
	// AC值
	};

	var prevNum = null;
	var prevNumInt = null;
	var sameMantissaObj = {};
	var redNumIntArr = [];
	var continuousObj = {};
	result.redNums.each(function(redNum) {
		var redNumInt = redNum.toInt();
		redNumIntArr.push(redNumInt);

		result.sum += redNumInt;

		if (prevResult != null && prevResult.redNums.contains(redNum)) {
			result.repeat += 1;
		}

		if (prevNumInt != null && redNumInt - prevNumInt == 1) {
			continuousObj[prevNum] = true;
			continuousObj[redNum] = true;
		}

		if (redNumInt < 12) {
			result.intervalCompare[0] += 1;
		} else if (redNumInt < 23) {
			result.intervalCompare[1] += 1;
		} else {
			result.intervalCompare[2] += 1;
		}

		if (redNumInt % 2 != 0) {
			result.oddeven[0] += 1;
		} else {
			result.oddeven[1] += 1;
		}

		var key = (redNumInt % 10).toString();
		var times = sameMantissaObj[key];
		sameMantissaObj[key] = (times == null) ? 0 : times + 1;

		prevNumInt = redNumInt;
		prevNum = redNum;
	});
	result.continuousObj = continuousObj;
	for ( var key in continuousObj) {
		result.continuous += 1;
	}
	for ( var p in sameMantissaObj) {
		var times = sameMantissaObj[p];
		result.sameMantissa += times;
	}

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
 * @param prevResult - (<em>object</em>) 上一期的开奖结果
 * @returns {object} - 该期的遗漏数据
 */
function buildMissItem(issue, result, prevMissItem, prevResult) {
	var missItem = {
		result : result
	};
	if (prevMissItem != null) {
		missItem.redMissHash = prevMissItem.redMissHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.redMissHash = new Hash();
		REDS.each(function(num) {
			missItem.redMissHash.set(num, 1);
		});
	}
	result.redNums.each(function(redNum) {
		missItem.redMissHash.set(redNum, 0);
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
	var html = '<td>' + issue + '</td>';
	var i = 0;
	REDS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = (i % 11 == 1) ? 'class="b1redpx"' : '';
		var ballHtml = (miss == 0) ? '<span class="'
				+ ((missItem.result.continuousObj[ball] == true) ? 'nballred' : 'graychar333') + '">' + ball
				+ '</span>' : miss;
		html += '<td ' + classHtml + '>' + ballHtml + '</td>';
	});
	html += '<td class="b1redpx">' + missItem.result.repeat + '</td>';
	html += '<td>' + missItem.result.continuous + '</td>';
	html += '<td>' + missItem.result.sum + '</td>';
	html += '<td>' + missItem.result.intervalCompare.join(':') + '</td>';
	html += '<td>' + missItem.result.oddeven.join(':') + '</td>';
	html += '<td class="b1redpx"><span class=" bluecharnor">' + missItem.result.blueNum + '</span></td>';
	html += '<td>' + missItem.result.ac + '</td>';
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

	var moniHTML = '';
	for ( var i = 0; i < 3; i++) {
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + MONI_SELECT + '</tr>';
	}

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML
			+ TAB_FOOTER.replace(/@SELECT@/, selectHTML);

	return html;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var redMissData = Shuangseqiured;
	for ( var issue in redMissData) {
		var missObj = {
			redMissObj : redMissData[issue]
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
		redMissHash : new Hash()
	};
	for ( var ball in missObj.redMissObj) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.redMissHash.set(ballNum, missObj.redMissObj[ball]);
	}
	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};
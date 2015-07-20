REDS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33' ];
BLUES = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16' ];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td rowspan="2">@SELECT@</td><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11"class="b1redpx">三区</td><td colspan="16" class="b1redpx">蓝球</td></tr><tr class=" zsttrpinklig "><td width="18" class="b1redpx">01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18">05</td><td width="18">06</td><td width="18">07</td><td width="18">08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18" class="b1redpx">12</td><td width="18">13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td><td width="18">17</td><td width="18">18</td><td width="18">19</td><td width="18">20</td><td width="18">21</td><td width="18">22</td><td width="18" class="b1redpx">23</td><td width="18">24</td><td width="18" >25</td><td width="18" >26</td><td width="18">27</td><td width="18">28</td><td width="18">29</td><td width="18" >30</td><td width="18" >31</td><td width="18">32</td><td width="18">33</td><td width="18" class=" b1redpx" >01</td><td width="18">02</td><td width="18">03</td><td width="18">04</td><td width="18" >05</td><td width="18">06</td><td width="18">07</td><td width="18" >08</td><td width="18">09</td><td width="18">10</td><td width="18">11</td><td width="18">12</td><td width="18" >13</td><td width="18">14</td><td width="18">15</td><td width="18">16</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">@SELECT@</td><td class="b1redpx">01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td class="b1redpx">12</td><td>13</td><td>14</td><td>15</td><td>16</td><td  >17</td><td  >18</td><td>19</td><td>20</td><td  >21</td><td  >22</td><td class="b1redpx">23</td><td>24</td><td >25</td><td >26</td><td>27</td><td>28</td><td>29</td><td >30</td><td >31</td><td>32</td><td>33</td><td class=" b1redpx" >01</td><td>02</td><td>03</td><td>04</td><td >05</td><td>06</td><td>07</td><td >08</td><td>09</td><td>10</td><td>11</td><td>12</td><td >13</td><td>14</td><td>15</td><td>16</td></tr><tr class="trtitlebg"><td colspan="11" class="b1redpx">一区</td><td colspan="11" class="b1redpx">二区</td><td colspan="11"  class="b1redpx">三区</td><td colspan="16" class="b1redpx">蓝球</td></tr></table>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td><td class="b1redpx"><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td><td class="b1redpx"><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td><td class="b1redpx"><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">24</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">25</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">26</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">27</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">28</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">29</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">30</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">31</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">32</a></td><td><a href="#" class="gball" onclick="red_click(this);return false;">33</a></td><td class="b1redpx"><a href="#" class="gballblue" onclick="blue_click(this);return false;">01</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">02</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">03</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">04</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">05</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">06</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">07</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">08</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">09</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">10</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">11</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">12</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">13</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">14</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">15</a></td><td class=" zsttrpinklig "><a href="#" class="gballblue" onclick="blue_click(this);return false;">16</a></td>';

function getDrawedData() {
	return dataShuangseqiu;
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
 * 计算一期遗漏数据
 * 
 * @param issue - (<em>string</em>) 期号
 * @param result - (<em>object</em>) 开奖结果
 * @param prevMissItem - (<em>object</em>) 上一期的遗漏数据
 * @returns {object} - 该期的遗漏数据
 */
function buildMissItem(issue, result, prevMissItem) {
	var missItem = {};
	if (prevMissItem != null) {
		missItem.redMissHash = prevMissItem.redMissHash.map(function(value) {
			return value + 1;
		});
		missItem.blueMissHash = prevMissItem.blueMissHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.redMissHash = new Hash();
		REDS.each(function(num) {
			missItem.redMissHash.set(num, 1);
		});
		missItem.blueMissHash = new Hash();
		BLUES.each(function(num) {
			missItem.blueMissHash.set(num, 1);
		});
	}
	result.redNums.each(function(redNum) {
		missItem.redMissHash.set(redNum, 0);
	});
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
	var i = 0;
	REDS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = (i % 11 == 1) ? 'class="b1redpx"' : '';
		var ballHtml = (miss == 0) ? '<span class="ballred">' + ball + '</span>' : miss;
		if(miss>=100){
			style=STYLE;
		}else{
			style="";
		}
		html += '<td ' + classHtml + ' '+style+'>' + ballHtml + '</td>';
	});
	i = 0;
	BLUES.each(function(ball) {
		var miss = missItem.blueMissHash.get(ball);
		i++;
		var classHtml = (i == 1) ? 'class="b1redpx"' : '';
		var ballHtml = (miss == 0) ? '<span class="bball" _cancas="cancas" _group="blue">' + ball + '</span>' : miss;
        html += '<td ' + classHtml + ' '+style+'>' + ballHtml + '</td>';
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
	var i = 0;
	REDS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = (i % 11 == 1) ? 'class="b1redpx"' : '';
		html += '<td ' + classHtml + ' '+style+'>' + miss + '</td>';
	});
	i = 0;
	BLUES.each(function(ball) {
		var miss = missItem.blueMissHash.get(ball);
		i++;
		var classHtml = (i == 1) ? 'class="b1redpx"' : '';
		html += '<td ' + classHtml + ' '+style+'>' + miss + '</td>';
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
		redMissHash : new Hash(),
		blueMissHash : new Hash()
	};
	var outObj = {
		redMissHash : new Hash(),
		blueMissHash : new Hash()
	};
	var lastMissItem = null;
	var missTypes = [ 'redMissHash', 'blueMissHash' ];
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';

		for ( var i = 0, len = missTypes.length; i < len; i++) {
			var missType = missTypes[i];
			missItem[missType].each(function(miss, ball) {
				var maxMiss = maxMissItem[missType].get(ball);
				if (maxMiss == null || miss > maxMiss)
					maxMissItem[missType].set(ball, miss);

				var outCount = outObj[missType].get(ball);
				if (outCount == null)
					outCount = 0;
				if (miss == 0)
					outCount++;
				outObj[missType].set(ball, outCount);
			});
		}
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		redMissHash : new Hash(),
		blueMissHash : new Hash()
	};
	missTypes.each(function(type) {
		outObj[type].each(function(out, ball) {
			var avgMiss;
			if (out > 0) {
				avgMiss = Math.floor(issueSize / out);
			} else if (lastMissItem != null) {
				avgMiss = lastMissItem[type].get(ball);
			} else {
				avgMiss = 0;
			}
			avgMissItem[type].set(ball, avgMiss);
		});
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
	var redMissData = Shuangseqiured;
	var blueMissData = Shuangseqiublue;
	for ( var issue in redMissData) {
		var missObj = {
			redMissObj : redMissData[issue],
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
		redMissHash : new Hash(),
		blueMissHash : new Hash()
	};
	for ( var ball in missObj.redMissObj) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.redMissHash.set(ballNum, missObj.redMissObj[ball]);
	}
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
	var tagArr1 = $$('span[_cancas="cancas"]');
	var myfun = function() {
		drawCancas(tagArr1, 1);
	};
	myfun.delay(10);
}
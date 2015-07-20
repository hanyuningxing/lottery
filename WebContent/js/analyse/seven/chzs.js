NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30' ];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="75" rowspan="2" align="center">@SELECT@</td> <td rowspan="2" align="center">开奖号码</td><td height="22" colspan="30" >号码走势</td><td width="45" rowspan="2" >3分区</td><td width="35" rowspan="2" >和值</td></tr><tr class="zsttrpinklig"><td width="21" >01</td><td width="21" >02</td><td width="21">03</td><td width="21" > 04</td><td width="21" >05</td><td width="21" >06</td><td width="21" >07</td><td width="21" >08</td><td width="21" >09</td><td width="21" >10</td><td width="21" >11</td><td width="21" >12</td><td width="21" >13</td><td width="21" >14</td><td width="21" >15</td><td width="21" >16</td><td width="21" >17</td><td width="21" >18</td><td width="21" >19</td><td width="21" >20</td><td width="21" >21</td><td width="21" >22</td><td width="21" >23</td><td width="21" >24</td><td width="21" >25</td><td width="21" >26</td><td width="21" >27</td><td width="21" >28</td><td width="21" >29</td><td width="21" >30</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">@SELECT@</td><td class="zsttrpinklig">&nbsp;</td><td>01</td><td>02</td><td>03</td><td>04</td><td>05</td><td>06</td><td>07</td><td>08</td><td>09</td><td>10</td><td>11</td><td>12</td><td>13</td><td>14</td><td>15</td><td>16</td><td  >17</td><td  >18</td><td>19</td><td>20</td><td  >21</td><td  >22</td><td width="21" >23</td><td width="21" >24</td><td width="21" >25</td><td width="21" >26</td><td width="21" >27</td><td width="21" >28</td><td width="21" >29</td><td width="21" >30</td><td width="55">3分区</td><td>和值</td></tr></table>';
MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">模拟选号</td><td class="zsttrpinklig">&nbsp;</td><td><a onclick="ball_click(this);return false;" class="gball">01</a></td><td ><a onclick="ball_click(this);return false;" class="gball">02</a></td><td ><a onclick="ball_click(this);return false;" class="gball">03</a></td><td ><a onclick="ball_click(this);return false;" class="gball">04</a></td><td ><a onclick="ball_click(this);return false;" class="gball">05</a></td><td ><a onclick="ball_click(this);return false;" class="gball">06</a></td><td  ><a onclick="ball_click(this);return false;" class="gball">07</a></td><td ><a onclick="ball_click(this);return false;" class="gball">08</a></td><td><a onclick="ball_click(this);return false;" class="gball">09</a></td><td><a onclick="ball_click(this);return false;" class="gball">10</a></td><td><a onclick="ball_click(this);return false;" class="gball">11</a></td><td class="zsttrpinklig"  ><a onclick="ball_click(this);return false;" class="gball">12</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">13</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">14</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">15</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">16</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">17</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">18</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">19</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">20</a></td><td><a onclick="ball_click(this);return false;" class="gball">21</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">22</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">23</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">24</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">25</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">26</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">27</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">28</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">29</a></td><td class="zsttrpinklig"><a onclick="ball_click(this);return false;" class="gball">30</a></td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td></tr>';



function getDrawedData() {
	return dataSeven;
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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5], numArr[6], numArr[7] ],
		spNum : numArr[7],
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
		missItem.redMissHash = prevMissItem.redMissHash.map(function(value) {
			return value + 1;
		});
	} else {
		missItem.redMissHash = new Hash();
		NUMS.each(function(num) {
			missItem.redMissHash.set(num, 1);
		});
	}
	result.nums.each(function(redNum) {
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
	var html = '<td class="graychar333" >' + issue + '</td>';
	var i = 0;
	
	var qu1=0;
	var qu2=0;
	var qu3=0;
	html += '<td class="trw graychar333">';
	missItem.result.nums.each(function(num,index){
		html += num;
		if(index < 6){
			html +=' ';
		}else if(index==6){
			html +=' + ';
		}
		if(num<=10){
			qu1 +=1;
		}else if(num<=20){
			qu2+=1;
		}else{
			qu3+=1;
		}
	});
	html += '</td>';
	NUMS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = 'tdjiange01';
		var ballClass='ballred';
		if(ball==missItem.result.spNum){
			ballClass='bball';
		}
		var ballHtml = (miss == 0) ? '<span class="'+ballClass+'">' + ball + '</span>' : miss;
		if(miss>=100){
			style=STYLE;
		}else{
			style="";
		}
		html += '<td class="tdjiange01" '+style+'>' + ballHtml + '</td>';
	});
	html += '<td class="trw graychar333">' + qu1+':'+qu2+':'+qu3 + '</td>';
	html += '<td class="trw graychar333">' + missItem.result.sum + '</td>';
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
	html += '<td></td>';
	NUMS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		html += '<td ' +style+'>' + miss + '</td>';
	});
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
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
	var missTypes = [ 'redMissHash' ];
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">' + missItem.html + '</tr>';

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

	var maxMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">'
			+ buildStatItemHTML('最大遗漏', maxMissItem) + '</tr>';
	var avgMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">'
			+ buildStatItemHTML('平均遗漏', avgMissItem) + '</tr>';

	var moniHTML = '';
	for ( var i = 0; i < 3; i++) {
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">' + MONI_SELECT + '</tr>';
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
NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09' ,'10', '11'];
HashNameArr = [ 'firstNumHash','secondNumHash','thirdNumHash'];

COMPARENUM=['03','12','21','30'];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td rowspan="2" align="center">@SELECT@</td><td colspan="3" rowspan="2" >开奖号码</td><td colspan="11" >第一个号码</td><td colspan="11" >第二个号码</td><td height="22" colspan="11" >第三 个号码</td><td colspan="4" >奇偶比</td><td colspan="4" >大小比</td></tr><tr class="zsttrpinklig"><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td width="30" >0:3</td><td width="30" >1:2</td><td width="30" >2:1</td><td width="30" >3:0</td><td width="30" >0:3</td><td width="30" >1:2</td><td width="30" >2:1</td><td width="30" >3:0</td></tr>';
TAB_FOOTER = '<tr class="zsttrpinklig"><td rowspan="2" align="center" class="trtitlebg">@SELECT@</td><td colspan="3" rowspan="2" class="trtitlebg" >开奖号码</td><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td >1</td><td >2</td><td>3</td><td >4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td >10</td><td >11</td><td >0:3</td><td >1:2</td><td >2:1</td><td >3:0</td><td >0:3</td><td >1:2</td><td >2:1</td><td >3:0</td></tr><tr class="trtitlebg"><td colspan="11" >第一个号码</td><td colspan="11" >第二个号码</td><td height="22" colspan="11" >第三 个号码</td><td colspan="4" class="trtitlebg" >奇偶比</td><td colspan="4" class="trtitlebg" >大小比</td></tr></table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig"  >&nbsp;</td><td>&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td></tr>';

function getDrawedData() {
	return dataEl11to5;
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
		nums : [ numArr[0], numArr[1], numArr[2]],
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
	var tempJiNum=0;
	var tempDaNum=0;
	result.nums.each(function(num, index) {
		if(num%2!=0){
			tempJiNum +=1;
		}
		if(num>5){
			tempDaNum+=1;
		}
	});
	
	if (prevMissItem != null) {
		
		HashNameArr.each(function(hashName) {
			missItem[hashName] = prevMissItem[hashName].map(function(value) {
				return value + 1;
			});
		});
		missItem.jiCompareHash = prevMissItem.jiCompareHash.map(function(value) {
			return value + 1;
		});
		missItem.daCompareHash = prevMissItem.daCompareHash.map(function(value) {
			return value + 1;
		});
		
	} else {
		missItem.jiCompareHash=new Hash();
		missItem.daCompareHash=new Hash();
		HashNameArr.each(function(hashName) {
			missItem[hashName] = new Hash();
			NUMS.each(function(num) {
				missItem[hashName].set(num, 1);
			});
		});
		COMPARENUM.each(function(num) {
			missItem.jiCompareHash.set(num,1);
			missItem.daCompareHash.set(num,1);
		});
	}
	result.nums.each(function(num, index) {
		missItem[HashNameArr[index]].set(num, 0);
		missItem.jiCompareHash.set(COMPARENUM[tempJiNum],0);
		missItem.daCompareHash.set(COMPARENUM[tempDaNum],0);
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
	var html = '<td height="22" class="trw graychar333">' + issue + '</td>';
	missItem.result.nums.each(function(num){
		html += '<td class="trw graychar333"><span class="redredbchar">'+num+'</span></td>';
	});
	var k = 0;
	HashNameArr.each(function(hashName, index) {
		NUMS.each(function(num) {
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0){
				classStr = 'tdjiange01';
			}else if(k==2){
				classStr = 'trgray';
			}
			var ballStr;
			if (miss == 0) {
				ballStr = '<span class="ballclickKeno" _cancas="cancas" _group="' + hashName + '">' + num + '</span>';
			} else {
				ballStr = miss;
			}
			html += '<td class="' + classStr + '">' + ballStr + '</td>';
		});
		k++;
	});
	COMPARENUM.each(function(num) {
		var miss = missItem.jiCompareHash.get(num);
		var classStr = 'tdjiange01';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="bluebg" _cancas="cancas" _group="jiCompareHash">' + num + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	COMPARENUM.each(function(num) {
		var miss = missItem.daCompareHash.get(num);
		var classStr = '';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="bgr1" _cancas="cancas" _group="daCompareHash">' + num + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
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
	var html = '<td>' + statText + '</td>';
	for(var i=0;i<3;i++){
		html += '<td></td>';
	}

	var classArr = [ 'trchosegrayd', 'ballclickKeno', 'ballblue', 'ballclickKeno' ];
	var k = 0;
	HashNameArr.each(function(hashName, index) {
		NUMS.each(function(num) {
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0){
				classStr = 'tdjiange01';
			}else if(k==2){
				classStr = 'trgray';
			}
			html += '<td class="' + classStr + '">' + miss + '</td>';
		});
		k++;
	});
	COMPARENUM.each(function(num) {
		var miss = missItem.jiCompareHash.get(num);
		var classStr = 'tdjiange01';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	COMPARENUM.each(function(num) {
		var miss = missItem.daCompareHash.get(num);
		var classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
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
		firstNumHash : new Hash(),
		secondNumHash : new Hash(),
		thirdNumHash : new Hash(),
		jiCompareHash : new Hash(),
		daCompareHash : new Hash()
	};
	var outMissItem = {
		firstNumHash : new Hash(),
		secondNumHash : new Hash(),
		thirdNumHash : new Hash(),
		jiCompareHash : new Hash(),
		daCompareHash : new Hash()
	};
	var lastMissItem = null;
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="trw">' + missItem.html + '</tr>';

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
		missItem.jiCompareHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.jiCompareHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.jiCompareHash.set(ball, miss);

			var outCount = outMissItem.jiCompareHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.jiCompareHash.set(ball, outCount);
		});
		missItem.daCompareHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.daCompareHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.daCompareHash.set(ball, miss);

			var outCount = outMissItem.daCompareHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.daCompareHash.set(ball, outCount);
		});
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		firstNumHash : new Hash(),
		secondNumHash : new Hash(),
		thirdNumHash : new Hash(),
		jiCompareHash : new Hash(),
		daCompareHash : new Hash()
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
	outMissItem.jiCompareHash.each(function(out, ball) {
		var avgMiss;
		if (out > 0) {
			avgMiss = Math.floor(issueSize / out);
		} else if (lastMissItem != null) {
			avgMiss = lastMissItem.jiCompareHash.get(ball);
		} else {
			avgMiss = 0;
		}
		avgMissItem.jiCompareHash.set(ball, avgMiss);
	});
	outMissItem.daCompareHash.each(function(out, ball) {
		var avgMiss;
		if (out > 0) {
			avgMiss = Math.floor(issueSize / out);
		} else if (lastMissItem != null) {
			avgMiss = lastMissItem.daCompareHash.get(ball);
		} else {
			avgMiss = 0;
		}
		avgMissItem.daCompareHash.set(ball, avgMiss);
	});

	var maxMissItemHTML = '<tr class="trw">'
			+ buildStatItemHTML('最大遗漏', maxMissItem) + '</tr>';
	var avgMissItemHTML = '<tr class="trw">'
			+ buildStatItemHTML('平均遗漏', avgMissItem) + '</tr>';
	var outMissItemHTML = '<tr class="trw">'
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
	var data = w3d_miss_zx;
	var number_data = w3d_miss_ch;
	for ( var issue in data) {
		var missObj = {
			hundredMissObj : data[issue].b,
			tenMissObj : data[issue].s,
			unitMissObj : data[issue].g,
			numberMissObj : number_data[issue]
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
		numberHash : new Hash(missObj.numberMissObj),
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
	[ 'firstNumHash','secondNumHash','thirdNumHash','jiCompareHash','daCompareHash'].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
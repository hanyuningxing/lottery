NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09' ,'10', '11'];
COUNTS=['0','1', '2', '3', '4', '5'];
HashNameArr = [ 'redMissHash','fhMissHash'];

TAB_HEADER = '<table width="980"  border="0"  cellpadding="0"  cellspacing="1"  class="zstablegraybg" ><tr class="trtitlebg" ><td width="75" rowspan="2" align="center" >@SELECT@</td><td colspan="5" rowspan="2" >开奖号码</td><td height="22" colspan="11" >号码分布</td><td class="b1redpx" colspan="6">重号个数</td><td colspan="1">一区</td><td colspan="1">二区</td><td colspan="1">三区</td><td colspan="1" rowspan="2">个数</td></tr><tr class="zsttrpinklig" ><td width="30" >01</td><td width="30" >02</td><td width="30" >03</td><td width="30" >04</td><td width="30" >05</td><td width="30" >06</td><td width="30" >07</td><td width="30" >08</td><td width="30" >09</td><td width="30" >10</td><td width="30" >11</td><td width="22" class="b1redpx">0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td><td width="22" >5</td><td width="22" >01-04</td><td width="22" >05-08</td><td width="22" >09-11</td></tr>';
TAB_FOOTER = '<tr  class="zsttrpinklig" ><td rowspan="2" align="center" class="trtitlebg" >@SELECT@</td><td colspan="5" rowspan="2" class="trtitlebg" >开奖号码</td><td width="30" >01</td><td width="30" >02</td><td width="30" >03</td><td width="30" >04</td><td width="30" >05</td><td width="30" >06</td><td width="30" >07</td><td width="30" >08</td><td width="30" >09</td><td width="30" >10</td><td width="30" >11</td><td width="22" >0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td><td width="22" >5</td><td width="22" >01-04</td><td width="22" >05-08</td><td width="22" >09-11</td><td colspan="1" rowspan="2">个数</td></tr><tr class="trtitlebg" ><td height="22" colspan="11" >号码分布</td><td colspan="6">重号个数</td><td colspan="1">一区</td><td colspan="1">二区</td><td colspan="1">三区</td></tr></table>';
MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td></tr>';

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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4] ],
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
		result : result,
		prevMissItem:prevMissItem
		
	};
	var hits=0;
	var qu1Hits=0;
	var qu2Hits=0;
	var qu3Hits=0;

	if (prevMissItem != null) {
		
		missItem.redMissHash = prevMissItem.redMissHash.map(function(value) {
			return value + 1;
		});
		missItem.fhMissHash = prevMissItem.fhMissHash.map(function(value) {
			return value + 1;
		});	
		missItem.quMissHash = prevMissItem.quMissHash.map(function(value) {
			return value + 1;
		});	
		
	} else {
		missItem.redMissHash = new Hash();
		missItem.fhMissHash = new Hash();
		missItem.quMissHash = new Hash();
		NUMS.each(function(num) {
			missItem.redMissHash.set(num, 1);
		});
		for(var i=0;i<=5;i++){
			missItem.fhMissHash.set(i, 1);
		}
		missItem.quMissHash.set("qu1", 0);
		missItem.quMissHash.set("qu2", 0);
		missItem.quMissHash.set("qu3", 0);
	}

	if(prevMissItem!=null){
		result.nums.each(function(num) {
			missItem.redMissHash.set(num, 0);
			if(prevMissItem.result.nums.contains(num)){
				hits++;
				if(num<4){
					qu1Hits++;
				}else if(num<8){
					qu2Hits++;
				}else{
					qu3Hits++;
				}
			}
		});
	}
	missItem.fhMissHash.set(hits, 0);
	missItem.quMissHash.set("qu1", qu1Hits);
	missItem.quMissHash.set("qu2", qu2Hits);
	missItem.quMissHash.set("qu3", qu3Hits);
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
		html += '<td class="trw graychar333"><span class="">'+num+'</span></td>';
	});
	NUMS.each(function(ball){
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = 'tdjiange01';
		var ballHtml = (miss == 0) ? '<span class="ballclickKeno">' + ball + '</span>' : miss;
		if(miss>=100){
			style=STYLE;
		}else{
			style="";
		}
//		html += '<td class="tdjiange01" '+style+'>' + ballHtml + '</td>';
		if(missItem.result.nums.indexOf(ball)>=0){
			if(missItem.prevMissItem!=null&&missItem.prevMissItem.result.nums.indexOf(ball)>=0){
				html += '<td class="tdjiange01"><span class="ballclickKeno">' + ball + '</span></td>';
			}else{
				html += '<td class="graychar333" style="font-weight:bold;">' + ball + '</td>';
			}
		}else{
			html += '<td class="tdjiange01">' + ball + '</td>';
		}
	});
	var hits=0;
	for(var i=0;i<=5;i++){
		var miss = missItem.fhMissHash.get(i);
		var classStr='';
		if(miss==0){
			hits=i;
		}
		if(i==0){
			classStr += ' b1redpx';
		}		
		var ballHtml = (miss == 0) ? '<span class="reddot" _cancas="cancas" _group="fh"></span>' : miss;
		html += '<td class="'+classStr+'">' + ballHtml + '</td>';
	}
	var miss = missItem.quMissHash.get('qu1');
	if(miss>0){
		html += '<td class="trw graychar333"><span class="redredbchar">'+miss+'</span></td>';
	}else{
		html += '<td class="trw graychar333"><span class="">'+0+'</span></td>';
	}
	var miss = missItem.quMissHash.get('qu2');
	if(miss>0){
		html += '<td class="trw graychar333"><span class="redredbchar">'+miss+'</span></td>';
	}else{
		html += '<td class="trw graychar333"><span class="">'+0+'</span></td>';
	}
	var miss = missItem.quMissHash.get('qu3');
	if(miss>0){
		html += '<td class="trw graychar333"><span class="redredbchar">'+miss+'</span></td>';
	}else{
		html += '<td class="trw graychar333"><span class="">'+0+'</span></td>';
	}
	html += '<td class="trw graychar333"><span class="">'+hits+'</span></td>';
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
	for(var i=0;i<5;i++){
		html += '<td></td>';
	}
	HashNameArr.each(function(hashName, index) {
		if(hashName=='redMissHash'){
			NUMS.each(function(num){
				var miss = missItem[hashName].get(num);
				html += '<td>' + miss + '</td>';
			});
		}else{
			for(var i=0;i<=5;i++){
				var miss = missItem[hashName].get(i);
				html += '<td>' + miss + '</td>';
			}
		}
	});
	for(var i=0;i<4;i++){
		html += '<td></td>';
	}
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
			fhMissHash : new Hash()
	};
	var outMissItem = {
			redMissHash : new Hash(),
			fhMissHash : new Hash()
	};
	var lcMissItem = {
			redMissHash : new Hash(),
			fhMissHash : new Hash()
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
		
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
			redMissHash : new Hash(),
			fhMissHash : new Hash()
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
	for ( var i = 0; i < 1; i++) {
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
	var data = el11to5Num;
	for ( var issue in data) {
		HISTORY_MISS_HASH.set(issue, buildHistoryMissItem(issue, data[issue]));
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
		numberHash:new Hash(),
		jiNumHash : new Hash(),
		oNumHash : new Hash(),
		xiaoNumHash : new Hash(),
		daNumHash : new Hash(),
		zhiNumHash : new Hash(),
		heNumHash : new Hash()
	};
	for ( var ball in missObj.num) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.numberHash.set(ballNum, missObj.num[ball]);
	}
	for ( var ball in missObj.jnums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.jiNumHash.set(ballNum, missObj.jnums[ball]);
	}
	for ( var ball in missObj.onums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.oNumHash.set(ballNum, missObj.onums[ball]);
	}
	for ( var ball in missObj.xnums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.xiaoNumHash.set(ballNum, missObj.xnums[ball]);
	}
	for ( var ball in missObj.dnums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.daNumHash.set(ballNum, missObj.dnums[ball]);
	}
	for ( var ball in missObj.znums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.zhiNumHash.set(ballNum, missObj.znums[ball]);
	}
	for ( var ball in missObj.hnums) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.heNumHash.set(ballNum, missObj.hnums[ball]);
	}
	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	[ 'fh' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
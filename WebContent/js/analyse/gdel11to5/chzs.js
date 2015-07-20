NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09' ,'10', '11'];
COUNTS=['0','1', '2', '3', '4', '5'];
HashNameArr = [ 'jiNumHash','oNumHash', 'xiaoNumHash','daNumHash','zhiNumHash', 'heNumHash'];

TAB_HEADER = '<table width="980"  border="0"  cellpadding="0"  cellspacing="1"  class="zstablegraybg" ><tr class="trtitlebg" ><td width="75" rowspan="2" align="center" >@SELECT@</td><td colspan="5" rowspan="2" >开奖号码</td><td height="22" colspan="11" >开奖号码分布图</td><td colspan="6" >'+(TYPE_OBJ.jiNum==true?'奇数':'偶数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?jo='+(TYPE_OBJ.jiNum?'1':'0')+'">'+(TYPE_OBJ.jiNum?'偶数':'奇数')+'</a>)</td><td colspan="6" >'+(TYPE_OBJ.xiaoNum?'小数':'大数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?dx='+(TYPE_OBJ.xiaoNum?'1':'0')+'">'+(TYPE_OBJ.xiaoNum?'大数':'小数')+'</a>)</td><td colspan="6" >'+(TYPE_OBJ.zhiNum?'质数':'合数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?zh='+(TYPE_OBJ.zhiNum?'1':'0')+'">'+(TYPE_OBJ.zhiNum?'合数':'质数')+'</a>)</td></tr><tr class="zsttrpinklig" ><td width="30" >01</td><td width="30" >02</td><td width="30" >03</td><td width="30" >04</td><td width="30" >05</td><td width="30" >06</td><td width="30" >07</td><td width="30" >08</td><td width="30" >09</td><td width="30" >10</td><td width="30" >11</td><td width="22" >0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td><td width="22" >5</td><td width="22" >0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td><td width="22" >5</td><td width="22" >0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td><td width="22" >5</td></tr>';
TAB_FOOTER = '<tr  class="zsttrpinklig" ><td rowspan="2" align="center" class="trtitlebg" >@SELECT@</td><td colspan="5" rowspan="2" class="trtitlebg" >开奖号码</td><td >01</td><td >02</td><td >03</td><td >04</td><td >05</td><td >06</td><td >07</td><td >08</td><td >09</td><td >10</td><td >11</td><td >0</td><td >1</td><td >2</td><td >3</td><td >4</td><td >5</td><td >0</td><td >1</td><td >2</td><td >3</td><td >4</td><td >5</td><td >0</td><td >1</td><td >2</td><td >3</td><td >4</td><td >5</td></tr><tr class="trtitlebg" ><td height="22" colspan="11" >开奖号码分布图</td><td colspan="6" >'+(TYPE_OBJ.jiNum==true?'奇数':'偶数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?jo='+(TYPE_OBJ.jiNum?'1':'0')+'">'+(TYPE_OBJ.jiNum?'偶数':'奇数')+'</a>)</td><td colspan="6" >'+(TYPE_OBJ.xiaoNum?'小数':'大数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?dx='+(TYPE_OBJ.xiaoNum?'1':'0')+'">'+(TYPE_OBJ.xiaoNum?'大数':'小数')+'</a>)</td><td colspan="6" >'+(TYPE_OBJ.zhiNum?'质数':'合数')+'个数遗漏(<a href="'+BASESITE+'/el11to5/analyse.action?zh='+(TYPE_OBJ.zhiNum?'1':'0')+'">'+(TYPE_OBJ.zhiNum?'合数':'质数')+'</a>)</td></tr></table>';
MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">0</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">1</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">2</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">3</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">4</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">5</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">0</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">1</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">2</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">3</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">4</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">5</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">0</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">1</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">2</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">3</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">4</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">5</a></td></tr>';

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
		result : result
		
	};
	var tempJiNum=0;
	var tempXiaoNum=0;
	var tempZhiNum=0;
	result.nums.each(function(num, index) {
		if(num%2!=0){
			tempJiNum +=1;
		}
		if(num<=5){
			tempXiaoNum+=1;
		}
		if(num==1||num==3||num==5||num==7||num==11){
			tempZhiNum+=1;
		}
	});

	if (prevMissItem != null) {
		
		missItem.numberHash = prevMissItem.numberHash.map(function(value) {
			return value + 1;
		});
		HashNameArr.each(function(hashName) {
			missItem[hashName] = prevMissItem[hashName].map(function(value) {
				return value + 1;
			});
		});
		
	} else {
		missItem.numberHash=new Hash();
		missItem.jiNumHash =new Hash();
		missItem.xiaoNumHash =new Hash();
		missItem.zhiNumHash =new Hash();
		missItem.oNumHash =new Hash();
		missItem.daNumHash =new Hash();
		missItem.heNumHash =new Hash();
		NUMS.each(function(num) {
			missItem.numberHash.set(num,1);
		});
		HashNameArr.each(function(hashName) {
			missItem[hashName] = new Hash();
			COUNTS.each(function(num) {
				missItem[hashName].set(num, 1);
			});
		});
	}
	
	result.nums.each(function(num, index) {
		missItem.numberHash.set(num, 0);
	});
	missItem.jiNumHash.set(tempJiNum,0);
	missItem.xiaoNumHash.set(tempXiaoNum,0);
	missItem.zhiNumHash.set(tempZhiNum,0);
	missItem.oNumHash.set(5-tempJiNum,0);
	missItem.daNumHash.set(5-tempXiaoNum,0);
	missItem.heNumHash.set(5-tempZhiNum,0);

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
	NUMS.each(function(num){
		var miss = missItem.numberHash.get(num);
		if(miss==0){
			html += '<td class="tdjiange01"><span class="ballclickKeno">'+num+'</span></td>';
		}else{
			html += '<td class="tdjiange01" >'+miss+'</td>';
		}
	});
	COUNTS.each(function(num){
		if(TYPE_OBJ.jiNum){
			var miss = missItem.jiNumHash.get(num);
		}else{
			var miss = missItem.oNumHash.get(num);
		}
		if(miss==0){
			html += '<td class="bluebg"><span _cancas="cancas" _group="jiNumHash">'+num+'</span></td>';
		}else{
			html += '<td>'+miss+'</td>';
		}
	});
	
	COUNTS.each(function(num){
		if(TYPE_OBJ.xiaoNum){
			var miss = missItem.xiaoNumHash.get(num);
		}else{
			var miss = missItem.daNumHash.get(num);
		}
		if(miss==0){
			html += '<td class="bgr1 tdjiange01"><span _cancas="cancas" _group="xiaoNumHash">'+num+'</span></td>';
		}else{
			html += '<td class="tdjiange01">'+num+'</td>';
		}
	});
	COUNTS.each(function(num){
		if(TYPE_OBJ.zhiNum){
			var miss = missItem.zhiNumHash.get(num);
		}else{
			var miss = missItem.heNumHash.get(num);
		}
		if(miss==0){
			html += '<td class="bluebg"><span _cancas="cancas" _group="zhiNumHash">'+num+'</span></td>';
		}else{
			html += '<td>'+num+'</td>';
		}
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
	for(var i=0;i<5;i++){
		html += '<td></td>';
	}

	var classArr = [ 'trchosegrayd', 'ballclickKeno', 'ballblue', 'ballclickKeno' ];
	NUMS.each(function(num) {
		var miss = missItem.numberHash.get(num);
		html += '<td>' + miss + '</td>';
	});
	HashNameArr.each(function(hashName, index) {
		var k = 0;
		COUNTS.each(function(num) {
			if(TYPE_OBJ.jiNum){
				if(hashName=='oNumHash'){
					return;
				}
			}else{
				if(hashName=='jiNumHash'){
					return;
				}
			}
			if(TYPE_OBJ.xiaoNum){
				if(hashName=='daNumHash'){
					return;
				}
			}else{
				if(hashName=='xiaoNumHash'){
					return;
				}
			}
			if(TYPE_OBJ.zhiNum){
				if(hashName=='heNumHash'){
					return;
				}
			}else{
				if(hashName=='zhiNumHash'){
					return;
				}
			}
			var miss = missItem[hashName].get(num);
			html += '<td>' + miss + '</td>';
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
		numberHash : new Hash(),
		jiNumHash : new Hash(),
		oNumHash : new Hash(),
		xiaoNumHash : new Hash(),
		daNumHash : new Hash(),
		zhiNumHash : new Hash(),
		heNumHash : new Hash()
	};
	var outMissItem = {
		numberHash : new Hash(),
		jiNumHash : new Hash(),
		oNumHash : new Hash(),
		xiaoNumHash : new Hash(),
		daNumHash : new Hash(),
		zhiNumHash : new Hash(),
		heNumHash : new Hash()
	};
	var lcMissItem = {
		numberHash : new Hash(),
		jiNumHash : new Hash(),
		oNumHash : new Hash(),
		xiaoNumHash : new Hash(),
		daNumHash : new Hash(),
		zhiNumHash : new Hash(),
		heNumHash : new Hash()
	};
	var lastMissItem = null;
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		//missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';
		missHTML += '<tr class="trw">' + missItem.html + '</tr>';

		missItem.numberHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.numberHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.numberHash.set(ball, miss);
			var outCount = outMissItem.numberHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.numberHash.set(ball, outCount);
		});
		
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
		/*
		missItem.jiNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.jiNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.jiNumHash.set(ball, miss);
			var outCount = outMissItem.jiNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.jiNumHash.set(ball, outCount);
		});
		missItem.oNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.oNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.oNumHash.set(ball, miss);
			var outCount = outMissItem.oNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.oNumHash.set(ball, outCount);
		});
		missItem.xiaoNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.xiaoNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.xiaoNumHash.set(ball, miss);
			var outCount = outMissItem.xiaoNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.xiaoNumHash.set(ball, outCount);
		});
		missItem.daNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.daNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.daNumHash.set(ball, miss);
			var outCount = outMissItem.daNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.daNumHash.set(ball, outCount);
		});
		missItem.zhiNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.zhiNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.zhiNumHash.set(ball, miss);
			var outCount = outMissItem.zhiNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.zhiNumHash.set(ball, outCount);
		});
		missItem.heNumHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.heNumHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.heNumHash.set(ball, miss);
			var outCount = outMissItem.heNumHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.heNumHash.set(ball, outCount);
		});
		*/
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		numberHash : new Hash(),
		jiNumHash : new Hash(),
		oNumHash : new Hash(),
		xiaoNumHash : new Hash(),
		daNumHash : new Hash(),
		zhiNumHash : new Hash(),
		heNumHash : new Hash()
	};
	outMissItem.numberHash.each(function(out, ball) {
		var avgMiss;
		if (out > 0) {
			avgMiss = Math.floor(issueSize / out);
		} else if (lastMissItem != null) {
			avgMiss = lastMissItem.numberHash.get(ball);
		} else {
			avgMiss = 0;
		}
		avgMissItem.numberHash.set(ball, avgMiss);
	});
	
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
	[ 'jiNumHash', 'xiaoNumHash', 'zhiNumHash' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
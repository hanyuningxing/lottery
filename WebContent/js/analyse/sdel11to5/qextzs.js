NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09' ,'10', '11'];

STATES=['大单', '大双', '小单', '小双', '0', '1', '2'];

STATES2=['大大', '大小', '小大', '小小', '单单', '单双', '双单', '双双'];

HashNameArr = [ 'firstNumHash','secondNumHash'];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"> <td rowspan="2" align="center">@SELECT@</td><td colspan="5" rowspan="2" >开奖号码</td><td colspan="4" >第一位大小单双</td><td colspan="3" >第一位012</td><td colspan="4" >第二位大小单双</td><td colspan="3" >第二位012</td><td colspan="4" >大小组合</td><td colspan="4" >单双组合</td></tr><tr class="zsttrpinklig"><td width="36" >大单</td><td width="36" >大双</td><td width="36">小单</td><td width="36">小双</td><td width="36">0</td><td width="36">1</td><td width="36">2</td><td width="36" >大单</td><td width="36" >大双</td><td width="36">小单</td><td width="36">小双</td><td width="36">0</td><td width="36">1</td><td width="36">2</td><td width="36" >大大</td><td width="36" >大小</td><td width="36">小大</td><td width="36">小小</td><td width="36">单单</td><td width="36">单双</td><td width="36">双单</td><td width="36">双双</td></tr>';

TAB_FOOTER = '<tr class="zsttrpinklig"><td rowspan="2" align="center" class="trtitlebg">@SELECT@</td><td colspan="5" rowspan="2" class="trtitlebg" >开奖号码</td><td width="36" >大单</td><td width="36" >大双</td><td width="36">小单</td><td width="36">小双</td><td width="36">0</td><td width="36">1</td><td width="36">2</td><td width="36" >大单</td><td width="36" >大双</td><td width="36">小单</td><td width="36">小双</td><td width="36">0</td><td width="36">1</td><td width="36">2</td><td width="36" >大大</td><td width="36" >大小</td><td width="36">小大</td><td width="36">小小</td><td width="36">单单</td><td width="36">单双</td><td width="36">双单</td><td width="36">双双</td></tr><tr class="trtitlebg"><td colspan="4" >第一位大小单双</td><td colspan="3" >第一位012</td><td colspan="4" >第二位大小单双</td><td colspan="3" >第二位012</td><td colspan="4" >大小组合</td><td colspan="4" >单双组合</td></tr></table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td></tr>';

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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4]],
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
		missItem.otherHash = prevMissItem.otherHash.map(function(value) {
			return value + 1;
		});
		
	} else {
		HashNameArr.each(function(hashName) {
			missItem[hashName] = new Hash();
			STATES.each(function(num) {
				missItem[hashName].set(num, 1);
			});
		});
		missItem.otherHash=new Hash();
		STATES2.each(function(num) {
			missItem.otherHash.set(num, 1);
		});
	}
	var _r1=result.nums[0];
	var _r2=result.nums[1];
	
	missItem.firstNumHash.set((_r1>5?'大':'小')+(_r1%2==0?'双':'单'),0);
	missItem.firstNumHash.set((_r1%3)+'',0);
	
	missItem.secondNumHash.set((_r2>5?'大':'小')+(_r2%2==0?'双':'单'),0);
	missItem.secondNumHash.set((_r2%3)+'',0);
	
	missItem.otherHash.set((_r1>5?'大':'小')+(_r2>5?'大':'小'),0);
	missItem.otherHash.set((_r1%2==0?'双':'单')+(_r2%2==0?'双':'单'),0);
	
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
	var classStr = '';
	missItem.result.nums.each(function(num,index){
		if(index<2){
			classStr='redredbchar';
		}else{
			classStr='';
		}
		html += '<td class="trw graychar333"><span class="'+classStr+'">'+num+'</span></td>';
	});
	var k = 0;
	HashNameArr.each(function(hashName, index) {
		STATES.each(function(num) {
			var miss = missItem[hashName].get(num);
			if(['0','1','2'].contains(num)){
				classStr='bluedot';
			}else{
				classStr='blackdot';
			}
			var ballStr;
			if (miss == 0) {
				ballStr = '<span class="'+classStr+'" _cancas="cancas" _group="' + hashName + classStr + '"></span>';
			} else {
				ballStr = miss;
			}
			html += '<td class="">' + ballStr + '</td>';
		});
	});
	STATES2.each(function(num) {
		var miss = missItem.otherHash.get(num);
		if(['大大','小小','小大','大小'].contains(num)){
			classStr='greendot';
		}else{
			classStr='reddot';
		}
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="'+classStr+'" _cancas="cancas" _group="otherHash' + classStr + '"></span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="">' + ballStr + '</td>';
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
	HashNameArr.each(function(hashName, index) {
		STATES.each(function(num) {
			var miss = missItem[hashName].get(num);
			var classStr = '';
			html += '<td class="' + classStr + '">' + miss + '</td>';
		});
	});
	STATES2.each(function(num) {
		var miss = missItem.otherHash.get(num);
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
		otherHash : new Hash(),
		firstNumHash : new Hash(),
		secondNumHash : new Hash()
	};
	var outMissItem = {
		otherHash : new Hash(),
		firstNumHash : new Hash(),
		secondNumHash : new Hash()
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
		missItem.otherHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.otherHash.get(ball);
			if (maxMiss == null || miss > maxMiss)
				maxMissItem.otherHash.set(ball, miss);

			var outCount = outMissItem.otherHash.get(ball);
			if (outCount == null)
				outCount = 0;
			if (miss == 0)
				outCount++;
			outMissItem.otherHash.set(ball, outCount);
		});
		
		lastMissItem = missItem;
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = {
		otherHash : new Hash(),
		firstNumHash : new Hash(),
		secondNumHash : new Hash()
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
	
	outMissItem.otherHash.each(function(out, ball) {
		var avgMiss;
		if (out > 0) {
			avgMiss = Math.floor(issueSize / out);
		} else if (lastMissItem != null) {
			avgMiss = lastMissItem.otherHash.get(ball);
		} else {
			avgMiss = 0;
		}
		avgMissItem.otherHash.set(ball, avgMiss);
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
	[ 'firstNumHashblackdot', 'firstNumHashbluedot', 'secondNumHashblackdot', 'secondNumHashbluedot', 'otherHashgreendot', 'otherHashreddot' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
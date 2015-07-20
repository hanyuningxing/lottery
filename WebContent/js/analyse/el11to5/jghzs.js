NUMS = [ '01-03', '02-04', '03-05', '04-06', '05-07', '06-08', '07-09', '08-10', '09-11'];
HashNameArr = [ 'ghHash','ghzHash','jiHash'];
COMPARE =['5:0','4:1','3:2','2:3','1:4','0:5'];
GESHU=['0','1','2','3','4','5'];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="75" rowspan="2" align="center">@SELECT@</td><td colspan="5" rowspan="2" >开奖号码</td><td colspan="9" >隔号走势</td><td height="22" colspan="5" >隔号码组数</td><td width="45" rowspan="2" >奇数组</td><td width="45" rowspan="2" >偶数组</td><td width="45" rowspan="2" >组数</td></tr><tr class="zsttrpinklig"><td width="45" >01-03</td><td width="45" >02-04</td><td width="45">03-05</td><td width="45" >04-06</td><td width="45" >05-07</td><td width="45" >06-08</td><td width="45" >07-09</td><td width="45" >08-10</td><td width="45" >09-11</td><td width="22" >0</td><td width="22" >1</td><td width="22" >2</td><td width="22" >3</td><td width="22" >4</td></tr>';

TAB_FOOTER = '</table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td  >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
 
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
		missItem.ghHash=prevMissItem.ghHash.map(function(value) {
			return value + 1;
		});
		missItem.ghzHash=prevMissItem.ghzHash.map(function(value) {
			return value + 1;
		});
		missItem.jiHash=prevMissItem.jiHash.map(function(value) {
			return value + 1;
		});
		missItem.oHash=prevMissItem.oHash.map(function(value) {
			return value + 1;
		});
		
	} else {
		missItem.ghHash=new Hash();
		missItem.ghzHash=new Hash();
		missItem.jiHash=new Hash();
		missItem.oHash=new Hash();
		
		NUMS.each(function(num){
			missItem.ghHash.set(num, 1);
		});
		
		for(var num=0;num<=4;num++){
			missItem.ghzHash.set(num, 1);
		}
		
		missItem.jiHash.set('ji',1);
		missItem.jiHash.set('o', 1);
	}
	var templhgs=0;
	for(var i=1;i<=9;i++){
		var _tempNum1=(i<10?("0"+i):(""+i));
		var _tempi=i+2;
		var _tempNum2=(_tempi<10?("0"+_tempi):(""+_tempi));
		if(result.nums.contains(_tempNum1)&&result.nums.contains(_tempNum2)){
			missItem.ghHash.set(NUMS[i-1], 0);
			templhgs++;
		}
	}
	missItem.ghzHash.set(templhgs, 0);
	
	if(templhgs%2==0){
		missItem.jiHash.set('o', 0);
	}else{
		missItem.jiHash.set('ji', 0);
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
	var html = '<td height="22" class="trw graychar333">' + issue + '</td>';
	missItem.result.nums.each(function(num){
		html += '<td class="trw graychar333"><span class="redredbchar">'+num+'</span></td>';
	});
	var k = 0;
	var templhstate='';
	NUMS.each(function(num){
		var miss = missItem.ghHash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bluebg';
			ballStr = '<span _cancas="cancas" _group="ghHash">'+num+'</span>';
			templhb=num;
			k++;
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	
	for(var num=0;num<=4;num++){
		var miss = missItem.ghzHash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bgr1 tdjiange01';
			ballStr = '<span _cancas="cancas" _group="ghzHash">'+num+'</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	}
	
	var miss = missItem.jiHash.get('ji');
	classStr='';
	if (miss == 0) {
		ballStr = '<span class="bluedot" _cancas="cancas" _group="jiHash"></span>';
	} else {
		ballStr = miss;
	}
	html += '<td class="' + classStr + '">' + ballStr + '</td>';
	
	var miss = missItem.jiHash.get('o');
	classStr='';
	if (miss == 0) {
		ballStr = '<span class="bluedot" _cancas="cancas" _group="jiHash"></span>';
	} else {
		ballStr = miss;
	}
	html += '<td class="' + classStr + '">' + ballStr + '</td>';
	

	html +='<td class="trw">'+k+'</td>';
	
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

	
	NUMS.each(function(num){
		var miss = missItem.ghHash.get(num);
		classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	
	for(var num=0;num<=4;num++){
		var miss = missItem.ghzHash.get(num);
		classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	}
	html += '<td></td>';
	html += '<td></td>';
	html += '<td></td>';
	
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
			ghHash : new Hash(),
			ghzHash : new Hash(),
			jiHash : new Hash(),
			oHash : new Hash()
	};
	var outMissItem = {
			ghHash : new Hash(),
			ghzHash : new Hash(),
			jiHash : new Hash(),
			oHash : new Hash()
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
			ghHash : new Hash(),
			ghzHash : new Hash(),
			jiHash : new Hash(),
			oHash : new Hash()
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
	[ 'ghzHash', 'jiHash','oHash' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
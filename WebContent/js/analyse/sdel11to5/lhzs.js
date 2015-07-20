NUMS1 = [ '01-02', '02-03', '03-04', '04-05', '05-06', '06-07', '07-08', '08-09', '09-10', '10-11'];
NUMS2 =[ '123','234','345','456','567','678','789','8910','91011'];
NUMS3 =[ '1234','2345','3456','4567','5678','6789','78910','891011'];

HashNameArr = [ 'lh2Hash','lh3Hash','lh4Hash'];
COMPARE =['5:0','4:1','3:2','2:3','1:4','0:5'];
GESHU=['0','1','2','3','4','5'];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="75" rowspan="2" align="center">@SELECT@</td><td colspan="5" rowspan="2" >开奖号码</td><td colspan="10" >二连号</td><td width="95" colspan="9">三连号</td><td height="22" colspan="8" >四连号</td></tr><tr class="zsttrpinklig"><td width="30" >01-02</td><td width="30" >02-03</td><td width="30">03-04</td><td width="30" >04-05</td><td width="30" >05-06</td><td width="30" >06-07</td><td width="30" >07-08</td><td width="30" >08-09</td><td width="30" >09-10</td><td width="30" >10-11</td><td width="30" class="b1redpx">123</td><td width="30" >234</td><td width="30" >345</td><td width="30" >456</td><td width="30" >567</td><td width="30" >678</td><td width="30" >789</td><td width="30" >8910</td><td width="30" >91011</td><td width="30" class="b1redpx">1234</td><td width="30" >2345</td><td width="30" >3456</td><td width="30" >4567</td><td width="30" >5678</td><td width="30" >6789</td><td width="30" >78910</td><td width="30" >891011</td></tr>';

TAB_FOOTER = '</table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td  >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td  >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
 
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
	var lhFlags=[0,0,0,0,0];
	
	if (prevMissItem != null) {
		missItem.lh2Hash=prevMissItem.lh2Hash.map(function(value) {
			return value + 1;
		});
		missItem.lh3Hash=prevMissItem.lh3Hash.map(function(value) {
			return value + 1;
		});
		missItem.lh4Hash=prevMissItem.lh4Hash.map(function(value) {
			return value + 1;
		});
		
	} else {
		missItem.lh2Hash=new Hash();
		missItem.lh3Hash=new Hash();
		missItem.lh4Hash=new Hash();
		
		NUMS1.each(function(num){
			missItem.lh2Hash.set(num, 1);
		});
		NUMS2.each(function(num){
			missItem.lh3Hash.set(num, 1);
		});
		NUMS3.each(function(num){
			missItem.lh4Hash.set(num, 1);
		});
	}
	for(var i=1;i<=10;i++){
		var _tempNum1=(i<10?("0"+i):(""+i));
		var _tempi=i+1;
		var _tempNum2=(_tempi<10?("0"+_tempi):(""+_tempi));
		_tempi=i+2;
		var _tempNum3=(_tempi<10?("0"+_tempi):(""+_tempi));
		_tempi=i+3;
		var _tempNum4=(_tempi<10?("0"+_tempi):(""+_tempi));
		
		if(result.nums.contains(_tempNum1)&&result.nums.contains(_tempNum2)){
			missItem.lh2Hash.set(NUMS1[i-1], 0);
			if(result.nums.contains(_tempNum3)){
				missItem.lh3Hash.set(NUMS2[i-1], 0);
				if(result.nums.contains(_tempNum4)){
					missItem.lh4Hash.set(NUMS3[i-1], 0);
				}
			}
		}
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
	NUMS1.each(function(num,index){
		var miss = missItem.lh2Hash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bluebg';
			ballStr = '<span _cancas="cancas" _group="lhHash">'+num+'</span>';
			templhb=num;
		} else {
			ballStr = miss;
		}
		if(index==0){
			classStr += ' b1redpx';
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	NUMS2.each(function(num,index){
		var miss = missItem.lh3Hash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bgr1 tdjiange01';
			ballStr = '<span _cancas="cancas" _group="ghHash">'+num+'</span>';
			templhb=num;
		} else {
			ballStr = miss;
		}
		if(index==0){
			classStr += ' b1redpx';
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	NUMS3.each(function(num,index){
		var miss = missItem.lh4Hash.get(num);
		classStr='';
		if(index==0){
			classStr += ' b1redpx';
		}
		if (miss == 0) {
			classStr='bluebg';
			ballStr = '<span _cancas="cancas" _group="ghHash">'+num+'</span>';
			templhb=num;
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
	for(var i=0;i<5;i++){
		html += '<td></td>';
	}
	
	NUMS1.each(function(num){
		var miss = missItem.lh2Hash.get(num);
		classStr='';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	NUMS2.each(function(num){
		var miss = missItem.lh3Hash.get(num);
		classStr='';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	NUMS3.each(function(num){
		var miss = missItem.lh4Hash.get(num);
		classStr='';
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
			lh2Hash : new Hash(),
			lh3Hash : new Hash(),
			lh4Hash : new Hash()
	};
	var outMissItem = {
			lh2Hash : new Hash(),
			lh3Hash : new Hash(),
			lh4Hash : new Hash()
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
			lh2Hash : new Hash(),
			lh3Hash : new Hash(),
			lh4Hash : new Hash()
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
//	[ 'lh3Hash', 'lh2Hash','lh4Hash' ].each(function(hashName, index) {
//
//		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
//		var myfun = function() {
//			drawCancas(tagArr1, 1);
//		};
//		myfun.delay(10);
//	});
}
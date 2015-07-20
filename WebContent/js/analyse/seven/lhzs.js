NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30' ];

HashNameArr = [ 'lhHash','lhbHash','lhgsHash','flhgsHash'];
COMPARE =['7:0','6:1','5:2','4:3','3:4','2:5','1:6','0:7'];
GESHU=['0','1','2','3','4','5','6','7'];

TAB_HEADER = '<table width="1080" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="65" rowspan="2" align="center">@SELECT@</td><td rowspan="2" width="150" align="center">开奖号码</td><td colspan="14" >连号形态</td><td height="22" colspan="8" >连号比</td><td width="35" rowspan="2" align="center">比例</td><td height="22" colspan="8" >连个数</td><td height="22" colspan="8" >－个数</td></tr><tr class="zsttrpinklig"><td width="25"  colspan="2">1位</td><td width="25"  colspan="2">2位	</td><td width="25"  colspan="2">3位	</td><td width="25"  colspan="2">4位</td><td width="25"  colspan="2">5位</td><td width="25"  colspan="2">6位</td><td width="25"  colspan="2">7位</td><td width="28" class="boldchar">7:0</td><td width="28" class="boldchar">6:1</td><td width="28" class="boldchar">5:2</td><td width="28" class="boldchar">4:3</td><td width="28" class="boldchar">3:4</td><td width="28" class="boldchar">2:5</td><td width="28" class="boldchar">1:6</td><td width="28" class="boldchar">0:7</td><td width="20" >0</td><td width="20" >1</td><td width="20" >2</td><td width="20" >3</td><td width="20" >4</td><td width="20" >5</td><td width="20" >6</td><td width="20" >7</td><td width="20" >0</td><td width="20" >1</td><td width="20" >2</td><td width="20" >3</td><td width="20" >4</td><td width="20" >5</td><td width="20" >6</td><td width="20" >7</td></tr>';

TAB_FOOTER = '</table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td  >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td  >&nbsp;</td><td >&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>';
 



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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4], numArr[5], numArr[6]],
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
	var lhFlags=[0,0,0,0,0,0,0];
	
	result.nums.each(function(num, index) {
		var preNum=num.toInt()+1;
		if(preNum>0&&preNum<=30){
			if(preNum<10){
				preNum="0"+preNum;
			}else{
				preNum=""+preNum;
			}
			if(result.nums.contains(preNum)){
				lhFlags[index]=1;
			}
		}
		var nextNum=num.toInt()-1;
		if(nextNum>0&&nextNum<=30){
			if(nextNum<10){
				nextNum="0"+nextNum;
			}else{
				nextNum=""+nextNum;
			}
			if(result.nums.contains(nextNum)){
				lhFlags[index]=1;
			}
		}
	});
	//alert(lhFlags);
	if (prevMissItem != null) {
		missItem.lhHash=prevMissItem.lhHash.map(function(value) {
			return value + 1;
		});
		missItem.lhbHash=prevMissItem.lhbHash.map(function(value) {
			return value + 1;
		});
		missItem.lhgsHash=prevMissItem.lhgsHash.map(function(value) {
			return value + 1;
		});
		missItem.flhgsHash=prevMissItem.flhgsHash.map(function(value) {
			return value + 1;
		});
		
	} else {
		missItem.lhHash=new Hash();
		missItem.lhbHash=new Hash();
		missItem.lhgsHash=new Hash();
		missItem.flhgsHash=new Hash();
		
		for(var num=0;num<=6;num++){
			missItem.lhHash.set(num, 1);
		}
		COMPARE.each(function(num){
			missItem.lhbHash.set(num, 1);
		});
		
		GESHU.each(function(num){
			missItem.lhgsHash.set(num, 1);
		});
		
		GESHU.each(function(num){
			missItem.flhgsHash.set(num, 1);
		});
	}
	var templhgs=0;
	lhFlags.each(function(num, index) {
		if(num==1){
			missItem.lhHash.set(index, 0);
			templhgs +=1;
		}
	});
	missItem.lhbHash.set(COMPARE[7-templhgs], 0);
	missItem.lhgsHash.set(templhgs, 0);
	missItem.flhgsHash.set(7-templhgs, 0);

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
	html += '<td class="trw graychar333">';
	missItem.result.nums.each(function(num,index){
		html += num;
		if(index < 6){
			html +=' ';
		}
	});
	html += ' + '+ missItem.result.spNum;
	html += '</td>';
	var k = 0;
	var templhstate='';
	for(var num=0;num<=6;num++){
		var miss = missItem.lhHash.get(num);
		if(k%3==0){
			classStr='bluebg';
		}else if(k%3==1){
			classStr='bgr1';
		}else{
			classStr='bggr1';
		}
		if (miss == 0) {
			ballStr = '<span _cancas="cancas" _group="lhHash">连</span>';
			templhstate+='连';
			html += '<td width="20" class="' + classStr + '"><span _cancas="cancas" _group="lhHash'+num+'">连</span></td>';
			html += '<td width="20"></td>';
		} else {
			templhstate+='□';
			html += '<td width="20"></td>';
			html += '<td width="20" class="' + classStr + '"><span _cancas="cancas" _group="lhHash'+num+'">□</span></td>';
		}
		k++;
	}
	//html +='<td class="trw">'+templhstate+'</td>';
	
	var templhb='';
	COMPARE.each(function(num){
		var miss = missItem.lhbHash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bgr1';
			ballStr = '<span _cancas="cancas" _group="lhbHash">'+num+'</span>';
			templhb=num;
			templhstate+='连';
		} else {
			ballStr = miss;
			templhstate+='□';
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	html +='<td class="trw">'+templhb+'</td>';
	
	GESHU.each(function(num){
		var miss = missItem.lhgsHash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bluebg';
			ballStr = '<span _cancas="cancas" _group="lhgsHash">'+num+'</span>';
			templhstate+='连';
		} else {
			ballStr = miss;
			templhstate+='□';
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	});
	
	GESHU.each(function(num){
		var miss = missItem.flhgsHash.get(num);
		classStr='';
		if (miss == 0) {
			classStr='bggr1';
			ballStr = '<span _cancas="cancas" _group="flhgsHash">'+num+'</span>';
			templhstate+='连';
		} else {
			ballStr = miss;
			templhstate+='□';
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
	html += '<td></td>';
	for(var num=0;num<=13;num++){
		var miss = missItem.lhHash.get(num);
		classStr = '';
		html += '<td></td>';
	}
	
	COMPARE.each(function(num){
		var miss = missItem.lhbHash.get(num);
		classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	html += '<td></td>';
	
	GESHU.each(function(num){
		var miss = missItem.lhgsHash.get(num);
		classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	});
	
	GESHU.each(function(num){
		var miss = missItem.flhgsHash.get(num);
		classStr = '';
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
	'lhHash','lhbHash','lhgsHash','flhgsHash'
	var maxMissItem = {
			lhHash : new Hash(),
			lhbHash : new Hash(),
			lhgsHash : new Hash(),
			flhgsHash : new Hash()
	};
	var outMissItem = {
			lhHash : new Hash(),
			lhbHash : new Hash(),
			lhgsHash : new Hash(),
			flhgsHash : new Hash()
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
			lhHash : new Hash(),
			lhbHash : new Hash(),
			lhgsHash : new Hash(),
			flhgsHash : new Hash()
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
	[ 'lhHash0','lhHash1','lhHash2','lhHash3','lhHash4','lhHash5','lhHash6', 'lhbHash', 'lhgsHash','flhgsHash' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
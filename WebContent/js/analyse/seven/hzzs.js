NUMS = [ '<60', '60-70', '71-80', '81-90', '91-100','101-110', '111-120', '121-130', '131-140', '>140'];

HashNameArr = [ 'daxiaoHash', 'jiouHash', 'chu3Hash' ];
TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="75" rowspan="2" align="center">@SELECT@</td><td colspan="8" rowspan="2" >开奖号码</td><td height="22" colspan="10" >和值</td></td><td width="55" rowspan="1" colspan="2">大小</td><td width="55" rowspan="1" colspan="2">奇偶</td><td rowspan="1" colspan="3">除三</td><td rowspan="2"  width="45"> 振幅</td></tr><tr class="zsttrpinklig"><td width="35" ><60</td><td width="35" >60-70</td><td width="35">71-80</td><td width="35" >81-90</td><td width="35" >91-100</td><td width="35" >101-110</td><td width="35" >111-120</td><td width="35" >121-130</td><td width="35" >131-140</td><td width="35" >>140</td><td width="22">大</td><td width="22">小</td><td width="22">奇</td><td width="22">偶</td><td>0</td><td>1</td><td>2</td></tr>';
TAB_FOOTER = '<tr class=" zsttrpinklig "><td rowspan="2" class="trtitlebg">@SELECT@</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td width="35" ><60</td><td width="35" >60-70</td><td width="35">71-80</td><td width="35" >81-90</td><td width="35" >91-100</td><td width="35" >101-110</td><td width="35" >111-120</td><td width="35" >121-130</td><td width="35" >131-140</td><td width="35" >>140</td><td width="55" rowspan="1" colspan="2">大小</td><td width="55" rowspan="1" colspan="2">奇偶</td><td rowspan="1" colspan="3">除三</td><td > 振幅</td></tr></table>';
//MONI_SELECT = '<tr class="trw"><td class="graychar333">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td width="45" >15</td><td width="45" > 20%	</td><td width="45">30%	</td><td width="45" >40%	</td><td width="45" >50%	</td><td width="45" >60%	</td><td width="45" >70%	</td><td width="45" >80%</td><td width="45" >90%	</td><td width="45" >100</td><td width="55" rowspan="2" >和值<br /></td><td width="55" rowspan="2" >大小<br /></td><td width="55" >奇偶</td><td >质合</td><td >除三</td><td > 振幅</td></tr>';



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
		zf:-1
	};
	if (prevMissItem != null) {
		missItem.redMissHash = prevMissItem.redMissHash.map(function(value) {
			return value + 1;
		});
		missItem.daxiaoHash = prevMissItem.daxiaoHash.map(function(value) {
			return value + 1;
		});
		missItem.zf=Math.abs(missItem.result.sum-prevMissItem.result.sum);
	} else {
		missItem.redMissHash = new Hash();
		NUMS.each(function(num) {
			missItem.redMissHash.set(num, 1);
		});
		missItem.daxiaoHash = new Hash();
		missItem.daxiaoHash.set("da",1);
		missItem.daxiaoHash.set("xiao",1);
		missItem.daxiaoHash.set("ji",1);
		missItem.daxiaoHash.set("ou",1);
		missItem.daxiaoHash.set("c0",1);
		missItem.daxiaoHash.set("c1",1);
		missItem.daxiaoHash.set("c2",1);
	}
	var index=0;
	if(result.sum<60){
		index=0;
	}else if(result.sum<=70){
		index=1;
	}else if(result.sum>140){
		index=9;
	}else{
		index=Math.ceil(result.sum/10)-6;
	}	
	missItem.redMissHash.set(NUMS[index], 0);
	if(result.sum>100){
		missItem.daxiaoHash.set("da",0);
	}else{
		missItem.daxiaoHash.set("xiao",0);
	}
	if(result.sum%2==0){
		missItem.daxiaoHash.set("ji",0);
	}else{
		missItem.daxiaoHash.set("ou",0);
	}
	missItem.daxiaoHash.set("c"+(result.sum%3),0);
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
	
	missItem.result.nums.each(function(num){
		html += '<td class="trw graychar333"><span class="redredbchar">'+num+'</span></td>';
	});
	NUMS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		var classHtml = 'tdjiange01';
		if(miss==0){
		 classHtml = 'bgr1';
		}
		var ballHtml = (miss == 0) ? '<span _cancas="cancas" _group="hz">'+missItem.result.sum+'</span>' : miss;
		if(miss>100){
			style=STYLE;
		}else{
			style="";
		}
		html += '<td class="'+classHtml+'">' + ballHtml + '</td>';
	});
	if(missItem.daxiaoHash.get('da')==0){
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="daxiao" class="greendot"></span></td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('xiao')+'</td>';
	}else{
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('da')+'</td>';
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="daxiao" class="reddot"></span></td>';
	}
	if(missItem.daxiaoHash.get('ji')==0){
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="jiou" class="greendot"></span></td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('ou')+'</td>';
	}else{
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('ji')+'</td>';
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="jiou" class="blackdot"></span></td>';
	}
	if(missItem.daxiaoHash.get('c0')==0){
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="c012" class="reddot"></span></td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c1')+'</td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c2')+'</td>';
	}else if(missItem.daxiaoHash.get('c1')==0){
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c0')+'</td>';
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="c012" class="greendot"></span></td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c2')+'</td>';
	}else{
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c0')+'</td>';
		html += '<td class="trw graychar333">'+missItem.daxiaoHash.get('c1')+'</td>';
		html += '<td class="trw graychar333"><span _cancas="cancas" _group="c012" class="blackdot"></span></td>';
	}
	html += '<td class="trw graychar333">' + (missItem.zf>=0?missItem.zf:'-') + '</td>';
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
	for(var i=0;i<8;i++){
		html += '<td></td>';
	}
	NUMS.each(function(ball) {
		var miss = missItem.redMissHash.get(ball);
		i++;
		html += '<td ' +style+'>' + miss + '</td>';
	});
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
	html += '<td class="trw graychar333"></td>';
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
		//moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">' + MONI_SELECT + '</tr>';
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
	[ 'hz', 'daxiao', 'jiou',"c012" ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
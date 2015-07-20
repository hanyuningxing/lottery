KD_ARR = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ];
HASH_NAME_ARR = [ 'maxKdHash', 'bsKdHash', 'sgKdHash', 'bgKdHash' ];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg"><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td rowspan="2" class="b1redpx" >试机</td><td rowspan="2" class="b1redpx" > 出号</td><td colspan="10" class="b1redpx">最大跨度</td><td colspan="10" class="b1redpx">百十跨度</td><td colspan="10" class="b1redpx">十个跨度 </td><td colspan="10" class="b1redpx">百个跨度</td><td width="30" rowspan="2" class="b1redpx">和值</td></tr><tr class="zsttrpinklig"><td width="18" class="b1redpx">0</td><td width="18" >1</td><td width="18" >2</td><td width="18">3</td><td width="18" > 4</td><td width="18" >5</td><td width="18" >6</td><td width="18" >7</td><td width="18" >8</td><td width="18" >9</td><td width="18" class="b1redpx">0</td><td width="18">1</td><td width="18" >2</td><td width="18">3</td><td width="18" > 4</td><td width="18" >5</td><td width="18" >6</td><td width="18" >7</td><td width="18" >8</td><td width="18" >9</td><td width="18" class="b1redpx">0</td><td width="18">1</td><td width="18" >2</td><td width="18">3</td><td width="18" > 4</td><td width="18" >5</td><td width="18" >6</td><td width="18" >7</td><td width="18" >8</td><td width="18" >9</td><td width="18" class="b1redpx">0</td><td width="18" >1</td><td width="18" >2</td><td width="18">3</td><td width="18" > 4</td><td width="18" >5</td><td width="18" >6</td><td width="18" >7</td><td width="18" >8</td><td width="18" >9</td></tr>';
TAB_FOOTER = '<tr class="trtitlebg"><td rowspan="2" class="zsttrpink">@SELECT@</td><td rowspan="2" class="b1redpx zsttrpink" >试机</td><td rowspan="2" class="b1redpx zsttrpink" >出号</td><td class="b1redpx">0</td><td >1</td><td >2</td><td>3</td><td > 4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td class="b1redpx">0</td><td>1</td><td >2</td><td>3</td><td > 4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td class="b1redpx">0</td><td>1</td><td >2</td><td>3</td><td > 4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td class="b1redpx">0</td><td >1</td><td >2</td><td>3</td><td > 4</td><td >5</td><td >6</td><td >7</td><td >8</td><td >9</td><td rowspan="2" class="b1redpx trtitlebg">和值</td></tr><tr class="trtitlebg"><td colspan="10"  class="b1redpx">最大跨度</td><td colspan="10"  class="b1redpx">百十跨度</td><td colspan="10"  class="b1redpx">十个跨度 </td><td colspan="10"  class="b1redpx">百个跨度</td></tr></table>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td><td  class="b1redpx">&nbsp;</td><td  class="b1redpx">&nbsp;</td><td  class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false">0</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">1</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">2</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">3</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">4</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">5</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">6</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">7</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">8</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">9</a></td><td  class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false">0</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">1</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">2</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">3</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">4</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">5</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">6</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">7</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">8</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">9</a></td><td  class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false">0</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">1</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">2</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">3</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">4</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">5</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">6</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">7</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">8</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">9</a></td><td  class="b1redpx"><a href="#" class="gball" onclick="ball_click(this);return false">0</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">1</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">2</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">3</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">4</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">5</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">6</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">7</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">8</a></td><td  ><a href="#" class="gball" onclick="ball_click(this);return false">9</a></td><td  class="b1redpx">&nbsp;</td>';

function getDrawedData() {
	return pl3_ball_data;
}

/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 */
function genResult(numStr) {
	var numArr = numStr.split(/[^\d]+/);
	var result = {
		nums : [ numArr[0], numArr[1], numArr[2] ],
		sum : 0,
		kds : [ 0, 0, 0 ],
		max_kd : 0
	};
	result.nums.each(function(num) {
		var numInt = num.toInt();
		result.sum += numInt;
	});
	var bInt = result.nums[0].toInt();
	var sInt = result.nums[1].toInt();
	var gInt = result.nums[2].toInt();
	var bs = Math.abs(bInt - sInt);
	var sg = Math.abs(sInt - gInt);
	var bg = Math.abs(bInt - gInt);

	result.kds[0] = bs;
	result.kds[1] = sg;
	result.kds[2] = bg;
	
	result.kds.each(function(kd) {
		if (kd > result.max_kd)
			result.max_kd = kd;
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
		HASH_NAME_ARR.each(function(hashName) {
			missItem[hashName] = prevMissItem[hashName].map(function(value) {
				return value + 1;
			});
		});
	} else {
		HASH_NAME_ARR.each(function(hashName) {
			missItem[hashName] = new Hash();
			KD_ARR.each(function(key) {
				missItem[hashName].set(key, 1);
			});
		});
	}
	missItem.maxKdHash.set(result.max_kd.toString(), 0);
	missItem.bsKdHash.set(result.kds[0].toString(), 0);
	missItem.sgKdHash.set(result.kds[1].toString(), 0);
	missItem.bgKdHash.set(result.kds[2].toString(), 0);

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
	html += '<td class="b1redpx graychar333"></td>';
	html += '<td class="b1redpx graychar333"><span class="redredbchar">' + missItem.result.nums.join() + '</span></td>';

	HASH_NAME_ARR.each(function(hashName, hashIndex) {
		var className = (hashIndex % 2 == 0) ? 'ballblue' : 'ballredone';
		KD_ARR.each(function(key, index) {
			var miss = missItem[hashName].get(key);
			var classStr = '';
			if (index == 0)
				classStr += 'b1redpx ';
			var ballStr;
			if (miss == 0) {
				ballStr = '<span class="' + className + '" _cancas="cancas" _group="' + hashName + '">' + key
						+ '</span>';
			} else {
				ballStr = miss;
			}
			html += '<td class="' + classStr + '">' + ballStr + '</td>';
		});
	});

	html += '<td class="b1redpx graychar333">' + missItem.result.sum + '</td>';
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
	var missHTML = '';
	var index = 0;
	var itemIandleFn = function(issue, missItem) {
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var moniHTML = '';
	for ( var i = 0; i < 1; i++) {
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + MONI_SELECT + '</tr>';
	}

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML
			+ TAB_FOOTER.replace(/@SELECT@/, selectHTML);
	return html;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var kdData = pl3_kd;
	for ( var issue in kdData) {
		var missObj = {
			kdMissObj : kdData[issue]
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
		maxKdHash : new Hash(missObj.kdMissObj.b),
		bsKdHash : new Hash(missObj.kdMissObj.bs),
		sgKdHash : new Hash(missObj.kdMissObj.sg),
		bgKdHash : new Hash(missObj.kdMissObj.bg)
	};

	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	HASH_NAME_ARR.each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
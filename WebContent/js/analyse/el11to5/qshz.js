NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09' ,'10', '11'];
HashNameArr = [ 'hezhiHash','heweiHash','yushuHash'];

COMPARENUM=['03','12','21','30'];

TAB_HEADER = '<table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="75" rowspan="2" align="center">@SELECT@</td><td colspan="3" rowspan="2" >开奖号码</td><td height="22" colspan="25" >和值</td><td colspan="10" >和尾</td><td colspan="3" >除3余数</td></tr><tr class="zsttrpinklig"><td width="20" >6</td><td width="20" >7</td><td width="20">8</td><td width="20" >9</td><td width="20" >10</td><td width="20" >11</td><td width="20" >12</td><td width="20" >13</td><td width="20" >14</td><td width="20" >15</td><td width="20" >16</td><td width="20" >17</td><td width="20" >18</td><td width="20" >19</td><td width="20">20</td><td width="20" >21</td><td width="20" >22</td><td width="20" >23</td><td width="20" >24</td><td width="20" >25</td><td width="20">26</td><td width="20" >27</td><td width="20" >28</td><td width="20" >29</td><td width="20" >30</td><td width="18" >0</td><td width="18">1</td><td width="18" >2</td><td width="18" >3</td><td width="18" >4</td><td width="18">5</td><td width="18" >6</td><td width="18" >7</td><td width="18" >8</td><td width="18">9</td><td width="18" >0</td><td width="18" >1</td><td width="18" >2</td></tr>';
TAB_FOOTER = '<tr class="zsttrpinklig"><td rowspan="2" align="center" class="trtitlebg">@SELECT@</td><td colspan="3" rowspan="2" class="trtitlebg" >开奖号码</td><td >6</td><td >7</td><td>8</td><td >9</td><td >10</td><td >11</td><td >12</td><td >13</td><td >14</td><td >15</td><td >16</td><td >17</td><td >18</td><td >19</td><td>20</td><td >21</td><td >22</td><td >23</td><td >24</td><td >25</td><td>26</td><td >27</td><td >28</td><td >29</td><td >30</td><td >0</td><td>1</td><td >2</td><td >3</td><td >4</td><td>5</td><td >6</td><td >7</td><td >8</td><td>9</td><td >0</td><td >1</td><td >2</td></tr><tr class="trtitlebg"><td height="22" colspan="25" >和值</td><td colspan="10" >和尾</td><td colspan="3" >除3余数</td></tr></table>';


MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig"  >&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td class="zsttrpinklig">&nbsp;</td><td><a onclick="ball_click(this);return false;" class="gball" href="#">6</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">7</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">8</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">9</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">12</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">13</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">14</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">15</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">16</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">17</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">18</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">19</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">20</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">21</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">22</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">23</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">24</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">25</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">26</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">27</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">28</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">29</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">30</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">0</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">1</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">2</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">3</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">4</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">5</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">6</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">7</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">8</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">9</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">0</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">1</a></td><td><a onclick="ball_click(this);return false;" class="gball" href="#">2</a></td></tr>';

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

	var tempHewei=result.sum%10;
	var tempyushu=result.sum%3;
	if (prevMissItem != null) {
		
		missItem.hezhiHash = prevMissItem.hezhiHash.map(function(value) {
			return value + 1;
		});
		missItem.heweiHash = prevMissItem.heweiHash.map(function(value) {
			return value + 1;
		});
		missItem.yushuHash = prevMissItem.yushuHash.map(function(value) {
			return value + 1;
		});
		
	} else {
		missItem.hezhiHash=new Hash();
		missItem.heweiHash=new Hash();
		missItem.yushuHash=new Hash();
		for(var num=6;num<=30;num++){
			if(result.sum==num){
				missItem.hezhiHash.set(num, 0);
			}else{
				missItem.hezhiHash.set(num, 1);
			}
		}
		for(var num=0;num<=9;num++){
			missItem.heweiHash.set(num, 1);
			missItem.yushuHash.set(num, 1);
		}
	}
	missItem.hezhiHash.set(result.sum, 0);
	missItem.heweiHash.set(tempHewei, 0);
	missItem.yushuHash.set(tempyushu, 0);

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
	for(var num=6;num<=30;num++){
		var miss = missItem.hezhiHash.get(num);
		var classStr = 'tdjiange01';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="ballclickKeno" _cancas="cancas" _group="hezhiHash">' + num + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	}
	for(var num=0;num<=9;num++){
		var miss = missItem.heweiHash.get(num);
		var classStr = '';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="ballredone" _cancas="cancas" _group="heweiHash">' + num + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	}
	for(var num=0;num<=2;num++){
		var miss = missItem.yushuHash.get(num);
		var classStr = 'tdjiange01';
		var ballStr;
		if (miss == 0) {
			ballStr = '<span class="ballclickKeno" _cancas="cancas" _group="yushuHash">' + num + '</span>';
		} else {
			ballStr = miss;
		}
		html += '<td class="' + classStr + '">' + ballStr + '</td>';
	}
	
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
	
	for(var num=6;num<=30;num++){
		var miss = missItem.hezhiHash.get(num);
		var classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	}
	for(var num=0;num<=9;num++){
		var miss = missItem.heweiHash.get(num);
		var classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
	}
	for(var num=0;num<=2;num++){
		var miss = missItem.yushuHash.get(num);
		var classStr = '';
		html += '<td class="' + classStr + '">' + miss + '</td>';
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
		hezhiHash : new Hash(),
		heweiHash : new Hash(),
		yushuHash : new Hash()
	};
	var outMissItem = {
			hezhiHash : new Hash(),
			heweiHash : new Hash(),
			yushuHash : new Hash()
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
		hezhiHash : new Hash(),
		heweiHash : new Hash(),
		yushuHash : new Hash()
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
	[ 'hezhiHash', 'heweiHash', 'yushuHash' ].each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
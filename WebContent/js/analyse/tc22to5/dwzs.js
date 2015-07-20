NUMS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22'];
HashNameArr = [ 'firstNumHash','secondNumHash','thirdNumHash','fourthNumHash','fifthNumHash'];

COMPARENUM=['03','12','21','30'];

TAB_HEADER = '<table width="1760"border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2" align="center">@SELECT@</td><td width="95" rowspan="2" align="center">开奖号码</td><td height="22" colspan="8" >第1位</td><td colspan="15" >第2位</td><td colspan="13" >第3位</td><td colspan="12" >第4位</td><td colspan="13" >第5位</td><td width="60" rowspan="2" >和值</td></tr><tr class="zsttrpinklig"><td width="25" >01</td><td width="25" >02</td><td width="25">03</td><td width="25" > 04</td><td width="25" >05</td><td width="25" >06</td><td width="25" >07</td><td width="25" >08</td><td width="25" >02</td><td width="25">03</td><td width="25" > 04</td><td width="25" >05</td><td width="25" >06</td><td width="25" >07</td><td width="25" >08</td><td width="25" >09</td><td width="25" >10</td><td width="25" >11</td><td width="25" >12</td><td width="25" >13</td><td width="25" >14</td><td width="25" >15</td><td width="25" >16</td><td width="25" >06</td><td width="25" >07</td><td width="25" >08</td><td width="25" >09</td><td width="25" >10</td><td width="25" >11</td><td width="25" >12</td><td width="25" >13</td><td width="25" >14</td><td width="25" >15</td><td width="25" >16</td><td width="25" >17</td><td width="25" >18</td><td width="25" >09</td><td width="25" >10</td><td width="25" >11</td><td width="25" >12</td><td width="25" >13</td><td width="25" >14</td><td width="25" >15</td><td width="25" >16</td><td width="25" >17</td><td width="25" >18</td><td width="25" >19</td><td width="25" >20</td><td width="25" >10</td><td width="25" >11</td><td width="25" >12</td><td width="25" >13</td><td width="25" >14</td><td width="25" >15</td><td width="25" >16</td><td width="25" >17</td><td width="25" >18</td><td width="25" >19</td><td width="25" >20</td><td width="25" >21</td><td width="25" >22</td></tr>';
TAB_FOOTER = '<tr class="zsttrpinklig"><td width="60" rowspan="2" align="center" class="trtitlebg">@SELECT@</td><td width="95" rowspan="2" align="center" class="trtitlebg">开奖号码</td><td width="20" >01</td><td width="20" >02</td><td width="20">03</td><td width="20" > 04</td><td width="20" >05</td><td width="20" >06</td><td width="20" >07</td><td width="20" >08</td><td width="20" >02</td><td width="20">03</td><td width="20" > 04</td><td width="20" >05</td><td width="20" >06</td><td width="20" >07</td><td width="20" >08</td><td width="20" >09</td><td width="20" >10</td><td width="20" >11</td><td width="20" >12</td><td width="20" >13</td><td width="20" >14</td><td width="20" >15</td><td width="20" >16</td><td width="20" >06</td><td width="20" >07</td><td width="20" >08</td><td width="20" >09</td><td width="20" >10</td><td width="20" >11</td><td width="20" >12</td><td width="20" >13</td><td width="20" >14</td><td width="20" >15</td><td width="20" >16</td><td width="20" >17</td><td width="20" >18</td><td width="20" >09</td><td width="20" >10</td><td width="20" >11</td><td width="20" >12</td><td width="20" >13</td><td width="20" >14</td><td width="20" >15</td><td width="20" >16</td><td width="20" >17</td><td width="20" >18</td><td width="20" >19</td><td width="20" >20</td><td width="20" >10</td><td width="20" >11</td><td width="20" >12</td><td width="20" >13</td><td width="20" >14</td><td width="20" >15</td><td width="20" >16</td><td width="20" >17</td><td width="20" >18</td><td width="20" >19</td><td width="20" >20</td><td width="20" >21</td><td width="20" >22</td><td width="60" rowspan="2" >和值</td></tr><tr class="trtitlebg"><td height="22" colspan="8" >第1位</td><td colspan="15" >第2位</td><td colspan="13" >第3位</td><td colspan="12" >第4位</td><td colspan="13" >第5位</td></tr></table>';

MONI_SELECT = '<tr class="zsttrpinklig"><td height="22" class="graychar333">预选区</td><td class="zsttrpinklig"  >&nbsp;</td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">01</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">02</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">03</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">04</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">05</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">12</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">13</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">14</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">15</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">16</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">06</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">07</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">08</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">12</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">13</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">14</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">15</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">16</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">17</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">18</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">09</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">12</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">13</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">14</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">15</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">16</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">17</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">18</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">19</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">20</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">10</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">11</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">12</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">13</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">14</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">15</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">16</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">17</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">18</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">19</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">20</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">21</a></td>	<td><a onclick="ball_click(this);return false;" class="gball" href="#">22</a></td><td></td></tr>';



function getDrawedData() {
	return dataTc22to5;
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
	html +='<td class="trw graychar333" ><strong>';
	missItem.result.nums.each(function(num){
		html += num+' ';
	});
	html +='</strong></td>';
	var k = 0;
	HashNameArr.each(function(hashName, index) {
		var begin=0;
		var end=0;
		if(hashName=='firstNumHash'){
			begin=0;
			end=7;
		}else if(hashName=='secondNumHash'){
			begin=1;
			end=15;
		}else if(hashName=='thirdNumHash'){
			begin=5;
			end=17;
		}else if(hashName=='fourthNumHash'){
			begin=8;
			end=19;
		}else if(hashName=='fifthNumHash'){
			begin=9;
			end=21;
		}
		for(var i=begin;i<=end;i++){
			var num=NUMS[i];
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0){
				classStr = 'tdjiange01';
			}else if(k==2){
				classStr = 'trgray';
			}
			if(i==begin){
				classStr += ' b1redpx';
			}
			var ballStr;
			if (miss == 0) {
				ballStr = '<span class="ballredone" _cancas="cancas" _group="' + hashName + '">' + num + '</span>';
			} else {
				ballStr = miss;
			}
			html += '<td class="' + classStr + '">' + ballStr + '</td>';
		};
		k++;
	});
	html +='<td class="trw graychar333" >'+missItem.result.sum+'</td>';
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

	var classArr = [ 'trchosegrayd', 'ballredone', 'ballblue', 'ballredone' ];
	var k = 0;
	HashNameArr.each(function(hashName, index) {
		var begin=0;
		var end=0;
		if(hashName=='firstNumHash'){
			begin=0;
			end=7;
		}else if(hashName=='secondNumHash'){
			begin=1;
			end=15;
		}else if(hashName=='thirdNumHash'){
			begin=5;
			end=17;
		}else if(hashName=='fourthNumHash'){
			begin=8;
			end=19;
		}else if(hashName=='fifthNumHash'){
			begin=9;
			end=21;
		}
		for(var i=begin;i<=end;i++){
			var num=NUMS[i];
			var miss = missItem[hashName].get(num);
			var classStr = '';
			if (k == 0){
				classStr = 'tdjiange01';
			}else if(k==2){
				classStr = 'trgray';
			}
			html += '<td class="' + classStr + '">' + miss + '</td>';
		}
		k++;
	});
	html +='<td class="trw graychar333" ></td>';
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
		fourthNumHash : new Hash(),
		fifthNumHash : new Hash()
	};
	var outMissItem = {
		firstNumHash : new Hash(),
		secondNumHash : new Hash(),
		thirdNumHash : new Hash(),
		fourthNumHash : new Hash(),
		fifthNumHash : new Hash()
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
		firstNumHash : new Hash(),
		secondNumHash : new Hash(),
		thirdNumHash : new Hash(),
		fourthNumHash : new Hash(),
		fifthNumHash : new Hash()
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
	HashNameArr.each(function(hashName, index) {

		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
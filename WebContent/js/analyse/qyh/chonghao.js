BALLS = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18',
		'19', '20', '21', '22', '23' ];
SMALL = [ '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11' ];// 小号
BIG = [  '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23' ];// 大号
RATIO = [ '0:5', '1:4', '2:3', '3:2', '4:1', '5:0' ];//比率
ODD = [ '01', '03', '05', '07', '09', '11', '13', '15', '17', '19', '21', '23' ];// 奇号
EVEN = [ '02', '04', '06', '08', '10', '12', '14', '16', '18', '20',  '22' ];// 偶号
PRIME =[ '01', '02' ,'03' ,'05', '07', '11', '13', '17' ,'19', '23'  ];// 质号
SUM = ['04' ,'06', '08' ,'09' ,'10' ,'12', '14', '15', '16', '18', '20' , '21', '22' ];// 合号
AREA1=['01','02','03','04','05','06','07','08'];//一区
AREA2=['09','10','11','12','13','14','15','16'];//二区
AREA3=['17','18','19','20','21','22','23'];//三区
TAB_HEADER = '<table width="980" border="0" cellpadding="1" cellspacing="1" class="zstablegraybg" ><tr class="trtitlebg"><td width="60" rowspan="2">@SELECT@</td><td colspan="8" class="b1redpx">一区</td><td colspan="8" class="b1redpx">二区</td><td colspan="7" class="b1redpx">三区</td><td colspan="9"   class="b1redpx">出号个数统计</td></tr><tr class=" zsttrpinklig "><td width="25" class="b1redpx">01</td><td width="25">02</td><td width="25">03</td><td width="25">04</td><td width="25">05</td><td width="25">06</td><td width="25">07</td><td width="25" >08</td><td width="25" class="b1redpx">09</td><td width="25">10</td><td width="25" >11</td><td width="25">12</td><td width="25">13</td><td width="25">14</td><td width="25">15</td><td width="25" >16</td><td width="25"  class="b1redpx">17</td><td width="25">18</td><td width="25" >19</td><td width="25" >20</td><td width="25" >21</td><td width="25" >22</td><td width="25" >23</td><td width="40" class="b1redpx">大小比</td><td width="40" >奇偶比</td><td width="40">质合比</td><td width="40">区间比</td><td width="28">和值</td><td width="28">重号</td><td width="28">邻号</td><td width="50">连号个数</td><td>同尾数</td></tr>';
MONI_SELECT = '<td class="trgray graychar333">模拟选号</td>	<td class=" zsttrpinklig b1redpx" ><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td><td class=" zsttrpinklig"><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td><td class=" zsttrpinklig  b1redpx" ><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td><td class=" zsttrpinklig"><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td><td class=" zsttrpinklig  b1redpx" ><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td><td class=" zsttrpinklig "><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td><td class=" zsttrpinklig " ><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td>   <td class=" zsttrpinklig  b1redpx" >大小比</td>    <td class=" zsttrpinklig " >奇偶比</td>    <td class=" zsttrpinklig ">质合比</td>    <td class=" zsttrpinklig ">区间比</td>    <td class=" zsttrpinklig ">和值</td>    <td class=" zsttrpinklig ">重号</td>    <td class=" zsttrpinklig ">邻号</td>    <td class=" zsttrpinklig ">连号个数</td>    <td class=" zsttrpinklig ">同尾数</td>';
TAB_FOOTER = '</table>';

function getDrawedData() {
	return dataQyh;
}
function getBigSmallValue(nums) {
	var v = '';
	nums.each(function(num) {
		v += (num.toInt() % 10 >5) ? 'b' : 's';
	});
	return v;
}

function getOddEvenValue(nums) {
	var v = '';
	nums.each(function(num) {
		v += (num.toInt() % 2 == 0) ? 'o' : 'j';
	});
	return v;
}
function getZhiheValue(nums) {
	var v = '';
	nums.each(function(num) {
		v += (1==num.toInt()||2==num.toInt()||3==num.toInt()||5==num.toInt()||7==num.toInt()||11==num.toInt()||13==num.toInt()||17==num.toInt()||19==num.toInt()) ? 'z' : 'h';
	});
	return v;
}
function getYuValue(nums) {
	var v = '';
	nums.each(function(num) {
		v += 'y'+num.toInt()%3;
	});
	return v;
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
			nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4]]
	};
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
			missHash : new Hash(),
			flagHash: new Hash(),
			items : []
	};
	if (prevMissItem != null) {
		missItem.missHash = prevMissItem.missHash.map(function(item){
			return item+1;
		});
		missItem.items = prevMissItem.items.map(function(item){
			return item+1;
		});
	} else {
		BALLS.each(function(num) {
			missItem.missHash.set(num, 1);
			missItem.flagHash.set(num, 0);
		});
		missItem.items = new Array(9);
		for ( var i = 0; i < missItem.items.length; i++) {
			missItem.items[i]=1;
		}
	}
	
	result.nums.each(function(num) {
		missItem.missHash.set(num, 0);
	});
	
	
	var big = small = odd = even = prime = sum = area1 = area2 = area3 = 0;	
	var count = reNum = connNum = nabeNum = 0;
	var endNumHash = new Hash();//统计尾数
	result.nums.each(function(num, i) {
		if (BIG.contains(num)) {
			big ++;
		} else {
			small++;
		}
		if (ODD.contains(num)) { 
			odd++;
		} else {
			even++;
		}
		if (PRIME.contains(num)) { 
			prime++;
		} else {
			sum++;
		}
		if (AREA1.contains(num)) { 
			area1++;
		} else if (AREA2.contains(num)) { 
			area2++;
		}else{
			area3++;
		}
		count += num.toInt();
		var flag = 0;//邻号标识
		if (prevMissItem != null) {
			if(prevMissItem.missHash.get(num)==0){//重号
				reNum++;
				flag=1;
			}
			var index = BALLS.indexOf(num);//邻号
			if(index==0){
				if(prevMissItem.missHash.get(BALLS[1])==0){
					nabeNum++;
				}
			}else if(index==BALLS.length-1){
				if(prevMissItem.missHash.get(BALLS[index-1])==0){
					nabeNum++;
				}
			}else{
				if(prevMissItem.missHash.get(BALLS[index-1])==0||prevMissItem.missHash.get(BALLS[index+1])==0){
					nabeNum++;
				}
			}
		}
		var index = BALLS.indexOf(num);//连号
		if(index==0){
			if(missItem.missHash.get(BALLS[1])==0){
				connNum++;
			}
		}else if(index==BALLS.length-1){
			if(missItem.missHash.get(BALLS[index-1])==0){
				connNum++;
			}
		}else{
			if(missItem.missHash.get(BALLS[index-1])==0||missItem.missHash.get(BALLS[index+1])==0){
				connNum++;
			}
		}
		if(flag==1){//
			missItem.flagHash.set(num, 1);//
		}
		var endNum= num.substring(num.length-1, num.length);//尾值
		var value = endNumHash.get(endNum);
		if(value!=null){
			endNumHash.set(endNum,value+1);
		}else{
			endNumHash.set(endNum,1);
		}		
	});
	var endNumGroupCount = 0;//同尾数
	endNumHash.each(function(value){
		if(value>1){
			endNumGroupCount+=value;
		}
	});
	missItem.items[0]=big+':'+small;//大小比
	missItem.items[1]=odd+':'+even;//奇偶比
	missItem.items[2]=prime+':'+sum;//质合比
	missItem.items[3]=area1+':'+area2+':'+area3;//区间比
	missItem.items[4]=count;//和值
	missItem.items[5]=reNum;//重号
	missItem.items[6]=nabeNum;//邻号
	missItem.items[7]=(connNum==1)?0:connNum;//连号个数
	missItem.items[8]=endNumGroupCount;//同尾号个数
	
	
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
var html = '<td class="trw graychar333" >' + issue + '</td>';
	
	var i=0;
	missItem.missHash.each(function(miss,ball) {
		var classHtml = (i == 0 || i==8 || i==16) ? 'class="b1redpx"' : '';
		var ballSpan = (miss==0)? '<span class="graychar333">'+ball+'</span>':miss;//中奖的球用黑体显示
		var ballHtml = (missItem.flagHash.get(ball)==1)?'<span class="nballred">'+ball+'</span>':ballSpan;		
		html += '<td ' + classHtml + '>'+ ballHtml +'</td>';
		i++;
	});
	
	missItem.items.each(function(item,i){
		var classHtml = (i == 0) ? 'class="b1redpx"' : '';
		html +='<td '+classHtml+'>'+item+'</td>';
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
	var html = '<td class="graychar333" >' + statText + '</td>';
	var i = 0;
	missItem.missHash.each(function(miss,ball) {
		var classHtml = (i == 0 || i==8 || i==16) ? 'class="b1redpx"' : '';
		html += '<td '+classHtml+'>' + miss + '</td>';
		i++;
	});
	
	for(var i=0;i<9;i++){
		var classHtml = (i == 0) ? 'class="b1redpx"' : '';
		html +='<td '+classHtml+'>&nbsp;</td>';
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
	//最大遗漏
	var maxMissItem = createMissItem();
    //出现次数
	var haveItem = createMissItem();
	//总遗漏
	var totalMissItem = createMissItem();
	//最大连出遗漏
	var connMissItem = createMissItem();
	//最大临时连出遗漏
	var connMissItemTemp = createMissItem();
	//记录上一次遗漏
	var prevMissItem = createMissItem();
	
	var missHTML = '';
	var index = 0;
	var issueSize = 0;
	var itemIandleFn = function(issue, missItem) {
		issueSize++;
		missHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">' + missItem.html + '</tr>';
		if (issueSize != 0 && issueSize % 5==0) {
			missHTML += '<tr class="hang5bggray"><td colspan="35"></td></tr>';
		}
		missItem.missHash.each(function(miss, ball) {
			var maxMiss = maxMissItem.missHash.get(ball);
			if (miss > maxMiss) {
				maxMissItem.missHash.set(ball,miss);// 统计最大遗漏
			}
			
			var totalMiss = totalMissItem.missHash.get(ball);
			totalMissItem.missHash.set(ball, totalMiss + miss);
			
            if(miss==0){//统计出现次数
            	var have = haveItem.missHash.get(ball);
            	haveItem.missHash.set(ball, have+1);	
            }
            
            var preMiss = prevMissItem.missHash.get(ball);
            var conn = connMissItemTemp.missHash.get(ball);
			if(miss==preMiss){//统计连号遗漏
				connMissItemTemp.missHash.set(ball,conn+1);
			}else if(haveItem.missHash.get(ball)==0){
				connMissItemTemp.missHash.set(ball,0);
			}else{
				connMissItemTemp.missHash.set(ball,1);
			}
			if(connMissItemTemp.missHash.get(ball)>connMissItem.missHash.get(ball)){
				connMissItem.missHash.set(ball,connMissItemTemp.missHash.get(ball));
			}
		});
		
		prevMissItem = missItem;//保存上次遗漏
	};
	displayHandle(key, checkFn, desc, itemIandleFn);

	var avgMissItem = createMissItem();
	// 计算平均遗漏
	haveItem.missHash.each(function(miss, ball) {
		var avgMiss = miss==0 ? maxMissItem.missHash.get(ball) : (issueSize/miss).round();
		avgMissItem.missHash.set(ball,avgMiss);
	});
	
	
	var countItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('出现总数', haveItem) + '</tr>';
	var avgMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('平均遗漏', avgMissItem) + '</tr>';
	var maxMissItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('最大遗漏', maxMissItem) + '</tr>';
	var connItemHTML = '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trgray') + '">'
			+ buildStatItemHTML('最大连出', connMissItem) + '</tr>';

	var moniHTML = '';
	for ( var i = 0; i < 1; i++) {
		moniHTML += '<tr class="zsttrpinklig">' + MONI_SELECT + '</tr>';
	}
	
	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML + countItemHTML +  avgMissItemHTML + maxMissItemHTML + connItemHTML + TAB_FOOTER;
	return html;
}
//创建遗漏对象
function createMissItem() {
	var missItem = {
		missHash : new Hash()
	};
	BALLS.each(function(num) {
		missItem.missHash.set(num, 0);
	});
	return missItem;
}

// 华丽的分隔线 ======================================================================

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	var missData = qyhNum;
	for ( var issue in missData) {
		var missObj = {
			missObj : missData[issue]
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
		missHash : new Hash(),
		flagHash: new Hash(),
		items : []
	};
	for ( var ball in missObj.missObj) {
		var ballInt = ball.toInt();
		var ballNum = (ballInt < 10) ? '0' + ball : ball;
		missItem.missHash.set(ballNum, missObj.missObj[ball]);
	}
	missItem.html = buildMissItemHTML(issue, missItem);
	return missItem;
};

// 华丽的分隔线 ======================================================================
/**
 * 显示遗漏数据
 * 
 * @param size - (<em>number</em>) 期数
 * @param desc - (<em>boolean</em>) 是否倒序输出
 */
function displayMissOfSize(size, desc) {
	CURRENT_FN = function(descSort) {
		displayMissOfSize(size, descSort);
	};

	if (desc == null)
		desc = false;
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;

	var keyOfHTML = 'HTML_' + MISS_MODE + '_' + size + '_' + desc;
	var html = getFromCache(keyOfHTML);
	if (html == null) {
		var key = 'MISS_DATA_' + MISS_MODE + '_' + size;
		var p = issueArr.length - size - 1;
		var checkFn;
		if (p > 0) {
			var issueInt = issueArr[p].replace("-","").toInt();
			checkFn = function(issue) {
				issue = issue.replace("-","");
				return issue.toInt() > issueInt;
			};
		} else {
			checkFn = function(issue) {
				return true;
			};
		}
		html = genMissHTML(key, checkFn, desc);

		cacheSet(keyOfHTML, html);
	}
	getMissElement().innerHTML = html;
	drawLine();
	setOnclick();
}
/**
 * 显示遗漏数据
 * 
 * @param startIssue - (<em>string</em>) 开始期号
 * @param endIssue - (<em>string</em>) 结束期号
 * @param desc - (<em>boolean</em>) 是否倒序输出
 */
function displayMissOfSearch(startIssue, endIssue, desc) {
	CURRENT_FN = function(descSort) {
		displayMissOfSearch(startIssue, endIssue, descSort);
	};

	if (desc == null)
		desc = false;
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;

	var keyOfHTML = 'HTML_' + MISS_MODE + '_' + startIssue + '_' + endIssue + '_' + desc;
	var html = getFromCache(keyOfHTML);
	if (html == null) {
		var key = 'MISS_DATA_' + MISS_MODE + '_' + startIssue + '_' + endIssue;
		var checkFn;
		var searchMaxSize = getSearchMaxSize();
		if (isBlank(startIssue) && isBlank(endIssue)) {
			alert('请输入查询条件.');
			return;
		} else if (isBlank(startIssue) && !isBlank(endIssue)) {
			var endIndex = issueArr.indexOf(endIssue);
			if (endIndex < 0) {
				alert('[' + endIssue + ']不在可供查询的范围内.');
				return;
			}
			var startIndex = (endIndex >= searchMaxSize) ? (endIndex + 1 - searchMaxSize) : 0;
			startIssue = issueArr[startIndex];
		} else if (!isBlank(startIssue) && isBlank(endIssue)) {
			var startIndex = issueArr.indexOf(startIssue);
			if (startIndex < 0) {
				alert('[' + startIssue + ']不在可供查询的范围内.');
				return;
			}
			var endIndex = startIndex + searchMaxSize - 1;
			if (endIndex >= issueArr.length)
				endIndex = issueArr.length - 1;
			endIssue = issueArr[endIndex];
		} else {
			var startIndex = issueArr.indexOf(startIssue);
			if (startIndex < 0) {
				alert('[' + startIssue + ']不在可供查询的范围内.');
				return;
			}
			var endIndex = issueArr.indexOf(endIssue);
			if (endIndex < 0) {
				alert('[' + endIssue + ']不在可供查询的范围内.');
				return;
			}
			if (endIndex - startIndex >= searchMaxSize) {
				alert('最大只允许查询' + searchMaxSize + '期.');
				return;
			} else if (startIndex > endIndex) {
				alert('查询条件有误.');
				return;
			}
		}
		var startIssueInt = startIssue.replace("-","").toInt();
		var endIssueInt = endIssue.replace("-","").toInt();
		checkFn = function(issue) {
			issue = issue.replace("-","");
			var issueInt = issue.toInt();
			return issueInt >= startIssueInt && issueInt <= endIssueInt;
		};
		html = genMissHTML(key, checkFn, desc);

		cacheSet(keyOfHTML, html);
	}
	getMissElement().innerHTML = html;
	drawLine();
	setOnclick();
}
function drawLine() {
	$("div_line").innerHTML = "";
	var tagArr1 = $$('span[_cancas="cancas"][_group="num"]');
	var myfun = function() {
		drawCancas(tagArr1, 1);
	};
	myfun.delay(10);
	[ 'bigsmall', 'oddeven','zhihe','yu' ].each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="' + hashName + '"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
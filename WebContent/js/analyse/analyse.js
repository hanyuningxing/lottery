CURRENT_FN = null;// 当前显示的函数
DESC_SORT = false;// 当前是否倒序状态
CURRENT_SORTMODE = 0;// 当前排序，0为默认，1为升序，2为倒序

/**
 * @returns {number} - 查询的最大期数限制
 */
function getSearchMaxSize() {
	return 300;
}

/**
 * 按照指定排序重新输出遗漏数据
 * 
 * @param sortMode - (<em>string</em>) 排序模式，0为默认，1为升序，2为倒序
 */
function sortDisplay(sortMode) {
	if (CURRENT_FN != null) {
		if (sortMode == null) {
			sortMode = CURRENT_SORTMODE;
		} else {
			CURRENT_SORTMODE = sortMode.toInt();
		}
		DESC_SORT = CURRENT_SORTMODE == 2;
		CURRENT_FN(DESC_SORT);
	}
}

/**
 * 切换显示的期数目
 * 
 * @param elId - (<em>string</em>) 当前切换的标签ID
 * @param size - (<em>number</em>) 显示的期数目
 */
function chgDisplay(elId, size) {
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = (elId == buttonEl.id) ? 'btnow' : 'btgray';
	});
	displayMissOfSize(size);
}

/**
 * 查询遗漏数据
 */
function searchDisplay() {
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = 'btgray';
	});
	var startIssue = document.getElementById('startIssueNo').value;
	var endIssue = document.getElementById('endIssueNo').value;
	displayMissOfSearch(startIssue, endIssue);
}

/**
 * 切换遗漏模式
 * 
 * @param mode - (<em>number</em>) 遗漏模式，1表示历史遗漏数据，2表示当前页遗漏数据
 */
function chgMissMode(mode) {
	if (mode == 1 || mode == 2) {
		MISS_MODE = mode;
		CURRENT_FN(DESC_SORT);
	}
}

// 华丽的分隔线 ======================================================================

DATA_HASH = new Hash();// 历史开奖数据
CACHE = null;// 遗漏数据缓存，启用页面缓存：CACHE = new Hash();
MISS_EL = null;// 遗漏数据展示的页面标签对象
HISTORY_MISS_HASH = new Hash();// 历史遗漏数据
MISS_MODE = 2;// 遗漏数据模式，1表示历史遗漏数据，2表示当前页遗漏数据

/**
 * 获取显示遗漏数据的标签对象
 * 
 * @returns {element} - 显示遗漏数据的标签对象
 */
function getMissElement() {
	if (MISS_EL == null)
		MISS_EL = document.getElementById('MISS_EL');
	return MISS_EL;
}

/**
 * 从缓存获取数据
 * 
 * @param cacheKey - (<em>string</em>) 缓存KEY
 * @returns {?} 缓存内容
 */
function getFromCache(cacheKey) {
	return (CACHE != null) ? CACHE.get(cacheKey) : null;
}

/**
 * 缓存数据
 * 
 * @param cacheKey - (<em>string</em>) 缓存KEY
 * @param cacheValue - (<em>?</em>) 缓存内容
 * @param cacheOption - (<em>object</em>) 缓存参数
 */
function cacheSet(cacheKey, cacheValue, cacheOption) {
	if (CACHE != null) {
		CACHE.set(cacheKey, cacheValue);
	}
}

/**
 * 获取排序选择的HTML代码
 * 
 * @param sort - (<em>boolean</em>) 当前排序
 * @returns {string} 排序选择的HTML代码
 */
function getSortSelectHTML(sort) {
	var html = '<select size="1" style="width:50px;" onchange="sortDisplay(this.value);">';
	html += '<option ' + (CURRENT_SORTMODE == 0 ? 'selected="selected"' : '') + ' value="0">期号</option>';
	html += '<option ' + (CURRENT_SORTMODE == 1 ? 'selected="selected"' : '') + ' value="1">小到大</option>';
	html += '<option ' + (CURRENT_SORTMODE == 2 ? 'selected="selected"' : '') + ' value="2">大到小</option>';
	html += '</select>';
	return html;
}

/**
 * 初始化DATA_HASH,将历史开奖数据转为Hash存储，方便访问
 */
function initDataHash() {
	var dataObj = getDrawedData();
	var prevResult = null;
	for ( var issue in dataObj) {
		var result = genResult(dataObj[issue], issue, prevResult);
		DATA_HASH.set(issue, result);
		prevResult = result;
	}
}

/**
 * 构建遗漏数据
 * 
 * @param checkFn - (<em>function</em>) 检查是否需要统计该期的函数
 *            <ul>
 *            <h3>语法：<code>fn(issue)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            </ol>
 *            </ul>
 * @returns {hash} - 遗漏数据
 */
function buildMissData(checkFn) {
	var missDataHash = new Hash();
	var prevMissItem = null;
	var prevResult = null;
	var index = 0;
	DATA_HASH.each(function(result, issue) {
		if (checkFn(issue)) {
			var missItem = buildMissItem(issue, result, prevMissItem, prevResult, index);
			missDataHash.set(issue, missItem);
			prevMissItem = missItem;
			prevResult = result;
			index++;
		}
	});
	return missDataHash;
}

/**
 * 获取符合条件的历史遗漏数据
 * 
 * @param checkFn - (<em>function</em>) 检查该期是否符合查询条件的函数
 *            <ul>
 *            <h3>语法：<code>fn(issue)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            </ol>
 *            </ul>
 * @returns {hash} - 符合条件的历史遗漏数据
 */
function getHistoryMissData(checkFn) {
	var missDataHash = new Hash();
	HISTORY_MISS_HASH.each(function(missItem, issue) {
		if (checkFn(issue))
			missDataHash.set(issue, missItem);
	});
	return missDataHash;
}

/**
 * @param key - (<em>string</em>) 用于缓存的Key
 * @param checkFn - (<em>function</em>) 检查是否需要统计该期的函数
 *            <ul>
 *            <h3>语法：<code>fn(issue)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            </ol>
 *            </ul>
 * @param desc - (<em>boolean</em>) 是否倒序输出
 * @param itemHandleFn - (<em>function</em>) 处理遗漏数据的函数 *
 *            <ul>
 *            <h3>语法：<code>function(issue, missItem)</code></h3>
 *            <h3>参数：</h3>
 *            <ol>
 *            <li>issue - (<em>string</em>) 期号</li>
 *            <li>missItem - (<em>object</em>) 遗漏数据</li>
 *            </ol>
 *            </ul>
 */
function displayHandle(key, checkFn, desc, itemHandleFn) {
	var missDataHash = getFromCache(key);
	if (missDataHash == null) {
		missDataHash = (MISS_MODE == 2) ? buildMissData(checkFn) : getHistoryMissData(checkFn);
		cacheSet(key, missDataHash);
	}

	var issueArr = missDataHash.getKeys();
	var issueSize = issueArr.length;
	if (desc) {
		var prevMissItem = null;
		for ( var i = issueSize - 1; i >= 0; i--) {
			var issue = issueArr[i];
			missItem = missDataHash.get(issue);
			itemHandleFn(issue, missItem, prevMissItem);
			prevMissItem = missItem;
		}
	} else {
		var prevMissItem = null;
		for ( var i = 0; i < issueSize; i++) {
			var issue = issueArr[i];
			missItem = missDataHash.get(issue);
			itemHandleFn(issue, missItem, prevMissItem);
			prevMissItem = missItem;
		}
	}
}

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
			var issueInt = issueArr[p].toInt();
			checkFn = function(issue) {
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
 * 检查字符串是否为NULL或为空字符串
 * 
 * @param str 字符串
 * @returns {boolean} 是否为NULL或为空字符串
 */
function isBlank(str) {
	return str == null || str.trim().length == 0;
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
		var startIssueInt = startIssue.toInt();
		var endIssueInt = endIssue.toInt();
		checkFn = function(issue) {
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

function displayMissOfPrefixSearch(prefixIsse, desc) {
	CURRENT_FN = function(descSort) {
		displayMissOfPrefixSearch(prefixIsse, descSort);
	};

	if (desc == null)
		desc = false;
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0)
		return;

	var keyOfHTML = 'HTML_' + MISS_MODE + '_' + prefixIsse.join('_') + '_' + desc;
	var html = getFromCache(keyOfHTML);
	if (html == null) {
		var key = 'MISS_DATA_' + MISS_MODE + '_' + prefixIsse.join('_');
		var checkFn;
		var searchMaxSize = getSearchMaxSize();
		if (prefixIsse.length==0) {
			alert('请输入查询条件.');
			return;
		}
		checkFn = function(issue) {
			var istr=false;
			prefixIsse.each(function(item){
				if(issue.indexOf(item)==0){
					istr=true;
					return true;
				}
			});
			return istr;
		};
		html = genMissHTML(key, checkFn, desc);

		cacheSet(keyOfHTML, html);
	}
	getMissElement().innerHTML = html;
	drawLine();
	setOnclick();
}

/**
 * 查询遗漏数据
 */
function searchPrefixDisplay() {
	var prefixArray=[];
	var firstPrefix;
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = 'btgray';
	});
	$('prefixDiv').getElements('input[type="checkbox"]').each(function(buttonEl) {
		if(!firstPrefix){
			firstPrefix=buttonEl.value;
		}
		if(buttonEl.checked){
			prefixArray.include(buttonEl.value);
		}
	});
	if(prefixArray.length==0){
		prefixArray.include(firstPrefix);
	}
	displayMissOfPrefixSearch(prefixArray);
}

// 华丽的分隔线 ======================================================================

/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 * @param prevResult - (<em>object</em>) 上一期开奖结果对象
 * @returns {object} - 开奖结果对象
 */
function genResult(numStr, issue, prevResult) {
	throw '组装各彩种不同的开奖结果对象，各彩种子类继承重载实现.';
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
	throw '组装各彩种不同的遗漏数据对象，各彩种子类继承重载实现.';
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
	throw '生成遗漏数据的HTML代码，各彩种子类继承重载实现.';
}

/**
 * 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
 */
function initHistoryMissHash() {
	throw '初始化历史遗漏数据，将提供的数据，转换成一致的格式存储.';
}

/**
 * 画线
 * 
 * @return
 */
function drawLine() {
	// 如果彩种需要画线, 则实现该方法.
}

function getDrawedData() {
	return getdata();
}

function setOnclick() {

}

function isPrime(num){
	return num==1||num==3||num==4||num==7||num==11||num==13||num==17||num==19||num==23;
}
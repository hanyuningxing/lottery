YNUMS = [ '0','1','2','3','4','5','6','7','8','9' ];

XT_NUMS = [ '00','01','02','10','11','12','20','21','22'];
GS_NUMS = [ '0','1','2' ];

TAB_HEADER = '<table class="trendCharts trendSSQ" cellspacing="0" id="tableChart" onmouseout=""><tbody><tr class="tr-first"><th colspan="2" rowspan="2" class="td-first" style="cursor:hand" title="期号">期号</th><th rowspan="2">开奖<br>号码</th><th colspan="3">十位</th><th colspan="3">个位</th><th colspan="9">012路形态</th><th colspan="4">余0号码</th><th colspan="3">余1号码</th><th colspan="3">余2号码</th><th class="td-last" rowspan="2">012路比</th></tr><tr class="tr-first"><th>0</th><th>1</th><th>2</th><th>0</th><th>1</th><th>2</th><th>00</th><th>01</th><th>02</th><th>10</th><th>11</th><th>12</th><th>20</th><th>21</th><th>22</th><th>0</th><th>3</th><th>6</th><th>9</th><th>1</th><th>4</th><th>7</th><th>2</th><th>5</th><th>8</th>';
MONI_SELECT = '<tr><td colspan="3" rowspan="5" class="group2-3 td-segment-r" style="color:#000000;">模拟选号</td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">10</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">11</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">12</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">20</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">21</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">22</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">10</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">11</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">12</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">20</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">21</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">22</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">10</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">11</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">12</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">20</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">21</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">22</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">10</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">11</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">12</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">20</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">21</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">22</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">10</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">11</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">12</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">20</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">21</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">22</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>';


DATA_HASH = new Hash();// 历史开奖数据
//个位
CURLOST_G_HASH = new Hash();
MAXMISS_G_HASH = new Hash();
AVEMISS_G_HASH = new Hash();
CURPANO_G_HASH = new Hash();
MAXPANO_G_HASH = new Hash();
LPANO_G_HASH = new Hash();

//十位
CURLOST_S_HASH = new Hash();
MAXMISS_S_HASH = new Hash();
AVEMISS_S_HASH = new Hash();
CURPANO_S_HASH = new Hash();
MAXPANO_S_HASH = new Hash();
LPANO_S_HASH = new Hash();

//形态
XT_HASH = new Hash();
MAXMISS_XT_HASH = new Hash();
CURLOST_XT_HASH = new Hash();
CURPANO_XT_HASH = new Hash();
MAXPANO_XT_HASH = new Hash();
LPANO_XT_HASH = new Hash();

//余0数
Y_HASH = new Hash();
MAXMISS_Y_HASH = new Hash();
CURLOST_Y_HASH = new Hash();
CURPANO_Y_HASH = new Hash();
MAXPANO_Y_HASH = new Hash();
LPANO_Y_HASH = new Hash();

var YSB = '';

function getDrawedData() {
	return dataSsc;
}
/**
 * 初始化DATA_HASH,将历史开奖数据转为Hash存储，方便访问
 */
function initDataHash(start,end) {
	var dataObj = dataSsc;
	var BODY = "";
	var html = "";
	var index = 0;
	initHash();
	for ( var issue in dataObj) {
		if(index<end&&index>=start){
			DATA_HASH.set(issue, genResult(dataObj[issue]));
			BODY+=buildGSItem(issue,DATA_HASH.get(issue));
		}
		index++;
	}
	var CURPANO_Html = calCurPano();
	var CURLOST_Html = calCurLost();
	var AVGLOST_Html = calAvgLost();
	var MAX_Html = calMaxLost();
	html = TAB_HEADER+BODY+MONI_SELECT+CURPANO_Html+CURLOST_Html+AVGLOST_Html+MAX_Html;
	$("ga").innerHTML = html;
	drawLine();			

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
function chgDisplay(end){
	initDataHash(0,end);
}


/**
 * 显示遗漏数据
 * 
 * @param startIssue - (<em>string</em>) 开始期号
 * @param endIssue - (<em>string</em>) 结束期号
 */
function displayMissOfSearch(startIssue, endIssue) {
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0){
		return;
	}
		
	var checkFn;
	var searchMaxSize = getMaxSize();
	if (isBlank(startIssue) && isBlank(endIssue)) {
		alert('请输入查询条件.');
		return;
	} else if (isBlank(startIssue) && !isBlank(endIssue)) {
		var endIndex = endIssue.replace("-","");
		var startIndex = issueArr[0];
		var endIussue = issueArr[issueArr.length-1];
		if(endIndex.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if(endIndex<startIndex||endIndex>endIussue){
			alert('[' + endIssue + ']不在可供查询的范围内.');
			return;
		}

		var index = issueArr.indexOf(endIndex);
		initDataHash(0,index+1);
		return ;
	} else if (!isBlank(startIssue) && isBlank(endIssue)) {
		var start = startIssue.replace("-","");
		var startIssueNo = issueArr[0];
		var endIussue = issueArr[issueArr.length-1];
		if(start.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if (start < startIssueNo||start>endIussue) {
			alert('[' + startIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		var endIndex = issueArr.length;
		var startIndex = issueArr.indexOf(start);
		initDataHash(startIndex,endIndex);
	} else {
		var start = startIssue.replace("-","");
		var end = endIssue.replace("-","");

		var startIndex = issueArr.indexOf(start);
		if(start.length<11||end.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if (startIndex < 0) {
			alert('[' + startIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		var endIndex = issueArr.indexOf(end)+1;
		if (endIndex < 0) {
			alert('[' + endIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		if (endIndex - startIndex > issueArr.length) {
			alert('最大只允许查询' + issueArr.length + '期.');
			return;
		} else if (startIndex > endIndex) {
			alert('查询条件有误.');
			return;
		}
		initDataHash(startIndex,endIndex);
	}
	
}

/**
 * 获取最大遗漏条数
 * @returns
 */
function getMaxSize(){
	return 200;
}
/**
 * 构建当前出现次数HTML代码
 */
function calCurPano(){
	var CURPANO_Html = '<tr class="drop"><td colspan="3" class="td-first">出现次数</td><td>'+CURPANO_S_HASH.get("0")+'</td><td>'+CURPANO_S_HASH.get("1")+'</td><td>'+CURPANO_S_HASH.get("2")+'</td><td>'+CURPANO_G_HASH.get("0")+'</td><td>'+CURPANO_G_HASH.get("1")+'</td><td>'+CURPANO_G_HASH.get("2")+'</td><td>'+CURPANO_XT_HASH.get("00")+'</td><td>'+CURPANO_XT_HASH.get("01")+'</td><td>'+CURPANO_XT_HASH.get("02")+'</td><td>'+CURPANO_XT_HASH.get("10")+'</td><td>'+CURPANO_XT_HASH.get("11")+'</td><td>'+CURPANO_XT_HASH.get("12")+'</td><td>'+CURPANO_XT_HASH.get("20")+'</td><td>'+CURPANO_XT_HASH.get("21")+'</td><td>'+CURPANO_XT_HASH.get("22")+'</td><td>'+CURPANO_Y_HASH.get("0")+'</td><td>'+CURPANO_Y_HASH.get("3")+'</td><td>'+CURPANO_Y_HASH.get("6")+'</td><td>'+CURPANO_Y_HASH.get("9")+'</td><td>'+CURPANO_Y_HASH.get("1")+'</td><td>'+CURPANO_Y_HASH.get("4")+'</td><td>'+CURPANO_Y_HASH.get("7")+'</td><td>'+CURPANO_Y_HASH.get("2")+'</td><td><span class="lger">'+CURPANO_Y_HASH.get("5")+'</span></td><td class="td-segment-r">'+CURPANO_Y_HASH.get("8")+'</td><td class="td-last"></td></tr>';
	return CURPANO_Html;
}
/**
 * 构建当前遗漏HTML代码
 */
function calCurLost(){
	var CURLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">当前遗漏</td><td>'+CURLOST_S_HASH.get("0")+'</td><td>'+CURLOST_S_HASH.get("1")+'</td><td>'+CURLOST_S_HASH.get("2")+'</td><td>'+CURLOST_G_HASH.get("0")+'</td><td>'+CURLOST_G_HASH.get("1")+'</td><td>'+CURLOST_G_HASH.get("2")+'</td><td>'+CURLOST_XT_HASH.get("00")+'</td><td>'+CURLOST_XT_HASH.get("01")+'</td><td>'+CURLOST_XT_HASH.get("02")+'</td><td>'+CURLOST_XT_HASH.get("10")+'</td><td>'+CURLOST_XT_HASH.get("11")+'</td><td>'+CURLOST_XT_HASH.get("12")+'</td><td>'+CURLOST_XT_HASH.get("20")+'</td><td>'+CURLOST_XT_HASH.get("21")+'</td><td>'+CURLOST_XT_HASH.get("22")+'</td><td>'+CURLOST_Y_HASH.get("0")+'</td><td>'+CURLOST_Y_HASH.get("3")+'</td><td>'+CURLOST_Y_HASH.get("6")+'</td><td>'+CURLOST_Y_HASH.get("9")+'</td><td>'+CURLOST_Y_HASH.get("1")+'</td><td>'+CURLOST_Y_HASH.get("4")+'</td><td>'+CURLOST_Y_HASH.get("7")+'</td><td>'+CURLOST_Y_HASH.get("2")+'</td><td><span class="lger">'+CURLOST_Y_HASH.get("5")+'</span></td><td class="td-segment-r">'+CURLOST_Y_HASH.get("8")+'</td><td class="td-last"></td></tr>';
	return CURLOST_Html;
}
/**
 * 构建最大连出HTML代码
 */
function calMaxLost(){
	var MAX_Html = '<tr class="histogram"><td colspan="3" class="td-first td-first-1">最大连出</td><td>'+MAXPANO_S_HASH.get("0")+'<i style="height:'+MAXPANO_S_HASH.get("0")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("1")+'</span><i style="height:'+MAXPANO_S_HASH.get("1")+'px"><em></em></i></td><td>'+MAXPANO_S_HASH.get("2")+'<i style="height:'+MAXPANO_S_HASH.get("2")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("0")+'<i style="height:'+MAXPANO_G_HASH.get("0")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_G_HASH.get("1")+'</span><i style="height:'+MAXPANO_G_HASH.get("1")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("2")+'<i style="height:'+MAXPANO_G_HASH.get("2")+'px"><em></em></i></td><td>'+MAXPANO_XT_HASH.get("00")+'<i style="height:'+MAXPANO_XT_HASH.get("00")+'px"><em></em></i></td><td>'+MAXPANO_XT_HASH.get("01")+'<i style="height:'+MAXPANO_XT_HASH.get("01")+'px"><em></em></i></td><td>'+MAXPANO_XT_HASH.get("02")+'<i style="height:'+MAXPANO_XT_HASH.get("02")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_XT_HASH.get("10")+'</span><i style="height:'+MAXPANO_XT_HASH.get("10")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_XT_HASH.get("11")+'</span><i style="height:'+MAXPANO_XT_HASH.get("11")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_XT_HASH.get("12")+'</span><i style="height:'+MAXPANO_XT_HASH.get("12")+'px"><em></em></i></td><td>'+MAXPANO_XT_HASH.get("20")+'<i style="height:'+MAXPANO_XT_HASH.get("20")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_XT_HASH.get("21")+'</span><i style="height:'+MAXPANO_XT_HASH.get("21")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_XT_HASH.get("22")+'</span><i style="height:'+MAXPANO_XT_HASH.get("22")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_Y_HASH.get("0")+'</span><i style="height:'+MAXPANO_Y_HASH.get("0")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_Y_HASH.get("3")+'</span><i style="height:'+MAXPANO_Y_HASH.get("3")+'px"><em></em></i></td><td>'+MAXPANO_Y_HASH.get("6")+'<i style="height:'+MAXPANO_Y_HASH.get("6")+'px"><em></em></i></td><td>'+MAXPANO_Y_HASH.get("9")+'<i style="height:'+MAXPANO_Y_HASH.get("9")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_Y_HASH.get("1")+'</span><i style="height:'+MAXPANO_Y_HASH.get("1")+'px"><em></em></i></td><td>'+MAXPANO_Y_HASH.get("4")+'<i style="height:'+MAXPANO_Y_HASH.get("4")+'px"><em></em></i></td><td>'+MAXPANO_Y_HASH.get("7")+'<i style="height:'+MAXPANO_Y_HASH.get("7")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_Y_HASH.get("2")+'</span><i style="height:'+MAXPANO_Y_HASH.get("2")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_Y_HASH.get("5")+'</span><i style="height:'+MAXPANO_Y_HASH.get("5")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_Y_HASH.get("8")+'</span><i style="height:'+MAXPANO_Y_HASH.get("8")+'px"><em></em></i></td><td class="td-last"></td></tr></table>';
	return MAX_Html;
}
/**
 * 构建平均遗漏HTML代码
 */
function calAvgLost(){
	//十位
	var avg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("0").toInt())/(CURPANO_S_HASH.get("0").toInt()+1)));
	var avg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("1").toInt())/(CURPANO_S_HASH.get("1").toInt()+1)));
	var avg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("2").toInt())/(CURPANO_S_HASH.get("2").toInt()+1)));
	//个位
	var gavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("0").toInt())/(CURPANO_G_HASH.get("0").toInt()+1)));
	var gavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("1").toInt())/(CURPANO_G_HASH.get("1").toInt()+1)));
	var gavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("2").toInt())/(CURPANO_G_HASH.get("2").toInt()+1)));
		
	//012路形态
	var zavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("00").toInt())/(CURPANO_XT_HASH.get("00").toInt()+1)));
	var zavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("01").toInt())/(CURPANO_XT_HASH.get("01").toInt()+1)));
	var zavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("02").toInt())/(CURPANO_XT_HASH.get("02").toInt()+1)));
	var zavg3 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("10").toInt())/(CURPANO_XT_HASH.get("10").toInt()+1)));
	var zavg4 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("11").toInt())/(CURPANO_XT_HASH.get("11").toInt()+1)));
	var zavg5 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("12").toInt())/(CURPANO_XT_HASH.get("12").toInt()+1)));
	var zavg6 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("20").toInt())/(CURPANO_XT_HASH.get("20").toInt()+1)));
	var zavg7 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("21").toInt())/(CURPANO_XT_HASH.get("21").toInt()+1)));
	var zavg8 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get("22").toInt())/(CURPANO_XT_HASH.get("22").toInt()+1)));
	//余0号码
	var kavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("0").toInt())/(CURPANO_Y_HASH.get("0").toInt()+1)));
	var kavg3 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("3").toInt())/(CURPANO_Y_HASH.get("3").toInt()+1)));
	var kavg6 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("6").toInt())/(CURPANO_Y_HASH.get("6").toInt()+1)));
	var kavg9 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("9").toInt())/(CURPANO_Y_HASH.get("9").toInt()+1)));
	//余1号码
	var kavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("1"))/(CURPANO_Y_HASH.get("1")+1)));
	var kavg4 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("4").toInt())/(CURPANO_Y_HASH.get("4").toInt()+1)));
	var kavg7 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("7").toInt())/(CURPANO_Y_HASH.get("7").toInt()+1)));
	//余2号码
	var kavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("2").toInt())/(CURPANO_Y_HASH.get("2").toInt()+1)));
	var kavg5 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("5").toInt())/(CURPANO_Y_HASH.get("5").toInt()+1)));
	var kavg8 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get("8"))/(CURPANO_Y_HASH.get("8")+1)));
	
	var AVGLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">平均遗漏</td><td>'+avg0+'</td><td>'+avg1+'</td><td>'+avg2+'</td><td>'+gavg0+'</td><td>'+gavg1+'</td><td>'+gavg2+'</td><td>'+zavg0+'</td><td>'+zavg1+'</td><td>'+zavg2+'</td><td>'+zavg3+'</td><td>'+zavg4+'</td><td>'+zavg5+'</td><td>'+zavg6+'</td><td>'+zavg7+'</td><td>'+zavg8+'</td><td>'+kavg0+'</td><td>'+kavg3+'</td><td>'+kavg6+'</td><td>'+kavg9+'</td><td>'+kavg1+'</td><td>'+kavg4+'</td><td>'+kavg7+'</td><td>'+kavg2+'</td><td><span class="lger">'+kavg5+'</span></td><td class="td-segment-r">'+kavg8+'</td><td class="td-segment-r"></td></tr>';
	return AVGLOST_Html;
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
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4] ],
		sum : 0
	};
	result.sum += numArr[3].toInt()+numArr[4].toInt();
	return result;
}

/**
 * 初始化HASH数据
 */
function initHash(){
	DATA_HASH = new Hash();
	YNUMS.each(function(ball){
		Y_HASH.set(ball,0);
		MAXMISS_Y_HASH.set(ball,0);
		CURLOST_Y_HASH.set(ball,0);
		CURPANO_Y_HASH.set(ball,0);
		MAXPANO_Y_HASH.set(ball,0);		
		LPANO_Y_HASH.set(ball,0);
	});
	XT_NUMS.each(function(ball){
		XT_HASH.set(ball,0);
		
		MAXMISS_XT_HASH.set(ball,0);
		CURLOST_XT_HASH.set(ball,0);
		CURPANO_XT_HASH.set(ball,0);
		MAXPANO_XT_HASH.set(ball,0);
		LPANO_XT_HASH.set(ball,0);

	});
	GS_NUMS.each(function(ball){
		CURLOST_G_HASH.set(ball,0);
		MAXMISS_G_HASH.set(ball,0);
		AVEMISS_G_HASH.set(ball,0);
		CURPANO_G_HASH.set(ball,0);
		MAXPANO_G_HASH.set(ball,0);
		LPANO_G_HASH.set(ball,0);

		CURLOST_S_HASH.set(ball,0);
		MAXMISS_S_HASH.set(ball,0);
		AVEMISS_S_HASH.set(ball,0);
		CURPANO_S_HASH.set(ball,0);
		MAXPANO_S_HASH.set(ball,0);
		LPANO_S_HASH.set(ball,0);

	});
	YSB = '';
}

/**
 * 计算一期个位十位遗漏数据
 * @param issue
 * @param result
 * @returns
 */
function buildGSItem(issue,result){
	var shi = result.nums[3].toInt();
	var ge = result.nums[4].toInt();
	
	//形态
	var xt = shi%3+""+ge%3;
	CURPANO_XT_HASH.set(xt,CURPANO_XT_HASH.get(xt)+1);
	LPANO_XT_HASH.set(xt,LPANO_XT_HASH.get(xt)+1);
	XT_HASH.set(xt,0);
	
	XT_NUMS.each(function(ball){			
		if(ball!=xt){
			XT_HASH.set(ball,XT_HASH.get(ball)+1);
			CURLOST_XT_HASH.set(ball,CURLOST_XT_HASH.get(ball)+1);
			LPANO_XT_HASH.set(ball,0);
		}
	});
	if(MAXMISS_XT_HASH.get(xt)<CURLOST_XT_HASH.get(xt)){
		MAXMISS_XT_HASH.set(xt,CURLOST_XT_HASH.get(xt));
	}
	if(MAXPANO_XT_HASH.get(xt)<LPANO_XT_HASH.get(xt)){
		MAXPANO_XT_HASH.set(xt,LPANO_XT_HASH.get(xt));
	}
	CURLOST_XT_HASH.set(xt,0);
	//余数 十位个位
	calYS(shi,ge);
	
	html = buildMissItemHTML(issue,result);
	return html;
}


function calYS(shi,ge){
	var sys = shi%3;
	var gys = ge%3;
	CURPANO_Y_HASH.set(ge,CURPANO_Y_HASH.get(ge)+1);
	CURPANO_Y_HASH.set(shi,CURPANO_Y_HASH.get(shi)+1);
	LPANO_Y_HASH.set(ge,LPANO_Y_HASH.get(ge)+1);
	LPANO_Y_HASH.set(shi,LPANO_Y_HASH.get(shi)+1);

	YNUMS.each(function(ball){
		if(ball.toInt()!=ge&&ball.toInt()!=shi){
			Y_HASH.set(ball.toInt(),Y_HASH.get(ball.toInt())+1);
			CURLOST_Y_HASH.set(ball.toInt(),CURLOST_Y_HASH.get(ball.toInt())+1);
			LPANO_Y_HASH.set(ball.toInt(),0);
		}
	}); 
	if(MAXMISS_Y_HASH.get(ge)<CURLOST_Y_HASH.get(ge)){
		MAXMISS_Y_HASH.set(ge,CURLOST_Y_HASH.get(ge));
	}	
	if(MAXMISS_Y_HASH.get(shi)<CURLOST_Y_HASH.get(shi)){
		MAXMISS_Y_HASH.set(shi,CURLOST_Y_HASH.get(shi));
	}
	if(MAXPANO_Y_HASH.get(ge)<LPANO_Y_HASH.get(ge)){
		MAXPANO_Y_HASH.set(ge,LPANO_Y_HASH.get(ge));
	}	
	if(MAXPANO_Y_HASH.get(shi)<LPANO_Y_HASH.get(shi)){
		MAXPANO_Y_HASH.set(shi,LPANO_Y_HASH.get(shi));
	}
	
	CURLOST_Y_HASH.set(ge,0);
	CURLOST_Y_HASH.set(shi,0);
	Y_HASH.set(ge,0);
	Y_HASH.set(shi,0);

	calGsNums(sys,3);
	calGsNums(gys,4);
	
}


function calGsNums(ys,pos){
	if(pos==3){
		CURPANO_S_HASH.set(ys,CURPANO_S_HASH.get(ys)+1);
		LPANO_S_HASH.set(ys,LPANO_S_HASH.get(ys)+1);

		GS_NUMS.each(function(ball){
			if(ball.toInt()!=ys){
				CURLOST_S_HASH.set(ball.toInt(),CURLOST_S_HASH.get(ball.toInt())+1);	
				LPANO_S_HASH.set(ball.toInt(),0);
			}
		});
		if(MAXMISS_S_HASH.get(ys)<CURLOST_S_HASH.get(ys)){
			MAXMISS_S_HASH.set(ys,CURLOST_S_HASH.get(ys));
		}
		if(MAXPANO_S_HASH.get(ys)<LPANO_S_HASH.get(ys)){
			MAXPANO_S_HASH.set(ys,LPANO_S_HASH.get(ys));
		}
		CURLOST_S_HASH.set(ys,0);
	}else if(pos==4){
		//个位最大遗漏
		CURPANO_G_HASH.set(ys,CURPANO_G_HASH.get(ys)+1);
		LPANO_G_HASH.set(ys,LPANO_G_HASH.get(ys)+1);
		GS_NUMS.each(function(ball){
			if(ball.toInt()!=ys){
				CURLOST_G_HASH.set(ball.toInt(),CURLOST_G_HASH.get(ball.toInt())+1);	
				LPANO_G_HASH.set(ball.toInt(),0);
			}
		});
		if(MAXMISS_G_HASH.get(ys)<CURLOST_G_HASH.get(ys)){
			MAXMISS_G_HASH.set(ys,CURLOST_G_HASH.get(ys));
		}
		if(MAXPANO_G_HASH.get(ys)<LPANO_G_HASH.get(ys)){
			MAXPANO_G_HASH.set(ys,LPANO_G_HASH.get(ys));
		}
		CURLOST_G_HASH.set(ys,0);
	}
	var count0=0;
	var count1=0;
	var count2=0;
	['0','3','6','9'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count0++;
		}
	});
	['1','4','7'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count1++;
		}
	});
	['2','5','8'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count2++;
		}
	});
	YSB = count0+":"+count1+":"+count2;
}
/**
 * 构建一期遗漏数据的HTML代码
 * 
 * @param issue - (<em>string</em>) 期号
 * @param missItem - (<em>object</em>) 遗漏数据
 * @returns {string} - 遗漏数据的HTML代码
 */
function buildMissItemHTML(issue, missItem) {
	var period = issue.substring(4,8)+'-'+issue.substring(8,issue.length);
	var z1 = missItem.nums[3].toInt()+""+missItem.nums[4].toInt();
	var z2 = missItem.nums[0].toInt()+""+missItem.nums[1].toInt()+""+missItem.nums[2].toInt()+"";
	var r = missItem.nums[0].toInt()+""+missItem.nums[1].toInt()+""+missItem.nums[2].toInt()+""+missItem.nums[3].toInt()+""+missItem.nums[4].toInt();
	var g0 = CURLOST_G_HASH.get("0")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">0</span>':CURLOST_G_HASH.get("0");
	var g1 = CURLOST_G_HASH.get("1")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">1</span>':CURLOST_G_HASH.get("1");
	var g2 = CURLOST_G_HASH.get("2")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">2</span>':CURLOST_G_HASH.get("2");
	
	var s0 = CURLOST_S_HASH.get("0")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">0</span>':CURLOST_S_HASH.get("0");
	var s1 = CURLOST_S_HASH.get("1")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">1</span>':CURLOST_S_HASH.get("1");
	var s2 = CURLOST_S_HASH.get("2")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">2</span>':CURLOST_S_HASH.get("2");
		
	var xt0 = XT_HASH.get("00")==0?'<span class="n-ssq">00</span>':XT_HASH.get("00");
	var xt1 = XT_HASH.get("01")==0?'<span class="n-ssq">01</span>':XT_HASH.get("01");
	var xt2 = XT_HASH.get("02")==0?'<span class="n-ssq">02</span>':XT_HASH.get("02");
	var xt3 = XT_HASH.get("10")==0?'<span class="n-ssq">10</span>':XT_HASH.get("10");
	var xt4 = XT_HASH.get("11")==0?'<span class="n-ssq">11</span>':XT_HASH.get("11");
	var xt5 = XT_HASH.get("12")==0?'<span class="n-ssq">12</span>':XT_HASH.get("12");
	var xt6 = XT_HASH.get("20")==0?'<span class="n-ssq">20</span>':XT_HASH.get("20");
	var xt7 = XT_HASH.get("21")==0?'<span class="n-ssq">21</span>':XT_HASH.get("21");
	var xt8 = XT_HASH.get("22")==0?'<span class="n-ssq">22</span>':XT_HASH.get("22");
	
	
	var ys0 = CURLOST_Y_HASH.get("0")==0?'<span class="n-xie">0</span>':CURLOST_Y_HASH.get("0");
	var ys1 = CURLOST_Y_HASH.get("1")==0?'<span class="n-xie">1</span>':CURLOST_Y_HASH.get("1");
	var ys2 = CURLOST_Y_HASH.get("2")==0?'<span class="n-xie">2</span>':CURLOST_Y_HASH.get("2");
	var ys3 = CURLOST_Y_HASH.get("3")==0?'<span class="n-xie">3</span>':CURLOST_Y_HASH.get("3");
	var ys4 = CURLOST_Y_HASH.get("4")==0?'<span class="n-xie">4</span>':CURLOST_Y_HASH.get("4");
	var ys5 = CURLOST_Y_HASH.get("5")==0?'<span class="n-xie">5</span>':CURLOST_Y_HASH.get("5");
	var ys6 = CURLOST_Y_HASH.get("6")==0?'<span class="n-xie">6</span>':CURLOST_Y_HASH.get("6");
	var ys7 = CURLOST_Y_HASH.get("7")==0?'<span class="n-xie">7</span>':CURLOST_Y_HASH.get("7");
	var ys8 = CURLOST_Y_HASH.get("8")==0?'<span class="n-xie">8</span>':CURLOST_Y_HASH.get("8");
	var ys9 = CURLOST_Y_HASH.get("9")==0?'<span class="n-xie">9</span>':CURLOST_Y_HASH.get("9");

	var html = '<tr><td class="td-first" colspan="2" title="第' + period + '期开奖号码:'+r+'">'+period+'</td><td class="group1" title="第'+period+'期开奖号码:'+r+'">'+z2+'<font color="red">'+z1+'</font></td>';
	var sHtml = '<td class="" style="cursor: pointer;">'+s0+'</td><td class="" style="cursor: pointer;">'+s1+'</td><td class="td-segment-r" style="cursor: pointer;">'+s2+'</td>';
	var gHtml = '<td class="td-blue" style="cursor: pointer;">'+g0+'</td><td class="td-blue" style="cursor: pointer;">'+g1+'</td><td class="td-blue" style="cursor: pointer;">'+g2+'</td>';
	
	var ysHtml = '<td>'+ys0+'</td><td>'+ys3+'</td><td>'+ys6+'</td><td>'+ys9+'</td><td>'+ys1+'</td><td>'+ys4+'</td><td>'+ys7+'</td><td>'+ys2+'</td><td>'+ys5+'</td><td class="">'+ys8+'</td><td class="td-segment-r">'+YSB+'</td></tr>';
	var xtHtml = '<td>'+xt0+'</td><td>'+xt1+'</td><td>'+xt2+'</td><td>'+xt3+'</td><td>'+xt4+'</td><td>'+xt5+'</td><td>'+xt6+'</td><td>'+xt7+'</td><td>'+xt8+'</td>';
		
	return html+sHtml+gHtml+xtHtml+ysHtml;
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
		moniHTML += '<tr class="' + ((index++ % 2 == 0) ? 'trw' : 'trw') + '">' + MONI_SELECT + '</tr>';
	}

	var selectHTML = getSortSelectHTML(desc);
	var html = TAB_HEADER.replace(/@SELECT@/, selectHTML) + missHTML + moniHTML + maxMissItemHTML + avgMissItemHTML
			+ TAB_FOOTER.replace(/@SELECT@/, selectHTML);
	return html;
}


// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	
	[ 'gewei' ,'shiwei', 'kuadu' ].each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="'+hashName+'"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
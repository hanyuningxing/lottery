NUMS = [ '0','1','2','3','4','5','6','7','8','9'];
DX_NUMS = [ '00','01','02','03','10','11','12','13','20','21','22','23','30','31','32','33' ];
FB_NUMS = ['0','1','2','3'];
XIAO_NUMS = ['0','1','2','3','4'];
DA_NUMS = ['5','6','7','8','9'];
DAN_NUMS = ['1','3','5','7','9'];
SHUANG_NUMS = ['0','2','4','6','8'];
//大大	大小	大单	大双	小大	小小	小单	小双	单大	单小	单单	单双	双大	双小	双单	双双
TAB_HEADER = '<table class="trendCharts trendSSQ" id="tableChart" cellspacing="0"><tbody><tr class="tr-first"><th colspan="2" rowspan="2" class="td-first" style="" title="点击排序" onclick="">期号</th><th rowspan="2">开奖号码</th><th colspan="16">大小单双</th><th colspan="4">十位</th><th colspan="4">个位</th><th colspan="4" class="td-last">大小单双分布</th></tr><tr class="tr-first"><th>大大</th><th>大小</th><th>大单</th><th>大双</th><th>小大</th><th>小小</th><th>小单</th><th>小双</th><th>单大</th><th>单小</th><th>单单</th><th>单双</th><th>双大</th><th>双小</th><th>双单</th><th class="td-segment-r">双双</th><th>大</th><th>小</th><th>单</th><th class="td-segment-r">双</th><th>大</th><th>小</th><th>单</th><th class="td-segment-r">双</th><th>大</th><th>小</th><th>单</th><th class="td-last">双</th></tr><tr>';
MONI_SELECT = '<tr><td colspan="3" rowspan="5" class="group2-3 td-segment-r" style="color:#000000;">模拟选号</td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双双</a></td>'+
				'<td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td></tr>'+
				'<tr><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双双</a></td>'+
				'<td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td></tr>'+

				'<tr><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双双</a></td>'+
				'<td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td></tr>'+

				'<tr><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双双</a></td>'+
				'<td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td></tr>'+

				'<tr><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">大双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">小双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">单双</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双大</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双小</a></td>'+
				'<td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双单</a></td><td class="sss"><a onclick="dxjo_click(this);return false;" style="cursor: pointer;">双双</a></td>'+
				'<td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td><td class="sss"></td></tr>';

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

//大小单双
DX_HASH = new Hash();
MAXMISS_DX_HASH = new Hash();
CURLOST_DX_HASH = new Hash();
CURPANO_DX_HASH = new Hash();
MAXPANO_DX_HASH = new Hash();
LPANO_DX_HASH = new Hash();

//分布
FB_HASH = new Hash();
MAXMISS_FB_HASH = new Hash();
CURLOST_FB_HASH = new Hash();
CURPANO_FB_HASH = new Hash();
MAXPANO_FB_HASH = new Hash();
LPANO_FB_HASH = new Hash();

PROPERTY_HASH = new Hash();
var dacount = 0;
var xiaocount = 0;
var dancount = 0;
var shuangcount = 0;

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
	
	html = TAB_HEADER+BODY+MONI_SELECT+"</table>";
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
	DUILOST = 0;
	HEZHI = 0;
	DATA_HASH = new Hash();
	
	DX_NUMS.each(function(ball){
		DX_HASH.set(ball,0);
		MAXMISS_DX_HASH.set(ball,0);
		CURLOST_DX_HASH.set(ball,0);
		CURPANO_DX_HASH.set(ball,0);
		MAXPANO_DX_HASH.set(ball,0);
		LPANO_DX_HASH.set(ball,0);
	});
	
	FB_NUMS.each(function(ball){
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
		
		FB_HASH.set(ball,0);
		MAXMISS_FB_HASH.set(ball,0);
		CURLOST_FB_HASH.set(ball,0);
		CURPANO_FB_HASH.set(ball,0);
		MAXPANO_FB_HASH.set(ball,0);
		LPANO_FB_HASH.set(ball,0);
	});
	PROPERTY_HASH.set("00","大大");
	PROPERTY_HASH.set("01","大小");
	PROPERTY_HASH.set("02","大单");
	PROPERTY_HASH.set("03","大双");
	PROPERTY_HASH.set("10","小大");
	PROPERTY_HASH.set("11","小小");
	PROPERTY_HASH.set("12","小单");
	PROPERTY_HASH.set("13","小双");
	PROPERTY_HASH.set("20","单大");
	PROPERTY_HASH.set("21","单小");
	PROPERTY_HASH.set("22","单单");
	PROPERTY_HASH.set("23","单双");
	PROPERTY_HASH.set("30","双大");
	PROPERTY_HASH.set("31","双小");
	PROPERTY_HASH.set("32","双单");
	PROPERTY_HASH.set("33","双双");
	PROPERTY_HASH.set("0","大");
	PROPERTY_HASH.set("1","小");
	PROPERTY_HASH.set("2","单");
	PROPERTY_HASH.set("3","双");

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
	
	//大小单双处理
	dacount = 0;
	xiaocount = 0;
	dancount = 0;
	shuangcount = 0;
	
	var dshi = 0;//大 十
	var dge = 0;//大 个
	var sshi = 3;//双 十
	var sge = 3;//双 个
	DA_NUMS.each(function(ball){
		if(ball.toInt()==shi){
			dshi=0;
			dacount++;
		}
	});
	DA_NUMS.each(function(ball){
		if(ball.toInt()==ge){
			dge=0;
			dacount++;
		}	
	});
	XIAO_NUMS.each(function(ball){
		if(ball.toInt()==shi){
			dshi=1;
			xiaocount++;
		}
	});
	XIAO_NUMS.each(function(ball){
		if(ball.toInt()==ge){
			dge=1;
			xiaocount++;
		}
	});
	if(shi%2==0){
		sshi=3;
		shuangcount++;
	}else{
		sshi=2;
		dancount++;
	}
	if(ge%2==0){
		sge=3;
		shuangcount++;
	}else{
		sge=2;
		dancount++;
	}
	calDXJO(true,dshi,sshi);
	calDXJO(false,dge,sge);

	var sgds = dshi.toString()+dge.toString();//十位大小+个位大小
	var sdgds = dshi.toString()+sge.toString();//十位大小+个位单双
	var ssgds = sshi.toString()+dge.toString();//十位单双+个位大小
	var sgd = sshi.toString()+ sge.toString();//十位单双+个位单双
	CURPANO_DX_HASH.set(sgds,CURPANO_DX_HASH.get(sgds)+1);
	CURPANO_DX_HASH.set(sdgds,CURPANO_DX_HASH.get(sdgds)+1);
	CURPANO_DX_HASH.set(ssgds,CURPANO_DX_HASH.get(ssgds)+1);
	CURPANO_DX_HASH.set(sgd,CURPANO_DX_HASH.get(sgd)+1);
	DX_NUMS.each(function(ball){
		if(ball!=sgds&&ball&&ball!=sdgds&&ball!=ssgds&&ball!=sgd){
			CURLOST_DX_HASH.set(ball,CURLOST_DX_HASH.get(ball)+1);
		}
	});
	
	if(MAXMISS_DX_HASH.get(sgds)<CURLOST_DX_HASH.get(sgds)){
		MAXMISS_DX_HASH.set(sgds,CURLOST_DX_HASH.get(sgds));
	}
	if(MAXMISS_DX_HASH.get(sdgds)<CURLOST_DX_HASH.get(sdgds)){
		MAXMISS_DX_HASH.set(sdgds,CURLOST_DX_HASH.get(sdgds));
	}
	if(MAXMISS_DX_HASH.get(ssgds)<CURLOST_DX_HASH.get(ssgds)){
		MAXMISS_DX_HASH.set(ssgds,CURLOST_DX_HASH.get(ssgds));
	}
	if(MAXMISS_DX_HASH.get(sgd)<CURLOST_DX_HASH.get(sgd)){
		MAXMISS_DX_HASH.set(sgd,CURLOST_DX_HASH.get(sgd));
	}
	CURLOST_DX_HASH.set(sgds,0);
	CURLOST_DX_HASH.set(sdgds,0);
	CURLOST_DX_HASH.set(ssgds,0);
	CURLOST_DX_HASH.set(sgd,0);
	
	html = buildMissItemHTML(issue,result);
	return html;
}

function calDXJO(shi,num1,num2){
	if(shi){
		CURPANO_S_HASH.set(num1,CURPANO_S_HASH.get(num1)+1);
		CURPANO_S_HASH.set(num2,CURPANO_S_HASH.get(num2)+1);

		FB_NUMS.each(function(ball){			
			if(ball!=num1&&ball!=num2){
				CURLOST_S_HASH.set(ball,CURLOST_S_HASH.get(ball)+1);
			}
		}); 
		if(MAXMISS_S_HASH.get(num1)<CURLOST_S_HASH.get(num1)){
			MAXMISS_S_HASH.set(num1,CURLOST_S_HASH.get(num1));
		}
		if(MAXMISS_S_HASH.get(num2)<CURLOST_S_HASH.get(num2)){
			MAXMISS_S_HASH.set(num2,CURLOST_S_HASH.get(num2));
		}
		CURLOST_S_HASH.set(num1,0);
		CURLOST_S_HASH.set(num2,0);
	}else{
		CURPANO_G_HASH.set(num1,CURPANO_G_HASH.get(num1)+1);
		CURPANO_G_HASH.set(num2,CURPANO_G_HASH.get(num2)+1);

		FB_NUMS.each(function(ball){			
			if(ball!=num1&&ball!=num2){
				CURLOST_G_HASH.set(ball,CURLOST_G_HASH.get(ball)+1);
			}
		}); 
		if(MAXMISS_G_HASH.get(num1)<CURLOST_G_HASH.get(num1)){
			MAXMISS_G_HASH.set(num1,CURLOST_G_HASH.get(num1));
		}
		if(MAXMISS_G_HASH.get(num2)<CURLOST_G_HASH.get(num2)){
			MAXMISS_G_HASH.set(num2,CURLOST_G_HASH.get(num2));
		}
		CURLOST_G_HASH.set(num1,0);
		CURLOST_G_HASH.set(num2,0);
	}
	
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
	
	var dxdsHtml = '';
	DX_NUMS.each(function(ball){
		var dxds = CURLOST_DX_HASH.get(ball)==0?'<span class="n-jiu">'+PROPERTY_HASH.get(ball)+'</span>':CURLOST_DX_HASH.get(ball);
		dxdsHtml+='<td class="" style="cursor: pointer;">'+dxds+'</td>';
	});
	
	var shiHtml = '';
	var geHtml = '';

	FB_NUMS.each(function(ball){
		var s=CURLOST_S_HASH.get(ball)==0?'<span class="n-gu">'+PROPERTY_HASH.get(ball)+'</span>':CURLOST_S_HASH.get(ball);
		shiHtml+='<td class="" style="cursor: pointer;">'+s+'</td>';
		var g=CURLOST_G_HASH.get(ball)==0?'<span class="n-gu">'+PROPERTY_HASH.get(ball)+'</span>':CURLOST_G_HASH.get(ball);
		geHtml+='<td class="" style="cursor: pointer;">'+g+'</td>';
	});

	var dxfbHtml = '';
	dxfbHtml=dxfbHtml+(dacount>0?'<td class="group2-2"><span class="n-big">'+dacount+'</span></td>':'<td class="group2-2">'+dacount.toString())+'</td>';
	dxfbHtml=dxfbHtml+(xiaocount>0?'<td class="group2-2"><span class="n-big">'+xiaocount+'</span></td>':'<td class="group2-2">'+xiaocount.toString())+'</td>';
	dxfbHtml=dxfbHtml+(dancount>0?'<td class="group2-2"><span class="n-big">'+dancount+'</span></td>':'<td class="group2-2">'+dancount.toString())+'</td>';
	dxfbHtml=dxfbHtml+(shuangcount>0?'<td class="group2-2"><span class="n-big">'+shuangcount+'</span></td>':'<td class="group2-2">'+shuangcount.toString())+'</td>';

	
	var html = '<tr><td class="td-first" colspan="2" title="第' + period + '期开奖号码:'+r+'">'+period+'</td><td class="group1" title="第'+period+'期开奖号码:'+r+'">'+z2+'<font color="red">'+z1+'</font></td>';
			
	return html+dxdsHtml+shiHtml+geHtml+dxfbHtml;
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

function dxjo_click(el) {
	var className = 'trchoseo';
	el = $(el);
	if (el.hasClass(className)) {
		el.removeClass(className);
	} else {
		el.addClass(className);
	}
}
DATA_HASH = new Hash();// 历史开奖数据
CACHE = null;// 遗漏数据缓存，启用页面缓存：CACHE = new Hash();
MISS_EL = null;// 遗漏数据展示的页面标签对象
HISTORY_MISS_HASH = new Hash();// 历史遗漏数据

var TotalMissHash=new Hash();
var maxMissHash=new Hash();
var issueSize=0;
var lastMissHash=new Hash();
var currentMissHash=new Hash();
var tempMissHash=new Hash();
var lastIssue;
/**
 * 初始化DATA_HASH,将历史开奖数据转为Hash存储，方便访问
 */
function initDataHash() {
	var dataObj = dataQyh;
	var prevResult = null;
	for ( var issue in dataObj) {
		var result = genResult(dataObj[issue], issue, prevResult);
		DATA_HASH.set(issue, result);
		prevResult = result;
		lastIssue=issue;
	}
}

/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 * @returns {object} - 开奖结果对象
 */
function genResult(numStr) {
	var numArr = numStr.split(/\s+/);
	var result = [numArr[0],numArr[1],numArr[2],numArr[3],numArr[4]];
	return result;
}

/**
* 初始化历史遗漏数据，将提供的数据，转换成一致的格式存储
*/
function initHistoryMissHash() {
	var missData =miss_data;
	var count=0;
	for ( var issue in missData) {
		HISTORY_MISS_HASH.set(issue, buildHistoryMissItem(issue, missData[issue]));
		if(count==missData.length-2){
			lastMissHash=HISTORY_MISS_HASH.get(issue);
		}
		if(count==missData.length-1){
			currentMissHash=HISTORY_MISS_HASH.get(issue);
		}
			
	}
}

/**
* 构建一期的历史遗漏数据
* 
* @param issue - (<em>string</em>) 期号
* @param missObj - (<em>object</em>) 历史遗漏数据对象
* @returns {object} - 该期的历史遗漏数据
*/
function buildHistoryMissItem(issue, missStr) {
	var missItem = {};
	missItem.numMissHash=new Hash();
	for(var num in missStr){
		missItem.numMissHash.set(num,missStr[num]);
		var total=TotalMissHash.get(num);
		if(total==null)
			total=0;
		if(missStr[num]==0){
			TotalMissHash.set(num,total+1);
			lastMissHash.set(num,tempMissHash.get(num));
		}else{
			tempMissHash.set(num,missStr[num]);//存储本期遗漏
		}
		var maxMiss=maxMissHash.get(num);
		if(maxMiss==null){
			maxMiss=0;
		}
		if(maxMiss<missStr[num]){
			maxMissHash.set(num,missStr[num]);
		}
		currentMissHash=missItem.numMissHash;//存储本期遗漏
		
	}
	issueSize++;
	return missItem;
};

function getHtml(){
	var html='';
	var className='';
	var num;
	var total;
	var shouldShow=(issueSize/23).toFixed(2);
	for(var i=1;i<=23;i++){
		className=i%2==0?'trw':'trgray';
		num=i<10?'0'+i:i;
		total=TotalMissHash.get(i);
		if(total==null){
			total=0;
		}
		avgMiss=((issueSize-total)/(total+1)).toFixed(2);
		lastMiss=lastMissHash.get(i)==null?0:lastMissHash.get(i);
		html +='<tr class="'+className+'" height="20">';
		html +='<td>'+num+'</td>';
		html +='<td> '+total+' </td>';
		html +='<td> '+shouldShow+' </td>';
		html +='<td>'+(total*100/issueSize).toFixed(2)+'% </td>';
		html +='<td  >'+avgMiss+' </td>';
		html +='<td>'+maxMissHash.get(i)+' </td>';
		html +='<td>'+lastMiss+'</td>';
		html +='<td > '+currentMissHash.get(i)+' </td>';
		html +='<td  >'+(currentMissHash.get(i)*100/avgMiss).toFixed(2)+'% </td>';
		html +='<td  >'+(currentMissHash.get(i)/23).toFixed(2)+' </td>';
		html +='<td>'+((lastMiss-currentMissHash.get(i))*100/23).toFixed(2)+'%</td>';
		html +='</tr>';
	}
	var TABLEHEAD='<table width="980" border="0" cellpadding="1" cellspacing="1" class="zstablegraybg333" ><tr class="zsttrpinklig" height="20">  <td>号码</td> <td>出现次数</td> <td>理论出现次数</td>   <td >出现概率</td>  <td >平均遗漏</td>  <td >最大遗漏</td>  <td >上期遗漏</td>  <td >本期遗漏</td>  <td >欲出几率</td>  <td >投资价值</td><td >回补几率</td></tr>';
	var TABLEEND='<tr class="zsttrpinklig" height="20">  <td>号码</td>  <td>出现次数</td><td>理论出现次数</td>  <td >出现概率</td>  <td >平均遗漏</td>  <td >最大遗漏</td>  <td >上期遗漏</td>  <td >本期遗漏</td>  <td >欲出几率</td>  <td >投资价值</td>  <td >回补几率</td></tr></table>';
	html =TABLEHEAD+html+TABLEEND;
	$('MISS_EL').innerHTML=html;
	$('lastIssue').innerHTML=lastIssue;
	$('totalIssue').innerHTML=issueSize;
}
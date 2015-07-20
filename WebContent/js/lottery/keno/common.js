/**
 * 截止倒计时
 * @param o
 * o = {
 * 	url,	//请求地址
 * 	iid,	//显示期数标签ID
 * 	tid		//显示时间标签ID
 * }
 * @return
 */
var Keno_Timer = {
	o:{
		end:true,
		chase_url:'',
		turl:'',
		burl:'',
		url:'',
		iid:'',
		tid:'',
		lid:'',
		lrid:'',
		labIssueNumber:{},
		labTimer:{},
		createFormPeriodId:{}
	},
	srvData:{
		periodId:'',
		issueNumber:'',
		lastResult:'',
		keno_service_time:0,
		stateValue:0,
		stateName:'',
		endTime:0,
		beforeTime:0,
	    leftTime:0
	}
};

function countDown(o){
	Keno_Timer.o = o;
	Keno_Timer.o.labIssueNumber = document.getElementById(Keno_Timer.o.iid);
	Keno_Timer.o.labTimer = document.getElementById(Keno_Timer.o.tid);
	Keno_Timer.o.lastResultIssueNumber = document.getElementById(Keno_Timer.o.lid);
	Keno_Timer.o.lastResult = document.getElementById(Keno_Timer.o.lrid);
	Keno_Timer.o.lastResultTime = document.getElementById(Keno_Timer.o.lrt);
	Keno_Timer.o.createFormPeriodId = document.getElementById('createFormPeriodId');
	getFromServer();
	showTimer();
}
function getServiceTime(){
	$.ajax({ 
		type: 'GET',
		cache : false,
		url: Keno_Timer.o.turl,
		success : function(data, textStatus) {
		     Keno_Timer.srvData.keno_service_time = parseInt(data, 10);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			///如果报错。或者是服务器关闭。。。
			Keno_Timer.srvData.keno_service_time = parseInt(new Date().getTime(), 10);
		}
	});
}
function getFromServer(){
	$.ajax({ 
		type: 'GET',
		cache : false,
		dataType : 'json',
		url: Keno_Timer.o.url,
		success : function(jsonObj, textStatus) {
				$.ajax({ 
					type: 'GET',
					cache : false,
					dataType : 'text',
					url: Keno_Timer.o.turl,
					success : function(data, textStatus) {
					     Keno_Timer.srvData.keno_service_time = parseInt(data, 10);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
				keno_time_js_local=jsonObj;
                Keno_Timer.srvData.periodId = jsonObj.periodId+'';
				Keno_Timer.srvData.issueNumber = jsonObj.issueNumber+'';
				Keno_Timer.srvData.lastResultIssueNumber = jsonObj.lastResultIssue+'';
				Keno_Timer.srvData.stateValue = parseInt(jsonObj.stateValue, 10);
				Keno_Timer.srvData.stateName = jsonObj.stateName+'';
				Keno_Timer.srvData.endTime = parseInt(jsonObj.endTime, 10);
				Keno_Timer.srvData.beforeTime = parseInt(jsonObj.beforeTime, 10);
				if(null!=jsonObj.lastResultIssue&&null!=jsonObj.lastResult){
					if(jsonObj.lastResult.indexOf(",")!=-1){
						var resultArr = jsonObj.lastResult.split(",");
						var resultHtml = '';
						for(var i=0;i<resultArr.length;i++){
							resultHtml+="<li>"+resultArr[i]+"</li>";
						}
						Keno_Timer.o.lastResult.innerHTML = resultHtml;
					}
					Keno_Timer.o.lastResultTime.innerHTML = jsonObj.lastResultTime;
					Keno_Timer.o.lastResultIssueNumber.innerHTML = jsonObj.lastResultIssue;
				}
				Keno_Timer.o.labIssueNumber.innerHTML = Keno_Timer.srvData.issueNumber;
				Keno_Timer.o.createFormPeriodId.value = Keno_Timer.srvData.periodId;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}
var jiezhiPeriodId;
var jiezhiIssueNumber;
var jiezhiUpdate=true;
var chaseUpdate=false;
var leftTime_count=0;
function showTimer(){
	setInterval(function(){
		try{
			if(leftTime_count<=0||leftTime_count<10000){
				///最后5秒每秒更新一次
					getFromServer();
			}else if((leftTime_count>60000&&leftTime_count%60000<800)||(leftTime_count<=60000&&leftTime_count%5000<800)){
					///最后1分钟每5秒更新一次
					///大于每1分钟更新一次
					getFromServer();
			}else{
				Keno_Timer.srvData.endTime = Keno_Timer.srvData.endTime-1000;
			}
			if(Keno_Timer.srvData.endTime==0||Keno_Timer.srvData.beforeTime==0||Keno_Timer.srvData.keno_service_time==0){
				return;
			}
			Keno_Timer.srvData.leftTime = Keno_Timer.srvData.endTime-Keno_Timer.srvData.keno_service_time-Keno_Timer.srvData.beforeTime*60000;
			leftTime_count = Keno_Timer.srvData.leftTime;
			if(Keno_Timer.srvData.leftTime <= 1){
				/////已经截至销售
				if(jiezhiUpdate){
						///确保只记录一次截至信息。在下一期开售的时候才打开
						Keno_Timer.o.labTimer.innerHTML = '已截止';
						jiezhiUpdate=false;
						jiezhiPeriodId=Keno_Timer.srvData.periodId;
						jiezhiIssueNumber=Keno_Timer.srvData.issueNumber;
						var createForm_chase = document.getElementById("createForm_chase");
						if(createForm_chase&&createForm_chase.checked){
							///有追号选
							chaseUpdate=true;
						}
				}
			}else{
				if(Keno_Timer.srvData.leftTime > 0){
					if(!jiezhiUpdate){
						///如果截至锁是关的。把他打开
						jiezhiUpdate=true;
					}
					if(chaseUpdate){
						chaseUpdate=false;
						chgChase(true,Keno_Timer.o.chase_url);//把追号列表刷新
					}
					if(null!=jiezhiPeriodId&&Keno_Timer.srvData.periodId!=jiezhiPeriodId){
						jiezhiPeriodId=null;
						var createForm = getCreateForm();
						var units = createForm.elements['createForm.units'].value;
						if(units>0){
							window.alert("您好,"+jiezhiIssueNumber+"期已经结束销售,当前期是"+Keno_Timer.srvData.issueNumber+",请您投注时候确认期号投注");
						}
					}
					var hour = parseInt(Keno_Timer.srvData.leftTime/3600000,10);
					var min = parseInt(Keno_Timer.srvData.leftTime%3600000/60000,10);
					var sec = parseInt(Keno_Timer.srvData.leftTime%60000/1000,10);
					var strTime = '';
					if(hour > 0){
						strTime = hour + ':' + (min<10?'0'+min:min) + ':' + (sec<10?'0'+sec:sec);
					}else{
						strTime = (min<10?'0'+min:min) + ':' + (sec<10?'0'+sec:sec);
					}
					Keno_Timer.o.labTimer.innerHTML = strTime;
				}
			}
		}catch(e){}
	},1000);
}
function loadRightContent(key){
	loadRightContentMethod(key);
	setInterval(function(){
		loadRightContentMethod(key);
	},60000);
}
var linebg = '<div class="linebg1"></div>';
var table_header = '<table width="243" border="0" cellspacing="1" cellpadding="0"><tr align="center" valign="middle">';
function get_max_miss(name,arrb,arrs){
     var htmlTemp = linebg;
     htmlTemp +=table_header;
     htmlTemp +='<td rowspan="3"  class="redboldchar">'+name+'</td>';
     htmlTemp +='<td  class="redc">热</td>';
     $.each(arrb,function(idx,item){ 
		    htmlTemp +='<td width="26" ><span class="redballsingle">'+item.name+'</span></td><td ><span class="tstip">'+item.value+'</span></td>';
	   });
	   htmlTemp +='</tr><tr align="center" valign="middle"><td colspan="7" ><div class="linebg1"></div></td></tr>';
	   htmlTemp +='<tr align="center" valign="middle"><td >冷</td>';
	   $.each(arrs,function(idx,item){ 
		    htmlTemp +='<td width="26" ><span class="grayballsingle">'+item.name+'</span></td><td ><span class="tstip">'+item.value+'</span></td>';
	   });
     htmlTemp +='</tr></table>';
     return htmlTemp;
	   
}
function loadRightContentMethod(key){
	try{
		$.ajax({ 
				type: 'GET',
				cache : false,
				dataType : 'json',
				url: window.BASESITE +'/keno/'+key+'/info.js',
				success : function(jsonObj, textStatus) {
					try {
						var resultList= jsonObj.resultList;
						for ( var i = 0; i < resultList.length; i++) {
                                      var resultList_ =  document.getElementById("resultList_"+i);
                                      $(resultList_).empty();
                                      var cell=resultList_.insertCell(0);  
						              cell.setAttribute("height","23"); 
						              cell.setAttribute("class","trw"); 
						              cell.innerHTML=resultList[i].periodNumber;
						              
						              cell=resultList_.insertCell(1);  
						              cell.innerHTML=resultList[i].prizeTimeFormat;
						              
						              cell=resultList_.insertCell(2);  
						              cell.setAttribute("class","redc"); 
						              cell.innerHTML=resultList[i].resultFormat;

                        }
                        var newWon= jsonObj.newWon;
                        var head_new_won_html="";
						for ( var i = 0; i < newWon.length; i++) {
							          var newWon_ =  document.getElementById("newWon_"+i);
							          $(newWon_).empty();
		                              var cell=newWon_.insertCell(0);  
						              cell.setAttribute("height","23"); 
						              cell.setAttribute("class","trw"); 
						              cell.innerHTML=newWon[i].sponsorName;
						              
						              cell=newWon_.insertCell(1);  
						              cell.innerHTML=newWon[i].betTypeString;
						              
						              cell=newWon_.insertCell(2);  
						              cell.setAttribute("class","redc"); 
						              cell.innerHTML=newWon[i].prize;
						              if(i<3){
						                  head_new_won_html=newWon[i].sponsorName+"   "+newWon[i].betTypeString+" <span class=\"redc\">喜中"+newWon[i].prize+"元</span>";
					                  }
						}
						document.getElementById("keno_head_new_won").innerHTML=head_new_won_html;
						
						//日榜
						var todayWon= jsonObj.todayWon;
						for ( var i = 0; i < todayWon.length; i++) {
							  var todayWon_ =  document.getElementById("todayWon_"+i);
							  $(todayWon_).empty();
                              var cell=todayWon_.insertCell(0);  
				              cell.setAttribute("height","23"); 
				              cell.setAttribute("class","trw"); 
				              cell.innerHTML=(i+1);
				              
				              cell=todayWon_.insertCell(1);  
				              cell.innerHTML=todayWon[i].sponsorName;
				              
				              cell=todayWon_.insertCell(2);  
				              cell.setAttribute("class","redc"); 
				              cell.innerHTML=todayWon[i].prize;
                        }
						
						//周榜
						var weekWon= jsonObj.weekWon;
						for ( var i = 0; i < weekWon.length; i++) {
							  var weekWon_ =  document.getElementById("weekWon_"+i);
							  $(weekWon_).empty();
                              var cell=weekWon_.insertCell(0);  
				              cell.setAttribute("height","23"); 
				              cell.setAttribute("class","trw"); 
				              cell.innerHTML=(i+1);
				              
				              cell=weekWon_.insertCell(1);  
				              cell.innerHTML=weekWon[i].sponsorName;
				              
				              cell=weekWon_.insertCell(2);  
				              cell.setAttribute("class","redc"); 
				              cell.innerHTML=weekWon[i].prize;
                        }
						
						
						//月榜
						var monthWon= jsonObj.monthWon;
						for ( var i = 0; i < monthWon.length; i++) {
							  var monthWon_ =  document.getElementById("monthWon_"+i);
							  $(monthWon_).empty();
                              var cell=monthWon_.insertCell(0);  
				              cell.setAttribute("height","23"); 
				              cell.setAttribute("class","trw"); 
				              cell.innerHTML=(i+1);
				              
				              cell=monthWon_.insertCell(1);  
				              cell.innerHTML=monthWon[i].sponsorName;
				              
				              cell=monthWon_.insertCell(2);  
				              cell.setAttribute("class","redc"); 
				              cell.innerHTML=monthWon[i].prize;
                        }
						
						
						//总榜
                        var sumWon= jsonObj.sumWon;
						for ( var i = 0; i < sumWon.length; i++) {
							  var sumWon_ =  document.getElementById("sumWon_"+i);
							  $(sumWon_).empty();
                              var cell=sumWon_.insertCell(0);  
				              cell.setAttribute("height","23"); 
				              cell.setAttribute("class","trw"); 
				              cell.innerHTML=(i+1);
				              
				              cell=sumWon_.insertCell(1);  
				              cell.innerHTML=sumWon[i].sponsorName;
				              
				              cell=sumWon_.insertCell(2);  
				              cell.setAttribute("class","redc"); 
				              cell.innerHTML=sumWon[i].prize;
                        }
					
						 
						
						//2011-5-20 高频彩遗漏更新
						var miss_data = null;
						if(typeof(qyh_max_miss)!="undefined"){ 
						     miss_data = qyh_max_miss;
						}
						if(null!=miss_data){
					           jsonArr = eval(miss_data);
					           var htmlTemp ='';
					           htmlTemp = get_max_miss('第<br />一<br />位',jsonArr['num_1_b'],jsonArr['num_1_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />二<br />位',jsonArr['num_2_b'],jsonArr['num_2_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />三<br />位',jsonArr['num_3_b'],jsonArr['num_3_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />四<br />位',jsonArr['num_4_b'],jsonArr['num_4_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />五<br />位',jsonArr['num_5_b'],jsonArr['num_5_s']);
					           var max_miss_div = document.getElementById('max_miss_div');
					           max_miss_div.innerHTML=htmlTemp;
					    }
						
						var s_miss_data = null;
						if(typeof(ssc_max_miss)!="undefined"){ 
						     s_miss_data = ssc_max_miss;
						}
						if(null!=s_miss_data){
					           jsonArr = eval(s_miss_data);
					           var htmlTemp ='';
					           htmlTemp = get_max_miss('第<br />一<br />位',jsonArr['num_1_b'],jsonArr['num_1_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />二<br />位',jsonArr['num_2_b'],jsonArr['num_2_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />三<br />位',jsonArr['num_3_b'],jsonArr['num_3_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />四<br />位',jsonArr['num_4_b'],jsonArr['num_4_s']);
					           htmlTemp = htmlTemp+get_max_miss('第<br />五<br />位',jsonArr['num_5_b'],jsonArr['num_5_s']);
					           var max_miss_div = document.getElementById('max_miss_div');
					           max_miss_div.innerHTML=htmlTemp;
					    }
						
						
						var sdel11to5_miss_data = null;
						if(typeof(sdel11to5_max_miss)!="undefined"){ 
						     sdel11to5_miss_data = sdel11to5_max_miss;
						}
						if(null!=sdel11to5_miss_data){
					           jsonArr = eval(sdel11to5_miss_data);
					           var htmlTemp ='';
					           htmlTemp = get_max_miss('号码',jsonArr['numb'],jsonArr['nums']);
					           var max_miss_div = document.getElementById('max_miss_div');
					           max_miss_div.innerHTML=htmlTemp;
					    }
						
					} catch (e) {
						//错误处理
						//window.alert(e);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
	}catch(e){}
}  
function submitKenoCreateForm() {
	//判断是否为智能追号
	if("true"==document.getElementById('isCapacity').value) {
		var totalCostOfChase = document.getElementById("createForm_capacityChase_totalCostOfChase").value;
		if(totalCostOfChase<=0) {
			window.alert("请先计算方案倍数，再进行投注！");
			return false;
		} else {
			document.getElementById('createForm_multiple').readOnly=false;
		}
	}
	
	if(jiezhiUpdate&&null!=jiezhiUpdate){
		//加入高频彩截至控制
		if(!jiezhiUpdate){
			if(jiezhiIssueNumber&&null!=jiezhiIssueNumber){
               window.alert(jiezhiIssueNumber+"期已截止投注");
			}else{
				window.alert("本期已截止投注");
			}
			return false;
			
		}else{
			submitCreateForm();
		}
	}
}
/**
 * 计数组合数
 */
function comp(r, n) {
	var C = 1;
	for ( var i = n - r + 1; i <= n; i++) {
		C = C * i;
	}
	for ( var j = 2; j <= r; j++) {
		C = C / j;
	}
	return C;
}

function comb(m, n) {
	if (m < 0 || n < 0 || m < n) {
		return 0;
	}
	n = n < (m - n) ? n : m - n;
	if (n == 0) {
		return 1;
	}
	var result = m;
	for (var i = 2, j = result - 1; i <= n; i++, j--) {
		result = result * j / i;// 得到C(m,i)
	}
	return result;
}


function comPL(m, n) {
	if (m < 0 || n < 0 || m < n) {
		return 0;
	}
	var result = m;
	for (var i = 2, j = result - 1; i <= n; i++, j--) {
		result = result * j // 得到A(m,i)
	}
	return result;
}
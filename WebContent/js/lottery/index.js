var SSQ_RED_BALL_ARR = [];
	for ( var i = 0; i < 33; i++) {
		var num = "";
		if(i + 1<10){
			num = "0"+(i + 1);
		}else{
			num = ""+(i + 1);
		}
		SSQ_RED_BALL_ARR[i] = num;
	}
var SSQ_BLUE_BALL_ARR = [];
	for ( var i = 0; i < 16; i++) {
		var num = "";
		if(i + 1<10){
			num = "0"+(i + 1);
		}else{
			num = ""+(i + 1);
		}
		SSQ_BLUE_BALL_ARR[i] = num;
	}
var DLT_RED_BALL_ARR = [];
	for ( var i = 0; i < 35; i++) {
		var num = "";
		if(i + 1<10){
			num = "0"+(i + 1);
		}else{
			num = ""+(i + 1);
		}
		DLT_RED_BALL_ARR[i] = num;
	}
var DLT_BLUE_BALL_ARR = [];
	for ( var i = 0; i < 12; i++) {
		var num = "";
		if(i + 1<10){
			num = "0"+(i + 1);
		}else{
			num = ""+(i + 1);
		}
		DLT_BLUE_BALL_ARR[i] = num;
	}
var W3D_BLUE_BALL_ARR = [];
	for ( var i = 0; i < 9; i++) {
		W3D_BLUE_BALL_ARR[i] = (i + 1);
	}
var randomSort = function() {
		var randomNum = Math.random();
		if (randomNum > 0.5)
			return 1;
		else if (randomNum < 0.5)
			return -1;
		else
			return 0;
};
function refreshRequestToken(form_name) {
	var form_ = document.getElementById(form_name);
	var request_token_el = form_.elements['request_token'];
	if (request_token_el != null)
		request_token_el.value = new Date().getTime();
}
//////////////////////////////////////////////////双色球
function ssqSubmit(){
	   $SSO.login_auth(function() {
	   	    var form_ssq = document.getElementById('form_ssq');
	   	    var redInputArr = $('#ssqNumRondom input[_name="red"]');
	   	    var betStr= "";
	   	    for(var i=0; i<redInputArr.length; i++) {
	   	    	if(i==redInputArr.length-1){
	   	    		betStr=betStr+redInputArr[i].value+"|";
	   	    	}else{
	   	    		betStr=betStr+redInputArr[i].value+",";
	   	    	}
	   	    }
			var blueInputArr = $('#ssqNumRondom input[_name="blue"]');
	   	    for(var i=0; i<blueInputArr.length; i++) {
	   	    	betStr=betStr+blueInputArr[i].value;
	   	    }
	   	    form_ssq.elements['createForm.quick_content'].value=betStr;
			var options = {
				type : 'POST',
				cache : false,
				data : {
					ajax : 'true'
				},
				success : function(data, textStatus) {
					var jsonObj = toJsonObject(data);
					var msg = getCommonMsg(jsonObj);
					if (jsonObj.success == true) {
						alert(msg);
						refreshRequestToken("form_ssq");
						if (jsonObj.redirectURL != null)
							window.location.href = jsonObj.redirectURL;
					} else {
						alert(msg);
						refreshRequestToken("form_ssq");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('提交请求失败.');
					refreshRequestToken("form_ssq");
				},
				beforeSend : function(XMLHttpRequest) {
					document.getElementById('ssq_span_createForm_submit').style.display = 'none';
					document.getElementById('ssq_span_createForm_waiting').style.display = '';
				},
				complete : function(XMLHttpRequest, textStatus) {
					document.getElementById('ssq_span_createForm_submit').style.display = '';
					document.getElementById('ssq_span_createForm_waiting').style.display = 'none';
				}
			};
			$(form_ssq).ajaxSubmit(options);
		});
}
function ssqRandomMethod(){
	var redInputArr = $('#ssqNumRondom input[_name="red"]');
	var blueInputArr = $('#ssqNumRondom input[_name="blue"]');
	var copyRedBallArr = SSQ_RED_BALL_ARR.slice(0, SSQ_RED_BALL_ARR.length);
	var copyBlueBallArr = SSQ_BLUE_BALL_ARR.slice(0, SSQ_BLUE_BALL_ARR.length);
	var redBall = copyRedBallArr.sort(randomSort).slice(0, 6).sort(asc);
	var blueBall = copyBlueBallArr.sort(randomSort).slice(0, 1).sort(asc);
	for(var i=0; i<redInputArr.length; i++) {
		var num = redBall[i];
		redInputArr[i].value = num;
	}
	for(var i=0; i<blueInputArr.length; i++) {
		var num = blueBall[i];
		blueInputArr[i].value = num;
	}
}
function stopSsqRandom(){
	clearInterval(time1);
}
function ssqRondom(){
	time1=setInterval("ssqRandomMethod()",Math.floor(Math.random()*50+50));
	refreshRequestToken("form_ssq");
	window.setTimeout("stopSsqRandom()",1000);
}
/////////////////////////////////////////////////////大乐透
function dltSubmit(){
	   $SSO.login_auth(function() {
	   	    var form_dlt = document.getElementById('form_dlt');
	   	    var redInputArr = $('#dltNumRondom input[_name="red"]');
	   	    var betStr= "";
	   	    for(var i=0; i<redInputArr.length; i++) {
	   	    	if(i==redInputArr.length-1){
	   	    		betStr=betStr+redInputArr[i].value+"|";
	   	    	}else{
	   	    		betStr=betStr+redInputArr[i].value+",";
	   	    	}
	   	    }
			var blueInputArr = $('#dltNumRondom input[_name="blue"]');
	   	    for(var i=0; i<blueInputArr.length; i++) {
	   	    	if(i==blueInputArr.length-1){
	   	    		betStr=betStr+blueInputArr[i].value;
	   	    	}else{
	   	    		betStr=betStr+blueInputArr[i].value+",";
	   	    	}
	   	    }
	   	    form_dlt.elements['createForm.quick_content'].value=betStr;
			var options = {
				type : 'POST',
				cache : false,
				data : {
					ajax : 'true'
				},
				success : function(data, textStatus) {
					var jsonObj = toJsonObject(data);
					var msg = getCommonMsg(jsonObj);
					if (jsonObj.success == true) {
						alert(msg);
						refreshRequestToken("form_dlt");
						if (jsonObj.redirectURL != null)
							window.location.href = jsonObj.redirectURL;
					} else {
						alert(msg);
						refreshRequestToken("form_dlt");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('提交请求失败.');
					refreshRequestToken("form_dlt");
				},
				beforeSend : function(XMLHttpRequest) {
					document.getElementById('dlt_span_createForm_submit').style.display = 'none';
					document.getElementById('dlt_span_createForm_waiting').style.display = '';
				},
				complete : function(XMLHttpRequest, textStatus) {
					document.getElementById('dlt_span_createForm_submit').style.display = '';
					document.getElementById('dlt_span_createForm_waiting').style.display = 'none';
				}
			};
			$(form_dlt).ajaxSubmit(options);
		});
}
function dltRandomMethod(){
	var redInputArr = $('#dltNumRondom input[_name="red"]');
	var blueInputArr = $('#dltNumRondom input[_name="blue"]');
	var copyRedBallArr = DLT_RED_BALL_ARR.slice(0, DLT_RED_BALL_ARR.length);
	var copyBlueBallArr = DLT_BLUE_BALL_ARR.slice(0, DLT_BLUE_BALL_ARR.length);
	var redBall = copyRedBallArr.sort(randomSort).slice(0, 5).sort(asc);
	var blueBall = copyBlueBallArr.sort(randomSort).slice(0, 2).sort(asc);
	for(var i=0; i<redInputArr.length; i++) {
		var num = redBall[i];
		redInputArr[i].value = num;
	}
	for(var i=0; i<blueInputArr.length; i++) {
		var num = blueBall[i];
		blueInputArr[i].value = num;
	}
}
function stopDltRandom(){
	clearInterval(time2);
}
function dltRondom(){
	time2=setInterval("dltRandomMethod()",Math.floor(Math.random()*50+50));
	refreshRequestToken("form_dlt");
	window.setTimeout("stopDltRandom()",1000);
}
///////////////////////////////////////////W3D
function welfare3dSubmit(){
	   $SSO.login_auth(function() {
	   	    var form_welfare3d = document.getElementById('form_welfare3d');
	   	    var ballInputArr = $('#welfare3dNumRondom input[_name="ball"]');
	   	    var betStr= "";
	   	    for(var i=0; i<ballInputArr.length; i++) {
	   	    	if(i==ballInputArr.length-1){
	   	    		betStr=betStr+ballInputArr[i].value+"";
	   	    	}else{
	   	    		betStr=betStr+ballInputArr[i].value+"-";
	   	    	}
	   	    }
	   	    form_welfare3d.elements['createForm.quick_content'].value=betStr;
			var options = {
				type : 'POST',
				cache : false,
				data : {
					ajax : 'true'
				},
				success : function(data, textStatus) {
					var jsonObj = toJsonObject(data);
					var msg = getCommonMsg(jsonObj);
					if (jsonObj.success == true) {
						alert(msg);
						refreshRequestToken("form_welfare3d");
						if (jsonObj.redirectURL != null)
							window.location.href = jsonObj.redirectURL;
					} else {
						alert(msg);
						refreshRequestToken("form_welfare3d");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('提交请求失败.');
					refreshRequestToken("form_welfare3d");
				},
				beforeSend : function(XMLHttpRequest) {
					document.getElementById('welfare3d_span_createForm_submit').style.display = 'none';
					document.getElementById('welfare3d_span_createForm_waiting').style.display = '';
				},
				complete : function(XMLHttpRequest, textStatus) {
					document.getElementById('welfare3d_span_createForm_submit').style.display = '';
					document.getElementById('welfare3d_span_createForm_waiting').style.display = 'none';
				}
			};
			$(form_welfare3d).ajaxSubmit(options);
		});
}
function welfare3dRandomMethod(){
	var ballInputArr = $('#welfare3dNumRondom input[_name="ball"]');
	var copyBallArr = W3D_BLUE_BALL_ARR.slice(0, W3D_BLUE_BALL_ARR.length);
	var ball1 = copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
	var ball2 = copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
	var ball3 = copyBallArr.sort(randomSort).slice(0, 1).sort(asc);
	ballInputArr[0].value = ball1;
	ballInputArr[1].value = ball2;
	ballInputArr[2].value = ball3;
	
}
function stopW3dRandom(){
	clearInterval(time3);
}
function welfare3dRondom(){
	time3=setInterval("welfare3dRandomMethod()",Math.floor(Math.random()*50+50));
	refreshRequestToken("form_welfare3d");
	window.setTimeout("stopW3dRandom()",1000);
}
function selectLottery(lottery){
	var lotteryArr = ["dlt","ssq","welfare3d"];
	for(var i=0; i<lotteryArr.length; i++) {
		var a = document.getElementById(lotteryArr[i]+"_a");
		var span = document.getElementById(lotteryArr[i]+"_span");
		var trArr = $('#betForm tr[_name="'+lotteryArr[i]+'_tr"]');
		if(lottery==lotteryArr[i]){
			a.className = "now";
			span.style.display="";
			for ( var j = 0; j < trArr.length; j++) {
				trArr[j].style.display="";
			}
		}else{
			a.className = "";
			span.style.display="none";
			for ( var j = 0; j < trArr.length; j++) {
				trArr[j].style.display="none";
			}
		}
	}
	
}
function selectAward(lottery){
	var lotteryArr = ["jingjicai","ticai","fucai"];
	for(var i=0; i<lotteryArr.length; i++) {
		var a = document.getElementById(lotteryArr[i]+"_a");
		var div = document.getElementById(lotteryArr[i]+"_div");
		if(lottery==lotteryArr[i]){
			a.className = "now";
			div.style.display="";
		}else{
			a.className = "";
			div.style.display="none";
		}
	}
	
}
function selectUserAward(lottery){
	var lotteryArr = ["dlt","ssq","sfzc","dczc"];
	for(var i=0; i<lotteryArr.length; i++) {
		var a = document.getElementById(lotteryArr[i]+"_win_a");
		var table = document.getElementById(lotteryArr[i]+"_win_table");
		if(lottery==lotteryArr[i]){
			a.className = "now";
			table.style.display="";
		}else{
			a.className = "";
			table.style.display="none";
		}
	}
	
}
function select_gonggao_help(type){
	var typeArr = ["gonggao","help"];
	for(var i=0; i<typeArr.length; i++) {
		var a = document.getElementById(typeArr[i]+"_news_a");
		var ul = document.getElementById(typeArr[i]+"_news_ul");
		if(type==typeArr[i]){
			a.className = "now";
			ul.style.display="";
		}else{
			a.className = "";
			ul.style.display="none";
		}
	}
	
}
function select_news_zq(type){
	var typeArr = ["sfzc","dczc","jczq"];
	for(var i=0; i<typeArr.length; i++) {
		var a = document.getElementById("news_"+typeArr[i]+"_a");
		var div = document.getElementById("news_"+typeArr[i]+"_div");
		if(type==typeArr[i]){
			a.className = "now";
			div.style.display="";
		}else{
			a.className = "";
			div.style.display="none";
		}
	}
}
function select_news_sz(type){
	var typeArr = ["ssq","dlt","seven"];
	for(var i=0; i<typeArr.length; i++) {
		var a = document.getElementById("news_"+typeArr[i]+"_a");
		var div = document.getElementById("news_"+typeArr[i]+"_div");
		if(type==typeArr[i]){
			a.className = "now";
			div.style.display="";
		}else{
			a.className = "";
			div.style.display="none";
		}
	}
}
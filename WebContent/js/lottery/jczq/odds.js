//提供赔率的公司
var companyArr = {};
companyArr[1] = '澳门';
companyArr[3] = 'SB/皇冠';
companyArr[4] = 'Libo';
companyArr[7] = 'SNAI';
companyArr[8] = 'Bet365';
companyArr[9] = '威廉希尔';
companyArr[12] = '易胜博';
companyArr[14]='韦德';
companyArr[17]='明陞';
companyArr[19]='Interwetten';
companyArr[22]='10BET';
companyArr[23] = '金宝博';
companyArr[24] = '12bet';
companyArr[31] = '利记';
companyArr[35] = '盈禾';
companyArr[41] = 'marathonbet';
companyArr.length=2;
companyNoArr = [1,3,4,7,8,9,12,14,17,19,22,23,24,31,35,41];

//封装数据
function buildMap(){
	if(typeof odds_data_arr == "undefined")
		return;
	odds_data_map = [];
	for(var i=0, maxSize=odds_data_arr.length; i<maxSize; i++) {
		for(var j=0, length=odds_data_arr[i].length; j<length; j++) {
			var date = odds_data_arr[i][j][0];
			var lineid = odds_data_arr[i][j][1];
			var company = odds_data_arr[i][j][2];
			odds_data_map[date + "_" + lineid + "_" + company] = odds_data_arr[i][j];
		}
	}
}
function buildAsiaMap(){
	if(typeof asia_odds_data_arr == "undefined")
		return;
	asia_odds_data_map = [];
	for(var i=0, maxSize=asia_odds_data_arr.length; i<maxSize; i++) {
		for(var j=0, length=asia_odds_data_arr[i].length; j<length; j++) {
			var date = asia_odds_data_arr[i][j][0];
			var lineid = asia_odds_data_arr[i][j][1];
			var company = asia_odds_data_arr[i][j][2];
			asia_odds_data_map[date + "_" + lineid + "_" + company] = asia_odds_data_arr[i][j];
		}
	}
}

//show欧赔信息
function showOddsTips(obj, index, matchDate) {
	if(typeof odds_data_map == "undefined")
		buildMap();
	var html = buildOddsDetailHtml(index, matchDate);
	obj.setAttribute("tips",html);
    tip.show3(obj);
}

//show亚赔信息
function showAsiaOddsTips(obj, index, matchDate) {
	if(typeof asia_odds_data_map == "undefined")
		buildAsiaMap();
	var html = buildAsiaOddsDetailHtml(index, matchDate);
	obj.setAttribute("tips",html);
    tip.show3(obj);
}

//构建欧赔信息html
function buildOddsDetailHtml(matchKey, matchDate) {
	var html = '<div id="zhishu" style="z-index:9999">' +
			'<div class="zstit">' +
	 '<table width="100%" border="0" cellpadding="0" cellspacing="0">' +
      '<tr>' + 
        '<td width="28%" align="center">公司名</td>' +
        '<td align="center">日期 时间</td>'+
        '<td width="14%" align="center">胜</td>'+
       ' <td width="14%" align="center">平</td>'+
       ' <td width="14%" align="center">负</td>'+
     ' </tr>'+
   ' </table>'+
	'</div>'+
'<table width="100%" border="0" cellpadding="0" cellspacing="0" class="zhishutab">';
	
	for(var i=0; i<companyNoArr.length; i++) {	
		try{
			var odds = odds_data_map[matchDate + "_" + matchKey + "_" + companyNoArr[i]]
			//求得公司名称
			var company = companyArr[companyNoArr[i]];
			
			//求得赔率变化时间
			try{
			var changeTime =  odds[3];
			}catch(e){
				
			}
			//求得胜的最新赔率和与初始赔率的比较
			var homeWinStr='';
			var firstHomeWinStr = '';
			var compTofirstHomeWinStr = '';
			var WinStrStyle="";			
			if(typeof odds[7] != "undefined"){
				 homeWinStr = toDecimal(odds[7]);
				 if(typeof odds[4] != "undefined"){
					 firstHomeWinStr = toDecimal(odds[4]);
					 if(homeWinStr > firstHomeWinStr){
						compTofirstHomeWinStr = '&uarr;';
						WinStrStyle='style="color:#F00;"';
					} else if(homeWinStr < firstHomeWinStr) {
						compTofirstHomeWinStr = '&darr;';
						WinStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstHomeWinStr = '&rarr;';
					} 
				 }
			}
			
			//求得平的最新赔率和与初始赔率的比较
			var standoffStr='';
			var firstStandoffStr = '';
			var compTofirstStandoffStr = '';
			var StandoffStrStyle="";
			if(typeof odds[8] != "undefined"){
				standoffStr = toDecimal(odds[8]);
				 if(typeof odds[5] != "undefined"){
					 firstStandoffStr = toDecimal(odds[5]);
					 if(standoffStr > firstStandoffStr){
						 compTofirstStandoffStr = '&uarr;';
						 StandoffStrStyle='style="color:#F00;"';
					} else if(homeWinStr < firstHomeWinStr) {
						compTofirstStandoffStr = '&darr;';
						StandoffStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstStandoffStr = '&rarr;';
					} 
				 }
			}
			
			//求得负的最新赔率和与初始赔率的比较
			var guestWinStr='';
			var firstGuestWinStr = '';
			var compTofirstGuestWinStr = '';
			var GuestWinStrStyle="";
			if(typeof odds[9] != "undefined"){
				guestWinStr = toDecimal(odds[9]);
				 if(typeof odds[6] != "undefined"){
					 firstGuestWinStr = toDecimal(odds[6]);
					 if(guestWinStr > firstGuestWinStr){
						 compTofirstGuestWinStr = '&uarr;';
						 GuestWinStrStyle='style="color:#F00;"';
					} else if(homeWinStr < firstHomeWinStr) {
						compTofirstGuestWinStr = '&darr;';
						GuestWinStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstGuestWinStr = '&rarr;';
					} 
				 }
			}
			
			html += '<tr>'+
			 ' <td width="28%" height="26" align="center" bgcolor="#ee7700" style="color:#FFF;">' + company + '</td>'+
			 ' <td align="center" bgcolor="#F2F2F2"> ' + changeTime + '</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + WinStrStyle + '>' + homeWinStr + compTofirstHomeWinStr + '</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + StandoffStrStyle +'>' + standoffStr + compTofirstStandoffStr +'</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + GuestWinStrStyle + '>' + guestWinStr + compTofirstGuestWinStr +'</td>'+
			'</tr>';
		}catch(b){
			
		}
		
	}	
	
	return html;
}
//构建亚赔信息html
function buildAsiaOddsDetailHtml(matchKey, matchDate) {
	var pankou = {};
	pankou[-2.00]='受两球';
	pankou[-1.75]='受球半/两';
	pankou[-1.50]='受球半';
	pankou[-1.25]='受一/球半';
	
	pankou[-1.00]='受一球';
	pankou[-0.75]='受半/一';
	pankou[-0.50]='受半球';
	pankou[-0.25]='受平/半';
	
	pankou[0]='平手';
	pankou[0.25]='平/半';
	pankou[0.50]='半球';
	pankou[0.75]='半/一';
	
	pankou[1.00]='一球';
	pankou[1.25]='一/球半';
	pankou[1.50]='球半';
	pankou[1.75]='球半/两';
	pankou[2.00]='球半/两';
	
	pankou[2.25]='两/两半';
	pankou[2.50]='两半';
	pankou[2.75]='两球半/三球';
	pankou[3.00]='三球';
	
	pankou[-3.00] = "受三球";
	pankou[-2.50]='受两半';
	pankou[-2.25]='受两/两半';

	var html = '<div id="zhishu" style="z-index:9999">' +
			'<div class="zstit">' +
	 '<table width="100%" border="0" cellpadding="0" cellspacing="0">' +
      '<tr>' + 
        '<td width="28%" align="center">公司名</td>' +
        '<td align="center">日期 时间</td>'+
        '<td width="14%" align="center">主</td>'+
       ' <td width="14%" align="center">盘</td>'+
       ' <td width="14%" align="center">客</td>'+
     ' </tr>'+
   ' </table>'+
	'</div>'+
'<table width="100%" border="0" cellpadding="0" cellspacing="0" class="zhishutab">';
	
	for(var i=0; i<companyNoArr.length; i++) {	
		try{
			var odd = asia_odds_data_map[matchDate + "_" + matchKey + "_" + companyNoArr[i]]
			//求得公司名称
			var company = companyArr[companyNoArr[i]];
			var changeTime ='';
			//求得赔率变化时间
			try{
			// changeTime = new Date(odd[3]).format("MM-dd hh:mm");
				  changeTime =  odd[3];
			}catch(b){
				
			}
			//求得主的赔率和与初始赔率的对比
			var homeWinStr='';
 
			var compTofirstHomeWinStr = '';
			var WinStrStyle="";
			if(typeof odd[7] != "undefined"){
				 homeWinStr = toDecimal(odd[7]);
				 if(typeof odd[4] != "undefined"){
					 firstHomeWinStr = toDecimal(odd[4]);
					 if(odd[7] > odd[4]){
						compTofirstHomeWinStr = '&uarr;';
						WinStrStyle='style="color:#F00;"';
					} else if(odd[7] < odd[4]) {
						compTofirstHomeWinStr = '&darr;';
						WinStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstHomeWinStr = '&rarr;';
					} 
				 }
			}
			
			//求得盘的赔率和与初始赔率的对比
			var standoffStr='';
			var firstStandoffStr = '';
			var compTofirstStandoffStr = '';
			var StandoffStrStyle="";
			if(typeof odd[8] != "undefined"){
				standoffStr = pankou[odd[8]];
				 if(typeof odd[5] != "undefined"){
					 firstStandoffStr = pankou[odd[5]];
					 if(odd[8] > odd[5]){
						 compTofirstStandoffStr = '&uarr;';
						 StandoffStrStyle='style="color:#F00;"';
					} else if(odd[8] < odd[5]) {
						compTofirstStandoffStr = '&darr;';
						StandoffStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstStandoffStr = '&rarr;';
					} 
				 }
			}
			
			//求得客的赔率和与初始赔率的对比
			var guestWinStr='';
			var firstGuestWinStr = '';
			var compTofirstGuestWinStr = '';
			var GuestWinStrStyle="";
			if(typeof odd[9] != "undefined"){
				guestWinStr = toDecimal(odd[9]);
				 if(typeof odd[6] != "undefined"){
					 firstGuestWinStr = toDecimal(odd[6]);
					 if(odd[9] > odd[6]){
						 compTofirstGuestWinStr = '&uarr;';
						 GuestWinStrStyle='style="color:#F00;"';
					} else if(odd[9] < odd[6]) {
						compTofirstGuestWinStr = '&darr;';
						GuestWinStrStyle='style="color:#418f1b;"';
					} else {
						compTofirstGuestWinStr = '&rarr;';
					} 
				 }
			}
			html += '<tr>'+
			 ' <td width="28%" height="26" align="center" bgcolor="#ee7700" style="color:#FFF;">' + company + '</td>'+
			 ' <td align="center" bgcolor="#F2F2F2"> ' + changeTime + '</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + WinStrStyle + '>' + homeWinStr + compTofirstHomeWinStr + '</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + StandoffStrStyle +'>' + standoffStr + compTofirstStandoffStr +'</td>'+
			 ' <td width="14%" align="center" bgcolor="#F2F2F2"' + GuestWinStrStyle + '>' + guestWinStr + compTofirstGuestWinStr +'</td>'+
			'</tr>';
		}catch(e){
			
		}
		
	}	
	
	return html;
}





//提供赔率的公司
var companyArr = {};
companyArr[0] = '99家';
companyArr[2] = '威廉';
companyArr[3] = '立博';
companyArr[4] = 'Bet365';
companyArr[5] = '澳门';
companyArr[6] = 'Bwin';
companyArr[9] = '金宝博';
companyArr.length = 7;
companyNoArr = [0, 2, 3 ,4, 5, 6, 9];

//封装数据
function buildMap(){
	if(typeof odds_data_arr == "undefined")
		return;
	odds_data_map = [];
	for(var j=0, length=odds_data_arr.length; j<length; j++) {
		var lineid = odds_data_arr[j][1];
		var company = odds_data_arr[j][2];
		odds_data_map[lineid + "_" + company] = odds_data_arr[j];
	}
}

//show欧赔信息
function showOddsTips(obj, index) {
	if(typeof odds_data_map == "undefined")
		buildMap();
	index = index + 1;
	var html = buildOddsDetailHtml(index);
	obj.setAttribute("tips",html);
    tip.show3(obj);
}

//show亚赔信息
function showAsiaOddsTips(obj, index) {
	if(typeof odds_data_map == "undefined")
		buildMap();
	index = index + 1;
	var html = buildAsiaOddsDetailHtml(index);
	obj.setAttribute("tips",html);
    tip.show3(obj);
}

//构建欧赔信息html
function buildOddsDetailHtml(matchKey) {
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
		if(companyNoArr[i] != 9) {
			var odds = odds_data_map[matchKey + "_" + companyNoArr[i]]
			//求得公司名称
			var company = companyArr[companyNoArr[i]];
			
			//求得赔率变化时间
			var changeTime = new Date(odds[3]).format("MM-dd hh:mm");
			
			//求得胜的最新赔率和与初始赔率的比较
			var homeWinStr='';
			var firstHomeWinStr = '';
			var compTofirstHomeWinStr = '';
			var WinStrStyle="";			
			if(typeof odds[10] != "undefined"){
				 homeWinStr = toDecimal(odds[10]);
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
			if(typeof odds[11] != "undefined"){
				standoffStr = toDecimal(odds[11]);
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
			if(typeof odds[12] != "undefined"){
				guestWinStr = toDecimal(odds[12]);
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
		}	
		
	}	
	
	return html;
}
//构建亚赔信息html
function buildAsiaOddsDetailHtml(matchKey) {
	var pankou = {};
	pankou[-2000]='受两球';
	pankou[-1750]='受球半/两';
	pankou[-1500]='受球半';
	pankou[-1250]='受一/球半';
	
	pankou[-1000]='受一球';
	pankou[-750]='受半/一';
	pankou[-500]='受半球';
	pankou[-250]='受平/半';
	
	pankou[0]='平手';
	pankou[250]='平/半';
	pankou[500]='半球';
	pankou[750]='半/一';
	
	pankou[1000]='一球';
	pankou[1250]='一/球半';
	pankou[1500]='球半';
	pankou[1750]='球半/两';
	pankou[2000]='球半/两';
	
	pankou[2250]='两/两半';
	pankou[2500]='两半';
	pankou[2750]='两球半/三球';
	pankou[3000]='三球';
	
	pankou[-3000] = "受三球";
	pankou[-2500]='受两半';
	pankou[-2250]='受两/两半';

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
		if(companyNoArr[i] != 6 && companyNoArr[i] != 0 && companyNoArr[i] != 2) {
			var odd = odds_data_map[matchKey + "_" + companyNoArr[i]]
			//求得公司名称
			var company = companyArr[companyNoArr[i]];
			
			//求得赔率变化时间
			var changeTime = new Date(odd[3]).format("MM-dd hh:mm");
			
			//求得主的赔率和与初始赔率的对比
			var homeWinStr='';
			var firstHomeWinStr = '';
			var compTofirstHomeWinStr = '';
			var WinStrStyle="";
			if(typeof odd[13] != "undefined"){
				 homeWinStr = toDecimal(odd[13]);
				 if(typeof odd[7] != "undefined"){
					 firstHomeWinStr = toDecimal(odd[7]);
					 if(odd[13] > odd[7]){
						compTofirstHomeWinStr = '&uarr;';
						WinStrStyle='style="color:#F00;"';
					} else if(odd[13] < odd[7]) {
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
			if(typeof odd[14] != "undefined"){
				standoffStr = pankou[odd[14]];
				 if(typeof odd[8] != "undefined"){
					 firstStandoffStr = pankou[odd[8]];
					 if(odd[14] > odd[8]){
						 compTofirstStandoffStr = '&uarr;';
						 StandoffStrStyle='style="color:#F00;"';
					} else if(odd[14] < odd[8]) {
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
			if(typeof odd[15] != "undefined"){
				guestWinStr = toDecimal(odd[15]);
				 if(typeof odd[9] != "undefined"){
					 firstGuestWinStr = toDecimal(odd[9]);
					 if(odd[15] > odd[9]){
						 compTofirstGuestWinStr = '&uarr;';
						 GuestWinStrStyle='style="color:#F00;"';
					} else if(pankou[guestWinStr] < pankou[firstGuestWinStr]) {
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
		}		
	}	
	
	return html;
}




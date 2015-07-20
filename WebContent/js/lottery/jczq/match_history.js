 var o_companyArr = {};
 o_companyArr[24] = '99家';
 o_companyArr[14] = '威廉';
 o_companyArr[82] = '立博';
 o_companyArr[27] = 'Bet365';
 o_companyArr[84] = '澳门';
 String.prototype.trim = function() {
	  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
};
var open_close = true;

var panluArr={};
panluArr[50]='<font style="color:red">赢</font>';
panluArr[25]='<font style="color:red">赢半</font>';
panluArr[0]='<font style="color:#111">走</font>';
panluArr[-25]='<font style="color:blue">输半</font>';
panluArr[-50]='<font style="color:blue">输</font>';
	var o_pankou = {};
	o_pankou[0]='平手';
	o_pankou[-4000]='四球';
	o_pankou[-3750]='三半/四';
	o_pankou[-3500]='三半';
	o_pankou[-3250]='三/三半';
	o_pankou[-3000] = "三球";
	o_pankou[-2750]='两半/三';
	o_pankou[-2500]='两半';	
	o_pankou[-2250]='两/两半';
	o_pankou[-2000]='两球';
	o_pankou[-1750]='球半/两';
	o_pankou[-1500]='球半';
	o_pankou[-1250]='一/球半';
	o_pankou[-1000]='一球';
	o_pankou[-750]='半/一';
	o_pankou[-500]='半球';
	o_pankou[-250]='平/半';	
	o_pankou[250]='受平/半';
	o_pankou[500]='受半球';
	o_pankou[750]='受半/一'; 	
	o_pankou[1000]='受一球';
	o_pankou[1250]='受一/球半';
	o_pankou[1500]='受球半';
	o_pankou[1750]='受球半/两';
	o_pankou[2000]='受球半/两';	
	o_pankou[2250]='受两/两半';
	o_pankou[2500]='受两半';
	o_pankou[2750]='受两半/三';
	o_pankou[3000]='受三球';	
	o_pankou[3250]='受三/三半';
	o_pankou[3500]='受三半';
	o_pankou[3750]='受三半四';
	o_pankou[4000]='受四球';


function showMatchHisTool(obj){
	obj.style.cursor='pointer';
	obj.style.cursor='hand';	
	var $obj  = $(obj);
	$obj.find('.mhtool').show(0);
	obj.onmouseout = function() { $obj.find('.mhtool').hide(0);}; 
}
var current_odds_company=84;
var current_eur_odds_company=14;
function changeAsiaCompany(obj){
	var company = obj.value;
	$(".odds_asia_div").hide();
	$(".odds_asia_div_"+company).show();
	current_odds_company=company;
}
function changeEuropeCompany(obj){
	var company = obj.value;
	$(".odds_eur_div").hide();
	$(".odds_eur_div_"+company).show();
	current_eur_odds_company=company;
}
function buildMatchHis(jsonData,t,matchKey){
	var html ="";
	var display='',eur_display='',tr_display='';
	 var matchArr,homeScore,guestScore;
	 var data_arr = eval(jsonData);
	 for(var i=0;i<data_arr.length;i++){
		   matchArr=data_arr[i]; 				
		   homeScore=matchArr[3];
		   if(homeScore!=null){
				guestScore=matchArr[4]==null?"":matchArr[4];
				var odds_data=matchArr[10]+"";
				var odds_arr;
				try{
					odds_arr=eval(odds_data);	
				}catch(e){
					odds_arr=[];
				}		 
				var asiaHomeWin,asiaStandoff,asiaGuestWin,homeWin,standoff,guestwin,firstHomeWin,firstStandoff,firstGuestWin;
				var asisSpan='';var europeSpan='';	
				
				var trClass='';
				for(var j=0;j<odds_arr.length;j++){
					display='style="display:none;"'; 
					eur_display='style="display:none;"'; 
					var odds= odds_arr[j]; 
					asiaHomeWin=(odds[1])?odds[1]:'-';
					asiaStandoff=(odds[2]!=null)?odds[2]:'';
					asiaStandoffStr=(odds[2]!=null)?o_pankou[odds[2]]:'-';
					asiaGuestWin=(odds[3])?odds[3]:'-';
					homeWin=(odds[4])?odds[4]:'-';standoff=(odds[5])?odds[5]:'-';guestwin=(odds[6])?odds[6]:'-';
					firstHomeWin=(odds[7])?''+odds[7]+'':'';firstStandoff=(odds[8])?''+odds[8]+'':'';firstGuestWin=(odds[9])?''+odds[9]+'':'';
					var panlu='';					 
						try{ 
							var pan = homeScore-guestScore+asiaStandoff/1000 ;						 
					 
							if((typeof(homeScore)=='string'&& homeScore=="")||(typeof(asiaStandoff)=='string'&& asiaStandoff=="")){
								panlu='-';
							}else{							
								if(pan==0){
									panlu=panluArr[0] ;
								}
								if(t==2||t==5){
									pan=-pan;
								}
								if(pan==0.25){
									panlu=panluArr[25] ;
								}else if(pan==-0.25){
									panlu=panluArr[-25] ;
								}else if(pan>0.25){
									panlu=panluArr[50] ;
								}else if(pan<-0.25){
									panlu=panluArr[-50];
								}
							}		
						}catch(ee){
							
						}				 
					if(odds[0]==current_odds_company){
						display='';
					}
					asisSpan+='<div class="odds_asia_div odds_asia_div_'+odds[0]+'" '+display+'><span class="odds_span d_yel_left1">'+oddsToDecimal(asiaHomeWin)+'</span><span class="odds_span d_yel_left1" style="width:50px;">'+asiaStandoffStr+'</span><span class="odds_span d_yel_left1">'+oddsToDecimal(asiaGuestWin)+'</span><span class="odds_span d_yel_leftrig1" style="width:28px;border-right:0px" >'+panlu+'</span>'+
								'</div>';
			 
					if(odds[0]==current_eur_odds_company){
						eur_display='';
					}				 
					europeSpan+='<div class="odds_eur_div odds_eur_div_'+odds[0]+'" '+eur_display+'><span class="odds_span d_blue_left1"><font style="color:#0ca3d2;">'+oddsToDecimal(homeWin)+'</font>('+oddsToDecimal(firstHomeWin)+')</span><span class="odds_span d_blue_left1"><font style="color:#0ca3d2;">'+oddsToDecimal(standoff)+'</font>('+toDecimal(firstStandoff)+')</span><span class="odds_span d_blue_leftrig1"><font style="color:#0ca3d2;">'+oddsToDecimal(guestwin)+'</font>('+oddsToDecimal(firstGuestWin)+')</span></div>';
				}
				if(i%2==0){
					trClass='tdlist';
				}else{
					trClass='tdlist1';
				}
			 if(i>10){
				 tr_display='style="display:none;"';
			 }
			  html+='<tr  align="center" '+tr_display+' id="mh_'+i+'_tr_'+matchKey+t+'" class="'+trClass+'"><td height="30">'+matchArr[7]+'</td><td>'+matchArr[6]+'</td>'+
			  			'<td >'+matchArr[2]+'</td><td><b>'+homeScore+'-'+guestScore+'</b></td><td  align="left">'+matchArr[5]+'</td>'+
			  			'<td>'+asisSpan+'</td>'+'<td>'+europeSpan+'</td>'+
			  		'</tr>';
		   }
	 }
	 return html;
} 
function buildMatchHisWithId(jsonData,t,matchKey,homeTeamId,guestTeamId){
	var html ="";
	var display='',eur_display='',tr_display='';
	 var matchArr,homeScore,guestScore,homeName='',guestName='';
 
	 var data_arr = eval(jsonData);
	 for(var i=0;i<data_arr.length;i++){
		   matchArr=data_arr[i]; 				
		   homeScore=matchArr[3];
		   if(homeScore!=null){
				guestScore=matchArr[4]==null?"":matchArr[4];
	 
				var odds_data=matchArr[10]+"";
				var odds_arr;
				try{
					odds_arr=eval(odds_data);	
				}catch(e){
					odds_arr=[];
				}		 
				var asiaHomeWin,asiaStandoff,asiaGuestWin,homeWin,standoff,guestwin,firstHomeWin,firstStandoff,firstGuestWin;
				var asisSpan='';var europeSpan='';				
				var trClass='';
				for(var j=0;j<odds_arr.length;j++){
					display='style="display:none;"'; 
					eur_display='style="display:none;"'; 
					var odds= odds_arr[j]; 
					asiaHomeWin=(odds[1])?odds[1]:'-';
					asiaStandoff=(odds[2]!=null)?odds[2]:'';
					asiaStandoffStr=(odds[2]!=null)?o_pankou[odds[2]]:'-';
					asiaGuestWin=(odds[3])?odds[3]:'-';
					homeWin=(odds[4])?odds[4]:'-';standoff=(odds[5])?odds[5]:'-';guestwin=(odds[6])?odds[6]:'-';
					firstHomeWin=(odds[7])?''+odds[7]+'':'';firstStandoff=(odds[8])?''+odds[8]+'':'';firstGuestWin=(odds[9])?''+odds[9]+'':'';
					var panlu='';					 
						try{ 
							var pan = homeScore-guestScore+asiaStandoff/1000 ;						 				 
							if((typeof(homeScore)=='string'&& homeScore=="")||(typeof(asiaStandoff)=='string'&& asiaStandoff=="")){
								panlu='-';
							}else{							
								if(pan==0){
									panlu=panluArr[0] ;
								}
								if(t==1||t==4){
									if(matchArr[11]!=homeTeamId)pan=-pan;
								}else{
									if(matchArr[12]==guestTeamId)pan=-pan;
								}
						 
								if(pan==0.25){
									panlu=panluArr[25] ;
								}else if(pan==-0.25){
									panlu=panluArr[-25] ;
								}else if(pan>0.25){
									panlu=panluArr[50] ;
								}else if(pan<-0.25){
									panlu=panluArr[-50];
								}
							}		
						}catch(ee){
							
						}				 
					if(odds[0]==current_odds_company){
						display='';
					}
					asisSpan+='<div class="odds_asia_div odds_asia_div_'+odds[0]+'" '+display+'><span class="odds_span d_yel_left1">'+oddsToDecimal(asiaHomeWin)+'</span><span class="odds_span d_yel_left1" style="width:50px;">'+asiaStandoffStr+'</span><span class="odds_span d_yel_left1">'+oddsToDecimal(asiaGuestWin)+'</span><span class="odds_span d_yel_leftrig1" style="width:28px;border-right:0px" >'+panlu+'</span>'+
								'</div>';
			 
					if(odds[0]==current_eur_odds_company){
						eur_display='';
					}				 
					europeSpan+='<div class="odds_eur_div odds_eur_div_'+odds[0]+'" '+eur_display+'><span class="odds_span d_blue_left1"><font style="color:#0ca3d2;">'+oddsToDecimal(homeWin)+'</font>('+oddsToDecimal(firstHomeWin)+')</span><span class="odds_span d_blue_left1"><font style="color:#0ca3d2;">'+oddsToDecimal(standoff)+'</font>('+toDecimal(firstStandoff)+')</span><span class="odds_span d_blue_leftrig1"><font style="color:#0ca3d2;">'+oddsToDecimal(guestwin)+'</font>('+oddsToDecimal(firstGuestWin)+')</span></div>';
				}
				if(i%2==0){
					trClass='tdlist';
				}else{
					trClass='tdlist1';
				}
			 if(i>10){
				 tr_display='style="display:none;"';
			 }
			if(t==1||t==4){
					if(matchArr[11]==homeTeamId){
						homeName='<span style="color:red">'+matchArr[2]+'<span>';
						guestName=matchArr[5];
					}else{
						homeName=matchArr[2];
						guestName='<span style="color:red">'+matchArr[5]+'<span>';
					}
			}else{
					if(matchArr[12]==guestTeamId){
						homeName=matchArr[2];
						guestName='<span style="color:red">'+matchArr[5]+'<span>';
					}else{
						homeName='<span style="color:red">'+matchArr[2]+'<span>';
						guestName=matchArr[5];		
					}
			}
			  html+='<tr align="center" '+tr_display+' id="mh_'+i+'_tr_'+matchKey+t+'" class="'+trClass+'"><td height="30">'+matchArr[7]+'</td><td>'+matchArr[6]+'</td>'+
			  			'<td >'+homeName+'</td><td><b>'+homeScore+'-'+guestScore+'</b></td><td  align="left">'+guestName+'</td>'+
			  			'<td>'+asisSpan+'</td>'+'<td>'+europeSpan+'</td>'+
			  		'</tr>';
		   }
	 }
	 return html;
}

function changeMatchHisPage(obj,matchKey,matchDate,index,t){
	var pageNo = obj.value;
	var match_history_tr ;
	for(var i=10;i<30;i++){
		  match_history_tr = document.getElementById('mh_'+i+'_tr_'+matchKey+t);
		  match_history_tr.style.display='none';
	}
	for(var i=10;i<pageNo;i++){
		  match_history_tr = document.getElementById('mh_'+i+'_tr_'+matchKey+t);
		  match_history_tr.style.display='';
	}
 
}
function hideMatchHisTips(){
	$('.match_history_tr').hide(0);
}
var _match_his_tips_flag=true;

function showMatchHisTips(teamName,matchKey,index, matchDate,t){
	if(open_close){
		var list = art.dialog.list;
		for (var i in list) {
		    list[i].close();
		}
	}
	
	var match_history= document.getElementById("_"+matchDate+"_"+index+"_"+t+"mh");
	var offset = $("#_"+matchKey+"_matchHis").offset();
	var left = offset.left-50;
	var top  =offset.top;
	if(!match_history && _match_his_tips_flag==true){
		var div = document.createElement("div");
	    _match_his_tips_flag=false;
	    div.setAttribute("class","match_history_tr");
	    div.setAttribute("id","_"+matchDate+"_"+index+"_"+t+"mh");
	    if(t==0||t==2){
	    	var url =  window.BASESITE+"/jczq/matchhistory!history.action?period="+matchDate+"&matchLine="+index+"&t="+t+"&pageNo="+0; 
	    }else{
			 url =  window.BASESITE+"/jczq/matchhistory!history.action?period="+matchDate+"&matchLine="+index+"&t="+t+"&pageNo="+0+"&op="+t;
		} 	
		$.ajax({
	    type : "GET", 
	    url : url,
		   cache:true,
		   dataType:"json", 
		   success : function(json){ 	 
				   var html =' <table width="100%" border="0" cellpadding="0" align="center"  cellspacing="0"  style="border:0px">' +
				   			'     <tr class="tdduilist char14"  height="24"> <td width="76" height="38">时间</td><td width="58">联赛</td> <td width="93">主队</td><td width="38">比分</td> <td width="93">客队</td><td width="160">亚赔<select id="_'+matchDate+'_'+index+'_'+t+'sa" onchange="changeAsiaCompany(this)"><option value="84">澳门</option><option value="82">立博</option><option value="27">Bet365</option></select></td><td width="186">欧终赔(初赔)<select  id="_'+matchDate+'_'+index+'_'+t+'se"  onchange="changeEuropeCompany(this)" class="selectEurope"><option value="14">威廉</option><option value="82">立博</option><option value="27">Bet365</option><option value="24">99家</option></select></td></tr>';
				   try{
					   if(t==0||t==2){
						   html+=buildMatchHis(json.matchHisResult,t,matchKey)+'</table>';
					   }else{
						   var jsonTemp = json.matchHisResult.split("#");
						   html+=buildMatchHisWithId(jsonTemp[3],t,matchKey,jsonTemp[1],jsonTemp[2])+'</table>';   
					   }
				   }catch(e){
					   html+='</table>';
					   _match_his_tips_flag=true;
				   }
				   var mh_tr =document.getElementById("_"+matchKey+"_matchHis");
				   div.innerHTML=html;
				   div.style.display='none';
				   mh_tr.appendChild(div);
				   art.dialog({
					    id:'_'+matchDate+'_'+index+'_'+t+'mh_dialog',
						padding: '2px',
						title: teamName+'历史赔率'+'<select id="_'+matchDate+'_'+index+'_'+t+'select" onchange="changeMatchHisPage(this,\''+matchKey+'\','+matchDate+','+index+','+t+')"><option value="10">10场</option><option value="20">20场</option><option value="30">30场</option></select>',
						left:left,
				   		top:(top - $(window).scrollTop()),
						content:html
					}); 
				   document.getElementById('_'+matchDate+'_'+index+'_'+t+'sa').value= current_odds_company;
				   document.getElementById('_'+matchDate+'_'+index+'_'+t+'se').value= current_eur_odds_company;
				   _match_his_tips_flag=true;
				   document.getElementById("_"+matchDate+"_"+index+"_"+t+"select").options[0].selected = true; 
			 
		 		},
		 	error:function(){
		 		_match_his_tips_flag=true;
		 	}
			});
		
	}else if(match_history){
		var html = match_history.innerHTML;
		   art.dialog({
			    id:'_'+matchDate+'_'+index+'_'+t+'mh_dialog',
				padding: '2px',
				title: teamName+'历史赔率'+'<select id="_'+matchDate+'_'+index+'_'+t+'select" onchange="changeMatchHisPage(this,\''+matchKey+'\','+matchDate+','+index+','+t+')"><option value="10">10场</option><option value="20">20场</option><option value="30">30场</option></select>',
				left:left,
		   		top:(top - $(window).scrollTop()),
				content:html
			}); 
		_match_his_tips_flag=true;
		document.getElementById('_'+matchDate+'_'+index+'_'+t+'sa').value= current_odds_company;
		document.getElementById('_'+matchDate+'_'+index+'_'+t+'se').value= current_eur_odds_company;
		document.getElementById("_"+matchDate+"_"+index+"_"+t+"select").options[0].selected = true; 
		
	}
} 
function hideTheOddsTip(){
	document.getElementById('zhishu').style.display='none';
}
//银行家算法2.154=2.15  2.155=2.16  2.165=2.16  2.166=2.17 
function oddsToDecimal(x) {  
	var f = parseFloat(x);  
	if (isNaN(f)) {  
		return '-';  
	}  
	var f = Math.round(x*1000)/1000; 
	var s = f.toString();  
	var rs = s.indexOf('.');  
	if (rs < 0) {  
		rs = s.length;  
		s += '.';  
	}  
	while (s.length <= rs + 3) {  
		s += '0';  
	} 

	var pointIndex = s.indexOf('.');//小数点的位置
	var thirdChar = s.charAt(pointIndex + 3);//小数点后面第3位的字符
	var secondChar = s.charAt(pointIndex + 2);//小数点后面第2位的字符
	
	if(parseInt(thirdChar)==5) {
		if(parseInt(secondChar)%2 == 0) {
			s = parseFloat(parseInt(s*100)/100);
		} else {
			s = parseFloat(parseInt(s*100 + 1)/100);
		}
		
	} else{
		s = Math.round(s*100)/100;
	}
	
	rs = s.toString().indexOf('.');
	if (rs < 0) { 
		
		rs = s.toString().length;  
		s += '.';  
	}  
	while (s.toString().length <= rs + 2) {  
		s += '0';  
	} 	
	return s;	  
} 
 
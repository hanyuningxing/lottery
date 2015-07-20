
function showAllOdds(index,matchDate,t){
	alert('2');
	var all_odds= document.getElementById("_"+matchDate+"_"+index+"_allodds");
	var offset = $("#_"+matchDate+"_"+index+"_allodds_pos").offset();
	var left = offset.left-50;
	var top  =offset.top;
	if(!all_odds){
		var div = document.createElement("div");
	    div.setAttribute("id","_"+matchDate+"_"+index+"_allodds");
	    var url;
	    if(t==1){
	    	url =  window.BASESITE+"/jczq/matchhistory!allOdds.action?period="+matchDate+"&matchLine="+index; 
	    }else{
	    	url =  window.BASESITE+"/jczq/matchhistory!allOdds.action?period="+matchDate+"&matchLine="+index; 
	    }	
		$.ajax({
	    type : "GET", 
	    url : url,
		   cache:true,
		   dataType:"json", 
		   success : function(json){ 	 
				   var html =' <table border="0" cellpadding="0" align="center"  cellspacing="0"  style="border:0px">' +
				   			'     <tr class="tdduilist char14"  height="24"><td height="38">公司</td><td>初赔</td><td>赔率</td></tr>';
				   try{
 
						 html+=buildOdds(json.allOdds)+'</table>';
				   }catch(e){
					   html+='</table>';
				   }
				   div.innerHTML=html;
				   div.style.display='none';
				   art.dialog({
						padding: '2px',
						title: '赔率',
						left:left,
				   		top:(top - $(window).scrollTop()),
						content:html
					}); 
			 
		 		},
		 	error:function(){
		 	 
		 	}
			});
		
	}else{
		var html = all_odds.innerHTML;
		   art.dialog({
			    id:'_'+matchDate+'_'+index+'_'+t+'mh_dialog',
				padding: '2px',
				title: teamName+'历史赔率'+'<select id="_'+matchDate+'_'+index+'_'+t+'select" onchange="changeMatchHisPage(this,\''+matchKey+'\','+matchDate+','+index+','+t+')"><option value="10">10场</option><option value="20">20场</option><option value="30">30场</option></select>',
				left:left,
		   		top:(top - $(window).scrollTop()),
				content:html
			}); 
 
		
	}
}
function buildOdds(jsonData){
	var html ="";
	 var company='',firstodds='',odds,trClass,oddsArr;
	 var data_arr = eval(jsonData);
	 for(var i=0;i<data_arr.length;i++){		   			   
		 oddsArr=data_arr[i]; 
		 company=oddsArr[0];
		 firstodds=oddsArr[1];
		 odds = oddsArr[2];
		 if(i%2==0){
				trClass='tdlist';
		 }else{
				trClass='tdlist1';
		 }
		 html+='<tr  align="center" id="all_odds_'+i+'" class="'+trClass+'"><td height="30" >'+company+'</td><td >'+firstodds+'</td>'+
			  			 
			  			'<td >'+odds+'</td></tr>';
			  	
		 
	 }
	 return html;
}



function showChangeOdds(index){
	var div = document.getElementById("change_content_"+index);
	var $td =  $("#oddsDate_td_" + index);
	var changeOddsDiv = document.getElementById("odds_change_"+index);
	var s = div.innerHTML;
	var array = eval(s);
	var html = "<table>";
	if(array!=null && array.length>1){
		for(var i=0;i<array.length;i++){
			
			var odds_arr =eval(array[i]);
			html+='<tr><td>'+odds_arr[1]+'</td><td>'+odds_arr[2]+'</td><td>'+odds_arr[3]+'</td></tr>';
		}
	}else{
		var first = document.getElementById("odds_first_content_"+index);
		var first_arr = first.innerHTML.split(",");
		html+='<tr><td><span>初</span>'+first_arr[0]+'</td><td>'+first_arr[1]+'</td><td>'+first_arr[2]+'</td></tr>';
	}
	html+="</table>";
	changeOddsDiv.innerHTML=html;
	changeOddsDiv.style.display='none';			
	$td.hover(						
			function () {
				changeOddsDiv.style.display='';								   
			},
			function () {
				changeOddsDiv.style.display='none';					   									  
			}
		 );
}
 
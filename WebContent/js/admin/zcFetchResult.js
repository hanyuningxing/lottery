$(function(){
	$("#sfzcFetch").click(function(){
		var lotteryType=document.getElementById("lottery").value;
		var periodNumber_=document.getElementById("periodNumber_").value;
		var url=window.BASESITE+"/admin/lottery/"+lotteryType+"/period!fetchResult.action";
		$.ajax({
			type : 'POST',
			cache : false,
			dataType : 'json',
			url : url,
			data:{periodNumber:periodNumber_},
			success : function(data) {
				if (data.success == true) {
					var dto=data.sfzcDto;
					var gameResult=dto['result'];
					document.getElementById("periodData.result").value=gameResult;
					for(var i=0;i<14;i++){
						homeScoreId="homeScore_"+i;
						guestScoreId="guestScore_"+i;
						var score=dto['table'][i]['score'];
						var homeScore=score.split("-")[0];
						var guestScore=score.split("-")[1];
						document.getElementById(homeScoreId).value=homeScore;
						document.getElementById(guestScoreId).value=guestScore;
					}
					var basicBonus_0=dto['bonus'][0]['basicBonus'];
					var basicBonus_1=dto['bonus'][1]['basicBonus'];
					var basicBonus_2=dto['bonus'][2]['basicBonus'];
					var bb_0=basicBonus_0.split(">")[1].split("<")[0].replace(new RegExp(",","gm"),"");
					var bb_1=basicBonus_1.split(">")[1].split("<")[0].replace(new RegExp(",","gm"),"");
					var bb_2=basicBonus_2.split(">")[1].split("<")[0].replace(new RegExp(",","gm"),"");
					
					var bottom=dto['bottom'];
					var reg=/>.*?</g;
					var matchArray=bottom.match(reg);
					var totalSales_14= matchArray[0].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					var totalSales=matchArray[2].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					var prizePool_14=matchArray[4].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					
					
					document.getElementById("periodData.firstPrize_14").value=isNaN(bb_0)? bb_0:parseInt(bb_0);
					document.getElementById("periodData.firstWinUnits_14").value=dto['bonus'][0]['basicStakes'];
					document.getElementById("periodData.secondPrize_14").value=isNaN(bb_1)? bb_1:parseInt(bb_1);
					document.getElementById("periodData.secondWinUnits_14").value=dto['bonus'][1]['basicStakes'];
					document.getElementById("periodData.totalSales_14").value=isNaN(totalSales_14)? totalSales_14:parseInt(totalSales_14);
					document.getElementById("periodData.prizePool_14").value=isNaN(prizePool_14)? prizePool_14:parseInt(prizePool_14);
					document.getElementById("periodData.firstPrize").value=isNaN(bb_2)? bb_2:parseInt(bb_2);
					document.getElementById("periodData.firstWinUnits").value=dto['bonus'][2]['basicStakes'];
					document.getElementById("periodData.totalSales").value=isNaN(totalSales)? totalSales:parseInt(totalSales);
				} else {		
					alert(data.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("抓取失败,请重试");
			}
		});
	});
	$("#sczcFetch").click(function(){
		var lotteryType=document.getElementById("lottery").value;
		var periodNumber_=document.getElementById("periodNumber_").value;
		var url=window.BASESITE+"/admin/lottery/"+lotteryType+"/period!fetchResult.action";
		$.ajax({
			type : 'POST',
			cache : false,
			dataType : 'json',
			url : url,
			data:{periodNumber:periodNumber_},
			success : function(data) {
				if (data.success == true) {
					var dto=data.sczcDto;
					var gameResult=dto['result'];
					document.getElementById("periodData.result").value=gameResult;
					for(var i=0;i<4;i++){
						homeScoreId="homeScore_"+i;
						guestScoreId="guestScore_"+i;
						document.getElementById(homeScoreId).value=dto['table'][i]['homeScore'];
						document.getElementById(guestScoreId).value=dto['table'][i]['guestScore'];
					}
					var basicBonus_0=dto['bonus'][0]['basicBonus'];
					var bb_0=basicBonus_0.split(">")[1].split("<")[0].replace(new RegExp(",","gm"),"");
					
					var bottom=dto['bottom'];
					var reg=/>.*?</g;
					var matchArray=bottom.match(reg);
					var totalSales= matchArray[0].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					var prizePool=matchArray[2].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					
					
					document.getElementById("periodData.firstPrize").value=isNaN(bb_0)? bb_0:parseInt(bb_0);
					document.getElementById("periodData.firstWinUnits").value=dto['bonus'][0]['basicStakes'];
					document.getElementById("periodData.totalSales").value=isNaN(totalSales)? totalSales:parseInt(totalSales);
					document.getElementById("periodData.prizePool").value=isNaN(prizePool)? prizePool:parseInt(prizePool);
				} else {		
					alert(data.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("抓取失败,请重试");
			}
		});
	});
	$("#lczcFetch").click(function(){
		var lotteryType=document.getElementById("lottery").value;
		var periodNumber_=document.getElementById("periodNumber_").value;
		var url=window.BASESITE+"/admin/lottery/"+lotteryType+"/period!fetchResult.action";
		$.ajax({
			type : 'POST',
			cache : false,
			dataType : 'json',
			url : url,
			data:{periodNumber:periodNumber_},
			success : function(data) {
				if (data.success == true) {
					var dto=data.lczcDto;
					var gameResult=dto['result'];
					document.getElementById("periodData.result").value=gameResult;
					for(var i=0;i<6;i++){
						var homeScoreId="homeScore_"+i;
						var guestScoreId="guestScore_"+i;
						var halfHomeScoreId="halfHomeScore_"+i;
						var halfGuestScoreId="halfGuestScore_"+i;
						var score=dto['table'][i]['score'];
						var halfScore=dto['table'][i]['halfScore'];
						var homeScore=score.split("-")[0];
						var guestScore=score.split("-")[1];
						var halfHomeScore=halfScore.split("-")[0];
						var halfGuestScore=halfScore.split("-")[1];
						document.getElementById(homeScoreId).value=homeScore;
						document.getElementById(guestScoreId).value=guestScore;
						document.getElementById(halfHomeScoreId).value=halfHomeScore;
						document.getElementById(halfGuestScoreId).value=halfGuestScore;
					}
					var basicBonus_0=dto['bonus'][0]['basicBonus'];
					var bb_0=basicBonus_0.split(">")[1].split("<")[0].replace(new RegExp(",","gm"),"");
					
					var bottom=dto['bottom'];
					var reg=/>.*?</g;
					var matchArray=bottom.match(reg);
					var totalSales= matchArray[0].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					var prizePool=matchArray[2].replace(new RegExp(">","gm"),"").replace(new RegExp("<","gm"),"").replace(new RegExp("元","gm"),"").replace(new RegExp(",","gm"),"");
					
					
					document.getElementById("periodData.firstPrize").value=isNaN(bb_0)? bb_0:parseInt(bb_0);
					document.getElementById("periodData.firstWinUnits").value=dto['bonus'][0]['basicStakes'];
					document.getElementById("periodData.totalSales").value=isNaN(totalSales)? totalSales:parseInt(totalSales);
					document.getElementById("periodData.prizePool").value=isNaN(prizePool)? prizePool:parseInt(prizePool);
				} else {		
					alert(data.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("抓取失败,请重试");
			}
		});
	});
});
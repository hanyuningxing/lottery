$(function(){
	
	$("#fecthWinningNumber").click(function(){
		var lotteryType=document.getElementById("lottery").value;
		var periodNumber_=document.getElementById("periodNumber_").value;
		var url=window.BASESITE+"/admin/lottery/"+lotteryType+"/period!fetchWinningNumber.action";
		$.ajax({
			type : 'POST',
			cache : false,
			dataType : 'json',
			url : url,
			data:{periodNumber:periodNumber_},
			success : function(data) {
				if (data.success == true) {
					var resultNumber=data.winningNumber;
					var resultArr=[];
					if(data.lottery=="dlt"||data.lottery=="ssq"||data.lottery=="seven"){
						var resultStr=resultNumber.split("+");
						var beforeStr=resultStr[0];
						var afterStr=resultStr[1];
						var before=beforeStr.split(" ");
						var after=afterStr.split(" ");
						resultArr[0]=before[0];
						resultArr[1]=before[1];
						resultArr[2]=before[2];
						resultArr[3]=before[3];
						resultArr[4]=before[4];
						if(data.lottery=="dlt"){
							resultArr[5]=after[0];
							resultArr[6]=after[1];
						}else if(data.lottery=="ssq"){
							resultArr[5]=before[5];
							resultArr[6]=after[0];
						}else if(data.lottery=="seven"){
							resultArr[5]=before[5];
							resultArr[6]=before[6];
							resultArr[7]=after[0];
						}
					}else if(data.lottery=="welfare3d"){
						resultArr[0]=resultNumber.substring(0,1);
						resultArr[1]=resultNumber.substring(1,2);
						resultArr[2]=resultNumber.substring(2);
					}else if(data.lottery=="pl"){
						resultArr[0]=resultNumber.substring(0,1);
						resultArr[1]=resultNumber.substring(1,2);
						resultArr[2]=resultNumber.substring(2,3);
						resultArr[3]=resultNumber.substring(3,4);
						resultArr[4]=resultNumber.substring(4);
					}else if(data.lottery=="sevenstar"){
						resultArr[0]=resultNumber.substring(0,1);
						resultArr[1]=resultNumber.substring(1,2);
						resultArr[2]=resultNumber.substring(2,3);
						resultArr[3]=resultNumber.substring(3,4);
						resultArr[4]=resultNumber.substring(4,5);
						resultArr[5]=resultNumber.substring(5,6);
						resultArr[6]=resultNumber.substring(6);
					}
					 $("select[name=num1] option").each(function(){ 
						if($(this).val() == parseInt(resultArr[0])){ 
						   $(this).attr("selected","selected"); 
						} 
					}); 
					 $("select[name=num2] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[1])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num3] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[2])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num4] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[3])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num5] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[4])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num6] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[5])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num7] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[6])){ 
							   $(this).attr("selected","selected"); 
							} 
						}); 
					 $("select[name=num8] option").each(function(){ 
							if($(this).val() == parseInt(resultArr[7])){ 
							   $(this).attr("selected","selected"); 
							} 
						});
					alert(data.msg);
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
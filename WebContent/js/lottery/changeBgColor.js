/**
 *
 * @param tr_id 需要改变背景颜色的行的id
 * @param arr 需要改变背景颜色的td数组（如果需要整行变色，请不要传值）
 */
	function changeBgColor(tr_id, arr) {	
		if(arr) {
			(function(tr_id, arr){								
				$("#" + tr_id).hover(						
					function () {
						for(var i=0, maxSize=arr.length; i<maxSize; i++) {							
							$("#" + arr[i]).addClass("tdChange");							
						}										   
					},
					function () {
						for(var i=0, maxSize=arr.length; i<maxSize; i++) {							
							$("#" + arr[i]).removeClass("tdChange");																			
						}							   									  
					}
				 );
									
			 })(tr_id, arr);
			
		} else {
			(function(tr_id){								
				$("#" + tr_id).hover(
					function () {
						$(this).addClass("tdChange");														   
					},
					function () {
						$(this).removeClass("tdChange");				   									  
					}
				);									
			})(tr_id);
		}
	}
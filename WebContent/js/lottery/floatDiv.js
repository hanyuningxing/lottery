	$(function(){  
    		//刷新时滚动条回顶部 
    		$(window).scrollTop(0);  
    		//info是需要浮动的div的id
    	 	var infoStateChangeSize = $("#info").offset().top;
    	 	//这里减去的66是误差 	 			 
			var topOfFooter = $("#footer").offset().top - 66;
			var hiddenSize = $("#tz_info").height();
			//计算出头部的高度
			var headHeight = $("#header").height();
			var scrollTopBeforeStop = 0;
			var k10 = $(".k10").height();
			var scrollTopBefore = 0;
			var info = document.getElementById("info");
			
	    	$(window).scroll(function() {	  
	    		if(!isIE6){ 
		    		if($("#tz_info").height()>document.documentElement.clientHeight) {
		    			info.style.position = "";
		    			return;
		    		}	
	    		}
	    		var scrollTop = $(window).scrollTop();
	    		
	    		
		    	if(scrollTop > scrollTopBefore && $("#createForm").height()>document.documentElement.clientHeight){
		    		hiddenSize = $("#tz_info").height() + k10;
		    	}else if(scrollTop < scrollTopBefore || $("#createForm").height()<document.documentElement.clientHeight){
		    		hiddenSize = 0;
		    	}
	    		
	    		scrollTopBefore = scrollTop;
	    		var bottomOfInfo = info.getBoundingClientRect().bottom + scrollTop;
		        if($(window).scrollTop() < infoStateChangeSize || (headHeight + $("#info").height()) > $("#footer").offset().top){
		        	if(isIE6){
				    	$("#info").removeClass("likeFixed");
				    	$("#info").removeClass("html");
				    }
		        	info.style.position = "";
		        //这里减20是为了判断效果更好
		        } else if(bottomOfInfo < $("#footer").offset().top - 20)	{
	    			scrollTopBeforeStop = scrollTop;
	    			
		    		if($(window).scrollTop() > infoStateChangeSize) {
		    			//IE6不支持fixed
		    			if(isIE6){
				    		$("#info").addClass("likeFixed");
		    				$("#info").addClass("html");
				    	} else {
				    		info.style.position = "fixed";
				    		info.style.top = -hiddenSize + "px";							
				    	}		    		
											
					} else {
						if(isIE6){
				    		$("#info").removeClass("likeFixed");
				    		$("#info").removeClass("html");
				    	}
						info.style.position = "";
					}
							
				} else {
					if(scrollTop < scrollTopBeforeStop) {
						//IE6不支持fixed
						if(isIE6){
				    		$("#info").addClass("likeFixed");
		    				$("#info").addClass("html");
				    	} else {
				    		info.style.position = "fixed";
				    		info.style.top = -hiddenSize + "px";							
				    	}
				    								
					} else if($("#info").offset().top > infoStateChangeSize) {
						//IE6不支持fixed
						if(isIE6){
				    		$("#info").removeClass("likeFixed");
				    		$("#info").removeClass("html");				    		
				    	}					
						info.style.position = "absolute";
						var infoTop = $("#footer").offset().top - $("#info").height() - headHeight - 2*k10;				
						info.style.top = infoTop + "px";
					}
				}
	    	});
    	});
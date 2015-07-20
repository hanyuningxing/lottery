 $(function(){
	   window.scrollTo(0, 0);
	   //note是需要浮动那个div的ID
	   var note = document.getElementById("note");
	   var noteStateChangeSize = $("#jz_match").offset().top;
	   var headHeight = $("#header").height();
	   
	   $(window).scroll(function() {
		    var scrollTop = $(window).scrollTop();		    	 	    
	        
		    if(scrollTop > noteStateChangeSize) {		    	
		    	if(isIE6){
		    		$("#note").addClass("likeFixed");
		    		$("#note").addClass("html");
		    	} else {
		    		note.style.position = "fixed";
		    		note.style.top = 0;
		    	}
			} else {
				if(isIE6){
		    		$("#note").removeClass("likeFixed");
		    		$("#note").removeClass("html");
		    	}
				note.style.position = "";
			}				
				
	  });
   });
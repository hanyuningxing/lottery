function hideGoldenEgg(){
		var div_obj = $("#zuce"); 
		var windowWidth = document.documentElement.clientWidth; 
		var windowHeight = document.documentElement.clientHeight; 
		var popupHeight = div_obj.height(); 
		var popupWidth = div_obj.width();
		$("#jindan").hide();
		$("#zuce").animate({left: windowWidth/2-popupWidth/2, top: windowHeight/2+popupHeight/2, opacity: "show" }, "slow");
		//$("#zuce").show();
		var _move=false;//移动标记 
		var _x,_y;//鼠标离控件左上角的相对位置 
		$("#zuce").mousedown(function(e){ 
			_move=true; 
			_x=e.pageX-parseInt($("#zuce").css("left")); 
			_y=e.pageY-parseInt($("#zuce").css("top")); 
			//$("#zuce").fadeTo(20, 0.5);//点击后开始拖动并透明显示 
		}); 
		$(document).mousemove(function(e){ 
		if(_move){ 
			var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置 
			var y=e.pageY-_y; 
			$("#zuce").css({top:y,left:x});//控件新位置 
		} 
		}).mouseup(function(){ 
			_move=false; 
			//$("#zuce").fadeTo("fast", 1);//松开鼠标后停止移动并恢复成不透明 
		});
	}
	function hiddenDetail(){
		$("#zuce").hide();
	}
	
	
	//初始化创建swf
	$(document).ready(function() {
		var d = document.getElementById('jindan');
		d.innerHTML = '<a href="javascript:void(0);" class="click" onclick="hideGoldenEgg();"><img style="width:100%; height:100%;" src="./pages/images/tm.gif" /></a>'+
		'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="311" height="229" title="彩蛋">'+
		'<param name="movie" value="./pages/swf/caidan.swf"/>'+
		'<param name="quality" value="high" /><param name="wmode" value="transparent"/>'+
		'<param name="wmode" value="transparent"/>'+
		'<param name="allowScriptAccess" value="always"/>'+
		'<embed src="./pages/swf/caidan.swf" wmode="transparent" allowScriptAccess="always" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="311" height="229"></embed>'+
		'</object>';
	});
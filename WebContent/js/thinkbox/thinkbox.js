function $floater(obj) {
	var option = {
		height : 0,
		width : 0,
		src : "",
		title : "",
		fix : true
	};
	for ( var i in obj) {
		option[i] = obj[i];
	}
	var iframe_height = (option.height - 34);
	var iframe_width = (option.width - 2);
	$floaterTip({
		title : option.title,
		html : '<span id="sync_uc" style="display:none"></span><div class="thinktitle"><h4>'
				+ option.title
				+ '</h4><a href="javascript:;" id="float_closer" title="关闭"></a></div><div class="thinkcontent" ><iframe src="'
				+ option.src
				+ '" width="'
				+ iframe_width
				+ '" height="'
				+ iframe_height
				+ '" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="auto" style="overflow-x:hidden"></iframe></div>',
		fix : option.fix,
		height : (option.height),
		width : (option.width),
		style : "none", // stand\none\poptip
		cover : false, // 显示覆盖背景
		onClose : function() {
			return true;
		} // 关闭事件
	});
}
function $floater1(obj) {
	var option = {
		height : 0,
		width : 0,
		src : "",
		title : "",
		fix : true
	};
	for ( var i in obj) {
		option[i] = obj[i];
	}
	var iframe_height = (option.height - 34);
	var iframe_width = (option.width - 2);
	$floaterTip({
		title : option.title,
		html : '<span id="sync_uc" style="display:none"></span><div class="thinktitle"><h4>'
				+ option.title
				+ '</h4></div><div class="thinkcontent" ><iframe src="'
				+ option.src
				+ '" width="'
				+ iframe_width
				+ '" height="'
				+ iframe_height
				+ '" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="auto" style="overflow-x:hidden"></iframe></div>',
		fix : option.fix,
		height : (option.height),
		width : (option.width),
		style : "none", // stand\none\poptip
		cover : false, // 显示覆盖背景
		onClose : function() {
			return true;
		} // 关闭事件
	});
}
/** ********* Begin Core Function ********** */
function $getPageScrollHeight() {
	var bodyCath = document.body;
	var doeCath = document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement;
	return (window.MessageEvent && navigator.userAgent.toLowerCase().indexOf(
			'firefox') == -1) ? bodyCath.scrollTop : doeCath.scrollTop;
}
function $getPageScrollWidth() {
	var bodyCath = document.body;
	var doeCath = document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement;
	return (window.MessageEvent && navigator.userAgent.toLowerCase().indexOf(
			'firefox') == -1) ? bodyCath.scrollLeft : doeCath.scrollLeft;
}
function $getWindowHeight() {
	return (document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement).clientHeight;
}
function $getWindowWidth() {
	return (document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement).clientWidth;
}
function $getContentHeight() {
	// 获取页面内容的实际高度
	var bodyCath = document.body;
	var doeCath = document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement;
	return (window.MessageEvent && navigator.userAgent.toLowerCase().indexOf(
			'firefox') == -1) ? bodyCath.scrollHeight : doeCath.scrollHeight;
}
function $getContentWidth() {
	// 获取页面内容的实际宽度
	var bodyCath = document.body;
	var doeCath = document.compatMode == 'BackCompat' ? body_cache
			: document.documentElement;
	return (window.MessageEvent && navigator.userAgent.toLowerCase().indexOf(
			'firefox') == -1) ? bodyCath.scrollWidth : doeCath.scrollWidth;
}
function $floaterTip(obj) {
	// 显示浮窗,支持各种形态
	var option = {
		title : "",
		html : "",
		left : "",
		top : "",
		width : '400',
		height : "",
		fix : false, // 是否固定居中随屏幕滚动，如果为true则left和top无效
		style : "", // stand\none\poptip
		cover : false, // 显示覆盖背景
		onClose : function() {
			return true;
		} // 关闭事件
	};
	for ( var i in obj) {
		option[i] = obj[i];
	}

	// 关闭掉类似窗口
	if (window._tipsHandle) {
		try {
			window._tipsHandle.close();
			window._tipsHandle = "";
		} catch (e) {
		}
	}
	// 处理覆盖的半透明背景
	if (option.cover) {
		var c = document.createElement("div");
		c.id = "coverbg";
		c.style.display = "none";
		c.style.width = "0px";
		c.style.height = "0px";
		c.style.backgroundColor = "#000";
		c.style.filter = "Alpha(Opacity=60)";
		c.style.opacity = "0.60";
		c.style.zIndex = 200;
		c.style.position = "absolute";
		c.style.left = "0px";
		c.style.top = "0px";
		document.body.appendChild(c);
		var slist = document.getElementsByTagName("select");
		for ( var i = 0; i < slist.length; i++) {
			if (slist[i].style.display != "none") {
				slist[i].style.display = "none";
				slist[i].setAttribute("hideForCover", "1");
			}
		}
		option.hideSelectList = slist;
		c.style.display = "block";
		c.style.left = "0px";
		c.style.top = "0px";
		c.style.position = "absolute";
		option.coverbg = c;
		window._floaterTimer1 = setInterval(
				function() {
					var c = document.getElementById("coverbg");
					if (c) {
						if (c.style.display == "none") {
							return;
						}
						c.style.height = ($getContentHeight() > $getWindowHeight() ? $getContentHeight()
								: $getWindowHeight())
								+ "px";
						c.style.width = ($getContentWidth() > $getWindowWidth() ? $getContentWidth()
								: $getWindowWidth())
								+ "px";
					} else {
						clearInterval(window._floaterTimer1);
						window._floater = null;
					}
				}, 30);
	}
	var c = document.createElement("div");
	c.id = 'float_box';
	// 根据样式输出不同模板，有标题和关闭按钮的
	if (option.style == "stand") {
		c.className = "module_box_normal";
		c.innerHTML = '<iframe frameBorder="0" style="position:absolute;left:0;top:0;width:100%;z-index:-1;filter:Alpha(Opacity=0);opacity:0;border:none;" id="float_iframe"></iframe><div class="box_title"><h4>'
				+ option.title
				+ '</h4><a href="javascript:;" class="bt_close" id="float_closer" title="关闭"></a></div><div class="box_content">'
				+ option.html + '</div>';
	}
	// 根据样式输出不同模板,无任何样式的时候输出一个空的div
	if (option.style == "") {
		c.className = "module_box_normal";
		c.innerHTML = '<iframe frameBorder="0" style="position:absolute;left:0;top:0;width:100%;z-index:-1;filter:Alpha(Opacity=0);border:none;" id="float_iframe"></iframe><div class="box_content">'
				+ option.html + '</div>';
	}
	if (option.style == "none") {
		// 完全空白，不含样式的模板
		c.className = "";
		c.style.position = "absolute";
		c.innerHTML = '<iframe frameBorder="0" style="position:absolute;left:0;top:0;width:100%;z-index:-1;filter:Alpha(Opacity=0);border:none;" id="float_iframe"></iframe><div class="box_content">'
				+ option.html + '</div>';
	}
	document.body.appendChild(c);
	// 返回操作句柄
	option.frame = c;
	option.height ? (option.frame.style.height = option.height + "px") : "";
	option.width ? (option.frame.style.width = option.width + "px") : "";
	option.frame.style.zIndex = 300;
	option.closer = document.getElementById("float_closer");
	option.iframe = document.getElementById("float_iframe");
	// 窗口定位，如果没有指定坐标则居中
	var p = [ 0, 0 ];
	p[0] = (option.left ? option.left
			: ($getPageScrollWidth() + ($getWindowWidth() - option.frame.scrollWidth) / 2));
	p[1] = (option.top ? option.top
			: ($getPageScrollHeight() + ($getWindowHeight() - option.frame.scrollHeight) / 2));
	// 如果超出屏幕则自动移入
	// 超出右侧
	(p[0] + option.frame.scrollWidth) > ($getPageScrollWidth() + $getWindowWidth()) ? (p[0] = $getPageScrollWidth()
			+ $getWindowWidth() - option.frame.scrollWidth - 10)
			: "";
	// 超出底部
	(p[1] + option.frame.scrollHeight) > ($getPageScrollHeight() + $getWindowHeight()) ? (p[1] = $getPageScrollHeight()
			+ $getWindowHeight() - option.frame.scrollHeight - 10)
			: "";
	// 超出顶部
	p[1] < $getPageScrollHeight() ? p[1] = $getPageScrollHeight() : "";
	// 超出左侧
	p[0] < $getPageScrollWidth() ? p[0] = $getPageScrollWidth() : "";
	// 调整iframe的高度与浮窗一样大小
	option.iframe.height = option.frame.scrollHeight + "px";
	option.iframe.width = option.frame.scrollWidth + "px";
	option.frame.style.left = p[0] + "px";
	option.frame.style.top = p[1] + "px";
	// 如果是fix则随屏幕滚动
	if (option.fix) {
		var arr = navigator.appVersion.split(";");
		if (arr.length > 1 && arr[1].replace(/[ ]/g, "") == "MSIE6.0") {
			setInterval(
					function() {
						var c = document.getElementById("float_box");
						if (c) {
							c.style.left = ($getPageScrollWidth() + ($getWindowWidth() - c.scrollWidth) / 2)
									+ "px";
							c.style.top = ($getPageScrollHeight() + ($getWindowHeight() - c.scrollHeight) / 2)
									+ "px";
						}
					}, 30);
		} else {
			var c = document.getElementById("float_box");
			c.style.position = "fixed";
			c.style.left = ($getWindowWidth() - c.scrollWidth) / 2 + "px";
			c.style.top = ($getWindowHeight() - c.scrollHeight) / 2 + "px";
		}
	}
	// 绑定关闭按钮的事件
	option.closer ? option.closer.onclick = function() {
		option.close();
	} : "";
	// 关闭方法
	option.close = function() {
		if (!option.onClose()) {
			return;
		}
		option.frame ? option.frame.style.display = "none" : "";
		if(option.frame && option.frame.parentNode)
			option.frame.parentNode.removeChild(option.frame);
		if (option.coverbg) {
			option.coverbg.style.display = "none";
			option.coverbg.parentNode.removeChild(option.coverbg);
			var slist = option.hideSelectList;
			for ( var i = 0; i < slist.length; i++) {
				if (slist[i].getAttribute("hideForCover") == "1") {
					slist[i].style.display = "";
				}
			}
		}
	};
	window._tipsHandle = option;
	return option;
};
/** ********* End Core Function ********** */
function $floatHTML(obj) {
	var option = {
		height : 0,
		width : 0,
		html : "",
		title : "",
		fix : true
	};
	for ( var i in obj) {
		option[i] = obj[i];
	}
	var iframe_height = (option.height - 34);
	var iframe_width = (option.width - 2);
	$floaterTip({
		title : option.title,
		html : '<span id="sync_uc" style="display:none"></span><div class="thinktitle"><h4>'
				+ option.title
				+ '</h4><a href="javascript:;" id="float_closer" title="关闭"></a></div><div class="thinkcontent" style="height:'
				+ option.height
				+ 'px;width:'
				+ option.width
				+ 'px">'
				+ option.html + '</div>',
		fix : option.fix,
		height : (option.height),
		width : (option.width),
		style : "none", // stand\none\poptip
		cover : false, // 显示覆盖背景
		onClose : function() {
			return true;
		} // 关闭事件
	});
}
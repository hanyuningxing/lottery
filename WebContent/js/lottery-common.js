


//当滚动条滚动时显示右下浮动图标
function showBarWhenScroll(objid,offsetObjid){
	var obj=$$(objid);
	if(obj==null){return;}
	var offsetObj=$$(offsetObjid);
	var align='right';
	var valign='bottom';
	var alignNum=0;
	var valignNum=0;
	if(offsetObj){
		if(offsetObj.style.display!="none"){
			valignNum=offsetObj.clientHeight;
		}
	}
	obj.style.display='none';
	//显示条件
	function canshow(){
		var top = document.documentElement.scrollTop || document.body.scrollTop;
		if(top>0){
			if(obj.style.display!='block'){
				obj.style.display='block';
			}
		}else{
			if(obj.style.display!='none'){
				obj.style.display='none';
			}
		}
	}
	fixedBox(obj,align,valign,alignNum,valignNum);
	bindEvent(window,'scroll',canshow);
}
//悬浮定位（窗口固定位置定位)
function fixedBox(objid, align, valign,alignNum,valignNum) {
	try{
		var obj = $$(objid);
		if (obj== undefined) {
			alert('对象不存在！');
			return false;
		}
		if (align == undefined) {align="center";} //水平对齐标准   left center right  除了center外，其他两个常量需要根据起对应alignNum数值来定位，默认0
		if (valign == undefined) {valign="center";}//垂直对齐标准   top center bottom  除了center外，其他两个常量需要根据起对应valignNum数值来定位，默认0
		if (alignNum == undefined){alignNum=0;}else{alignNum=parseInt(alignNum);}
		if (valignNum == undefined){valignNum=0;}else{valignNum=parseInt(valignNum);}

		obj.style.zIndex = 100;
		if (isIE6) {
			obj.style.position = 'absolute';
			setPosForDoc(obj, align, valign,alignNum,valignNum);
			bindEvent(window, 'scroll', function() {
				setPosForDoc(obj, align, valign,alignNum,valignNum);
			});
			bindEvent(window, 'resize', function() {
				window.scroll(document.documentElement.scrollLeft,document.documentElement.scrollTop);
				setPosForDoc(obj, align, valign,alignNum,valignNum);
			});
		} else {
			obj.style.position = "fixed";
			setPosForWin(obj, align, valign,alignNum,valignNum);
			if (align == 'center' || valign == 'center') {
				bindEvent(window, 'resize', function() {
					setPosForWin(obj, align, valign,alignNum,valignNum);
				});
			}
		}
	}catch(e){alert(e);}
}
//悬浮定位（非窗口固定位置定位,及当该原件将要被滚动条卷上去的时候悬浮，相对于左边不变）
function topFixed(objid) {
	try {
		var obj = $$(objid);
		if(obj==null){return;}
		var wrapid = objid + '_wrap_item';
		var orect=getElementBox(obj);
		// 对象浮动时，为了不影响原来的布局，要在该对象外面为浮动元素添加浮动时的占位符，方法是为其添加父元素占位
		var wrap=document.createElement("div");
		wrap.id=wrapid;
		wrap.style.position="relative";
		wrap.style.width=orect.width+"px";
		wrap.style.height=orect.height+"px";
		wrap.style.margin="0px";
		wrap.style.padding="0px";
		var cloneObj = obj.cloneNode(true);
		cloneObj.style.zIndex=1000;
	  	wrap.appendChild(cloneObj);
		obj.parentNode.replaceChild(wrap, obj);
		// 绑定事件
		bindEvent(window, "scroll", function() {
			scrolls(objid);
		});
		bindEvent(window, "resize", function() {
			scrolls(objid);
		});
		// 滚动事件
		function scrolls(objid) {
			var obj = $$(objid);
			var wrap = $$(objid + '_wrap_item');
			var obox = getElementBox(obj);
			var wbox = getElementBox(wrap);
			if (wbox.top<=0) {
				if (!isIE6) {
					obj.style.position = "fixed";
					obj.style.top = "0px";
					if(isIE){
						obj.style.left = wbox.left-2+ "px";
					}else{
						obj.style.left = wbox.left+ "px";
					}
				} else {
					obj.style.position = "absolute";
					obj.style.top = -wbox.top + "px";
					obj.style.left ="0px";
				}
			} else {
				obj.style.position = "";
			}
		}
		scrolls(objid);
	} catch (e) {alert(e);
	}
}

//事件绑定
function bindEvent(obj, e, func) {
	if (window.attachEvent) {
		obj.attachEvent("on" + e, func);
		if (window.event)
			window.event.cancelBubble = true;
	} else {
		obj.addEventListener(e, func, false);
	}
}
// 解除事件绑定
function releaseEvent(obj, e, func) {
	if (window.detachEvent) {
		obj.detachEvent("on" + e, func);
	} else {
		obj.removeEventListener(e, func, false);
	}
}

//进度条
function progress(objid,backimg){
	if(!objid){return false;}
	var _obj=$$(objid);
	if(_obj==null){return false;}
	//默认值
	var _bgcolor="#00f";
	var _proc=document.createElement("div");
	_proc.style.width="0px";
	_proc.style.height=_obj.clientHeight+"px";
	if(backimg){
		_proc.style.backgroundImage="url('"+backimg+"')";
		_proc.style.backgroundRepeat="repeat-x";
	}else{
		_proc.style.backgroundColor=_bgcolor;
	}
	_obj.appendChild(_proc);
	this.progressbar=_proc;
	//设置进度条
	this.setprogress=function(prcNum){
		if(prcNum>100){return false;}
		this.progressbar.style.width=parseInt(prcNum)+"%";
	};
}

//兼容的实时侦听form空间的value变化
function checkChange(obj,func) {
	if (window.attachEvent) {
		obj.attachEvent("onpropertychange", func);
	} else {
		obj.addEventListener('input', func, false);
	}
}
//控制图层透明度
function alpha(obj, n) {
	var obj=$$(obj);
	if (document.all) {
		obj.style.filter = "alpha(opacity=" + n + ")";
	} else {
		obj.style.opacity = (n / 100);
	}
}
//渐现
function fadeon(objid,speed,opac){
	var obj=$$(objid);
	var opac = opac?opac:100;
	var speed=speed?speed:10;
	var so=0;
	var timer;
	function fade(){
		alpha(obj, so);

		so+=5;
		if (so < opac) {
			timer = setTimeout(fade, speed);
		} else {
		   clearTimeout(timer);
		};
	}
	fade();
}
//渐隐
function fadeout(objid,speed,opac){
	var obj=$$(objid);
	var opac = opac?opac:0;
	var speed=speed?speed:10;
	var so=100;
	var timer;
	function fade(){
		alpha(obj, so);
		so-=5;
		if (so > opac) {
			timer = setTimeout(fade, speed);
		} else {
		   clearTimeout(timer);
		};
	}
	fade();
}

//获取文档或者页面元素的相对于视窗的绝对位置和大小
function getElementBox(objid) {
	var isW3CBOX = document.compatMode == "CSS1Compat" ? true : false; // 判断盒模型渲染模式是否w3c标准
	var documentRoot = isW3CBOX?document.documentElement:document.body;
	var obj = objid ? $$(objid) : documentRoot;
	var orect = obj.getBoundingClientRect();
	orect = {
		left : Math.round(orect.left),
		right :Math.round(orect.right),
		top : Math.round(orect.top),
		bottom : Math.round(orect.bottom),
		width :Math.round(orect.right - orect.left),
		height :Math.round(orect.bottom - orect.top)
	};
	if(isIE){
		orect.width =  Math.round(obj.scrollWidth);
		orect.height=  Math.round(obj.scrollHeight);
	}
	if(!objid){
		orect.clientWidth= Math.round(obj.clientWidth);
		orect.clientHeight= Math.round(obj.clientHeight);
	}
	return orect;
}

//相对于浏览器窗口定位
function setPosForWin(objid,align,valign,alignNum,valignNum) {
	var obj = $$(objid);
	if (obj == undefined) {return false;}

	var brect = getElementBox();
	var orect = getElementBox(obj);
	switch (align) {
	case 'left':
		obj.style.left = alignNum+'px';
		break;
	case 'right':
		obj.style.right = alignNum+'px';
		break;
	case 'center':
		obj.style.left = (brect.clientWidth - orect.width) / 2 + 'px';
		break;
	default:
		return false;
		break;
	}
	switch (valign) {
	case 'top':
		obj.style.top = valignNum+'px';
		break;
	case 'bottom':
		obj.style.bottom = valignNum+'px';
		break;
	case 'center':
		obj.style.top = (brect.clientHeight - orect.height) / 2 + 'px';
		break;
	default:
		return false;
		break;
	}
}

//相对于document定位
function setPosForDoc(obj, align, valign,alignNum,valignNum,forObj) {
	var left = 0, top = 0;
	var obj=$$(obj);
	var rootE =forObj?$$(forObj):documentRoot;
	var sLeft=document.documentElement.scrollLeft||document.body.scrollLeft;
	var sTop=document.documentElement.scrollTop||document.body.scrollTop;
	switch (align) {
		case 'left':
			left = sLeft+alignNum;
			break;
		case 'right':
			left = sLeft + rootE.clientWidth - obj.offsetWidth-alignNum;
			break;
		case 'center':
			left = sLeft + (rootE.clientWidth - obj.offsetWidth) / 2;
			break;
		default:
			return false;
			break;
	}
	switch (valign) {
		case 'top':
			top = sTop+valignNum;
			break;
		case 'bottom':
			top = sTop + rootE.clientHeight - obj.offsetHeight-valignNum;
			break;
		case 'center':
			top = sTop + (rootE.clientHeight - obj.offsetHeight) / 2;
			break;
		default:
			return false;
			break;

	}
	obj.style.left = left + 'px';
	obj.style.top = top + 'px';
}

/**
 * 简单显示提示信息
 * @param text
 */
function dialogSimple(text){
	text=text?text:"操作成功!";
	dialog('','信息',text);
	//setCommonResult(text,text?false:true);
}

//窗口显示实例
function dialog(types,titles,text,params,func){
	var _objid="msc_dialog_show";
	var _objcss="width:360px; height:245px;";
	var _wrapcss="position:relative;width:100%; height:100%;";
	var _maincss="position:absolute;top:0px;left:0px;z-index:0;width:100%;height:100%;opacity:0.3;filter:alpha(opacity=30);background:#333;";//
	var _diamaincss="position:absolute;top:12px;left:12px;z-index:1;width:94%; height:90%; background:#FFF; border:1px solid #aaaaa9";
	var _topcss="height:37px; line-height:37px; background:#F2F2F2; border-bottom:1px solid #dedede;";
	var _titlecss="float:left;color:#f33b3b; padding-left:10px; font-size:14px;";
	var _closecss="float:right; padding-top:5px;  padding-right:5px; border:none;";
	var _obj=$$(_objid);
	var _wrap,_main,_diamain,_top,_title,_a,_img;
	if(_obj==null){
		_obj=document.createElement("div");
		_wrap=document.createElement("div");
		_main=document.createElement("div");
		_diamain=document.createElement("div");
		_top=document.createElement("div");
		_title=document.createElement("div");
		_a=document.createElement("a");

		_img=document.createElement("img");
		_img.src="../V1/images/close.jpg";

		_a.setAttribute("href","#");
		if(_obj.style.cssText!=undefined){
			_obj.style.cssText=_objcss;
			_wrap.style.cssText=_wrapcss;
			_main.style.cssText=_maincss;
			_diamain.style.cssText=_diamaincss;
			_top.style.cssText=_topcss;
			_title.style.cssText=_titlecss;
			_img.style.cssText=_closecss;
		}else{
			_obj.setAttribute('style',_objcss);
			_wrap.setAttribute('style',_wrapcss);
			_main.setAttribute('style',_maincss);
			_diamain.setAttribute('style',_diamain);
		    _top.setAttribute('style',_topcss);
			_title.setAttribute('style',_titlecss);
			_img.setAttribute('style',_closecss);
		}
	}
	//CONTEXT
	var title="";
	var context;
	switch(types){
		case 'login':
			title=titles?titles:"欢迎回来";
			_obj.style.width="455px";
			_obj.style.height="260px";
			context=logindialog();
		break;
		case 'seccode':
			title=titles?titles:"欢迎回来";
			context = seccodeDialog(params);
		break;
		case 'page':
			title=titles?titles:"操作成功";
			var _pagea=document.createElement("a");
			_pagea.setAttribute("href","#");
			_pagea.setAttribute("style","text-align:center;font-size:12px;display:block;height:100px;line-height:100px");
			_pagea.innerHTML="页面跳转中…";
			context=_pagea;
			setTimeout("window.location.reload();",1000);
		break;
		case 'favok':
			title=titles?titles:"操作成功";
			var _pagea=document.createElement("a");
			_pagea.setAttribute("href","#");
			_pagea.setAttribute("style","text-align:center;font-size:14px;display:block;height:100px;line-height:100px");
			_pagea.innerHTML="收藏成功！";
			
			context=_pagea;
			setTimeout(function(){document.body.removeChild(_obj);},1000);
		default:
			// 这里只处理一个参数的情况
			title="提示";
			var _pagea=document.createElement("div");
			_pagea.setAttribute("style","text-align:center;font-size:12px;display:block;height:100px;line-height:100px");
			switch (types){
				case 'hasdo':
					_pagea.innerHTML = "您已经执行过该操作！";
				break;
				default:
					_pagea.innerHTML = text;
				break;
			}
			context=_pagea;
			setTimeout(function(){document.body.removeChild(_obj);},3000);
		break;
	}
	function closethis(){
		document.body.removeChild(_obj);
	}
	
	//显示对话框
	_obj.setAttribute('id',_objid);
	_a.appendChild(_img);
	_top.appendChild(_a);
	_title.innerHTML=title;
	_top.appendChild(_title);
	_diamain.appendChild(_top);	
	_diamain.appendChild(context);
	_wrap.appendChild(_diamain);
	_wrap.appendChild(_main);
	_obj.appendChild(_wrap);
	document.body.appendChild(_obj);
	_img.onclick=function(){
		document.body.removeChild(_obj);
		return false;
	};
	fixedBox(_obj,'center','center');
	
	 function seccodeDialog(params) {
		 var wrap = document.createElement("div");
		 wrap.innerHTML = '<div style="padding-left: 25px; padding-top: 15px; color: #333; font-size: 12px; text-align: left;"><div style="margin: 5px 0px; width: 100%; height: 25px; line-height: 25px;font-size:16px;">'+ params.question +'</div><div style="margin: 5px 0px; width: 100%; height: 25px; line-height: 25px;"><input onkeydown="this.parentNode.parentNode.parentNode.d_keydown(event);" id="id_seccode_txt" style="height: 25px; line-height: 25px; width: 80%;"></div><div>&nbsp;</div><div style="margin-left: 15%;"><input onclick="this.parentNode.parentNode.parentNode.d_ok();" style="background: url(../styles/images/ok.gif) repeat scroll 0% 0% transparent; width: 65px; height: 30px; color: rgb(206, 67, 0); border: medium none; font-size: 14px; font-weight: bold; margin-left: 10px;"type="button"><input onclick="this.parentNode.parentNode.parentNode.d_cancel();" style="background: url(../images/no.gif) repeat scroll 0% 0% transparent; width: 65px; height: 30px; color: rgb(206, 67, 0); border: medium none; font-size: 14px; font-weight: bold; margin-left: 30px;" type="button"></div></div>';
		wrap.d_cancel = function() {
			document.body.removeChild(_obj);
		};
		wrap.d_keydown = function(event) {
			var ev = window.event?window.event:event;
			if (ev && ev.keyCode == 13) {
				this.d_ok();
			}
		};
		wrap.d_ok = function() {
			var ans = document.getElementById('id_seccode_txt');
			if (ans.value) {
				params._defs.sendData += "&seccode=" + ans.value;
				try {
					xmlHttpSend(params._defs);
				} catch (e) {}
				document.body.removeChild(_obj);
			}
		};
		setTimeout(function(){
			var ans = document.getElementById('id_seccode_txt');
			if (ans)
				ans.focus();
		}, 100);
		return wrap;
	 }
	 //登录
	 function logindialog(){
		var _warpcss="position:relative;padding-top:10px; color:#6E6E6E;font-size:12px; width:429px;";
		var _div_1css="width:220px;float:left;text-align:left;height:50px;font-size:12px; 20px;line-height:25px;";
		var _div_2css="padding-top:10px;float:left;text-align:left;height:50px;font-size:12px; line-height:25px;";
		var _div_3css="float:left;height:30px;padding-top:5px";
		var _inputnamecss="background-color:#f9f9f9; border:1px solid #ccc; color:#666;font-size:13px;height:23px; line-height:23px;width:180px";
		var _acss="margin-left:23px; color:#2c629e; text-decoration:none";
		var _btnacss="float:left;width:90px;height:30px; color:#CE4300; font-size:14px;font-weight:bold; background:url(../styles/images/lilogin.gif);";
		var _div_4css="float:left;height:32px;line-height:32px;";
		var _zhuceacss="float:left;padding-left:20px;color:#4ca703;text-decoration:none ";
		var _moveacss="float:left;color:#666666;text-decoration:none";
		var _linecss="float:left;padding-left:5px;padding-right:5px";
		var warp=document.createElement("div");
		
		/*我新写的*/
		var _leftcss="width:240px;float:left; padding-left:30px;";
		var left=document.createElement("div");
		var _rightcss="width:135px; float:left; text-align:left; padding-top:5px;border-left:1px dotted #ccc;padding-left:20px;";
		var right=document.createElement("div");
		right.innerHTML="<span style='color:#6E6E6E;'>还没有账号？</span><br><a id='msregid'  href='"+window.BASESITE+"/user/user!inReg.action' style='background:url(../styles/images/freeregist.gif) no-repeat; float:left; margin-top:5px;width:90px; height:30px; display:block;'> </a><br><br><br><a href='"+ window.BASESITE +"/user/user!lostpasswd.action' style='float:left;color:#2C629E'>忘记密码？</a><br><br><br><div style='color:#6E6E6E'>用合作网站账号登陆：</div><div>"+
            "<a title=\"用qq帐号登录\" href=\"/app/qq/login.php\"><img src=\"../styles/images/qq_mini.gif\"></a>"+
           "<a style='margin-left:8px;' title=\"用sina微博登录\" href=\"/app/sina/login.php\"><img src=\"../styles/images/sina_mini.gif\"></a>"+
           "<a style='margin-left:8px;' title=\"用淘宝帐号登录\" href=\"/app/taobao/login.php\"><img src=\"../styles/images/taobao_mini.gif\"></a>"+
		   "<a style='margin-left:8px;' title=\"用豆瓣帐号登录\" href=\"/app/douban/login.php\"><img src=\"../styles/images/douban_mini.gif\"></a></div>";
		
		var _div_1=document.createElement("div");
		var _div_2=document.createElement("div");
		var  _inputname=document.createElement("input");
		var  _inputpass=document.createElement("input");
		var _div_3=document.createElement("div");
		var _a=document.createElement("a");
		_a.href=window.BASESITE+"/user/user!lostpasswd.action";
		_div_1.innerHTML="用户名：<br>";
		_div_2.innerHTML="密<span style=color:#fff>__</span>码：<br>";
		_inputname.setAttribute('name',"username");
		_inputpass.setAttribute('name',"password");
		_inputpass.setAttribute('type',"password");
		_div_3.innerHTML="<input type=checkbox name=autoLogin id=autoLogin value=true checked=checked>记住我(下次自动登录)";
		_a.innerHTML="";
		var _btna=document.createElement("a");
			_btna.href="javascript:;";

		var _div_4=document.createElement("div");
		var _zhucea=document.createElement("a");
		_zhucea.innerHTML="";
		_zhucea.href=window.BASESITE+"/user/user!inReg.action";
		_zhucea.title="";
		var _line=document.createElement("span");
		_line.innerHTML="";
		var _movea=document.createElement("a");
		_movea.innerHTML="";
		_movea.title="";
		_movea.href=window.BASESITE+"/user/user!inLogin.action";
		if(warp.style.cssText!=undefined){
			warp.style.cssText=_warpcss;
			_div_1.style.cssText=_div_1css;
			_div_2.style.cssText=_div_1css;
			_inputname.style.cssText=_inputnamecss;
			_inputpass.style.cssText=_inputnamecss;
			_div_3.style.cssText=_div_3css;
			_a.style.cssText=_acss;
			_btna.style.cssText=_btnacss;
			_zhucea.style.cssText=_zhuceacss;
			_movea.style.cssText=_moveacss;
			_div_4.style.cssText=_div_4css;
			_line.style.cssText=_linecss;
			left.style.cssText=_leftcss;
			right.style.cssText=_rightcss;
		}else{
			warp.setAttribute('style',_warpcss);
			_div_1.setAttribute('style',_div_1css);
			_div_2.setAttribute('style',_div_2css);
			_inputname.setAttribute('style',_inputnamecss);
			_inputpass.setAttribute('style',_inputnamecss);
			_div_3.setAttribute('style',_div_3css);
			_a.setAttribute('style',_acss);
			_btna.setAttribute('style',_btnacss);
			_zhucea.setAttribute('style',_zhuceacss);
			_movea.setAttribute('style',_moveacss);
			_div_4css.setAttribute('style',_div_4css);
			_line.setAttribute('style',_linecss);
			left.setAttribute('style',_leftcss);
			right.setAttribute('style',_rightcss);
		}
		_inputpass.onkeydown=function(event){
			var ev = window.event?window.event:event;
			if (ev && ev.keyCode == 13) {
			  login();
			}
		};
		_btna.onclick=function (){
			login();
		};
		function login(){
			if(_inputname.value=="" || _inputpass.value==""){
				alert("用户名或者密码不能为空！");
				return false;
			}else{
				new Request({
					url: FOODEYE.base+"/user/user!doLogin.action",
					method: 'post',
					data: "user.userNo="+_inputname.value+"&user.userPwd="+_inputpass.value,
					onSuccess: function(responseText, responseXML){
				    	 var jsonObj = JSON.decode(responseText);
				    	 if(jsonObj["issuccess"]){
				    		document.body.removeChild(_obj);
							dialog('page','登录成功！');
						 }else{
						   	window.alert(jsonObj["msg"]);
						 }				 
					},
					onFailure: function(instance){
				    }
				}).send({'__t=':new Date().getTime()});					
			}
		}

		_div_1.appendChild(_inputname);
		_div_2.appendChild(_inputpass);
		left.appendChild(_div_1);
		
		left.appendChild(_div_2);
		_div_3.appendChild(_a);
		left.appendChild(_div_3);
	
		_div_4.appendChild(_btna);
		_div_4.appendChild(_zhucea);
		_div_4.appendChild(_line);
		_div_4.appendChild(_movea);
		left.appendChild(_div_4);
		warp.appendChild(left);
		warp.appendChild(right);
		return warp;
	}
}

/**
 * 关闭投注对话框
 */
function closeDialog(objid){
	$("#"+objid).css("display","none");
};


/**
 * 拖拽对话框
 */
var closeLoginBox=function(){
	 var loginBox=document.getElementById('loginBox');
	 var cover=document.getElementById('cover');		 
	 document.body.removeChild(loginBox);
	 document.body.removeChild(cover);	 
};

var openLoginBox=function(){
var cover=document.createElement('div');
	cover.setAttribute('id','cover');
    cover.style.width='100%';
	cover.style.height=document.documentElement.scrollHeight+'px';
	cover.style.background='#333';
	cover.style.position='absolute';
	cover.style.left='0';
	cover.style.top='0';
	cover.zIndex='99';
	cover.style.opacity=0.7;
	cover.style.filter='alpha(opacity=70)';
	document.body.appendChild(cover);
var loginBox=document.createElement('div');
    loginBox.setAttribute('id','loginBox');
    loginBox.style.width='400px';
	loginBox.style.height='250px';
    loginBox.style.border='5px solid #c66';
    loginBox.style.position='absolute';
    loginBox.style.left='50%';
	loginBox.style.top=document.documentElement.scrollTop+(document.documentElement.clientHeight-250)/2+'px';
    loginBox.style.overflow='hidden';
    loginBox.style.marginLeft='-200px';
    loginBox.style.width='400px';
	loginBox.style.boxShadow='0 0 19px #666666';
	loginBox.style.borderRadius='10px';
	document.body.appendChild(loginBox);
var loginBoxHandle=document.createElement('h1');
    loginBoxHandle.setAttribute('id','loginBoxHandle');
	loginBoxHandle.style.fontSize='14px';
	loginBoxHandle.style.color='#c00';
	loginBoxHandle.style.background='#f7dcdc';
	loginBoxHandle.style.textAlign='left';
	loginBoxHandle.style.padding='8px 15px';
	loginBoxHandle.style.margin='0';
	loginBoxHandle.innerHTML='用户登录<span onclick="closeLoginBox()" title="关闭" style="position:absolute; cursor:pointer; font-size:14px;right:8px; top:8px">×</span>';
    loginBox.appendChild(loginBoxHandle);
var iframe=document.createElement('iframe');
   iframe.setAttribute('src','/index.html');
   iframe.setAttribute('frameborder','0');
   iframe.setAttribute('scrolling','no');
   iframe.setAttribute('width',400);
   iframe.setAttribute('height',250);
   loginBox.appendChild(iframe);
	new dragDrop({
        target:document.getElementById('loginBox'),
        bridge:document.getElementById('loginBoxHandle')
	});
};
	 

/* new Dragdrop({
*         target      拖拽元素 HTMLElemnt 必选
*         bridge     指定鼠标按下哪个元素时开始拖拽，实现模态对话框时用到 
*         dragX      true/false false水平方向不可拖拽 (true)默认
*         dragY     true/false false垂直方向不可拖拽 (true)默认
*         area      [minX,maxX,minY,maxY] 指定拖拽范围 默认任意拖动
*         callback 移动过程中的回调函数
* });
*/
Array.prototype.max = function() {
   return Math.max.apply({},this);
};
Array.prototype.min = function() {
   return Math.min.apply({},this);
};
var getByClass=function(searchClass){
       var tags = document.getElementsByTagName('*');
           var el = new Array();
       for(var i=0;i<tags.length;i++){
           if(tags[i].className==searchClass){
               el.push(tags[i]);
               };
           }
           return el;
   };
function dragDrop(option){
    this.target=option.target;
    this.dragX=option.dragX!=false;
    this.dragY=option.dragY!=false;
    this.disX=0;
    this.disY=0;
    this.bridge =option.bridge;
    this.area=option.area;
    this.callback=option.callback;
    this.target.style.zIndex='0';
    var _this=this;
	     this.bridge && (this.bridge.onmouseover=function(){ _this.bridge.style.cursor='move'});
     this.bridge?this.bridge.onmousedown=function(e){ _this.mousedown(e)}:this.target.onmousedown=function(e){ _this.mousedown(e)}
         }
    dragDrop.prototype={
        mousedown:function(e){
            var e=e||event;
                        var _this=this;    
             this.disX=e.clientX-this.target.offsetLeft;
             this.disY=e.clientY-this.target.offsetTop;
            this.target.style.cursor = 'move';
           
             if(window.captureEvents){ 
             e.stopPropagation();
          e.preventDefault();}
              else{
                e.cancelBubble = true;
                }
            if(this.target.setCapture){
                this.target.onmousemove=function(e){_this.move(e)}
                this.target.onmouseup=function(e){_this.mouseup(e)}
                this.target.setCapture()
                }
                else{
            document.onmousemove=function(e){_this.move(e)}
            document.onmouseup=function(e){_this.mouseup(e)}
                }
                    },
    move:function(e){
	 this.target.style.margin=0;
                var e=e||event;
                var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;
                var moveX=e.clientX-this.disX;
                var moveY=e.clientY-this.disY;
                if(this.area){
                moveX < _this.area[0] && (moveX = this.area[0]); // left 最小值
                moveX > _this.area[1] && (moveX = this.area[1]); // left 最大值
                moveY < _this.area[2] && (moveY = this.area[2]); // top 最小值
                moveY > _this.area[3] && (moveY = this.area[3]); // top 最大值                    
                    }
                    
                this.dragX && (this.target.style.left=moveX+'px');
                this.dragY && (this.target.style.top=moveY+'px');
                //限定范围
                 parseInt(this.target.style.top)<0 && (this.target.style.top=0);
                 parseInt(this.target.style.left)<0 && (this.target.style.left=0);
                 parseInt(this.target.style.left)>document.documentElement.clientWidth-this.target.offsetWidth&&(this.target.style.left=document.documentElement.clientWidth-this.target.offsetWidth+"px");
                 parseInt(this.target.style.top)>scrollTop+document.documentElement.clientHeight-this.target.offsetHeight && (this.target.style.top=scrollTop+document.documentElement.clientHeight-this.target.offsetHeight+'px');
                if(this.callback){
                    var obj = {moveX:moveX,moveY:moveY};
                    this.callback.call(this,obj)
                    }
            return false
            },
     mouseup:function (e)
            {
             var e=e||event;
             this.target.style.cursor = 'default';
             var _this=this;
			  this.target.onmousemove=null;
			  this.target.onmouseup=null;
            document.onmousemove=null;
            document.onmouseup=null;
            if(this.target.releaseCapture) {this.target.releaseCapture()}
            }    
        }

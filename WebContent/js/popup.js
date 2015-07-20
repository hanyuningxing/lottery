document.write("<style>"
+".popup-overlay{background-color:#000000; filter: alpha(opacity=20);opacity:.2;top:0; right:0; bottom:0; left:0; margin:0; padding:0;}"
+".popup-overlay-again{background-color:#000000; filter: alpha(opacity=50);opacity:.5;top:0; right:0; bottom:0; left:0; margin:0; padding:0;}"
+".popup-container{ width:100%;background-color:#FFFFFF;overflow: hidden; }"
+".popup-container .popup-titlebar{height:28px;background:url(/images/ext/t1.gif) repeat-x;cursor:move;border-top:1px solid #A3CDF1;background-position:0px -2px;}"
+".popup-container .popup-titlebar .Ptitle{font-weight:700; line-height:30px; color:#fff; margin-left:15px;background:none; font-size:13px;}"
+".popup-container .popup-titlebar .Pclose{ float:right; text-decoration:none; margin-top:5px; margin-right:3px; background:url(/images/ext/close.gif) no-repeat; width:20px; height:20px;}"
+".popup-container .popup-titlebar .Pclose:hover{background:url(/images/ext/close.gif) no-repeat;}"
+".popup-container .popup-content{border:solid 3px #DBF2FE; padding-bottom:5px;margin:0;overflow: hidden;}"
+".popup-container .popup-resize{ text-align:right;margin-top:-30px; height:30px; line-height:0;overflow: hidden;}"
+".popup-container .popup-resize div{ width:30px;line-height:30px; background:url(/images/a_popup_resize.gif) 23px 22px no-repeat;float:right;text-indent:100px;overflow: hidden;cursor:se-resize;}"
+".JsTitle{position: absolute;top: 0;left: 0;z-index: 2000;padding:5px;background-color:#0C609C;color:white;-webkit-box-shadow: 0 0 9px #999;box-shadow: 0 0 9px #999;filter: progid:DXImageTransform.Microsoft.Shadow(color='#999999',direction=135,strength=4);}"
+".JsTitleNoBG{position: absolute;top: 0;left: 0;z-index: 2000;padding:0px;-webkit-box-shadow: 0 0 9px #999;box-shadow: 0 0 9px #999;}</style>");

var Popup={
		maxZindex:1000,
		list:new Array(),
		current:null,
		settings:{target:null,autoOpen:false,createOverlay:false,overlay:undefined,overlayClass:"popup-overlay",openButton:undefined,closeButton:undefined,isOpen:false,drag:false,resize:false,autoLayout:true,listenEsc:true,align:'center',vertical:'middle',alignSlant:0,verticalSlant:0},
		init:function(settings){
			var isExist=false;
			for(var i=0;i<this.list.length;i++){
				if(this.list[i].target==settings.target){
					isExist=true;
					this.current=this.list[i];
					break;
				}
			}
			if(!isExist){
				settings.target=this.$(settings.target);
				settings.closeButton=this.$(settings.closeButton);
				if(settings.target.id==''){
					settings.target.id='popup_ad_'+this.list.length;
				}
				for(p in this.settings){
					if(!settings.hasOwnProperty(p))
					settings[p]=this.settings[p];
					}
				if(settings.closeButton){
					settings.closeButton.onclick=function(){
						Popup.getInstanceById(settings.target.id).close();
					};
				}
				if(settings.openButton){
					this.$(settings.openButton).onclick=function(){
						Popup.getInstanceById(settings.target.id).open();
					};
				}
				this.list.push(settings);
				this.current=settings;
			}
			if(settings.autoOpen){
				this.open();
			}
		},
		getInstanceById:function(id){
			for(var i=0;i<this.list.length;i++){
				var current=this.list[i];
				if(current.target.id==id){
					this.current=current;
				}
			}
			return this;
		},
		overlay:function(){
			var overlay=this.current.overlay;
			if(overlay==undefined){
				overlay=document.createElement("div");
				overlay.className=this.current.overlayClass?this.current.overlayClass:"popup-overlay";
				overlay.style.top=0;
				overlay.style.left=0;
				overlay.style.position="absolute";
				overlay.style.zIndex=++this.maxZindex;
				var currentId=this.current.target.id;
				document.body.insertBefore(overlay,document.body.childNodes[0]);
			}
			overlay.style.width=this.width()+"px";
			overlay.style.height=this.height()+"px";
			overlay.style.display="block";
			return overlay;
		},
		open:function(){
			var self=this;
			var target=this.current.target;
			if(target.parentNode!=document.body){
				document.body.insertBefore(target,document.body.childNodes[0]);
			}
			if(this.current.createOverlay){
				this.current.overlay=this.overlay();
				this.toggleIframe(true);
			}
			target.style.position="absolute";
			target.style.zIndex=++this.maxZindex;
			target.style.display="block";
			if(this.opened()==null){
				if(this.current.autoLayout===true){
					this.addEvent(window,"scroll",this.autoLayout);
					this.addEvent(window,"resize",this.autoLayout);
				}
				if(this.current.listenEsc===true)
					this.addEvent(document,"keydown",this.eKeydown);
				}
			if(this.current.closeButton){
				this.current.closeButton.focus();
			}
			this.current.isOpen=true;
			this.autoLayout(this.current);
			if(this.current.drag==true){
				var dragHandle=target;
				var titlebar=target.getElementsByTagName("div")[0];
				if(typeof(titlebar)!='undefined'&&titlebar.className.indexOf("titlebar")>-1){
					dragHandle=titlebar;
				}else if(typeof(titlebar)!='undefined'&&typeof(titlebar.getElementsByTagName("div")[0])!='undefined'){
					titlebar=titlebar.getElementsByTagName("div")[0];
					if(titlebar.className.indexOf("titlebar")>-1)
						dragHandle=titlebar;
				}
				this.eDrag(target,dragHandle);
			}
			if(this.current.resize==true){
				var resizeHandle=this.current.target.lastChild;
				if(resizeHandle.nodeType==3||resizeHandle.className.indexOf('popup-resize')==-1){
					target.onselectstart=function(e){return false;};
					resizeHandle=document.createElement('div');
					resizeHandle.className='popup-resize';
					resizeHandle.innerHTML='<div title="点击缩放大小">▲</div>';
					this.current.target.appendChild(resizeHandle);
					this.eResize(target,resizeHandle,function(x,y,target,resizeHandle){
						var self=Popup;
						target.style.width=x+'px';
						target.style.height='auto';
						var content=target.lastChild.previousSibling;
						while(content.nodeType==3){content=content.previousSibling;}
						var titleHeight=self.objProperty(target,'Height')-self.objProperty(content,'Height');
						content.style.height=y-titleHeight+'px';
						var iframe=target.getElementsByTagName('iframe')[0];
						if(typeof(iframe)!='undefined'){
							iframe.style.display='none';iframe.height=y-titleHeight+'px';iframe.width=x+'px';
						}
					},
					function(x,y,target,resizeHandle){
						var iframe=target.getElementsByTagName('iframe')[0];
						if(iframe)
							iframe.style.display='block';
					});
				}
			}
		},
		close:function(){
			if(!this.current)
				return;
			this.current.isOpen=false;
			this.current.target.style.display="none";
			if(this.current.overlay)
				this.current.overlay.style.display="none";
			this.removeEvent(window,"scroll",this.autoLayout);
			this.removeEvent(window,"resize",this.autoLayout);
			this.removeEvent(document,"keydown",this.eKeydown);
			if(this.current.createOverlay){this.toggleIframe(false);}
			if(this.opened()!=null){
				this.current=this.opened();
				if(this.current.autoLayout===true){
					this.addEvent(window,"scroll",this.autoLayout);
					this.addEvent(window,"resize",this.autoLayout);}
				if(this.current.listenEsc===true)
					this.addEvent(document,"keydown",this.eKeydown);
				if(this.current.closeButton)
					this.current.closeButton.focus();
				}
			},
		opened:function(){
			for(var i=0;i<this.list.length;i++){
				if(this.list[i].isOpen)
					return this.list[i];
			}
			return null;
		},
		create:function(containerId,title,content,width,height){
			var container=document.getElementById(containerId);
			if(container!=null){return container;}
			container=document.createElement("div");
			container.id=containerId;
			container.className="popup-container";
			container.style.display="none";
			container.style.width=width+"px";
			container.innerHTML="<div class=\"popup-titlebar\" title=\"点击拖拽\">"
				+"<a class=\"Pclose\" href=\"javascript:Popup.getInstanceById('"+containerId+"').close();\" title=\"关闭\">&nbsp;</a>"
				+"<span class=\"Ptitle\">"+title+"</span>"
				+"</div>"
				+"<div class=\"popup-content\"></div>";
			if(width&&height){
				container.lastChild.innerHTML='<iframe allowtransparency="true" frameborder="0" scrolling="no" src="'+content+'" width="'+width+'" height="'+height+'"></iframe>';
			}else{
				container.lastChild.appendChild(this.$(content));
			}
			document.body.insertBefore(container,document.body.childNodes[0]);return container;
		},
		autoLayout:function(current){
			var self=Popup;
			function layout(current){
				var target=current.target;
				var left=0;
				switch(current.align){
				case'left':left=0;break;
				case'center':left=(self.objProperty(window,"Width")-self.objProperty(target,"Width"))/2;break;
				case'right':left=self.objProperty(window,"Width")-self.objProperty(target,"Width");break;
				}
				left+=current.alignSlant;
				target.style.left=left+self.scrollLeft()+"px";
				var top=0;
				switch(current.vertical){
				case'top':top=0;break;
				case'middle':top=(self.objProperty(window,"Height")-self.objProperty(target,"Height"))/2;break;
				case'bottom':top=self.objProperty(window,"Height")-self.objProperty(target,"Height");break;
				}
				top+=current.verticalSlant;target.style.top=top+self.scrollTop()+"px";
				if(current.createOverlay){self.toggleIframe();}
				var overlay=current.overlay;
				if(overlay==undefined)
					return;
				overlay.style.width=0;
				overlay.style.height=0;
				overlay.style.width="100%";
				overlay.style.height=self.height()+"px";
			}
			if(current&&!current.type){
				layout(current);
				return;
			}
			for(var i=0;i<self.list.length;i++){
				var settings=self.list[i];
				if(settings.autoLayout&&settings.isOpen)
					layout(settings);
				}
			},
			toggleIframe:function(display){
				if(!(this.browser.msie&&this.browser.version<=6))
					return;
				if(display===true||display===false){
					var selects=document.getElementsByTagName("select");
					for(var i=0;i<selects.length;i++){
						selects[i].style.visibility=display?"hidden":"visible";
						}
					}
				return;
				var iframeId="puopup-overlay-iframe";
				var iframe=document.getElementById(iframeId);
				if(iframe==null){
					iframe=document.createElement("iframe");
					iframe.id=iframeId;
					iframe.name=iframeId;
					iframe.tabIndex=-1;
					iframe.frameBorder=0;
					iframe.style.position="absolute";
					iframe.style.top=0;
					iframe.style.left=0;
					iframe.src="about:blank";
					document.body.insertBefore(iframe,document.body.lastChild);
				}
				iframe.style.width=0;
				iframe.style.height=0;
				iframe.style.width=this.width()+"px";
				iframe.style.height=this.height()+"px";
				if(display===true||display===false)
					iframe.style.display=display?"block":"none";
				},
				eDrag:function(target,dragHandle){
					dragHandle=dragHandle||target;dragHandle.onmousedown=function(e){
						e=window.event||e;
						var _xy=parseInt(target.offsetTop)-e.clientY;
						var _xx=parseInt(target.offsetLeft)-e.clientX;
						document.onmouseup=function(){
							this.onmousemove=null;
							this.onmouseup=null;
						}
						if(e.preventDefault){e.preventDefault();}
						document.onmousemove=function(e){
							var e=window.event||e;
							target.style.top=_xy+e.clientY+"px";
							target.style.left=_xx+e.clientX+"px";
						}
						window.getSelection?window.getSelection().removeAllRanges():document.selection.empty();
					};
				},
				eResize:function(target,resizeHandle,mousemove,mouseup){
					var self=Popup;resizeHandle=resizeHandle||target;
					resizeHandle.childNodes[0].onmousedown=function(e){
						e=window.event||e;
						var y=parseInt(self.objProperty(target,'Height'))-e.clientY;
						var x=parseInt(self.objProperty(target,'Width'))-e.clientX;
						document.onmouseup=function(){
							this.onmousemove=null;
							this.onmouseup=null;
							if(mouseup)
								mouseup(x,y,target,resizeHandle);
						}
						if(e.preventDefault){e.preventDefault();}
						document.onmousemove=function(e){
							var e=window.event||e;
							if(mousemove)
								mousemove(x+e.clientX,y+e.clientY,target,resizeHandle);
						}
						window.getSelection?window.getSelection().removeAllRanges():document.selection.empty();
					};
				},
				eKeydown:function(e){
					var num=e.which?e.which:e.keyCode;
					if(num==27)
						Popup.close();
				},$:function(id){
					if(typeof(id)=='string')
						return document.getElementById(id);
					return id;
				},
			browser:{
				userAgent:navigator.userAgent.toLowerCase(),
				version:(navigator.userAgent.toLowerCase().match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/)||[])[1],
				safari:/webkit/.test(navigator.userAgent.toLowerCase()),
				opera:/opera/.test(navigator.userAgent.toLowerCase()),
				msie:/msie/.test(navigator.userAgent.toLowerCase())&&!/opera/.test(navigator.userAgent.toLowerCase()),
				mozilla:/mozilla/.test(navigator.userAgent.toLowerCase())&&!/(compatible|webkit)/.test(navigator.userAgent.toLowerCase())},
				addEvent:function(target,eventType,func){
					if(target.attachEvent){
						target.attachEvent("on"+eventType,func);
					}else if(target.addEventListener){
						target.addEventListener(eventType,func,false);
					}
					return this;
				},
			removeEvent:function(target,eventType,func){
				if(target.detachEvent){
					target.detachEvent("on"+eventType,func);
				}else if(target.removeEventListener){
					target.removeEventListener(eventType,func,false);
				}
				return this;
			},
			scrollTop:function(){
				return this.browser.msie?((document.compatMode&&document.compatMode!="BackCompat")?document.documentElement.scrollTop:document.body.scrollTop):window.pageYOffset;
			},
			scrollLeft:function(){
				return this.browser.msie?((document.compatMode&&document.compatMode!="BackCompat")?document.documentElement.scrollLeft:document.body.scrollLeft):window.pageXOffset;
			},
			height:function(){
				if(this.browser.msie&&this.browser.version<7){
					var scrollHeight=Math.max(document.documentElement.scrollHeight,document.body.scrollHeight);
					var offsetHeight=Math.max(document.documentElement.offsetHeight,document.body.offsetHeight);
					if(scrollHeight<offsetHeight){
						return this.objProperty(window,"Height");
					}else{
						return scrollHeight;
					}
				}else{
					return this.objProperty(document,"Height");
				}
			},
			width:function(){
				if(this.browser.msie&&this.browser.version<7){
					var scrollWidth=Math.max(document.documentElement.scrollWidth,document.body.scrollWidth);
					var offsetWidth=Math.max(document.documentElement.offsetWidth,document.body.offsetWidth);
					if(scrollWidth<offsetWidth){
						return this.objProperty(window,"Width");
					}else{
						return scrollWidth;
					}
				}else{
					return this.objProperty(document,"Width");
				}
			},
			objProperty:function(target,name){
				return target==window?this.browser.opera&&(name=="Height"?document.documentElement.clientHeight:document.body["client"+name])||this.browser.safari&&window["inner"+name]||document.compatMode=="CSS1Compat"&&document.documentElement["client"+name]||document.body["client"+name]:target==document?this.browser.opera&&name=="Width"?document.documentElement["client"+name]:Math.max(document.documentElement["client"+name],Math.max(document.body["scroll"+name],document.documentElement["scroll"+name]),Math.max(document.body["offset"+name],document.documentElement["offset"+name])):target["client"+name];
			}
		};
 
var _titleID_="title_"+Math.random();
var tip={
		mousePos:function(e){
			var x,y;
			var e=e||window.event;
			return{
				x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
				y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
			};
		},
		start:function(obj){
			var self=this;
			var div=document.getElementById(_titleID_);
			if(!div){
				div=document.createElement("div");
				div.id=_titleID_;
				div.className="JsTitle";
				document.body.insertBefore(div,document.body.childNodes[0]);
			}
			obj.onmousemove=function(e){
				var mouse=self.mousePos(e);
				div.style.left=mouse.x+10+'px';
				div.style.top=mouse.y+10+'px';
				div.innerHTML=obj.getAttribute("tips");
				div.style.display='';
				if(div.offsetWidth>300)
					div.style.width="300px";
				else
					div.style.width = "";
			}

			obj.onmouseout = function() { 
				div.style.width = ""; 
				div.style.display = 'none'; 
			} 
		}, 
		show: function(obj) {
			var self = this; var div = document.getElementById(_titleID_); 
			if (!div) { 
				div = document.createElement("div"); 
				div.id = _titleID_; 
				div.className = "JsTitleNoBG"; 
				document.body.insertBefore(div, document.body.childNodes[0]); 
			}

			obj.onmousemove = function(e) { 
				var mouse = self.mousePos(e); 
				div.innerHTML = obj.getAttribute("tips"); 
				div.style.left = (mouse.x - (div.offsetWidth == 0 ? 350 : div.offsetWidth)) + 'px'; 
				div.style.top = mouse.y + 10 + 'px'; div.style.display = ''; 
			}

			obj.onmouseout = function() { div.style.width = ""; div.style.display = 'none'; } 
		}, 
		show2: function(obj) {
			var self = this; 
			var div = document.getElementById(_titleID_); 
			if (!div) { 
				div = document.createElement("div"); 
				div.id = _titleID_; 
				div.className = "JsTitleNoBG"; 
				document.body.insertBefore(div, document.body.childNodes[0]); 
			}

			obj.onmousemove = function(e) { 
				var mouse = self.mousePos(e); 
				div.innerHTML = obj.getAttribute("tips"); 
				div.style.left = (mouse.x - 100) + 'px'; 
				div.style.top = mouse.y + 10 + 'px'; 
				div.style.display = ''; 
			}

			obj.onmouseout = function() { 
				div.style.width = ""; 
				div.style.display = 'none'; 
			}
		}, 
		show3: function(obj) {
			var self = this; 
			var div = document.getElementById(_titleID_); 
			if (!div) { 
				div = document.createElement("div"); 
				div.id = _titleID_; 
				div.className = "JsTitleNoBG"; 
				document.body.insertBefore(div, document.body.childNodes[0]); 
			}


			obj.onmousemove = function(e) { 
				var mouse = self.mousePos(e); 
				div.innerHTML = obj.getAttribute("tips"); 
				div.style.left = (mouse.x - 1) + 'px'; div.style.top = mouse.y + 10 + 'px'; div.style.display = ''; 
			}

			obj.onmouseout=function(){
				div.style.width="";
				div.style.display='none';
			}
		},
		show4: function(obj) {
			var self = this; 
			var div = document.getElementById(_titleID_); 
			if (!div) { 
				div = document.createElement("div"); 
				div.id = _titleID_; 
				div.className = "JsTitleNoBG"; 
				document.body.insertBefore(div, document.body.childNodes[0]); 
			}
			obj.onclick = function(e) { 
				var mouse = self.mousePos(e); 
				div.innerHTML = obj.getAttribute("tips"); 
				div.style.left = (mouse.x - 1) + 'px'; div.style.top = mouse.y + 10 + 'px'; div.style.display = ''; 
			}
		}
	}
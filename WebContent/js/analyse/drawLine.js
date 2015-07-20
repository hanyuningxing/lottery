 var BALLSIZE=8;
 /***预设容器canvas*/
 function drawCancas(arry,ballSize){
		//var arry = $('analyse_table').getElements('span[_group=drawline]');
		var size = arry.length;
		var pre = null;// 前一个对象
		if (size > 1)
			pre = arry[0];
		var curId = "curId_";
		for ( var i = 1; i < size; i++) {
			var cur = arry[i];
			if (top == null)
				top = 40;
			paintDivs(pre, cur, top, 0, curId, i,ballSize);
			pre = cur;
		}
 }
 /**画出所有的canvas容器*/
 function paintDivs(pre, next, top, left, curId, i,ballSize){
		var preArr = pos(pre);
		var nextArr = pos(next);
		var x0 = preArr.x;// 宽
		var y0 = preArr.y;// 高
		var x1 = nextArr.x;// 宽
		var y1 = nextArr.y;// 高

		if(!ballSize){
			ballSize=BALLSIZE;
		}
		var np=this.nPos(x0,y0,x1,y1,ballSize);//两端缩减函数（防止覆盖球）
		x0=np[0];y0=np[1];x1=np[2];	y1=np[3];
		if(Browser.Engine.trident){
			x1=np[0];y1=np[1];x2=np[2];	y2=np[3];		
			var line = document.createElement( "<esun:line></esun:line>" );
			line.from=x1+","+y1;
			line.to=x2+","+y2;
			line.strokeColor='#9999CC';
			line.strokeWeight="2px";
			line.style.cssText="position:absolute;z-index:999;top:0;left:0";
			//line.style.visibility=this.visible;
			line.coordOrigin="0,0";
			//this.lines.push(line);
			document.getElementById("div_line").appendChild(line);
		}else{
			var cvs=document.createElement("canvas");
			cvs.style.position="absolute";
			cvs.style.visibility="visible";
			cvs.width=Math.abs(x0-x1)||1.5;
			cvs.height=Math.abs(y0-y1)||1.5;
			var newY=Math.min(y0,y1);
			var newX=Math.min(x0,x1);
			cvs.style.top=newY+"px";
			cvs.style.left=newX+"px";
			document.getElementById("div_line").appendChild(cvs);
//			if(Browser.Engine.trident){
//				cvs=window.G_vmlCanvasManager.initElement(cvs);
//			}
			var FG=cvs.getContext("2d");
			FG.save();//缓存历史设置
			FG.strokeStyle='#9999CC';
			FG.lineWidth=1.5;
			//FG.globalAlpha=0.5;//透明度；	
			FG.beginPath(); 
			FG.moveTo(x0-newX,y0-newY);
			FG.lineTo(x1-newX,y1-newY);
			FG.closePath();
			FG.stroke();
		}
 }
 
 function nPos(x1, y1, x2, y2, r){
	var a = x1 - x2, b = y1 - y2;
	var c = Math.round(Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
	var x3, y3, x4, y4;
	var _a = Math.round((a * r)/c);
	var _b = Math.round((b * r)/c);
	return [x2 + _a, y2 + _b, x1 - _a, y1 - _b]; 
}

 function pos(obj){
 	if(obj.nodeType==undefined)return obj;// input {x:x,y:y} return;
	var pos={x:0,y:0},a=obj;
	for(;a;a=a.offsetParent){pos.x+=a.offsetLeft;pos.y+=a.offsetTop;if(this.wrap&&a.offsetParent===this.wrap)break;};// 兼容居中div
	pos.x+=parseInt(obj.offsetWidth/2);
	pos.y+=parseInt(obj.offsetHeight/2);
	return pos;
}
 
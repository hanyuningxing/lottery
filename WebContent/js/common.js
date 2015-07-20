var isIE = false;
var isIE6 = false;
var isIE8 = false;
var isIE7 = false;
var isIE9 = false;
var Sys = {};
var ua = navigator.userAgent.toLowerCase();
if(window.ActiveXObject){
	isIE = true;
	Sys.ie = ua.match(/msie ([\d.]+)/)[1];
	if(Sys.ie == "6.0"){
		isIE6 = true;
	}else if(Sys.ie == "7.0"){
		isIE7 = true;
	}else if(Sys.ie == "8.0"){
		isIE8 = true;
	}else if(Sys.ie == "9.0"){
		isIE9 = true;
	}
} 
Date.prototype.format =function(format)
{
var o = {
"M+" : this.getMonth()+1, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
}

String.prototype.trim=function(){
   var reSpace=/^\s*(.*?)\s*$/;
   return this.replace(reSpace,"$1");
};
/**
 * 验证邮件地址是否正确
 */
String.prototype.isEmail = function() {
	return this.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g);
};

/**
 * 判断字符由字母和数字，下划线组成.且开头的只能是字母
 */
String.prototype.isValidName = function() {
	return this.match(/^([a-zA-z]{1})([a-zA-z_0-9]*)$/g);
};

/**
 * 判断字符串是不是JSON对象字符串形式
 */
String.prototype.isJsonObjectString = function() {
	return this.match(/^(?:\<pre[^\>]*\>)?(\{.*\})(?:\<\/pre\>)?$/ig);
};

/**
 * 把JSON字符串转为JSON对象
 * 
 * @param jsonString JSON对象字符串形式
 */
function toJsonObject(jsonString){
  if(typeof jsonString == 'object')
    return jsonString;
	jsonString = jsonString.replace(/^(?:\<pre[^\>]*\>)?(\{.*\})(?:\<\/pre\>)?$/ig,"$1");
	return eval('(' + jsonString + ')');
}

var loadingIco = "<img src=\"${base}/images/reglog/data-loading.gif\" align=\"absmiddle\" /> ";
// 定义String的startsWith函数
String.prototype.startsWith = function(str) {
	return (this.match('^' + str) == str);
};

// 定义String的endsWith函数
String.prototype.endsWith = function(str) {
	return (this.match(str + '$') == str);
};

// 定义数组的移除函数
Array.prototype.erase = function(item){
	for (var i = this.length; i--; i){
		if (this[i] === item) this.splice(i, 1);
	}
	return this;
};

function isNullObj(obj){
    for(var i in obj){
        if(obj.hasOwnProperty(i)){
            return false;
        }
    }
    return true;
}

function Event_stopPropagation(e){
	if (e.stopPropagation) e.stopPropagation();
	else e.cancelBubble = true;
	return e;
}
function Event_preventDefault(e){
	if (e.preventDefault) e.preventDefault();
	else e.returnValue = false;
	return e;
}
function Event_stop(e){
	e = Event_stopPropagation(e);
	e = Event_preventDefault(e);
	return e;
}
function Event_target(e){
	return e.target || e.srcElement;;
}

/**
 * 是否JSON字符串
 * 
 * @param {String} str 字符串
 */
function isJsonString(str) {
	return str.startsWith('{') && str.endsWith('}');
}

/**
 * jQuery对象、对象ID、dom对象
 * 
 * @param obj
 * 
 * @return dom对象
 */
function $$(obj){
	var dom=obj;
	if(typeof(dom)=="string"){
		dom = document.getElementById(obj);
	}else if(obj[0]){ //检查jQuery对象
	  dom=obj[0];
	}	
	return dom;
}

/**
 * 获取表单中被选中的radio标签的值
 * 
 * @param {Object} submitForm 表单对象
 * @param {String} propertyName radio标签name属性
 */
function getRadioVal(submitForm, propertyName) {
	var radios = submitForm.elements[propertyName];
	if (radios) {
		if (radios.length) {
			for ( var i = 0; i < radios.length; i++) {
				if (radios[i].checked == true) {
					return radios[i].value;
				}
			}
		} else if (radios.checked == true) {
			return radios.value;
		}
	}
	return null;
}

/**
 * 获取光标在文本框中的位置
 * 
 * @param {Object} obj 文本框对象
 */
function getCursorPos(obj) {
	if (typeof obj.selectionStart == "number") {
		return obj.selectionStart;
	} else {
		obj.focus();
		var currentRange = document.selection.createRange();
		var workRange = currentRange.duplicate();
		obj.select();
		var allRange = document.selection.createRange();
		var pos = 0;
		while (workRange.compareEndPoints("StartToStart", allRange) > 0) {
			workRange.moveStart("character", -1);
			pos++;
		}
		currentRange.select();
		return pos;
	}
}

/**
 * 文本框验证：只能为数字
 * 
 * @param {Object} obj 文本框对象
 * @param {Object} evt window事件对象
 * @param {Number} type [可选]表示允许的小数位，null表示只能为数字
 */
function number_check(obj, evt, type) {
	var keyCode = evt.keyCode || evt.which;
	if (keyCode == 8 || keyCode == 46 || keyCode == 37 || keyCode == 39 || keyCode == 9 || keyCode == 229) {
		// evt.returnValue = true;
		return;
	}

	var selection;
	if (typeof obj.selectionStart == "number") {
		selection = obj.value.substring(obj.selectionStart, obj.selectionEnd);
	} else {
		selection = document.selection.createRange().text;
	}
	if (selection == obj.value) {
		if ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105)) {
			// evt.returnValue = true;
		} else {
			if (evt.preventDefault) {
				evt.preventDefault();
			} else {
				evt.returnValue = false;
			}
		}
	} else {
		if (type >= 1 && (keyCode == 110 || keyCode == 190)) {
			if (obj.value.indexOf('.') == -1) {

			} else {
				if (evt.preventDefault) {
					evt.preventDefault();
				} else {
					evt.returnValue = false;
				}
			}
		} else {
			var p = obj.value.indexOf('.');
			var cp = getCursorPos(obj);
			if (p != -1 || (obj.value != 0 && cp > 0)) {
				if ((p == -1 || cp <= p || obj.value.length - 1 - p < type)
						&& ((keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105))) {
					// evt.returnValue = true;
				} else {
					if (evt.preventDefault) {
						evt.preventDefault();
					} else {
						evt.returnValue = false;
					}
				}
			} else {
				if ((keyCode >= 49 && keyCode <= 57) || (keyCode >= 97 && keyCode <= 105)) {
					if (obj.value == 0 && getCursorPos(obj) > 0) {
						obj.value = "";
					}
					// evt.returnValue = true;
				} else {
					if (evt.preventDefault) {
						evt.preventDefault();
					} else {
						evt.returnValue = false;
					}
				}
			}
		}

	}
}


/**
 * 禁止粘贴(非IE浏览器)
 */
function fncKeyStop(evt) {
	if (!window.event) {
		var keycode = evt.keyCode;
		var key = String.fromCharCode(keycode).toLowerCase();
		if (evt.ctrlKey && key == "v") {
			evt.preventDefault();
			evt.stopPropagation();
		}
	}
}

/**
 * 生成一个min到max的随机整数
 * 
 * @parma min {Number} 生成的随机整数的最小值
 * @parma max {Number} 生成的随机整数的最大值
 */
function getRndNum(min, max) {
	return Math.round((max - min) * Math.random() + min);
}

/**
 * 供数组排序用，降序
 * 
 * @param {Number} x
 * @param {Number} y
 */
function desc(x, y) {
	x = parseInt(x, 10);
	y = parseInt(y, 10);
	if (x >= y)
		return -1;
	if (x < y)
		return 1;
}

/**
 * 供数组排序用，升序
 * 
 * @param {Number} x
 * @param {Number} y
 */
function asc(x, y) {
	x = parseInt(x, 10);
	y = parseInt(y, 10);
	if (x > y)
		return 1;
	if (x <= y)
		return -1;
}

/**
 * 获取字符串长度，1个汉字代表2单位长度
 * 
 * @param {String} sString 字符串
 */
function getBytesLength(sString) {
	var sStr, iCount, i, strTemp;
	iCount = 0;
	sStr = sString.split("");
	for (i = 0; i < sStr.length; i++) {
		strTemp = escape(sStr[i]);
		if (strTemp.indexOf("%u", 0) == -1) { // 表示是汉字
			iCount = iCount + 1;
		} else {
			iCount = iCount + 2;
		}
	}
	return iCount;
}

function getCommonMsg(jsonObj){
	var msg = '';
	if (jsonObj.actionErrors != null) {
		msg += '错误：\r\n';
		for ( var i = 0, l = jsonObj.actionErrors.length; i < l; i++) {
			msg += '\t' + jsonObj.actionErrors[i] + '\r\n';
		}
	}
	if (jsonObj.fieldErrors != null) {
		if (msg.length == 0)
			msg += '错误：\r\n';
		for ( var p in jsonObj.fieldErrors) {
			msg += '\t' + p + '\r\n';
			var errArr = jsonObj.fieldErrors[p];
			for ( var i = 0, l = errArr.length; i < l; i++) {
				msg += '\t  --' + errArr[i] + '\r\n';
			}
		}
	}
	if (jsonObj.actionMessages != null) {
		for ( var i = 0, l = jsonObj.actionMessages.length; i < l; i++) {
			msg += '\t' + jsonObj.actionMessages[i] + '\r\n';
		}
	}
	return msg;
}


/**
 * 计数组合数
 * 
 * @param r 需要取的数目
 * @param n 可供选择的数目
 * @return 组合数
 */
function comp(r, n) {
	var C = 1;
	for ( var i = n - r + 1; i <= n; i++) {
		C = C * i;
	}
	for ( var j = 2; j <= r; j++) {
		C = C / j;
	}
	return C;
}

/**
 * <b>组合算法：</b>
 * <p>
 * 本程序的思路是开一个数组，其下标表示1到m个数，数组元素的值为1表示其下标 代表的数被选中，为0则没选中。首先初始化，将数组前n个元素置1，表示第一个
 * 组合为前n个数。然后从左到右扫描数组元素值的“10”组合，找到第一个“10”组合
 * 后将其变为“01”组合，同时将其左边的所有“1”全部移动到数组的最左端。当第一
 * 个“1”移动到数组的m-n的位置，即n个“1”全部移动到最右端时，就得到了最后一个 组合。
 * </p>
 * 
 * @param n 备选数目
 * @param m 选多少个
 * @param callbackFn 回调函数
 * @return
 */
function C3(n, m,callbackFn) {
	if (m < 0 || m > n) {
		return;
	}

	var bs = [];
	for ( var i = 0; i < m; i++) {
		bs[i] = true;
	}
	if (n == m) {
		callbackFn(bs, n, m);
		return;
	}
	for ( var i = m; i < n; i++) {
		bs[i] = false;
	}

	var flag = true;
	var tempFlag = false;
	var pos = 0;
	var sum = 0;
	do {
		sum = 0;
		pos = 0;
		tempFlag = true;
		callbackFn(bs, n, m);

		for ( var i = 0; i < n - 1; i++) {
			if (bs[i] == true && bs[i + 1] == false) {
				bs[i] = false;
				bs[i + 1] = true;
				pos = i;
				break;
			}
		}

		for ( var i = 0; i < pos; i++) {
			if (bs[i] == true) {
				sum++;
			}
		}
		for ( var i = 0; i < pos; i++) {
			if (i < sum) {
				bs[i] = true;
			} else {
				bs[i] = false;
			}
		}

		for ( var i = n - m; i < n; i++) {
			if (bs[i] == false) {
				tempFlag = false;
				break;
			}
		}
		if (tempFlag == false) {
			flag = true;
		} else {
			flag = false;
		}
	} while (flag);
	callbackFn(bs, n, m);
}

function IsDigit(evt){
  var keyCode = evt.keyCode || evt.which;
  return ((keyCode >= 48) && (keyCode <= 57)||keyCode == 13);
}

function addBookmark(title,url) {
	if (window.sidebar) { 
		window.sidebar.addPanel(title, url,""); 
	} else if( document.all ) {
		window.external.AddFavorite( url, title);
	} else if( window.opera && window.print ) {
		return true;
	}
}

function setHome(obj,vrl){
    try{
    	obj.style.behavior='url(#default#homepage)';
    	obj.setHomePage(vrl);
    }catch(e){
        if(window.netscape) {
            try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
            } catch (e) { 
            	alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'"); 
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage',vrl);
         }
    }
}

function randomOrder(targetArray){
    var arrayLength = targetArray.length;
    //
    // 先创建一个正常顺序的数组
    var tempArray1 = new Array();

    for (var i = 0; i < arrayLength; i++){
        tempArray1 [i] = i;
    }
    //
    // 再根据上一个数组创建一个随机乱序的数组
    var tempArray2 = new Array();

    for (var i = 0; i < arrayLength; i++){
        // 从正常顺序数组中随机抽出元素
        tempArray2 [i] = tempArray1.splice (Math.floor (Math.random () * tempArray1.length) , 1);
    }
    //
    // 最后创建一个临时数组存储 根据上一个乱序的数组从targetArray中取得数据
    var tempArray3 = new Array();

    for (var i = 0; i < arrayLength; i++){
        tempArray3 [i] = targetArray [tempArray2 [i]];
    }
    //
    // 返回最后得出的数组
    return tempArray3;
}


/**判断表单值是否改变**/
function IsFormChanged(name) {
    var isChanged = false;
    var form = document.forms[name];
    for (var i = 0; i < form.elements.length; i++) {
           var element = form.elements[i];
           var type = element.type;
           if (type == "text" || type == "hidden" || type == "textarea" || type == "button") {
                  if (element.value != element.defaultValue) {
                         isChanged = true;
                         break;
                  }
           } else if (type == "radio" || type == "checkbox") {
                  if (element.checked != element.defaultChecked) {
                         isChanged = true;
                         break;

                  }

           } else if (type == "select-one"|| type == "select-multiple") {
                  for (var j = 0; j < element.options.length; j++) {
                         if (element.options[j].selected != element.options[j].defaultSelected) {
                                isChanged = true;
                                break;
                         }
                  }

           } else { 

           }     

    }    
    return isChanged;

}

function openWind(url){
	var top=(document.body.clientHeight-420)/2;
	var left=(document.body.clientWidth-520)/2;
	window.open(url,'window', 'height=420, width=520, toolbar =no, menubar=no, scrollbars=yes, resizable=no,top='+top+',left='+left+', location=no, status=no');
}
/**
*是否质数
*/
function isPrime(num){
	return num==1||num==3||num==5||num==7||num==11||num==13||num==17||num==19||num==23
}
function openPurchaseAgreement() {
	if ($("#purchaseAgreement").length == 0) {  
        $("body").append('<div id="purchaseAgreement"></div>');
        $("#purchaseAgreement").dialog({
            autoOpen: false,
            title: '用户协议',
            width :490,
        	height :360,
            modal: true
        });		
    }
		   $.ajax({
					type : 'GET',
					cache : false,
					url : window.BASESITE + '/html/buy-info.htm',
					success : function(htmlValue) {
						 $("#purchaseAgreement").html(htmlValue);
						 $("#purchaseAgreement").dialog('open');//设置为‘open’时将显示对话框  	
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					}
		   });
} 
function chg_right_news(value){
    var liArr = $('#rigth_news_ul li[_name="rigth_news_li"]');
    for(var i=0;i<liArr.length;i++){
       if(value==i){
           liArr[i].className  ="active";
       }else{
           liArr[i].className  ="";
       }
    }
	if(value==0){
		$("#forecast").css("display","block");
		$("#skill").css("display","none");
	}else if(value==1){
		$("#skill").css("display","block");
		$("#forecast").css("display","none");
	}else if(value==2){
		$("#result").css("display","block");
		$("#info").css("display","none");
	}else if(value==3){
		$("#info").css("display","block");
		$("#result").css("display","none");
	}
			
}

//用于创建dialog
function createDialog(id_, title_, width_, height_) {
		$(id_).dialog({  
			autoOpen:false,
			title:title_,
			width:width_, 
			height:height_, 
			modal:true
		});
	}

//银行家算法2.154=2.15  2.155=2.16  2.165=2.16  2.166=2.17 
function toDecimal(x) {  
	var f = parseFloat(x);  
	if (isNaN(f)) {  
		return false;  
	}  
	var f = Math.round(x*1000)/1000; 
	var s = f.toString();  
	var rs = s.indexOf('.');  
	if (rs < 0) {  
		rs = s.length;  
		s += '.';  
	}  
	while (s.length <= rs + 3) {  
		s += '0';  
	} 

	var pointIndex = s.indexOf('.');//小数点的位置
	var thirdChar = s.charAt(pointIndex + 3);//小数点后面第3位的字符
	var secondChar = s.charAt(pointIndex + 2);//小数点后面第2位的字符
	
	if(parseInt(thirdChar)==5) {
		if(parseInt(secondChar)%2 == 0) {
			s = parseFloat(parseInt(s*100)/100);
		} else {
			s = parseFloat(parseInt(s*100 + 1)/100);
		}
		
	} else{
		s = Math.round(s*100)/100;
	}
	
	rs = s.toString().indexOf('.');
	if (rs < 0) { 
		
		rs = s.toString().length;  
		s += '.';  
	}  
	while (s.toString().length <= rs + 2) {  
		s += '0';  
	} 
	
	return s;
	  
} 

function isInt( chars ) {
	var re=/^\d*$/;
	if (chars.match(re) == null)
	   return false;
	 else
	   return true;
}

//set返回信息
	function setCommonResult(msg,success) {
	$("#commonResultDialogData").empty();
	$("#commonResultDialogData").html(msg);
	if(success) {
		$("#tckico").addClass("tckico01");
		} else {
		$("#tckico").addClass("tckico02");
		}
	$("#commonResultDialog").dialog('open');
//	setTimeout(function() {$("#commonResultDialog").dialog('close');}, 3000);
	
	}

/****提示框***************************************************************/
function SaveTips(obj) {
    if (obj.getAttribute("tips") == "") obj.setAttribute("tips", "<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>");
    tip.show(obj);
}


function DanTips(obj){
    if (obj.getAttribute("tips") == "") obj.setAttribute("tips", "<div style='width:340px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>胆就是您确定某个投注选项肯定会猜中（可自由选择是否设胆）</div>");
	tip.show(obj);
}

function OptimizeTips(obj){
    if (obj.getAttribute("tips") == "") obj.setAttribute("tips", "<div style='width:250px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>奖金优化场次最多不能超15场、单倍投最大1000注，只支持2串1-8串1的复式倍投方案。</div>");
	tip.show(obj);
}
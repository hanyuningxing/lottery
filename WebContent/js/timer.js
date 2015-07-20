function createTimer(aElId, aYear, aMonth, aDate, aweek, aHour, aMinute, aSecond, formatStr) {
	var Timer = function () {
		var obj = null;
		var now = {year:0, month:0, date:0, week:0, hour:0, minute:0, second:0};
		this.init = function (aElId, aYear, aMonth, aDate, aweek, aHour, aMinute, aSecond) {
			if (aElId) {
				obj = document.getElementById(aElId);
				if (obj) {
					now.year = aYear;
					now.month = aMonth;
					now.date = aDate;
					now.week = aweek;
					now.hour = aHour;
					now.minute = aMinute;
					now.second = aSecond;
					showTime();
				}
			}
		};
		var showTime = function () {
			if ((++now.second) > 59) {
				now.second = 0;
				if ((++now.minute) > 59) {
					now.minute = 0;
					if ((++now.hour) > 23) {
						now.hour = 0;
						var maxDate = 0;
						switch (now.month) {
						  case 1, 3, 5, 7, 8, 10, 12:
							maxDate = 31;
							break;
						  case 4, 6, 9, 11:
							maxDate = 30;
							break;
						  case 2:
							maxDate = ((((now.year % 4 == 0) && (now.year % 100 != 0)) || (now.year % 400 == 0)) ? true : false) ? 29 : 28;
						}
						if ((++now.week) > 7) {
							now.week = 1;
						}
						if ((++now.date) > maxDate) {
							now.date = 1;
							if ((++now.month) > 12) {
								now.month = 1;
								++now.year;
							}
						}
					}
				}
			}
			var dayofweek = "";
			switch (now.week) {
			  case 1:
				dayofweek = "星期天";
				break;
			  case 2:
				dayofweek = "星期一";
				break;
			  case 3:
				dayofweek = "星期二";
				break;
			  case 4:
				dayofweek = "星期三";
				break;
			  case 5:
				dayofweek = "星期四";
				break;
			  case 6:
				dayofweek = "星期五";
				break;
			  case 7:
				dayofweek = "星期六";
				break;
			}
			var dateObj = {
				year:now.year,
				month:(now.month < 10) ? "0" + now.month : now.month,
				day:((now.date < 10) ? "0" + now.date : now.date),
				week:dayofweek,
				hour:(now.hour < 10) ? "0" + now.hour : now.hour,
				minute:(now.minute < 10) ? "0" + now.minute : now.minute,
				second:(now.second < 10) ? "0" + now.second : now.second
			};
			if(formatStr == null){
				formatStr = '{year}年{month}月{day}日 {week} {hour}:{minute}:{second}';
			}
			
			//var sNow = now.year + "年" + ((now.month < 10) ? "0" + now.month : now.month) + "月" + ((now.date < 10) ? "0" + now.date : now.date) + "日 " + dayofweek + " " + ((now.hour < 10) ? "0" + now.hour : now.hour) + ":" + ((now.minute < 10) ? "0" + now.minute : now.minute) + ":" + ((now.second < 10) ? "0" + now.second : now.second);
			obj.innerHTML = formatStr.substitute(dateObj); ;
			window.setTimeout(showTime, 1000);
		};
	};
	
	var timer = new Timer();
	timer.init(aElId, aYear, aMonth, aDate, aweek, aHour, aMinute, aSecond);
}

/**
 * 创建倒计时
 * @param {Object} options 包含以下属性：<ul>
 * <li><b>elId</b> {String} 显示信息的Element Id</li>
 * <li><b>day</b> {String} [可选]初始天数</li>
 * <li><b>hour</b> {String} [可选]初始小时</li>
 * <li><b>minute</b> {String} [可选]初始分钟</li>
 * <li><b>second</b> {String} [可选]初始秒数</li>
 * <li><b>msgFront</b> {String} [可选]时间信息前面的信息</li>
 * <li><b>msgBehind</b> {String} [可选]时间信息后面的信息</li>
 * <li><b>endMsg</b> {String} [可选]计时结束时显示的信息,默认为空</li>
 * <li><b>fnType</b> {Number} [可选]计时类型,0表示倒计时,1到表示计时,默认值为0</li>
 * <li><b>showType</b> {Number} [可选]显示类型,表示最多显示的内容，默认全部显示</li>
 * </ul>
 */
function createTimeWork(options){
	var TimeWork = function(){
		var obj = null;
		var endMsg = "";
		var msgFront = "";
		var msgBehind = "";
		var showType = 4;
		var data = {day:0, hour:0, minute:0, second:0};
		var execFn = null;
		var finishFn = null;
		this.init = function (options) {
			if (options.elId) {
				obj = document.getElementById(options.elId);
				if (obj) {
					if(options.endMsg && typeof options.endMsg == 'string'){
						endMsg = options.endMsg;
					}
					if(options.msgFront && typeof options.msgFront == 'string'){
						msgFront = options.msgFront;
					}
					if(options.msgBehind && typeof options.msgBehind == 'string'){
						msgBehind = options.msgBehind;
					}
					if(options.showType && typeof options.showType == 'number' && options.showType > 0){
						showType = options.showType;
					}
					
					if(options.day && typeof options.day == 'number' && options.day > 0)
						data.day = options.day;
					if(options.hour && typeof options.hour == 'number' && options.hour > 0 && options.hour < 24)
						data.hour = options.hour;
					if(options.minute && typeof options.minute == 'number' && options.minute > 0 && options.minute < 60)
						data.minute = options.minute;
					if(options.second && typeof options.second == 'number' && options.second > 0 && options.second < 60)
						data.second = options.second;
						
					if(options.fnType && typeof options.fnType == 'number' && options.fnType == 1)
						execFn = countUpFn;
					else
						execFn = countDownFn;
					
					if(options.finishFn && typeof options.finishFn == 'function')
						finishFn = options.finishFn;
						
					execFn();
				}
			}
		};
		
		var countDownFn = function () {
			if((--data.second) < 0){
				data.second = 59;
				if((--data.minute) < 0){
					data.minute = 59;
			    	if((--data.hour) < 0){
			      		data.hour = 23;
			      		--data.day;
			   	 	}
			  	}		 
			}  
			
			showFn();
		};
		
		
		var countUpFn = function () {
			if((++data.second) > 59){
				data.second = 0;
				if((++data.minute) > 59){
					data.minute = 0;
			    	if((++data.hour) > 23){
			      		data.hour = 0;
			      		++data.day;
			   	 	}
			  	}		 
			}
			
			showFn();
		};
		
		var showFn = function(){
			var str = '';
			if(data.day < 0){
		  		str = endMsg;
		  		if(finishFn != null){
		  			finishFn();
		  		}
			}else{
				str += msgFront;
				
				var count = 0;
				if(data.day > 0){
		  			str += data.day+"天";
		  			count ++;
		  		}
		  		if((count == 0 && data.hour > 0) || (count > 0 && count < showType)){
		  			str += data.hour+"小时";
		  			count ++;
		  		}
		  		if((count == 0 && data.minute > 0) || (count > 0 && count < showType)){
		  			str += data.minute+"分钟"
		  			count ++;
		  		}
		  		if((count == 0 && data.second > 0) || (count > 0 && count < showType)){
		  			var tempStr = data.second+"秒";
		  			if(count == 0){
		  				tempStr = '<font color="red">' + tempStr + "</font>";
		  			}
		  			str += tempStr;
		  			count ++;
		  		}
		  		
				str += msgBehind;
				
		  		window.setTimeout(execFn,1000);	      
			}
		
			obj.innerHTML = str;	
		}
		
	};
	
	
	var instance = new TimeWork();
	instance.init(options);
}
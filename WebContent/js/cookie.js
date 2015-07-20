/**
 * Cookie操作类
 */
var Cookies = {};

/**
 * 设置Cookie
 * @param {Object} o javascript对象，该对象可以包含以下属性：<ul>
 * <li><b>name</b> {String} cookie名称</li>
 * <li><b>value</b> {String} cookie值</li>
 * <li><b>expires</b> {Number} (可选)，cookie失效时间，单位秒</li>
 * <li><b>path</b>	{String} (可选)，cookie路径</li>
 * <li><b>domain</b> {String} (可选)，cookie域</li>
 * <li><b>secure</b> {Boolean}(可选)，我也不清楚这个有什么用</li>
 * </ul>
 */
Cookies.set = function(o){
	 var expdate = new Date();
     if(o.expires !== null){
		expdate.setTime(expdate.getTime() + ( o.expires * 1000 ));
	 } 
	 
     document.cookie = o.name + "=" + escape (o.value) +
       ((o.expires === undefined) ? "" : ("; expires=" + expdate.toGMTString())) +
       ((o.path === undefined) ? "" : ("; path=" + o.path)) +
       ((o.domain === undefined) ? "" : ("; domain=" + o.domain)) +
       ((o.secure === true) ? "; secure" : "");
};

/**
 * 根据cookie名称获取相应cookie值
 * @param {Object} name cookie名称
 */
Cookies.get = function(name){
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	var j = 0;
	while(i < clen){
		j = i + alen;
		if (document.cookie.substring(i, j) == arg){
			return Cookies.getCookieVal(j);
		}
		i = document.cookie.indexOf(" ", i) + 1;
		if(i === 0){
			break;
		}
	}
	return null;
};

/**
 *清除指定的cookie
 * @param {Object} name cookie名称
 */
Cookies.clear = function(name) {
  if(Cookies.get(name)){
    document.cookie = name + "=" +
    "; expires=Thu, 01-Jan-70 00:00:01 GMT; path=/";
  }
};

/**
 * 从cookie名值对中取出值
 * @param {Object} offset cookie名值对
 */
Cookies.getCookieVal = function(offset){
   var endstr = document.cookie.indexOf(";", offset);
   if(endstr == -1){
       endstr = document.cookie.length;
   }
   return unescape(document.cookie.substring(offset, endstr));
};
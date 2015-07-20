//var forumSite = "forum.ppp.com";
var forumSite = "forum.hclotto";
<!-- 登录球客论坛 -->	
function goToDZC(){
	//var url = "http://www.ppp.com:8080/lottery-gz/dzc.do";
	var url = "http://www.cai310.com/dzc.do";
	var params = {
		action : "login"	
	};	
	$.ajax({
		type : 'post',
		cache : false,
		dataType : 'json',
		params:params,
		url : url,
		success : function(data) {
			if(data.msg=="login")
				loginCallback(data);
			else
				logoutCallback(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});	
	
}



function loginCallback(data) {

	if (data.result == "success") {

		if (data.dzurl != "") {
			var idx = data.dzurl.indexOf("/", 7);
			var url = "http://" + forumSite + data.dzurl.substr(idx);
			var index = url.indexOf("/api");
			var baseUrl = url.substring(url,index,0);
	
			window.open(baseUrl,"球客论坛");
			var reloadUrl = "<script language=\"javascript\" src=\"" + url + "\" reload=\"1\" ><\/script>";		
			var jsIframe = document.createElement("iframe");
			jsIframe.style.display = "none";// 把jsIframe隐藏起来
			document.body.appendChild(jsIframe);
			with (window.frames[window.frames.length - 1]) {
				document.open();
				document.write(reloadUrl); // 执行JS代码
				document.close();
			}
			//document.body.removeChild(jsIframe);
		}
	} else {

	}
}

function logoutCallback(data) {
	if (data.result == "success") {

		if (data.dzurl != "") {
			var idx = data.dzurl.indexOf("/", 7);
			var url = "http://" + forumSite + data.dzurl.substr(idx);
			var index = url.indexOf("/api");
			var baseUrl = url.substring(url,index,0);
			
			window.open(baseUrl,"球客论坛");
			var reloadUrl = "<script language=\"javascript\" src=\"" + url + "\" reload=\"1\" ><\/script>";
			var jsIframe = document.createElement("iframe");
			jsIframe.style.display = "none";// 把jsIframe隐藏起来
			document.body.appendChild(jsIframe);
			with (window.frames[window.frames.length - 1]) {
				document.open();
				document.write(reloadUrl); // 执行JS代码
				document.close();
			}
			//document.body.removeChild(jsIframe);
		}
	} else {
		
	}

}
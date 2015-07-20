function dxjo_click(el) {
	var className = 'trchoseo1';
	el = $(el);
	if (el.hasClass(className)) {
		el.removeClass(className);
	} else {
		el.addClass(className);
	}
}


function chgDisplay(value,formName){
	$("#count").val(value);
	document.forms[0].submit();
}

function searchDisplay(){
	var start = $("#start").val();
	var end = $("#end").val();
	/*if(""!=start&&!/[0-9]{11}$/.test(start)){
		alert("查询的格式有误！格式为:20110701001");
		return ;
	}
	if(""!=end&&!/[0-9]{11}$/.test(end)){
		alert("查询的格式有误！格式为:20110701001");
		return ;
	}*/

	document.forms[0].submit();
}
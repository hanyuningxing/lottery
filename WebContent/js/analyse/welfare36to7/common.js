function dxjo_click(el) {
	var className = 'trchoseo';
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
	document.forms[0].submit();
}

function sortDisplay(value){
	$("#sort").val(value);	
	document.forms[0].submit();
}


CLASS_OBJ = {
		red : {
			selected : 'nowball',
			unselected : 'gball'
		},
		blue : {
			selected : 'bball',
			unselected : 'gballblue'
		}
	};

	function red_click(el) {
		var cls_obj = CLASS_OBJ.red;
		el.className = (el.className == cls_obj.selected) ? cls_obj.unselected : cls_obj.selected;
	}

	function blue_click(el) {
		var cls_obj = CLASS_OBJ.blue;
		el.className = (el.className == cls_obj.selected) ? cls_obj.unselected : cls_obj.selected;
	}
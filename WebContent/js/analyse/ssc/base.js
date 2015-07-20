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
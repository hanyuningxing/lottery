CLASS_OBJ = {
	selected : 'nowball',
	unselected : 'gball'
};

function ball_click(el) {
	var cls_obj = CLASS_OBJ;
	el.className = (el.className == cls_obj.selected) ? cls_obj.unselected : cls_obj.selected;
}
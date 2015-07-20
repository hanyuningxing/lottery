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

//质数列表
var primeNumberArr=[1,2,3,5,7,11,13,17,19,23,29,31];

//判断是否是质数
function isPrimeNum(num){
	for(var i=0;i<primeNumberArr.length;i++){
		if(Number(num)==primeNumberArr[i]){
			return true;
		}
	}
	return false;
}
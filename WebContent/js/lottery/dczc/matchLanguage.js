function chgLanguange(mode) {
	var arr = [ '_gy', '_gd' ];
	var s = arr[mode];
	var alltable = $('alltable');
	$('#alltable span[' + s + ']').each(function(index, domEle) {
		domEle.innerHTML = domEle.getAttribute(s);
	});
}
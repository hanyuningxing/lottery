function chgLanguange(mode) {
	var arr = [ '_gy', '_gd' ];
	var s = arr[mode];
	var s2 = arr[arr.length-1-mode];
	var alltable = $('alltable');
	$('#alltable span[' + s + ']').each(function(index, domEle) {
		domEle.innerHTML = domEle.getAttribute(s);
	});
	document.getElementById('match_language'+s).style.fontWeight = 'bold';
	document.getElementById('match_language'+s2).style.fontWeight = 'normal';
}
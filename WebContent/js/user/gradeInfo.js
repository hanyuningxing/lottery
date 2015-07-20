var zq_class_prefit="qk_football";
var lq_class_prefit="qk_basketball";

function showGradeInfo(obj,gradeMedalInfoArr){
	if(!gradeMedalInfoArr)return;
	var html = buildGradeDetailHtml(gradeMedalInfoArr);
	obj.setAttribute("tips",html);
    tip.show3(obj);
}

function buildGradeDetailHtml(gradeMedalInfoArr){
	var html = '<div style="border:3px solid #ccc; color:#333; width:300px; font-family:Tahoma; font-size:12px; position:absolute;background:#FFF;z-index:9999">' +
	'<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:1px solid #e4e4e4;">';
	for(var i=0;i<gradeMedalInfoArr.length;i++){
		var gradeMedalOfPt = gradeMedalInfoArr[i];
		html += '<tr style="border-bottom:1px solid #e4e4e4;">'+
		 ' <td width="40%" height="26" align="center" bgcolor="#ee7700" style="color:#FFF;border-bottom:1px solid #e4e4e4;">' + gradeMedalOfPt[1] + '</td>'+
		 ' <td align="center" bgcolor="#F2F2F2" style="border-bottom:1px solid #e4e4e4;"> ' + bulidShowIcon(gradeMedalOfPt) + '</td>'+
		'</tr>';
	}
	html +='</table>';
	return html;
}

function bulidShowIcon(gradeMedalOfPt){
	var iconHtml ="";
	var type=gradeMedalOfPt[0];
	var classStr = "";
	if(type=='zq'){
		classStr=zq_class_prefit;
	}else if(type=='lq'){
		classStr=lq_class_prefit;
	}
	var classIndex=1;
	for(var i=2;i<gradeMedalOfPt.length;i++){
		var num = gradeMedalOfPt[i];
		iconHtml+='<span class="'+ classStr +'0'+ classIndex +'" style="height:12px;width:'+ num*13 +'px;"></span>';
		classIndex++;
	}
	return iconHtml;
}
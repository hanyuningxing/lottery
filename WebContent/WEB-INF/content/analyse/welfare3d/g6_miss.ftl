<head>
	<title>${lotteryType.lotteryName}组6遗漏</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/welfare3d/w3d_g6_miss.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/welfare3d/base.js"/>"></script>
	<script type="text/javascript">
		/**
		 * 切换显示的期数目
		 * 
		 * @param elId - (<em>string</em>) 当前切换的标签ID
		 * @param size - (<em>number</em>) 显示的期数目
		 */
		var chooseNum=30;
		function chgDisplay(elId, size) {
			$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
				buttonEl.className = (elId == buttonEl.id) ? 'btnow' : 'btgray';
			});
			chooseNum=size;
	    }
	    HEADER='<table width="980" border="0" cellpadding="0" cellspacing="0"><tr><td width="240" valign="top">';
	    MIDD='</td><td width="5">&nbsp;</td><td width="240" valign="top">';
	    FOOTER='</td></tr></table>';
	    TAB_HEADER = '<table width="240" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" ><tr align="center"  class="trtitlebg"><td width="70">号码 </td><td>当前遗漏 </td><td>历史最大遗漏</td></tr>'
	    TAB_FOOTER = '</table>'
		window.addEvent('domready', function() {
		    var htmlTmp=HEADER;
		    
			var data = w3d_g6_miss;
			var miss;
			var nowmiss;
			var count = 0;
			var table1=TAB_HEADER;
			var table2=TAB_HEADER;
			var table3=TAB_HEADER;
			var table4=TAB_HEADER;
			for ( var num in data) {
				miss=data[num];
				if(miss>=chooseNum){
				   nowmiss=chooseNum;
				}else{
				   nowmiss=miss;
				}
				count++;
				if(180>=count){
				     table1+='<tr align="center" class="trw"><td><span class="redredbchar">'+num+'</span></td><td>'+nowmiss+'</td><td>'+miss+'</td></tr>';
				}else if(180 < count && count<= 360 ){
				     table2+='<tr align="center" class="trw"><td><span class="redredbchar">'+num+'</span></td><td>'+nowmiss+'</td><td>'+miss+'</td></tr>';
				}else if(360 < count && count<= 540){
				     table3+='<tr align="center" class="trw"><td><span class="redredbchar">'+num+'</span></td><td>'+nowmiss+'</td><td>'+miss+'</td></tr>';
				}else if(540 < count && count<= 720){
				     table4+='<tr align="center" class="trw"><td><span class="redredbchar">'+num+'</span></td><td>'+nowmiss+'</td><td>'+miss+'</td></tr>';
				}
			}
			table1+=TAB_FOOTER;
			table2+=TAB_FOOTER;
			table3+=TAB_FOOTER;
			table4+=TAB_FOOTER;
			htmlTmp+=table1;
			htmlTmp+=MIDD;
			htmlTmp+=table2;
			htmlTmp+=MIDD;
		    htmlTmp+=table3;
		    htmlTmp+=MIDD;
		    htmlTmp+=table4;
		    htmlTmp+=FOOTER;
		    var MISS_EL = document.getElementById('MISS_EL');
		    MISS_EL.innerHTML=htmlTmp;
			chgDisplay('size_30', 30);
		});
</script>
</head>
<div class="main980">
<#assign type='g6Miss'/>
        <#include "top.ftl"/>
		<#include "../top_common.ftl"/>
</div>
<#include "../bottom_common.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.domain}-${webapp.webName}彩票网-彩票合买-彩票包年-竞彩-北单-体育彩票-福利彩票${title}</title>
<meta name="Keywords" content="彩票、${webapp.webName}、${webapp.domain}、竞彩"/>
<meta name="Description" content="${webapp.domain}${webapp.webName}彩票网覆盖了快开彩，竞彩，体育彩票，福利彩票。是一家服务于中国彩民的互联网彩票合买代购交易平台"/>
<script type="text/javascript">
window.BASESITE='${base}';
var linebg = '<div class="linebg1"></div>';
var table_header = '<table width="243" border="0" cellspacing="1" cellpadding="0"><tr align="center" valign="middle">';
function get_max_miss(name,arrb,arrs,twoFormat){
     var htmlTemp = linebg;
     htmlTemp +=table_header;
     htmlTemp +='<td rowspan="3"  class="redboldchar">'+name+'</td>';
     htmlTemp +='<td  class="redc">热</td>';
     $.each(arrb,function(idx,item){
            var name = item.name;
            if(twoFormat){
	            if(!isNaN(item.name)){
				   if(parseInt(item.name, 10)<10){
				      name = '0' + parseInt(item.name, 10)
				   }
				}
            }
            
		    htmlTemp +='<td width="26" ><span class="redballsingle">'+name+'</span></td><td ><span class="tstip">'+item.value+'</span></td>';
	 });
	   htmlTemp +='</tr><tr align="center" valign="middle"><td colspan="7" ><div class="linebg1"></div></td></tr>';
	   htmlTemp +='<tr align="center" valign="middle"><td >冷</td>';
	 $.each(arrs,function(idx,item){ 
	         var name = item.name;
            if(twoFormat){
	            if(!isNaN(item.name)){
				   if(parseInt(item.name, 10)<10){
				      name = '0' + parseInt(item.name, 10)
				   }
				}
            }
		    htmlTemp +='<td width="26" ><span class="grayballsingle">'+name+'</span></td><td ><span class="tstip">'+item.value+'</span></td>';
	 });
     htmlTemp +='</tr></table>';
     return htmlTemp;							   
}
//title=标题，htmlValue=提示HTML，width=宽度(默认auto)，height=高度(默认auto)，modal是否modal提示（默认是true，弹出后不能选择提示框外）
		function openDialog(title,htmlValue,width,height,modal){
				$("#common_dialog").dialog('option', 'autoOpen', false);  
		        if(width)$("#common_dialog").dialog('option', 'width', width);
		        if(height)$("#common_dialog").dialog('option', 'height', height);
				if(title)$("#common_dialog").dialog('option', 'title', title);
				if(modal)$("#common_dialog").dialog('option', 'modal', modal); 	
				if(htmlValue)$("#common_dialog").html(htmlValue);		
				$("#common_dialog").dialog('open');//设置为‘open’时将显示对话框  
		}
		//title=标题，url=请求Url，width=宽度(默认auto)，height=高度(默认auto)，modal是否modal提示（默认是true，弹出后不能选择提示框外）
		function openAjaxDialog(title,url,width,height,modal){
		        $("#common_dialog").dialog('option', 'autoOpen', false);  
		        if(width)$("#common_dialog").dialog('option', 'width', width);
		        if(height)$("#common_dialog").dialog('option', 'height', height);
				if(title)$("#common_dialog").dialog('option', 'title', title);
				if(modal)$("#common_dialog").dialog('option', 'modal', modal); 	
				if(url){
				   $.ajax({
							type : 'GET',
							cache : false,
							url : window.BASESITE + url,
							success : function(htmlValue) {
								 $("#common_dialog").html(htmlValue);	
								 $("#common_dialog").dialog('open');//设置为‘open’时将显示对话框  	
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
							}
				   });
				}
		}
</script>
<script type="text/javascript" src="<@c.url value= "/jquery/jquery-1.8.3.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/lottery-common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/lottery/count.js"/>"></script>		
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>

<script src="${base}/jquery/ui/js/jquery-ui-1.9.2.custom.min.js"/></script>
<link href="${base}/jquery/ui/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/js/jquery/formValidator/style/validator.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator_min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>

<link href="${base}/V1/css/jczq.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/qktz.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/yhzx.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/tlogin.css" rel="stylesheet" type="text/css" />
${head}
</head>
<body>
    <div style="height: 1000px; display: none;" class="coverDiv" id="jq_guide_cover"></div>
	
   <#assign head_index='1'/>
   <#include "/V1/common/header.ftl"/>
 	${body}
	<#include "/V1/common/footer.ftl" />
</body>
</html>

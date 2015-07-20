<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>11选5开奖和手机滚屏页</title>
<style charset=utf-8>
body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,lengend,button,input,textarea,th,td{margin:0;padding:0;}
body,button,input,select,textarea{font:12px/1.5 Arial,sans-serif,"微软雅黑";}
/*+++++++++ public top bottom end +++++++++ */ 
.toptr01{ background:#FF0000; text-align:center; font-size:140px;word-spacing:100px;line-height:177px; font-weight:bold; height:177px;}
.toptr01 td{border-bottom:3px solid #DABE68; color:#fff;}

.top_tr01{ background:#fcfdc8; text-align:center;font-size:200px;word-spacing:100px;line-height:296px; font-weight:bold; height:296px;}
.top_tr01 td{border-bottom:3px solid #DBCF98; color:#FF0000;}
.xiugaibg {
		width:80px;
		height:30px;
		line-height:30px;
		color:#fff;
		text-align:center;
		background:url(${base}/V1/images/xiugaibg.png) no-repeat;
		display:inline-block;
		text-decoration:none;
		cursor:pointer;
		margin:0 auto;
	}
	a.xiugaibg, a.xiugaibg:visited, a.xiugaibg:hover {
		color:#fff;
		text-decoration:none;
	}
	.input1 {
		width:200px;
		height:20px;
		line-height:20px;
		color:#999;
		border:1px solid #A4ACB4;
		margin:0;
		padding:0;
	}
	.tdright10 {
		padding-right:10px;
	}
</style>
<script type="text/javascript" src="${base}/js/common.js"></script>
<script type="text/javascript" src="<@c.url value= "/jquery/jquery-1.8.3.js"/>"></script>
<script src="${base}/jquery/ui/js/jquery-ui-1.9.2.custom.js"/></script>
<link href="${base}/jquery/ui/css/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/jczq.css" rel="stylesheet" type="text/css" />
</head>
<body>

<!--手机号码输入对话框 -->
<div id="mobileWriteDialog" style="margin:20px;">
	<span><p>请输入手机号码！</p></span>	</br>	
	<form id="mobileWriteForm" >
		<table width="300" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size:14px; color:#515B66;">
		  <tr height="33">
		    <td width="60" align="right" class="tdright10">手机号码</td>
		    <td><input type="text" name="mobile" id="mobile" class="input1" /></td>
		  </tr>
		  <tr height="33">
		    <td align="right"></td>
		    <td><a href="javascript:;" class="xiugaibg" onclick="javascript:$('#mobileWriteForm').submit(); return false;">确定</a>&nbsp;
		    <a href="javascript:;" onclick="$('#mobileWriteDialog').dialog('close');" class="xiugaibg">返回</a></td>
		  </tr>
		</table>		
	</form>
</div> 

<!--公共结果对话框 -->
<div id="commonResultDialog" style="padding: 0px;"> 
	<div class="tckbg">	
			<div class="tcknr">
				<table width="400" border="0" cellspacing="0" cellpadding="0" align="center">
	       			<tr>
						<td width="50"><span id="tckico" class="tckico01"></span></td>
						<td><span class="tckwz" id="commonResultDialogData"></span></td>						
					</tr>	
					<tr align="center">
						<td>&nbsp;</td>
						<td><div style="padding: 0px 150px 0px 25px;"><a href="javascript:;" onclick="$('#commonResultDialog').dialog('close');" class="tckan"></a></div>	</td>						
					</tr>	
				</table>
			</div>
		
	</div>
</div> 

<script>
	var id_top = {};
</script>
<table width="1920" border="0" cellspacing="0" cellpadding="0" height="1080" id="zj_t">
  <tr class="toptr01" id="head_zj" style="position:absolute;top:0px;z-index:9999">
    <td style="width:1933px;">中奖用户手机号<input type="button" id="bt" value="停止" onclick="changeFunc();"></input></td>
  </tr>
  
   <#if mobileList?? && mobileList?size gt 0>
   		<#assign i=0>
		<#list mobileList as data>		
		<#assign top = 179 + 300*i>
		   <tr class="top_tr01" id="mobile_${i}" style="position:absolute;top:${top}px;<#if i gt 2>display:none</#if>" >
		    	<td style="width:1933px">${data.number?substring(0,4)}XXX${data.number?substring(7)}</td>
		   </tr>
		  <script>
		  		id_top["mobile_${i}"] = ${top};
		  </script>
		<#assign i=i+1>
  		</#list>
  	</#if>
  	
</table>
<script>
window.BASESITE='${base}';
	$(function(){   
		createDialog('#mobileWriteDialog', '手机填写', 420, 210);
		 createDialog('#commonResultDialog', '提示信息', 350, 219);
		$('#mobileWriteForm').submit(function() {
			mobile  = $('#mobileWriteForm [name="mobile"]').val();
				$.post(window.BASESITE + '/info/mobile!addMobile.action',{
					mobile: mobile
					},function(data) {
						$('#mobileWriteDialog').dialog('close');
						if(data.success){
							setCommonResult(data.msg, true);
						} else{
							setCommonResult(data.msg, false);
						}
					},"json");			 
			return false; 
		});		
	});
	var interval;	
	window.onload = function(){
		interval = setInterval(sroll, 1);
	}
	
	function sroll(){
		for(i=0,maxSize=${mobileList.size()}; i<maxSize; i++){
			var top = id_top["mobile_" + i] -1;
			document.getElementById("mobile_" + i).style.top = top + "px";
			id_top["mobile_" + i] = top;
			if(top==-179){
				id_top["mobile_" + i] = 179 + 300*(maxSize-1) - 60;
				document.getElementById("mobile_" + i).style.display="none";
			} else {
				if(top==179+3*300-1) {
					document.getElementById("mobile_" + i).style.display="";
				}
			}
		}
	}
	
	var state=0;
	
	function changeFunc() {
		if(state==1) {
			state=0;
			interval = setInterval(sroll, 1);
			document.getElementById("bt").value="停止";
		} else {
			state=1;
			clearInterval(interval);
			document.getElementById("bt").value="滚动";
		}
	}
</script>
</body>
</html>

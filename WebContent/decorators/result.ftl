<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.domain}-${webapp.webName}彩票网-彩票合买-彩票包年-竞彩-北单-体育彩票-福利彩票${title}</title>
<meta name="Keywords" content="彩票、${webapp.webName}、${webapp.domain}、竞彩"/>
<meta name="Description" content="${webapp.domain}${webapp.webName}彩票网覆盖了快开彩，竞彩，体育彩票，福利彩票。是上市公司中国通讯服务(股票代码${webapp.domain})旗下一家服务于中国彩民的互联网彩票合买代购交易平台"/>
<link href="<@c.url value= "/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/kjpd.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/index.css"/>" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<@c.url value= "/jquery/jquery-1.8.3.js"/>"></script>
<script src="${base}/jquery/ui/js/jquery-ui-1.9.2.custom.min.js"/></script>
<link href="${base}/jquery/ui/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>		
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
<script type="text/javascript">
	    function drawedIssue(key){
	       if(key!=-1||key!='-1'||key!="-1"){
				var url="${base}/"+key+"/result!drawedIssue.action";
				$("#issue_select").empty();
				$.ajax({ 
						type: 'GET',
						cache : false,
						url: url,
						success : function(data, textStatus) {
							var jsonObj = toJsonObject(data);
							try {
								if(jsonObj.options!=null){
									var optionObj ;
									for(var i=0;i<jsonObj.options.length;i++){
									      $("#issue_select").append("<option value='"+jsonObj.options[i].value+"'>"+jsonObj.options[i].word+"</option>");  //添加一项option
									}
								}
							} catch (e) {
								
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							
						}
				});
		  }
	   }
	   function gotoIssue(){
	       var lottery_select_form=document.getElementById('lottery_select_form');
	       var lottery_select = lottery_select_form.elements['lottery_select'];
	       var playType = lottery_select_form.elements['playType'];
	       var issue_select= document.getElementById('issue_select');
	       var id=issue_select.options[issue_select.selectedIndex].value;
	       var lotteryName=lottery_select.options[lottery_select.selectedIndex].innerHTML;
	       var key=lottery_select.options[lottery_select.selectedIndex].value;
	       if(key!=-1||key!='-1'||key!="-1"){
	          if(id!=-1||id!='-1'||id!="-1"){
	            var url="${base}/"+key+"/result!resultInfo.action";
	            if(lotteryName=="排列三"){
	                playType.value="0";
	            }else if(lotteryName=="排列五"){
	                playType.value="1";
	            }else if(lotteryName=="胜负彩"){
	               playType.value="SFZC14";
	            }else if(lotteryName=="任选9场"){
	               playType.value="SFZC9";
	            }else{
	               playType.value="";
	            }
				lottery_select_form.action=url;
				lottery_select_form.submit();
			  }
		   }
	   }
	   function copyResult(obj){
	      copyToClipboard(obj.title);
	   }
	</script>
${head}
<script type="text/javascript">
	var BASESITE='${base}';
</script>
</head>
<body>
    <#assign head_index='3'/>
    <#include "/V1/common/header.ftl"/>
 	${body}
	<#include "/V1/common/footer.ftl" />
	
</body>
</html>

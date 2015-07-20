<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${webapp.domain}-${webapp.webName}彩票网-彩票合买-彩票包年-竞彩-北单-体育彩票-福利彩票${title}</title>
<meta name="Keywords" content="彩票、${webapp.webName}、${webapp.domain}、竞彩"/>
<meta name="Description" content="${webapp.domain}${webapp.webName}彩票网覆盖了快开彩，竞彩，体育彩票，福利彩票。是上市公司中国通讯服务(股票代码${webapp.domain})旗下一家服务于中国彩民的互联网彩票合买代购交易平台"/>

<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value= "/pages/css/guoguang.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/jquery/jquery-1.8.3.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidator.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/formValidator/formValidatorRegex.js"/>"></script>
<script src="${base}/jquery/ui/js/jquery-ui-1.9.2.custom.js"/></script>
<link href="${base}/jquery/ui/css/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
<title>${webapp.webName}-福利彩票|体育彩票|足球彩票网上合买代购平台,双色球,福彩3D，大乐透，排列三，排列五</title>
<meta name="keywords" content="网上购彩" />
<meta name="description" content="合买彩票" />
${head}
<script type="text/javascript">
	var BASESITE='${base}';
</script>
</head>
<body>
    <#include "/V1/common/header.ftl"/>
    <script type="text/javascript">
     function showMoney(){ 
	      document.getElementById('_s_2').style.display="";
          document.getElementById('_s_1').style.display="none";
	 }
	 function hiddenMoney(){ 
	      document.getElementById('_s_1').style.display="";
          document.getElementById('_s_2').style.display="none";
	 }
	 function checkLogin(){ 
	              $.ajax({ 
						type: 'GET',
						cache : false,
						url: "${base}/user/user!checkLogin.action",
						success : function(data, textStatus) {
						     	var jsonObj = toJsonObject(data);
						     	if(jsonObj.success){
						     	    //没有登录
						     	    document.getElementById('index_login_name').innerHTML=jsonObj.info.userName;
						     	    document.getElementById('_s_2').innerHTML=jsonObj.info.remainMoney;
						     	    document.getElementById('login_ed_div').style.display="";
						     	    document.getElementById('un_login_div').style.display="none";
						     	}else{
						     	    //没有登录
						     	    document.getElementById('login_ed_div').style.display="none";
						     	    document.getElementById('un_login_div').style.display="";
						     	}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							
						}
					});
					return false;
	 }
	 //checkLogin();
</script>

 	${body}
	<#include "/V1/common/footer.ftl" />
	
</body>
</html>

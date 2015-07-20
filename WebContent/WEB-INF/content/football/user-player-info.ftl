<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta name="decorator" content="none" />
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/pdzx.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<style type="text/css">
body{margin:0;padding:0;font-size:12px}
div{font-size:12px;font-family:Arial, Helvetica, sans-serif;line-height:150%;}
form,li,ul,dl{padding:0;margin:0}
a{text-decoration:none;color:#000}
a:hover{text-decoration:underline;color:#960000}
.clear{clear:both;}
.btn_send{background:url(${base}/pages/images/btn_common.jpg) no-repeat;border:0;color:#FFF;width:88px;height:30px;line-height:30px;cursor:pointer}
.error{padding-top:10px;color:red}
.login_title{background:url(${base}/pages/images/uc_login_title.png) no-repeat;}
.login_title ul{width:358px;padding:0;margin:0;clear:both}
.login_title li{float:left;height:28px;line-height:28px;text-align:left;font-size:14px;list-style-type:none}
.login_title #nor{text-indent:25px;font-weight:bold;width:117px;}
.login_title #ten_pay a{padding-left:35px;width:70px;font-weight:bold;}
.login_title #ali_pay a{padding-left:70px;width:30px;font-weight:bold;}
.login_title a{color:#000;display:inline-block;}
.login_title a:hover{color:#960000}

.login_table{border:1px solid #CCC;border-top:0;padding:20px;text-align:center;background:#FFF}
.login_table td{height:20px;}
.login_table .input_txt{border:1px solid #CCC;height:22px;line-height:22px;padding-left:3px;width:170px;font-family:Arial, Helvetica, sans-serif}

#userquickLogin{width:360px;margin:20px auto 0 auto;}
.quick_link{text-align:center;padding-top:10px;}
.quick_link a{display:inline-block;width:110px}
</style>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript">
	function selectTable(num,obj){
       var commonTable=document.getElementById('commonTable');
       var kaTable=document.getElementById('kaTable');
       var commonA=document.getElementById('commonA');
       var kaA=document.getElementById('kaA');
       if(0==num){
           commonA.style.color="red";
           kaA.style.color="";
           commonTable.style.display="";
           kaTable.style.display="none";
       }
       if(1==num){
           commonA.style.color="";
           kaA.style.color="red";
           commonTable.style.display="none";
           kaTable.style.display="";
       }
    }
</script>
</head>
<body>
	<div class="login_title">
	    <ul>
			<li id="ten_pay"><a href="#" onclick="selectTable(0);return false;" id="commonA">基本资料</a></li>
	        <li id="ali_pay"><a href="#" onclick="selectTable(1);return false;" id="kaA">卡片</a></li>
		</ul>
	    <ul class="login_table">  
		    <table id="commonTable" width="400" border="0" cellspacing="0" cellpadding="0"  class="hmTable">
		              <#if playerInfo??>
		                          <thead >
							         <tr align="center">
								     <#list playerInfo?keys as key>
								            <td align="center" width="150" >${key}</td>
								            <td align="center" width="100" ><b>${playerInfo.get(key)}</b></td>
									          <#if (key_index+1)%2==0>
									               </tr></thead><thead><tr align="center">
								             </#if>
								      </#list>
								       </tr>
								   </thead>
					  </#if>
		    </table>
		    <table id="kaTable" width="400" border="0" cellspacing="0" cellpadding="0"  class="hmTable" style="display:none;">
		              <#if playerKaInfo??>
		                          <thead >
							         <tr align="center">
								     <#list playerKaInfo?keys as key>
								            <td align="center" width="150" >${key}</td>
								            <td align="center" width="100" ><b>${playerKaInfo.get(key)}</b></td>
									          <#if (key_index+1)%2==0>
									               </tr></thead><thead><tr align="center">
								             </#if>
								      </#list>
								       </tr>
								   </thead>
					  </#if>
		    </table>
		</ul>
	</div>
</body>
</html>
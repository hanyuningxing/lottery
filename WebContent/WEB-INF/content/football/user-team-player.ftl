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
.login_title #ten_pay a{padding-left:35px;width:70px;}
.login_title #ali_pay a{padding-left:70px;width:30px;}
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
</head>
<body>
	<div class="login_title" style="width:100%;" >
	    <ul class="login_table"  style="width:100%;" >  
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
		       	<thead>
					<tr align="center">
					    <td width="20" height="20">序</td>
					    <td>位置</td>
					    <td>名字</td>
				        <td>定价</td>
				        <td>现价</td>
					</tr>
				</thead>
				<tbody>
				<#if playerList?? && playerList?size gt 0>
				<#list playerList as data>
				<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
				<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				  <td height="20">${data_index+1}</td>
			      <td>${data.reg_position}</td>
			      <td><a href="<@c.url value= "/football/user!playerInfo.action?id=${data.id!}"/>" target="_blank">${data.name!}</a></td>
			      <td>${data.price!}</td>
	              <td>${data.now_price!}</td>
				</tr>
				</#list>
			  <#else>
				<tr>
				  <td colspan="4" align="center" >暂无记录.</td>
				</tr>
			  </#if>
			</tbody>
		</table>
	  </ul>
	</div>
</body>
</html>
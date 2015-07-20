<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta name="decorator" content="none" />
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
.login_table td{padding-bottom:5px;}
.login_table .input_txt{border:1px solid #CCC;height:22px;line-height:22px;padding-left:3px;width:170px;font-family:Arial, Helvetica, sans-serif}

#userquickLogin{width:360px;margin:20px auto 0 auto;}
.quick_link{text-align:center;padding-top:10px;}
.quick_link a{display:inline-block;width:110px}
</style>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#opr_form").submit(function(){
        $("#submit").attr('disabled',true);
            var name_J = $("input[name=name_J]").val();
            var id = $("input[name=id]").val();
            var sesson1 = $("select[name=sesson1]").val();
            var sesson2 = $("select[name=sesson2]").val();
            var round = $("select[name=round]").val();
            $.ajax({
                type: "post",
                url : "<@c.url value= "/football/user!sclassCreate.action"/>",
                dataType:'json',
                async:false,
                data: {
                      id:id,
                      name_J:name_J,
                      sesson1:sesson1,
                      sesson2:sesson2,
                      round:round
                },
                success:function(json){
					if(json.success == true){
						try{
						   var msg = getCommonMsg(json);
						   window.alert(msg);
						   parent.location.reload();
						}catch( ee ){}
					}else{
						var msg = getCommonMsg(json);
						window.alert(msg);
                    	$("#submit").attr('disabled',false);
					}
                },
                error:function(){
                    window.alert('网络错误，请重试');
                    $("#submit").attr('disabled',false);
                }
            });
        return false;
    });
});

</script>
</head>
<body>
	<div class="login_title">
	    <ul class="login_table">  
	    
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <input name="id" id="id"  type="hidden"  value="<#if id??>${id}</#if>"/>
		        <form id="opr_form" action="" method="post" autocomplete="off">
		            <tr>
		                <td width="25%">联赛名：</td>
		                <td width="75%" align="left"><input name="name_J" id="name_J"  type="text"  maxlength="10" size="10" value="<#if name_J??>${name_J}</#if>"/></td>
		            </tr>
		            <tr>
		                <td width="25%">当前赛季：</td>
		                <td width="75%" align="left">
		               	   <select name="sesson1">
					           <#list 2011..2020 as a>
					              <option value="${a}" <#if sesson1??&&sesson1 ==a>selected</#if>>${a}</option>
					           </#list>
					      </select>
					             -
					      <select name="sesson2">
					           <#list 1..12 as a>
					              <option value="${a}" <#if sesson2??&&sesson2 ==a>selected</#if>>${a}</option>
					           </#list>
					      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">显示轮数：</td>
		                <td width="75%" align="left">
		               	   <select name="round">
					           <#list 0..20 as a>
					              <option value="${a}" <#if round??&&round ==a>selected</#if>>${a}</option>
					           </#list>
					       </select>
		                </td>
		            </tr>
		            <tr>
		                <td></td>
		                <td align="left"><input id="submit" type="submit" class="btn_send" value="提交" /></td>
		            </tr>
		        </form>
		    </table>
		</ul>
	</div>
</body>
</html>
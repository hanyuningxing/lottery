<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>调整返点</title>
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
            var userId = $("input[name=userId]").val();
             var rebate1 = $("select[name=rebate1]").val();
              var rebate2 = $("select[name=rebate2]").val();
            $.ajax({
                type: "post",
                url : "<@c.url value= "/user/user!oprRebate.action"/>",
                dataType:'json',
                async:false,
                data: {userId:userId,rebate1:rebate1,rebate2:rebate2},
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
		        <form id="opr_form" action="" method="post" autocomplete="off">
		            <input type="hidden" name ="userId" value="${opr_user_id!}"/>
		            <tr>
		                <td width="25%">我的返点：</td>
		                <td width="75%" align="left">${my_rebate!}</td>
		            </tr>
		            <tr>
		                <td width="25%">用户名：</td>
		                <td width="75%" align="left">${opr_user_name!}</td>
		            </tr>
		            <tr>
		                <td>返&nbsp;&nbsp;&nbsp;&nbsp;点：</td>
		                <td align="left">
		                <select name="rebate1">
					           <#list 0..20 as a>
					              <option value="${a}" <#if rebate1 ==a>selected</#if>>${a}</option>
					           </#list>
					      </select>
					               点
					      <select name="rebate2">
					           <#list 0..9 as a>
					              <option value="${a}" <#if rebate2 ==a>selected</#if>>${a}</option>
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
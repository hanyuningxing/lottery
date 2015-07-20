<title>用户管理</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="user"/>
<script type="text/javascript" src="<@c.url value='/js/jquery/jquery-1.4.2.min.js'/>"></script>
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
                url : "<@c.url value= "/admin/fund/fund!oprRebate.action"/>",
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

<form id="opr_form" action="" method="post" autocomplete="off">
<input type="hidden" name ="userId" value="${opr_user_id!}"/>

<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="center" align="left">
	 					<tr bgcolor="#F5f5f5">
			                <td width="25%">用户名：</td>
			                <td width="75%" align="left">${opr_user_name!}</td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
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
			            <tr bgcolor="#F5f5f5">
			                <td></td>
			                <td align="left"><input id="submit" type="submit" class="btn_send" value="提交" /></td>
			            </tr>
			          </table>
    </form>  
    
</body>
</html>
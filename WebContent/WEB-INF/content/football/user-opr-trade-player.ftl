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
            
            var playId = $("select[name=playId]").val();
            var my_price = $("select[name=my_price]").val();
            var teamId = $("select[name=teamId]").val();
            var his_playId = $("select[name=his_playId]").val();
            var his_price = $("select[name=his_price]").val();
            $.ajax({
                type: "post",
                url : "<@c.url value= "/football/user!tradePlayer.action"/>",
                dataType:'json',
                async:false,
                data: {
                      playId:playId,
                      my_price:my_price,
                      teamId:teamId,
                      his_playId:his_playId,
                      his_price:his_price
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
function getTeamPlayerById(){
            var teamId = $("select[name=teamId]").val();
            if('0'==teamId||'-1'==teamId){
                var his_playId_div=document.getElementById('his_playId_div');
				his_playId_div.innerHTML = '';  
            }
            $.ajax({
                type: "post",
                url : "<@c.url value= "/football/user!getTeamPlayerById.action"/>",
                dataType:'json',
                async:false,
                data: {
                      teamId:teamId
                },
                success:function(json){
					if(json.success == true){
						try{
						        var list=json.list;
						        var temp="";
						        temp+="<select name=\"his_playId\" id=\"his_playId\">";
							    for(var i=0;i<list.length;i++){
							          temp+="<option value=\""+list[i].id+"\">"+list[i].name+"-"+list[i].now_price+"</option>";
								}
								temp+="</select>";
								var his_playId_div=document.getElementById('his_playId_div');
								his_playId_div.innerHTML = temp;
						}catch( ee ){}
					}else{
						window.alert(json.msg);
                    	$("#submit").attr('disabled',false);
					}
                },
                error:function(){
                    window.alert('网络错误，请重试');
                    $("#submit").attr('disabled',false);
                }
            });
   
}
</script>
</head>
<body>
	<div class="login_title">
	    <ul class="login_table">  
	        <input name="id" id="id"  type="hidden"  value="<#if id??>${id}</#if>"/>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <form id="opr_form" action="" method="post" autocomplete="off">
		            <tr>
		                <td width="25%">我的球员：</td>
		                <td width="75%" align="left">
					          <select name="playId">
						          <#if teamList??>
						           <option value="">无</option>
						           <#list playerList as player>
						              <option value="${player.id}" >${player.name!}-${player.now_price!}</option>
						           </#list>
						         </#if>
						      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">我的金额：</td>
		                <td width="75%" align="left">
					          <select name="my_price">
						           <#list 0..2000  as price>
						              <#if price%50==0><option value="${price}" >${price!}</option></#if>
						           </#list>
						      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">交易球队：</td>
		                <td width="75%" align="left">
					          <select name="teamId" onchange="getTeamPlayerById()">
						          <#if teamList??>
						           <option value="0">无</option>
						           <#list teamList as team>
						              <option value="${team.id}">${team.name!}</option>
						           </#list>
						           <option value="-1">解约</option>
						         </#if>
						      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">交易球员：</td>
		                <td width="75%" align="left">
		                     <div id = "his_playId_div"></div>
		                </td>
		            </tr>
		           <tr>
		                <td width="25%">交易方金额：</td>
		                <td width="75%" align="left">
					          <select name="his_price">
						           <#list 0..2000  as price>
						              <#if price%50==0><option value="${price}" >${price!}</option></#if>
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
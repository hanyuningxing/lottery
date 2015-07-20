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
            var scheduleId = $("input[name=scheduleId]").val();
            var homeScore = $("select[name=homeScore]").val();
            var guestScore = $("select[name=guestScore]").val();
            var homeGoal = $("select[name=homeGoal]");
            var homeGoalStr ="";
            for(var i=0;i<homeGoal.length;i++){
                  var  homeGoalSelect = homeGoal[i];
                  homeGoalStr += homeGoalSelect.options[homeGoalSelect.selectedIndex].value+"-";
            }
            var guestGoal = $("select[name=guestGoal]");
            var guestGoalStr ="";
            for(var i=0;i<guestGoal.length;i++){
                  var  guestGoalSelect = guestGoal[i];
                  guestGoalStr += guestGoalSelect.options[guestGoalSelect.selectedIndex].value+"-";
            }
            
            
            var homeRedNum = $("select[name=homeRedNum]").val();
            var guestRedNum = $("select[name=guestRedNum]").val();
            var homeRed = $("select[name=homeRed]");
            var homeRedStr ="";
            for(var i=0;i<homeRed.length;i++){
                  var  homeRedSelect = homeRed[i];
                  homeRedStr += homeRedSelect.options[homeRedSelect.selectedIndex].value+"-";
            }
            var guestRed = $("select[name=guestRed]");
            var guestRedStr ="";
            for(var i=0;i<guestRed.length;i++){
                  var  guestRedSelect = guestRed[i];
                  guestRedStr += guestRedSelect.options[guestRedSelect.selectedIndex].value+"-";
            }
            
            
            var explain = $("input[name=explain]").val();
            $.ajax({
                type: "post",
                url : "<@c.url value= "/football/user!oprSchedule.action"/>",
                dataType:'json',
                async:false,
                data: {
                     scheduleId:scheduleId,
                     explain:explain,
                     guestScore:guestScore,
                     homeScore:homeScore,
                     homeGoalStr:homeGoalStr,
                     guestGoalStr:guestGoalStr,
                     
                     homeRedNum:homeRedNum,
                     guestRedNum:guestRedNum,
                     homeRedStr:homeRedStr,
                     guestRedStr:guestRedStr
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
function gethomeGoalSelect(){
    var num = $("select[name=homeScore]").val();
    var temp="";
    for(var i=1;i<=num;i++){
	      temp+="<select name=\"homeGoal\" id=\"homeGoal_"+i+"\">";
									           <#if homePlayer??>
									               <#list homePlayer as player>
										               temp+="<option value=\"${player.id}\">${player.name!}</option>";
										           </#list>
										             temp+="<option value=\"0\">对方乌龙</option>";
									            </#if> 
		   temp+="</select>";
	}
	var homeGoalSpan=document.getElementById('homeGoalSpan');
	homeGoalSpan.innerHTML = temp;
	
}
function getguestGoalSelect(){
    var num = $("select[name=guestScore]").val();
    var temp="";
    for(var i=1;i<=num;i++){
	      temp+="<select name=\"guestGoal\" id=\"guestGoal_"+i+"\">";
									           <#if guestPlayer??>
									               <#list guestPlayer as player>
										               temp+="<option value=\"${player.id}\">${player.name!}</option>";
										           </#list>
										           temp+="<option value=\"0\">对方乌龙</option>";
									            </#if> 
		   temp+="</select>";
	}
	var guestGoalSpan=document.getElementById('guestGoalSpan');
	guestGoalSpan.innerHTML = temp;
	
}

function gethomeRedSelect(){
    var num = $("select[name=homeRedNum]").val();
    var temp="";
    for(var i=1;i<=num;i++){
	      temp+="<select name=\"homeRed\" id=\"homeRed_"+i+"\">";
									           <#if homePlayer??>
									               <#list homePlayer as player>
										               temp+="<option value=\"${player.id}\">${player.name!}</option>";
										           </#list>
									            </#if> 
		   temp+="</select>";
	}
	var homeRedSpan=document.getElementById('homeRedSpan');
	homeRedSpan.innerHTML = temp;
	
}
function getguestRedSelect(){
    var num = $("select[name=guestRedNum]").val();
    var temp="";
    for(var i=1;i<=num;i++){
	      temp+="<select name=\"guestRed\" id=\"guestRed_"+i+"\">";
									           <#if guestPlayer??>
									               <#list guestPlayer as player>
										               temp+="<option value=\"${player.id}\">${player.name!}</option>";
										           </#list>
									            </#if> 
		   temp+="</select>";
	}
	var guestRedSpan=document.getElementById('guestRedSpan');
	guestRedSpan.innerHTML = temp;
	
}
</script>
</head>
<body>
	<div class="login_title" style="width:100%">
	    <ul class="login_table"  style="width:100%">  
	        <input name="scheduleId" id="scheduleId"  type="hidden"  value="<#if scheduleId??>${scheduleId}</#if>"/>
		    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <form id="opr_form" action="" method="post" autocomplete="off">
		            <tr>
		                <td width="25%">赛事：</td>
		                <td width="75%" align="left">
		                       ${homeName!} VS ${guestName!}
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">比分：</td>
		                <td width="75%" align="left">
					          <select name="homeScore" onchange="gethomeGoalSelect();return false;">
						           <#list 0..20 as a >
						              <option value="${a}" <#if homeScore??&&homeScore ==a>selected</#if>>${a}</option>
						           </#list>
						      </select>
						      -
						      <select name="guestScore" onchange="getguestGoalSelect();return false;">
						           <#list 0..20 as a>
						              <option value="${a}" <#if guestScore??&&guestScore ==a>selected</#if>>${a}</option>
						           </#list>
						      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">进球者：</td>
		                <td width="75%" align="left">
						             主队：<br/>
						      <div id="homeGoalSpan">
						      <#if homeGoal??&& homeGoal?size gt 0>
						              <#list homeGoal as homeGoalId>
						                  <select name="homeGoal" id="homeGoal_${homeGoalId_index}">
								           <#if homePlayer??>
								               <#list homePlayer as player>
									              <option value="${player.id}" <#if player.id ==homeGoalId>selected</#if>>${player.name!}</option>
									           </#list>
									           <option value="0" <#if 0 ==homeGoalId>selected</#if>>对方乌龙</option>
								            </#if> 
								           </select>
						              </#list>
						      </#if>
						      </div>
						      <br/>
						               客队：<br/>
						       <div id="guestGoalSpan">
							      <#if guestGoal??&& guestGoal?size gt 0>
							              <#list guestGoal as guestGoalId>
							                  <select name="guestGoal" id="guestGoal_${guestGoalId_index}">
									           <#if guestPlayer??>
									               <#list guestPlayer as player>
										              <option value="${player.id}" <#if player.id ==guestGoalId>selected</#if>>${player.name!}</option>
										           </#list>
										             <option value="0" <#if 0 ==guestGoalId>selected</#if>>对方乌龙</option>
									            </#if> 
									           </select>
							              </#list>
							      </#if>
						      </div>
		                </td>
		            </tr>
		            
		            <tr>
		                <td width="25%">红牌：</td>
		                <td width="75%" align="left">
					          <select name="homeRedNum" onchange="gethomeRedSelect();return false;">
						           <#list 0..5 as a >
						              <option value="${a}" <#if homeRedNum??&&homeRedNum==a>selected</#if>>${a}</option>
						           </#list>
						      </select>
						      -
						      <select name="guestRedNum" onchange="getguestRedSelect();return false;">
						           <#list 0..5 as a>
						              <option value="${a}" <#if guestRedNum??&&guestRedNum ==a>selected</#if>>${a}</option>
						           </#list>
						      </select>
		                </td>
		            </tr>
		            <tr>
		                <td width="25%">红牌者：</td>
		                <td width="75%" align="left">
						             主队：<br/>
						      <div id="homeRedSpan">
						      <#if homeRed??&& homeRed?size gt 0>
						              <#list homeRed as homeRedId>
						                  <select name="homeRed" id="homeRed_${homeRedId_index}">
								           <#if homePlayer??>
								               <#list homePlayer as player>
									              <option value="${player.id}" <#if player.id ==homeRedId>selected</#if>>${player.name!}</option>
									           </#list>
								            </#if> 
								           </select>
						              </#list>
						      </#if>
						      </div>
						      <br/>
						               客队：<br/>
						       <div id="guestRedSpan">
							      <#if guestRed??&& guestRed?size gt 0>
							              <#list guestRed as guestRedId>
							                  <select name="guestRed" id="guestRed_${guestRedId_index}">
									           <#if guestPlayer??>
									               <#list guestPlayer as player>
										              <option value="${player.id}" <#if player.id ==guestRedId>selected</#if>>${player.name!}</option>
										           </#list>
									            </#if> 
									           </select>
							              </#list>
							      </#if>
						      </div>
		                </td>
		            </tr>
		            <#if create_time??>
                    <tr>
		                <td>创建时间:</td>
		                <td align="left">${create_time}</td>
		            </tr>
		            </#if>
		            <#if last_time??>
                    <tr>
		                <td>最后更新时间:</td>
		                <td align="left">${last_time}</td>
		            </tr>
		            </#if>
		            <#if canUpdate??&&canUpdate>
		            <tr>
		                <td></td>
		                <td align="left"><input id="submit" type="submit" class="btn_send" value="提交" /></td>
		            </tr>
		            </#if>
		        </form>
		    </table>
		</ul>
	</div>
</body>
</html>
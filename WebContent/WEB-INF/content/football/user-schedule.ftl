<head>
	<title>赛程</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="findSchedule" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<script>
	   function oprMethod(width,height,src,title){
		    $floater({
				width : width,
				height : height,
				src : window.BASESITE + src,
				title : title,
				fix : 'false'
			});
	   }
	</script>
	<style>
	     
          .lun {color:#F47704; font-weight:bold;font-size:14px;}
	      .team {font-weight:bold;font-size:14px;}
	</style>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'findSchedule'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	<span style="float:left">
	    	       <form action="<@c.url value="/football/user!findSchedule.action" />" method="get" id="scheme_list_form">
	    	                <select name="sclassId" onchange="this.form.submit();">
					             <#if sclassList??>
						           <#list sclassList as sclass>
						             	 <option value="${sclass.id}" <#if sclassId??&& sclass??&&sclass.id??&&sclass.id ==sclassId>selected</#if> >${sclass.name_J}</option>
						           </#list>
						         </#if>
						     </select>
		           </form>
	    	</span>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
	        <td>主队</td>
	        <td>比分</td>
	        <td>客队</td>
	        <td>操作</td>
		</tr>
	</thead>
	<tbody>
     <#if scheduleMap??>
		<#list scheduleMap?keys as key>
		   <thead>
				<tr align="center">
				    <td colspan="3"><span class="lun">第${key_index+1}轮</span></td>
				    <td>&nbsp;</td>
				</tr>
			</thead>
		   <#list scheduleMap.get(key) as schedule>
			<#if (schedule_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
			<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
			   <td align="right" class="<#if teamId==schedule.homeTeamID>lun<#else>team</#if>"><a href="#" onclick = "oprMethod(410,540,'/football/user!teamPlayer.action?teamId=${schedule.homeTeamID!}','查看人员');return false;"><#if schedule.homeTeamEnum??>${schedule.homeTeamEnum.teamName}<img style="vertical-align:middle;" src="<@c.url value= "/pages${schedule.homeTeamEnum.teamPicUrl}" />" width="30" height="30"/></a> </#if></td>
			   <td style="font-size:16px">${schedule.score!}</td>
			   <td align="left" class="<#if teamId==schedule.guestTeamID>lun<#else>team</#if>"><a href="#" onclick = "oprMethod(410,540,'/football/user!teamPlayer.action?teamId=${schedule.guestTeamID!}','查看人员');return false;"><#if schedule.guestTeamEnum??><img src="<@c.url value= "/pages/${schedule.guestTeamEnum.teamPicUrl}"/>" style="vertical-align:middle;"  width="30" height="30"/>${schedule.guestTeamEnum.teamName}</a></#if></td>
			   <td><#if teamId==schedule.homeTeamID||teamId==schedule.guestTeamID||(maneger??&&maneger)><a href="#" onclick = "oprMethod(400,500,'/football/user!oprSchedule.action?scheduleId=${schedule.id}','报分');return false;">报分</a><#else>&nbsp;</#if></td>
			</tr>
			</#list>
		</#list>
	  <#else>
		<tr>
		  <td colspan="3" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
</table>
 <div class="kong5"></div>
    <div class="kong10"></div>
  </div>
</div>
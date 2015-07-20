<head>
	<title>积分榜</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="findTeamOrder" />
	<script>
	   function oprMethod(width,height,src,title){
		    $floater({
				width : width,
				height : height,
				src : window.BASESITE + src,
				title : title,
				fix : 'true'
			});
	   }
	</script>
	<style>
	   .my_team{ background:#FFF500;}
	</style>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'findTeamOrder'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop" style="width:500px">
	    	<span style="float:left;width:500px"><form action="<@c.url value="/football/user!findTeamOrder.action" />" method="get" id="scheme_list_form">
	    	                <select name="sclassId" onchange="this.form.submit();">
					             <#if sclassList??>
						           <#list sclassList as sclass>
						             	 <option value="${sclass.id}" <#if sclassId??&& sclass??&&sclass.id??&&sclass.id ==sclassId>selected</#if> >${sclass.name_J}</option>
						           </#list>
						         </#if>
						     </select>
		           </form></span>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>参赛者</td>
		    <td width="40"></td>
		    <td>队名</td>
		    <td>场次</td>
		    <td>赢</td>
	        <td>平</td>
	        <td>负</td>
	        <td>进球</td>
	        <td>失球</td>
	        <td>净胜球</td>
	        <td>积分</td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr <#if teamId??&&data.teamId??&&data.teamId==teamId>class="my_team"<#else>class="${trClass!}" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'"</#if> align="center">
		  <td height="32">${data_index+1}</td>
		  <td><#list teamList as team><#if team.id==data.teamId>${team.userName}</#if></#list></td>
		  <td><#if data.teamEnum??><img src="<@c.url value= "/pages${data.teamEnum.teamPicUrl}" />" width="30" height="30"/></#if></td>
	      <td><a href="#" onclick = "oprMethod(410,540,'/football/user!teamPlayer.action?teamId=${data.teamId!}','查看人员');return false;">${data.teamName!}</a></td>
	      <td>${data.win+data.draw+data.lose}</td>
	      <td>${data.win!}</td>
	      <td>${data.draw!}</td>
	      <td>${data.lose!}</td>
	      <td>${data.goal!}</td>
	      <td>${data.loseGoal!}</td>
	      <td>${data.winGoal!}</td>
	      <td>${data.fen!}</td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="10" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>队员</td>
		    <td>进球</td>
		    <td>所属球队</td>
		</tr>
	</thead>
	<tbody>
     <#if goalList?? && goalList?size gt 0>
		<#list goalList as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr <#if teamId??&&data.teamID??&&data.teamID==teamId>class="my_team"<#else>class="${trClass!}" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'"</#if> align="center">
		  <td height="32">${data_index+1}</td>
	      <td><a href="#" onclick = "oprMethod(420,560,'/football/user!playerInfo.action?id=${data.playerID!}','球员明细');return false;">${data.playername!}</a></td>
	      <td>${data.num_}</td>
	      <td>${data.teamName!}</td>
		</tr>
		</#list>
	  </#if>
	</tbody>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>队员</td>
		    <td>红牌</td>
		    <td>所属球队</td>
		</tr>
	</thead>
	<tbody>
     <#if redList?? && redList?size gt 0>
		<#list redList as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr <#if teamId??&&data.teamID??&&data.teamID==teamId>class="my_team"<#else>class="${trClass!}" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'"</#if> align="center">
		  <td height="32">${data_index+1}</td>
	      <td><a href="#" onclick = "oprMethod(420,560,'/football/user!playerInfo.action?id=${data.playerID!}','球员明细');return false;">${data.playername!}</a></td>
	      <td>${data.num_}</td>
	      <td>${data.teamName!}</td>
		</tr>
		</#list>
	  </#if>
	</tbody>
</table>
	  
	  
	  
	  
    <div class="kong10"></div>
  </div>
</div>
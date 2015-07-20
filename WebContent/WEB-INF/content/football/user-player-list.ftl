<head>
	<title>球星榜</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="playerList" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
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
	     
          .lun {color:#F47704; font-weight:bold;font-size:14px;}
	      .team {font-weight:bold;font-size:14px;}
	</style>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'playerList'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	<span style="float:left">
	    	</span>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<tbody>
     <#if playerMap??>
		<#list playerMap?keys as key>
		   <thead>
				<tr align="center">
				    <td align="left"><span class="lun">${key}</span></td>
				</tr>
			</thead>
			<#if (key_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
			<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
			  <td align="left">
			     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
			         <tr align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				      <#list playerMap.get(key) as player>
				            <td align="left"><#if player??&&player.name??><a href="#" onclick = "oprMethod(420,560,'/football/user!playerInfo.action?id=${player.id!}','球员明细');return false;"><#if player.teamId??><span class="lun">${player.name!}</span><#else><span class="team">${player.name!}</span></#if></#if></a>&nbsp;</td>
					          <#if (player_index+1)%3==0>
					                    </tr>
					                    <tr align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				             </#if>
				      </#list>
				       </tr>
			      </table>
			  </td>
			</tr>
		</#list>
	  <#else>
		<tr>
		  <td align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
</table>
 <div class="kong5"></div>
    <div class="kong10"></div>
  </div>
</div>
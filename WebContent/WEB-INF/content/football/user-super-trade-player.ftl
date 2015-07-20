<head>
	<title>抢购</title>
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
	      .btn_send{background:url(${base}/pages/images/btn_common.jpg) no-repeat;border:0;color:#FFF;width:88px;height:30px;line-height:30px;cursor:pointer}
	</style>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'superTradePlayer'/>
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
				      <#list playerMap.get(key) as player>
				          <tr align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				            <td align="left" width="30%"><#if player??&&player.name??><a href="#" onclick = "oprMethod(420,560,'/football/user!playerInfo.action?id=${player.id!}','球员明细');return false;"><span class="team">${player.name!}</span></#if></a>&nbsp;</td>
				            <td align="left" width="20%">定价：${player.price!}-现价：${player.now_price!}</td>
				            <td align="left" width="40%">
				            
				            
				                       <#assign tradeList=player.getTradeList()!'' />
				                       <#if tradeList??&&tradeList?has_content>
				                           <#list tradeList as trade>
				                                   <#assign tradeTeamId=trade.teamId!'' />
				                          			<b><#if trade.teamId??&&tradeTeamId??&&player.teamId == tradeTeamId><font color="red">${trade.teamName!}拒绝报价</font><#else><font color="red">${trade.teamName!}出价${trade.prize!}已确认&nbsp;&nbsp;&nbsp;审核中</font></#if>${trade.oprDate?string('yyyy-MM-dd HH:mm')}</b></br>
				                           </#list>
				                           <#assign qianGouList=player.getQianGouList()!'' />
					                       <#if qianGouList??>
					                           <#list qianGouList as qianGou>
					                          			${qianGou.teamName!}出价<b><#if qianGou.teamId??&&tradeTeamId??&&qianGou.teamId == tradeTeamId&&qianGou.isFunc??&&qianGou.isFunc><font color="green">${qianGou.prize!}&nbsp;&nbsp;&nbsp;有效</font><#else><font color="gray">${qianGou.prize!}&nbsp;&nbsp;&nbsp;无效</font></#if>${qianGou.oprDate?string('yyyy-MM-dd HH:mm')}</b></br>
					                           </#list>
					                       </#if>
					                           <#else>
					                           <#assign qianGouList=player.getQianGouList()!'' />
						                       <#if qianGouList??>
						                           <#list qianGouList as qianGou>
						                          			${qianGou.teamName!}出价<b><#if qianGou.isFunc??&&qianGou.isFunc><font color="green">${qianGou.prize!}&nbsp;&nbsp;&nbsp;有效</font><#else><font color="gray">${qianGou.prize!}&nbsp;&nbsp;&nbsp;无效</font></#if>${qianGou.oprDate?string('yyyy-MM-dd HH:mm')}</b></br>
						                           </#list>
						                       </#if>
				                       </#if>
				                       
				                       
				                       &nbsp;
				            </td>
				            <td align="left" width="10%">
				                  <#if tradeList??&&tradeList?has_content>
				                   <#else>
				                   <#if player??&&player.teamId??&&login_team_id??&&login_team_id==player.teamId>
				                      <#if qianGouList??&&qianGouList?has_content>
				                         <#if qianggou_queren??&&qianggou_queren>
					                      <a href="#" onclick = "oprMethod(362,371,'/football/user!superTradePlayerCon.action?id=${player.id!}&type=0','接受');return false;"><input id="submit" type="submit" class="btn_send" value="接受" /></a>
					                      </br></br>
					                      <a href="#" onclick = "oprMethod(362,371,'/football/user!superTradePlayerCon.action?id=${player.id!}&type=1','拒绝');return false;"><input id="submit" type="submit" class="btn_send" value="拒绝" /></a>
				                         </#if>
				                      </#if>
				                      <#else>
				                       <#if qianggou_con??&&qianggou_con>
					                      <a href="#" onclick = "oprMethod(362,171,'/football/user!oprSuperTradePlayer.action?id=${player.id!}','我要出价');return false;">
					                          
					                          <input id="submit" type="submit" class="btn_send" value="我要出价" />
					                      
					                      </a>
				                       </#if>
				                   </#if>
				               </#if>
				            </td>
				         </tr>
				      </#list>
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
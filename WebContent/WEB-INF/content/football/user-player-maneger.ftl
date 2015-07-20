<head>
	<title>球员管理</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="playerManeger" />
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
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'playerManeger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	<form action="<@c.url value="/football/user!playerManeger.action" />" method="get" id="query_form">
	    	   <input type=text id="shirtName" name="shirtName" class="header_searchinput" style="width:40px"/>
	    	   <a href="#" onclick="document.getElementById('query_form').submit();return false;"> <img src="<@c.url value='/pages/images/center_btcx.gif' />" align="absmiddle"/></a>
	    	     身体:<select name="balance" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if balance??&& balance==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			  速度:<select name="speed" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if speed??&& speed==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     带精:<select name="dribleAccuracy" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if dribleAccuracy??&& dribleAccuracy==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     带速:<select name="dribleSpeed" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if dribleSpeed??&& dribleSpeed==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     短精:<select name="shortPassAccuracy" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if shortPassAccuracy??&& shortPassAccuracy==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     长精:<select name="longPassAccuracy" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if longPassAccuracy??&& longPassAccuracy==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     射精:<select name="shotAccuracy" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if shotAccuracy??&& shotAccuracy==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     控球:<select name="ball_control" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if ball_control??&& ball_control==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			     团队:<select name="teamwork" onchange="this.form.submit();">
					<option value="">无</option><#list 75..99 as a>
						  <option value="${a}" <#if teamwork??&& teamwork==a>selected</#if> >${a}</option>
					</#list>
			     </select>
			      状态:<select name="form" onchange="this.form.submit();">
					<#list 0..8 as a>
						  <option value="${a}" <#if form??&& form==a>selected</#if> >${a}</option>
					</#list>
			     </select>
		   </form>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>名字</td>
	        <td>国家</td>
	        <td>队伍</td>
	        <td>位置</td>
	        <td>定价</td>
	        <td>现价</td>
	        <td>操作 </td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		  <td height="32">${data_index+1}</td>
	      <td><a href="#" onclick = "oprMethod(420,560,'/football/user!playerInfo.action?id=${data.id!}','球员明细');return false;">${data.name!}</a></td>
	      <td>${data.country!}</td>
	      <td>${data.teams!}</td>
	      <td>${data.reg_position!}</td>
	      <td>${data.price!}</td>
	      <td>${data.now_price!}</td>
	      <td><a href="#" onclick = "oprMethod(365,170,'/football/user!oprPlayer.action?id=${data.id!}','调整');return false;">调整</a></td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="7" align="center" >暂无记录.</td>
		</tr>
	  </#if>
	</tbody>
</table>
 <div class="kong5"></div>
	        <!-- 翻页开始 -->
		            <#import "../../macro/pagination.ftl" as b />
			        <@b.page />
	  
	  
	  
	  
    <div class="kong10"></div>
  </div>
</div>
<head>
	<title>球队管理</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="teamManeger" />
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
      <#assign left_menu = 'teamManeger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	<span style="float:left">球队管理</span>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>队名</td>
		    <td>联赛</td>
	        <td>所对应用户名</td>
	        <td>队徽</td>
	        <td>操作 </td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		  <td height="32">${data_index+1}</td>
	      <td>${data.name!}</td>
	      <td>${data.sclassName!}</td>
	      <td>${data.userName!}</td>
	      <td>${data.picUrl!}</td>
	      <td><a href="#" onclick = "oprMethod(365,170,'/football/user!oprTeam.action?teamId=${data.id!}','调整级别');return false;">调整级别</a>|<a href="#" onclick = "oprMethod(400,540,'/football/user!teamPlayer.action?teamId=${data.id!}','查看人员');return false;">查看人员</a></td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="6" align="center" >暂无记录.</td>
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
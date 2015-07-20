<head>
	<title>赛事管理</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="sclassManeger" />
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
	   function createSchedule(id){
	      $.ajax({
                type: "get",
                url : "<@c.url value= "/football/user!createSchedule.action"/>?id="+id,
                dataType:'json',
                async:false,
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
	   }
	   function createPlayer(){
	      $.ajax({
                type: "get",
                url : "<@c.url value= "/football/user!createPlayer.action"/>",
                dataType:'json',
                async:false,
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
	   }
	</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'sclassManeger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	<span style="float:left"><a href="#" onclick = "oprMethod(365,170,'/football/user!sclassCreate.action','新建联赛');return false;">新建联赛</a>------------
	    	</span>
	    	<form action="<@c.url value= "/football/user!createPlayer.action"/>" method="POST" enctype="multipart/form-data">  
		                           选择文件：<input type="file" name="player_data" size="50"/>
		       <input type="submit" value="导入数据（慎用） "/>          
		    </form>  
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>ID</td>
	        <td>名称</td>
	        <td>当前赛季</td>
	        <td>当前显示轮次</td>
	        <td>操作 </td>
		</tr>
	</thead>
	<tbody>
     <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		  <td height="32">${data_index+1}</td>
	      <td>${data.id}</td>
	      <td>${data.name_J!}</td>
	      <td>${data.curr_matchSeason!}</td>
	      <td>${data.curr_round!}</td>
	      <td>
	          <a href="#" onclick = "oprMethod(365,170,'/football/user!sclassCreate.action?id=${data.id}','修改');return false;">修改</a>|
	          <a href="#" onclick = "createSchedule(${data.id});return false;">生成${data.curr_matchSeason!}赛程</a>
	      </td>
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
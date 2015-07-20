<head>
	<title>交易</title>
	<meta name="decorator" content="football" />
	<meta name="menu" content="userTradeManeger" />
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
	   function oprMethodAjax(url){
	      $.ajax({
                type: "get",
                url : url,
                dataType:'json',
                async:false,
                success:function(json){
					if(json.success == true){
						try{
						   var msg = json.msg;
						   window.alert(msg);
						   parent.location.reload();
						}catch( ee ){}
					}else{
						  var msg = json.msg;
						window.alert(msg);
					}
                },
                error:function(){
                    window.alert('网络错误，请重试');
                }
            });
	   }
	</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'userTradeManeger'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
	    <div class="crighttop">
	    	  <a href="#" onclick = "oprMethod(362,211,'/football/user!tradePlayer.action','我要交易');return false;">我要交易</a>
	   </div>
    <div class="kong10"></div>
	  
	  
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
	<thead>
		<tr align="center">
		    <td width="20" height="20">序 </td>
		    <td>ID</td>
	        <td>名字</td>
	        <td>所属球队</td>
	        <td>定价</td>
	        <td>现价</td>
	        <td>交易情况</td>
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
	       <td>${data.name!}</td>
	      <td>
	       			    <#list teamList as team>
				             <#if data.teamId??&&team.id == data.teamId>
				                ${team.name}
				             </#if>
				        </#list>
	      </td>
	     
	      <td>${data.price!}</td>
	      <td>${data.now_price!}</td>
	      <td> 
	             <#assign trade=data.getPerTradeObj()!'' />
				     <#if trade??>
				         ${trade.display!}
					 </#if>     
          </td>
	      <td>
	        <#if user_team_id??&&user_team_id==data.teamId>
	         <!-- <a href="#" onclick = "oprMethodAjax('<@c.url value= '/football/user!confirmTrade.action?type=1&type_trade=0&id=${data.id!}'/>');return false;">确定</a>
	           |  <a href="#" onclick = "oprMethodAjax('<@c.url value= '/football/user!confirmTrade.action?type=0&type_trade=0&id=${data.id!}'/>');return false;">取消</a>
	          -->
	        </#if>
	        <a href="#" onclick = "oprMethodAjax('<@c.url value= '/football/user!confirmTrade.action?type=0&type_trade=0&id=${data.id!}'/>');return false;">取消</a>
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
<head>
	<title>我的追号</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="chase" />
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
	<script type="text/javascript">
		function stopConfirm(id){
		  var stopUrl="<@c.url value="/lottery/chase!stop.action?id=" />"+id;
		  if (confirm("您确定停止该追号吗？")) {
			  $.ajax({ 
					type: 'GET',
					cache : false,
					url: stopUrl,
					success : function(data, textStatus) {
								var jsonObj = toJsonObject(data);
								alert(jsonObj.msg);
								window.location.reload();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					        
					},
					complete : function(XMLHttpRequest, textStatus) {
				    }
		    	});
		   }
		}
	</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'chase'/>
       <#include "../user/left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
     <form action="<@c.url value='/lottery/auto!list.action' />" method="get">
	    <div class="crighttop">
	        <strong>追号记录</strong><#include "/common/message.ftl" />
	   </div>
	 </form>
    <div class="kong10"></div>
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="28" height="28">序 </td>
        <td>彩种</td>
		<td>状态</td>
		<td>追号总金额</td>
		<td>已追金额</td>
		<td>追号总期数</td>
		<td>已追期数</td>
		<td>中奖停追</td>
		<td>开号停追[高频]</td>
		<td>创建时间</td>
		<td>操作</td>
		<td>查看</td>
      </tr>
      <#if pagination.result?? && pagination.result?size gt 0>
			<#list pagination.result as data>
			<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
			<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
			  <td height="32">${data_index+1}</td>
				<td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
				<td>${data.state.stateName!}</td>
				<#assign frequent=data??&&data.lotteryType??&&data.lotteryType.category=='FREQUENT'!false>
				<td>
				    <#if frequent>
						<#if data.zoomTotalCost??>#{data.zoomTotalCost;M2}</#if>
                    <#else>
                    	${data.totalCost}
                    </#if>
				</td>
				<td>
				    <#if frequent>
						<#if data.zoomChasedCost??>#{data.zoomChasedCost;M2}</#if>
                    <#else>
                    	${data.chasedCost}
                    </#if>
                </td>
				<td>${data.chaseTotalSize!}</td>
				<td>${data.chasedSize!}</td>
				<td><#if data.wonStop>是<#else>否</#if></td>
				<td><#if data.outNumStop??> <#if data.outNumStop>是<#else>否</#if></#if></td>
				
				<td><#if data.createTime??>${data.createTime?string("yy-MM-dd HH:mm")}</#if></td>
				<td>
					<#if data.canStop>
						<a href="javascript:void(0);" onclick="stopConfirm(${data.id!});return false;">停止</a>
					</#if>
				</td>
				<td><a href="<@c.url value="/${data.lotteryType.key!}/chase!show.action?id=${data.id!}" />" />追号详情</a></td>
			</tr>
			</#list>
		<#else>
			<tr>
				<td height="32" align="center" colspan="9" >暂无记录.</td>
			</tr>
		</#if>
		</table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
    <div class="kong10"></div>
  </div>
</div>




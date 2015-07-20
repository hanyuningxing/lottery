<head>
	<title>江西体彩11选5，11选5网上购买投注- ${webapp.webName}安全购彩平台</title>
	<meta name="Keywords" content="购买江西11选5,江西体彩多乐彩网上投注,多乐彩11选5开奖，多乐彩11选5预测，江西11选5技巧，江西11选5分析推荐" />
	<meta name="Description" content="多乐彩11选5是江西体彩玩法，每12分钟开一次奖，全天销售65期，销售时间为每天9：07-21：55，${webapp.webName}为彩民提供江西体彩11选5购买投注推荐，以及网上购买投注服务和11选5开奖查询，技巧等。" />
	<meta name="decorator" content="trade" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
</head>
<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
    <#include "../common/keno-head.ftl" />
            <div class="k3px" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
					<thead>
						<tr align="center">
						      <td width="20" height="20">序 </td>
					          <td>期号</td>
					          <td>方案号</td>
					          <td>玩法类型</td>
					          <td>方案内容</td>
					          <td>方案金额</td>
					          <td>是否中奖</td>
					          <td>中奖金额</td>
					          <td>发起时间</td>
					          <td>查看</td>
						</tr>
					</thead>
					<tbody>
					  <#if pagination??&& (pagination.result![])?size gt 0>
						<#list pagination.result as data>
							<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
							<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
							  <td height="32">${data_index+1}</td>
					          <td>${data.periodNumber!}</td>
					          <td><a href="<@c.url value="/${lotteryType.key}/scheme!show.action?id=${data.id!}" />">${data.schemeNumber!}</a></td>
					          <td><#if data.betType??>${data.betType.typeName!}</#if></td>
					          <td>
					              <#if data.mode=='COMPOUND'>
					                <#if data.compoundContentText??>${data.compoundContentText!}</#if>
									<#elseif data.mode??&&data.mode=='SINGLE'>
									<a href="<@c.url value="/${lotteryType.key}/scheme!show.action?id=${data.id!}" />">单式点击查看</a>
								  </#if>
							  </td>
					        <td><#if data.zoomSchemeCost??>#{data.zoomSchemeCost;M2}</#if></td>
					          <td><#if data.won??&&data.won><font color="red">是</font></#if></td>
					          <td><#if data.won??&&data.won>${data.prize!}</#if></td>
					          <td><#if data.createTime??>${data.createTime!?string("yyyy-MM-dd HH:mm")}</#if></td>
					          <td><a href="<@c.url value="/${lotteryType.key}/scheme!show.action?id=${data.id!}" />">查看</a></td>
					        </tr>
				        </#list>
				      <#else>
				      <tr>
				          <td colspan="10">暂无记录</td>
				       </tr>
				      </#if>
					</tbody>
				</table>
						  <!-- 搜索结束 -->
	      
	          <div class="kong5"></div>
	        <!-- 翻页开始 -->
		            <#import "../../macro/pagination.ftl" as b />
			        <@b.page />
	      </div>
  </div>
  <!-- left结束 -->
<!--右边 -->
      <#include 'rightContent.ftl' />
</div>
<input type="hidden" name="createForm.periodId" id="createFormPeriodId" value="" /><!--无意义-->
 <script type="text/javascript">
  countDown({
    chase_url:'<@c.url value= "/${lotteryType.key}/scheme!canChaseIssue.action"/>',
    burl:'<@c.url value= "/${lotteryType.key}/scheme!asyncTime.action"/>',
    url:'<@c.url value= "/keno/${lotteryType.key}/time.js"/>',
    turl:'<@c.url value= "/serviceTime.jsp"/>',
    iid:'keno_issueNumber',
    tid:'keno_count_down',
    lid:'keno_last_issueNumber',
    lrid:'keno_last_result',
    lrt:'keno_last_result_time'
  });
  loadRightContent('${lotteryType.key}');
   </script>

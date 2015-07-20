<head>
	<title>福彩群英会，山东群英会、深圳群英会网上购买投注 - ${webapp.webName}安全购彩平台</title>
	<meta name="description" content="“${webapp.webName}山东群英会网上投注频道：为广大彩民提供福彩群英会的预测分析，山东群英会的购彩技巧和最新的群英会开奖公告及群英会走势图，也提供了深圳群英会网上投注，是可以信赖的群英会网上投注安全购彩平台。" />
	<meta name="decorator" content="trade" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
</head>
<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
    <#include "../common/keno-head.ftl" />
            <div class="k3px" >
               <div class="hemaint">
				         <#include "../common/keno-result-list-query-menu.ftl"/>
						 <table width="98%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
							  <thead>
							            <tr>
								          <td>期号</td>
								          <td>开奖号码</td>
								          <td>开奖时间</td>
								        </tr>
							  </thead>
							  <tbody>
							  <#if pagination.result?? && pagination.result?size gt 0>
								<#list pagination.result as data>
							          <tr class="row${(data_index+1)%2}"  onmouseout="this.className=''" onmouseover="this.className='trhover'">
							          <td>${data.periodNumber!}</td>
							          <td>
							               <#if data.results??>
							                <#assign resultArr = data.resultFormat?split(",")>
							                <ul class="kjball">
								                 <li class="redballsingle">${resultArr[0]!}</li> 
								                 <li class="redballsingle">${resultArr[1]!}</li> 
								                 <li class="redballsingle">${resultArr[2]!}</li> 
								                 <li class="redballsingle">${resultArr[3]!}</li> 
								                 <li class="redballsingle">${resultArr[4]!}</li> 
							               </#if>
							          </td> 
							          <td><#if data.prizeTime??>${data.prizeTime?string("yyyy-MM-dd HH:mm")}</#if></td> 
							      </#list>  	
							    <#else>
									<tr class="row1">
										<td colspan="10">暂无记录.</td>
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

<head>
	<title>时时彩 - ${webapp.webName}</title>
	<meta name="Keywords" content="彩票投注,时时彩购买,重庆时时彩,时时彩，时时彩预测，时时彩技巧，时时彩分析推荐" />
	<meta name="Description" content="${webapp.webName}提供重庆时时彩、时时彩开奖、走势图、预测分析以及网上购买服务，重庆时时彩是一种在线即开型彩票玩法，属于基诺型彩票，由重庆市福利彩票发行管理中心负责承销，安全诚信请放心购买。" />
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
								          <td>开奖号码(万千百十个)</td>
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

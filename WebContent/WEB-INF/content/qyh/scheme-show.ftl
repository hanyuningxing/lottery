
<head>
 <title>福彩群英会，山东群英会、深圳群英会网上购买投注 - ${webapp.webName}安全购彩平台</title>
<meta name="description" content="“${webapp.webName}山东群英会网上投注频道：为广大彩民提供福彩群英会的预测分析，山东群英会的购彩技巧和最新的群英会开奖公告及群英会走势图，也提供了深圳群英会网上投注，是可以信赖的群英会网上投注安全购彩平台。" />
  <meta name="decorator" content="trade" />
  <meta name="category" content="${lotteryType.category}" />
  <meta name="lottery" content="${lotteryType}" />
  <script type="text/javascript" src="<@c.url value="/js/timer.js" />"></script>
  <script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
  <script type="text/javascript" src="<@c.url value="/js/lottery/scheme-show.js" />"></script>
  <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
</head>
<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
    <#assign menuType=3>
    <#include "../common/keno-head.ftl" />
      <#assign canViewDetail=canViewDetail!false />
      <#assign canSubscribe=canSubscribe!false />
      <div class="k3px" >
      <div class="bggray32" style="line-height:32px;">
                               此方案发起时间：<#if scheme??&&scheme.createTime??>${scheme.createTime?string("MM-dd HH:mm:ss")}</#if> 　
                               认购截止时间：<#if period??&&period.endedTime??>${period.endedTime?string("MM-dd HH:mm:ss")}</#if>  　
                               方案编号：<span class="bluelightchar">${scheme.schemeNumber}</span>
            </div>
      <!-- 提示时间结束 -->
      <div class="hemaint">
             <#include '../common/schemeShowCommon.ftl' />
      <!-- 返回-->
      <div class="all20px" align="center"><a href="<@c.url value="/${lotteryType.key}/scheme!list.action?salesMode=${scheme.mode!}&menuType=2"/>"><img src="<@c.url value="/pages/images/back.gif" />" /></a></div>
</div>
    </div>
  </div>
  <!-- left结束 -->
  
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
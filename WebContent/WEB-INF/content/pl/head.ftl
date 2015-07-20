<div class="typelinks">
<#assign currentTab=currentTab!'' />
<#if playType==0>
    <div class="img_logo"><img src="<@c.url value="/styles/lottery/images/pailie3_logo.gif"/>" width="150" height="34" /></div>
   <#elseif playType==1><div class="img_logo"><img src="<@c.url value="/styles/lottery/images/pailie5_logo.gif"/>" width="150" height="34" /></div>
</#if>

<div class="tabs_double_ball_link">
  <ul style="margin:auto;">
      <li><#if currentTab == 'singleInput'><a class="s_tabs" href="<@c.url value="/pl/scheme!list?salesMode=SINGLE&playType=${playType!}"/>">单式投注</a><#else><a href="<@c.url value="/pl/scheme!list?salesMode=SINGLE&playType=${playType!}"/>">单式投注</a></#if></li>

      <li><#if currentTab == 'compoundInput'><a class="s_tabs" href="<@c.url value="/pl/scheme!list?salesMode=COMPOUND&playType=${playType!}"/>">复式投注</a><#else><a href="<@c.url value="/pl/scheme!list?salesMode=COMPOUND&playType=${playType!}"/>">复式投注</a></#if></li>

      <li><#if currentTab == 'drawinfo'>
        
      <#if playType==0>
      		<a class="s_tabs" href="${base}/${lotteryType.key}/result!pl3.action">开奖号码</a>
   			<#elseif playType==1>
   			<a class="s_tabs" href="${base}/${lotteryType.key}/result!pl5.action">开奖号码</a>
	  </#if>
      <#else>
        <#if playType==0>
      		<a href="${base}/${lotteryType.key}/result!pl3.action">开奖号码
   			<#elseif playType==1>
   			<a href="${base}/${lotteryType.key}/result!pl5.action">开奖号码
	  </#if></#if></li>

      <li><#if currentTab == 'tendencyChart '><a class="s_tabs" href="http://number.bctime.cc/p3/zh.html" target="_blank">数据图表</a><#else><a href="http://number.bctime.cc/p3/zh.html" target="_blank">数据图表</a></#if></li>

      <li><#if currentTab == 'protocol'><a class="s_tabs" href="<@c.url value="/pl/scheme!protocol?playType=${playType!}"/>">合买协议</a><#else><a href="<@c.url value="/pl/scheme!protocol?playType=${playType!}"/>">合买协议</a></#if></li>

      <li><#if currentTab == 'introduction'><a class="s_tabs" href="<@c.url value="/pl/scheme!introduction?playType=${playType!}"/>">玩法介绍</a><#else><a href="<@c.url value="/pl/scheme!introduction?playType=${playType!}"/>">玩法介绍</a></#if></li>
  </ul>
</div>
</div>

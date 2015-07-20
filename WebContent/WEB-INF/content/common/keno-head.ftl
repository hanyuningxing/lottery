<!-- 根据彩种加载相应标题 -->
<#if lotteryType??>
  	<#assign lotteryTitle=lotteryType.lotteryName>
    <#if lotteryType=='EL11TO5'>  
		<#assign topbgClass='topbg115'>
	<#elseif lotteryType=='SDEL11TO5'>
		<#assign topbgClass='topbgsd115'>
	<#elseif lotteryType=='QYH'>
		<#assign topbgClass='topbgqyh'>
	<#elseif lotteryType=='SSC'>
			<#assign topbgClass='topbgssc'>
	<#elseif lotteryType=='GDEL11TO5'>
			<#assign topbgClass='topbggd115'>
	<#elseif lotteryType=='KLSF'>
			<#assign topbgClass='topbggdklsf'>
	</#if>
</#if>


<!-- 针对有不同玩法彩种的判断,根据玩法加载相应标题(如：14场同九场) -->
<#if !playType?? && scheme??&&scheme.playType??>
  <#assign playType = scheme.playType />
</#if>
<#assign playTypeParameter = '' />
<#if playType??>
	<#assign playTypeStr = playType?string />
	<#assign playTypeParameter = '&playType=${playType}' />
</#if>
      <div id="${topbgClass!}">
          <div class="t115_left01">
	        <div class="font14char boldchar textcenter">当前期：<span class="redc" id="keno_issueNumber"><#if issueData??>${issueData.issueNumber!}</#if></span>期 </div>
	        <div class="t115_wz01">距离本期购买截止还有 </div>
	        <div class="t115_wz02" id="keno_count_down"></div>
	      </div>
	      <div class="t115_left02">
	          <div class="textcenter lineh20">
	               ${lotteryTitle} （返奖率高达59%）<br /><span class="redc">最新开奖：第<span id="keno_last_issueNumber"></span>期</span>
	          </div>
			  <div class="t115ballwz">
			          <ul class="t115ball" id="keno_last_result">
			          </ul>
			  </div>
        	  <div class=" cleanboth textcenter">开奖时间：<span id="keno_last_result_time"></span></div>
          </div>
       <div class="t115czj" id="keno_head_new_won"> </div>
      </div> 
      <!-- navmenu begin -->
      <div class="navweizhi">
	       <div class="floatleft" style="width:510px;">
	         <ul class="tytopnav">
	          <li><a href="<@c.url value="/${lotteryType.key}/scheme!index.action?menuType=0${playTypeParameter}"/>" <#if menuType==0>class="now"</#if>>发起代购</a></li>
	          <li><a href="javascript:void(0);" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/lottery/chase!list.action?menuType=1"/>';});return false;" <#if menuType==1>class="now"</#if> rel="nofollow">我的追号</a></li>
	          <li><a href="<@c.url value="/${lotteryType.key}/scheme!note.action?menuType=2${playTypeParameter}"/>" <#if menuType==2>class="now"</#if> rel="nofollow">开奖号码</a></li>
		      <li><a href="javascript:void(0);" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/${lotteryType.key}/scheme!list.action?menuType=3${playTypeParameter}"/>';});return false;" <#if menuType==3>class="now"</#if> rel="nofollow">我的投注</a></li>
		      <li><a href="<@c.url value="/info/rule/rule.action?lottery=${lotteryType}"/>" target="_blank" <#if menuType==4>class="now"</#if> rel="nofollow">玩法规则</a></li>
		     </ul>
	       </div>  
	       <!-- 公告标签 -->
           <#if noticeNewsMap??&&noticeNewsMap[lotteryType]??>
       	   <div class="kcwzgg">
		   <div class="kcwzgg_img"></div>
		   <div class="kcwzgg_zi">
					${noticeNewsMap[lotteryType]!}
			</div>
	       </div>
	       </#if> 
       </div>
      <!-- navmenu end -->

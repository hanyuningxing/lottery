<@override name="style">
	<link href="<@c.url value="/V1/css/jczq.css"/>" rel="stylesheet" type="text/css" />
	<@block name="editNewStyle"></@block>
</@override>

<@override name="top">
	<div id="jczqtop02" class="jztop1">
	    <div class="tztopnews" >
	      <ul class="tztopnew">
	        <#if gonggaoList?? && gonggaoList?size gt 0>
			<#list gonggaoList as data>
				<li><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
			</#list>
		  </#if>
	      </ul>
	    </div>
	    <div class="tztopbt"> <a href="${base}/info/rule/JCZQ.html" target="_blank" class="tztopbt1">玩法介绍</a><br />
	      <a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45" class="tztopbt1">申请票样</a> </div>
	    <div class="tzday1"><b>周一至周五</b><br />
	      09:00～00:00</div>
	    <div class="tzday2"><b>周六/日</b><br />
	      09:00～01:00</div>
	    <div class="tzday3"></div>
    </div>
</@override>

<@override name="menu">
	<#if !playType??><#assign playType='SPF'></#if>	
	<#assign SPF_RQSPF_show=passMode?exists && passMode=='PASS' && (playType=='SPF' || playType=='RQSPF') && salesMode?exists && salesMode=='COMPOUND'>
	<#assign PASS_show=passMode?exists && passMode=='PASS' && playType!='SPF' && playType!='RQSPF' && salesMode?exists && salesMode=='COMPOUND'>
	<#assign MIX_PASS_show=passMode?exists && passMode=='MIX_PASS'>
	<#assign SINGLE_PASS_show=passMode?exists && passMode=='SINGLE'>
	<#assign SINGLE_UPLOAD_show=salesMode?exists && salesMode=='SINGLE'>
	<div class="menubg">
  	  <div class="menu">
	  	  <ul>
		  	  <li class="<#if !playTypeWeb?? && SPF_RQSPF_show>menuli03<#else>menuli04</#if>"><div class="menuhot"><a href="${base}/jczq/PASS.html">胜平负/让球胜平负</a></div></li>
		  	  <li class="menuli02"></li>			  			  
			  <li class="<#if playTypeWeb?? && playTypeWeb=='DGP'>menuli03<#else>menuli04</#if>"><div class="menunew2"><a href="${base}/jczq/new/DGP.html">半球盘单关配</a></div></li>	
			  <li class="menuli02"></li>			  			  
			  <li class="<#if playTypeWeb?? && playTypeWeb=='EXY'>menuli03<#else>menuli04</#if>"><div class="menunew"><a href="${base}/jczq/new/EXY.html">盘口(生死盘)</a></div></li>	
			  <li class="menuli02"></li>		  
		  	  <li class="<#if MIX_PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jczq/MIX_PASS.html">混合过关</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jczq/JQS-PASS.html">过关投注</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if SINGLE_PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jczq/SINGLE.html">单关投注</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if SINGLE_UPLOAD_show>menuli03<#else>menuli01</#if>"><a href="${base}/jczq/SPF-SINGLE-SELF.html">单式上传</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if !SPF_RQSPF_show && !PASS_show && !MIX_PASS_show && !SINGLE_PASS_show && !SINGLE_UPLOAD_show>menuli03<#else>menuli01</#if>"><a href="${base}/jczq/scheme_review.html">赛事开奖</a></li>
		  </ul>
    </div>
    <#if PASS_show>
	    <div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='JQS'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/JQS-PASS.html">进球数</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BF-PASS.html">比分</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BQQ'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BQQ-PASS.html">半全场</a></li>
				</ul>
			</div>
		</div>
	<#elseif SINGLE_PASS_show>
		<div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='SPF' || playType=='RQSPF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/SPF-SINGLE.html">胜平负/让球胜平负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='JQS'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/JQS-SINGLE.html">进球数</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BF-SINGLE.html">比分</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BQQ'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BQQ-SINGLE.html">半全场</a></li>
				</ul>
			</div>
		</div>
	<#elseif SINGLE_UPLOAD_show>
		<div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='SPF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/SPF-SINGLE-SELF.html">胜平负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='RQSPF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/RQSPF-SINGLE-SELF.html">让球胜平负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='JQS'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/JQS-SINGLE-SELF.html">进球数</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BF-SINGLE-SELF.html">比分</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='BQQ'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jczq/BQQ-SINGLE-SELF.html">半全场</a></li>
				</ul>
			</div>
		</div>
	</#if>
  </div>
</@override>

<@extends name="/WEB-INF/content/common/simple-baseV1.ftl"/> 

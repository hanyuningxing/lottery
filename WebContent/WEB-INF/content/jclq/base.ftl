<@override name="style">
	<link href="<@c.url value="/V1/css/jczq.css"/>" rel="stylesheet" type="text/css" />
	<@block name="editNewStyle"></@block>
</@override>

<@override name="top">
	<div id="jclqtop02" class="jztop2">
	    <div class="tztopnews">
	      <ul class="tztopnew">
	          <#if gonggaoList?? && gonggaoList?size gt 0>
			<#list gonggaoList as data>
				<li><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
			</#list>
		  </#if>
	      </ul>
	    </div>
	    <div class="tztopbt"> <a href="${base}/info/rule/JCLQ.html" target="_blank" class="tztopbt1">玩法介绍</a><br />
	      <a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45" class="tztopbt1">申请票样</a> </div>
	    <div class="tzday1"><b>周一至周五</b><br />
	      09:00～00:00</div>
	    <div class="tzday2"><b>周六/日</b><br />
	      09:00～01:00</div>
	    <div class="tzday3"></div>
    </div>
</@override>

<@override name="menu">
	<#if !playType??><#assign playType='SF'></#if>
	<#assign PASS_show=passMode?exists && passMode=='PASS' && salesMode?exists && salesMode=='COMPOUND'>
	<#assign MIX_PASS_show=passMode?exists && passMode=='MIX_PASS'>
	<#assign SINGLE_PASS_show=passMode?exists && passMode=='SINGLE'>
	<#assign SINGLE_UPLOAD_show=salesMode?exists && salesMode=='SINGLE'>
	<div class="menubg">
  	  <div class="menu">
	  	  <ul>
		  	  <li class="<#if MIX_PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jclq/MIX_PASS.html">混合过关</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jclq/PASS.html">过关投注</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if SINGLE_PASS_show>menuli03<#else>menuli01</#if>"><a href="${base}/jclq/SF-SINGLE.html">单关投注</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if SINGLE_UPLOAD_show>menuli03<#else>menuli01</#if>"><a href="${base}/jclq/SF-SINGLE-SELF.html">单式上传</a></li>
			  <li class="menuli02"></li>
		  	  <li class="menuli01"><a href="${base}/jclq/scheme_subList.html">合买方案</a></li>
			  <li class="menuli02"></li>
		  	  <li class="<#if !PASS_show && !MIX_PASS_show && !SINGLE_PASS_show && !SINGLE_UPLOAD_show>menuli03<#else>menuli01</#if>"><a href="${base}/jclq/scheme_review.html">赛事开奖</a></li>
		  </ul>
    </div>
    <#if PASS_show>
	    <div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='SF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SF-PASS.html">胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='RFSF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/RFSF-PASS.html">让分胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='SFC'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SFC-PASS.html">胜分差</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='DXF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/DXF-PASS.html">大小分</a></li>
				</ul>
			</div>
		</div>
	<#elseif SINGLE_PASS_show>
		<div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='SF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SF-SINGLE.html">胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='RFSF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/RFSF-SINGLE.html">让分胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='SFC'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SFC-SINGLE.html">胜分差</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='DXF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/DXF-SINGLE.html">大小分</a></li>					
				</ul>
			</div>
		</div>
	<#elseif SINGLE_UPLOAD_show>
		<div class="navmenubg">
			<div class="navmenu">
				<ul>
					<li class="<#if playType=='SF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SF-SINGLE-SELF.html">胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='RFSF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/RFSF-SINGLE-SELF.html">让分胜负</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='SFC'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/SFC-SINGLE-SELF.html">胜分差</a></li>
					<li class="navmenu03"></li>
					<li class="<#if playType=='DXF'>navmenu02<#else>navmenu01</#if>"><a href="${base}/jclq/DXF-SINGLE-SELF.html">大小分</a></li>
				</ul>
			</div>
		</div>
	</#if>
  </div>
</@override>

<@extends name="/WEB-INF/content/common/simple-baseV1.ftl"/> 

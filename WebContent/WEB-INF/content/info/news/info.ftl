<head>
	<title><#if infoBeanForm??&&infoBeanForm.title??>${infoBeanForm.title!}--${webapp.webName}</#if></title>
	<meta name="decorator" content="newsV1" />
	<script src="${base}/js/common.js" type="text/javascript" ></script>
	<link href="<@c.url value="/pages/css/zxck.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/zxlb.css"/>" rel="stylesheet" type="text/css" />
</head>
<!-- 内容开始 -->
<div id="index_center">
  <div id="left_z">
  <div id="zxck_left">
     <div class="zixunwz"><a href="/">${webapp.webName}</a>   
      	 <#if infoBeanForm??&&infoBeanForm.lotteryType??>
      	 		<#assign titleLink='/info/news!index.action?lottery=${infoBeanForm.lotteryType!}'/>
	     </#if>
	     <#if titleLink??>
	     	    > <a href='<@c.url value="${titleLink!}"/>'>${infoBeanForm.lotteryType.lotteryName!}</a>
	     </#if>
	     <#if infoBeanForm??&&infoBeanForm.lotteryType?? && infoBeanForm.type??>
	        <#assign infoSubTypeLink='/info/news!index.action?lottery=${infoBeanForm.lotteryType!}&infoType=${infoBeanForm.type}'/>
	     </#if>
	      <#if infoSubTypeLink??>
	     	     > <a href='<@c.url value="${infoSubTypeLink!}"/>'>${infoBeanForm.type.typeName!}</a>
	     </#if>
	     >
    	  正文</div>
	 <div class="zwck_news">
	 	<div class="xwbt"><#if infoBeanForm??&&infoBeanForm.title??>${infoBeanForm.title!}</#if></div>
		<div class="xwtime">来源：${webapp.webName}彩票网 | 点击：<#if infoBeanForm??&&infoBeanForm.clickNum??>${infoBeanForm.clickNum}</#if> | 责任编辑：<#if infoBeanForm??&&infoBeanForm.author??>${infoBeanForm.author!}</#if>  |  <#if infoBeanForm??&&infoBeanForm.createTime??>${infoBeanForm.createTime?string("MM-dd HH:mm:ss")}</#if>　 </div>
		<div class="xwtime"><#if infoBeanForm??&&infoBeanForm.description??>导语: ${infoBeanForm.description!}</#if></div>
		<div class="xwfgx"></div>
	    <div class="xwnr">
	    	<p style="font-size:13px; color:#949390;">
	    	&nbsp&nbsp&nbsp&nbsp${webapp.webName}安全购彩平台${infoBeanForm.type.typeName!""}频道小编${infoBeanForm.author!""}于${infoBeanForm.createTime?string("MM-dd HH:mm:ss")}为您整理编辑关于“<#if infoBeanForm.lotteryType??>${infoBeanForm.lotteryType.lotteryName!""}</#if>预测之：${infoBeanForm.title!""}”的${infoBeanForm.type.typeName!""}，所发布整理${infoBeanForm.type.typeName!""}均来源于本站编辑或互联网网友分享，与本站立场无关。
	    	</p>
	    	<b>【<#if infoBeanForm.lotteryType??>${infoBeanForm.lotteryType.lotteryName!}</#if>${infoBeanForm.type.typeName!""}：${infoBeanForm.title!""}】内容如下：</b>
		  <#if infoBeanForm??&&infoBeanForm.content??>${infoBeanForm.content!}</#if>
		  
		  <p style="font-size:13px; color:#949390">&nbsp&nbsp&nbsp&nbsp
		  <#if infoBeanForm??&&infoBeanForm.lotteryType??>
		  	<#if infoBeanForm.lotteryType=='DCZC'||infoBeanForm.lotteryType=='SFZC'||infoBeanForm.lotteryType=='SCZC'||infoBeanForm.lotteryType=='LCZC'||infoBeanForm.lotteryType=='LCZC'||infoBeanForm.lotteryType=='JCZQ'||infoBeanForm.lotteryType=='JCLQ'>
		  		[<a href="/dczc/scheme!editNew.action"><b>北京单场</b></a>][<a href="/sfzc/scheme!editNew.action?playType=SFZC9"><b>任选九场</b></a>][<a href="/sfzc/scheme!editNew.action"><b>胜负彩14场</b></a>][<a href="/sczc/scheme!editNew.action"><b>进球彩</b></a>][<a href="/lczc/scheme!editNew.action"><b>六半全场</b></a>][<a href="/jczq/scheme!editNew.action"><b>竞彩足球</b></a>][<a href="/jclq/scheme!editNew.action"><b>竞彩篮球</b></a>]竞彩频道彩种投注通道，
		  	<#elseif infoBeanForm.lotteryType=='SDEL11TO5'||infoBeanForm.lotteryType=='EL11TO5'||infoBeanForm.lotteryType=='GDEL11TO5'||infoBeanForm.lotteryType=='QYH'||infoBeanForm.lotteryType=='SSC'>
		  		[<a href="/sde111to5/scheme.action"><b>山东11选5</b></a>][<a href="/el11to5/scheme.action"><b>江西11选5</b></a>][<a href="/gdel11to5/scheme.action"><b>广东11选5</b></a>][<a href="/qyh/scheme.action"><b>山东群英会</b></a>][<a href="/ssc/scheme.action"><b>重庆时时彩</b></a>] 快彩高频彩频道彩种投注通道， 
		  	<#elseif infoBeanForm.lotteryType=='DLT'||infoBeanForm.lotteryType=='PL'||infoBeanForm.lotteryType=='TC22TO5'>
		  		[<a href="/dlt/scheme!editNew.action"><b>大乐透</b></a>][<a href="/pl-0/scheme!editNew.action"><b>排列三</b></a>][<a href="/pl-1/scheme!editNew.action"><b>排列五</b></a>][<a href="/tc22to5/scheme!editNew.action"><b>22选5</b></a>] 福彩数字彩彩种投注通道， 
		  	<#elseif infoBeanForm.lotteryType=='SSQ'||infoBeanForm.lotteryType=='WELFARE3D'||infoBeanForm.lotteryType=='SEVEN'||infoBeanForm.lotteryType=='WELFARE36To7'>
		  		[<a href="/ssq/scheme!editNew.action"><b>双色球</b></a>][<a href="/welfare3d/scheme!editNew.action"><b>福彩3D</b></a>][<a href="/seven/scheme!editNew.action"><b>七乐彩</b></a>][<a href="/welfare36to7/scheme!editNew.action"><b>好彩1</b></a>] 福彩数字彩彩种投注通道， 
		  	</#if>
		  </#if>
			欢迎彩民朋友进行购买投注，网上安全诚信购彩首选${webapp.webName}。
			以上关于“${infoBeanForm.title!""}”${infoBeanForm.type.typeName!""}为网友彩民热心提供，${webapp.webName}编辑${infoBeanForm.author!""}整理编辑发布，转载请注明出处：${webapp.webName}网(${webapp.domainF})。</p>
		</div>
		<div align="center">
			<#if moreNewsInfoDataList?? && moreNewsInfoDataList?size gt 2>
				<a href="<@c.url value="/info/new.action?id=${id-1!}"/>" target="_blank">上一篇</a>&nbsp;&nbsp;
				<a href="<@c.url value="/info/new.action?id=${id+1!}"/>" target="_blank">下一篇</a>
			</#if>
		</div>
		<div class="xgyd">
				相关阅读:
				<#if map??&&map?size gt 0>
					<#list map?keys as key>
						<a href="/tag/${key}/scheme!editNew.action" target="_blank">${map[key]}</a>&nbsp;&nbsp;	
					</#list>
				</#if>		
		</div>
	    <div class="fxiang">
			<div class="fxzt"><img src="<@c.url value='/pages/images/fxiang_03.gif'/>" /></div><div class="fxzt">分享到：</div>
			<!-- JiaThis Button BEGIN -->
			<div id="ckepop">
				<a href=" http://www.jiathis.com/share/?uid=1503743" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">分享</a>
				<span class="jiathis_separator">|</span>
				<a class="jiathis_button_icons_1"></a>
				<a class="jiathis_button_icons_2"></a>
				<a class="jiathis_button_icons_3"></a>
				<a class="jiathis_button_icons_4"></a>
			</div>
			<script type="text/javascript">var jiathis_config = {"data_track_clickback":true};</script>
			<script type="text/javascript" src=" http://v1.jiathis.com/code/jia.js?uid=1503743" charset="utf-8"></script>
			<!-- JiaThis Button END -->
			<a href="javascript:void(0);"><div class="fxzt1"><img src="<@c.url value='/pages/images/fxiang_19.gif'/>" /></div></a>
		</div>
	 
	    <div class="moreabout">
		   <b>更多关于 <#if infoBeanForm??&&infoBeanForm.keywords??>${infoBeanForm.keywords!}</#if>的新闻</b>
		   <div class="gdxw_lj">
		     <div class="white">
			    <div class="gd_link" style="margin-right:20px;">
					  <ul>
					  <#if moreNewsInfoDataList?? && moreNewsInfoDataList?size gt 0>
						<#list moreNewsInfoDataList as data>
						<#if data_index == 6 >
							 </ul>
							</div>
							 <div class="gd_link" >
					  		<ul>
							<li>
							 <#if data.titleLink??>
							        <#if data.titleLink=='#'>
							        	<a href="<@c.url value="/info/new.action?id=${data.id!}"/>" target="_blank">
							        	<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							        	</a>
							      	<#else>
							      		<a href="${data.titleLink}" target="_blank">
							      		<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							      		</a>
							 	    </#if>
							 <#else><a href="<@c.url value="/info/new.action?id=${data.id!}"/>" target="_blank">
									<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							 </a>
							 </#if>
							</li>
					    <#else>
					    	<li>
					    	 <#if data.titleLink??>
							        <#if data.titleLink=='#'>
							        	<a href="<@c.url value="/info/new.action?id=${data.id!}"/>" target="_blank">
							        	<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							        	</a>
							      	<#else>
							      		<a href="${data.titleLink}" target="_blank">
							      		<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							      		</a>
							 	    </#if>
							 <#else><a href="<@c.url value="/info/new.action?id=${data.id!}"/>" target="_blank">
									<#if data.lotteryType??><span>[${data.lotteryType.lotteryName!}]</span></#if>${data.shortTitle!}
							 </a>
							 </#if>
					    	</li>
						</#if>
						</#list>
					   </#if>
				  </ul>
				</div>
			 </div>
		   </div>
		</div>
	 </div>	
  </div>
  
  <div class="rdlink">
     <div class="rdlink_bt">
	    <img src="<@c.url value='/pages/images/rd_link_01.gif'/>" />
	 </div>
	  <div class="lmdlj">
	  <#if infoBeanForm??&&infoBeanForm.lotteryType??>
	      <!--排列3/5-->
	      <#if infoBeanForm.lotteryType=='PL'>
  	              <a href="<@c.url value="/pl/scheme!editNew.action?playType=0"/>" target="_blank">排列三投注</a>
  	              <a href="<@c.url value="/pl/scheme!subList.action?playType=0"/>" target="_blank">排列三合买</a>
  	              <a href="<@c.url value="/pl3/analyse.action"/>" target="_blank">排列三走势图</a>
  	              <a href="/result-pl-0/scheme!editNew.action" target="_blank">排列三开奖结果</a>
       			  <a href="<@c.url value="/pl/scheme!editNew.action?playType=1"/>" target="_blank">排列五投注</a>
  	              <a href="<@c.url value="/pl/scheme!subList.action?playType=1"/>" target="_blank">排列五合买</a>
  	              <a href="<@c.url value="/pl5/analyse.action"/>" target="_blank">排列五走势图</a>
  	              <a href="/result-pl-1/scheme!editNew.action" target="_blank">排列五开奖结果</a>
	       </#if>
	       <!--高频彩无合买、走势图-->
	       <#if infoBeanForm??&&infoBeanForm.lotteryType=='SDEL11TO5'||infoBeanForm.lotteryType=='EL11TO5'||infoBeanForm.lotteryType=='QYH'||infoBeanForm.lotteryType=='SSC'>
	      		  <a href="/${infoBeanForm.lotteryType.key}/scheme.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}投注</a>
	      		  <a href="/result-${infoBeanForm.lotteryType.key}/scheme!editNew.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}开奖结果</a>
	       </#if>
	       <!--竞技菜无合买、走势图-->
	       <#if infoBeanForm??&&infoBeanForm.lotteryType=='SFZC'||infoBeanForm.lotteryType=='LCZC'||infoBeanForm.lotteryType=='DCZC'||infoBeanForm.lotteryType=='SCZC'>
	       		  <#if infoBeanForm.lotteryType=='SFZC'>
	       		    <a href="/sfzc/scheme!editNew.action" target="_blank">胜负14场投注</a>
	       		 	<a href="/sfzc/scheme!editNew.action?playType=SFZC9" target="_blank">胜负任选9场投注</a>
	       		    <a href="/result-sfzc9/scheme!editNew.action" target="_blank">胜负任选9场开奖结果</a>
	       		    <a href="/result-sfzc14/scheme!editNew.action" target="_blank">胜负14场开奖结果</a>
	       		  <#elseif infoBeanForm.lotteryType=='DCZC'>
	       		  	<a href="<@c.url value="/dczc/scheme!editNew.action?playType=SPF"/>" target="_blank">单场胜负平</a>
	       		  	<a href="<@c.url value="/dczc/scheme!editNew.action?playType=ZJQS"/>" target="_blank">单场进球数</a>
	       		  	<a href="<@c.url value="/dczc/scheme!editNew.action?playType=SXDS"/>" target="_blank">单场上下单双</a>
	       		    <a href="<@c.url value="/dczc/scheme!editNew.action?playType=BF"/>" target="_blank">单场比分</a>
	       		  	<a href="<@c.url value="/dczc/scheme!editNew.action?playType=BQQSPF"/>" target="_blank">单场半全场</a>
	      		  <#else>
	      		  	<a href="/${infoBeanForm.lotteryType.key}/scheme!editNew.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}投注</a>
	      		    <a href="/result-${infoBeanForm.lotteryType.key}/scheme!editNew.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}开奖结果</a>
	      		  </#if>
	       </#if>
	       <!--双色球、大乐透、福彩3D-->
	       <#if infoBeanForm??&&infoBeanForm.lotteryType=='SSQ'||infoBeanForm.lotteryType=='WELFARE3D'||infoBeanForm.lotteryType=='DLT'>
	              <a href="/${infoBeanForm.lotteryType.key}/scheme!editNew.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}投注</a>
  	              <a href="<@c.url value="/${infoBeanForm.lotteryType.key}/scheme!subList.action"/>" target="_blank">${infoBeanForm.lotteryType.lotteryName!}合买</a>
  	              <a href="<@c.url value="/${infoBeanForm.lotteryType.key}/analyse.action"/>" target="_blank">${infoBeanForm.lotteryType.lotteryName!}走势图</a>
  	              <a href="/result-${infoBeanForm.lotteryType.key}/scheme!editNew.action" target="_blank">${infoBeanForm.lotteryType.lotteryName!}开奖结果</a>
	       </#if>
	  </#if>
	    </div>
	 <div class="fhsy">
	    <a href="<@c.url value="/index.html"/>">返回${webapp.webName}网首页</a>
	 </div>
  </div>
</div>  
   <#include "../results/right-new-result.ftl" />
  </div>  			
</div>
<!-- 内容结束 -->





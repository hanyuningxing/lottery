<head>
	<title><#if lottery??>${lottery.lotteryName!}</#if><#if type??>${type.typeName}</#if>频道</title>
	<meta name="decorator" content="newsV1" />
</head>

<div class="w980">
  <div id="list">
    <div class="listleft">
     <div class="listlefttopban"><img src="${base}/pages/images/wz.gif" /> 您的位置：<a href="${base}/info/news!index.action" class="charr">资讯新闻</a> > <a href="${base}/info/news!infoList.action?type=<#if type??>${type}</#if>&lottery=<#if lottery??>${lottery}</#if>" class="charr"><#if lottery??>${lottery.lotteryName!}</#if><#if type??>${type.typeName}</#if>频道</a></div>
      <div class="wzpadding">
       <ul class="list ">
       <ul class="list ">
            <#import "../../../macro/news.ftl" as news_macro />
	        <#if pagination.result?? && pagination.result?size gt 0>
			 <#list pagination.result as data>
			       <@news_macro.news_list news=data />
			      
			       <#if data_index==4|| data_index==9|| data_index==14>
						</ul>
				         <div class="xuline" style="margin:5px 0"></div>
				        <ul class="list ">
			       </#if>
			 </#list>
			</#if>	
	    </ul>
        <div class="linebg1" style="margin:5px 0"></div>
        <div class="kong10"></div>
        <div class=" cleanboth pagelist" align="center">
           <#import "../../../macro/pagination.ftl" as b />
		   <@b.page />
        </div>
      </div>
    </div>
  </div>
  <!--右边-->
  <div class="main_rig">
      <#include '${lottery.key}_right.html' />
  </div>
  </div>
  </div>
</div>













<head>
	<title>彩票开奖公告号码总汇-双色球_大乐透_3D_排列3_群英会 - ${webapp.webName}安全购彩平台</title>
	<meta name="decorator" content="resultV1" />
	<script type="text/javascript" src="<@c.url value="/js/thinkbox/thinkbox.js"/>"></script>
	<link href="<@c.url value= "/pages/css/pdzx.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/analyse.css"/>" rel="stylesheet" type="text/css" />
	<link href="${base}/V1/css/yhzx.css" rel="stylesheet" type="text/css" />
	
</head>
<div class="main">
  <!--顶导航-->
  <!--顶导航结束-->
  <div class="kong10"></div>
  
  <div class="kj_kb1">
    <div class="kj_t001"><h1>开奖公告</h1></div>
    <div style="margin:10px;">
    <div class="zjmxtit">
		  	<ul>
			  <a href="${base}/jczq/scheme!review.action?passMode=PASS">
				<li>竞彩足球</li>
			  </a>
			  <a href="${base}/jclq/scheme!review.action">
				<li>竞彩篮球</li>
			  </a>
			  <a href="${base}/dczc/scheme!review.action">
				<li>北京单场</li>
			  </a>
			   <a href="${base}/results!index.action">
				<li class="zjmxli01">传统足球</li>
			  </a>
			</ul>
    </div>
     </div>
    <div class="kj_datewz">
      <div class="floatleft top5px"><img src="<@c.url value= "/pages/images/kj_date.gif"/>" align="absmiddle" />&nbsp;&nbsp;今天：${dateFormat!}</div>
      <form action="" method="get" target="_blank" id="lottery_select_form">
          <input name="playType" type="hidden" value=""/>
	      <div class="floatrig">开奖查询：
	        <label>
	        <select name="lottery_select" size="1" onchange="drawedIssue(this.options[this.selectedIndex].value);return false;">
	          <option value="-1">选择查询彩种</option>
	          <#if lotteryList?? && lotteryList?size gt 0>
				 <#list lotteryList as data>
				       <#if data=='SFZC'>
				             <option value="${data.key}">胜负彩</option>
				             <option value="${data.key}">任选9场</option>
				        <#elseif data=='LCZC' || data=="SCZC">
				             <option value="${data.key}">${data.lotteryName!}</option>
				       </#if>
				     
				 </#list>
			 </#if>	
	        </select>
	        </label>
	        <label>
	        <select name="id" size="1" id="issue_select">
	          <option value="-1">选择开奖期号</option>
	        </select>
	        </label>
	        <label><a href="javascript:void(0);" onclick="gotoIssue();return false;"><img src="<@c.url value= "/pages/images/kj_btsearch.gif"/>" align="absmiddle"/></a></label>
	      </div>
       </form>
    </div>
    <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="978"  
  border="1" >
      <tr class="center_tablegray" align="center" >
        <td width="80" height="28">彩种名称</td>
        <td width="95"> 开奖期号 <br /></td>
        <td width="100">开奖时间 </td>
        <td width="320">开奖号码</td>
        <td width="120">当期销量 </td>
        <td width="120">滚存奖金 </td>
        <td>开奖日 </td>
        <td width="50">详情</td>
      </tr>
    </table>

    
    
    
    <div class="kj_jtitle"> <span><h2>竞技彩票开奖公告</h2></span> </div>
    <#if zcExists>
      <#include '/html/info/results/zc-result.html' />
    </#if>
    <!--
    <div class="kj_jtitle"> <span><h2>高频彩票开奖公告</h2></span> </div>
    <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="978"  
  border="1" >
    <!--el11to5Exists<#if el11to5Exists>
        <#include '/html/info/results/el11to5-result.html' />
    </#if>
     <!--sdEl11to5Exists<#if sdEl11to5Exists>
        <#include '/html/info/results/sdel11to5-result.html' />
     </#if>
      <!--gdEl11to5Exists<#if gdEl11to5Exists>
        <#include '/html/info/results/gdel11to5-result.html' />
     </#if>-->
     <!--quh5Exists<#if quh5Exists>
        <#include '/html/info/results/qyh-result.html' />
    </#if>-->
    
   <!-- <div class="kj_jtitle"> <span><h2>数字彩票开奖公告</h2></span> </div> -->
   
   
    <!--
    <#if sdEl11to5Exists>
        <#include '/html/info/results/sdel11to5-result.html' />
     </#if>
     <#if sscExists>
        <#include '/html/info/results/ssc-result.html' />
    </#if>
  -->
    </table>
  </div>
 
</div>

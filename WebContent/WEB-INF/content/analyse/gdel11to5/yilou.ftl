<head>
	<title>${lotteryType.lotteryName}遗漏</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/el11to5data.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/el11to5_num.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/el11to5/base.js"/>"></script>
</head>
<div class="main980">
        <#include "top.ftl"/>
        <div class="tybanbg">
		    <div class=" zstopleft0">
		       ${lotteryType.lotteryName}走势图
	 	    </div>
		    <div id="size_div" class=" zstopleft1"> 显示：
		      <input type="button" onclick="window.location.href='<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}" />'" value="全部期" class="<#if !(count??)>btnow<#else>btgray</#if>" id="size_30"> 
			  <input type="button" onclick="window.location.href='<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=100" />'" value="最近100期" class="<#if count??&&count=='100'>btnow<#else>btgray</#if>" id="size_30"> 
			  <input type="button" onclick="window.location.href='<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=200" />'" value="最近200期" class="<#if count??&&count=='200'>btnow<#else>btgray</#if>" id="size_30"> 
			  <input type="button" onclick="window.location.href='<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=500" />'" value="最近500期" class="<#if count??&&count=='500'>btnow<#else>btgray</#if>" id="size_30"> 
			  <input type="button" onclick="window.location.href='<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=1000" />'" value="最近1000期" class="<#if count??&&count=='1000'>btnow<#else>btgray</#if>" id="size_30"> 
		    <span style="color:red;">数据开始于<b>${bp}</b>期，截止于<b>${ep}</b>期</span>
		    </div>
	   </div>
    <table width="980" border="0" cellpadding="0" cellspacing="1" class="zstablegraybg" >
    <tr class="trtitlebg">
      <td width="75" height="25" align="center">类型<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=key" />&desc=<#if order??&&order=='key'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td >出现次数<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=s" />&desc=<#if order??&&order=='s'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td >理论出现次数 </td>
      <td >出现频率<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=sper" />&desc=<#if order??&&order=='sper'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a> </td>
      <td height="22" >平均遗漏 <a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=avg" />&desc=<#if order??&&order=='avg'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td height="22" >最大遗漏<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=mx" />&desc=<#if order??&&order=='mx'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a> </td>
      <td height="22" >上次遗漏 <a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=ls" />&desc=<#if order??&&order=='ls'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td height="22" >本次遗漏<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=cs" />&desc=<#if order??&&order=='cs'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td height="22" >欲出几率<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=wo" />&desc=<#if order??&&order=='wo'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td height="22" >回补几率<a href="<@c.url value="/gdel11to5/analyse!yilou.action?type=${type!}&count=${count!0}&order=huibu" />&desc=<#if order??&&order=='huibu'>${desc*-1}</#if>"><img src="${base}/pages/images/bg2.gif" width="21" height="10" /></a></td>
      <td height="22" >最大遗漏期间 </td>
    </tr>
    <#list beanList as item>
    <tr class="<#if item_index%2==0>trw graychar333<#else>trgray graychar333</#if>">
      <td height="22"  >${item.key!}</td>
      <td  >${item.show}</td>
      <td  >${item.willShow?string('0.00')}</td>
      <td   >${item.showPercent?string('0.00%')}</td>
      <td >${item.avgYilou?string('0.00')}</td>
      <td  >${item.maxYilou}</td>
      <td  >${item.lastYilou}</td>
      <td  >${item.curYilou}</td>
      <td  >${item.willHappen?string('0.00')}</td>
      <td  >${item.huibu?string('0.00')}</td>
      <td >${item.periodAbout}</td>
    </tr>
    </#list>
    
  </table>
  <div class="kong10"></div>
  <div class=" bor1"> <strong>参数说明：</strong><br />
    [出现次数] 指该号码历史上出现的次数<br />
    [理论出现次数] 指遗漏对象理论上应该出现的次数<br />
    [出现频率] 用实出次数除以总期数，实际上是指该遗漏对象出现次数在全部遗漏对象中所占的比例<br />
    [平均遗漏] 指多期遗漏的平均值<br />
    [最大遗漏] 历史上遗漏的最大值<br />
    [上次遗漏] 指该号码上次开出之前的遗漏次数<br />
    [本次遗漏] 指该号码自上次开出之后的遗漏次数<br />
    [欲出几率] 本期遗漏/平均遗漏<br />
    [回补几率] 回补几率的计算方法为上期遗漏减去本期遗漏再除以循环周期即得回补几率<br />
    [最大遗漏期间] 指最大遗漏出现的期间段<br />
    <span class="redchar">[注]：因平均遗漏和最大遗漏是在统计期数范围内历史数据基础上做出的统计(不包括本次),所以导致遗漏表中某些栏目没有统计数据,暂用"-"表示</span></div>
</div>
<#include "../bottom_common.ftl"/>
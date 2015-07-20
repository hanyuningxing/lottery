<head>
  <title>奖金预测</title>
  <meta name="decorator" content="tradeV1" />
  <script type="text/javascript">
	function showDetail(id){
	  $('table[_group="detail"]').each(function(index, domEle){
	  	domEle.style.display = (id == domEle.id)?'':'none';
	  });
	}
  </script>
<style>
.k10 {
	height:10px;
	display:block;
	clear:both;
	line-height:1px;
	font-size:1px;
	clear:both;
}
.yucetrtitle {
	line-height:24px;
}
.yucetrtitle td {
	background:#1F6973;
	color:#fff;
	font-size:12px;
	text-align:center;
	border-right:1px solid #68A0BD;
	border-bottom:1px solid #5085A1;
}
.yucetr08 {
	line-height:24px;
}
.yucetr08 td {
	background:#7EBFE0;
	color:#25536B;
	font-size:12px;
	text-align:center;
	border-right:1px solid #68A0BD;
}
.yucetrorg {
	line-height:24px;
}
.yucetrorg td {
	background:#FF5622;
	color:#fff;
	font-size:12px;
	text-align:center;
	border-right:1px solid #ccc;
	border-bottom:1px solid #ccc;
}
.yucetrwhite {
	line-height:24px;
}
.yucetrwhite td {
	background:#fff;
	color:#0D2D3E;
	font-size:12px;
	text-align:center;
	border-right:1px solid #ccc;
	border-bottom:1px solid #ccc;
}
.yucetryellow {
	line-height:20px;
}
.yucetryellow td {
	background:#F7EDBC;
	color:#975D41;
	font-size:12px;
	text-align:center;
	border-right:1px solid #ccc;
	border-bottom:1px solid #ccc;
}
.yucetrf5 {
	line-height:24px;
}
.yucetrf5 td {
	background:#f5f5f5;
	color:#0D2D3E;
	font-size:12px;
	text-align:center;
	border-right:1px solid #ccc;
	border-bottom:1px solid #ccc;
}
.yucetable {
	border-collapse:collapse;
	border:1px solid #ccc;
}
.yucesm {
	width:938px;
	margin:0 auto;
	color:#1B2529;
	line-height:24px;
	padding:10px;
	border:1px solid #ccc;
	background:#FFFEF8;
	font-size:14px;
}
.title {
	width:938px;
	margin:0 auto;
	color:#c63c26;
	text-align:center;
	line-height:24px;
	padding:10px;
	border:1px solid #ccc;
	background:#FFFEF8;
	font-size:18px;
	font-weight:blod;
}
</style>
</head>

<div class="title">奖金预测</div>

<table width="960" cellspacing="1" cellpadding="1" border="0" bgcolor="#dcdcdc" align="center" class="yucetable">
  <tr class="yucetrtitle">
    <td rowspan="2">场次 </td>
    <td rowspan="2">对阵 </td>
    <td rowspan="2">比赛时间 </td>
    <td rowspan="2">投注选项（参考SP值） </td>
    <td rowspan="2">最小SP值</td>
    <td colspan="4">最大SP值</td>
    <td rowspan="2">设胆</td>
  </tr>
  <tr class="yucetr08">
    <td>胜负 </td>
    <td>让分胜负 </td>
    <td>胜分差</td>
    <td>大小分 </td>
  </tr>
  <#assign sf=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SF") />
  <#assign rfsf=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@RFSF") />
  <#assign sfc=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@SFC") />
  <#assign dxf=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@DXF") />
  <#assign minMaxSpOfMatchMap = prizeForecast.minMaxSpOfMatchMap>
  
  <#if matchs?exists>
  	<#list matchs as match>
  		<#assign matchkey = match.matchKey>
  		<#if match_index%2==0>
  			<#assign class="yucetrwhite">
  		<#else>
  			<#assign class="yucetrf5">
  		</#if>
	  <tr class="${class}">
	    <td>${matchkey} </td>
	    <td title="${match.homeTeamName!} VS ${match.guestTeamName!}">${match.homeTeamName!} VS ${match.guestTeamName!}</td>
	    <td>${match.matchTime?string('yy-MM-dd HH:mm')}</td>
	    <td>
	    	<#if matchItemMap?exists>
	    		<#assign matchItems = matchItemMap[matchkey]>
	    		<#list matchItems as matchItem>
	    			<#if matchItem.playType==rfsf>(${match.singleHandicap!0})</#if>${matchItem.buildSelectedItemStr()}<br/>
	    		</#list>
	    	</#if>
	    </td>
	    <#assign minMaxSp = minMaxSpOfMatchMap[matchkey]>
	    <td>${minMaxSp[0]?string('0.00')}</td>
	    <td><#if minMaxSp[1].get(sf)?exists>${minMaxSp[1].get(sf)?string('0.00')}</#if></td>
	    <td><#if minMaxSp[1].get(rfsf)?exists>${minMaxSp[1].get(rfsf)?string('0.00')}</#if></td>
	    <td><#if minMaxSp[1].get(sfc)?exists>${minMaxSp[1].get(sfc)?string('0.00')}</#if></td>
	    <td><#if minMaxSp[1].get(dxf)?exists>${minMaxSp[1].get(dxf)?string('0.00')}</#if></td>
	    <td><#if danOfMatchMap?exists && danOfMatchMap[matchkey]?exists>√<#else>×</#if></td>
	  </tr>
  	</#list>
  </#if>
  <tr class="yucetrwhite">
    <td colspan="11">选择场数：${matchItemMap?size} 场　过关方式：[<#if prizeForecast.passTypes?size=1>普通过关<#else>多选过关</#if>] 
    	<#list prizeForecast.passTypes as passType>
          <#if passType_index gt 0>,</#if>${passType.text}
        </#list>
                    　  金额：￥${prizeForecast.cost} 元　 倍数：${prizeForecast.multiple} 倍 
    </td>
  </tr>
</table>
<div class="k10"></div>
<table width="960" cellspacing="1" cellpadding="1" border="0" bgcolor="#dcdcdc" align="center" class="yucetable">
  <tbody>
    <tr class="yucetrtitle">
      <td colspan="15">方案奖金预测详情</td>
    </tr>
    <tr class="yucetryellow">
      <td width="125" rowspan="2">中奖场数</td>
      <td colspan="${prizeForecast.passMatchList?size}">中奖注数</td>
      <td width="50" rowspan="2">倍数</td>
      <td colspan="2">奖金范围</td>
    </tr>
    <tr class="yucetryellow">
      <#list prizeForecast.passMatchList as m>
	      <td><#if m=1>单关<#else>${m}串1</#if></td>
	  </#list>
      <td>最小奖金</td>
      <td>最大奖金</td>
    </tr>
    <#assign minPassMatch=(prizeForecast.passTypes[0]).passMatchs[0]>
    <#if prizeForecast.passMatchWonUnitsMap?exists>
    	<#list minPassMatch..matchItemMap?size as passMatch>
	    	<tr class="yucetrwhite">
		      <td>${passMatch}</td>
		      <#list prizeForecast.passMatchList as passCount>
		      	<td> ${prizeForecast.passMatchWonUnitsMap.get(passMatch).get(passCount)!''} </td>
		      </#list>
		      <td>${prizeForecast.multiple}</td>
		      <td> ${prizeForecast.passMatchPrizeMap.get(passMatch)[0]?string('0.00')} <a title="点击查看明细" href="javascript:;" onclick="showDetail('min_${passMatch_index}');return false;" >明细</a></td>
		      <td> ${prizeForecast.passMatchPrizeMap.get(passMatch)[1]?string('0.00')} <a title="点击查看明细" href="javascript:;" onclick="showDetail('max_${passMatch_index}');return false;" >明细</a></td>
		    </tr>
	    </#list>
	</#if>    
  </tbody>
</table>

<div class="k10"></div>

<#macro prizeForecastDetail prizeInfoDetail id=''>
	<table id="${id}" _group="detail" width="960" cellspacing="1" cellpadding="1" border="0" bgcolor="#dcdcdc" align="center" class="yucetable" style="display:none;">
	  <tr class="yucetrorg">
	    <td colspan="4">奖金计算明细</td>
	  </tr>
	  <tr class="yucetryellow">
	    <td>过关方式</td>
	    <td>中奖注数</td>
	    <td>奖金明细</td>
	    <td>奖金合计</td>
	  </tr>
	  <#local totalUnits=0 />
	  <#local totalPrize=0 />
	  <#list prizeInfoDetail?keys as passCount>
	  	 <tr class="yucetrwhite">
		    <td><#if passCount=1>单关<#else>${passCount}串1</#if></td>
		    <#assign prizeInfoDetailArr = prizeInfoDetail.get(passCount)>
		    <#assign units=prizeInfoDetailArr[0]?size />
		    <#local totalUnits=totalUnits+units />
		    <#local totalPrize=totalPrize+prizeInfoDetailArr[1] />
		    <td>${units}</td>
		    <td>
		    	<#list prizeInfoDetailArr[0] as prizeDetailItem>
		    		${prizeDetailItem}<br />
		    	</#list>
		    </td>
		    <td>${prizeInfoDetailArr[1]?string('0.00')}</td>
		  </tr>
	  </#list>
	  
	  <tr class="yucetrf5">
	    <td>合计: </td>
	    <td>${totalUnits}</td>
	    <td></td>
	    <td>${totalPrize?string('0.00')}</td>
	  </tr>
	</table>
</#macro>
<#list prizeForecast.passMatchPrizeInfoMap?keys as passMatch>
	<@prizeForecastDetail prizeForecast.passMatchPrizeInfoMap.get(passMatch)[0] 'min_'+passMatch_index />
	<@prizeForecastDetail prizeForecast.passMatchPrizeInfoMap.get(passMatch)[1] 'max_'+passMatch_index />
</#list>

<div class="k10"></div>
<div align="center"><a href="javascript:;" onclick="window.close();"><img src="${base}/V1/images/yucebt_close.png" /></a></div>
<div class="k10"></div>
<div class="yucesm"> 注：<br />
  1、奖金评测是根据您选择的当前最小和最大参考sp值计算出大概奖金范围，仅供参考。<br />
  2、随着sp值的变化，计算出的奖金和实际奖金会有出入，最终奖金以实际开奖sp值计算得出的奖金为准；<br />
  3、“单注最小SP值乘积”指相同过关方式的单注中最小的SP值乘积。<br />
  4、“单注最大SP值乘积”指相同过关方式的单注中最大的SP值乘积。<br />
  5、“最小奖金”=单注最小SP值乘积×中奖注数×倍数×2元；<br />
  6、“最大奖金”=单注最大SP值乘积×中奖注数×倍数×2元；<br />
  7、“单关奖金”=单注固定SP值×倍数，页面中单关投注奖金仅供参考，实际奖金以官方最终公布的奖金为准；<br />
  8、没有SP值的投注结果不参与奖金计算。<br />
</div>
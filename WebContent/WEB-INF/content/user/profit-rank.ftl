<head>
	<title>${webapp.webName}网用户盈利排行榜</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="Keywords" content="盈利排行榜" />
	<meta name="Description" content="盈利排行榜" />
	<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
	<link href="${base}/V1/css/kaijiang.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/js/user/gradeInfo.js"></script>
</head>
<div class="k10"></div>
<!--All top end -->
<!--main-->
<div class="w1000">
  <div class="kaijiang_left">
    <div class="pxbang_navtop"></div>
    <div class="kaijiang_bg">
      <!--竞彩足球 begin-->
      <div class="kaijiang_jg">竞彩足球</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=5" <#if lottery=='JCZQ' && ptOrdinal==5>class="now"</#if>>让球</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=0" <#if lottery=='JCZQ' && ptOrdinal==0>class="now"</#if>>胜平负</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=2" <#if lottery=='JCZQ' && ptOrdinal==2>class="now"</#if>>比分</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=1" <#if lottery=='JCZQ' && ptOrdinal==1>class="now"</#if>>进球数</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=3" <#if lottery=='JCZQ' && ptOrdinal==3>class="now"</#if>>半全场</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCZQ&ptOrdinal=4" <#if lottery=='JCZQ' && ptOrdinal==4>class="now"</#if>>混合过关</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--竞彩足球 end-->
      <!--竞彩篮球 begin-->
      <div class="kaijiang_jg">竞彩篮球</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/user/user_profitRank.html?lottery=JCLQ&ptOrdinal=0" <#if lottery=='JCLQ' && ptOrdinal==0>class="now"</#if>>胜负</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCLQ&ptOrdinal=1" <#if lottery=='JCLQ' && ptOrdinal==1>class="now"</#if>>让分胜负</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCLQ&ptOrdinal=2" <#if lottery=='JCLQ' && ptOrdinal==2>class="now"</#if>>胜分差</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCLQ&ptOrdinal=3" <#if lottery=='JCLQ' && ptOrdinal==3>class="now"</#if>>大小分</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=JCLQ&ptOrdinal=4" <#if lottery=='JCLQ' && ptOrdinal==4>class="now"</#if>>混合过关</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--竞彩篮球 end-->
      <!--北京单场 begin-->
      <div class="kaijiang_jg">北京单场</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/user/user_profitRank.html?lottery=DCZC&ptOrdinal=0" <#if lottery=='DCZC' && ptOrdinal==0>class="now"</#if>>让球胜平负</a></li>
          <li><a href="#">&nbsp;</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=DCZC&ptOrdinal=1" <#if lottery=='DCZC' && ptOrdinal==1>class="now"</#if>>进球数</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=DCZC&ptOrdinal=2" <#if lottery=='DCZC' && ptOrdinal==2>class="now"</#if>>上下单双</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=DCZC&ptOrdinal=3" <#if lottery=='DCZC' && ptOrdinal==3>class="now"</#if>>比分</a></li>
          <li><a href="${base}/user_profitRank.html?lottery=DCZC&ptOrdinal=4" <#if lottery=='DCZC' && ptOrdinal==4>class="now"</#if>>半全场</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--北京单场 end-->
      <!--足球彩票 begin-->
      <div class="kaijiang_jg">足球彩票</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/user/user_profitRank.html?lottery=SFZC&ptOrdinal=0" <#if lottery=='SFZC' && ptOrdinal==0>class="now"</#if>>14场胜负</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=SFZC&ptOrdinal=1" <#if lottery=='SFZC' && ptOrdinal==1>class="now"</#if>>任选9场</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=LCZC" <#if lottery=='LCZC'>class="now"</#if>>6场半全场</a></li>
          <li><a href="${base}/user/user_profitRank.html?lottery=SCZC" <#if lottery=='SCZC'>class="now"</#if>>4场进球</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--足球彩票 end-->
    </div>
  </div>
  <!--hm left end-->
  <div class="kaijiang_rig">
    <div class="kaijiang_topbg">
      <!--足彩通用-->
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="80" align="center"><div class="kaijiang_logo kjlogo_zc"></div></td>
          <td  class="kaijiang_name" style="text-align:left;">${lottery.lotteryName}${playTypeName!}排行榜</td>
          <td></td>
          <td align="center">&nbsp;</td>
          <td></td>
        </tr>
      </table>
    </div>
    <div class="k5"></div>
    <div class="paixbtop">
      <div class="fl l10px">
        <ul class="pai_menu">
          <li class="<#if fadan>paion</#if>"><a href="${base}/user/user_profitRank.html?lottery=${lottery!}&ptOrdinal=${ptOrdinal!}&fadan=true">发单盈利榜</a></li>
          <li class="<#if !fadan>paion</#if>"><a href="${base}/user/user_profitRank.html?lottery=${lottery!}&ptOrdinal=${ptOrdinal!}&fadan=false">跟单盈利榜</a></li>
        </ul>
      </div>
      <div class="fr r10px">
        <ul class="paixb_date">
          <li class="<#if days==7>pxbchose</#if>"><a href="${base}/user/user_profitRank.html?lottery=${lottery!}&ptOrdinal=${ptOrdinal!}&fadan=${fadan?string}&days=7">近7日</a></li>
          <li class="<#if days==30>pxbchose</#if>"><a href="${base}/user/user_profitRank.html?lottery=${lottery!}&ptOrdinal=${ptOrdinal!}&fadan=${fadan?string}&days=30">近30日</a></li>
          <li class="<#if days==90>pxbchose</#if>"><a href="${base}/user/user_profitRank.html?lottery=${lottery!}&ptOrdinal=${ptOrdinal!}&fadan=${fadan?string}&days=90">近90日</a></li>
        </ul>
      </div>
    </div>
    <!--合买表格-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" class="kjtableb1">
      <tr align="center" class="kjtdtitle"  height="28">
        <td width="50">排名</td>
        <td width="135">用户名</td>
        <td width="110">战绩</td>
        <td width="100">发单金额</td>
        <td width="100">中奖金额</td>
        <td width="100">盈利金额</td>
        <td width="80">回报率(倍)</td>
        <td width="90">当前期方案</td>
        <td>自动跟单</td>
      </tr>      
      <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
      	<script>
			var gradeMedalInfoArrOfPage = new Array(${pagination.result?size});
	  	</script>
		<#list pagination.result as data>
			<#assign wonRank = data[4]>
			<#assign user = data[0]>
			<#assign gradeInfo = data[2]>
			<#if days??>
				<#if days==7>
					<#assign cost=wonRank.cost_7>
					<#assign profit=wonRank.profit_7>
				<#elseif days==30>
					<#assign cost=wonRank.cost_30>
					<#assign profit=wonRank.profit_30>
				<#elseif days==90>
					<#assign cost=wonRank.cost_90>
					<#assign profit=wonRank.profit_90>
				</#if>
			<#else>
				<#assign cost=wonRank.cost_30>
				<#assign profit=wonRank.profit_30>
			</#if>
			<tr align="center" class="kjtdwhitelist" height="31" onmouseover="this.className='kjtrhover'" onmouseout="this.className='kjtdwhitelist'" >
	        <td>${data_index+1} </td>
	        <td><a href="${base}/blog/${user.userId}/">${user.userName} </a></td>
	        <td><a onmouseover="showGradeInfo(this,gradeMedalInfoArrOfPage[${data_index}])"><span class="qk_football01" style="height:12px;width:${gradeInfo.medal_zq.golden1()*13}px;" ></span><span class="qk_football02" style="height:12px;width:${gradeInfo.medal_zq.golden2()*13}px;"></span><span class="qk_football03" style="height:12px;width:${gradeInfo.medal_zq.silvery3()*13}px;"></span></a></td>
	        <script>
				<#assign medalsMap=gradeInfo.getMedalMap()/>
				<#if medalsMap?exists>		
					var medalInfoArr = new Array(${medalsMap?size});
					<#assign idx=0>
		            <#list medalsMap?keys as key>
		            	var medalInfoArrOfPt = new Array(5);
		            	<#assign typeAndText=user.gradeInfo.getTypeAndKey2Text(key)>
		            	medalInfoArrOfPt[0]='${typeAndText[0]}';
		            	medalInfoArrOfPt[1]='${typeAndText[1]}';
		            	<#assign gradeMedal=medalsMap.get(key)>
		            	medalInfoArrOfPt[2]=${gradeMedal.golden1()};
		            	medalInfoArrOfPt[3]=${gradeMedal.golden2()};
		            	medalInfoArrOfPt[4]=${gradeMedal.golden3()};
		            	medalInfoArr[${idx}]=medalInfoArrOfPt;
		            	<#assign idx=idx+1>                
		            </#list>
		            gradeMedalInfoArrOfPage[${data_index}] = medalInfoArr;
		        </#if>
			</script>
	        <td>${cost?string(',###.00')}</td>
	        <td>${(cost+profit)?string(',###.00')}</td>
	        <td>${profit?string(',###.00')}</td>
	        <td>${(profit/cost)?string('#.##')}</td>
	        <td><a href="${base}/${wonRank.lottery?lower_case?html}/scheme_subList.html">查看</a></td>
	        <td><a href="${base}/lottery/auto!autoNew.action?userId=${user.userId}">定制</a></td>
	      </tr>
		</#list>
	  <#else>
		<tr>
		     <td colspan="9" align="center" >暂无记录.</td>
		</tr>
	  </#if>
    </table>
    <#import "../../macro/pagination.ftl" as b />
	<@b.page {'ajaxContainer':'extra-data-container'} />
  </div>
</div>
<!--main end-->
<!--其他几个彩种 LOGO begin-->
<br />
<br />
<br />
<br />
<!--其他几个彩种 LOGO end-->
<!--bottm-->
<!-- 页尾版权开始 -->
<!-- 页尾版权信息结束 -->

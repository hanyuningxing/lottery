<head>
	<#if playType==0>
		<meta name="Description" content="${webapp.webName}排列3网上投注频道：为广大彩民实时在线提供体彩排列三投注服务、手机排列三投注服务，包括每日第一时间更新排列三试机号和开奖号、专家预测分析、跨度走势图，排列3购买方案推荐等网上资讯，以及专业安全的网上投注彩票功能服务。" />
		<title>排列三试机号，体彩排列三购买投注 - ${webapp.webName}安全购彩平台</title>
	<#elseif playType==1>
		<meta name="Description" content="${webapp.webName}排列5网上投注频道：为广大彩民实时在线提供体彩排列五各种服务、手机排列五投注服务，包括每日第一时间更新排列五开奖、专家预测分析、跨度走势图，排列5购买方案推荐等网上资讯，以及专业安全的网上投注彩票功能服务。" />
		<title>体彩排列五，排列五购买投注，排列五开奖- ${webapp.webName}安全购彩平台</title>
	</#if>
	
	<meta name="decorator" content="trade" />
	<meta name="category" content="${lotteryType.category}" />
	<meta name="lottery" content="${lotteryType}" />
	<link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/chase.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/pl/compoundInit.js"/>"></script>
</head> 
<#if createForm??&&createForm.playType??>
    <#assign salesPlayType = createForm.playType>
</#if>
<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
  	<div class="mbxdh"><span>您当前的位置:<a href="/">${webapp.webName}彩票网</a> > ${lotteryType.lotteryName}</span></div>
    <#include "../common/head.ftl" />
   <div class="cleanboth k3px" >
   
      <!--选择表单 -->
            <form action="<@c.url value="/${lotteryType.key}/scheme!editNew.action" />" method="get" id="selectForm">
							          <input type="hidden" name="createForm.playType" value="<#if createForm??&&createForm.playType??>${createForm.playType}</#if>" />
								      <input type="hidden" name="salesMode" value="${salesMode}" />
									  <input type="hidden" name="playType" value="${playType}" />
									  <#if shareType??>
											<input type="hidden" name="shareType" value="${shareType}" />
									  </#if>
									  <input type="hidden"  name="menuType" value="${menuType}" />
									  <#if periodNumber??>
											<input type="hidden" name="periodNumber" value="${periodNumber}" />
									  </#if>
			</form>
						<script> 
						    function selectFormMethod(playType){
								var selectForm = document.getElementById("selectForm");
								selectForm["createForm.playType"].value=playType;
								selectForm.submit();
								return false;
							}
							 function selectSalesModeFormMethod(salesMode){
								var selectForm = document.getElementById("selectForm");
								selectForm["salesMode"].value=salesMode;
								selectForm.submit();
								return false;
							}
						</script>
	                    <div class="bggray32"> 
	                                        <!--复式-->
	                                        <#if playType==1>
	                                             <a <#if salesMode??&&salesMode='COMPOUND'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectSalesModeFormMethod('COMPOUND');return false;">直选复式</a>
	                                             <a <#if salesMode??&&salesMode='SINGLE'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectSalesModeFormMethod('SINGLE');return false;">直选单式</a>
									        <#elseif playType==0>
									             <a <#if salesPlayType='P3Direct'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('P3Direct');return false;">直选</a>
									             <a <#if salesPlayType='Group3'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('Group3');return false;">组选3</a>
									             <a <#if salesPlayType='Group6'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('Group6');return false;">组选6</a>
									             <a <#if salesPlayType='DirectSum'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('DirectSum');return false;">直选和值</a>
									             <a <#if salesPlayType='GroupSum'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('GroupSum');return false;">组选和值</a>
									             <a <#if salesPlayType='P3DirectKuadu'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('P3DirectKuadu');return false;">直选跨度</a>
									             <a <#if salesPlayType='P3Group3Kuadu'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('P3Group3Kuadu');return false;">组三跨度</a>
									             <a <#if salesPlayType='P3Group6Kuadu'>class="graydownmenunow"<#else>class="graydownmenu"</#if> href="javascript:void(0);" onclick="selectFormMethod('P3Group6Kuadu');return false;">组六跨度</a>
									             <a class="graydownmenu" href="javascript:void(0);" onclick="selectSalesModeFormMethod('SINGLE');return false;"><image src="<@c.url value="/pages/images/danshibt.gif" />" class="qiehuan"/></a>
									       </#if>
	  </div>
      <!-- 当前期-->
      <div class="all8px">
         <form action="<@c.url value="/${lotteryType.key}/scheme!editNew.action" />" method="get">
				<#list periods as p>
					   <#if p.periodNumberDisplay??>
						    <#if 1==p.periodNumberDisplay>
						               <input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
						                 <label class="inputcheckbox orgchar">(第${p.periodNumber!}期)当前期</label>
						            <#elseif 2==p.periodNumberDisplay>
						                 <input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
						                 <label class="inputcheckbox">(第${p.periodNumber!}期)预售期</label>
		                   	</#if>
		                   	<#else>
		                   	<input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>
		                   	<label class="inputcheckbox">(第${p.periodNumber!}期)</label>
	                   </#if>
				</#list>
			<input type="hidden" name="createForm.playType" value="${salesPlayType}"/>
			<input type="hidden" name="salesMode" value="${salesMode}" />
			<input type="hidden" name="playType" value="${playType}" />
			<#if shareType??>
			<input type="hidden" name="shareType" value="${shareType}" />
			</#if>
         </form>
      </div>
      <!-- 玩法说明-->
      <div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
           &nbsp;&nbsp;
           <#if playType==1>
                                   玩法说明：从0-9中选择5个号码分别对万、千、百、十、个位投注，所选号码与开奖号码全部一致且顺序相同，即中奖；<span class=" rebchar">单注奖金100000元</span>。
      	  	 <#elseif playType==0>
      	  	   <#if salesPlayType='P3Direct'>玩法说明：从0-9中选择3个号码（或以上）对百、十、个位投注，所选号码与开奖号码全部一致，且顺序相同，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='Group3'>玩法说明：从0-9中选择2个号码（或以上）投注，系统会自动将所选号码的所有组三组合（即三个号中有两个号相同）进行购买，若当期开奖号码的形态为组三且包含了投注号码，即中奖，单注奖金320元。</#if>
      	  	   <#if salesPlayType='Group6'>玩法说明：从0-9中选择3个号码（或以上）投注，系统会自动将所选号码的所有组六组合（即三个号码各不相同）进行购买，若当期开奖号码的形态为组六且包含了投注号码，即中奖，单注奖金160元。</#if>
      	  	   <#if salesPlayType='DirectSum'>玩法说明：和值为当期开奖号码之和。和值购买时，系统会自动选取该和值的所有排列组合进行购买。所选和值与当期开奖号码的和值一致，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='GroupSum'>玩法说明：和值为当期开奖号码之和。只要个、十、百位三数相加为投注和值即中奖，不限顺序和形态，视开奖号码而定，组六奖金160元、组三奖金320元。</#if>
      	  	   <#if salesPlayType='BaoChuan'>玩法说明：选择任意一个或者多个号码进行任意排列投注，只要你所选的号码包含在全部开奖号码中，即中奖，单注奖金1000元。包1个号码时即为豹子。</#if>
      	  	   <#if salesPlayType='P3DirectKuadu'>玩法说明：从0-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部号码按顺序进行排列组合，若当期开奖号码的最大跨度为所选跨度，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='P3Group3Kuadu'>玩法说明：从1-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部组三组合进行购买，若当期开奖号码的形态为组三且最大跨度为所选跨度，即中奖，单注奖金320元。</#if>
      	  	   <#if salesPlayType='P3Group6Kuadu'>玩法说明：从2-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部组六组合进行购买，若当期开奖号码的形态为组六且最大跨度为所选跨度，即中奖，单注奖金160元。</#if>
      	   </#if>
      </div>
      <!-- 投注区-->
     <div  class="cleanboth" >
        <div id="touzzhuqu">
          <!-- 红球-->
          <div class="left" style="width:665px;">
            <fieldset class="redfieldset">
             <legend class="cdownjl"><span class="redc1">${salesPlayType.typeName!}复式投注区</span>&nbsp;<span class="grayc1"><#if salesPlayType='Group3'>每行数字至少选择2个数字<#elseif salesPlayType='Group6'>每行数字至少选择3个数字<#else>每行数字至少选择1个数字</#if></span></legend>
             <div class="all10px" id="area_box">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <#if salesPlayType=="P5Direct">
				   <#list 1..5 as n>
				        <tr id="_pl5ball_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>万位<#elseif n==2>千位<#elseif n==3>百位<#elseif n==4>十位<#elseif n==5>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
	               </#list>
	               <#elseif salesPlayType=="P3Direct">
				     <#list 1..3 as n>
				        <tr id="_pl3ball_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>百位<#elseif n==2>十位<#elseif n==3>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td>
		                   <ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
		                    </ul>
		                  </td>
		                </tr>
				     </#list>
				     <#elseif salesPlayType=="DirectSum"||salesPlayType=="GroupSum">
				         <tr id="${salesPlayType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 0..27 as num>
		                         <#if salesPlayType=="GroupSum">
		                                 <#if num gt 0&&num lt 27>
		                                   <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                                 </#if>
		                         <#else>
		                            <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                         </#if>
		                         
		                      </#list>
		                    </ul>
		                 </td>
		                </tr>
				     <#else>
				       <tr id="${salesPlayType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <#if salesPlayType=="P3Group3Kuadu">
		                              <#if num gt 0>
		                                  <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                              </#if>
		                         <#elseif salesPlayType=="P3Group6Kuadu">
		                             <#if num gt 1>
		                                  <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                              </#if>
		                         <#else>
		                            <li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                         </#if>
		                         
		                      </#list>
		                    </ul>
		                 </td>
		                  <td>
		                  <#if salesPlayType=="Group3"||salesPlayType=="Group6">
		                     <ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
		                     </ul>
		                  </#if>
		                 </td>
		                </tr>
	            </#if>
              </table>
            </div>
            </fieldset>
          </div>
        </div>
      </div> 
      <#include '../common/count.ftl' />
      <div class="kong10"></div>
      <#if salesPlayType=="Group3"||salesPlayType=="Group6"||salesPlayType=="P3Direct"||salesPlayType=="P5Direct">
	      <div id="jixuanqu">
	        <!-- 输入框-->
	        <div class="left">
	          <div class="left1">
	            	 <ul id="createForm_select_content" class="texk haomaqu"></ul>
	          </div>
	          <!-- bt机选按钮-->
	          <div class="floatleft">
	            <ul class="jixuan_menu">
	             <#if salesPlayType=="Group3">
	                  <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(1);return false;">机选二注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(3);return false;">机选六注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(4);return false;">机选八注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(5);return false;">机选十注</a></li>
	              <#else>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(1);return false;">机选一注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(5);return false;">机选五注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(8);return false;">机选八注</a></li>
		              <li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(10);return false;">机选十注</a></li>
	              </#if>
	              <li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
	            </ul>
	          </div>
	          <div class="kong10"></div>
	        </div>
	         <#if salesPlayType=="P3Direct"||salesPlayType=="P5Direct">
	            <#if salesPlayType=="P5Direct">
	                <#assign luckLotteryNum='4' />
	            <#else>
	            	<#assign luckLotteryNum='3' />
	            </#if>
	         	<#include '../common/luck.ftl' />
	         </#if>
	      </div>
      <#else>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td align="center">
		               <ul id="createForm_select_content" class="texk haomaqu1" style=" width:460px;height:90px;list-style:none;"></ul>
		          </td>
		        </tr>
		        <tr>
		          <td height="30" align="center"><input type="button" value="清空号码" onclick="clearAll();return false;"/>
		          </td>
		        </tr>
		      </table>
		      <div class="kong10"></div>
      </#if>
      
      
      <form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
        <#if createForm??&&createForm.playType??>
			<input type="hidden" name="createForm.playType" value="${createForm.playType}" />
		</#if>
		<input type="hidden" name="playType" value="${playType}" />
	    <#assign canChase=true />
	    <#include '../common/init_common_new.ftl' />
      </form>
    </div>
  </div>
  <!-- left结束 -->
  <#include 'rightContent.ftl' />
  
</div>
	      	<div class="kong10"></div>
<#if playType??>
	<#if playType==0>
			<div class="zxpd">
			<div class="zx"><h1>${webapp.webName}体彩排列3网上投注频道</h1></div>
			<div class="zx_link">
			为广大彩民实时在线提供<a href="<@c.url value="/pl/scheme!editNew.action?playType=0"/>" target="_blank"><b>体彩排列三</b></a>投注服务、手机排列三投注服务，包括每日第一时间更新排列三试机号和开奖号、专家预测分析、跨度走势图，排列3购买方案推荐等网上资讯，以及专业安全的网上投注彩票功能服务。<br />
			<b>排列三彩彩种玩法介绍： </b><br />
			  排列三是一种国家体彩中心统一发行，统一开奖，统一兑奖的小盘玩法游戏玩法彩种，排列三投注区分为百位、十位和个位，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为3位数。排列三玩法即是竟猜3位开奖号码。 排列三分直选、组选三和组选六三种玩法。 <br />
			  <b>排列三玩法特点： </b><br />
			  1.排列三节奏较快：体彩排列三每天开奖一次，节奏较快。<br />
			  2. 排列三玩法简单：数字型玩法，更易组合和分析，使运气成分降低，智力型增强。<br />
			  3. 排列三中奖率高：直选、组3、组6中奖概率分别为1/1000、1/333和1/167。<br />
			  4. 排列三返奖率高：体彩排列三计提返奖率为50%，但实际返奖比较往往高于50%。<br />
			  5. 排列三小玩法、大品牌：体彩排列三是典型的小盘玩法游戏，是体彩品牌玩法。
			  </div>
			</div>
	<#elseif playType==1>
			<div class="zxpd">
			<div class="zx"><h1>${webapp.webName}体彩排列5网上投注频道</h1></div>
			<div class="zx_link">
			为广大彩民实时在线提供<a href="<@c.url value="/pl/scheme!editNew.action?playType=1"/>" target="_blank"><b>体彩排列五</b></a>各种服务、手机排列五投注服务，包括每日第一时间更新排列五开奖、专家预测分析、跨度走势图，排列5购买方案推荐等网上资讯，以及专业安全的网上投注彩票功能服务。<br /> 
			<b>排列五彩种玩法介绍： </b><br />
			  排列五类似于排列三，是国家体彩中心统一发行，全国统一开奖，兑奖的小盘体彩玩法游戏彩种，排列五投注区分为万位、千位、百位、十位和个位，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为5位数。排列五玩法即是竟猜5位开奖号码，且要保证顺序一致。 <br />
			<b>排列五购买投注方式： </b><br />
			1、体彩排列五标准投注：对万位、千位、百位、十位和个位各选1个号码为一注，每位号码最多可0～9全选，投注号码与开奖号码按位一致即为中奖，单注奖金十万元。<br />
			2、体彩排列五单式上传：将固定格式的单式号码统一上传给系统进行投注。此投注方式对于将过滤软件过滤出的单式号码统一投注十分方便。
			 </div>			
			</div>
	</#if>
</#if>
 
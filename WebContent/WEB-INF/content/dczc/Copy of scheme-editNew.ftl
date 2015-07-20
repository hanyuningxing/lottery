<#if shareType?? && shareType == 'SELF'>
	<#assign menu_type='scheme_editNew_SELF' />
<#else>
	<#assign menu_type='scheme_editNew_TOGETHER' />
</#if>
<#assign passTypeArr=passTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PassType@values()")) />
<#assign hasHandicap=playType=='SPF' />
<@override name="title">
	<title>北京单场足彩，北京单场推荐 - ${webapp.webName}安全购彩平台</title>
</@override>  
<@override name="head">
	<meta name="Keywords" content="北单单场,单场足彩,网上买单场,单场投注,五大联赛单场,世界杯单场，欧洲杯单场，网上购买" /> 
	<meta name="Description"content="${webapp.webName}提供单场网上购买和在线选号以及赔率和数据分析，用户在${webapp.webName}可以购买和 参与合买单场胜负，比分，进球数，半全场以及大小单双。"/>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchLanguage.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilter.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/freshSp.js"/>"></script>
	<script type="text/javascript">
		window.maxPassMatchCount = ${playType.maxMatchSize};
		window.PassType = {};
		window.PassTypeArr = [];
		<#list passTypeArr as passType>
			window.PassTypeArr[${passType_index}] = PassType['${passType}'] = {
				key : '${passType}',
				units : ${passType.units},
				matchCount : ${passType.matchCount},
				passMatchs :[<#list passType.passMatchs as c><#if c_index gt 0>,</#if>${c}</#list>],
				text : '${passType.text}',
				value : ${passType.value}
			};
		</#list>
	</script>
	<@block name="editNewHead"></@block>
</@override>  

<@override name="content">
    <!--对阵头部-->
    <@block name="topContent"></@block>
    <!--对阵-->
		<@block name="matchContent">
			<div style="float:left; width:710px;">
			<#if matchMap?? && matchMap?size gt 0>
				<#include 'match-${playType}.ftl' />
			<#else>
				<div style="line-height:100px;text-align:center;height:100px;border:1px dashed #ccc;border-top:none;background:#F4F5F3;">暂无可投注的赛事.</div>
			</#if>
			</div>	
		</@block>
	<div class="k10"></div>
	    <div class="j9ts">
		  <div class="bro_af5529">竞彩足球投注提示</div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">1、</span><span class="tztsr">竞猜全部玩法过关投注、及比分单关投注奖金为固定奖金，赛事选择界面显示仅为当前参考奖金。实际奖金以出票时刻固定奖金为准。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">2、</span><span class="tztsr">让球符号含义，"+"为客让主，"-"为主让客。让球数含义，即（主队得分±让球数）减客队得分，大于0为胜，等于0为平，小于0为负。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">3、</span><span style="float:left; width:645px;">竞彩足球的官方销售时间为：周一至周五09:00-00:00，周六至周日09:00-01:00。本平台的代购截止时间提前于官方彩票销售截止时间 15分钟。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">4、</span><span style="float:left; width:645px;">竞彩足球彩果，以比赛90分钟内比分（含伤停补时）结果为准。其中投注赛事取消、中断或改期，官方比赛彩果公布或确认取消将延后36小时，对应场次奖金派发或退款将同步延后处理；取消比赛的任何结果都算对，固定奖金按照1计算</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">5、</span><span style="float:left; width:645px;">普通过关让球胜平负玩法最多过8关，总进球数玩法最多过6关，比分和半全场胜平负玩法最多过4关。自由过关没限制。</span></div>
	      <div class="tzts bro_6D4F42"><span class="tztsl">6、</span><span style="float:left; width:645px;">竞彩足球比分玩法是唯一可进行单关固定奖金投注的玩法，在比分玩法页面中可同时进行单关+过关的固定奖金投注。</span></div>
	    </div>
	</div>
</@override>   
    
<#--    
    
	<div class=" cleanboth dctwobg">
	  <div class="twowz">
	    <ul class="twotopnav">
	    	<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()")) />
	    	<#list playTypeArr as pt>
	        <li><a <#if playType?? && pt==playType>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${pt}&shareType=${shareType!}" />">${pt.text}</a></li>
	    	</#list>
	    </ul>
	  </div>
	</div>
    <div class="BSelect">
    	<#assign COMPOUND_AHEAD_END_NORMAL_PASS_MODE=stack.findValue("@com.cai310.lottery.DczcConstant@COMPOUND_AHEAD_END_NORMAL_PASS_MODE") />
    	<#assign COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE=stack.findValue("@com.cai310.lottery.DczcConstant@COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE") />
    	<#assign SINGLE_AHEAD_END=stack.findValue("@com.cai310.lottery.DczcConstant@SINGLE_AHEAD_END") />
		<table width="805" height="75" cellspacing="0" cellpadding="0" border="0" background="<@c.url value="/pages/images/dc_twobg.gif" />" class="lrb1">
	      <tbody><tr height="30">
	        <td class="left10"><span class="boldchar">截止时间：</span>普通过关赛前 <span class="timegreenchar">${COMPOUND_AHEAD_END_NORMAL_PASS_MODE}</span> 分钟；多选过关赛前 <span class="timegreenchar">${COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE}</span> 分钟；单式赛前 <span class="timegreenchar">${SINGLE_AHEAD_END}</span> 分钟 </td>
	        <td width="170" align="right" class="rig10">
	        	<#if salesMode == 'SINGLE'>
	        		<a class="dfmenu" href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${playType!}&salesMode=COMPOUND&shareType=${shareType!}" />">切换到复式玩法</a>
	        	<#else>
	        		<a class="dfmenu" href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${playType!}&salesMode=SINGLE&shareType=${shareType!}" />">切换到单式玩法</a>
	        	</#if>
	        </td>
	      </tr>
	      <tr height="45">
	        <td class="left10">
	          <img width="80" height="22" align="absmiddle" src="<@c.url value="/pages/images/dc_sx.gif" />" onclick="document.getElementById('LeagueIDDIV').style.display='block';return false;" style="cursor:pointer;" /><#rt/>
			  &nbsp;&nbsp;隐藏<span id="c_h" class="rebchar">0</span>场比赛 <a href="#" onclick="matchFilterReset();return false;">恢复</a> |<#rt/>
			  <span <#if !hasHandicap>style="display:none;"</#if>>
	          	<input id="s_hdp1" onclick="matchFilter();" checked="checked" type="checkbox" class="inputcheckbox" autocomplete="off"/><label for="s_hdp1">让球(<span class="rebchar">${handicapCount!}</span>)</label>
	          	<input id="s_hdp0" onclick="matchFilter();" checked="checked" type="checkbox" class="inputcheckbox" autocomplete="off"/><label for="s_hdp0">非让球(<span class="rebchar">${unHandicapCount!}</span>)</label>
	          </span>
	          <input id="s_end" onclick="matchFilter();" type="checkbox" class="inputcheckbox" autocomplete="off"/><label for="s_end">显示已截止[<span id="m_e_c" class="rebchar">0</span>]</label>
	        </td>
	        <td align="right" class="rig10" width="180"><#if period??&&period.current>[${period.periodNumber!}(当前期)]</#if> 查看：
	          <select name="periodNumber" onchange="window.location.href='<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${playType!}&salesMode=${salesMode!}&shareType=${shareType!}"/>&periodNumber='+this.value;">
		          <#list periods ?sort_by(["periodNumber"])?reverse as p>
					<option <#if period.id=p.id>selected="selected"</#if> value="${p.periodNumber}">${p.periodNumber}期</option>
				  </#list>
		      </select>
	        </td>
	      </tr>
	    </tbody></table>
		<div class="LeagueDIV" id="LeagueIDDIV" >
			<dl>
				<table cellspacing="1" cellpadding="0" id="LeagueTable"><tbody>
					<tr>
						<td><ul id="sg" class="game_select">
							<#if games??>
							<#list games as gameName>
								<li><input onclick="gameFilter();" id="gameName_chb_${gameName_index}" type="checkbox" checked="checked" value="${gameName_index}" autocomplete="off" /><label for="gameName_chb_${gameName_index}">${gameName}</label></li>
							</#list>
							</#if>
						</ul></td>
					</tr>
				</tbody></table>
			</dl>
			<dl class="control">
				<input type="button" class="FSFunctionMenu" onclick="batchSelectGame(1);" value="全选">
				<input type="button" class="FSFunctionMenu" onclick="batchSelectGame(0);" value="清除">
	           	<input type="button" class="FSFunctionMenu" onclick="javascript:document.getElementById('LeagueIDDIV').style.display='none';" value="关闭">
	   		</dl>
		</div>
	</div>
	<@block name="matchContent">
		<#include 'match-'+(playType?string)+'.ftl' /><#-- 导入不同玩法的对阵列表 -->
	</@block>
	<@block name="initContent"></@block>
	<span id="sp_version" style="display:none">${spVersion!0}</span>
	<script type="text/javascript">
		freshSp('${period.periodNumber}','${playType}');
	   if(!${(period.current)?string("true","false")}) {
	 	document.getElementById('s_end').checked =true;
		matchFilter();
	   }
	</script>
</@override>-->

<@extends name="base.ftl"/> 

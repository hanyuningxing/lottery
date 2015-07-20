<#assign passTypeArr=passTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PassType@values()")) />
<#assign hasHandicap=playType=='SPF' />
    <head>
	<title>方案编辑</title>
	<meta name="menu" content="activity"/>
	<meta name="menuItem" content="editDCZCScheme"/>
	</head>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchLanguage.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilter.js"/>"></script>
	<link href="<@c.url value="/pages/css/dc_main.css"/>" rel="stylesheet" type="text/css" />

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
	<div class="twonavgray">
		<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">北单方案编辑</span></div>
	</div>

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
	        <td align="right" class="rig10" width="180">查看：<select name="periodNumber" onchange="window.location.href='<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${playType!}&salesMode=${salesMode!}&shareType=${shareType!}"/>&periodNumber='+this.value;">
		          <#list periods as p>
					<option <#if period.id=p.id>selected="selected"</#if> value="${p.periodNumber}">${p.periodNumber}期<#if p.current>(当前期)</#if></option>
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



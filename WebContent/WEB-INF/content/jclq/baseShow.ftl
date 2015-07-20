<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="schemeInfo">
	<#if scheme.mode=='COMPOUND'>
		<#assign bestMinPrize=scheme.compoundContent.bestMinPrize * scheme.multiple>
		<#assign bestMaxPrize=scheme.compoundContent.bestMaxPrize * scheme.multiple>
	<#else>
		<#assign bestMinPrize=scheme.singleContent.bestMinPrize>
		<#assign bestMaxPrize=scheme.singleContent.bestMaxPrize>
		<#assign optimize=scheme.singleContent.optimize>
	</#if>
      过关方式：${scheme.passMode.text} <span class="red">${scheme.getPassTypeText()}</span>&nbsp;所选场次<span class="red">${scheme.getSelectedMatchKeys()?size}</span>场<br />
  <#if scheme.mode=='COMPOUND' || optimize?? && optimize>预计奖金： <span class="red">${bestMinPrize!0?string('￥###,###.00')}元-${bestMaxPrize!0?string('￥###,###.00')}元</span>（预计奖金为投注时的奖金，实际奖金以最终出票时为准）<br /></#if>
</@override>

<@override name="content">  
      <#if canViewDetail>
      	<#if scheme.mode=='COMPOUND' || optimize?? && optimize>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
            <tr class="v01tr">
              <td>赛事编号 </td>
              <td>赛事 </td>
              <td>开赛 </td>
              <td>主队 VS 客队 </td>
              <td>让球数 </td>
              <td>全场比分 </td>
              <td>赛果 </td>
              <td>您的选择</td>
              <td>胆码</td>
            </tr>
            <#list selectedMatchList as matchInfo>
            	<#assign match=matchInfo[0]>
            	<#assign matchItems=matchInfo[1]>
            	<tr class="v02tr">
	              <td>${match.matchKeyText} </td>
	              <td>${match.gameName} </td>
	              <td>${match.matchTime?string('MM-dd HH:mm')} </td>
	              <td>${match.homeTeamName!} VS ${match.guestTeamName!}</td>
	              <#assign handicapClass=''/>
			      <#if match.handicap??>
			    	<#if match.handicap gt 0>
			    		<#assign handicapClass='rc1'/>
			    	<#elseif match.handicap lt 0>
			    		<#assign handicapClass='green01'/>
			    	</#if>
			      </#if>
	              <td><span class="${handicapClass}">${match.handicap!}</span></td>
	              <td><#if match.fullHomeScore?? && match.fullGuestScore??>${match.fullHomeScore!}:${match.fullGuestScore!}<#else>&nbsp;</#if></td>
	              <#assign resultStr = ''>
	              <#assign selectedItemStr = ''>	                            
	              <#list matchItems as matchItem>
	              	<#if !match.cancel && match.ended>
		              	<#assign result = match.getResult(matchItem.playType)!''>
		              	<#if result!=''><#assign resultStr = resultStr + result.text + '&nbsp;'></#if>
		            </#if>
	              	<#assign selectedItemStr = selectedItemStr + matchItem.buildSelectedItemStr() + '&nbsp;'>
	              </#list>
	              <td><#if match.cancel><font color="blue">取消</font><#else>${resultStr!}&nbsp;</#if></td>
	              <td>${selectedItemStr!}&nbsp;</td>
	              <td><#if matchItems.get(0).dan>√<#else>×</#if></td>
	            </tr>
            </#list>
          </table>
        <#elseif scheme.mode=='SINGLE'>
			<textarea name="content" cols="106" rows="10" readonly="readonly" style="border:1px solid #A7CAED;font-size:12px;">${(scheme.contentText)!}</textarea>
		</#if>		
          <div class="a10px">
	          <@block name="ticketCombination_a"><a onclick="showTicketInfo(this);return false;" href="<@c.url value="/${scheme.lotteryType.key}/scheme!viewTicketCombination.action?id=${scheme.id}" />">点击查看拆分明细</a></@block>&nbsp;&nbsp;
	          <span class="red">*方案奖金计算以最终出票时为准</span>
          </div>
          <!--显示拆票详细-->
          <span id="extra-ticket-container"></span>
      <#else>
      	${scheme.secretType.secretName}
      </#if>
</@override>

<@extends name="/WEB-INF/content/common/simple-scheme-showV1.ftl"/> 
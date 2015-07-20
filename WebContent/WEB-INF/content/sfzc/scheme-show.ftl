<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="content">
	<#if canViewDetail>
		<#if scheme.mode=='COMPOUND'>
			<#assign hasDan=scheme.playType=='SFZC9' />
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
		        <tr class="v01tr">
				  <td>场次</td>
				  <td>主客队</td>
				  <td>比赛时间</td>
				  <td>比分</td>
				  <td>彩果</td>
				  <#if hasDan><td>设胆</td></#if>
				  <td>复式投注</td>
				</tr>
			    <#list items as betItem>
			    	<#assign match=matchs[betItem_index] />
					<tr class="v02tr">
					  <td>${(match.lineId+1)?string('00')}</td>
					  <td>${match.homeTeamName!} <font color="red">VS</font> ${match.guestTeamName!}</td>
					  <td>${match.matchTime?string("MM-dd HH:mm (E)")}</td>
					  <td><#if match.cancel>取消<#elseif match.homeScore?? && match.guestScore??>${match.homeScore}:${match.guestScore}<#else>&nbsp;</#if></td>
					  <td class="redc">${match.result!}&nbsp;</td>
					  <#if hasDan><td><#if betItem.shedan>√<#else>&nbsp;</#if></td></#if>
					  <td>${betItem.toBetString()}</td>
					</tr>
			    </#list>
			</table>
		<#elseif scheme.mode=='SINGLE'>
			<#if scheme.uploaded>
				<@block name="singleSchemeContent">
					<textarea name="content" cols="106" rows="10" readonly="readonly" style="border:1px solid #A7CAED;font-size:12px;">${(scheme.contentText)!}</textarea>
				</@block>
			<#elseif loggedUser?? && loggedUser.id == scheme.sponsorId>
				<a href="<@c.url value="/${scheme.lotteryType.key}/scheme!editUpload.action?schemeNumber=${scheme.schemeNumber}" />">上传方案内容</a>
			<#else>
				方案未上传
			</#if>
		</#if>
	<#else>
      	${scheme.secretType.secretName}
      </#if>	
</@override>

<@extends name="/WEB-INF/content/common/simple-scheme-showV1.ftl"/>
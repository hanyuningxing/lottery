<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="content">
	<#if canViewDetail>
		<#if scheme.mode=='COMPOUND'>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
		        <tr class="v01tr">
				  <td>场次</td>
				  <td>主客队</td>
				  <td>比赛时间</td>
		  		  <td>类型</td>
				  <td>比分</td>
				  <td>彩果</td>
				  <td>复式投注</td>
				</tr>
				<#assign index=0 />
			    <#list matchs as match>
			    	<#assign betItem=items[index] />
					<tr class="v02tr">
					  <td rowspan="2">${index+1}</td>
					  <td rowspan="2">${match.homeTeamName!} <font color="red">VS</font> ${match.guestTeamName!}</td>
					  <td rowspan="2">${match.matchTime?string("MM-dd HH:mm (E)")}</td>
					  <td>半场</td>
					  <td><#if match.cancel>取消<#elseif match.halfHomeScore?? && match.halfGuestScore??>${match.halfHomeScore}:${match.halfGuestScore}<#else>&nbsp;</#if></td>
					  <td class="redc">${match.halfResult!}&nbsp;</td>
					  <td>${betItem.toBetString()}</td>
					</tr>
		    		<#assign index=index+1 />
			    	<#assign betItem=items[index] />
					<tr class="v02tr">
					  <td>全场</td>
					  <td><#if match.cancel>取消<#elseif match.homeScore?? && match.guestScore??>${match.homeScore}:${match.guestScore}<#else>&nbsp;</#if></td>
					  <td class="redc">${match.result!}&nbsp;</td>
					  <td>${betItem.toBetString()}</td>
					</tr>
		    		<#assign index=index+1 />
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
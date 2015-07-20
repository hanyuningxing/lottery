<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="schemeInfo">
	<#list scheme.compoundContent.items as matchItem>
		<#assign danSize=0 />
		<#if matchItem.dan><#assign danSize=danSize+1 /></#if>
	</#list>
	<span>过关方式：<#list scheme.passTypes as passType>${passType.text}&nbsp;</#list></span>
						  	<#if scheme.compoundContent.danMinHit?? && scheme.compoundContent.danMinHit lt danSize>
						  		<span style="margin-left:10px;">模糊设胆：胆码至少对${scheme.compoundContent.danMinHit}场</span>
						  	</#if></br>
	此方案发起时间：<#if (scheme.createTime)??><span class="red">${scheme.createTime?string("MM-dd HH:mm:ss")}</span></#if> 　认购截止时间：<#if (period.endedTime)??><span class="red">${period.endedTime?string("MM-dd HH:mm:ss")}</span></#if>  　方案编号：<span class="red">${scheme.schemeNumber}</span></span>    

</@override>

<@override name="content">
			<#if canViewDetail>
				<#if scheme.mode=='COMPOUND'>
					<#assign hasHandicap=scheme.playType=='SPF' />
					<#assign hasHalf=scheme.playType=='BQQSPF' />
					<#assign compoundContent=scheme.compoundContent />
					 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
		              <thead>
						 <tr class="v01tr">
						  <td>场次</td>
						  <td>主客队</td>
						  <td>比赛时间</td>
						  <#if hasHalf>
						  	<td>半场</td>
						  	<td>全场</td>
						  <#else>
						  	<td>比分</td>
						  </#if>
						  <#if hasHandicap><td>让球</td></#if>
						  <td>彩果</td>
						  <td>设胆</td>
						  <td>复式投注</td>
						  <td>SP</td>
						</tr>
					  </thead>
					  <tbody>
					    <#assign danSize=0 />
					    <#list compoundContent.items as matchItem>
					    	<#assign match=matchMap.get(matchItem.lineId) />
						    <tr class="v02tr">
					  		  <td>${match.lineId+1}</td>
					  		  <td>${match.homeTeamName!} <font color="red">VS</font> ${match.guestTeamName!}</td>
					  		  <td>${match.matchTime?string("MM-dd HH:mm (E)")}</td>
							  <#if hasHalf>
							  	<td><#if match.cancel>取消<#elseif match.fullHomeScore?? && match.fullGuestScore??>${match.halfHomeScore}:${match.halfGuestScore}</#if></td>
							  	<td><#if match.cancel>取消<#elseif match.fullHomeScore?? && match.fullGuestScore??>${match.fullHomeScore}:${match.fullGuestScore}</#if></td>
							  <#else>
							  	<td><#if match.cancel>取消<#elseif match.fullHomeScore?? && match.fullGuestScore??>${match.fullHomeScore}:${match.fullGuestScore}</#if></td>
							  </#if>
							  <#if hasHandicap><td>${match.handicap!}</td></#if>
							  <td>
							  	<#if match.cancel>
							  		取消
							  	<#elseif match.ended>
							  		<#assign rs=match.getResult(scheme.playType)!'' />
							  		 <#if rs!=''><font style="color:red;">${rs.text}</font></#if>
							  	</#if>
							  </td>
						  	  <td><#if matchItem.dan>是<#assign danSize=danSize+1 /><#else>否</#if></td>
					  	      <td>
					  	        <#assign prevHas=false />
					  	      	<#list scheme.playType.allItems as opItem>
					  	      		<#if matchItem.hasSelect(opItem)>
					  	      			<#lt/><#if prevHas> </#if>
					  	      			<#if rs??&& rs!=''&&rs.text??&&rs.text==opItem.text><font style="color:red;">${scheme.playType.text}:${opItem.text}&nbsp;</font>
					  	      			<#else>
					  	      				${scheme.playType.text}:${opItem.text}&nbsp;&nbsp;&nbsp;&nbsp;
					  	      			</#if><#rt/>
					  	      			<#assign prevHas=true />
					  	      		</#if>
					  	      	</#list>
					  	      </td>
					  		  <td>
					  		  	<#if match.cancel>
							  		1.0
							  	<#elseif match.ended>
							  		<#assign rs_sp=match.getResultSp(scheme.playType)!0/>
							  		<#if rs_sp??>#{rs_sp;M6}</#if>
							  	</#if>
					  		  </td>
							</tr>
					    </#list>
					    
					  </tbody>
					</table>
	  			<#elseif scheme.mode=='SINGLE'>
					<textarea name="content" cols="60" rows="10" readonly="readonly" style="border:1px solid #A7CAED;font-size:12px;">${(scheme.contentText)!}</textarea>
	  			</#if>
			<#else>
				${(scheme.secretType.secretName)!}
			</#if>
	</div>
</@override>
<@extends name="/WEB-INF/content/common/simple-scheme-showV1.ftl"/> 
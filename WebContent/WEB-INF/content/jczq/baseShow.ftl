<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]方案详细</title>
</@override>

<@override name="schemeInfo">
	<#if scheme.mode=='COMPOUND'>
		<#assign bestMinPrize=scheme.compoundContent.bestMinPrize>
		<#assign bestMaxPrize=scheme.compoundContent.bestMaxPrize>
	<#else>
		<#assign bestMinPrize=scheme.singleContent.bestMinPrize>
		<#assign bestMaxPrize=scheme.singleContent.bestMaxPrize>
		<#assign optimize=scheme.singleContent.optimize>
	</#if>
      过关方式：${scheme.passMode.text} <span class="red">${scheme.getPassTypeText()}</span>&nbsp;所选场次<span class="red">${scheme.getSelectedMatchKeys()?size}</span>场<br />
  <#if scheme.mode=='COMPOUND' || optimize?? && optimize>预计奖金： <span class="red">${bestMinPrize?string.currency}元-${bestMaxPrize?string.currency}元</span>（预计奖金为投注时的奖金，实际奖金以最终出票时为准）<br /></#if>
</@override>
<@override name="content">  
      <#if canViewDetail>
      	<#if scheme.mode=='COMPOUND' || optimize?? && optimize>
      	
          <table width="100%" style="TABLE-LAYOUT: fixed" border="0" cellspacing="0" cellpadding="0" class="vtable">
            <tr class="v01tr">
              <td width="50">赛事编号 </td>
              <td width="80">赛事 </td>
              <td width="35">开赛 </td>
              <td width="130">主队 VS 客队 </td>
              <td width="10">让球 </td>
              <td width="30">全场比分 </td>
              <td width="90">赛果 </td>
              <td>您的选择</td>
              <td width="10">胆码</td>
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
	              <#assign i=0>	 	              
	              <#assign EXY_rs = 0>                  
	              <#list matchItems as matchItem>
		            <#assign result = match.getResult(matchItem.playType)!''>
	              	<#if !match.cancel && match.ended>
		              	<#if scheme.playTypeWeb??&&scheme.playTypeWeb=="EXY">
		              		 <#if match.handicap==1>
		              		 	<#if result == "WIN">
		              		 		<#assign EXY_rs = 4>
		              		 		<#assign resultStr = '主不败' + '&nbsp;'>
		              		 	<#elseif result == "DRAW" && matchItem.playType == "SPF">
		              		 		<#assign EXY_rs = 1>
		              		 		<#assign resultStr = '主胜' + '&nbsp;'>		              		 				              		 		
		              		 	<#elseif result == "DRAW" && matchItem.playType == "RQSPF">
		              		 		<#assign EXY_rs = 2>
		              		 		<#assign resultStr = '客不败' + '&nbsp;'>
		              		 		
		              		 	<#elseif result == "LOSE">
		              		 		<#assign EXY_rs = 3>
		              		 		<#assign resultStr = '客胜' + '&nbsp;'>
		              		 	</#if>
		              		 <#elseif match.handicap==-1>
		              		 	
		              		 	<#if result == "LOSE">
		              		 		<#assign EXY_rs = 2>
		              		 		<#assign resultStr = '客不败' + '&nbsp;'>
		              		 	<#elseif result == "DRAW" && matchItem.playType == "SPF">
		              		 		<#assign EXY_rs = 2>
		              		 		<#assign resultStr = '客不败' + '&nbsp;'>
		              		 	<#elseif result == "DRAW" && matchItem.playType == "RQSPF">
		              		 		<#assign EXY_rs = 1>
		              		 		<#assign resultStr = '主胜' + '&nbsp;'>
		              		 	<#elseif result == "WIN">
		              		 		<#assign EXY_rs = 1>
		              		 		<#assign resultStr = '主胜' + '&nbsp;'>
		              		 	</#if>	
		              		 </#if>
		              	<#else>
		              		<#if result!=''>
		              			<#assign resultStr = resultStr + matchItem.playType.text + ": " + result.text + "</br>">		              				              			
		              		</#if>		              		
		              	</#if>	              		
		              	
		            </#if>
		            <#assign i=i+1>
		             <#assign rsClass=""> 
		            		 <#if match.handicap==1>
				              		<#if matchItem.value == 4>
				              			<#if EXY_rs == 3>
				              				<#assign rsClass="rc1">
				              			</#if>
				              			<#assign preStr = '客胜'>
				              		<#elseif matchItem.value == 1>
				              			<#if EXY_rs == 4>
				              				<#assign rsClass="rc1">
				              			</#if>
				              			<#assign preStr = '主不败'>
				              		</#if>
				              <#elseif match.handicap==-1>
				              		<#if matchItem.value == 4>
				              			<#if EXY_rs == 2>
				              				<#assign rsClass="rc1">
				              			</#if>
				              			<#assign preStr = '客不败'>
				              		<#elseif matchItem.value == 1>
				              			<#if EXY_rs == 1>
				              				<#assign rsClass="rc1">
				              			</#if>
				              			<#assign preStr = '主胜'>
				              		</#if>
				              </#if> 
		            <#if i==matchItems.size()>
		            	<#if scheme.playTypeWeb??&&scheme.playTypeWeb=="EXY">		            						            		            		
		            		<#assign selectedItemStr = selectedItemStr + scheme.playTypeWeb.text + ": <span class='${rsClass}'>" + preStr + matchItem.buildSelectedItemStrOfEXY() + '</span>'>		            		
		            	<#else>
		            		<#if result!=''>
		            			<#assign selectedItemStr = selectedItemStr + matchItem.playType.text + ": "  + matchItem.buildSelectedItemStr(result)>
		            		<#else>
		            			<#assign selectedItemStr = selectedItemStr + matchItem.playType.text + ": "  + matchItem.buildSelectedItemStr()>
		            		</#if>
		            	</#if>
		            	
		            <#else>	
		            	<#if scheme.playTypeWeb??&&scheme.playTypeWeb=="EXY">		            		
		            		<#assign selectedItemStr = selectedItemStr + scheme.playTypeWeb.text + ": <span class='${rsClass}'>" + preStr + matchItem.buildSelectedItemStrOfEXY() + '</span></br>'>
		            	<#else>
		            		<#if result!=''>
		            			<#assign selectedItemStr = selectedItemStr + matchItem.playType.text + ": "  + matchItem.buildSelectedItemStr(result) + '</br>'>
		            		<#else>
		            			<#assign selectedItemStr = selectedItemStr + matchItem.playType.text + ": "  + matchItem.buildSelectedItemStr() + '</br>'>
		            		</#if>
		            	</#if>
		            
		            </#if>
	              </#list>
	              <td><#if match.cancel><font color="blue">取消</font><#elseif resultStr?? && resultStr!=""><#if  scheme.playTypeWeb??&&scheme.playTypeWeb=="EXY">${resultStr}<#else>${resultStr?substring(0,resultStr?length-5)}&nbsp;</#if></#if></td>	              
	              <td style="WORD-WRAP: break-word">${selectedItemStr!}&nbsp;</td>	              
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
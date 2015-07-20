<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" class="kjtableb1">
      <tr align="center" class="kjtdtitle"  height="28">
        <td>序</td>
        <td>用户</td>
        <td>方案号</td>
        <td>类型</td>
        <td>注数</td>
        <td>方案金额</td>
        <td>税后奖金</td>
        <td>状态</td>
      </tr>
     
      <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
      <#list pagination.result as data>
      <tr align="center" class="kjtdwhitelist" height="31" onmouseover="this.className='kjtrhover'" onmouseout="this.className='kjtdwhitelist'" >
        <td>${data_index+1} </td>
        <td><a href="<@c.url value='/blog/${data.sponsorId}/' />" target="_blank">${data.sponsorName}</a></td>
        <td><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />">${data.schemeNumber}</a></td>
        <td>${data.passMode.text}${data.shareType.shareName}</td>
        <td> ${data.units} </td>
        <td>${data.schemeCost} </td>
        <td><span class="kjred">#{data.prizeAfterTax;M2}</span></td>
        <#assign url="">
        <#if playType??>
        <#assign playTypeOrdinal = playType.ordinal()>
        	<td><a href="#" class="kjgray">${data.state.stateName}</a>&nbsp;&nbsp;<a href="${base}/lottery/auto!autoNew.action?lotteryType=${lotteryType}&userId=${data.sponsorId}&lotteryPlayType=${playTypeOrdinal}">定制</a></td>
        <#else>	
        	 <td><a href="#" class="kjgray">${data.state.stateName}</a>&nbsp;&nbsp;<a href="${base}/lottery/auto!autoNew.action?lotteryType=${lotteryType}&userId=${data.sponsorId}>定制</a></td>        	
        </#if>
      </tr>
      </#list>
      <#else>
		<tr>
			 <td colspan="12" align="center" >暂无记录.</td>
		</tr>
      </#if>
       <tr>
            <td height="50" colspan="12" align="center" bgcolor="#FFFFFF">
                <#import "../../macro/pagination.ftl" as b />
                <@b.page {'ajaxContainer':'extra-data-container'} />
			    
			</td>
        </tr>
    </table>
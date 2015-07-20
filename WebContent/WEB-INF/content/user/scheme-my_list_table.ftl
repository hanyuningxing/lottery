 <table width="100%" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#dae8f5">
              <tbody>
                <tr height="23">
                  <td width="4%" height="35" align="center" bgcolor="#d5f6fd"><strong>序</strong></td>
                  <td width="9%" align="center" bgcolor="#d5f6fd"><strong>期号</strong></td>
                  <td width="14%" align="center" bgcolor="#d5f6fd"><strong>方案编号</strong></td>
                  <td width="9%" align="center" bgcolor="#d5f6fd"><strong>发起人</strong></td>
                  <td width="11%" align="center" bgcolor="#d5f6fd"><strong>投注时间</strong></td>
                  <td width="8%" align="center" bgcolor="#d5f6fd"><strong>方案金额</strong></td>
                  <td width="8%" align="center" bgcolor="#d5f6fd"><strong>购买金额</strong></td>
                  <td width="6%" align="center" bgcolor="#d5f6fd"><strong>状态</strong></td>
                  <td width="9%" align="center" bgcolor="#d5f6fd"><strong>出票</strong></td>
                  <td width="9%" align="center" bgcolor="#d5f6fd"><strong>方案奖金<br />
                  (元)</strong></td>
                  <td width="8%" align="center" bgcolor="#d5f6fd"><strong>我的奖金<br />
                  (元)</strong></td>
                  <td align="center" bgcolor="#d5f6fd"><strong>操作</strong></td>
                </tr>
                
                 <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
					<#list pagination.result as data>
					<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		                <tr>
		                  <td height="35" align="center" bgcolor="#FFFFFF">${data_index+1}</td>
		                  <td align="center" bgcolor="#FFFFFF">${data.periodNumber}</td>
		                  <td align="center" bgcolor="#FFFFFF"><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />" target="_blank">${data.schemeNumber}</a></td>
		                  <td align="center" bgcolor="#FFFFFF"><a href="<@c.url value='/blog/${data.sponsorId}/' />" target="_blank">${data.sponsorName}</a></td>
		                  <td align="center" bgcolor="#FFFFFF"><#if data.createTime??>${data.createTime?string("yyyy-MM-dd HH:mm")}</#if></td>
		                  <td align="center" bgcolor="#FFFFFF">#{data.zoomSchemeCost;M2}</td>
		                  <td align="center" bgcolor="#FFFFFF">#{(data.zoomMyCost!0);M2}</td>
		                  <td align="center" bgcolor="#FFFFFF">${data.schemeState.stateName}</td>
		                  <td align="center" bgcolor="#FFFFFF">${data.schemePrintState.stateName!}</td>
		                  <td align="center" bgcolor="#FFFFFF">
		                  		<#if data.winningUpdateStatus??&&(data.winningUpdateStatus=='WINNING_UPDATED'||data.winningUpdateStatus=='PRICE_UPDATED')>
					                <#if data.prize?? && data.prize!=0><span style="color:red">#{data.prize;M2}</span><#else>&nbsp;</#if>
					              <#else>
					                <#if data.prize?? && data.prize!=0><span style="color:red">#{data.prize;M2}</span><#else>&nbsp;</#if>
					           </#if> 
		                  </td>
		                  <td align="center" bgcolor="#FFFFFF">
		                  		 <#if data.winningUpdateStatus??&&(data.winningUpdateStatus=='WINNING_UPDATED'||data.winningUpdateStatus=='PRICE_UPDATED')>
						                <#if data.myBonus??><span style="color:red">#{data.myBonus;M2}</span><#else>&nbsp;</#if>
						              <#else>
						                <#if data.myBonus??><span style="color:red">#{data.myBonus;M2}</span><#else>&nbsp;</#if>
						           </#if>   
		                  </td>
		                  <td align="center" bgcolor="#FFFFFF"><a href="#"><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />" target="_blank">查看</a></a></td>
		                </tr>
                </#list>
				  <#else>
					<tr>
					  <td colspan="12" align="center" >暂无记录.</td>
					</tr>
				  </#if>
	</tbody>
                <tr>
                  <td height="50" colspan="12" align="center" bgcolor="#FFFFFF">
			         <#import "../../macro/pagination.ftl" as b />
			         <@b.page {'ajaxContainer':'extra-data-container'} />
					</td>
                </tr>
              </tbody>
        </table>
	        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zjmxtab">
            </table>
	 
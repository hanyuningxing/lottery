  <div class="table">
	<!--合买表格-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" class="hmtableb1">
      <tr align="center" class="hmtdtitle"  height="28">
        <td>序</td>
        <td>方案编号</td>
        <td>发起人</td>
        <td>投注内容</td>
        <td>总金额</td>
        <td>最低认购</td>
        <td>进度</td>
        <td>剩余金额</td>
        <td>购买</td>
        <td>详细</td>
      </tr>
      <#if pagination.result?? && pagination.result?size gt 0>
      <#list pagination.result as data>				
	      <tr align="center" class="hmtdwhitelist" height="31" onmouseover="this.className='hmtrhover'" onmouseout="this.className='hmtdwhitelist'" >
	        <td>${data_index+1} </td>
	        <td><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html"/>" target="_blank">${data.schemeNumber}</a></td>
	        <td><a href="${base}/blog/${data.sponsorId}/" target="_blank">${data.sponsorName}</a></td>
	        <td>
	        	<#if data.secretType=="FULL_PUBLIC">
						<#if data.mode=="SINGLE">						
					        <#if data.uploaded>
					        	<#if (data.lotteryType=='JCZQ' || data.lotteryType=='JCLQ' || data.lotteryType=='DCZC') && data.getSingleContent().optimize>
			                 		<a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html"/>">查看(优化)</a>
			                 	<#else>
			                 	 	<a target="_blank" href="<@c.url value="/${data.lotteryType.key}/scheme!download.action?schemeNumber=${data.schemeNumber}"/>">下载方案内容</a>
			                 	</#if>
			  				<#elseif loggedUser?? && loggedUser.id == data.sponsorId>
			  					<a href="<@c.url value="/${data.lotteryType.key}/scheme!editUpload.action?schemeNumber=${data.schemeNumber}" />">上传方案内容</a>
			  				<#else>
			  					待上传中... ...
			  				</#if>
						<#else>
	            			<#assign schemeContent = data.compoundContentTextForList!>
	            			<#if schemeContent?length gt 0 && schemeContent?length lt 18>
	            				${schemeContent!}
		                  	<#else>
		                  		<a target="_blank" href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html"/>">查看</a>
		                    </#if>
						</#if>
				<#else>
		        	${data.secretType.secretName}
				</#if>
	        </td>
	        <td><span class="red">${data.schemeCost}</span></td>
	        <td><#if data.minSubscriptionCost??>#{data.minSubscriptionCost;M2}<#else>不限</#if></td>
	        <td>
	        	<#assign baodiProgressRate=data.baodiProgressRate!0 />
            	<span class="jindu">#{data.progressRate!0;M2}%</span>
            	<#if baodiProgressRate gt 0><br /><span class="greenchar">+#{baodiProgressRate;M2}%</span><span style="color:red">(保)</span></#if>
	        </td>
	        <td><span class="green">#{(data.remainingCost!0);M2}</span></td>
	       	<td><#if data.state=='UNFULL'><input id="subscribe_${data.id}" class="inputw80" type="text" maxlength="6" onkeydown="number_check(this,event,0)" oncontextmenu="return false;" autocomplete="off" />
	       		<a href="#" onclick="submitSubscribeReq(${data.id},'${data.lotteryType.key}');return false"><img border="0" align="absmiddle" src="<@c.url value="/V1/images/bugbti.gif" />"/></a><#else>${data.state.stateName}</#if></td>	      	    
	        <td><a href="<@c.url value="/${data.lotteryType.key}/scheme-${data.schemeNumber}.html" />" target="_blank">详细</a></td>
	     </tr>
     </#list>
     <#else>
		<tr align="center" class="hmtdwhitelist" height="31" onmouseover="this.className='hmtrhover'" onmouseout="this.className='hmtdwhitelist'" >
			<td colspan="9">暂无记录.</td>
		</tr>
	 </#if>
    </table>
  <!--   <div class="cb pagelist" align="center"> <a href="#" class="pagenum">首页</a> <a href="#" class="pagenum">上一页</a> <a href="#" class="pagenum">1</a> <a href="#" class="pagenum">2</a> <a href="#" class="pagenum">3</a> <a href="#" class="pagenum">4</a> <a href="#" class="pagenum">5</a> <a href="#" class="pagenum">6</a> <a href="#"  class="nowpage">7</a> <a href="#" class="pagenum">8</a>... <a href="#" class="pagenum">25</a> <a href="#" class="pagenum">下一页</a> <a href="#" class="pagenum">尾页</a></div>-->
  </div>
    <span id="extra-count" style="display:none">${pagination.totalCount!0}</span>
	<div class="all10px" align="center">
	<#import "../../macro/pagination.ftl" as b />
	</div>
  	<@b.page />
    </div> 
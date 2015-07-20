 <table width="100%" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#dae8f5">
          <tbody>
            <tr height="23">
              <td width="14%" height="35" align="center" bgcolor="#d5f6fd"><strong>方案编号</strong></td>
              <td width="15%" align="center" bgcolor="#d5f6fd"><strong>彩种</strong></td>
              <td width="11%" align="center" bgcolor="#d5f6fd"><strong>期号</strong></td>
              <td width="11%" align="center" bgcolor="#d5f6fd"><strong>玩法</strong></td>
              <td width="12%" align="center" bgcolor="#d5f6fd"><strong>投注时间</strong></td>
              <td width="10%" align="center" bgcolor="#d5f6fd"><strong>方案奖金<br />
              (元)</strong></td>
              <td width="8%" align="center" bgcolor="#d5f6fd"><strong>状态</strong></td>
              <td align="center" bgcolor="#d5f6fd"><strong>操作</strong></td>
            </tr>
            
            <#if pagination?? && pagination.result?? && pagination.result?size gt 0>
				<#list pagination.result as data>
	                <tr>
	                  <td height="35" align="center" bgcolor="#FFFFFF"><a href="<@c.url value="/${data.lotteryType.key}/scheme!showTemp.action?id=${data.id}" />">${data.id}</a></td>
	                  <td align="center" bgcolor="#FFFFFF">${data.lotteryType.lotteryName}</td>
	                  <td align="center" bgcolor="#FFFFFF">${data.periodNumber}</td>
	                  <td align="center" bgcolor="#FFFFFF">${data.mode.modeName}${data.shareType.shareName}</td>
	                  <td align="center" bgcolor="#FFFFFF">${data.createTime}</td>
	                  <td align="center" bgcolor="#FFFFFF">${data.schemeCost}</td>
	                  <td align="center" bgcolor="#FFFFFF">${data.state.stateName}</td>
	                  <td align="center" bgcolor="#FFFFFF"><a href="<@c.url value="/${data.lotteryType.key}/scheme!showTemp.action?id=${data.id}" />">查看</a></td>
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
			        <@b.page />
					</td>
                </tr>
            
          </tbody>
    </table>
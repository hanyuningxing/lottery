<table width="100%" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
	      <tr>
            <td width="8%" height="30" align="center" bgcolor="#D5F6FD"><strong>订单号</strong></td>
	        <td width="20%" align="center" bgcolor="#D5F6FD"><strong>提款时间</strong></td>
	        <td width="22%" align="center" bgcolor="#D5F6FD"><strong>提款到</strong></td>
	        <td width="10%" align="center" bgcolor="#D5F6FD"><strong>提款金额</strong></td>
	        <td width="8%" align="center" bgcolor="#D5F6FD"><strong>手续费</strong></td>
	        <td width="10%" align="center" bgcolor="#D5F6FD"><strong>实际到帐</strong></td>
	        <td width="22%" align="center" bgcolor="#D5F6FD"><strong>状态</strong></td>
          </tr>
        <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
	      <tr style="font-size:14px; font-family:Arial, Helvetica, sans-serif;">
            <td height="30" align="center" bgcolor="#FFFFFF">${data.id}</td>
	        <td align="center" bgcolor="#FFFFFF">${data.sendtime!}</td>
	        <td align="center" bgcolor="#FFFFFF">${data.bankAccounts!}</td>
	        <td align="center" bgcolor="#FFFFFF" class="blue_0091d4"><strong class="yel_F60">#{(data.money!0);M2}</strong></td>
	        <td align="center" bgcolor="#FFFFFF" class="bro_6D4F42"><strong class="blue_0091d4">#{(data.commission!0);M2}</strong></td>
	        <td align="center" bgcolor="#FFFFFF" class="STYLE1">#{(data.money - data.commission!0);M2}</td>
	        <td align="center" bgcolor="#FFFFFF" class="green_419900"><#if data??&&data.state??>${data.state.stateName!}</#if></td>
          </tr>
	     </#list>
		  <#else>
			<tr>
			  <td colspan="7" align="center" >暂无记录.</td>
			</tr>
		  </#if>
          <tr>
          	<td height="50" colspan="7" align="center" bgcolor="#FFFFFF">
			    <#import "../../macro/pagination.ftl" as b />
			    <@b.page {'ajaxContainer':'extra-data-container'} />
          	</td>
          </tr>
        </table>
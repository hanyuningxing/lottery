<#assign menu="lottryUser" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem="log" />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>日志管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
</head>

<@com.displaySimpleNav menuObj menuItemObj true>日志管理</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">日志管理</span></div>
</div>
<div>
  <table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="left">
                        <tr class="trlbrown">
						  <td colspan=2>日志信息</td>
					    </tr>
					    <tr bgcolor="#F5f5f5">
			              <td bgcolor="#F5f5f5" align="center" width="20%">彩种：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			              <#if eventLog??&&eventLog.lotteryType??>${eventLog.lotteryType.lotteryName!}</#if>
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">期号：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <#if eventLog.periodNumber??>${eventLog.periodNumber!}</#if>
			              </span></td>
			            </tr>
						<tr bgcolor="#F5f5f5">
			              <td align="center">期号id：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                     <#if eventLog.periodId??>${eventLog.periodId!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">地址：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                     <#if eventLog.logType??>${eventLog.eventLogType.text!}</#if>
			             </span></td>
			            </tr>
						<tr bgcolor="#F5f5f5">
			              <td align="center">操作时间：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                     <#if eventLog??&&eventLog.startTime??>
			                     ${eventLog.startTime?string("yyyy-MM-dd HH:mm")}
			                     </#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">结束时间：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if eventLog??&&eventLog.doneTime??>
			                    	${eventLog.doneTime?string("yyyy-MM-dd HH:mm")}
			                    </#if>
			                    
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">是否成功：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                     <#if eventLog??&&eventLog.normalFinish??&&eventLog.normalFinish>是<#else>否</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">操作员：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                     <#if eventLog.operator??>${eventLog.operator!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">操作ip：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if eventLog.ip??>${eventLog.ip!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">详情：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				                   <#if eventLog.msg??>${eventLog.msg!}</#if>   
				           </span></td>
			            </tr>
</table>
  
</div>
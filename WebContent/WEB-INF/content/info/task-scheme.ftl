<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
						      <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5">彩票号</td>
						               <td bgcolor="#F5f5f5">订单号</td>
						               <td bgcolor="#F5f5f5">金额</td>
						               <td bgcolor="#F5f5f5">彩票状态</td>
						               <td bgcolor="#F5f5f5">打印状态</td>
						               <td bgcolor="#F5f5f5">发起时间</td>
						               <td bgcolor="#F5f5f5">截止时间</td>
						      </tr>
					           <#if schemeList??>
						          <#list schemeList as data>
						              <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5"><a href="<@c.url value="/admin/lottery/${data.lotteryType.key}/scheme!show.action?schemeId=${data.id}"/>" target="_blank">${data.schemeNumber}</a></td>
						               <td bgcolor="#F5f5f5"><#if data.orderId??>${data.orderId!}</#if></td>
						               <td bgcolor="#F5f5f5"><#if data.schemeCost??>${data.schemeCost!}</#if></td>
						               <td bgcolor="#F5f5f5"><#if data.state??>${data.state.stateName!}</#if></td>
						               <td bgcolor="#F5f5f5"><#if data.schemePrintState??>${data.schemePrintState.stateName!}</#if></td>
						               <td bgcolor="#F5f5f5"><#if data.createTime??>${data.createTime?string("MM-dd HH:mm:ss")}</#if></td>
						               <td bgcolor="#F5f5f5"><#if data.commitTime??>${data.commitTime?string("MM-dd HH:mm:ss")}</#if></td>
						     		 </tr>
							       </#list>
						    	</#if>
</table>
				 	
							          
				       
				       
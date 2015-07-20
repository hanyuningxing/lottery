<#if newsInfoDataList?? && newsInfoDataList?size gt 0>
	<#list newsInfoDataList as data>
			<#if data.titleLink??>
				<#if data_index==0>
			    	<tr>
						<td width="75%" height="30"><a href="${data.titleLink!""}" target="_blank">${data.shortTitle!} </a><img src="../../../pages/images/images_zxpd/jxz.gif" /></td>
						<td width="25%" height="30">${data.createTime?string("yyyy-MM-dd")}</td>
					</tr>
				<#else>
					<tr>
						<td height="30"><a href="${data.titleLink!""}" target="_blank">${data.shortTitle!} </a> </td>
						<td height="30">${data.createTime?string("yyyy-MM-dd")}</td>
					</tr>
				</#if>
          	<#else>
          		<#if data_index==0>
			    	<tr>
						<td width="75%" height="30"><a href="/info/${data.id!}.html" target="_blank">${data.shortTitle!} </a><img src="../../../pages/images/images_zxpd/jxz.gif" /></td>
						<td width="25%" height="30">${data.createTime?string("yyyy-MM-dd")}</td>
					</tr>
				<#else>
					<tr>
						<td height="30"><a href="/info/${data.id!}.html" target="_blank">${data.shortTitle!} </a> </td>
						<td height="30">${data.createTime?string("yyyy-MM-dd")}</td>
					</tr>
				</#if>	
     	    </#if>
	</#list>
</#if>
        
		
		
		
							
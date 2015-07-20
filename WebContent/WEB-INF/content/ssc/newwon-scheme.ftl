		<#if wonSchemeList??&& (wonSchemeList![])?size gt 0>  
	 		<#list wonSchemeList as data>	
				<li><a href="#">恭喜 ${data.sponsorName?substring(0,2)}*** 喜中${data.betTypeString} 奖金<span>${data.prize}</span>元</a></li>
			</#list>									
		</#if>
				
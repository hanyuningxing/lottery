<#assign menu_type='result' />
<@override name="title">
	<title>${lotteryType.lotteryName}开奖</title>
</@override>
<@override name="style">
	<link href="<@c.url value= "/styles/trade/css/trade_public.css"/>" rel="stylesheet" type="text/css" />
</@override>
<@override name="content">
	<div class="hemaint k3px">
		<@block name="query">
			<table width="98%" cellspacing="0" cellpadding="0" border="0" class="hemain_filter">
				<tr>
				  <td align="left">
				    <#assign playTypeParameter = '' />
				    <#if playType??>
						<#assign playTypeParameter = '&playType=${playType}' />
				    </#if>
				  	<#list [20,30,50] as rs_count>
				  		<input type="button" value="近${rs_count}期" name="twenty" class="bd_upload" onclick="location.href='<@c.url value= "/${lotteryType.key}/result.action?count=${rs_count}${playTypeParameter}"/>';"/>
				  	</#list>
				  </td>
				  <td align="right">&nbsp;</td>
				</tr>
			</table>
		</@block>
		<@block name="list_table"></@block>
	</div>
</@override>
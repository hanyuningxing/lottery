<head>
	<meta name="decorator" content="none" />
</head>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablebgwhite">
  <tbody>
    <#assign startIndex=(pagination.pageNo-1)*pagination.pageSize />
	<#list pagination.result![] as data>
	    <tr align="center" valign="middle" >
	      <td align="left">
	      	<#list data.matchItemList as matchItem>
      			<#assign match=matchMap.get(matchItem.matchKey) />
  				<#if match.cancel>
  					<#assign r=-1 />
  				<#elseif match.ended>
  					<#assign rsOp = match.getResult(scheme.playType)!'' />
  					<#if rsOp != ''>
  						<#assign r=rsOp.ordinal() />
  					<#else>
  						<#assign r=-2 />
  					</#if>
  				<#else>
  					<#assign r=-2 />
  				</#if>
	      		
	      		<#if data.printed>
	      			<#assign itemAwardMap=data.awardMap.get(matchItem.matchKey)!'' />		
	      		<#else>
	      			<#assign itemAwardMap='' />		
	      		</#if>
	      		
	      		${match.matchKeyText}
	      		<#list data.playType.allItems as item>
	      			<#if matchItem.hasSelect(item)>
	      				<#if itemAwardMap!=''>
	      					<#assign award=itemAwardMap.get(item.value) />
	      				<#else>
	      					<#assign award=0 />
	      				</#if>
	      				<span <#if r==-1 || r==item.ordinal()>style="color:red;font-weight:bold"</#if> >${item.text}[<#if data.printed>${(award!0)?string("0.00")}<#else>---</#if>]</span>
	      			</#if>	
	      		</#list>
	      		<br/>
	      	</#list>
	      </td>
	    </tr>
    </#list>
  </tbody>
</table>
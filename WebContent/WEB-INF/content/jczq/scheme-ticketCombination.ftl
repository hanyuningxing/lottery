<head>
	<meta name="decorator" content="none" />
</head>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablebgwhite">
  <thead>
    <tr align="center" valign="middle" class="bglan boldchar" height="23" >
      <td witdh="30">注号</td>
      <td>票面固定奖金详情</td>
      <td witdh="40">状态 </td>
      <td witdh="40">过关 </td>
      <td witdh="40">倍数</td>
      <td witdh="120">出票奖金范围</td>
      <td witdh="120">税后奖金</td>
      <td witdh="40">命中</td>
    </tr>
  </thead>
  <tbody>
    <#assign startIndex=(pagination.pageNo-1)*pagination.pageSize />
	<#list pagination.result![] as data>
	    <tr align="center" valign="middle" class="<#if data_index%2==0>bgbai<#else>bghui</#if>" >
	      <td>${startIndex+data_index+1}</td>
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
	      <td><#if data.printed>已出票<#else>正在获取sp值...</#if></td>
	      <td>${data.passType.text}</td>
	      <td>${data.multiple}</td>
      	  <td><#if data.printed >#{(data.forecastMinPrize!0);M2}~#{(data.forecastMaxPrize!0);M2}</#if></td>
      	  <td style="color:red;"><#if data.printed && data.won?? && data.won && data.totalPrizeAfterTax??>#{data.totalPrizeAfterTax;M2}</#if></td>
      	  <td><#if data.printed && data.won??><#if data.won><span style="color:red;">√</span><#else>×</#if></#if></td>
	    </tr>
    </#list>
  </tbody>
</table>

<span id="extra-count" style="display:none">${pagination.totalCount!0}</span>
<div class="all10px" align="center">
<#import "../../macro/paginationTicket.ftl" as b />
</div>
<@b.page {'ajaxContainer':'extra-ticket-container'} />
<head>
	<meta name="decorator" content="none" />
</head>

<#assign canCancelScheme=canCancelScheme!false />
<#assign canCancelSubscription=canCancelSubscription!false />
<#if loggedUser??>
	<#assign loggedUser_id=loggedUser.id />
<#else>
	<#assign loggedUser_id=-1 />
</#if>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #E0E1E2;" class="cb">
    <tr bgcolor="#F7FAFD">
      <td>用户名</td>
      <td>认购金额</td>
      <td>拥有股份</td>
      <td>购买时间</td>
      <td>奖金</td>
      <td>操作</td>
    </tr>
    <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trClass="bgbai" /><#else><#assign trClass="bghui" /></#if>
		<tr valign="middle" class="${trClass}">
		  <td>${data.userName} </td>
	      <td>#{data.cost;M2}</td>
	      <td>#{(data.cost*100/scheme.schemeCost);M4}%</td>
	      <td>${data.createTime?string("yyyy-MM-dd HH:mm")}</td>
	      <td class="redredchar"><#if data.bonus??>#{data.bonus;M2}</#if></td>
	      <td class="redredchar">
	      	<#if data_index==0>
				<#if canCancelScheme && loggedUser_id==data.userId>
					<a href="<@c.url value="/${scheme.lotteryType.key}/scheme!cancel.action?id=${scheme.id}" />" onclick="if(window.confirm('您确定要撤销方案？')){commonSendAjaxReq(this);}return false;" class="btn">撤销方案</a>
				</#if>
			<#else>
				<#if canCancelSubscription && scheme.canCancelSubscription && data.canCancel && loggedUser_id==data.userId>
					<a href="<@c.url value="/${data.lotteryType.key}/scheme!cancelSubscription.action?id=${data.id}" />" onclick="if(window.confirm('您确定要撤销认购？')){commonSendAjaxReq(this);}return false;"  class="btn">撤销认购</a>
				</#if>
			</#if>
	      </td>
		</tr>
		</#list>
	<#else>
		<tr>
			<td colspan="6">暂无记录.</td>
		</tr>
	</#if>
</table>
<span id="extra-count" style="display:none">${pagination.totalCount!0}</span>
<div class="all10px" align="center">
<#import "../../macro/pagination.ftl" as b />
</div>
<@b.page {'ajaxContainer':'extra-data-container'} />
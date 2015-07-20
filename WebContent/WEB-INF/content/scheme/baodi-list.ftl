<head>
	<meta name="decorator" content="none" />
</head>
<#assign canTransfer=canTransfer!false/>
<#if loggedUser??>
	<#assign loggedUser_id=loggedUser.id />
<#else>
	<#assign loggedUser_id=-1 />
</#if>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #E0E1E2;" class="cb">
    <tr bgcolor="#F7FAFD">
      <td>序号</td>
      <td>用户名</td>
      <td>保底金额</td>
      <td>保底时间</td>
      <td>操作</td>
    </tr>
    <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trClass="bgbai" /><#else><#assign trClass="bghui" /></#if>
		<tr valign="middle" class="${trClass}">
		  <td>${data_index+1}</td>
	      <td>${data.userName}</td>
	      <td>#{data.cost;M2}</td>
	      <td>${data.createTime?string("MM-dd HH:mm")}</td>
	      <td class="redboldchar">
	      	<#if canTransfer && loggedUser_id==data.userId>
				<a href="<@c.url value="/${data.lotteryType.key}/scheme!transferSubscription.action?id=${data.id}" />" onclick="if(window.confirm('您确定要将保底转为认购？')){commonSendAjaxReq(this);}return false;" class="btn">保底转认购</a>
			</#if>
	      </td>
		</tr>
		</#list>
	<#else>
		<tr>
			<td colspan="5">暂无记录.</td>
		</tr>
	</#if>
</table>

<span id="extra-count" style="display:none">${pagination.totalCount!0}</span>
<div class="all10px" align="center">
<#import "../../macro/pagination.ftl" as b />
</div>
<@b.page {'ajaxContainer':'extra-data-container'} />
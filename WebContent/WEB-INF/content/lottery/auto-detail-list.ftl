<head>
	<title>自动跟单</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="auto" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'auto'/>
        <#include "../user/left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright"> 
	  	  <#include "../user/user-loginInfo.ftl">
	  
    <div class="border04">
      <div class="yhzxdivtit white_FFF_14">跟单记录</div>
      <div style="color:#333; padding:10px 0;">
    <table  width="100%" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#dae8f5">
      <tr class="center_tablegray" align="center">
        <td bgcolor="#d5f6fd" width="28" height="28"><strong>序</strong> </td>
        <td bgcolor="#d5f6fd"><strong>跟单人</strong></td>
		<td bgcolor="#d5f6fd"><strong>期号</strong></td>
		<td bgcolor="#d5f6fd"><strong>彩种</strong></td>
		<td bgcolor="#d5f6fd"><strong>方案号</strong></td>
		<td bgcolor="#d5f6fd"><strong>跟单状态</strong></td>
		<td bgcolor="#d5f6fd"><strong>跟单金额</strong></td>
		<td bgcolor="#d5f6fd"><strong>创建时间</strong></td>
		<td bgcolor="#d5f6fd"><strong>备注</strong></td>
      </tr>
       <#if pagination.result?? && pagination.result?size gt 0>
			<#list pagination.result as autodetail>
			<#if autodetail_index%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		        <tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
					<td>${autodetail.followOrderId!}</td>
					<td>${autodetail.sponsorUserName!}</td>
					<td>${autodetail.periodNumber!}</td>
					<td>${autodetail.lotteryType.lotteryName!}</td>
					<td>${autodetail.lotteryType.schemeNumberPrefix!}${autodetail.schemeId!}</td>
					<td><#if autodetail??&&autodetail.state??>${autodetail.state.stateName!}</#if></td>
					<td>${autodetail.followCost!}</td>
					<td><#if autorder??&&autodetail.createTime??>${autodetail.createTime?string("yy-MM-dd HH:mm")}</#if></td>
					<td>${autodetail.remark!}</td>
				</tr>
			</#list>
	    <#else> 
				<tr>
					<td align="center" colspan="9">无记录</td>
				</tr>
		</#if>
		</table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
    <div class="kong10"></div>
  </div>
</div>


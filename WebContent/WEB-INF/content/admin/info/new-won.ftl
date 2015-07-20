<#--最新中奖信息-->
<#if schemeList??>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs_td title_r">
  <tr>
    <td class="line_title">会员名</td>
    <td class="line_title">彩种 </td>
    <td class="line_title">中奖金额</td>
    <td class="line_title">跟单</td>
  </tr>
<#list schemeList as data>
	<#if data_index gt 5><#break/></#if>
	<#if data.commitTime??>
	  <!---->
	  <tr>
	    <td>${data.sponsorName!}</td>
	    <td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
	    <td>${data.prize!}元</td>
	    <td><a href="${base}/lottery/auto!autoNew.action?autoForm.sponsorUserName=${data.sponsorName!}" target="_blank">定制跟单</a></td>
	  </tr>
  	<#else>
	  <tr>
	    <td>${data.userName!}</td>
	    <td><#if data.lottery??>${data.lottery.lotteryName!}</#if></td>
	    <td>${data.totalPrize!}元</td>
	    <td></td>
	  </tr>  	
  	</#if>
</#list>
 </table>
 </#if>
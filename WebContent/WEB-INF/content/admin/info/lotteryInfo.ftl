<#--彩票日历-->
<#if issueList ??>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs_td title_r">
  <tr>
 	<td class="line_title">彩种</td>
    <td class="line_title">期号 </td>
    <td class="line_title">单式截止</td>
    <td class="line_title">复式截止</td>
  </tr>
<#list issueList as data>
  <tr>
    <td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
    <td>${data.periodNumber!}期 </td>
    <td><#if data.singleShareEndInitTime??>${data.singleShareEndInitTime?string("yyyy/MM/dd HH:mm")}</#if></td>
    <td><#if data.compoundShareEndInitTime??>${data.compoundShareEndInitTime?string("yyyy/MM/dd HH:mm")}</#if></td>
  </tr>
</#list>
  </table>
 </#if>
<#--热门方案-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs_td">
             <tr class="height weight700">
              <td>用户名</td>
              <td>金额</td>
              <td>进度</td>
              <td>合买</td>
             </tr>
             <#list data as data>
	             <tr>
	                <td>${data.sponsorName!}</td>
	                <td>#{data.schemeCost;M0}元 </td>
	                <td>#{data.progressRate;M0}%</td>
	                <td><a href="${base}/${lotteryType.key}/scheme.action!show.action?schemeNumber=${data.schemeNumber}" targer="_blank">参与</a></td>
	             </tr>
            </#list>
</table>
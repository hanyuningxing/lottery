<#--热门方案-->
<style>
table {
	text-align:center;
}
table .head {
	height:25px;
	line-height:25px;
	vertical-align:middle;
	background-color:#E6EFFA;
}
table tbody td{
	height:25px;
	line-height:25px;
	vertical-align:middle;
}
</style>
<script type="text/javascript">
	$(function(){
		$("table.main_table tbody>tr:odd").addClass("row0");
		$("table.main_table tbody>tr:even").addClass("row1");
	});
</script>
<table width="100%" border="1" cellpadding="0" cellspacing="0" class="main main_table" style="border-collapse:collapse;" bordercolor="#A7CAED">
    <thead  class="head">
    <tr>
        <td>序号</td>
        <td>发起人</td>
        <td>战绩</td>
        <td>彩种</td>
        <td>期号</td>
        <td>总金额</td>
        <td>进度</td>
        <td>合买</td>
      </tr>
    </thead>
    <tbody>
     <#list data as data>
      <tr>
        <td>${data.schemeNumber!}</td>
        <td>${data.sponsorName!}</td>
        <td>战绩</td>
        <td>${lotteryType.lotteryName!}</td>
        <td>${data.periodNumber!}</td>
        <td>#{data.schemeCost;M0}元</td>
        <td>#{data.progressRate;M0}%</td>
        <td><input name="canyu" type="button" value="参与" class="btn color_fff" onclick="window.location.href='${base}/${lotteryType.key}/scheme-${data.schemeNumber}.html'"/></td>
      </tr>
     </#list>
   </tbody>
</table>
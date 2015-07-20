<head>
<title>${lotteryType.lotteryName!}销售统计 - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="COUNTER"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
	<script type="text/javascript">
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
	</script>
</head>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → 
	<a href="${base}/admin/lottery/${lotteryType.key!}/period!list.action">${lotteryType.lotteryName!}</a> → 
	<a href="${base}/admin/counter/${lotteryType.key!}">销售统计</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">${lotteryType.lotteryName!}销售统计</span>
        </div>
     </div>
</div>
<div>
<form name="queryForm" action="<@c.url value='/admin/counter/${lotteryType.key!}!count.action' />" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">&nbsp;
	       期号：<input id="periodNumber" type="text" name="periodNumber" value="${periodNumber!}" size="10"/>&nbsp;
	   <#if lotteryType=="DCZC">
  	     <#assign playTypeList=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()") />
	  	  	<select name="playType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list playTypeList as dczcplayType>
	  	  	  <option value="${dczcplayType!}" <#if playType?? && playType==dczcplayType>selected="selected"</#if>>${dczcplayType.text}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
	<input type="submit" name="submit" value=" 统计 "/>
</div>


</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22">序号</td>
		<td>日期</td>
		<td>总方案数</td>
		<td>成功方案数</td>
		<td>总中奖方案数</td>
		<td>总销量(元)</td>
		<td>税前总奖金</td>
		<td>税后总奖金</td>
	</tr>
	<#if counterList??>
		<#assign size = counterList?size>
		<#list counterList as data>
			<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
			<#if data_index == (size - 1)>
			<tr class="trw">
				<td colspan="2"  class="trlbrown" height="25">合计：</td>
				<td>#{data.total;M2}</td>
				<td>#{data.totalSuccess;M2} </td>
				<td>#{data.totalWon;M2}</td>
				<td>#{data.totalSales;M2}</td>
				<td>#{data.totalPrice;M2}</td>
				<td>#{data.prizeAfterTax;M2}</td>
			</tr>
			<#else>
			<tr class="${trColor}">
				<td height="22">${data_index + 1}</td>
				<td><#if data.day??>${data.day?string("yyyy-MM-dd")}</#if></td>
				<td>#{data.total;M2}</td>
				<td>#{data.totalSuccess;M2} </td>
				<td>#{data.totalWon;M2}</td>
				<td>#{data.totalSales;M2}</td>
				<td>#{data.totalPrice;M2}</td>
				<td>#{data.prizeAfterTax;M2}</td>
			</tr>
			</#if>
		</#list>
	<#else> 
	    <tr>
	      <td class="trw" align="center" colspan="8">无记录</td>
	    </tr>
	</#if>
</table>
</div>
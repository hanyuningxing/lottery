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
	<a href="${base}/admin/lottery/keno/${lotteryType.key!}/sales-manager!list.action">${lotteryType.lotteryName!}</a> → 
	<a href="${base}/admin/counter/${lotteryType.key!}.action">销售统计</a>  
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
	开始时间：<input id="beginTime" type="text" name="beginTime" value="<#if beginTime??>${beginTime?string('yyyy-MM-dd')}</#if>" size="12" />
			    	<img onclick="WdatePicker({el:$('beginTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
	
	结束时间：<input id="endTime" type="text" name="endTime" value="<#if endTime??>${endTime?string('yyyy-MM-dd')}</#if>" size="12"/>
		    	<img onclick="WdatePicker({el:$('endTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    	
		 <#if lotteryType=="SSC">
  	     <#assign playTypeList=stack.findValue("@com.cai310.lottery.support.ssc.SscPlayType@values()") />
	  	  	<select name="playType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list playTypeList as sscPlayType>
	  	  	  <option value="${sscPlayType!}" <#if playType?? && playType==sscPlayType>selected="selected"</#if>>${sscPlayType.typeName}</option>
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
				<td>${data.total!}</td>
				<td>${data.totalSuccess!}</td>
				<td>${data.totalWon!}</td>
				<td>${data.totalSales!}</td>
				<td>${data.totalPrice!}</td>
				<td>${data.prizeAfterTax!}</td>
			</tr>
			<#else>
			<tr class="${trColor}">
				<td height="22">${data_index + 1}</td>
				<td><#if data.day??>${data.day?string("yyyy-MM-dd")}</#if></td>
				<td>${data.total!}</td>
				<td>${data.totalSuccess!}</td>
				<td>${data.totalWon!}</td>
				<td>${data.totalSales!}</td>
				<td>${data.totalPrice!}</td>
				<td>${data.prizeAfterTax!}</td>
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
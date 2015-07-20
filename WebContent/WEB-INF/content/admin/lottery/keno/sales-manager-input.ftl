<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery!}"/>
<script type='text/javascript' src='${base}/js/My97DatePicker/WdatePicker.js'></script>
</head>
<div class="nowpalce">
	<div style="float:left;">
		您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/keno/${lottery.key}/sales-manager!list.action">${lottery.lotteryName!}</a> → <span style="color:red;">编辑数据</span>
	</div>
</div>
<div>
<form name="issueForm" action="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!updateResults.action" method="post">
<input type="hidden" name="issueData.id" value="${issueData.id!}" />
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
  <tr class="trw">
  	<td class="trhemaigray" align="right" width="200">期号： </td>
  	<td align="left"><input type="text" name="issueData.periodNumber" value="${issueData.periodNumber!}" readonly="true"/></td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">官方开售时间：</td>
    <td align="left">
    	<input id="issueData.beginTime" type="text" name="issueData.beginTime" value="<#if issueData.beginTime??>${issueData.beginTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">官方截止时间：</td>
    <td align="left">
    	<input id="issueData.endedTime" type="text" name="issueData.endedTime" value="<#if issueData.endedTime??>${issueData.endedTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"  />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">官方开奖时间：</td>
    <td align="left">
    	<input id="issueData.resultTime" type="text" name="issueData.resultTime" value="<#if issueData.resultTime??>${issueData.resultTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">开奖结果：</td>
    <td align="left"><input id="issueData.results" name="issueData.results" maxlength="23" type="text" value="${issueData.results!}"/><span style="color:red;margin-left:10px;">* 每个开奖号码以","分隔。例如:1,2,3,4,5,快乐十分是1,2,3,4,5,6,7,8</span></td>
  </tr>
</table>
<input type="submit" name="method:update" value="更新数据" />
</form>
</div>
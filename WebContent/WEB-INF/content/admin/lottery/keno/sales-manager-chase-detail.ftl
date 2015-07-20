<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery}"/>
<script type='text/javascript' src='${base}/js/My97DatePicker/WdatePicker.js'></script>
</head>
<div class="nowpalce">
	<div style="float:left;">
		您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/${lottery.key}/sales-manager!list.action">${lottery.lotteryName}</a> → <span style="color:red;">编辑数据</span>
	</div>
</div>
<div>
<form name="issueForm" action="${base}/admin/lottery/keno/${lottery.key}/sales-manager!save.action" method="post">
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
  <tr class="trw">
  	<td class="trhemaigray" align="right" width="200">期号： </td>
  	<td align="left"></td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">官方开售时间：</td>
    <td align="left">
    	
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">官方截止时间：</td>
    <td align="left">
    	
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">官方开奖时间：</td>
    <td align="left">
    	
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">状态：</td>
    <td align="left"></td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">开奖结果：</td>
    <td align="left"></td>
  </tr>
</table>
<input type="submit" name="method:update" value="更新数据" />
</form>
</div>
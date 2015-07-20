<head>
<title>彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lotteryType!'SSQ'}"/>
</head>
<div class="nowpalce">
	<div style="float:left;">
		您所在位置：<a href="${base}/">彩种管理</a> → <a href="${base}/${lotteryType.key}/issue.action">${lotteryType.lotteryName}</a> → <span style="color:red;">编辑开奖数据</span>
	</div>
    <div style="float:left;padding-left:60px;">
		快捷链接：
	  	<a href="${base}/${lotteryType.key}/manage.action?id=${period.id!}">销售管理</a>
	  	<a href="${base}/${lotteryType.key}/scheme!list.action?issueNumber=${period.periodNumber}" >方案管理</a>
	  	<#if period.drawed>
	  	<a href="${base}/${lotteryType.key}/period-data.action!edit?id=${period.id}" >开奖数据</a>
	  	</#if>
  	</div>
</div>
<div>
<form name="issueForm" action="${base}/admin/lottery/${lotteryType.key}/period-data.action" method="post">
<input type="hidden" name="period.id" value="${period.id!}" />
<input type="hidden" name="period.lotteryType" value="${period.lotteryType!'SSQ'}" />
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
  <tr>
  	<td class="trhemaigray" align="right" width="200">期号： </td>
  	<td align="left">${period.periodNumber!}</td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right" width="200">开奖号码:</td>
    <td align="left">
    	<input type="text" name="periodData.result" value="${periodData.result!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">一等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">二等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">三等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">四等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">五等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>
  <tr class="trw">
    <td class="trhemaigray" align="right">六等奖奖金：</td>
    <td align="left">
    	<input type="text" name="periodData.prize.firstPrize" value="${periodData.prize.firstPrize!}" />
    </td>
  </tr>

</table>
<input type="hidden" name="periodData.periodId" value="${periodData.periodId!}" />${periodData.periodId!}
<input type="submit" name="method:update" value="更新数据" />
</form>
</div>
<head>
	<title>当前方案列表</title>
	<meta name="menu" content="activity"/>
	<meta name="menuItem" content="showKenoScheme"/>
</head>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">当前方案列表</span></div>
</div>
	          

<a href="${base}/admin/activity/keno/scheme!destroyAllKenoScheme.action" target="_blank">删除所有</a>	          
 <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
      <td>彩种</td>
      <td>彩期</td>
       <td>倍数</td>
      <td>发起人</td>
    </tr>
        <#if kenoSchemeList??&& (kenoSchemeList![])?size gt 0>
    	<#list kenoSchemeList as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.lotteryType!}</td>
	      <td>${data.periodNumber!}</td>
	      <td>${data.multiple!}</td>
	      <td>${data.sponsorName!}</td>
	      <td><a href="${base}/admin/activity/keno/scheme!destroyAllKenoScheme.action?kenoSchemeKey=${data.lotteryType!}"|"${data.periodNumber!}" target="_blank">删除本期</a></td>
		</tr>
    	</#list>
    	 </#if>
  </table>
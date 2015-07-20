<head>
<title>频道通知</title>
<meta name="decorator" content="adminJquery" />
<head>
<meta name="menu" content="index"/>
<meta name="menuItem" content="channelNotice"/>
</head>
<body>
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<form method="post"  action="<@c.url value="/admin/info/news!buildChannelNoticeNews.action"/>">
              	<input name="channelNoticeNews" type="text" size="50" value="<a href='**' target='blank'>***</a>" />
              		<select name="lotteryType" size="1" id="infoBeanForm.lotteryType">
              		    <#list lotteryTypeArr as type>
							<option  value="${type}">${type.lotteryName}</option>
						</#list>]
				   </select>
                 <input type="image" src="${base}/images/comfirm.gif" />
    </form>
    
    <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
      <td>彩种</td>
      <td>公告</td>
      <td>操作</td>
    </tr>
    <#if noticeNewsMap??>
    	<#list noticeNewsMap?keys as mapKey>
		<#if mapKey_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	 		<td>${mapKey}</td>
		<td>${noticeNewsMap[mapKey]!}</td>
	    <td><a href="<@c.url value="/admin/info/news!deleteChannelNoticeNews.action?lotteryType=${mapKey}"/>"> 删除</a></td>
	    </tr> 
    </#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="11">无记录</td>
	    </tr>
    </#if>
  </table>
    
    
</body>
</html>
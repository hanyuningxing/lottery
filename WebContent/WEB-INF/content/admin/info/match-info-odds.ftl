<form action="${base}/admin/info!dcMatchInfoEdit.action">
请输入单场期 号:<input type="text" name="periodNumber" value="${periodNumber!}" />  <input type="submit" name="method:updateMatch" value="载入" />
</form>
<#if matchList?? && matchList?size gt 0>
<form action="${base}/admin/info!dcMatchInfoSave.action">
<input type="hidden" name="periodNumber" value="${periodNumber!}" />
<table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#DCDCDC" id="updatematchssptable">
  <tr class="trlbrown" align="center">
    <td>场次</td>
    <td>联赛名称</td>
    <td>亚赔</td>
    <td>平均欧赔胜</td>
    <td>平均欧赔平</td>
    <td>平均欧赔负</td>
  </tr>
  <#list matchList as match>
  <#if match_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
  <tr class="${trColor}" align="center" id="match_sp_${match_index+1}">
    <td>${match.lineId+1}<input type="hidden" name="matchList[${match_index}].lineId" value="${match.lineId!}" /></td>
    <td>${match.gameName!}<input type="hidden" name="matchList[${match_index}].periodId" value="${match.periodId!}" /></td>
	<td><input type="text" name="matchList[${match_index}].oddsData.assia" value="<#if match.oddsData?? && match.oddsData.assia??>#{match.oddsData.assia;M6}</#if>" style="width:30px;" /></td>
	<td><input type="text" name="matchList[${match_index}].oddsData.europWin" value="<#if match.oddsData?? && match.oddsData.europWin??>#{match.oddsData.europWin;M6}</#if>" style="width:30px;" /></td>
	<td><input type="text" name="matchList[${match_index}].oddsData.europDraw" value="<#if match.oddsData?? && match.oddsData.europDraw??>#{match.oddsData.europDraw;M6}</#if>" style="width:30px;" /></td>
	<td><input type="text" name="matchList[${match_index}].oddsData.europLost" value="<#if match.oddsData?? && match.oddsData.europLost??>#{match.oddsData.europLost;M6}</#if>" style="width:30px;" /></td>	
  </tr>
  </#list>
  <tr class="twonavlblue">
  	<td height="35" colspan="21" style="padding-left:20px;">
    	<input type="submit" name="method:updateMatch" value="保存" />
  	</td>
  </tr>
</table>
</form>
</#if>
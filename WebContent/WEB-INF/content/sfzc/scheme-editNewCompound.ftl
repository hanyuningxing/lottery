<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<@override name="editNewHead">	
	<#assign isSFZC9=playType=='SFZC9'/>
	<script type="text/javascript"><#if isSFZC9>var isSFZC9=true;<#else>var isSFZC9=false;</#if></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>

<@override name="content">
	<#if isSFZC9>
		<#assign clickFn="clickItemFn" />
		<#assign columnFn="columnClickFn" />
	<#else>
		<#assign clickFn="selectAt" />
		<#assign columnFn="columnSelect" />
	</#if>
	<!--table-->
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
      <tr align="center" class="tdduilist"  height="24">
        <td height="30">场</td>
        <td>赛事</td>
        <td>开赛</td>
        <td width="140">主队</td>
        <td width="140" >客队</td>
        <td class="tdr1px">平均指数</td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><label><input id="column_homeWin" type="checkbox" onclick="${columnFn}(this,'homeWin');" /> 胜</label></td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><input id="column_draw" type="checkbox" onclick="${columnFn}(this,'draw');" /> 平</label></td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><label><input id="column_guestWin" type="checkbox" onclick="${columnFn}(this,'guestWin');" /> 负</label></td>
        <td><#if isSFZC9>胆<#else>全选</#if></td>
      </tr>
      <#if matchs??>
  		<#list matchs as match>
  			<#if match_index%2==0>
  				<#assign className='tdlist' />
  			<#else>
  				<#assign className='tdlist1' />
  			</#if>
		      <tr id="tr_${match.lineId}" align="center" class="${className}" >
		        <td width="20" height="38" id="match_${match.lineId}">${(match.lineId+1)?string('00')}</td>
		        <td style="background:<#if match.gameColor?? && match.gameColor?length gt 0>${match.gameColor}</#if>; color:#fff;">${match.gameName!}</td>
		        <td class="tdr1px" title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('HH:mm')}</td>
		        <td class="char14"><span class="bc0 char14" id="home_${match.lineId}">${match.homeTeamName!}</span></td>
		        <td class="char14"><span class="bc0 char14" id="guest_${match.lineId}">${match.guestTeamName!}</span></td>
		        <td><#if match.odds1??>${match.odds1?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds2??>${match.odds2?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds3??>${match.odds3?string('0.00')}</#if></td>
		        <td width="120" class="tdyelnor" id="li_${match.lineId}_homeWin" onclick="${clickFn}(${match.lineId},'homeWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${match.lineId}_homeWin" onclick="${clickFn}(${match.lineId},'homeWin',event);" type="checkbox" name="createForm.items[${match.lineId}].homeWin" value="true" />3</td>
		        <td width="120" class="tdyelnor" id="li_${match.lineId}_draw" onclick="${clickFn}(${match.lineId},'draw',event);" style="cursor:pointer"><input style="display:none;" id="chk_${match.lineId}_draw" onclick="${clickFn}(${match.lineId},'draw',event);" type="checkbox" name="createForm.items[${match.lineId}].draw" value="true" />1</td>
		        <td width="120" class="tdyelnor" id="li_${match.lineId}_guestWin" onclick="${clickFn}(${match.lineId},'guestWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${match.lineId}_guestWin" onclick="${clickFn}(${match.lineId},'guestWin',event);" type="checkbox" name="createForm.items[${match.lineId}].guestWin" value="true" />0</td>
		        <td class="tdl1px">
		        <#if isSFZC9>
	          		<input disabled="disabled" type="checkbox" name="createForm.items[${match.lineId}].shedan" onclick="clickDanFn(${match.lineId},'shedan')" value="true" />
	          	<#else>
	          		<input type="checkbox" id="bao_${match.lineId}" onclick="rowSelect(${match.lineId},event);"/>
	          	</#if></td>
		      </tr>	
		      			<script>							
							var tr_id = "tr_${match.lineId}";						
							changeBgColor(tr_id);																																
						</script>	      			
		  </#list>
		  <#if isSFZC9>
	        <tr class="tdlist"  height="34">
	      		<td colspan="10" height="25" style="text-align:right;">模糊设胆：<span id="mohushedan"></span></td>
	      	</tr>
      	</#if>
      </#if>      
    </table>
    <div class="k10"></div>
</@override>

<@extends name="base.ftl"/>
<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>

<@override name="content">
	<#assign clickFn="selectAt" />
	<#assign columnFn="columnSelect" />
	<!--table-->
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
      <tr align="center" class="tdduilist"  height="24">
        <td height="30">场</td>
        <td>赛事</td>
        <td>开赛</td>
        <td width="140">主队</td>
        <td width="140" >客队</td>
        <td   class="tdr1px">平均指数</td>
        <td width="60" class="tdr1px">类型</td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><span class="tdl1px">
          <input type="checkbox" id="column_homeWin" type="checkbox" onclick="${columnFn}(this,'homeWin');" />
        </span>胜</td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><span class="tdl1px">
          <input type="checkbox" id="column_draw" type="checkbox" onclick="${columnFn}(this,'draw');" />
        </span>平</td>
        <td style="background:#FEF5D2; color:#CC4212;" class="tdr1px"><span class="tdl1px">
          <input type="checkbox" id="column_guestWin" type="checkbox" onclick="${columnFn}(this,'guestWin');" />
        </span>负</td>
      </tr>
      
      <#if matchs?? && matchs?size gt 0>
		<#assign index=0 />
  		<#list matchs as match>
  			<#if match_index%2==0>
  				<#assign className='tdlist' />
  			<#else>
  				<#assign className='tdlist1' />
  			</#if>
  			  <tr id="tr1_${match.lineId}" align="center" class="${className}" >
		        <td width="20" rowspan="2" id="match_${match.lineId}">${(match.lineId+1)?string('00')}</td>
		        <td rowspan="2" style="background:<#if match.gameColor?? && match.gameColor?length gt 0>${match.gameColor}</#if>; color:#fff;">${match.gameName!}</td>
		        <td rowspan="2" id="time_${match.lineId}" class="tdr1px" title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('HH:mm')}</td>
		        <td rowspan="2" id="hometd_${match.lineId}" class="char14"><span class="bc0 char14" id="home_${match.lineId}">${match.homeTeamName!}</span></td>
		        <td rowspan="2" id="guesttd_${match.lineId}" class="char14"><span class="bc0 char14" id="guest_${match.lineId}">${match.guestTeamName!}</span></td>		        
		        <td rowspan="2" id="odd_${match.lineId}" class="tdr1px"><#if match.odds1??>${match.odds1?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds2??>${match.odds2?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds3??>${match.odds3?string('0.00')}</#if></td>
		        <td height="38" id="ban_${match.lineId}">半场</td>
		        <td width="120" id="li_${index}_homeWin" class="tdyelnor" onclick="${clickFn}(${index},'homeWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_homeWin" onclick="${clickFn}(${index},'homeWin',event);" type="checkbox" name="createForm.items[${index}].homeWin" value="true" />3</td>
		        <td width="120" id="li_${index}_draw" class="tdyelnor" onclick="${clickFn}(${index},'draw',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_draw" onclick="${clickFn}(${index},'draw',event);" type="checkbox" name="createForm.items[${index}].draw" value="true" />1</td>
		        <td width="120" id="li_${index}_guestWin" class="tdyelnor" onclick="${clickFn}(${index},'guestWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_guestWin" onclick="${clickFn}(${index},'guestWin',event);" type="checkbox" name="createForm.items[${index}].guestWin" value="true" />0</td>
	          </tr>
	         					 <script>
		      												
									var arr = new Array("match_${match.lineId}", "time_${match.lineId}", "hometd_${match.lineId}", "guesttd_${match.lineId}", "odd_${match.lineId}", "ban_${match.lineId}");						
									var tr_id = "tr1_${match.lineId}";						
									changeBgColor(tr_id, arr);																																
								</script>
		      <#assign index=index+1 />
		      <tr id="tr2_${match.lineId}" align="center" class="${className}" >
		        <td id="quan_${match.lineId}" height="38">全场</td>
		        <td id="li_${index}_homeWin" class="tdyelnor" onclick="${clickFn}(${index},'homeWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_homeWin" onclick="${clickFn}(${index},'homeWin',event);" type="checkbox" name="createForm.items[${index}].homeWin" value="true" />3</td>
	            <td id="li_${index}_draw" class="tdyelnor" onclick="${clickFn}(${index},'draw',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_draw" onclick="${clickFn}(${index},'draw',event);" type="checkbox" name="createForm.items[${index}].draw" value="true" />1</td>
	            <td id="li_${index}_guestWin" class="tdyelnor" onclick="${clickFn}(${index},'guestWin',event);" style="cursor:pointer"><input style="display:none;" id="chk_${index}_guestWin" onclick="${clickFn}(${index},'guestWin',event);" type="checkbox" name="createForm.items[${index}].guestWin" value="true" />0</td>	        
	          </tr>
	          					<script>
		      												
									var arr = new Array("match_${match.lineId}", "time_${match.lineId}", "hometd_${match.lineId}", "guesttd_${match.lineId}", "odd_${match.lineId}", "quan_${match.lineId}");						
									var tr_id = "tr2_${match.lineId}";						
									changeBgColor(tr_id, arr);																																
								</script>
	          <#assign index=index+1 /> 
	          					
        </#list>
      </#if>
    </table>
    <div class="k10"></div>
</@override>

<@extends name="base.ftl"/>
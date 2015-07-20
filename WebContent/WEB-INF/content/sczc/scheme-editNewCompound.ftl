<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>

<@override name="content">
	<#assign clickFn="selectAt" />
	<#assign columnFn="columnSelect" />
	<!--table-->
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
      <tr align="center" class="tdduilist"  height="24"  >
        <td height="30">场</td>
        <td>赛事</td>
        <td>开赛</td>
        <td width="180" >主队/客队</td>
        <td class="tdr1px">平均指数</td>
        <td width="100" class="tdr1px" style="background:#FEF5D2; color:#CC4212;"><input id="column_nonGoal" type="checkbox" onclick="${columnFn}(this,'nonGoal');" />
          <b>0</b></td>
        <td width="100" class="tdr1px" style="background:#FEF5D2; color:#CC4212;"><input id="column_oneGoal" type="checkbox" onclick="${columnFn}(this,'oneGoal');" />
          <b>1</b></td>
        <td width="100" class="tdr1px" style="background:#FEF5D2; color:#CC4212;"><input id="column_twoGoal" type="checkbox" onclick="${columnFn}(this,'twoGoal');" />
          <b>2</b></td>
        <td width="100" class="tdr1px" style="background:#FEF5D2; color:#CC4212;"><input id="column_threeGoal" type="checkbox" onclick="${columnFn}(this,'threeGoal');" />
          <b>3+</b></td>
        <td width="60">全选</td>
      </tr>
      
      <#if matchs?? && matchs?size gt 0>
		<#assign index=0 />
  		<#list matchs as match>
  			<#if match_index%2==0>
  				<#assign className='tdlist' />
  			<#else>
  				<#assign className='tdlist1' />
  			</#if>
  			  <tr id="tr_${match.lineId}" align="center" class="${className}" >
  			    <td width="20" rowspan="2" id="match_${match.lineId}">${(match.lineId+1)?string('00')}</td>
		        <td rowspan="2" <#if match.gameColor?? && match.gameColor?length gt 0>bgcolor="${match.gameColor}" class="whitechar"</#if>>${match.gameName!}</td>
		        <td rowspan="2" id="time_${match.lineId}" class="tdr1px" title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('MM-dd HH:mm')}</td>		        
		        <td height="38" id="hometd_${match.lineId}"><span class="bc0 char14" id="home_${match.lineId}">${match.homeTeamName!}</span></td>
		        <td rowspan="2" id="odd_${match.lineId}" class="tdl1px"><#if match.odds1??>${match.odds1?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds2??>${match.odds2?string('0.00')}</#if>&nbsp;&nbsp;<#if match.odds3??>${match.odds3?string('0.00')}</#if></td>
		        <td id="li_${index}_nonGoal" onclick="${clickFn}(${index},'nonGoal',event);" width="100" class="tdyelnor"><input style="display:none;" id="chk_${index}_nonGoal" onclick="${clickFn}(${index},'nonGoal',event);" type="checkbox" name="createForm.items[${index}].nonGoal" value="true" />0</td>
	            <td id="li_${index}_oneGoal" onclick="${clickFn}(${index},'oneGoal',event);" width="100" class="tdyelnor"><input style="display:none;" id="chk_${index}_oneGoal" onclick="${clickFn}(${index},'oneGoal',event);" type="checkbox" name="createForm.items[${index}].oneGoal" value="true" />1</td>
	            <td id="li_${index}_twoGoal" onclick="${clickFn}(${index},'twoGoal',event);" width="100" class="tdyelnor"><input style="display:none;" id="chk_${index}_twoGoal" onclick="${clickFn}(${index},'twoGoal',event);" type="checkbox" name="createForm.items[${index}].twoGoal" value="true" />2</td>
	            <td id="li_${index}_threeGoal" onclick="${clickFn}(${index},'threeGoal',event);" width="100" class="tdyelnor"><input style="display:none;" id="chk_${index}_threeGoal" onclick="${clickFn}(${index},'threeGoal',event);" type="checkbox" name="createForm.items[${index}].threeGoal" value="true" />3+</td>
	            <td class="tdl1px"><a href="javascript:void(0);" onclick="rowSelect(${index});return false;">全选</a></td>
		      </tr>
		      					<script>
		      												
									var arr = new Array("match_${match.lineId}", "time_${match.lineId}", "hometd_${match.lineId}", "odd_${match.lineId}");						
									var tr_id = "tr_${match.lineId}";						
									changeBgColor(tr_id, arr);																																
								</script>
		      <#assign index=index+1 />
		      <tr id="tr2_${match.lineId}" align="center" class="${className}" >
		        <td height="38" id="guesttd_${match.lineId}"><span class="bc0 char14" id="guest_${match.lineId}">${match.guestTeamName!}</span></td>
		        <td id="li_${index}_nonGoal" onclick="${clickFn}(${index},'nonGoal',event);" class="tdyelnor"><input style="display:none;" id="chk_${index}_nonGoal" onclick="${clickFn}(${index},'nonGoal',event);" type="checkbox" name="createForm.items[${index}].nonGoal" value="true" />0</td>
	            <td id="li_${index}_oneGoal" onclick="${clickFn}(${index},'oneGoal',event);" class="tdyelnor"><input style="display:none;" id="chk_${index}_oneGoal" onclick="${clickFn}(${index},'oneGoal',event);" type="checkbox" name="createForm.items[${index}].oneGoal" value="true" />1</td>
	            <td id="li_${index}_twoGoal" onclick="${clickFn}(${index},'twoGoal',event);" class="tdyelnor"><input style="display:none;" id="chk_${index}_twoGoal" onclick="${clickFn}(${index},'twoGoal',event);" type="checkbox" name="createForm.items[${index}].twoGoal" value="true" />2</td>
	            <td id="li_${index}_threeGoal" onclick="${clickFn}(${index},'threeGoal',event);" class="tdyelnor"><input style="display:none;" id="chk_${index}_threeGoal" onclick="${clickFn}(${index},'threeGoal',event);" type="checkbox" name="createForm.items[${index}].threeGoal" value="true" />3+</td>	        
	            <td class="tdl1px"><a href="javascript:void(0);" onclick="rowSelect(${index});return false;">全选</a></td>
	          </tr>
	         					<script>
		      												
									var arr = new Array("match_${match.lineId}", "time_${match.lineId}", "guesttd_${match.lineId}", "odd_${match.lineId}");						
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
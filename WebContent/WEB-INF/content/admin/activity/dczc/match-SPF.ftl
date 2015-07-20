<#assign isSingle=salesMode?? && salesMode=='SINGLE' />
	  <table width="805" cellspacing="1" cellpadding="1" border="0" class="dctbg">
		<thead>
		  <tr align="center" class="dctro">
	          <td width="30" rowspan="2">序</td>
	          <td width="60" rowspan="2">赛事</td>
	          <td width="33" rowspan="2">开赛</td>
	          <!--<td width="45" rowspan="2">截止</td>-->
	          <td width="85" rowspan="2">主队</td>
	          <td width="30" rowspan="2">让球</td>
	          <td width="83" rowspan="2">客队</td>
	          <td width="50" rowspan="2">数据<br/>分析</td>
	          <td height="15" colspan="3"><a class="rebchar" href="#">平均指数</a>&nbsp;<a href="#">亚盘数据</a></td>
	          <td width="30" rowspan="2">比分</td>
	          <td colspan="3">即时SP</td>
	          <td rowspan="2"><#if isSingle>选<#else>全</#if></td>
          </tr>
          <tr align="center" class="dctry">
	          <td height="15" width="35">胜赔</td>
	          <td width="35">平赔</td>
	          <td width="35">负赔</td>
	          <#list itemArr as item>
	          <td><input id="column_${item.ordinal()}" onclick="columnBatch(${item.ordinal()});" type="checkbox" disabled="disabled" style="display:none;" /><label for="column_${item.ordinal()}">${item.text}(${item.value})</label></td>
	          </#list>
          </tr>	
	    </thead>
	    <tbody id="matchTbody">
	      <#assign endCount=0 />
	      <#if matchMap??>
	  		<#list matchMap.entrySet() as entry>
		  	  <tr align="center" onmouseover="this.className='trhover'" onmouseout="this.className='trw'" class="trw">
		        <td colspan="${(itemArr?size)+13}" height="22" height="22" ><img height="15" width="35" align="absmiddle" src="<@c.url value="/pages/images/btyc.gif" />" onclick="dayFilter(this);" _di="${entry_index}" style="cursor:pointer;" /> <span class="boldchar">${entry.getKey()}（10：00 -- 次日 10：00）两队之间数字为让球数 “-”负号表示主队让客队</span></td>
			  </tr>
			  <#list entry.getValue() as match>
				  <#if match_index%2==0>
				  	<#assign trClass='trw' />
				  <#else>
				    <#assign trClass='dctro1' />
				  </#if>
				  <#assign saleEnd=match.checkSaleEnd(aheadMinuteEnd) />
				  <#assign rs=match.getResult(playType)!'' />
				  <#if rs!=''><#assign rs_sp=match.getResultSp(playType)!'' /><#else><#assign rs_sp='' /></#if>
			      <#assign spinfo=infoMap.get(match.lineId)!'' />
			      <#if spinfo!=''><#assign itemspinfo=spinfo.getContent() /><#else><#assign itemspinfo='' /></#if>
				  <#if saleEnd><#assign endCount=endCount+1 /></#if>
				   <#assign endSaleTime=match.getEndSaleTime(salesMode) />
				  <tr id="tr_${match.lineId}" _lid="${match.lineId}" class="${trClass}" align="center" onmouseover="this.className='trhover'" onmouseout="this.className='${trClass}'" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd>1<#else>0</#if>" <#if saleEnd>style="display:none"</#if>>
			        <td height="24"><img id="img_hide_${match.lineId}" height="11" width="12" src="<@c.url value="/pages/images/dt01.gif" />" onclick="hideRow(this);" style="cursor:pointer;" />${(match.lineId+1)?string('00')}</td>
			        <td bgcolor="<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultDczcGameColor!}</#if>" class="whitechar">${match.gameName!}</td>
			        <td title="${match.matchTime?string('yyyy-MM-dd HH:mm')}">${match.matchTime?string('HH:mm')}</td>
			        <!--<td><#if endSaleTime??>${endSaleTime?string('HH:mm')}</#if></td>-->
			        <td id="td_h_${match.lineId}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
			        <td><span id="handicap_${match.lineId}">${match.handicap!}<span></td>
			        <td id="td_g_${match.lineId}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
			        <td><a href="#" id="x_${match.lineId}">析</a> <a href="#" id="y_${match.lineId}">亚</a> <a href="#" id="o_${match.lineId}">欧</a></td>
		            <td>--</td>
		            <td>--</td>
		            <td>--</td>
		           	<td>
			        	<#if match.cancel>
			        		取消
			        	<#elseif match.ended>
			        		<#if match.fullHomeScore?? && match.fullGuestScore??>
			        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
			        		</#if>
			        	</#if>
			        </td>
			        <#list itemArr as item>
			        <td <#if match.cancel>title="取消"</#if> id="td_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" style="cursor: pointer;" >
			        	<input id="chk_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" class="inputcheckbox" type="checkbox" autocomplete="off" /><br/>
			        	<#assign spId='sp_lid'+match.lineId+'_'+item?string />
			        	<#if match.cancel>
			        		<span id="${spId}" _g="sp" class="redredchar">1.00</span>
			        	<#elseif match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
			        		<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.000000')}</span></#if>
			        	<#elseif itemspinfo?? && itemspinfo!=''>
			        		<span id="${spId}" _g="sp">${(itemspinfo.get(item.name())!0)?string('0.00')}</span>
			        	<#else>
			        		<span id="${spId}" _g="sp">0.00</span>
			        	</#if>
			        </td>
			        </#list>
			        <td>
				        	<#if isSingle>
				        		<input id="chb_select_${match.lineId}" type="checkbox" value="${match.lineId}" onclick="selectMatch(this);" autocomplete="off" />
				        	<#else>		
				        		<img style="cursor:pointer;" onclick="rowBatch(${match.lineId},0,event)" height="20" width="21" border="0" src="<@c.url value="/pages/images/dc_all.gif" />" />
			        			<input id="bao_0_${match.lineId}" type="checkbox" onclick="rowBatch(${match.lineId},0,event)" style="display:none;" autocomplete="off" />
			        		</#if>
			        </td>
			      </tr>
		      </#list>
	  		</#list>
	  	  </#if>
		</tbody>
	  </table>
	  <script type="text/javascript">
	  	$(document).ready(function() {
	  		var el = document.getElementById('m_e_c');
	  		if(el != null)
				el.innerHTML = '${endCount}';
        });
	  </script>
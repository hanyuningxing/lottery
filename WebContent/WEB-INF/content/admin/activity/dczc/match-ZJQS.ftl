<#assign isSingle=salesMode?? && salesMode=='SINGLE' />
<#if isSingle><#assign col=8/><#else><#assign col=7/></#if>
	  <table width="805" cellspacing="1" cellpadding="1" border="0" class="dctbg">
		<thead>
		  <tr align="center" class="dctro">
	          <td height="25" width="30">序</td>
	          <td>赛事</td>
	          <td width="33">开赛</td>
	          <!--<td width="33">截止</td>-->
	          <td width="90">主队</td>
	          <td width="90">客队</td>
	          <#list itemArr as item>
	        	<td width="<#if isSingle>40<#else>45</#if>"><label for="column_${item.ordinal()}">${item.text}</label></td>
	          </#list>
	          <td width="48">比分</td>
	          <td width="48">分析</td>
	          <#if isSingle><td width="30">选</td></#if>
          </tr>
	    </thead>
	    <tbody id="matchTbody">
	      <#assign endCount=0 />
	      <#if matchMap??>
	  		<#list matchMap.entrySet() as entry>
		  	  <tr align="center" onmouseover="this.className='trhover'" onmouseout="this.className='trw'" class="trw">
		        <td colspan="${(itemArr?size)+col}" height="22" height="22" ><img height="15" width="35" align="absmiddle" src="<@c.url value="/pages/images/btyc.gif" />" onclick="dayFilter(this);" _di="${entry_index}" style="cursor:pointer;" /> <span class="boldchar">${entry.getKey()}（10：00 -- 次日 10：00）两队之间数字为让球数 “-”负号表示主队让客队</span></td>
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
			        <td id="td_g_${match.lineId}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
			        <#list itemArr as item>
			        <td <#if match.cancel>title="取消"</#if> <#if !saleEnd>id="td_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" style="cursor: pointer;"</#if> >
			        	<#if !saleEnd && !isSingle><input id="chk_${match.lineId}_${item.ordinal()}" onclick="clickItem(${match.lineId},${item.ordinal()},event);" class="inputcheckbox" type="checkbox" autocomplete="off" /><br/></#if>
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
			        	<#if match.cancel>
			        		取消
			        	<#elseif match.ended>
			        		<#if match.fullHomeScore?? && match.fullGuestScore??>
			        		<font style="color:red;font-weight:bold;">${match.fullHomeScore}:${match.fullGuestScore}</font>
			        		</#if>
			        	</#if>
			        </td> 
			        <td><a href="#" id="x_${match.lineId}">析</a> <a href="#" id="y_${match.lineId}">亚</a> <a href="#" id="o_${match.lineId}">欧</a></td>
			        <#if isSingle>
			        <td>
			        	<#if !saleEnd>
				        	<input id="chb_select_${match.lineId}" type="checkbox" value="${match.lineId}" onclick="selectMatch(this);" autocomplete="off" />
			        	<#else>
			        		&nbsp;
			        	</#if>
			        </td>
			        </#if>
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
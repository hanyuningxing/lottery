<img alt="loading" id="loading_img" src="${base}/styles/trade/images/loading.gif" style="display:none;"/>
	<div id="quick_buy_container">
		<input id="quick_qid" type="hidden" value="${period.id!}" />
		<input id="qihao" type="hidden" value="${period.periodNumber!}" />
			<div id="duizhenA_tb_0" class="duizhenA_tb_">
			    <ul class="duizhenA_ulA">
			        <li id="duizhenA_tb_12" class="duizhenA_normaltab"><strong>${period.periodNumber!}期</strong></li>
			     </ul>
			     <ul class="duizhenA_ulB">    
			        <li class="duizhenA_title">
			               单式截至：<#if singleEndTime??>${singleEndTime?string("yyyy-MM-dd HH:mm")}</#if>&nbsp;&nbsp;
			              复式截至 <#if compoundEndTime??>${compoundEndTime?string("yyyy-MM-dd HH:mm")}</#if>
			        </li>
			    </ul>
           </div>
       	  <div class="duizhenA_ctt">
		        <div id="duizhenA_tbc_12">
				        <table border="0" cellspacing="0" cellpadding="0" class="duizhenTable">
				          <thead>
				            <tr>
				              <td width="6%">场次</td>
				              <td width="8%">比赛</td>
				              <td width="16%">开赛时间</td>
				              <td width="20%">对阵</td>
							  <td>亚赔</td>
				              <td width="26%">平均欧赔</td>
				              <td width="8%">数据分析</td>
				            </tr>
				          </thead>
				          <tbody>
				          	<#if matchArr??>
  								<#list matchArr as data>
				                <tr>
					                <td>${data.lineId + 1}&nbsp;</td>
					                <td>${data.gameName!}</td>
					                <td><#if data.matchTime??>${data.matchTime?string("MM-dd HH:mm")}</#if>&nbsp;</td>
					                <td>${data.homeTeamName!} &nbsp;<font style="color:red">${data.homeScore!} : ${data.guestScore!}</font>&nbsp; ${data.guestTeamName!}&nbsp;</td>
					                <td><#if data.asiaOdd??>${data.asiaOdd?string("#.##")}</#if></td>
					                <td>
						                <#if data.odds1??><span class="speilv">${data.odds1?string("#.##")}</span></#if>
									    <#if data.odds2??><span class="normalpeilv">${data.odds2?string("#.##")}</span></#if>
									    <#if data.odds3??><span class="normalpeilv">${data.odds3?string("#.##")}</span></#if>
					                </td>
					                <td>
					                    <a href="#" target="_blank">析</a>
					                    <a href="#" target="_blank">亚</a>
					                    <a href="#" target="_blank">欧</a>
					                </td>
				                </tr>    
				                </#list>
				                </#if>
				          </tbody>
				        </table>        
		         </div>
         </div>
	</div>
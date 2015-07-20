<#if matchFilterDatas??&&matchFilterDatas?size gt 0>
						  <div id="matchDatas">
					   			<#list matchFilterDatas as data>
								  <tr class="nr_bga">
									<td width="29" height="25" name="selectBall" id="selectBall_${data[0]}">${data[0]}</td>
									<td width="51" height="25">${data[1]}</td>
									<td width="24" height="25">${data[2]!""}</td>
									<td width="54" height="25">${data[3]}</td>
									<td width="28" height="25" class="ss2" id="ss2_${data[0]}_3"><a href="javascript:void(0);" onclick="selectSingle('${data[0]}','3')">3</a></td>	
									<td width="28" height="25" class="ss2" id="ss2_${data[0]}_1"><a href="javascript:void(0);" onclick="selectSingle('${data[0]}','1')">1</a></td>	
									<td width="28" height="25" class="ss2" id="ss2_${data[0]}_0"><a href="javascript:void(0);" onclick="selectSingle('${data[0]}','0')">0</a></td>	
									<td width="37" height="25" id="sps_win_${data[0]}">${data[7]!""}</td>
									<td width="37" height="25" id="sps_draw_${data[0]}">${data[8]!""}</td>
									<td width="36" height="25" id="sps_lose_${data[0]}">${data[9]!""}</td>
								  </tr>
							  </#list>
							  </div>
						  </#if>
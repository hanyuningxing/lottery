<!-- 要弹出的内容开始-->
<div id="div_pop_content" style="display:none">            
	<#include 'match-hit.ftl' />		        	
</div>
<!-- 要弹出的内容结束-->

<!-- 分组要弹出的内容开始-->
<div id="div_group_content" style="display:none">            
	<#include 'match-group.ftl' />		        	
</div>
<!-- 分组要弹出的内容结束-->



<form id="filterForm" action="${base}/${lotteryType.key}/filter!filterDetailShow.action" method="post" autocomplete="off" target="_blank">
 <div class="right2">
				   <div class="right2_main0">
					      <table width="470" border="0"  cellspacing="0" class="sc_tb">
						    <tr >
							  <td  class="rt_line" width="75" height="24"><a href="javascript:void(0);" onclick="clearAll();">全删</a></td>
							  <td  class="rt_line" height="24" colspan="3">条件设置</td>
							  <td class="rt_line" width="138" height="24">容错</td>
						    </tr>
				      </table>
					       <div class="right2_main1">
					       		<!--过滤步骤条件开始-->
								<table width="470" border="0"  cellspacing="0" class="sc_tb4" id="condition"></table>	
		              			<!--过滤步骤条件结束-->
		              			<!--过滤简介开始-->
		              			<div class="zx" id="filterIntroduce"> <img style="padding-left:28px;padding-top:25px;" src="<@c.url value= "/pages/images/glq/glq_gg_17.gif"/>" /></div>
		              			<!--过滤简介结束-->
					 </div>
				    <div class="shaxiang8_index"></div>
				    <div class="rt_cl"><a href="javascript:void(0);" onclick="dealCond();" id="dealCond">处理</a>
				    <span style="display:none;" id="span_createForm_waiting"><div style="text-align:center;">正在处理,请稍等...</div></span>
				    </div>
						 <div class="shaxiang8_index"></div>
						 <div class="right2_main2">
						<table width="470"border="0"  cellspacing="0" class="sc_tb1">
							  <tr style="background:#f8f8ab">
								<td width="112" height="24" style="text-align:right">单注区</td>
								<td width="47" height="24">|&lt; </td>
								<td width="26" height="24">&lt;</td>
								<td width="28" height="24">&gt; </td>
								<td width="48" height="24">&gt; | </td>
								<td width="47" height="24" style="text-align:right">跳到</td>
								<td width="21" height="24" style="text-align:right"> <input name="15" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onclick="this.className='header_searchclick'"   size="3" style="height:12px;"/></td>
							    <td width="125" style="text-align:left">/0页</td>
							  </tr>
					  </table>
					   <div class="shaxiang8_index"></div>
					 	 <table width="453" border="0" cellpadding="0" cellspacing="1" class="rt_ck1">
						  <tr class="yhxx1">
						     <td width="66" height="22">序号</td>
						     <td width="387">单式</td>
						  </tr>
						 </table>
						 
						  <div class="main_ck">
						  <table width="453" border="0" cellpadding="0"  class="rt_ck" id="result">
						  <tr>
							<td width="66" height="22">1</td>
						    <td width="380">&nbsp;</td>
						  </tr>
						  <tr style="background:#fafaf1">
							<td height="22">2</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						  <tr>
							<td height="22">3</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						  <tr style="background:#fafaf1">
							<td height="22">4</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						  <tr>
							<td height="22">5</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						   <tr style="background:#fafaf1">
							<td height="22">6</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						  <tr>
							<td height="22">7</td>
						    <td height="22">&nbsp;</td>
						  </tr>
						</table>
						</div>
						 <div class="shaxiang8_index"></div>
                         <table width="470" border="0"  cellspacing="0" class="sc_tb2">
							  <tr>
								<td width="104" height="24">投注倍数：</td>
								<td width="237" height="24" style="text-align:left">
								<input type="text" name="createForm.multiple" value="1" size="5" id="createForm_multiple" onchange="updateBet();"/>
								</td>
								<td width="123" height="24"  class="rt_tj"><a href="javascript:void(0);" onclick="handlerItems();">提交</a></td>
							  </tr>
							  <tr>
								<td height="24" id="finallyCount">注数：<b>未处理</b>         </td>
								<td height="24" id="totalCost">总金额：0</td>
								<td height="24">&nbsp;</td>
							  </tr>
						   </table>
					</div>			
				</div>
		    </div>
		    	    <input type="hidden" name="playType" value="${playType}"/>	
		    	    <input type="hidden" name="filterForm.units" id="units" value="${filterForm.units}"/>		    	    
		    	    <input type="hidden" id="size" name="size" value="${size!0}"/>	
		    	    <input type="hidden" name="createForm.units" value="${createForm.units!0}" id="createForm_units"/>	
		    	    <input type="hidden" name="createForm.schemeCost" value="" id="createForm_schemeCost"/>	
		    	    <input type="hidden" name="createForm.mode" value="SINGLE"/>	
		    	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
		    	    <input type="hidden" name="createForm.content" value="${createForm.content!""}" id="createForm_content"/>
		    	    <input type="hidden" name="createForm.passTypes" value="P${size!0}_1" id="createForm_passTypes"/>
</form>		    
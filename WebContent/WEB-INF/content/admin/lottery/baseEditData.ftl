<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>

	<script type="text/javascript">window.BASESITE='${base}';</script>
<script type='text/javascript'>
	jQuery.noConflict(); 
	jQuery(document).ready(function() {
         jQuery('#periodForm input').keyup(trimkeyup);
	 });  
	 function trimkeyup(e) {
	     lucene_objInput = jQuery(this);
	     if (e.keyCode != 38 && e.keyCode != 40 && e.keyCode != 13) {
	         var im = jQuery.trim(lucene_objInput.val());
	         lucene_objInput.val(im); 
	     }
	}        
</script>

		  <tr class="trw">
		    <td align="left" colspan="2">
		      <input id="updatePeriod" type="checkbox" checked="checked" name="updatePeriod" value="true" /><label for="updatePeriod">更新期数据</label>
		      <input id="periodNumber_" type="hidden"  value="${period.periodNumber}"/>
		      <input id="lottery" name="lottery" type="hidden" value="${period.lotteryType.key}"/>	
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right" width="160">官方截止时间：</td>
		    <td align="left">
		    	<input id="period.endedTime" type="text" name="period.endedTime" value="<#if period.endedTime??>${period.endedTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('period.endedTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">官方开奖时间：</td>
		    <td align="left">
		    	<input id="period.prizeTime" type="text" name="period.prizeTime" value="<#if period.prizeTime??>${period.prizeTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('period.prizeTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">开奖：</td>
		    <td align="left">
		    	<input id="period_drawed_false" type="radio" name="period.drawed" <#if !period.drawed>checked="checked"</#if> value="false" /><label for="period_drawed_false">未开奖</label>
		    	<input id="period_drawed_true" type="radio" name="period.drawed" <#if period.drawed>checked="checked"</#if> value="true" /><label for="period_drawed_true">已开奖</label>
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">备注：</td>
		    <td align="left"><textarea name="period.remark" style="width:150px;height:60px;">${period.remark!}</textarea></td>
		  </tr>
		  <tr class="trw">
		    <td align="left" colspan="2">
		      <input id="updateSinglePeriodSales" type="checkbox" checked="checked" name="updateSinglePeriodSales" value="true" /><label for="updateSinglePeriodSales">更新单式销售配置</label>
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">单式合买发起截止时间：</td>
		    <td align="left">
		   	    <input id="singlePeriodSales.shareEndInitTime" type="text" name="singlePeriodSales.shareEndInitTime" value="<#if singlePeriodSales.shareEndInitTime??>${singlePeriodSales.shareEndInitTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('singlePeriodSales.shareEndInitTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">单式自购发起截止时间：</td>
		    <td align="left">
		    	<input id="singlePeriodSales.selfEndInitTime" type="text" name="singlePeriodSales.selfEndInitTime" value="<#if singlePeriodSales.selfEndInitTime??>${singlePeriodSales.selfEndInitTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('singlePeriodSales.selfEndInitTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">单式认购截止时间：</td>
		    <td align="left">
		    	<input id="singlePeriodSales.endJoinTime" type="text" name="singlePeriodSales.endJoinTime" value="<#if singlePeriodSales.endJoinTime??>${singlePeriodSales.endJoinTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('singlePeriodSales.endJoinTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td align="left" colspan="2">
		      <input id="updateCompoundPeriodSales" type="checkbox" checked="checked" name="updateCompoundPeriodSales" value="true" /><label for="updateCompoundPeriodSales">更新复式销售配置</label>
		     </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">复式合买发起截止时间：</td>
		    <td align="left">
		    	<input id="compoundPeriodSales.shareEndInitTime" type="text" name="compoundPeriodSales.shareEndInitTime" value="<#if compoundPeriodSales.shareEndInitTime??>${compoundPeriodSales.shareEndInitTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('compoundPeriodSales.shareEndInitTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">复式自购发起截止时间：</td>
		    <td align="left">
		    	<input id="compoundPeriodSales.selfEndInitTime" type="text" name="compoundPeriodSales.selfEndInitTime" value="<#if compoundPeriodSales.selfEndInitTime??>${compoundPeriodSales.selfEndInitTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('compoundPeriodSales.selfEndInitTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
		  <tr class="trw">
		    <td class="trhemaigray" align="right">复式认购截止时间：</td>
		    <td align="left">
		    	<input id="compoundPeriodSales.endJoinTime" type="text" name="compoundPeriodSales.endJoinTime" value="<#if compoundPeriodSales.endJoinTime??>${compoundPeriodSales.endJoinTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
		    	<img onclick="WdatePicker({el:$('compoundPeriodSales.endJoinTime'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
		    </td>
		  </tr>
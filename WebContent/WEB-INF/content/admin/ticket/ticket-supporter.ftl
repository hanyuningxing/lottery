<title>出票管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="ticket"/>
<meta name="menuItem" content="supporter"/> 
<script type="text/javascript">  
     function updateTicketState(index,lotteryName,id){
       var isIE = document.all && window.external;
       if(id==null||id==""){
         	alert("该记录还未设置，请先保存后修改！");
         	return;
       }
       if (confirm('您确认修改<'+lotteryName+'>状态吗？')) {
         $.post("<@c.url value='/admin/ticket/ticket!updateTiketState.action' />",
                 {id:id},
                 function(returnedData, status){
                     if("success" == status){
                         spanObj = document.getElementById('span_state_'+index);
                         if (isIE){
                         	spanObj.outerText=returnedData;
                         }else{
                         	spanObj.textContent=returnedData;
                         }
                 	}  
                 }); 
         } 
     }
 </script>  
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">彩种</td>
      <td>出票商</td>  
      <td>启用状态</td>
    </tr>
    <#assign ticketSupporterArr=stack.findValue("@com.cai310.lottery.ticket.common.TicketSupporter@values()") />
    <#list lotterySupporterList as data>
    <form name="dataForm_${data_index}" action="${base}/admin/ticket/ticket!saveLotterySupporter.action" method="get">
        <input type="hidden" name="index" value="${data_index}">
        <#if data.id??><input type="hidden" name="id" value="${data.id!}"></#if>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	    	<td>${data.lotteryType.lotteryName}</td>
	    	<td> 
	    	<#if data.lotteryType=='SSQ'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='KLSF'>
	    	<#elseif data.lotteryType=='KLPK'>
	    	<#elseif data.lotteryType=='SEVENSTAR'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='EL11TO5'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='SSC'>
	    	<#elseif data.lotteryType=='SSL'>
	    	<#elseif data.lotteryType=='WELFARE3D'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='PL'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='DCZC'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='SEVEN'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='SFZC'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='LCZC'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='SCZC'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='WELFARE36To7'>
	    	<#elseif data.lotteryType=='DLT'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='SDEL11TO5'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	<#elseif data.lotteryType=='GDEL11TO5'>
	    	<#elseif data.lotteryType=='QYH'>
	    	<#elseif data.lotteryType=='TC22TO5'>
	    	<#elseif data.lotteryType=='JCZQ'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	       		
	    	<#elseif data.lotteryType=='JCLQ'>
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='LIANG'>checked</#if> value="10">&nbsp;量彩&nbsp;&nbsp;&nbsp;
	    	<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter=='CAI310'>checked</#if> value="6">&nbsp;本地&nbsp;&nbsp;&nbsp;	    	
	    		<input type="submit" name="submitForm" value="保 存"/>
	    	</#if>
	    	<!--
	    	<#list ticketSupporterArr as ts>
	    		<input type="radio" name="supporters[${data_index}]" <#if data.ticketSupporter?? && data.ticketSupporter==ts>checked</#if> value="${ts_index}">&nbsp;${ts.supporterName}&nbsp;&nbsp;&nbsp;
	    	</#list>
	    	<input type="submit" name="submitForm" value="保 存"/>
	    	-->
	    	</td>
	    	<td><a href="#" onclick="updateTicketState('${data_index}','${data.lotteryType.lotteryName}','${data.id!}');"><span class="colorBlack" id="span_state_${data_index}" style='cursor:pointer'><#if data.usable>开启中<#else>关闭中</#if></span></a></td></td>
    	</tr>
    </form>	
    </#list>
  </table>
</body>
</html>
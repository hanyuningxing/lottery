<title>出票管理</title>
<meta name="menu" content="ticket"/>
<meta name="menuItem" content="ticketList"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>

<script type="text/javascript">
	 function pauseSend(ticketId){
	 	if(ticketId==null||ticketId==""){
         	alert("不能识别你操作的记录，标识为空！");
         	return;
       }
       var pauseUrl="${base}/admin/ticket/ticket!pauseSend.action?ticketId="+ticketId;
	   var buttonObj = document.getElementById('btn_'+ticketId+'_pause');
	   if (confirm('您确定<'+ticketId+'>要暂停出票吗？')) {
		  	try{
		  		new Request({
					    url: pauseUrl,
						onSuccess: function(responseText, responseXML){
						   var jsonObj = JSON.decode(responseText);
						   var msg=jsonObj["msg"];
						   if(jsonObj["success"]){
						      buttonObj.disabled=true;
						      document.getElementById("span_updateSupporter_"+ticketId).style.display="";
						   }else{
						      window.alert(msg);
						   }
						},
						onFailure: function(instance){
							buttonObj.disabled=false;
							window.alert("暂停操作异常！");
						}
				   }).get({'__t=':new Date().getTime()});
		  	}catch(e){
		  		buttonObj.disabled=false;
		  		window.alert("暂停操作异常！");
		  	}
		  }
		  return false;
		}

     function resetTicketSupporter(ticketId){
       var isIE = document.all && window.external;
       if(ticketId==null||ticketId==""){
         	alert("不能识别你操作的记录，标识为空！");
         	return;
       }
       var supporterHtmlId = "supporter_"+ticketId;
       var supporterName = document.getElementById(supporterHtmlId).options[document.getElementById(supporterHtmlId).selectedIndex].text;
	   var supporterID = document.getElementById(supporterHtmlId).value;

	  var supporterName_old = document.getElementById('span_ticketSupporter_'+ticketId).innerHTML;
	  if(supporterName_old==supporterName){
	  	alert("重复设置出票方，请确认您的操作！");
	  	return false;
	  }
	  var updateUrl="${base}/admin/ticket/ticket!resetLotterySupporter.action?ticketId="+ticketId+"&ticketSupporter="+supporterID;
	  var buttonObj = document.getElementById('btn_'+ticketId);
	  var buttonObj_pause = document.getElementById('btn_'+ticketId+'_pause');
	  if (confirm('您确定<'+ticketId+'>要切换为<'+supporterName+'>吗？')) {
	  	buttonObj.disabled=true;
	  	try{
	  		new Request({
				    url: updateUrl,
					onSuccess: function(responseText, responseXML){
					   var jsonObj = JSON.decode(responseText);
					   var msg=jsonObj["msg"];
					   if(jsonObj["success"]){
					      var spanObj = document.getElementById('span_ticketSupporter_'+ticketId);
					      if (isIE){
	                     	spanObj.outerText=msg;
	                      }else{
	                     	spanObj.textContent=msg;
	                      }
	                      buttonObj_pause.disabled=false;
	                      document.getElementById("span_updateSupporter_"+ticketId).style.display="none";
					   }else{
					      window.alert(msg);
					   }
					   buttonObj.disabled=false;
					},
					onFailure: function(instance){
						buttonObj.disabled=false;
						window.alert("重置异常！");
					}
			   }).get({'__t=':new Date().getTime()});
	  	}catch(e){
	  		buttonObj.disabled=false;
	  		window.alert("重置异常！");
	  	}
	  }
	  return false;
	}	
	
	function selectBatchTicket(obj){
		var checked = obj.checked;
		var ticketArr = document.getElementsByName("ticketIds");
		for(var i = 0; i < ticketArr.length; i++){
			ticketArr[i].checked = checked;
		}
	}
	
	function cleanSelectedBatchTicket(){
		document.getElementById("selectBatchTicket").checked=false;
		var ticketArr = document.getElementsByName("ticketIds");
		for(var i = 0; i < ticketArr.length; i++){
			ticketArr[i].checked = false;
		}
	}
	
	function findSelectedTicketIds(){
		var ticketArr = document.getElementsByName("ticketIds");
		var ticketIds = '';
		for(var i = 0; i < ticketArr.length; i++){
			var ticketObj = ticketArr[i];
			if(ticketObj.checked){
				ticketIds+=ticketObj.value+',';
			}
		}
		if(ticketIds!=''){
			ticketIds=ticketIds.substr(0, ticketIds.length-1);
		}
		return ticketIds;
	}
	
	function pauseSendOfBatch(){
		var ticketIds = findSelectedTicketIds();
		if(ticketIds==''){
			alert("请选择你要操作的票！");
			return;
		}
		var updateUrl="${base}/admin/ticket/ticket!pauseSendOfBatch.action?ticketIds="+ticketIds;
		if (confirm('您确定暂停系统发送所选的票吗？')) {
		  	try{
		  		new Request({
					    url: updateUrl,
						onSuccess: function(responseText, responseXML){
						   var jsonObj = JSON.decode(responseText);
						   var msg=jsonObj["msg"];
						   if(jsonObj["success"]){
						   	  var selectedTicketIdArr = findSelectedTicketIds().split(",")
						   	  for(var i = 0; i < selectedTicketIdArr.length; i++){
						   	  	var ticketId = selectedTicketIdArr[i];
						   	  	var buttonObj_pause = document.getElementById('btn_'+ticketId+'_pause');
						   	  	buttonObj_pause.disabled=true;
						   	  	document.getElementById("span_updateSupporter_"+ticketId).style.display="";
						   	  }
						   }else{
						      window.alert(msg);
						   }
						},
						onFailure: function(instance){
							window.alert("操作异常！");
						}
				   }).get({'__t=':new Date().getTime()});
		  	}catch(e){
		  		window.alert("操作异常！");
		  	}
	  }
	  return false;
	}
	
	
	function resetTicketSupporterOfBatch(){
		var supporterHtmlId = "supporter_batch";
        var supporterName = document.getElementById(supporterHtmlId).options[document.getElementById(supporterHtmlId).selectedIndex].text;
	    var supporterID = document.getElementById(supporterHtmlId).value;
		var ticketIds = findSelectedTicketIds();
		if(ticketIds==''){
			alert("请选择你要操作的票！");
			return;
		}
	    var selectedTicketIdArr = findSelectedTicketIds().split(",");
   	    for(var i = 0; i < selectedTicketIdArr.length; i++){
   	  	  var ticketId = selectedTicketIdArr[i];
   	  	  var supporterName_old = document.getElementById('span_ticketSupporter_'+ticketId).innerHTML;
   	  	  if(supporterName_old==supporterName){
		  	alert(ticketId+":重复设置出票方，请确认您的操作！");
		  	return false;
		  }
		}
						   	  	
	    var updateUrl="${base}/admin/ticket/ticket!resetTicketSupporterOfBath.action?ticketIds="+ticketIds+"&ticketSupporter="+supporterID;
	    var btn_batchObj = document.getElementById('btn_batch');
	    
		  if (confirm('您确定批量切换为<'+supporterName+'>吗？')) {
		  	btn_batchObj.disabled=true;
		  	reset_btn_batch(true);
		  	try{
		  		new Request({
					    url: updateUrl,
						onSuccess: function(responseText, responseXML){
						   var jsonObj = JSON.decode(responseText);
						   var msg=jsonObj["msg"];
						   if(jsonObj["success"]){
						   	  for(var i = 0; i < selectedTicketIdArr.length; i++){
						   	  	var ticketId = selectedTicketIdArr[i];
						   	  	var spanObj = document.getElementById('span_ticketSupporter_'+ticketId);
						   	  	if (isIE){
			                     	spanObj.outerText=msg;
			                    }else{
			                     	spanObj.textContent=msg;
			                    }
			                    var buttonObj_pause = document.getElementById('btn_'+ticketId+'_pause');
			                    buttonObj_pause.disabled=false;
			                    document.getElementById("span_updateSupporter_"+ticketId).style.display="none";
			                    cleanSelectedBatchTicket();
						   	  }
						   }else{
						      window.alert(msg);
						   }
						   btn_batchObj.disabled=false;
						},
						onFailure: function(instance){
							btn_batchObj.disabled=false;
							window.alert("重置异常！");
						}
				   }).get({'__t=':new Date().getTime()});
		  	}catch(e){
		  		btn_batchObj.disabled=false;
		  		window.alert("重置异常！");
		  	}
		  }
		  return false;
	}
	
	function reset_btn_batch(disabled){
		var ticketIds = findSelectedTicketIds();
		if(ticketIds=='')return;
		var selectedTicketIdArr = findSelectedTicketIds().split(",")
		for(var i = 0; i < selectedTicketIdArr.length; i++){
			var ticketId = selectedTicketIdArr[i];
			var buttonObj_pause = document.getElementById('btn_'+ticketId+'_pause');
			buttonObj_pause.disabled=disabled;
		}
		return;
	}
 </script> 

<form name="queryForm" action="${base}/admin/ticket/ticket!ticketList.action" method="get">
     <#assign ticketSupporterArr=stack.findValue("@com.cai310.lottery.ticket.common.TicketSupporter@values()") />
     <select name="ticketSupporter">
		    <option value="">全部出票商</option>
			 <#list ticketSupporterArr as supporter>
			    <option <#if ticketSupporter?? && ticketSupporter==supporter>selected="selected"</#if> value="${supporter}">${supporter.supporterName}</option>
			 </#list>
     </select>
     &nbsp;
       拆分方案编号:<input name="ticketId" value="<#if ticketId??>${ticketId!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          期号：<input name="periodNumber" value="<#if issueNumber??>${issueNumber!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />&nbsp;
          方案编号： <input name="schemeNumber" value="<#if schemeNumber??>${schemeNumber!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
     <#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
              	<select name="lotteryType" onchange="document.forms['queryForm'].submit();return false;">
					<option value="">全部彩种</option>
					<#list lotteryTypeArr as type>
					<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
	 </select>
	 <select name="ticketState" onchange="document.forms['queryForm'].submit();return false;">
	              <option value="">全部</option>	 
	              <option value="3" <#if ticketState?? && ticketState=="3">selected="selected"</#if>>暂停</option>             
                  <option value="2" <#if ticketState?? && ticketState=="2">selected="selected"</#if>>委托</option>
                  <option value="0" <#if ticketState?? && ticketState=="0">selected="selected"</#if>>失败</option>
                  <option value="1" <#if ticketState?? && ticketState=="1">selected="selected"</#if>>成功</option>
      </select>
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
	  &nbsp;&nbsp;<span style="color:red;">(未成功的票可重置出票商)</span>
</form> 
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td width="80" height="22">拆分方案编号</td>
      <td width="100">所属方案号</td>
      <td width="80">期号</td>
      <td width="100">彩种</td>
      <td width="60">投注方式</td>
      <td width="100">方案金额</td>
      <td width="80">出票状态</td>
      <td width="180">电子票号</td>
      <td width="130">发票时间</td>
      <td width="130">最后状态修改时间</td>
      <td>错误码</td>
      <td width="100">出票商</td>
      <td width="260">操作</td>
      <td width="50">批量 <input type="checkbox" id="selectBatchTicket" name="selectBatchTicket" onclick="selectBatchTicket(this);"/></td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as ticket>
	    	<#if ticket_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
	    	<#assign ticketIsSuccess = false/>
	    	<#if ticket.stateCode?? && ticket.stateCode=="1">
	    		<#assign ticketIsSuccess = true/>
	    	</#if>
	    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
		      <td>${ticket.id}</td>
		      <td><a href="${base}/admin/ticket/ticket!gotoScheme.action?schemeNumber=${ticket.schemeNumber}" target="_blank">${ticket.schemeNumber}</a></td>
		      <td>${ticket.periodNumber}</td>
		      <td>${ticket.lotteryType.lotteryName}</td>
		      <td>${ticket.mode.modeName}</td>
		      <td>${ticket.schemeCost}</td>
		      <td><#if ticket.stateCode?? && ticket.stateCode=="0">委托中
		          <#elseif ticket.stateCode?? && ticket.stateCode=="1">成功
		          <#elseif ticket.stateCode?? && ticket.stateCode=="7">限号
		          <#elseif !ticket.stateCode?? || ticket.stateCode=="">未出票
		          <#else>失败</#if>
		      </td>
		      <td>${ticket.remoteTicketId!}</td>
		      <td>
		        <#if ticket.sendTime??>${ticket.sendTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
		      </td>
		      <td>
		          <#if ticket.stateModifyTime??>${ticket.stateModifyTime?string('yyyy-MM-dd HH:mm:ss')}</#if>
		      </td>
		      <td>${ticket.stateCodeMessage!}</td>
		      <td><span id="span_ticketSupporter_${ticket.id}"><#if ticket.ticketSupporter??>${ticket.ticketSupporter.supporterName!}</#if></td>
		      <td align="left">&nbsp;
		      	<#if !ticketIsSuccess>
		     		<button id="btn_${ticket.id}_pause" type="button" onclick="pauseSend('${ticket.id}');" <#if ticket.isPause()>disabled</#if>>暂停出票</button>&nbsp;&nbsp;&nbsp;&nbsp;
		     		<span id="span_updateSupporter_${ticket.id}" <#if !ticket.isPause()>style="display:none"</#if>>
				     	<select id="supporter_${ticket.id}" name="ticketSupporter">
						 <#list ticketSupporterArr as supporter>
						 	<#if supporter=='CAI310' || supporter=='LIANG'>
						    	<option <#if ticket.ticketSupporter?? && ticket.ticketSupporter==supporter>selected="selected"</#if> value="${supporter}">${supporter.supporterName}</option>
						    </#if>
						 </#list>
				     	</select>
			     		&nbsp;&nbsp;<button id="btn_${ticket.id}" type="button" onclick="resetTicketSupporter('${ticket.id}');">切换出票商</button>
			     	</span>
		      	</#if>&nbsp;	      	
		      	</td>
		      	<td><#if !ticketIsSuccess><input type="checkbox" name="ticketIds" value="${ticket.id}"/></#if></td>
			</tr>
    	</#list>
    	<tr class="trw">
    		<td height="50" colspan="11" style="background-color:#AFDDDD;"></td>
    		<td colspan="3" style="background-color:#5FADAD;">
    			<button id="btn_batch_pause" type="button" onclick="pauseSendOfBatch();">批量暂停出票</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<select id="supporter_batch" name="supporter_batch">
					 <#list ticketSupporterArr as supporter>
					 	<#if supporter=='CAI310' || supporter=='LIANG'>
					    	<option value="${supporter}">${supporter.supporterName}</option>
					    </#if>
					 </#list>
		     	</select>
    		<button id="btn_batch" type="button" onclick="resetTicketSupporterOfBatch();">批量切换出票商</button></td>
    	</tr>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="14">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
</body>
</html>
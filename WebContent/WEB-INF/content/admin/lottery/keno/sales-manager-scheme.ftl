<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery}"/>
	<script type="text/javascript">
		function printInfoMethod(schemeNum){
				      var PrintCostTd=document.getElementById(schemeNum+"PrintCostTd");
				      var PrizeTd=document.getElementById(schemeNum+"PrizeTd");
				      var ReturnPrizeTd=document.getElementById(schemeNum+"ReturnPrizeTd");
					  var printInfoUrl="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!printInfo?schemeNum="+schemeNum;
					  new Request({
							 url: printInfoUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      if(null!=jsonObj["printCost"]){
								          PrintCostTd.innerHTML=jsonObj["printCost"];
								      }
								      if(null!=jsonObj["prize"]){
								          PrizeTd.innerHTML=jsonObj["prize"];
								      }
								      if(null!=jsonObj["returnPrize"]){
								          ReturnPrizeTd.innerHTML=jsonObj["returnPrize"];
								      }
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					  return false;
		}
		function updateWinScheme(periodNumber){
					  var updateWinSchemeUrl="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!updateWinScheme?periodNumber="+periodNumber;
					  new Request({
							 url: updateWinSchemeUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      window.alert("操作成功");
								      window.location.reload();
								   }else{
								      window.alert(jsonObj["msg"]);
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					  return false;
		}
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
		function cancelScheme(schemeNum,id){
		  var closeUrl="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!cancelScheme.action?id="+id;
		  if (confirm("您确定把方案"+schemeNum+"撤单退款吗？")) {
		            new Request({
				    url: closeUrl,
					onSuccess: function(responseText, responseXML){
					   var jsonObj = JSON.decode(responseText);
					   if(jsonObj["success"]){
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					      window.location.reload();
					   }else{
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					   }
					}
			}).get({'__t=':new Date().getTime()});
		  }
		  return false;
		}
		
		function batchForcePrint(schemeNum,id){
		  var closeUrl="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!batchForcePrint.action?id="+id;
		  if (confirm("您确定把方案"+schemeNum+"强制出票吗？")) {
		            new Request({
				    url: closeUrl,
					onSuccess: function(responseText, responseXML){
					   var jsonObj = JSON.decode(responseText);
					   if(jsonObj["success"]){
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					      window.location.reload();
					   }else{
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					   }
					}
			}).get({'__t=':new Date().getTime()});
		  }
		  return false;
		}
		
		
		//统计销售总量跟中奖金额
		function onStat(){
			var startDate = document.getElementById("queryForm.startDate").value;
			var endDate = document.getElementById("queryForm.endDate").value;
			if(null==startDate||""==startDate){
				alert("请选择开始时间");
				return false;
			}
			if(null==endDate||""==endDate){
				alert("请选择截止时间");
				return false;
			}
			var statUrl="${base}/admin/lottery/keno/${lottery.key}/sales-manager!stat.action?queryForm.startDate="+startDate+"&&queryForm.endDate="+endDate;
					  new Request({
							 url: statUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								   	  document.getElementById("totalSales").innerHTML="销售总量(已出票)："+jsonObj.totalSales;
								   	  document.getElementById("totalHits").innerHTML="中奖总额(已出票):"+jsonObj.totalHits;					
								   }else{
								   	  document.getElementById("totalSales").innerHTML="销售总量(已出票)：0";
								   	  document.getElementById("totalHits").innerHTML="中奖总额(已出票):0";					
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					  return false;		  
		}
	</script>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
</head>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/keno/${lottery.key}/sales-manager!list.action">${lottery.lotteryName}</a>  
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">${lottery.lotteryName}方案管理</span>
        </div>
     </div>
</div>
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
	<ul>
  	  <li>
  	  <label for="queryForm_periodNumber" style="font-weight:bold;">开始时间：</label>
  	  <input id="queryForm.startDate" type="text" name="queryForm.startDate" value="<#if queryForm.startDate??>${queryForm.startDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
	  <img onclick="WdatePicker({el:$('queryForm.startDate'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
  	  <label for="queryForm_periodNumber" style="font-weight:bold;">截止时间：</label>
  	  <input id="queryForm.endDate" type="text" name="queryForm.endDate" value="<#if queryForm.endDate??>${queryForm.endDate?string('yyyy-MM-dd HH:mm:ss')}</#if>" />
	  <img onclick="WdatePicker({el:$('queryForm.endDate'),startDate:'%y-%M-%d %H:%m:00',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
  	  <label for="queryForm_periodNumber" style="font-weight:bold;" id="totalSales">销售总量(已出票)：</label>
  	  
  	  <label for="queryForm_periodNumber" style="font-weight:bold;" id="totalHits">中奖总额(已出票):</label>  
  	  
  	  <a href="#" onclick="onStat();return false;"><img src="<@c.url value='/styles/adminDefault/images/ico_search.gif' />" border="0" align="absmiddle"/></a>
	  </li>
	</ul>
</div>
<form name="queryForm" action="<@c.url value='/admin/lottery/keno/${lottery.key}/sales-manager!scheme.action' />" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
	<ul>
  	  <li>
  	   <#if lotteryType=="SSC">
  	     <#assign sscPlayType=stack.findValue("@com.cai310.lottery.support.ssc.SscPlayType@values()") />
	  	  	<select name="queryForm.sscPlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list sscPlayType as playType>
	  	  	  <option value="${playType}" <#if queryForm?? && queryForm.sscPlayType?? && queryForm.sscPlayType==playType>selected="selected"</#if>>${playType.typeName}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	    
  	     <#if lotteryType=="EL11TO5">
  	     <#assign el11to5PlayType=stack.findValue("@com.cai310.lottery.support.el11to5.El11to5PlayType@values()") />
	  	  	<select name="queryForm.el11to5PlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list el11to5PlayType as playType>
	  	  	  <option value="${playType}" <#if queryForm?? && queryForm.el11to5PlayType?? && queryForm.el11to5PlayType==playType>selected="selected"</#if>>${playType.typeName}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	    
  	     <#if lotteryType=="QYH">
  	     <#assign qyhPlayType=stack.findValue("@com.cai310.lottery.support.qyh.QyhPlayType@values()") />
	  	  	<select name="queryForm.qyhPlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list qyhPlayType as playType>
	  	  	  <option value="${playType}" <#if queryForm?? && queryForm.qyhPlayType?? && queryForm.qyhPlayType==playType>selected="selected"</#if>>${playType.typeName}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	    
  	     <#if lotteryType=="SDEL11TO5">
  	     <#assign sdel11to5PlayType=stack.findValue("@com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType@values()") />
	  	  	<select name="queryForm.sdEl11to5PlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list sdel11to5PlayType as playType>
	  	  	  <option value="${playType}" <#if queryForm?? && queryForm.sdEl11to5PlayType?? && queryForm.sdEl11to5PlayType==playType>selected="selected"</#if>>${playType.typeName}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	  
  	  
  	    <#assign schemeStateArr=stack.findValue("@com.cai310.lottery.common.keno.SchemeState@values()") />
  	  	<select name="queryForm.state">
  	  	  <option value="">方案状态</option>
  	  	  <#list schemeStateArr as state>
  	  	  <option value="${state}" <#if queryForm?? && queryForm.state?? && queryForm.state==state>selected="selected"</#if>>${state.stateName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	
  	  	<#assign ticketStateArr=stack.findValue("@com.cai310.lottery.common.SchemePrintState@values()")/>
  	  	<select name="queryForm.ticketState">
  	  	  <option value="">出票状态</option>
  	  	  <#list ticketStateArr as ticketState>
  	  	  <option value="${ticketState}" <#if queryForm?? && queryForm.ticketState?? && queryForm.ticketState==ticketState>selected="selected"</#if>>${ticketState.stateName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	
  	  	<select name="queryForm.won">
  	  	  <option value="">是否中奖</option>
  	  	  <option value="true" <#if queryForm?? && queryForm.won?? && queryForm.won>selected="selected"</#if>>中奖</option>
  	  	  <option value="false" <#if queryForm?? && queryForm.won?? && !queryForm.won>selected="selected"</#if>>未中奖</option>
  	  	</select>&nbsp;
  	  	<select name="queryForm.prizeSended">
  	  	  <option value="">是否已派奖</option>
  	  	  <option value="true" <#if queryForm?? && queryForm.prizeSended?? && queryForm.prizeSended>selected="selected"</#if>>已派奖</option>
  	  	  <option value="false" <#if queryForm?? && queryForm.prizeSended?? && !queryForm.prizeSended>selected="selected"</#if>>未派奖</option>
  	  	</select>&nbsp;
  	  	
  	  </li>
      <li>
      	<label for="queryForm_periodNumber" style="font-weight:bold;">期号：</label><input id="queryForm_periodNumber" name="queryForm.periodNumber" value="<#if queryForm?? && queryForm.periodNumber??>${queryForm.periodNumber}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_schemeNumbers" style="font-weight:bold;">方案号：</label><input id="queryForm_schemeNumbers" name="queryForm.schemeNumbers" value="<#if queryForm?? && queryForm.schemeNumbers??>${queryForm.schemeNumbers}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_sponsorNames" style="font-weight:bold;">发起人：</label><input id="queryForm_sponsorNames" name="queryForm.sponsorNames" value="<#if queryForm?? && queryForm.sponsorNames??>${queryForm.sponsorNames}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
  		&nbsp;
      	<select name="pagination.pageSize">
  	  	  <option value="">每页记录数</option>
  	  	  <#list [20,50,100,200] as size>
			<option value="${size}" <#if pagination?? && pagination.pageSize == size>selected="selected"</#if>>每页${size}条</option>
		  </#list>
  	  	</select>
  	  	&nbsp;&nbsp;
  	  	<a href="#" onclick="onSubmitSearch();return false;"><img src="<@c.url value='/styles/adminDefault/images/ico_search.gif' />" border="0" align="absmiddle"/></a>
	  </li>
	</ul>
</div>
</form>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22">方案号</td>
		<td>期号</td>
		<td>用户名称</td>
		<td>单复式</td>
		<td>投注内容</td>
		<td>注数</td>
		<td>倍数 </td>
		<td>金额</td>
		<td>是否中奖</td>
		<td>奖金</td>
		<td>奖金明细</td>
		<td>出票状态</td>
		<td>方案状态</td>
		<td>来源</td>
		<td>操作</td>
	</tr>
	<#if pagination??&& (pagination.result![])?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			<td>${data.schemeNumber!}</td>
			<td>${data.periodNumber!}</td>
			<td>${data.sponsorName!}</td>
			<td><#--${data.salesMode.modeName!}--></td>
			<td>${data.contentText!}</td>
			<td>${data.units!}</td>
			<td>${data.multiple!}</td>
			<td>${data.schemeCost!}</td>
			
			<td><#if data.won??&&data.won><font color="red">是</font></#if></td>
			<td>${data.prize!}</td>
			<td>${data.prizeDetail!}</td>
			<td>${data.schemePrintState.stateName}</td>
			<td>${data.state.stateName!}</td>
			<td><#if data.platform??>${data.platform.typeName!}<#else>网站</#if></td>
			<td>
			    <#if data.cut??&&data.cut><#else><a href="#" onclick="cancelScheme('${data.schemeNumber!}',${data.id!});">撤单退款</a>&nbsp;&nbsp;|&nbsp;&nbsp;</#if>
	  			<a href="<@c.url value='/${lottery.key}/scheme!show.action?id=' />${data.id!}" target="_blank">查看详细</a>
	  		    <#if data.cut??&&data.cut><#else><a href="#" onclick="batchForcePrint('${data.schemeNumber!}',${data.id!});">强制出票</a>&nbsp;&nbsp;|&nbsp;&nbsp;</#if>
			</td>
		</tr>
		</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="17">无记录</td>
	    </tr>
	</#if>
</table>
<#import "/WEB-INF/macro/pagination_admin.ftl" as b />
<@b.page />
</div>

<#if queryForm?? && queryForm.periodNumber??&&queryForm.periodNumber!=''>
<div><a href="#" onclick="updateWinScheme('${queryForm.periodNumber}');return false;">同步本期奖金</a></div>
</#if>
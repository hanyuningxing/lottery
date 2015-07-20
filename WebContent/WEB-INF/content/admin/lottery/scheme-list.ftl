<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if> 
 
<#assign searchUrl><@c.url value='/admin/lottery/${lotteryType.key}/scheme!list.action' /></#assign>
 
<head>
	<title>方案管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type="text/javascript">
		function printInfoMethod(schemeNum){
				      var PrintCostTd=document.getElementById(schemeNum+"PrintCostTd");
				      var PrizeTd=document.getElementById(schemeNum+"PrizeTd");
				      var ReturnPrizeTd=document.getElementById(schemeNum+"ReturnPrizeTd");
					  var printInfoUrl="${base}/admin/lottery/${lotteryType.key}/scheme!printInfo.action?schemeNum="+schemeNum;
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
		function printSpMethod(schemeId){
					  var sp_span=document.getElementById("sp_span_"+schemeId);
					  var sp_span_inner_html = sp_span.innerHTML;
					  sp_span.innerHTML='更新中..';
					  var printInfoUrl="${base}/admin/lottery/${lotteryType.key}/scheme!odds.action?schemeId="+schemeId;
					  new Request({
							 url: printInfoUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      window.alert("操作成功");
								       sp_span.innerHTML = sp_span_inner_html;
								      window.location.reload();
								   }else{
								      window.alert(jsonObj["msg"]);
								       sp_span.innerHTML = sp_span_inner_html;
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					 
					  return false;
		}
		function updateWinScheme(periodNumber){
					  var updateWinSchemeUrl="${base}/admin/lottery/${lotteryType.key}/scheme!updateWinScheme.action?periodNumber="+periodNumber;
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
		function selectFn(mode){
			var operateForm = document.forms['operateForm'];
			var checkedSchemeIds = operateForm.elements['checkedSchemeIds'];
			if(!checkedSchemeIds.length){
				checkedSchemeIds = [checkedSchemeIds];
			}
			var el;
			if(mode == 0){//清空
				for(var i=0,len=checkedSchemeIds.length;i<len;i++){
					el = checkedSchemeIds[i];
					if(el.checked === true)
						el.checked = false;
				}
			}else if(mode == 1){//全选
				for(var i=0,len=checkedSchemeIds.length;i<len;i++){
					el = checkedSchemeIds[i];
					if(el.checked === false)
						el.checked = true;
				}
			}else if(mode == 2){//反选
				for(var i=0,len=checkedSchemeIds.length;i<len;i++){
					el = checkedSchemeIds[i];
					el.checked = !el.checked;
				}
			}
		}
		
		function execBatch(el,url){
			var operateForm = document.forms['operateForm'];
			var checkedSchemeIds = operateForm.elements['checkedSchemeIds'];
			if(!checkedSchemeIds.length){
				checkedSchemeIds = [checkedSchemeIds];
			}
			var hasSelect = false;
			var tempObj;
			for(var i=0,len=checkedSchemeIds.length;i<len;i++){
				tempObj = checkedSchemeIds[i];
				if(tempObj.checked === true){
					hasSelect = true;
					break;
				}
			}
			if(hasSelect === false){
				window.alert('请选择您要操作的方案！');
				return false;
			}
			
			if(window.confirm('您确认要执行【'+el.value+'】操作吗？')){
				operateForm.action = url;
				return true;	
			}
			return false;
		}
		
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
	</script>
</head>

<@com.displaySimpleNav menuObj menuItemObj true>方案管理</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">方案管理</span></div>
</div>

<form name="queryForm" action="${searchUrl}" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
	<ul>
  	  <li>
  	    <#if lotteryType=="SFZC">
  	     <#assign sfzcPlayType=stack.findValue("@com.cai310.lottery.support.zc.PlayType@values()") />
	  	  	<select name="queryForm.sfzcPlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list sfzcPlayType as playType>
	  	  	  <option value="${playType}" <#if queryForm?? && queryForm.sfzcPlayType?? && queryForm.sfzcPlayType==playType>selected="selected"</#if>>${playType.text}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	    <#if lotteryType=="PL">
	  	  	<select name="queryForm.plPlayType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <option value="0" <#if queryForm?? && queryForm.plPlayType?? && queryForm.plPlayType==0>selected="selected"</#if>>排列5</option>
	  	  	  <option value="1" <#if queryForm?? && queryForm.plPlayType?? && queryForm.plPlayType==1>selected="selected"</#if>>排列3</option>
	  	  	 </select>&nbsp;
  	    </#if>
  	    
  	    <#if lotteryType=="WELFARE36To7">
  	     <#assign playTypeList=stack.findValue("@com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType@values()") />
	  	  	<select name="queryForm.welfare36to7playType">
	  	  	  <option value="">彩种玩法</option>
	  	  	  <#list playTypeList as playType>
	  	  	  <option value="${playType!}" <#if queryForm?? && queryForm.welfare36to7playType?? && queryForm.welfare36to7playType==playType>selected="selected"</#if>>${playType.typeName}</option>
	  	  	  </#list>
	  	     </select>&nbsp;
  	    </#if>
  	    
  	    <#assign salesModeArr=stack.findValue("@com.cai310.lottery.common.SalesMode@values()") />
  	  	<select name="queryForm.mode">
  	  	  <option value="">投注类型</option>
  	  	  <#list salesModeArr as mode>
  	  	  <option value="${mode}" <#if queryForm?? && queryForm.mode?? && queryForm.mode==mode>selected="selected"</#if>>${mode.modeName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	<#assign shareTypeArr=stack.findValue("@com.cai310.lottery.common.ShareType@values()") />
  	  	<select name="queryForm.shareType">
  	  	  <option value="">分享类型</option>
  	  	  <#list shareTypeArr as shareType>
  	  	  <option value="${shareType}" <#if queryForm?? && queryForm.shareType?? && queryForm.shareType==shareType>selected="selected"</#if>>${shareType.shareName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
  	  	<select name="queryForm.secretType">
  	  	  <option value="">保密类型</option>
  	  	  <#list secretTypeArr as secretType>
  	  	  <option value="${secretType}" <#if queryForm?? && queryForm.secretType?? && queryForm.secretType==secretType>selected="selected"</#if>>${secretType.secretName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	<#assign schemeStateArr=stack.findValue("@com.cai310.lottery.common.SchemeState@values()") />
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
  	  	<select name="queryForm.updateWon">
  	  	  <option value="">是否已开奖</option>
  	  	  <option value="true" <#if queryForm?? && queryForm.updateWon?? && queryForm.updateWon>selected="selected"</#if>>已开奖</option>
  	  	  <option value="false" <#if queryForm?? && queryForm.updateWon?? && !queryForm.updateWon>selected="selected"</#if>>未开奖</option>
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
  	  	<select name="queryForm.orderPriority">
  	  	  <option value="">排序等级</option>
  	  	  <#assign defaultPriority=stack.findValue("@com.cai310.lottery.Constant@ORDER_PRIORITY_DEFAULT") />
  	  	  <option value="${defaultPriority}" <#if queryForm?? && queryForm.orderPriority?? && queryForm.orderPriority=defaultPriority>selected="selected"</#if>>默认</option>
  	  	  <#assign topPriority=stack.findValue("@com.cai310.lottery.Constant@ORDER_PRIORITY_TOP") />
  	  	  <option value="${topPriority}" <#if queryForm?? && queryForm.orderPriority?? && queryForm.orderPriority=topPriority>selected="selected"</#if>>置顶</option>
  	  	  <#assign bottomPriority=stack.findValue("@com.cai310.lottery.Constant@ORDER_PRIORITY_BOTTOM") />
  	  	  <option value="${bottomPriority}" <#if queryForm?? && queryForm.orderPriority?? && queryForm.orderPriority=bottomPriority>selected="selected"</#if>>置底</option>
  	  	</select>
  	  	
  	  	
  	  </li>
      <li>
      	<label for="queryForm_periodNumber" style="font-weight:bold;">期号：</label><input id="queryForm_periodNumber" name="queryForm.periodNumber" value="<#if queryForm?? && queryForm.periodNumber??>${queryForm.periodNumber}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_schemeNumbers" style="font-weight:bold;">方案号：</label><input id="queryForm_schemeNumbers" name="queryForm.schemeNumbers" value="<#if queryForm?? && queryForm.schemeNumbers??>${queryForm.schemeNumbers}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_sponsorNames" style="font-weight:bold;">发起人：</label><input id="queryForm_sponsorNames" name="queryForm.sponsorNames" value="<#if queryForm?? && queryForm.sponsorNames??>${queryForm.sponsorNames}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
  		&nbsp;&nbsp;
  		<input id="queryForm_reserved" type="checkbox" name="queryForm.reserved" value="true" <#if queryForm?? && queryForm.reserved!false>checked="checked"</#if> /><label for="queryForm_reserved">保留</label>
  		&nbsp;&nbsp;
  		<input id="queryForm_hasBaodi" type="checkbox" name="queryForm.hasBaodi" value="true" <#if queryForm?? && queryForm.hasBaodi!false>checked="checked"</#if> /><label for="queryForm_hasBaodi">有保底</label>
		&nbsp;&nbsp;
		<#assign orderArr=stack.findValue("@com.cai310.lottery.web.controller.admin.lottery.SchemeQueryOrder@values()") />
  	  	<select name="queryForm.order">
  	  	  <option value="">搜索排序</option>
  	  	  <#list orderArr as order>
  	  	  <option value="${order}" <#if queryForm?? && queryForm.order?? && queryForm.order==order>selected="selected"</#if>>${order.text}</option>
  	  	  </#list>
  	  	</select>&nbsp;
      	<input id="queryForm_descOrder" type="checkbox" name="queryForm.descOrder" value="true" <#if queryForm?? && queryForm.descOrder!false>checked="checked"</#if> /><label for="queryForm_descOrder">倒序</label>
      	&nbsp;&nbsp;
      	<select name="pagination.pageSize">
  	  	  <option value="">每页记录数</option>
  	  	  <#list [20,50,100,200,300] as size>
			<option value="${size}" <#if pagination?? && pagination.pageSize == size>selected="selected"</#if>>每页${size}条</option>
		  </#list>
  	  	</select>
  	  	&nbsp;&nbsp;
  	  	<a href="#" onclick="onSubmitSearch();return false;"><img src="<@c.url value='/styles/adminDefault/images/ico_search.gif' />" border="0" align="absmiddle"/></a>
	  </li>
	</ul>
</div>
</form>

<form name="operateForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/scheme.action' />" method="post">
<#if queryForm?? && queryForm.periodNumber??>
	<input type="hidden" name="periodNumber" value="${queryForm.periodNumber}" />
</#if>
<div style="border: 1px solid #DCDCDC;padding:5px 0 0 0;border-top:1px dotted #ccc !important;;border-bottom:none;">
	<ul>
		<li>
			<span><strong>操作：</strong></span>
			&nbsp;&nbsp;
			<input type="button" onclick="selectFn(1);" value="全选"/>
			<input type="button" onclick="selectFn(0);" value="清空"/>
			<input type="button" onclick="selectFn(2);" value="反选"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!keepTop.action' />');" type="submit" value="置顶"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!keepBottom.action' />');" type="submit" value="置底"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!keepDefault.action' />');" type="submit" value="恢复默认排序"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!reserved.action' />');" type="submit" value="保留"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!cancelReserved.action' />');" type="submit" value="取消保留"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!resetUnUpdateWon.action' />');" type="submit" value="重置为未开奖"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!resetPriceUpdated.action' />');" type="submit" value="重置为未中奖"/>
			
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!batchCancelScheme.action' />');" type="submit" value="普通撤单"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!batchForceCancelScheme.action' />');" type="submit" value="强制撤单"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!batchRefundment.action' />');" type="submit" value="退款"/>
			<input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!batchForcePrint.action' />');" type="submit" value="强制出票"/>
		
		</li>
	</ul>
</div>

<div>
  <table align="center" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td width="30" height="22">选择</td>
      <td>方案号</td>
      <td>发起人</td>
      <td>投注类型</td>
      <td>分享类型</td>
      <td>发起时间</td>
      <td>方案金额</td>
      <td>倍数</td>
      <td>注数</td>
      <td>进度</td>
      <td>状态</td>
      <td>开奖状态</td>
      <td>开奖详情</td>
      <td>出票状态</td>
      <td>保留</td>
      <td>排序</td>
      <td>来源</td>
      <#if lotteryType.key=='jczq'||lotteryType.key=='jclq'><td>查看</td></#if>
    </tr>
    <#if pagination?? && (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<#assign baodiProgressRate=data.baodiProgressRate!0>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td align="center" height="22"><input type="checkbox" name="checkedSchemeIds" value="${data.id }" /></td>
	      <td><a href="<@c.url value='/admin/lottery/${lotteryType.key}/scheme!show.action?schemeId=${data.id }' />" >${data.schemeNumber}</a></td>
	      <td>${data.sponsorName}</td>
      	  <td>${data.mode.modeName}</td>
      	  <td>${data.shareType.shareName}</td>
	      <td>${data.createTime?string('yy-MM-dd HH:mm')}</td>
	      <td><font color="red">${data.schemeCost!}<font></td>
	      <td><font color="red">${data.multiple!}<font></td>
	      <td><font color="red">${data.units!}<font></td>
	      <td style="line-height:120%">${data.progressRate!}%<#if baodiProgressRate gt 0><br/>(<span class="rebchar">保${baodiProgressRate}%</span>)</#if></td>
	      <td>${data.state.stateName}</td>
	      <td>${data.wonStatusHtml}</td> 
      	  <td align="left"> 
      	  	<#if data.updatePrize>
      	  		${data.getPrizeDetailHtml(true)!}
      	  	<#elseif data.updateWon>
      	  		${data.getWonDetailHtml(true)!}
      	  	</#if>
      	  </td>
      	  <td>
      	     ${data.schemePrintState.stateName}
      	  </td>
	      <td><#if data.reserved><font color="red">√<font><#else><font color="gray">--<font></#if></td>
	      <td>${data.orderPriorityText!}</td>
	     <td><#if data.platform??>${data.platform.typeName!}<#else>网站</#if></td>
	      <#if lotteryType.key=='jczq'||lotteryType.key=='jclq'><td><span id= "sp_span_${data.id }"><a href="#" onclick="printSpMethod(${data.id });return false;">同步SP</a></span></td></#if>
	    </tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="17">无记录</td>
	    </tr>
    </#if>
  </table>
</div>
</form>
<#import "../../../macro/pagination_admin.ftl" as b />
<@b.page />
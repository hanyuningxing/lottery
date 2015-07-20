<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>
<head>
	<title>方案详情</title>
	<meta name="decorator" content="adminJquery" />
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<style>
		.bglan {
		    background: none repeat scroll 0 0 #A38175;
		    color: #FFFFFF;
		    text-align: center;
		}
		.bgbai {
		    background: none repeat scroll 0 0 #FFFFFF;
		    line-height: 22px;
		    text-align: center;
		}
		.bghui {
		    background: none repeat scroll 0 0 #FFFFF0;
		    line-height: 22px;
		    text-align: center;
		}
		.tablebgwhite {
		    background: none repeat scroll 0 0 #DCDCDC;
		}
		
	</style>
	<script type="text/javascript">
		function execBatch(el,url){
			var operateForm = document.forms['schemeForm'];
			var checkedSchemeIds = operateForm.elements['checkedSchemeIds'];
			if(!checkedSchemeIds.length){
				checkedSchemeIds = [checkedSchemeIds];
			}
			if(window.confirm('您确认要执行【'+el.value+'】操作吗？')){
				operateForm.action = url;
				return true;	
			}
			return false;
		}
	</script>
</head>

<@com.displaySimpleNav menuObj menuItemObj true><a href="<@c.url value='/admin/lottery/${lotteryType.key}/scheme!list.action?queryForm.periodNumber=${scheme.periodNumber}' />">方案管理</a>&nbsp;&nbsp;&gt;&gt;方案详情</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle"><span style="color:red;">[${scheme.schemeNumber}]</span>方案详情</span></div>
</div>

<div>
<form name="schemeForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/scheme!show.action?schemeId=${scheme.id}' />" method="post">
	<#assign baodiProgressRate=scheme.baodiProgressRate!0>
	<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">彩种：</td>
	    <td width="180" align="left">
	        ${scheme.lotteryType.lotteryName}
	         <#if lotteryType=="SFZC">
	                                                      玩法 【 ${scheme.playType.text!}】
  	    	 </#if>
  	    	  <#if lotteryType=="PL">
	                                                      玩法 【 ${scheme.playType.typeName!}】
  	    	 </#if>
	    </td>
	  	<td class="trhemaigray" align="right" width="80">期号：</td>
	    <td width="180" align="left">${scheme.periodNumber}</td>
	  	<td class="trhemaigray" align="right" width="80">投注类型：</td>
	    <td align="left">${scheme.mode.modeName}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">发起人：</td>
	    <td width="180" align="left">${scheme.sponsorName}</td>
	  	<td class="trhemaigray" align="right" width="80">发起时间：</td>
	    <td width="180" align="left">${scheme.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
	  	<td class="trhemaigray" align="right" width="80">分享类型：</td>
	    <td align="left">${scheme.shareType.shareName}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">单倍注数：</td>
	    <td width="180" align="left" >${scheme.units!'--'}</td>
	  	<td class="trhemaigray" align="right" width="80">倍数：</td>
	    <td width="180" align="left">${scheme.multiple!'--'}</td>
	  	<td class="trhemaigray" align="right" width="80">方案金额：</td>
	    <td align="left"">${scheme.schemeCost!'--'}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">已认购金额：</td>
	    <td width="180" align="left" >#{(scheme.subscribedCost!0);M2}</td>
	  	<td class="trhemaigray" align="right" width="80">剩余金额：</td>
	    <td width="180" align="left">#{(scheme.remainingCost!0);M2}</td>
	  	<td class="trhemaigray" align="right" width="80">保底金额：</td>
	    <td align="left"">#{(scheme.baodiCost!0);M2}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">状态：</td>
	    <td width="180" align="left">${scheme.state.stateName}</td>
	  	<td class="trhemaigray" align="right" width="80">上传：</td>
	    <td width="180" align="left"><#if scheme.uploaded>已上传<#else>未上传</#if></td>
	  	<td class="trhemaigray" align="right" width="80">出票：</td>
	    <td align="left"><#if scheme.sendToPrint>已发送到出票接口<#else>未出票
	    </#if>
	    <#if scheme.state.stateName=="成功"||scheme.state.stateName=="撤销"||scheme.state.stateName=="退款">
	    <#else>
	    <input onclick="return execBatch(this,'<@c.url value='/admin/lottery/${lotteryType.key}/scheme!batchForceCancelScheme.action' />');" type="submit" value="强制撤单"/>
	    </#if>
	    <input type="hidden" name="checkedSchemeIds" value="${scheme.id}"/>
	    <input type="hidden" name="schemeDetail" value="true"/>
	    <input type="hidden" name="schemeId" value="${scheme.id}"/>
	    </td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">方案内容：</td>
	    <td align="left" colspan="5"><#if contentText??>${contentText?replace('\r\n','<br/>')}<#else>${scheme.content!}</#if></td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">开奖状态：</td>
	    <td align="left" colspan="5">${scheme.wonStatusHtml!}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">出票状态：</td>
	    <td align="left" colspan="5">${scheme.schemePrintState.stateName!}</td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">中奖描述：</td>
	    <td align="left" colspan="5" class="rebchar">${scheme.getWonDetailHtml(true)!}</td>
	  </tr>	
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">奖金分配：</td>
	    <td align="left" colspan="5" class="rebchar">${scheme.getPrizeDetailHtml(true)!}</td>
	  </tr>	
	</table>
	</form>
</div>


<div style="padding-top:8px;">
	<div style="float:left;width:55%">
		<div style="padding-right:8px;">
			<div class="twonavgray" style="padding-left:15px;">
				<span class="chargraytitle">认购列表：</span>
			</div>
			<table width="100%" align="center" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
				<tr class="trlbrown">
					<td height="25">编号</td>
					<td>用户名</td>
					<td>金额</td>
					<td>时间</td>
					<td>奖金</td>
					<#--<td>操作</td>-->
				</tr>
				<#if subscriptionList?? && subscriptionList?size gt 0>
					<#list subscriptionList as data>
		    			<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
			    		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
							<td height="23">${data.id}</td>
							<td>${data.userName}</td>				
						  	<td>#{data.cost;M2}</td>
						  	<td>${data.createTime?string("MM-dd HH:mm")}</td>
							<td class="redredchar"><#if data.bonus??>#{data.bonus;M2}</#if></td>
							<#-- <td>
							</td>-->
						</tr>
					</#list>
				<#else>	
					<tr class="trw">
						<td colspan="6">无记录</td>
					</tr>
				</#if>
			</table>
		</div>
	</div>
	<div style="float:left;width:45%">
		<div>
			<div class="twonavgray" style="padding-left:15px;">
				<span class="chargraytitle">保底列表：</span>
			</div>
			<table width="100%" align="center" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
				<tr class="trlbrown">
					<td height="25">编号</td>
					<td>用户名</td>
					<td>金额</td>
					<td>时间</td>
					<#--<td>操作</td>-->
				</tr>
				<#if baodiList?? && baodiList?size gt 0>
					<#list baodiList as data>
		    		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		    		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
						<td height="23">${data.id}</td>
						<td>${data.userName}</td>				
					  	<td>#{data.cost;M2}</td>
					  	<td>${data.createTime?string("MM-dd HH:mm")}</td>
						<#--<td>
						</td>-->
					</tr>
					</#list>
				<#else>	
					<tr class="trw">
						<td colspan="5">无记录</td>
					</tr>
				</#if>	
			</table>	
		</div>
	</div>
</div>

<#if scheme.lotteryType.key=='jczq'||scheme.lotteryType.key=='jclq'>
<div style="padding-top:8px;">
	<div style="float:left;width:55%">
		<div style="padding-right:8px;">
			<div class="twonavgray" style="padding-left:15px;">
				<span class="chargraytitle">出票列表：</span>
			</div>
			<div id="extra-ticket-container"></div>          
			<script type="text/javascript">
			    var ticketUrl='<@c.url value="/${scheme.lotteryType.key}/scheme!viewTicketCombination.action?id=${scheme.id}" />';
			    $('#extra-ticket-container').load(ticketUrl,{_t:new Date().getTime()}, function() {
				});
			</script>
		</div>
	</div>
</div>
</#if>
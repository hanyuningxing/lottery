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
</head>

<@com.displaySimpleNav menuObj menuItemObj true><a href="<@c.url value='/admin/lottery/${lotteryType.key}/scheme!list.action?queryForm.periodNumber=${scheme.periodNumber}' />">方案管理</a>&nbsp;&nbsp;&gt;&gt;方案详情</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle"><span style="color:red;">[${scheme.schemeNumber}]</span>方案详情</span></div>
</div>

<div>
	<#assign baodiProgressRate=scheme.baodiProgressRate!0>
	<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">彩种：</td>
	    <td width="180" align="left">
	        ${scheme.lotteryType.lotteryName}
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
	    <td align="left"><#if scheme.sendToPrint>已发送到出票接口<#else>未出票</#if></td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="80">方案内容：</td>
	    <td align="left" colspan="5">
               <#if canViewDetail>
		              <#if scheme.playType??>[玩法：${scheme.playType.typeName!}]<br /></#if>
		              <#if scheme.mode=='COMPOUND'>
		                <#if scheme.compoundContentText??>
		                  <#if periodData??&&periodData.result??&&compoundContentDetail??>
							      ${compoundContentDetail!}
							     <#else>
							      ${(scheme.compoundContentText)!}
						 </#if>
		                </#if>
		              <#elseif scheme.mode=='SINGLE'>
		                <textarea  name="content" cols="60" rows="10" readonly="readonly" style="border:1px solid #A7CAED;">${scheme.content!}</textarea>
		              </#if>
		           <#else>${scheme.secretType.secretName!}
		       </#if>
	    </td>
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


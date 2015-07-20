<#assign frequent=false /><#--高频彩标志-->
<#if (scheme.lotteryType.category)?? && scheme.lotteryType.category=='FREQUENT'>
	<#assign frequent=true />
</#if>
<#assign currUser=loggedUser!'' />
<#assign canSubscribe=canSubscribe!false />
<form id="subscribeForm" action="<@c.url value="/${scheme.lotteryType.key}/scheme!subscribe.action" />" method="post" autocomplete="off">
	<input type="hidden" name="id" value="${scheme.id}" />
	<@block name="subscribeHiddenInput"></@block><#--其它隐藏参数 -->
	<table width="100%" border="0" cellspacing="0" cellpadding="7" align="center" class="b1">
	  <tr>
	    <td width="95" align="right" class="tdboldbggray hm_detaillef1px">方案发起人：</td>
	    <td valign="bottom" class="hm_detailrig1px">${scheme.sponsorName}</td>
	  </tr>
	  <tr>
	    <td width="95" align="right" class="tdboldbggray hm_detaillef1px">方案期号：</td>
	    <td valign="bottom" class="hm_detailrig1px">${scheme.periodNumber!}</td>
	  </tr>
	  <tr>
	    <td align="right" class="tdboldbggray hm_detaillef1px">方案状态：</td>
	    <td class="hm_detailrig1px">
	       <span class="redc"><#if scheme.unFullState>未满员<#else>${scheme.state.stateName}</#if></span>
	       (当前<span class=" redc">进度
	       <span class="jindu">#{scheme.progressRate!0;M2}%</span>
	       <#assign baodiProgressRate=scheme.baodiProgressRate!0 />
	       <#if baodiProgressRate gt 0><!--<span class="greenchar">+#{baodiProgressRate;M2}%(保)</span>--></#if>
	       </span>)
		</td>
	  </tr>
	    <#if periodData??&&periodData.result??>
	  <tr>
	    <td align="right" class="tdboldbggray hm_detaillef1px">开奖号码：</td>
	     <td align="left" class="hm_detailrig1px">
		      <span class=" redc">${periodData.result}</span>
		</td>
	  </tr>
	   </#if>
	  <tr>
	    <td align="right" class="tdboldbggray hm_detaillef1px">方案内容：</td>
	    <td align="left" class="hm_detailrig1px">
	    	<@block name="schemeContent">
				<#if canViewDetail>
					<#if scheme.mode=='COMPOUND'>
						<@block name="compoundSchemeContent">
						      ${(scheme.compoundContentText)!}
						</@block>
		  			<#elseif scheme.mode=='SINGLE'>
		  				<#if scheme.uploaded>
							<@block name="singleSchemeContent">
								<textarea name="content" cols="60" rows="10" readonly="readonly" style="border:1px solid #A7CAED;">${(scheme.content)!}</textarea>
							</@block>
		  				<#elseif currUser!='' && currUser.id == scheme.sponsorId>
		  					<a href="<@c.url value="/${scheme.lotteryType.key}/scheme!editUpload.action?schemeNumber=${scheme.schemeNumber}" />">上传方案内容</a>
		  				<#else>
		  					未上传
		  				</#if>
		  			</#if>
				<#else>
					${(scheme.secretType.secretName)!}
				</#if>
	    	</@block>
	    </td>
	  </tr>
	 
	 
	  <tr>
	    <td align="right" class="tdboldbggray hm_detaillef1px">方案信息：</td>
	    <td  align="left" class="hm_detailrig1px"><table width="100%" border="0" cellspacing="1" cellpadding="0" class="hmTable">
		    <tr align="center" class="trgray">
		      <td width="55">总金额 </td>
		      <td width="85">注数</td>
		      <td>倍数</td>
		      <#if !frequent>
				<td>发起人提成</td>
				<td>最低认购</td>
		      </#if>
		      <td>已认购</td>
		      <#if !frequent>
		      	<td>保底金额</td>
		      </#if>
		      <td>出票状态 </td>
			</tr>         
	    	<tr align="center" onmouseout="this.className='trlightblue'" onmouseover="this.className='trhover'" class="trlightblue">
		        <td class="redc" align="center" style="border-left: 0pt none;">￥${scheme.schemeCost!'--'}</td>
		        <td align="center">${scheme.units!'-'}</td>
		        <td align="center">${scheme.multiple!'-'}</td>
		        <#if !frequent>
					<td align="center"><#if scheme.commissionRate?? && scheme.commissionRate gt 0>${(scheme.commissionRate*100)?string('#')}%<#else>无佣金</#if></td>
		            <td align="center"><#if scheme.minSubscriptionCost??>#{(scheme.minSubscriptionCost!'-');M2}元<#else>-</#if></td>
		        </#if>  
		        <td align="center"><#if scheme.subscribedCost??>#{(scheme.subscribedCost!'-');M2}元<#else>-</#if></td>
		        <#if !frequent>
		        	<td align="center"><#if scheme.baodiCost??>#{(scheme.baodiCost!'-');M2}元<#else>-</#if></td>
		        </#if>   
		        <td align="center"><span style="color: green;"><#if scheme.state=="CANCEL"||scheme.state=="REFUNDMENT">未出票<#else>${scheme.schemePrintState.stateName!}</#if></span></td>
	      	</tr>
		</table></td>
	  </tr>
	  <#if !frequent>
		<tr>
			<td align="right" class="tdboldbggray hm_detaillef1px">合买用户：</td>
			 
			<td align="left" class="hm_detailrig1px" id="extra-data-menu">
				<a onclick="return false" onmouseover="chgExtraDataMenu(this);return false;" id="subscriptionObj" href="<@c.url value="/${scheme.lotteryType.key}/scheme!subscriptionList.action?id=${scheme.id}" />">认购详情</a>&nbsp;&nbsp;
				<a onclick="return false" onmouseover="chgExtraDataMenu(this);return false;" href="<@c.url value="/${scheme.lotteryType.key}/scheme!baodiList.action?id=${scheme.id}" />">保底详情</a>&nbsp;&nbsp;
				<@block name="ticketContent"></@block>
				<a onclick="hiddenExtraDataMenu();return false;" href="#">关闭详细列表</a>
			</td>
		</tr>
		<tr style="display:none" id="extra-data-tr">
			<td align="right" class="tdboldbggray hm_detaillef1px">&nbsp;</td>
			<td align="left" class="hm_detailrig1px" id="extra-data-container"></td>          
		</tr>
		<script type="text/javascript">
		        var selectedClass = 'redboldchar';
	            var unSelectedClass = '';
			    var subscriptionObj = document.getElementById('subscriptionObj');
			    $('#extra-data-container').load(subscriptionObj.href,{_t:new Date().getTime()}, function() {
					$('#extra-data-menu  a[class="' + selectedClass + '"]').attr('class', unSelectedClass);
					subscriptionObj.className = selectedClass;
					document.getElementById('extra-data-tr').style.display="";
					var count = document.getElementById('extra-count').innerHTML;
				});
		</script>
	  </#if>
	  <#if !canSubscribe>
		<#assign successWon=scheme.successWon /><#-- 方案成功中奖 -->
		<#assign unSuccessWon=scheme.unSuccessWon /><#-- 方案不成功流产 -->
		<#if successWon || unSuccessWon>
			<#if scheme.updatePrize><#-- 已更新奖金详情 -->
				<#assign prizeDetailHtml=scheme.getPrizeDetailHtml(canViewDetail)!'' />
				<#if prizeDetailHtml!=''>
					<tr>
			    		<td align="right" class="tdboldbggray hm_detaillef1px"><#if successWon>奖金分配：<#else>中奖奖金：</#if></td>
			    		<td align="left" class="hm_detailrig1px"><span <#if successWon>class="redboldchar"</#if>>${prizeDetailHtml}</span></td>
				  	</tr>
				</#if>
			<#else>
				<#assign wonDetailHtml=scheme.getWonDetailHtml(canViewDetail)!'' />
				<#if wonDetailHtml!=''>
					<tr class="trw">
						<td align="right" class="tdboldbggray hm_detaillef1px">中奖情况：</td>
						<td align="left" class="hm_detailrig1px"><span <#if successWon>class="redboldchar"</#if>>${wonDetailHtml}</span></td>
					</tr>
				</#if>
			</#if>
		</#if>
	  <#else>
	  	<tr>
		    <td align="right" class="tdboldbggray hm_detaillef1px">合买状态：</td>
		    <td align="left" class="hm_detailrig1px">
				<div style="float: left; padding-left: 15px;">
	          		<span class="redboldchar">合买进行中...</span>
	          		<#if scheme.sendToPrint><span class="greenchar">[网站保底]</span></#if>
	                                                当前进度：
	            	<span class="redboldchar">#{(scheme.progressRate!0);M2}% </span>，
	            	<!--保底 <span class="redboldchar">#{(scheme.baodiProgressRate!0);M2}%</span> ，-->
	            	还有 <span class="redboldchar" id="span_remainingCost">#{(scheme.remainingCost!0);M2}</span> 元可认购 。
				</div>
			</td>          
		</tr>
		<tr>
			<td align="right" class="tdboldbggray hm_detaillef1px">您的认购记录：</td>
			<td align="left" class="hm_detailrig1px">
	            <#assign userSubscribedCost=userSubscribedCost!0 />
	            <#if userSubscribedCost gt 0>
	               <span class="redboldchar">此方案您认购了<span class="rebredchar">#{userSubscribedCost;M2}</span>元。</span>
	          	<#else>
	            	<span>此方案您未认购。</span>
	          	</#if>
	          	<#if currUser!='' && account?? && canSubscribe>
	          		<#--您的余额为：<span class="redboldchar">￥<span id="userRemainMoney">#{(account.balance!0);M2}</span></span> 元，-->最多可认购 
	          		<span class="redboldchar" id="span_canSubscribedCost">#{(canSubscribedCost!0);M2}</span> 元 &nbsp;&nbsp;[<a href="<@c.url value="/user/fund!payPer.action"/>" target="_blank"><font color="red">充值</font></a>]
	          	</#if>
			</td>       
		</tr>
	  	<#if scheme.minSubscriptionCost??>
		 	<tr>
			    <td align="right" class="tdboldbggray hm_detaillef1px">最低认购：</td>
			    <td align="left" class="hm_detailrig1px"><span id="span_minSubscriptionCost" class="redboldchar">#{scheme.minSubscriptionCost;M2}</span> 元。</td>       
		 	</tr>
	  	</#if>
	  	<#if needPassword!false>
		 	<tr>
			    <td align="right" class="tdboldbggray hm_detaillef1px">认购密码：</td>
			    <td align="left" class="hm_detailrig1px"><input type="password"  class="txtbox color_fff" name="subscribeForm.password" value="" maxlength="10" /></td>       
		 	</tr>
	  	</#if>
	  	<tr>
			<td align="right" class="tdboldbggray hm_detaillef1px">抢购时间：</td>
			<td align="left" class="hm_detailrig1px">
		    	<span class="redboldchar" id="countdown_view"></span>
		    	<#if timerMap??>
					<script type="text/javascript">
						createTimeWork({
							elId:'countdown_view',
							day:${timerMap.day!0},
							hour:${timerMap.hour!0},
							minute:${timerMap.minute!0},
							second:${timerMap.second!0},
						  		showType:2,
						  		msgFront:'距离截止时间还有：',
						  		endMsg:'已截止'
					  	});
					</script>
			    <#else>
					已截止
			    </#if>
			</td>          
	  	</tr>
	  	<tr>
			<td align="right" class="tdboldbggray hm_detaillef1px">我要认购：</td>
			<td align="left" class="hm_detailrig1px"><input type="text" tabindex="2"  maxlength="6" style="ime-mode: disabled; width: 80px;" name="subscribeForm.subscriptionCost" onkeydown="number_check(this,event,0)" oncontextmenu="return false;" autocomplete="off" />元</td>       
	  	</tr>
	  	<tr>
			<td align="right" class="tdboldbggray hm_detaillef1px">我要保底：</td>
			<td align="left" class="hm_detailrig1px"><input type="text" tabindex="2"  maxlength="6" style="ime-mode: disabled; width: 80px;" name="subscribeForm.baodiCost" onkeydown="number_check(this,event,0)" oncontextmenu="return false;" autocomplete="off" />元</td>       
	  	</tr>
	  	<tr>
	    	<td align="right" class="tdboldbggray hm_detaillef1px">确认认购：</td>
	    	<td align="left" class="hm_detaillef1px lineh25">
		      <div align="left" class="top5px">
		        <input type="checkbox" id="agree" checked="checked" />
		        <label for="agree" class="inputcheckbox graychar">我已阅读了《<a href="#" class="graychar"   onclick="openPurchaseAgreement();return false;">用户合买代购协议</a>》并同意其中条款</label>
		      </div>
		      <div class="all5px">
				<span id="span_subscribeForm_submit"><img style="cursor:pointer;" src="<@c.url value="/pages/images/bt_goumai.gif" />" onclick="submitSubscribeForm()" /></span>
		     	<span id="span_subscribeForm_wait" class="loadData" style="display: none;">正在提交数据，请稍等...</span>
		      </div>
		  	</td>
	  	</tr>
	  </#if>
	  <tr>
	    <td align="right" class="tdboldbggray hm_detaillef1px">方案描述： </td>
	    <td align="left" class="hm_detaillef1px">${scheme.description!}</td>
	  </tr>
	  <tr>
	    <td align="right" class="tdboldbggray">本页地址：</td>
	    <td align="left" class="hm_detailrig1px">
			<a id="scheme_link" href="<@c.url value="/${scheme.lotteryType.key}/scheme-${scheme.schemeNumber}.html" />"></a>
	     	<input id="scheme_link_input" type="text" size="65"  value=""/>
	     	<script type="text/javascript">document.getElementById("scheme_link_input").value=document.getElementById('scheme_link').href;</script>
	    	<input onclick="copyToClipboard(document.getElementById('scheme_link_input').value);" type="button" value="复制地址"/>
	    </td>
	  </tr>
	</table>
</form>
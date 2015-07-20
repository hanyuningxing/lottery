 <head>
	<@block name="title"></@block>
	<@block name="decorator"><meta name="decorator" content="tradeV1" /></@block>
	<@block name="style"></@block>
	<@block name="head">
		<script type="text/javascript" src="<@c.url value="/js/timer.js" />"></script>
	  	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
		<script type="text/javascript" src="<@c.url value="/js/lottery/scheme-showV1.js" />"></script>
		<script type="text/javascript" src="${base}/js/ZeroClipboard.js"></script>
	  	<@block name="addHead"></@block>
	</@block>
	<meta name="menu" content="${webapp.hmdt}"/>
</head>
<#assign canSubscribe=canSubscribe!false />
<!--main-->
<div class="w1000">
  <div class="fd_border">  
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="fd_trtop">
        <td class="a10px" width="80" align="center"><div class=" hm_logo hmlogo_jczq"></div></td>
        <td>
           <span class="fd_h1">
           	<#if scheme.playTypeWeb??>
           		${scheme.playTypeWeb.text}</span><#if scheme.playTypeWeb=='RENJIU'><span class="c14">(只中２场就有奖)</span></#if>
           	<#elseif scheme.playType?exists>
           		${scheme.playType.text}
           	<#else>
           		${lotteryType.lotteryName}
           	</#if></span><br />
           <span class="fd_gray">认购截止时间：<#if firstMatchTime??>${firstMatchTime?string("yyyy-MM-dd HH:mm")}</#if>&nbsp;&nbsp;<#if scheme.schemeNumber??>方案编号：${scheme.schemeNumber}</#if></span>
        </td>
        <td width="100"><a href="${base}/${lotteryType.key}/scheme!subList.action">返回合买列表>></a></td>
      </tr>
      <tr class="fd_trtop" >
        <td colspan="3" align="left" class=" l10 d20"><div class="fl"><b>方案基本信息</b></div>
          <div class="fr"><a href="${base}/phone/phone!index.action"><span class="red">下载手机客户端投注更方便</span></a></div></td>
      </tr>
      <!--方案进度-->
      <@block name="progress">
      <tr class="fd_trw" >
        <td align="center" class="fd_trg">进度信息</td>
        <#assign submitClass="node finished">
        <#assign submitProceClass="proce finished">
        <#assign costLockedClass="node actived">
        <#assign costLockedProceClass="proce doing">
        <#assign ticketClass="node wait">
        <#assign ticketProceClass="proce wait">
        <#assign updateResultClass="node wait">
        <#assign updateResultProceClass="proce wait">
        <#assign prizeClass="node wait">
        <#--已发送出票-->
        <#if scheme.sendToPrint>
        	<#assign costLockedClass="node finished">
	        <#assign costLockedProceClass="proce finished">
        	<#if scheme.schemePrintState=='SUCCESS' || scheme.schemePrintState=='FAILD'>	        	
	        	<#assign ticketClass="node finished">
        		<#assign ticketProceClass="proce finished">
        	<#elseif scheme.schemePrintState=='PRINT'>
        		<#assign ticketClass="node actived">
        		<#assign ticketProceClass="proce doing">
        	</#if>
        	
        	<#--已更新中奖，已开奖-->
        	<#if scheme.winningUpdateStatus=='NONE'>
	        	<#assign updateResultClass="node wait">
	        	<#assign updateResultProceClass="proce wait">
	        <#elseif scheme.winningUpdateStatus=='PRICE_UPDATED'>
	        	<#assign ticketClass="node finished">
	        	<#assign ticketProceClass="proce finished">
	        <#else>
	        	<#assign updateResultClass="node actived">
	        	<#assign updateResultProceClass="proce doing">
	        </#if>
	        <#--已派奖-->
	        <#if scheme.prizeSended>
	        	<#assign updateResultClass="node finished">
	        	<#assign updateResultProceClass="proce finished">
	        	<#assign prizeClass="node finished">
	        </#if>
        </#if>
        
        <td>
        	<div class="process" align="center" style="margin:15px 0;clear:both;">
            <div class="${submitClass}">
              <ul>
                <li class="tx1">提交方案</li>
                <li class="tx2">${scheme.createTime?string("MM-dd HH:mm")}</li>
              </ul>
            </div>
            <div class="${submitProceClass}"></div>
            <div class="${costLockedClass}">
              <ul>
                <li class="tx1"><#if scheme.state="CANCEL" || scheme.state="REFUNDMENT">方案已撤销<#else>资金冻结</#if></li>
                <li class="tx2"></li>
              </ul>
            </div>
            <div class="${costLockedProceClass}">
              <#if scheme.state=="UNFULL" && remainTime??>
              <div class="doing_tips">
                <div class="tips_con">认购截止还有:${remainTime}</div>
                <div class="tips_arrow"></div>
              </div>
              </#if>
            </div>
            <div class="${ticketClass}">
              <ul>
                <li class="tx1">${scheme.schemePrintState.stateName}</li>
                <li class="tx2"></li>
              </ul>
            </div>
            <div class="${ticketProceClass}"></div>
            <div class="${updateResultClass}">
              <ul>
                <li class="tx1">${scheme.winningUpdateStatus.statusName}</li>
                <li class="tx2"></li>
              </ul>
            </div>
            <div class="${updateResultProceClass}"></div>
            <div class="${prizeClass}">
              <ul>
                <li class="tx1">派奖</li>
                <li class="tx2"></li>
              </ul>
            </div>            
          </div>
        </td>
        <td>&nbsp;</td>
      </tr>
       <#if scheme.prizeSended>
       		<div style="position:absolute; width:204px; height:254px; background:url(${base}/V1/images/zhongjiang.png); left:830px; top:100px;">
		  		<div style="padding:112px 55px 0 35px; height:30px; line-height:30px; text-align:center; font-family:Arial, '微软雅黑'; color:#FFF; font-size:20px; font-weight:bold;">#{scheme.prizeAfterTax;M2}<span style="font-size:16px;">元</span></div>
		 	 </div>
	   </#if>
      
      </@block>
      <tr class="fd_trw" >
        <td align=" center" class="fd_trg">发起人信息</td>
        <td><span class="blue"><a href="${base}/blog/${scheme.sponsorId}/" target="_blank">${scheme.sponsorName}</a></span></td>
        <td></td>
      </tr>
      <tr class="fd_trw" >
        <td align=" center" class="fd_trg">方案信息</td>
        <td ><div class="tdbback">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="vtable">
              <tr class="v01tr">
                <td>总金额</td>
                <td>总注数</td>
                <td>倍数</td>
                <td>份数/每份</td>
                <td>发起人提成</td>
                <td>彩票标识</td>
                <td>保底金额</td>
                <td>购买进度</td>
              </tr>
              <tr class="v02tr">
                <td><span class="red">${scheme.schemeCost?string('￥###,###.00')}</span>元</td>              
                <td>${scheme.schemeCost/2}注</td>
                <td>${scheme.multiple}倍</td>
                <#assign copies=scheme.schemeCost/scheme.minSubscriptionCost/>
                <td>${copies}份/${scheme.minSubscriptionCost?string('###,###.00')}元</td>
                <td><span class="red">${scheme.commissionRate!0*100}%</span></td>
                <td><#if scheme.state=="CANCEL"||scheme.state=="REFUNDMENT">未出票<#else>${scheme.schemePrintState.stateName!}</#if></td>
                <td><#if scheme.baodiCost??>${scheme.baodiCost!0?string('￥###,###.00')}元<#else>未保底</#if></td>
                <td><span class="red">${scheme.progressRate}%</span></td>
              </tr>
            </table>
          </div>
          <div class="k10"></div>
          <!--方案内容补充说明-->
          <@block name="schemeInfo"></@block>
		 </td>
		 <td>&nbsp;</td>
      </tr>
      <tr class="fd_trw" >
	    <td align=" center" class="fd_trg">方案内容</td>
	    <td><@block name="content"></@block></td>
	    <td>&nbsp;</td>
	  </tr>
	  <#if !canSubscribe>
		<#assign successWon=scheme.successWon /><#-- 方案成功中奖 -->
		<#assign unSuccessWon=scheme.unSuccessWon /><#-- 方案不成功流产 -->
		<#if successWon || unSuccessWon>
			<#if scheme.updatePrize><#-- 已更新奖金详情 -->
				<#assign prizeDetailHtml=scheme.getPrizeDetailHtml(canViewDetail)!'' />
				<#if prizeDetailHtml!=''>
					<tr class="fd_trw" >
			    		<td align=" center" class="fd_trg"><#if successWon>奖金分配：<#else>中奖奖金：</#if></td>
			    		<td><span <#if successWon>style="font-weight:bold;color:#D62F2F;"</#if>>${prizeDetailHtml}</span></td>
			    		<td>&nbsp;</td>
				  	</tr>
				</#if>
	 		</#if>
	 	</#if>
	 <#else>
	 	<!--认购操作-->
		  <@block name="operSale">
		  	<form id="subscribeForm" action="<@c.url value="/${scheme.lotteryType.key}/scheme!subscribe.action" />" method="post" autocomplete="off">
			  <input type="hidden" name="id" value="${scheme.id}" />
		      <tr class="fd_trw" >
		        <td align=" center" class="fd_trg">我要认购</td>
		        <td  style="line-height:26px;"><table width="90%" border="0" cellspacing="0" cellpadding="0">
		            <tr>
		              <td style="border-bottom:none;">
		              	<div id="common_logined" style="display:none">
		              		<span class="gc0"></span> 您的帐户余额：<strong> <span class="red" id="common_remainMoney"></span></strong> 元 [ <a href="${base}/user/fund!payPer.action" id="confirm_tz_payPer_a">帐户充值</a> ]
		              	</div>
		              	<#if loginUser??>
		              		<div id="user_logined">
			              		<span class="gc0"></span> 您的帐户余额：<strong> <span class="red">${loginUser.remainMoney?string.currency}</span></strong> 元 [ <a id="confirm_tz_payPer_a_" href="${base}/user/fund!payPer.action">帐户充值</a> ]
			              	</div>	       
			        	<#else>
			        		<div id="common_unlogin">未登录 【<a href="javascript:void(0)" onclick="$('#userquickLogin').dialog('open'); return false;" class="blue_0091d4">登录</a>】</div>		        	              	
			        	</#if>
		              	
		                                                     已认购 <span class="red">#{(scheme.subscribedCost!0);M2}</span> 元，<#if scheme.state=='UNFULL'>还可认购 <span class="red">#{(scheme.remainingCost!0);M2}</span> 元<#else>方案已${scheme.state.stateName}</#if>，
		                <#assign userSubscribedCost=userSubscribedCost!0 />
			            <#if userSubscribedCost gt 0>
			               <span class="redboldchar">此方案您已认购了<span class="rebredchar">#{userSubscribedCost;M2}</span> 元，占方案比例#{(userSubscribedCost/scheme.schemeCost)*100;M2}%</span>
			          	<#else>
			            	<span>此方案您未认购。</span>
			          	</#if><br/>
			          	<#if scheme.state=='UNFULL'>
		                                                     最低认购#{scheme.minSubscriptionCost;M2}元，我要认购<input name="subscribeForm.subscriptionCost" type="text" id="subscribeForm.subscriptionCost" size="10" onkeyup="updateSubscriptionPer(this,${scheme.schemeCost},${scheme.remainingCost});" onkeydown="number_check(this,event,0);"/> 元，
		                                                     我要保底<input name="subscribeForm.baodiCost" type="text" id="subscribeForm.baodiCost" size="10" onkeyup="updateBaodi(this,${scheme.schemeCost},${scheme.remainingCost});" onkeydown="number_check(this,event,0);"/>元&nbsp;
		                	<input type="checkbox" name="fullBaodi" id="fullBaodi" onclick="doFullBaodi(event,'${scheme.remainingCost}');"/><label for="fullBaodi">全保</label><br />
			                <script type="text/javascript">
			                	function updateSubscriptionPer(obj,schemeCost,remainCost){
			                		if(!isNaN(obj.value)){
			                			var value = parseFloat(obj.value);
				                		if(value>remainCost){
				                			setCommonResult("最多只能认购"+remainCost+"元", false);
				                			value=obj.value=remainCost;
				                			$$('subscribeForm.baodiCost').value = "";
				                		}
				                		var chkEl = $$('fullBaodi');
				                		if(chkEl.checked){
				                			$$('subscribeForm.baodiCost').value = remainCost-value;
				                		}
				                		var percent = (value/schemeCost).toFixed(2)*100 + '%';
				                		$$('percentSpan').innerHTML = percent;
			                		}else{
			                			obj.value='';
			                			$$('percentSpan').innerHTML = '0.0%';
			                		}              		
			                	}
			                	
			                	function updateBaodi(obj,schemCost,remainCost){
				                	if(!isNaN(obj.value)){
				                		var value = parseFloat(obj.value);
				                		var subscriptionCost = parseFloat($$('subscribeForm.subscriptionCost').value);
				                		var baodiCost = remainCost-subscriptionCost;
				                		if(value>baodiCost){
				                			setCommonResult("最多只能保底"+baodiCost+"元", false);
				                			$$('subscribeForm.baodiCost').value = baodiCost;
				                		}
				                	}
			                	}
			                	
			                	function doFullBaodi(event,remainingCost){
			                	    var chkEl = $$('fullBaodi');
			                		if (event != null) {
										Event_stopPropagation(event);// event.stopPropagation();
										var target = Event_target(event);// event.target
										if (target.id != chkEl.id)
											chkEl.checked = !chkEl.checked;
									} else {
										chkEl.checked = !chkEl.checked;
									}
									if(chkEl.checked){
									    var subscriptionCost = $$('subscribeForm.subscriptionCost').value;
									    var remainBaodi = 0;
										if(subscriptionCost!=''){
											remainBaodi = remainingCost-parseFloat(subscriptionCost);
										}else{
											remainBaodi = remainingCost
										}
										$$('subscribeForm.baodiCost').value = remainBaodi;
									}else{
										$$('subscribeForm.baodiCost').value = '';
									}
			                	}
			                </script>
		                                                     占方案比例 <span id="percentSpan" class="red">0.0%</span><br />
		                	<input type="checkbox" checked="checked" name="agree" id="agree" /><label for="agree"><@com.buyProtocol /></label>	                	
		                 </#if>
		                </td>
		              <td width="180" style="border-bottom:none;"><#if scheme.state=='UNFULL'><a href="javascript:;" class="btbuy" onclick="submitSubscribeForm();"></a></#if> </td>
		            </tr>
		          </table></td>
		        <td>&nbsp;</td>
		      </tr>
		    </form>
	      </@block>
	 </#if>
            
      <@block name="schemeShare">
	      <tr class="fd_trtop" >
	        <td colspan="3" align="left" class=" l10 d20"><b>方案分享信息</b></td>
	      </tr>
	      <#assign canCancelScheme=canCancelScheme!false />
		  <#assign canCancelSubscription=canCancelSubscription!false />
		  <#if loggedUser??>
			  <#assign loggedUser_id=loggedUser.id />
		  <#else>
			  <#assign loggedUser_id=-1 />
		  </#if>
	      <tr class="fd_trw">
	        <td align="center" class="fd_trg">发起人撤单</td>
	        <#if scheme.state="CANCEL" || scheme.state="REFUNDMENT">
	         	<td>该方案已撤单；</td>
	        <#else>
	        	<td>该方案已购买${scheme.progressRate}%<#if !canCancelScheme>，不允许撤单</#if>；</td>
	        </#if>
	        <td ></td>
	      </tr>
	      <tr class="fd_trw">
	        <td align=" center" class="fd_trg">方案描述</td>
	        <td ><span class="fl fd_gray" style="display:block; width:500px;">${scheme.description!}</span>
	        <a id="scheme_link" href="<@c.url value="/${scheme.lotteryType.key}/scheme-${scheme.schemeNumber}.html" />"></a>
	        <span class="fr t10"><a href="javascript:;" id="copyTo">点击复制方案地址</a></span></td>
	        <td ></td>
	      </tr>
      </@block>
      
      <@block name="schemeSaleInfo">
	      <tr class="fd_trw" >
	        <td align=" center" class="fd_trg">合买用户</td>
	        <td colspan="2" id="extra-data-menu">该方案${scheme.secretType.secretName}。<br />
	          <a id="subscriptionObj" onclick="chgExtraDataMenu(this);return false;" href="<@c.url value="/${scheme.lotteryType.key}/scheme!subscriptionList.action?id=${scheme.id}" />">认购详情</a>
	          <a id="baodiObj" onclick="chgExtraDataMenu(this);return false;" href="<@c.url value="/${scheme.lotteryType.key}/scheme!baodiList.action?id=${scheme.id}" />" class="fdlistbtnor">保底详情</a>
	          <span id="rengou" <#if !loggedUser??>style="display:none"</#if>> <a onclick="chgExtraDataMenu(this);return false;" href="<@c.url value="/${scheme.lotteryType.key}/scheme!subscriptionList.action?id=${scheme.id}&type=self" />" class="fdlistbtnor">您的认购记录</a></span>
	          <!--显示详细-->
	          <span id="extra-data-container"></span>
	          <script type="text/javascript">
	      		var selectedClass = 'fdlistbtnow';
				var unSelectedClass = 'fdlistbtnor';
				var subscriptionObj = $$('subscriptionObj');
				$('#extra-data-container').load(subscriptionObj.href,{_t:new Date().getTime()}, function() {
					$('#extra-data-menu  a[class="' + selectedClass + '"]').attr('class', unSelectedClass);
					subscriptionObj.className = selectedClass;
					var count = document.getElementById('extra-count').innerHTML;
				});
			  </script>
			</td>		
	      </tr>
      </@block>
      
    </table>
  </div>
</div>

<script>
	if(typeof(ZeroClipboard) != "undefined"){
		ZeroClipboard.setMoviePath( "${base}/js/ZeroClipboard.swf" ); 
	}
</script>
<#include "/common/schemeSuccessDialog.ftl" />
<!--main end-->
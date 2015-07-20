<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/nineOptimizeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value="/js/jquery/popup_layer.js"/>"></script>
	<script type="text/javascript" src="${base}/js/lottery/changeBgColor.js"></script>
</@override>

<@override name="top">
  <div class="tztop1">
    <div class="tztopname">买任9中2场就有奖</div>
    <div class="tztopnews">
      <ul class="tztopnew">
         <#if gonggaoList?? && gonggaoList?size gt 0>
			<#list gonggaoList as data>
				<li><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
			</#list>
		  </#if>	
      </ul>
    </div>
    <div class="tztopbt"> <a href="#" class="tztopbt1">玩法介绍</a><br />
      <a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45" class="tztopbt1">申请票样</a> </div>
    <div class="tzday1"><b>周一至周五</b><br />
      09:00～00:00</div>
    <div class="tzday2"><b>周六/日</b><br />
      09:00～01:00</div>
    <div class="tzday3"></div>
  </div>
</@override>  

<@override name="content">
  <div class="tzleft">
    <div class="tzleftt">
      <div class="tzleftname"></div>
      <div class="tzleft2">
      		请对以下14场比赛任意选9场或以上的赛果进行竞猜<br />
        	每场比赛至少竞猜一个赛果，如需投注更多竞彩赛事 <a href="${base}/jczq/scheme!editNew.action">可点击这里</a>
        	<!--<div class="tzbtwz"> <a href="#" class="tzbttop">让球和非场球混合机选</a> <a href="#" class="tzbttop">机选让球投注</a> <a href="#" class="tzbttop">机选非场球投注</a> </div>-->
      </div>
    </div>
    <!--table-->
    <table width="710" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
      <tr class="tdduilist" align="center">
        <td rowspan="2" height="43">场</td>
        <td rowspan="2">赛事</td>
        <td rowspan="2">开赛</td>
        <td rowspan="2">主队</td>
        <td rowspan="2">让球 </td>
        <td rowspan="2" class="tdr1px">客队</td>
        <td colspan="3" class="tdr1px" style="background:#FEF5D2; color:#CC4212;">非让球即时赔率/投注区</td>
        <td colspan="3" class="tdr1px" style="background:#e4f7ff;">让球即时赔率/投注区</td>
        <td rowspan="2" >胆</td>
      </tr>
      <tr class="tdduilist" align="center">
        <td style="background:#f2e2b7; color:#000;">胜</td>
        <td style="background:#f2e2b7; color:#000;">平</td>
        <td class="tdr1px" style="background:#f2e2b7; color:#000;">负</td>
        <td style="background:#D5F6FD; color:#000;">胜</td>
        <td style="background:#D5F6FD; color:#000;">平</td>
        <td class="tdr1px" style="background:#D5F6FD; color:#000;">负</td>
      </tr>
      
        <#assign endCount=0 />
        <#assign SPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@SPF") />
		<#assign RQSPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@RQSPF") />
		<#list matchs as match>
			<#if match_index%2==0>
				<#assign trClass='tdlist' />
			<#else>
				<#assign trClass='tdlist1' />
			</#if>
			<#if spfRateData??>
				<#assign spfRateMap=spfRateData.get(match.matchKey)!'' />
			<#else>
				<#assign spfRateMap='' />
			</#if>
			<#if rqspfRateData??>
				<#assign rqspfRateMap=rqspfRateData.get(match.matchKey)!'' />
			<#else>
				<#assign rqspfRateMap='' />
			</#if>
			
			<#assign saleEnd_SPF=match.isNotDisplay(SPF,passMode)/>
			<#assign saleEnd_RQSPF=match.isNotDisplay(RQSPF,passMode)/>
			<#assign open_SPF=match.isOpen(SPF,passMode)/>
			<#assign open_RQSPF=match.isOpen(RQSPF,passMode)/>
		    
	      <tr id="${(match_index+1)?string('00')}" align="center" class="${trClass}" >
	        <td id="td1${(match_index+1)?string('00')}" width="20" height="38">${match.lineId?string('000')}</td>
	        <td style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
	        <td id="td3${(match_index+1)?string('00')}" >${match.matchTime?string('HH:mm')}</td>
	        <td id="td4${(match_index+1)?string('00')}" class="dzrig char14"><span id="td_h_${match.matchKey}">${match.homeTeamName!}</span></td>
	        <#assign handicapClass=''/>
		    <#if match.handicap??>
		    	<#if match.handicap gt 0>
		    		<#assign handicapClass='rc1'/>
		    	<#elseif match.handicap lt 0>
		    		<#assign handicapClass='green01'/>
		    	</#if>
		    </#if>
	        <td id="td5${(match_index+1)?string('00')}" class="${handicapClass}"><span id="handicap_${match.matchKey}">${match.handicap!}</span></td>
	        <td id="td6${(match_index+1)?string('00')}" class="dzleft char14"><span id="td_g_${match.matchKey}">${match.guestTeamName!}</span></td>
	        <span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span>
	              
	        
	        <#--非让球胜平负-->	
	        <#assign rs=match.getResult(SPF)!'' />
		    <#if rs!=''><#assign rs_sp=match.getResultSp(SPF)!'' /><#else><#assign rs_sp='' /></#if>       
            <#if open_SPF>
	            <#list itemArr as item>
	            	<td width="50" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_SPF>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');"<#else>bgColor="#808080"</#if>>
	            	 <#assign spId='SPF_sp_${match.matchKey}_${item.ordinal()}' />
	               	 	<#assign chkId='SPF_chk_${match.matchKey}_${item.ordinal()}' />
	               	 	<#if spfRateMap?? && spfRateMap!=''>
							<#assign rateItem=spfRateMap.get(item.name())!'' />
						<#else>
							<#assign rateItem='' />
						</#if>
						
						<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
		        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
		        		<#else>
		        			
		               	 	<#if !saleEnd_SPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
		               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>--</#if></span>
	            	   </#if>
	            	</td>
	            </#list>
	         <#else>
            	<td class="tdyelnor" colspan="${itemArr?size}">未开售</td>
            </#if>
		    <#--让球胜平负-->
		    <#assign rs=match.getResult(RQSPF)!'' />
		    <#if rs!=''><#assign rs_sp=match.getResultSp(RQSPF)!'' /><#else><#assign rs_sp='' /></#if>
            <#if open_RQSPF>
	            <#list itemArr as item>
	            	<td width="50" class="tdnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_RQSPF>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');"<#else>bgColor="#808080"</#if>>
	            	 <#assign spId='RQSPF_sp_${match.matchKey}_${item.ordinal()}' />
	               	 	<#assign chkId='RQSPF_chk_${match.matchKey}_${item.ordinal()}' />
	               	 	<#if rqspfRateMap?? && rqspfRateMap!=''>
							<#assign rateItem=rqspfRateMap.get(item.name())!'' />
						<#else>
							<#assign rateItem='' />
						</#if>
						
						<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
		        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
		        		<#else>
		               	 	<#if !saleEnd_RQSPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
		               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>--</#if></span>
	            	   </#if>
	            	</td>
	            </#list>
	        <#else>
            	<td class="tdnor" colspan="${itemArr?size}">未开售</td>
            </#if>
            <td class="tdl1px">
            	<#if !saleEnd_RQSPF || !saleEnd_SPF>
            		<input id="dan_${match.matchKey}" name="dans" type="checkbox" disabled="disabled" onclick="clickShedan('${match.matchKey}',this)" autocomplete="off" />
            	<#else>
            		<input id="dan" type="checkbox" disabled="false" autocomplete="off" />
            	</#if>
            </td>
	     </tr>	      	     
	     <script>			
			var tr_id = "${(match_index+1)?string('00')}";
										
			changeBgColor(tr_id);
		</script>
      </#list>
    </table>
    <!--优化提交form-->
	<div style="display:none">
		<form id="optimizeForm" action="${base}/${lotteryType.key}/optimize!bonusOptimizeNine.action" method="post" autocomplete="off">
			<input type="hidden" name="ajax" value="true"/>
			<input type="hidden" name="prizeForecast" value="true"/>
			<input type="hidden" id="createForm.multiple" name="createForm.multiple" value="5"/>
			<div id="div_op"></div>
		</form>
	</div>
    <div class="k10"></div>
    <div class="j9ts"> <b>竞9玩法提示</b>
      <p>1、投注选项中的数值为官方提供的实时投注奖金，仅供参考，奖金计算以实际出票时的奖金为准，可能会与投注时的奖金有细微的差别。</p>
      <p>2、竞9奖金计算：按竞彩实际计算奖金方法计算。</p>
      <p>3、点击投注项中的数值即可进行投注。</p>
      <p>4、让球数：“+”号为客队让主队，“-”为主队让客队"</p>
    </div>
  </div>
</@override>

<@override name="right">
  <div class="tzrig">
  	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
	    <div class="rigbgblue">确认投注信息</div>
	    <table width="280" border="0" align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;" class="tableb1">
	      <tr align="center" class="tdyellist"  height="24" bgcolor="#FFFDEA">
	        <td height="50">方案金额</td>
	        <td align="left"><span id="schemeCost" _name="createForm_schemeCost" class="rc0">0</span></td>
	        <td>注数</td>
	        <td><span id="units" name="units">0</span></td>
	        <td>倍数</td>
	        <td><input type="text" name="createForm.multiple" id="multiple" value="1" class="inputw80" onkeyup="updateMultiple();" onkeydown="number_check(this,event)" onblur="if(new Number(this.value) < 0){this.value=1;updateMultiple();}"/></td>
	      </tr>
	      <tr id="errorTRId" style="display:none" class="tdyellist"  height="24" bgcolor="#FFFDEA"><td colspan="6"><span id="errorSpanId" class="rc0"></span></td></tr>
	    </table>
	    <div class="rigbgblue">方案奖金预测详情<div id="forecastBtnDivId" style="float:right;padding-top:3px;"></div></div>
	    <div id="prizeForecast_content" >
		    <table width="278" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#ccc;" class="tableb1">
		      <tr align="center" class="tdgraylist"  height="24">
		        <td>命中场数</td>
		        <td>中出奖金范围</td>
		        <td>回报率</td>
		      </tr>
		      <tr align="center" class="tdwhitelist" height="28">
		        <td>中[x]场 </td>
		        <td class="rc0">--~--</td>
		        <td>--~--</td>
		      </tr>
		    </table>
	    </div>
	    <table width="280" border="0" align="center" cellpadding="00" cellspacing="0" style="border-collapse:collapse;" class="tableb1">
	      <tr align="center" class="tdwhitelist1"  height="28">
	        <td style="border-right:1px solid #DCE7F4;width:40px; text-align:center;"><b>类型</b></td>
	        <td align="left"><input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">代购&nbsp;</label>
	          <input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">合买&nbsp;</label>
	          <input id="freeSave" type="radio" value="true" name="freeSave" onclick="chgShareType(this);"/><label for="freeSave">免费保存</label>
	          <a href="javascript:;"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0" tips="<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>" onmouseover="SaveTips(this)"/></a>
	        </td>
	      </tr>
	      <tr id="tr_TOGETHER_content" style="display:none">
	      	<td colspan="2">
	      		<table width="100%" cellspacing="0" cellpadding="1" border="0" style="background-color:#ccc;">
	            <tr align="center" class="tdwhitelist"  height="24">
		          	<td style="text-align:right">最低认购： </td>
		          	<td style="text-align:left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="inputw" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
		        </tr>
		        <tr align="center" class="tdwhitelist"  height="24">
		          	<td style="text-align:right">我要认购：</td>
		          	<td style="text-align:left">
		              <input name="createForm.subscriptionCost" type="text" size="7" class="inputw" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="subscriptionPerSpan">0.00</span>%</span>
		          	</td>
		        </tr>
		        <tr align="center" class="tdwhitelist"  height="24">
		            <td style="text-align:right">发起人提成：</td>
		            <td style="text-align:left">
			            <#assign commissionRate=(createForm.commissionRate)!0.00 />
			            <select id="createForm.commissionRate" name="createForm.commissionRate" size="1" style="width:70px;height:22px; line-height:22px; color:#555; margin:0; padding:0;border:1px solid #ccc;">
			              <option value="0">无佣金</option>
			              <#list 1..5 as c>
			              	<#assign rate=c/100 />
			              	<option value="${rate?string('0.00')}" <#if commissionRate=rate>selected="selected"</#if> >${c}%佣金</option>
			              </#list>
			            </select>
		            </td>
		        </tr>
		        <tr align="center" class="tdwhitelist"  height="24">
		            <td style="text-align:right">我要保底：</td>
		            <td style="text-align:left"><input name="createForm.baodiCost" type="text" size="7" class="inputw" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="baodiCostPerSpan">0.00</span>%</span></td>
		        </tr>
		        <tr align="center" class="tdwhitelist"  height="24">
		            <td style="text-align:right">方案描述：</td>
		            <td style="text-align:left"><textarea style="border:solid 1px #BABABA; color:#787878; padding:1px;" rows="3" cols="25" name="createForm.description"></textarea></td>
		        </tr>
	          </table>
	      	</td>
	      </tr>
	      <tr align="center" class="tdwhitelist1" height="28">
	        <td style="border-right:1px solid #DCE7F4; width:40px; text-align:center;"><b>保密</b></td>
	        <td align="left">
	        	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[0] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
				</#list>
	        </td>
	      </tr>
	      <tr align="center" class="tdwhitelist1" height="28">
	      	<td colspan="2"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocolSimple /></label></td>
	      </tr>
	    </table>

	    <div class="enterbt" align="center"><a onclick="beforeSubmit();return false;" href="javascript:;" class="btbuy"></a><span id="span_createForm_submit"></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span></div>
	    <input type="hidden" id="createForm.schemeCost" name="createForm.schemeCost" value="" />
	    <input type="hidden" id="createForm.units" name="createForm.units" value="" />
	    <input type="hidden" name="request_token" value="${datetime().millis}" />
	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
	    <input type="hidden" id="playType" name="playType" value="MIX"/>
	    <input type="hidden" id="playTypeWeb" name="playTypeWeb" value=""/>
	    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
	    <input type="hidden" name="createForm.schemeType" value="SIMPLE_PASS"/>
	    <input type="hidden" name="createForm.passMode" value="MIX_PASS"/>
		<input type="hidden" name="createForm.passTypes" value="P2_1"/>
	    <input type="hidden" name="createForm.optimize" value="true" />
	    <input type="hidden" id="createForm.bestMinPrize" name="createForm.bestMinPrize" value="" />
	    <input type="hidden" id="createForm.bestMaxPrize" name="createForm.bestMaxPrize" value="" />
	    <div id="div_item"></div>
	    <div id="div_sps"></div>
	    <div id="schemeFormData"></div>
	</form>
    <div class="k10"></div>
    <div style="padding-bottom:10px;"><a href="${base}/jczq/scheme!editNew.action?playTypeWeb=EXY" target="_blank"><img src="${base}/V1/images/guanggao.jpg" class="guanggao"/></a></div>
    <!--新闻资讯 begin-->
    <div class="tzgrayk">
      <div class="tzgraybg">新闻资讯</div>
      <div class="tznews_listwz"> 
      <ul class="tznews_list">
          <#if newsList?? && newsList?size gt 0>
          <#assign i=0/>
			<#list newsList as data>
				<#assign i=i+1/>
				<#if i lt 4>
					<li><span class="iconred">${i}</span><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
				<#else>
					<li><span class="iconty">${i}</span><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>					
				</#if>
			</#list>
		  </#if>		  		  	
        </ul>
      </div>
    </div>
    <!--新闻资讯 end-->
    <#--最新中奖
    <div class="k10"></div>
    <div class="tzgrayk">
      <div class="tzgraybg">最新中奖</div>
      <table width="278" border="0" align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;border-top:none;" class="tableb1">
        <tr align="center" class="tdgraylist"  height="20">
          <td>户名</td>
          <td>方案</td>
          <td>中奖</td>
        </tr>
       <#if newestWonSubcriptionList?? && newestWonSubcriptionList?size gt 0>
         	<#list newestWonSubcriptionList as data>
	          <tr align="center" class="tdwhitelist"  >
		          <td class="tc"><a href="${base}/${data.lotteryType.key}/scheme!show.action?id=${data.schemeId}" target="_blank">${data.userName}</a></td>
		          <td align="left"> ￥${data.cost} </td>
		          <td class="winchar">${data.bonus}</td>
	          </tr>
	        </#list>  
         </#if> 	
      </table>
    </div>
    最新中奖end-->
  </div>
</@override>
  
<!--投注信息确认对话框-->
<div id="dialogDiv" style="display:none">
	<div class="w730">
	  <div class="w730title">
	    <div class="close" id="close" onclick="closeDialog('dialogDiv');return false;"></div>
	    	竞9投注方案${currDate?string("yyyy")}年${currDate?string("MM")}月${currDate?string("dd")}日发起
	    </div>
	  <div class="tdbback">
	    <table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">
	      <tr class="v01tr">
	        <td>总金额</td>
	        <td>倍数</td>
	        <td>份数</td>
	        <td>每份</td>
	        <td>发起人提成</td>
	        <td>彩票标识</td>
	        <td>保底金额</td>
	        <td>购买进度</td>
	      </tr>
	      <tr class="v02tr">
	        <td>￥<span class="red eng" id="confirmCost">X</span>元</td>
	        <td><span id="confirmMultiple">X</span>倍</td>
	        <td><span id="confirmQuantity">X</span>份</td>
	        <td>￥<span id="confirmMinCost">X</span>元</td>
	        <td><span class="red eng" id="confirmCommissionRate">X%</span></td>
	        <td>暂未出票</td>
	        <td><span id="confirmBaodi">X元</span></td>
	        <td><span class="red eng" id="subscriptionPer">X%</span></td>
	      </tr>
	    </table>
	  </div>
	  <div class="tdbback" id="selectedContentDiv">
	    <table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">
	      <tr class="v01tr">
	        <td>赛事编号 </td>
	        <td>主队 VS 客队 </td>
	        <td>让球数 </td>
	        <td>您的选择</td>
	        <td>胆码</td>
	      </tr>
	  	<tr class="v02tr">
	        <td>周六006 </td>
	        <td>爱媛FC VS 鸟取希望</td>
	        <td><span class="blue">-1</span></td>
	        <td>胜(3.05)，平(3.45) </td>
	        <td>×</td>
	      </tr>
	      <tr class="v02tr">
	        <td>周六007 </td>
	        <td>岐阜FC VS 北九州向日葵 </td>
	        <td><span class="red">+1</span></td>
	        <td>平(3.5),负(3.45)</td>
	        <td>√</td>
	      </tr>  
	    </table>
	  </div>
	  <!--end-->
	  <div class="tdbback" id="prizeForecastOfDialog">
	    <table width="730" border="0" cellspacing="0" cellpadding="0" class="vtable">
	      <tr align="center" class="tdetailtr">
	        <td colspan="6">方案奖金预测详情</td>
	      </tr>
	      <tr align="center" class="v01tr">
	        <td>命中场数</td>
	        <td>中出奖金范围（小-大） </td>
	        <td>奖金回报率 </td>
	      </tr>
	      <tr align="center" class="v02tr">
	        <td>中9场</td>
	        <td><span class="red eng">￥12040~￥50800</span></td>
	        <td><span class="red eng">100%</span></td>
	      </tr>
	      <tr align="center" class="v02tr">
	        <td>中8场</td>
	        <td>&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	  </div>
	  <div class="k10"></div>
	  <div align="center" class="a10px"> 
	 	 <a onclick="$('#dialogDiv').dialog('close');return false;" href="javascript:;" class="btback"></a>&nbsp;&nbsp;
	  	<a onclick="submitCreateForm('buy');return false;" href="javascript:;" class="btbuy"></a>&nbsp;&nbsp;
	  	<a onclick="submitCreateForm('save');return false;" href="javascript:;" class="btsave"></a> 
	  </div>
	</div>
</div>

<@extends name="/WEB-INF/content/common/simple-baseV1.ftl"/> 
<script>
	function beforeSubmit(){
		submitOptimizeForm();
		confirmDialog();
	}
	window.onload = function() {
		createDialog_header('#dialogDiv', '提示信息', 780);		
	}
</script>
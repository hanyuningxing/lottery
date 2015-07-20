<#assign isSingle=passMode?? && passMode=='SINGLE' />
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/cache.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilter.js"/>"></script>
	<link href="${base}/V1/css/qk_newadd.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		$(function() {
			window.PassTypeUtil = {};
			PassTypeUtil.getSinglePassType = function(matchCount){
				if (matchCount < 1 || matchCount > window.maxPassMatchCount) {
					return null;
				}
				return PassTypeArr[0];
			};
			PassTypeUtil.getNormalPassType = function(matchCount){
				if (matchCount < 1 || matchCount > window.maxPassMatchCount) {
					return null;
				}
				var types = [];
				if (matchCount > 1) {
					for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
						var type = PassTypeArr[i];
						if (type.matchCount == matchCount) 
							types.push(type);
						else if (type.matchCount > matchCount)
							break;
					}
				}
				return types;
			};
			PassTypeUtil.getMultiplePassType = function(matchCount) {
				var types = [];
				if (matchCount < 1 || matchCount > window.maxPassMatchCount) {
					return types;
				}
				for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
					var type = PassTypeArr[i];
					if(type.matchCount > 1){
						if (type.matchCount <= matchCount && type.units == 1) 
							types.push(type);
						else if (type.matchCount > matchCount)
							break;
					}
				}
				return types;
			};
			
			var itemFn = function(v, t) {
				return {
					value : v,
					text : t
				};
			};
			
			var playItemFn = function(k, n ,m) {
				return {
					key : k,
					name : n,
					min : m
				};
			};
			
			window.play2playItem = {};
			window.play2ItemValue = {};
			window.playTypeItem = {};			
			window.playTypes=[];
			<#if playMap?exists && playMap?has_content>
				<#list playMap.entrySet() as entry>
					<#assign key=entry.getKey() />
					<#assign item=entry.getValue() />
				     playTypeItem['${key}'] = playItemFn('${item}', '${item.text}', '${item.maxMatchSize}');
				     playTypes.push('${key}');
				</#list>
			</#if>
			<#if itemMap?exists && itemMap?has_content>
				<#list itemMap.entrySet() as entry>
				    var v = 0;
					<#assign key=entry.getKey() />
					<#assign itemArr=entry.getValue() />
					var playItemArr = [];
					window.rowItemValues_${key} = [];
				    <#list itemArr as item>
				     	playItemArr.push('${item.text}');
				     	v |= 1<<${item.ordinal()};
				    </#list>
				    play2playItem['${key}'] = playItemArr;
				    window.play2ItemValue['${key}'] = v;
				    <#if key == 'BF' && bfItemMap?exists && bfItemMap?has_content>
						<#list bfItemMap.entrySet() as entry>
							<#assign arr=entry.getValue() />
							v = 0;
							<#list arr as item>
								v |= 1<<${item.ordinal()};
							</#list>
							window.rowItemValues_${key}.push(v);
						</#list>
					<#else>
						window.rowItemValues_${key}.push(v);
					</#if>
				</#list>
			</#if>
			
			
			<#if playTypeWeb??>
				playTypeWeb = "${playTypeWeb}";
			</#if>
		});
		
		//根据玩法对应的页面彩种
	    var selectedItemClass = [];
	    var passType_recommend;//推荐的过关方式
	    var playTypeOfGobal = '${playType}';
	    if(playTypeOfGobal=="SPF" || playTypeOfGobal=="RQSPF"){
	        passType_recommend='P2_1';
	        selectedItemClass.push('sheng button2');
	        selectedItemClass.push('ping button2');
	        selectedItemClass.push('fu button2');
	    }else if(playTypeOfGobal=="MIX"){
	    	selectedItemClass.push('sheng button2');
	        selectedItemClass.push('ping button2');
	        selectedItemClass.push('fu button2');
	        selectedItemClass.push('butlin1 button2');
	        selectedItemClass.push('butlin2 button2');	        
	    }else{
	    	selectedItemClass.push('ping button2');
	    }
	    
		<#-- 注：此JS必须在compoundInit.js之前 -->
	</script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>

	
	<script type="text/javascript">
		<#-- 奖金优化也适用于request提交，目前使用form提交 -->
		function bonusOptimizeRequest(){
			var matchKeys='';
			var betvs='';
			var len=0;
			var hashObj = getSelectedItems();
			for(var matchKey in hashObj){
				var obj = hashObj[matchKey];
				matchKeys += obj.matchKey+",";
				betvs +=obj.value+",";
				len++;
			}
			matchKeys = matchKeys.substring(0,matchKeys.length-1);
			betvs = betvs.substring(0,betvs.length-1);
			
			var createForm = getCreateForm();
			var arr = createForm.elements['createForm.passTypes'];
			var typev;
			if (arr != null) {
				if (arr.length == null) {
					arr = [ arr ];
				}
				for ( var i = 0, len = arr.length; i < len; i++) {
					var typeEl = arr[i];
					if (typeEl.checked) {
						var type = PassType[typeEl.value];
						typev = type.value;
					}
				}
			}
			var  schemeCost= createForm.elements['createForm.schemeCost'].value;
			var url = '${base}/jczq/scheme!bonusOptimize.action?matchKeys='+matchKeys+'&betvs='+betvs+'&typev='+typev+'&pt=${playType}&m='+schemeCost;
			window.location.href=url;
		}
				
	</script>
</@override>

<@override name="topContent">
    <div class="tzleftt">
    	<#if (playType=='SPF' || playType=='RQSPF') && !playTypeWeb??>
			<div class="spfwz"></div>
			<div class="tzleft2">
				同一场让球和非让球胜平负都可选，由于同场比赛不能组合成一注【官方规则限制】，<br>所以需要多选一场比赛进行过关组合。
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
		<#elseif playTypeWeb?? && playTypeWeb=='EXY'>
			<div class="spf2to1"></div>
			<div class="tzleft2">
				同一场让球和非让球胜平负都可选，由于同场比赛不能组合成一注【官方规则限制】，<br>所以需要多选一场比赛进行过关组合。
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
		<#elseif playTypeWeb?? && playTypeWeb=='DGP'>
			<div class="spfdgp"></div>
			<div class="tzleft2">
				以生死盘（二选一）投注为的基础，演变出来的简单、高效、高命中、返奖稳定的投注方法，用户只需要选择一场（或以上）比赛，系统就会根据当前对阵的即时SP值自动匹配一场返奖率较高的混合投注的方案，并对此方案进行优化，从而使用户的中奖奖金中奖率得到一定提升
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
  		<#elseif playType=='MIX'>
			<div class="hhggwz"></div>
			<div class="hhggr">“混合过关”并非玩法，而是一种投注方式。在我们以往的竞彩过关投注中，全部是前后比赛场次选择同一运动项目的同一种游戏进行投注，这种投注方式被称为“一般过关投注”，而现在新的投注方式出现了：前后比赛场次选择同一运动项目不同游戏也可以进行投注，这种投注方式称为“混合过关投注”。</div>
		<#elseif playType=='JQS'>
			<div class="ggtz"></div>			
			<div class="tzleft2">
				竞彩足球的“总进球数”玩法是预测对阵双方整场赛事（不计算加时、点球）的进球总数。也就是说无论这场赛事谁胜谁负，比分多少，只要主客队加起来的进球数是您投注的结果的话就是中奖。
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
		<#elseif playType=='BF'>
			<div class="ggtz2"></div>
			<div class="tzleft2">
			竞猜两支球队在在90分钟内(含伤停补时)的比分。</br>
			1:0 2:0 3:0等25种选项。
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
		<#elseif playType=='BQQ'>
			<div class="ggtz3"></div>
			<div class="tzleft2">
				竞彩足球的“总进球数”玩法是预测对阵双方整场赛事（不计算加时、点球）的进球总数。也就是说无论这场赛事谁胜谁负，比分多少，只要主客队加起来的进球数是您投注的结果的话就是中奖。
				<!--<div class="tzbtwz2"><a class="tzbttop" href="#">网站高手帮你选</a></div>-->
			</div>
		</#if>
    </div>	
	<div class="w710div">
  	  <div id="sssx">
	  	<div class="sssx01">
		  	<div style="float:left;">
			  <a class="ssxsan" href="javascript:;" onclick="javascript:$$('sssxxzdiv').style.display='';"></a>
		      &nbsp;&nbsp;隐藏 <strong><span id="c_h" class="rc1">0</span></strong> 场比赛&nbsp;&nbsp;<a href="javascript:;" onclick="matchFilterReset();return false;" class="blue_0091d4">恢复</a>&nbsp;&nbsp;
		      <span style="display:none;">| &nbsp;&nbsp;
		      <input id="s_hdp1" onclick="matchFilter();" checked="checked" type="checkbox" autocomplete="off"/>让球(<span class="rc1">${handicapCount!}</span>)&nbsp;&nbsp;
	          <input id="s_hdp0" onclick="matchFilter();" checked="checked" type="checkbox" autocomplete="off"/>非让球(<span class="rc1">${unHandicapCount!}</span>)
	          </span>
	        </div>
		    <#if playTypeWeb?? && playTypeWeb != "YP">
				<div style="float:right;">赛事回查：
				<#if playTypeWeb?? && playTypeWeb != "YP">
					<select name="matchDate" onchange="window.location.href='<@c.url value="/${lotteryType.key}/scheme!editNew.action?playTypeWeb=${playTypeWeb}&passMode=${passMode!'PASS'}"/>&matchDate='+this.value;">			
				<#else>
				  <select name="matchDate" onchange="window.location.href='<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=${playType}&passMode=${passMode!'PASS'}"/>&matchDate='+this.value;">
				</#if>
						<#list dateList as date>
						<option <#if matchDate?? && matchDate==(date.toString('yyyyMMdd'))>selected="selected"</#if> value="${date.toString('yyyyMMdd')}">${date.toString('yyyy-MM-dd')}</option>
				    	</#list>
				  </select>
			    </div>
		  </#if>
	  	</div>
	  </div>
	</div>
	<!--赛事筛选框-->
	<div id="sssxxzdiv" style="display:none;z-index:9999">
	  <div style="top:30%;left:25%; background:#aaa;">
	  	<div style="border:1px solid #666; background:#fff; position:relative;top:-3px;left:-3px;">
	  	  <div id="sg" class="sssxxza">
		  <#if games??>
			<#list games as gameName>
				<span class="sssxxzb"><input onclick="gameFilter();" id="gameName_chb_${gameName_index}" type="checkbox" checked="checked" value="${gameName_index}" autocomplete="off" /><label for="gameName_chb_${gameName_index}"><span class="black_666_14">${gameName}</label></span></span>
			</#list>
		  </#if>		  
		  </div>
		  <div class="sssxxzc">
		  	<a href="javascript:;" onclick="javascript:$$('sssxxzdiv').style.display='none';" class="guanbian">关 闭</a>
		  	<a href="javascript:;" onclick="batchSelectGame(0);" class="guanbian">清 除 </a>
		  	<a href="javascript:;" onclick="batchSelectGame(1);" class="guanbian">全 选</a>
		  </div>
		</div>
	  </div>
	</div>
	<!--赛事筛选框-->
</@override>

<@override name="initContent">
<!--
<div>
	<input type="text" id="returnRate" width="30"/>
	<input type="text" id="startMutiple" width="30"/>
	<input type="submit" value="提交"/ onclick="chaseBegin();">
</div>
-->
 <!--新增追投开始-->
 <#if playTypeWeb?? && playTypeWeb="DGP" && loginUser?? && (loginUser.userName=="zhubao" || loginUser.userName == "chenjiecai" || loginUser.userName == "mark" || loginUser.userName == "zhusheng" || loginUser.userName == "SKY2" || loginUser.userName == "klx1314" || loginUser.userName == "chuwen" || loginUser.userName == "wilson" || loginUser.userName == "zl1999999")>
  <div class="newadd_tzxx">
    <div class="newadd_fr"><a href="#"><img src="${base}/V1/images/newadd_add.gif" /></a></div>
    新增追投</div>
  <div class="newadd_border01">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="newadd_trb1">
        <td width="95" height="34" align="right" class="newadd_r8">追投计划名称</td>
        <td><input type="text"  id="chasePlan_name" style="width:120px;"/>
        </td>
      </tr>
      <tr class="newadd_trb1">
        <td height="34" align="right" class="newadd_r8">追投回报率范围</td>
        <td>
          <select size="1" id="returnRate">
            <option value="1">10%-20%</option>
            <option value="2">10%-30%</option>
            <option value="3">20%-30%</option>
            <option value="4">30%-40%</option>
            <option value="5">40%-50%</option>
          </select>
        </td>
      </tr>
      <tr class="newadd_trb1">
        <td height="34" align="right" class="newadd_r8">起始倍数</td>
        <td><input type="text" id="startMutiple" style="width:120px;" class="newadd_input" onclick="this.value='';" value="不低于10倍"/></td>
      </tr>
      <tr>
      	<td>【<a href="${base}/jczq/chasePlan!index.action" onclick="search_my_chasePlan();return false;" target="_blank">查看我的追投</a>】</td>
      </tr>
      <tr bgcolor="#FFFDEA">
        <td height="50">&nbsp;</td>
        <td><a href="javascript:;" onclick="chaseBegin();" class="newadd_bt01"></a></td>
      </tr>
    </table>
  </div>
  </#if>
  <!-- 新增追投结束 -->

<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">

	<div id="tz_info">
		<div class="tzxx">投注信息</div>
		   <div class="border01">
			  <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dce7f4">
		      	  <tr>
		            <td width="30%" height="30" align="center" valign="middle" bgcolor="#D5F6FD"><a href="javascript:;" onclick="cleanSelectedMatch();"><img src="${base}/V1/images/button03.gif" width="34" height="17" border="0" align="middle" /></a> 场次</td>
		            <td align="center" bgcolor="#D5F6FD">主 VS 客</td>
		            <td id="td_danma" width="12%" align="center" bgcolor="#D5F6FD" <#if isSingle || ( playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP"))>style="display:none;"</#if> tips="<div style='width:340px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>胆就是您确定某个投注选项肯定会猜中（可自由选择是否设胆）</div>" onmouseover="DanTips(this)">胆<a href="javascript:;"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0"/></a></td>
		          </tr>
	          </table>
	          <!--投注选择信息-->
	          <div id="div_show">
			  <table width="100%" height="26" border="0" cellpadding="0" cellspacing="1" bgcolor="#dce7f4">
			  	  <tr class="tdwhitelist1"><td width="30%" height="30" align="center" bgcolor="#FFFFFF">请在投注区选择至少<#if isSingle || ( playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP"))>1<#else>2</#if>场赛果</td></tr>
			  	  <!--
	              <tr>
	                <td width="30%" height="30" align="center" bgcolor="#FFFFFF"><a href="#" onclick=""><img src="${base}/V1/images/chacha.gif" border="0" /></a> 周六002</td>
	                <td width="58%" align="center" bgcolor="#FFFFFF">松本山雅  VS  FC横滨</td>
	                <td align="center" style="display:none" bgcolor="#FFFFFF" class="blue_0091d4"><input id="createForm.items[0].dan" type="checkbox" name="checkbox" value="checkbox" /></td>
	              </tr>
	              <tr>
	                <td height="30" colspan="3" align="left" bgcolor="#FFFFFF" class="pad010"><span style="float:left;">投注项：</span><a href="#" class="sheng button2">胜</a><a href="#" class="ping button2">平</a><a href="#" class="fu button2">负</a></td>
	              </tr>
	              -->
	          </table>
	          </div>
	          <div id="div_items"></div>
		      <div id="div_mohushedan" style="padding:6px 10px;<#if isSingle || ( playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP"))>display:none;</#if>">
		      	<span class="bro_af5529">模糊设胆：</span>
		      	<span id="mohushedan">
			        <select name="dan" disabled='disabled'>
			          <option>至少含2个胆</option>
		            </select>
	            </span>
	            <a href="${base}/html/navigator/dan.htm" class="blue_0091d4" target="_ablank">使用说明</a>
	          </div>
		    </div>
		 </div>
	<div <#if playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP")>style="display:none"</#if>>
		<div class="tzxx">过关方式</div>
		<#if isSingle>
		<div class="border01 pad1">
			<input id="createForm.schemeType" name="createForm.schemeType" type="hidden" value="SINGLE"/>
			<div class="ggfs02" id="schemeType_s_div">
				  <ul id="pass_container"><input type="radio" checked="checked" name="createForm.passTypes" value="P1" />${passMode.text}</ul>
			  </div>
		</div>
		<#else>	
		<div class="border01 pad1">
	  	  <div class="ggfs">
		  	  <ul>
			      <li id="createForm_schemeType_s" onclick="chgPassMode('SIMPLE_PASS');" style="background:#D5F6FD url(${base}/V1/images/guoguanbg.jpg) left bottom repeat-x;color:#000;CURSOR: hand"><a href="javascript:;">普通过关</a></li>
			      <li id="createForm_schemeType_m" onclick="chgPassMode('MULTIPLE_PASS');" class="ggfsli01" style="CURSOR: hand"><a href="javascript:;">多选过关</a></li>
			  </ul>
	      </div>
	      <input id="createForm.schemeType" name="createForm.schemeType" type="hidden" value="MULTIPLE_PASS"/>
		  <div class="ggfs02" id="schemeType_s_div">
			  <ul id="pass_container">
			  	<!--
			  	<li class="ggfs02li01"><input type="checkbox" name="checkbox7" id="checkbox5" /> 2串1</li>
			  	<li class="ggfs02li02"><input type="checkbox" name="checkbox7" id="checkbox5" /> 2串1</li>
			  	<li class="ggfs02li02"><input type="checkbox" name="checkbox7" id="checkbox5" /> 2串1</li>
			  	-->
			  </ul>
		  </div>
		</div>
		</#if>	
	</div>
	
	<div>
	  <div class="tzxx">确认投注方案</div>
	  <div class="border01">
	    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dce7f4">
	      <tr>
	        <td width="15%" height="30" align="center" bgcolor="#FFFFFF">金额</td>
	        <td bgcolor="#FFFFFF" class="pad010">
	        	<span id="span_msg">
		        	<strong><span _name="createForm_schemeCost" class="rc1">0</span></strong> 元，
		        	共选择：<strong><span id="createForm_selectedMatchCount" style="color:#FF0000">0</span></strong> 场，
		        	<strong><span _name="createForm_units" style="color:#FF0000">0</span></strong> 注
	        	</span><span id="span_err" style="color:#FF0000"></span>
	        </td>
	      </tr>
	      <tr>
	      	<td height="30" align="center" bgcolor="#FFFFFF">倍投</td>
	      	<td bgcolor="#FFFFFF" class="pad010">
	        <table width="80" height="21" border="0" cellpadding="0" cellspacing="1" bgcolor="#bbbbbb">
	          <tr>
	            <td width="19"><a class="jian" href="javascript:;" onclick="AddMutiple(-1);"></a></td>
	            <#if playTypeWeb?? && playTypeWeb=="DGP">
	            <td align="center" bgcolor="#FFFFFF"><input onkeyup="updateBetCost();" onblur="if(this.value < 10){this.value=10;updateBetCost();}" name="createForm.multiple" type="text" value="10" size="9" maxLength="9" style="width:93px;height:18px;text-align:center;font-weight:bold;" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/></td>
	            <#elseif  playTypeWeb?? && playTypeWeb=="YP">
	            <td align="center" bgcolor="#FFFFFF"><input onkeyup="updateBetCost();" onblur="if(this.value < 125){this.value=125;updateBetCost();}" name="createForm.multiple" type="text" value="125" size="9" maxLength="9" style="width:93px;height:18px;text-align:center;font-weight:bold;" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/></td>	            
	            <#else>
	            <td align="center" bgcolor="#FFFFFF"><input onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" type="text" value="1" size="9" maxLength="9" style="width:93px;height:18px;text-align:center;font-weight:bold;" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/></td>            	
	            </#if>
	            <td width="19"><a class="jia" href="javascript:;" onclick="AddMutiple(1);"></a></td>
	          </tr>
	        </table>
	        </td>
	      </tr>
	      <tr <#if playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP")>style="display:none"</#if>>
	        <td height="30" align="center" bgcolor="#FFFFFF">功能</td>
	        <td bgcolor="#FFFFFF" class="pad010">
		        <#if !isSingle>
		        	<a class="butjjyh button3" href="javascript:;" onclick="bonusOptimize('${base}/${lotteryType.key}/optimize!bonusOptimize.action');return false;" tips="<div style='width:250px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>奖金优化场次最多不能超15场、单倍投最大1000注，只支持2串1-8串1的复式倍投方案。</div>" onmouseover="OptimizeTips(this)"></a>
				</#if>
				<a class="butjjyc button3" href="javascript:;" onclick="prizeForecast();return false;"></a>
	        </td>
	      </tr>
	      
	      <tr <#if playTypeWeb?? && playTypeWeb=="YP">style="display:none"</#if>>
	      	<td height="30" align="center" bgcolor="#FFFFFF">奖金</td>
	      	<td bgcolor="#FFFFFF" class="pad010">
	      		<div>最小奖金：<span style="color:red"><strong id="minPrize">0.00</strong></span>元</div>
	      		<div>最大奖金：<span style="color:red"><strong id="maxPrize">0.00</strong></span>元</div>
	      	</td>
	      </tr>
	      
	      <tr>
	        <td height="30" align="center" bgcolor="#FFFFFF">余额</td>
	        <td bgcolor="#FFFFFF" class="pad010">
	        	<#if loginUser??>
	        		<div id="user_logined"><strong><span class="rc1">${loginUser.remainMoney?string.currency}</span></strong> [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action">帐户充值</a> ]</div>
	        	<#else>
	        		<div id="common_unlogin">未登录 【<a href="javascript:void(0)" onclick="$('#userquickLogin').dialog('open'); return false;" class="blue_0091d4">登录</a>】</div>
	        	</#if>
	        	<div id="common_logined" style="display:none"><strong><span class="rc1" id="common_remainMoney"></span></strong> [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action">帐户充值</a> ]</div>
	        </td>	      	
	      </tr>
			 <#if playTypeWeb?? && playTypeWeb="DGP" && loginUser?? && (loginUser.userName=="zhubao" || loginUser.userName == "chenjiecai" || loginUser.userName == "mark" || loginUser.userName == "zhusheng" || loginUser.userName == "SKY2" || loginUser.userName == "klx1314" || loginUser.userName == "chuwen" || loginUser.userName == "wilson" || loginUser.userName == "zl1999999")>
			
		        <td height="30" align="center" bgcolor="#FFFFFF">追投</td>
		        <td bgcolor="#FFFFFF" class="pad010">
		        	【<a href="${base}/jczq/chasePlan!index.action" onclick="search_my_chasePlan();return false;" target="_blank">查看我的追投</a>】
		        </td>	      	
		      </tr>
		      <script>
		      	function search_my_chasePlan() {
		      		$SSO.login_auth(function() {
		      			window.location = '${base}/jczq/chasePlan!index.action';
		      		});
		      	}
		      </script>
	      </#if>
	      <tr <#if playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP")>style="display:none"</#if> >
	        <td height="30" align="center" bgcolor="#FFFFFF">类型</td>
	        <td bgcolor="#FFFFFF" class="pad010">
	          <input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">代购&nbsp;</label>
	          <input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">合买&nbsp;</label>
	          <input id="freeSave" type="radio" value="true" name="freeSave" onclick="chgShareType(this);"/><label for="freeSave">免费保存</label>
	          <a href="javascript:;"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0" tips="<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>" onmouseover="SaveTips(this)"/></a>
	        </td>
	      </tr>
	      <tr id="tr_TOGETHER_content" style="display:none">
	      	<td colspan="2" bgcolor="#FFFFFF">
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
	      <tr <#if playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP")>style="display:none"</#if>>
	        <td height="30" align="center" bgcolor="#FFFFFF">保密</td>
	        <td bgcolor="#FFFFFF" class="pad010">
	        	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[2] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName} </label>
				</#list>
	        </td>
	      </tr>
	      <tr align="center" class="tdwhitelist1" height="28">
	      	<td colspan="2"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocolSimple /></label></td>
	      </tr>
	      <tr>
	      	<#if playTypeWeb?? && (playTypeWeb=="DGP" || playTypeWeb=="YP")>
	        	<td height="46" colspan="2" align="center" bgcolor="#FFFDEA"><a onclick="bonusOptimize('${base}/${lotteryType.key}/optimize!bonusOptimize.action?playTypeWeb=${playTypeWeb}');return false;" href="javascript:;" class="btbuy"></a><span id="span_createForm_submit"></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span></td>	      		
	      	<#else>
	      		<td height="46" colspan="2" align="center" bgcolor="#FFFDEA"><a onclick="confirmDialog();return false;" href="javascript:;" class="btbuy"></a></td>	 
	      	</#if>
	      </tr>
	    </table>
	  </div>
	</div>

	<input type="hidden" name="playType" value="${playType}"/>
    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
    <input type="hidden" name="request_token" value="${datetime().millis}" />
    <input type="hidden" name="createForm.periodId" value="${period.id}" />
    <#if playTypeWeb??>
    	<input type="hidden" name="playTypeWeb" value="${playTypeWeb}" />
    </#if>
    <#if jczqChasePlanDetailId??>
     	<input type="hidden" name="createForm.jczqChasePlanDetailId" value="${jczqChasePlanDetailId}" />
    </#if>
    <#if playTypeWeb?? && playTypeWeb=="YP">
    	<input type="hidden" name="optimizeType" id="optimizeType" value=""/>
    </#if>
    
    <input type="hidden" name="createForm.units" value="0" />
    <input type="hidden" name="createForm.schemeCost" value="0" /> 
    <input type="hidden" name="createForm.bestMinPrize" id="bestMinPrize" value="0" />
    <input type="hidden" name="createForm.bestMaxPrize" id="bestMaxPrize" value="0" />
</form>

</@override>

	<!--追号信息-->
	<div id="chase_dialog" style="display:none">
		<div id="chase_detail">
		</div>
	   <div align="center" class="a10px"> 
	  	<a onclick="$('#chase_dialog').dialog('close');return false;" href="javascript:;" class="btback"></a>&nbsp;&nbsp;
	  	<a onclick="$SSO.login_auth(confirmChasedPlan);return false;" href="javascript:;" class="confirmpaln"></a>&nbsp;&nbsp;
	  </div>
	</div>

<!--投注信息确认对话框-->
<div id="dialogDiv" style="display:none">
	<div class="w730">
	  <div class="w730title">
	    	${lotteryType.lotteryName}投注方案${currDate?string("yyyy")}年${currDate?string("MM")}月${currDate?string("dd")}日发起
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
	  <div class="k10"></div>
	  <div align="center" class="a10px"> 
	  	<a onclick="$('#dialogDiv').dialog('close');return false;" href="javascript:;" class="btback"></a>&nbsp;&nbsp;
	  	<span id="span_createForm_submitbuy"><a onclick="submitCreateForm('buy');return false;" href="javascript:;" class="btbuy"></a></span><span style="display: none;" id="span_createForm_waitingbuy"><a href="#" id="loading_tzbuy"></a></span>&nbsp;&nbsp;
	  	<span id="span_createForm_submitsave"><a onclick="submitCreateForm('save');return false;" href="javascript:;" class="btsave"></a></span><span style="display: none;" id="span_createForm_waitingsave"><a href="#" id="loading_tzsave"></a></span>	  	 
	  </div>
	</div>
</div>
<@extends name="scheme-editNew.ftl"/> 
<script>
	window.onload = function() {
		createDialog_header('#dialogDiv', '提示信息', 780);	
		createDialog_header('#chase_dialog', '这是根据您设定的回报率和起始倍数为您制定的追投计划(实际投注还是由您自己决定)', 780);	
	}
</script>
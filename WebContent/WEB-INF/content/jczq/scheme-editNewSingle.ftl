<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/matchFilterSingle.js"/>"></script>
	<script type="text/javascript">
		$(function() {
			window.PassTypeUtil = {};
			window.PassTypeUtil.getSinglePassType = function(matchCount){
				if (matchCount < 1 || matchCount > 15) 
			      	return [];
			    if(matchCount == 1)
			    	return [PassTypeArr[0]];
			      
			    var types = [];
			    for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
			      	var type = PassTypeArr[i];
			      	if (type.matchCount <= matchCount && type.units == 1 && type.matchCount <= window.maxPassMatchCount) {
			        	if(matchCount > 1 && i == 0)
			          	continue;
			        	types.push(type);
			      	}else if (type.matchCount > matchCount || type.matchCount > window.maxPassMatchCount){
			        	break;
			      	}
			    }
			    return types;
			};
		});
		<#-- 注：此JS必须在singleInit.js之前 -->
	</script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/singleInit.js"/>"></script>
</@override>

<@override name="editNewStyle">
	<style type="text/css">
	<!--
	.white {color:#FFF;}
	.red {color:#F00;}
	.dsscbb {height:35px; background:#FFFDEA; text-align:center; line-height:35px; font-weight:bold; border-bottom:1px solid #CCC;}
	.dsscaa {display:block; overflow:hidden;}
	.dsscaa ul {list-style:none; padding:0; margin:0;}
	.dsscaa ul li {float:left; width:312px; border-bottom:1px solid #D7E2E6; padding:8px 10px; color:#666;}
	-->
	</style>
</@override>

<@override name="left">
<div class="border04 marginb20">
	<form id="createForm" action="${base}/${lotteryType.key}/scheme!create.action" method="post" autocomplete="off">
	
	<div class="sssx01">
	  	<div style="float:left;">
	  	  <div>
	  	    <label><input type="radio" name="uploadType" value="SELECT_MATCH" onclick="chgUploadType(this);" checked="checked" />选择场次</label>
            <label><input type="radio" name="uploadType" value="CONTAIN_MATCH" onclick="chgUploadType(this);"/>上传已包含场次的文本</label>
            <!--<label><input type="radio" name="uploadType" value="AHEAD" onclick="chgUploadType(this);"/>先发起后上传</label>-->
  	      </div>
	  	</div>
		<div style="float:right;">截止时间：以各场次中的截止时间为准</div>
	</div>
	
	<div class="jjyhtit white_FFF_14"><strong>第一步：选择场次</strong></div>
	
	<div class="sssx01" id="stepOneOper">
	  	<div style="float:left;"><a class="ssxsan" href="javascript:;" onclick="javascript:$$('sssxxzdiv').style.display='';"></a>&nbsp;&nbsp;隐藏 <strong><span id="c_h" class="rc1">0</span></strong> 场比赛&nbsp;&nbsp;<a href="javascript:;" onclick="matchFilterReset();return false;" class="blue_0091d4">恢复</a>&nbsp;&nbsp;
	  		<input id="s_hdp1" onclick="matchFilter();" checked="checked" type="checkbox" autocomplete="off"/>让球(<span class="rc1">${handicapCount!}</span>)&nbsp;&nbsp;
	        <input id="s_hdp0" onclick="matchFilter();" checked="checked" type="checkbox" autocomplete="off"/>非让球(<span class="rc1">${unHandicapCount!}</span>)
	    </div>
		<div style="float:right;">
		  <input type="radio" name="languange" onclick="chgLanguange(0);return false;" checked="checked"/>国语 
		  <input type="radio" name="languange" onclick="chgLanguange(1);return false;"/>粤语
		</div>
		
		<!--赛事筛选框-->
		<div id="sssxxzdiv" style="display:none;">
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
  	</div>
  	    
    <div class="dsscdiv" id="stepOneMatchs">
    <#if matchMap??>
		<#list matchMap.entrySet() as entry>
			<div style="border:1px solid #CCC;">
				<div class="dsscbb"><strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong></div>
				<div class="dsscaa" id="${entry_index}">
					<ul>
						<#list entry.getValue() as match>
							<#assign saleEnd=match.isNotDisplay(playType,passMode)/>
							<#if !saleEnd>
							<li id="${match.matchKey}" text_${match.matchKey}="${match.matchKeyText}" _d="${entry_index}" _m="1" 
								<#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> 
								_h="${match.handicap!0}" 
								_e="<#if saleEnd>1<#else>0</#if>">
								<input id="chb_select_${match.matchKey}" type="checkbox" value="${match.matchKey}" onclick="selectMatch(this);" autocomplete="off" /> ${match.lineId?string('000')}  
								<a href="javascript:;"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></a> 
								${match.matchTime?string('HH:mm')} 
								<a href="javascript:;"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></a>
							</li>
							</#if>
						</#list>
					</ul>
				</div>
		  </div>
		</#list>
	</#if>	  
	</div>

	<div class="border04 marginb20">
  	  <div class="jjyhtit white_FFF_14">第二步：选择过关方式和倍数</div>
      <div style="color:#333;">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="jjyhtab">
          <tr id="tr_itemShow">
            <td width="11%" height="30" align="center" class="bgD5F6FD"><strong><a href="javascript:;" onclick="cleanSelectedMatch();return false;"></a></strong>
              <strong><a  href="javascript:;" onclick="cleanSelectedMatch();return false;"><img src="${base}/V1/images/button03.gif" width="34" height="17" border="0" align="middle" /></a> 已选场次</strong></td>
            <td bgcolor="#FFFFFF" id="selected_match_container">请选择竞赛场次</td>
          </tr>
          <tr>
            <td width="11%" height="30" align="center" class="bgD5F6FD"><strong>过关方式</strong></td>
            <td bgcolor="#FFFFFF" id="pass_container">请选择竞赛场次</td>
          </tr>
          <tr>
            <td width="11%" height="30" align="center" class="bgD5F6FD"><strong>投注倍数</strong></td>
            <td bgcolor="#FFFFFF"><input type="text" value="1" size="3" style="border:1px solid #afbbc7; text-align:right; height:20px; padding:0 6px;" onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" maxLength="3" class="txtbox color_fff" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off"/>倍</td>
          </tr>
        </table>
      </div>
	</div>
    
	<div class="border04 marginb20">
  	  <div class="jjyhtit white_FFF_14">第三步：上传方案</div>
      <div style="color:#333;">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="jjyhtab">
          <tr>
            <td width="11%" height="30" align="center" class="bgD5F6FD"><strong>文件上传</strong></td>
            <td bgcolor="#FFFFFF"> 
            	<input type="hidden" name="createForm.fileUpload" value="true" />
            	<span id="format">           	
            	格式说明：<#if itemMap??>
            				<#list itemMap.get(playType?string) as item>
            					<#if item_index lt 3>
            						${item.text}→<span>${item.value}</span>&nbsp;
            					</#if>
            					<#if item_index==3>
            						...
            					</#if>
            					<input type="hidden" name="createForm.codes" _d="${item.value}" value="${item.value}"/>
            				</#list>
            			</#if>
            		</span>
            	<a href="javascript:;" id="update_single">修改</a> <br/>
            	<input type="file" name="createForm.upload" id="createForm_upload" onchange="chgUploadFile(this);" disabled="disabled" style="border:1px solid #afbbc7; height:22px;" /><input type="button" id="createForm_autoCount" value="重新计算注数" onclick="chgUploadFile(document.getElementById('createForm_upload'));" disabled="disabled" class="btn" /><span id="upload_waiting" style="display:none">正在计算注数...</span>
            </td>
          </tr>
          <tr>
            <td height="30" align="center" class="bgD5F6FD"><strong>我的余额</strong></td>
            <td bgcolor="#FFFFFF">
            	<div id="common_logined" style="display:none">
            		<span class="gc0" id="common_login_name"></span>
            			<strong><span class="rc1" id="common_remainMoney"></span></strong> 元 [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action">帐户充值</a> ]
            	</div>
            	
            		<#if loginUser??>
	              		<div id="user_logined">
	              			<strong><span class="rc1" id="common_remainMoney">${loginUser.remainMoney?string.currency}</span></strong> 元 [ <a id="confirm_tz_payPer_a" href="${base}/user/fund!payPer.action">帐户充值</a> ]		              		
		              	</div>	       
		        	<#else>
		        		<div id="common_unlogin">未登录 【<a href="javascript:void(0)" onclick="$('#userquickLogin').dialog('open'); return false;" class="blue_0091d4">登录</a>】</div>		        	              	
		        	</#if>
 
            </td>
          </tr>
          <tr>
            <td height="30" align="center" class="bgD5F6FD"><strong>方案金额</strong></td>
            <td bgcolor="#FFFFFF">共 <strong><span class="rc1" _name="createForm_schemeCost">0</span></strong> 元（单倍注数<span class="rc1"><strong><span class="rc1" _name="createForm_units">0</span></strong></span>注）</td>
          </tr>
          <tr>
            <td height="30" align="center" class="bgD5F6FD"><strong>购彩类型</strong></td>
            <td bgcolor="#FFFFFF">
            	<input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="chgShareType(this);"  checked="checked"/><label for="createForm_shareType_SELF">代购&nbsp;</label>
          		<input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="chgShareType(this);"/><label for="createForm_shareType_TOGETHER">合买&nbsp;</label>
          		<input id="freeSave" type="radio" value="true" name="freeSave" onclick="chgShareType(this);"/><label for="freeSave">免费保存</label>
          		<a href="#"><img src="${base}/V1/images/wenhao.gif" width="12" height="12" border="0" tips="<div style='width:400px;border:1px solid #EB6502;background-color:#FEEDDD;padding:5px;line-height:20px;'>免费保存是指将方案寄存到我的账户中，方便在方案截止前随时付款购买。</div>" onmouseover="SaveTips(this)"/></a>
          	</td>
          </tr>
          <tr id="tr_TOGETHER_content" style="display:none">
            <td height="30" align="center" class="bgD5F6FD">&nbsp;</td>
	      	<td bgcolor="#FFFFFF">
	      		<table width="100%" cellspacing="0" cellpadding="1" border="0" style="background-color:#ccc;">
		            <tr align="left" class="tdwhitelist"  height="24">
			          	<td width="11%" style="text-align:right">最低认购： </td>
			          	<td style="text-align:left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="inputw" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
			        </tr>
			        <tr align="left" class="tdwhitelist"  height="24">
			          	<td style="text-align:right">我要认购：</td>
			          	<td style="text-align:left">
			              <input name="createForm.subscriptionCost" type="text" size="7" class="inputw" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="subscriptionPerSpan">0.00</span>%</span>
			          	</td>
			        </tr>
			        <tr align="left" class="tdwhitelist"  height="24">
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
			        <tr align="left" class="tdwhitelist"  height="24">
			            <td style="text-align:right">我要保底：</td>
			            <td style="text-align:left"><input name="createForm.baodiCost" type="text" size="7" class="inputw" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例 <span style="font-weight:bold;color:#D62F2F;"><span id="baodiCostPerSpan">0.00</span>%</span></td>
			        </tr>
			        <tr align="left" class="tdwhitelist"  height="24">
			            <td style="text-align:right">方案描述：</td>
			            <td style="text-align:left"><textarea style="border:solid 1px #BABABA; color:#787878; padding:1px;" rows="3" cols="35" name="createForm.description"></textarea></td>
			        </tr>
	          </table>
	      	</td>
	      </tr>
          <tr>
            <td height="30" align="center" class="bgD5F6FD"><strong>保密设置</strong></td>
            <td bgcolor="#FFFFFF">
            	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[0] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName} </label>
				</#list>
			</td>
          </tr>
          <tr height="30">
          	<td height="30" align="center" class="bgD5F6FD"><strong>购买协议</strong></td>
	      	<td  bgcolor="#FFFFFF"><input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label></td>
	      </tr>
          
          <tr>
            <td height="50" colspan="2" bgcolor="#FFFFFF" style="padding-left:117px;"><span id="span_createForm_submit"><a id="formSubmit" onclick="submitCreateForm();return false;" href="#" class="btbuy"></a></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span></td>
          </tr>
        </table>
      </div>
    </div>      
	<input type="hidden" name="createForm.passMode" value="PASS"/>
	<input type="hidden" name="createForm.schemeType" value="SIMPLE_PASS"/>
	<input type="hidden" name="playType" value="${playType}"/>
	<input type="hidden" name="createForm.mode" value="${salesMode}"/>
	<input type="hidden" name="createForm.periodId" value="${period.id}" />
	<input type="hidden" name="request_token" value="${datetime().millis}" />
	<input type="hidden" name="createForm.units" value="0" />
	<input type="hidden" name="createForm.schemeCost" value="0" />
	</form>	
</div>


<script>
	var arr = new Array();
</script>
<div class="popup-content" id="updateSingle" style="display:none">
<div id="divSelCodeSet" style="line-height:20px;padding:0px;">

<table bgcolor="#CCCCCC" cellspacing="1" cellpadding="2" style="line-height:20px; margin:6px 0;width:100%;">
<tbody>
<#if playType!="BF">
<tr style="background:#E7FAFE;text-align:center;">
<td>选项</td>	
						<#if itemMap??>
            				<#list itemMap.get(playType?string) as item>  
            					<td style="text-align: center;">         					
            						${item.text}
            					</td>           					
            				</#list>
            			</#if>

<td> </td>
</tr>
<tr bgcolor="#f3f3f3">

<td align="center">上传字符</td>
						<#if itemMap??>
							<#assign index=0>
							<#if playType=="BQQ">
								<#assign maxlength = 2>
							<#else>
								<#assign maxlength = 1>	
							</#if>
            				<#list itemMap.get(playType?string) as item> 
            					
	            					<td style="text-align: center;">         					
	            						<input id="TempCode${index}" type="text" style="text-align: center;" autocomplete="off"  value="${item.value}" size="2" maxlength="${maxlength}"> 
	            					</td> 
	            				 
            					<script>
	            					arr[${index}] = "${item.value}";
            					</script>
            					<#assign index=index+1>   					
            				</#list>
            				
            			</#if>
<td align="center">
<input type="button" onclick="codereload();" value="默认">
<input type="button" onclick="confirmCode();" value="确定">
</td>	
</tr>
<#else>
	 <#assign index=0/>
	<#list bfItemMap.entrySet() as entry>
		<tr style="background:#E7FAFE;text-align:center;">
			<td>选项</td>            				
				<#assign itemArr=entry.getValue()/>
				<#assign itemArrSize=itemArr?size />
            	<#list 0..(itemArrSize-1) as i>
            		 <#assign item=itemArr[i] />
            		 <#if itemArrSize == 5 && i == 4>            		 	
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;"></td>
            		 	<td style="text-align: center;">         					
	            			${item.text}
	            		</td>            		
            		<#else>
            		 	<td style="text-align: center;">         					
	            			${item.text}
	            		</td>
            		</#if>  
            	</#list>		
			<td> </td>
			</tr>
			<tr bgcolor="#f3f3f3">			
			<td align="center">上传字符</td>			
				<#assign itemArr=entry.getValue()/>
					<#assign itemArrSize=itemArr?size />										
            		<#list 0..(itemArrSize-1) as i>
            			 <#assign item=itemArr[i] />  
            			 	 <#if itemArrSize == 5 && i == 4>            			 	
		            			<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;"></td>
		            		 	<td style="text-align: center;">         					
		            				<input id="TempCode${index}" type="text" style="text-align: center;"  value="${item.value}" size="2" maxlength="2"> 
		            			</td>	    	            				 
	            				<script>
		            				arr[${index}] = "${item.value}";
	            				</script>
            			 	 <#else>         			 
	            				<td style="text-align: center;">         					
		            				<input id="TempCode${index}" type="text" style="text-align: center;"  value="${item.value}" size="2" maxlength="2"> 
		            			</td> 	            				 
	            				<script>
		            				arr[${index}] = "${item.value}";
	            				</script>
            				</#if> 
            			 <#assign index=index+1>   
            		</#list>
			
			<td align="center">
			<input type="button" onclick="codereload();" value="默认">
			<input type="button" onclick="confirmCode();" value="确定">
			</td>	
		</tr>
	</#list>
</#if>
</tbody>
</table>
	<div style="padding-left:10px;">
		<span id="shuoming0">
		<#if playType=="SPF" || playType=="RQSPF">
			<br>
			选择5场比赛5串1如：30101 或 3,0,1,0,1
			<br>
			选择5场比赛4串1如：301*0 或 3,0,1,*,0
		
		</b>
		<#elseif playType="JQS">
			<br>
			选择5场比赛5串1如：54631 或 5,4,6,3,1
			<br>
			选择5场比赛4串1如：25*46 或 2,5,*,4,6
			<br>
		<#elseif playType="BF">	
			<br>
				选择3场比赛3串1如：31WW33 或 31,WW,33
			<br>
				选择3场比赛3串1如：32**DD 或 32,**,DD
			<br>
		<#elseif playType="BQQ">
			<br>
			选择3场比赛3串1如：311133 或 31,11,33
			<br>
			选择3场比赛3串1如：1131** 或 11,31,**
			<br>	
		</#if>	
			<br>
			</span>
			<span id="shuoming1" style="display:none">
			2串1(
			<font color="red">1001:周一001，1006:周一006</font>
			)如：
			<br>
			1001:[3]/1006:[0]
			<br>
			1001→3,1006→0
			<br>
			倍投(玩法|内容|串关:倍投)如下：
			<br>
			SPF|111214001=3,111214006=1|2*1:45
			<br>
			SPF|1001:[3]/1006:[0]|2*1:45
			<br>
			SPF|1001→3,1006→0|2*1:45
			<br>
		</span>
		<b>
		①每一行为一组方案（最多14场）
		<br>
		②每场比赛之间可用英文逗号隔开
		<br>
		③多串过关支持包含多种过关方式
		</b>
	</div>							
</div>
</div>
</div>
<script>
	createDialog_header('#updateSingle','上传',700);
	var update = document.getElementById("update_single");
	update.onclick = function() {		
		$('#updateSingle').dialog('open');
	}
	
	function codereload(){
		$("#format span").each(function(i){
			this.innerHTML = arr[i];
		})
		
		for(var i=0, maxSize = arr.length; i<maxSize; i++) {
			document.getElementById("TempCode" + i).value = arr[i];
		}
		
		$('#updateSingle').dialog('close');
	}	
	
	function confirmCode(){
		$("#format span").each(function(i){
			this.innerHTML = document.getElementById("TempCode" + i).value;
		})
		
		$("#format input").each(function(i){
			this.value = document.getElementById("TempCode" + i).value;
			
		})
		
		$('#updateSingle').dialog('close');
	}
</script>
</@override>

<@override name="right"></@override>

<@extends name="scheme-editNew.ftl"/>
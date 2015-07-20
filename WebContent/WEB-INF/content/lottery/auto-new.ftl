<head>
	<title>自动跟单</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="auto" />
	<script type="text/javascript">
		function chgLotteryType(obj){
			var form = obj.form;
			form.elements['autoForm.lotteryPlayType'].value = '';
			for ( var i = 1, len = obj.options.length; i < len; i++) {
				var op = obj.options[i];
				if (op.selected) {
					var lotteryPlayType = op.getAttribute('_lotteryType');
					if(lotteryPlayType != null){
						form.elements['autoForm.lotteryPlayType'].value = lotteryPlayType;
					}
					break;
				}
			}
		}
	</script>
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'auto'/>
        <#include "../user/left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright" style="border:0">
	<#include "/common/message.ftl" />

	<div style="width:686px;">
      <div class="yhzxdivtit white_FFF_14">新增自动跟单</div>
  <div style="color:#333;">
  		<form method="post" action="<@c.url value="/lottery/auto!doNew.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}" id="zdgdForm"> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab" style="font-size:14px;">
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单人用户名：</td>
                <td bgcolor="#FFFFFF"><input name="autoForm.sponsorUserName" value="<#if autoForm??&&autoForm.sponsorUserName??>${autoForm.sponsorUserName!}</#if>" type="text" size="30" /></td>
              </tr>
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单上限：</td>
                <td bgcolor="#FFFFFF">  <input name="autoForm.periodMaxFollowCost" value="<#if autoForm??&&autoForm.periodMaxFollowCost??>${autoForm.periodMaxFollowCost!}</#if>" type="text" size="30" />
                  <span class="black_888_14">(本规则每期跟单的上限金额)</span></td>
              </tr>
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单彩种：</td>
                <td bgcolor="#FFFFFF"><#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
													<select name="autoForm.lotteryType" onchange="chgLotteryType(this)">
														<#list lotteryTypeArr as type>
																<#if type == 'SFZC'>
																	<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.zc.PlayType@values()") />
										                   			<#list lotteryPlayTypeArr as play>
										                   			<option _lotteryType="${play.ordinal()}" <#if (lotteryType?? && lotteryType==type && lotteryPlayType?? && lotteryPlayType=play.ordinal())||(autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal())>selected="selected"</#if> value="${type}">${play.text}</option>	
										                   			</#list>
																<#elseif type == 'DCZC'>
																	<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()") />
										                   			<#list lotteryPlayTypeArr as play>
										                   			<option _lotteryType="${play.ordinal()}" <#if (lotteryType?? && lotteryType==type && lotteryPlayType?? && lotteryPlayType=play.ordinal())||(autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal())>selected="selected"</#if> value="${type}">${play.text}</option>	
										                   			</#list>
																<#elseif type=="JCZQ" || type=="JCLQ" || type=="LCZC" || type=="SCZC">
																	<option <#if (lotteryType?? && lotteryType==type)||(autoForm??&&autoForm.lotteryType?? && autoForm.lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
																</#if>
														</#list>
													</select>
													<input type="hidden" name="autoForm.lotteryPlayType" value="<#if lotteryPlayType??>${lotteryPlayType!}<#elseif autoForm?? && autoForm.lotteryPlayType??>${autoForm.lotteryPlayType!}</#if>" />

                  <span class="black_888_14">(指定我要跟单的彩种)</span></td>
              </tr>
              <tr>
                <td height="40" rowspan="2" align="right" bgcolor="#D5F6FD" class="black_222_14">状态：</td>
                <td bgcolor="#FFFFFF">
<input type="radio" name="autoForm.followType" value="FUND"  onclick="typechang()" <#if autoForm?? && autoForm.followType??&& autoForm.followType=='FUND'>checked</#if>/>按金额跟单:&nbsp;&nbsp;每个方案的跟单金额：   <input name="autoForm.followCost" class="tkxdbd" id="autoFormfollowCost"  value="<#if autoForm??&&autoForm.followCost??>${autoForm.followCost!}</#if>" type="text" size="8"/>　元

                     </td>
              </tr>
              <tr>
                <td bgcolor="#FFFFFF">
<input type="radio" name="autoForm.followType" value="PERCEND"  onclick="typechang()" <#if autoForm?? && autoForm.followType??&& autoForm.followType=='PERCEND'>checked</#if>/>按股份跟单:&nbsp;&nbsp;每个方案的跟单股份（1-99）：<input name="autoForm.followPercent" id="autoFormfollowPercent" value="<#if autoForm??&&autoForm.followPercent??>${autoForm.followPercent!}</#if>" type="text"  class="tkxdbd" size="8"  />　%
  
  </td>
              </tr>
              <tr>
                <td height="45" bgcolor="#FFFFFF">&nbsp;</td>
                <td bgcolor="#FFFFFF"><a href="#" class="zdgdan01"/ onclick="$('#zdgdForm').submit();"></a></td>
              </tr>
        </table>
        </form>
        	<script type="text/javascript">
										 typechang();
										 function typechang(){
										 	if(document.getElementsByName('autoForm.followType')[0].checked){
										 		document.getElementById("autoFormfollowPercent").style.background="#eeeeee";
										 		document.getElementById("autoFormfollowPercent").readOnly=true;
										 		document.getElementById("autoFormfollowPercent").value="";
										 		document.getElementById("autoFormfollowCost").style.background='#FFFFFF';
										 		document.getElementById("autoFormfollowCost").readOnly=false;
										 	}else if(document.getElementsByName('autoForm.followType')[1].checked){
										 		document.getElementById("autoFormfollowCost").style.background="#eeeeee";
										 		document.getElementById("autoFormfollowCost").readOnly=true;
										 		document.getElementById("autoFormfollowCost").value="";
										 		document.getElementById("autoFormfollowPercent").style.background='#FFFFFF';
										 		document.getElementById("autoFormfollowPercent").readOnly=false;
										 	}else{
										 		document.getElementsByName('autoForm.followType')[0].checked=true;
										 		typechang();
										 	}
										 	
										 }
				</script>
	  </div>
</div>
  </div>
</div>

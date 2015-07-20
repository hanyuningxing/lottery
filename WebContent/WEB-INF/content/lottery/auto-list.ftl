<head>
	<title>自动跟单</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="auto" />
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />-->
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
  <div class="yhzxright"> 
	  	  <#include "../user/user-loginInfo.ftl">
	  
    <div class="border04">
      <div class="yhzxdivtit white_FFF_14">跟单记录</div>
      <div style="color:#333; padding:10px 0;">
        <form action="<@c.url value='/lottery/auto!list.action' />" method="get">
        <table width="100%" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
          <tr>
            <td height="30" align="left" bgcolor="#FFFFFF">
            		<#assign lotteryTypeArr = webLotteryType />
                 <select name="autoForm.lotteryType" onchange="chgLotteryType(this);this.form.submit();return false" style="font-size:13px;">
					<option value="" >全部</option>
					<#list lotteryTypeArr as type>
						<#if type!='KLSF' && type!='EL11TO5' && type!='SSC' && type!='SSL'>
							<#if type == 'PL'>
		               			<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.pl.LotteryPlayType@values()") />
		               			<#list lotteryPlayTypeArr as play>
		               			<option _lotteryType="${play.ordinal()}" <#if autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal()>selected="selected"</#if> value="${type}">${play.text}</option>	
		               			</#list>
							<#elseif type == 'SFZC'>
								<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.zc.PlayType@values()") />
		               			<#list lotteryPlayTypeArr as play>
		               			<option _lotteryType="${play.ordinal()}" <#if autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal()>selected="selected"</#if> value="${type}">${play.text}</option>		
		               			</#list>
							<#elseif type == 'DCZC'>
								<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()") />
		               			<#list lotteryPlayTypeArr as play>
		               			<option _lotteryType="${play.ordinal()}" <#if autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal()>selected="selected"</#if> value="${type}">${play.text}</option>		
		               			</#list>
							<#else>
								<option <#if (lotteryType?? && lotteryType==type)||(autoForm??&&autoForm.lotteryType?? && autoForm.lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
							</#if>
						</#if>
					</#list>
				</select>
				<input type="hidden" name="autoForm.lotteryPlayType" value="<#if autoForm?? && autoForm.lotteryPlayType??>${autoForm.lotteryPlayType!}</#if>" />
              <input type="submit" name="Submit" value="查询" class="chaxun" />
              <a href="<@c.url value="/lottery/auto!autoNew.action" />" target="_blank">跟单功能定制</a></td>
          </tr>
        </table>
        <table width="100%" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#dae8f5">
              <tbody>
                <tr height="23">
                  <td width="4%" height="35" align="center" bgcolor="#d5f6fd"><strong>序</strong></td>
                  <td width="8%" align="center" bgcolor="#d5f6fd"><strong>跟单人</strong></td>
                  <td width="8%" align="center" bgcolor="#d5f6fd"><strong>彩种</strong></td>
                  <td width="10%" align="center" bgcolor="#d5f6fd"><strong>跟单上限</strong></td>
                  <td width="18%" align="center" bgcolor="#d5f6fd"><strong>跟单详细</strong></td>
                  <td width="6%" align="center" bgcolor="#d5f6fd"><strong>状态</strong></td>
                  <td width="12%" align="center" bgcolor="#d5f6fd"><strong>创建时间</strong></td>
                  <td width="12%" align="center" bgcolor="#d5f6fd"><strong>最后修改时间</strong></td>
                  <td width="11%" align="center" bgcolor="#d5f6fd"><strong>查看</strong></td>
                  <td align="center" bgcolor="#d5f6fd"><strong>操作</strong></td>
                </tr>
           <#if pagination.result?? && pagination.result?size gt 0>
		    <#list pagination.result as autorder>
                <tr>
                  <td height="35" align="center" bgcolor="#FFFFFF">${autorder.id!}</td>
                  <td height="35" align="center" bgcolor="#FFFFFF">${autorder.sponsorUserName!}</td>
                  <td height="35" align="center" bgcolor="#FFFFFF">${autorder.lotteryName}</td>
                  <td height="35" align="center" bgcolor="#FFFFFF">${autorder.periodMaxFollowCost}</td>
                  <td height="35" align="center" bgcolor="#FFFFFF"><#if autorder??&&autorder.followType??>
								${autorder.followType.typeName!}
								<#if autorder.followType=='FUND'>跟单金额为：#{(autorder.followCost!0);M2}
									                  <#elseif autorder.followType=='PERCEND'>跟单股份为：${autorder.followPercent!}%
								</#if>
							</#if></td>
                  <td height="35" align="center" bgcolor="#FFFFFF"><#if autorder??&&autorder.state??>${autorder.state.stateName!}</#if></td>
                  <td height="35" align="center" bgcolor="#FFFFFF"><#if autorder??&&autorder.createTime??>${autorder.createTime?string("yy-MM-dd HH:mm")}</#if></td>
                  <td height="35" align="center" bgcolor="#FFFFFF"><#if autorder??&&autorder.lastModifyTime??>${autorder.lastModifyTime?string("yy-MM-dd HH:mm")}</#if></td>
                  <td height="35" align="center" bgcolor="#FFFFFF"><a href="${base}/lottery/auto!detailList.action?autoForm.id=${autorder.id!}">查看跟单详情</a></td>
                  <td height="35" align="center" bgcolor="#FFFFFF">
                  			<#if autorder??&&autorder.state??>
							    <span id="stateSpan">
								 <#if autorder.state.stateValue==0><a href="<@c.url value="/lottery/auto!setSate.action?autoForm.id=${autorder.id!}" />">停用</a>
								         <#elseif autorder.state.stateValue==1><a href="<@c.url value="/lottery/auto!setSate.action?autoForm.id=${autorder.id!}" />">启用</a>
								  </#if>
								 </span>
							  </#if>| <a href="<@c.url value="/lottery/auto!edit.action?autoForm.id=${autorder.id!}" />" target="_blank">编辑</a></td>
                </tr>
             </#list>
				<#else> 
				<tr>
					<td height="32" align="center" colspan="10">无记录</td>
				</tr>
			</#if>
               
              </tbody>
        </table>
         <div class="kong10"></div>
    
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
	  </div>
    </div>
    
    <div class="kong10"></div>
    <#include "/common/message.ftl" />
<form></form>  
    <#if autoForm??&&autoForm.id??>
    <div style="width:686px;">
      <div class="yhzxdivtit white_FFF_14">修改自动跟单</div>
      <div style="color:#333;">
      <form method="post" action="${base}/lottery/auto!save.action"  onkeydown="if(event.keyCode==13){new Event(event).stop();}" id="updateGd">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab" style="font-size:14px;">
        <input name="autoForm.id"  type="hidden" value="<#if autoForm??&&autoForm.id??>${autoForm.id!}<#else>-1</#if>"/>
        		<tr>
        			<td width="95" align="left" class="tdboldbggray hm_detaillef1px">
						 <#if autoForm??&&autoForm.id??><#if autoForm.id==-1>新增<#else>修改</#if><#else>新增</#if>自动跟单
					 </td>
					<td valign="bottom" class="hm_detailrig1px">
						&nbsp;
					</td>
        		</tr>
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单人用户名：</td>
                <td bgcolor="#FFFFFF">
                 	<input name="autoForm.sponsorUserName" value="<#if autoForm??&&autoForm.sponsorUserName??>${autoForm.sponsorUserName!}</#if>" class="tkxdbd" type="text" size="30" />
                </td>
              </tr>
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单上限：</td>
                <td bgcolor="#FFFFFF">
                 <input name="autoForm.periodMaxFollowCost" value="<#if autoForm??&&autoForm.periodMaxFollowCost??>${autoForm.periodMaxFollowCost!}</#if>" type="text" size="30" class="tkxdbd"/>
                  <span class="black_888_14">(本规则每期跟单的上限金额)</span></td>
              </tr>
              <tr>
                <td height="40" align="right" bgcolor="#D5F6FD" class="black_222_14">跟单彩种：</td>
                <td bgcolor="#FFFFFF">
                 <#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
											<select name="autoForm.lotteryType" onchange="chgLotteryType(this)">
												<#list lotteryTypeArr as type>
													<#if type!='KLSF' && type!='EL11TO5' && type!='SSC' && type!='SSL'>
														<#if type == 'PL'>
								                   			<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.pl.LotteryPlayType@values()") />
								                   			<#list lotteryPlayTypeArr as play>
								                   			<option _lotteryType="${play.ordinal()}" <#if (lotteryType?? && lotteryType==type && lotteryPlayType?? && lotteryPlayType=play.ordinal())||(autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal())>selected="selected"</#if> value="${type}">${play.text}</option>	
								                   			</#list>
														<#elseif type == 'SFZC'>
															<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.zc.PlayType@values()") />
								                   			<#list lotteryPlayTypeArr as play>
								                   			<option _lotteryType="${play.ordinal()}" <#if (lotteryType?? && lotteryType==type && lotteryPlayType?? && lotteryPlayType=play.ordinal())||(autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal())>selected="selected"</#if> value="${type}">${play.text}</option>	
								                   			</#list>
														<#elseif type == 'DCZC'>
															<#assign lotteryPlayTypeArr=stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()") />
								                   			<#list lotteryPlayTypeArr as play>
								                   			<option _lotteryType="${play.ordinal()}" <#if (lotteryType?? && lotteryType==type && lotteryPlayType?? && lotteryPlayType=play.ordinal())||(autoForm?? && autoForm.lotteryType?? && autoForm.lotteryType==type && autoForm.lotteryPlayType?? && autoForm.lotteryPlayType=play.ordinal())>selected="selected"</#if> value="${type}">${play.text}</option>	
								                   			</#list>
														<#else>
															<option <#if (lotteryType?? && lotteryType==type)||(autoForm??&&autoForm.lotteryType?? && autoForm.lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
														</#if>
													</#if>
												</#list>
											</select>
											<input type="hidden" name="autoForm.lotteryPlayType" value="<#if lotteryPlayType??>${lotteryPlayType!}<#elseif autoForm?? && autoForm.lotteryPlayType??>${autoForm.lotteryPlayType!}</#if>" />
					              
                  <span class="black_888_14">(指定我要跟单的彩种)</span></td>
              </tr>
              <tr>
                <td height="40" rowspan="2" align="right" bgcolor="#D5F6FD" class="black_222_14">状态：</td>
                <td bgcolor="#FFFFFF">
<input type="radio" name="autoForm.followType" value="FUND"  onclick="typechang()" <#if autoForm?? && autoForm.followType??&& autoForm.followType=='FUND'>checked</#if>/>按金额跟单:&nbsp;&nbsp;每个方案的跟单金额：   <input name="autoForm.followCost" id="autoFormfollowCost"  value="<#if autoForm??&&autoForm.followCost??>${autoForm.followCost!}</#if>" type="text" size="8" />　元

                 </td>
              </tr>
              <tr>
                <td bgcolor="#FFFFFF">
<input type="radio" name="autoForm.followType" value="PERCEND"  onclick="typechang()" <#if autoForm?? && autoForm.followType??&& autoForm.followType=='PERCEND'>checked</#if>/>按股份跟单:&nbsp;&nbsp;每个方案的跟单股份（1-99）：<input name="autoForm.followPercent" id="autoFormfollowPercent" value="<#if autoForm??&&autoForm.followPercent??>${autoForm.followPercent!}</#if>" type="text"  size="8" />　%
                
                </td>
              </tr>
              <tr>
                <td height="45" bgcolor="#FFFFFF">&nbsp;</td>
                <td bgcolor="#FFFFFF"><a href="#" class="zdgdan02"/ onclick="$('#updateGd').submit();"></a></td>
              </tr>
        </table>
       </form>
	  </div>
</div>

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
 </#if>   
  </div>
</div>

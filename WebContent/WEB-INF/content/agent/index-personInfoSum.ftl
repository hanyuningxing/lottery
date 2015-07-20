<meta name="decorator" content="agent" />
<meta name="menu" content="count" />
<script>
	   function seeUser(userId,userName){
		    $floater({
				width : 250,
				height : 250,	
				src : window.BASESITE + '/agent/index!seeUserRebate.action?userId='+userId,
				title : userName+'的返点',
				fix : 'true'
			});
	   }
</script>
 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">个人总报表</td>
              </tr>
            </table>
</div>
      <div class="right_c">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #718598;">
          <tr>
            <td style="border-bottom:1px solid #718598;" height="44" bgcolor="#ebedf0">
              <div style="background-color:#d7dce2; margin:1px; width:100%; height:44px">
              	<form name="queryForm" action="${base}/agent/index!findAgentPersonInfoSum.action" method="get">
              		 <input type="hidden" name="userId" value="${userId!}"/>
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="5%" height="44">&nbsp;</td>
	                        <td width="5%">会员名：</td>
	                        <td width="12%"><input type="text" class="input_01	" name="userName" value="${userName!}"/></td>
	                        <td width="5%">下属类型：</td>
	                        <td width="12%">
	                            <#assign agentUserTypeArr=stack.findValue("@com.cai310.lottery.common.AgentUserType@values()") />
							     <select name="agentUserType" onchange="document.forms['queryForm'].submit();return false;">
										 <#list agentUserTypeArr as agentUser>
										    <option <#if agentUserType?? && agentUserType==agentUser>selected="selected"</#if> value="${agentUser}">${agentUser.typeName}</option>
										 </#list>
							     </select>
						    </td>
	                        <td width="78%"><input type="submit" class="button" onclick="document.forms['queryForm'].submit();return false;" value="筛选" /> </td>
	                      </tr>
	                </table>
	            </form>
              </div>
            </td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <td style="text-align:left"><#include "${base}/common/message.ftl" /></td>
	     </tr>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="con_table">
          <tr>                                                                                                            
            <th scope="col">用户ID </th>
            <th scope="col">用户名</th>
            <th scope="col">余额 </th>
            <th scope="col">投注</th>
            <th scope="col">充值</th>
            <th scope="col">提款</th>
            <th scope="col">奖金</th>
            <th scope="col">拥金</th>
            <th scope="col">赠送</th>
            <th scope="col">盈亏</th>
            <th scope="col">最后操作时间</th>
            <th scope="col">操作</th>
          </tr>
           <#if pagination??&& (pagination.result![])?size gt 0>
		    	<#list pagination.result as data>
		    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			      <td>${data.userId!}</td>
			      <td><a href="${base}/agent/index!findAgentPersonInfoSum.action?userId=${data.userId!}<#if agentUserType??>&agentUserType=${agentUserType!}</#if>" target="_blank">${data.userName!}</a>(${data.userAgent})</td>
			      <td><#if data.remainMoney??>#{data.remainMoney;M2}</#if></td>
			      <td><#if data.bet_money??>#{data.bet_money;M2}</#if></td>
			      <td><#if data.ips_money??>#{data.ips_money;M2}</#if></td>
			      <td><#if data.drawing_money??>#{data.drawing_money;M2}</#if></td>
			      <td><#if data.prize_money??>#{data.prize_money;M2}</#if></td>
			      <td><#if data.rebate_money??>#{data.rebate_money;M2}</#if></td>
			      <td><#if data.luck_money??>#{data.luck_money;M2}</#if></td>
			      <td><#if data.yingkui??>#{data.yingkui;M2}</#if></td>
			      <td><#if data.lastModifyTime??>${data.lastModifyTime?string('yyyy-MM-dd HH:mm')}</#if></td>
			      <td><a href="#" onclick="seeUser('${data.userId!}','${data.userName!}');">查看返点</a> | <a href="${base}/agent/index!oprUserRebate.action?userId=${data.userId!}">调整配额</a> |<a href="${base}/agent/index!findAgentPersonInfo.action?userId=${data.userId!}">查看明细</a> | <a href="${base}/agent/user!fundList.action?userId=${data.userId!}" target="_blank">资金明细</a></td>
			     </tr>
		    	</#list>
		    <#else> 
		    <tr>
		      <td class="trw" align="center" colspan="11">无记录</td>
		    </tr>
	    </#if>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="${base}/pages/agent/images/right_02.jpg" height="32" style="border:1px solid #c3cdd5; border-top:none; text-align:right">
            	  <#import "../../macro/pagination.ftl" as b />
            	  <@b.page />
            </td>
          </tr>
        </table>


      </div>      
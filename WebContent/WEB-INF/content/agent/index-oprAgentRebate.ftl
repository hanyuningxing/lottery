<meta name="decorator" content="none" />
<style type="text/css">
.con_table{border:1px solid #a5a9af; border-top:none;}
.con_table th , .con_table td{ border-bottom:1px solid #c3cdd5; border-right:1px solid #c3cdd5;}
.con_table th{height:25px; repeat-x left top; line-height:29px;}
.con_table td{padding:0px 0; text-align:center}
</style>
<form action="<@c.url value='/agent/index!oprAgentRebate.action' />" method="post" id="agent_oprUserRebate_form">
<input type="hidden" name="request_token" value="${datetime().millis}" />
<input type="hidden" name="userId" value="${userId!}" />
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="con_table">
                    <tr >
					                        <th width="40%" scope="row">彩种(${userName!})</th>
					                        <td width="20%">返点</td>
					                        <td width="20%">我的返点</td>
					                        <td width="20%">调整</td>
					                      </tr>
                        	   <#if agentRebateList?exists&&agentRebateList?has_content>
                        	      <#list agentRebateList as data>
                        	           <#assign agentLotteryType=data.agentLotteryType />
                        	              
                        	              <tr <#if agentLotteryType=='EL11TO5'||agentLotteryType=='PL3D'||agentLotteryType=='NUMBER'||agentLotteryType=='KENO'>style="display:none"</#if>>
					                        <th width="40%" scope="row"> ${agentLotteryType.typeName}</th>
					                        <td width="20%"><#if data.rebate??>#{(data.rebate!0);M1}</#if></td>
					                        <td width="20%">
					                                  <#list myAgentRebateList as myRebate>
			                        	                   <#if myRebate.agentLotteryType??&&agentLotteryType==myRebate.agentLotteryType>
			                        	                      <#if myRebate.rebate??>#{(myRebate.rebate!0);M1}</#if>
			                        	                   </#if>
			                        	              </#list>
					                        </td>
					                        <td width="20%">
					                            <select name="${agentLotteryType}">
					                            
					                                <#list agentLotteryTypeList as myAgentLotteryType>
			                        	                   <#if myAgentLotteryType==agentLotteryType>
			                        	                      <#list myAgentLotteryType.rebateList as rebate>
								                               		<option value="${rebate}" <#if myAgentLotteryType.choose??&&rebate?string ==myAgentLotteryType.choose?string>selected</#if>>${rebate}</option>
								                              </#list>
			                        	                   </#if>
			                        	            </#list>
					                            </select>
					                        </td>
					                      </tr>
                        	      </#list>
                        	   </#if>
                        	    <tr >
					                        <th width="40%" scope="row">&nbsp;</th>
					                        <td width="20%">&nbsp;</td>
					                        <td width="20%">&nbsp;</td>
					                        <td width="20%" align="center"><span style="float: left;padding-left:15px;"><a class="yhzxli04" href="javascript:;" onclick="oprUserRebate();return false;" id="oprUserRebate_a">调整</a></span></td>
					           </tr>
                        	   
                    </table>
 
      </form>  
<meta name="decorator" content="none" />
<style type="text/css">
.con_table{border:1px solid #a5a9af; border-top:none;}
.con_table th , .con_table td{ border-bottom:1px solid #c3cdd5; border-right:1px solid #c3cdd5;}
.con_table th{height:25px; repeat-x left top; line-height:29px;}
.con_table td{padding:0px 0; text-align:center}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="con_table">
                      
                    <tr >
					                        <th width="50%" scope="row">彩种</th>
					                        <td width="50%">返点</td>
					                      </tr>
                        	   <#if agentRebateList?exists&&agentRebateList?has_content>
                        	      <#list agentRebateList as data>
                        	           <#assign agentLotteryType=data.agentLotteryType />
                        	          
                        	          
                        	              <tr <#if agentLotteryType=='EL11TO5'||agentLotteryType=='PL3D'||agentLotteryType=='NUMBER'||agentLotteryType=='KENO'>style="display:none"</#if>>
					                        <th width="50%" scope="row"> ${agentLotteryType.typeName}</th>
					                        <td width="50%"><#if data.rebate??>#{(data.rebate!0);M1}</#if></td>
					                      </tr>
                        	      </#list>
                        	   </#if>
                    </table>

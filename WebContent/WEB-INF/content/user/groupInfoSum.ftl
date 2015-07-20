<head>
	<title>用户中心</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<script src="${base}/js/jquery/jquery.form.js"/></script>
	<link rel="stylesheet" href="<@c.url value="/pages/agent/css/right.css"/>" type="text/css" />
	<script>
	   function seeUser(userId,userName){		   			
			 $.get(window.BASESITE + '/agent/index!seeAgentRebate.action',{userId:userId},function(data){
			 		
			    	document.getElementById("agentRebate").innerHTML = data;
			    	$("#agentRebate").dialog('open');
			  });
	   }
	   function getUserRebate(userId,userName){		   			
			 $.get(window.BASESITE + '/agent/index!oprAgentRebate.action',{userId:userId},function(data){
			 		
			    	document.getElementById("agentRebate").innerHTML = data;
			    	$("#agentRebate").dialog('open');
			  });
	   }
	   function oprUserRebate(){
	   		var agent_oprUserRebate_form = document.getElementById("agent_oprUserRebate_form");
			if (confirm('您确认要修改吗？')) {
				var options = {
					type : 'POST',
					cache : false,
					data : {
						ajax : 'true'
					},
					success : function(data, textStatus) {
						var jsonObj = toJsonObject(data);
						var msg = jsonObj.msg;
						if (jsonObj.success == true) {
							setCommonResult(msg, jsonObj.success);
							$("#agentRebate").dialog('close');
						} else {
							setCommonResult(msg, jsonObj.success);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						setCommonResult('提交请求失败.', false);
					},
					beforeSend : function(XMLHttpRequest) {
						document.getElementById('oprUserRebate_a').style.display = 'none';
					},
					complete : function(XMLHttpRequest, textStatus) {
						document.getElementById('oprUserRebate_a').style.display = '';
					}
				};
				$(agent_oprUserRebate_form).ajaxSubmit(options);
			}
	   }
	   window.onload = function() {
			createDialog_header('#agentRebate', '返点', 400);	
	   }
	    $(function() {
			    var dates = $( "#dateEnd, #dateStart" ).datepicker({
			        dateFormat: 'yy-mm-dd',
				    monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				    dayNamesMin: ['日','一','二','三','四','五','六'],
					defaultDate: "+1w",
					changeMonth: true,
					numberOfMonths: 3,
					onSelect: function( selectedDate ) {
						var option = this.id == "dateStart" ? "minDate" : "maxDate",
							instance = $( this ).data( "datepicker" ),
							date = $.datepicker.parseDate(
								instance.settings.dateFormat ||
								$.datepicker._defaults.dateFormat,
								selectedDate, instance.settings );
						dates.not( this ).datepicker( "option", option, date );
					}
				  });
		 });
	   </script>
</head>


<div id="agentRebate" style="display:none">
	
</div>

  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
  	 <#include "user-loginInfo.ftl"/>
	 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">团队总报表</td>
              </tr>
            </table>
</div>
      <div class="right_c">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #718598;">
          <tr>
            <td style="border-bottom:1px solid #718598;" height="44" bgcolor="#ebedf0">
              <div style="background-color:#d5f6fd; margin:1px; width:100%; height:44px">
              	<form name="queryForm" action="${base}/agent/index!findAgentGroupInfoSum.action" method="get">
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="5%" height="44">&nbsp;</td>
	                        <td width="8%">会员名：</td>
	                        <td width="12%"><input type="text" class="input_01	" name="userName" value="${userName!}"/></td>
	                         <td width="8%">下属类型：</td>
	                        <td width="12%">
	                            <#assign agentUserTypeArr=stack.findValue("@com.cai310.lottery.common.AgentUserType@values()") />
							     <select name="agentUserType" onchange="document.forms['queryForm'].submit();return false;">
										 <#list agentUserTypeArr as agentUser>
										    <option <#if agentUserType?? && agentUserType==agentUser>selected="selected"</#if> value="${agentUser}">${agentUser.typeName}</option>
										 </#list>
							     </select>
						    </td>
						    <td width="7%">日期：</td>
	                        <td width="15%">
	                        	<input type="text" class="input_01	" id="dateStart" name="dateStart" value="${dateStart!}"/>
	                        </td>
	                        <td width="2%">至</td>
	                        <td width="15%">
	                        	<input type="text" class="input_01	" id="dateEnd" name="dateEnd" value="${dateEnd!}"/>
	                        </td>
	                        <td><input type="submit" class="button" onclick="document.forms['queryForm'].submit();return false;" value="筛选" /> </td>
	                      </tr>
	                </table>
	            </form>
              </div>
            </td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <td style="text-align:left"></td>
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
            <th scope="col">操作</th>
          </tr>
           <#if pagination??&& (pagination.result![])?size gt 0>
		    	<#list pagination.result as data>
		    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		    	<tr style="height:40px" class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			      <td>${data.userId!}</td>
			      <td><a href="${base}/agent/index!findAgentGroupInfoSum.action?userId=${data.userId!}<#if agentUserType??>&agentUserType=${agentUserType!}</#if>" target="_blank">${data.userName!}</a></td>
			      <td><#if data.remainMoney??>#{data.remainMoney;M2}</#if></td>
			      <td><#if data.bet_money??>#{data.bet_money;M2}</#if></td>
			      <td><#if data.ips_money??>#{data.ips_money;M2}</#if></td>
			      <td><#if data.drawing_money??>#{data.drawing_money;M2}</#if></td>
			      <td><#if data.prize_money??>#{data.prize_money;M2}</#if></td>
			      <td><#if data.rebate_money??>#{data.rebate_money;M2}</#if></td>
			      <td><#if data.luck_money??>#{data.luck_money;M2}</#if></td>
			      <td><a href="#" onclick="getUserRebate('${data.userId!}','${data.userName!}');">调整返点</a> | <a href="#" onclick="seeUser('${data.userId!}','${data.userName!}');">查看返点</a> | <a href="${base}/agent/index!findAgentGroupInfo.action?userId=${data.userId!}">查看明细</a> | <a href="${base}/agent/user!fundList.action?userId=${data.userId!}" target="_blank">资金明细</a></td>
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
  </div>
  <!--mleft end -->
  <!--mright begin -->
  <!--main end -->
</div>
<meta name="decorator" content="agent" />
<meta name="menu" content="count" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-ui-1.8.18.custom.min.js"/>"></script>
<link href="<@c.url value= "/js/jquery/css/jquery-ui-1.8.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
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
 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">个人明细报表</td>
              </tr>
            </table>
</div>
      <div class="right_c">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #718598;">
          <tr>
            <td style="border-bottom:1px solid #718598;" height="44" bgcolor="#ebedf0">
              <div style="background-color:#d7dce2; margin:1px; width:100%; height:44px">
              	<form name="queryForm" action="${base}/agent/index!findAgentPersonInfo.action" method="get">
              	     <input type="hidden" name="userId" value="${userId!}"/>
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="5%" height="44">&nbsp;</td>
	                        <td width="5%">会员名：</td>
	                        <td width="12%"><input type="text" class="input_01	" name="userName" value="${userName!}"/></td>
	                         <#assign agentLotteryTypeArr=agentLotteryTypeArr!(stack.findValue("@com.cai310.lottery.common.AgentLotteryType@values()")) />
    
     					    <td width="12%">
     					       <select name="agentLotteryType"  onchange="document.forms['queryForm'].submit();return false;">
     					            <option value="">全部彩钟</option>
								    <#list agentLotteryTypeArr as agentLottery>
								       <option value="${agentLottery}" <#if agentLotteryType??&&agentLottery==agentLotteryType>selected="selected"</#if>>${agentLottery.dName}</option>
								    </#list>
     					       </select>
     					    </td>
	                        <td width="4%">日期：</td>
	                        <td width="15%">
	                        	<input type="text" class="input_01	" id="dateStart" name="dateStart" value="${dateStart!}"/>
	                        	<select name="fromHour">
						    	     <#list 0..23 as a>
						    	          <option value="${a}" <#if fromHour??&&a?string==fromHour?string>selected="selected"</#if>>${a?string('00')}</option>
						    	     </#list>
	    	     				 </select>时
	                        </td>
	                        <td width="2%">至</td>
	                        <td width="15%">
	                        	<input type="text" class="input_01	" id="dateEnd" name="dateEnd" value="${dateEnd!}"/>
	                        	<select name="toHour">
						    	     <#list 0..23 as a>
						    	          <option value="${a}" <#if toHour??&&a?string==toHour?string>selected="selected"</#if>>${a?string('00')}</option>
						    	     </#list>
	    	     				 </select>时
	                        </td>
	                        <td width="42%"><input type="submit" class="button" onclick="document.forms['queryForm'].submit();return false;" value="筛选" /> </td>
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
            <th scope="col">编号</th>
            <th scope="col">用户名</th>
            <th scope="col">彩种</th>
            <th scope="col">投注</th>
            <th scope="col">充值</th>
            <th scope="col">提款</th>
            <th scope="col">奖金</th>
            <th scope="col">返点</th>
            <th scope="col">拥金</th>
            <th scope="col">赠送</th>
            <th scope="col">盈亏</th>
            <th scope="col">操作时间</th>
            <th scope="col">操作</th>
          </tr>
          <#assign bet_money=0 />
          <#assign ips_money=0 />
          <#assign drawing_money=0 />
          <#assign prize_money=0 />
          <#assign rebate=0 />
           <#assign rebate_money=0 />
            <#assign yingkui=0 />
              <#assign luck_money=0 />
            
           <#if pagination??&& (pagination.result![])?size gt 0>
		    	<#list pagination.result as data>
		    	<#if data_index%2==0><#assign trColor="trgray" /><#else><#assign trColor="trw" /></#if>
		    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			      <td>${data.id!}</td>
			      <td>${data.userName!}(${data.sumUser!})</td>
			      <td><#if data.agentLotteryType??>${data.agentLotteryType.dName}</#if></td>
			      <td><#if data.bet_money??>#{data.bet_money;M2}<#assign bet_money=bet_money+data.bet_money /></#if></td>
			      <td><#if data.ips_money??>#{data.ips_money;M2}<#assign ips_money=ips_money+data.ips_money /></#if></td>
			      <td><#if data.drawing_money??>#{data.drawing_money;M2}<#assign drawing_money=drawing_money+data.drawing_money /></#if></td>
			      <td><#if data.prize_money??>#{data.prize_money;M2}<#assign prize_money=prize_money+data.prize_money /></#if></td>
			      <td><#if data.rebate??>#{data.rebate;M2}<#assign rebate=rebate+data.rebate /></#if></td>
			      <td><#if data.rebate_money??>#{data.rebate_money;M2}<#assign rebate_money=rebate_money+data.rebate_money /></#if></td>
			      <td><#if data.luck_money??>#{data.luck_money;M2}<#assign luck_money=luck_money+data.luck_money /></#if></td>
			      <td><#if data.yingkui??>#{data.yingkui;M2}<#assign yingkui=yingkui+data.yingkui /></#if></td>
			      <td><#if data.timePosDate??>${data.timePosDate?string('yyyy-MM-dd HH:mm')}</#if></td>
			      <td></td>
			     </tr>
		    	</#list>
		    <#else> 
		    <tr>
		      <td class="trw" align="center" colspan="11">无记录</td>
		    </tr>
	    </#if>
	    </tr>
           <tr>              	           	                   	                    	                                                 	
            <td>当前页合计</td>
            <td>&nbsp;</td>
             <td>&nbsp;</td>
            <td>#{bet_money;M2}</td>
			 <td>#{ips_money;M2}</td>
			 <td>#{drawing_money;M2}</td>
			 <td>#{prize_money;M2}</td>
			  <td>#{rebate;M2}</td>
			 <td>#{rebate_money;M2}</td>
			  <td>#{luck_money;M2}</td>
			  <td>#{yingkui;M2}</td>
               <td>&nbsp;</td>
                <td>&nbsp;</td>
          </tr>
          <tr class="trw">
            <td>总合计</td>
           <td>&nbsp;</td>
            <td>&nbsp;</td>
             <td><#if agentPersonInfo??&&agentPersonInfo.bet_money??>#{agentPersonInfo.bet_money;M2}</#if></td>
			 <td><#if agentPersonInfo??&&agentPersonInfo.ips_money??>#{agentPersonInfo.ips_money;M2}</#if></td>
			 <td><#if agentPersonInfo??&&agentPersonInfo.drawing_money??>#{agentPersonInfo.drawing_money;M2}</#if></td>
			 <td><#if agentPersonInfo??&&agentPersonInfo.prize_money??>#{agentPersonInfo.prize_money;M2}</#if></td>
			  <td></td>
			  <td><#if agentPersonInfo??&&agentPersonInfo.rebate_money??>#{agentPersonInfo.rebate_money;M2}</#if></td>
			   <td><#if agentPersonInfo??&&agentPersonInfo.luck_money??>#{agentPersonInfo.luck_money;M2}</#if></td>
			  <td><#if agentPersonInfo??&&agentPersonInfo.yingkui??>#{agentPersonInfo.yingkui;M2}</#if></td>
               <td>&nbsp;</td>
                <td>&nbsp;</td>
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
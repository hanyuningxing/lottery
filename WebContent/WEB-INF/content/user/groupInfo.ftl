<head>
	<title>用户中心</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<link rel="stylesheet" href="<@c.url value="/pages/agent/css/right.css"/>" type="text/css" />
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
</head>

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
                <td width="95%">团队明细报表</td>
              </tr>
            </table>
</div>
      <div class="right_c">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #718598;">
          <tr>
            <td style="border-bottom:1px solid #718598;" height="44" bgcolor="#ebedf0">
              <div style="background-color:#d5f6fd; margin:1px; width:100%; height:44px">
              	<form name="queryForm" action="${base}/agent/index!findAgentGroupInfo.action" method="get">
              	     <input type="hidden" name="userId" value="${userId!}"/>
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="5%" height="44">&nbsp;</td>
	                        <td width="8%">会员名：</td>
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
	                        <td width="7%">日期：</td>
	                        <td width="20%">
	                        	<input type="text" class="input_01	" id="dateStart" name="dateStart" value="${dateStart!}"/>
	                        	<select name="fromHour">
						    	     <#list 0..23 as a>
						    	          <option value="${a}" <#if fromHour??&&a?string==fromHour?string>selected="selected"</#if>>${a?string('00')}</option>
						    	     </#list>
	    	     				 </select>时
	                        </td>
	                        <td width="2%">至</td>
	                        <td width="20%">
	                        	<input type="text" class="input_01	" id="dateEnd" name="dateEnd" value="${dateEnd!}"/>
	                        	<select name="toHour">
						    	     <#list 0..23 as a>
						    	          <option value="${a}" <#if toHour??&&a?string==toHour?string>selected="selected"</#if>>${a?string('00')}</option>
						    	     </#list>
	    	     				 </select>时
	                        </td>
	                        <td width="36%"><input type="submit" class="button" onclick="document.forms['queryForm'].submit();return false;" value="筛选" /> </td>
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
			      <td><#if data.lotteryType??>${data.lotteryType.dName}</#if></td>
			      <td><#if data.detailType??&&data.detailType=='BUY'>#{data.money;M2}<#assign bet_money=bet_money+data.money /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='PAY'>#{data.money;M2}<#assign ips_money=ips_money+data.money /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='DRAWING'>#{data.money;M2}<#assign drawing_money=drawing_money+data.money /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='PRIZE'>#{data.money;M2}<#assign prize_money=prize_money+data.money /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='REBATE'>#{data.rebate;M2}<#assign rebate=rebate+data.rebate /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='REBATE'>#{data.money;M2}<#assign rebate_money=rebate_money+data.money /></#if></td>
			      <td><#if data.detailType??&&data.detailType=='LUCK'>#{data.money;M2}<#assign luck_money=luck_money+data.luck_money /></#if></td>
			      <td><#if data.createTime??>${data.createTime?string('yy-MM-dd HH:mm')}</#if></td>
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
            <td>合计</td>
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
<head>
	<title>彩票记录</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="scheme" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
	   function oprMethod(width,height,src,title){
		    $floater({
				width : width,
				height : height,
				src : window.BASESITE + src,
				title : title,
				fix : 'true'
			});
	   }
	   $(function() {
	   
	   
	     var dates = $( "#from, #to" ).datepicker({
	        dateFormat: 'yy-mm-dd',
		    monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    dayNamesMin: ['日','一','二','三','四','五','六'],
			defaultDate: "+1w",
			changeMonth: true,
			numberOfMonths: 3,
			onSelect: function( selectedDate ) {
				var option = this.id == "from" ? "minDate" : "maxDate",
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
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-ui-1.8.18.custom.min.js"/>"></script>
	<link href="<@c.url value= "/js/jquery/css/jquery-ui-1.8.css"/>" rel="stylesheet" type="text/css" />
	
</head>
</div><!-- End demo -->
<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/ticket/user!list.action' />" method="get" id="schemeForm">
	    <div class="crighttop">
	    	<span style="float:left">
	    	  <label>
	    	       <input id="from" type="text" name="from" <#if from??>value="${from?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/>
	    	  </label>
	    	  <label><select name="fromHour">
		    	     <#list 0..23 as a>
		    	          <option value="${a}" <#if fromHour??&&a==fromHour>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	      </select>:
	    	   </label>
	    	   <label><select name="fromMin">
		    	     <#list 0..59 as a>
		    	          <option value="${a}" <#if fromMin??&&a==fromMin>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	  </select></label>
			  <label>到<input id="to" type="text" name="to" <#if to??>value="${to?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/></label>
			  <label><select name="toHour">
		    	     <#list 0..23 as a>
		    	          <option value="${a}" <#if toHour??&&a==toHour>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	      </select>:
	    	   </label>
	    	   <label><select name="toMin">
		    	     <#list 0..59 as a>
		    	          <option value="${a}" <#if toMin??&&a==toMin>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	  </select></label> 
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			                近<select name="timeFrame" onchange="this.form.submit();">
			          <option value="0" <#if timeFrame==0>selected="selected"</#if>>一天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>七天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>十五天</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>一个月</option>
			      </select>
		      </label>
		      <label>
			    <#assign lotteryTypeArr = webLotteryType />
				<select name="lotteryType" onchange="this.form.submit();">
					<option value="">全部</option>
					<#list lotteryTypeArr as type>
						<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
				</select>
		      </label>
		      <label>订单</label>
		      <label>
			    <input id="orderId" type="text" name="orderId" <#if orderId??>value="${orderId}"</#if> style="width:80px" maxlength="14"/>
		      </label>
		      <label>
		          <#assign stateTypeArr=stack.findValue("@com.cai310.lottery.common.TicketSchemeState@values()") />
				  <select name="state" onchange="this.form.submit();">
						<option value="">全部</option>
						<#list stateTypeArr as type>
							<option <#if state?? && state==type>selected="selected"</#if> value="${type}">${type.stateName}</option>
						</#list>
				  </select>
			   </label>
			   <label>
		         <input type="submit" value="查询"/>
			   </label>
			   </span>
	   </div>
	 </form>
    <div class="kong10"></div>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="hmTable">
			<thead>
				<tr align="center">
				    <td width="20" height="20">序 </td>
			        <td>期号</td>
			        <td>订单号 </td>
			        <td>彩票编号 </td>
			        <td width="50">注数</td>
			        <td width="50">倍数</td>
			        <td width="50">金额 </td>
			        <td width="50">彩票 </td>
			        <td width="60">奖金 </td>
			        <td width="100">投注时间</td>
			        <td width="30">操作</td>
				</tr>
			</thead>
			<tbody>
		     <#if pagination.result?? && pagination.result?size gt 0>
				<#list pagination.result as data>
				<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
				<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
				  <td height="32">${data_index+1}</td>
			      <td><#if data.lotteryType.key=='jczq'||data.lotteryType.key=='jclq'><#else>${data.periodNumber}</#if></td>
			      <td><#if data.orderId??><a href="#" onclick = "oprMethod(740,400,'<@c.url value="/${data.lotteryType.key}/scheme!ticket_show.action?orderId=${data.orderId}" />','查看彩票');return false;">${data.orderId}</a></#if></td>
			      <td><a href="#" onclick = "oprMethod(740,400,'<@c.url value="/${data.lotteryType.key}/scheme!ticket_show.action?schemeNumber=${data.schemeNumber}" />','查看彩票');return false;">${data.schemeNumber}</a></td>
			      <td>#{data.units;M0}</td>
			      <td>#{data.multiple;M0}</td>
			      <td>#{data.schemeCost;M0}</td>
			      <td>
			         <#if data.schemeState=="CANCEL"||data.schemeState=="REFUNDMENT"||data.schemeState=="UNFULL">失败
			         	<#elseif data.schemeState=="FULL">
			              <#if data.schemePrintState=="SUCCESS">成功
			                  <#elseif data.schemePrintState=="PRINT"||data.schemePrintState=="UNPRINT">委托中
			                  <#elseif data.schemePrintState=="FAILED">出票失败
			              </#if>
			        	<#elseif data.schemeState=="SUCCESS">成功
			         </#if>
			      </td>
			      <td><#if data.prize??>#{data.prize;M2}<#else>&nbsp;</#if></td> 
			      <td <#if data.createTime??>title="${data.createTime?string("yyyy-MM-dd HH:mm")}"</#if>><#if data.createTime??>${data.createTime?string("yyyy-MM-dd HH:mm")}</#if></td>
			      <td><a href="#" onclick = "oprMethod(740,400,'<@c.url value="/${data.lotteryType.key}/scheme!ticket_show.action?schemeNumber=${data.schemeNumber}" />','查看彩票');return false;">查看</a></td>
				</tr>
				</#list>
			  <#else>
				<tr>
				  <td colspan="12" align="center" >暂无记录.</td>
				</tr>
			  </#if>
			</tbody>
		</table>
		 <div class="kong5"></div>
			        <!-- 翻页开始 -->
				            <#import "../../macro/pagination.ftl" as b />
					        <@b.page />
	
    <div class="kong10"></div>
  </div>
</div>
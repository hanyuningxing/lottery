<head>
	<title>资金明细</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="fund" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-ui-1.8.18.custom.min.js"/>"></script>
	<link href="<@c.url value= "/js/jquery/css/jquery-ui-1.8.css"/>" rel="stylesheet" type="text/css" />
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
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'fund'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
      <form action="<@c.url value='/ticket/user!fund_list.action' />" method="get" id="fundForm">
	    <div class="crighttop">
	    	<label>
	    	             从<input id="from" type="text" name="from" <#if from??>value="${from?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/>
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
		      <label><strong>查询</strong>:</label>
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			      <select name="timeFrame">
			          <option value="0" <#if timeFrame==0>selected="selected"</#if>>一天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>七天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>十五天</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>一个月</option>
			      </select>
		      </label>
		      &nbsp;&nbsp;
		      <label>交易类型：</label>
		      <label>
			    <#assign fundDetailTypeArr=stack.findValue("@com.cai310.lottery.common.FundDetailType@values()") />
              	<select name="fundType">
					<option value="">全部</option>
					<#list fundDetailTypeArr as type>
					<#if type.superTypeName==''>
					<#else>
					<option <#if fundType?? && fundType==type>selected="selected"</#if> value="${type}">${type.superTypeName}</option>
					</#if>
					</#list>
				</select>
		      </label>
		      <a href="#" onclick="document.getElementById('fundForm').submit();return false;"> <img src="<@c.url value='/pages/images/center_btcx.gif' />" align="absmiddle"/></a>
	     </div>
	   </form>
	     <#assign totalIn=0 />
    <#assign totalOut=0 />
    <div class="kong10"></div>
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="28" height="28">序 </td>
        <td>进/出</td>
        <td>交易类型</td>
        <td>金额</td>
        <td>账户余额</td>
        <td>时间</td>
        <td>备注</td>
      </tr>
      <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if data.mode=='OUT'>
		          			<#assign totalOut=totalOut+data.money />
		          		</#if>
		          		<#if data.mode=='IN'>
		          			<#assign totalIn=totalIn+data.money />
		          		</#if>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		    <td height="32">${data_index+1}</td>
	        <td>${data.mode.typeName}</td>
	        <td>${data.type.superTypeName}</td>
	        <td>#{(data.money!0);M2}</td>
	        <td>#{(data.resultMoney!0);M2}</td>
	        <td>${data.createTime?string('yyyy-MM-dd HH:mm')}</td>
	        <td style="text-align:left;padding:2px;">${data.ticketRemarkString!}</td>
		</tr>
		</#list>
		<tr class="center_tablegray" align="center">
		 <td height="22">合计</td>
			      <td>收入</td>
			      <td>${totalIn}</td>
			      <td>支出</td>
			      <td>${totalOut}</td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			    </tr>
		</tr>
	  <#else>
		<tr>
		  <td colspan="7">暂无记录.</td>
		</tr>
	  </#if>
	 </table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
    <div class="kong10"></div>
  </div>
</div>


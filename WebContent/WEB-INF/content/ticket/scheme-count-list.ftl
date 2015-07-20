<head>
	<title>彩票统计</title>
	<meta name="decorator" content="ticket" />
	<meta name="menu" content="count" />
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
		  var dates1 = $("#from_prize, #to_prize" ).datepicker({
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
      <#assign left_menu = 'count'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/ticket/user!countList.action' />" method="get" id="schemeForm">
     <div class="crighttop">
	    	<span style="float:left">
	    	  <label>
	    	        按奖金返奖时间   从<input id="from_prize" type="text" name="from_prize" <#if from_prize??>value="${from_prize?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/>
	    	  </label>
	    	  <label><select name="fromHour_prize">
		    	     <#list 0..23 as a>
		    	          <option value="${a}" <#if fromHour_prize??&&a==fromHour_prize>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	      </select>:
	    	   </label>
	    	   <label><select name="fromMin_prize">
		    	     <#list 0..59 as a>
		    	          <option value="${a}" <#if fromMin_prize??&&a==fromMin_prize>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	  </select></label>
			  <label>到<input id="to_prize" type="text" name="to_prize" <#if to_prize??>value="${to_prize?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/></label>
			  <label><select name="toHour_prize">
		    	     <#list 0..23 as a>
		    	          <option value="${a}" <#if toHour_prize??&&a==toHour_prize>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	      </select>:
	    	   </label>
	    	   <label><select name="toMin_prize">
		    	     <#list 0..59 as a>
		    	          <option value="${a}" <#if toMin_prize??&&a==toMin_prize>selected="selected"</#if>>${a?string('00')}</option>
		    	     </#list>
	    	  </select></label> 
			   </span>
	   </div>
	    <div class="crighttop">
	    	<span style="float:left">
	    	  <label>
	    	            按彩票创建时间   从<input id="from" type="text" name="from" <#if from??>value="${from?string('yyyy-MM-dd')}"</#if> style="width:80px" maxlength="10"/>
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
		      <label>彩种：</label>
		      <label>
			    <#assign lotteryTypeArr = webLotteryType />
				<select name="lotteryType" onchange="this.form.submit();">
				    <option  value="">全部</option>
					<#list lotteryTypeArr as type>
						<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
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
			        <td>状态</td>
			        <td>票数</td>
			        <td>注数</td>
			        <td>金额</td>
			        <td>税前奖金</td>
			        <td>税后奖金</td>
			        <td width="60">操作</td>
				</tr>
			</thead>
			<tbody>
			 <#if countMap?? && countMap?size gt 0>
			        <#list countMap?keys as lottery>
				            <thead>
								<tr align="center">
								    <td colspan="8" align="left" >${lottery.lotteryName!}</td>
								</tr>
							</thead>
			                 <#assign map = countMap.get(lottery)/>
						     <#if map?? && map?size gt 0>
						        <#list map?keys as key>
						           <#assign data = map.get(key) />
									<#if (key_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
									<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
									  <td height="32">${key_index+1}</td>
								      <td><#if data.ticketSchemeState??>${data.ticketSchemeState.stateName!}</#if></td>
								      <td>#{data.version;M0}</td>
								      <td>#{data.units;M0}</td>
								      <td>#{data.schemeCost;M0}</td>
								      <td><#if data.prize??>#{data.prize;M2}</#if></td>
								      <td><#if data.prizeAfterTax??>#{data.prizeAfterTax;M2}</#if></td>
								      <td><a href="<@c.url value='/ticket/user!list.action'/>?from=<#if from??>${from?string('yyyy-MM-dd')}</#if>&to=<#if to??>${to?string('yyyy-MM-dd')}</#if>&fromHour=<#if fromHour??>${fromHour}</#if>&fromMin=<#if fromMin??>${fromMin}</#if>&toHour=<#if toHour??>${toHour}</#if>&toMin=<#if toMin??>${toMin}</#if>&lotteryType=${lottery}&state=<#if data.ticketSchemeState??>${data.ticketSchemeState}</#if>" target="_blank">查看详情</a></td>
									</tr>
								</#list>
							</#if>
				   </#list>
				<#else>
				<tr>
				  <td colspan="8" align="center" >暂无记录.</td>
				</tr>
			  </#if>
			</tbody>
		</table>
  </div>
</div>
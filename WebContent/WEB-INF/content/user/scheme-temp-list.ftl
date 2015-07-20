<head>
	<title>保存方案</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	
	<script src="${base}/jquery/ui/js/jquery-ui-timepicker-addon.js"/></script>
	<link href="${base}/jquery/ui/css/jquery-ui-timepicker-addon.css" rel="stylesheet" type="text/css" />
</head>

<script>
	$(function(){ 
		 $("#startTime").datetimepicker({
	        currentText: '现在',
			closeText: '完成',
			timeText: '小时:分钟',
			hourText: '  小时',
			minuteText: '  分钟',
			secondText: '    秒',
	        timeFormat: 'HH:mm:ss',
	        dateFormat: 'yy-mm-dd',
		    monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    dayNamesMin: ['日','一','二','三','四','五','六'],
			defaultDate: +1,
			changeMonth: true,
			showSecond: true,
			changeYear: true
		  });
	});
	
	$(function(){ 
		 $("#endTime").datetimepicker({
	        currentText: '现在',
			closeText: '完成',
			timeText: '小时:分钟',
			hourText: '  小时',
			minuteText: '  分钟',
			secondText: '    秒',
	        timeFormat: 'HH:mm:ss',
	        dateFormat: 'yy-mm-dd',
		    monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
		    dayNamesMin: ['日','一','二','三','四','五','六'],
			defaultDate: +1,
			changeMonth: true,
			showSecond: true,
			changeYear: true
		  });
	});
</script>


<!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>

<!--右边开始-->
  <div class="yhzxright">
  	  <#include "user-loginInfo.ftl"/>
  	  <#include "/common/message.ftl" />
    <div class="border04">
      <div class="zjmxtit">
		  	<ul>
				<a href="${base}/user/scheme!list.action">
				<li>投注记录</li>
			  </a>
				<a href="${base}/user/scheme-temp!list.action">
				<li class="zjmxli01">保存的方案</li>
				</a>
			</ul>
      </div>
	  <div style="color:#333; padding:10px 0;">
	  	<form action="<@c.url value='/user/scheme-temp!list.action' />" method="get" id="schemeTempForm">
            <table width="100%" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
              <tr>
                <td height="30" align="center" bgcolor="#FFFFFF">
                
                 <#assign lotteryTypeArr = stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
                 <select name="lotteryType" style="font-size:13px;" onchange="$('#schemeTempForm').submit();">
					<option value="">全部彩种</option>
					<#list lotteryTypeArr as type>
						<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
				</select>
                  
日期：
<#--<input name="textarea" id="startTime" type="text" value="" class="zjmxrl" /> -->
<input class="zjmxrl" id="startTime" type="text" name="startTime" onclick="this.value='';" <#if startTime??>value="${tick.startTime?string('yyyy-MM-dd')}"</#if>  maxlength="10" readonly="readonly"/>		           	
至
<input class="zjmxrl" id="endTime" type="text" name="endTime" onclick="this.value='';" <#if endTime??>value="${tick.endTime?string('yyyy-MM-dd')}"</#if>  maxlength="10" readonly="readonly"/>

<input type="submit" name="Submit" value="查询" onclick="$('#schemeTempForm').submit();return false;" class="chaxun" />
              </tr>
        </table>
  </form> 
            <div id="extra-data-container">
             <script>
             	$(function(){
		         		$.post(window.BASESITE + '/user/scheme-temp!listTable.action', {
		         			
		         		}, function(data){
		         			document.getElementById('extra-data-container').innerHTML = data;
		         		});
		        
		         	});
		      
		       </script>
             	<#import "../../macro/pagination.ftl" as b />
			    <@b.page {'ajaxContainer':'extra-data-container'} />
             </div>
             
             <script>
             	$(function(){
		         	$('#schemeTempForm').submit(function() {
		         		
			         	var startTime = $('#schemeTempForm [name="startTime"]').val();
			         	var endTime = $('#schemeTempForm [name="endTime"]').val();
			         	var lotteryType = $('#schemeTempForm [name="lotteryType"]').val();
		         		
		         		
		         		$.post(window.BASESITE + '/user/scheme-temp!listTable.action', {
		         			
		         			startTime: startTime,
		         			endTime: endTime,
		         			lotteryType: lotteryType
		         			
		         		}, function(data){
		         			document.getElementById('extra-data-container').innerHTML = data;
		         		});
		         	return false;
		         	});
		         });
             </script>
	  </div>
    </div>
  </div>
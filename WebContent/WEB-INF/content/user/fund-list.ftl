<head>
	<title>资金明细</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="fund" />
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->

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

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'fund'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
   <div class="yhzxright">
  	 <#include "user-loginInfo.ftl"/>
    <div class="border04">
    
	  <div style="color:#333; padding:10px 0;">
	 
	   <form action="<@c.url value='/user/fund!list.action' />" method="post" id="fundForm">
            <table width="100%" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
              <tr>
                <td height="30" align="center" bgcolor="#FFFFFF">
                <select name="timeFrame" style="font-size:13px;" onchange="$('#fundForm').submit();">
                  <option value="4" <#if timeFrame==4>selected="selected"</#if>>当天</option>
                  <option value="0" <#if timeFrame==0>selected="selected"</#if>>最近一周</option>
                  <option value="2" <#if timeFrame==2>selected="selected"</#if>>最近一个月</option>
                  <option value="3" <#if timeFrame==3>selected="selected"</#if>>最近三个月</option>
                </select>
日期：
<#--<input name="textarea" id="startTime" type="text" value="" class="zjmxrl" /> -->
<input class="zjmxrl" id="startTime" type="text" name="startTime" onclick="this.value='';" <#if startTime??>value="${tick.startTime?string('yyyy-MM-dd')}"</#if>  maxlength="15" readonly="readonly"/>		           	
至
<input class="zjmxrl" id="endTime" type="text" name="endTime" onclick="this.value='';" <#if endTime??>value="${tick.endTime?string('yyyy-MM-dd')}"</#if>  maxlength="15" readonly="readonly"/>

<#assign fundDetailTypeArr=stack.findValue("@com.cai310.lottery.common.FundDetailType@values()") />
              	<select name="fundType" onchange="$('#fundForm').submit();">
					<option value="">全部</option>
					<#list fundDetailTypeArr as type>
					<option <#if fundType?? && fundType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
					</#list>
				</select>

<a href="#" onclick="$('#fundForm').submit();return false;"> <img src="<@c.url value='/pages/images/center_btcx.gif' />" align="absmiddle"/></a>

              </tr>
        </table>
          
        </form>
        <div id="extra-data-container">
	        <script>
		         $(function(){     	
		         		$.post(window.BASESITE + '/user/fund!listTable.action', {
		         			timeFrame: 0
		         		}, function(data){
		         			document.getElementById('extra-data-container').innerHTML = data;
		         		});
		         	
		         });
	         </script>
	        
         <div class=" cleanboth pagelist" align="center">
		        <#import "../../macro/pagination.ftl" as b />
		 </div>
		<@b.page {'ajaxContainer':'extra-data-container'} />
	        
    	</div>
         
        
         <script>
         $(function(){
         	$('#fundForm').submit(function() {

         		var timeFrame = $('#fundForm [name="timeFrame"]').val();
	         	var startTime = $('#fundForm [name="startTime"]').val();
	         	var endTime = $('#fundForm [name="endTime"]').val();
	         	var fundType = $('#fundForm [name="fundType"]').val();
         	
         		$.post(window.BASESITE + '/user/fund!listTable.action', {
         			timeFrame: timeFrame,
         			startTime: startTime,
         			endTime: endTime,
         			fundType: fundType
         		}, function(data){
         			document.getElementById('extra-data-container').innerHTML = data;
         		});
         	return false;
         	});
         });
         </script> 
            
	     
	  </div>
    </div>
	  <div style="padding:20px; line-height:24px;"><strong class="bro_af5529">说明： </strong><br />
        <span class="bro_6D4F42">1、账户明细默认提供近三月的记录查询。<br />
2、以上充值记录中，状态“未完成”代表充值操作未完成，“失败”代表该次充值不成功。<br />
3、账户中的资金冻结的原因：（1）用户取款   （2）保底冻结 </span></div>
  </div>
    <!-- 版权开始 -->
    
    <div class="kong10"></div>
  </div>
</div>


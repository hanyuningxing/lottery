<head>
	<title>购彩记录</title>
	<meta name="decorator" content="trade" />
	<meta name="menu" content="scheme" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="cright">
    <#include "/common/message.ftl" />
     <form action="<@c.url value='/user/scheme!list.action' />" method="get" id="schemeForm">
	    <div class="crighttop">
	    	<span style="float:left">
		      <#if lotteryType??>
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			      <select name="timeFrame" onchange="this.form.submit();">
			          <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
			      </select>
		      </label>
		      &nbsp;&nbsp;
		      </#if>
		      <label>彩种：</label>
		      <label>
			    <#assign lotteryTypeArr = webLotteryType />
				<select name="lotteryType" onchange="this.form.submit();">
					<option value="">全部彩种</option>
					<#list lotteryTypeArr as type>
						<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
					</#list>
				</select>
		      </label>
		      &nbsp;&nbsp;
		      <label>购买状态：</label>
		      <label>
		          <#assign stateTypeArr=stack.findValue("@com.cai310.lottery.common.SchemeState@values()") />
				  <select name="state" onchange="this.form.submit();">
						<option value="">全部</option>
						<#list stateTypeArr as type>
						
							<option <#if state?? && state==type>selected="selected"</#if> value="${type}">${type.stateName}</option>
						</#list>
				  </select>
			   </label></span>
			   <span style="float:right;color:red;"><label>(全部彩种显示最近100条记录)</label></span>
	   </div>
	 </form>
    <div class="kong10"></div>
	 <#include '../common/scheme-my-list-table.ftl' /><#-- 导入方案列表 -->
	
    <div class="kong10"></div>
  </div>
</div>
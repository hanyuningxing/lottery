<head>
	<title>方案列表</title>
	<meta name="decorator" content="trade" />
</head>


<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
    <div class="k3px" >
			<div class="bggray32" >
			    <div class="floatleft" style="padding:5px 0 0 8px;">
			        <#include "/common/message.ftl" />
				     <form action="<@c.url value='/user/scheme!findUserScheme.action' />" method="get" id="schemeForm">
				        <input id="id" name="id" type="hidden" value="${id}"/>
					    <div class="crighttop">
					    	<span style="float:left">
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
			    </div>
			</div>
          <!-- 搜索结束 -->
	      <div class="hemaint">
	          <#include '../common/scheme-my-list-table.ftl' /><#-- 导入方案列表 -->
	      </div>
	    
    </div>
  </div>
  <!-- left结束 -->
</div>

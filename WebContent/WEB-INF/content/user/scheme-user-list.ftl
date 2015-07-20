<head>
	<title>方案列表</title>
	<meta name="decorator" content="tradeV1" />
	<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/pdzx.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/js/user/gradeInfo.js"></script>
	<style>
		.c14px{font-size:14px;}
		.td10pxleft{padding-left:6px;}
		.orgbold{color:#FF3300;font-weight:bold;}
		.bluebold{color:#1F5CE5;font-weight:bold;}
		.dtable{border:1px solid #97BFDD;border-bottom:none;border-right:none;border-collapse:separate;}
		.tdrb1px{}
		.tdrb1px td{border:1px solid #97BFDD;border-top:none;border-left:none;background:#E7F3FB;}
		.tdrb1pxfff{}
		.tdrb1pxfff td{border:1px solid #97BFDD;border-top:none;border-left:none;background:#fff;}
		.left8px{padding-left:8px;}
		.bcblue{font-weight:bold;color:#336881;}
	</style>
	<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
</head>

<div class="k10"></div>

<div class="main" style="width:1000px">
 <#--用户战绩信息-->
  <!-- left开始 -->
  <div>
  	<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" class="dtable">
	  <tr class="tdrb1px trtitlebg" height="26">
	    <td width="25%" class="left8px bcblue char14" >个人档案</td>
	    <td width="25%" class="left8px bcblue char14">历史战绩</td>
	    <td width="25%" class="left8px bcblue char14">数据统计</td>
	    <td width="25%" class="left8px bcblue char14">最近动态</td>
	  </tr>
	  <tr class="tdrb1pxfff">
	    <td valign="top"><div class="a10px d20">
	        <div class="fl" style="width:80px; display:block; height:80px;"><img src="${base}/V1/images/lv3.gif" /></div>
	        <div class="fl d20"> 
	          <span class="c14 bc bluebold">${user.userName}</span> <br />
	          <span class=" red">
	          	 <#if user.gradeInfo?? && user.gradeInfo.wonTimes_wan gt 0>
	          	 	万元大奖得主
	          	 <#elseif user.gradeInfo?? && user.gradeInfo.wonTimes_qian gt 0>
	          	 	千元大奖得主
	          	 <#else>
	          	           努力得奖中...
	          	 </#if>
	          </span>
	          <div class="d20 gc0">注册日期：${user.createTime?string("yyyy-MM-dd")}</div>
	          <#--<div class="d20 gc0">被关注：<b>118</b> 次 </div>-->
	        </div>
	        <div class="cb"></div>
	      </div>
	    </td>
	    <td valign="top">
	    	<div class="a10px d20">
	    	   TA 所斩获奖牌<br />
			        足球奖牌： <a onmouseover="showGradeInfo(this,buildGradeMedalInfo())"><span class="qk_football01" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_zq.golden1()*13}px;</#if>" ></span><span class="qk_football02" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_zq.golden2()*13}px;</#if>"></span><span class="qk_football03" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_zq.silvery3()*13}px;</#if>"></span></a><br />
			        篮球奖牌：  <a onmouseover="showGradeInfo(this,buildGradeMedalInfo())"><span class="qk_basketball01" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_lq.golden1()*13}px;"</#if>></span> <span class="qk_basketball02" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_lq.golden2()*13}px;</#if>"></span> <span class="qk_basketball03" style="height:12px;<#if user.gradeInfo??>width:${user.gradeInfo.medal_lq.golden3()*13}px;</#if>"></span></a><br />
			        总奖金：<span class="orgbold"><#if user.gradeInfo??>#{user.gradeInfo.totalPrize!0;M2}元<#else>--</#if></span>&nbsp;&nbsp;
		    </div>
		</td>
	    <td valign="top">
	    	<div class="a10px d20">
	    	        发单次数：<span class="bluebold"><#if user.gradeInfo??>${user.gradeInfo.fadanNums}<#else>0</#if></span><br />
			        发单成功率：<span class="bluebold"><#if user.gradeInfo??>#{(user.gradeInfo.fadanSuccessNums/user.gradeInfo.fadanNums*100);M2}%<#else>--</#if></span><br />
			        总中奖次数：<span class="bluebold"><#if user.gradeInfo??>${user.gradeInfo.wonTimes}<#else>0</#if></span><br />
			        万元大奖：<span class="bluebold"><#if user.gradeInfo??>${user.gradeInfo.wonTimes_wan}<#else>0</#if>次</span>&nbsp;&nbsp;
			        千元大奖：<span class="bluebold"><#if user.gradeInfo??>${user.gradeInfo.wonTimes_qian}<#else>0</#if>次</span>
			</div>
		</td>
	    <td valign="top">
	    	<div class="a10px d20">
	    		<#if newestLogs?? && newestLogs?size gt 0>
	    		    <#assign logSize=newestLogs?size/>
	    			<#list newestLogs as log>	    				
	    				${log.userName}<#if log.shareType=='TOGETHER'>发起</#if>${log.shareType.shareName}<#if log.newestType=='WON' && log.lottery.lotteryName?? && log.playTypeName??>${log.lottery.lotteryName}${log.playTypeName}方案：<a href="#" class="blue">${log.schemeNumber}</a>&nbsp;&nbsp;喜中奖金<span class="orgbold">￥#{log.money;M2}</span>
	    				<#elseif log.lottery.lotteryName?? && log.playTypeName??><#if log.lottery=='JCZQ' || log.lottery=='JCLQ'><#else>第${log.issueNumber}期</#if> ${log.lottery.lotteryName}${log.playTypeName}</#if>
	    				<#if log_index lt logSize><br/></#if>
	    			</#list>
	    		<#else>
	    			TA 还没有最新动态信息显示... ...
	    		</#if>
		    </div>
		</td>
	  </tr>
	</table>
  </div>
  <div class="main_czleft" style="padding-top:5px;width:1000px">  
    <div class="k3px" style="border:1px solid #97BFDD;width:998px">
			<div class="bggray32" >
			    <div class="floatleft" style="padding:5px 0 0 8px;">
			        <#include "/common/message.ftl" />
				     <form action="<@c.url value='/user/scheme!findUserScheme.action' />" method="get" id="schemeForm">
				        <input id="id" name="id" type="hidden" value="${user.id}"/>
					    <div class="crighttop">
					    	<span style="float:left">
						      <label>彩种：</label>
						      <label>
							    <#assign lotteryTypeArr = webLotteryType />
								<select name="lotteryType" onchange="this.form.submit();">
									<option value="">全部彩种</option>
									<#list lotteryTypeArr as type>
										<#if type=='JCZQ' || type=='JCLQ' || type=='DCZC' || type=='SFZC' || type=='LCZC' || type=='SCZC'>
										<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
										</#if>
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

<script>
	function buildGradeMedalInfo(){
	<#if user.gradeInfo??>
		<#assign medalsMap=user.gradeInfo.getMedalMap()/>
		<#if medalsMap?exists>		
			var medalInfoArr = new Array(${medalsMap?size});
			<#assign idx=0>
            <#list medalsMap?keys as key>
            	var medalInfoArrOfPt = new Array(5);
            	<#assign typeAndText=user.gradeInfo.getTypeAndKey2Text(key)>
            	medalInfoArrOfPt[0]='${typeAndText[0]}';
            	medalInfoArrOfPt[1]='${typeAndText[1]}';
            	<#assign gradeMedal=medalsMap.get(key)>
            	medalInfoArrOfPt[2]=${gradeMedal.golden1()};
            	medalInfoArrOfPt[3]=${gradeMedal.golden2()};
            	medalInfoArrOfPt[4]=${gradeMedal.golden3()};
            	medalInfoArr[${idx}]=medalInfoArrOfPt;
            	<#assign idx=idx+1>                
            </#list>
            return medalInfoArr;
        </#if>
       </#if>
	}
</script>

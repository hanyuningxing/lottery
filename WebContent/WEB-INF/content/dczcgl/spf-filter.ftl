<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>单场足彩过滤-${webapp.webName}网</title>
<#assign passTypeArr=passTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PassType@values()")) />

<link href="<@c.url value="/pages/css/fz.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/glq.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script></head>
<script type="text/javascript" src="<@c.url value= "/js/jquery/leeDialog/dialog.js"/>"></script>

<script type="text/javascript">
	window.maxPassMatchCount = ${playType.maxMatchSize};
	window.PassType = {};
	window.PassTypeArr = [];
	<#list passTypeArr as passType>
		window.PassTypeArr[${passType_index}] = PassType['${passType}'] = {
			key : '${passType}',
			units : ${passType.units},
			matchCount : ${passType.matchCount},
			passMatchs :[<#list passType.passMatchs as c><#if c_index gt 0>,</#if>${c}</#list>],
			text : '${passType.text}',
			value : ${passType.value}
		};
	</#list>
</script>

<script type="text/javascript">
	$(function() {
		window.PassTypeUtil = {};
		window.PassTypeUtil.getSinglePassType = function(matchCount){
			if (matchCount < 1 || matchCount > 15) 
		      	return [];
		    if(matchCount == 1)
		    	return [PassTypeArr[0]];
		      
		    var types = [];
		    for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
		      	var type = PassTypeArr[i];
		      	if (type.matchCount <= matchCount && type.units == 1 && type.matchCount <= window.maxPassMatchCount) {
		        	if(matchCount > 1 && i == 0)
		          	continue;
		        	types.push(type);
		      	}else if (type.matchCount > matchCount || type.matchCount > window.maxPassMatchCount){
		        	break;
		      	}
		    }
		    return types;
		};
	});		
	<#-- 注：此JS必须在compoundInit.js之前 -->
</script>
<!--<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/singleInit.js"/>"></script>-->
<script type="text/javascript" src="<@c.url value= "/js/lottery/dczc/filter.js"/>"></script>
<body>
<div class="main">
	    	<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()")) />

   	<#include '../common/filter-header.ftl'/>		
	     <#include "/common/message.ftl" />

   <div class="shaxiang8_index"></div>
   <div class="content">
	   <div class="left">
	       <div class="left_main1">
		        <div class="leftm_xx">
				   <div class="leftl"><a href="#">选择场次</a></div>
				   <div class="leftr"><a href="#">清除此项</a></div>
				</div>
				
				  <div class="shaxiang8_index"></div>
				     <table width="396" cellpadding="0" cellspacing="0"  class="tbbge5">
						  <tr>
							<td width="27" height="25"  class="shx">场次</td>
							<td height="25" width="110"  class="shx">对阵</td>
							<td height="25"  width="80" colspan="3"  class="shx">投注结果</td>
							<td height="25"  width="80"  class="shx">
							     <select name="select" size="1">
									<option>SP值</option>
								  </select></td>
						  </tr>
					</table>
						  <div class="left_tb">
						   <table width="396" cellpadding="0" cellspacing="0"  class="tbbge5">
						  <#if matchFilterDatas??&&matchFilterDatas?size gt 0>
						  <div id="matchDatas">
					   			<#list matchFilterDatas as data>
								  <tr trblue>
									<td width="20" height="25" id="lineId_${data[0]}">${data[0]}</td>
									<td width="57" height="25" id="homeTeamName_${data[0]}">${data[1]}</td>
									<td width="26" height="25" id="handicap_${data[0]}">${data[2]!""}</td>
									<td width="70" height="25" id="guestTeamName_${data[0]}">${data[3]}</td>
									<#if data[4]??&&"-1"==data[4]>
										<td class="ss1" width="27" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(0,${data[0]});" id="spf_win_${data[0]}"></a></td>									
									<#else>	
										<td class="ss1" width="27" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(0,${data[0]});" id="spf_win_${data[0]}">${data[4]!""}</a></td>																	
									</#if>
									<#if data[5]??&&"-1"==data[5]>	
										<td class="ss1" width="26" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(1,${data[0]});" id="spf_draw_${data[0]}"></a></td>	
									<#else>	
										<td class="ss1" width="26" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(1,${data[0]});" id="spf_draw_${data[0]}">${data[5]!""}</a></td>																		
									</#if>
									<#if data[6]??&&"-1"==data[6]>	
										<td class="ss1" width="25" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(2,${data[0]});" id="spf_lose_${data[0]}"></a></td>																											
									<#else>	
										<td class="ss1" width="25" height="25" style="text-align:center"><a href="javascript:void(0);" onclick="chgSPFValues(2,${data[0]});" id="spf_lose_${data[0]}">${data[6]!""}</a></td>																			
									</#if>
									<td width="45" height="25" id="sps_win_${data[0]}">${data[7]!""}</td>
									<td width="37" height="25" id="sps_draw_${data[0]}">${data[8]!""}</td>
									<td width="36" height="25" id="sps_lose_${data[0]}">${data[9]!""}</td>
								    <td width="43"  class="lt"><input class="cht" name="matchCk" style="display:none;" type="checkbox" value="${data[0]}" id="group_${data[0]}" checked="true"/></td>								  
								  </tr>
							  </#list>
							  </div>
						  </#if>
						</table>
				</div>
	       </div>
		   	
	   <#include '../common/filter-left-bottom.ftl' />		
	   </div>
	   <div class="right_content">
	   <!-- navmenu begin -->
	     <#include 'menu.ftl' />		     
	   <!-- navmenu end -->
		<#include 'column-condition.ftl' />		        
		<#include 'right-bottom.ftl' />		

   </div>
</div>



</body>
</html>

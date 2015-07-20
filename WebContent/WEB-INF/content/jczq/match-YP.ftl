<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>球客彩票网竞9投注</title>
<meta name="Keywords" content="球客彩票网竞9投注" />
<meta name="Description" content="球客彩票网竞9投注" />
<link href="${base}/V1/css/qiuke_tb.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/qktz.css" rel="stylesheet" type="text/css" />
<link href="${base}/V1/css/jczq.css" rel="stylesheet" type="text/css" />
<script>
		$(function(){
			matchKeyOfMaxFanjianglvArr = [];
			<#assign matchKeyOfMaxFanjianglvArr = 0 />
			<#if fangjianglvAndMatchKeyMap??>
				<#list fangjianglvAndMatchKeyMap.entrySet() as entry>
					<#assign matchKey_a = entry.getValue()>
						matchKeyOfMaxFanjianglvArr[matchKeyOfMaxFanjianglvArr.length] = '${matchKey_a}';
						<#if matchKey_a.substring(0,8) == today.toString()>
							<#assign matchKeyOfMaxFanjianglvArr = matchKey_a />
						</#if>
				</#list>			
			</#if>
			var date = new Date().format("yyyyMMdd");
			//选出当天返奖率最高的比赛
			for(var i=matchKeyOfMaxFanjianglvArr.length-1; i>=0; i--) {
				if(matchKeyOfMaxFanjianglvArr[i].substr(0, 8) == date) {
					matchKeyOfMaxFanjianglv = matchKeyOfMaxFanjianglvArr[i];
					break;
				}
			}								
			
			<#if playTypeWeb??>
				playTypeWeb = "${playTypeWeb}";
			</#if>
			
			if(typeof matchKeyOfMaxFanjianglv == "undefined")
				return;
			var hd0 = document.getElementById("handicap_" + matchKeyOfMaxFanjianglv).innerHTML;
			if(parseInt(hd0) == -1) {
				document.getElementById("SPF_td_" + matchKeyOfMaxFanjianglv + "_0").onclick();
				document.getElementById("RQSPF_td_" + matchKeyOfMaxFanjianglv + "_2").onclick();		
			} else if(parseInt(hd0) == 1) {
				document.getElementById("SPF_td_" + matchKeyOfMaxFanjianglv + "_2").onclick();
				document.getElementById("RQSPF_td_" + matchKeyOfMaxFanjianglv + "_0").onclick();	
			}
		})
</script>

<style type="text/css">
#zhishu {border:3px solid #ccc; color:#333; width:300px; font-family:Tahoma, '宋体'; font-size:12px; position:absolute;background:#FFF;}
.zstit {text-align:center; padding:8px 0; background:#1898ba; color:#FFF; font-size:14px; margin-bottom:1px;}
.zhishutab {color:#333;}
.zhishutab tr td {border-bottom:1px solid #e4e4e4;}
</style>
<style>
a.open_sp {
	background: url(${base}/images/closeopen.png) no-repeat 0px 0px;
	height: 24px;
	width: 76px;
	margin:0 auto;
	display: block;
}
a.open_sp:hover {
	background: url(${base}/images/closeopen.png) no-repeat 0px -24px;
	height: 24px;
	width: 76px;
	margin:0 auto;
	display: block;
}
a.close_sp {
	background: url(${base}/images/closeopen.png) no-repeat 0px -48px;
	height: 24px;
	width: 76px;
	margin: 0 auto;
	display: block;
}

.tzleftname_pktz{width:160px;background:url(${base}/images/hhgg.png) no-repeat 0 -549px;height:103px;float:left;}

</style>
</head>
<body>
<!-- top -->
<div class="k10"></div>
<!--All top end -->
<!--main-->
<div class="w1000">
  <div class="tzleft">
  <div id="note" style="width:710px;">
    <table width="710" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
      <tr align="center" class="tdduilist"  height="24">
        <td height="30">场</td>
        <td>赛事</td>
        <td>开赛&nbsp;|&nbsp;<a href="#"><span class="rc1">截止</span></a></td>
        <td width="120">主队</td>
        <td width="80">盘口 </td>
        <td width="120" >客队</td>
        <td width="84">&nbsp;</td>
      </tr>
    </table>
   </div>
    <table width="710" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#D7E2E6; border:1px solid #ccc; color:#666;">
     <#assign endCount=0 />
			<#if matchMap??>				
				<#assign SPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@SPF") />
				<#assign RQSPF=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@RQSPF") />
				<#list matchMap.entrySet() as entry>	
					<tr class="tdlist" align="center">
						<td class="black_000" height="35" colspan="13">
							<strong>${entry.getKey()}（11:00——次日11:00）<a class="blue" name="s_h_a" onclick="dayFilter(this);" _di="${entry_index}" href="javascript:;">隐藏</a></strong>
						</td>
					</tr>
					<#list entry.getValue() as match>
						<#if match_index%2==0>
							<#assign trClass='tdlist' />
						<#else>
							<#assign trClass='tdlist1' />
						</#if>
						<#if spfRateData??>
							<#assign spfRateMap=spfRateData.get(match.matchKey)!'' />
						<#else>
							<#assign spfRateMap='' />
						</#if>
						<#if rqspfRateData??>
							<#assign rqspfRateMap=rqspfRateData.get(match.matchKey)!'' />
						<#else>
							<#assign rqspfRateMap='' />
						</#if>
						<#assign saleEnd_SPF=match.isNotDisplay(SPF,passMode)/>
						<#assign saleEnd_RQSPF=match.isNotDisplay(RQSPF,passMode)/>
						<#assign open_SPF=match.isOpen(SPF,passMode)/>
						<#assign open_RQSPF=match.isOpen(RQSPF,passMode)/> 
	<#if match.handicap==1 || match.handicap==-1>
      <tr align="center" class="tdlist" id="tr_${match.matchKey}" _lid="${match.matchKey}" _m="1" _d="${entry_index}" <#if games?? && match.gameName?? && match.gameName?trim !=''>_g="${games.indexOf(match.gameName)}"</#if> _h="${match.handicap!0}" _e="<#if saleEnd_SPF && saleEnd_RQSPF>1<#else>0</#if>" >
      	<input type="hidden" id="key_${match.matchKey}" value="${match.getMatchKeyText()}"/>
        <td width="40" height="38" align="center"  id="td_id_${match.matchKey}">${match.lineId?string('000')}<span id="text_${match.matchKey}" style="display:none;">${match.matchKeyText}</span></td>
        <td width="111" align="center" style="background:<#if match.gameColor?? && match.gameColor?trim != ''>${match.gameColor}<#else>${defaultJcGameColor!}</#if>; color:#fff;">${match.gameName}</td>
        <td width="149" align="center" id="td_t_${match.matchKey}">${match.matchTime?string('HH:mm')}|<span style="color:red">${match.getWebOfficialEndTime()?string('HH:mm')}</span></td>
        <td width="120" class="tc char14" id="td_h_${match.matchKey}"><span <#if match.homeTeamGuangdongName?? && match.homeTeamGuangdongName !=''>_gd="${match.homeTeamGuangdongName}" _gy="${match.homeTeamName!}"</#if>>${match.homeTeamName!}</span></td>
        <td width="80" align="center" class="char14">vs</td>
        				 <#assign handicapClass=''/>
						    <#if match.handicap??>
						    	<#if match.handicap gt 0>
						    		<#assign handicapClass='rc1'/>
						    	<#elseif match.handicap lt 0>
						    		<#assign handicapClass='green'/>
						    	</#if>
						    </#if>
		<td width="28" style="display:none" id="td_hd_${match.matchKey}" class="${handicapClass}"><strong><span id="handicap_${match.matchKey}" >${match.handicap!}</span></strong></td>
        <td width="120" align="center" class="tc char14"  id="td_g_${match.matchKey}"><span <#if match.guestTeamGuangdongName?? && match.guestTeamGuangdongName !=''>_gd="${match.guestTeamGuangdongName}" _gy="${match.guestTeamName!}"</#if>>${match.guestTeamName!}</span></td>
        <td align="center" class="tdl1px tc"><a href="#" class="open_sp"></a> </td>
      </tr>
      <tr align="center" class="tdlist" >
      	 <td colspan="3" align="center"><span class=" char14 tc">竞彩让球&nbsp;<#if match.handicap gt 0><span class=" red"><#else><span class=" green"></#if><b>${match.handicap!}</b></span></span></td>
        <td colspan="7" align="right">
        				<table width="410" border="0" cellspacing="0" cellpadding="0">
        					<#--非让球胜平负-->		                	
							<#assign rs=match.getResult(SPF)!'' />
						    <#if rs!=''><#assign rs_sp=match.getResultSp(SPF)!'' /><#else><#assign rs_sp='' /></#if>
						    <#if open_SPF>
						    	
						   		 <#if spfRateMap?? && spfRateMap!=''>
									<#assign win_rateItem=spfRateMap('WIN')!'' />
									<#assign draw_rateItem=spfRateMap('DRAW')!'' />
									<#assign lose_rateItem=spfRateMap('LOSE')!'' />
								<#else>
									<#assign win_rateItem='' />
									<#assign draw_rateItem='' />
									<#assign lose_rateItem='' />
								</#if>
								
								
						   		 <#if rqspfRateMap?? && rqspfRateMap!=''>
									<#assign rq_win_rateItem=rqspfRateMap.get('WIN')!'' />
									<#assign rq_draw_rateItem=rqspfRateMap.get('DRAW')!'' />
									<#assign rq_lose_rateItem=rqspfRateMap.get('LOSE')!'' />
								<#else>
									<#assign rq_win_rateItem='' />
									<#assign rq_draw_rateItem='' />
									<#assign rq_lose_rateItem='' />
								</#if>
								
								<#assign win_sp_0=(draw_rateItem.value-(1/0.87))*win_rateItem.value*0.87/draw_rateItem.value>
								<#assign lose_sp_0=(draw_rateItem.value-(1/0.87))*lose_rateItem.value*0.87/draw_rateItem.value>
								
								<#assign win_sp_025=(draw_rateItem.value-(0.5/0.87))*win_rateItem.value*0.87/draw_rateItem.value>
								<#assign lose_sp_025=(0.87*draw_rateItem.value*lose_rateItem.value-0.5*lose_rateItem.value)/(draw_rateItem.value+0.5*lose_rateItem.value) />
								 
								<tr>
					              <td width="120" height="38" class="tdnor tc" id="r0_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'SPF', 0);">${(win_sp_0!0)?string('0.00')}</td>
					              <td width="80" class="tdyelnor tc">0</td>
					              <td width="120" class="tdnor tc" id="r0_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'SPF', 0);">${(lose_sp_0!0)?string('0.00')}</td>
					              <td width="83" class="  tc tdl1px" ><a href="#">亚</a></td>
					            </tr>
							
						    	 <tr>
					              <td height="38" class="tdnor tc tdl1px" id="r025_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'SPF', 25);">${(win_sp_025!0)?string('0.00')}</td>
					              <td class="tdyelnor tc"><#if match.handicap==-1>-</#if>0.25</td>
					              <td class="tdnor tc tdl1px" id="r025_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'SPF', 25);">${(lose_sp_025!0)?string('0.00')}</td>
					              <td width="83" class="  tc tdl1px"><a href="#">亚</a></td>
					            </tr>					            					          
					            
					            <#if match.handicap==1>
					            	<tr>
						              <td height="38" class="tdnor tc tdl1px" id="r05_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'RQSPF', 5);">${(rq_win_rateItem.value*0.87!0)?string('0.00')}</td>
						              <td class="tdyelnor tc">0.5</td>
						              <td class="tdnor tc tdl1px" id="r05_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'SPF', 5);">${(lose_rateItem.value*0.87!0)?string('0.00')}</td>
						              <td width="83" class="  tc tdl1px"><a href="#">亚</a></td>
						            </tr>
					            </#if>
						    	
						    	 <#if match.handicap==-1>
					            	<tr>
					            	   <td class="tdnor tc tdl1px" id="r05_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'SPF', 5);">${(win_rateItem.value*0.87!0)?string('0.00')}</td>
					            	   <td class="tdyelnor tc">-0.5</td>
					            	   <td height="38" class="tdnor tc tdl1px" id="r05_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'RQSPF', 5);">${(rq_lose_rateItem.value*0.87!0)?string('0.00')}</td>					            	   
						              <td width="83" class="  tc tdl1px"><a href="#">亚</a></td>
						            </tr>
					            </#if>
						    	
					            <#list itemArr as item>
					            	<td style="display:none"  width="50" class="tdyelnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_SPF>id="SPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');"</#if>>
					            	 <#assign spId='SPF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='SPF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if spfRateMap?? && spfRateMap!=''>
											<#assign rateItem=spfRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd_SPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'SPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>						               	 	
					            	   </#if>
					            	</td>
					            </#list>
					         
				            <#else>
				            	<td class="tdyelnor" colspan="${itemArr?size}">未开售</td>
				            </#if>
				            
				             <#--让球胜平负-->
						    <#assign rs=match.getResult(RQSPF)!'' />
						    <#if rs!=''><#assign rs_sp=match.getResultSp(RQSPF)!'' /><#else><#assign rs_sp='' /></#if>
						    <#if open_RQSPF>
						   		 
						    	
						   		 <#if rqspfRateMap?? && rqspfRateMap!=''>
									<#assign win_rateItem=rqspfRateMap.get('WIN')!'' />
									<#assign draw_rateItem=rqspfRateMap.get('DRAW')!'' />
									<#assign lose_rateItem=rqspfRateMap.get('LOSE')!'' />
								<#else>
									<#assign win_rateItem='' />
									<#assign draw_rateItem='' />
									<#assign lose_rateItem='' />
								</#if>
								<#assign win_sp_075=(0.87*draw_rateItem.value*win_rateItem.value-0.5*win_rateItem.value)/(draw_rateItem.value+0.5*win_rateItem.value) />
								<#assign lose_sp_075=(draw_rateItem.value-(0.5/0.87))*lose_rateItem.value*0.87/draw_rateItem.value>
								
								<#assign win_sp_1=(draw_rateItem.value-(1/0.87))*win_rateItem.value*0.87/draw_rateItem.value>
								<#assign lose_sp_1=(draw_rateItem.value-(1/0.87))*lose_rateItem.value*0.87/draw_rateItem.value>
								
								 <tr>
					              <td height="38" class="tdnor tc" id="r075_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'RQSPF', 75);">${(win_sp_075!0)?string('0.00')}</td>
					              <td class="tdyelnor tc"><#if match.handicap==-1>-</#if>0.75</td>
					              <td class="tdnor tc"  id="r075_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'RQSPF', 75);">${(lose_sp_075!0)?string('0.00')}</td>
					              <td width="83" class="   tc tdl1px"><a href="#">亚</a></td>
					            </tr>
					            <tr>
					              <td height="38" class="tdnor tc"  id="r1_${match.matchKey}_0" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',0,event,'RQSPF',1);">${(win_sp_1!0)?string('0.00')}</td>
					              <td class="tdyelnor tc"><#if match.handicap==-1>-</#if>1</td>
					              <td class="tdnor tc" id="r1_${match.matchKey}_2" onclick="clickItemOfYP($(this),${match_index},'${match.matchKey}',2,event,'RQSPF',1);">${(lose_sp_1!0)?string('0.00')}</td>
					              <td width="83" class="tc tdl1px"><a href="#">亚</a></td>
					            </tr>
								
			
						   
					            <#list itemArr as item>
					            	<td style="display:none"  width="50" class="tdnor" <#if match.cancel>title="取消"</#if> <#if !saleEnd_RQSPF>id="RQSPF_td_${match.matchKey}_${item.ordinal()}" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');"</#if>>
					            	 <#assign spId='RQSPF_sp_${match.matchKey}_${item.ordinal()}' />
					               	 	<#assign chkId='RQSPF_chk_${match.matchKey}_${item.ordinal()}' />
					               	 	<#if rqspfRateMap?? && rqspfRateMap!=''>
											<#assign rateItem=rqspfRateMap.get(item.name())!'' />
										<#else>
											<#assign rateItem='' />
										</#if>
										
										<#if match.ended && rs!='' && rs_sp?? && rs_sp?string!=''>
						        			<#if item=rs><span id="${spId}" _g="sp" class="redredchar">${rs_sp?string('0.00')}</span></#if>
						        		<#else>
						               	 	<#if !saleEnd_RQSPF><input id="${chkId}" style="display:none" onclick="clickItem(${match_index},'${match.matchKey}',${item.ordinal()},event,'RQSPF');" type="checkbox" autocomplete="off" /></#if><#rt/>
						               	 	<#lt/><span id="${spId}"><#if rateItem!=''>${(rateItem.value!0)?string('0.00')}<#else>0.00</#if></span>					               	 		
					            	   </#if>
					            	</td>
					            </#list>
					          
					        <#else>
				            	<td class="tdnor" colspan="${itemArr?size}">未开售</td>
				            </#if>
				           
           
          </table>
       </td>
      </tr>
    </#if>
     	</#list>						
	</#list>
  </#if>
    </table>
  </div>
  
</div>
<script type="text/javascript" src="${base}/js/lottery/jczq/navigator.js"></script> 
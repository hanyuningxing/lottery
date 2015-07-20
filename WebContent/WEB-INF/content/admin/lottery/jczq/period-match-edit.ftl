<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>
<style>
	.red{color:red}
</style>
<head>
	<title>${lotteryType.lotteryName}-编辑</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
	<script type='text/javascript'>
		function endOrCancel(index,mode,val){
			var arr = ['ended','cancel'];
			document.getElementById(arr[mode]+'_hidden_'+index).value = val;
			if(val == true){
				var mode2 = (mode==0)?1:0;
				var obj = document.getElementById(arr[mode2]+'_checkbox_'+index);
				if(obj.checked == true){
					obj.checked = false;
					document.getElementById(arr[mode2]+'_hidden_'+index).value = false;
				}
			}
		}
		
		function zcOper(index,val){
			var obj = document.getElementById('zc_hidden_'+index);
			if(val == true){
				obj.value = true;
			}else{
				obj.value = false;
			}
		}
		
		function handleOpenFlag(el,matchIndex,pt_ordinal,pm_ordinal){
			var flag = getOpenFlag(pt_ordinal, pm_ordinal);
			var matchForm = el.form;
			var openFlagEl = matchForm.elements["matchs[" + matchIndex + "].openFlag"];
			var val = parseInt(openFlagEl.value, 10);
			if(el.checked){
				val |= flag;
			}else{
				if((val & flag) > 0)
					val ^= flag;
			}
			openFlagEl.value = val;
			
		}
		function getOpenFlag(pt_ordinal,pm_ordinal){
			var len = 2;
			var p = pt_ordinal * len + pm_ordinal;
			return 0x1<<p;
		}
		function spMethod(id){
		
					  var sp_span=document.getElementById("sp_span_"+id);
					  var sp_span_inner_html = sp_span.innerHTML;
					  sp_span.innerHTML='更新中..';
					  var printInfoUrl="${base}/admin/lottery/${lotteryType.key}/period!odds.action?id="+id;
					  new Request({
							 url: printInfoUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      var spValue = jsonObj["msg"];
								      var spArr = spValue.split(",");
								      // map.put("msg", jczqMatch.getSpfResultSp()+","+jczqMatch.getJqsResultSp()+","+jczqMatch.getBfResultSp()+","+jczqMatch.getBqqResultSp());
								      var spf = spArr[0];//胜平负
								      var spf_sp_=document.getElementById("spf_sp_"+id);
								      spf_sp_.value = spf;
								      
								      var jqs = spArr[1];//进球数
								      var jqs_sp_=document.getElementById("jqs_sp_"+id);
								       jqs_sp_.value = jqs;
								       
								      var bf = spArr[2];//比分
								      var bf_sp_=document.getElementById("bf_sp_"+id);
								       bf_sp_.value = bf;
								       
								      var bqq = spArr[3];//半全场
								      var bqq_sp_=document.getElementById("bqq_sp_"+id);
								       bqq_sp_.value = bqq;
					                  sp_span.innerHTML = sp_span_inner_html;
								   }else{
								      window.alert(jsonObj["msg"]);
					                  sp_span.innerHTML = sp_span_inner_html;
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					  return false;
		}
		function allSpMethod(matchDate){
					  var sp_span=document.getElementById("all_sp_span");
					  var sp_span_inner_html = sp_span.innerHTML;
					  sp_span.innerHTML='更新中..';
					  var printInfoUrl="${base}/admin/lottery/${lotteryType.key}/period!odds_all.action?matchDate="+matchDate;
					  new Request({
							 url: printInfoUrl,
							 onSuccess: function(responseText, responseXML){
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      var spList = jsonObj["spList"];
								      for(var i = 0;i<spList.length;i++){
								         try{
								          var match = spList[i];
								          var matchDate = match.matchDate;
								          var lineId = match.lineId;
								          var spf_sp_=document.getElementById("spf_sp_"+matchDate+"_"+lineId);
								          spf_sp_.value = match.spfResultSp;
								           var spf_sp_flag_=document.getElementById("spf_sp_flag_"+matchDate+"_"+lineId);
								          spf_sp_flag_.innerHTML= match.spfFlag;
								          
								          var jqs_sp_=document.getElementById("jqs_sp_"+matchDate+"_"+lineId);
								          jqs_sp_.value = match.jqsResultSp;
								            var jqs_sp_flag_=document.getElementById("jqs_sp_flag_"+matchDate+"_"+lineId);
								          jqs_sp_flag_.innerHTML  = match.jqsFlag;
								          
								          var bf_sp_=document.getElementById("bf_sp_"+matchDate+"_"+lineId);
								          bf_sp_.value = match.bfResultSp;
								          
								          var bqq_sp_=document.getElementById("bqq_sp_"+matchDate+"_"+lineId);
								          bqq_sp_.value = match.bqqResultSp;
								            var bqq_sp_flag_=document.getElementById("bqq_sp_flag_"+matchDate+"_"+lineId);
								          bqq_sp_flag_.innerHTML  = match.bqqFlag;
								          
								          var rfspf_sp_=document.getElementById("rfspf_sp_"+matchDate+"_"+lineId);
								          rfspf_sp_.value = match.rfspfResultSp;
								            var rfspf_sp_flag_=document.getElementById("rfspf_sp_flag_"+matchDate+"_"+lineId);
								          rfspf_sp_flag_.innerHTML  = match.rfspfFlag;
								          
								      
								          var bidui_flag=document.getElementById("bidui_flag_"+matchDate+"_"+lineId);
								          bidui_flag.innerHTML  = match.biduiFlag;
 
								          var halfHomeScore=document.getElementById("halfHomeScore_"+matchDate+"_"+lineId);
								          halfHomeScore.value = match.halfHomeScore;
								          
								           var halfGuestScore=document.getElementById("halfGuestScore_"+matchDate+"_"+lineId);
								          halfGuestScore.value = match.halfGuestScore;
								          
								           var fullHomeScore=document.getElementById("fullHomeScore_"+matchDate+"_"+lineId);
								          fullHomeScore.value = match.fullHomeScore;
								          
								           var fullGuestScore=document.getElementById("fullGuestScore_"+matchDate+"_"+lineId);
								          fullGuestScore.value = match.fullGuestScore;
								          
								          }catch(e){
								             continue;
								          }
								      }
					                  sp_span.innerHTML = sp_span_inner_html;
								   }else{
								      window.alert(jsonObj["msg"]);
					                  sp_span.innerHTML = sp_span_inner_html;
								   }
							 }
						}).get({'__t=':new Date().getTime()});
					  return false;
		}
		function importMatch(notice,action){
		   if(window.confirm(notice)){
				var matchForm = document.getElementById('matchForm');
				matchForm.action = action;
			    matchForm.submit();
		   }
		}
	</script>
	
	
</head>

<@com.displaySimpleNav menuObj menuItemObj true><a href="<@c.url value='/admin/lottery/${lotteryType.key}/period!matchManage.action?id=${(period.id)!}' />">对阵管理</a> &gt;&gt; 编辑对阵</@>

<#assign playTypeArr=stack.findValue("@com.cai310.lottery.support.jczq.PlayType@values()") />
<#assign passModeArr=stack.findValue("@com.cai310.lottery.support.jczq.PassMode@values()") />

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${matchDate}</span>&nbsp;&nbsp;&nbsp;<span id= "all_sp_span"><a href="#" onclick="allSpMethod(${matchDate });return false;">同步SP</a></span></div>
</div>
<div>
	<form id="matchForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/period.action' />" method="post">
		<input type="hidden" name="id" value="${(period.id)!}" />
		<input type="hidden" name="matchDate" value="${matchDate}" />
		<table id="matchTab" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
		  <tr class="trlbrown" align="center" id="match0">
		    <td height="22">场次</td>
		    <td>联赛名</td>
		    <td>颜色</td>
		    <td>比赛时间</td>
		    <td>主队名称</td>
		    <td>主队粤语</td>
		    <td>客队名称</td>
		    <td>客队粤语</td>
		    <td>足球对阵</td>
		    <td>让球</td>
		    <td>结束</td>
		    <td>取消</td>
		    <td>半场比分</td>
		    <td>全场比分</td>
		    <td>胜平负SP</td>
		    <td>进球数SP</td>
		    <td>比分SP</td>
		    <td>半全场SP</td>
		    <td>让分SP</td>
	  		<#list playTypeArr as pt>
	  			<#if pt=='MIX'>
		    	 <#else>
		    		<td width="50">${pt.text}</td>
		    	</#if>
	  		</#list>
		  </tr>
		  <#if matchs??>
	  		<#list matchs as match>
	  		  <#if match_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
	  		  <tr _group="match" id="match_${match_index}" class="${trColor}" align="center">
			    <td>
			    	${match.matchKeyText}
			   	 	<input type="hidden" name="matchs[${match_index}].matchKey" value="${match.matchKey}" />
			   	 	<input type="hidden" name="matchs[${match_index}].matchDate" value="${match.matchDate}" />
		    		<input type="hidden" name="matchs[${match_index}].lineId" value="${match.lineId}"/>
		    		<input type="hidden" name="matchs[${match_index}].openFlag" value="${match.openFlag}" />
			    </td>
			    <td><input name="matchs[${match_index}].gameName" value="${match.gameName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].gameColor" value="${match.gameColor!}" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].matchTime" value="<#if match.matchTime??>${match.matchTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"  style="width:100px;"/></td>
			    <td><input name="matchs[${match_index}].homeTeamName" value="${match.homeTeamName!}" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].homeTeamGuangdongName" value="${match.homeTeamGuangdongName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamName" value="${match.guestTeamName!}" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamGuangdongName" value="${match.guestTeamGuangdongName!}" style="width:40px;" /></td>
			    <td>
			    	<input id="zc_checkbox_${match_index}" type="checkbox" <#if match.zcMatch>checked="checked"</#if> onclick="zcOper(${match_index},this.checked);">
	    			<input id="zc_hidden_${match_index}" type="hidden" name="matchs[${match_index}].zcMatch" value="<#if match.zcMatch>true<#else>false</#if>">
	    		</td>
			    <td><input name="matchs[${match_index}].handicap" value="${match.handicap!}" style="width:30px;" /></td>
			    <td>
			    	<input id="ended_checkbox_${match_index}" type="checkbox" <#if match.ended>checked="checked"</#if> onclick="endOrCancel(${match_index},0,this.checked);">
	    			<input id="ended_hidden_${match_index}" type="hidden" name="matchs[${match_index}].ended" value="<#if match.ended>true<#else>false</#if>">
			    </td>
			    <td>
			    	<input id="cancel_checkbox_${match_index}" type="checkbox" <#if match.cancel>checked="checked"</#if> onclick="endOrCancel(${match_index},1,this.checked);">
	    			<input id="cancel_hidden_${match_index}" type="hidden" name="matchs[${match_index}].cancel" value="<#if match.cancel>true<#else>false</#if>">
			    </td>
			    <td><input name="matchs[${match_index}].halfHomeScore" value="${match.halfHomeScore!}" style="width:20px;" id= "halfHomeScore_${match.matchDate}_${match.lineId}">:<input type="text" name="matchs[${match_index}].halfGuestScore" value="${match.halfGuestScore!}" style="width:20px;" id= "halfGuestScore_${match.matchDate}_${match.lineId}"></td>
			    <td><input name="matchs[${match_index}].fullHomeScore" value="${match.fullHomeScore!}" style="width:20px;" id= "fullHomeScore_${match.matchDate}_${match.lineId}">:<input type="text" name="matchs[${match_index}].fullGuestScore" value="${match.fullGuestScore!}" style="width:20px;" id= "fullGuestScore_${match.matchDate}_${match.lineId}"></td> 
			    <td><input name="matchs[${match_index}].spfResultSp" value="#{(match.spfResultSp!0);M2}" style="width:60px;" id= "spf_sp_${match.matchDate}_${match.lineId}"/><span id="spf_sp_flag_${match.matchDate}_${match.lineId}" class="red"></span><span id="bidui_flag_${match.matchDate}_${match.lineId}" class="red"></span></td>
			    <td><input name="matchs[${match_index}].jqsResultSp" value="#{(match.jqsResultSp!0);M2}" style="width:60px;" id= "jqs_sp_${match.matchDate}_${match.lineId}"/><span id="jqs_sp_flag_${match.matchDate}_${match.lineId}" class="red"></span></td>
			    <td><input name="matchs[${match_index}].bfResultSp" value="#{(match.bfResultSp!0);M2}" style="width:60px;" id= "bf_sp_${match.matchDate}_${match.lineId}"/> </td> 
			    <td><input name="matchs[${match_index}].bqqResultSp" value="#{(match.bqqResultSp!0);M2}" style="width:60px;" id= "bqq_sp_${match.matchDate}_${match.lineId}"/><span id="bqq_sp_flag_${match.matchDate}_${match.lineId}" class="red"></span></td> 
			    <td><input name="matchs[${match_index}].rfspfResultSp" value="#{(match.rfspfResultSp!0);M2}" style="width:60px;" id= "rfspf_sp_${match.matchDate}_${match.lineId}"/><span id="rfspf_sp_flag_${match.matchDate}_${match.lineId}" class="red"></span></td>
			    <#list playTypeArr as pt>
			     <#if pt=='MIX'><#else>
			    	<td>
			    		<#list passModeArr as pm>
			    		    <#if pm=='MIX_PASS'||pt=='MIX'>
			    		      <#else>
			    				<input <#if match.isOpen(pt,pm)>checked="checked"</#if> id="openFlag_${match_index}_${pt}_${pm}" type="checkbox" onclick="handleOpenFlag(this,${match_index},${pt.ordinal()},${pm.ordinal()});" /><label for="openFlag_${match_index}_${pt}_${pm}">${pm.text}</label><br/>
			    		    </#if>
			    		</#list>
			    	</td>
			      </#if>
	  			</#list>
			  </tr>
	  		</#list>
	  	  </#if>
	  	  <tr id="last_tr" class="trlblue">
		    <td height="30" colspan="${(playTypeArr?size)+18}" style="text-align:left;padding-left:20px;">
		      <input type="button" value="保存"  onclick="importMatch('您确认要保存？','<@c.url value='/admin/lottery/${lotteryType.key}/period!saveMatch.action' />')" />
		      <input style="margin-left:30px" type="button" value="抓取" onclick="importMatch('您确认要捉取？','<@c.url value='/admin/lottery/${lotteryType.key}/period!importMatch.action' />')" />
		    </td>
		  </tr>
		</table>
	</form>
</div>
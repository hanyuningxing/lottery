<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>
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
								      var sf = spArr[0];//胜负
								      var sf_sp_=document.getElementById("sf_sp_"+id);
								      sf_sp_.value = sf;
								      var rfsf = spArr[1];//进球数
								      var rfsf_sp_=document.getElementById("rfsf_sp_"+id);
								       rfsf_sp_.value = rfsf;
								      var sfc = spArr[2];//比分
								      var sfc_sp_=document.getElementById("sfc_sp_"+id);
								       sfc_sp_.value = sfc;
								      var dxf = spArr[3];//半全场
								      var dxf_sp_=document.getElementById("dxf_sp_"+id);
								      dxf_sp_.value = dxf;
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
								          var sf_sp_=document.getElementById("sf_sp_"+matchDate+"_"+lineId);
								          sf_sp_.value = match.sfResultSp;
								          var rfsf_sp_=document.getElementById("rfsf_sp_"+matchDate+"_"+lineId);
								          rfsf_sp_.value = match.rfsfResultSp;
								          var sfc_sp_=document.getElementById("sfc_sp_"+matchDate+"_"+lineId);
								          sfc_sp_.value = match.sfcResultSp;
								          var dxf_sp_=document.getElementById("dxf_sp_"+matchDate+"_"+lineId);
								          dxf_sp_.value = match.dxfResultSp;
								          
								          
								          var homeScore=document.getElementById("homeScore_"+matchDate+"_"+lineId);
								          homeScore.value = match.homeScore;
								          
								           var guestScore=document.getElementById("guestScore_"+matchDate+"_"+lineId);
								          guestScore.value = match.guestScore;
								          
								          
								           var singleHandicap=document.getElementById("singleHandicap_"+matchDate+"_"+lineId);
								           if(null!=match.singleHandicap&&''!=match.singleHandicap){
								         		 singleHandicap.value = match.singleHandicap;
								           }
								           var singleTotalScore=document.getElementById("singleTotalScore_"+matchDate+"_"+lineId);
								           if(null!=match.singleTotalScore&&''!=match.singleTotalScore){
								         		 singleTotalScore.value = match.singleTotalScore;
								           }
								          
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

<#assign playTypeArr=stack.findValue("@com.cai310.lottery.support.jclq.PlayType@values()") />
<#assign passModeArr=stack.findValue("@com.cai310.lottery.support.jclq.PassMode@values()") />

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${matchDate}</span>&nbsp;&nbsp;&nbsp;<span id= "all_sp_span"><a href="#" onclick="allSpMethod(${matchDate });return false;">同步SP</a></span></div>
</div>
<div>
	<form id="matchForm" action="#" method="post">
		<input type="hidden" name="id" value="${(period.id)!}" />
		<input type="hidden" name="matchDate" value="${matchDate}" />
		<table id="matchTab" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
		  <tr class="trlbrown" align="center" id="match0">
		    <td height="22">场次</td>
		    <td>联赛名</td>
		    <td>颜色</td>
		    <td>比赛时间</td>
		    <td>主队</td>
		    <td>主队粤语</td>
		    <td>客队</td>
		    <td>客队粤语</td>
		    <td>主队让分</td>
		    <td>预设总分</td>
		    <td>结束</td>
		    <td>取消</td>
		    <td>比分</td>
		    <td>胜负SP</td>
		    <td>让分胜负SP</td>
		    <td>胜分差SP</td>
		    <td>大小分SP</td>
		    <td>操作</td>
	  		<#list playTypeArr as pt>
		    	<td>${pt.text}</td>
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
			    <td><input name="matchs[${match_index}].gameColor" value="<#if match.gameColor??>${match.gameColor!}<#else>#0066FF</#if>" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].matchTime" value="<#if match.matchTime??>${match.matchTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"  style="width:80px;"/></td>
			    <td><input name="matchs[${match_index}].homeTeamName" value="${match.homeTeamName!}" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].homeTeamGuangdongName" value="${match.homeTeamGuangdongName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamName" value="${match.guestTeamName!}" style="width:50px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamGuangdongName" value="${match.guestTeamGuangdongName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].singleHandicap" value="<#if match.singleHandicap??>${match.singleHandicap?string('0.0')}</#if>" style="width:40px;" id= "singleHandicap_${match.matchDate}_${match.lineId}"/></td>
			    <td><input name="matchs[${match_index}].singleTotalScore" value="<#if match.singleTotalScore??>${match.singleTotalScore?string('0.0')}</#if>" style="width:40px;" id= "singleTotalScore_${match.matchDate}_${match.lineId}"/></td>
			    <td>
			    	<input id="ended_checkbox_${match_index}" type="checkbox" <#if match.ended>checked="checked"</#if> onclick="endOrCancel(${match_index},0,this.checked);">
	    			<input id="ended_hidden_${match_index}" type="hidden" name="matchs[${match_index}].ended" value="<#if match.ended>true<#else>false</#if>">
			    </td>
			    <td>
			    	<input id="cancel_checkbox_${match_index}" type="checkbox" <#if match.cancel>checked="checked"</#if> onclick="endOrCancel(${match_index},1,this.checked);">
	    			<input id="cancel_hidden_${match_index}" type="hidden" name="matchs[${match_index}].cancel" value="<#if match.cancel>true<#else>false</#if>">
			    </td>
			    <td><input name="matchs[${match_index}].homeScore" value="${match.homeScore!}" style="width:20px;" id= "homeScore_${match.matchDate}_${match.lineId}">:<input type="text" name="matchs[${match_index}].guestScore" value="${match.guestScore!}" style="width:20px;" id= "guestScore_${match.matchDate}_${match.lineId}"></td> 

			    <td><input name="matchs[${match_index}].sfResultSp" value="#{(match.sfResultSp!0);M2}" style="width:60px;" id= "sf_sp_${match.matchDate}_${match.lineId}"/></td>
			    <td><input name="matchs[${match_index}].rfsfResultSp" value="#{(match.rfsfResultSp!0);M2}" style="width:60px;" id= "rfsf_sp_${match.matchDate}_${match.lineId}"/></td> 
			    <td><input name="matchs[${match_index}].sfcResultSp" value="#{(match.sfcResultSp!0);M2}" style="width:60px;" id= "sfc_sp_${match.matchDate}_${match.lineId}"/></td>
			    <td><input name="matchs[${match_index}].dxfResultSp" value="#{(match.dxfResultSp!0);M2}" style="width:60px;" id= "dxf_sp_${match.matchDate}_${match.lineId}"/></td> 
			     <td><#if match.id??><span id= "sp_span_${match.id }"><a href="#" onclick="spMethod(${match.id });return false;">同步SP</a></span></#if></td> 
			    

			    <#list playTypeArr as pt>
		    	<td>
		    		<#list passModeArr as pm>
		    		    <#if pm=='MIX_PASS'||pt=='MIX'>
		    		      <#else>
		    				<input <#if match.isOpen(pt,pm)>checked="checked"</#if> id="openFlag_${match_index}_${pt}_${pm}" type="checkbox" onclick="handleOpenFlag(this,${match_index},${pt.ordinal()},${pm.ordinal()});" /><label for="openFlag_${match_index}_${pt}_${pm}">${pm.text}</label><br/>
		    		    </#if>
		    		</#list>
		    	</td>
	  			</#list>
			  </tr>
	  		</#list>
	  	  </#if>
	  	  <tr id="last_tr" class="trlblue">
		    <td height="30" colspan="${(playTypeArr?size)+13}" style="text-align:left;padding-left:20px;">
		      <input type="button" value="保存"  onclick="importMatch('您确认要保存？','<@c.url value='/admin/lottery/${lotteryType.key}/period!saveMatch.action' />')" />
		      <input style="margin-left:30px" type="button" value="抓取" onclick="importMatch('您确认要捉取？','<@c.url value='/admin/lottery/${lotteryType.key}/period!importMatch.action' />')" />
		    </td>
		  </tr>
		</table>
	</form>
</div>
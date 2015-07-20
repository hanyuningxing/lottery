<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>${lotteryType.lotteryName}-编辑期数据</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
		<script type="text/javascript" src="${base}/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${base}/js/admin/zcFetchResult.js"></script>
	<script type='text/javascript'>
		function cancel(index,val){
			document.getElementById('cancel_hidden_'+index).value = val;
		}
		
		function addRow(obj){
			var trClass = (obj.index%2==0)?'trw':'trgray';
			var trEl = new Element('tr', {
				'id':'match_'+obj.index,
				'align':'center',
				'_group':'match',
				'class' :trClass
	        });
	        trEl.grab(new Element('td', {
				'html' :(obj.index+1)+''
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].gameName" value="'+((obj.gameName!=null)?obj.gameName:'')+'" style="width:60px;" />'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].gameColor" value="'+((obj.gameColor!=null)?obj.gameColor:'')+'" style="width:60px;" />'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].matchTime" value="'+((obj.matchTime!=null)?obj.matchTime:'')+'"  style="width:120px;" onclick="WdatePicker({el:$(\'matchs['+obj.index+'].matchTime\'),startDate:\'%y-%M-%d %H:%m:00\',dateFmt:\'yyyy-MM-dd HH:mm:ss\'})"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].homeTeamName" value="'+((obj.homeTeamName!=null)?obj.homeTeamName:'')+'"  style="width:80px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].guestTeamName" value="'+((obj.guestTeamName!=null)?obj.guestTeamName:'')+'"  style="width:80px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].asiaOdd" value="'+((obj.asiaOdd!=null)?obj.asiaOdd:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].odds1" value="'+((obj.odds1!=null)?obj.odds1:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].odds2" value="'+((obj.odds2!=null)?obj.odds2:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].odds3" value="'+((obj.odds3!=null)?obj.odds3:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].homeScore" value="" style="width:40px;">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].guestScore" value="" style="width:40px;">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input id="cancel_checkbox_'+obj.index+'" type="checkbox" onclick="cancel(obj.index,this.checked);">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input id="cancel_hidden_'+obj.index+'" type="hidden" name="matchs['+obj.index+'].cancel" value="false">'
			}));
			trEl.inject(document.getElementById('last_tr'), 'before');
		}
		
		function setMatchSize(){
			var size = 14;
			var matchTabObj = $('#matchTab');
			var trArr = matchTabObj.find('tr[_group="match"]');
			var len = trArr.length;
			if(len == size)
				return;
			else if(len > size){
				for(var i=size;i<len;i++){
					trArr[i].dispose();
				}
			}else{
				for(var i=len;i<size;i++){
					addRow({index:i});
				}
			}
			document.getElementById('match_count').value = size;
		}
		function fetchMatch(periodId){
			var size = 14;
			var matchTabObj = $('#matchTab');
			var trArr = matchTabObj.find('tr[_group="match"]');
			var len = trArr.length;
			if(len == size){
				
			}else if(len > size){
				for(var i=size;i<len;i++){
					trArr[i].dispose();
				}
			}else{
				for(var i=len;i<size;i++){
					addRow({index:i});
				}
			}
			document.getElementById('match_count').value = size;
			
			
			
		    var match_button_span = document.getElementById("match_button_span");
		    var buttonHtml = match_button_span.innerHTML
		    var loaddingHtml = "<img src='${base}/images/data-loading.gif'/>更新中";
		    match_button_span.innerHTML = loaddingHtml;
		    
		    var url = "${base}/admin/lottery/${lotteryType.key}/period!fetchMatch.action?id="+periodId;
		    var jsonRequest = new Request.JSON({
		    	url: url, 
			    onComplete: function(resultObj, text){
			        match_button_span.innerHTML = buttonHtml;
			        if(resultObj != null){
			            var matchForm = document.getElementById('matchForm');
			            var input ;
			        	if(resultObj.success == true){
			        		for(var i = 0;i<resultObj.list.length; i++){
			        		    zcMatch = resultObj.list[i];
								input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].gameName'];
			        		    input.value = zcMatch.gameName;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].matchTime'];
			        		    input.value = zcMatch.matchTimeStr;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].homeTeamName'];
			        		    input.value = zcMatch.homeTeamName;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].guestTeamName'];
			        		    input.value = zcMatch.guestTeamName;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds1'];
			        		    input.value = zcMatch.odds1;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds2'];
			        		    input.value = zcMatch.odds2;
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds3'];
			        		    input.value = zcMatch.odds3;
			        		    
			        		     input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].asiaOdd'];
			        		    input.value = zcMatch.asiaOdd;
			        		         
			        	    }
			        	}else{
			        		alert(resultObj.msg);
			        	}
			        }
			    }
			}).get({ 
			    '__t': new Date().getTime()
			});
		}
	</script>
	
	
</head>

<@com.displaySimpleNav menuObj menuItemObj true>编辑期数据</@>

<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${period.periodNumber}期</span></div>
</div>

<div>
	<form id="periodForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/period!update.action' />" method="post">
		<input type="hidden" name="id" value="${period.id!}" />
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
		  <#include "../baseEditData.ftl" />
		  <tr class="trlblue">
		    <td align="left" colspan="2"><input type="submit" value="更新数据" /></td>
		  </tr>
		</table>
	</form>
</div>

<div style="padding-top:5px;">
	<form action="<@c.url value='/admin/lottery/${lotteryType.key}/period!updateMatch.action' />" method="post" id="matchForm">
		<input type="hidden" name="id" value="${period.id!}" />
		<table id="matchTab" width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC">
		  <tr class="twonavlblue">
		    <td colspan="3" style="text-align:left;padding-left:10px;"><input type="button" value="添加赛事对阵" onclick="setMatchSize();" /></td>
		    <td colspan="3" style="text-align:left;padding-left:10px;"><span id="match_button_span"><input type="button" value="捉取赛事对阵" onclick="fetchMatch(${period.id!});" /></span></td>
		    <td colspan="12"><input type="button" value="抓取赛果" id="sfzcFetch"/></td>
		  </tr>
		  <tr class="trlbrown" align="center" id="match0">
		    <td height="22">场次</td>
		    <td>联赛名</td>
		    <td>颜色</td>
		    <td>比赛时间</td>
		    <td>主队名称</td>
		    <td>客队名称</td>
		    <td>亚赔</td>
		    <td>欧赔1</td>
		    <td>欧赔2</td>
		    <td>欧赔3</td>
		    <td>主队进球</td>
		    <td>客队进球</td>
		    <td>取消</td>
		  </tr>
		  <#if matchs??>
	  		<#list matchs as match>
	  		  <#if match_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
	  		  <tr _group="match" id="match_${match_index}" class="${trColor}" align="center">
			    <td>${match.lineId+1}</td>
			    <td><input name="matchs[${match_index}].gameName" value="${match.gameName!}" style="width:60px;" /></td>
			    <td><input name="matchs[${match_index}].gameColor" value="${match.gameColor!}" style="width:60px;" /></td>
			    <td><input name="matchs[${match_index}].matchTime" value="<#if match.matchTime??>${match.matchTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"  style="width:120px;"/></td>
			    <td><input name="matchs[${match_index}].homeTeamName" value="${match.homeTeamName!}" style="width:80px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamName" value="${match.guestTeamName!}" style="width:80px;" /></td>
			    <td><input name="matchs[${match_index}].asiaOdd" value="<#if match.asiaOdd??>${match.asiaOdd.toString()}</#if>" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].odds1" value="<#if match.odds1??>${match.odds1.toString()}</#if>" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].odds2" value="<#if match.odds2??>${match.odds2.toString()}</#if>" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].odds3" value="<#if match.odds3??>${match.odds3.toString()}</#if>" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].homeScore" id="homeScore_${match_index}" value="${match.homeScore!}" style="width:40px;"> </td>
			    <td><input name="matchs[${match_index}].guestScore" id="guestScore_${match_index}"  value="${match.guestScore!}" style="width:40px;"></td> 
			    <td>
			    	<input id="cancel_checkbox_${match_index}" type="checkbox" <#if match.cancel>checked="checked"</#if> onclick="cancel(${match_index},this.checked);">
	    			<input id="cancel_hidden_${match_index}" type="hidden" name="matchs[${match_index}].cancel" value="<#if match.cancel>true<#else>false</#if>">
			    </td>
			  </tr>
	  		</#list>
	  	  </#if>
	  	  <tr id="last_tr" class="trlblue">
		    <td height="30" colspan="16" style="text-align:left;padding-left:20px;">
		      <input id="match_count" type="hidden" name="matchCount" value="${(matchs![])?size}" />
		      <input type="submit" value="提 交" />&nbsp;&nbsp;&nbsp;<input type="submit" name="isPasscount" value="执行过关统计" />
		    </td>
		  </tr>
		</table>
	</form>
</div>
<div>
	<form action="<@c.url value='/admin/lottery/${lotteryType.key}/period!updatePeriodData.action' />" method="post">
		<input type="hidden" name="id" value="${period.id!}" />		
		<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#DCDCDC">
		  <tr>
		    <td align="left" colspan="4"><label>胜负彩开奖数据</label></td>
		  </tr>
		  <tr class="trw">
            <td class="trhemaigray" align="right" width="160">当前赛果：</td>
            <td align="left" colspan="3">
    	       <input type="text" name="periodData.result" id="periodData.result" value="${periodData.result!}" />
            </td>
          </tr>
		  <tr class="trw">
            <td class="trhemaigray" align="right" width="160">一等奖奖金：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.firstPrize_14" id="periodData.firstPrize_14" value="${periodData.firstPrize_14!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">一等奖注数：</td>
            <td align="left">
    	       <input type="text" name="periodData.firstWinUnits_14" id="periodData.firstWinUnits_14" value="${periodData.firstWinUnits_14!}" />
            </td>
          </tr>
          <tr class="trw">
            <td class="trhemaigray" align="right" width="160">二等奖奖金：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.secondPrize_14" id="periodData.secondPrize_14" value="${periodData.secondPrize_14!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">二等奖注数：</td>
            <td align="left">
    	       <input type="text" name="periodData.secondWinUnits_14" id="periodData.secondWinUnits_14" value="${periodData.secondWinUnits_14!}" />
            </td>
          </tr>
          <tr class="trw">
            <td class="trhemaigray" align="right" width="160">总销售额：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.totalSales_14" id="periodData.totalSales_14" value="${periodData.totalSales_14!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">奖池金额：</td>
            <td align="left">
    	       <input type="text" name="periodData.prizePool_14" id="periodData.prizePool_14" value="${periodData.prizePool_14!}" />
            </td>
          </tr>
          <tr>
		    <td align="left" colspan="4"><label>任九开奖数据</label></td>
		  </tr>
		  <tr class="trw">
            <td class="trhemaigray" align="right" width="160">一等奖奖金：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.firstPrize" id="periodData.firstPrize" value="${periodData.firstPrize!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">一等奖注数：</td>
            <td align="left">
    	       <input type="text" name="periodData.firstWinUnits" id="periodData.firstWinUnits" value="${periodData.firstWinUnits!}" />
            </td>
          </tr>
          <tr class="trw">
            <td class="trhemaigray" align="right" width="160">二等奖奖金：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.secondPrize" value="${periodData.secondPrize!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">二等奖注数：</td>
            <td align="left">
    	       <input type="text" name="periodData.secondWinUnits" value="${periodData.secondWinUnits!}" />
            </td>
          </tr>
          <tr class="trw">
            <td class="trhemaigray" align="right" width="160">总销售额：</td>
            <td align="left" width="200">
    	       <input type="text" name="periodData.totalSales" id="periodData.totalSales" value="${periodData.totalSales!}" />
            </td>
            <td class="trhemaigray" align="right" width="160">奖池金额：</td>
            <td align="left">
    	       <input type="text" name="periodData.prizePool" value="${periodData.prizePool!}" />
            </td>
          </tr>
		  <tr class="trlblue">
		    <td align="left" colspan="4"><input type="submit" value="更新数据" /></td>
		  </tr>
		</table>
	</form>
</div>
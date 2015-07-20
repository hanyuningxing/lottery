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
	<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
	<style>
		.error{background-color:#66ccff;}
	</style>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
	<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>	
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
	<script type='text/javascript'>
		jQuery.noConflict(); 
		jQuery(document).ready(function() {
	         jQuery('#matchForm input').keyup(trimkeyup);
		 });  
		 function trimkeyup(e) {
		     lucene_objInput = jQuery(this);
		     if (e.keyCode != 38 && e.keyCode != 40 && e.keyCode != 13) {
		         var im = jQuery.trim(lucene_objInput.val());
		         lucene_objInput.val(im); 
		     }
		}        
		
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
				'html' :'<input name="matchs['+obj.index+'].gameName" value="'+((obj.gameName!=null)?obj.gameName:'')+'" style="width:40px;" />'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].gameColor" value="'+((obj.gameColor!=null)?obj.gameColor:'')+'" style="width:50px;" />'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].matchTime" value="'+((obj.matchTime!=null)?obj.matchTime:'')+'"  style="width:100px;" onclick="WdatePicker({el:$(\'matchs['+obj.index+'].matchTime\'),startDate:\'%y-%M-%d %H:%m:00\',dateFmt:\'yyyy-MM-dd HH:mm:ss\'})"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].homeTeamName" value="'+((obj.homeTeamName!=null)?obj.homeTeamName:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].homeTeamGuangdongName" value="'+((obj.homeTeamGuangdongName!=null)?obj.homeTeamGuangdongName:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].guestTeamName" value="'+((obj.guestTeamName!=null)?obj.guestTeamName:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].guestTeamGuangdongName" value="'+((obj.guestTeamGuangdongName!=null)?obj.guestTeamGuangdongName:'')+'"  style="width:40px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].handicap" value="'+((obj.handicap!=null)?obj.handicap:'')+'"  style="width:30px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input id="ended_checkbox_'+obj.index+'" type="checkbox" onclick="endOrCancel('+obj.index+',0,this.checked);"><input id="ended_hidden_'+obj.index+'" type="hidden" name="matchs['+obj.index+'].ended" value="false">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].halfHomeScore" value="" style="width:20px;">:<input type="text" name="matchs['+obj.index+'].halfGuestScore" value="" style="width:20px;">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].fullHomeScore" value="" style="width:20px;">:<input type="text" name="matchs['+obj.index+'].fullGuestScore" value="" style="width:20px;">'
			}));
			trEl.grab(new Element('td', {
				'html' :'	<input id="cancel_checkbox_'+obj.index+'" type="checkbox" onclick="endOrCancel('+obj.index+',1,this.checked);"><input id="cancel_hidden_'+obj.index+'" type="hidden" name="matchs['+obj.index+'].cancel" value="false">'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].spfResultSp" value=""  style="width:55px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].zjqsResultSp" value=""  style="width:55px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].sxdsResultSp" value=""  style="width:55px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].bfResultSp" value=""  style="width:55px;"/>'
			}));
			trEl.grab(new Element('td', {
				'html' :'<input name="matchs['+obj.index+'].bqqspfResultSp" value=""  style="width:55px;"/>'
			}));
			trEl.inject($('last_tr'), 'before');
		}
		function isEmpty(value){
		  return /^\s*$/.test(value);
		}
		function fetchMatch(periodId){
			
			
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
			        	    document.getElementById('match_size').value=resultObj.size;
			        	    setMatchSize();
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
			        		    
			        		    input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].handicap'];
			        		    input.value = zcMatch.handicap;
			        		    
			        		    //input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds1'];
			        		    //input.value = zcMatch.odds1;
			        		    
			        		    //input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds2'];
			        		   // input.value = zcMatch.odds2;
			        		    
			        		    //input =  matchForm.elements['matchs['+(zcMatch.lineId-1)+'].odds3'];
			        		    //input.value = zcMatch.odds3;
			        		         
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
		function getmatchSp(periodId){
		    var buttonHtml = "<input  type=\"button\" name=\"match_sp_button\" onclick=\"getmatchSp(${period.id!});return false;\" value=\"更新sp\"/>";
		    var match_sp_button_td = document.getElementById("match_sp_button_td");
		    var loaddingHtml = "<img src='${base}/images/data-loading.gif'/>更新中";
		    match_sp_button_td.innerHTML = loaddingHtml;
		    
		    var url = "${base}/admin/lottery/${lotteryType.key}/period!dczcOdds.action?period.id="+periodId;
		    var jsonRequest = new Request.JSON({
		    	url: url, 
			    onComplete: function(resultObj, text){
			        match_sp_button_td.innerHTML = buttonHtml;
			        if(resultObj != null){
			        	if(resultObj.success == true){
			        		if(null!=resultObj.dczcMatch_sp){
			        		     var  dczcMatch;
			        		     var matchId=null;
			        		     var spValue=null;
			        		     var bidui_result = resultObj.dczcMatch_sp_bidui_result;
			        		     document.getElementById('bidui_result').innerHTML=bidui_result;
			        		     for(var i = 0;i<resultObj.dczcMatch_sp.length; i++){
			        		         dczcMatch = resultObj.dczcMatch_sp[i];
			        		         matchId=dczcMatch.lineId;
			        		         
			        					if(dczcMatch.spfResultSp<0){
			        		    			 document.getElementById(matchId+'_spfResultSp_flag').innerHTML='error';
			        		    			 document.getElementById('match_'+matchId).className='error';
			        		    			 document.getElementById(matchId+'_spfResultSp').value=-dczcMatch.spfResultSp;  
			        		    		}else{
			        		    			document.getElementById(matchId+'_spfResultSp').value=dczcMatch.spfResultSp;  
			        		    		}
			        		     
			        		            if(dczcMatch.zjqsResultSp<0){
			        		    			 document.getElementById(matchId+'_zjqsResultSp_flag').innerHTML='error';
			        		    			 document.getElementById('match_'+matchId).className='error';
			        		    			  document.getElementById(matchId+'_zjqsResultSp').value=-dczcMatch.zjqsResultSp;	 
			        		    		}else{
			        		            	 document.getElementById(matchId+'_zjqsResultSp').value=dczcMatch.zjqsResultSp;	    
			        		             }         
			        		       
			        		             if(dczcMatch.sxdsResultSp<0){
			        		    			 document.getElementById(matchId+'_sxdsResultSp_flag').innerHTML='error';
			        		    			 document.getElementById('match_'+matchId).className='error';
			        		    			  document.getElementById(matchId+'_sxdsResultSp').value=-dczcMatch.sxdsResultSp;
			        		    		}else{
			        		    			document.getElementById(matchId+'_sxdsResultSp').value=dczcMatch.sxdsResultSp;
			        		    		}
			        		             
			        		
			        		             if(dczcMatch.bfResultSp<0){
			        		    			 document.getElementById(matchId+'_bfResultSp_flag').innerHTML='error';
			        		    			 document.getElementById('match_'+matchId).className='error';
			        		    			 document.getElementById(matchId+'_bfResultSp').value=-dczcMatch.bfResultSp;
			        		    		}else{
			        		    			document.getElementById(matchId+'_bfResultSp').value=dczcMatch.bfResultSp;
			        		    		}
			        		        
			        		            if(dczcMatch.bqqspfResultSp<0){
			        		    			 document.getElementById(matchId+'_bqqspfResultSp_flag').innerHTML='error';
			        		    			 document.getElementById('match_'+matchId).className='error';
			        		    			 document.getElementById(matchId+'_bqqspfResultSp').value=-dczcMatch.bqqspfResultSp;
			        		    		}else{
			        		    			document.getElementById(matchId+'_bqqspfResultSp').value=dczcMatch.bqqspfResultSp;
			        		    		}
			        		             
			        		          <!--}-->
			        		         
			        		         var _halfHomeScore = document.getElementById(matchId+'_halfHomeScore').value;
			        		         if(0==Number(_halfHomeScore)||null==_halfHomeScore||isEmpty(_halfHomeScore)){
			        		             document.getElementById(matchId+'_halfHomeScore').value=dczcMatch.halfHomeScore;
			        		         }
			        		         
			        		          var _fullHomeScore = document.getElementById(matchId+'_fullHomeScore').value;
			        		         if(0==Number(_fullHomeScore)||null==_fullHomeScore||isEmpty(_fullHomeScore)){
			        		             document.getElementById(matchId+'_fullHomeScore').value=dczcMatch.fullHomeScore;
			        		         }
			        		          var _halfGuestScore = document.getElementById(matchId+'_halfGuestScore').value;
			        		         if(0==Number(_halfGuestScore)||null==_halfGuestScore||isEmpty(_halfGuestScore)){
			        		             document.getElementById(matchId+'_halfGuestScore').value=dczcMatch.halfGuestScore;
			        		         }
			        		          var _fullGuestScore = document.getElementById(matchId+'_fullGuestScore').value;
			        		         if(0==Number(_fullGuestScore)||null==_fullGuestScore||isEmpty(_fullGuestScore)){
			        		             document.getElementById(matchId+'_fullGuestScore').value=dczcMatch.fullGuestScore;
			        		         }
			        		         
			        		     }
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
		function setMatchSize(){
			var size = document.getElementById('match_size').value;
			if(!/\d/.test(size)){
				alert('场次数目必须是数字.');
				return;
			}
			size = parseInt(size, 10);
			if(size < 0){
				alert('场次数目不能小于0.');
				return;
			}
			var matchTabObj = $('matchTab');
			var trArr = matchTabObj.getElements('tr[_group=match]');
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
		
		function importMatchData(){
			var arr = ['gameName','gameColor','matchTime','homeTeamName','guestTeamName','handicap','homeTeamGuangdongName','guestTeamGuangdongName'];
			
			var firstIndexStr = document.getElementById('firstIndex').value;
			if(!/\d/.test(firstIndexStr)){
				alert("场次起始值必须是数字.");
				return ;
			}
			var firstIndex = parseInt(firstIndexStr,10);
			if(firstIndex <= 0){
				alert("场次起始值从1开始算，不能小于0.");
				return ;
			}
			var match_count = document.getElementById('match_count').value;
			if(firstIndex > (match_count+1)){
				firstIndex = match_count+1;
			}
			
			var headStr = document.getElementById('headStr').value.trim();
			var headIndexArr = headStr.split(',');
			
			var content = document.getElementById('matchContent').value.trim();
			content = content.replace(/(\r\n|\n)+/g,'$');
			if(content.length > 0 && content.substr(content.length-1) == '$')
				content = content.substr(0,content.length-1);
				
	        var matchArr = content.split('$');
	        var index = firstIndex - 1;
	        for(var i=0,l=matchArr.length;i<l;i++){
	        	var str = matchArr[i].trim();
	        	str = str.replace(/(\d{4}(-|\/)\d{1,2}\2\d{1,2})\s(\d{1,2}:\d{1,2})/, '$1@~@$3');
	        	var items = str.split(/\s+/g);
	        	var obj = {index : index};
	        	for(var j=0;j<items.length;j++){
	        		if(j < headIndexArr.length){
		        		var p = parseInt(headIndexArr[j],10);
		        		if(p < 0 || p > arr.length){
		        			alert("列对应值错误.");
		        			return;
		        		}
		        		obj[arr[p]] = items[j].replace('@~@', ' ');
	        		}
	        	}
	        	if(index < match_count){
	        		var matchForm = document.getElementById('matchForm');
	        		for(var op in obj){
	        			if(op != 'index'){
	        				matchForm.elements['matchs['+obj.index+'].'+op].value= obj[op];
	        			}
	        		}
	        	}else{
	        		addRow(obj);
	        	}
	        	index++;
	        }
	        document.getElementById('match_size').value = index;
	        document.getElementById('match_count').value = index;
	        document.getElementById('firstIndex').value = index+1;
		}
		function getGameColor(gameName){
			if(gameName == null || gameName=='') return;
			var url="<@c.url value="/jsp/gameColor.jsp" />";
			url += '?gameName='+gameName;
			$floater({
				width : 580,
				height : 350,
				src : url,
				title : '选择颜色',
				fix : 'true'
			});
		} 
		function updateColor(gameName,color){
			$('#matchForm input[_group="'+gameName+'"]').each(function(index,item){
				item.value = color;
				item.style.backgroundColor = color;
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
	<form id="matchForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/period!updateMatch.action' />" method="post">
		<input type="hidden" name="id" value="${period.id!}" />
		<table id="matchTab" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
		  <tr class="twonavlblue">
		    <tr class="twonavlblue">
		    <td colspan="4" style="text-align:left;padding-left:10px;">场次数目：<input id="match_size" value="${(matchs![])?size}" style="width:40px;" />&nbsp;<input type="button" value="设置" onclick="setMatchSize();" /></td>
		    <td colspan="3" style="text-align:left;padding-left:10px;"><span id="match_button_span"><input type="button" value="捉取赛事对阵" onclick="fetchMatch(${period.id!});" /></span></td>
		    <td colspan="12">&nbsp;<span id="bidui_result" style="color:red;font-weight:bold;"></span></td>
		  </tr>
		  </tr>
		  <tr class="trlbrown" align="center" id="match0">
		    <td height="22">场次</td>
		    <td>联赛名</td>
		    <td>颜色</td>
		    <td>比赛时间</td>
		    <td>主队名称</td>
		    <td>主队粤语</td>
		    <td>客队名称</td>
		    <td>客队粤语</td>
		    <td>让球</td>
		    <td>结束</td>
		    <td>半场比分</td>
		    <td>全场比分</td>
		    <td>取消</td>
		    <td>胜平负开奖SP</td>
		    <td>进球数开奖SP</td>
		    <td>上下单双开奖SP</td>
		    <td>比分开奖SP</td>
		    <td>半全场开奖SP</td>
		  </tr>
		  <#if matchs??>
	  		<#list matchs as match>
	  		  <#if match_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
	  		  <tr _group="match" id="match_${match_index}" class="${trColor}" align="center">
			    <td>${match.lineId+1}</td>
			    <td><input name="matchs[${match_index}].gameName" value="${match.gameName!}" style="width:40px;" id="gameName_${match.lineId+1}" onclick="getGameColor('${match.gameName!}');"/></td>
			    <td><input name="matchs[${match_index}].gameColor" value="${match.gameColor!}" style="width:55px;background-color:${match.gameColor!}" onkeyup="this.style.backgroundColor=this.value;" onchange="this.style.backgroundColor=this.value;" _group="${match.gameName!}" /></td>
			    <td><input name="matchs[${match_index}].matchTime" value="<#if match.matchTime??>${match.matchTime?string('yyyy-MM-dd HH:mm:ss')}</#if>"  style="width:100px;"/></td>
			    <td><input name="matchs[${match_index}].homeTeamName" value="${match.homeTeamName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].homeTeamGuangdongName" value="${match.homeTeamGuangdongName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamName" value="${match.guestTeamName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].guestTeamGuangdongName" value="${match.guestTeamGuangdongName!}" style="width:40px;" /></td>
			    <td><input name="matchs[${match_index}].handicap" value="${match.handicap!}" style="width:30px;" /></td>
			    <td>
			    	<input id="ended_checkbox_${match_index}" type="checkbox" <#if match.ended>checked="checked"</#if> onclick="endOrCancel(${match_index},0,this.checked);">
	    			<input id="ended_hidden_${match_index}" type="hidden" name="matchs[${match_index}].ended" value="<#if match.ended>true<#else>false</#if>">
			    </td>
			    <td><input name="matchs[${match_index}].halfHomeScore" value="${match.halfHomeScore!}" id="${match.lineId}_halfHomeScore" style="width:20px;">:<input type="text" name="matchs[${match_index}].halfGuestScore" id="${match.lineId}_halfGuestScore" value="${match.halfGuestScore!}" style="width:20px;"></td>
			    <td><input name="matchs[${match_index}].fullHomeScore" value="${match.fullHomeScore!}" id="${match.lineId}_fullHomeScore" style="width:20px;">:<input type="text" name="matchs[${match_index}].fullGuestScore" id="${match.lineId}_fullGuestScore" value="${match.fullGuestScore!}"  style="width:20px;"></td> 
			    <td>
			    	<input id="cancel_checkbox_${match_index}" type="checkbox" <#if match.cancel>checked="checked"</#if> onclick="endOrCancel(${match_index},1,this.checked);">
	    			<input id="cancel_hidden_${match_index}" type="hidden" name="matchs[${match_index}].cancel" value="<#if match.cancel>true<#else>false</#if>">
			    </td>
			    <td><input name="matchs[${match_index}].spfResultSp" value="<#if match.spfResultSp??>#{match.spfResultSp;M7}</#if>" style="width:55px;" id="${match.lineId}_spfResultSp"/><span style="color:red;background-color:yellow;" id="${match.lineId}_spfResultSp_flag"></span></td>
			    <td><input name="matchs[${match_index}].zjqsResultSp" value="<#if match.zjqsResultSp??>#{match.zjqsResultSp;M7}</#if>" style="width:55px;" id="${match.lineId}_zjqsResultSp"/><span style="color:red;background-color:yellow;" id="${match.lineId}_zjqsResultSp_flag"></span></td>
			    <td><input name="matchs[${match_index}].sxdsResultSp" value="<#if match.sxdsResultSp??>#{match.sxdsResultSp;M7}</#if>" style="width:55px;" id="${match.lineId}_sxdsResultSp"/><span style="color:red;background-color:yellow;" id="${match.lineId}_sxdsResultSp_flag"></span></td>
			    <td><input name="matchs[${match_index}].bfResultSp" value="<#if match.bfResultSp??>#{match.bfResultSp;M7}</#if>" style="width:55px;" id="${match.lineId}_bfResultSp"/><span style="color:red;background-color:yellow;" id="${match.lineId}_bfResultSp_flag"></span></td>
			    <td><input name="matchs[${match_index}].bqqspfResultSp" value="<#if match.bqqspfResultSp??>#{match.bqqspfResultSp;M7}</#if>" style="width:55px;" id="${match.lineId}_bqqspfResultSp"/><span style="color:red;background-color:yellow;" id="${match.lineId}_bqqspfResultSp_flag"></span></td>
			    
			  </tr>
	  		</#list>
	  		<tr id="last_tr" class="trlblue">
		  		<td height="30" colspan="13" style="text-align:left;padding-left:20px;">
			    </td>
			    <td colspan="5" id="match_sp_button_td"><input  type="button" name="match_sp_button" onclick="getmatchSp(${period.id!});return false;" value="更新sp"/></td>
		    </tr>
	  	  </#if>
	  	  <tr id="last_tr" class="trlblue">
		    <td height="30" colspan="18" style="text-align:left;padding-left:20px;">
		      <input id="match_count" type="hidden" name="matchCount" value="${(matchs![])?size}" />
		      <input type="submit" value="提交" />
		    </td>
		  </tr>
		</table>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DCDCDC" style="margin-top:5px;">
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="160">列对应值说明：</td>
	    <td align="left">
	    	联赛名：0，颜色：1，比赛时间：2，主队名称：3，客队名称：4，让球：6，主队粤语名称：7，客队粤语名称：5<br/>
	    	各列之间用逗号隔开，如：0,3,4，表示准备导入的第一列为联赛名，第二列为主队名，第三列为客队名
	    </td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="160">准备导入列：</td>
	    <td align="left">
	    	<input id="headStr" type="text" value="0,2,3,4,5" />
	    </td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="160">从第几场开始导入：</td>
	    <td align="left"><input id="firstIndex" type="text" value="${((matchs![])?size)+1}" /></td>
	  </tr>
	  <tr class="trw">
	    <td class="trhemaigray" align="right" width="160">对阵内容：</td>
	    <td align="left">
		  <textarea id="matchContent" cols="100" rows="10"  wrap="off"></textarea>
	    </td>
	  </tr>
	  <tr class="trlblue">
	    <td align="left" colspan="2"><input type="button" value="导入数据" onclick="importMatchData();" /></td>
	  </tr>
	</table>
</div>
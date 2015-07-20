<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>彩种销售管理</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type='text/javascript' src='${base}/dwr/util.js'></script>
	<script type='text/javascript' src='${base}/dwr/engine.js'></script>
	<script type="text/javascript" src="${base}/dwr/interface/${period.lotteryType}Manage.js"></script>
	<script type="text/javascript">
		if(!Browser.Engine.trident){
			HTMLElement.prototype.insertAdjacentHTML=function(where, html){
			           var e=this.ownerDocument.createRange();
			           e.setStartBefore(this);
			           e=e.createContextualFragment(html);
			           switch (where) {
			                    case 'beforeBegin': this.parentNode.insertBefore(e, this);break;
			                    case 'afterBegin': this.insertBefore(e, this.firstChild); break;
			                    case 'beforeEnd': this.appendChild(e); break;
			                    case 'afterEnd':
			                    if(!this.nextSibling) this.parentNode.appendChild(e);
			                    else this.parentNode.insertBefore(e, this.nextSibling); break;
			           }
			}; 
		}
		
		var li_log = null;
	 	var div_log = null;
		var span_currentRun = null;
		var lockLogScroll = false;//是否锁定日志滚动条，默认不锁定，日志自动滚动
		var buttonArr = [];
		var isRunWork = false;
		
	 	function execWork(cmd,salesMode,button){
			if(button == null || button.disabled === true){
				return ;
			}
			if(!window.confirm('您确定要执行【'+button.value+'】操作吗？')){
				return ;
			}
			var saleManageForm = document.forms['saleManageForm'];
			var periodId = saleManageForm.elements['periodId'].value;
			var execFn = '${period.lotteryType}Manage.runWork('+periodId+',"'+salesMode+'","' + cmd +'")';
			
			beforeStartWork(cmd,button);
			try{
				eval(execFn);
			}catch(err){
				window.alert(err.message);
				dwr.engine.setActiveReverseAjax(false);
				isRunWork = false;
			}
		}
		
		function beforeStartWork(cmd,button){
			isRunWork = true;
			var tempObj;
			for(var i=0,len=buttonArr.length;i<len;i++){
				tempObj = document.getElementById(buttonArr[i]);
				if(tempObj != null){
					tempObj.disabled = true;
				}
			}
			if(div_log == null){
				div_log = document.getElementById("div_log");
			}
			div_log.innerHTML = '';
			if(li_log == null){
				li_log = document.getElementById("li_log");
			}
			if(li_log.style.display == 'none'){
				var displayButton = document.getElementById("button_displayLog");
				displayLog(displayButton);
			}
			if(span_currentRun == null){
				span_currentRun = document.getElementById("span_currentRun");
			}
			var str = '';
			if(button.id.indexOf('${singlePrefix }') >= 0){
				str = '单式';
			}else if(button.id.indexOf('${polyPrefix }') >= 0){
				str = '复式';
			}
			span_currentRun.innerHTML = '<img src="${base}/images/data-loading.gif" />正在执行'+str+button.value;
			dwr.engine.setActiveReverseAjax(true);
		}
		var globalMsg="";
		var tempMsg="";
		function receiveMessage(msg){
			globalMsg=globalMsg+msg+"<br />";
		}
		function writeMsg(){
			globalMsg=globalMsg.substr(tempMsg.length);
			tempMsg=globalMsg;
			if(tempMsg.length>0){
				if(div_log == null){
					div_log = document.getElementById("div_log");
				}
				div_log.insertAdjacentHTML("beforeEnd",tempMsg);
				if(lockLogScroll == false){
					div_log.scrollTop += div_log.scrollHeight;
				}
			}
			setTimeout("writeMsg()",100);
		}
		
		function workEnd(){
			dwr.engine.setActiveReverseAjax(false);
			isRunWork = false;
			if(span_currentRun == null){
				span_currentRun = document.getElementById("span_currentRun");
			}
			span_currentRun.innerHTML = '';
		}
		
		function updateButtonState(arr){
			if(arr != null){
				var tempObj;
				for(var i=0,len=arr.length;i<len;i++){
					tempObj = document.getElementById(arr[i]);
					if(tempObj != null){
						tempObj.disabled = false;
					}
				}
			}
		}
		
		function updateStatusText(obj){
			if(typeof obj == 'object'){
				<#list statusMarks![] as mark>
				if(obj.${mark}){
					document.getElementById('${mark}').innerHTML = obj.${mark};
				}
				</#list>
			}
		}
		
		function changeLogScrollConfig(button){
			lockLogScroll = button.checked;
		}
		
		function displayLog(button){
			if(li_log == null){
				li_log = document.getElementById("li_log");
			}
			if(li_log.style.display == 'none'){
				li_log.style.display = '';
				button.value = '隐藏'
			}else{
				li_log.style.display = 'none';
				button.value = '显示'
			}
		}
		
		function checkRunWork(){
			if(isRunWork == true){
				window.alert('正在执行操作，请稍候再试...');
				return false;
			}
			return true;
		}
	</script>
</head>
<div class="nowpalce">
	<div style="float:left;">
		您所在位置：<a href="${base}/">彩种管理</a> → <a href="${base}/admin/lottery/${period.lotteryType.key}/period!list.action">${period.lotteryType.lotteryName}</a> → <span style="color:red;">销售管理</span>
	</div>
    <div style="float:left;padding-left:60px;">
		快捷链接：
	  	<a href="${base}/admin/lottery/${period.lotteryType.key}/period!edit.action?id=${period.id}">数据编辑</a>
	  	<a href="${base}/admin/lottery/${period.lotteryType.key}/scheme!list.action?periodNumber=${period.periodNumber}" >方案管理</a>
  	</div>
</div>
<div class="twonavgray">
	<div >
        <div  style="padding:0px 0px 0px 15px;">
        	<span class="chargraytitle">销售管理：<span class="charredbold">${period.periodNumber!}期</span></span>
        	<span id="${IssueStatusMark}" style="font-size:14px;">${period.saleManageStatusHtml!}</span>
        </div>
     </div>
</div>
<div>
    <div style="border: 1px solid #DCDCDC;padding-bottom:10px;">
    <form name="saleManageForm" onsubmit="return false;">
	<input type="hidden" name="periodId" value="${period.id }" />
    <ul>
		<li>
			<div style="margin-right:6px;">
				<div class="info" style="font-weight: bold;">
					单式销售流程<#if singleSales.state??><font color="blue">【<span id="${singleStatusMark}">${singleSales.state.stateName}</span>】</font></#if>
				</div>
		    	<div>
		    		<#list singleSaleCmdMap.entrySet() as entry>
		    			<#assign buttonId="${singlePrefix }${entry.key }" />
		    			<input id="${buttonId}" type="button" value="${entry.key.cmdName }" onclick="execWork('${entry.key }','SINGLE',this);" <#if !entry.value?? || !entry.value>disabled="disabled"</#if> />
		    			<script type="text/javascript">buttonArr.push('${buttonId }');</script>
		    		</#list>
		    	</div>
			</div>
			<div style="padding-top:10px;margin-right:6px;">
				<div class="info" style="font-weight: bold;">
					复式销售流程<#if compoundSales.state??><font color="blue">【<span id="${polyStatusMark}">${compoundSales.state.stateName}</span>】</font></#if>
				</div>
		    	<div>
		    		<#list polySaleCmdMap.entrySet() as entry>
		    			<#assign buttonId="${polyPrefix }${entry.key }" />
		    			<input id="${buttonId}" type="button" value="${entry.key.cmdName }" onclick="execWork('${entry.key }','COMPOUND',this);" <#if !entry.value?? || !entry.value>disabled="disabled"</#if> />
		    			<script type="text/javascript">buttonArr.push('${buttonId }');</script>
		    		</#list>
		    	</div>
			</div>
			<div style="padding-top:10px;margin-right:6px;">
				<div class="info" style="font-weight: bold;">
					其他操作
				</div>
		    	<div>
		    		<#list otherCmdMap.entrySet() as entry>
		    			<#assign buttonId="${otherPrefix }${entry.key }" />
		    			<input id="${buttonId}" type="button" value="${entry.key.cmdName }" onclick="execWork('${entry.key }','',this);" <#if !entry.value?? || !entry.value>disabled="disabled"</#if> />
		    			<script type="text/javascript">buttonArr.push('${buttonId }');</script>
		    		</#list>
		    	</div>
			</div>
			<div style="padding-top:10px;margin-right:6px;">
				<div class="info" style="font-weight: bold;">
					开奖流程
				</div>
		    	<div>
		    		<#list prizeCmdMap.entrySet() as entry>
		    			<#assign buttonId="${prizePrefix }${entry.key }" />
		    			<input id="${buttonId}" type="button" value="${entry.key.cmdName }" onclick="execWork('${entry.key }','',this);" <#if !entry.value?? || !entry.value>disabled="disabled"</#if> />
		    			<script type="text/javascript">buttonArr.push('${buttonId }');</script>
		    		</#list>
		    	</div>
			</div>
			<div style="padding-top:10px;margin-right:6px;">
				<div class="info" style="font-weight: bold;">
					结束后操作
				</div>
		    	<div>
		    		<#list afterFinishCmdMap.entrySet() as entry>
		    			<#assign buttonId="${afterFinishPrefix }${entry.key }" />
		    			<input id="${buttonId}" type="button" value="${entry.key.cmdName }" onclick="execWork('${entry.key }','',this);" <#if !entry.value?? || !entry.value>disabled="disabled"</#if> />
		    			<script type="text/javascript">buttonArr.push('${buttonId }');</script>
		    		</#list>
		    	</div>
			</div>
		</li>
		
		<li class="info" style="font-weight: bold;padding-top:10px;margin-left:6px;margin-right:6px;">
			<span style="float: left;">操作信息</span>
			<span id="span_currentRun" style="padding-left: 20px;float: left;"></span>
			<span style="float: right;">
				<input id="lockScroll" type="checkbox" value="true" onclick="changeLogScrollConfig(this);" /><label for="lockScroll">锁定滚动</label>
				<input id="button_displayLog" type="button" value="显示" onclick="displayLog(this);" style="width: 40px;" />
			</span>
		</li>
		<li id="li_log" style="display:none;margin-right:6px;">
			<div id="div_log" style="line-height: 18px;height: 150px;overflow-y:scroll;overflow-x:auto;border: 1px solid #97a5b0;">
				
			</div>
		</li>
	</ul>
	</form>
	<script type="text/javascript">writeMsg();//启动</script>
	</div>
</div>
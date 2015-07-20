<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery}"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
	<script type="text/javascript">
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
		function setSaleState(type){
		  reUrl = "${base}/admin/lottery/keno/${lottery.key!}/sales-manager!pause.action";
		  var alertString="";
		  if(0==type){
		     alertString="启动销售";
		  }else if(1==type){
		   	 alertString="暂停销售";
		  }
		  if (confirm("您确定"+alertString+"吗？")) {
		            new Request({
				    url: reUrl,
					onSuccess: function(responseText, responseXML){
					   var jsonObj = JSON.decode(responseText);
					   if(jsonObj["success"]){
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					      window.location.reload();
					   }else{
					      var msg=jsonObj["msg"];
					      window.alert(msg);
					   }
					}
			}).get({'__t=':new Date().getTime()});
		  }
		  return false;
		}
		/**
		 * 操作时间
		 */
		function oprIssueTimeForm(form){
					    if(window.confirm('您确认要提交吗？')){
					        var form=$(form);
							var schemeFormOption = {
								onSuccess: function(responseText, responseXML){
								// 请求成功后执行的方法
								   var jsonObj = JSON.decode(responseText);
								   if(jsonObj["success"]){
								      var msg=jsonObj["msg"];
								      window.alert(msg);
								      window.location.reload();
								   }else{
								      var msg=jsonObj["msg"];
								      window.alert(msg);
								   }
								},
								onFailure: function(instance){
								// 请求失败后执行的方法
								   window.alert('失败');
								}
							};
							form.set('send', schemeFormOption);// 设置form提交异步请求的处理方法
							form.send();// 提交异步请求 
						}
						return false;
		} 
	</script>
</head>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/keno/${lottery.key}/sales-manager!list.action">${lottery.lotteryName}</a>  
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">${lottery.lotteryName}销售配置管理</span>
        </div>
     </div>
</div>
<form action="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!oprIssueTime.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}" onsubmit="return oprIssueTimeForm(this);return false"><#-- 禁止回车提交 -->
	<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="center" align="left">
	 		<tr class="trlbrown">
				<td colspan=2>${lottery.lotteryName}销售配置</td>
			</tr>
			<tr bgcolor="#F5f5f5">
				<td bgcolor="#F5f5f5" align="center" width="20%">销售状态：</td>
				   <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				      <#if pause == 'true'>暂停<#else>正常销售</#if>
				      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      <#if pause == 'true'><a href="#" onclick="setSaleState(0)">启动销售</a><#else><a href="#" onclick="setSaleState(1)">暂停销售</a></#if>
				   </span></td>
			</tr>     
			
			<tr class="trlbrown">
				<td colspan=2>批量调整期时间(查询期号时间按照截至时间去查询)</td>
			</tr>   
			<tr bgcolor="#F5f5f5">
				<td bgcolor="#F5f5f5" align="center" width="20%">开售时间：</td>
				   <td bgcolor="#F5f5f5">
				  	从<input id="dateStar" type="text" name="dateStar" onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})"/>
			    	<img onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				           到<input id="dateEnd" type="text" name="dateEnd" onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})"/>
			    	<img onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				  &nbsp;&nbsp;&nbsp;  格式：yyyy-MM-dd HH:mm</td>
			</tr>    
			<tr bgcolor="#FFFFFF">
				     <td align="center">调整类型：</td>
				      <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                增加：<input type="radio" name="oprType" value="0" checked="true"/>                  
					                减少：<input type="radio" name="oprType" value="1"/>
					   </span></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				     <td align="center">调整时间：</td>
				      <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					          <input name="dateMin" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />分钟  
					   </span></td>
			</tr>
			<tr bgcolor="#F5f5f5">
				<td bgcolor="#F5f5f5" align="center" width="20%"></td>
				   <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				      <input type="image" src="${base}/images/comfirm.gif" /></span>
				   </td>
			</tr>    
	</table>
</form>
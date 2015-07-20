<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<head>
	<title>${lotteryType.lotteryName}-添加新一期</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
	<script type="text/javascript">  
	     function checkRemoteIssueData(form){
	         var gameIssue = form.elements['period.periodNumber'].value;
	         var jsonRequest = new Request.JSON({
		    	url: "<@c.url value='/admin/lottery/${lotteryType.key}/period!checkRemoteIssueData.action?gameIssue=' />"+gameIssue,
			    onComplete: function(jsonObj, text){
			        if(jsonObj != null){
			        	 if (jsonObj.sameIssue != null){
							    if(jsonObj.sameIssue){
							       form.submit();
							    }else{
							       if (confirm('出票合作方未能匹配该期号，确定要添加吗？')) {
		                              form.submit();
		                           }
							    }
						 }else{
								 if (confirm('出票合作方未能匹配该期号，确定要添加吗？')) {
		                               form.submit();
		                           }
						 }
			        }else{
			            if (confirm('出票合作方未能匹配该期号，确定要添加吗？')) {
		                               form.submit();
		                }
			        }
			    }
			}).get({ 
			    '__t': new Date().getTime()
			});
			return false;
	      }
	 </script>  
</head>

<@com.displaySimpleNav menuObj menuItemObj true>添加新一期</@>

<div>
	<form id="periodForm" action="<@c.url value='/admin/lottery/${lotteryType.key}/period!create.action' />" method="post">
		<#include "baseEditNewData.ftl" />
		<input type="submit" value="保存数据" />
	</form>
</div>
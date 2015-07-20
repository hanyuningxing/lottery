<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery}"/>
	<script type="text/javascript">
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
		function setIssueClose(id){
		  var closeUrl="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!setIssueClose.action?id="+id;
		  if (confirm("您确定把该期设为全部结束吗？")) {
		            new Request({
				    url: closeUrl,
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
	</script>
</head>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!list.action">${lottery.lotteryName!}</a>  
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">${lottery.lotteryName!}管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
      <a href="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!saleConfig.action">销售配置</a>
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
  </div>
</div>
<div>
<form name="queryForm" action="<@c.url value='/admin/lottery/keno/${lottery.key}/sales-manager!list.action' />" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
	<ul>
  	  <li>
  	    <#assign schemeStateArr=stack.findValue("@com.cai310.lottery.common.keno.IssueState@values()") />
  	    <label for="queryForm_periodNumber" style="font-weight:bold;">期号：</label><input id="queryForm_periodNumber" name="queryForm.periodNumber" value="<#if queryForm?? && queryForm.periodNumber??>${queryForm.periodNumber}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
  	  	<select name="queryForm.issueState">
  	  	  <option value="">方案状态</option>
  	  	  <#list schemeStateArr as state>
  	  	  <option value="${state}" <#if queryForm?? && queryForm.issueState?? && queryForm.issueState==state>selected="selected"</#if>>${state.stateName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
      	<select name="pagination.pageSize">
  	  	  <option value="">每页记录数</option>
  	  	  <#list [20,50,100,200] as size>
			<option value="${size}" <#if pagination?? && pagination.pageSize == size>selected="selected"</#if>>每页${size}条</option>
		  </#list>
  	  	</select>
  	  	&nbsp;&nbsp;
  	  	<a href="#" onclick="onSubmitSearch();return false;"><img src="<@c.url value='/styles/adminDefault/images/ico_search.gif' />" border="0" align="absmiddle"/></a>
	  </li>
	</ul>
</div>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22">编号</td>
		<td>期号</td>
		<td>系统开售时间</td>
		<td>官方截止时间</td>
		<td>开奖号码</td>
		<td>销售状态</td>
		<td>总方案数</td>
		<td>总销量</td>
		<td>总中奖数</td>
		<td>操作</td>
		<td>特殊操作</td>
	</tr>
	<#if pagination??&& (pagination.result![])?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			<td>${data.id!}</td>
			<td>${data.periodNumber!}</td>
			<td>${data.beginTime!}</td>
			<td>${data.endedTime!}</td>
			<td>${data.results!}</td>
			<td>${data.state.stateName!}</td>
			<td>${data.allSchemeCount!}</td>
			<td>${data.allSale!}</td>
			<td>${data.allPrize!}</td>
			<td>
			<a href="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!edit.action?issueData.id=${data.id!}">编辑</a>
			<a href="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!scheme.action?queryForm.periodNumber=${data.issueNumber!}" >方案管理</a>
	  		<a href="${base}/admin/lottery/keno/${lottery.key!}/sales-manager!chase.action?queryForm.periodNumber=${data.issueNumber!}" >追号管理</a>
	  		<a href="<@c.url value='/admin/counter/${lottery.key}.action?periodNumber=${data.issueNumber!}' />">统计报表</a>
			</td>
			<td>
			  <#if data.state=='ISSUE_SATE_FINISH'><#else><a href="#" onclick="setIssueClose(${data.id!})">设为全部结束(停用该期)</a></#if></if>
			</td>
		</tr>
		</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="11">无记录</td>
	    </tr>
	</#if>
</table>
<#import "/WEB-INF/macro/pagination_admin.ftl" as b />
<@b.page />
</div>
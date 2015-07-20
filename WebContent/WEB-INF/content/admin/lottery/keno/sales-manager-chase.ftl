<head>
<title>${lottery.lotteryName} - 彩种管理</title>
<meta name="menu" content="lotteryManager"/>
<meta name="menuItem" content="${lottery}"/>
	<script type="text/javascript">
		function onSubmitSearch(){
			var queryForm = document.forms['queryForm'];
			queryForm.submit();
		}
	</script>
</head>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/lottery/ssq/period!list.action">彩种管理</a> → <a href="${base}/admin/lottery/keno/${lottery.key}/sales-manager!list.action">${lottery.lotteryName}</a>  
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">${lottery.lotteryName}追号管理</span>
        </div>
     </div>
</div>
<form name="queryForm" action="<@c.url value='/admin/lottery/keno/${lottery.key}/sales-manager!chase.action' />" method="get">
<div style="border: 1px solid #DCDCDC;padding-top:5px;border-bottom:none;">
	<ul>
  	  <li>
  	    <#assign schemeStateArr=stack.findValue("@com.cai310.lottery.common.ChaseState@values()") />
  	  	<select name="queryForm.chaseState">
  	  	  <option value="">方案状态</option>
  	  	  <#list schemeStateArr as state>
  	  	  <option value="${state}" <#if queryForm?? && queryForm.chaseState?? && queryForm.chaseState==state>selected="selected"</#if>>${state.stateName}</option>
  	  	  </#list>
  	  	</select>&nbsp;
  	  	<select name="queryForm.wonStop">
  	  	  <option value="">是否中奖后停追</option>
  	  	  <option value="true" <#if queryForm?? && queryForm.wonStop?? && queryForm.wonStop>selected="selected"</#if>>是</option>
  	  	  <option value="false" <#if queryForm?? && queryForm.wonStop?? && !queryForm.wonStop>selected="selected"</#if>>否</option>
  	  	</select>&nbsp;
  	  	<select name="queryForm.won">
  	  	  <option value="">是否中奖</option>
  	  	  <option value="true" <#if queryForm?? && queryForm.won?? && queryForm.won>selected="selected"</#if>>中奖</option>
  	  	  <option value="false" <#if queryForm?? && queryForm.won?? && !queryForm.won>selected="selected"</#if>>未中奖</option>
  	  	</select>&nbsp;
      	<label for="queryForm_periodNumber" style="font-weight:bold;">期号：</label><input id="queryForm_periodNumber" name="queryForm.periodNumber" value="<#if queryForm?? && queryForm.periodNumber??>${queryForm.periodNumber}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_firstPeriodNumber" style="font-weight:bold;">首期期号：</label><input id="queryForm_firstPeriodNumber" name="queryForm.firstPeriodNumber" value="<#if queryForm?? && queryForm.firstPeriodNumber??>${queryForm.firstPeriodNumber}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
      	&nbsp;
      	<label for="queryForm_sponsorNames" style="font-weight:bold;">发起人：</label><input id="queryForm_sponsorNames" name="queryForm.sponsorNames" value="<#if queryForm?? && queryForm.sponsorNames??>${queryForm.sponsorNames}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" style="width:80px;" maxlength="20" />
  		&nbsp;
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
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
	<tr class="trlbrown">
		<td height="22">当前期ID</td>
		<td>当前方案ID</td>
		<td>发起人ID</td>
		<td>发起人名称</td>
		<td>状态</td>
		<td>创建时间</td>
		<td>总追号期数 </td>
		<td>已追期数</td>
	</tr>
	<#if pagination?? && pagination.result?? && (pagination.result![])?size gt 0>
		<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			<td>${data.curPeriodId!}</td>
			<td>${data.curSchemeId!}</td>
			<td>${data.userId!}</td>
			<td>${data.userName!}</td>
			<td>${data.state.stateName!}</td>
			<td>${data.createTime!?string("yyyy-MM-dd HH:mm")}</td>
			<td>${data.allCount!}</td>
			<td>${data.nowCount!}</td>
		</tr>
		</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="10">无记录</td>
	    </tr>
	</#if>
</table>
<#import "/WEB-INF/macro/pagination_admin.ftl" as b />
<@b.page />
</div>
<title>用户管理</title>
<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="fundetail"/>
<script src="${base}/js/admin/user.js" type="text/javascript"></script>
<form name="queryForm" action="${base}/admin/user/user!fundetailList.action" method="get">
     用户名：<input name="userName" value="<#if userName??>${userName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
     <#assign fundDetailTypeArr=stack.findValue("@com.cai310.lottery.common.FundDetailType@values()") />
              	<select name="fundType" onchange="document.forms['queryForm'].submit();return false;">
					<option value="">全部</option>
					<#list fundDetailTypeArr as type>
					<option <#if fundType?? && fundType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
					</#list>
	 </select>
	  <select name="timeFrame" onchange="document.forms['queryForm'].submit();return false;">
                  <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
                  <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
                  <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
                  <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
      </select>
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>用户名</td>
      <td>类型</td>
      <td>金额</td>
      <td>用户余额</td>
      <td>类型</td>
      <td>备注</td>
      <td>时间</td>
    </tr>
     <#if userName??>
    	<#assign totalIn=0 />
    	<#assign totalOut=0 />
    </#if>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as fundDetail>
    	<#if fundDetail_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${fundDetail.id}</td>
	      <td><span id="userInfoContainerSpan"><a href="${base}/admin/user/user!userInfo.action?user.id=${fundDetail.userId}">${fundDetail.userName}</a></span></td>
	      <td> 
	          <#if fundDetail??&&fundDetail.mode??>
	          ${fundDetail.mode.typeName!} 
	          		<#if userName??>
		          		<#if fundDetail.mode=='OUT'>
		          			<#assign totalOut=totalOut+fundDetail.money />
		          		</#if>
		          		<#if fundDetail.mode=='IN'>
		          			<#assign totalIn=totalIn+fundDetail.money />
		          		</#if>
	          		</#if>
	          </#if>
	      </td>
	      <td>${fundDetail.money!}</td>
	      <td>${fundDetail.resultMoney!}</td>
	      <td><#if fundDetail??&&fundDetail.type??>${fundDetail.type.typeName!}</#if></td>
	      <td>${fundDetail.remark!}</td>
	      <td>${fundDetail.createTime!}</td>
		</tr>
    	</#list>
    	<#if userName??>
    	<tr class="trlbrown">
		  <td height="22">合计</td>
	      <td>收入</td>
	      <td>${totalIn}</td>
	      <td>支出</td>
	      <td>${totalOut}</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
    	</#if>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="8">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
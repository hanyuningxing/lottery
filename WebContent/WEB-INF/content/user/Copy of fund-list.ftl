<head>
	<title>资金明细</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="fund" />
<!--	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" /> -->
</head>

<div id="center_main">
  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'fund'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
    <#include "/common/message.ftl" />
      <form action="<@c.url value='/user/fund!list.action' />" method="get" id="fundForm">
	    <div class="crighttop">
		      <label><strong>查询</strong>:</label>
		      <label>
		          <#assign timeFrame=timeFrame!0 />
			      <select name="timeFrame">
			          <option value="4" <#if timeFrame==4>selected="selected"</#if>>一天</option>
			          <option value="5" <#if timeFrame==5>selected="selected"</#if>>二天</option>
			          <option value="6" <#if timeFrame==6>selected="selected"</#if>>三天</option>
			          <option value="7" <#if timeFrame==7>selected="selected"</#if>>四天</option>
			          <option value="8" <#if timeFrame==8>selected="selected"</#if>>五天</option>
			          <option value="9" <#if timeFrame==9>selected="selected"</#if>>六天</option>
			          <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
			          <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
			          <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
			          <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
			      </select>
		      </label>
		      &nbsp;&nbsp;
		      <label>交易类型：</label>
		      <label>
			    <#assign fundDetailTypeArr=stack.findValue("@com.cai310.lottery.common.FundDetailType@values()") />
              	<select name="fundType">
					<option value="">全部</option>
					<#list fundDetailTypeArr as type>
					<option <#if fundType?? && fundType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
					</#list>
				</select>
		      </label>
		      <a href="#" onclick="document.getElementById('fundForm').submit();return false;"> <img src="<@c.url value='/pages/images/center_btcx.gif' />" align="absmiddle"/></a>
	     </div>
	   </form>
    <div class="kong10"></div>
    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; border-top:1px solid #E3E3E3;" bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="100%" align="center" border="1" >
      <tr class="center_tablegray" align="center">
        <td width="28" height="28">序 </td>
        <td>进/出</td>
        <td>交易类型</td>
        <td>金额</td>
        <td>账户余额</td>
        <td>时间</td>
        <td>备注</td>
      </tr>
      </tr>
      <#if pagination.result?? && pagination.result?size gt 0>
		<#list pagination.result as data>
		<#if (data_index+1)%2==0><#assign trClass = 'center_trw'/><#else><#assign trClass = 'center_trgray'/></#if>
		<tr class="${trClass!}" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
		    <td height="32">${data_index+1}</td>
	        <td>${data.mode.typeName}</td>
	        <td>${data.type.typeName}</td>
	        <td>#{(data.money!0);M2}</td>
	        <td>#{(data.resultMoney!0);M2}</td>
	        <td>${data.createTime?string('yyyy-MM-dd HH:mm')}</td>
	        <td style="text-align:left;padding:2px;">${data.remarkString!}</td>
		</tr>
		</#list>
	  <#else>
		<tr>
		  <td colspan="7">暂无记录.</td>
		</tr>
	  </#if>
	 </table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center">
        <#import "../../macro/pagination.ftl" as b />
		<@b.page />
    </div>
    <div class="kong10"></div>
  </div>
</div>


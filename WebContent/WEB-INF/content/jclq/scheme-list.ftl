<#assign menu_type='scheme_list' />

<@override name="content">
	<div class=" cleanboth dctwobg">
	 <div class="twowz">
	    <ul class="twotopnav">
	    </ul>
	  </div>
	</div>
	<form action="<@c.url value="/${lotteryType.key}/scheme!list.action" />" method="get" id="scheme_list_form">
		<div class="bggray32" >
		    <div class="floatleft" style="padding:5px 0 0 8px;">
		        <select name="currentDate" onchange="this.form.submit();">
					<option value="">最近${dateList?size}天</option>
					<#list dateList as date>
					<option <#if currentDate?? && currentDate==(date.toString('yyyy-MM-dd'))>selected="selected"</#if> value="${date.toString('yyyy-MM-dd')}">${date.toString('yyyy-MM-dd')}</option>
			    	</#list>
			  </select>
		    </div>
		    <div class=" floatrig rig10" style="padding-top:4px;">
		      <select name="queryForm.schemeState" onchange="this.form.submit();">
				<option value="">全部</option>
		        <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="SUCCESS">selected="selected"</#if> value="SUCCESS">成功</option>
		         <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="FULL">selected="selected"</#if> value="FULL">满员</option>
		          <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="UNFULL">selected="selected"</#if> value="UNFULL">未满</option>
		        <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="CANCEL">selected="selected"</#if> value="CANCEL">撤销</option>
		      </select>
		      <select name="queryForm.costType" onchange="this.form.submit();">
		        <option value="0" <#if queryForm?? && queryForm.costType?? && queryForm.costType==0>selected="selected"</#if>>方案金额</option>
		        <option value="1" <#if queryForm?? && queryForm.costType?? && queryForm.costType==1>selected="selected"</#if>>千元以下</option>
		        <option value="2" <#if queryForm?? && queryForm.costType?? && queryForm.costType==2>selected="selected"</#if>>千元以上</option>
		        <option value="3" <#if queryForm?? && queryForm.costType?? && queryForm.costType==3>selected="selected"</#if>>不限</option>
		      </select>
		      <select name="queryForm.chooseType" onchange="this.form.submit();">
		        <option value="0" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==0>selected="selected"</#if>>方案类型</option>
		        <option value="1" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==1>selected="selected"</#if>>已保底</option>
		        <option value="2" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==2>selected="selected"</#if>>我的方案</option>
		        <option value="3" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==3>selected="selected"</#if>>全部</option>
		      </select>
		      &nbsp;&nbsp;
		      <input value="<#if queryForm??&&queryForm.sponsorName??>${queryForm.sponsorName}<#else>用户名或方案号</#if>" size="15" onclick="this.value='';" id="queryForm.sponsorName" name="queryForm.sponsorName" value="<#if queryForm??>${queryForm.sponsorName!}</#if>">
		      
		      
		      <input id="searchbutton" type="image" src="<@c.url value="/pages/images/btsearch.gif" />" />
		    </div>
		</div>
      	<input type="hidden" name="queryForm.orderType" value="<#if queryForm??&&queryForm.orderType??>${queryForm.orderType!}<#else>PROCESS_RATE_DESC</#if>" />
        <#if salesMode??><input type="hidden" name="salesMode" value="${salesMode}" /></#if>
	</form>
	<div class="hemaint k3px">
		<#include '../common/scheme-list-table.ftl' />
	</div>
</@override>

<@extends name="base.ftl"/> 
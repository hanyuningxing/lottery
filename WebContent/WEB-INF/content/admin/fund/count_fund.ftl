<title>用户管理</title>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="fundcount"/>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<form name="queryForm" action="${base}/admin/fund/fund!countFund.action" method="post">
      	从<input id="dateStar" type="text" name="dateStar" onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateStar??>${dateStar?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateStar'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				           到<input id="dateEnd" type="text" name="dateEnd" onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" value="<#if dateEnd??>${dateEnd?string('yyyy-MM-dd HH:mm')}</#if>"/>
			    	<img onclick="WdatePicker({el:$('dateEnd'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})" src="${base}/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer;" />
				  &nbsp;&nbsp;&nbsp;  格式：yyyy-MM-dd HH:mm
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown" height="25">
      <td>用户id</td>
	  <td>用户名</td>
      <td>用户类型</td>
      <td>消费</td>
      <td>充值</td>
      <td>提款</td>
      <td>中奖</td>
      <td>活动彩金</td>
      <td>佣金</td>
      <td>后台添加</td>
      <td>后台扣除</td>
    </tr>
    <#assign useCost =0 />
    <#assign payCost =0 />
    <#assign drawCost =0 />
    <#assign bonusCost =0 />
    <#assign actCost =0 />
    <#assign rebateCost =0 />
    <#assign adminInCost =0 />
    <#assign adminOutCost =0 />
    <#if countMap??&& (countMap![])?size gt 0>
        <#list countMap?keys as countMapKey> 
    	<#assign fundForm=countMap.get(countMapKey) />
    	<#if countMapKey_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td><#if fundForm??&&fundForm.userId??>${fundForm.userId!}</#if></td>
	      <td><#if fundForm??&&fundForm.userName??>${fundForm.userName!}</#if></td>
	      <td><#if fundForm??&&fundForm.userWay??>${fundForm.userWay.typeName!}</#if></td>
	      <td>${(fundForm.useCost!0)?string("0.##")}</td>
	      <td>${(fundForm.payCost!0)?string("0.##")}</td>
	      <td>${(fundForm.drawCost!0)?string("0.##")}</td>
	      <td>${(fundForm.bonusCost!0)?string("0.##")}</td>
	      <td>${(fundForm.actCost!0)?string("0.##")}</td>
	      <td>${(fundForm.rebateCost!0)?string("0.##")}</td>
	      <td>${(fundForm.adminInCost!0)?string("0.##")}</td>
	      <td>${(fundForm.adminOutCost!0)?string("0.##")}</td>
		</tr>
		<#assign useCost=useCost+fundForm.useCost />
		<#assign payCost=payCost+fundForm.payCost />
		<#assign drawCost=drawCost+fundForm.drawCost />
		<#assign bonusCost=bonusCost+fundForm.bonusCost />
		<#assign actCost=actCost+fundForm.actCost />
		<#assign rebateCost=rebateCost+fundForm.rebateCost />
		<#assign adminInCost=adminInCost+fundForm.adminInCost />
		<#assign adminOutCost=adminOutCost+fundForm.adminOutCost />
    	</#list>
    	<tr class="trlbro" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>合计</td>
	      <td>-</td>
	      <td>-</td>
	      <td>${(useCost!0)?string("0.##")}</td>
	      <td>${(payCost!0)?string("0.##")}</td>
	      <td>${(drawCost!0)?string("0.##")}</td>
	      <td>${(bonusCost!0)?string("0.##")}</td>
	      <td>${(actCost!0)?string("0.##")}</td>
	      <td>${(rebateCost!0)?string("0.##")}</td>
	      <td>${(adminInCost!0)?string("0.##")}</td>
	      <td>${(adminOutCost!0)?string("0.##")}</td>
		</tr>
    </#if>
    <tr class="trlbrown" height="25">
      <td>用户id</td>
	  <td>用户名</td>
      <td>用户类型</td>
      <td>消费</td>
      <td>充值</td>
      <td>提款</td>
      <td>中奖</td>
      <td>活动彩金</td>
      <td>佣金</td>
      <td>后台添加</td>
      <td>后台扣除</td>
    </tr>
   <#assign inResultCount =0 />
    <#if inResultList??&& (inResultList![])?size gt 0>
    	<#list inResultList as fundForm>
    	<#if fundForm_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td><#if dateStar??&&dateEnd??>${dateStar?string('yyyy-MM-dd HH:mm')}~${dateEnd?string('yyyy-MM-dd HH:mm')}</#if></td>
	      <td><#if fundForm??&&fundForm.type??>${fundForm.type.typeName!}</#if></td>
	      <td>收入</td>
	      <td>#{fundForm.money;M2}</td>
	      <#if fundForm.money??><#assign inResultCount=inResultCount+fundForm.money /></#if>
		</tr>
    	</#list>
    	<tr class="trlbro" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>合计</td>
	      <td>-</td>
	      <td>-</td>
	      <td>#{inResultCount;M2}</td>
		</tr>
    </#if>
  </table>
<#assign menu="lotteryManager" />
<#assign menuObj=globalMenus[menu]!{} />
<#assign menuItem=lotteryType.toString() />
<#if menuObj.items??>
	<#assign menuItemObj=menuObj.items[menuItem]!{} />
<#else>
	<#assign menuItemObj={} />
</#if>

<#assign searchUrl><@c.url value='/admin/lottery/${lotteryType.key}/scheme!printDetail.action' /></#assign>

<head>
	<title>出票详情</title>
	<meta name="menu" content="${menu}"/>
	<meta name="menuItem" content="${menuItem}"/>
</head>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">${schemeNum!}出票详情</span></div>
</div>
<div>
  <table align="center" width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td width="30" height="22">ID</td>
      <td>方案号</td>
      <td>方案内容</td>
      <td>倍数</td>
      <td>注数</td>
      <td>方案金额</td>
      <td>状态</td>
      <td>奖金</td>
      <td>税后奖金</td>
      <td>打印时间</td>
      <td>打印信息</td>
    </tr>
    <#if pagination?? && (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
		<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    		<#assign baodiProgressRate=data.baodiProgressRate!0>
    		<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    		    <td>${data.id}</td>
    		    <td>${data.schemeNum!}</td>
    		    <td>${data.betContent!}</td>
    		    <td>${data.multiple!}</td>
    		    <td>${data.betUnits!}</td>
    		    <td>${data.betCost!}</td>
    		    <td>${data.stateString!}</td>
    		    <td>${data.prize!}</td>
    		    <td>${data.returnPrize!}</td>
    		    <td><#if data.printTime??>${data.printTime?string("yyyy-MM-dd HH:mm")}</#if></td>
    		    <td>${data.printAgentMessage!}</td>
	        </tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="11">无记录</td>
	    </tr>
    </#if>
  </table>
</div>
</form>

<#import "../../../macro/pagination_admin.ftl" as b />
<@b.page />
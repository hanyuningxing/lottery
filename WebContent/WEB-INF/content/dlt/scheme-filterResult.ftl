<@override name="title">
	<title>体彩大乐透玩法，大乐透购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/dlt_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">大乐透免费在线过滤工具</div>
  <div class="kong10"></div>
  <form id="shrinkForm" action="" method="post" autocomplete="off" onSubmit="return false;">
  	  <input type="hidden" name="file_name" value="缩水结果" />
  	  <input id="shrinkBean_content" type="hidden" name="shrinkBean.content" value="${result!}" />
	  <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td valign="top" class="ssqss_sm">
	      	<div>
	      		<span class="font14char">根据你的选择号码以及过滤条件，缩水号码如下：</span><br />
	          	共计注数：<span class="redboldchar">${units}</span> 注，金额：<span class="redboldchar">${units*2}元</span><br /><br />
	          	<#if contents?? && contents?size gt 0>
		          <#list contents?chunk(5,'') as rows>
		          	<#list rows as line>
		          		${line}<#if line_has_next>&nbsp;&nbsp;&nbsp;&nbsp;</#if>
		          	</#list><br />
		          </#list>
	            </#if>
	      	</div>
	        <div class="all10px textcenter ">感谢您支持，预祝您本次缩水中奖！</div>
	      </td>
	      <td width="200" align="center" class="left10">
	        <img id="shrink_buy" _action="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=SINGLE&createForm.playType=General" />" src="<@c.url value="/pages/images/bt_buy.gif" />" width="174" height="43" style="cursor:pointer;" /><br /><br />
	        <a id="shrink_output" _action="<@c.url value="/jsp/download.jsp" />" href="#" onclick="return false;">导出缩水结果</a>
	      </td>
	    </tr>
	  </table>
  </form>
  <div class="kong10"></div>
</div>
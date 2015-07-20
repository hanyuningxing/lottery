<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/ssq_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">22选5在线选号：中5保4旋转矩阵(请选定7至18个基号)</div>
  <div class="kong10"></div>
  <form id="shrinkForm" action="" method="post" autocomplete="off" onSubmit="return false;">
  	  <input type="hidden" name="file_name" value="缩水结果" />
  	  <input id="shrinkBean_content" type="hidden" name="shrinkBean.content" value="${result!}" />
	  <table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td valign="top" class="ssqss_sm">
		      <div>
		      	你总选择了<span class="redboldchar">${numArr?size}</span>个基号.<br />旋转注数：<span class="redboldchar">${units}</span>注 . 共计金额：<span class="redboldchar">${units*2}元</span><br /><br />
		          <#if contents?? && contents?size gt 0>
			          <#list contents?chunk(6,'') as rows>
			          	<#list rows as line>
			          		${line}<#if line_has_next>&nbsp;&nbsp;&nbsp;&nbsp;</#if>
			          	</#list><br />
			          </#list>
		          </#if>
		      </div>
	        <div class="all10px textcenter">感谢您支持，预祝您本次缩水中奖！</div>
	      </td>
	      <td width="200" align="center" class="left10">
	        <img id="shrink_buy" _action="<@c.url value="/${lotteryType.key}/scheme!editNew.action?playType=2" />" src="<@c.url value="/pages/images/bt_buy.gif" />" width="174" height="43" style="cursor:pointer;" /><br /><br />
	        <a id="shrink_output" _action="<@c.url value="/jsp/download.jsp" />" href="javascript:void(0);" onclick="return false;">导出缩水结果</a>
	      </td>
	    </tr>
	    <tr>
	      <td valign="top"><div class="ssqss_d1">将上面号码进行二次缩水</div></td>
	      <td align="center" class="left10">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top" class="ssqss_sm">
	        <#include 'shrink.ftl' />
			<div style="padding:10px 0 0px 235px;">
				<span class="floatleft rig10"><input id="shrink_submit" _action="<@c.url value="/${lotteryType.key}/scheme!shrink.action" />" type="button" border="0" value="确定二次过滤" class="btblue" style="cursor:pointer;" /></span>
			</div>
		  </td>
	      <td align="center" class="left10">&nbsp;</td>
	    </tr>
	  </table>
  </form>
  <div class="kong10"></div>
</div>  
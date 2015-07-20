<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/ssq_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">双色球免费在线过滤工具</div>
  <div class="ssqss_d1">第一步：选择基本号码及胆码（说明：基本号码在7个号码以上，胆码为0至2之间）</div>
  <div class="kong10"></div>
  <form id="shrinkForm" action="" method="post" autocomplete="off" onSubmit="return false;">
  	  <input type="hidden" id="shrinkBean_content" name="shrinkBean.content" value="" />
	  <table width="890" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr> 
	      <td width="435" valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
		          <legend class="cdownjl"><span class="redc1">选择基本号码</span>&nbsp;<span class="grayc1">至少选择7个号码</span></legend>
		          <div class="all10px" style="width:380px; margin:0 auto; ">
		            <ul class="ssqball" id="filter_red_box">
		            	<#list 1..33 as num>
		                	<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball">${num?string('00')}</a></li>
		            	</#list>
		            </ul>
		          </div>
	          </fieldset>
	        </div></td>
	      <td>&nbsp;</td>
	      <td width="435"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
		          <legend class="cdownjl"><span class="redc1">选择胆码</span>&nbsp;<span class="grayc1">选择0-2个号码</span></legend>
		          <div class="all10px" style="width:380px; margin:0 auto; ">
		            <ul class="ssqball" id="filter_red_box_galls" >
		            	<#list 1..33 as num>
		                	<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball">${num?string('00')}</a></li>
		            	</#list>
		            </ul>
		          </div>
	          </fieldset>
	        </div></td>
	    </tr>
	    <tr>
	      <td colspan="3" valign="top" height="5">
	    </tr>
	    <tr>
	      <td colspan="3" valign="top">
	      	<div class="ssqss_sm">
	      		使用说明：为了防止过度占用服务器资源，本工具对基本号的限定如下：<br />
	          	①不设置胆码时，基本号不能超过13个；②设置1个胆码时，基本号不能超过15个；③设置2个胆码时，基本号不能超过17个。
	        </div>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="3" align="center" valign="top">
	      	<textarea id="filter_select_show" cols="60" rows="3" class="lineh22px all10px" readOnly="readOnly">基本号码：${'\r\n'}选择胆码：</textarea>
		  </td>
	    </tr>
	  </table>
	  <div class="ssqss_d1">第二步：选择号码过滤条件 </div>
	  <div style="width:890px;margin:auto;">
	  	<#include 'shrink.ftl' />
	  </div>
	  <div style="padding:10px 0 0px 265px;">
	  	<span class="floatleft rig10"><input id="filter_submit" _action="<@c.url value="/${lotteryType.key}/scheme!filter.action" />" type="button" border="0" value="确定投注过滤" class="btblue" style="cursor:pointer;" /></span>
	  	<span class="floatleft left10"><input id="filter_reset" type="button" border="0" value="取消" class="btblue" style="cursor:pointer;" /></span>
	  </div>
  </form>
  <div class="kong20"></div>
</div> 
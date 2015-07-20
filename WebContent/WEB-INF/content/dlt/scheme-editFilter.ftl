<@override name="title">
	<title>体彩大乐透玩法，大乐透购买投注 - 银生宝安全购彩平台</title>
</@override>
<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/dlt_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">大乐透免费在线过滤工具</div>
  <div class="ssqss_d1">第一步：选择基本号码及胆码（说明：基本号码在7个号码以上，胆码为0至2之间）</div>
  
  <form id="shrinkForm" action="" method="post" autocomplete="off" onSubmit="return false;">
  	  <input type="hidden" name="dlt35to5ShrinkBean.content" value="" />
  	  <input type="hidden" name="dlt12to2ShrinkBean.content" value="" />
	  <table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td valign="top" width="550"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          <legend class="cdownjl"><span class="redc1">选择前区号码</span>&nbsp;<span class="grayc1">至少选择5个号码</span></legend>
	          <div class="all10px" style="width:480px; ">
	          	<ul class="ssqball" id="filter_red_area_box">
		        	<#list 1..35 as num>
		              	<li><a href="#" onclick="return false;" _name="red_ball">${num?string('00')}</a></li>
		        	</#list>
		      	</ul>
	          </div>
	          </fieldset>
	        </div></td>
	      <td>&nbsp;</td>
	      <td width="330" valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          <legend class="cdownjl"><span class="redc1">选择后区号码</span>&nbsp;<span class="grayc1">0-2个号码</span></legend>
	          <div class="all10px" style="width:190px; ">
	            <ul class="ssqball" id="filter_blue_area_box">
		        	<#list 1..12 as num>
		       			<li><a href="#" onclick="return false;" _name="blue_ball">${num?string('00')}</a></li>
		            </#list>
		        </ul>
	          </div>
	          </fieldset>
	        </div></td>
	    </tr>
	    <tr>
	      <td height="10" colspan="5"></td>
	    </tr>
	    <tr>
	      <td valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          <legend class="cdownjl"><span class="redc1">选择前区胆码</span>&nbsp;<span class="grayc1">0-2个号码</span></legend>
	          <div class="all10px" style="width:480px; ">
	            <ul class="ssqball" id="filter_red_area_box_galls">
		        	<#list 1..35 as num>
		            	<li><a href="#" onclick="return false;" _name="red_ball">${num?string('00')}</a></li>
		        	</#list>
				</ul>
	          </div>
	          </fieldset>
	        </div></td>
	      <td>&nbsp;</td>
	      <td valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          <legend class="cdownjl"><span class="redc1">选择后区胆码</span>&nbsp;<span class="grayc1">0-1个号码</span></legend>
	          <div class="all10px" style="width:190px;  ">
	            <ul class="ssqball" id="filter_blue_area_box_galls">
		        	<#list 1..12 as num>
		            	<li><a href="#" onclick="return false;" _name="blue_ball">${num?string('00')}</a></li>
		        	</#list>
		      	</ul>
	          </div>
	          </fieldset>
	        </div></td>
	    </tr>
	    <tr>
	      <td colspan="3" valign="top" height="10">&nbsp;</td>
	    </tr>
	    <tr align="center">
	      <td><textarea id="filter_red_select_show" rows="3" class="lineh22px all10px" style="width:460px;">前区号码：${'\r\n'}前区胆码：</textarea></td>
	      <td>&nbsp;</td>
	      <td><textarea id="filter_blue_select_show" rows="3" class="lineh22px all10px" style="width:210px;">后区号码：${'\r\n'}后区胆码：</textarea></td>
	    </tr>
	  </table>
	  <div class="ssqss_d1">第二步：选择号码过滤条件（注：至少选择一种缩水规则）</div>
	  <div style="width:900px;margin:auto;">
	  	<#include 'shrink.ftl' />
	  </div>
	  <div style="padding:10px 0 0px 265px;">
	  	<span class="floatleft rig10"><input id="filter_submit" _action="<@c.url value="/${lotteryType.key}/scheme!filter.action" />" type="button" border="0" value="确定投注过滤" class="btblue" style="cursor:pointer;" /></span>
	  	<span class="floatleft left10"><input id="filter_reset" type="button" border="0" value="取消" class="btblue" style="cursor:pointer;" /></span>
	  </div>
  </form>
  <div class="kong20"></div>
</div> 
<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/ssq_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">双色球在线选号：中6保5旋转矩阵(请选定8至20个基号)</div>
  <div class="ssqss_d1">第一步：选择号码</div>
  <div class="kong10"></div>
  <form id="shrinkForm" action="<@c.url value="/${lotteryType.key}/scheme!spinmatrix.action" />" method="post" autocomplete="off">
	  <input type="hidden" name="shrinkNums" value="" />
	  <table width="620" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr> 
	      <td valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          	<legend class="cdownjl"><span class="redc1">选择号码</span>&nbsp;<span class="grayc1">选择8-20个号码</span></legend>
		          <div class="all10px" style="width:580px; margin:0 auto; ">
		            <ul class="ssqball" id="red_box">
		            	<#list 1..33 as num>
		                	<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
		            	</#list>
		          	</ul>
		          </div>
	          </fieldset>
	        </div></td>
	    </tr>
	    <tr>
	      <td valign="top" height="5"></td>
	    </tr>
	    <tr>
	      <td align="center" valign="top">
	      	<div style="padding:10px 0 0px 235px;">
	      		<span class="floatleft rig10"><input id="spinmatrix_submit" type="button"  border="0" value="提交" class="btblue" style="cursor:pointer;" /></span>
	      		<span class="floatleft left10"><input id="spinmatrix_reset" type="button"  border="0" value="重置" class="btblue" style="cursor:pointer;" /></span>
	      	</div>
	      </td>
	    </tr>
	    <tr>
	      <td valign="top"><div class="ssqss_sm">说明：旋转矩阵是许多彩民朋友采用的投注方法，能实现中6保5(只要选定的号码中含有6个正确的红号，结果肯定有一注含有5个正确的红号)，同时以极低的成本实现复试投注的效果。旋转矩阵，很多人使用的、效果真不错的、您的在线超级缩水工具！</div></td>
	    </tr>
	  </table>
  </form>
  <table width="620" border="0" align="center" cellpadding="0" cellspacing="0" class="lc_t_t">
    <tbody>
      <tr class="lc_t_td01">
        <td width="96" height="25"  >号码个数</td>
        <td width="118">复式注数</td>
        <td width="125">资金</td>
        <td width="129">旋转注数</td>
        <td width="115">资金</td>
        <td width="103">缩水比例</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >8</td>
        <td>28</td>
        <td>56</td>
        <td>4</td>
        <td>8</td>
        <td>14.3%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >9</td>
        <td>84</td>
        <td>168</td>
        <td>7</td>
        <td>14</td>
        <td>8.3%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >10</td>
        <td>210</td>
        <td>420</td>
        <td>14</td>
        <td>28</td>
        <td>6.7%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >11</td>
        <td>462</td>
        <td>924</td>
        <td>25</td>
        <td>50</td>
        <td>5.4%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >12</td>
        <td>924</td>
        <td>1848</td>
        <td>44</td>
        <td>88</td>
        <td>4.8%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >13</td>
        <td>1716</td>
        <td>3432</td>
        <td>74</td>
        <td>148</td>
        <td>4.3%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >14</td>
        <td>3003</td>
        <td>6006</td>
        <td>118</td>
        <td>236</td>
        <td>3.9%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >15</td>
        <td>5005</td>
        <td>10010</td>
        <td>174</td>
        <td>348</td>
        <td>3.5%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >16</td>
        <td>8008</td>
        <td>16016</td>
        <td>260</td>
        <td>520</td>
        <td>3.2%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >17</td>
        <td>12376</td>
        <td>24752</td>
        <td>402</td>
        <td>804</td>
        <td>3.2%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >18</td>
        <td>18564</td>
        <td>37128</td>
        <td>569</td>
        <td>1138</td>
        <td>3.1%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22"  >19</td>
        <td>27132</td>
        <td>54264</td>
        <td>783</td>
        <td>1566</td>
        <td>2.9%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22"  >20</td>
        <td>38760</td>
        <td>77520</td>
        <td>1073</td>
        <td>2146</td>
        <td>2.8%</td>
      </tr>
    </tbody>
  </table>
  <div class="kong20"></div>
</div>   
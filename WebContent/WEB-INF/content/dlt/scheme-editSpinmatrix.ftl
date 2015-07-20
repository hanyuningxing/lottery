<@override name="title">
	<title>体彩大乐透玩法，大乐透购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<head>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value="/pages/css/dlt_suoshui.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/shrink.js"/>"></script>
</head>

<div class="mainsuoshui b3px">
  <div class="ssq_sst1">大乐透在线选号：中5保4旋转矩阵(请选定7至18个基号)</div>
  <div class="ssqss_d1">第一步：选择号码</div>
  <div class="kong10"></div>
  <form id="shrinkForm" action="<@c.url value="/${lotteryType.key}/scheme!spinmatrix.action" />" method="post" autocomplete="off">
	  <input type="hidden" name="shrinkNums" value="" />
	  <table width="660" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td valign="top"><div id="touzzhuqu">
	          <fieldset class="redfieldset">
	          	  <legend class="cdownjl"><span class="redc1">选择号码</span>&nbsp;<span class="grayc1">选择7-18个号码</span></legend>
		          <div class="all10px" style="width:605px; margin:0 auto; ">
		            <ul class="ssqball" id="red_box">
		            	<#list 1..35 as num>
		                	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
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
	      <td valign="top"><div class="ssqss_sm">说明：旋转矩阵是许多彩民朋友采用的投注方法，能实现中5保4(只要选定的号码中含有5个正确的红号，结果肯定有一注含有4个正确的红号)，同时以极低的成本实现复试投注的效果。旋转矩阵，很多人使用的、效果真不错的、您的在线超级缩水工具！</div></td>
	    </tr>
	  </table>
  </form>
  <table width="660" border="0" align="center" cellpadding="0" cellspacing="0" class="lc_t_t">
    <tbody>
      <tr class="lc_t_td01">
        <td height="24"  >号码个数</td>
        <td>复式注数</td>
        <td>资金</td>
        <td>旋转注数</td>
        <td>资金</td>
        <td>缩水比例</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">7</td>
        <td>21</td>
        <td>42</td>
        <td>3</td>
        <td>6</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">8</td>
        <td>56</td>
        <td>112</td>
        <td>5</td>
        <td>8</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">9</td>
        <td>126</td>
        <td>252</td>
        <td>9</td>
        <td>18</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">10</td>
        <td>252</td>
        <td>504</td>
        <td>14</td>
        <td>28</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">11</td>
        <td>462</td>
        <td>924</td>
        <td>22</td>
        <td>44</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">12</td>
        <td>792</td>
        <td>1584</td>
        <td>35</td>
        <td>70</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">13</td>
        <td>1287</td>
        <td>2574</td>
        <td>50</td>
        <td>100</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">14</td>
        <td>2002</td>
        <td>4004</td>
        <td>69</td>
        <td>138</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">15</td>
        <td>3003</td>
        <td>6006</td>
        <td>95</td>
        <td>190</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">16</td>
        <td>4368</td>
        <td>8736</td>
        <td>134</td>
        <td>268</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td02">
        <td height="22">17</td>
        <td>6188</td>
        <td>12376</td>
        <td>179</td>
        <td>358</td>
        <td>0%</td>
      </tr>
      <tr class="lc_t_td03">
        <td height="22">18</td>
        <td>8568</td>
        <td>17136</td>
        <td>234</td>
        <td>468</td>
        <td>0%</td>
      </tr>
    </tbody>
  </table>
  <div class="kong20"></div>
</div>   
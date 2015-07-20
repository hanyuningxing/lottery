<head>
	<title>${lotteryType.lotteryName}连号走势图</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/qyhdata.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/qyh_num.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/lianhao.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
		    chgDisplay('size_30', 30);
		});
	</script>
</head>
<#assign menu="lianhao" />
<div class="main980">
        <#include "top.ftl"/>
		 <div class="kong5"></div>
		  <table width="980" border="0" cellpadding="2" cellspacing="1" class="tablegraybg" >
		    <tr class=" zs_bgligyellow">
		      <td width="85" align="center" class=" bglyellow boldchar">辅助说明</td>
		      <td  align="left"><div class="all5px lineh20">[重号] 前两期连续出现两次的号码<br />
        [连号] 即相连号，中奖号码按顺序相连<br />
        [邻号] 也叫边号，与上期开出的中奖号码加减余1的号码<br />
        [和值] 各个中奖号码数值之和。<br />
        [同尾组]指奖号个位相同的组数，如“01、11”为一组。<br />
        奇偶划分情况<br />
        [奇号] 01 03 05 07 09 11 13 15 17 19 21 23<br />
        [偶号] 02 04 06 08 10 12 14 16 18 20 22 <br />
        质合划分情况<br />
        [质号] 01 02 03 05 07 11 13 17 19 23<br />
        [合号] 04 06 08 09 10 12 14 15 16 18 20 21 22<br />
        大小划分情况<br />
        [小号]01-11，[大号]12-23。<br />
        区间划分情况<br />
          一区01-08，二区09-16，三区17-23。<br />
        </div></td>
		    </tr>
		  </table>
		 <div class="kong5"></div>
</div>
<#include "../bottom_common.ftl"/>
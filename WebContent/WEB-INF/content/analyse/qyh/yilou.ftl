<head>
	<title>${lotteryType.lotteryName}位置遗漏</title>
	<meta name="decorator" content="analyse" />
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/qyhdata.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/miss_${wz}.js"/>?_t=${randomStr}"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/base.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/analyse/qyh/yilou.js"/>"></script>
	<script type="text/javascript">
		window.addEvent('domready', function() {
			initDataHash();// 初始化历史开奖数据
			initHistoryMissHash();//初始化历史遗漏数据
		    getHtml();
		});
	</script>
</head>
<#assign menu="yilou" />
<div class="main980">
        <#include "top.ftl"/>
		 <div class="kong5"></div>
		  <table width="997" border="0" cellpadding="2" cellspacing="1" class="tablegraybg" >
		    <tr class=" zs_bgligyellow">
		      <td width="85" align="center" class=" bglyellow boldchar">辅助说明</td>
		      <td  align="left"><div class="all5px lineh20">
				        理论次数=总期数÷循环周期 <br />
				        出现概率=实出次数 ÷ 总期数 <br />
				        平均遗漏=（总期数-实出次数）÷（实出次数+1） <br />
				        欲出几率=本期遗漏 ÷ 平均遗漏 <br />
				        投资价值=本期遗漏 ÷ 循环周期 <br />
				        回补几率=（上期遗漏-本期遗漏）÷循环周期<br />
		        </div></td>
		    </tr>
		  </table>
		 <div class="kong5"></div>
</div>
<#include "../bottom_common.ftl"/>
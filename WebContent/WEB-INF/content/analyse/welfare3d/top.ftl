<#include "../banner.ftl"/>

<div class="kong5"></div>
<div class="topbgty"><table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogo3d">${lotteryType.lotteryName}</div>
		</td>
		    <td width="4"><img src="<@c.url value= "/pages/images/new_shuline.gif"/>" /></td>
		    <td valign="top">
			<div class="all10px">
			
			<div class="title_c_orange">${lotteryType.lotteryName}走势图表</div>
			<div class="kong5"></div>
			<ul class="navt_menu">
			<li><a href="<@c.url value= "/welfare3d/analyse.action"/>" <#if type='zhonghe'>class="now"</#if>>${lotteryType.lotteryName}综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="<@c.url value= "/welfare3d/analyse!hezhi.action"/>" <#if type='hezhi'>class="now"</#if>>${lotteryType.lotteryName}和值走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare3d/analyse!kuadu.action"/>" <#if type='kuadu'>class="now"</#if>>${lotteryType.lotteryName}跨度走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare3d/analyse!zhixuan.action"/>" <#if type='zhixuan'>class="now"</#if>>${lotteryType.lotteryName}直三走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare3d/analyse!g3Miss.action"/>" <#if type='g3Miss'>class="now"</#if>>${lotteryType.lotteryName}组3遗漏</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare3d/analyse!g6Miss.action"/>" <#if type='g6Miss'>class="now"</#if>>${lotteryType.lotteryName}组6遗漏</a></li>					
			</ul>	
			</div>
			</td>
		  </tr>
	</table>
</div>
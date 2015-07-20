<#include "../banner.ftl"/>

<div class="kong5"></div>
<div class="topbgty"><table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogodlt">超级大乐透</div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">大乐透走势图表</div>
			<div class="kong5"></div>
			<ul class="navt_menu">
			<li><a href="<@c.url value= "/dlt/analyse!zhzs.action"/>" <#if menu='zhzs'>class="now"</#if> >大乐透综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="<@c.url value= "/dlt/analyse!lhzs.action"/>" <#if menu='lhzs'>class="now"</#if>>大乐透前区走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/dlt/analyse!lqzs.action"/>" <#if menu='lqzs'>class="now"</#if>>大乐透后区走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/dlt/analyse!chpl.action"/>" <#if menu='chpl'>class="now"</#if>>大乐透开奖号频率柱状图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/dlt/analyse!lsbd.action"/>" <#if menu='lsbd'>class="now"</#if>>大乐透历史开奖数据查询比对</a></li>				
			</ul>	
			</div>
			</td>
		  </tr>
	</table>
</div>
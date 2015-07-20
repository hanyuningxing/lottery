<div class="main_wz">
<#if lotteryType??&&lotteryType.key??&&lotteryType.category=='FREQUENT'>
	<div class="shaix_cuti_btwz">您所在的位置:<a href="/">${webapp.webName}彩票网 </a><#if lotteryType??> > <a href="${base}/${lotteryType.key!""}/scheme.action">${lotteryType.lotteryName!}</a> > ${lotteryType.lotteryName!}走势图</#if> </div>
<#else>
	<div class="shaix_cuti_btwz">您所在的位置:<a href="/">${webapp.webName}彩票网 </a><#if lotteryType??> > <a href="${base}/${lotteryType.key!""}/scheme!editNew.action">${lotteryType.lotteryName!}</a> > ${lotteryType.lotteryName!}走势图</#if> </div>
</#if>
</div>

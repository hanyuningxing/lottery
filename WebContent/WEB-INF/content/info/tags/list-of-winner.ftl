<#if activityWinnerList??&& (activityWinnerList![])?size gt 0>
<#list activityWinnerList as data>
	<li><a href="#">恭喜 ${data[0]!} 喜中${data[1]!} 奖金<span>${data[2]!}</span>元</a></li>
</#list>
</#if>



    

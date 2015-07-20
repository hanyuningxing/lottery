<#if wonSchemeList??&& (wonSchemeList![])?size gt 0>
<#list wonSchemeList as data>
 <li><a href="javascript:void(0);"  class="graychar">[${data.lotteryType.lotteryName!}]</a>恭喜 ${data.sponsorName!} 喜中   奖金<span><#if data.prize??>#{data.prize;M0}</#if></span>元</a></li>
</#list>
</#if>



    

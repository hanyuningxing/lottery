<#if wonSchemeList??&& (wonSchemeList![])?size gt 0>
<#list wonSchemeList as data>
 <li>[${data.lotteryType.lotteryName!}]</a>恭喜 ${data.sponsorName!} 喜中   奖金<span><#if data.prize??>#{data.prize;M0}</#if></span>元</li>
</#list>
</#if>
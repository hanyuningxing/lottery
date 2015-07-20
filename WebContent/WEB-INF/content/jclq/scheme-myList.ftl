<@override name="contentHeader">
 	<div class=" cleanboth dctwobg">
	  <div class="twowz">
	    <ul class="twotopnav">
	    	<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.jclq.PlayType@values()")) />
	    	<#list playTypeArr as pt>
	        <li><a <#if playType?? && pt==playType>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!myList.action?playType=${pt}" />">${pt.text}</a></li>
	    	</#list>
	    </ul>
	  </div>
	</div>
</@override>

<@extends name="/WEB-INF/content/common/simple-scheme-myList.ftl"/> 
<@extends name="base.ftl"/> 
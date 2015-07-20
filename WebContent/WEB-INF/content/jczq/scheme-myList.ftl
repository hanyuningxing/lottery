<@override name="contentHeader">
	  <div class="navmenubg">
			<div class="navmenu">
				<ul>
					<#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.jczq.PlayType@values()")) />
			    	<#list playTypeArr as pt>
			        <li  <#if playType?? && pt==playType>class="navmenu02"<#else> class="navmenu01"</#if>><a href="<@c.url value="/${lotteryType.key}/scheme!myList.action?playType=${pt}" />">${pt.text}</a></li>
			    	<li class="navmenu03"></li>
			    	</#list>
				</ul>
			</div>
		</div>
	
</@override>

<@override name="menu"></@override>

<@extends name="/WEB-INF/content/common/simple-scheme-myList.ftl"/> 
<@extends name="base.ftl"/> 
<!-- 针对有不同玩法彩种的判断,根据玩法加载相应标题(如：14场同九场) -->
<#if !playType?? && scheme??&&scheme.playType??>
  <#assign playType = scheme.playType />
</#if>
<#assign playTypeParameter = '' />
<#if playType??>
	<#assign playTypeParameter = '?playType=${playType}' />
	<#if playType==0>
	      	<#assign topId = 'pl3_topbg' />
	<#elseif playType==1>
			<#assign topId = 'pl5_topbg' />
	</#if>
</#if> 
<@override name="menu_left">
        <ul class="tytopnav">
				<#assign menu_type=menu_type!'' />
			    <li><a <#if menu_type=='scheme_editNew'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!editNew.action${playTypeParameter}" />" >购买彩票</a></li>
			    <li><a <#if menu_type=='scheme_list'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/scheme!list.action${playTypeParameter}" />" >参与合买</a></li>
			    <li><a <#if menu_type=='result'>class="now"</#if> href="<@c.url value="/${lotteryType.key}/result.action${playTypeParameter}"/>">开奖号码</a></li>
			    <li><a <#if menu_type=='scheme_myList'>class="now"</#if> href="javascript:void(0);" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/${lotteryType.key}/scheme!myList.action${playTypeParameter}"/>';});return false;">我的投注</a></li>					
				<li><a href="<@c.url value="/info/rule/rule.action?lottery=${lotteryType}&type=${playType}"/>" target="_blank" <#if menuType==4>class="now"</#if> rel="nofollow">玩法规则</a></li>
		            
		</ul>
</@override>
<@override name="right"><#include 'rightContent.ftl' /></@override>
<@extends name="/WEB-INF/content/common/number-base.ftl"/> 


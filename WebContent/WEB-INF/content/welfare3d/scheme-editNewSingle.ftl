<@override name="content.play_caption">
	<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
      	  	   <#if salesPlayType='Direct'>玩法说明：从0-9中选择3个号码（或以上）对百、十、个位投注，所选号码与开奖号码全部一致，且顺序相同，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='Group3'>玩法说明：从0-9中选择2个号码（或以上）投注，系统会自动将所选号码的所有组三组合（即三个号中有两个号相同）进行购买，若当期开奖号码的形态为组三且包含了投注号码，即中奖，单注奖金320元。</#if>
      	  	   <#if salesPlayType='Group6'>玩法说明：从0-9中选择3个号码（或以上）投注，系统会自动将所选号码的所有组六组合（即三个号码各不相同）进行购买，若当期开奖号码的形态为组六且包含了投注号码，即中奖，单注奖金160元。</#if>
      	  	   <#if salesPlayType='DirectSum'>玩法说明：和值为当期开奖号码之和。和值购买时，系统会自动选取该和值的所有排列组合进行购买。所选和值与当期开奖号码的和值一致，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='GroupSum'>玩法说明：和值为当期开奖号码之和。只要个、十、百位三数相加为投注和值即中奖，不限顺序和形态，视开奖号码而定，组六奖金160元、组三奖金320元、豹子奖金1000元。</#if>
      	  	   <#if salesPlayType='BaoChuan'>玩法说明：选择任意一个或者多个号码进行任意排列投注，只要你所选的号码包含在全部开奖号码中，即中奖，单注奖金1000元。包1个号码时即为豹子。</#if>
      	  	   <#if salesPlayType='DirectKuadu'>玩法说明：从0-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部号码按顺序进行排列组合，若当期开奖号码的最大跨度为所选跨度，即中奖，单注奖金1000元。</#if>
      	  	   <#if salesPlayType='Group3Kuadu'>玩法说明：从1-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部组三组合进行购买，若当期开奖号码的形态为组三且最大跨度为所选跨度，即中奖，单注奖金320元。</#if>
      	  	   <#if salesPlayType='Group6Kuadu'>玩法说明：从2-9中选择跨度（1个或以上）投注，系统自动将所选跨度的全部组六组合进行购买，若当期开奖号码的形态为组六且最大跨度为所选跨度，即中奖，单注奖金160元。</#if>
   </div>
</@override>
<@override name="content.sample">&nbsp;标准格式样本<#if salesPlayType=='Group3'>01,01,03<#else>01,02,03</#if></@override>
<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
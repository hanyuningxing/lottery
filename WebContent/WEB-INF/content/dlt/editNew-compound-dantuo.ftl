<div id="touzzhuqu">
	<fieldset class="redfieldset">
		<legend class="cdownjl"><span class="redc1">前区胆码</span>&nbsp;<span class="grayc1">至少选择1个,至多4个</span></legend>
    	<ul class="ssqball" id="red_area_box_galls">
        	<#list 1..35 as num>
            	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
        	</#list>
		</ul>
    </fieldset>
    <div class="kong20"></div>
	<fieldset class="redfieldset">
        <legend class="cdownjl"><span class="redc1">前区拖码</span>&nbsp;<span class="grayc1">拖码个数加胆码个数要大于或等于5</span></legend>
      	<ul class="ssqball" id="red_area_box">
        	<#list 1..35 as num>
              	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
        	</#list>
      	</ul>
    </fieldset>
    <div class="kong20"></div>
    <fieldset class="bluefieldset">
        <legend class="cdownjl"><span class="bluec1">后区胆码</span>&nbsp;<span class="grayc1">至多选择1个</span></legend>
    	<ul class="ssqball" id="blue_area_box_galls">
        	<#list 1..12 as num>
            	<li><a href="#" onclick="return false;" _name="blue_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
        	</#list>
      	</ul>
    </fieldset>
    <div class="kong20"></div>
    <fieldset class="bluefieldset">
        <legend class="cdownjl"><span class="bluec1">后区拖码</span>&nbsp;<span class="grayc1">拖码个数加胆码个数要大于或等于2</span></legend>
        <ul class="ssqball" id="blue_area_box">
        	<#list 1..12 as num>
       			<li><a href="#" onclick="return false;" _name="blue_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
            </#list>
        </ul>
 	</fieldset>
</div>

<#include '/WEB-INF/content/common/count.ftl' />
<div class="kong10"></div>

<#include 'editNew-mode2.ftl' />
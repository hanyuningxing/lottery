<div id="touzzhuqu">
	<div class="left">
		<fieldset class="redfieldset">
      		<legend class="cdownjl"><span class="redc1">前区</span>&nbsp;<span class="grayc1">从35个数字中至少选择5个数字</span></legend>
      		<ul class="ssqball" id="red_area_box">
	            <#list 1..35 as num>
	            	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            </#list>
      		</ul>
      		<div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
        		<div class="floatleft">
              		<select name="red_select" id="red_select">
		                <#list 5..18 as num>
		                  <option value="${num}">${num}</option>
		                </#list>
              		</select>
        		</div>
        		<div class="floatleft left10"><a href="#" onclick="randomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/dlt_bt_jixuanred.gif"/>"/></a>&nbsp;<a href="#" onclick="clearRandomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
      		</div>
      	</fieldset>
    </div>
    <div class="right">
      	<fieldset class="bluefieldset" style="width:185px;">
      		<legend class="cdownjl"><span class="bluec1">后区</span>&nbsp;<span class="grayc1">从12个数字中选2个</span></legend>
      		<ul class="ssqball" id="blue_area_box">
         		<#list 1..12 as num>
              		<li><a href="#" onclick="return false;" _name="blue_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
        		</#list>
      		</ul>
      		<div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
            	<div class="floatleft">
              		<select name="blue_select" id="blue_select">
                 		<#list 2..12 as num>
                  			<option value="${num}">${num}</option>
                		</#list>
              		</select>
            	</div>
        		<div class="floatleft left10"><a href="#" onclick="randomSelectBall('blue');return false;"><img src="<@c.url value="/pages/images/dlt_bt_jixuanblue.gif"/>"/></a>&nbsp;<a href="#" onclick="clearRandomSelectBall('blue');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
      		</div>
      	</fieldset>
	</div>
</div>

<#include '/WEB-INF/content/common/count.ftl' />
<div class="kong10"></div>

<#include 'editNew-mode1.ftl' />
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>
<@override name="content.select">
	<#if playType==1>
		<div id="touzzhuqu">
        	<fieldset class="redfieldset">
        		<legend class="cdownjl"><span class="redc1">选号胆码</span>&nbsp;<span class="grayc1">至少选择1个,至多6个</span></legend>
	          	<ul class="ssqball" id="red_box_galls">
	            	<#list 1..30 as num>
	                	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
        	</fieldset>
	        <div class="kong20"></div>
	        <fieldset class="redfieldset">
	        	<legend class="cdownjl"><span class="redc1">选号拖码</span>&nbsp;<span class="grayc1">拖码个数加胆码个数要大于7</span></legend>
	          	<ul class="ssqball" id="red_box">
	            	<#list 1..30 as num>
	                	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
	        </fieldset>
		</div>
	<#elseif playType==0>
		<div id="touzzhuqu">
          		<fieldset class="redfieldset">
          			<legend class="cdownjl"><span class="redc1">选号区</span>&nbsp;<span class="grayc1">至少选择7个选号</span></legend>
          			<ul class="ssqball" id="red_box">
            			<#list 1..30 as num>
                  			<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
            			</#list>
          			</ul>
          			<div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
            			<div class="floatleft">
              				<select name="red_select" id="red_select">
                				<#list 7..15 as num>
                 		 			<option value="${num}">${num}</option>
                				</#list>
              				</select>
            			</div>
            			<div class="floatleft left10"><a href="#" onclick="randomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_jixuanhaoma.gif"/>"/></a>&nbsp;<a href="#" onclick="clearRandomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
          			</div>
          		</fieldset>
		</div>
	</#if>
	
    <#include '/WEB-INF/content/common/count.ftl' />
    
	<div class="kong10"></div>
	<div id="jixuanqu">
	<div class="left">
		<div class="left1" style="width:260px;">
			<ul id="createForm_select_content" class="texk haomaqu" style=" width:260px;height:90px;list-style:none;"></ul>
			<div class="kong10"></div>
			<div class="xs_menu" style="padding-left:40px;" id="clear">
				<ul>
	            	<li><a href="#" onclick="clearAll();return false;">清空所有号码</a></li>
				</ul>
			</div>
		</div>
        <div class="floatleft" style="margin-left:15px;">
      		<ul class="jixuan_menu_nowidth">
            	<li><a href="#" onclick="randomSelect(1);return false;">机选一注</a></li>
              	<li><a href="#" onclick="randomSelect(3);return false;">机选三注</a></li>
              	<li><a href="#" onclick="randomSelect(5);return false;">机选五注</a></li>
              	<li><a href="#" onclick="randomSelect(10);return false;">机选十注</a></li>
     		</ul>
		</div>
		<div class=" floatrig">
        	<ul class="jixuan_menu_nowidth">
            	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(1)<#else>danRandomSelect(1)</#if>;return false;">定胆机选一注</a></li>
              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(3)<#else>danRandomSelect(3)</#if>;return false;">定胆机选三注</a></li>
              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(5)<#else>danRandomSelect(5)</#if>;return false;">定胆机选五注</a></li>
              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(10)<#else>danRandomSelect(10)</#if>;return false;">定胆机选十注</a></li>
			</ul>
		</div>
	</div>
	<#assign luckLotteryNum='6' />
	<#include '/WEB-INF/content/common/luck.ftl' />
</div>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>
<@override name="content.select">
	<#if playType==2>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="trw">
          <td width="100" class="lineh20" height="115">
          	<span class=" font14char boldchar rebchar">第一步</span><br />
            <span class="rebchar">红球方案列表</span>
          </td>
          <td align="left" class="lineh20" >
          	标准格式样本：01,02,03,04,05,06<br />
            <textarea id="shrink_content" cols="45" rows="3" class="texk" style="width:411px; height:85px;" autocomplete="off">${(shrinkBean.content)!}</textarea>
          </td>
        </tr> 
        <tr class="trw">
          <td width="100" height="60" class="lineh20" style="border-top:1px solid #E9EEF5;">
          	<span class=" font14char boldchar rebchar">第二步</span><br />
            <span class="rebchar">选择蓝球号码</span>
          </td>
          <td align="left" class="lineh20" style="border-top:1px solid #E9EEF5;">
            <div id="ssqtz" style="margin:0px;">
          	  <ul class="ssqball" id="shrink_blue_box">
				<#list 1..16 as num>
	      			<li><a href="javascript:void(0);" onclick="return false;" _name="blue_ball">${num?string('00')}</a></li>
	         	</#list>
	          </ul>
            </div>
		  </td>
        </tr>
      </table>
	<#elseif playType==1>
		<div id="touzzhuqu">
        	<fieldset class="redfieldset">
        		<legend class="cdownjl"><span class="redc1">红球胆码</span>&nbsp;<span class="grayc1">至少选择1个,至多5个</span></legend>
	          	<ul class="ssqball" id="red_box_galls">
	            	<#list 1..33 as num>
	                	<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
        	</fieldset>
	        <div class="kong20"></div>
	        <fieldset class="redfieldset">
	        	<legend class="cdownjl"><span class="redc1">红球拖码</span>&nbsp;<span class="grayc1">拖码个数加胆码个数要大于6</span></legend>
	          	<ul class="ssqball" id="red_box">
	            	<#list 1..33 as num>
	                	<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
	        </fieldset>
	        <div class="kong20"></div>
	        <fieldset class="bluefieldset">
	        	<legend class="cdownjl"><span class="bluec1">蓝球区</span>&nbsp;<span class="grayc1">至少选择1个蓝球</span></legend>
	          	<ul class="ssqball" id="blue_box">
	            	<#list 1..16 as num>
	                  	<li><a href="javascript:void(0);" onclick="return false;" _name="blue_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
	        </fieldset>
		</div>
	<#elseif playType==0>
		<div id="touzzhuqu">
        	<div class="left">
          		<fieldset class="redfieldset">
          			<legend class="cdownjl"><span class="redc1">红球区</span>&nbsp;<span class="grayc1">至少选择6个红球</span></legend>
          			<ul class="ssqball" id="red_box">
            			<#list 1..33 as num>
                  			<li><a href="javascript:void(0);" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
            			</#list>
          			</ul>
          			<div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
            			<div class="floatleft">
              				<select name="red_select" id="red_select">
                				<#list 6..20 as num>
                 		 			<option value="${num}">${num}</option>
                				</#list>
              				</select>
            			</div>
            			<div class="floatleft left10"><a href="javascript:void(0);" onclick="randomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_jixuanred.gif"/>"/></a>&nbsp;<a href="javascript:void(0);" onclick="clearRandomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
          			</div>
          		</fieldset>
        	</div>
        	<div class="right">
          		<fieldset class="bluefieldset">
          			<legend class="cdownjl"><span class="bluec1">蓝球区</span>&nbsp;<span class="grayc1">至少选择1个蓝球</span></legend>
      				<ul class="ssqball" id="blue_box">
         				<#list 1..16 as num>
              				<li><a href="javascript:void(0);" onclick="return false;" _name="blue_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
        				</#list>
      				</ul>
      				<div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
        				<div class="floatleft">
          					<select name="blue_select" id="blue_select">
             					<#list 1..16 as num>
              						<option value="${num}">${num}</option>
            					</#list>
          					</select>
        				</div>
        				<div class="floatleft left10"><a href="javascript:void(0);" onclick="randomSelectBall('blue');return false;"><img src="<@c.url value="/pages/images/bt_jixuanblue.gif"/>"/></a>&nbsp;<a href="javascript:void(0);" onclick="clearRandomSelectBall('blue');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
      				</div>
          		</fieldset>
        	</div>
		</div>
	</#if>
	
    <#include '/WEB-INF/content/common/count.ftl' />
    
	<div class="kong10"></div>
    <#if playType==2>
    	<div id="jixuanqu_2">
	        <div class="left">
	        	<div class="left1">
	          		<ul id="createForm_select_content" class="texk haomaqu" style="width:100%;height:100px;"></ul>
	          	</div>
	          	<div class="floatleft" style="height:105px;padding-top:40px;">
	            	<ul class="jixuan_menu">
		              	<li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
	            	</ul>
				</div>
	          	<div class="kong10"></div>
			</div>
		</div>
    <#else>
		<div id="jixuanqu">
	        <div class="left">
	        	<div class="left1">
	          		<ul id="createForm_select_content" class="texk haomaqu"></ul>
	          	</div>
	          	<div class="floatleft">
	            	<ul class="jixuan_menu">
		              	<li><a href="javascript:void(0);" onclick="randomSelect(1);return false;">机选一注</a></li>
		              	<li><a href="javascript:void(0);" onclick="randomSelect(5);return false;">机选五注</a></li>
		              	<li><a href="javascript:void(0);" onclick="randomSelect(8);return false;">机选八注</a></li>
		              	<li><a href="javascript:void(0);" onclick="randomSelect(10);return false;">机选十注</a></li>
		              	<li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
	            	</ul>
				</div>
	          	<div class="kong10"></div>
			</div>
		</div>
    </#if>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
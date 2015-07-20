<div class="cleanboth" >
	<div id="touzzhuqu">
		<div class="left" style="width:665px;">
        	<fieldset class="redfieldset">
            	<legend class="cdownjl"><span class="redc1">12选2</span>&nbsp;<span class="grayc1">从12个数字中至少选择2个数字</span></legend>
            	<div class="all10px">
              		<table width="100%" border="0" cellspacing="0" cellpadding="0">
                		<tr>
                  			<td>
			                    <ul class="ssqball" id="select12to2_area_box">
			                        <#list 1..12 as num>
						                  <li><a href="#" onclick="return false;" _name="12to2_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						            </#list>
			                    </ul>
			               	</td>
	                  		<td>
	                     		<ul class="qqjo_bt" id="select12to2_choose_box">
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      		<li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
	                    		</ul>
	                 		</td>
                		</tr>
              		</table>
            	</div>
 			</fieldset>
		</div>
	</div>
</div>

<#include '/WEB-INF/content/common/count.ftl' />
<div class="kong10"></div>

<#include 'editNew-mode2.ftl' />
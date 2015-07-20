 <script> 
 window.onload=function(){ 
	$('input[type="checkbox"][name="Zodiac"]').attr("checked","");
	$('input[type="checkbox"][name="Season"]').attr("checked","");
	$('input[type="checkbox"][name="Azimuth"]').attr("checked","");
	}
 </script> 
<@override name="content.play_caption">
	<div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
  	  	   <#if salesPlayType='Haocai1'>玩法说明：从号码01～36中选择1个号码为一注，选择1个以上号码为复式投注，单注选号与开奖号码相同即中奖46元。</#if>
  	  	   <#if salesPlayType='Zodiac'>玩法说明：从十二生肖中选择1个生肖为一注，选择1个以上生肖为复式投注，所选生肖与开奖号码所属生肖相同即中奖15元。</#if>
     	   <#if salesPlayType='Season'>玩法说明：从春夏秋冬4个季节中选择1个季节为一注，选择1个以上季节为复式投注，所选季节与开奖号码所属季节相同即中奖5元。</#if>
           <#if salesPlayType='Azimuth'>玩法说明：从东南西北4个方位中选择1个方位为一注，选择1个以上方位为复式投注，所选方位与开奖号码所属方位相同即中奖5元。</#if>
    </div>
</@override>
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/compute.js"/>"></script>
</@override> 
<@override name="content.select">
	<div id="touzzhuqu">
		<div class="left" style="width:665px;">
			<fieldset class="redfieldset">
             	<legend class="cdownjl"><span class="redc1">${salesPlayType.typeName!}复式投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
             	<div class="all10px" id="area_box">
	             	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		            	<#if salesPlayType=="Haocai1">
		            		<tbody>
								<tr id="Haocai1">
			                  		<td width="55" height="30" align="center" valign="middle"><div class=" top10px boldchar">第一区：</div></td>
			                  		<td width="442">
			                  			<ul class="ssqball">
				                         	<#list 1..12 as num>
				                         		<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
				                      		</#list>
		                    	    	</ul>	
		                    	      </td>
			                  		<td width="130" rowspan="5">
									  <div style="width:128px;" class="hcq_wk">
			                   			<ul class="hcqqjo_bt">
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="all">全</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="big">大</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="small">小</a></li>                  		
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="odd">单</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="even">双</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="zeroRoad">0路</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="oneRoad">1路</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="twoRoad">2路</a></li>
			                      			<li style="width:29px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
	                    		       </ul>
								     </div>
								     </td>
			                	</tr>
								
							    <tr>
			                  		<td width="55" height="30" align="center" valign="middle"><div class=" top10px boldchar">第二区：</div></td>
			                  		<td>
			                  			<ul class="ssqball">
			                         		 <#list 13..24 as num>
			                         	    	<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
			                      	 		 </#list>
				                    	</ul>
				                      </td>
		                  		  </tr>
								 
								<tr>
			                  		<td width="55" height="30" align="center" valign="middle"><div class=" top10px boldchar">第三区：</div></td>
			                  		<td>
			                  			<ul class="ssqball">
				                         	 <#list 25..36 as num>
			                         	    	<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
			                      	 		 </#list>
				                    	</ul>
				                    </td>
		                  		  </tr>
								
								
						  <#elseif salesPlayType=="Zodiac">
						  <tr>
							<td width="63">&nbsp;</td>
							<td>
							  <div class="hc1_sx1" id="${salesPlayType}_class_1">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">01</div>
									<div class="hc1_sx1_hm hc1_hm4jg">13</div>
									<div class="hc1_sx1_hm">25</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_shu">
								     <div class="sxname">
								     <input id="${salesPlayType}_1" name="${salesPlayType}" type="checkbox" value="1"/>鼠
								     </div>
								 </div>				  
							  </div>
							  </td>
							<td width="16">&nbsp;</td>
							<td>
								<div class="hc1_sx1" id="${salesPlayType}_class_2">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">02</div>
									<div class="hc1_sx1_hm hc1_hm4jg">14</div>
									<div class="hc1_sx1_hm">26</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_niu">
								      <div class="sxname">
								     <input id="${salesPlayType}_2" name="${salesPlayType}" type="checkbox" value="2"/>牛
								     </div>
								       
								 </div>				  
							  </div>
							 </td>
							<td width="16">&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_3">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">03</div>
									<div class="hc1_sx1_hm hc1_hm4jg">15</div>
									<div class="hc1_sx1_hm">27</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_hu">
							  	    <div class="sxname">
								     <input id="${salesPlayType}_3" name="${salesPlayType}" type="checkbox" value="3"/>
								       虎</div>
								 </div>				  
							  </div>				</td>
							<td width="16">&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_4">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">04</div>
									<div class="hc1_sx1_hm hc1_hm4jg">16</div>
									<div class="hc1_sx1_hm">28</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_tu">
								     <div class="sxname">
								     <input id="${salesPlayType}_4" name="${salesPlayType}" type="checkbox" value="4"/>兔
								     </div>
								 </div>				  
							  </div>
							  </td>
							<td width="16">&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_5">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">05</div>
									<div class="hc1_sx1_hm hc1_hm4jg">17</div>
									<div class="hc1_sx1_hm">29</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_long">
							  	    <div class="sxname">
							  	 	 <input id="${salesPlayType}_5" name="${salesPlayType}" type="checkbox" value="5"/>龙
							  	 	 </div>
								 </div>				  
							  </div>
							  </td>
							<td width="16">&nbsp;</td>
							<td>
							  <div class="hc1_sx1" id="${salesPlayType}_class_6">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">06</div>
									<div class="hc1_sx1_hm hc1_hm4jg">18</div>
									<div class="hc1_sx1_hm">30</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_she">
							  	    <div class="sxname">
							  	 	<input id="${salesPlayType}_6" name="${salesPlayType}" type="checkbox" value="6"/>蛇
								 </div>	
								 </div>				  
							  </div>
							  </td>
						  </tr>
						
						  <tr height="7">
							<td colspan="12">
							  <div class="hc1_xuxian"></div>
							</td>
						  </tr>
						  
						  <tr>
							<td>&nbsp;</td>
							<td>
							  <div class="hc1_sx1" id="${salesPlayType}_class_7">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">07</div>
									<div class="hc1_sx1_hm hc1_hm4jg">19</div>
									<div class="hc1_sx1_hm">31</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_ma">
								     <div class="sxname">
								     <input id="${salesPlayType}_7" name="${salesPlayType}" type="checkbox" value="7"/>马
								     </div>
								 </div>				  
							  </div>
							  </td>
							<td>&nbsp;</td>
							<td>
								<div class="hc1_sx1" id="${salesPlayType}_class_8">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">08</div>
									<div class="hc1_sx1_hm hc1_hm4jg">20</div>
									<div class="hc1_sx1_hm">32</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_yang">
									<div class="sxname">
								     <input id="${salesPlayType}_8" name="${salesPlayType}" type="checkbox" value="8"/>羊
								     </div>
								  </div>				  
							  </div>
							  </td>
							<td>&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_9">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">09</div>
									<div class="hc1_sx1_hm hc1_hm4jg">21</div>
									<div class="hc1_sx1_hm">33</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_hou">
									<div class="sxname">
								     <input id="${salesPlayType}_9" name="${salesPlayType}" type="checkbox" value="9"/>猴
								     </div>								 
								  </div>				  
							  </div>
							  </td>
							<td>&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_10">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">10</div>
									<div class="hc1_sx1_hm hc1_hm4jg">22</div>
									<div class="hc1_sx1_hm">34</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_ji">
								     <div class="sxname">
								     <input id="${salesPlayType}_10" name="${salesPlayType}" type="checkbox" value="10"/>鸡
								     </div>
								 </div>				  
							  </div>
							  </td>
							<td>&nbsp;</td>
							<td>
							   <div class="hc1_sx1" id="${salesPlayType}_class_11">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">11</div>
									<div class="hc1_sx1_hm hc1_hm4jg">23</div>
									<div class="hc1_sx1_hm">35</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_gou">
     								 <div class="sxname">
								     <input id="${salesPlayType}_11" name="${salesPlayType}" type="checkbox" value="11"/>狗
								     </div>								 
								     </div>				  
							  </div>				
							  </td>
							<td>&nbsp;</td>
							<td>
							  <div class="hc1_sx1" id="${salesPlayType}_class_12">
							     <div class="hc1_sx1left">
								    <div class="hc1_sx1_hm hc1_hm4jg">12</div>
									<div class="hc1_sx1_hm hc1_hm4jg">24</div>
									<div class="hc1_sx1_hm">36</div>
								 </div>				  
							  	 <div class="hc1_sx1right hc1_sx1_zhu">
 									 <div class="sxname">
								     <input id="${salesPlayType}_12" name="${salesPlayType}" type="checkbox" value="12"/>猪
								     </div>									
								  </div>				  
							  </div>				
							  </td>
						  </tr>
						  
					   <#elseif salesPlayType=="Season">
							<tr>
							<td width="63">&nbsp;</td>
							<td>
							  <div class="hc1_jijie" id="${salesPlayType}_class_1">
							     <div class="hc1_jj_ct">
								    <input id="${salesPlayType}_1" name="${salesPlayType}" type="checkbox"  class="jjxzk"  value="1"/>
								 </div>
								 <div class="jjhmxk">
								    <div class="jjhm_an jjhm_jg">01</div>
									<div class="jjhm_an jjhm_jg">02</div>
									<div class="jjhm_an">03</div>
									<div class="jjhm_an jjhm_jg">04</div>
									<div class="jjhm_an jjhm_jg">05</div>
									<div class="jjhm_an">06</div>
									<div class="jjhm_an jjhm_jg">07</div>
									<div class="jjhm_an jjhm_jg">08</div>
									<div class="jjhm_an">09</div>
								 </div>	  
							  </div>				
							</td>
							<td width="31">&nbsp;</td>
							<td>
							    <div class="hc1_jijie" id="${salesPlayType}_class_2">
							     <div class="hc1_jj_xt">
								    <input id="${salesPlayType}_2" name="${salesPlayType}" type="checkbox"  class="jjxzk"  value="2"/>
								 </div>
								 <div class="jjhmxk">
								    <div class="jjhm_an jjhm_jg">10</div>
									<div class="jjhm_an jjhm_jg">11</div>
									<div class="jjhm_an">12</div>
									<div class="jjhm_an jjhm_jg">13</div>
									<div class="jjhm_an jjhm_jg">14</div>
									<div class="jjhm_an">15</div>
									<div class="jjhm_an jjhm_jg">16</div>
									<div class="jjhm_an jjhm_jg">17</div>
									<div class="jjhm_an">18</div>
								 </div>	  
							  </div>
							</td>
							<td width="31">&nbsp;</td>
							<td>
							   <div class="hc1_jijie" id="${salesPlayType}_class_3">
							     <div class="hc1_jj_qt">
								     <input id="${salesPlayType}_3" name="${salesPlayType}" type="checkbox"  class="jjxzk"  value="3"/>
								 </div>
								 <div class="jjhmxk">
								    <div class="jjhm_an jjhm_jg">19</div>
									<div class="jjhm_an jjhm_jg">20</div>
									<div class="jjhm_an">21</div>
									<div class="jjhm_an jjhm_jg">22</div>
									<div class="jjhm_an jjhm_jg">23</div>
									<div class="jjhm_an">24</div>
									<div class="jjhm_an jjhm_jg">25</div>
									<div class="jjhm_an jjhm_jg">26</div>
									<div class="jjhm_an">27</div>
								 </div>	  
							  </div>
							</td>
							<td width="31">&nbsp;</td>
							<td>
							   <div class="hc1_jijie" id="${salesPlayType}_class_4">
							     <div class="hc1_jj_dt">
								     <input id="${salesPlayType}_4" name="${salesPlayType}" type="checkbox"  class="jjxzk"  value="4"/>
								 </div>
								 <div class="jjhmxk">
								    <div class="jjhm_an jjhm_jg">28</div>
									<div class="jjhm_an jjhm_jg">29</div>
									<div class="jjhm_an">30</div>
									<div class="jjhm_an jjhm_jg">31</div>
									<div class="jjhm_an jjhm_jg">32</div>
									<div class="jjhm_an">33</div>
									<div class="jjhm_an jjhm_jg">34</div>
									<div class="jjhm_an jjhm_jg">35</div>
									<div class="jjhm_an">36</div>
								 </div>	  
							  </div>
							</td>
							
						  </tr>
						  
						<#elseif salesPlayType=="Azimuth">
						<tr>
						<td width="63">&nbsp;</td>
						<td>
						  <div class="hc1_jijie" id="${salesPlayType}_class_1">
						     <div class="hc1_fw_d">
							    <input id="${salesPlayType}_1" name="${salesPlayType}" type="checkbox"  class="fwxzk"  value="1"/>
							 </div>
							 <div class="jjhmxk">
							    <div class="jjhm_an jjhm_jg">01</div>
								<div class="jjhm_an jjhm_jg">03</div>
								<div class="jjhm_an">05</div>
								<div class="jjhm_an jjhm_jg">07</div>
								<div class="jjhm_an jjhm_jg">09</div>
								<div class="jjhm_an">11</div>
								<div class="jjhm_an jjhm_jg">13</div>
								<div class="jjhm_an jjhm_jg">15</div>
								<div class="jjhm_an">17</div>
							 </div>	  
						  </div>				
						</td>
						<td width="31">&nbsp;</td>
						<td>
						    <div class="hc1_jijie" id="${salesPlayType}_class_2">
						     <div class="hc1_fw_n">
							    <input id="${salesPlayType}_2" name="${salesPlayType}" type="checkbox"   class="fwxzk"  value="2"/>
							 </div>
							 <div class="jjhmxk">
							    <div class="jjhm_an jjhm_jg">02</div>
								<div class="jjhm_an jjhm_jg">04</div>
								<div class="jjhm_an">06</div>
								<div class="jjhm_an jjhm_jg">08</div>
								<div class="jjhm_an jjhm_jg">10</div>
								<div class="jjhm_an">12</div>
								<div class="jjhm_an jjhm_jg">14</div>
								<div class="jjhm_an jjhm_jg">16</div>
								<div class="jjhm_an">18</div>
							 </div>	  
						  </div>
						</td>
						<td width="31">&nbsp;</td>
						<td>
						   <div class="hc1_jijie" id="${salesPlayType}_class_3">
						     <div class="hc1_fw_x">
							    <input id="${salesPlayType}_3" name="${salesPlayType}" type="checkbox"   class="fwxzk"  value="3"/>
							 </div>
							 <div class="jjhmxk">
							    <div class="jjhm_an jjhm_jg">19</div>
								<div class="jjhm_an jjhm_jg">21</div>
								<div class="jjhm_an">23</div>
								<div class="jjhm_an jjhm_jg">25</div>
								<div class="jjhm_an jjhm_jg">27</div>
								<div class="jjhm_an">29</div>
								<div class="jjhm_an jjhm_jg">31</div>
								<div class="jjhm_an jjhm_jg">33</div>
								<div class="jjhm_an">35</div>
							 </div>	  
						  </div>
						</td>
						<td width="31">&nbsp;</td>
						<td>
						   <div class="hc1_jijie" id="${salesPlayType}_class_4">
						     <div class="hc1_fw_b">
							    <input id="${salesPlayType}_4" name="${salesPlayType}" type="checkbox"  class="fwxzk"  value="4"/>
							 </div>
							 <div class="jjhmxk">
							    <div class="jjhm_an jjhm_jg">20</div>
								<div class="jjhm_an jjhm_jg">22</div>
								<div class="jjhm_an">24</div>
								<div class="jjhm_an jjhm_jg">26</div>
								<div class="jjhm_an jjhm_jg">28</div>
								<div class="jjhm_an">30</div>
								<div class="jjhm_an jjhm_jg">32</div>
								<div class="jjhm_an jjhm_jg">34</div>
								<div class="jjhm_an">36</div>
							 </div>	  
						  </div>
						</td>
						
					  </tr>
						</#if>
					</table>
				</div>
			</fieldset>
		</div>
	</div>

	<#include '/WEB-INF/content/common/count.ftl' />
	<div class="kong10"></div>
		<div id="jixuanqu">
			<div class="left">
	          	<div class="left1">
					<ul id="createForm_select_content" class="texk haomaqu"></ul>
	          	</div>
	          	<#if salesPlayType=="Haocai1">
	          	<div class="floatleft">
	            	<ul class="jixuan_menu">
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(1);return false;">机选一注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(5);return false;">机选五注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(8);return false;">机选八注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(10);return false;">机选十注</a></li>
	              		    <li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
	           	 	</ul>
	          	</div>
	          	<#else>
					<div class="xs_menu" style="padding-left:40px;" id="clear">
						<ul>
			            	<li><a href="#" onclick="clearAll();return false;">清空所有号码</a></li>
						</ul>
					</div>
	          	</#if>
	          	<div class="kong10"></div>
	        </div>
		</div>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
 
 
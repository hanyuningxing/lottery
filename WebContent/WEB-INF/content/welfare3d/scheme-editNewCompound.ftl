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
<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override> 
<@override name="content.select">
	<div id="touzzhuqu">
		<div class="left" style="width:665px;">
			<fieldset class="redfieldset">
             	<legend class="cdownjl"><span class="redc1">${salesPlayType.typeName!}复式投注区</span>&nbsp;<span class="grayc1"><#if salesPlayType='Group3'>每行数字至少选择2个数字<#elseif salesPlayType='Group6'>每行数字至少选择3个数字<#else>每行数字至少选择1个数字</#if></span></legend>
             	<div class="all10px" id="area_box">
	             	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		            	<#if salesPlayType=="Direct">
					     	<#list 1..3 as n>
					        	<tr id="_w3dball_${n}">
			                  		<td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>百位<#elseif n==2>十位<#elseif n==3>个位</#if></div></td>
			                  		<td>
			                  			<ul class="ssqball">
				                      		<#list 0..9 as num>
				                         		<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}${n}">${num}</a></li>
				                      		</#list>
				                    	</ul>
			                 		</td>
			                  		<td>
			                   			<ul class="qqjo_bt top10px" >
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
			                     			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
			                    		</ul>
			                  		</td>
			                	</tr>
					     	</#list>
						<#elseif salesPlayType=="DirectSum" || salesPlayType=="GroupSum">
							<tr id="${salesPlayType}Tr">
			                  	<td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
			                  	<td>
			                  		<ul class="ssqball">
			                  			<#if salesPlayType=="GroupSum">
		                                 	<#list 1..26 as num>
		                                   		<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                                 	</#list>
		                         		<#else>
		                            		<#list 0..27 as num>
		                                   		<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
		                                 	</#list>
		                         		</#if>
			                    	</ul>
			                 	</td>
							</tr>
						<#else>
							<tr id="${salesPlayType}Tr">
			                  	<td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
			                  	<td>
			                  		<ul class="ssqball">
			                  			<#if salesPlayType=="Group3Kuadu">
				                  			<#list 1..9 as num>
				                  				<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
				                  			</#list>
		                         		<#elseif salesPlayType=="Group6Kuadu">
		                             		<#list 2..9 as num>
				                  				<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
				                  			</#list>
		                         		<#else>
		                           			<#list 0..9 as num>
				                  				<li><a href="javascript:void(0);" onclick="return false;" _name="${salesPlayType}">${num}</a></li>
				                  			</#list>
		                         		</#if>
			                    	</ul>
			                 	</td>
			                  	<td>
			                  		<#if salesPlayType=="Group3"||salesPlayType=="Group6">
			                     		<ul class="qqjo_bt top10px" >
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
			                      			<li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
			                     		</ul>
			                  		</#if>
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

	<#if salesPlayType=="Group3" || salesPlayType=="Group6" || salesPlayType=="Direct">
		<div id="jixuanqu">
			<div class="left">
	          	<div class="left1">
					<ul id="createForm_select_content" class="texk haomaqu"></ul>
	          	</div>
	          	<div class="floatleft">
	            	<ul class="jixuan_menu">
	             		<#if salesPlayType=="Group3">
	                  		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(1);return false;">机选二注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(3);return false;">机选六注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(4);return false;">机选八注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(5);return false;">机选十注</a></li>
	              		<#else>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(1);return false;">机选一注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(5);return false;">机选五注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(8);return false;">机选八注</a></li>
		              		<li><a href="javascript:void(0);" onclick="${salesPlayType}RandomSelect(10);return false;">机选十注</a></li>
	              		</#if>
	              		<li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
	           	 	</ul>
	          	</div>
	          	<div class="kong10"></div>
	        </div>
			<#if salesPlayType=="Direct">
	        	<#assign luckLotteryNum='2' />
	         	<#include '/WEB-INF/content/common/luck.ftl' />
			</#if>
		</div>
	<#else>
      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          	<td align="center"><ul id="createForm_select_content" class="texk haomaqu1" style=" width:460px;height:90px;list-style:none;"></ul></td>
	        </tr>
	        <tr>
	          	<td height="30" align="center"><input type="button" value="清空号码" onclick="clearAll();return false;"/></td>
	        </tr>
      	</table>
      	<div class="kong10"></div>
	</#if>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
 
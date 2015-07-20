<#include "../banner.ftl"/>

<div class="kong5"></div>
<div class="topbgty">
	<table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogotcssc"></div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">时时彩走势图表</div>
			<div class="kong5"></div>
			<div style="width:850px;">			
			<ul class="navt_menu">
			<li><a href="<@c.url value= "/ssc/analyse!generalAward.action"/>" <#if menu='generalAward'>class="now"</#if> >二星综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="<@c.url value= "/ssc/analyse!lyel.action"/>" <#if menu='lyel'>class="now"</#if>>二星012路走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!samSung.action"/>" <#if menu='samSung'>class="now"</#if>>三星综合走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!sxlyel.action"/>" <#if menu='sxlyel'>class="now"</#if>>三星012路走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!dxds.action"/>" <#if menu='dxds'>class="now"</#if>>大小单双走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!ssczs.action?type=wxzs"/>" <#if menu='wxzs'>class="now"</#if>>五星走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!ssczs.action?type=exhz"/>" <#if menu='exhz'>class="now"</#if>>二星和值走势图</a></li>		
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!ssczs.action?type=sxhz"/>" <#if menu='sxhz'>class="now"</#if>>三星和值走势图</a></li>		
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!ssczs.action?type=sxjo"/>" <#if menu='sxjo'>class="now"</#if>>三星奇偶走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!ssczs.action?type=wnzs"/>" <#if menu='wnzs'>class="now"</#if>>万能六码走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx1"/>" <#if type='zx1'>class="now"</#if>>一星直选遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx2"/>" <#if type='zx2'>class="now"</#if>>二星直选遗漏</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!hezhi.action?type=exhzyl"/>" <#if type='exhzyl'>class="now"</#if>>二星和值遗漏</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx3"/>" <#if type='zx3'>class="now"</#if>>三星直选遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/ssc/analyse!hezhi.action?type=sxhzyl"/>" <#if type='sxhzyl'>class="now"</#if>>三星和值遗漏</a></li>
			</ul>	
			</div>	
			</div>
			</td>
		  </tr>
	</table>
	<div class="kong5"></div>
       <div id="div_line"></div>
</div>

	<#if menu=='generalAward'||menu=='lyel'||menu=='samSung'||menu='sxlyel'||menu='dxds'>
		       <div class="tybanbg">
		        
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示50期" onclick="chgDisplay(50);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示100期" onclick="chgDisplay(100);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" />
			    </div>
			    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startIssueNo" size="12" /> -  结束期数<input class="inputh18" id="endIssueNo" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>
			   </div>
		<#elseif menu=='wxzs'||menu=='exhz'||menu=='sxhz'||menu=='sxjo'||menu=='wnzs'>
			<div class="tybanbg1">
			        <div class=" zstopleft0" >
				       ${lotteryType.lotteryName}走势图
				    </div>
				    <form action="<@c.url value= "/ssc/analyse!ssczs.action"/>" method="post">
				    <input type="hidden" name="type" value="${type }" id="type">		
				    <input type="hidden" name="count" value="50" id="count">
				    <div class=" zstopleft1" id="size_div"> 显示：
				      <input id="size_30" type="button" class="btnow" value="显示50期" onclick="chgDisplay(50);" /> 
					  <input id="size_50" type="button" class="btgray" value="显示100期" onclick="chgDisplay(100);" /> 
					  <input id="size_80" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" />
				    </div>
				    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
				    </div>
			    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>				 
					</form>
		   </div>
		<#elseif type=='zx1'||type=='zx2'||type=='zx3'||type=='gx2'>
			<div class="tybanbg1">
			        <div class=" zstopleft0" >
				       ${lotteryType.lotteryName}走势图
				    </div>
				    <form action="<@c.url value= "/ssc/analyse!zhixuan.action"/>" method="post">
				    <input type="hidden" name="type" value="${type }" id="type">		
				    <input type="hidden" name="count" value="50" id="count">
				    <div class=" zstopleft1" id="size_div"> 显示：
				      <input id="size_30" type="button" class="btnow" value="显示100期" onclick="chgDisplay(100);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示500期" onclick="chgDisplay(500);" />
				  <input id="size_80" type="button" class="btgray" value="显示1000期" onclick="chgDisplay(1000);" />				
			      <input id="size_80" type="button" class="btgray" value="全部" onclick="chgDisplay(0);" />	
				    </div>
				    <!-- <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
				    </div>
			    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>				 
					-->
					</form>
		   </div>
		<#elseif type=='sxhzyl'||type=='exhzyl'>
			<div class="tybanbg1">
			        <div class=" zstopleft0" >
				       ${lotteryType.lotteryName}走势图
				    </div>
				    <form action="<@c.url value= "/ssc/analyse!hezhi.action"/>" method="post">
				    <input type="hidden" name="type" value="${type }" id="type">		
				    <input type="hidden" name="count" value="50" id="count">
				    <div class=" zstopleft1" id="size_div"> 显示：
				      <input id="size_30" type="button" class="btnow" value="显示100期" onclick="chgDisplay(100);" /> 
					  <input id="size_50" type="button" class="btgray" value="显示200期" onclick="chgDisplay(200);" /> 
					  <input id="size_80" type="button" class="btgray" value="显示500期" onclick="chgDisplay(500);" />
					  <input id="size_80" type="button" class="btgray" value="显示1000期" onclick="chgDisplay(1000);" />				
				      <input id="size_80" type="button" class="btgray" value="全部" onclick="chgDisplay(0);" />	
				    </div>
				    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
				    </div>
			    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>				 
					-->
					</form>
		   </div>
		   
		</#if>
 	   <div class="kong5"></div>
       <div id="MISS_EL">正在加载......</div>
       <div id="div_line"></div>
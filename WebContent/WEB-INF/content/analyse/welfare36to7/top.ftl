
<div class="kong5"></div>
<div class="topbgty">
	<table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogohcy"></div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">好彩一走势图表</div>
			<div class="kong5"></div>
			<ul class="navt_menu">
			
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action"/>" <#if type='index'>class="now"</#if>>综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=szzst"/>" <#if type='szzst'>class="now"</#if>>数字走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=sxzst"/>" <#if type='sxzst'>class="now"</#if>>生肖走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=jjfwzst"/>" <#if type='jjfwzst'>class="now"</#if>>季节方位走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=wszst"/>" <#if type='wszst'>class="now"</#if>>尾数走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=wxzst"/>" <#if type='wxzst'>class="now"</#if>>五行走势图</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=hmyl"/>" <#if type='hmyl'>class="now"</#if>>号码遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=sxyl"/>" <#if type='sxyl'>class="now"</#if>>生肖遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=wsyl"/>" <#if type='wsyl'>class="now"</#if>>尾数遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=hsyl"/>" <#if type='hsyl'>class="now"</#if>>合数遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=wxyl"/>" <#if type='wxyl'>class="now"</#if>>五行遗漏</a></li>	
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=xtyl"/>" <#if type='xtyl'>class="now"</#if>>形态遗漏</a></li>		
			
			</ul>	
			</div>
			</td>
		  </tr>
	</table>
	<div class="kong5"></div>
       <div id="div_line"></div>
</div>

		<#if type=='index'||type=='szzst'||type=='sxzst'||type='jjfwzst'||type='wszst'||type='wxzst'>
		       <div class="tybanbg">
		        
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    	<form action="<@c.url value= "/welfare36to7/analyse!w36to7zs.action"/>" method="post">
				    
				    <input type="hidden" name="type" value="${type }" id="type">		
			    <input type="hidden" name="count" value="30" id="count">
			    <input type="hidden" name="sort" value="${sort }" id="sort">
				    <div class=" zstopleft1" id="size_div"> 显示：
				      <input id="size_30" type="button" class="btnow" value="显示30期" onclick="chgDisplay(30);" /> 
					  <input id="size_50" type="button" class="btgray" value="显示50期" onclick="chgDisplay(50);" /> 
					  <input id="size_80" type="button" class="btgray" value="显示100期" onclick="chgDisplay(100);" />
				    </div>
				    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="start" name="start" size="12" /> -  结束期数<input class="inputh18" id="end" name="end" size="12" />
				    </div>
			    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>				 
					</form>		
		<#elseif type=='hmyl'||type=='sxyl'||type=='wsyl'||type=='hsyl'||type=='wxyl'||type=='xtyl'>
			<div class="tybanbg1">
			        <div class=" zstopleft0" >
				       ${lotteryType.lotteryName}走势图
				    </div>
				    <form action="<@c.url value= "/welfare36to7/analyse!w36to7yl.action"/>" method="post">
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
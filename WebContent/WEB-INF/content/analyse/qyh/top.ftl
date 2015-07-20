<#include "../banner.ftl"/>

<div class="kong5"></div>
<div class="topbgty"><table width="978" border="0" cellspacing="0" cellpadding="0">
		<tr>
		    <td width="89"><div class="toptylogoqyh">${lotteryType.lotteryName}</div>
		</td>
		    <td width="4"><img src="${base}/pages/images/new_shuline.gif" /></td>
		    <td valign="top">
			<div class="all10px">
			<div class="title_c_orange">${lotteryType.lotteryName}走势图表</div>
			<div class="kong5"></div>
			<ul class="navt_menu">
			<li><a href="<@c.url value= "/qyh/analyse.action"/>" <#if menu='chzs'>class="now"</#if> >${lotteryType.lotteryName}综合走势图</a></li>
			<li><span>|</span></li>
			<li><a href="<@c.url value= "/qyh/analyse!weizhi.action"/>" <#if menu='weizhi'>class="now"</#if>>${lotteryType.lotteryName}位置走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/qyh/analyse!yilou.action"/>" <#if menu='yilou'>class="now"</#if>>${lotteryType.lotteryName}位置遗漏</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/qyh/analyse!lianhao.action"/>" <#if menu='lianhao'>class="now"</#if>>${lotteryType.lotteryName}连号走势图</a></li>
			<li><span>|</span></li>	
			<li><a href="<@c.url value= "/qyh/analyse!chonghao.action"/>" <#if menu='chonghao'>class="now"</#if>>${lotteryType.lotteryName}重号走势图</a></li>	
			<li><span>|</span></li>		
			<li><a href="<@c.url value= "/qyh/analyse!weixuan-1.action"/>" <#if menu=='weixuan'&&wz==1>style="color:#FF0000;"</#if>>围二遗漏</a></li>
	        <li><span>|</span></li>	
	        <li><a href="<@c.url value= "/qyh/analyse!weixuan-2.action"/>" <#if menu=='weixuan'&&wz==2>style="color:#FF0000;"</#if>>围三遗漏</a></li>
	        <li><span>|</span></li>	
	        <li><a href="<@c.url value= "/qyh/analyse!renxuan-6.action"/>" <#if menu=='renxuan'>class="now"</#if>>任三遗漏</a></li>
	        <li><span>|</span></li>
	        <li><a href="<@c.url value= "/qyh/analyse!hezhi.action"/>" <#if menu=='hezhi'>class="now"</#if>>围二和值</a></li>
	        <li><span>|</span></li>
	        <li><a href="<@c.url value= "/qyh/analyse!lianhaoMiss.action"/>" <#if menu=='lianhaoMiss'>class="now"</#if>>围二连号</a></li>
	        <li><span>|</span></li>
	        <li><a href="<@c.url value= "/qyh/analyse!commonTailNum.action"/>" <#if menu=='twh'>class="now"</#if>>围二同尾号</a></li>
	        <li><span>|</span></li>
	        <li><a href="<@c.url value= "/qyh/analyse!oddEvenSummary-3.action"/>" <#if menu=='jiou'&&wz=3>style="color:#FF0000;"</#if>>围二大小奇偶</a></li>
	        <li><span>|</span></li>	
	        <li><a href="<@c.url value= "/qyh/analyse!oddEvenSummary-4.action"/>" <#if menu=='jiou'&&wz==4>style="color:#FF0000;"</#if>>围三大小奇偶</a>	</li>            	
			<li><span>|</span></li>	
	        <li><a href="<@c.url value= "/qyh/analyse!oddEvenSummary-5.action"/>" <#if menu=='jiou'&&wz==5>style="color:#FF0000;"</#if>>任三大小奇偶</a>	</li>            			
			</ul>
			</div>
			</td>
		  </tr>
	</table>
</div>
<div class="tybanbg">
      <#if menu='yilou'>
           <div class=" zstopleft0" style="width:230px;">
                <a href="<@c.url value= "/${lotteryType.key}/analyse!yilou.action?wz=1"/>" <#if wz==1>style="color:#FF0000;"</#if>>第一位</a>
                <a href="<@c.url value= "/${lotteryType.key}/analyse!yilou.action?wz=2"/>" <#if wz==2>style="color:#FF0000;"</#if>>第二位</a>
                <a href="<@c.url value= "/${lotteryType.key}/analyse!yilou.action?wz=3"/>" <#if wz==3>style="color:#FF0000;"</#if>>第三位</a>
                <a href="<@c.url value= "/${lotteryType.key}/analyse!yilou.action?wz=4"/>" <#if wz==4>style="color:#FF0000;"</#if>>第四位</a>
                <a href="<@c.url value= "/${lotteryType.key}/analyse!yilou.action?wz=5"/>" <#if wz==5>style="color:#FF0000;"</#if>>第五位</a>
		   </div>
		   <div class=" zstopleft1"> 
			       最新开期：<span id="lastIssue"></span> 期，共 <span id="totalIssue"></span> 期
			</div>
	       <#elseif menu='weizhi'>
	           <div class=" zstopleft0" style="width:230px;">
			        <a href="<@c.url value= "/${lotteryType.key}/analyse!weizhi.action?wz=1"/>" <#if wz==1>style="color:#FF0000;"</#if>>第一位</a>
	                <a href="<@c.url value= "/${lotteryType.key}/analyse!weizhi.action?wz=2"/>" <#if wz==2>style="color:#FF0000;"</#if>>第二位</a>
	                <a href="<@c.url value= "/${lotteryType.key}/analyse!weizhi.action?wz=3"/>" <#if wz==3>style="color:#FF0000;"</#if>>第三位</a>
	                <a href="<@c.url value= "/${lotteryType.key}/analyse!weizhi.action?wz=4"/>" <#if wz==4>style="color:#FF0000;"</#if>>第四位</a>
	                <a href="<@c.url value= "/${lotteryType.key}/analyse!weizhi.action?wz=5"/>" <#if wz==5>style="color:#FF0000;"</#if>>第五位</a>
			    </div>
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示30期" onclick="chgDisplay(this.id,30);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示50期" onclick="chgDisplay(this.id,50);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示80期" onclick="chgDisplay(this.id,80);" />
			    </div>
			    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startIssueNo" size="12" /> -  结束期数<input class="inputh18" id="endIssueNo" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>
		    <#elseif menu="weixuan">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!weixuan.action"/>" method="post" id="weixuan_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>	
			<#elseif menu=="jiou">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!oddEvenSummary.action"/>" method="post" id="jiou_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>	
			<#elseif menu=="lianhaoMiss">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!lianhaoMiss.action"/>" method="post" id="lianhao_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>	
			<#elseif menu=="twh">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!commonTailNum.action"/>" method="post" id="twh_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>	
			<#elseif menu=="renxuan">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!renxuan.action"/>" method="post" id="renxuan_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>	
			<#elseif menu=="hezhi">
				<div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <form action="<@c.url value= "/${lotteryType.key}/analyse!hezhi.action"/>" method="post" id="hezhi_menu">
			    <input type="hidden" name="count" id="count"/>
			    <input type="hidden" name="wz" value="${wz}"/>		    
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="chg_30" type="button" class="btnow" value="显示100期" onclick="chgPeriodNumber(100);" /> 
				  <input id="chg_50" type="button" class="btgray" value="显示200期" onclick="chgPeriodNumber(200);" /> 
				  <input id="chg_80" type="button" class="btgray" value="显示500期" onclick="chgPeriodNumber(500);" />
				  <input id="chg_80" type="button" class="btgray" value="显示1000期" onclick="chgPeriodNumber(1000);" />			  
			    </div>
			    <!--<div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startPeriod" name="startPeriod" size="12" /> -  结束期数<input class="inputh18" id="endPeriod" name="endPeriod" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchMiss();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>-->
				</form>				
		    <#else>
		        <div class=" zstopleft0" >
			       ${lotteryType.lotteryName}走势图
			    </div>
			    <div class=" zstopleft1" id="size_div"> 显示：
			      <input id="size_30" type="button" class="btnow" value="显示30期" onclick="chgDisplay(this.id,30);" /> 
				  <input id="size_50" type="button" class="btgray" value="显示50期" onclick="chgDisplay(this.id,50);" /> 
				  <input id="size_80" type="button" class="btgray" value="显示80期" onclick="chgDisplay(this.id,80);" />
			    </div>
			    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startIssueNo" size="12" /> -  结束期数<input class="inputh18" id="endIssueNo" size="12" />
			    </div>
		    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>
		</#if>
		
	   </div>
 	   <div class="kong5"></div>
       <div id="MISS_EL">正在加载......</div>
       <div id="div_line"></div>
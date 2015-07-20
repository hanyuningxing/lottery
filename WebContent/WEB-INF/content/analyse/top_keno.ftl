<div class="tybanbg">
		    <div class=" zstopleft0" >
		       ${lotteryType.lotteryName}走势图
		    </div>
		    <div class=" zstopleft1" id="size_div"> 显示：
		      <input id="size_30" type="button" class="btnow" value="显示50期" onclick="chgDisplay(this.id,50);" /> 
			  <input id="size_50" type="button" class="btgray" value="显示100期" onclick="chgDisplay(this.id,100);" /> 
			  <input id="size_80" type="button" class="btgray" value="显示200期" onclick="chgDisplay(this.id,200);" />
			  <input id="size_500" type="button" class="btgray" value="显示500期" onclick="chgDisplay(this.id,500);" />
		    </div>
		    <#if type?? &&(type='g6Miss'||type='g3Miss')>
		    <#else>
		    <div class="zstopleft2" id="prefixDiv">
		    <input type="checkbox" value="${action.getTime(0)!}" />今天  <input type="checkbox" value="${action.getTime(-1)!}" />昨天  <input type="checkbox" value="${action.getTime(-2)!}" />前天
		    <input type="hidden" class="inputh18" value="2011062851" id="startIssueNo" size="8" />
		    <input type="hidden" class="inputh18" value="2011062860" id="endIssueNo" size="8" />
		    </div>
	    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchPrefixDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>
	         </#if>
	   </div>
 	   <div class="kong5"></div>
       <div id="MISS_EL">正在加载......</div>
       <div id="div_line"></div>
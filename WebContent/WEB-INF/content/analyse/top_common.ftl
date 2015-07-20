<div class="tybanbg">
		    <div class=" zstopleft0" >
		       ${lotteryType.lotteryName}走势图
		    </div>
		    <div class=" zstopleft1" id="size_div"> 显示：
		      <input id="size_30" type="button" class="btnow" value="显示30期" onclick="chgDisplay(this.id,30);" /> 
			  <input id="size_50" type="button" class="btgray" value="显示50期" onclick="chgDisplay(this.id,50);" /> 
			  <input id="size_80" type="button" class="btgray" value="显示80期" onclick="chgDisplay(this.id,80);" />
		    </div>
		    <#if type?? &&(type='g6Miss'||type='g3Miss')>
		    <#else>
		    <div class="zstopleft2"> 查询：开始期数<input class="inputh18" id="startIssueNo" size="8" /> -  结束期数<input class="inputh18" id="endIssueNo" size="8" />
		    </div>
	    	<div style="padding-top:4px;" class="left5 floatleft"><a href="#" onclick="searchDisplay();return false;"><img src="<@c.url value= "/pages/images/tybtsearchnew.gif"/>" width="85" height="20" border="0" /></a></div>
	         </#if>
	   </div>
 	   <div class="kong5"></div>
       <div id="MISS_EL">正在加载......</div>
       <div id="div_line"></div>
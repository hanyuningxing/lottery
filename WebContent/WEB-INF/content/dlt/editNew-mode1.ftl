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
            	<li><a href="#" onclick="${salesPlayType}RandomSelect(1);return false;">机选一注</a></li>
              	<li><a href="#" onclick="${salesPlayType}RandomSelect(3);return false;">机选三注</a></li>
              	<li><a href="#" onclick="${salesPlayType}RandomSelect(5);return false;">机选五注</a></li>
              	<li><a href="#" onclick="${salesPlayType}RandomSelect(10);return false;">机选十注</a></li>
     		</ul>
		</div>
		<div class=" floatrig">
        	<ul class="jixuan_menu_nowidth">
            	<li style="width:80px;"><a href="#" onclick="danRandomSelect(1);return false;">定胆机选一注</a></li>
              	<li style="width:80px;"><a href="#" onclick="danRandomSelect(3);return false;">定胆机选三注</a></li>
              	<li style="width:80px;"><a href="#" onclick="danRandomSelect(5);return false;">定胆机选五注</a></li>
              	<li style="width:80px;"><a href="#" onclick="danRandomSelect(10);return false;">定胆机选十注</a></li>
			</ul>
		</div>
	</div>
	<#assign luckLotteryNum='5' />
	<#include '/WEB-INF/content/common/luck.ftl' />
</div>
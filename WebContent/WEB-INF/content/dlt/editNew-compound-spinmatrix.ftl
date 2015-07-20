<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="trw">
      <td width="100" class="lineh20" height="115">
      	<span class=" font14char boldchar rebchar">第一步</span><br />
        <span class="rebchar">前区方案列表</span>
      </td>
      <td align="left" class="lineh20" >
      	标准格式样本：01,02,03,04,05<br />
        <textarea id="shrink_content" cols="45" rows="3" class="texk" style="width:411px; height:85px;" autocomplete="off">${(shrinkBean.content)!}</textarea>
      </td>
    </tr>
    <tr class="trw">
      <td width="100" height="60" class="lineh20" style="border-top:1px solid #E9EEF5;">
      	<span class=" font14char boldchar rebchar">第二步</span><br />
        <span class="rebchar">选择后区号码</span>
      </td>
      <td align="left" class="lineh20" style="border-top:1px solid #E9EEF5;">
        <div id="ssqtz" style="margin:0px;">
      	  <ul class="ssqball" id="shrink_blue_box">
			<#list 1..12 as num>
      			<li><a href="#" onclick="return false;" _name="blue_ball">${num?string('00')}</a></li>
         	</#list>
          </ul>
        </div>
	  </td>
    </tr>
</table>

<#include '/WEB-INF/content/common/count.ftl' />

<div class="kong10"></div>
<div id="jixuanqu_2">
    <div class="left">
    	<div class="left1">
      		<ul id="createForm_select_content" class="texk haomaqu" style="width:100%;height:100px;"></ul>
      	</div>
      	<div class="floatleft" style="height:105px;padding-top:40px;">
        	<ul class="jixuan_menu">
              	<li><a href="#" onclick="clearAll();return false;">全部清空</a></li>
        	</ul>
		</div>
      	<div class="kong10"></div>
	</div>
</div>
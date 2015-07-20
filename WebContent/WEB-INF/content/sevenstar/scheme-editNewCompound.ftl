<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>
<@override name="content.select">
 <div  class="cleanboth" >
        <div id="touzzhuqu">
		 <div class="left" style="width:665px;">
            <fieldset class="redfieldset">
             <legend class="cdownjl"><span class="redc1">复式投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
             <div class="all10px" id="area_box">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <#list 1..7 as n>
				        <tr>
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">第${n}位</div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="javascript:void(0);" onclick="return false;" _name="area${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td  id="area${n}ChooseUl"><ul class="qqjo_bt top10px">
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="javascript:void(0);" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
	               </#list>
	          	
	           </table>
            </div>
            </fieldset>
          </div>
           </div>
      </div> 
       <#include '/WEB-INF/content/common/count.ftl' />
	<div class="kong10"></div>
	<div id="jixuanqu">
	<div class="left">
		<div class="left1" style="width:300px;">
			<ul id="createForm_select_content" class="texk haomaqu" style=" width:300px;height:120px;list-style:none;"></ul>
			<div class="kong10"></div>
			<div class="xs_menu" style="padding-left:40px;" id="clear">
				<ul>
	            	<li><a href="#" onclick="clearAll();return false;">清空所有号码</a></li>
				</ul>
			</div>
		</div>
        <div class="floatleft" style="margin-left:15px;">
      		<ul class="jixuan_menu_nowidth">
            	<li><a href="#" onclick="randomSelect(1);return false;">机选一注</a></li>
              	<li><a href="#" onclick="randomSelect(3);return false;">机选三注</a></li>
              	<li><a href="#" onclick="randomSelect(5);return false;">机选五注</a></li>
              	<li><a href="#" onclick="randomSelect(10);return false;">机选十注</a></li>
     		</ul>
		</div>
	</div>
</div>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
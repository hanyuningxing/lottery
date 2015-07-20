<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
</@override>
<@override name="content.select">
	<#if playType==2>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="trw">
          <td width="100" class="lineh20" height="115">
          	<span class=" font14char boldchar rebchar">选号结果</span><br />
          </td>
          <td align="left" class="lineh20" >
          	标准格式样本：01,02,03,04,05<br />
            <textarea id="shrink_content" cols="45" rows="3" class="texk" style="width:411px; height:85px;" autocomplete="off">${(shrinkBean.content)!}</textarea>
            <input id="shrink_counter" type="button"  border="0" value="计算注数" class="btblue" style="cursor:pointer;" />
          </td>          
        </tr>
      </table>
      <script type="text/javascript">
      	document.getElementById('shrink_content').focus();
      </script>
	<#elseif playType==1>
		<div id="touzzhuqu">
        	<fieldset class="redfieldset">
        		<legend class="cdownjl"><span class="redc1">选球胆码</span>&nbsp;<span class="grayc1">至少选择1个,至多4个</span></legend>
	          	<ul class="ssqball" id="red_box_galls">
	            	<#list 1..22 as num>
	                	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
        	</fieldset>
	        <div class="kong20"></div>
	        <fieldset class="redfieldset">
	        	<legend class="cdownjl"><span class="redc1">选球拖码</span>&nbsp;<span class="grayc1">拖码个数加胆码个数要大于5</span></legend>
	          	<ul class="ssqball" id="red_box">
	            	<#list 1..22 as num>
	                	<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
	            	</#list>
	          	</ul>
	        </fieldset>
		</div>
	<#elseif playType==0>
		<div id="touzzhuqu">
          		<fieldset class="redfieldset">
		            <legend class="cdownjl"><span class="redc1">22选5复式投注区</span>&nbsp;<span class="grayc1">至少选择5个数字</span></legend>
		            <div class="all10px">
		              <table width="100%" border="0" cellspacing="0" cellpadding="0">
		                <tr id="tr_ball">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号</div></td>
		                  <td><ul class="ssqball" id="red_box">
		                      <#list 1..11 as num>
		                  		<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
		            		  </#list>
		                    </ul></td>
		                  <td><ul class="qqjo_bt top10px" >
		                  	  <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
		                <tr id="tr1_ball">
		                  <td width="40" height="30" align="center" valign="middle">&nbsp;</td>
		                  <td><ul class="ssqball" id="red_box1">
		                      <#list 12..22 as num>
		                  		<li><a href="#" onclick="return false;" _name="red_ball"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
		            		  </#list>
		                    </ul></td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>                
		              </table>
		            </div>
		            <div class="cleanboth floatrig" style="padding:12px 8px 0 0 ">
            			<div class="floatleft">
              				<select name="red_select" id="red_select">
                				<#list 5..18 as num>
                 		 			<option value="${num}">${num}</option>
                				</#list>
              				</select>
            			</div>
            			<div class="floatleft left10"><a href="#" onclick="randomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_jixuanhaoma.jpg"/>"/></a>&nbsp;<a href="#" onclick="clearRandomSelectBall('red');return false;"><img src="<@c.url value="/pages/images/bt_qingchu.gif"/>" /></a></div>
          			</div>
		       </fieldset>
		</div>
	</#if>
	
    <#include '/WEB-INF/content/common/count.ftl' />
    
	<div class="kong10"></div>
    <#if playType==2>
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
    <#else>
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
		            	<li><a href="#" onclick="randomSelect(1);return false;">机选一注</a></li>
		              	<li><a href="#" onclick="randomSelect(3);return false;">机选三注</a></li>
		              	<li><a href="#" onclick="randomSelect(5);return false;">机选五注</a></li>
		              	<li><a href="#" onclick="randomSelect(10);return false;">机选十注</a></li>
		     		</ul>
				</div>
				<div class=" floatrig">
		        	<ul class="jixuan_menu_nowidth">
		            	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(1)<#else>danRandomSelect(1)</#if>;return false;">定胆机选一注</a></li>
		              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(3)<#else>danRandomSelect(3)</#if>;return false;">定胆机选三注</a></li>
		              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(5)<#else>danRandomSelect(5)</#if>;return false;">定胆机选五注</a></li>
		              	<li style="width:80px;"><a href="#" onclick="<#if playType==1>danRandomSelect_1(10)<#else>danRandomSelect(10)</#if>;return false;">定胆机选十注</a></li>
					</ul>
				</div>
			</div>
			<#assign luckLotteryNum='22' />
			<#include '/WEB-INF/content/common/luck.ftl' />
		</div>
    </#if>
</@override>

<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
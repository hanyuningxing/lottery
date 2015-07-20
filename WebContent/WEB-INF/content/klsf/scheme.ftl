<head>
	<title>广东快乐十分网上购买投注、开奖、走势查询 – ${webapp.webName}</title>
	<meta name="keywords" content="快乐十分" />
	<meta name="Description" content="${webapp.webName}广东快乐十分网上投注频道为广大彩民朋友提供最新的广东快乐十分预测分析，快乐十分精准追号计算功能与官方同步的开奖结果查询服务，快乐十分专家定时为广大彩民做出分析解答，为您解决一切网上投注广东快乐十分所遇到的问题，免去您的后顾之忧，让您轻松体验网上购彩的乐趣。" />
	<meta name="decorator" content="trade" />
	<link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/klsf.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/chase.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/compute.js"/>"></script>
</head>
<div class="main">
  <!-- left开始 -->
  <div class="main_czleft">
    <#include "../common/keno-head.ftl" />
    <div class="cleanboth k3px" >
						<script> 
						    function selectFormMethod(playType){
								var selectForm = document.getElementById("selectForm");
								selectForm["createForm.playType"].value=playType;
								selectForm.submit();
								return false;
							}
							function selectBetTypeFormMethod(playType){
								var betTypeForm = document.getElementById("betForm");
								document.getElementById("betType").value=playType;
								betTypeForm.submit();
							}
							 function selectSalesModeFormMethod(salesMode){
								var selectForm = document.getElementById("selectForm");
								selectForm["salesMode"].value=salesMode;
								selectForm.submit();
								return false;
							}
						</script>
        <div id="cz115">
             <ul class="caizhonglist">
             <#assign playTypeArr=stack.findValue("@com.cai310.lottery.support.klsf.KlsfPlayType@values()") />
	         <form id="betForm" action="<@c.url value='/${lotteryType.key}/scheme!index.action'/>" method="post">
	            <input id="betType" type="hidden" name="betType" value=""/>
                 <#list playTypeArr as playType>	               
                   <li>
	                <a <#if betType==playType> class="now"</#if> href="javascript:selectBetTypeFormMethod('${playType}');">${playType.typeName}</a></li>
	               </li> 
	             </#list>
              </form>
           	  </ul>     
              <input type="hidden" id="betType_input" value="${betType}"/>
              <input type="hidden" id="betType_lineLimit" value="${betType.lineLimit}"/>
        </div>
      <!-- 当前期-->
     <div class=" cleanboth bggray32" style="line-height:32px; border-top:1px solid #dcdcdc;">
		<#if  betType!='RedOne'&&betType!='NormalOne'>
		<input type="radio" name="createForm_salesMode" value="1" id="createForm_salesMode_1" onclick="SalesModeSelect(this.value);"/>单式
		</#if>
		<input type="radio" name="createForm_salesMode" value="0" id="createForm_salesMode_0" checked="checked" onclick="SalesModeSelect(this.value);"/>复式
		<#if betType=='RandomTwo'||betType=='RandomThree'||betType=='RandomFour'||betType=='RandomFive'>
		   	<input type="radio" name="createForm_salesMode" value="2" id="createForm_salesMode_2" onclick="SalesModeSelect(this.value);"/>胆拖
		</#if>
      </div>
      <!-- 玩法说明-->
      <div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
	            <#if betType=='NormalOne'>
			          选一数投 从01至18中任意选择1个数字号码竞猜开奖号码的第一位,投注号码与开奖号码第一位相同即中奖，奖金25元
		        </#if>
		        <#if betType=='RedOne'>
		                     选一红投  "从19和20两个红色号码中任意选择1个红色号码竞猜开奖号码的第一位,开奖号码第一位是红球即中奖，奖金5元
		        </#if>
		        <#if betType=='RandomTwo'>
		                      任选二  从01-20中选择数字竞猜开奖号码的任意两位,投注号码与开奖号码任意两位相同即中奖，奖金8元
		        </#if>
		        <#if betType=='ConnectTwoGroup'>
		                      选二连组   从01-20中竞猜任意连续两位,投注号码与开奖号码任意连续两位数字相同(顺序不限)即中奖，奖金31元
		        </#if>
		        <#if betType=='ConnectTwoDirect'>
		                      选二连直  从01-20中选择数字竞猜开奖号码的任意连续两位,投注号码与开奖号码任意连续两位数字、顺序均相同即中奖，奖金62元
		        </#if>
		        <#if betType=='RandomThree'>
		                      任选三  从01-20中选择数字竞猜开奖号码的任意三位,投注号码与开奖号码任意三位相同即中奖，奖金24元
		        </#if>
		        <#if betType=='ForeThreeGroup'>
		                      选三前组  从01-20数字中竞猜开奖号码的前三位,投注号码与开奖号码前三位数字相同(顺序不限)即中奖,奖金1300元
		        </#if>
		        <#if betType=='ForeThreeDirect'>
		                      选三前直  从01-20中竞猜开奖号码前三位,投注号码与开奖号码前三位数字、顺序均相同即中奖，奖金8000元
		        </#if>
		        <#if betType=='RandomFour'>
		                      任选四  从01-20中选择数字竞猜开奖号码的任意四位,投注号码与开奖号码任意四位相同即中奖，奖金80元
		        </#if>
		        <#if betType=='RandomFive'>
		                      任选五  从01-20中选择数字竞猜开奖号码的任意五位,投注号码与开奖号码任意五位相同即中奖，奖金320元
		        </#if>
      </div>
      	<!-- 单式投注区-->
         <div class="left" style="width:665px;display:none;" id="single_select_content_div">
              <div class="kong10"></div>
		      <div id="jixuanqu">
		        <!-- 输入框-->
		        <div class="left">
		          <div class="left1">参考格式：01 02 03 04 05 
		            	 <textarea id="single_createForm_content" onblur="countTextAreaMoney();" value="" cols="35" rows="5" style="border:1px solid #A7CAED;"></textarea>
		          </div>
		          <!-- bt机选按钮-->
		          <div class="floatleft">
		            <ul class="jixuan_menu">
			              <li><a href="#" onclick="${betType}RandomSelect(1);return false;">机选一注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(5);return false;">机选五注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(8);return false;">机选八注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(10);return false;">机选十注</a></li>
		              <li><a href="#" onclick="singleClearAll();return false;">全部清空</a></li>
		            </ul>
		          </div>
		          <div class="kong10"></div>
		        </div>
		      </div>
         </div>
         
         <!-- 胆拖投注区-->
         <div id="compound_select_content_div">
          <div  class="cleanboth" >
	          <div id="touzzhuqu">
	           <!-- 胆拖-->
	           <div id="compound_select_content_div_dan" style="display:none;">
	             <fieldset class="bluefieldset">
	             <legend class="cdownjl"><span class="redc1">胆拖投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
		             <div class="all10px" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
				                 <tr id="ball_area_box_dan">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 1..10 as num>
						            	 <li><a _name="bet_ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td> </td>
				                </tr>
				                <tr _name="${betType}ball_area_box">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 11..20 as num>
						            	 <li><a _name="bet_ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td></td>
				                </tr>
		              </table>
		              </div>
	            </fieldset>
	            </div>
	            
	           <!-- 复试投注区-->
	          <div class="left" style="width:665px;" >
	            <fieldset class="redfieldset">
	             <legend class="cdownjl"><span class="redc1" id="compound_txt">复式投注区</span>&nbsp;<span class="grayc1">每行数字至少选择${betType.lineLimit}个数字</span></legend>
	             <div class="all10px" >
	             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                  <#if betType=='NormalOne'>
			           <tr _name="${betType}ball_area_box">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..9 as num>
					            	 <li><a _name="${betType}ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1,null);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1,null);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1,null);return false;">全</a></li>
			                    </ul></td>
			                </tr>
			                <tr _name="${betType}ball_area_box">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
			                  <td><ul class="ssqball">
			                     <#list 10..18 as num>
					            	 <li><a _name="${betType}ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1,null);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1,null);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1,null);return false;">清</a></li>
			                    </ul></td>
			                </tr>
			            </#if>
			           <#if betType=='RedOne'>
			           <tr _name="${betType}ball_area_box">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
			                  <td><ul class="ssqball">
					            	 <li><a _name="${betType}ball"   href="javascript:void(0);" onclick="return false;">19</a></li>
			                   		 <li><a _name="${betType}ball"  href="javascript:void(0);" onclick="return false;">20</a></li>
			                    </ul>
			                  </td>
			                  <td> </td>
			                </tr>
			            </#if>
			            <#if betType=='RandomTwo'||betType=='RandomThree'||betType=='RandomFour'||betType=='RandomFive'||betType=='ConnectTwoGroup'||betType=='ForeThreeGroup'>
				          <tr _name="${betType}ball_area_box">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 1..10 as num>
						            	 <li><a _name="${betType}ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1,null);return false;">大</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1,null);return false;">小</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1,null);return false;">全</a></li>
				                    </ul></td>
				                </tr>
				                <tr _name="${betType}ball_area_box">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 11..20 as num>
						            	 <li><a _name="${betType}ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1,null);return false;">奇</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1,null);return false;">偶</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1,null);return false;">清</a></li>
				                    </ul></td>
				                </tr>
			            </#if>
			            
			             <#if betType=='ConnectTwoDirect'||betType=='ForeThreeDirect'>
				          	<#list 1..betType.lineLimit as n>
						      <tr id="${betType}ball_area_box_${n}">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">第${n}位</div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..10 as num>
					            	 <li><a _name="${betType}ball${n}" _line="${n}" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',${n},null);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',${n},null);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',${n},null);return false;">全</a></li>
			                    </ul>
			                   </td>
			             	 </tr>
				             <tr id="${betType}ball_area_${n}_2">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"></div></td>
				                  <td><ul class="ssqball">
				                     <#list 11..20 as num>
						            	 <li><a _name="${betType}ball${n}" _line="${n}" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',${n},null);return false;">奇</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',${n},null);return false;">偶</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',${n},null);return false;">清</a></li>
				                    </ul></td>
				             </tr>
		               		</#list>
			            </#if>
	              </table>
	            </div>
	            </fieldset>
	          </div>
	        </div>
     	 </div> 
     	 <#include '../common/count.ftl' />
              <div class="kong10"></div>
		      <div id="jixuanqu">
		        <!-- 输入框-->
		        <div class="left">
		          <div class="left1">
		            	 <ul id="createForm_select_content" class="texk haomaqu"></ul>
		          </div>
		          <!-- bt机选按钮-->
		          <#if betType!='RedOne'>
		          <div class="floatleft">
		            <ul class="jixuan_menu">
			              <li><a href="#" onclick="${betType}RandomSelect(1);return false;">机选一注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(5);return false;">机选五注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(8);return false;">机选八注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(10);return false;">机选十注</a></li>
		              <li><a href="#" onclick="clearAll();return false;">全部清空</a></li>
		            </ul>
		          </div>
		           </#if> 
		          <div class="kong10"></div>
		        </div>
		      </div>
		  </div> 
		   
		  <div class="kong10"></div>
	      <form id="createForm" action="<@c.url value='/${lotteryType.key}/scheme!bet.action' />" method="post" autocomplete="off">
	        <input type="hidden" name="betType" value="${betType}"/>
	        <#assign canChase=true />
	        <#include '../common/KenoCommon.ftl' />
	      </form>
    </div>
    <div class="kong10"></div>
	  <div class="zxpd">
	<div class="zx"><h1>${webapp.webName}广东快乐十分网上投注频道</h1></div>
	<div class="zx_link">
	 ${webapp.webName}广东快乐十分网上投注频道为广大彩民朋友提供最新的广东<a href="http://www.cai310.com/ch/ZPD_KLSF_FORECAST.htm" target="_blank" ><b>快乐十分</b>预测</a>分析，快乐十分精准追号计算功能与官方同步的开奖结果查询服务，快乐十分专家定时为广大彩民做出<a href="http://www.cai310.com/ch/ZPD_KLSF_SKILLS.htm" target="_blank" >快乐十分技巧</a>分析解答，为您解决一切网上投注广东快乐十分所遇到的问题，免去您的后顾之忧，让您轻松体验网上购彩的乐趣。<br />
	<b>快乐十分介绍： </b><br />
	快乐十分是广东省福彩中心发行新的全新快速开奖的彩种，快乐十分几乎其它彩种的所有优点，返奖率高（最高返奖率可高达59%），开奖快速，玩法简单易于上手。<br />
	<b>快乐十分玩法介绍：</b><br />
	广东省快乐十分采取每十分钟开奖一次，全天共开奖84期，每期从01~20中随机抽出8个不同号码，作为本期的中奖号码。十种不同的快乐十分玩法（排列玩法和组合投注玩法）满足彩民朋友不同的需求。其中，排列、组合玩法可分为三种：第一位组合选号法：如任二、任三、任四、任五选法；二是组合兼排列的定位玩法：即定中奖号码位置但不定中奖号码出现顺序，包括选二连组、选三前组；三是排列玩法，是既定中奖号码位置又定中奖号码出现顺序，包括选一数投、选一红投和选二连直、选三前直共4种玩法
	 </div>
	</div>
    
  </div>
  <!-- left结束 -->
    <#include 'rightContent.ftl' />
    
    
    
    
</div>
 <script type="text/javascript">
  countDown({
    chase_url:'<@c.url value= "/${lotteryType.key}/scheme!canChaseIssue.action"/>',
    burl:'<@c.url value= "/${lotteryType.key}/scheme!asyncTime.action"/>',
    url:'<@c.url value= "/keno/${lotteryType.key}/time.js"/>',
    turl:'<@c.url value= "/serviceTime.jsp"/>',
    iid:'keno_issueNumber',
    tid:'keno_count_down',
    lid:'keno_last_issueNumber',
    lrid:'keno_last_result',
    lrt:'keno_last_result_time'
  });
  loadRightContent('${lotteryType.key}');
  </script>

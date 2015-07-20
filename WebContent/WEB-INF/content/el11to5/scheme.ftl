<head>
	<title>江西体彩11选5，11选5网上购买投注- ${webapp.webName}安全购彩平台</title>
	<meta name="decorator" content="trade" />
	<link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" />
    	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/chase.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/el11to5.js"/>"></script>
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
               <form id="betForm" action="<@c.url value='/${lotteryType.key}/scheme!index.action'/>" method="post">
                <input id="betType" type="hidden" name="betType" value=""/>
                <li><a <#if betType=='NormalOne'>class="now"</#if> href="javascript:selectBetTypeFormMethod('NormalOne');">任选一</a></li>
				<li><a <#if betType=='RandomTwo'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomTwo');">任选二</a></li>
				<li><a <#if betType=='RandomThree'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomThree');">任选三</a>
				</li>
				<li><a <#if betType=='RandomFour'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomFour');">任选四</a></li>
				<li><a <#if betType=='RandomFive'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomFive');">任选五</a></li>
                <li><a <#if betType=='RandomSix'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomSix');">任选六</a></li>
				<li><a <#if betType=='RandomSeven'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomSeven');">任选七</a>
				</li>
				<li><a <#if betType=='RandomEight'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomEight');">任选八</a></li>
				<li><a <#if betType=='ForeTwoGroup'>class="now"</#if> href="javascript:selectBetTypeFormMethod('ForeTwoGroup');">前二组选</a></li>
			    <li><a <#if betType=='ForeThreeGroup'>class="now"</#if> href="javascript:selectBetTypeFormMethod('ForeThreeGroup');">前三组选</a></li>
				<li><a <#if betType=='ForeTwoDirect'>class="now"</#if> href="javascript:selectBetTypeFormMethod('ForeTwoDirect');">前二直选</a></li>
                <li><a <#if betType=='ForeThreeDirect'>class="now"</#if> href="javascript:selectBetTypeFormMethod('ForeThreeDirect');">前三直选</a></li>     
               </form>
           </ul>     
                <input type="hidden" id="betType_input" value="${betType}"/>
                <input type="hidden" id="betType_lineLimit" value="${betType.lineLimit}"/>
        </div>
      <!-- 当前期-->
     <div class=" cleanboth bggray32" style="line-height:32px; border-top:1px solid #dcdcdc;">
        <#if betType=='NormalOne'>
		   <#else><input type="radio" name="createForm_salesMode" value="1" id="createForm_salesMode_1" onclick="SalesModeSelect(this.value);"/>单式
		</#if>
		
		<input type="radio" name="createForm_salesMode" value="0" id="createForm_salesMode_0" checked="checked" onclick="SalesModeSelect(this.value);"/>复式
		<#if betType=='ForeThreeDirect'|| betType=='ForeTwoDirect'|| betType=='NormalOne'>
		   <#else><input type="radio" name="createForm_salesMode" value="2" id="createForm_salesMode_2" onclick="SalesModeSelect(this.value);"/>胆拖
		</#if>
      </div>
      <!-- 玩法说明-->
      <div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
            <#if betType=='NormalOne'>
		          选一：竞猜开奖号码的第一位，选择1个或以上号码投注，奖金13元。
		        </#if>
		        <#if betType=='RandomTwo'>
		          选二：竞猜开奖号码的任意两位，选择2个或以上号码投注，奖金6元。
		        </#if>
		        <#if betType=='ForeTwoGroup'>
		          前二组选：竞猜开奖号码的前面两位，选择2个或以上号码投注，猜中号码则中奖，奖金65元。
		        </#if>
		        <#if betType=='ForeTwoDirect'>
		          前二直选：竞猜开奖号码的前面两位，选择2个或以上号码投注，猜中号码且位置一致则中奖，奖金130元。
		        </#if>
		        <#if betType=='RandomThree'>
		          任选三：竞猜开奖号码的任意三位，选择3个或以上号码投注，奖金19元。
		        </#if>
		        <#if betType=='ForeThreeGroup'>
		          前三组选：竞猜开奖号码的前面三位，选择3个或以上号码投注，猜中号码则中奖，奖金195元。
		        </#if>
		        <#if betType=='ForeThreeDirect'>
		          前三直选：竞猜开奖号码的前面三位，选择3个或以上号码投注，猜中号码且位置一致则中奖，奖金1170元。
		        </#if>
		        <#if betType=='RandomFour'>
		          任选四：竞猜开奖号码的任意四位，选择4个或以上号码投注，奖金78元。
		        </#if>
		        <#if betType=='RandomFive'>
		          任选五：竞猜全部开奖号码，至少选择5个或以上号码投注，奖金540元。
		        </#if>
		        <#if betType=='RandomSix'>
		          任选六：竞猜全部开奖号码，至少选择6个或以上号码投注，任意5个猜中则中奖，奖金90元。
		        </#if>
		        <#if betType=='RandomSeven'>
		          任选七：竞猜全部开奖号码，至少选择7个或以上号码投注，任意5个猜中则中奖，奖金26元。
		        </#if>
		        <#if betType=='RandomEight'>
		          任选八：竞猜全部开奖号码，至少选择8个或以上号码投注，任意5个猜中则中奖，奖金9元。
        </#if>
      </div>
      <!-- 投注区-->
         <div class="left" style="width:665px;display:none;" id="single_select_content_div">
              <div class="kong10"></div>
		      <div id="jixuanqu">
		        <!-- 输入框-->
		        <div class="left">
		          <div class="left1">
		            	 <textarea id="single_createForm_content" onblur="countTextAreaMoney();" value="" cols="35" rows="5" style="border:1px solid #A7CAED;"></textarea>
		          </div>
		          <!-- bt机选按钮-->
		          <div class="floatleft">
		            <ul class="jixuan_menu">
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(1);return false;">机选一注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(5);return false;">机选五注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(8);return false;">机选八注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(10);return false;">机选十注</a></li>
		              <li><a href="javascript:void(0);" onclick="singleClearAll();return false;">全部清空</a></li>
		            </ul>
		          </div>
		          <div class="kong10"></div>
		        </div>
		      </div>
         </div>
         <div id="compound_select_content_div">
          <div  class="cleanboth" >
	          <div id="touzzhuqu">
	           <div id="compound_select_content_div_dan" style="display:none;">
	             <fieldset class="bluefieldset">
	             <legend class="cdownjl"><span class="redc1">胆拖投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
		             <div class="all10px" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
				                 <tr id="ball_area_box_dan">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 1..11 as num>
						            	 <li><a _name="bet_ball" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><!--<ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'all',null);return false;">全</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'big',null);return false;">大</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'small',null);return false;">小</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'odd',null);return false;">奇</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'even',null);return false;">偶</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan','clear',null);return false;">清</a></li>
				                    </ul>--></td>
				                </tr>
		              </table>
		              </div>
	            </fieldset>
	             </div>
	          <!-- 红球-->
	          <div class="left" style="width:665px;" >
	            <fieldset class="redfieldset">
	             <legend class="cdownjl"><span class="redc1" id="compound_txt">复式投注区</span>&nbsp;<span class="grayc1">每行数字至少选择${betType.lineLimit}个数字</span></legend>
	             <div class="all10px" >
	             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                  <#if betType=='ForeTwoDirect'||betType=='ForeThreeDirect'>
			           <tr id="${betType}ball_area_box_1">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='ForeTwoDirect'||betType=='ForeThreeDirect'>第一位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..11 as num>
					            	 <li><a _name="${betType}ball1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                        <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1);return false;">全</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1);return false;">清</a></li>
			                    </ul></td>
			             </tr>
			            </#if>
			            <#if betType=='ForeTwoDirect'||betType=='ForeThreeDirect'>
			             <tr id="${betType}ball_area_box_2">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='ForeTwoDirect'||betType=='ForeThreeDirect'>第二位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..11 as num>
					            	  <li><a _name="${betType}ball2" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                       <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',2);return false;">全</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',2);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',2);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',2);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',2);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',2);return false;">清</a></li>
			                    </ul></td>
			                </tr>
				         </#if>
				         <#if betType=='ForeThreeDirect'>
				            <tr id="${betType}ball_area_box_3">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='ForeThreeDirect'>第三位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..11 as num>
					            	 <li><a _name="${betType}ball3" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                       <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',3);return false;">全</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',3);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',3);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',3);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',3);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',3);return false;">清</a></li>
			                    </ul></td>
			                </tr>
			                <#elseif betType=='ForeTwoDirect'>
			                <#else>
			                 <tr id="${betType}ball_area_box">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..11 as num>
					            	 <li><a _name="${betType}ball" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',null);return false;">全</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',null);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',null);return false;">小</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',null);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',null);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',null);return false;">清</a></li>
			                    </ul></td>
			                </tr>
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
		          <div class="floatleft">
		            <ul class="jixuan_menu">
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(1);return false;">机选一注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(5);return false;">机选五注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(8);return false;">机选八注</a></li>
			              <li><a href="javascript:void(0);" onclick="${betType}RandomSelect(10);return false;">机选十注</a></li>
		              <li><a href="javascript:void(0);" onclick="clearAll();return false;">全部清空</a></li>
		            </ul>
		          </div>
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
  </div>
  <!-- left结束 -->
    <#include 'rightContent.ftl' />
    
    <div class="kong10"></div>
	  <div class="zxpd">
	<div class="zx"><h1>${webapp.webName}江西11选5网上投注频道</h1></div>
	<div class="zx_link">
	为广大彩民提供<a href="/ch/el11to5.htm" target="_blank"><b>江西体彩11选5</b></a>的预测分析，江西十一运夺金的购彩技巧和最新的开奖公告及实时的江西11选5走势图，是可以信赖的11选5网上投注安全购彩平台。<br />
	<b>江西11选5彩种玩法介绍： </b><br />
	  江西11选5亦称江西多乐彩, 是由国家体育总局发行，江西省体彩中心承销的在线即开彩种玩法。11选5选号区号码范围为01～11，每期开出5个不同数字作为中奖号码。11选5玩法即是竞猜5位开奖号码的全部或部分号码。 <br />
	  <b>江西11选5玩法特点： </b><br />
	  1、即投、即开、即兑：每12分钟一期，全天65期（9:00-22:00)，在线即投、即开、即兑，可玩性极高的一种快开彩票。<br />
	  2、玩法简单多样：包括任选一、任选二、任选三、任选四、任选五、任选六、任选七、任选八、前二直选、前二组选、前三直选、前三组选等多种玩法。<br />
	  3、中奖率和返奖率高：返奖率高达59％，是体彩数字玩法游戏中最高的。其中任选二的中奖率为1/5.5，任选八的中奖率为1/8.25，任选一的中奖率为1/11。此外，前三直选中奖率为1/990，奖金为1170元，同类型直选玩法中中奖更容易，奖金更高。
	  
	  </div>
	</div>
    
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

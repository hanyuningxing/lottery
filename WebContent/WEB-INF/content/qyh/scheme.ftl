<head>
	<title>福彩群英会，山东群英会、深圳群英会网上购买投注 - ${webapp.webName}安全购彩平台</title>
	<meta name="description" content="“${webapp.webName}山东群英会网上投注频道：为广大彩民提供福彩群英会的预测分析，山东群英会的购彩技巧和最新的群英会开奖公告及群英会走势图，也提供了深圳群英会网上投注，是可以信赖的群英会网上投注安全购彩平台。" />
	<meta name="decorator" content="trade" />
	<link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/chase.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/qyh.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/compute.js"/>"></script>
    <script type="text/javascript" src="<@c.url value="/js/thinkbox/thinkbox.js"/>"></script>
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
							 function selectSalesModeFormMethod(salesMode){
								var selectForm = document.getElementById("selectForm");
								selectForm["salesMode"].value=salesMode;
								selectForm.submit();
								return false;
							}
						</script>
      <!-- navmenu end -->
      <div class=" cleanboth qyhtwobg">
        <DIV class="qyhwz">
          <ul class="qyhtopnav">
            <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=RandomFour" />" <#if type="1">class="now"</#if>>任选投注</a></li>
            <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=RoundOne" />" <#if type="2">class="now"</#if>>围选投注</a></li>
            <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectOne" />" <#if type="3">class="now"</#if>>顺选投注</a></li>
          </ul>
        </div>
      </div>
      <!-- 当前期-->
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
      <div id="qyh" style="position: relative;">
             <ul class="caizhonglist">
             <form id="betForm" action="<@c.url value='/${lotteryType.key}/scheme!index.action'/>" method="post">
             <input id="betType" type="hidden" name="betType" value=""/>
             <#if type=="1">
                <li><a <#if betType=='RandomOne'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomOne');">任选一</a></li>
				<li><a <#if betType=='RandomTwo'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomTwo');">任选二</a>
				</li>
				<li><a <#if betType=='RandomThree'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomThree');">任选三</a></li>
				<li><a <#if betType=='RandomFour'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomFour');">任选四</a></li>
				<li><a <#if betType=='RandomFive'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomFive');">任选五</a></li>
                <li><a <#if betType=='RandomSix'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomSix');">任选六</a></li>
				<li><a <#if betType=='RandomSeven'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomSeven');">任选七</a></li>
				<li><a <#if betType=='RandomEight'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomEight');">任选八</a></li>   
				<li><a <#if betType=='RandomNine'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomNine');">任选九</a></li>
				<li><a <#if betType=='RandomTen'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RandomTen');">任选十</a></li>   
            <#elseif type=="2">
            	<li><a <#if betType=='RoundOne'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RoundOne');">围选一</a></li>
				<li><a <#if betType=='RoundTwo'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RoundTwo');">围选二</a></li>
				<li><a <#if betType=='RoundThree'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RoundThree');">围选三</a></li>				
            	<li><a <#if betType=='RoundFour'>class="now"</#if> href="javascript:selectBetTypeFormMethod('RoundFour');">围选四</a></li>
			<#else>	
            	<li><a <#if betType=='DirectOne'>class="now"</#if> href="javascript:selectBetTypeFormMethod('DirectOne');">顺选一</a>
            	</li>
				<li><a <#if betType=='DirectTwo'>class="now"</#if> href="javascript:selectBetTypeFormMethod('DirectTwo');">顺选二</a></li>
				<li><a <#if betType=='DirectThree'>class="now"</#if> href="javascript:selectBetTypeFormMethod('DirectThree');">顺选三</a></li>				
            </#if>
            </form>
            </ul>     
                <input type="hidden" id="betType_input" value="${betType}"/>
                <input type="hidden" id="betType_lineLimit" value="${betType.lineLimit}"/>
        </div>
      <!-- 当前期-->
     <div class=" cleanboth bggray32" style="line-height:32px; border-top:1px solid #dcdcdc;">
		<input type="radio" name="createForm_salesMode" value="1" id="createForm_salesMode_1" onclick="SalesModeSelect(this.value);"/>单式
		<input type="radio" name="createForm_salesMode" value="0" id="createForm_salesMode_0" checked="checked" onclick="SalesModeSelect(this.value);"/>复式
		<#if Type=='1'&&betType!='RandomOne'&&betType!='RandomTen'>
		   <input type="radio" name="createForm_salesMode" value="2" id="createForm_salesMode_2" onclick="SalesModeSelect(this.value);"/>胆拖
		</#if>
      </div>
      <!-- 玩法说明-->
      <div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
            <#if betType=='RandomOne'>
		         任一：从01~23共23个数字号码中任选1个<span id="span_bet_memo">以上</span>号码进行投注，奖金5元。
		        </#if>
		        <#if betType=='RandomTwo'>
		         任二；从01~23共23个数字号码中任选2个<span id="span_bet_memo">以上</span>号码进行投注，奖金30元。
		        </#if>
		        <#if betType=='RandomThree'>
		          任三：从01-23共23个数字号码中任选3个<span id="span_bet_memo">以上</span>号码,投注号码与开奖号码任意三位相同即中3,奖金100元;任意两位相同即中2,奖金5元。      
		        </#if>
		        <#if betType=='RandomFour'>
		          任四：从01-23共23个数字号码中任选4个<span id="span_bet_memo">以上</span>号码,投注号码与开奖号码任意四位相同即中4,奖金1000元;任意三位相同即中3,奖金30元。      
		        </#if>
		        <#if betType=='RandomFive'>
		          任五： 从01-23共23个数字号码中任选5个<span id="span_bet_memo">以上</span>号码,投注号码与开奖号码全部相同即中5,奖金10000元;任意四位相同即中4,奖金150元;任意三位相同即中3,奖金10元。
		        </#if>
		        <#if betType=='RandomSix'>
		          任六：从01~23共23个数字号码中任选6个<span id="span_bet_memo">以上</span>号码进行投注，奖金6500元。
		        </#if>
		        <#if betType=='RandomSeven'>
		          任七：从01~23共23个数字号码中任选7个<span id="span_bet_memo">以上</span>号码进行投注，奖金1800元。
		        </#if>
		        <#if betType=='RandomEight'>
		          任八：从01~23共23个数字号码中任选8个<span id="span_bet_memo">以上</span>号码进行投注，奖金680元。
        </#if>
		        <#if betType=='RandomNine'>
		          任九：从01~23共23个数字号码中任选9个<span id="span_bet_memo">以上</span>号码进行投注，奖金300元。
        </#if>
		        <#if betType=='RandomTen'>
		         任十：从01~23共23个数字号码中任选10个<span id="span_bet_memo">以上</span>号码进行投注，奖金150元。
        </#if>
		  <#if betType=='RoundOne'>
		         围一：围一投注的一个<span id="span_bet_memo">以上</span>号码与当期开出的第一个中奖号码相同即中奖25元。
        </#if>
		  <#if betType=='RoundTwo'>
		         围二：围二投注的两个<span id="span_bet_memo">以上</span>号码与当期开出的前两个中奖号相符（顺序不限）即中奖300元。
        </#if>
		  <#if betType=='RoundThree'>
		       围三：围三投注的三个<span id="span_bet_memo">以上</span>号码与当期开出的前三个中奖号相符（顺序不限）即中奖2000元。
        </#if>
		  <#if betType=='RoundFour'>
		       围四：围四投注的四个<span id="span_bet_memo">以上</span>号码与当期开出的前四个中奖号相符（顺序不限）即中奖10000元。
        </#if>
		  <#if betType=='DirectOne'>
		     顺一：顺一投注一个<span id="span_bet_memo">以上</span>号码中的一位号码与当期开出的第一个中奖号码相同即中奖26元。
        </#if>
		  <#if betType=='DirectTwo'>
		       顺二：顺二投注两个<span id="span_bet_memo">以上</span>号码中的两位号码与当期开出的前两个中奖号码及其顺序相同即中奖590元。
        </#if>
		  <#if betType=='DirectThree'>
		      顺三：顺三投注三个<span id="span_bet_memo">以上</span>号码中的三位号码与当期开出的前三个中奖号码及其顺序相同即中奖12300元。
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
	          <!-- 红球-->
	          <div class="left" style="width:665px;" >
	            <div id="compound_select_content_div_dan" style="display:none;">
	             <fieldset class="bluefieldset">
	             <legend class="cdownjl"><span class="redc1">胆拖投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
		             <div class="all10px" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
				                 <tr id="ball_area_box_dan">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 1..12 as num>
						            	 <li><a _name="bet_ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><!--<ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'big',1,null);return false;">大</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'small',1,null);return false;">小</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'all',1,null);return false;">全</a></li>
				                    </ul>--></td>
				                </tr>
				                <tr id="ball_area_box_dan">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 13..23 as num>
						            	 <li><a _name="bet_ball" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td><!--<ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'odd',1,null);return false;">奇</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan', 'even',1,null);return false;">偶</a></li>
				                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('Dan','clear',1,null);return false;">清</a></li>
				                    </ul>--></td>
				                </tr>
		              </table>
		              </div>
	            </fieldset>
	             </div>
	            <fieldset class="redfieldset">
	             <legend class="cdownjl"><span class="redc1" id="compound_txt">复式投注区</span>&nbsp;<span class="grayc1">每行数字至少选择${betType.lineLimit}个数字</span></legend>
	             <div class="all10px" >
	             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                  <#if betType=='DirectTwo'||betType=='DirectThree'>
			           <tr id="${betType}ball_area_box_1">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='DirectTwo'||betType=='DirectThree'>第一位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..12 as num>
					            	 <li><a _name="${betType}ball1" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1,1);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1,1);return false;">小</a></li>
			                        <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1,1);return false;">全</a></li>
			                    </ul></td>
			             </tr>
			             <tr id="${betType}ball_area_box_1">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"></div></td>
			                  <td><ul class="ssqball">
			                     <#list 13..23 as num>
					            	 <li><a _name="${betType}ball1" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1,1);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1,1);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1,1);return false;">清</a></li>
			                    </ul></td>
			             </tr>
			             <tr id="${betType}ball_area_box_2">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='DirectTwo'||betType=='DirectThree'>第二位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..12 as num>
					            	  <li><a _name="${betType}ball2" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1,2);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1,2);return false;">小</a></li>
			                       <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1,2);return false;">全</a></li>
			                    </ul></td>
			                </tr>
			             <tr id="${betType}ball_area_box_2">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"></div></td>
			                  <td><ul class="ssqball">
			                     <#list 13..23 as num>
					            	  <li><a _name="${betType}ball2" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1,2);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1,2);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1,2);return false;">清</a></li>
			                    </ul></td>
			                </tr>
				         </#if>
				         <#if betType=='DirectThree'>
				            <tr id="${betType}ball_area_box_3">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if betType=='DirectThree'>第三位<#else>号码</#if></div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..12 as num>
					            	 <li><a _name="${betType}ball3" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'big',1,3);return false;">大</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'small',1,3);return false;">小</a></li>
			                       <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'all',1,3);return false;">全</a></li>
			                    </ul></td>
			                </tr>
				            <tr id="${betType}ball_area_box_3">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"></div></td>
			                  <td><ul class="ssqball">
			                     <#list 13..23 as num>
					            	 <li><a _name="${betType}ball3" _line="1" href="javascript:void(0);" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
					             </#list>
			                    </ul>
			                 </td>
			                  <td><ul class="qqjo_bt top10px" >
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'odd',1,3);return false;">奇</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}', 'even',1,3);return false;">偶</a></li>
			                      <li style="width:22px;"><a href="javascript:void(0);" onclick="chooseBallMethod('${betType}','clear',1,3);return false;">清</a></li>
			                    </ul></td>
			                </tr>
			                </#if>
			                <#if betType!='DirectTwo'&&betType!='DirectThree'>
			                 <tr _name="${betType}ball_area_box">
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
			                  <td><ul class="ssqball">
			                     <#list 1..12 as num>
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
			                     <#list 13..23 as num>
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
	<div class="zx"><h1>${webapp.webName}山东群英会网上投注频道</h1></div>
	<div class="zx_link">
	为广大彩民提供福彩<a href="/ch/qyh.htm" target="_blank"><b>群英会</b></a>的预测分析，山东群英会的购彩技巧和最新的群英会开奖公告及群英会走势图，也提供了深圳群英会网上投注，是可以信赖的群英会网上投注安全购彩平台。<br />
	<b>山东群英会彩种玩法介绍： </b><br />
	  山东群英会,是在线即开型彩票玩法的一种，属于基诺型彩票范畴，中国福利彩票群英会是财政部批准发行，由山东福彩中心承销的快开彩票。 <br />
	  山东群英会(福彩群英会)是从23位齐鲁古代名将(以1-23个号码代表)中选择1～10位(用代码表示)进行投注，一组1～3位号码(代码)的排列或1～10位号码的组合称为一注，群英会玩法即是竞猜5位开奖号码的全部或部分号码。 <br />
	  <b>群英会购买投注方式： </b><br />
	  &ldquo;群英会&rdquo;投注玩法分为顺选投注、围选投注和任选投注。<br />
	  1. 群英会顺选投注：是指从1～23中任意选择1～3个号码的唯一排列作为一注投注号码进行顺选对奖的投注购买，分为顺一、顺二、顺三，最高奖金达12300元;<br />
	  2. 群英会围选投注：是指从1～23中任意选择1～4个号码的任意组合作为一注投注号码进行围选对奖的投注购买，分为围一、围二、围三和围四;<br />
	  3. 群英会任选投注：是指从1～23中任意选择1～10个号码的任意组合作为一注投注号码进行任选对奖的投注购买，玩法从任一到任十，各级奖金不等，最高为10000元。
		  
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

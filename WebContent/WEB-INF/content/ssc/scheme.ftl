<head>
	<title>重庆时时彩官网平台购买投注、追号计算、开奖结果查询 - ${webapp.webName}安全购彩平台</title>
	<meta name="keywords" content="重庆时时彩购买投注频道为您提供最新的重庆时时彩预测分析，时时彩平台精准追号计算功能与官方同步的开奖结果查询服务，时时彩分析专家定时为广大彩民做出分析解答，为您解决一切网上投注重庆时时彩所遇到的问题，免去您的后顾之忧，让您轻松体验网上购彩的乐趣。" />
	<meta name="description" content="" />
	<meta name="decorator" content="trade" />
	<link href="<@c.url value= "/pages/css/danfuzhuan.css"/>" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/chase.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/common.js"/>"></script>
    <script type="text/javascript" src="<@c.url value= "/js/lottery/keno/ssc.js"/>"></script>
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
							 function selectSalesModeFormMethod(salesMode){
								var selectForm = document.getElementById("selectForm");
								selectForm["salesMode"].value=salesMode;
								selectForm.submit();
								return false;
							}
						</script>
      <!-- navmenu end -->
      <!-- 当前期-->
      <div id="qyh">
             <ul class="caizhonglist">
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectOne&&salesMode=COMPOUND"/>" <#if betType=='DirectOne'>class="now"</#if>>一星</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectTwo&&salesMode=COMPOUND"/>" <#if betType=='DirectTwo'>class="now"</#if>>二星直选</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=GroupTwo&&salesMode=COMPOUND" />"  <#if betType=='GroupTwo'>class="now"</#if>>二星组选</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=GroupTwoSum&&salesMode=COMPOUND" />"  <#if betType=='GroupTwoSum'>class="now"</#if>>二组和值</a></li>
        	    <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectThree&&salesMode=COMPOUND"/>"  <#if betType=='DirectThree'>class="now"</#if>>三星直选</a></li>
        	    <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=ThreeGroup3&&salesMode=COMPOUND"/>"  <#if betType=='ThreeGroup3'>class="now"</#if>>三直组三</a></li>
        	    <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=ThreeGroup6&&salesMode=COMPOUND"/>"  <#if betType=='ThreeGroup6'>class="now"</#if>>三直组六</a></li>
        	    <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectThreeSum&&salesMode=COMPOUND"/>"  <#if betType=='DirectThreeSum'>class="now"</#if>>三直和值</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectFive&&salesMode=COMPOUND"/>" <#if betType=='DirectFive'>class="now"</#if>>五星直选</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=AllFive&&salesMode=COMPOUND"/>" <#if betType=='AllFive'>class="now"</#if>>五星通选</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=BigSmallDoubleSingle&&salesMode=COMPOUND"/>"<#if betType=='BigSmallDoubleSingle'>class="now"</#if>>大小单双</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=RANDOMONE&&salesMode=COMPOUND"/>"<#if betType=='RANDOMONE'>class="now"</#if>>任选一</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=RANDOMTWO&&salesMode=COMPOUND"/>"<#if betType=='RANDOMTWO'>class="now"</#if>>任选二</a></li>
                <li><a href="<@c.url value= "/${lotteryType.key}/scheme.action?betType=DirectFour&&salesMode=COMPOUND"/>"<#if betType=='DirectFour'>class="now"</#if>>四星直选</a></li>
            </ul>     
            <input type="hidden" id="betType_input" value="${betType}"/>
            <input type="hidden" id="betType_lineLimit" value="${betType.lineLimit}"/>
       </div>
      <!-- 当前期-->
      <div class=" cleanboth bggray32" style="line-height:32px; border-top:1px solid #dcdcdc;">
		<#if betType=='GroupTwo'|| betType=='ThreeGroup3'|| betType=='ThreeGroup6'||betType=='DirectTwo'|| betType=='DirectThree'>
		   <input type="radio" name="createForm_salesMode" value="1" id="createForm_salesMode_1" onclick="SalesModeSelect(this.value);"/>单式
		</#if>
		<input type="radio" name="createForm_salesMode" value="0" id="createForm_salesMode_0" checked="checked" onclick="SalesModeSelect(this.value);"/>复式
		<#if betType=='GroupTwo'|| betType=='ThreeGroup3'|| betType=='ThreeGroup6'>
		   <input type="radio" name="createForm_salesMode" value="2" id="createForm_salesMode_2" onclick="SalesModeSelect(this.value);"/>胆拖
		</#if>
      </div>
      
      <!-- 玩法说明-->
      <div class="czshuoming"><img src="<@c.url value="/pages/images/icontishi.gif" />" />
     	    <#if betType=='DirectOne'>
		       	 玩法说明：从个位选择1个或者1个以上号码进行投注，选号与开奖号码个位号码一致即中10元。
		    </#if>
		    <#if betType=='DirectTwo'>
		       	 玩法说明：从0-9中选择号码分别对个、十位进行投注，所选号码与开奖号码按位一致即中100元。
		    </#if>
		     <#if betType=='DirectTwoSum'>
		      	玩法说明：从0-18中选择1个或者多个和值进行投注，与开奖号码个、十位 两个数之和相同即中100元。
		    </#if>
		    <#if betType=='GroupTwo'>
				玩法说明：从0-9中选择2个或者2个以上号码，与开奖号码个、十位一致(不包含对子、不限顺序)即中50元。		    
			</#if>
		    <#if betType=='GroupTwoSum'>
		      	 玩法说明：从1-17中选择1个或者多个和值进行投注，选号与开奖号码个、十位之和一致(不包含对子)即中50元。 
		    </#if>
		    <#if betType=='DirectThree'>
				玩法说明：从0-9个中选择号码分别对个、十、百位进行投注，所选号码与开奖号码按位一致即中1000元。 		    
			</#if>
		    <#if betType=='DirectThreeSum'>
		     	 玩法说明：从0-27中选择1个或者多个和值进行投注，与开奖号码个、十、百三个数之和相同即中1000元
		    </#if>
		     <#if betType=='ThreeGroup3'>
				玩法说明：选择两个或两个以上号码进行投注，与开奖号码后三位号码一致(开奖号码为组三、不限顺序)即中320元。
		    </#if>
		    <#if betType=='ThreeGroup6'>
				玩法说明：从0-9中选择3个或3个以上号码进行投注，与开奖号码后三位一致(开奖号码为组六、不限顺序)即中160元。
		    </#if>
		    <#if betType=='GroupThreeSum'>
				玩法说明：从1-26中选择1个或者1个以上和值进行投注，与开奖号码后三位一致(不含豹子、不限顺序)即中160元或者320元。
		    </#if>
            <#if betType=='DirectFive'>
				玩法说明：每个位置选择1个或多个号码，投注号码与开奖号码按位一致即中10万元。		    
			</#if>
		    <#if betType=='AllFive'>
		       	 玩法说明：每个位置选择1个或多个号码，全部按位一致奖金20000元；猜中前三或后三奖金200元；猜中前二或后二奖金20元；兼中单注最高奖金20440元。
		    </#if>
		      <#if betType=='BigSmallDoubleSingle'>
		       	玩法说明：对十、个位进行大小单双投注，所选大小单双与开奖号码十、个位大小单双一致即中奖4元。
		       	号码0～9中，0～4为小，5～9为大，1、3、5、7、9为单，0、2、4、6、8为双。
		    </#if>
		     <#if betType=='RANDOMONE'>
		       	从任意位选择1个或多个号码，选号与对应位置的开奖号码一致即中奖11元
		    </#if>
		      <#if betType=='RANDOMTWO'>
		       	从任意2位选择1个或多个号码，选号与对应位置的开奖号码一致即中奖116元
		    </#if>
		     <#if betType=='DirectFour'>
		       从四位各选1个或多个号码，选号与开奖号码后四位按位一致即中奖1万元
		    </#if>
      </div>
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
     <!-- 投注区-->
         <div id="compound_select_content_div">
          <div  class="cleanboth" >
	        <div id="touzzhuqu">
	        <div class="left" style="width:665px;" >
	        <div id="compound_select_content_div_dan" style="display:none;">
	             <fieldset class="bluefieldset">
	             <legend class="cdownjl"><span class="redc1">胆拖投注区</span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
		             <div class="all10px" >
		             <table width="100%" border="0" cellspacing="0" cellpadding="0">
				                 <tr id="ball_area_box_dan">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">号码</div></td>
				                  <td><ul class="ssqball">
				                     <#list 0..9 as num>
						            	 <li><a _name="bet_ball" _line="1" href="#" onclick="return false;"><#if num lt 10>0${num}<#else>${num}</#if></a></li>
						             </#list>
				                    </ul>
				                 </td>
				                  <td></td>
				                </tr>
		              </table>
		              </div>
	            </fieldset>
	           </div>
	        
	        
            <fieldset class="redfieldset">
             <legend class="cdownjl">
             <span class="redc1" id="compound_txt" >
	             <#if hasDan!=true>
	             	${betType.typeName!}复式投注区
	             <#else>
	             	拖码投注区
	             </#if>
             </span>&nbsp;<span class="grayc1">每行数字至少选择1个数字</span></legend>
             <div class="all10px" id="area_box">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <#if betType=="DirectFive">
				   <#list 1..5 as n>
				        <tr id="_directFive_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>万位<#elseif n==2>千位<#elseif n==3>百位<#elseif n==4>十位<#elseif n==5>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
	               </#list>
	               <#elseif betType=="AllFive">
	               <#list 1..5 as n>
				        <tr id="_allFive_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>万位<#elseif n==2>千位<#elseif n==3>百位<#elseif n==4>十位<#elseif n==5>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
	               </#list>
	              <#elseif betType=="DirectThree">
	               	<#list 1..3 as n>
				        <tr id="_directThree_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>百位<#elseif n==2>十位<#elseif n==3>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
	               </#list>
	                <#elseif betType=="DirectTwo">
	                	<#list 1..2 as n>
				        <tr id="_directTwo_${n}">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>十位<#elseif n==2>个位</#if></div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
		              </#list>
		              <#elseif betType=="DirectOne">
		             	 <tr id="_directOne">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">个位</div></td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                         <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                  <td><ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                    </ul></td>
		                </tr>
		               <#elseif betType=="DirectTwoSum"||betType=="GroupTwoSum">
		                <tr id="${betType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                            <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                </tr>
		                <tr id="${betType}Tr2">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 10..18 as num>
		                            <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                 </td>
		                </tr>
		                
		               <#elseif betType=="DirectThreeSum"||betType=="GroupThreeSum">
		                <tr id="${betType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 0..13 as num>
		                            <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                  </td>
		                </tr>
		                <tr id="${betType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 14..27 as num>
		                            <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                  </td>
		                </tr>
		               <#elseif betType=="ThreeGroup3"||betType=="ThreeGroup6"||betType=="GroupTwo"||betType=="GroupThree">
		                <tr id="${betType}Tr">
		                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar">选号区</td>
		                  <td><ul class="ssqball">
		                      <#list 0..9 as num>
		                            <li><a href="#" onclick="return false;" _name="${betType}">${num}</a></li>
		                      </#list>
		                    </ul>
		                  </td>
		                  <#if hasDan!=true> 
		                  <td>
		                     <ul class="qqjo_bt top10px" >
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
		                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
		                     </ul>
		                 </td>
		                  </#if>
		                </tr>
		                <#elseif betType=="BigSmallDoubleSingle">
				        <tr id="_bigSmallDoubleSingle">
				          <#list 1..2 as n>
			                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>十位<#elseif n==2>个位</#if></div></td>
			                  <td>
			                    <ul class="ssqball">
			                         <li><a href="#" onclick="return false;" id="BigSmallDoubleSingle${n}_2" _name="${betType}${n}">大</a></li>
	 		                         <li><a href="#" onclick="return false;" id="BigSmallDoubleSingle${n}_1" _name="${betType}${n}">小</a></li>
	 		                         <li><a href="#" onclick="return false;" id="BigSmallDoubleSingle${n}_5" _name="${betType}${n}">单</a></li>
	 		                         <li><a href="#" onclick="return false;" id="BigSmallDoubleSingle${n}_4" _name="${betType}${n}">双</a></li>
			                    </ul>
			                  </td>
		                  </#list>
		                </tr>
		                   <#elseif betType=="RANDOMONE">
			               <#list 1..5 as n>
						        <tr id="_RANDOMONE_${n}">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>万位<#elseif n==2>千位<#elseif n==3>百位<#elseif n==4>十位<#elseif n==5>个位</#if></div></td>
				                  <td><ul class="ssqball">
				                      <#list 0..9 as num>
				                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
				                      </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
				                    </ul></td>
				                </tr>
			               </#list>
			                  <#elseif betType=="RANDOMTWO">
			               <#list 1..5 as n>
						        <tr id="_RANDOMTWO_${n}">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>万位<#elseif n==2>千位<#elseif n==3>百位<#elseif n==4>十位<#elseif n==5>个位</#if></div></td>
				                  <td><ul class="ssqball">
				                      <#list 0..9 as num>
				                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
				                      </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
				                    </ul></td>
				                </tr>
			               </#list>
			            <#elseif betType=="DirectFour">
						   <#list 1..4 as n>
						        <tr id="_directFour_${n}">
				                  <td width="40" height="30" align="center" valign="middle"><div class=" top10px boldchar"><#if n==1>千位<#elseif n==2>百位<#elseif n==3>十位<#elseif n==4>个位</#if></div></td>
				                  <td><ul class="ssqball">
				                      <#list 0..9 as num>
				                         <li><a href="#" onclick="return false;" _name="${betType}${n}">${num}</a></li>
				                      </#list>
				                    </ul>
				                 </td>
				                  <td><ul class="qqjo_bt top10px" >
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="all">全</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="big">大</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="small">小</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="odd">奇</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="even">偶</a></li>
				                      <li style="width:22px;"><a href="#" onclick="return false;" _name="clear">清</a></li>
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
		          <div class="floatleft">
		            <ul class="jixuan_menu">
			              <li><a href="#" onclick="${betType}RandomSelect(1);return false;">机选一注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(5);return false;">机选五注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(8);return false;">机选八注</a></li>
			              <li><a href="#" onclick="${betType}RandomSelect(10);return false;">机选十注</a></li>
		              <li><a href="#" onclick="clearAll();return false;">全部清空</a></li>
		            </ul>
		          </div>
		          <div class="kong10"></div>
		        </div>
		      </div>
		  </div> 
		   
		  <div class="kong10"></div>
	      <form id="createForm" action="<@c.url value='/${lotteryType.key}/scheme!bet.action' />" method="post" autocomplete="off">
	        <input type="hidden" id="betType" name="betType" value="${betType}"/>
	        <#assign canChase=true />
	        <#include '../common/KenoCommon.ftl' />
	      </form>
    </div>
  </div>
  <!-- left结束 -->
    <#include 'rightContent.ftl' />
    
    <div class="kong10"></div>
	  <div class="zxpd">
		<div class="zx"><h1>重庆时时彩网购频道</h1></div>
		<div class="zx_link">
		${webapp.webName}<a href="/ch/ssc.htm" target="_blank"><b>重庆时时彩</b></a>网购频道为广大彩民朋友提供最新的重庆时时彩预测分析，时时彩精准追号计算功能与官方同步的开奖结果查询服务，时时彩分析专家定时为广大彩民做出分析解答，为您解决一切网上投注重庆时时彩所遇到的问题，免去您的后顾之忧，让您轻松体验网上购彩的乐趣。<br />
		<b>重庆时时彩彩种玩法介绍： </b><br />
		重庆时时彩是在线即开型彩票，属于基诺型彩票玩法，是重庆市福利彩票发行管理中心负责承销的一种快开型彩票。  重庆时时彩选号区分为万位、千位、百位、十位和个位，每个位置的号码范围都为0～9。每期各位上都开出1个号码作为中奖号码，所以开奖号码为5位数的排列组合。重庆时时彩玩法即是分别竞猜5个位置开奖号码的全部号码、部分号码或部分号码特征。<br />
		<b>重庆时时彩玩法特点 </b><br />
		1、重庆时时彩即投、即开、即兑  ：全天24小时销售，每5分钟（22:00-1:55）或10分钟1期（10:00-22:00），每天120期在线即投、即开、即兑，彩民时时都能享受购彩的乐趣。<br />
		2、重庆时时彩玩法简单多样：包括五星、三星、二星、一星和大小单双等多种玩法，具有超强的趣味性、分析性和娱乐性。<br />
		3、重庆时时彩中奖率高：最高中奖概率高达25％，相比同类型玩法具有竞争力。<br />
		4、重庆时时彩一注号码、五次中奖机会：首创五星通选投注方式，2元一注号码即可三个奖级通吃，共有5次中奖机会。
		
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

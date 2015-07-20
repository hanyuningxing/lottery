<head>
<meta name="decorator" content="tradeV1" />
<link href="${base}/V1/css/kaijiang.css" rel="stylesheet" type="text/css" />
<meta name="menu" content="${webapp.zjcx}"/>
<!--只需这个就可以了-->
</head>

<div class="w1000">
  <div class="kaijiang_left">
    <div class="kaijiang_navtop"></div>
    <div class="kaijiang_bg">
      <!--竞彩足球 begin-->
	   <#assign RQClass="">
	   <#assign MIXClass="">
	   <#assign SPFClass="">
	   <#assign JQSClass="">
	   <#assign BQQClass="">
	   <#assign BFClass="">
	    <#if lotteryType == "JCZQ">
	   <#if playType == "RQSPF">
	   		<#assign RQClass="now">
	   <#elseif playType=="MIX">
	   		<#assign MIXClass="now">
	   	<#elseif playType=="SPF">
	   		<#assign SPFClass="now">
	   	<#elseif playType=="JQS">
	   		<#assign JQSClass="now">
	   	<#elseif playType=="BF">
	   		<#assign BFClass="now">
	   	<#elseif playType=="BQQ">
	   	<#assign BQQClass="now">
	   	</#if>
   		</#if>
      <div class="kaijiang_jg">竞彩足球</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/jczq/scheme!wonList.action?playType=RQSPF"  class="${RQClass}">让球</a></li>
          <li><a href="${base}/jczq/scheme!wonList.action?playType=SPF" class="${SPFClass}">胜平负</a></li>
          <li><a href="${base}/jczq/scheme!wonList.action?playType=BF" class="${BFClass}">比分</a></li>
          <li><a href="${base}/jczq/scheme!wonList.action?playType=JQS" class="${JQSClass}">进球数</a></li>
          <li><a href="${base}/jczq/scheme!wonList.action?playType=BQQ" class="${BQQClass}">半全场</a></li>
          <li><a href="${base}/jczq/scheme!wonList.action?playType=MIX" class="${MIXClass}">混合过关</a></li>
        </ul>
      </div>
 
    	
      <div class="cb"></div>
      <!--竞彩足球 end-->
      <!--竞彩篮球 begin-->
   
   	   <#assign SFClass="">
	   <#assign RFSFClass="">
	   <#assign SFCClass="">
	   <#assign DXFClass="">
	   <#assign MIXClass="">
	   <#if lotteryType == "JCLQ">
	   <#if playType?? && playType == "SF">
	   		<#assign SFClass="now">
	   <#elseif playType?? && playType=="RFSF">
	   		<#assign RFSFClass="now">
	   	<#elseif playType?? && playType=="SFC">
	   		<#assign SFCClass="now">
	   	<#elseif playType?? && playType=="DXF">
	   		<#assign DXFClass="now">
	   	<#elseif playType?? && playType=="MIX">
	   		<#assign MIXClass="now">
	  </#if>
	  </#if>
	  
      <div class="kaijiang_jg">竞彩篮球</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/jclq/scheme!wonList.action?playType=SF"  class="${SFClass}">胜负</a></li>
          <li><a href="${base}/jclq/scheme!wonList.action?playType=RFSF" class="${RFSFClass}">让分胜负</a></li>
          <li><a href="${base}/jclq/scheme!wonList.action?playType=SFC" class="${SFCClass}">胜分差</a></li>
          <li><a href="${base}/jclq/scheme!wonList.action?playType=DXF" class="${DXFClass}">大小分</a></li>
          <li><a href="${base}/jclq/scheme!wonList.action?playType=MIX" class="${MIXClass}">混合过关</a></li>
        </ul>
      </div>
   
      <div class="cb"></div>
      <!--竞彩篮球 end-->
      <!--北京单场 begin-->
	   <#assign SPFClass="">
	   <#assign ZJQSClass="">
	   <#assign SXDSClass="">
	   <#assign BFClass="">
	   <#assign BQQSPFClass="">
	<#if lotteryType == "DCZC">
	   <#if playType?? && playType == "SPF">
	   		<#assign SPFClass="now">
	   	<#elseif playType?? && playType=="ZJQS">
	   		<#assign ZJQSClass="now">
	   	<#elseif playType=="SXDS">
	   		<#assign SXDSClass="now">
	   	<#elseif playType?? && playType=="BF">
	   		<#assign BFClass="now">
	   	<#elseif playType?? && playType=="BQQSPF">
	   	<#assign BQQSPFClass="now">
	  </#if>
   </#if>
      <div class="kaijiang_jg">北京单场</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/dczc/scheme!wonList.action?playType=SPF"  class="${SPFClass}">胜平负</a></li>
          <li><a href="#"></a></li>
          <li><a href="${base}/dczc/scheme!wonList.action?playType=ZJQS"  class="${ZJQSClass}">进球数</a></li>
          <li><a href="${base}/dczc/scheme!wonList.action?playType=SXDS"  class="${SXDSClass}">上下单双</a></li>
          <li><a href="${base}/dczc/scheme!wonList.action?playType=BF"  class="${BFClass}">比分</a></li>
          <li><a href="${base}/dczc/scheme!wonList.action?playType=BQQSPF"  class="${BQQSPFClass}"">半全场</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--北京单场 end-->
      
	   <#assign SFZC14Class="">
	   <#assign SFZC9Class="">
	   <#if lotteryType == "SFZC">
	   <#if playType?? && playType == "SFZC14">
	   		<#assign SFZC14Class="now">
	   	<#elseif playType?? && playType=="SFZC9">
	   		<#assign SFZC9Class="now">
	   	</#if>
   		</#if>
   		<#assign LCZCClass="">
   		<#if lotteryType == "LCZC">
	   	<#assign LCZCClass="now">
   		</#if>
   		
   		<#assign SCZCClass="">
   		<#if lotteryType == "SCZC">
	   	<#assign SCZCClass="now">
	   	</#if>
      <!--足球彩票 begin-->
      <div class="kaijiang_jg">足球彩票</div>
      <div class="kaijiang_jgwz">
        <ul class="kaijiang_jgwzlist">
          <li><a href="${base}/sfzc/scheme!wonList.action?playType=SFZC14" class="${SFZC14Class}">14场胜负</a></li>
          <li><a href="${base}/sfzc/scheme!wonList.action?playType=SFZC9" class="${SFZC9Class}">任选9场</a></li>
          <li><a href="${base}/lczc/scheme!wonList.action" class="${LCZCClass}">6场半全场</a></li>
          <li><a href="${base}/sczc/scheme!wonList.action" class="${SCZCClass}">4场进球</a></li>
        </ul>
      </div>
      <div class="cb"></div>
      <!--足球彩票 end-->
    </div>
  </div>
  
   <div class="kaijiang_rig">
    <div class="kaijiang_topbg">
      <!--足彩通用-->
    <form action="<@c.url value="/${lotteryType.key}/scheme!wonListTable.action" />" method="post" name="scheme_won_list_form" id="scheme_won_list_form">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <#if lotteryType=="JCZQ">
        	<#assign logo = "kaijiang_logo kjlogo_jczq"/>
        <#elseif lotteryType=="JCLQ">
        	<#assign logo = "kaijiang_logo kjlogo_jclq"/>
        <#elseif lotteryType=="DCZC">
        	<#assign logo = "kaijiang_logo kjlogo_bjdc"/>
        <#elseif lotteryType=="SCZC" || lotteryType=="SFZC" || lotteryType=="LCZC">
        	<#assign logo = "kaijiang_logo kjlogo_zc"/>
        </#if>
          <td width="80" align="center"><div class="${logo}"></div></td>
          <td  class="kaijiang_name" style="text-align:left;">${lotteryType.lotteryName}中奖查询</td>
          <td>	
          		<select name="periodId" onchange="$('#scheme_won_list_form').submit();"  size="1">
               <#list periods ?sort_by(["periodNumber"])?reverse as p>
					<option <#if period.id=p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber}期</option>
			   </#list>
            </select>
            </td>
          <#assign isNone="none">
          <#if lotteryType == "JCZQ" || lotteryType == "JCLQ">
          	 <#assign isNone="">
          </#if>
          <td align="center" style="display:${isNone}">返奖时间 </td>
          <td><select name="timeFrame" size="1" id="select4" onchange="$('#scheme_won_list_form').submit();" style="display:${isNone}">
              <option value="7" selected="selected">全部</option>
              <#if days??>
              	<#assign i=0/>
	              <#list days as day>	              	
	              	<option value="${i}">${day?string('yyyy-MM-dd')}</option>
	              	<#assign i=i+1/>
	              </#list>
              </#if>
            </select></td>
          <td>
          	<input value="用户名或方案号" size="15" onclick="this.value='';" id="sponsorNameOrSchemeNum" name="sponsorNameOrSchemeNum">
          	<script>
          		var sponsorNameOrSchemeNum = document.getElementById("sponsorNameOrSchemeNum");
          		 sponsorNameOrSchemeNum.onfocus = function(){
          		 	sponsorNameOrSchemeNum.value='';
          		 }
          		 sponsorNameOrSchemeNum.onblur = function(){
          		 	if(sponsorNameOrSchemeNum.value=='')
          		 	sponsorNameOrSchemeNum.value='用户名或方案号';
          		 }
          	</script>
		  </td>
          <td><a href="javascript:;" class="kjsearchbt" onclick="$('#scheme_won_list_form').submit();"></a></td>
        </tr>
      </table>
    </div>
    
    <div id="extra-data-container">
				<script>
				<#if playType??>
					var url = window.BASESITE + '/${lotteryType.key}/scheme!wonListTable.action?playType=${playType}';
				<#else>
					var url = window.BASESITE + '/${lotteryType.key}/scheme!wonListTable.action';
				</#if>
					$(function(){
		         		$.post(url, {
		         			timeFrame: 7
		         		}, function(data){
		         			document.getElementById('extra-data-container').innerHTML = data;
		         		});
		        
		         	});
		        </script>
		        <#import "../../macro/pagination.ftl" as b />
			    <@b.page {'ajaxContainer':'extra-data-container'} />
    </div>
    </form>
    <script>
			$(function(){
				$('#scheme_won_list_form').submit(function() {		    
					 var periodId = $('#scheme_won_list_form [name="periodId"]').val();
					 var timeFrame = $('#scheme_won_list_form [name="timeFrame"]').val();
					 var sponsorNameOrSchemeNum = $('#scheme_won_list_form [name="sponsorNameOrSchemeNum"]').val();
					 			$.post(url, {
				         			sponsorNameOrSchemeNum: sponsorNameOrSchemeNum,
				         			periodId: periodId,
				         			timeFrame: timeFrame
				         			
				         		}, function(data){
				         			document.getElementById('sponsorNameOrSchemeNum').value = '';
				         			document.getElementById('extra-data-container').innerHTML = data;
				         		});
				     return false;  //阻止表单提交
				});
			})
	</script>
  </div>
</div>

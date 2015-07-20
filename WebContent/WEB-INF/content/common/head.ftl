<!-- 投注方式的判断 -->
<#assign saleModeArr=stack.findValue("@com.cai310.lottery.common.SalesMode@values()") />
<#assign shareTypeArr=stack.findValue("@com.cai310.lottery.common.ShareType@values()") />
<#if createForm?? && createForm.mode??>
	<#assign salesMode=createForm.mode />
</#if>
<#if !(salesMode??) && scheme??>
   <#assign salesMode=scheme.mode />
</#if>
<#if !(salesMode??)>
	<#assign salesMode=saleModeArr[0] />
</#if>
<#assign salesModeStr = salesMode?string />


<#if createForm?? && createForm.shareType??>
	<#assign shareType=createForm.shareType />
</#if>
<#if !(shareType??) && scheme??>
   <#assign shareType=scheme.shareType />
</#if>
<#if !(shareType??)>
	<#assign shareType=shareTypeArr[0] />
</#if>
<#assign shareTypeStr = shareType?string />



<#if salesModeStr??>
      <#if salesModeStr=='COMPOUND'>
         <!-- 复式 -->
            <#if shareTypeStr??&&compoundPeriodSales??>
			      <#if shareTypeStr=='TOGETHER'>
			         <!-- 合买 -->
			            <#assign endInitTime = compoundPeriodSales.shareEndInitTime />
			         <#elseif shareTypeStr=='SELF'>
			         <!-- 代购 -->
			            <#assign endInitTime = compoundPeriodSales.selfEndInitTime />
			      </#if>
			</#if>
         <#elseif salesModeStr=='SINGLE'&&singlePeriodSales??>
         <!-- 单式 -->
            <#if shareTypeStr??>
			      <#if shareTypeStr=='TOGETHER'>
			         <!-- 合买 -->
			          <#assign endInitTime = singlePeriodSales.shareEndInitTime />
			         <#elseif shareTypeStr=='SELF'>
			         <!-- 代购 -->
			         <#assign endInitTime = singlePeriodSales.selfEndInitTime />
			      </#if>
			</#if>
      </#if>
</#if>


<!-- 根据彩种加载相应标题 -->
<#if lotteryType??>
  	<#assign lotteryTitle=lotteryType.lotteryName>
  	<#if lotteryType=='SCZC'>
  		<#assign leftTopBannerClass='scjq'>
	<#elseif lotteryType=='LCZC'>  
		<#assign leftTopBannerClass='ncbq'>  
	<#elseif lotteryType=='PL'>  
		   <#if playType==0>
	      		<#assign leftTopBannerClass = 'pl3' />
	      		<#assign lotteryTitle='排列三'>
			<#elseif playType==1>
			   	<#assign leftTopBannerClass = 'pl5' />
			   	<#assign lotteryTitle='排列五'>
		   </#if>
	<#elseif lotteryType=='SEVEN'>  
			<#assign leftTopBannerClass='star7'> 
	<#elseif lotteryType=='WELFARE3D'>  
			<#assign leftTopBannerClass='fc3d'> 
	<#elseif lotteryType=='DLT'>  
	         <#assign lotteryTitle='大乐透'>
			<#assign leftTopBannerClass='dlt'> 
	<#elseif lotteryType=='SSQ'>  
		<#assign leftTopBannerClass='ssq'> 
	</#if>
	
</#if>


<!-- 针对有不同玩法彩种的判断,根据玩法加载相应标题(如：14场同九场) -->
<#if !playType?? && scheme??&&scheme.playType??>
  <#assign playType = scheme.playType />
</#if>
<#assign playTypeParameter = '' />
<#if playType??>
	<#assign playTypeStr = playType?string />
	<#if playTypeStr=='SFZC14'>
	    <#assign leftTopBannerClass='sfc'>
	    <#assign lotteryTitle='胜负彩'>
	  <#elseif playTypeStr=='SFZC9'>
	    <#assign leftTopBannerClass='r9'>
	    <#assign lotteryTitle='任选九场'>
	</#if>
	<#assign playTypeParameter = '&playType=${playType}' />
</#if>

      <div id="${leftTopBannerClass}_topbg">
        <div class="czlogochar">${lotteryTitle}</div>
	      <div class="rightqu">
	         <#if period??>
		        <div><span class="heiti16">当前销售：第<span class="shuzhieng16">${period.periodNumber}</span>期</span>&nbsp;&nbsp;
		            <#if period.lotteryType=='SSQ'>
		        	    <span class="dbrown font14char">(每周二、四、日晚 21：30开奖)</span>
		        	</#if>
		       
		        <#if endInitTime??>
			        <div class="dbrown top10px">
			               	  投注截止时间：${endInitTime?string('MM-dd HH:mm')}&nbsp;&nbsp;&nbsp;&nbsp;开奖时间：${period.prizeTime?string('MM-dd HH:mm')}
			        </div>
		        </#if>
		        <!--
		        <div class="top10px font14char dbrown">
		                              奖池累积：<span class="shuzhieng16 rebchar">￥294,170,505</span>元
		        </div>-->
		        </div> 
		     </#if>
	      </div>
      </div>
      <!-- navmenu begin -->
      <div class="navweizhi">
	       <div class="floatleft" style="width:480px;">
	         <ul class="tytopnav">
	          <li><a href="<@c.url value="/${lotteryType.key}/scheme!editNew.action?salesMode=COMPOUND${playTypeParameter}&menuType=0&shareType=SELF"/>" <#if menuType==0>class="now"</#if>>购买彩票</a></li>
	          <li><a href="<@c.url value="/${lotteryType.key}/scheme!list.action?salesMode=COMPOUND${playTypeParameter}&menuType=2"/>" <#if menuType==2>class="now"</#if>>参与合买</a></li>
	          <li><a href="<@c.url value="/${lotteryType.key}/result.action?menuType=3${playTypeParameter}"/>" <#if menuType==3>class="now"</#if>>开奖号码</a></li>
		      <li><a href="#" onclick="$SSO.login_auth(function(){window.location.href = '<@c.url value="/${lotteryType.key}/scheme!myList.action?menuType=4${playTypeParameter}"/>';});return false;" <#if menuType==4>class="now"</#if>>我的投注</a></li>
		      <li><a href="<@c.url value="/info/rule/rule.action?lottery=${lotteryType}&type=${playType}"/>" target="_blank" <#if menuType==4>class="now"</#if> rel="nofollow">玩法规则</a></li>
		     </ul>
	       </div>  
	       
	      <div class="floatrig rig5 top5px" id="show_count">
		      <#if period??&&endInitTime??>
		                                购买截止还有: <span id="count_d" class="timegreenchar">0</span>天<span id="count_h" class="timegreenchar">0</span>时<span id="count_i" class="timegreenchar">0</span>分<span id="count_s" class="timegreenchar">0</span>秒</span>
					<script type="text/javascript">
						var time = 0;
						var start_time = get_server_time();
						var end_time = new Date('${endInitTime?string('MM/dd/yyyy HH:mm:ss')}');
						var d_end_time = new Date('');
						var end_utc_time = Date.UTC(end_time.getYear(),end_time.getMonth(),end_time.getDate(),end_time.getHours(),end_time.getMinutes(),end_time.getSeconds());
						var start_utc_time = Date.UTC(start_time.getYear(),start_time.getMonth(),start_time.getDate(),start_time.getHours(),start_time.getMinutes(),start_time.getSeconds());
						
						var d_end_utc_time = Date.UTC(d_end_time.getYear(),d_end_time.getMonth(),d_end_time.getDate(),d_end_time.getHours(),d_end_time.getMinutes(),d_end_time.getSeconds());
						function jz_show_tips()
						{
						    $('#error_txt').show();
						}
						
						$(document).ready(
							function()
							{
							  run(start_utc_time,end_utc_time,jz_show_tips);
							}
						);
					</script>
			  </#if>
		  </div>
			  
       </div>
      <!-- navmenu end -->

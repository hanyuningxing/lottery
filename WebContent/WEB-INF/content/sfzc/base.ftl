<@override name="title">
	<#if playType=='SFZC14'>
		<title>足球彩票14场胜负预测、分析、网上购买投注 - ${webapp.webName}安全购彩平台</title>
		<meta name="Description" content="${webapp.webName}胜负彩在线投注服务，为广大彩民在线提供胜负彩14场网上合买、代购，手机胜负彩14场网上投注服务，全新的赔率数据，独到的场次分析，精准的比赛预测，是您购买足彩的重要法宝。" />
	<#elseif playType=='SFZC9'>
		<title>任选九场，任选9场网站购买投注 - ${webapp.webName}安全购彩平台</title>
		<meta name="Description" content="${webapp.webName}任选9场在线投注服务，为广大彩民在线提供任选九场网上投注，手机任选9场网上服务，强大的过滤软件和预测分析，让你中奖不再是难题。" />	
	</#if>
</@override>

<@override name="playTop">
	<#if playType=='SFZC14'>
		<#assign className='zc_r14_tzleftname'>
	<#elseif playType=='SFZC9'>
		<#assign className='zc_r9_tzleftname'>
	</#if>
	<div class="tzleftt" style="width:998px;">
      <div class="${className}">
        <div class="zc_choseqihao">
          <form action="<@c.url value="/${lotteryType.key}/scheme!editNew.action" />" method="get">
	          <select name="periodNumber" size="1" id="periodNumber" onchange="this.form.submit();">
	            <#list periods as p>
				   <#if p.periodNumberDisplay??>
					    <#if 1==p.periodNumberDisplay>
					    	<option value="${p.periodNumber!}" <#if period.id=p.id>selected</#if>>${p.periodNumber!}期(当前)</option>
					    <#elseif 2==p.periodNumberDisplay>
					         <option value="${p.periodNumber!}" <#if period.id=p.id>selected</#if>>${p.periodNumber!}期(预售)</option>
	                   	</#if>
	               <#else>
	                   	<option value="${p.periodNumber!}" <#if period.id=p.id>selected</#if>>${p.periodNumber!}期(历史)</option>
                   </#if>
				</#list>
	          </select>	          
	          <input type="hidden" name="salesMode" value="${salesMode}" />
			  <#if playType??>
			    <input type="hidden" name="playType" value="${playType}" />
			  </#if>
			  <#if shareType??>
			    <input type="hidden" name="shareType" value="${shareType}" />
			  </#if>
          </form>
        </div>
      </div>
      <div class="tzleft2"  >请对以下14场比赛任意选9长或以上的赛果进行竞猜，每场比赛至少竞猜一个赛果<br />如需投注更多竞彩赛事 <a href="#">可点击这里</a>
        <#assign playTypeStr='' />
        <#if playType??>
			<#assign playTypeStr='playType=${playType}&' />
		</#if>
        <div class="tzbtwz" style="padding-left:0px;"> 
	        <a href="${base}/${lotteryType.key}/${playType}-COMPOUND-${shareType!}.html" <#if salesMode?? && salesMode=='COMPOUND'>class="tzbttopnow"<#else>class="tzbttop"</#if>>复式投注</a> 
	        <a href="${base}/${lotteryType.key}/${playType}-SINGLE-${shareType!}.html" <#if salesMode?? && salesMode=='SINGLE' && !aheadOfUpload>class="tzbttopnow"<#else>class="tzbttop"</#if>>单式投注</a> 
	        <a href="${base}/${lotteryType.key}/${playType}-SINGLE-${shareType!}-true.html" <#if salesMode?? && salesMode=='SINGLE' && aheadOfUpload>class="tzbttopnow"<#else>class="tzbttop"</#if>>发起再上传</a>
	        <a href="${base}/${lotteryType.key}/scheme_subList-${playType}.html" class="tzbttop">参与合买</a> 
	        <a href="${base}/user/scheme!list.action" class="tzbttop">我的投注</a> 
	        <a href="${base}/info/rule/${lotteryType.key?upper_case}.html" class="tzbttop">玩法规则</a> 
        </div>
      </div>
    </div>
</@override>

<@override name="extraData">
	<input type="hidden" name="createForm.playType" value="${playType}"/>
</@override>

<@override name="explain">
	<div class="j9ts" style="width:958px;">
		<b>胜负彩彩种玩法介绍：</b>
		<p>
			14场胜负彩是国内发行的一种竞技型玩法彩种，比赛范围广，赛事丰富，是彩民热别是球迷朋友最忠实的彩种。
			<br>
			玩法简单只要猜对指定的14场比赛在90分钟内的比赛结果即可，每场比赛只有三个结果，既胜平负，竞猜容易，玩法简单。
			<br>
			胜负彩购买投注方式：
			<br>
			1、胜负彩复试投注：每场比赛可以选择多个赛果，最多可以选择3个，而胜负彩的单场最高奖金是500万，而这个投注方式在资金要求上相对比较宽松，对于资金宽裕的用户并不合适。
			<br>
			2、胜负彩单式上传：将固定格式的单式号码统一上传给系统进行投注。通过过滤软件的特殊处理，把概率较小的垃圾注给过滤掉，不仅帮用户节省了方案成本，更是让奖金的回报增加。 14场这种玩法来说，单场上传是极其重要的方法，也是其保证命中率的利器，对于大资金投入者来说，单式上传这种投注方式是首选。
		</p>
	</div>
</@override>

<@extends name="/WEB-INF/content/common/simple-scheme-editNew-zc.ftl"/>

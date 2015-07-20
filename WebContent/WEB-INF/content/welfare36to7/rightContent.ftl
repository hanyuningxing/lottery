 <script type="text/javascript" >
	function chg(value){
		if(value==0){
			$("#forecast").css("display","block");
			$("#skill").css("display","none");
		}else if(value==1){
			$("#skill").css("display","block");
			$("#forecast").css("display","none");
		}else if(value==2){
			$("#result").css("display","block");
			$("#info").css("display","none");
		}else if(value==3){
			$("#info").css("display","block");
			$("#result").css("display","none");
		}
				
	}
</script>
 
 <!-- 右边开始 -->
<#if newestPeriodData??&&newestPeriod??>
<#assign period=newestPeriod />
<#assign periodData=newestPeriodData />
<#assign lotteryType=period.lotteryType />
      <div class="rightkuangb1">
	   <div class="rigtopbg">
        <div class="lefttc1">好彩一开奖公告</div>
        <div class="righttc"><a href="${base}/${lotteryType.key}/result.action?menuType=3" class="graychar">历史开奖公告>></a></div>
        
        
      </div>
      <div class="kaijtishi">
        <div class="graychar"><span class="redboldchar">${lotteryType.lotteryName!}</span> 第<span class="orgchar">${period.periodNumber!}</span>期</div>
        <div class="top5px">开奖时间：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></div>
        <div style="padding:8px 0px;">
        		 <#if periodData.result??>
					<#assign resultArr = periodData.resultFormat?split(",") />
	                <ul class="kjball">
	                	<li class="blueballsingle">${resultArr[6]!}</li>
	                 	<li class="blueballsingle">${resultArr[7]!}</li>
	                 	<li class="blueballsingle">${resultArr[8]!}</li>
	                 	<li class="blueballsingle">${resultArr[9]!}</li>
	                </ul>
				</#if> 
        </div>
        <div class=" cleanboth top5px lineh20"> 本期销量：<#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元<br />
          <span class="rebchar">奖池累积： <#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if>元</span><br />
          开奖日期：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if><br />
        </div>
        <div class="top5px">
          <table width="100%" border="0" cellpadding="4" cellspacing="1" style="background:#e5e5e5;">
    <tr>
        <td align="center" bgcolor="#f0f0f0"><b>奖项</b></td>
        <td align="center" bgcolor="#f0f0f0"><b>中奖注数</b></td>

        <td align="center" bgcolor="#f0f0f0"><b>每注奖金</b></td>
    </tr>
    
     <tr id="item_6">
        <td align="center" bgcolor="#f9f9f9">好彩一数字</td>
        <td align="center" bgcolor="#f9f9f9" ><span id='bonus6_num'><#if periodData.winUnit.haocai1WinUnits??>${periodData.winUnit.haocai1WinUnits}</#if></span></td>
        <td align="center" bgcolor="#f9f9f9"  style="padding-right:10px;"><span id='bonus7_money' ><#if periodData.prize.haocai1Prize??>${periodData.prize.haocai1Prize}</#if> 元</span></td>
    </tr>
     <tr id="item_9">
        <td align="center" bgcolor="#f9f9f9">好彩一生肖</td>
        <td align="center" bgcolor="#f9f9f9" ><span id='bonus6_num'><#if periodData.winUnit.zodiacWinUnits??>${periodData.winUnit.zodiacWinUnits}</#if></span></td>
        <td align="center" bgcolor="#f9f9f9"  style="padding-right:10px;"><span id='bonus10_money' ><#if periodData.prize.zodiacPrize??>${periodData.prize.zodiacPrize}</#if> 元</span></td>
    </tr>
     <tr id="item_9">
        <td align="center" bgcolor="#f9f9f9">好彩一季节</td>
        <td align="center" bgcolor="#f9f9f9" ><span id='bonus6_num'><#if periodData.winUnit.seasonWinUnits??>${periodData.winUnit.seasonWinUnits}</#if></span></td>
        <td align="center" bgcolor="#f9f9f9"  style="padding-right:10px;"><span id='bonus11_money' ><#if periodData.prize.zodiacPrize??>${periodData.prize.seasonPrize}</#if> 元</span></td>
    </tr>
    <tr id="item_9">
        <td align="center" bgcolor="#f9f9f9">好彩一方位</td>
        <td align="center" bgcolor="#f9f9f9" ><span id='bonus6_num'><#if periodData.winUnit.azimuthWinUnits??>${periodData.winUnit.azimuthWinUnits}</#if></span></td>
        <td align="center" bgcolor="#f9f9f9"  style="padding-right:10px;"><span id='bonus11_money' ><#if periodData.prize.azimuthPrize??>${periodData.prize.azimuthPrize}</#if> 元</span></td>
    </tr>
</table>
          </div>
        </div>
    </div>
    <#if commonAnalyse??&&commonSingleAnalyse??>
	    <!-- 专家推荐-->
	  <div class="rigkuang" style="border-bottom:none;">
	    <div class="chuhaobg">
	      <div class="chuhaobglef">${lotteryName!}专家推荐</div>
	      <div class="chuhaobgrig"></div>
	    </div>
	    <!--zj 01-->
	    <div class="zhuanjia"> <img src="${base}/pages/images/zj01.jpg" />
	      <div class="zhuanjiawz">${lotteryName!}${period.periodNumber!}期推荐<br />
	        复式:<em class=" rebchar">${commonAnalyse!}</em><br />
	      </div>
	    </div>
	    <!--zj 01 end-->
	    <!--zj 02 -->
	    <div class="zhuanjia"> <img src="${base}/pages/images/zj02.jpg" />
	      <div class="zhuanjiawz">${lotteryName!}${period.periodNumber!}期推荐<br />
	        精选1注:<em class=" rebchar">${commonSingleAnalyse!}</em><br />
	      </div>
	    </div>
	    <!--zj 02 end-->
	    <div class="kong5"></div>
	  </div>
  </#if>
    <div class="kong10"></div>
    <div class="rightkuangb1">
      <div class="kaijtishi" style="padding-bottom:4px;">
        <div class="yelbgbt"><a href="${base}/${lotteryType.key}/analyse!w36to7zs.action" target="_blank" ><font color="#c61d01">${lotteryType.lotteryName!}数据分析图表</font></a></div>
        <div class="kong10"></div>
       <div class="kong10"></div>
        <ul class="graybt_menu">
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action" target="_blank" style="margin-right:6px;">${lotteryType.lotteryName!}综合走势图</a></li>
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action?type=szzst" target="_blank">${lotteryType.lotteryName!}数字走势图</a></li>
        </ul>
        <ul class="graybt_menu">
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action?type=sxzst" target="_blank" style="margin-right:6px;">${lotteryType.lotteryName!}生肖走势图</a></li>
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action?type=jjfwzst" target="_blank">季节方位走势图</a></li>
        </ul>
        <ul class="graybt_menu">
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action?type=wszst" target="_blank" style="margin-right:6px;">${lotteryType.lotteryName!}尾数走势图</a></li>
          <li><a href="${base}/welfare36to7/analyse!w36to7zs.action?type=wxzst" target="_blank">五行走势图</a></li>
        </ul>
        </div> 
    </div>
   <div class="kong10"></div>
       <div class="rightkuangb1">
	    <div class="chuhaobg">
	      <div class="chuhaobglef">${lotteryType.lotteryName!}文章分析</div>
	      <div class="chuhaobgrig"><a href="<@c.url value="/info/news!index.action"/>" target="_blank"  class="graychar">更多>></a></div>
	    </div>
        <ul class="gray_lab_sm" id="rigth_news_ul">
	      <li class="active" _name="rigth_news_li"><a href="<@c.url value="/info/news!index.action?lottery=${lotteryType}&infoType=FORECAST"/>" target="_blank"} onmouseover="chg_right_news(0);">预测</a></li>
	      <li _name="rigth_news_li"><a href="<@c.url value="/info/news!index.action?lottery=${lotteryType}&infoType=SKILLS"/>" target="_blank"} onmouseover="chg_right_news(1);">技巧</a></li>
	      <li _name="rigth_news_li"><a href="<@c.url value="/info/news!index.action?lottery=${lotteryType}&infoType=RESULTNOTICE"/>" target="_blank" onmouseover="chg_right_news(2);">开奖</a></li>
          <li _name="rigth_news_li"><a href="<@c.url value="/info/news!index.action?lottery=${lotteryType}&infoType=INFO"/>" target="_blank" onmouseover="chg_right_news(3);">热点</a></li>
	    </ul>  
	     <#import "../../macro/news.ftl" as news_macro />
		
		<div class="num_detail" style="display:none;" id="skill">
	      <table>
	        <tbody>
	         <#if skillsList?? && skillsList?size gt 0>
				<#list skillsList as data>
						     <tr>
					            <td><@news_macro.news_data news=data limit=35 class=''/></td>
					         </tr>
						</#list>
			  </#if>
	        </tbody>
	      </table>
	    </div> 
	    
		 <div class="num_detail" id="forecast">
	      <table>
	        <tbody>
	          <#if forecastList?? && forecastList?size gt 0>
					<#list forecastList as data> 
						     <tr>
					            <td><@news_macro.news_data news=data limit=35 class=''/></td>
					         </tr>
						</#list>
			  </#if>
	        </tbody>
	      </table>
	    </div> 
		<div class="num_detail" style="display:none;" id="result">
	      <table>
	        <tbody>
	          <#if topwon?? && topwon?size gt 0>
						<#list topwon as data>
						     <tr>
					            <td><@news_macro.news_data news=data limit=35 class=''/></td>
					         </tr>
						</#list>
			  </#if>
	        </tbody>
	      </table>
	    </div> 
		 
		
	    
	    <div class="num_detail" style="display:none;" id="info">
	      <table>
	        <tbody>
	          <#if infoList?? && infoList?size gt 0>
						<#list infoList as data>
						     <tr>
					            <td><@news_macro.news_data news=data limit=35 class=''/></td>
					         </tr>
						</#list>
			  </#if>
	        </tbody>
	      </table>
	    </div>
	    
	    
	    <!--table end-->
	  </div>
	  
    
    
	<div class="kong10"></div>
    <div class="ssqrigkuang1">
      <div class="chuhaobg">
        <div class="chuhaobglef">${lotteryType.lotteryName!}热门搜索</div>
      </div>
      <div class="right_neik">
		 <div class="cp_clear1"></div>	 
	  </div>
	 </div>    
</#if>



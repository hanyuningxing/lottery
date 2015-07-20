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
        <div class="lefttc"><h2>${lotteryType.lotteryName!}开奖公告</h2></div>
        <div class="righttc"><a href="${base}/${lotteryType.key}/result.action?menuType=3" class="graychar">历史开奖公告>></a></div>
      </div>
      <div class="kaijtishi">
        <div class="graychar"><span class="redboldchar">${lotteryType.lotteryName!}</span> 第<span class="orgchar">${period.periodNumber!}</span>期</div>
        <div class="top5px">开奖时间：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if></div>
        <div style="padding:8px 0px;">
          <ul class="kjball">
            <li class="redballsingle">${periodData.rsultArr[0]}</li>
            <li class="redballsingle">${periodData.rsultArr[1]}</li>
            <li class="redballsingle">${periodData.rsultArr[2]}</li>
            <li class="redballsingle">${periodData.rsultArr[3]}</li>
            <li class="redballsingle">${periodData.rsultArr[4]}</li>
            <li class="blueballsingle">${periodData.rsultArr[5]}</li>
            <li class="blueballsingle">${periodData.rsultArr[6]}</li>
          </ul>
        </div>
        <div class=" cleanboth top5px lineh20"> 本期销量：<#if periodData.totalSales??>${periodData.totalSales?string('###,###')}</#if> 元<br />
          <span class="rebchar">奖池累积： <#if periodData.prizePool??>${periodData.prizePool?string('###,###')}</#if>元</span><br />
          开奖日期：<#if period.prizeTime??>${period.prizeTime?string("yyyy-MM-dd")}</#if><br />
        </div>
        <div class="top5px">
          <table width="100%" border="0" cellspacing="1" cellpadding="3" class="tbbge5">
            <tr class="trgrayf0f0f0 boldchar" >
              <td>奖项</td>
              <td>中奖注数</td>
              <td>每注奖金</td>
            </tr>
            <tr class="trw">
              <td>一等奖 </td>
              <td><#if periodData.winUnit.firstWinUnits??>${periodData.winUnit.firstWinUnits}</#if></td>
              <td class=" redc"><#if periodData.prize.firstPrize??>${periodData.prize.firstPrize}</#if> 元</td>
            </tr>
            <tr class="trw">
              <td>二等奖</td>
              <td><#if periodData.winUnit.secondWinUnits??>${periodData.winUnit.secondWinUnits}</#if></td>
              <td class="redc"><#if periodData.prize.secondPrize??>${periodData.prize.secondPrize}</#if> 元</td>
            </tr>
          </table>
          </div>
        </div>
    </div>
    <div class="kong10"></div>
  <!-- 专家推荐-->
   <#if commonAnalyse??&&commonSingleAnalyse??>
	  <div class="rigkuang" style="border-bottom:none;">
	    <div class="chuhaobg">
	      <div class="chuhaobglef">${lotteryType.lotteryName!}专家推荐</div>
	      <div class="chuhaobgrig"></div>
	    </div>
	    <!--zj 01-->
	    <div class="zhuanjia"> <img src="${base}/pages/images/zj01.jpg" />
	      <div class="zhuanjiawz">${lotteryType.lotteryName!}${period.periodNumber!}期后区<em class="bluechar">${analyse!}</em><br />
	        前区:<em class=" rebchar">${commonAnalyse!}</em><br />
	        后区:<em class=" bluechar">${analyse!}</em><br />
	      </div>
	    </div>
	    <!--zj 01 end-->
	    <!--zj 02 -->
	    <div class="zhuanjia"> <img src="${base}/pages/images/zj02.jpg" />
	      <div class="zhuanjiawz">${lotteryType.lotteryName!}${period.periodNumber!}期后区<em class="bluechar">${singleAnalyse!}</em><br />
	        精选1注:<em class=" rebchar">${commonSingleAnalyse!}</em> + <em class="bluechar">${singleAnalyse!}</em><br />
	      </div>
	    </div>
	    <!--zj 02 end-->
	    <div class="kong5"></div>
	  </div>
  </#if>
  <!--专家推荐end-->
    <script type="text/javascript" src="<@c.url value= "/js/analyse/${lotteryType.key}/${lotteryType.key}_max_miss.js?_t=${randomStr}"/>"></script>
    <div class="kong10"></div>
    <div class="rightkuangb1">
      <div class="chuhaobg">
        <div class="chuhaobglef">号码冷热</div>
        <div class="chuhaobgrig"><a href="<@c.url value= "/${lotteryType.key}/analyse.action"/>"  target="_blank">更多</a></div>
      </div>
	    <div class="all5px">
	        <div class="left10">近200期内号码统计</div>
	        <div id="max_miss_div"></div>
	    </div>
	 </div>
	 <script type="text/javascript" language="javascript">
						var miss_data = null;
						if(typeof(${lotteryType.key}_max_miss)!="undefined"){ 
						     miss_data = ${lotteryType.key}_max_miss;
						}
						if(null!=miss_data){
					           jsonArr = eval(miss_data);
					           var htmlTemp ='';
					           htmlTemp = get_max_miss('前区',jsonArr['redb'],jsonArr['reds'],true);
					           htmlTemp = htmlTemp+get_max_miss('后区',jsonArr['blueb'],jsonArr['blues'],true);
					           var max_miss_div = document.getElementById('max_miss_div');
					           max_miss_div.innerHTML=htmlTemp;
					    } 
	</script>
	
    <div class="kong10"></div>
    <div class="rightkuangb1">
      <div class="kaijtishi" style="padding-bottom:4px;">
        <div class="yelbgbt"><a href="<@c.url value="/${lotteryType.key!}/analyse.action" />" target="_blank" ><font color="#c61d01">${lotteryType.lotteryName!}数据分析图表</font></a></div>
        <div class="kong10"></div>
        <ul class="graybt_menu">
          <li><a href="<@c.url value="/${lotteryType.key!}/analyse!zhzs.action" />" target="_blank" style="margin-right:6px;">${lotteryType.lotteryName!}前区走势图</a></li>
          <li><a href="<@c.url value="/${lotteryType.key!}/analyse!lqzs.action" />" target="_blank">${lotteryType.lotteryName!}后区走势图</a></li>
        </ul>
        <ul class="graybt_menu">
          <li><a href="<@c.url value="/${lotteryType.key!}/analyse!lhzs.action" />" target="_blank" style="margin-right:6px;">${lotteryType.lotteryName!}连号走势图</a></li>
          <li><a href="<@c.url value="/${lotteryType.key!}/analyse!chpl.action" />" target="_blank">开奖号频率走势图</a></li>
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
        <div class="chuhaobglef"><h2>${lotteryType.lotteryName!}中奖排行榜</h2></div>
      </div>
     <div class="right_neik">
		 <div class="cp_clear1">
		     <div id="topwonDiv" _name="won">
	          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tbbge5">
	            <tr class="trgrayf0f0f0 boldchar" >
	              <td width="105" height="20">用户名 </td>
	              <td>中奖金额</td>
	              <td width="65">制定跟单</td>
	            </tr>
	            <tr class="trw" id="topwon_0"></tr>
				<tr class="trw" id="topwon_1"></tr>
				<tr class="trw" id="topwon_2"></tr>
				<tr class="trw" id="topwon_3"></tr>
				<tr class="trw" id="topwon_4"></tr>
				<tr class="trw" id="topwon_5"></tr>
	          </table>
	        </div>
	        <script type="text/javascript" language="javascript">
	                var followUrl = window.BASESITE +"/lottery/auto!autoNew.action?lotteryType=${lotteryType}&lotteryPlayType=0&userId=";
			        try{
						$.ajax({ 
								type: 'GET',
								cache : false,
								dataType : 'json',
								url: window.BASESITE +'/js/analyse/${lotteryType.key}/topwon.js',
								success : function(jsonObj, textStatus) {
									try {
										var topwon= jsonObj.topwon;
										for ( var i = 0; i < topwon.length; i++) {
				                                      var topwon_ =  document.getElementById("topwon_"+i);
				                                      $(topwon_).empty();
				                                      var cell=topwon_.insertCell(0);  
										              cell.setAttribute("height","23"); 
										              cell.setAttribute("class","trw"); 
										              cell.innerHTML=topwon[i].userName;
										              
										              cell=topwon_.insertCell(1);  
										              cell.setAttribute("class","redc"); 
										              cell.innerHTML=topwon[i].bonus;
										              
										              cell=topwon_.insertCell(2);  
										              cell.innerHTML="<a href = \""+followUrl+topwon[i].userId+"\">制定</a>";
				
				                        }
										
									} catch (e) {
									}
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
								}
							});
					}catch(e){
					}
			</script>
		 </div>	 
	  </div>
	 </div>    
</#if>
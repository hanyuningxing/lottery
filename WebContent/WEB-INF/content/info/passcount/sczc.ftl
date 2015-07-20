<!--右边开始-->
    <div class="gg_k">
      <form action="<@c.url value="/passcount/index.action" />" method="get">
      <div class="gg_kj_top"> ${lottery.lotteryName!}第
         <select name="periodNumber" onchange="this.form.submit();">
                <#list issueList as issue>
          			<option value="${issue.periodNumber}" <#if periodNumber??&&periodNumber?string==issue.periodNumber?string>selected="selected"</#if>>${issue.periodNumber!}</option>
                </#list>
          </select>
          <input type="hidden" value="${lottery!}" name="lottery">期开奖结果
      </div>
      </form>
      <#if sczcData??&&sczcData.zcMatchs??>
      <div class="all10px">
        <div class="gg_left">
          <table width="465" height="94" border="0" cellpadding="0" cellspacing="1" class="gg_b trw">
            <tr align="center" class="gg_tr1">
              <td width="60" height="20" class="gg_tr2">场次</td>
	          <td>01</td>
	          <td>01</td>
	          <td>02</td>
	          <td>02</td>
	          <td>03</td>
	          <td>03</td>
	          <td>04</td>
	          <td>04</td>
            </tr>
            <tr align="center" class="trgray">
              <td height="55" class="gg_tr2">球队</td>
              <td>${sczcData.zcMatchs[0].homeTeamName!}</td>
	          <td>${sczcData.zcMatchs[0].guestTeamName!}</td>
	          <td>${sczcData.zcMatchs[1].homeTeamName!}</td>
	          <td>${sczcData.zcMatchs[1].guestTeamName!}</td>
	          <td>${sczcData.zcMatchs[2].homeTeamName!}</td>
	          <td>${sczcData.zcMatchs[2].guestTeamName!}</td>
	          <td>${sczcData.zcMatchs[3].homeTeamName!}</td>
	          <td>${sczcData.zcMatchs[3].guestTeamName!}</td>
            </tr>
            <tr class="trgray">
              <td height="20" align="center" class="gg_tr2">赛果</td>
              <td class="rebchar boldchar">${sczcData.zcMatchs[0].homeResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[0].guestResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[1].homeResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[1].guestResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[2].homeResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[2].guestResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[3].homeResult!}</td>
	          <td class="rebchar boldchar">${sczcData.zcMatchs[3].guestResult!}</td>
            </tr>
          </table>
        </div>
        
        <div class="gg_rig"> 一等奖：<span class="redboldchar"><#if sczcData.firstWinUnits??>${sczcData.firstWinUnits?string('###,###')}</#if></span> 注，每注奖金<span class="redboldchar"><#if sczcData.firstPrize??>${sczcData.firstPrize?string('###,###')}</#if></span>元<br />
           
        
          全国销量：<span class="redboldchar"><#if sczcData.totalSales??>${sczcData.totalSales?string('###,###')}</#if> </span>元<br />
          奖池滚存：<span class="redboldchar"><#if sczcData.prizePool??>${sczcData.prizePool?string('###,###')}</#if></span>元 </div>
      </div>
      </#if>  
      <div class="kong10"></div>
    </div>
    <div class="kong10"></div>
    <#assign baseUrl='/passcount/index.action?lottery=${lottery!}' />
    <#if periodNumber??>
        <#assign baseUrl=baseUrl+'&periodNumber=${periodNumber}' />
    </#if>
    <form action="<@c.url value="/passcount/index.action" />" method="get" id="passcountForm">
    <div class="ggnavwz">
      <ul class="ggnav">
        <li><a onclick="return checkIsLogin();" href="<@c.url value='${baseUrl}&mine=true&menuType=0' />" <#if menuType??&&menuType==0>class="now"</#if>>我的过关</a></li>
        <li><a href="<@c.url value='${baseUrl}&state=SUCCESS&menuType=1' />" <#if menuType??&&menuType==1>class="now"</#if>>全部成交</a></li>
        <li><a href="<@c.url value='${baseUrl}&mode=SINGLE&state=SUCCESS&menuType=2' />" <#if menuType??&&menuType==2>class="now"</#if>>单式成交</a></li>
        <li><a href="<@c.url value='${baseUrl}&mode=COMPOUND&state=SUCCESS&menuType=3' />" <#if menuType??&&menuType==3>class="now"</#if>>复式成交</a></li>
        <li><a href="<@c.url value='${baseUrl}&state=CANCEL&menuType=4' />" <#if menuType??&&menuType==4>class="now"</#if>>撤单方案</a></li>
      </ul>
    </div>
        <#if lottery??>
           <input type="hidden" value="${lottery!}" name="lottery">
        </#if>
        <#if state??>
           <input type="hidden" value="${state!}" name="state">
        </#if>
        <#if mode??>
           <input type="hidden" value="${mode!}" name="mode">
        </#if>
        <#if periodNumber??>
        <input type="hidden" value="${periodNumber!}" name="periodNumber">
        </#if>
	    <div class="ggnavt">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td width="85" align="right">方案搜索：</td>
	          <td width="160"><input class="txtbox" value="<#if searchStr??&&searchStr?string!=''>${searchStr!}<#else>输入发起姓名</#if>" name="searchStr" type="text" onclick="this.value=''"/></td>
	          <td width="80"><a href="javascript:void(0);" onclick="document.getElementById('passcountForm').submit();return false;"><img src="<@c.url value="/pages/images/btsearch.gif" />"/></a></td>
	          <td width="420"><input type="checkbox" name="won" value="true" <#if won??&&won>checked="checked"</#if> onclick="document.getElementById('passcountForm').submit();return false;"/>
	            只显示中奖方案</td>
	        </tr>
	      </table>
	    </div>
    </form>
    <table style="background:#fff; border-collapse:collapse; border:1px solid #E3E3E3;"  
bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" width="788" align="center" 
  border="1" >
      <tr class="center_tablegray" align="center" >
        <td width="35" height="28">排名 </td>
        <td width="115">发起人<br /></td>
         <td>方案号</td>
        <td>注数</td>
        <td>全对</td>
        <td>错一 </td>
        <td>错二 </td>
        <td>错三</td>
        <td>命中场次</td>
        <td>税前奖金</td>
        <td>详细</td>
      </tr>
      <#if pagination??&& (pagination.result![])?size gt 0>
        <#list pagination.result as data>
          <tr class="center_trw" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
               <td>${data_index+1}</td>
               <td>${data.sponsorName!}</td>
               <td><a href="<@c.url value='/sczc/scheme-${data.schemeNum!}.html' />" target="_blank">${data.schemeNum!}</td>
               <td>${data.units!}</td>
               <td><#if data.lost0Won??&&data.lost0Won&&data.won??&&data.won><font color="red">${data.lost0!}</font><#else>${data.lost0!}</#if></td>
               <td>${data.lost1!}</td>
               <td>${data.lost2!}</td>
               <td>${data.lost3!}</td>
               <td><#if data.won??&&data.won><font color="red">${data.passcount!}</font><#else>${data.passcount!}</#if></td>
               <td><font color="red">${data.prize!}</font></td>
               <td><a href="<@c.url value='/sczc/scheme-${data.schemeNum!}.html' />" target="_blank">查看</a></td>
        </#list>
        <#else> 
         <tr class="center_trw" align="center" onMouseOver="this.className='center_trhover'" onMouseOut="this.className='center_trw'">
            <td class="trw" align="center" colspan="10">无记录...</td>
          </tr>
      </#if>
    </table>
    <div class="kong10"></div>
    <!-- 版权开始 -->
    <div class=" cleanboth pagelist" align="center"> 
        <#import "../../../macro/pagination.ftl" as b />
        <@b.page />
    </div>
    <div class="kong10"></div>
</div>

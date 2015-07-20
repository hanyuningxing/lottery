<!--右边开始-->
    <div class="gg_k">
      <form action="<@c.url value="/passcount/index.action" />" method="get">
      <div class="gg_kj_top"> <#if pt??&&pt==0>胜负彩<#elseif pt??&&pt==1>任选九场</#if>第
         <select name="periodNumber" onchange="this.form.submit();">
                <#list issueList as issue>
          			<option value="${issue.periodNumber}" <#if periodNumber??&&periodNumber?string==issue.periodNumber?string>selected="selected"</#if>>${issue.periodNumber!}</option>
                </#list>
          </select>
          <input type="hidden" value="${lottery!}" name="lottery">
          <input type="hidden" value="${pt!}" name="pt">期开奖结果
      </div>
      </form>
      <#if sfzcData??&&sfzcData.zcMatchs??>
      <div class="all10px">
        <div class="gg_left">
          <table width="465" height="94" border="0" cellpadding="0" cellspacing="1" class="gg_b trw">
            <tr align="center" class="gg_tr1">
              <td width="60" height="20" class="gg_tr2">场次</td>
              <td>1</td>
              <td>2</td>
              <td>3</td>
              <td>4</td>
              <td>5</td>
              <td>6</td>
              <td>7</td>
              <td>8</td>
              <td>9</td>
              <td>10</td>
              <td>11</td>
              <td>12</td>
              <td>13</td>
              <td>14</td>
            </tr>
            <tr align="center" class="trgray">
              <td height="55" class="gg_tr2">主队</td>
              <td>${sfzcData.zcMatchs[0].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[1].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[2].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[3].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[4].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[5].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[6].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[7].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[8].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[9].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[10].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[11].homeTeamName!}</td>
	          <td>${sfzcData.zcMatchs[12].homeTeamName!}</td>   
	          <td>${sfzcData.zcMatchs[13].homeTeamName!}</td>          
            </tr>
            <tr class="trgray">
              <td height="20" align="center" class="gg_tr2">赛果</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[0].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[1].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[2].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[3].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[4].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[5].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[6].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[7].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[8].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[9].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[10].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[11].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[12].result!}</td>
              <td class="rebchar boldchar">${sfzcData.zcMatchs[13].result!}</td>
            </tr>
          </table>
        </div>
        <div class="gg_rig"> 一等奖：<span class="redboldchar"><#if pt??&&pt==0>${sfzcData.firstWinUnits_14!}<#elseif pt??&&pt==1>${firstWinUnits_9!}</#if></span> 注，每注奖金<span class="redboldchar"><#if pt??&&pt==0><#if sfzcData.firstPrize_14??>${sfzcData.firstPrize_14?string('###,###')}</#if><#elseif pt??&&pt==1><#if sfzcData.firstPrize_9??>${sfzcData.firstPrize_9?string('###,###')}</#if></#if></span>元<br />
       <#if pt??&&pt==0>
                      二等奖：<span class="redboldchar">${sfzcData.secondWinUnits_14!}</span>注，每注奖金<span class="redboldchar"><#if sfzcData.secondPrize_14??>${sfzcData.secondPrize_14?string('###,###')}</#if></span> 元<br />
       </#if>      
        
          全国销量：<span class="redboldchar"><#if pt??&&pt==0><#if sfzcData.totalSales_14??>${sfzcData.totalSales_14?string('###,###')}</#if><#elseif pt??&&pt==1><#if sfzcData.totalSales_9??>${sfzcData.totalSales_9?string('###,###')}</#if></#if> </span>元<br />
          奖池滚存：<span class="redboldchar"><#if pt??&&pt==0><#if sfzcData.prizePool_14??>${sfzcData.prizePool_14?string('###,###')}</#if><#elseif pt??&&pt==1><#if sfzcData.prizePool_14??>${sfzcData.prizePool_14?string('###,###')}</#if></#if></span>元 </div>
      </div>
      </#if>  
      <div class="kong10"></div>
    </div>
    <div class="kong10"></div>
    <#assign baseUrl='/passcount/index.action?lottery=${lottery!}' />
    <#if pt??>
        <#assign baseUrl=baseUrl+'&pt=${pt}' />
    </#if>
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
        <#if pt??>
            <input type="hidden" value="${pt!}" name="pt">
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
               <td><a href="<@c.url value='/sfzc/scheme-${data.schemeNum!}.html' />" target="_blank">${data.schemeNum!}</a></td>
               <td>${data.units!}</td>
               <td><#if data.lost0Won??&&data.lost0Won&&data.won??&&data.won><font color="red">${data.lost0!}</font><#else>${data.lost0!}</#if></td>
               <#if pt??&&pt==0>
                  <td><#if data.lost1Won??&&data.lost1Won&&data.won??&&data.won><font color="red">${data.lost1!}</font><#else>${data.lost1!}</#if></td>
                    <#else>
                    <td>${data.lost1!}</td>
               </#if>
               <td>${data.lost2!}</td>
               <td>${data.lost3!}</td>
               <td><#if data.won??&&data.won><font color="red">${data.passcount!}</font><#else>${data.passcount!}</#if></td>
               <td><font color="red">${data.prize!}</font></td>
               <td><a href="<@c.url value='/sfzc/scheme-${data.schemeNum!}.html' />" target="_blank">查看</a></td>
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
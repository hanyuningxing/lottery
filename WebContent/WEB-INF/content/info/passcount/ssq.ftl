<div class="secnav">
      <form action="<@c.url value="/passcount/index.action" />" method="get">
      <ul>
        <li class="title">◎双色球过关统计  </li> 
        <li class="r">
          <select name="periodNumber" onchange="this.form.submit();">
             <#if issueList??>
             <#list issueList as issue>
          		<option value="${issue.periodNumber}" <#if periodNumber??&&periodNumber?string==issue.periodNumber?string>selected="selected"</#if>>${issue.periodNumber!}</option>
             </#list>
             </#if>
          </select>
          <input type="hidden" value="${lottery!}" name="lottery">
        </li>
    <li class="r"><a href="javascript:void(0);">比赛结果查询</a></li>
      </ul>  
      </form>
    </div>
    <div class="fixture">
      <table border="1" bordercolor="#EF9E9E" cellspacing="0" cellpadding="0" class="leftside" style="border-collapse:collapse;">
        <tr>
          <td class="one">开奖号码</td>
          <td>${numberData.result!}</td>
        </tr>
        <tr class="time">
          <td colspan="2"><div class="tdleft">本期开售时间：<span class="fontcolor">${numberData.startTime!}</span></div><div class="tdright">本期停售时间：<span class="fontcolor">${numberData.endTime!}</span></div></td>
        </tr>
      </table>
      <table border="1" bordercolor="#EF9E9E" cellspacing="0" cellpadding="0" class="rightside" style="border-collapse:collapse;">
        <tr>
          <td width="80" align="right">本期销量：</td>
          <td width="100" align="left">${numberData.totalSales!}元</td>
        </tr>
        <tr>
          <td align="right">一等奖：</td>
          <td align="left">${numberData.firstPrize!}元</td>
        </tr>
        <tr>
          <td align="right">本期滚存：</td>
          <td align="left">${numberData.prizePool!}元</td>
        </tr>
      </table>
    </div>
    <#assign baseUrl='/passcount/index.action?lottery=${lottery!}' />
  <#if periodNumber??>
        <#assign baseUrl=baseUrl+'&periodNumber=${periodNumber}' />
  </#if>
    <div id="demography" class="ui-tabs ui-widget ui-widget-content ui-corner-all">      
      <form action="<@c.url value="/passcount/index.action" />" method="get">
        <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
          <li _name="demography_li" class="ui-state-default ui-corner-top ui-tabs-selected ui-state-active"><a href="<@c.url value='${baseUrl}' />"><font color="#555555">全部方案</font></a></li>
          <li _name="demography_li" class="ui-state-default ui-corner-top"><a href="<@c.url value='${baseUrl}&mode=SINGLE&state=SUCCESS' />"><font color="#555555">单式成功</font></a></li>
          <li _name="demography_li" class="ui-state-default ui-corner-top"><a href="<@c.url value='${baseUrl}&mode=COMPOUND&state=SUCCESS' />"><font color="#555555">复式成功</font></a></li>
          <li _name="demography_li" class="ui-state-default ui-corner-top"><a href="<@c.url value='${baseUrl}&mode=SINGLE&state=CANCEL' />"><font color="#555555">单式未成功</font></a></li>
          <li _name="demography_li" class="ui-state-default ui-corner-top"><a href="<@c.url value='${baseUrl}&mode=COMPOUND&state=CANCEL' />"><font color="#555555">复式未成功</font></a></li>
          <li _name="demography_li" class="ui-state-default ui-corner-top"><a onclick="return checkIsLogin();" href="<@c.url value='${baseUrl}&mine=true' />"><font color="#555555">我的过关</font></a></li>
          <li class="r_title"><input class="btn" type="submit"  value="搜索" /></li>
          <li class="r_title mr5"><input class="txtbox" value="<#if searchStr??&&searchStr?string!=''>${searchStr!}<#else>输入发起姓名</#if>" name="searchStr" type="text" onclick="this.value=''"/></li>
        </ul>
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
     </form>
      <#if mode??&&state??>
          <script type="text/javascript">
              var selectedClassName="ui-state-default ui-corner-top ui-tabs-selected ui-state-active";
              var unSelectedClassName="ui-state-default ui-corner-top";
        var selectedValue=0;
        var mode='${mode}';
        var state='${state}';
        if(null!=mode&&''!=mode&&null!=state&&''!=state){
                if('SINGLE'==mode){
                      if('SUCCESS'==state){
                        selectedValue=1;
                  }else if('CANCEL'==state){
                    selectedValue=3;   
                  }
                }else if('COMPOUND'==mode){
                      if('SUCCESS'==state){
                        selectedValue=2;
                  }else if('CANCEL'==state){
                    selectedValue=4;      
                  }
                }
        }
          var liList=$('#demography li[_name="demography_li"]');
        for(var i=0;i<liList.length;i++){
              if(i==selectedValue){
                  liList[i].className=selectedClassName;
              }else{
                  liList[i].className=unSelectedClassName;
              }
        }
      </script>
         <#else>
          <#if mine??&&mine>
            <script type="text/javascript">
               var selectedClassName="ui-state-default ui-corner-top ui-tabs-selected ui-state-active";
              var unSelectedClassName="ui-state-default ui-corner-top";
        var selectedValue=5;
              var liList=$('#demography li[_name="demography_li"]');
        for(var i=0;i<liList.length;i++){
              if(i==selectedValue){
                  liList[i].className=selectedClassName;
              }else{
                  liList[i].className=unSelectedClassName;
              }
        }
            </script>
            <#else>
             <script type="text/javascript">
               var selectedClassName="ui-state-default ui-corner-top ui-tabs-selected ui-state-active";
              var unSelectedClassName="ui-state-default ui-corner-top";
        var selectedValue=0;
              var liList=$('#demography li[_name="demography_li"]');
        for(var i=0;i<liList.length;i++){
              if(i==selectedValue){
                  liList[i].className=selectedClassName;
              }else{
                  liList[i].className=unSelectedClassName;
              }
        }
          </script>
          </#if>
      </#if>
       <div id="tabs_drawalottery-1">
      <table border="1" cellspacing="0" cellpadding="0" bordercolor="#EF9E9E" style="border-collapse:collapse;" class="main b_top" width="100%" >
        <thead class="b_top">
          <tr>                                        
            <td class="b_top pl5" colspan="14" height="25">
             <#if numberData??>
                   更新时间：<span class="fontcolor">${numberData.updateTime!}</span>   
             </#if>
            </td>          
          </tr>   
          <tr class="head">                                        
            <td width="40">排名</td>
            <td width="100">发起人</td>
            <td width="72">方案号</td>
            <td width="60">注数</td>
            <td width="60">倍数</td>
            <td width="60">一等<br/>注数</td>
            <td width="60">二等<br/>注数</td>
            <td width="60">三等<br/>注数</td>
            <td width="60">四等<br/>注数</td>
            <td width="60">五等<br/>注数</td>
            <td width="60">六等<br/>注数</td>
            <td width="50">状态</td>
            <td width="40">跟单</td>
          </tr>
        </thead>
      <tbody>
      <#if pagination??&& (pagination.result![])?size gt 0>
        <#list pagination.result as data>
        <#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
          <tr>
             
                <td>${data.id!}</td>
                <td><#if data.canSee??&&data.canSee>${data.sponsorName!}<#else>****</#if></td>
                <td><#if data.canSee??&&data.canSee><a href="<@c.url value='/ssq/scheme!show.action?schemeNumber=${data.schemeNum!}' />" target="_blank">${data.schemeNum!}</a><#else>****</#if></td>
                <td><#if data.canSee??&&data.canSee>${data.units!}<#else>****</#if></td>
                <td><#if data.canSee??&&data.canSee>${data.multiple!}<#else>****</#if></td>
                <td><#if data.firstWinUnits??&&data.firstWinUnitsWon??&&data.firstWinUnitsWon><font color="red">${data.firstWinUnits!}</font><#else>${data.firstWinUnits!}</#if></td>
                <td><#if data.secondWinUnits??&&data.secondWinUnitsWon??&&data.secondWinUnitsWon><font color="red">${data.secondWinUnits!}</font><#else>${data.secondWinUnits!}</#if></td>
                <td><#if data.thirdWinUnits??&&data.thirdWinUnitsWon??&&data.thirdWinUnitsWon><font color="red">${data.thirdWinUnits!}</font><#else>${data.thirdWinUnits!}</#if></td>
                <td><#if data.fourthWinUnits??&&data.fourthWinUnitsWon??&&data.fourthWinUnitsWon><font color="red">${data.fourthWinUnits!}</font><#else>${data.fourthWinUnits!}</#if></td>
                <td><#if data.fifthWinUnits??&&data.fifthWinUnitsWon??&&data.fifthWinUnitsWon><font color="red">${data.fifthWinUnits!}</font><#else>${data.fifthWinUnits!}</#if></td>
                <td><#if data.sixthWinUnits??&&data.sixthWinUnitsWon??&&data.sixthWinUnitsWon><font color="red">${data.sixthWinUnits!}</font><#else>${data.sixthWinUnits!}</#if></td>
                <td><#if data.state??>${data.state.stateName!}</#if></td>
                <td><a href="<@c.url value='/center.html?26$${data.sponsorName!}' />" target="_blank">定制</a></td>
              </tr>
        </#list>
        <#else> 
          <tr>
            <td class="trw" align="center" colspan="4">无记录</td>
          </tr>
      </#if>
        </tbody>
        </table>
        <#import "/WEB-INF/macro/pagination.ftl" as b />
        <@b.page />
    </div>
    </div>
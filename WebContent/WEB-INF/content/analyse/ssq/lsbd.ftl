<head>
	<title>${lotteryType.lotteryName}历史开奖数据查询比对</title>
	<meta name="decorator" content="analyse" />
	<link href="<@c.url value="/pages/css/db.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value="/js/common.js"/>"></script>
</head>
<div class="main980">
        <#assign menu='lsbd'/>
        <#include "top.ftl"/>
    
	<div id="duibi">
        <form action="<@c.url value="/ssq/analyse!lsbd.action" />" method="post" >
        <div class=" cleanboth topbgty">任意输入 <span class="redchar">双色球</span> 奖号，跟历史开奖的号码对比看看，看看你选的号码是否有开出过；查询：
        <select name="count" size="1" id="select">
        <option value="50" <#if count==50>selected</#if> >最近50期</option>
        <option value="100" <#if count==100>selected</#if>>最近100期</option>
        <option value="200" <#if count==200>selected</#if>>最近200期</option>
      </select></div>
    <div class="tybanbg">
      <div class="floatleft"> <strong>双色球奖号对比</strong>&nbsp;&nbsp;|&nbsp;&nbsp;输入号码：
        <input name="red1" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red1!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red2" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red2!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red3" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red3!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red4" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red4!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red5" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red5!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red6" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red6!}" size="2"/>
        &nbsp;+&nbsp;
        <input name="blue" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${blue!}" size="2"/>
      </div>
      <div class="floatleft left10"><input type="submit" class="btsearchn" value="" /></div>
        </form>
    </div>
    <div class="kong5"></div>
    <table width="980" border="0" cellspacing="0" cellpadding="1" class="b1">
      <tr class="trtitlebg" height="25">
        <td width="100">期号</td>
        <td width="120">开奖时间 </td>
        <td width="90">红1</td>
        <td width="90">红2</td>
        <td width="90">红3</td>
        <td width="90">红4</td>
        <td width="90">红5</td>
        <td width="90">红6</td>
        <td width="100" class="bluecharnor">蓝球</td>
        <td >对比</td>
      </tr>
      <#list list as item>
      <#assign results=item.rsultArr />
      <#assign redHit=0 />
      <#assign blueHit=0 />
      <tr class="<#if item_index%2==0>trw<#else>trgray</#if>" height="24" onmouseover="this.className='trhover'" onmouseout="this.className='trw'">
        <td>${periodMap.get(item.periodId).periodNumber!}</td>
        <td>${periodMap.get(item.periodId).prizeTime?string('yyyy-MM-dd HH:ss')}</td>
        <td><span class="<#if action.isContain(results[0])>redchar<#assign redHit=redHit+1 /></#if>">${results[0]}</span></td>
        <td><span class="<#if action.isContain(results[1])>redchar<#assign redHit=redHit+1 /></#if>">${results[1]}</span></td>
        <td><span class="<#if action.isContain(results[2])>redchar<#assign redHit=redHit+1 /></#if>">${results[2]}</span></td>
        <td><span class="<#if action.isContain(results[3])>redchar<#assign redHit=redHit+1 /></#if>">${results[3]}</span></td>
        <td><span class="<#if action.isContain(results[4])>redchar<#assign redHit=redHit+1 /></#if>">${results[4]}</span></td>
        <td><span class="<#if action.isContain(results[5])>redchar<#assign redHit=redHit+1 /></#if>">${results[5]}</span></td>
        <td><span class="<#if blue==results[6]>bluecharnor<#assign blueHit=blueHit+1 /></#if>">${results[6]}</span></td>
        <td>${redHit}+${blueHit}</td>
        ${action.updateKey(redHit+'_'+blueHit)}
      </tr>
      </#list>
    </table>
    <div class="kong10"></div>
    <table width="100%" border="0" cellspacing="1" cellpadding="0" class="b1">
      <tr class="trtitlebg" height="25">
        <td>对比</td>
        <td>6+1</td>
        <td>6+0</td>
        <td>5+1</td>
        <td>5+0</td>
        <td>4+1</td>
        <td>4+0</td>
        <td>3+1</td>
        <td>3+0</td>
        <td>2+1</td>
        <td>2+0</td>
        <td>1+1</td>
        <td>1+0</td>
        <td>0+1</td>
        <td>0+0</td>
      </tr>
      <tr class="trgray" height="25">
        <td>次数 </td>
        <td>${(keyMap.get('6_1'))!0}</td>
        <td>${(keyMap.get('6_0'))!0}</td>
        <td>${(keyMap.get('5_1'))!0}</td>
        <td>${(keyMap.get('5_0'))!0}</td>
        <td>${(keyMap.get('4_1'))!0}</td>
        <td>${(keyMap.get('4_0'))!0}</td>
        <td>${(keyMap.get('3_1'))!0}</td>
        <td>${(keyMap.get('3_0'))!0}</td>
        <td>${(keyMap.get('2_1'))!0}</td>
        <td>${(keyMap.get('2_0'))!0}</td>
        <td>${(keyMap.get('1_1'))!0}</td>
        <td>${(keyMap.get('1_0'))!0}</td>
        <td>${(keyMap.get('0_1'))!0}</td>
        <td>${(keyMap.get('0_0'))!0}</td>
      </tr>
    </table>
    <div class="kong10"></div>
    <div class="lineh20" align="center"> *从往期的资料库中查询和您选择的号码对照有相同号码的期数，默认为最近一期的号码。<br />
      *查询全部期数因为页面很大，显示需要时间比较长，请耐心等待．</div>
  </div>
</div>
</div>
<#include "../bottom_common.ftl"/>
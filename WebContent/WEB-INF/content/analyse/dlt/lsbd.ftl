<head>
	<title>${lotteryType.lotteryName}历史开奖数据查询比对走势图</title>
	<meta name="decorator" content="analyse" />
	<link href="<@c.url value="/pages/css/db.css"/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<@c.url value="/js/common.js"/>"></script>
</head>
<div class="main980">
        <#assign menu='lsbd'/>
        <#include "top.ftl"/>
    
	<div id="duibi">
        <form action="<@c.url value="/dlt/analyse!lsbd.action" />" method="post" >
        <div class=" cleanboth topbgty">任意输入 <span class="redchar">大乐透</span> 奖号，跟历史开奖的号码对比看看，看看你选的号码是否有开出过；查询：
        <select name="count" size="1" id="select">
        <option value="50" <#if count==50>selected</#if> >最近50期</option>
        <option value="100" <#if count==100>selected</#if>>最近100期</option>
        <option value="200" <#if count==200>selected</#if>>最近200期</option>
      </select></div>
    <div class="tybanbg">
      <div class="floatleft"> <strong>大乐透奖号对比</strong>&nbsp;&nbsp;|&nbsp;&nbsp;输入号码：
        <input name="red1" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red1!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red2" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red2!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red3" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red3!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red4" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red4!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="red5" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${red5!}" size="2"/>
        &nbsp;+&nbsp;
        <input name="blue1" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${blue1!}" size="2"/>
        &nbsp;-&nbsp;
        <input name="blue2" onkeydown="number_check(this,event,0);" maxlength="2" type="text" class="input1" id="textfield" value="${blue2!}" size="2"/>
      </div>
      <div class="floatleft left10"><input type="submit" class="btsearchn" value="" /></div>
        </form>
    </div>
    <div class="kong5"></div>
    <table width="980" border="0" cellspacing="0" cellpadding="1" class="b1">
      <tr class="trtitlebg" height="25">
        <td width="100">期号</td>
        <td width="120">开奖时间 </td>
        <td width="90">前1</td>
        <td width="90">前2</td>
        <td width="90">前3</td>
        <td width="90">前4</td>
        <td width="90">前5</td>
        <td width="90" class="bluecharnor">后1</td>
        <td width="100" class="bluecharnor">后2</td>
        <td >对比</td>
      </tr>
      <#assign redArr=[red1,red2,red3,red4,red5,blue1,blue2] />
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
        <td><span class="<#if action.isBlueContain(results[5])>bluecharnor<#assign blueHit=blueHit+1 /></#if>">${results[5]}</span></td>
        <td><span class="<#if action.isBlueContain(results[6])>bluecharnor<#assign blueHit=blueHit+1 /></#if>">${results[6]}</span></td>
        <td>${redHit}+${blueHit}</td>
        ${action.updateKey(redHit+'_'+blueHit)}
      </tr>
      </#list>
    </table>
    <div class="kong10"></div>
    <table width="100%" border="0" cellspacing="1" cellpadding="0" class="b1">
      <tr class="trtitlebg" height="25">
        <td>对比</td>
        <td>5+2</td>
        <td>5+1</td>
        <td>5+0</td>
        <td>4+2</td>
        <td>4+1</td>
        <td>4+0</td>
        <td>3+2</td>
        <td>3+1</td>
        <td>3+0</td>
        <td>2+2</td>
        <td>2+1</td>
        <td>2+0</td>
        <td>1+2</td>
        <td>1+1</td>
        <td>1+0</td>
        <td>0+2</td>
        <td>0+1</td>
        <td>0+0</td>
      </tr>
      <tr class="trgray" height="25">
        <td>次数 </td>
        <td>${(keyMap.get('5_2'))!0}</td>
        <td>${(keyMap.get('5_1'))!0}</td>
        <td>${(keyMap.get('5_0'))!0}</td>
        <td>${(keyMap.get('4_2'))!0}</td>
        <td>${(keyMap.get('4_1'))!0}</td>
        <td>${(keyMap.get('4_0'))!0}</td>
        <td>${(keyMap.get('3_2'))!0}</td>
        <td>${(keyMap.get('3_1'))!0}</td>
        <td>${(keyMap.get('3_0'))!0}</td>
        <td>${(keyMap.get('2_2'))!0}</td>
        <td>${(keyMap.get('2_1'))!0}</td>
        <td>${(keyMap.get('2_0'))!0}</td>
        <td>${(keyMap.get('1_2'))!0}</td>
        <td>${(keyMap.get('1_1'))!0}</td>
        <td>${(keyMap.get('1_0'))!0}</td>
        <td>${(keyMap.get('0_2'))!0}</td>
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
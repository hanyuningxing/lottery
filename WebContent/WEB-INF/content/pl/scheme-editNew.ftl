<head>
	<title>发起页面</title>
</head>

<div>
${period.periodNumber}期
</div>
<div>
<form action="${base}/welfare36to7/scheme!create.action" method="post">
期ID:<input name="createForm.periodId" value="${period.id}" /><br/>

投注方式:
<#assign saleModeArr=stack.findValue("@com.cai310.lottery.common.SalesMode@values()") />
<#assign defalutMode=saleModeArr[0] />
<#list saleModeArr as mode>
${mode.modeName}:<input type="radio" name="createForm.mode" <#if defalutMode==mode>checked="checked"</#if> value="${mode}"/>
</#list>
<br/>

保密类型:
<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
<#assign defalutType=secretTypeArr[0] />
<#list secretTypeArr as type>
${type.secretName}:<input type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/>
</#list>
<br/>

先发起后上传：<input type="checkbox" name="createForm.aheadOfUploadContent" value="true" /><br/>
方案内容:<br/>
复式示例：10:01,02;03,04,05,06,07|01,02&nbsp;&nbsp;&nbsp;&nbsp;(注数:红球胆码;红球|蓝球)<br/>
单式示例：01,02,03,04,05,06,01<br/>
<textarea rows="6" cols="60" name="createForm.content"></textarea>
<br/>

方案注数:<input name="createForm.units" value="" /><br/>
方案倍数:<input name="createForm.multiple" value="1" /><br/>
方案金额:<input name="createForm.schemeCost" value="" /><br/>

分享方式:
<#assign shareTypeArr=stack.findValue("@com.cai310.lottery.common.ShareType@values()") />
<#assign defalutType=shareTypeArr[0] />
<#list shareTypeArr as type>
${type.shareName}:<input type="radio" name="createForm.shareType" <#if defalutType==type>checked="checked"</#if> value="${type}"/>
</#list>
<br/>

认购许可方式:
<#assign licenseTypeArr=stack.findValue("@com.cai310.lottery.common.SubscriptionLicenseType@values()") />
<#assign defalutType=licenseTypeArr[0] />
<#list licenseTypeArr as type>
${type.licenseName}:<input type="radio" name="createForm.subscriptionLicenseType" <#if defalutType==type>checked="checked"</#if> value="${type}"/>
</#list>
认购密码:<input name="createForm.subscriptionPassword" value="" /><br/>
<br/>

发起人佣金率:<input name="createForm.commissionRate" value="" /><br/>
最低认购金额:<input name="createForm.minSubscriptionCost" value="" /><br/>
认购金额:<input name="createForm.subscriptionCost" value="" /><br/>
保底金额:<input name="createForm.baodiCost" value="" /><br/>


方案描述:<br/>
<textarea rows="4" cols="60" name="createForm.description"></textarea>
<br/>
<br/>
<hr/>
追号信息：<br/>
是否追号：<input type="checkbox" name="createForm.chase" value="true" /><br/>
追号金额：<input name="createForm.totalCostOfChase" value="" /><br/>
追号期数：<input name="createForm.periodSizeOfChase" value="5" /><br/>
追号倍数：
<input name="createForm.multiplesOfChase" value="1" />
<input name="createForm.multiplesOfChase" value="1" />
<input name="createForm.multiplesOfChase" value="1" />
<input name="createForm.multiplesOfChase" value="1" />
<input name="createForm.multiplesOfChase" value="1" /><br/>
机选追号：<input type="checkbox" name="createForm.randomOfChase" value="true" /><br/>
机选注数：<input name="createForm.randomUnitsOfChase" value="1" /><br/>
是否设胆：<input type="checkbox" name="createForm.hasDanOfChase" value="true" /><br/>
红球胆码<input name="createForm.redDanOfChase" value="" /><br/>
蓝球胆码<input name="createForm.blueDanOfChase" value="" /><br/>
中奖后停追：<input type="checkbox" name="createForm.wonStopOfChase" value="true" /><br/>
奖金大于<input name="createForm.prizeForWonStopOfChase"/>才停追<br/>
<br/><br/>
<input type="submit" value="提交" />
</form>
</div>

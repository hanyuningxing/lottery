<head>
	<title>个人资料</title>
	<meta name="decorator" content="userCenter" />
	<meta name="menu" content="userinfo" />
</head> 
    
<#assign user=loginUser />
 
<#include "/common/message.ftl" />
<div id="tab_body">
  <form action="<@c.url value="/user/user!updateInfo.action" />" method="post" style="padding:0;margin:0;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="min-width:800px;">
    <tr class="trhang">
      <td class="tdbiaoti" colspan="3">修改个人资料 <span class="fontcolor">温馨提示：为了给您提供更好的服务，请及时完善您的个人资料信息！</span></td>
    </tr>
    <tr>
      <td class="tdleft">用户名：</td>
      <td class="tdcenter"><font color="red"><b>${user.userName}</b></font></td>
      <td class="tdright">你的账号</td>
    </tr>
    <tr>
      <td class="tdleft">真实姓名：</td>
      <td class="tdcenter">
	      <#if canUpdateReal??&&canUpdateReal><input class="txtbox color_fff" name="infoForm.realName" maxlength="20" />
	     	 <#else>${infoForm.realName!}<input type="hidden" name="infoForm.realName" value="${infoForm.realName!}"/>
	      </#if>
      </td>
      <td class="tdright">&nbsp;</td>
    </tr>
    <tr>
      <td class="tdleft">身份证：</td>
      <td class="tdcenter">
	      <#if canUpdateIdCard??&&canUpdateIdCard><input class="txtbox color_fff" name="infoForm.idCard" maxlength="20" />
	     	 <#else>${infoForm.idCard!}<input type="hidden" name="infoForm.idCard" value="${infoForm.idCard!}"/>
	      </#if>
      </td>
      <td class="tdright">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" style="padding-left:130px; padding-bottom:3px;">
         <#if (canUpdateReal??&&canUpdateReal)||(canUpdateIdCard??&&canUpdateIdCard)>
         <input type="submit" value="提交" class="btn color_fff" />
         <#else>您已经绑定真实姓名和身份证。如需修改请联系客服
	      </#if>
      </td>
    </tr>
  </table>
  </form>
  <form action="<@c.url value="/user/user!updateBank.action" />" method="post">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" style="min-width:800px;">
    <tr class="trhang">
      <td class="tdbiaoti" colspan="3">修改银行卡资料</td>
    </tr>
    <tr>
      <td class="tdleft">银行卡开户银行：</td>
       <td class="tdcenter">
      	   <#if canUpdateBank??&&canUpdateBank><input class="txtbox color_fff" name="bankForm.bankName" maxlength="20" />
	     	 <#else>${bankForm.bankName!}
	      </#if>
       </td>
      <td class="tdright">&nbsp;</td>
    </tr>
    <tr>
      <td class="tdleft">银行卡号码：</td>
       <td class="tdcenter">
      	   <#if canUpdateBank??&&canUpdateBank><input class="txtbox color_fff" name="bankForm.bankCard" maxlength="20" />
	     	 <#else>${bankForm.bankCard!}
	      </#if>
       </td>
      <td class="tdright">必须输入数字无空格非全角字符</td>
    </tr>
    <#if canUpdateBank??&&canUpdateBank>
	    <tr>
	      <td class="tdleft">请输入密码：</td>
	      <td class="tdcenter"><input class="txtbox color_fff" type="password" name="bankForm.password" value="<#if bankForm??>${bankForm.password!}</#if>" /></td>
	      <td class="tdright">为了您的帐户安全，修改银行卡资料请输入密码（<font color="red"><strong>注：是购彩账户密码，非银行卡密码</strong></font>）</td>
	    </tr>
    <#else>
	</#if>
    <tr>
      <td colspan="3" style="padding-left:130px; padding-bottom:3px;">
     	<#if canUpdateBank??&&canUpdateBank>
         <input type="submit" value="提交" class="btn color_fff" />
         <#else>您已经绑定银行信息。如需修改请联系客服
	      </#if>
      </td>
    </tr>
  </table>
  </form>
</div>
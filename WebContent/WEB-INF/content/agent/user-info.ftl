<meta name="decorator" content="agent" />
<meta name="menu" content="user" />
<style type="text/css">
</style>
        <div class="left_title">
         <form name="updateForm" action="${base}/agent/user!updateInfo.action" method="post">
       <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">个人资料</td>
              </tr>
            </table>
</div>
      <div class="right_c">
      <div class="tx"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <td style="text-align:left"><#include "${base}/common/message.ftl" /></td>
	     </tr>
           <table width="90%" border="0" cellspacing="0" cellpadding="0" class="form_table">
 
  <tr>
    <th width="20%" scope="row">用户名：</th>
    <td width="80%"><#if loginUser??>${loginUser.userName!}</#if></td>
  </tr>
  <tr>
    <th scope="row">手机号：</th>
    <td>
    	<#if user.info??&&user.info.mobile??&&user.info.mobile!="">
				      	 <input style="width:144px" class="input_01" type="text" disabled="disabled"  value="${user.info.mobile?substring(0,3)}****${user.info.mobile?substring(7)}">(注:为了校验安全,手机号码补充完后不给修改)
		<#else>
				         <input style="width:144px" class="input_01" type="text" id="phoneInput" name="regForm.phoneNumber" value="<#if regForm??>${regForm.phoneNumber!}</#if>">(注:为了校验安全,手机号码补充完后不给修改)
		</#if>
	</td>
  </tr>
  <tr>
    <th scope="row">真实姓名：</th>
    <td>
      <#if user.info??&&user.info.realName??&&user.info.realName!="">
      	<input class="input_01" type="text" style="width:144px" id="realNameInput" name="regForm.realName" disabled="disabled" value="<#if user.info??&&user.info.realName??>${user.info.realName!}</#if>">(注:为了校验安全,真实姓名补充完后不给修改)
      <#else>
      	<input class="input_01" type="text" style="width:144px" id="realNameInput" name="regForm.realName" value="<#if regForm??>${regForm.realName!}</#if>">(注:为了校验安全,真实姓名补充完后不给修改)
	  </#if>
    </td>
  </tr>
  <tr>
    <th scope="row">身份证：</th>
    <td>
    	<#if user.info??&&user.info.idCard??&&user.info.idCard!=""&&user.info.idCard?length gte 10>
			<input  style="width:144px" class="input_01" type="text" disabled="disabled"  value="${user.info.idCard?substring(0,10)}**********">(注:为了校验安全,身份证号补充完后不给修改)
		<#else>
			<input  style="width:144px" class="input_01" type="text" id="idCardInput" name="regForm.idCard" value="<#if regForm??>${regForm.idCard!}</#if>">(注:为了校验安全,身份证号补充完后不给修改)
		</#if>
    </td>
  </tr>
   <tr>
    <th scope="row">电子邮箱：</th>
    <td>
    	<#if user.info??&&user.info.email??&&user.info.email!="">
			<input style="width:144px" class="input_01" type="text" disabled="disabled"  value="${user.info.email?substring(0,3)}****${user.info.email?substring(7)}">(注:为了校验安全,补充完后不给修改)
		<#else>
			<input style="width:144px" class="input_01" type="text" id="emailInput" name="regForm.email" <#if user.validatedEmail??&&user.validatedEmail=1>disabled="disabled"</#if> value="<#if regForm??>${regForm.email!}</#if>">(注:为了校验安全,补充完后不给修改)		 
		</#if>
    </td>
  </tr>
  <tr>
    <th colspan="2" scope="row"><div class="but_b"><input type="submit" style="width:90px" class="input_03" value="确定修改" /></div></th>
    </tr>
</table>
</form>
     </div>
</div>      
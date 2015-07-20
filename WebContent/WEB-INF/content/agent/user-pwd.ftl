<meta name="decorator" content="agent" />
<meta name="menu" content="user" />
<style type="text/css">
</style>
        <div class="left_title">
        <form name="updateForm" action="${base}/agent/user!updatePwd.action" method="post">
       <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">修改密码</td>
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
    <th scope="row">当前用户密码：</th>
    <td><input  name="regForm.oldpassword" type="password" class="input_01" style="width:220px" value=""/><font color="#FF0000">*</font></td>
  </tr>
  <tr>
    <th scope="row">新密码：</th>
    <td><input type="password"  name="regForm.password" class="input_01" style="width:220px" value=""/><font color="#FF0000">*</font></td>
  </tr>
  <tr>
    <th scope="row">确认新密码：</th>
    <td><input type="password"  name="regForm.confirmPassword" class="input_01" style="width:220px" value=""/><font color="#FF0000">*</font></td>
  </tr>
  <tr>
    <th colspan="2" scope="row"><div class="but_b"><input type="submit" style="width:90px" class="input_03" value="确定修改" /></div></th>
  </tr>
</table>
</form>
     </div>
</div>      
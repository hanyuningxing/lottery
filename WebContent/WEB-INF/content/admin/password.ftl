<head>
	<title>修改密码</title>
	<meta name="menu" content="member"/>
	<meta name="menuItem" content="password"/>
</head>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">修改密码</span></div>
</div>
<form name="userForm" action="${base}/admin/password!doPasswordEdit.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
	           <table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">原密码：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input name="oldPassword" type="password" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">新密码：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                  <input name="newPassword" type="password" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">确认新密码：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                  <input name="confirmPassword" type="password" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#FFFFFF">
			                 <input type="image" src="${base}/images/comfirm.gif" />
			              </td>
			            </tr>
			          </table>
</form>
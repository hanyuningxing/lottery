<title>后台用户管理</title>
<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="user"/>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/security/user.action">彩票后台用户管理</a> → <a href="${base}/admin/security/user.action">后台用户管理</a>  <a href="${base}/adminLogin.jsp">退出登录</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">后台用户管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/security/user.action?entity.id=-1"><b>新增用户</b></a>
  </div>
</div>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>名称</td>
      <td>真实姓名</td>
      <td>角色</td>
      <td>状态</td>
      <td>编辑</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as adminUser>
    	<#if adminUser_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${adminUser.id}</td>
	      <td>${adminUser.loginName}</td>
	      <td>${adminUser.name}</td>
	      <td>${adminUser.roleNames!}</td>
	      <td>
		      <#if adminUser.enabled>可用<#else>停用</#if>
	      </td>
	      <td><a href="${base}/admin/security/user!edit.action?entity.id=${adminUser.id}"><b>编辑</b></a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="6">无记录</td>
	    </tr>
    </#if>
  </table>
</div>
<div style="display:<#if entity??&&entity.id??>''<#else>none</#if>;">
<form name="userForm" action="${base}/admin/security/user!save.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
	           <table width="80%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
				        <input name="entity.id"  type="hidden" value="<#if entity??&&entity.id??>${entity.id!}</#if>"/>
				        <tr bgcolor="#F5f5f5">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#F5f5f5">
			                 <#if entity??&&entity.id??><#if entity.id==-1>新增<#else>修改</#if></#if>用户
			              </td>
			            </tr>
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">用户名：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input name="entity.loginName" value="<#if entity??&&entity.loginName??>${entity.loginName!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td width="85" align="center" bgcolor="#F5f5f5">真实姓名：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                <input name="entity.name" value="<#if entity??&&entity.name??>${entity.name!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <#if entity??&&entity.id??>
				            <#if entity.id==-1>
					            <tr bgcolor="#FFFFFF">
					              <td width="85" align="center" bgcolor="#F5f5f5">密码：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					             	  后台用户默认密码是123456
					              </span></td>
					            </tr>
				            </#if>
			            </#if>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">拥有角色：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			               <#if allRoleList??&& (allRoleList![])?size gt 0>
			                    <#list allRoleList as role>
				              	             <input type="checkbox" name="checkedRoleIds" value="${role.id!}" <#if entity??&&entity.roleIds??&&(entity.roleIds![])?size gt 0>
					              	             <#list entity.roleIds as roleId>
					              	                  <#if role.id== roleId>
					              	                     checked="true"
	                                                  </#if>
					              	             </#list>
					              	          </#if>>${role.name!}
								</#list>
						   </#if>
			              </span></td>
			            </tr>
			             <tr bgcolor="#F5f5f5">
			              <td align="center">状态：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="entity.enabled" >
				                      <option value="true" <#if entity?? && entity.enabled??&& entity.enabled>selected</#if>>可用</option>
				                      <option value="false" <#if entity?? && entity.enabled??&& !entity.enabled>selected</#if>>停用</option>
				                </select>	
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
</div>
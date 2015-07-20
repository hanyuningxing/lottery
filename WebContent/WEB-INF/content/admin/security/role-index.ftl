<title>保护资源管理</title>
<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="role"/>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/security/user.action">彩票后台用户管理</a> → <a href="${base}/admin/security/role.action">角色管理</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">角色管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/security/role.action?entity.id=-1"><b>新增角色</b></a>
  </div>
</div>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>名称</td>
      <td>权限</td>
      <td>编辑</td>
    </tr>
    <#if allRoleList??&& (allRoleList![])?size gt 0>
    	<#list allRoleList as role>
    	<#if role_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${role.id}</td>
	      <td>${role.name}</td>
	      <td>${role.authNames!}</td>
	      <td><a href="${base}/admin/security/role!edit.action?entity.id=${role.id}"><b>编辑</b></a></td>
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
<form name="userForm" action="${base}/admin/security/role!save.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
	           <table width="80%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
				        <input name="entity.id"  type="hidden" value="<#if entity??&&entity.id??>${entity.id!}</#if>"/>
				        <tr bgcolor="#F5f5f5">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#F5f5f5">
			                 <#if entity??&&entity.id??><#if entity.id==-1>新增<#else>修改</#if></#if>角色
			              </td>
			            </tr>
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">名称：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input name="entity.name" value="<#if entity??&&entity.name??>${entity.name!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">拥有权限：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			               <#if allAuthorityList??&& (allAuthorityList![])?size gt 0>
			                    <#list allAuthorityList as authority>
				              	             <input type="checkbox" name="checkedAuthIds" value="${authority.id!}" <#if entity??&&entity.authIds??&&(entity.authIds![])?size gt 0>
					              	             <#list entity.authIds as authId>
					              	                  <#if authority.id== authId>
					              	                     checked="true"
	                                                  </#if>
					              	             </#list>
					              	          </#if>>${authority.displayName!}
								</#list>
						   </#if>
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
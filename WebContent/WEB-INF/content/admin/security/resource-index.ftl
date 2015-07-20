<title>保护资源管理</title>
<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="resource"/>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/security/user.action">彩票后台用户管理</a> → <a href="${base}/admin/security/resource.action">保护资源管理</a>  <a href="${base}/adminLogin.jsp">退出登录</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">保护资源管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/security/resource.action?entity.id=-1"><b>新增保护资源</b></a>
  </div>
</div>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>资源类型</td>
      <td>资源值</td>
      <td>检测顺序</td>
      <td>资源赋予权限</td>
      <td>编辑</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as resource>
    	<#if resource_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    		<td>${resource.id}</td>
	      <td>
	         <#if MENU_TYPE==resource.resourceType>目录</#if>
	         <#if URL_TYPE==resource.resourceType>地址</#if>
	      </td>
	      <td>${resource.value}</td>
	      <td>${resource.position}</td>
	      <td>${resource.authDNames!}</td>
	      <td><a href="${base}/admin/security/resource!edit.action?entity.id=${resource.id}"><b>编辑</b></a></td>
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
<form name="resourceForm" action="${base}/admin/security/resource!save.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
	           <table width="80%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
				        <input name="entity.id"  type="hidden" value="<#if entity??&&entity.id??>${entity.id!}</#if>"/>
				        <tr bgcolor="#F5f5f5">
			              <td align="center">资源类型：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="entity.resourceType" >
				                      <option value="${MENU_TYPE!}" <#if entity?? && entity.resourceType??&& entity.resourceType==MENU_TYPE>selected</#if>>目录</option>
				                      <option value="${URL_TYPE!}" <#if entity?? && entity.resourceType??&& entity.resourceType==URL_TYPE>selected</#if>>地址</option>
				                </select>	
			              </span></td>
			            </tr>
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">保护资源值：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input name="entity.value" value="<#if entity??&&entity.value??>${entity.value!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td width="85" align="center" bgcolor="#F5f5f5">检测顺序：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                   <select name="entity.position" >
			                        <#list 1..9 as p>
			                              <option value="${p}" <#if entity?? && entity.position??&& entity.position==p>selected</#if>>顺序${p}</option>
			                        </#list>
			                   </select>	
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">资源的授权：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			               <#if allAuthorityList??&& (allAuthorityList![])?size gt 0>
			                    <#list allAuthorityList as authority>
				              	             <input type="checkbox" name="checkedAuthorityIds" value="${authority.id!}" <#if entity??&&entity.authIds??&&(entity.authIds![])?size gt 0>
					              	             <#list entity.authIds as authorityId>
					              	                  <#if authority.id== authorityId>
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
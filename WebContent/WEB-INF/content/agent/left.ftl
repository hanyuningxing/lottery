<div class="left">
	            <div class="left_title"><table width="100%" cellspacing="0" cellpadding="0" border="0">
	              <tbody><tr>
	               <td width="9%"></td>
	                <td width="15%"><img width="21" height="20" src="${base}/pages/agent/images/tip_01.jpg"></td>
	                <td width="76%">功能菜单</td>
	              </tr>
	            </tbody></table>
	            </div>
	            
	            <#if menu=='index'>
	               <#assign menu_name='用户管理' />
	            <#elseif menu=='fund'>
	               <#assign menu_name='财务管理' />
	            <#elseif menu=='count'>
	               <#assign menu_name='查询统计' />
	            <#elseif menu=='user'>
	               <#assign menu_name='安全中心' />
	            </#if>
	            <div class="content">
	                 <div class="con_title">
	                    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	                      <tbody><tr>
	                        <td width="10%" height="34">&nbsp;</td>
	                        <td width="13%"><img width="14" height="13" src="${base}/pages/agent/images/tip_02.jpg"></td>
	                        <td width="77%">${menu_name!}</td>
	                      </tr>
	                    </tbody></table>
	              </div>
	                <div class="con_con">
         				<#if menu=='index'>
		                    <ul>
		                        <li><a href="${base}/agent/index!findAgentPersonInfoSum.action" >用户列表</a></li> 
		                        <li id="nav_01" onclick="setNav(1)"><a href="javascript:void(0)">注册会员</a>
		                             <ul id="nav_ul01">
		                                <li><a href="${base}/agent/index!reg.action" >直接增加会员</a></li> 
		                                <li><a href="${base}/agent/index!reg.action?type=link" >生成注册链接</a></li>
		                                <li><a href="${base}/agent/index!reg.action?rebate=1" >直接增加高于4.5会员</a></li>
		                             </ul>
		                        </li> 
		                    </ul>
		                    <#elseif menu=='count'>
		                    <ul>
		                        <li id="nav_02" onclick="setNav(2)"><a href="javascript:void(0)">统计查询</a>
		                            <ul id="nav_ul02">
		                                <li><a href="${base}/agent/index!findAgentGroupInfoSum.action" >用户团队列表</a></li> 
		                                <li><a href="${base}/agent/index!findAgentPersonInfoSum.action" >用户个人列表</a></li>
		                             </ul>
		                        </li>
		                    </ul>
	          				<#elseif menu=='fund'>
	          				  <ul>
	          				    <li id="nav_02" onclick="setNav(2)"><a href="javascript:void(0)">财务管理</a>
	          				     	<ul id="nav_ul02">
	          				    		<li><a href="${base}/agent/user!fundList.action">资金明细</a></li> 
	          				    	</ul>
	          				      </li>
		                      </ul>
		                     <#elseif menu=='user'>
	          				  <ul>
	          				        <li><a href="${base}/agent/user!updateInfo.action">个人资料</a></li>
	          				    	<li><a href="${base}/agent/user!updatePwd.action">修改密码</a></li>
		                      </ul>
          				</#if>
	                </div>
	            </div>
</div>
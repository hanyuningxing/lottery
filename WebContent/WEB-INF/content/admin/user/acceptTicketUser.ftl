<meta name="menu" content="lottryUser"/>
<meta name="menuItem" content="addUser"/>
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="/jquery/jquery-1.8.3.js"></script>
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/user/user!acceptTicketUserList.action?entity.id=-1"><b>新增接票用户</b></a>
  </div>
</div>
<div>
<form name="queryForm" action="${base}/admin/user/user!searchUser.action" method="post">
      &nbsp;
      用户名：<input name="userName" value="<#if userName??>${userName!}</#if>" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
      <a href="#" onclick="document.forms['queryForm'].submit();return false;"><img src="${base}/styles/adminDefault/images/ico_search.gif" border="0" align="absmiddle"/></a>
</form>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">平台ID</td>
	  <td>用户ID</td>
      <td>用户名</td>
      <td>接票用户余额</td>
      <td>用户密码（密文）</td>
      <td>最后更新时间</td>
      <td>状态</td>
      <td>编辑</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as platform>
    	<#if platform_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${platform.id}</td>
	      <td>${platform.userId}</td>
	      <td>${platform.platformName}</td>
	      <td>${platform.remainMoney}</td>
	      <td>${platform.password}</td>
	      <td><#if platform?? && platform.lastModifyTime??>${platform.lastModifyTime}</#if></td>
	      <td>
		      <#if platform.locked>停用<#else>可用</#if>
	      </td>
	      <td><a href="${base}/admin/user/user!edit.action?entity.id=${platform.userId}"><b>编辑</b></a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="6">无记录</td>
	    </tr>
    </#if>
  </table>
</div>
  <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
 <script type="text/javascript">
 	window.onload=check;
 	function check(){
 		document.getElementById('userName').onclick=function(){
 			document.getElementById('userNameTip').innerHTML="";
 		}
 		document.getElementById('password').onclick=function(){
 			document.getElementById('passwordTip').innerHTML="";
 		}
 		document.getElementById('confirmPassword').onclick=function(){
 			document.getElementById('confirmPasswordTip').innerHTML="";
 		} 
 		document.getElementById('userName').onblur=function(){
 		var userName=document.getElementById('userName').value;
 			if(userName==""||userName==null){
				document.getElementById('userNameTip').innerHTML="用户名不合法";
				document.getElementById('userNameTip').style.color="red";
			}else{
				var msg="";
				var url =window.BASESITE + '/admin/user/user!checkUserRegAble.action';
				$.ajax({
					type :'POST',
					cache:'false',
					dataType:'json',
	        		url : url,
	        		data:{'userName':userName},
	        		success : function(data){
	        				msg = data.success;
	        			if(msg){
	        				document.getElementById('userNameTip').innerHTML="该用户名可以使用";
	        				document.getElementById('userNameTip').style.color="green";
	        			}else{
	        				document.getElementById('userNameTip').innerHTML="该用户名已存在";
	        				document.getElementById('userNameTip').style.color="red";
	        			}
	        		}
				});
					
			}
 		}
 		document.getElementById('password').onblur=function(){
 		var confirmPassword=document.getElementById('confirmPassword').value;
 		var password=document.getElementById('password').value;
 			if(password==""||password==null){
				document.getElementById('passwordTip').innerHTML="密码不能为空";
				document.getElementById('passwordTip').style.color="red";
			}else{
				if(password==confirmPassword){
					document.getElementById('confirmPasswordTip').innerHTML="";
				}
			}	
 		}
 		document.getElementById('confirmPassword').onblur=function(){
 		var confirmPassword=document.getElementById('confirmPassword').value;
 		var password=document.getElementById('password').value;
 			if(confirmPassword==""||confirmPassword==null){
				document.getElementById('confirmPasswordTip').innerHTML="确认密码不能为空";
				document.getElementById('confirmPasswordTip').style.color="red";
			}else{
				if(password!=confirmPassword){
				document.getElementById('confirmPasswordTip').innerHTML="两次密码不一致";
				document.getElementById('confirmPasswordTip').style.color="red";
				}				
			}	
 		}
 	}
	
 </script>
<div style="display:<#if entity??&&entity.id??>''<#else>none</#if>;">
<form name="userForm" action="${base}/admin/user/user!save.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
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
			               <#if entity??&&entity.id??>
			              		<#if entity.id==-1>
			                		<input id="userName" name="entity.userName" value="" type="text" class="heade_searchinput"  size="30" />
			               		<#else>
			                	${entity.userName}
								</#if>
			              	</span>
			              		<#if entity.id==-1>
			              			<span id="userNameTip"></span>
			              		</#if>
			              </#if>
			              </td>
			            </tr>
	
			            <#if entity??&&entity.id??>
				            <#if entity.id==-1>
					            <tr bgcolor="#FFFFFF">
					              <td width="85" align="center" bgcolor="#F5f5f5">密码：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					             	  <input type="password" id="password" name="entity.password" value="" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30"/>
					              </span><span id="passwordTip"></span></td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="85" align="center" bgcolor="#F5f5f5">确认密码：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					             	  <input type="password" id="confirmPassword" name="entity.confirmPassword" value="" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30"/>
					              </span><span id="confirmPasswordTip"></span></td>
					            </tr>					            
				            </#if>
			            </#if>
			            <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">授权投注彩种：</td>
					       <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
		                    <#list lotteryTypeArr as lot>
	              	             <input type="checkbox" name="checkedLotteryIds" value="${lot.ordinal()}" <#if ticketPlatform??&&ticketPlatform.allOpenList??&& (ticketPlatform.allOpenList![])?size gt 0>
		              	             <#list ticketPlatform.allOpenList as openLottery>
		              	                  <#if (lot.ordinal()+"")== openLottery>
		              	                     checked="true"
                                          </#if>
		              	             </#list>
		              	          </#if>>${lot.lotteryName!}
							</#list>
			              </span></td>
			            </tr>
					    <tr bgcolor="#FFFFFF">
					              <td width="85" align="center" bgcolor="#F5f5f5">用户类型：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					             	接票用户
					              </span><br/>
					              IP限制(接票用户)多个IP用|分隔
					              <input type="text" name="entity.ipLimit" value="<#if ticketPlatform??&&ticketPlatform.limitIp??>${ticketPlatform.limitIp}</#if>" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30"/>
					              
					              </td>
					    </tr>		            
			            <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">手机号码：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                <input id="mobile" name="entity.info.mobile" value="<#if entity??&&entity.info??&&entity.info.mobile??>${entity.info.mobile}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
			              </span><span id="error"></span></td>
			            </tr>
			            <#if entity??&&entity.id??>
						<#if entity.id!=-1>
			             <tr bgcolor="#F5f5f5">
			              <td align="center">状态：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                    <select name="entity.locked" >
				                      <option value="true" <#if entity?? && entity.locked??&& entity.locked>selected</#if>>停用</option>
				                      <option value="false" <#if entity?? && entity.locked??&& !entity.locked>selected</#if>>可用</option>
				                </select>	
			              </span></td>
			            </tr>
			            </#if>
			            </#if>
			            <tr bgcolor="#FFFFFF">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#FFFFFF">
			                 <input type="image" src="${base}/images/comfirm.gif" onclick="return checkMessage();"/>
			              </td>
			            </tr>
			          </table>
</form>
</div>
<meta name="menu" content="fund"/>
<meta name="menuItem" content="user"/>
<script type="text/javascript" src="<@c.url value='/js/jquery/jquery-1.4.2.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
			//保存
		$("#oprMoneySubmit").click(function(){	
		
			 var oprType = document.getElementsByName("oprType");
		 	 var oprTypeValue;
		    for(i = 0; i < oprType.length; i++)
		    {  
		      if(oprType[i].checked) { 
		       if(oprType[i].value==0)  {
		       		oprTypeValue="【增加】"
		        }
		        if(oprType[i].value==1)  {
		       		oprTypeValue="【扣除】"
		        }
 		      }  
		    }
		
			if(oprTypeValue==null){
				alert("请选择操作类型");
				return false;
			}
						
		    var confirmflag = "你确定要"+oprTypeValue+"："+$("#oprMoney").val()+"元吗？";
			if (confirm(confirmflag)) {
			 $("#userForm").submit();
			}
		});
	});
	</script>
<form id="userForm" name="userForm" action="${base}/admin/fund/fund!oprMoney.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
<input name="id"  type="hidden" value="<#if user??&&user.id??>${user.id!}</#if>"/>
<input type="hidden" name="request_token" value="${datetime().millis}" />
<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="center" align="left">
 						<tr class="trlbrown">
						  <td colspan=2>额度调整</td>
					    </tr>
					    <tr bgcolor="#F5f5f5">
			              <td bgcolor="#F5f5f5" align="center" width="20%">用户名：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			              <#if user??&&user.userName??>${user.userName!}</#if>
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">真实姓名：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                  <#if user??&&user.realName??>${user.realName!}</#if>
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">当前可用金额：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                   <#if user??&&user.remainMoney??>#{(user.remainMoney!0);M2}</#if>
				           </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">共消费金额额度：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				                      <#if user?? && user.consumptionMoney??>${user.consumptionMoney!}</#if>
				           </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">调整类型：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                 	    增加：<input type="radio" name="oprType" value="0"/>
				                 	     &nbsp;    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;                   
				                                                         扣除：<input type="radio" name="oprType" value="1"/>
				           </span></td>
			            </tr>
          				<tr bgcolor="#FFFFFF">
			              <td align="center">调整金额：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                 <input id="oprMoney" name="oprMoney" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
				          </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">备注：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                 <input name="remark" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="35" />
				          </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#F5f5f5">
								  <p style="margin-top: 13px; margin-bottom: 45px;">
								    <input type="button" value="确认提交" id="oprMoneySubmit" class="ButtonStyle_max" />
							      </p>
	      			              </td>
			            </tr>
			          </table>
    </form>  

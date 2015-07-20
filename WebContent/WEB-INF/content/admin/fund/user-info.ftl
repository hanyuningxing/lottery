<meta name="menu" content="fund"/>
<meta name="menuItem" content="user"/>
<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="left">
                         <tr class="trlbrown">
						  <td colspan=2>用户信息</td>
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
			                <#if user??&&user.info??&&user.info.realName??>${user.info.realName!}</#if>
			              </span></td>
			            </tr>
						<tr bgcolor="#F5f5f5">
			              <td align="center">身份证：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                     <#if user??&&user.info??&&user.info.idCard??>${user.info.idCard!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">手机号码：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                     <#if user??&&user.info??&&user.info.mobile??>${user.info.mobile!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">注册邮箱：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                       <#if user??&&user.info??&&user.info.email??>${user.info.email!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">开户银行：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                     <#if user.bank??&&user.bank??>${user.bank.bankName!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">银行账号：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if user.bank??&&user.bank??>${user.bank.bankCard!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">开户银行省份：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if user.bank??&&user.bank??>${user.bank.partBankProvince!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">开户银行城市 ：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if user.bank??&&user.bank??>${user.bank.partBankCity!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">开户支行名称：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
			                    <#if user.bank??&&user.bank??>${user.bank.partBankName!}</#if>
			             </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">是否可用：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				                      <#if user.locked??&&user.locked><font color="gray">否</font><#else><font color="blue">是</font></#if>
				           </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">当前现金钱包余额：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                      <#if user??  && user.consumptionMoney??>${user.consumptionMoney!}</#if>
				           </span></td>
			            </tr>
			             <tr bgcolor="#FFFFFF">
			              <td align="center">注册时间：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                      <#if user.bank??&&user.bank??&&user.bank.createTime??>${user.bank.createTime!}</#if>   
				           </span></td>
			            </tr>
			            
			             <tr bgcolor="#F5f5f5">
			              <td align="center">最后修改时间：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			             			 <#if user.bank??&&user.bank??&&user.bank.lastModifyTime??>${user.bank.lastModifyTime!}</#if>  
				           </span></td>
			            </tr>
			            
			             <tr bgcolor="#F5f5f5">
			              <td align="center">是否可用：</td>
			              <td bgcolor="#F5f5f5">
			                 <span class="ssq_tr_2">
			                        <#if user?? && user.locked?? && user.locked?string == '1'>停用</#if>
			                        <#if user?? && user.locked?? && user.locked?string == '0'>可用</#if>
				           </span></td>
			            </tr>
			            
					    
</table>

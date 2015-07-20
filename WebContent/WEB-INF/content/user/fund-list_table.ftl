
            <table width="100%" border="0" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
              <tbody>
                <tr height="26">
                  <td width="28%" height="30" bgcolor="#FFFFFF">支出交易：<strong>${countPay!0}</strong> 笔</td>
                  <td width="24%" bgcolor="#FFFFFF">支出金额：<span class="green_419900"><strong>#{(sumPay!0);M2}</strong></span> 元</td>
                  <td width="24%" bgcolor="#FFFFFF">收入交易：<strong>${countIn!0}</strong> 笔</td>
                  <td bgcolor="#FFFFFF">收入金额：<span class="rc1"><strong>#{(sumIn!0);M2}</strong></span>   元</td>
                </tr>
              </tbody>
            </table>
            
	        <table width="100%" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#dae8f5" class="zjmxtab">
              <tbody>
                <tr height="23">
                  <td width="16%" height="30" align="center" bgcolor="#d5f6fd">交易时间</td>
                  <td width="12%" align="center" bgcolor="#d5f6fd">收入</td>
                  <td width="12%" align="center" bgcolor="#d5f6fd">支出</td>
                  <td width="12%" align="center" bgcolor="#d5f6fd">余额</td>
                  <td width="12%" align="center" bgcolor="#d5f6fd">交易类型</td>
                  <td align="center" bgcolor="#d5f6fd">备注</td>
                </tr>
                 <#if pagination.result?? && pagination.result?size gt 0>
				<#list pagination.result as data>
	                <tr>
	                  <td height="30" align="center" bgcolor="#F3F3F3">${data.createTime?string('yyyy-MM-dd HH:mm')}</td>
	                  <td align="center" bgcolor="#F3F3F3"><span class="red"><#if data.mode.typeName=='收入'>￥#{(data.money!0);M2}</#if></span></td>
	                  <td align="center" bgcolor="#F3F3F3"><span class="green"><#if data.mode.typeName=='支出'>￥#{(data.money!0);M2}</#if></td>
	                  <td align="center" bgcolor="#F3F3F3">￥<strong>#{(data.resultMoney!0);M2}</strong></td>
	                  <td align="center" bgcolor="#F3F3F3">${data.type.typeName}</td>
	                  <td align="center" bgcolor="#F3F3F3">${data.remarkString!}</td>
	                </tr>
               </#list>          
			   <#else>
					<tr>
					 <td colspan="6">暂无记录.</td>
					</tr>
				</#if>
              </tbody>
            </table>
            
            <div class=" cleanboth pagelist" align="center">
		        <#import "../../macro/pagination.ftl" as b />
				
		    </div>
		    <@b.page {'ajaxContainer':'extra-data-container'} />
<meta name="decorator" content="agent" />
<meta name="menu" content="index" />
  <form action="<@c.url value='/agent/index!reg.action' />" method="post" id="userCreateForm">
   <input type="hidden" name="request_token" value="${datetime().millis}" />
	 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">会员推广</td>
              </tr>
            </table>
</div>
      <div class="right_c">
         <div class="login">
             <div class="login_c">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                 	  <tr>
                        <th width="13%" scope="row">&nbsp;</th>
                        <td width="87%"><#include "/common/message.ftl" /></td>
                      </tr>
                      <tr>
                        <th width="13%" scope="row"> 用户名：</th>
                        <td width="87%"><input type="text" class="input_02" id="userName" name="userName" value="${userName!}"/>&nbsp;</td>
                      </tr>
                      <tr>
                        <th width="13%" scope="row"> 登录密码：</th>
                        <td width="87%">默认是123456</td>
                      </tr>
                      <tr>
                        <th valign="top" scope="row"><div style="padding-top:20px">返点：</div></th>
                        <td valign="top">
                        	<div style=" line-height:30px">
                        	   <script>
                        	       function fetchRebateLimit(agentLotteryType,rebate){
									    var url = "${base}/agent/index!fetchRebateLimit.action?agentLotteryType="+agentLotteryType+"&rebate="+rebate;
									    
									    $.ajax({ 
											type: 'GET',
											cache : false,
											url: url,
											success : function(data, textStatus) {
												var jsonObj = toJsonObject(data);
												try {
													if(jsonObj.success == true){
										        	    document.getElementById(agentLotteryType+'_limit').innerHTML=jsonObj.rebateItem;
										        	    
										        	}else{
										        	}
												} catch (e) {
													
												}
											},
											error : function(XMLHttpRequest, textStatus, errorThrown) {
												
											}
									    });
									}
                        	   </script>
                        	   <#if agentLotteryTypeList?exists&&agentLotteryTypeList?has_content>
                        	      <#list agentLotteryTypeList as data>
                        	          ${data.typeName}<select name="${data}" onchange="fetchRebateLimit('${data}',this.options[this.selectedIndex].value);return false;">
                        	              <#list data.rebateList as rebate>
                                          <#assign myRebate=0 />
                                             <#if userRebateList?exists&&userRebateList?has_content>
                                              <#list userRebateList as userRebate>
                                                   <#if userRebate.agentLotteryType??&&data==userRebate.agentLotteryType>
                                                     <#assign myRebate=userRebate.rebate />
                                                   </#if>
                                              </#list>
                                             </#if>
                        	                 	<option value="${rebate}" <#if data.choose??&&rebate?string ==data.choose?string>selected</#if>>${rebate}</option>
                                          </#list>
                        	          </select>
                        	           <#if userRebateList?exists&&userRebateList?has_content>
                        	          	  <#list userRebateList as userRebate>
                        	                   <#if userRebate.agentLotteryType??&&data==userRebate.agentLotteryType>
                        	                      &nbsp;&nbsp; 我的返点<#if userRebate.rebate??>#{(userRebate.rebate!0);M1}</#if>
                        	                   
                        	                   </#if>
                        	              </#list>
                        	             </#if>
                        	             &nbsp;&nbsp; <span id="${data}_limit"></span>
                        	            <br/>
                        	            <script>
                        	              <#if data.choose??>
                        	               	fetchRebateLimit('${data}','${data.choose}')
                        	              </#if>
                        	            </script>
                        	      </#list>
                        	   </#if>
	                         </div>   
                         </td>
                      </tr>
                      <tr>
                        <th width="13%" scope="row"> 验证码：</th>
                        <td width="87%"><input class="input_02" maxLength="4" style="width:40px" type="text" id="captcha" name="captcha"/>&nbsp;&nbsp; <img src="${base}/vcode.jpg" id="RIC"/>看不清？<A href="#" onclick="javascript:document.getElementById('RIC').src='${base}/vcode.jpg?t'+new Date().getTime();return false;">换一张</A></td>
                      </tr>
                      <tr>
                        <th scope="row"></th>
                        <td style="padding:0; margin:0;">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th width="20%" scope="row"> <input type="submit" class="input_03" style="width:112px" value="直接增加会员" /></th>
    <td width="80%">&nbsp;&nbsp;</td>
  </tr>
</table></td>
                      </tr>
                    </table>

             </div>
         </div>
      </div>   
      </form>   

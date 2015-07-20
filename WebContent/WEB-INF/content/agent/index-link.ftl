<meta name="decorator" content="agent" />
<meta name="menu" content="index" />
  <form action="<@c.url value='/agent/index!reg.action' />" method="post" id="userCreateForm">
	 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">会员注册</td>
              </tr>
            </table>
</div>
      <div class="right_c">
         <div class="login">
             <div class="login_c">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <th width="13%" scope="row"> 推广地址：</th>
                        <td width="87%">
                            <a id="i_link" href="<@c.url value="/user/user!reg.action?i=${loginUser.id}" />"></a>
							<input id="i_link_input" type="text" size="65"  value=""/>
						</td>
                      </tr>
                      <tr>
                        <th scope="row"></th>
                        <td style="padding:0; margin:0;">
                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th width="20%" scope="row">
      
      <script type="text/javascript">document.getElementById("i_link_input").value=document.getElementById('i_link').href;</script>
	    	<input  class="input_03" onclick="copyToClipboard(document.getElementById('i_link_input').value);" type="button" value="复制地址"/>
	</th>
    <td width="80%">&nbsp;&nbsp;</td>
  </tr>
</table></td>
                      </tr>
                    </table>

             </div>
         </div>
      </div>   
      </form>   

<head>
	<title>用户中心</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<link rel="stylesheet" href="<@c.url value="/pages/agent/css/right.css"/>" type="text/css" />
	<script>
	   function copyToClipboard(txt) {
		    if(window.clipboardData) {
		        window.clipboardData.clearData();
		        window.clipboardData.setData("Text", txt);
		    } else if(navigator.userAgent.indexOf("Opera") != -1) {
		        window.location = txt;
		    } else if (window.netscape) {
		        try {
		            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		        } catch (e) {
		            alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
		        }
		        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		        if (!clip)
		            return;
		        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		        if (!trans)
		            return;
		        trans.addDataFlavor('text/unicode');
		        var str = new Object();
		        var len = new Object();
		        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		        var copytext = txt;
		        str.data = copytext;
		        trans.setTransferData("text/unicode",str,copytext.length*2);
		        var clipid = Components.interfaces.nsIClipboard;
		        if (!clip)
		            return false;
		        clip.setData(trans,null,clipid.kGlobalClipboard);
		        window.alert("复制成功！")
    		}
		}
	
	</script>
</head>

  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
  	 <#include "user-loginInfo.ftl"/>
	  <form action="<@c.url value='/agent/index!reg.action' />" method="post" id="userCreateForm">
	 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">会员注册</td>
              </tr>
            </table>
</div>
      <div>
         <div class="login" style="margin:0">
             <div class="login_c">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <th width="13%" scope="row"> 推广地址：</th>
                        <td width="87%">
                            <a id="i_link" href="<@c.url value="/${loginUser.id}/index" />"></a>
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
	 
  </div>
  <!--mleft end -->
  <!--mright begin -->
  <!--main end -->
</div>
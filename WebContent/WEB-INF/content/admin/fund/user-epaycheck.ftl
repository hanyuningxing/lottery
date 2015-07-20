<meta name="menu" content="fund"/>
<meta name="menuItem" content="user"/>
<script type="text/javascript" src="<@c.url value='/js/jquery/jquery-1.4.2.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
			//保存
		$("#epayCheckSubmit").click(function(){					
		   var confirmflag = "你确定要操作："+$("#total_fee").val()+"元吗？";
			if (confirm(confirmflag)) {
			 $("#userForm").submit();
			}
		});
		
		function alipaySingleTradeQuery(){
				var customOrderNo =$("#customOrderNo").val().trim();
				if(customOrderNo == ""){
					alert('订单号不能为空');
					return false;
				}
				var url = window.BASESITE + '/user/fund!alipaySingleTradeQuery.action?orderNo='+customOrderNo;
				$.ajax({
				type : 'get',
				cache : false,
				dataType : 'json',
				url : url,
				success : function(jsonObj) {
					if (jsonObj.success == true) {
						alert(jsonObj.msg);
					} else {		
						alert(jsonObj.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("网络不通");
				}});	
			}
		
	});
	</script>
<form id="userForm" name="userForm" action="${base}/admin/fund/fund!epayCheck.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
<input type="hidden" name="request_token" value="${datetime().millis}" />
<table width="100%" height="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6" align="center" align="left">
 						<tr class="trlbrown">
						  <td colspan=2>手动充值</td>
					    </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">订单号：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				              <input id="customOrderNo" name="order_no" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
				           </span></td>
			            </tr>
          				<tr bgcolor="#FFFFFF">
			              <td align="center">交易号：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                 <input id="trade_no" name="trade_no" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
				          </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center">交易金额：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                 <input id="total_fee" name="total_fee" type="text" class="header_searchinput" onblur="this.className='header_searchinput'" onfocus="this.className='header_searchclick'" size="15" />
				          </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#F5f5f5">
								  <p style="margin-top: 13px; margin-bottom: 45px;">
								    <input type="button" value="手动充值" id="epayCheckSubmit" class="ButtonStyle_max" />
								    <input type="button" value="手动查询"  class="ButtonStyle_max" onclick="alipaySingleTradeQuery();" />
							      </p>
							      手动充值：输入 订单、交易、交易金额  为用户充值<br>
							      手动查询：如出现支付宝延迟到账，可以主动发起查询，如果该单到账未充值，可以自动充值
	      			              </td>
			            </tr>
			          </table>
    </form>  

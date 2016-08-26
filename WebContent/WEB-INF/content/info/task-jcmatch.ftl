<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>监控</title>
                    <script type="text/javascript">window.BASESITE='${base}';</script>
					<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
					<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>	
					<script type="text/javascript">
						   function oprMethod(width,height,src,title){
							    $floater({
									width : width,
									height : height,
									src : window.BASESITE + src,
									title : title,
									fix : 'true'
								});
						   }
					</script>
</head>
<body>
                        <form method="post" action="<@c.url value="/info/task!jcmatch.action" />" id="taskForm"></form>
						  <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#E6E2D6">
						     <tr bgcolor="#F5f5f5">
						           <td bgcolor="#F5f5f5">项目</td>
					               <td bgcolor="#F5f5f5">本地场次数</td>
					               <td bgcolor="#F5f5f5">线程场次数</td>
					               <td bgcolor="#F5f5f5">是否需要更新</td>
					         </tr>
							 <tr bgcolor="#F5f5f5">
						         <td bgcolor="#F5f5f5">篮球</td>
						         <td bgcolor="#F5f5f5">${lastJclqMatchSize!}</td>
						         <td bgcolor="#F5f5f5">${jclqMatchSize!}</td>
						         <td bgcolor="#F5f5f5"><#if lastJclqMatchSize!=jclqMatchSize><font color="red">是</font><#else>否</#if></td>
						     </tr>
						      <tr bgcolor="#F5f5f5">
						         <td bgcolor="#F5f5f5">足球</td>
						         <td bgcolor="#F5f5f5">${lastJczqMatchSize!}</td>
						         <td bgcolor="#F5f5f5">${jczqMatchSize!}</td>
						         <td bgcolor="#F5f5f5"><#if lastJczqMatchSize!=jczqMatchSize><font color="red">是</font><#else>否</#if></td>
						     </tr>
						   </table>
						   <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#E6E2D6">
						      <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5">彩种</td>
						               <td bgcolor="#F5f5f5">未出票</td>
						               <td bgcolor="#F5f5f5">委托中</td>
						               <td bgcolor="#F5f5f5">出票失败</td>
						               <td bgcolor="#F5f5f5">未出票但方案成功</td>
						               <td bgcolor="#F5f5f5">出票但方案失败</td>
						      </tr>
					           <#if lotteryArr??>
						           <#list lotteryArr as data>
				  					 <#if data??>
				  					    <tr bgcolor="#F5f5f5">
								           <td bgcolor="#F5f5f5">${data.lotteryName!}</td>
							               <td bgcolor="#F5f5f5">
							                    <#if unprint??&&unprint.get(data)??>
							                       <#list unprint.get(data) as ticket>
							                             <#if ticket.commitTime??>${ticket.commitTime?string("MM-dd HH:mm:ss")}</#if>&nbsp;&nbsp;${ticket.units!}个&nbsp;<a href="#" onclick = "oprMethod(740,400,'/info/task!findScheme.action?lottery=${data}&type=0&commitTime=${ticket.commitTime!''}','查看');return false;">查看</a></br>
							                       </#list>
							                    </#if>
							               </td>
							               <td bgcolor="#F5f5f5">
							                    <#if print??&&print.get(data)??>
							                        <#list print.get(data) as ticket>
							                             <#if ticket.commitTime??>${ticket.commitTime?string("MM-dd HH:mm:ss")}</#if>&nbsp;&nbsp;${ticket.units!}个&nbsp;<a href="#" onclick = "oprMethod(740,400,'/info/task!findScheme.action?lottery=${data}&type=1&commitTime=${ticket.commitTime!''}','查看');return false;">查看</a></br>
							                      </#list>
												</#if>
							               </td>
							               <td bgcolor="#F5f5f5">
							                    <#if failed??&&failed.get(data)??>
							                        <#list failed.get(data) as ticket>
							                             <#if ticket.commitTime??>${ticket.commitTime?string("MM-dd HH:mm:ss")}</#if>&nbsp;&nbsp;${ticket.units!}个&nbsp;<a href="#" onclick = "oprMethod(740,400,'/info/task!findScheme.action?lottery=${data}&type=2&commitTime=${ticket.commitTime!''}','查看');return false;">查看</a></br>
							                      </#list>
												</#if>
							               </td>
							               <td bgcolor="#F5f5f5">
							                    <#if upprint_success??&&upprint_success.get(data)??>
							                        <#list upprint_success.get(data) as ticket>
							                             <#if ticket.commitTime??>${ticket.commitTime?string("MM-dd HH:mm:ss")}</#if>&nbsp;&nbsp;${ticket.units!}个&nbsp;<a href="#" onclick = "oprMethod(740,400,'/info/task!findScheme.action?lottery=${data}&type=3&commitTime=${ticket.commitTime!''}','查看');return false;">查看</a></br>
							                      </#list>
												</#if>
							               </td>
							               <td bgcolor="#F5f5f5">
							                    <#if printed_canecl??&&printed_canecl.get(data)??>
							                        <#list printed_canecl.get(data) as ticket>
							                             <#if ticket.commitTime??>${ticket.commitTime?string("MM-dd HH:mm:ss")}</#if>&nbsp;&nbsp;${ticket.units!}个&nbsp;<a href="#" onclick = "oprMethod(740,400,'/info/task!findScheme.action?lottery=${data}&type=4&commitTime=${ticket.commitTime!''}','查看');return false;">查看</a></br>
							                      </#list>
												</#if>
							               </td>
							             </tr>
							          </#if>
							        </#list>
						    	</#if>
						   </table>
						   <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#E6E2D6">
						      <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5">充值合作方</td>
						               <td bgcolor="#F5f5f5">未完成充值</td>
						      </tr>
					           <#if ipsorderList??>
					            	<#list ipsorderList as data>
				  					    <tr bgcolor="#F5f5f5">
								           <td bgcolor="#F5f5f5" width="50%"><#if data.payWay??>${data.payWay.payName!}</#if></td>
							               <td bgcolor="#F5f5f5" width="50%"><#if data.version??>${data.version!}</#if></td>
							             </tr>
							        </#list>
						    	</#if>
						   </table>
						   <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#E6E2D6">
						      <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5" width="50%">提款状态</td>
						               <td bgcolor="#F5f5f5" width="50%">数量</td>
						      </tr>
					           <#if drawingOrderList??>
					            	<#list drawingOrderList as data>
				  					    <tr bgcolor="#F5f5f5">
								           <td bgcolor="#F5f5f5"><#if data.state??>${data.state.stateName!}</#if></td>
							               <td bgcolor="#F5f5f5"><#if data.version??>${data.version!}</#if></td>
							             </tr>
							        </#list>
						    	</#if>
						   </table>
	</body>
	      <script type="text/javascript">
							window.setTimeout(function(){
							          document.getElementById("taskForm").submit();
							},10000)
				 	</script> 
</html>			 	
							          
				       
				       
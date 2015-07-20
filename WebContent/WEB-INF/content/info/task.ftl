                        <form method="post" action="<@c.url value="/info/task!index.action" />" id="taskForm">
						  <table width="90%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
						     <tr bgcolor="#F5f5f5">
					               <td bgcolor="#F5f5f5">线程名字</td>
					               <td bgcolor="#F5f5f5">线程状态</td>
					               <td bgcolor="#F5f5f5">线程更新次数</td>
					               <td bgcolor="#F5f5f5">线程更新信息</td>
					               <td bgcolor="#F5f5f5">线程最后更新时间</td>
					         </tr>
					        <#if taskInfoDataList??>
					           <#list taskInfoDataList as data>
			  					 <#if data??>
							        <tr bgcolor="#F5f5f5">
						               <td bgcolor="#F5f5f5">${data.taskType.typeName!}</td>
						               <td bgcolor="#F5f5f5">${data.taskState.stateName!}</td>
						               <td bgcolor="#F5f5f5">${data.runTime!}</td>
						               <td bgcolor="#F5f5f5">${data.runString!}</td>
						               <td bgcolor="#F5f5f5">${data.lastModifyTime?string("yy-MM-dd HH:mm:ss")}</td>
						            </tr>
						          </#if>
						        </#list>
						    </#if>
						   </table>
				       </form>
				    <script type="text/javascript">
							window.setTimeout(function(){
							          document.getElementById("taskForm").submit();
							},60000)
				 	</script> 
				       
				       
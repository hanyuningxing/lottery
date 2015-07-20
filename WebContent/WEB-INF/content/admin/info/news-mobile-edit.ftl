<head>
<title>文章管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<#if infoBeanForm??&&infoBeanForm.id??><#if infoBeanForm.id==-1><meta name="menuItem" content="indexMobileEdit"/><#else><meta name="menuItem" content="mobileIndex"/></#if><#else><meta name="menuItem" content="indexMobileEdit"/></#if>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
<script type="text/javascript">  
	     function selectMobileSubType(){
	            var indexInfoSaveForm = document.getElementById('indexInfoSaveForm');
	            var lotteryType = indexInfoSaveForm.elements['infoBeanForm.lotteryType'];
	            var lotteryTypeValue = lotteryType.options[lotteryType.selectedIndex].value; 
		        $.ajax({ 
					type: 'GET',
					cache : false,
					url: "<@c.url value='/admin/info/news!selectMobileSubType.action' />?lotteryType="+lotteryTypeValue,
					success : function(data, textStatus) {
						var jsonObj = toJsonObject(data);
						try {
						    if (jsonObj.periodList != null){
						        var selectObj =  indexInfoSaveForm.elements['infoBeanForm.periodId'];
						        selectObj.options.length = 0; 
						        for(var i=0;i<jsonObj.periodList.length;i++){
						             selectObj.options.add(new Option( jsonObj.periodList[i].name, jsonObj.periodList[i].obj)); 
						        }
							}
							if (jsonObj.tyepList != null){
						        var selectObj =  indexInfoSaveForm.elements['infoBeanForm.lotteryPlayType'];
						        selectObj.options.length = 0; 
						        for(var i=0;i<jsonObj.tyepList.length;i++){
						             selectObj.options.add(new Option( jsonObj.tyepList[i].name, jsonObj.tyepList[i].obj)); 
						        }
							}else{
							 	var selectObj =  indexInfoSaveForm.elements['infoBeanForm.lotteryPlayType'];
						        selectObj.options.length = 0; 
						        selectObj.options.add(new Option( "常规玩法", "0")); 
							}
						} catch (e) {
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					
					}
			   });
			   return false;
	      }
	 </script>   
</head>
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.MobileInfoType@values()") />
         <form method="post" id="indexInfoSaveForm" action="<@c.url value="/admin/info/news!mobileNewsSave.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}" onsubmit="return indexInfoSaveMethod();">
			    <table width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
		        <input name="infoBeanForm.id"  type="hidden" value="<#if infoBeanForm??&&infoBeanForm.id??>${infoBeanForm.id!}<#else>-1</#if>"/>
		        <tr bgcolor="#F5f5f5">
	              <td align="center" valign="top">&nbsp;</td>
	              <td bgcolor="#F5f5f5">
	                <#if infoBeanForm??&&infoBeanForm.id??><#if infoBeanForm.id==-1>新增<#else>修改</#if><#else>新增</#if>
	              </td>
	            </tr>
	           				            
			    <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">标题：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
	                <input name="infoBeanForm.title" value="<#if infoBeanForm??&&infoBeanForm.title??>${infoBeanForm.title!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'"  maxlength="15" size="24" />
	              </span>
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">彩种：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
	              		<select name="infoBeanForm.lotteryType" size="1"  onchange="selectMobileSubType();return false;">
	                        <option selected="selected" value=""></option>
	              		    <#list lotteryTypeArr as type>
								<option <#if (infoBeanForm??&&infoBeanForm.lotteryType?? && infoBeanForm.lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
							</#list>
					   </select>		
                   </span></td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">玩法：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
	              		<select name="infoBeanForm.lotteryPlayType" size="1">
	              		   <#list lotteryPlayTypeMap?keys as key>
	              		       	<option <#if (infoBeanForm??&&infoBeanForm.lotteryPlayType?? && infoBeanForm.lotteryPlayType==lotteryPlayTypeMap[key])>selected="selected"</#if> value="${lotteryPlayTypeMap[key]}">${key}</option>
							</#list>
					   </select>		
                   </span></td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">期号：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
	              		<select name="infoBeanForm.periodId" size="1">
	              		   <#list periodMap?keys as key>
	              		       	<option <#if (infoBeanForm??&&infoBeanForm.periodId?? && infoBeanForm.periodId==periodMap[key])>selected="selected"</#if> value="${periodMap[key]}">${key}</option>
							</#list>
					   </select>		
                   </span></td>
	            </tr>
				<tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">类型：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
	              		<select name="infoBeanForm.mobileInfoType">
	              		  <#if infoTypeArr??>
		                   <#list infoTypeArr as type>
								<option <#if (infoBeanForm??&&infoBeanForm.mobileInfoType??&&type==infoBeanForm.mobileInfoType)>selected="selected"</#if> value="${type}">${type.typeName!}</option>
						   </#list>
						  </#if>
	              		</select>
                   </span></td>
	            </tr>	     
		        <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">专家荐号/单式：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
		                  <input name="infoBeanForm.singleAnalyse" value="<#if infoBeanForm??&&infoBeanForm.singleAnalyse??>${infoBeanForm.singleAnalyse!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'"  maxlength="100" size="50" />
	              </span>
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">专家荐号/复式：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
		                  <input name="infoBeanForm.analyse" value="<#if infoBeanForm??&&infoBeanForm.analyse??>${infoBeanForm.analyse!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'"  maxlength="100" size="100" />
	              </span>
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">上期推介（注：这里必须填纯文本）：<br/>专家荐号需要填写</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
		             <textarea id="infoBeanForm.lastContent" name="infoBeanForm.lastContent" rows="20" cols="30" style="width: 100%">
		             <#if infoBeanForm??&&infoBeanForm.lastContent??>${infoBeanForm.lastContent!}</#if>
		             </textarea>
		             </span>
	              </td>
	            </tr>
	             <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">本期推介/手机内容（注：这里必须填纯文本）：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
		             <textarea id="infoBeanForm.content" name="infoBeanForm.content" rows="20" cols="30" style="width: 100%">
		             <#if infoBeanForm??&&infoBeanForm.content??>${infoBeanForm.content!}</#if>
		             </textarea>
		             </span>
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td align="center" valign="top">&nbsp;</td>
	              <td bgcolor="#FFFFFF">
	                 <input type="image" src="${base}/images/comfirm.gif" />
	              </td>
	            </tr>		            
	          </table> 
	    </form>
	</body>
</html>
<head>
<title>文章管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<#if infoBeanForm??&&infoBeanForm.id??><#if infoBeanForm.id==-1><meta name="menuItem" content="indexMobileEdit"/><#else><meta name="menuItem" content="mobileIndex"/></#if><#else><meta name="menuItem" content="indexMobileEdit"/></#if>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js' />'></script>
  
</head>
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />
<#assign infoTitleColorArr=stack.findValue("@com.cai310.lottery.common.InfoTitleColor@values()") />
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.InfoType@values()") />
         <form method="post" id="indexInfoSaveForm" action="<@c.url value="/admin/info/news!tagsInfoSave.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}">
			    <table width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
		        <!----><input name="infoBeanForm.id"  type="hidden" value="<#if infoBeanForm??&&infoBeanForm.id??&&tagsInfoData??>${tagsInfoData.id!}<#else>-1</#if>"/>
		        
	           				            
			    <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">标签词：</td>
	              <td bgcolor="#FFFFFF" width="400">
	                <textarea name="infoBeanForm.tags" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'"  height="200" width="200" ><#if tagsInfoData??&&tagsInfoData.tags??>${tagsInfoData.tags!}</#if></textarea>
	             
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">冷热程度：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						<select name="infoBeanForm.level" size="1" id="infoBeanForm.level">
						 
              		    	<option <#if infoBeanForm??&&infoBeanForm.level??&&infoBeanForm.level==0>selected="selected"</#if> value="0">热门</option>  
              		    	<option <#if infoBeanForm??&&infoBeanForm.level??&&infoBeanForm.level==1>selected="selected"</#if> value="1">一般</option>           		    
              		    	<option <#if infoBeanForm??&&infoBeanForm.level??&&infoBeanForm.level==2>selected="selected"</#if> value="2">冷门</option>    
              		          		    
				   		</select>	              
				   </span>
	              </td>
	            </tr>
	            <tr bgcolor="#FFFFFF">
	              <td width="130" align="center" bgcolor="#F5f5f5">彩种：</td>
	              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">	           	
					   <select name="infoBeanForm.lotteryType" size="1" id="infoBeanForm.lotteryType">
              		    <option selected="selected" value=""></option>           		    
              		    <#list lotteryTypeArr as type>
							<option <#if tagsInfoData??&&tagsInfoData.lotteryType?? && tagsInfoData.lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
						</#list>]
				   </select>
					   		
                   </span></td>
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
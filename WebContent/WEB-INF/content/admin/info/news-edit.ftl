<head>
<title>文章管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<#if infoBeanForm??&&infoBeanForm.id??><#if infoBeanForm.id==-1><meta name="menuItem" content="indexInfoEdit"/><#else><meta name="menuItem" content="indexInfoList"/></#if><#else><meta name="menuItem" content="indexInfoEdit"/></#if>

<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
<script type="text/javascript">  
	     function getInfoSubType(){
	            var indexInfoSaveForm = document.getElementById('indexInfoSaveForm');
	            var lotteryType = indexInfoSaveForm.elements['infoBeanForm.lotteryType'];
	            var lotteryTypeValue = lotteryType.options[lotteryType.selectedIndex].value; 
	            var infoType = indexInfoSaveForm.elements['infoBeanForm.type'];
	            var infoTypeValue = infoType.options[infoType.selectedIndex].value; 
		        $.ajax({ 
					type: 'GET',
					cache : false,
					url: "<@c.url value='/admin/info/news!selectInfoSubType.action' />?infoType="+infoTypeValue+"&lotteryType="+lotteryTypeValue,
					success : function(data, textStatus) {
						var jsonObj = toJsonObject(data);
						try {
						    if (jsonObj.infoTypeList != null){
						        var selectObj =  indexInfoSaveForm.elements['infoBeanForm.subType'];
						        selectObj.options.length = 0; 
						        for(var i=0;i<jsonObj.infoTypeList.length;i++){
						             selectObj.options.add(new Option( jsonObj.infoTypeList[i].name, jsonObj.infoTypeList[i].obj)); 
						        }
						        
						        var selectTag1 = indexInfoSaveForm.elements['infoBeanForm.tags1'];
						        var selectTag2 = indexInfoSaveForm.elements['infoBeanForm.tags2'];
						        var selectTag3 = indexInfoSaveForm.elements['infoBeanForm.tags3'];
						        selectTag1.options.length = 0; 
						        selectTag2.options.length = 0; 
						        selectTag3.options.length = 0; 
						        selectTag1.options.add(new Option("", ""));
						        selectTag2.options.add(new Option("", ""));
						        selectTag3.options.add(new Option("", ""));
						        
						        for(var i=0;i<jsonObj.tagsInfoDatas.length;i++){
						             selectTag1.options.add(new Option(jsonObj.tagsInfoDatas[i].name, jsonObj.tagsInfoDatas[i].obj)); 
 						             selectTag2.options.add(new Option(jsonObj.tagsInfoDatas[i].name, jsonObj.tagsInfoDatas[i].obj)); 
 						             selectTag3.options.add(new Option(jsonObj.tagsInfoDatas[i].name, jsonObj.tagsInfoDatas[i].obj)); 
						        }
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
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.InfoType@values()") />
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />
<#assign infoTitleColorArr=stack.findValue("@com.cai310.lottery.common.InfoTitleColor@values()") />
<script type="text/javascript">
     tinyMCE.init({
		mode : "textareas",
		theme : "advanced",
		language : "zh-cn" ,
		
		plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups,autosave",

		theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,formatselect,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
		theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
		theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,restoredraft",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : true,

		content_css : "${base}/tiny_mce/word/css/word.css",
        template_external_list_url : "${base}/tiny_mce/word/lists/template_list.js",
		external_link_list_url : "${base}/tiny_mce/word/lists/link_list.js",
		external_image_list_url : "${base}/tiny_mce/word/lists/image_list.js",
		media_external_list_url : "${base}/tiny_mce/word/lists/media_list.js",
		template_replace_values : {
			username : "Some User",
			staffid : "991234"
		}
	});
	 function indexInfoSaveMethod(){
        					var content = tinyMCE.getInstanceById("infoBeanForm.content").getBody().innerHTML;
			                var indexInfoSaveForm = document.getElementById('indexInfoSaveForm');
                            indexInfoSaveForm.elements['infoBeanForm.content'].value = content;
						    var options = {
								type : 'POST',
								cache : false,
								data : {
									ajax : 'true'
								},
								success : function(data, textStatus) {
									var jsonObj = toJsonObject(data);
									var msg = getCommonMsg(jsonObj);
									if (jsonObj.success == true) {
										alert(jsonObj.msg);
									    window.location.reload();
									} else {
										alert(jsonObj.msg);
									}
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									alert('提交请求失败.');
								},
								beforeSend : function(XMLHttpRequest) {
								},
								complete : function(XMLHttpRequest, textStatus) {
								}
							};
							$(indexInfoSaveForm).ajaxSubmit(options);
							return false;
   }
</script>
	                     <form method="post" id="indexInfoSaveForm" action="<@c.url value="/admin/info/news!newsInfoSave.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}" onsubmit="return indexInfoSaveMethod();">
	   						    <table width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
						        <input name="infoBeanForm.id"  type="hidden" value="<#if infoBeanForm??&&infoBeanForm.id??>${infoBeanForm.id!}<#else>-1</#if>"/>
						        <tr bgcolor="#F5f5f5">
					              <td align="center" valign="top">&nbsp;</td>
					              <td bgcolor="#F5f5f5">
					                 <#if infoBeanForm??&&infoBeanForm.id??><#if infoBeanForm.id==-1>新增<#else>修改</#if><#else>新增</#if>
					              </td>
					            </tr>
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">彩种：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="infoBeanForm.lotteryType" size="1" onchange="getInfoSubType();return false;">
					                        <option selected="selected" value=""></option>
					              		    <#list lotteryTypeArr as type>
												<option <#if (infoBeanForm??&&infoBeanForm.lotteryType?? && infoBeanForm.lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
											</#list>]
									   </select>
									   首页公告：<input type="checkbox" name="infoBeanForm.isNotice" value="1" <#if (infoBeanForm??&&infoBeanForm.isNotice??&&infoBeanForm.isNotice==1)>checked="checked"</#if> />
	                              	  栏目公告：<input type="checkbox" name="infoBeanForm.isNotice" value="2" <#if (infoBeanForm??&&infoBeanForm.isNotice??&&infoBeanForm.isNotice==2)>checked="checked"</#if> />
	                              
	                               </span></td>
					            </tr>
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">类型：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="infoBeanForm.type" size="1" onchange="getInfoSubType();return false;">
						                   <#list infoTypeArr as type>
												<option <#if (infoBeanForm??&&infoBeanForm.type??&&type==infoBeanForm.type)>selected="selected"</#if> value="${type}">${type.typeName!}</option>
										   </#list>
									   </select>
	                               </span></td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">子类型：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="infoBeanForm.subType">
					              		  <#if infoTypeList??>
						                   <#list infoTypeList as type>
												<option <#if (infoBeanForm??&&infoBeanForm.subType??&&type==infoBeanForm.subType)>selected="selected"</#if> value="${type}">${type.typeName!}</option>
										   </#list>
										  </#if>
					              		</select>
	                               </span></td>
					            </tr>
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">标题颜色：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="infoBeanForm.titleColor" size="1">
						                   <#list infoTitleColorArr as color>
												<option <#if (infoBeanForm??&&infoBeanForm.titleColor??&&color==infoBeanForm.titleColor)>selected="selected"</#if> value="${color}">${color.colorName!}</option>
										   </#list>
									   </select>
	                               </span></td>
					            </tr>
					           <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">长标题：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                <input name="infoBeanForm.title" value="<#if infoBeanForm??&&infoBeanForm.title??>${infoBeanForm.title!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="50" />
					              </span></td>
					            </tr>
					            
							    <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">短标题：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                <input name="infoBeanForm.shortTitle" value="<#if infoBeanForm??&&infoBeanForm.shortTitle??>${infoBeanForm.shortTitle!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'"  maxlength="15" size="24" />
					              </span></td>
					            </tr>
							  
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">标题连接：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<input name="infoBeanForm.titleLink" value="<#if infoBeanForm??&&infoBeanForm.titleLink??>${infoBeanForm.titleLink!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="50" />
	                               </span></td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">首页排序：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						                 <select name="infoBeanForm.level">
						                        <option <#if infoBeanForm??&&infoBeanForm.level==100>selected="selected"</#if> value="100">默认</option>
												<#list 1..10 as c>
													<option <#if infoBeanForm??&&infoBeanForm.level==c>selected="selected"</#if> value="${c}">${c!}</option>
												</#list>
												
									     </select>
						            </span>
					              </td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">关键字/专家荐号号码：</td>
						              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						                <input name="infoBeanForm.keywords" value="<#if infoBeanForm??&&infoBeanForm.keywords??>${infoBeanForm.keywords!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="100" />
						              </span>
					              </td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">页面描述：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                <input name="infoBeanForm.description" value="<#if infoBeanForm??&&infoBeanForm.description??>${infoBeanForm.description!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="100" />
					              </span></td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">标签词：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              <select name="infoBeanForm.tags1">
					              				<option value=""></option>	
													<#list tagsInfoDatas as data>
														<option <#if (infoBeanForm??&&infoBeanForm.tags1??&&infoBeanForm.tags1==(data.id+":"+data.tags))>selected="selected"</#if> value="${data.id+":"+data.tags}">${data.tags!}</option>
													</#list>	
									     </select>
					              
					              <select name="infoBeanForm.tags2">
					              				<option value=""></option>				              
												<#list tagsInfoDatas as data>
													<option <#if (infoBeanForm??&&infoBeanForm.tags2??&&infoBeanForm.tags2==(data.id+":"+data.tags))>selected="selected"</#if> value="${data.id+":"+data.tags}">${data.tags!}</option>
												</#list>												
									     </select>
									     
									<select name="infoBeanForm.tags3">
												<option value=""></option>								
												<#list tagsInfoDatas as data>
													<option <#if (infoBeanForm??&&infoBeanForm.tags3??&&infoBeanForm.tags3==(data.id+":"+data.tags))>selected="selected"</#if> value="${data.id+":"+data.tags}">${data.tags!}</option>
												</#list>												
									     </select>	     
					              </span></td>
					            </tr>
					            
					            <!--
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">标题链接：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						                   <input name="infoBeanForm.titleLink" value="<#if infoBeanForm??&&infoBeanForm.titleLink??>${infoBeanForm.titleLink!}<#else>#</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
					                </span>如果是#的话。默认是连接到文章详情
					              </td>
					            </tr>
					            <tr bgcolor="#F5f5f5">
					              <td width="130" align="center" bgcolor="#F5f5f5">标题类型：</td>
					              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
					                <input name="infoBeanForm.titleType" value="<#if infoBeanForm??&&infoBeanForm.titleType??>${infoBeanForm.titleType!}<#else>${infoType.typeName!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
					              </span>
					              </td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">标题类型链接：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						                   <input name="infoBeanForm.titleTypeLink" value="<#if infoBeanForm??&&infoBeanForm.titleTypeLink??>${infoBeanForm.titleTypeLink!}<#else>#</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
					                </span>如果是#的话。框内不出现连接
					              </td>
					            </tr>-->
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">内容：</td>
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
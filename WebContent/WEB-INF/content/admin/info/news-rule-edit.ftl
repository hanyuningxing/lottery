<head>
<title>规则管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<meta name="menuItem" content="ruleIndex"/>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
<script type="text/javascript">  
          function getInfoSubType(){
	            var indexInfoSaveForm = document.getElementById('saveForm');
	            var lotteryType = indexInfoSaveForm.elements['lotteryType'];
	            var lotteryTypeValue = lotteryType.options[lotteryType.selectedIndex].value; 
		        $.ajax({ 
					type: 'GET',
					cache : false,
					url: "<@c.url value="/admin/info/news!selectPlayType.action" />"+"?lotteryType="+lotteryTypeValue,
					success : function(data, textStatus) {
						var jsonObj = toJsonObject(data);
						try {
						    if (jsonObj.playTypeList != null){
						        var selectObj =  indexInfoSaveForm.elements['playType'];
						        selectObj.options.length = 0; 
						        $.each(jsonObj.playTypeList, function(key, value) {  
			                        selectObj.options.add(new Option(value,key)); 
		                        });  
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
	                     <form method="post" id="saveForm" action="<@c.url value="/admin/info/news!editRule.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}">
	   						    <table width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
						        <input name="id"  type="hidden" value="<#if id??>${id!}<#else></#if>"/>
						        <tr bgcolor="#F5f5f5">
					              <td align="center" valign="top">&nbsp;</td>
					              <td bgcolor="#F5f5f5">
					                 <#if id??>修改<#else>新增</#if>
					              </td>
					            </tr>
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">彩种：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="lotteryType" size="1" onchange="getInfoSubType();return false;">
					              		    <#list lotteryTypeArr as type>
												<option <#if (lotteryType?? && lotteryType==type)>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
											</#list>]
									   </select>
	                               </span></td>
					            </tr>
					             <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">玩法：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					              		<select name="playType" size="1">
					              		<#if playTypeMap??>
										 <#list playTypeMap?keys as key>
								         		 <option <#if (playType?? && playType==key)>selected="selected"</#if> value="${key}">${playTypeMap.get(key)!}</option>
								          </#list>
								        </#if>
									   </select>
	                               </span></td>
					            </tr>
					            <tr bgcolor="#FFFFFF">
					              <td width="130" align="center" bgcolor="#F5f5f5">内容：</td>
					              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						             <textarea id="ruleStr" name="ruleStr" rows="20" cols="30" style="width: 100%">
						             <#if ruleStr??>${ruleStr!}</#if>
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
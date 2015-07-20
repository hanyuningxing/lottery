<head>
<title>文章管理</title>
<meta name="menu" content="index"/>
<meta name="menuItem" content="indexInfoList"/>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
</head>
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.InfoType@values()") />
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />
<form name="queryForm" action="<@c.url value='/admin/info/index!indexInfoList.action'/>" method="get">
      <select name="type" size="1" onchange="document.forms['queryForm'].submit();return false;">
         <#list infoTypeArr as infoType>
				<option <#if infoType==type||(indexInfoData??&&indexInfoData.type??&&indexInfoData.type==infoType)>selected="selected"</#if> value="${infoType}">${infoType.typeName!}</option>
		</#list>
      </select>
      &nbsp;
</form>
<script type="text/javascript">
     tinyMCE.init({
		mode : "textareas",
		theme : "advanced",
		plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups,autosave",

		theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
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
	 var indexInfoSaveMethod=function(){
        					var content = tinyMCE.getInstanceById("indexInfoData.content").getBody().innerHTML;
			                var indexInfoSaveForm = $('indexInfoSaveForm');
                            indexInfoSaveForm.elements['indexInfoData.content'].value = content;
							var schemeFormOption = {
								onSuccess: function(responseText, responseXML){
								    var jsonObj = JSON.decode(responseText);
								    if(jsonObj["success"]){
								        window.alert(jsonObj["msg"]);
								        window.location.reload();
									}else{
									    window.alert(jsonObj["msg"]);
									}
								},
								onFailure: function(instance){
								      window.alert("报错出错");
								}
							};
							indexInfoSaveForm.set('send',schemeFormOption);
						    indexInfoSaveForm.send();
							return false;
   }
</script>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>标题</td>
      <td>标题连接</td>
      <td>标题类型</td>
      <td>标题类型链接 </td>
      <td>首页显示级别</td>
      <td>类型</td>
      <td>状态</td>
      <td>创建时间</td>
      <td>最后修改时间</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>
	      <td><#if data??>
                     <#if data.titleLink??>
                          <#if data.titleLink=='#'>
                            	<a href="${base}/info/info.action?id=${data.id!}" target="_blank">${data.title!}</a>
                          	<#else><a href="${data.titleLink}" target="_blank">${data.title!}</a>
                     	  </#if>
                          <#else><a href="${base}/info/info.action?id=${data.id!}" target="_blank">${data.title!}</a>
                     </#if>
               </#if>
          </td>
	      <td>${data.titleLink!}</td>
	      <td>${data.titleType!}</td>
	      <td>${data.titleTypeLink!}</td>
	      <td><#if data.level??><#if data.level==100>不显示<#else>${data.level!}</#if></#if></td>
	      <td>${data.type.typeName!}</td>
	      <td>${data.state.stateName!}</td>
	      <td>${data.createTime?string("MM-dd HH:mm:ss")}</td>
	      <td>${data.lastModifyTime?string("MM-dd HH:mm:ss")}</td>
	      <td><a href="${base}/admin/info/index!loadindexInfo.action?indexInfoData.id=${data.id}">编辑</a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="11">无记录</td>
	    </tr>
    </#if>
  </table>
    <#import "../../../macro/pagination_admin.ftl" as b />
	<@b.page />
	<a href="${base}/admin/info/index!loadindexInfo.action?type=${type}">新建</a>
  <form method="post" id="indexInfoSaveForm" action="<@c.url value="/admin/info/index!indexInfoSave.action" />"  onkeydown="if(event.keyCode==13){new Event(event).stop();}" onsubmit="return indexInfoSaveMethod();">
   						    <table width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
					        <input name="indexInfoData.id"  type="hidden" value="<#if indexInfoData??&&indexInfoData.id??>${indexInfoData.id!}<#else>-1</#if>"/>
					        <tr bgcolor="#F5f5f5">
				              <td align="center" valign="top">&nbsp;</td>
				              <td bgcolor="#F5f5f5">
				                 <#if indexInfoData??&&indexInfoData.id??><#if indexInfoData.id==-1>新增<#else>修改</#if><#else>新增</#if>
				              </td>
				            </tr>
				             <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">类型：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				              		<select name="indexInfoData.type" size="1">
					                   <#list infoTypeArr as infoType>
											<option <#if indexInfoData??&&indexInfoData.type??&&infoType==indexInfoData.type||(type==infoType)>selected="selected"</#if> value="${infoType}">${infoType.typeName!}</option>
									   </#list>
								   </select>
                               </span></td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">状态：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
							            <select name="indexInfoData.state">
											<#list infoStateArr as infoState>
												<option <#if indexInfoData??&&indexInfoData.state==infoState>selected="selected"</#if> value="${infoState}">${infoState.stateName!}</option>
											</#list>
										</select>
					            </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">首页排序：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                 <select name="indexInfoData.level">
					                        <option <#if indexInfoData??&&indexInfoData.level==100>selected="selected"</#if> value="100">不显示</option>
											<#list 1..10 as c>
												<option <#if indexInfoData??&&indexInfoData.level==c>selected="selected"</#if> value="${c}">${c!}</option>
											</#list>
											
								     </select>
					            </span>
				              </td>
				            </tr>
						    <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">标题：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
				                <input name="indexInfoData.title" value="<#if indexInfoData??&&indexInfoData.title??>${indexInfoData.title!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
				              </span></td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">标题链接：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                   <input name="indexInfoData.titleLink" value="<#if indexInfoData??&&indexInfoData.titleLink??>${indexInfoData.titleLink!}<#else>#</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
				                </span>如果是#的话。默认是连接到文章详情
				              </td>
				            </tr>
				            <tr bgcolor="#F5f5f5">
				              <td width="130" align="center" bgcolor="#F5f5f5">标题类型：</td>
				              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
				                <input name="indexInfoData.titleType" value="<#if indexInfoData??&&indexInfoData.titleType??>${indexInfoData.titleType!}<#else>${type.typeName!}</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
				              </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">标题类型链接：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                   <input name="indexInfoData.titleTypeLink" value="<#if indexInfoData??&&indexInfoData.titleTypeLink??>${indexInfoData.titleTypeLink!}<#else>#</#if>" type="text" class="heade_searchinput" onblur="this.className='heade_searchinput'" onclick="this.className='heade_searchclick'" size="30" />
				                </span>如果是#的话。框内不出现连接
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">内容：</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					             <textarea id="indexInfoData.content" name="indexInfoData.content" rows="20" cols="30" style="width: 100%">
					             <#if indexInfoData??&&indexInfoData.content??>${indexInfoData.content!}</#if>
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
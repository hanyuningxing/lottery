<head>
<title>文章管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<meta name="menuItem" content="indexInfoList"/>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
</head>
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.InfoType@values()") />
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />
<form name="queryForm" action="<@c.url value='/admin/info/news!tagsIndex.action'/>" method="get">
      <select name="lotteryType"  size="1" onchange="document.forms['queryForm'].submit();return false;">
			<option selected="selected" value=""></option>
			<#list lotteryTypeArr as type>
				<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
			</#list>
	  </select>
      
</form>
<script type="text/javascript">
   function oprNewsState(id,state){
        		  var url="<@c.url value="/admin/info/news!changeState.action?id=" />"+id+"&state="+state;
        		  var confirm_word = "您确定发布文章吗？";
        		  if('CANCEL'==state) var confirm_word = "您确定删除文章吗？";
				  if (confirm(confirm_word)) {
					  $.ajax({ 
							type: 'GET',
							cache : false,
							url: url,
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
							        
							},
							complete : function(XMLHttpRequest, textStatus) {
						    }
				    	});
				}			
   }
   
   function addTags(){
      var url="${base}/admin/info/news!tagsInfoToSave.action";
	  window.location.href=url;
   }
</script>
<tr bgcolor="#F5f5f5">
              <td align="center" valign="top">&nbsp;</td>
              <td bgcolor="#F5f5f5">
                 		<input type="button" value="新增" onclick="addTags();"/>    
              </td>
            </tr>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>标签词</td>
      <td>类型</td>
      <td>等级</td>
      <td>创建时间</td>
      <td>最后修改时间</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>	  
	      <td>${data.tags!}</td>
	      <td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
	      <td><#if data.level??&&data.level=0>热门<#elseif data.level??&&data.level=1>一般<#elseif data.level??&&data.level=2>冷门</#if></td>
	      <td>${data.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td>${data.lastModifyTime?string("yyyy-MM-dd HH:mm:ss")}</td> 
	      <td><a href="${base}/admin/info/news!loadTagInfo.action?infoBeanForm.id=${data.id}">编辑</a> | <a href="${base}/admin/info/news!delTags.action?infoBeanForm.id=${data.id}">删除</a></td>
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
</body>

</html>
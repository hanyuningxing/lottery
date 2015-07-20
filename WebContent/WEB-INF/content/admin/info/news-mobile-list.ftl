<head>
<title>文章管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<meta name="menuItem" content="mobileIndex"/>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
<script type="text/javascript">
   function oprNewsState(id,state){
        		  var url="<@c.url value="/admin/info/news!changeMobileNewsState.action?id=" />"+id+"&state="+state;
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
</script>
</head>
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.MobileInfoType@values()") />
<#assign infoStateArr=stack.findValue("@com.cai310.lottery.common.InfoState@values()") />

<form name="queryForm" action="<@c.url value='/admin/info/news!mobileIndex.action'/>" method="get">
       <select name="infoBeanForm.state" onchange="document.forms['queryForm'].submit();return false;">					                      
					<option value="">请选择状态</option>
					<#list infoStateArr as state>
							<option <#if infoBeanForm??&&infoBeanForm.state??&&infoBeanForm.state==state>selected="selected"</#if> value="${state}">${state.stateName}</option>
					</#list>												
		</select>
		<select name="infoBeanForm.mobileInfoType" onchange="document.forms['queryForm'].submit();return false;">					                      
					<option value="">请选择文章类型</option>		
					<#list infoTypeArr as type>
							<option <#if infoBeanForm??&&infoBeanForm.mobileInfoType??&&infoBeanForm.mobileInfoType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
					</#list>												
		</select>
</form>

<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>标题</td>
       <td>彩钟</td>
      <td>状态</td>
      <td>类型</td>
      <td>创建时间</td>
      <td>最后修改时间</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>
	      <td>${data.title!}</td>
	      <td><#if data.lotteryType??>${data.lotteryType.lotteryName!}</#if></td>
	      <td>${data.state.stateName!}</td>
	      <td>${data.mobileInfoType.typeName!}</td>
	      <td>${data.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td>${data.lastModifyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td><a href="#" onclick="oprNewsState(${data.id},'NORMAL');">发布</a> | <a href="${base}/admin/info/news!loadMobileNews.action?infoBeanForm.id=${data.id}">编辑</a> | <a href="#" onclick="oprNewsState(${data.id},'CANCEL');">删除</a></td>
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
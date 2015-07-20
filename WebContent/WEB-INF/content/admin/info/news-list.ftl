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
<form name="queryForm" action="<@c.url value='/admin/info/news!newsInfoList.action'/>" method="get">
      <select name="lotteryType"  size="1" onchange="document.forms['queryForm'].submit();return false;">
			<option selected="selected" value="">全部</option>
			<#list lotteryTypeArr as type>
				<option <#if lotteryType?? && lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
			</#list>
	  </select>
      <select name="infoType" size="1" onchange="document.forms['queryForm'].submit();return false;">
       	<option selected="selected" value="">全部</option>
         <#list infoTypeArr as type>
				<option <#if infoType??&&infoType==type>selected="selected"</#if> value="${type}">${type.typeName!}</option>
		</#list>
      </select>
      其他类型：
      <select name="isNotice" size="1" onchange="document.forms['queryForm'].submit();return false;">
			<option selected="selected" value="">全部</option>
			<option <#if isNotice??>selected="selected"</#if> value="1">首页文章</option>
      </select>
      &nbsp;
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
</script>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>标题</td>
      <td>首页显示级别</td>
      <td>类型</td>
      <td>子类型</td>
      <td>状态</td>
      <td>创建时间</td>
      <td>创建人</td>
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
                            	<a href="${base}/info/news-${data.id!}.html" target="_blank">${data.title!}</a>
                          	<#else><a href="${base}/info/news-${data.id!}.html" target="_blank">${data.title!}</a>
                     	  </#if>
                          <#else><a href="${base}/info/news-${data.id!}.html" target="_blank">${data.title!}</a>
                     </#if>
               </#if>
               	  <#if data.state!='CANCEL'&&data.isNotice??&&data.isNotice==1>
			      	<span  <#if data.state=='NORMAL'> style="color:red;"</#if>>(首页文章)</span>
			      </#if>
			      <#if data.state!='CANCEL'&&data.isNotice??&&data.isNotice==2>
			   	        <span <#if data.state=='NORMAL'>style="color:red;"</#if>>(栏目公告)</span>
			      </#if>
          </td>
	      <td><#if data.level??><#if data.level==100>默认<#else>${data.level!}</#if></#if></td>
	      <td>${data.type.typeName!}</td>
	      <td>${data.subType.typeName!}</td>
	      <td>${data.state.stateName!}
	      </td>
	      <td>${data.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td>${data.author!}</td>
	      <td>${data.lastModifyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      
	      <td><a href="#" onclick="oprNewsState(${data.id},'NORMAL');">发布</a> | <a href="${base}/admin/info/news!loadNewsInfo.action?infoBeanForm.id=${data.id}">编辑</a> | <a href="#" onclick="oprNewsState(${data.id},'CANCEL');">删除</a></td>
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
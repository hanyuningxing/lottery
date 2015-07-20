<head>
<title>规则管理</title>
<meta name="decorator" content="adminJquery" />
<meta name="menu" content="index"/>
<meta name="menuItem" content="ruleIndex"/>
<script type="text/javascript" src="<@c.url value='/tiny_mce/tiny_mce.js'/>"></script>
</head>
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
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/info/news!editRule.action"><b>新增规则</b></a>
  </div>
</div>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>彩种</td>
      <td>玩法</td>
      <td>创建时间</td>
      <td>最后修改时间</td>
      <td>操作</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
	      <td>${data.id}</td>
	      <td>${data.lotteryType.lotteryName!}</td>
	      <td>
	     	 <#assign lotteryType=data.lotteryType/>
	         <#assign playTypeMap=data.getPlayTypeBy(lotteryType)/> 
	         <#if playTypeMap??>
				<#list playTypeMap?keys as key>
					<#if (data.playType?? && data.playType?string==key)>${playTypeMap.get(key)!}</#if>
				</#list>
			</#if>
	      </td>
	      <td>${data.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td>${data.lastModifyTime?string("yyyy-MM-dd HH:mm:ss")}</td>
	      <td><a href="${base}/admin/info/news!editRule.action?id=${data.id}">编辑</a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="11">无记录</td>
	    </tr>
    </#if>
  </table>
  </div>
    <#import "../../../macro/pagination_admin.ftl" as b />
	<@b.page />
</body>

</html>
<title>后台用户管理</title>
<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="fetch"/>
<script>
function fetchData(id){
		    var match_button_span = document.getElementById("match_button_span_"+id);
		    var buttonHtml = match_button_span.innerHTML
		    var loaddingHtml = "<img src='${base}/images/data-loading.gif'/>更新中";
		    match_button_span.innerHTML = loaddingHtml;
		    var url = "${base}/admin/security/schedule!fetchData.action?id="+id;
		    var jsonRequest = new Request.JSON({
		    	url: url, 
			    onComplete: function(resultObj, text){
			        match_button_span.innerHTML = buttonHtml;
			        if(resultObj != null){
			        	if(resultObj.success == true){
			        	    window.alert(resultObj.msg);
			        	    window.location.reload();
			        	}else{
			        		window.alert(resultObj.msg);
			        		 window.location.reload();
			        	}
			        }
			    }
			}).get({ 
			    '__t': new Date().getTime()
			});
		}

</script>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/security/user.action">彩票后台用户管理</a> → <a href="${base}/admin/security/user.action">后台用户管理</a>  <a href="${base}/adminLogin.jsp">退出登录</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">赛程捉取管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
    <a href="${base}/admin/security/schedule!save.action"><b>新增联赛</b></a>
  </div>
</div>
<div>
<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>联赛名称</td>
      <td>联赛赛季</td>
      <td>联赛轮次</td>
      <td>联赛休战月</td>
      <td>是否开始更新</td>
      <td>是否更新完成</td>
      <td>编辑</td>
    </tr>
    <#if pagination??&& (pagination.result![])?size gt 0>
    	<#list pagination.result as data>
    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
    	  <td>${data.sclass_out}</td>
	      <td>${data.name!}</td>
	      <td>${data.starSeason}-${data.endSeason}</td>
	      <td>${data.count_round}</td>
	      <td>${data.endMonth}</td>
	      <td><#if data.isUpdate><font color="blue">是</font><#else><font color="red">否</font></#if></td>
	      <td><#if data.updated><font color="blue">是</font><#else><font color="red">否</font></#if></td>
	      <td><a href="${base}/admin/security/schedule!save.action?id=${data.id}"><b>编辑</b></a>|<span id="match_button_span_${data.id}"><a href="#" onclick="fetchData(${data.id});return false;"><b>更新</b></a></span>|<a href="${base}/football/user!scheduleList.action?id=${data.id}" target="_blank"><b>查看</b></a></td>
		</tr>
    	</#list>
    <#else> 
	    <tr>
	      <td class="trw" align="center" colspan="8">无记录</td>
	    </tr>
    </#if>
  </table>
   <#import "../../../macro/pagination_admin.ftl" as b />
  <@b.page />
</div>
</div>
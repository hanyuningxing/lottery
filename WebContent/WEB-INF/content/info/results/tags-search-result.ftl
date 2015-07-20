<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${keyWord!""} -标签-${webapp.webName}安全购彩平台</title>
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/main.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/pdzx.css"/>" rel="stylesheet" type="text/css" />

<link href="<@c.url value="/js/thinkbox/thinkbox.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/ss.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/lottery/count.js"/>"></script>	
<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>		
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>
 <script type="text/javascript">
 	function timeFilter(fiterType){
 		//var url = "${base}/info/news!hotSearch.action?infoBeanForm.fiterType="+fiterType;
 		//var lottery = $("select[name=lottery] option[selected]").val(); 
 		//var keywords = $("#infoBeanForm_keywords").val();
 		//if(lottery==null||lottery=="undefined"){
 		//	lottery="";
 		//}
       	//var url = "${base}/info/news!hotSearch.action?infoBeanForm.fiterType="+fiterType+"&&lottery="+lottery;
        //$("#fiterType").val(fiterType);
        //$("#hotSearch").submit();
        
        var keywords = document.getElementById('infoBeanForm_keywords').value;
		$("#fiterType").val(fiterType);        
		var lottery = document.getElementById('lottery').value;
		document.getElementById("hotSearch").action="/tag/"+lottery+"_"+encodeURI(keywords)+"_"+fiterType+"/";
		document.getElementById('hotSearch').submit();
		return false;
        
	   }
	   
	function search(){   
		var keywords = document.getElementById('infoBeanForm_keywords').value;
		var lottery = document.getElementById('lottery').value;
		document.getElementById("hotSearch").action="/tag/"+lottery+"_"+encodeURI(keywords)+"_/";
		document.getElementById('hotSearch').submit();
		return false;
	}
 </script>
</head>
<body>
<!-- 头开始 -->
 <#include "/common/header.ftl" />
<!-- 头结束 -->
<!-- 内容 -->
<#assign lotteryTypeArr =stack.findValue("@com.cai310.lottery.common.Lottery@values()") />


<div class="ss_content">
	<div class="ss_header">
		<div class="ss_header_left"><span>站内搜索</span></div>
		  <div class="ss_header_right">
			 <div class="ss_header_cz">
			 <form name="queryForm" id="hotSearch" action="" method="get">
			 <input type="hidden" name="infoBeanForm.fiterType" id="fiterType" value="">
				 <select name="lottery" id="lottery">
				    <option value="">选择彩种</option>
					<#list lotteryTypeArr as type>
							<option <#if lottery??&&lottery==type>selected</#if> value="${type}">${type.lotteryName}</option>
					</#list>
				</select><span> 站内搜索：</span>
				<#assign value=""/>								
				<#if infoBeanForm??&&infoBeanForm.keywords??>
					<#assign value=infoBeanForm.keywords/>
				</#if>
				 <input type="text" name="infoBeanForm.keywords" id="infoBeanForm_keywords" value="${value}"/>
				</form>
				</div>
			   <div  class="ss_header_ss"><img src="<@c.url value="/pages/images/btsearch.gif"/>" border="0" onclick="search();" target="_blank"/> </div>
		  </div>
		 <div class="kong_8ge"></div>
	</div>
 <div class="shaxiang10"></div>
 <div class="ss_main">
 <div class="ss_main_left">
 
    <div class="right_rigkuang">
	    <div class="right_chuhaobg">
			<div class="right_chuhaobglef">热门资讯</div>
			 </div>
		<div class="right_neik">
		    	<#if allNewsList?? && allNewsList?size gt 0>
				<#list allNewsList as data>
					<a href="/info/${data.id!}.html" target="_blank"><#if data.lotteryType??>[${data.lotteryType.lotteryName!}]</#if>
								${data.shortTitle!}
				    	</a><br/>
				    	
				</#list>
			  </#if>		  
		</div>
	</div>

 </div>
 
 <div class="ss_main_right">
	 <div class="ss_main_title">
		  <div class="ss_main_title_left"><span>您当前的位置:<a href="/">${webapp.webName}买彩票 </a> >> 标签 <#if keyWord??&&keyWord!="">>>${keyWord!}</#if></span></div>
		  <div class="ss_main_title_right"><span>从全部结果搜索${pagination.totalCount!0}条</span></div>
	 </div>
	<div class="zxlb_lb">
		    <table style=" background:#fff; border-collapse:collapse; border-bottom:1px solid #E3E3E3; width:100%;"  
bordercolor="#FFFFFF" cellspacing="0" cellpadding="1" align="center" 
  border="1" >
			  <tr class="center_tablegray_zxlb" align="center">
				<td width="10%" height="28">彩种 </td>
				<td width="10%">资讯类别</td>
				<td width="38%">标题 </td>
				<td width="32%">标签</td>
				<td width="10%">日期</td>
			  </tr>
			  <#if pagination??&&pagination.result??&&pagination.totalCount gt 0>
			  <#list pagination.result as data>
				  <tr align="center" class="center_trw_zxlb" onMouseOver="this.className='center_trhover_zxlb'" onMouseOut="this.className='center_trw_zxlb'">
					<td height="32">${data.lotteryType.lotteryName!}</td>
					<td>${data.subType.typeName!}</td>
					<td class="tb_left"><a href="/info/${data.id!}.html" target="_blank"><#if data.subType??>[${data.subType.typeName!}]</#if>${data.title!}</a></td>
					<td class="tb_left">
						<#if results?size gt 0>
						<#assign result=results[data_index]>
							<#list result?keys as key>						
								<a href="/tag/${key!}.htm" target="_blank">${result[key]!}</a>
							</#list>
						</#if>
					</td>
					<td>${data.createTime}</td>
				  </tr>
			  </#list>
			  <#elseif pagination??&&pagination.totalCount == 0>
			  	   <tr class="center_tablegray_zxlb" align="center">
						<td class="center_trw_zxlb" align="center" colspan="4">暂无记录</td>
				  </tr>
			 </#if>
			</table>
			<div class="kong10"></div>
			<div class=" cleanboth pagelist" align="center">
				
			</div>
           <#if ((pagination.totalPages)!0) gt 1>
			    <div class="kong10"></div>
			    <div class=" cleanboth pagelist" align="center">
        		<#import "../../../macro/pagination.ftl" as b />
				<@b.page />
			    </div>
			</#if>
    </div>
	 </div>
	 
</div>
	
</div>
  <div class="shaxiang10"></div>
<!-- 内容 -->
<!-- 底部开始 -->
<#include "/common/footer.ftl" />


 <script type="text/javascript">
	$(document).ready(function(){

            //初始化双色球
            drawedIssue("ssq","双色球","true");
            setTimeout("gotoIssue()",1000) 
	});
	
   function drawedIssue(key,value,flag){
	 		//刷新投注 合买连接
           changeTouzhuAndHemai(key,value);
	       if(key!=-1||key!='-1'||key!="-1"){
				var url="${base}/"+key+"/result!drawedIssue.action";
				$.ajax({ 
						type: 'GET',
						cache : false,
						url: url,
						success : function(data, textStatus) {
						$("#issue_select").empty()
							var jsonObj = toJsonObject(data);
							try {
								if(jsonObj.options!=null){
									var optionObj ;
									for(var i=0;i<jsonObj.options.length;i++){
									  if(flag&&i==1) {
									  	$("#issue_select").append("<option value='"+jsonObj.options[i].value+"' selected=selected >"+jsonObj.options[i].word+"</option>");  //添加一项option
									  } else {
									  	$("#issue_select").append("<option value='"+jsonObj.options[i].value+"'>"+jsonObj.options[i].word+"</option>");  //添加一项option
									  }
									}
								}
							} catch (e) {
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							
						}
				});
		  }
	   }
	 
	 //刷新投注 合买连接
	function changeTouzhuAndHemai(key,value){
	   	    //动态改变投注合买连接
           var touzhuHref= "<a href='${base}/"+key+"/scheme!editNew.action'>"+value+"投注</a>";
           var hemaiHref= "<a href='${base}/"+key+"/scheme!subList.action'>"+value+"合买</a>";
            if(value=="排列三"){
	           	 touzhuHref= "<a href='${base}/"+key+"/scheme!editNew.action?playType=0'>"+value+"投注</a>";
	           	 hemaiHref= "<a href='${base}/"+key+"/scheme!subList.action?playType=0'>"+value+"合买</a>";
	        } 
	        if(value=="排列五"){
                 touzhuHref= "<a href='${base}/"+key+"/scheme!editNew.action?playType=1'>"+value+"投注</a>";
       			 hemaiHref= "<a href='${base}/"+key+"/scheme!subList.action?playType=1'>"+value+"合买</a>";
            }
            $("#touzhu").html(touzhuHref);
            $("#hemai").html(hemaiHref);
	}   
	   
	   
   //获取彩期号
   function gotoIssue(){
       var key=$("#lottery_select").val();
       var id = $("#issue_select").val();
       if(null==id||id=="") {
       return;
       }
       var  playType;
       if(key!=-1||key!='-1'||key!="-1"){
          if(id!=-1||id!='-1'||id!="-1"){
            var url="${base}/"+key+"/result!resultInfoBylotteryTypeAndPeriod.action?id="+id;
             $.ajax({ 
				type: 'GET',
				cache : false,
				url: url,
				success : function(data, textStatus) {
				 if(data!=null&&data!=""){
					 var jsonObj = toJsonObject(data);
					 
					 try {
						  if(jsonObj.msg!=null){
							    $("#rightkjhm").html(jsonObj.msg);
							}
						} catch (e) {
						}
					} else {
						$("#periodDataDiv").remove();
						  $("#rightkjhm").html("无记录");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				}
		   });
		  }
	   }
   }
</script>		
 <script type="text/javascript">
	function search(){   
		//var keywords = document.getElementById('infoBeanForm.keywords').value;
		//var lottery = document.getElementById('selectLottery').value;
		//document.getElementById("hotSearch").action="/tag/"+lottery+"_"+encodeURI(keywords)+"_/";
		document.getElementById('hotSearch').submit();
		return false;
	}
	</script>	
  <#assign lotteryTypeArr =stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
  <form name="queryForm" id="hotSearch" action="/info/news!hotSearch.action" method="post">
  <div id="zxlb_right">
  <div class="cai_ss">
     <div class="ss_title"><img src="<@c.url value= "/pages/images/fdj.gif"/>" /><span>热门搜索</span></div>
	  <div class="ss_content">
		  <div class="ss_left">		 
		  	<select name="lottery" id="selectLottery">
			    <option value="">选择彩种</option>
				<#list lotteryTypeArr as type>
						<option value="${type}">${type.lotteryName}</option>
				</#list>
			</select>
			<input type="text" name="infoBeanForm.keywords" id="infoBeanForm.keywords" value=""/></div>
		  <div class="ss_right"><button onclick="search();" target="_blank"><span>查询</span></button>  </div> <div class="kong_8ge"></div>
	 </div>
 </div>
 </form>
 	<div class="kong_8ge"></div>
 
    <div class="right_rigkuang">
	    <div class="right_chuhaobg">
			<div class="right_chuhaobglef"><h2>热门资讯</h2></div>
			<div class="right_chuhaobgrig"></div>
	    </div>
		<div class="right_rmzx_neik">
		    <ul>
		     <#if allNewsList?? && allNewsList?size gt 0>
				<#list allNewsList as data>
				    	<li><a href="/info/${data.id!}.html" target="_blank"><#if data.lotteryType??>[${data.lotteryType.lotteryName!}]</#if>
				    	<span <#if data??&&data.titleColor??> class="${data.titleColor.colorStyle}"</#if>> 
								${data.shortTitle!} 
						</span>
				    	</a></li>
				</#list>
			  </#if>
			 </ul>
		</div>
	</div>
	  
	
	<div class="kong_8ge"></div>
    <div class="right_rigkuang">
	  <div class="right_chuhaobg">
			<div class="right_chuhaobglef">
			  <h2>实战技巧</h2>
			</div>
	  </div>
	  <div class="right_szjq">
	     <ul>
		  
		 </ul> 
	  </div>
    </div>
	
	<div class="kong_8ge"></div>
   

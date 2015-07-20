<@override name="Description">
	<meta name="Description"content="${webapp.webName}北京单场在线投注服务，为广大彩民在线提供北京单场网上投注，手机北京单场网上服务，强大的过滤软件和预测分析，让你投注轻松，享受购彩，天天中奖。"/>
</@override>
 

<script type="text/javascript">
var Class = {
　create: function() {
　　return function() {
　　　this.initialize.apply(this, arguments);
　　}
　}
}
　　
Object.extend = function(destination, source) {
　　for (var property in source) {
　　　　destination[property] = source[property];
　　}
　　return destination;
}
　　
function addEventHandler(oTarget, sEventType, fnHandler) {
　　if (oTarget.addEventListener) {
　　　　oTarget.addEventListener(sEventType, fnHandler, false);
　　} else if (oTarget.attachEvent) {
　　　　oTarget.attachEvent("on" + sEventType, fnHandler);
　　} else {
　　　　oTarget["on" + sEventType] = fnHandler;
　　}
};　
</script>
<@override name="style">
	<link href="<@c.url value="/V1/css/jczq.css"/>" rel="stylesheet" type="text/css" />
</@override>

<@override name="top">
	<div id="jczqtop02" class="jztopbjdj">
	    <div class="tztopnews" >
	      <ul class="tztopnew">
	         <#if gonggaoList?? && gonggaoList?size gt 0>
			<#list gonggaoList as data>
				<li><a href="${base}/info/news-${data.id!}.html" target="_blank"><#if data.subType?? && data.subType!='NONE'>[${data.subType.typeName!}]&nbsp;&nbsp;</#if>${data.shortTitle!}</a></li>
			</#list>
		  </#if>
	      </ul>
	    </div>
	    <div class="tztopbt"> <a href="${base}/info/rule/rule.action?lottery=DCZC" target="_blank" class="tztopbt1">玩法介绍</a><br />
	      <a href="http://61.147.127.247:8012/forum.php?mod=forumdisplay&fid=45" class="tztopbt1">申请票样</a> </div>
	    <div class="tzday1" style="width:120px;padding-top:10px;">每天 09:00～05:00</div>
    </div>
</@override>

<@override name="info">
</@override>

<@override name="menu">
	<#if !playType??><#assign playType='SPF'></#if>
	<#assign SPF_RQSPF_show=(playType=='SPF') && salesMode?exists && salesMode=='COMPOUND'>
	<div class="menubg">
  	  <div class="menu">
	  	  <ul>
	  	  
	  	    <#assign playTypeArr=playTypeArr!(stack.findValue("@com.cai310.lottery.support.dczc.PlayType@values()")) />
	    	<#list playTypeArr as pt>
	                   <#if playType?? && pt==playType>
	       					 <li class="menuli03">
	       					 	<a href="${base}/${lotteryType.key}/${pt}-${shareType!}.html">${pt.text}</a>
	       					 </li>
	       		 			 <li class="menuli02"></li>
	       		 			 <#else>
	       		 			 <li class="menuli01"><a href="${base}/${lotteryType.key}/${pt}-${shareType!}.html">${pt.text}</a></li>
	       		 			 <li class="menuli02"></li>
	       		        </#if> 
	    	</#list>
		  </ul>
    </div>
  </div>
</@override>

<@extends name="/WEB-INF/content/common/simple-baseV1.ftl"/> 

<div class="kong10"></div>			  
		
		
		<script type="text/javascript">
 var index = 0;
$(document).ready(function() {

 	var url = "../../../xml/dczc/xyo.xml";
 	$.ajax({
		    url:url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");		    
		      
		    	$(xml).find("item").each(function(j){
		            var mid=$(this).attr("mid"); //取文本
		            var xSit = $(this).attr("xSit");		
		            var xLotyid = $(this).attr("xLotyid");  
		            var ySit = $(this).attr("ySit");            	        		              		            
		            var yLotyid = $(this).attr("yLotyid");    
		                    	        		              
		            var xUrl = "http://info.hclotto/index.php?controller=analysis&mid="+mid+"&lotyid="+xLotyid;         	        		              
		            var oUrl = "http://odds.hclotto/index.php?controller=detail&mid="+mid+"&sit="+ySit+"&lotyid="+yLotyid;         	        		              
		            var yUrl = "http://odds.hclotto/index.php?controller=detail&mid="+mid+"&sit="+xSit+"&lotyid="+xLotyid;         	        		              
		            $("#x_"+j).attr("href",xUrl);
		            $("#y_"+j).attr("href",yUrl);   	  
		            $("#o_"+j).attr("href",oUrl);    
		        }); 		    	
		    }
		});
		
	var oyp_url = "../../../xml/dczc/oyp.xml";	
	$("#op").css("display","");
	$("#yp").css("display","none");
		            
	$.ajax({
		    url:oyp_url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");	
	    		index = $(xml).find("item").length;	    
		    	$(xml).find("item").each(function(j){
		            var no=$(this).attr("no"); //取文本
		            var oz = $(this).attr("oz");		
		            var oh = $(this).attr("oh");  
		            var ok = $(this).attr("ok");            	        		              		            		              
		                    	        		              
		            $("#oz_"+j).html(oz);
		            $("#oh_"+j).html(oh);   	  
		            $("#ok_"+j).html(ok);    		             		            		           
	            
		        }); 		    	
		    }
		});	
});

function chgData(id){
	var oyp_url = "../../../xml/dczc/oyp.xml";	

	if(id=='1'){
		
		$("#pjzs").addClass("rebchar");
        $("#ypzs").removeClass("rebchar");
        
        $.ajax({
		    url:oyp_url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");	
	    		index = $(xml).find("item").length;	    
		    	$(xml).find("item").each(function(j){
		            var no=$(this).attr("no"); //取文本
		            var oz = $(this).attr("oz");		
		            var oh = $(this).attr("oh");  
		            var ok = $(this).attr("ok");            	        		              		            		         
		                    	        		              
		            $("#oz_"+j).html(oz);
		            $("#oh_"+j).html(oh);   	  
		            $("#ok_"+j).html(ok);    		            
		            	         	            	            
		        }); 		    	
		    }
		});	
        
        $("#op").css("display","");
		$("#yp").css("display","none");
		            
	}else{
		
		 $("#pjzs").removeClass("rebchar");
         $("#ypzs").addClass("rebchar");
         
         $.ajax({
		    url:oyp_url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");	
	    		index = $(xml).find("item").length;	    
		    	$(xml).find("item").each(function(j){
		            var no=$(this).attr("no"); //取文本
		            var yz = $(this).attr("yz");		
		            var yh = $(this).attr("yh");  
		            var yk = $(this).attr("yk");            	        		              		            		            
		                    	        		              
		            $("#oz_"+j).html(yz);
		            $("#oh_"+j).html(yh);   	  
		            $("#ok_"+j).html(yk);    		          
		            		         	            
		        }); 		    	
		    }
		});
         
         
         $("#op").css("display","none");
		 $("#yp").css("display","");
		            
	}
}

function chgBFData(id){
	var oyp_url = "../../../xml/dczc/oyp.xml";	

	if(id=='1'){
		
		$("#pjzs").addClass("rebchar");
        $("#ypzs").removeClass("rebchar");
        
        $.ajax({
		    url:oyp_url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");	
	    		index = $(xml).find("item").length;	    
		    	$(xml).find("item").each(function(j){
		            var oz = $(this).attr("oz");				                    	        		              
		            $("#oz_"+j).html(oz);		            	         	            	            
		        }); 		    	
		    }
		});	
        
        $("#op").css("display","");
		$("#yp").css("display","none");
		            
	}else{
		
		 $("#pjzs").removeClass("rebchar");
         $("#ypzs").addClass("rebchar");
         
         $.ajax({
		    url:oyp_url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");	
	    		index = $(xml).find("item").length;	    
		    	$(xml).find("item").each(function(j){
		            var oz = $(this).attr("oz");				                    	        		              
		            $("#yz_"+j).html(oz);		            	         	            	            
		        }); 		    	
		    }
		});	
         
         $("#op").css("display","none");
		 $("#yp").css("display","");
		            
	}
}
</script>

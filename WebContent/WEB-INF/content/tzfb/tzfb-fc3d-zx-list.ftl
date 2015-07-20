<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>福彩3D组选投注分布-按号码</title>
<link href="<@c.url value="/pages/css/sitety.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/topdownpublic.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/index.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/banner.css"/>" rel="stylesheet" type="text/css" />
<link href="<@c.url value="/pages/css/tzfb.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/common.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/cookie.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "/js/lottery/count.js"/>"></script>	
<script type="text/javascript" src="<@c.url value= "/js/thinkbox/thinkbox.js"/>"></script>		
<script type="text/javascript" src="<@c.url value= "/js/lottery/luck.js"/>"></script>	
<script type="text/javascript" src="<@c.url value= "/js/ssologin.js"/>"></script>

</head>
<body>
<!-- 头开始 -->
<#include "/common/header.ftl" />
<!-- 头结束 -->

<script type="text/javascript">
var p;	
var type;
var g;
$(document).ready(function() {
		var playType = '${playType}';
 		var period = '${curPeriod}';
 		var groupType ='${groupType}';
 		p=period;
 		type=playType;
 		g=groupType;
 		showAndView(playType);
			
 		$("#p_"+period).attr("selected",true);
 		$("#r_"+playType).attr("checked",true);
 		$("#g_"+groupType).attr("checked",true);
 		init(playType,period,groupType);
});

//改变统计方式
function ptChange(playType){
	showAndView(playType);
	
	type=playType;
	$("#r_"+playType).attr("checked",true);
	init(playType,p,g);
}
//改变期数
function pdChange(period){
	$("#p_"+period).attr("selected",true);
	$("#r_"+type).attr("checked",true);
	p=period;
	
	init(type,period,g);
}
//改变组
function pgChange(groupType){
	g = groupType;
	$("#p_"+p).attr("selected",true);	
	init(type,p,g);
}


function init(playType,period,groupType){
	var url;
	if(period.length>=6){
		period = period.substring(2, period.length);
	}
	if(playType=="1"){
 			url = "./../xml/fc3d/zuxuan/dz/"+period+"_"+groupType+".xml";
 	}else{
 			url = "./../xml/fc3d/zuxuan/dg/"+period+"_"+groupType+".xml";
 	}
 	
 	showAndView(playType);
 	$.ajax({
		    url:url,
		    type: 'GET',
		    dataType:'xml',
		    timeout: 1000,
		    error: function(xml){   	
		        alert('该期暂无数据...');
		    },
		    success: function(xml){
	    		var items = $(xml).find("items");
		    	var period = items.attr("period");
		    	var playType = items.attr("playType");
		    	var groupType = items.attr("groupType");
		    	$("#p_"+period).attr("selected",true);
		    	$("#r_"+p).attr("checked",true);
		    	$("#g_"+g).attr("checked",true);
			    	
		    	if(playType=="2"){
		    		var items = $(xml).find("item");
		    		$(xml).find("item").each(function(i){
			           	var bw = $(this).attr("bw");
			           	var sw = $(this).attr("sw");
			           	var gw = $(this).attr("gw");
			            var per = $(this).attr("per"); //取文本
			            var units = $(this).attr("units");		
			       
			            
			            if(groupType=="1"){
							$("#bh_"+i).html(bw);
				            $("#sh_"+i).html(sw);   	  
				            $("#gh_"+i).html(gw); 
				            $("#uh_"+i).html(units);  
				            $("#hh_"+i).css('width',per+"%"); 
						}else{
							$("#b1_"+i).html(bw);
				            $("#s1_"+i).html(sw);   	  
				            $("#g1_"+i).html(gw); 
				            $("#u1_"+i).html(units);  
				            $("#h1_"+i).css('width',per+"%"); 
						}
			                        	        		              
			            
		        	});	
		    	}else{	 
		    	
		    		$(xml).find("item").each(function(i){
			           	var gw = $(this).attr("gw");
			           	var per = $(this).attr("per");
			           	var units = $(this).attr("units");		        

			            $("#ge_"+i).css('width',per+"%");              	        		               
			            $("#gc_"+i).html(units); 
			            $("#gn_"+i).html(gw); 
		        	});				    		
		    	}
		    		    	   	    	
		    }
		});
}

//直选复式分析和叶签的展现
function showAndView(playType){
	if(playType=="1"){
	 		$("#fc3d_dz").show();
			$("#fc3d_dg").hide();
			if(g=="1"){		
				$("#sz").show();
				$("#lz").hide();
			}else{
				$("#sz").hide();
				$("#lz").show();
			}		
 		}else{
 			$("#fc3d_dg").show();
			$("#fc3d_dz").hide();
 		}
}

</script>

<!--资讯部分开始-->
<div class="an_box_fc">
  
  <div class="an_title"><!--标题选项卡开始-->
    <ul>
      <li><a title="当期投注号码分布" href="${base}/tzfb/tzfb!fc3d_link.action?playType=1&period=${curPeriod}&result=${result}">直选复式分析</a></li>
      <li class="an_cur"><a title="历史冷热号码分布" href="javascript:void(0);">组选复式分析</a></li>
    </ul>
    <span>满员复式数据采集时间:${curDate} 每隔10分钟更新一次</span>
  
  </div><!--标题选项卡结束-->
  
  
  <div id="">
    <div class="an_select2">
      <div class="an_sleft2">
	    <span class="gray">组选</span>&nbsp;
	    <label class="sele" for="">
          <input name="RadioGroup2" type="radio" id="g_1" value="1" onclick="pgChange('1');"/>
          组选三</label>
        <label for="">
          <input type="radio" name="RadioGroup2" value="2" id="g_2" onclick="pgChange('2');"/>
          组选六</label>
		
		<span class="gray">统计方式</span>&nbsp;
        <label class="sele" for="">
          <input name="RadioGroup1" type="radio" id="r_1" value="1" onclick="ptChange('1');"/>
          按单注号码</label>
        <label for="">
          <input type="radio" name="RadioGroup1" value="2" id="r_2" onclick="ptChange('2');"/>
          按单个号码</label>
        
        <span class="mar_right15">选择
        <select name="riqi" id="" onChange="pdChange(this.options[this.selectedIndex].value);">
          <#list periods as periodNum>
		  	<option value="${periodNum}" id="p_${periodNum}">${periodNum}</option>
		  </#list>
        </select>期</span>
		　排序按
        <select name="riqi" id="">
          <option value="1">注数顺序</option>
          <option>号码顺序</option>
        </select>
      </div> 
	  
	  <div class="kjh_right">
	     <div class="kjh_fgx"></div>
		   
			<table class="kjh_zjhm" width="240" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="65" class="kjh_zjhm_wz">开奖号码：</td>
				<td width="23" class="kjh_zjhm_chengq">${results[0]}</td>
				<td width="23" class="kjh_zjhm_chengq">${results[1]}</td>
				<td width="23" class="kjh_zjhm_chengq">${results[2]}</td>
				<td width="4">&nbsp;</td>
				<td width="100">试机号 0，1，13</td>
			  </tr>
		</table>

	  </div>
	  
  <div style=" clear:both;">	  
	  <div class="dchm_anniu1">
	     <a href="javascript:void(0);">导出全部号码</a>
	  </div>
	  <div style="clear:both;"></div>
    </div>
  </div>
    
    <div id="fc3d_dz">
      <div class="fcsd_blt" >
<!--三组开始-->
      <div id="sz">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zu_table">
          <tr>
            <th width="150" class="lstou">号码</td>
            <th width="303" class="lstou">注数/比例图</td>          
          </tr>

	          <#list 0..89 as i>
				  <tr>
		            <td>
					   <div class="zu_num">
					      <span class="zu_num_qiu" id="bh_${i}"></span>
						  <span class="zu_num_qiu" id="sh_${i}"></span>
						  <span class="zu_num_qiu" id="gh_${i}"></span>
					   </div>
					</td>
		            <td>
					   <div class="last_td">
					      <span class="blt_zu" id="uh_${i}"></span>
						  <span class="blt_tu">
						     <span class="blt_tu_heng" id="hh_${i}"></span>
						  </span>			   
					   </div>
					</td>
				  </tr>
			  </#list>	
        </table>
		</div>
<!--三组结束-->		
<!--六组开始-->		
<div id="lz">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zu_table">
          <tr>
            <th width="150" class="lstou">号码</td>
            <th width="303" class="lstou">注数/比例图</td>          
          </tr>

	          <#list 0..119 as i>
				  <tr>
		            <td>
					   <div class="zu_num">
					      <span class="zu_num_qiu" id="b1_${i}"></span>
						  <span class="zu_num_qiu" id="s1_${i}"></span>
						  <span class="zu_num_qiu" id="g1_${i}"></span>
					   </div>
					</td>
		            <td>
					   <div class="last_td">
					      <span class="blt_zu" id="u1_${i}"></span>
						  <span class="blt_tu">
						     <span class="blt_tu_heng" id="h1_${i}"></span>
						  </span>			   
					   </div>
					</td>
				  </tr>
			  </#list>	
        </table>
		</div>		
<!--六组结束-->		
	
		<div class="ckgd">
		   <div class="ckgd_left">
		       <span class="ckgd_left_zhong"></span>当期开奖号码
		   </div>
		</div>
		
      </div>
	  
	  
      <div class="fcsd_mnxh">
        <div class="mnxh_title">模拟选号</div>
		<div class="hmz_short">
		   <table width="350" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			  <tr>
				<td><span class="zu_num_qiu">1</span></td>
				<td><span class="zu_num_qiu">2</span></td>
				<td><span class="zu_num_qiu">3</span></td>
				<td><span class="zu_num_qiu">4</span></td>
				<td><span class="zu_num_qiu">5</span></td>
				<td><span class="zu_num_qiu">6</span></td>
				<td><span class="zu_num_qiu">7</span></td>
				<td><span class="zu_num_qiu">8</span></td>
				<td><span class="zu_num_qiu">9</span></td>
				<td><span class="qingkong_anniu"><a href="javascript:void(0);">清空</a></span></td>
			  </tr>			  
		   </table>
			<div class="xhl_anniu"><INPUT type="image" src="<@c.url value='/pages/images/xhl_anniu.gif' />" value=""/></div>
		</div>
		<div class="hmzjt"></div>
		
		<div class="hmz_nrk">
		    <textarea name="" cols="" rows=""></textarea>
			<div class="anniulang">
			   <span class="ljtz_anniu"><a href="javascript:void(0);">立即投注</a></span>
			   <span class="qbqk_anniu" style="margin-right:5px;"><a href="javascript:void(0);">清空全部</a></span>			   
			</div>
		</div>
		
      </div>
	  
	  
      <div class="clearb"></div>
    </div>



<!--单个号码统计开始-->
	<div id="fc3d_dg">
      <div class="fcsd_blt" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zu_table">
          <tr>
            <th width="150" class="lstou">号码</td>
            <th width="303" class="lstou">注数/比例图</td>          
          </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_0"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_0"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_0"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_1"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_1"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_1"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_2"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_2"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_2"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_3"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_3"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_3"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_4"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_4"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_4"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_5"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_5"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_5"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_6"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_6"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_6"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_7"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_7"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_7"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_8"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_8"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_8"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td>
			   <div class="zu_num">
				  <span class="zu_num_qiu" id="gn_9"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="gc_9"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="ge_9"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
        </table>
		
		<div class="ckgd">
		   <div class="ckgd_left">
		       <span class="ckgd_left_zhong"></span>当期开奖号码
		   </div>
		</div>
		
      </div>
	  
	  
      <div class="fcsd_mnxh">
        <div class="mnxh_title">模拟选号</div>
		<div class="hmz_short">
		   <table width="350" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			  <tr>
				<td><span class="zu_num_qiu">1</span></td>
				<td><span class="zu_num_qiu">2</span></td>
				<td><span class="zu_num_qiu">3</span></td>
				<td><span class="zu_num_qiu">4</span></td>
				<td><span class="zu_num_qiu">5</span></td>
				<td><span class="zu_num_qiu">6</span></td>
				<td><span class="zu_num_qiu">7</span></td>
				<td><span class="zu_num_qiu">8</span></td>
				<td><span class="zu_num_qiu">9</span></td>
				<td><span class="qingkong_anniu"><a href="javascript:void(0);">清空</a></span></td>
			  </tr>			  
		   </table>
			<div class="xhl_anniu"><INPUT type="image" src="<@c.url value='/pages/images/xhl_anniu.gif' />" value=""/></div>
		</div>
		<div class="hmzjt"></div>
		
		<div class="hmz_nrk">
		    <textarea name="" cols="" rows=""></textarea>
			<div class="anniulang">
			   <span class="ljtz_anniu"><a href="javascript:void(0);">立即投注</a></span>
			   <span class="qbqk_anniu" style="margin-right:5px;"><a href="javascript:void(0);">清空全部</a></span>			   
			</div>
		</div>
		
      </div>
	  
	  
      <div class="clearb"></div>
    </div>
<!--单个号码统计结束-->

    <div class="an_explain">
      <p>说明：<span class="gray">&quot;直选复式分析&quot;、&quot;组选复式分析&quot;仅指本站满员复式统计数据，不包括单式上传投注统计数据。</span></p>
      <p>组选三：<span class="gray">两个相同的数字和另外一个单码组成的号码，不分位置和顺序。</span></p>
      <p>组选六：<span class="gray">三个数字都不同且不分位置与顺序的号码。</span></p>
    </div>
	<div style="clear:both;"></div>
	
  </div>
</div>
<!--资讯部分结束-->



	  
	 


<!-- 底部开始 -->
<#include "/common/footer.ftl" />
	<script type="text/javascript">
	       var redirectUrl = document.referrer;
	       Cookies.set({name:"redirectUrl",value:redirectUrl,path: "/"}); 
	       $SSO.refresh_login_info();
	</script>
<!-- 底部开始 -->


</body>
</html>

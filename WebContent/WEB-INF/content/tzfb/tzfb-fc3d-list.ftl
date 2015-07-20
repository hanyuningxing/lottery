<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>福彩3D直选投注分布-按号码</title>
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
$(document).ready(function() {
		$("#fc3d_ahm").show();
		$("#fc3d_awz").hide();
 		var playType = '${playType}';
 		var period = '${curPeriod}';
 		p=period;
 		type=playType;
 		$("#p_"+period).attr("selected",true);
 		$("#r_"+playType).attr("checked",true);
 		init(playType,period);
});
//改变统计方式
function ptChange(playType){
	if(playType=="1"){
		$("#fc3d_ahm").show();
		$("#fc3d_awz").hide();
	}else{
		$("#fc3d_awz").show();
		$("#fc3d_ahm").hide();
	}
	$("#r_"+type).removeAttr("checked");
	type=playType;
	$("#r_"+playType).attr("checked",true);
	init(playType,p);
}
//改变期数
function pdChange(period){
	var playType = $("input[name=RadioGroup1][@checked]").val();
	$("#p_"+period).attr("selected",true);
	p=period;
	init(type,period);
}

function init(playType,period){
	var url;
	if(period.length>=6){
		period = period.substring(2, period.length);
	}
	if(playType=="1"){
 			url = "./../xml/fc3d/zhixuan/hmtj/20"+period+".xml";
 	}else{
 			url = "./../xml/fc3d/zhixuan/wztj/20"+period+".xml";
 	}
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
		    	$("#p_"+period).attr("selected",true);
		    	$("#r_"+playType).attr("checked",true);
		    	if(playType=="1"){
		    		$(xml).find("item").each(function(i){
			           //var red=$(this).children("red"); //取对象
			           	var no = $(this).attr("no");
			           	var bw = $(this).attr("bw");
			           	var sw = $(this).attr("sw");
			           	var gw = $(this).attr("gw");
			            var per=$(this).attr("per"); //取文本
			            var units = $(this).attr("units");		
			            
			            $("#ch_"+i).html(no);              	        		              
			            $("#bh_"+i).html(bw);
			            $("#sh_"+i).html(sw);   	  
			            $("#gh_"+i).html(gw); 
			            $("#uh_"+i).html(units);  
			            $("#hh_"+i).css('width',per+"%"); 
		        	});	
		    	}else{	 
		    		var baiAreas = $(xml).find("baiAreas");	
		    		baiAreas.find("item").each(function(i){
			           	var num = $(this).attr("num");
			           	var per = $(this).attr("per");
			           	var units = $(this).attr("units");		        

			            $("#bai_"+i).css('height',per+"%");              	        		               
			            $("#bc_"+i).html(units); 
			            $("#bn_"+i).html(num); 
		        	});	
		    				    		
		    		var shiAreas = $(xml).find("shiAreas");	
		    		shiAreas.find("item").each(function(i){
			           	var num = $(this).attr("num");
			           	var per = $(this).attr("per");
			           	var units = $(this).attr("units");		        

			            $("#shi_"+i).css('height',per+"%");              	        		               
			            $("#sc_"+i).html(units); 
			            $("#sn_"+i).html(num); 
		        	});	
		        	
		        	var geAreas = $(xml).find("geAreas");	
		    		geAreas.find("item").each(function(i){
			           	var num = $(this).attr("num");
			           	var per = $(this).attr("per");
			           	var units = $(this).attr("units");		        

			            $("#ge_"+i).css('height',per+"%");              	        		               
			            $("#gc_"+i).html(units); 
			            $("#gn_"+i).html(num); 
		        	});
		    	}
		    		    	   	    	
		    }
		});
}


</script>

<!--讯部分开始-->
<div class="an_box_fc">
  
  <div class="an_title"><!--标题选项卡开始-->
    <ul>
      <li class="an_cur"><a title="当期投注号码分布" href="javascript:void(0);" onclick="zxshow();">直选复式分析</a></li>
      <li><a title="历史冷热号码分布" href="${base}/tzfb/tzfb!fc3d_zx_link.action?playType=1&period=${curPeriod}&result=${result}&groupType=1">组选复式分析</a></li>
    </ul>
    <span>满员复式数据采集时间:${curDate} 每隔10分钟更新一次</span>
  
  </div><!--标题选项卡结束-->
  
  <div id="fc3d_zx">
  <div id="">
    <div class="an_select">
      <div class="an_sleft1">
        <label class="sele" for="">
          <input name="" type="radio" value="1" onclick="ptChange('1');" id="r_1"/>
          按号码统计</label>
        <label for="">
          <input type="radio" name="" value="2" onclick="ptChange('2');" id="r_2"/>
          按位置统计</label>
        
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
	  
	  <div class="kjh">
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
	  <div class="dchm_anniu">
	     <a href="javascript:void(0);">导出全部号码</a>
	  </div>
	  
    </div>
    
    <div id="fc3d_ahm">
      <div class="fcsd_blt" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="zu_table">
          <tr>
            <th width="70" class="lstou">序号</td>
            <th width="150" class="lstou">号码</td>
            <th width="303" class="lstou">注数/比例图</td>          
          </tr>
          <tr>
            <td id="ch_0"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_0"></span>
				  <span class="zu_num_qiu" id="sh_0"></span>
				  <span class="zu_num_qiu" id="gh_0"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_0"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_0"></span>
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_1"></td>
            <td>
			  <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_1"></span>
				  <span class="zu_num_qiu" id="sh_1"></span>
				  <span class="zu_num_qiu" id="gh_1"></span>
		      </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_1"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_1"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_2"</td>
            <td>
			  <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_2"></span>
				  <span class="zu_num_qiu" id="sh_2"></span>
				  <span class="zu_num_qiu" id="gh_2"></span>
		      </div>
			</td>
            <td>
			  <div class="last_td">
			      <span class="blt_zu" id="uh_2"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_2"></span>				  
				  </span>			  
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_3"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_3"></span>
				  <span class="zu_num_qiu" id="sh_3"></span>
				  <span class="zu_num_qiu" id="gh_3"></span>
			   </div>
			</td>
            <td>
			  <div class="last_td">
			      <span class="blt_zu" id="uh_3"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_3"></span>				  
				  </span>			   
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_4"></td>
            <td>
			  <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_4"></span>
				  <span class="zu_num_qiu" id="sh_4"></span>
				  <span class="zu_num_qiu" id="gh_4"></span>
		      </div>
			</td>
            <td>
			  <div class="last_td">
			      <span class="blt_zu" id="uh_4"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_4"></span>				  
				  </span>			   
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_5"></td>
            <td>
			  <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_5"></span>
				  <span class="zu_num_qiu" id="sh_5"></span>
				  <span class="zu_num_qiu" id="gh_5"></span>
		      </div>
			</td>
            <td>
			  <div class="last_td">
			      <span class="blt_zu" id="uh_5"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_5"></span>				  
				  </span>			   
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_6"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_6"></span>
				  <span class="zu_num_qiu" id="sh_6"></span>
				  <span class="zu_num_qiu" id="gh_6"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_6"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_6"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_7"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_7"></span>
				  <span class="zu_num_qiu" id="sh_7"></span>
				  <span class="zu_num_qiu" id="gh_7"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_7"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_7"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td  id="ch_8"></td>
            <td>
			  <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_8"></span>
				  <span class="zu_num_qiu" id="sh_8"></span>
				  <span class="zu_num_qiu" id="gh_8"></span>
		      </div>
			</td>
            <td>
			  <div class="last_td">
			      <span class="blt_zu" id="uh_8"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_8"></span>				  
				  </span>				   
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_9"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_9"></span>
				  <span class="zu_num_qiu" id="sh_9"></span>
				  <span class="zu_num_qiu" id="gh_9"></span>
		      </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_9"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_9"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_10"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_10"></span>
				  <span class="zu_num_qiu" id="sh_10"></span>
				  <span class="zu_num_qiu" id="gh_10"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_10"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_10"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_11"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_11"></span>
				  <span class="zu_num_qiu" id="sh_11"></span>
				  <span class="zu_num_qiu" id="gh_11"></span>
			   </div>
			</td>
            <td>
			  <div class="last_td">
			     <span class="blt_zu" id="uh_11"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_11"></span>				  
				  </span>			   
			  </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_12"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_12"></span>
				  <span class="zu_num_qiu" id="sh_12"></span>
				  <span class="zu_num_qiu" id="gh_12"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_12"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_12"></span>				  
				  </span>				   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_13"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_13"></span>
				  <span class="zu_num_qiu" id="sh_13"></span>
				  <span class="zu_num_qiu" id="gh_13"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_13"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_13"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
		  <tr>
            <td id="ch_14"></td>
            <td>
			   <div class="zu_num">
			      <span class="zu_num_qiu" id="bh_14"></span>
				  <span class="zu_num_qiu" id="sh_14"></span>
				  <span class="zu_num_qiu" id="gh_14"></span>
			   </div>
			</td>
            <td>
			   <div class="last_td">
			      <span class="blt_zu" id="uh_14"></span>
				  <span class="blt_tu">
				     <span class="blt_tu_heng" id="hh_14"></span>				  
				  </span>			   
			   </div>
			</td>
		  </tr>
        </table>
		
		<div class="ckgd">
		   <div class="ckgd_left">
		       <span class="ckgd_left_zhong"></span>当期开奖号码
		   </div>
		   <div class="ckgd_right">
		       <span class="ckgd_xsj"></span>
		       <a href="javascript:void(0);">查看更多的号码</a>			   
		   </div>
		</div>
		
      </div>
	  
	  
      <div class="fcsd_mnxh">
        <div class="mnxh_title">模拟选号结果</div>
		<div class="hmz">
		   <table width="393" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td class="ws">百位</td>
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
			  <tr>
				<td class="ws">十位</td>
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
			  <tr>
				<td class="ws">个位</td>
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
			<div class="xhl_anniu"><INPUT type="image" src="<@c.url value="/pages/images/xhl_anniu.gif"/>" value=""/></div>
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


<!--按位置统计开始-->
<div id="fc3d_awz">
      <div class="fcsd_blt" >          
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="an_table_awz">
          <tr>
            <td style="width:60px;" rowspan="2" class="baiwei">
			   百位
			</td>
            <td style="width:96px;" class="left_td">
			   <span class="an_sp1">比例</span><br />
			</td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_0"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_1"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_2"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_3"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_4"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_5"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_6"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_7"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="bai_8"></span></div></td>
            <td style="width:36px;" class="bright_no"><div class="an_percent"><span class="blue_per" id="bai_9"></span></div></td>
          </tr>
          <tr class="an_num1" id="">
            <td class="left_td"><span class="an_sp1">次数</span><br />
              <span class="an_sp2"><strong>模拟选号</strong><br />
              <a href="javascript:void(0);" id="" title="[清空选择]">[清空选择]</a></span>
			</td>
            <td ><div class="total_num"><span class="writing" id="bc_0"></span></div>
              <span class="blue_num" id="bn_0"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_1"></span></div>
              <span class="blue_num" id="bn_1"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_2"></span></div>
              <span class="blue_num" id="bn_2"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_3"></span></div>
              <span class="blue_num" id="bn_3"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_4"></span></div>
              <span class="blue_num" id="bn_4"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_5"></span></div>
              <span class="blue_num" id="bn_5"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_6"></span></div>
              <span class="blue_num"  id="bn_6"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_7"></span></div>
              <span class="blue_num" id="bn_7"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_8"></span></div>
              <span class="blue_num" id="bn_8"></span><span class="curkj_no"></span></td>
            <td class="bright_no" ><div class="total_num"><span class="writing"  id="bc_9"></span></div>
              <span class="blue_num" id="bn_9"></span><span class="curkj_no"></span></td>
          </tr>
        </table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="an_table_awz">
          <tr>
            <td style="width:60px;" rowspan="2" class="baiwei">
			   十位
			</td>
            <td style="width:96px;" class="left_td">
			   <span class="an_sp1">比例</span><br />
			</td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_0"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_1"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_2"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_3"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_4"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_5"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_6"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_7"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="shi_8"></span></div></td>
            <td style="width:36px;" class="bright_no"><div class="an_percent"><span class="blue_per" id="shi_9"></span></div></td>
          </tr>
          <tr class="an_num1" id="">
            <td class="left_td"><span class="an_sp1">次数</span><br />
              <span class="an_sp2"><strong>模拟选号</strong><br />
              <a href="javascript:void(0);" id="" title="[清空选择]">[清空选择]</a></span>
			</td>
            <td ><div class="total_num"><span class="writing"  id="bc_0"></span></div>
              <span class="blue_num" id="sn_0"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing"  id="bc_1"></span></div>
              <span class="blue_num" id="sn_1"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_2"></span></div>
              <span class="blue_num" id="sn_2"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_3"></span></div>
              <span class="blue_num" id="sn_3"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_4"></span></div>
              <span class="blue_num" id="sn_4"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_5"></span></div>
              <span class="blue_num" id="sn_5"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_6"></span></div>
              <span class="blue_num" id="sn_6"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_7"></span></div>
              <span class="blue_num" id="sn_7"></span><span class="curkj_no"></span></td>    
            <td ><div class="total_num"><span class="writing" id="bc_8"></span></div>
              <span class="blue_num" id="sn_8"></span><span class="curkj_no"></span></td>    
            <td class="bright_no" ><div class="total_num"><span class="writing"  id="bc_9"></span></div>
              <span class="blue_num"  id="sn_9"></span><span class="curkj_no"></span></td>
          </tr>
        </table>
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="an_table_awz">
          <tr>
            <td style="width:60px;" rowspan="2" class="baiwei">
			   个位
			</td>
            <td style="width:96px;" class="left_td">
			   <span class="an_sp1">比例</span><br />
			</td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_0"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_1"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_2"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_3"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_4"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_5"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_6"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_7"></span></div></td>
            <td style="width:36px;"><div class="an_percent"><span class="blue_per" id="ge_8"></span></div></td>
            <td style="width:36px;" class="bright_no"><div class="an_percent"><span class="blue_per" id="ge_9"></span></div></td>
          </tr>
          <tr class="an_num1" id="">
            <td class="left_td"><span class="an_sp1">次数</span><br />
              <span class="an_sp2"><strong>模拟选号</strong><br />
              <a href="javascript:void(0);" id="" title="[清空选择]">[清空选择]</a></span>
			</td>
            <td ><div class="total_num"><span class="writing"  id="gc_0"></span></div>
              <span class="blue_num" id="gn_0"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing"  id="gc_1"></span></div>
              <span class="blue_num" id="gn_1"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_2"></span></div>
              <span class="blue_num" id="gn_2"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_3"></span></div>
              <span class="blue_num" id="gn_3"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_4"></span></div>
              <span class="blue_num" id="gn_4"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_5"></span></div>
              <span class="blue_num" id="gn_5"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_6"></span></div>
              <span class="blue_num" id="gn_6"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="gc_7"></span></div>
              <span class="blue_num" id="gn_7"></span><span class="curkj_no"></span></td>    
            <td ><div class="total_num"><span class="writing" id="gc_8"></span></div>
              <span class="blue_num" id="gn_8"></span><span class="curkj_no"></span></td>    
            <td class="bright_no" ><div class="total_num"><span class="writing"  id="gc_9"></span></div>
              <span class="blue_num"  id="gn_9"></span><span class="curkj_no"></span></td>
          </tr>
        </table>
		
		<div class="ckgd">
		   <div class="ckgd_left">
		       <span class="ckgd_left_zhong"></span>当期开奖号码
		   </div>
		</div>
		
      </div>
	  
	  
      <div class="fcsd_mnxh">
        <div class="mnxh_title">模拟选号结果</div>
		<div class="hmz">
		   <table width="393" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td class="ws">百位</td>
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
			  <tr>
				<td class="ws">十位</td>
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
			  <tr>
				<td class="ws">个位</td>
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
			<div class="xhl_anniu"><INPUT type="image" src="<@c.url value="/pages/images/xhl_anniu.gif"/>" value=""/></div>
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
</div>  

	
	
    <div class="an_explain">
      <p>说明：<span class="gray">&quot;直选复式分析&quot;、&quot;组选复式分析&quot;仅指本站满员复式统计数据，不包括单式上传投注统计数据。</span></p>
      <p>组选三：<span class="gray">两个相同的数字和另外一个单码组成的号码，不分位置和顺序。</span></p>
      <p>组选六：<span class="gray">三个数字都不同且不分位置与顺序的号码。</span></p>
    </div>
	<div style="clear:both;"></div>
  </div>
  </div>
  
  
  <!--以下部分是组选复式分析-->
  
  
  
  
  
  
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

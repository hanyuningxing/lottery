<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>大乐透球投注分布</title>
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

<!--大乐透资讯部分开始-->
<div class="an_box">
  
  <div class="an_title"><!--标题选项卡开始-->
    <ul>
      <li class="an_cur"><a title="当期投注号码分布" href="javascript:void(0);">当期投注号码分布</a></li>
      <li><a title="历史冷热号码分布" href="javascript:void(0);">历史冷热号码分布</a></li>
    </ul>
    <span>满员复式数据采集时间:${curDate} 每隔半小时更新一次</span>
  
  </div><!--标题选项卡结束-->
  
 <script type="text/javascript">
var p;	
var type;
$(document).ready(function() {
 		//var playType = $("input[name=RadioGroup1][@checked]").val();
 		var playType = '${playType}';
 		//var period = $("select[name=pd] option[selected]").text();
 		var period = '${curPeriod}';
 		p=period;
 		type=playType;
 		$("#p_"+period).attr("selected",true);
 		init(playType,period);
});
//改变统计方式
function ptChange(playType){
	type=playType;
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
	if(playType=="1"){
 			url = "./../xml/dlt/hmtj/"+period+".xml";
 	}else{
 			url = "./../xml/dlt/dmtj/"+period+".xml";
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
		    	if(playType==$("#hm").val()){
		    		$("#hm").attr("checked",true);
		    		$("#dm").removeAttr("checked");
		    	}else{
		    		$("#dm").attr("checked",true);
		    		$("#hm").removeAttr("checked");
		    	}
		    	$(xml).find("red").each(function(i){
		           //var red=$(this).children("red"); //取对象
		            var per=$(this).attr("per"); //取文本
		            var count = $(this).attr("count");		
		            var num = $(this).attr("num");            	        		              
		            $("#h_"+i).css('height',per+"%");
		            $("#c_"+i).html(count);   	  
		            $("#n_"+i).html(num);    
		        });
		      
		    	$(xml).find("blue").each(function(j){
		            var per=$(this).attr("per"); //取文本
		            var count = $(this).attr("count");		
		            var num = $(this).attr("num");            	        		              
		            $("#bh_"+j).css('height',per+"%");
		            $("#bc_"+j).html(count);   	  
		            $("#bn_"+j).html(num);    
		        }); 
		    	
		    }
		});
}
</script>
  
  
  
  <div id="">
    <div class="an_select">
      <div class="an_sleft" id="">
        <label class="sele" for="">
          <input name="RadioGroup1" type="radio"  id="hm" value="1" onclick="ptChange('1');"/>
          号码投注统计</label>
        <label for="">
          <input type="radio" name="RadioGroup1"  value="2" id="dm" onclick="ptChange('2');"/>
          胆码投注统计</label>
        <span class="mar_right15">选择
        <select name="pd" id="pd" onChange="pdChange(this.options[this.selectedIndex].value);">
		  <#list periods as periodNum>
		  	<option value="${periodNum}" id="p_${periodNum}">${periodNum}</option>
		  </#list>
        </select>期
		</span>
		　排序按
        <select name="riqi" id="">
          <option  value="1" selected="selected">号码顺序</option>
          <option  value="2" >大小顺序</option>
        </select>
      </div>
	  
	  <div class="kjh">
	     <div class="kjh_fgx"></div>
		   
			<table class="kjh_zjhm" width="228" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td class="kjh_zjhm_wz">开奖号码：</td>
					<td class="kjh_zjhm_hq">${results[0]}</td>
					<td class="kjh_zjhm_hq">${results[1]}</td>
					<td class="kjh_zjhm_hq">${results[2]}</td>
					<td class="kjh_zjhm_hq">${results[3]}</td>
					<td class="kjh_zjhm_hq">${results[4]}</td>
					<td class="kjh_zjhm_lq">${results[5]}</td>
					<td class="kjh_zjhm_lq">${results[6]}</td>
			  </tr>
			</table>

	  </div>
	  
    </div>
    <div>
      <div class="an_redball"><strong class="font-s14 red2">红球统计</strong> <span class="gray">提示：点击号码按钮进行模拟选号</span></div>
      <table width="978px" border="0" cellspacing="0" cellpadding="0" class="an_table" >
        <tr>
          <td class="left_td"><span class="an_sp1">比例</span><br />
            <span class="an_sp2 gray">红球<br />
            </span></td>
          <td><div class="an_percent"><span class="red_per" id="h_0"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_1"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_2"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_3"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_4"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_5"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_6"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_7"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_8"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_9"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_10"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_11"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_12"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_13"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_14"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_15"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_16"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_17"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_18"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_19"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_20"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_21"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_22"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_23"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_24"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_25"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_26"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_27"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_28"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_29"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_30"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_31"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_32"></span></div></td>
          <td><div class="an_percent"><span class="red_per" id="h_33"></span></div></td>
          <td class="bright_no"><div class="an_percent"><span class="red_per" id="h_34"></span></div></td>
        </tr>
        <tr class="an_num" id="">
          <td class="left_td"><span class="an_sp1">次数</span><br />
            <span class="an_sp2"><strong>模拟选号</strong><br />
            <a href="#;" id="" title="[清空选择]">[清空选择]</a></span></td>
          <td ><div class="total_num"><span class="writing" id="c_0"></span></div>
            <span class="red_num" id="n_0"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_1"></span></div>
            <span class="red_num" id="n_1"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_2"></span></div>
            <span class="red_num" id="n_2"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_3"></span></div>
            <span class="red_num" id="n_3"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_4"></span></div>
            <span class="red_num" id="n_4"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_5"></span></div>
            <span class="red_num" id="n_5"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_6"></span></div>
            <span class="red_num" id="n_6"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_7"></span></div>
            <span class="red_num" id="n_7"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_8"></span></div>
            <span class="red_num" id="n_8"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_9"></span></div>
            <span class="red_num" id="n_9"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_10"></span></div>
            <span class="red_num" id="n_10"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_11"></span></div>
            <span class="red_num" id="n_11"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_12"></span></div>
            <span class="red_num" id="n_12"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_13"></span></div>
            <span class="red_num" id="n_13"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_14"></span></div>
            <span class="red_num" id="n_14"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_15"></span></div>
            <span class="red_num" id="n_15"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_16"></span></div>
            <span class="red_num" id="n_16"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_17"></span></div>
            <span class="red_num" id="n_17"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_18"></span></div>
            <span class="red_num" id="n_18"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_19"></span></div>
            <span class="red_num" id="n_19"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_20"></span></div>
            <span class="red_num" id="n_20"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_21"></span></div>
            <span class="red_num" id="n_21"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_22"></span></div>
            <span class="red_num" id="n_22"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_23"></span></div>
            <span class="red_num" id="n_23"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_24"></span></div>
            <span class="red_num" id="n_24"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_25"></span></div>
            <span class="red_num" id="n_25"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_26"></span></div>
            <span class="red_num" id="n_26"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_27"></span></div>
            <span class="red_num" id="n_27"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_28"></span></div>
            <span class="red_num" id="n_28"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_29"></span></div>
            <span class="red_num" id="n_29"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_30"></span></div>
            <span class="red_num" id="n_30"></span><span class="curkj_no"></span></td>
          <td ><div class="total_num"><span class="writing" id="c_31"></span></div>
            <span class="red_num" id="n_31"></span><span class="curkj_no"></span></td>
		  <td ><div class="total_num"><span class="writing" id="c_32"></span></div>
            <span class="red_num" id="n_32"></span><span class="curkj_no"></span></td> 
          <td ><div class="total_num"><span class="writing" id="c_33"></span></div>
            <span class="red_num" id="n_33"></span><span class="curkj_no"></span></td>             
          <td class="bright_no" ><div class="total_num"><span class="writing"id="c_34"></span></div>
            <span class="red_num" id="n_34"></span><span class="curkj_no"></span></td>
        </tr>
      </table>
    </div>
    <div>
      <div class="blueB_table">
        <div class="an_blueball"><strong class="font-s14 blue">蓝球统计</strong></div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="an_table">
          <tr>
            <td class="left_td"><span class="an_sp1">比例</span><br />
              <span class="an_sp2 gray">蓝球<br />
              </span></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_0"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_1"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_2"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_3"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_4"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_5"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_6"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_7"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_8"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_9"></span></div></td>
            <td><div class="an_percent"><span class="blue_per" id="bh_10"></span></div></td>         
            <td class="bright_no"><div class="an_percent"><span class="blue_per" id="bh_11"></span></div></td>
          </tr>
          <tr class="an_num" id="">
            <td class="left_td"><span class="an_sp1">次数</span><br />
              <span class="an_sp2"><strong>模拟选号</strong><br />
              <a href="javascript:void(0);" id="" title="[清空选择]">[清空选择]</a></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_0">461</span></div>
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
              <span class="blue_num" id="bn_6"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_7"></span></div>
              <span class="blue_num" id="bn_7"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_8"></span></div>
              <span class="blue_num" id="bn_8"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_9"></span></div>
              <span class="blue_num" id="bn_9"></span><span class="curkj_no"></span></td>
            <td ><div class="total_num"><span class="writing" id="bc_10"></span></div>
              <span class="blue_num" id="bn_10"></span><span class="curkj_no"></span></td>         
            <td class="bright_no" ><div class="total_num"><span class="writing" id="bc_11"></span></div>
              <span class="blue_num" id="bn_11"></span><span class="curkj_no"></span></td>
          </tr>
        </table>
      </div>
      <div class="si_results">
        <div class="si_title">模拟选号结果</div>
        <div class="si_number">
          <div> <span class="text red2">红球号码</span><span class="red2 number" id="">16 18 20 21</span> </div>
          <div> <span class="text blue">蓝球号码</span><span class="blue number" id="">24 05</span></div>
        </div>
        <div class="si_tips"><a href="javascript:void(0);" class="btn_Dora_b" id=""  target="_blank">立即投注</a> 您选择了<span id="">0</span>个红球，<span id="">0</span>个蓝球 <a href="" id="l" title="清空全部">[清空全部]</a><br />
          最多可选20个红球，16个蓝球 </div>
      </div>
      <div class="clearb"></div>
    </div>
    <div class="an_explain">
      <p>说明：<span class="gray">"号码投注统计、胆码投注统计、杀号投注统计"仅指本站满员复式统计数据，不包括单式投注统计数据。</span></p>
      <p>号码投注统计：<span class="gray">本站双色球当期投注用户选择的号码排序</span></p>
      <p>胆码投注统计：<span class="gray">本站双色球当期投注用户认为必出的号码排序</span></p>
      <p>杀号投注统计：<span class="gray">本站双色球当期投注用户认为不会出的号码排序</span></p>
    </div>
  </div>
</div>
<!--双色球资讯部分结束-->

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

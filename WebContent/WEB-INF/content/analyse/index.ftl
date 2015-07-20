<head>
	<title>彩票走势图-双色球走势图-福彩3D走势图-大乐透走势图-排列三走势图-排列五走势图</title>
	<meta name="decorator" content="news" />
	<script src="${base}/js/common.js" type="text/javascript" ></script>
	<link href="<@c.url value="/pages/css/zst.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value="/pages/css/zxlb.css"/>" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
	$(document).ready(function(){
			$("#lotteryTypeDiv a").click(function(){  
                var value= $(this).attr("value");
                $("#lottery").val(value);
                $("#subType").val("");
                $("#infoBeanForm").submit();
            });
            
			$("#infoTypeDiv a").click(function(){  
                var value= $(this).attr("value");
                $("#infoType").val(value);
                 if(""==value) {
                  $("#subType").val("");
                }
                getInfoSubType();
                $("#infoBeanForm").submit();
            });
             $("#subTypeDiv a").click(function(){  
                var value= $(this).attr("value");
                $("#subType").val(value);
                $("#infoBeanForm").submit();
            });
	});
	
	function getInfoSubType(){
	            var lotteryTypeValue = $("#lottery").val();; 
	            var infoTypeValue =  $("#infoType").val(); 
	            $("#subTypeDiv a").remove();
	            var add = "<a href='#' id='subTypeAll' value=''>全部</a>";
	            $("#zxzl").after(add);
		        $.ajax({ 
					type: 'GET',
					cache : false,
					url: "<@c.url value='/info/news!selectInfoSubType.action' />?infoType="+infoTypeValue+"&lottery="+lotteryTypeValue,
					success : function(data, textStatus) {
					    if(data!="") {
						var jsonObj = toJsonObject(data);
							try {
							    if (jsonObj.infoTypeList != null){
							        for(var i=jsonObj.infoTypeList.length-1;i>=0;i--){
								         var obj = jsonObj.infoTypeList[i].obj;
								         var href = "&nbsp;&nbsp;<a href='javascript:void(0);'  value='"+obj+"'>"+jsonObj.infoTypeList[i].name+"</a>";
								         $("#subTypeAll").after(href);
							        }
							        $("#subTypeDiv a").click(function(){  
						                var value= $(this).attr("value");
						                $("#subType").val(value);
						                $("#infoBeanForm").submit();
						            });
								}
							} catch (e) {
							}
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					
					}
			   });
	      }
	      
	  function chgZst(key){
	  		if('welfare'==key){
	  			$("#welfare_zst").css("display","");
	  			$("#ticai_zst").css("display","none");
	  			$("#keno_zst").css("display","none");
	  		}else if('ticai'==key){
	  			$("#welfare_zst").css("display","none");
	  			$("#ticai_zst").css("display","");
	  			$("#keno_zst").css("display","none");
	  		}else if('keno'==key){
	  			$("#welfare_zst").css("display","none");
	  			$("#ticai_zst").css("display","none");
	  			$("#keno_zst").css("display","");
	  		}
	  }    
</script>
<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />
<#assign infoTypeArr=stack.findValue("@com.cai310.lottery.common.InfoType@values()") />
<#assign subTypeArr=stack.findValue("@com.cai310.lottery.common.InfoSubType@values()") />
<!-- 内容开始 -->
<div id="index_center">
	<div class="zst_banner"><img src="<@c.url value= "/pages/images/zst_banner.jpg"/>" /></div>
	<div class="zst_ksrk">
		<b>走势图快速入口：</b>
		<a href="<@c.url value= "/gdel11to5/analyse.action"/>" target="_blank">广东11选5</a> 
		<a href="<@c.url value= "/sdel11to5/analyse.action"/>" target="_blank">山东11选5</a>  
		<a href="<@c.url value= "/el11to5/analyse.action"/>" target="_blank">江西11选5</a>  
		<a href="<@c.url value= "/ssq/analyse.action"/>" target="_blank">双色球</a>  
		<a href="<@c.url value= "/dlt/analyse.action"/>" target="_blank">大乐透</a>  
		<a href="<@c.url value= "/welfare3d/analyse.action"/>" target="_blank">3D</a>  
		<a href="<@c.url value= "/pl3/analyse.action"/>" target="_blank">排列3</a>
		<a href="<@c.url value= "/pl5/analyse.action"/>" target="_blank">排列5</a>  
	</div>

  <!-- 左边栏开始 -->
  <div id="zst_nindleft">
  
     <div class="zst_xzcz1">
		<div class="zst_dlbg1">
			<a class="zgflc1" href="javascript:void(0);" onclick="chgZst('welfare');" onMouseOver="this.className='zgflc1'" onMouseOut="this.className='zgflc'"></a>
			<a class="zgtyc" href="javascript:void(0);" onclick="chgZst('ticai');" onMouseOver="this.className='zgtyc1'" onMouseOut="this.className='zgtyc'"></a>
			<a class="zggpc" href="javascript:void(0);" onclick="chgZst('keno');" onMouseOver="this.className='zggpc1'" onMouseOut="this.className='zggpc'"></a>
		</div>
		<div class="zst_dlbg2"></div>
     </div>

	 <!--数字彩走势开始 -->
	 <div id="welfare_zst">
	 <div class="zst_fck">
	 	<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_fc_07.gif"/>" />
				<font>双色球</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/ssq/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />双色球走势图</a></span>		   
							<a href="<@c.url value= "/ssq/analyse!zhzs.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/ssq/analyse!lhzs.action"/>" target="_blank">红球走势</a>
							<a href="<@c.url value= "/ssq/analyse!lqzs.action"/>" target="_blank">蓝球走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/ssq/analyse!chpl.action"/>" target="_blank">开奖频率柱状图</a>			  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/ssq/analyse!lsbd.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_21.gif"/>" />双色球工具</a></span>		   
							<a href="<@c.url value= "/ssq/analyse!lsbd.action"/>" style="width:110px;" target="_blank">历史开奖查询对比</a>
							<a href="<@c.url value= "/ssq/scheme!editFilter.action"/>" style="width:70px;" target="_blank">在线缩水</a>
							<a href="<@c.url value= "/ssq/scheme!editSpinmatrix.action"/>" target="_blank">中6保5旋转矩阵</a>										
						</li>
					</ul>
				</div>
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/ssq/result!resultInfo.action"/>" class="ssqkj" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_16.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/ssq/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_20.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 	
		<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_fc_26.gif"/>" />
				<font>七乐彩</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1" style="margin-top:25px;">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/seven/analyse!chzs.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />七乐彩走势图</a></span>		   
							<a href="<@c.url value= "/seven/analyse!chzs.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/seven/analyse!dwzs.action"/>" target="_blank">定位走势</a>
							<a href="<@c.url value= "/seven/analyse!lhzs.action"/>" target="_blank">连号走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/seven/analyse!fhzs.action"/>" target="_blank">重号走势</a>
						   <a href="<@c.url value= "/seven/analyse!hzzs.action"/>" target="_blank">和值走势</a>
						   <a href="<@c.url value= "/seven/analyse!tmzs.action"/>" target="_blank">特码走势</a>		  
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan" style="padding-top:5px;">
				<a href="<@c.url value= "/seven/result!resultInfo.action"/>" class="ssqkj" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_27.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/seven/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_29.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	    <div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_fc_32.gif"/>" />
				<font>福彩3D</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/welfare3d/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />福彩3D走势图</a></span>		   
							<a href="<@c.url value= "/welfare3d/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/welfare3d/analyse!hezhi.action"/>" target="_blank">和值走势</a>
							<a href="<@c.url value= "/welfare3d/analyse!kuadu.action"/>" target="_blank">跨度走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/welfare3d/analyse!zhixuan.action"/>" target="_blank">直三走势图</a>			  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/welfare3d/analyse!g3Miss.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />福彩3D遗漏</a></span>		   
							<a href="<@c.url value= "/welfare3d/analyse!g3Miss.action"/>" target="_blank">组3遗漏</a>
							<a href="<@c.url value= "/welfare3d/analyse!g6Miss.action"/>" target="_blank">组6遗漏</a>										
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/welfare3d/result!resultInfo.action"/>" class="ssqkj" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_33.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/welfare3d/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_35.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	 <div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_fc_41.gif"/>" />
				<font>好彩1</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />好彩1走势图</a></span>		   
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=szzst"/>" target="_blank">数字走势</a>
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=sxzst"/>" target="_blank">生肖走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=jjfwzst"/>" target="_blank">季节方位走势</a>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=wszst"/>" target="_blank">尾数走势</a>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7zs.action?type=wxzst"/>" target="_blank">五行走势</a>			  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=hmyl"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />好彩1遗漏</a></span>		   
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=hmyl"/>" target="_blank">号码遗漏</a>
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=sxyl"/>" target="_blank">生肖遗漏</a>
							<a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=wsyl"/>" target="_blank">尾数遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=hsyl"/>" target="_blank">合数遗漏</a>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=wxyl"/>" target="_blank">五行遗漏</a>
						   <a href="<@c.url value= "/welfare36to7/analyse!w36to7yl.action?type=xtyl"/>" target="_blank">形态遗漏</a>			  
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/welfare36to7/result!resultInfo.action"/>" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_fc_42.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/welfare36to7/scheme.action"/>"><img src="<@c.url value= "/pages/images/zst_fc_44.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
			 </div>
		<!--数字彩走势结束-->
		
		<div class="zst_kong186"></div>
	 	
	 </div>
<!--高频彩走势开始 -->
	<div id="keno_zst" style="display:none">
		<div class="zst_fck">
	 	<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_gpc_03.gif"/>" />
				<font>重庆时时彩</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/ssc/analyse!generalAward.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />时时彩走势图</a></span>		   
							<a href="<@c.url value= "/ssc/analyse!generalAward.action"/>" target="_blank">二星综合走势</a>
							<a href="<@c.url value= "/ssc/analyse!lyel.action"/>" target="_blank">二星012路走势</a>
							<a href="<@c.url value= "/ssc/analyse!samSung.action"/>" target="_blank">三星综合走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/ssc/analyse!sxlyel.action"/>" target="_blank">三星012路走势</a>
							<a href="<@c.url value= "/ssc/analyse!dxds.action"/>" target="_blank">大小单双走势</a>
							<a href="<@c.url value= "/ssc/analyse!ssczs.action?type=wxzs"/>" target="_blank">五星走势</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/ssc/analyse!ssczs.action?type=exhz"/>" target="_blank">二星和值走势</a>
							<a href="<@c.url value= "/ssc/analyse!ssczs.action?type=sxhz"/>" target="_blank">三星和值走势</a>
							<a href="<@c.url value= "/ssc/analyse!ssczs.action?type=sxjo"/>" target="_blank">三星奇偶走势</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/ssc/analyse!ssczs.action?type=wnzs"/>" target="_blank">万能六码走势</a>		  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx1"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />时时彩遗漏</a></span>		   
							<a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx1"/>" target="_blank">一星直选遗漏</a>
							<a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx2"/>" target="_blank">二星直选遗漏</a>
							<a href="<@c.url value= "/ssc/analyse!hezhi.action?type=exhzyl"/>" target="_blank">二星和值遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>		   
							<a href="<@c.url value= "/ssc/analyse!zhixuan.action?type=zx3"/>" target="_blank">三星直选遗漏</a>
							<a href="<@c.url value= "/ssc/analyse!hezhi.action?type=sxhzyl"/>" target="_blank">三星和值遗漏</a>									
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan" style="padding-top:25px;">
				<a href="<@c.url value= "/ssc/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_gpc_06.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/ssq/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_09.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 	
		<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_gpc_12.gif"/>"/>
				<font>山东11选5</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/sdel11to5/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />11选5走势图</a></span>		   
							<a href="<@c.url value= "/sdel11to5/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/sdel11to5/analyse!qezs.action"/>" target="_blank">前二走势</a>
							<a href="<@c.url value= "/sdel11to5/analyse!qextzs.action"/>" target="_blank">前二形态走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/sdel11to5/analyse!qszs.action"/>" target="_blank">前三走势</a>
						   <a href="<@c.url value= "/sdel11to5/analyse!qshz.action"/>" target="_blank">前三和值走势</a>
						   <a href="<@c.url value= "/sdel11to5/analyse!lhzs.action"/>" target="_blank">连号走势</a>		  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/sdel11to5/analyse!qhzs.action"/>" target="_blank">重号走势</a>
						   <a href="<@c.url value= "/sdel11to5/analyse!jgh.action"/>" target="_blank">隔位号走势</a>
						   <a href="<@c.url value= "/sdel11to5/analyse!jgh.action"/>" target="_blank">开奖号频率柱状图</a>		  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=QEZX"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />11选5遗漏</a></span>		   
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=QEZX"/>" target="_blank">前二组选遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=QEDX"/>" target="_blank">前二直选遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=QSZX"/>" target="_blank">前三组选遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=QSDX"/>" target="_blank">前三直选遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=RAND2"/>" target="_blank">任二遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=RAND3"/>" target="_blank">任三遗漏</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=RAND4"/>" target="_blank">任四遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=RAND5"/>" target="_blank">任五遗漏</a>
							<a href="<@c.url value= "/sdel11to5/analyse!yilou.action?type=RAND6"/>" target="_blank">任六遗漏</a>			  
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan" style="padding-top:25px;">
				<a href="<@c.url value= "/sdel11to5/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_gpc_13.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/sdel11to5/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_16.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	    <div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_gpc_18.gif"/>" />
				<font>江西11选5</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/el11to5/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />11选5走势图</a></span>		   
							<a href="<@c.url value= "/el11to5/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/el11to5/analyse!qezs.action"/>" target="_blank">前二走势</a>
							<a href="<@c.url value= "/el11to5/analyse!qextzs.action"/>" target="_blank">前二形态走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/el11to5/analyse!qszs.action"/>" target="_blank">前三走势</a>
						   <a href="<@c.url value= "/el11to5/analyse!qshz.action"/>" target="_blank">前三和值走势</a>
						   <a href="<@c.url value= "/el11to5/analyse!lhzs.action"/>" target="_blank">连号走势</a>		  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/el11to5/analyse!qhzs.action"/>" target="_blank">重号走势</a>
						   <a href="<@c.url value= "/el11to5/analyse!jgh.action"/>" target="_blank">隔位号走势</a>
						   <a href="<@c.url value= "/el11to5/analyse!jgh.action"/>" target="_blank">开奖号频率柱状图</a>		  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/el11to5/analyse!yilou.action?type=QEZX"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />11选5遗漏</a></span>		   
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=QEZX"/>" target="_blank">前二组选遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=QEDX"/>" target="_blank">前二直选遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=QSZX"/>" target="_blank">前三组选遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/el11to5/analyse!yilou.action?type=QSDX"/>" target="_blank">前三直选遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=RAND2"/>" target="_blank">任二遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=RAND3"/>" target="_blank">任三遗漏</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/el11to5/analyse!yilou.action?type=RAND4"/>" target="_blank">任四遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=RAND5"/>" target="_blank">任五遗漏</a>
							<a href="<@c.url value= "/el11to5/analyse!yilou.action?type=RAND6"/>" target="_blank">任六遗漏</a>			  
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan" style="padding-top:25px;">
				<a href="<@c.url value= "/el11to5/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_gpc_19.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/el11to5/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_21.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	 	<div class="zst_fc_ssq">
			<div class="zst_cplogo"><img src="<@c.url value= "/pages/images/zst_gpc_24.gif"/>" /><font>群英会</font>			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/qyh/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />群英会走势图</a></span>		   
							<a href="<@c.url value= "/qyh/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/qyh/analyse!weizhi.action"/>" target="_blank">位置走势</a>
							<a href="<@c.url value= "/qyh/analyse!lianhao.action"/>" target="_blank">连号走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/qyh/analyse!chonghao.action"/>" target="_blank">重号走势</a>	  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/qyh/analyse!yilou.action"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />群英会遗漏</a></span>		   
							<a href="<@c.url value= "/qyh/analyse!yilou.action"/>" target="_blank">位置遗漏</a>
							<a href="<@c.url value= "/qyh/analyse!weixuan-1.action"/>" target="_blank">围二遗漏</a>
							<a href="<@c.url value= "/qyh/analyse!weixuan-2.action"/>" target="_blank">围三遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/qyh/analyse!oddEvenSummary-5.action"/>" target="_blank">任三遗漏</a>
							<a href="<@c.url value= "/qyh/analyse!hezhi.action"/>" target="_blank">围二和值遗漏</a>
							<a href="<@c.url value= "/qyh/analyse!lianhaoMiss.action"/>" target="_blank">围二连号遗漏</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/qyh/analyse!commonTailNum.action"/>" target="_blank">围二同尾遗漏</a>
							<a href="<@c.url value= "/qyh/analyse!oddEvenSummary-3.action"/>" target="_blank">围二大小奇偶</a>
							<a href="<@c.url value= "/qyh/analyse!oddEvenSummary-4.action"/>" target="_blank">围三大小奇偶</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/qyh/analyse!oddEvenSummary-5.action"/>" target="_blank">任三大小奇偶</a>			  
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan" style="padding-top:25px;">
				<a href="<@c.url value= "/qyh/result!resultInfo.action"/>" class="ssqkj" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_25.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/qyh/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_28.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
	   </div>

	 	<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_gpc_18.gif"/>" />
				<font>广东11选5</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/gdel11to5/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />11选5走势图</a></span>		   
							<a href="<@c.url value= "/gdel11to5/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/gdel11to5/analyse!qezs.action"/>" target="_blank">前二走势</a>
							<a href="<@c.url value= "/gdel11to5/analyse!qextzs.action"/>" target="_blank">前二形态走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/gdel11to5/analyse!qszs.action"/>" target="_blank">前三走势</a>
						   <a href="<@c.url value= "/gdel11to5/analyse!qshz.action"/>" target="_blank">前三和值走势</a>
						   <a href="<@c.url value= "/gdel11to5/analyse!lhzs.action"/>" target="_blank">连号走势</a>		  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/gdel11to5/analyse!qhzs.action"/>" target="_blank">重号走势</a>
						   <a href="<@c.url value= "/gdel11to5/analyse!jgh.action"/>" target="_blank">隔位号走势</a>
						   <a href="<@c.url value= "/gdel11to5/analyse!jgh.action"/>" target="_blank">开奖号频率柱状图</a>		  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=QEZX"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />11选5遗漏</a></span>		   
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=QEZX"/>" target="_blank">前二组选遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=QEDX"/>" target="_blank">前二直选遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=QSZX"/>" target="_blank">前三组选遗漏</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=QSDX"/>" target="_blank">前三直选遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=RAND2"/>" target="_blank">任二遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=RAND3"/>" target="_blank">任三遗漏</a>			  
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						    <a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=RAND4"/>" target="_blank">任四遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=RAND5"/>" target="_blank">任五遗漏</a>
							<a href="<@c.url value= "/gdel11to5/analyse!yilou.action?type=RAND6"/>" target="_blank">任六遗漏</a>			  
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan" style="padding-top:25px;">
				<a href="<@c.url value= "/gdel11to5/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_gpc_21c.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/gdel11to5/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_gpc_21b.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 	
	 </div>
		 </div>
		<!--高频彩走势结束-->
	  
	  
	  <!--体彩走势开始 -->
	<div id="ticai_zst" style="display:none">
	<div class="zst_fck">
	 	<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_tc_03.gif"/>" />
				<font>大乐透</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/dlt/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />大乐透走势图</a></span>		   
							<a href="<@c.url value= "/dlt/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/dlt/analyse!lhzs.action"/>" target="_blank">前区走势</a>
							<a href="<@c.url value= "/dlt/analyse!lqzs.action"/>" target="_blank">后区走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/dlt/analyse!chpl.action"/>" target="_blank" style="width:130px;">历史开奖数据查询对比</a>			  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/dlt/analyse.action"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_21.gif"/>" />超级大乐透工具</a></span>		   
							<a href="<@c.url value= "/dlt/analyse!lsdb.action"/>" target="_blank" style="width:110px;">历史开奖查询对比</a>
							<a href="<@c.url value= "/dlt/scheme!editFilter.action"/>" target="_blank" style="width:70px;">在线缩水</a>
							<a href="<@c.url value= "/dlt/scheme!editSpinmatrix.action"/>" target="_blank">中5保4旋转矩阵</a>										
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/dlt/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_tc_06.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/dlt/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_tc_09.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 	
		<div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_tc_12.gif"/>" />
				<font>22选5</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/tc22to5/analyse!chzs.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />22选5走势图</a></span>		   
							<a href="<@c.url value= "/tc22to5/analyse!chzs.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/tc22to5/analyse!dwzs.action"/>" target="_blank">定位走势</a>
							<a href="<@c.url value= "/tc22to5/analyse!lhzs.action"/>" target="_blank">连号走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/tc22to5/analyse!fhzs.action"/>" target="_blank">重号走势</a>
						   <a href="<@c.url value= "/tc22to5/analyse!hzzs.action"/>" target="_blank">和值走势</a>
						   <a href="<@c.url value= "/tc22to5/analyse!chpl.action"/>" target="_blank">开奖号频率柱状图</a>		  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/tc22to5/analyse!lsbd.action"/>" target="_blank" style="width:110px;"><img src="<@c.url value= "/pages/images/zst_fc_21.gif"/>" />22选5工具</a></span>		   
							<a href="<@c.url value= "/tc22to5/analyse!lsbd.action"/>" style="width:110px;" target="_blank">历史开奖查询对比</a>
							<a href="<@c.url value= "/tc22to5/scheme!editSpinmatrix.action"/>" target="_blank">中5保4旋转矩阵</a>										
						</li>
					</ul>
				</div>			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/tc22to5/result!resultInfo.action"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_tc_13.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/tc22to5/scheme.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_tc_15.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	    <div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_tc_18.gif"/>" />
				<font>排列三</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/pl3/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />排列三走势图</a></span>		   
							<a href="<@c.url value= "/pl3/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/pl3/analyse!hezhi.action"/>" target="_blank">和值走势</a>
							<a href="<@c.url value= "/pl3/analyse!kuadu.action"/>" target="_blank">跨度走势</a>										
						</li>
						<li>
						   <span class="kcai_cuti1">&nbsp;</span>
						   <a href="<@c.url value= "/pl3/analyse!zhixuan.action"/>" target="_blank">直三走势图</a>			  
						</li>
					</ul>
				</div>
				
				<div class="kong_3ge"></div>
				<div class="fczst_link1">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/pl3/analyse!g3Miss.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_37.gif"/>" />排列三遗漏</a></span>		   
							<a href="<@c.url value= "/pl3/analyse!g3Miss.action"/>" target="_blank">组3遗漏</a>
							<a href="<@c.url value= "/pl3/analyse!g6Miss.action"/>" target="_blank">组6遗漏</a>										
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/pl/result!resultInfo.action?playType=0"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_tc_19.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/pl/scheme.action?playType=0"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_tc_21.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
	 
	 
	 <div class="zst_fc_ssq">
			<div class="zst_cplogo">
				<img src="<@c.url value= "/pages/images/zst_tc_24.gif"/>" />
				<font>排列五</font>
			</div>
			
			<div class="zst_zhlb">
				<div class="fczst_link1" style="margin-top:25px;">
					<ul>
						<li>
						   <span class="zst_cuti1"><a href="<@c.url value= "/pl5/analyse.action"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_fc_10.gif"/>" />排列五走势图</a></span>		   
							<a href="<@c.url value= "/pl5/analyse.action"/>" target="_blank">综合走势</a>
							<a href="<@c.url value= "/pl5/analyse!hezhi.action"/>" target="_blank">和值走势</a>
							<a href="<@c.url value= "/pl5/analyse!zhixuan.action"/>" target="_blank">直选走势</a>										
						</li>
					</ul>
				</div>
			
			</div>
			
			<div class="zst_kjan">
				<a href="<@c.url value= "/pl/result!resultInfo.action?playType=1"/>" target="_blank" class="ssqkj"><img src="<@c.url value= "/pages/images/zst_tc_25.gif"/>" border="0" /></a>
				<a href="<@c.url value= "/pl/scheme.action?playType=1"/>" target="_blank"><img src="<@c.url value= "/pages/images/zst_tc_27.gif"/>" border="0" /></a> </div>		
			<div class="cp_clear1"></div>
		</div>
		
		<div class="zst_kong190"></div>
	 	
	 </div>
	
	
	
	  </div>
		<!--体彩彩走势结束-->
  </div>
  <!-- 左边栏结束 -->
  
  <!-- 右边栏开始 -->  
   <#include "../info/results/right-new-zst.ftl" />
  <!-- 右边栏开始 -->
 
</div>
<!-- 内容结束 -->

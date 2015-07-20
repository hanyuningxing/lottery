<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1生肖走势图 - 球客网</title>

<script type="text/javascript" src="../../../../js/analyse/welfare36to7/common.js""></script>
<script type="text/javascript" src="../../../../js/mootools-1.2.4-core-yc.js""></script>
<script type="text/javascript" src="../../../../js/analyse/excanvas.js""></script>
<script type="text/javascript" src="../../../../js/analyse/drawLine.js"></script>
<script type="text/javascript">
		window.addEvent('domready', function() {
			//$("MISS_EL").setStyle("display","none");
			//document.getElementById('MISS_EL').style.display = "none";
			drawLine();
		});
		
		function drawLine() {
			$("div_line").innerHTML = "";		
			[ 'shengxiao'].each(function(hashName, index) {
				var tagArr1 = $$('span[_cancas="cancas"][_group="'+hashName+'"]');
				var myfun = function() {
					drawCancas(tagArr1, 1);
				};
				myfun.delay(10);
			});
		}
	</script>

</head>
<body>
	<jsp:include page="top.jsp" />	
	<div id="ga">
		<table class="zstablegraybg" width="980" border="0" cellpadding="0" cellspacing="1">
           <tbody>
             <tr class="trtitlebg">
               <td rowspan="2" width="61"><select name="select" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
                 <%
								Integer sort = (Integer)request.getAttribute("sort");
								if(sort==0){
							%>
								<option selected="selected" value="0">期号</option>
							<%
								}else{
							%>	
								<option value="0">期号</option>		
							<%
								}
								if(sort==1){
							%>
								<option selected="selected" value="1">小到大</option>
								
							<%
								}else{
							%>
								<option value="1">小到大</option>			
							<%
								}
								if(sort==2){
							%>					
								<option selected="selected"  value="2">大到小</option>
							<%
								}else{
							%>
								<option value="2">大到小</option>							
							<%
								}
								
							%>
               </select></td>
               <td colspan="12" class="b1redpx">生肖走势</td>
               <td colspan="2" class="b1redpx">单双肖</td>
               <td colspan="2" class="b1redpx">是否重肖</td>
               <td colspan="3" class="b1redpx">012路肖</td>
               <td colspan="4" class="b1redpx">区间</td>
               <td colspan="2" class="b1redpx">天地肖</td>
               <td colspan="2" class="b1redpx">家禽野兽</td>
               <td colspan="2" class="b1redpx">吉凶肖</td>
               <td colspan="2" class="b1redpx">阴阳肖</td>
             </tr>
             <tr class="zsttrpinklig">
               <td width="30" class="">鼠</td>
               <td width="30" class="">牛</td>
               <td class="" width="30">虎</td>
               <td width="30">兔</td>
               <td width="30">龙</td>
               <td width="30">蛇</td>
               <td width="30">马</td>
               <td width="30">羊</td>
               <td width="30">猴</td>
               <td width="30">鸡</td>
               <td width="30">狗</td>
               <td width="30">猪</td>
               <td class="b1redpx" width="30">单肖</td>
               <td width="30"><span class="">双肖</span></td>
               <td width="30">重肖</td>
               <td width="30">非重</td>
               <td width="30">0路</td>
               <td width="30">1路</td>
               <td width="30">2路</td>
               <td width="30">1区</td>
               <td width="30">2区</td>
               <td width="30">3区</td>
               <td class="" width="30">4区</td>
               <td width="30">天肖</td>
               <td width="30">地肖</td>
               <td width="30">家禽</td>
               <td width="30">野兽</td>
               <td width="30">吉肖</td>
               <td width="30">凶肖</td>
               <td width="30">阳肖</td>
               <td width="30">阴肖</td>
             </tr>               
                    
             <%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<String, Integer> missMap = new HashMap<String, Integer>();					

						Integer[] NUMS = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
						//单肖: 鼠0、虎2、龙4、马6、猴8、狗10； 双肖：牛1、兔3、蛇5、羊7、鸡9、猪12。
						String[] sxStr = new String[]{"01 13 25","02 14 26","03 15 27","04 16 28","05 17 29","06 18 30","07 19 31","08 20 32","09 21 33","10 22 34","11 23 35","12 24 36"};
						for(String sx:sxStr){
							missMap.put(sx,0);
						}
						int dxCount = 0;
						int sxCount = 0;
						int index = 0;
						int fcxCount = 0;
						int cxCount = 0;
						int lCount = 0;
						int yCount= 0;
						int eCount = 0;
						int yqCount = 0;
						int eqCount = 0;
						int sqCount = 0;
						int siqCount = 0;
						int txCount = 0;
						int dixCount = 0;
						int jqCount = 0;
						int ysCount = 0;
						int jxCount = 0;
						int xxCount = 0;
						int yinxCount = 0;
						int yangxCount = 0;
						for (Welfare36to7MissDataInfo missData : datas) {
							String resultDatas = missData.getResult();
							String periodNumber = missData.getPeriodNumber();
							String[] arr = resultDatas.split(",");
							String result = arr[6];
							if(result.length()<2)result = "0"+arr[6];
							Integer num = new Integer(result);
							
							for(String sx:sxStr){
								if(sx.indexOf(result)==-1){
									missMap.put(sx,missMap.get(sx)+1);
								}else{
									missMap.put(sx,0);
								}
							}
							String shengxiao = "";
					%>
			<tr class="trw">
               <td class="graychar333"><%=periodNumber %></td>
               <%
				for(String sx:sxStr){
               		if(missMap.get(sx)==0){
               			shengxiao = sx;
            	%>
            			<td width="30" class=""><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>
            	<%			
               		}else{
               	%>
               		<td width="30" class=""><%=missMap.get(sx) %></td>       			
               	<%
               		}
				}
               %>
               <%
				//单肖: 鼠0、虎2、龙4、马6、猴8、狗10； 双肖：牛1、兔3、蛇5、羊7、鸡9、猪12。

               		if(shengxiao.equals(sxStr[0])||shengxiao.equals(sxStr[2])||shengxiao.equals(sxStr[4])||shengxiao.equals(sxStr[6])||shengxiao.equals(sxStr[8])||shengxiao.equals(sxStr[10])){
						dxCount=0;	
               	%>
               		 <td width="30" class=""><img src="../../../../pages/images/g2.gif" width="12" height="12" /></td>
               <%					
               		}else{
						dxCount++;	
				%>	
               		<td width="30" class=""><%=dxCount %></td>						
				<%	
               		}
               	
               %>  
               <%
               		if(shengxiao.equals(sxStr[1])||shengxiao.equals(sxStr[3])||shengxiao.equals(sxStr[5])||shengxiao.equals(sxStr[7])||shengxiao.equals(sxStr[9])||shengxiao.equals(sxStr[11])){
						sxCount=0;	
               	%>
               		 <td width="30" class=""><img src="../../../../pages/images/g2.gif" width="12" height="12" /></td>
               <%					
               		}else{
						sxCount++;	
				%>	
               		<td width="30" class=""><%=sxCount %></td>						
				<%	
               		}             
               %> 
               <%          
					Welfare36to7MissDataInfo preMissData = null;
               		String preResult = "";
               		Integer preNum = null;
               		if(index<1){
               			fcxCount = 0;
               			cxCount++;
               		}else{
               			if(index<datas.size()){
    						preMissData = datas.get(index-1);
    						preResult = (preMissData.getResult().split(","))[6];
    						preNum = new Integer(preResult);
    						if((num%12)==(preNum%12)){
    							fcxCount++;
    							cxCount=0;
    						}else{
    							fcxCount=0;
    							cxCount++;
    						}
    					}
               		}			
								
					if(cxCount==0){
               %>      
               		<td width="30" class="b1redpx"><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>       		
               <%
					}else{
				%>
					               <td width="30" class="b1redpx"><%=cxCount %></td>				
				<%			
					}
					if(fcxCount==0){
			   %>      
		               		<td width="30" class="b1redpx"><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>       		
		       <%		
					}else{
			   %>
			               <td width="30" class="b1redpx"><%=fcxCount %></td>				
				<%			
					}
               %>
               <%					
					if(num%3==0){
						lCount=0;
               %>	
               		<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>
                <%					
					}else{
						lCount++;

               %>
                    <td width="30" class="b1redpx"><%=lCount %></td>
                <%					
					}		
               %>
                <%					
					if(num%3==1){
						yCount=0;
               %>	
               		<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>
                <%					
					}else{
						yCount++;

               %>
                    <td width="30" class="b1redpx"><%=yCount %></td>
                <%					
					}		
               %>
                <%					
					if(num%3==2){
						eCount=0;
               %>	
               		<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>
                <%					
					}else{
						eCount++;

               %>
                    <td width="30" class="b1redpx"><%=eCount %></td>
                <%					
					}		
               %>
               <%
					if(num==1||num==2||num==3||num==13||num==14||num==15||num==25||num==26||num==27){
						yqCount = 0;				
				%>
					<td width="30" class=""><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>				
					
		        <%	
					}else{
						yqCount++;
				%>			 
						<td width="30" class=""><%=yqCount %></td>				
				<%
					}			
               %>
               <%					
					if(num==4||num==5||num==6||num==16||num==17||num==18||num==28||num==29||num==30){
						eqCount = 0;				
				%>
					<td width="30" class=""><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>				
					
		        <%	
					}else{
						eqCount++;
				%>			 
						<td width="30" class=""><%=eqCount %></td>				
				<%
					}			
               %>
               <%					
					if(num==7||num==8||num==9||num==19||num==20||num==21||num==31||num==32||num==33){
						sqCount = 0;				
				%>
					<td width="30" class=""><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>				
					
		        <%	
					}else{
						sqCount++;
				%>			 
						<td width="30" class=""><%=sqCount %></td>				
				<%
					}			
               %>
               <%					
					if(num==10||num==11||num==12||num==22||num==23||num==24||num==34||num==35||num==36){
						siqCount = 0;				
				%>
					<td width="30" class=""><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>				
					
		        <%	
					}else{
						siqCount++;
				%>			 
						<td width="30" class=""><%=siqCount %></td>				
				<%
					}			
               %>
                <%
              //牛-02、14、26  兔-04、16、28  龙-05、17、29 马-07、19、31 猴-09、21、33  猪-12、24、36 
                //鼠-01、13、25 虎-03、15、27  蛇-06、18、30  羊-08、20、32  鸡-10、22、34  狗-11、23、35 
                    if(num==2||num==14||num==26||num==4||num==16||num==28||num==5||num==17||num==29||num==7||num==19||num==31||num==9||num==21||num==33||num==12||num==24||num==36){
                		txCount = 0;
        		%>
        				<td width="30" class="b1redpx"><img src="../../../../pages/images/g2.gif" width="12" height="12" /></td>				  			
                <%		
                	}else{    
                		txCount++;
                %>
        				<td width="30" class=""><%=txCount %></td>				  			                	
                <%
                	}
                	if(num==1||num==13||num==25||num==3||num==15||num==27||num==6||num==18||num==30||num==8||num==20||num==32||num==10||num==22||num==34||num==11||num==23||num==35){
                		dixCount = 0;
                %>
         				<td width="30" class="b1redpx"><img src="../../../../pages/images/g2.gif" width="12" height="12" /></td>				  			               	
                <%		
                	}else{
                		dixCount++;
                %>
         			   <td width="30" class=""><%=dixCount %></td>				  			                	               	
               	<%			
                	}
                %>
                <%
                //家禽----牛、马、羊、鸡、狗、猪；野兽----鼠、虎、兔、龙、蛇、猴。
                 //天肖--- 牛、兔、龙、马、猴、猪；地肖----鼠、虎、蛇、羊、鸡、狗
                 //牛-02、14、26 马-07、19、31 羊-08、20、32 鸡-10、22、34 狗-11、23、35 猪-12、24、36
                 //鼠-01、13、25 虎-03、15、27   兔-04、16、28  龙-05、17、29 蛇-06、18、30 猴-09、21、33
                
					if(num==2||num==14||num==26||num==7||num==19||num==31||num==8||num==20||num==32||num==10||num==22||num==34||num==11||num==23||num==35||num==12||num==24||num==36){
						jqCount=0;
					
				%>
					    <td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>				  			               	
				<%
					}else{
						jqCount++;
				%>
         			   <td width="30" class=""><%=jqCount %></td>				  			                	               						
				<%		
					}
                
	                if(num==1||num==13||num==25||num==3||num==15||num==27||num==4||num==16||num==28||num==5||num==17||num==29||num==6||num==18||num==30||num==9||num==21||num==33){
						ysCount=0;
				%>
						<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>				  			               						
				<%	
	                }else{
						ysCount++;
				%>
					   <td width="30" class=""><%=ysCount %></td>				  			                	               									
				<%		
					}
                %>
                <%
                //吉肖----兔、龙、蛇、马、羊、鸡；凶肖-----鼠、牛、虎、猴、狗、猪。 
                //兔-04、16、28 龙-05、17、29  蛇-06、18、30 马-07、19、31 羊-08、20、32 鸡-10、22、34
                //鼠-01、13、25  牛-02、14、26 虎-03、15、27 猴-09、21、33 狗-11、23、35 猪-12、24、36
					if(num==4||num==16||num==28||num==5||num==17||num==29||num==6||num==18||num==30||num==7||num==19||num==31||num==8||num==20||num==32||num==10||num==22||num==34){
						jxCount = 0;
				%>
							<td width="30" class="b1redpx"><img src="../../../../pages/images/g3.gif" width="12" height="12" /></span></td>				  			               												 
			     <%		
					}else{
						jxCount++;
				%>
							<td width="30" class=""><%=jxCount %></td>				  			                	               														
			    <%		
					}               	
                	if(num==1||num==13||num==25||num==2||num==14||num==26||num==3||num==15||num==27||num==9||num==21||num==33||num==11||num==23||num==35||num==12||num==24||num==36){
                		xxCount = 0;
        		 %>
 							<td width="30" class="b1redpx"><img src="../../../../pages/images/g3.gif" width="12" height="12" /></td>				  			               												 
       		 	
                 <%		
                	}else{
                		xxCount++;
        		%>
        					<td width="30" class=""><%=xxCount %></td>				  			                	               														      				
                <%               		
                	}
                %>
                <%
                
                //阴肖----鼠、龙、蛇、马、狗、猪；阳肖----牛、虎、兔、羊、猴、鸡
                //鼠-01、13、25 龙-05、17、29 蛇-06、18、30 马-07、19、31 狗-11、23、35 猪-12、24、36
                //牛-02、14、26 虎-03、15、27 兔-04、16、28 羊-08、20、32 猴-09、21、33 鸡-10、22、34
					 if(num==1||num==13||num==25||num==5||num==17||num==29||num==6||num==18||num==30||num==7||num==19||num==31||num==11||num==23||num==35||num==12||num==24||num==36){
						yinxCount = 0;
				%>
					 		<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>				  			               												 				
				<%		
					 }else{
						 yinxCount++;
				%>
					        <td width="30" class=""><%=yinxCount %></td>				  			                	               														      								
	            <%		 
					 }
					if(num==2||num==14||num==26||num==3||num==15||num==27||num==4||num==16||num==28||num==8||num==20||num==32||num==9||num==21||num==33||num==10||num==22||num==34){
						yangxCount = 0;
				%>
					 		<td width="30" class="b1redpx"><img src="../../../../pages/images/g1.gif" width="12" height="12" /></td>				  			               												 											
				<%		
					}else{
						yangxCount++;
				%>
							<td width="30" class=""><%=yangxCount %></td>				  			                	               														      													
				<%		
					}
                %>
              
             </tr>
			<%
						index++;
						}
			%>		
			<tr class="trgray">
               <td class="trgray graychar333">模拟选号</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">鼠</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">牛</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">虎</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">兔</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">龙</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">蛇</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">马</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">羊</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">猴</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">鸡</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">狗</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">猪</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">单肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">双肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">重肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">非重</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">0路</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">1路</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">2路</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">一区</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">二区 </td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">三区</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">四区</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">天肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">地肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">家畜</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">野兽</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">吉肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">凶肖</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">阴肖</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">阳肖</td>
             </tr>
             <tr class="zsttrpinklig">
               <td rowspan="2" class="trtitlebg"><select name="select2" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
                 <option selected="selected" value="0">期号</option>
                 <option value="1">小到大</option>
                 <option value="2">大到小</option>
               </select></td>
               <td colspan="12" class="trtitlebg"><span class="">生肖走势</span></td>
             <td colspan="2" class="trtitlebg">单双肖</td>
               <td colspan="2" class="trtitlebg">是否重肖</td>
               <td colspan="3" class="trtitlebg">012路肖</td>
               <td colspan="4" class="trtitlebg">区间</td>
               <td colspan="2" class="trtitlebg">天地肖</td>
               <td colspan="2" class="trtitlebg">家禽野兽</td>
               <td colspan="2" class="trtitlebg">吉凶肖</td>
               <td colspan="2" class="trtitlebg">阴阳肖</td>
             </tr>
             <tr class="zsttrpink">
               <td class="trtitlebg">鼠</td>
               <td class="trtitlebg">牛</td>
               <td class="trtitlebg">虎</td>
               <td class="trtitlebg">兔</td>
               <td class="trtitlebg">龙</td>
               <td class="trtitlebg">蛇</td>
               <td class="trtitlebg">马</td>
               <td class="trtitlebg">羊</td>
               <td class="trtitlebg">猴</td>
               <td class="trtitlebg">鸡</td>
               <td class="trtitlebg">狗</td>
               <td class="trtitlebg">猪</td>
               <td class=" trtitlebg">单肖</td>
               <td class=" trtitlebg">双肖</td>
               <td class=" trtitlebg">重肖</td>
               <td class=" trtitlebg">非重</td>
               <td class=" trtitlebg">0路</td>
               <td class=" trtitlebg">1路</td>
               <td class=" trtitlebg">2路</td>
               <td class=" trtitlebg">一区</td>
               <td class=" trtitlebg">二区</td>
               <td class=" trtitlebg">三区</td>
               <td width="30" class=" trtitlebg">四区</td>
               <td width="30" class=" trtitlebg">天肖</td>
               <td width="30" class=" trtitlebg">地肖</td>
               <td width="30" class=" trtitlebg">家畜</td>
               <td width="30" class=" trtitlebg">野兽</td>
               <td width="30" class=" trtitlebg">吉肖</td>
               <td width="30" class=" trtitlebg">凶肖</td>
               <td width="30" class=" trtitlebg">阴肖</td>
               <td width="30" class=" trtitlebg">阳肖</td>
             </tr>
           </tbody>
         </table>
<div class="kong5"></div>
		 <table class="tablegraybg" width="980" border="0" cellpadding="2" cellspacing="1">
    <tbody><tr class=" zs_bgligyellow">
      <td class=" bglyellow boldchar" width="85" align="center">辅助说明</td>
      <td align="left"><div class="all5px lineh20"><span class="boldchar">生肖划分：</span><br>
        鼠-01、13、25 　　牛-02、14、26 <br>
        虎-03、15、27 　　兔-04、16、28 <br>

        龙-05、17、29　　 蛇-06、18、30 <br>
        马-07、19、31　　 羊-08、20、32 <br>
        猴-09、21、33　　 鸡-10、22、34 <br>
狗-11、23、35 　　猪-12、24、36 <br>
<br>
<span class="boldchar">单双肖划分：</span><br>
单肖: 鼠、虎、龙、马、猴、狗； 双肖：牛、兔、蛇、羊、鸡、猪。<br>
<br>
<span class="boldchar">重肖：</span><br>

是指开出于上期一样的生肖。<br>
<br>
<span class="boldchar">012路生肖划分：</span><br>

0路生肖：虎、蛇、猴、猪； 1路生肖：鼠、兔、马、鸡； 2路生肖：牛、龙、羊、狗。<br>
<br>
<span class="boldchar">区间划分：</span><br>
一区生肖：鼠、牛、虎；二区生肖：兔、龙、蛇；<br>
三区生肖：马、羊、猴；四区生肖：鸡、狗、猪。<br />
<span class="boldchar">天地肖划分：</span><br>
天肖--- 牛、兔、龙、马、猴、猪；地肖----鼠、虎、蛇、羊、鸡、狗。<br>
<br>

<span class="boldchar">家禽野兽划分：</span><br>
家禽----牛、马、羊、鸡、狗、猪；野兽----鼠、虎、兔、龙、蛇、猴。<br>
<br>
<span class="boldchar">阴阳肖划分：</span><br>
阴肖----鼠、龙、蛇、马、狗、猪；阳肖----牛、虎、兔、羊、猴、鸡。 <br>
<br>
<span class="boldchar">吉凶肖划分：</span><br>
吉肖----兔、龙、蛇、马、羊、鸡；凶肖-----鼠、牛、虎、猴、狗、猪。 <br>
<br></div></td>
    </tr>
  </tbody></table>		 
       </div>


		<jsp:include page="../bottom_common.jsp" />
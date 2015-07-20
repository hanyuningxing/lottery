<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1五行走势图 - 球客网</title>

<script type="text/javascript" src="../../../../js/analyse/welfare36to7/common.js""></script>
<script type="text/javascript" src="../../../../js/mootools-1.2.4-core-yc.js""></script>
<script type="text/javascript" src="../../../../js/analyse/excanvas.js""></script>
<script type="text/javascript" src="../../../../js/analyse/drawLine.js"></script>
<script type="text/javascript">
		window.addEvent('domready', function() {
			drawLine();
		});
		
		function drawLine() {
			$("div_line").innerHTML = "";		
			[ 'wuxing' ].each(function(hashName, index) {
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
               <td rowspan="2" width="53"><select name="select" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
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
               <td width="36" rowspan="2" class="b1redpx">奖号</td>
               <td height="25" colspan="6" class="b1redpx">金</td>
               <td height="25" colspan="6" class="b1redpx">木</td>
               <td height="25" colspan="8" class="b1redpx">水</td>
               <td height="25" colspan="8" class="b1redpx">火</td>
               <td height="25" colspan="8" class="b1redpx">土</td>
               <td colspan="5" class="b1redpx">五行走势</td>
             </tr>
  
             <tr class="zsttrpinklig">
               <td width="20" height="20" class="">09</td>
               <td width="21" height="20" class="">10</td>
               <td width="21" height="20" class="">17</td>
               <td width="21" height="20" class="">18</td>
               <td width="21" height="20" class="">25</td>
               <td width="19" height="20" class="">26</td>
               <td width="21" height="20" class="">07</td>
               <td width="21" height="20" class="">08</td>
               <td width="21" height="20" class="">21</td>
               <td width="21" height="20" class="">22</td>
               <td width="21" height="20" class="">29</td>
               <td width="21" height="20" class="">30</td>
               <td width="22" height="20" class="">05</td>
               <td width="21" height="20" class="">06</td>
               <td width="21" height="20" class="">13</td>
               <td width="21" height="20" class="">14</td>
               <td width="21" height="20" class="">27</td>
               <td width="21" height="20" class="">28</td>
               <td width="22" height="20" class="">35</td>
               <td width="21" height="20" class="">36</td>
               <td width="21" height="20" class="">01</td>
               <td width="21" height="20" class="">02</td>
               <td width="19" height="20" class="">15</td>
               <td width="21" height="20" class="">16</td>
               <td width="22" height="20">23</td>
               <td width="21" height="20">24</td>
               <td width="21" height="20">31</td>
               <td width="21" height="20">32</td>
               <td width="21" height="20">03</td>
               <td width="21" height="20">04</td>
               <td width="21" height="20">11</td>
               <td width="21" height="20">12</td>
               <td width="21" height="20">19</td>
               <td width="21" height="20">20</td>
               <td width="21" height="20">33</td>
               <td width="25" height="20">34</td>
               <td width="22">金</td>
               <td width="16">木</td>
               <td width="16">水</td>
               <td width="15">火</td>
               <td width="20">土</td>
             </tr>
             
              <%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<Integer, Integer> missMap = new HashMap<Integer, Integer>();
						Map<Integer, Integer> tailMissMap = new HashMap<Integer, Integer>();
				       
						Integer[] NUMS = new Integer[]{9,10,17,18,25,26,7,8,21,22,29,30,5,6,13,14,27,28,35,36,1,2,15,16,23,24,31,32,3,4,11,12,19,20,33,34};

						for(Integer ball:NUMS){
							missMap.put(ball,0);
						}
						int jCount = 0;
						int mCount = 0;
						int sCount = 0;
						int hCount = 0;
						int tCount = 0;
						for (Welfare36to7MissDataInfo missData : datas) {
							String resultDatas = missData.getResult();
							String periodNumber = missData.getPeriodNumber();
							String[] arr = resultDatas.split(",");
							String result = arr[6];
							Integer num = new Integer(result);
													
							for(Integer ball:NUMS){
								if(ball!=num){
									missMap.put(ball,missMap.get(ball)+1);
								}
							}
							missMap.put(num,0);
							
							Integer[] jBalls = new Integer[]{9,10,17,18,25,26};
							Integer[] mBalls = new Integer[]{7,8,21,22,29,30};
							Integer[] sBalls = new Integer[]{5,6,13,14,27,28,35,36};
							Integer[] hBalls = new Integer[]{1,2,15,16,23,24,31,32};
							Integer[] tBalls = new Integer[]{3,4,11,12,29,20,33,34};
							
							boolean flag = false;
							jCount++;
							mCount++;
							sCount++;
							hCount++;
							tCount++;
							for(Integer jBall:jBalls){
								if(num.intValue()==jBall.intValue()){
									jCount = 0;
									flag=true;
									break;
								}
							}
							if(!flag){
								for(Integer mBall:mBalls){
									if(num.intValue()==mBall.intValue()){
										mCount = 0;
										flag=true;
										break;
									}
								}
							}
							if(!flag){
								for(Integer sBall:sBalls){
									if(num.intValue()==sBall.intValue()){
										sCount = 0;
										flag=true;
										break;
									}
								}
							}
							if(!flag){
								for(Integer hBall:hBalls){
									if(num.intValue()==hBall.intValue()){
										hCount = 0;
										flag=true;
										break;
									}
								}
							}
							if(!flag){
								for(Integer tBall:tBalls){
									if(num.intValue()==tBall.intValue()){
										tCount = 0;
										flag=true;
										break;
									}
								}
							}
					%>
             
            <tr class="trw">
             	<td class="graychar333"><%=periodNumber %></td>
               <td class="graychar333"><%=result %></td>
               <%
					for(Integer ball:NUMS){
						if(0==missMap.get(ball)){
               %>
   					 	<td width="39" class=""><span class="n-ssq" _cancas="cancas" _group="wuxing"><%=ball %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=missMap.get(ball) %></td>             				               		
               <%
						}
					}
               %>
               <%		
						if(0==jCount){
               %>
   					 	<td width="39" class=""><span class="ballredone1" _cancas="cancas" _group="shuangxiao"><%=num %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=jCount %></td>             				               		
               <%
						}
               %>
               <%		
						if(0==mCount){
               %>
   					 	<td width="39" class=""><span class="ballredone1" _cancas="cancas" _group="shuangxiao"><%=num %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=mCount %></td>             				               		
               <%
						}
               %>
               <%		
						if(0==sCount){
               %>
   					 	<td width="39" class=""><span class="ballredone1" _cancas="cancas" _group="shuangxiao"><%=num %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=sCount %></td>             				               		
               <%
						}
               %>
               <%		
						if(0==hCount){
               %>
   					 	<td width="39" class=""><span class="ballredone1" _cancas="cancas" _group="shuangxiao"><%=num %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=hCount %></td>             				               		
               <%
						}
               %>
               <%		
						if(0==tCount){
               %>
   					 	<td width="39" class=""><span class="ballredone1" _cancas="cancas" _group="shuangxiao"><%=num %></span></td>          			  					               			
               <%
						}else{
               %>
               			<td width="21" class=""><%=tCount %></td>             				               		
               <%
						}
               %>            
             </tr>
             
             <%
						}
             %>
             
             <tr class="trgray">
               <td class="trgray graychar333">模拟选号</td>
               <td width="36" style="cursor: pointer;" onclick="dxjo_click(this);">&nbsp;</td>
               <td width="20"><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td>
                <td width="19"><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td>
                <td width="19"><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">24</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">25</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">26</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">27</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">28</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">29</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">30</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">31</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">32</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">33</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">34</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">35</a></td>
                <td width="25"><a href="#" class="gball" onclick="red_click(this);return false;">36</a></td>
                <td width="22" width="15" style="cursor: pointer;" onclick="dxjo_click(this);">金</td>
                <td width="16" width="15" style="cursor: pointer;" onclick="dxjo_click(this);">木</td>
                <td width="16" width="15" style="cursor: pointer;" onclick="dxjo_click(this);">水</td>
                <td width="15" width="15" style="cursor: pointer;" onclick="dxjo_click(this);">火</td>
                <td width="20" width="15" style="cursor: pointer;" onclick="dxjo_click(this);">土</td>
             </tr>
             <tr class="zsttrpinklig">
               <td rowspan="2" class="trtitlebg"><select name="select2" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
                  <%
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
               <td colspan="7" class="trtitlebg">金</td>
               <td colspan="6" class="trtitlebg">木</td>
               <td colspan="8" class="trtitlebg">水</td>
               <td colspan="8" class="trtitlebg">火</td>
               <td colspan="8" class="trtitlebg">土</td>
               <td colspan="5" class="trtitlebg">五行走势</td>
             </tr>
             
             <tr class="zsttrpink">
               <td class="trtitlebg">&nbsp;</td>
               <td class="trtitlebg">01</td>
               <td class="trtitlebg">02</td>
               <td class="trtitlebg">03</td>
               <td class="trtitlebg">04</td>
               <td class="trtitlebg">05</td>
               <td class="trtitlebg">06</td>
               <td class="trtitlebg">07</td>
               <td class="trtitlebg">08</td>
               <td class="trtitlebg">09</td>
               <td class="trtitlebg">10</td>
               <td class="trtitlebg">11</td>
               <td class="trtitlebg">12</td>
               <td class=" trtitlebg">13</td>
               <td class=" trtitlebg">14</td>
               <td class=" trtitlebg">15</td>
               <td class=" trtitlebg">16</td>
               <td class=" trtitlebg">17</td>
               <td class=" trtitlebg">18</td>
               <td class=" trtitlebg">19</td>
               <td class=" trtitlebg">20</td>
               <td class=" trtitlebg">21</td>
               <td class=" trtitlebg">22</td>
               <td class=" trtitlebg">23</td>
               <td width="21" class=" trtitlebg">24</td>
               <td width="22" class=" trtitlebg">25</td>
               <td width="21" class=" trtitlebg">26</td>
               <td width="21" class=" trtitlebg">27</td>
               <td width="21" class=" trtitlebg">28</td>
               <td width="21" class=" trtitlebg">29</td>
               <td width="21" class=" trtitlebg">30</td>
               <td width="21" class=" trtitlebg">31</td>
               <td width="21" class=" trtitlebg">32</td>
               <td width="21" class=" trtitlebg">33</td>
               <td width="21" class=" trtitlebg">34</td>
               <td width="21" class=" trtitlebg">35</td>
               <td width="25" class=" trtitlebg">36</td>
               <td width="22" class=" trtitlebg">金</td>
               <td width="16" class=" trtitlebg">木</td>
               <td width="16" class=" trtitlebg">水</td>
               <td width="15" class=" trtitlebg">火</td>
               <td width="20" class=" trtitlebg">土</td>
             </tr>
           </tbody>
         </table>
		   
    
  <div class="kong5"></div>
  <table class="tablegraybg" width="980" border="0" cellpadding="2" cellspacing="1">
    <tbody><tr class=" zs_bgligyellow">
      <td class=" bglyellow boldchar" width="85" align="center">辅助说明</td>
      <td align="left"><div class="all5px lineh20">金;09 10 17 18 25 26 <br>
        木;07 08 21 22 29 30 <br>
        水;05 06 13 14 27 28 35 36 <br>

        火;01 02 15 16 23 24 31 32 <br>
        土;03 04 11 12 19 20 33 34<br>
        </div></td>
    </tr>
  </tbody></table>
	</div>
			<jsp:include page="../bottom_common.jsp" />
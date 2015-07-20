<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1数字走势图 - 球客网</title>

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
			[ 'xiaowei' ,'zhongwei', 'dawei' ].each(function(hashName, index) {
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
               <td rowspan="3" width="57"><select name="select" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
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
               <td width="44" rowspan="3" class="b1redpx">奖号</td>
               <td colspan="18" class="b1redpx">小数</td>
               <td colspan="18" class="b1redpx">大数</td>
             </tr>
             <tr class="zsttrpinklig">
               <td colspan="12" class="">小数</td>
               <td colspan="12" class="">中数</td>
               <td colspan="12">大数</td>
             </tr>                   
             
             <tr class="zsttrpinklig">
               <td width="25" class="">01</td>
               <td width="25" class="">02</td>
               <td width="25" class="">03</td>
               <td width="25">04</td>
               <td width="25">05</td>
               <td width="25">06</td>
               <td width="25">07</td>
               <td width="25">08</td>
               <td width="25">09</td>
               <td width="25">10</td>
               <td width="25">11</td>
               <td width="25">12</td>
               <td width="25" class="b1redpx">13</td>
               <td width="25">14</td>
               <td width="25">15</td>
               <td width="25">16</td>
               <td width="25">17</td>
               <td width="25">18</td>
               <td width="25" class="b1redpx1">19</td>
               <td width="25">20</td>
               <td width="25">21</td>
               <td width="25">22</td>
               <td width="25">23</td>
               <td width="25" class="">24</td>
               <td width="26">25</td>
               <td width="25">26</td>
               <td width="26">27</td>
               <td width="25">28</td>
               <td width="26">29</td>
               <td width="25">30</td>
               <td width="26">31</td>
               <td width="25">32</td>
               <td width="25">33</td>
               <td width="25">34</td>
               <td width="35">35</td>
               <td width="25">36</td>
             </tr>
             <%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<Integer, Integer> missMap = new HashMap<Integer, Integer>();					

						Integer[] NUMS = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
						for(Integer ball:NUMS){
							missMap.put(ball,0);
						}
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

					%>
			<tr class="trw">
               <td class="graychar333"><%=periodNumber %></td>
               <td width="44" class="redredbchar"><%=result %></td>
               <%
               				for(Integer xBall:new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12}){
               					if(0==missMap.get(xBall)){              				
               %>
               		<td width="25" class="" style="cursor: pointer;"><span class="n-ssq" _cancas="cancas" _group="xiaowei"><%=xBall %></span></td>
               	<%
								}else{
               %>
                	<td width="25" class=""><%=missMap.get(xBall) %></td>             
               <%
								}
							}
                            for(Integer zBall:new Integer[]{13,14,15,16,17,18,19,20,21,22,23,24}){
               					if(0==missMap.get(zBall)){              				
               %>
               		<td width="25" class=""><span class="ballredone2" _cancas="cancas" _group="zhongwei"><%=zBall %></span></td>
               	<%
								}else{
               %>
                	<td width="25" class=""><%=missMap.get(zBall) %></td>             
               <%
								}
							}
                            for(Integer dBall:new Integer[]{25,26,27,28,29,30,31,32,33,34,35,36}){
               					if(0==missMap.get(dBall)){              				
               %>
               		<td width="25" class=""><span class="ballredone3" _cancas="cancas" _group="dawei"><%=dBall %></span></td>
               	<%
								}else{
               %>
                	<td width="25" class=""><%=missMap.get(dBall) %></td>             
               <%
								}
							}
               %>            
             </tr>
			<%
						}
			%>		
			<tr class="trgray">
               <td class="trgray graychar333">模拟选号</td>
               <td width="44" style="cursor: pointer;" onclick="dxjo_click(this);">&nbsp;</td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td>
               <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">24</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">25</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">26</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">27</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">28</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">29</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">30</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">31</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">32</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">33</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">34</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">35</a></td>
                <td width="22"><a href="#" class="gball" onclick="red_click(this);return false;">36</a></td>
             </tr>
             <tr class="zsttrpinklig">
               <td rowspan="3" class="trtitlebg"><select name="select2" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
                 <option selected="selected" value="0">期号</option>
                 <option value="1">小到大</option>
                 <option value="2">大到小</option>
               </select></td>
               <td colspan="19" class="trtitlebg"><span class="">小数</span></td>
             <td colspan="18" class="b1redpx1 trtitlebg">大数</td>
             </tr>
             <tr class="zsttrpinklig">
               <td colspan="13" class="trtitlebg">小数</td>
               <td colspan="12" class="trtitlebg">中数</td>
               <td colspan="12" class="trtitlebg">大数</td>
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
               <td class="b1redpx1 trtitlebg">19</td>
               <td class=" trtitlebg">20</td>
               <td class=" trtitlebg">21</td>
               <td class=" trtitlebg">22</td>
               <td class=" trtitlebg">23</td>
               <td width="25" class=" trtitlebg">24</td>
               <td width="26" class=" trtitlebg">25</td>
               <td width="25" class=" trtitlebg">26</td>
               <td width="26" class=" trtitlebg">27</td>
               <td width="25" class=" trtitlebg">28</td>
               <td width="26" class=" trtitlebg">29</td>
               <td width="25" class=" trtitlebg">30</td>
               <td width="26" class=" trtitlebg">31</td>
               <td width="25" class=" trtitlebg">32</td>
               <td width="25" class=" trtitlebg">33</td>
               <td width="25" class=" trtitlebg">34</td>
               <td width="35" class=" trtitlebg">35</td>
               <td width="25" class=" trtitlebg">36</td>
             </tr>
           </tbody>
         </table>
		 
       </div>


		<jsp:include page="../bottom_common.jsp" />
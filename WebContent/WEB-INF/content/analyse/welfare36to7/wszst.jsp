<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1尾数走势图 - 球客网</title>

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
			[ 'weishu' ].each(function(hashName, index) {
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
               <td rowspan="3" width="56"><select name="select" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
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
               <td colspan="19" class="b1redpx">小尾区</td>
               <td colspan="17" class="b1redpx">大尾数</td>
               <td colspan="10" class="b1redpx">尾数走势图</td>
             </tr>
             <tr class="zsttrpinklig">
               <td colspan="3" class="">0尾</td>
               <td colspan="4">1尾</td>
               <td colspan="4">2尾</td>
               <td colspan="4">3尾</td>
               <td colspan="4">4尾</td>
               <td colspan="4" class="b1redpx">5尾</td>
               <td colspan="4">6尾</td>
               <td colspan="3">7尾</td>
               <td colspan="3">8尾</td>
               <td colspan="3">9尾</td>
               <td width="15" rowspan="2">0尾</td>
               <td width="15" rowspan="2">1尾</td>
               <td width="15" rowspan="2">2尾</td>
               <td width="15" rowspan="2">3尾</td>
               <td width="15" rowspan="2">4尾</td>
               <td width="15" rowspan="2">5尾</td>
               <td width="15" rowspan="2">6尾</td>
               <td width="15" rowspan="2">7尾</td>
               <td width="15" rowspan="2">8尾</td>
               <td width="15" rowspan="2">9尾</td>
             </tr>
             
             <tr class="zsttrpinklig">
               <td width="21" class="">10</td>
               <td width="21" class="">20</td>
               <td width="21" class="">30</td>
               <td width="21">01</td>
               <td width="21">11</td>
               <td width="21">21</td>
               <td width="21">31</td>
               <td width="21">02</td>
               <td width="21">12</td>
               <td width="21">22</td>
               <td width="21">32</td>
               <td width="21">03</td>
               <td width="21">13</td>
               <td width="21">23</td>
               <td width="21">33</td>
               <td width="21">04</td>
               <td width="21">14</td>
               <td width="21">24</td>
               <td width="21">34</td>
               <td width="21" class="b1redpx">05</td>
               <td width="21">15</td>
               <td width="21">25</td>
               <td width="21">35</td>
               <td width="21">06</td>
               <td width="21">16</td>
               <td width="21">26</td>
               <td width="21">36</td>
               <td width="21">07</td>
               <td width="21">17</td>
               <td width="21" class="">27</td>
               <td width="21">08</td>
               <td width="21">18</td>
               <td width="21">28</td>
               <td width="21">09</td>
               <td width="21">19</td>
               <td width="21">29</td>
             </tr>
             
             <%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<Integer, Integer> missMap = new HashMap<Integer, Integer>();
						Map<Integer, Integer> tailMissMap = new HashMap<Integer, Integer>();

						Integer[] NUMS = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
						Integer[] SORTNUMS = new Integer[]{10,20,30,1,11,21,31,2,12,22,32,3,13,23,33,4,14,24,34,5,15,25,35,6,16,26,36,7,17,27,8,18,28,9,19,29};
						Integer[] TAILNUMS = new Integer[]{0,1,2,3,4,5,6,7,8,9};
						for(Integer ball:NUMS){
							missMap.put(ball,0);
						}
						for(Integer ball:TAILNUMS){
							tailMissMap.put(ball,0);
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
             <tr class="trgray">
               <td class="graychar333"><%=periodNumber %></td>
               <%
               		for(Integer sortNum:SORTNUMS){
               			if(0==missMap.get(sortNum)){
   				%>		
   					 	<td width="39" class=""><span class="ballredone3" _cancas="cancas" _group="daxiaowei"><%=num %></span></td>          			  					
   				<%		
               			}else{
               	%>	
               			<td width="21" class=""><%=missMap.get(sortNum) %></td>             				
               	<%		
               			}
               		}
               %>
               <%
               		for(Integer tailNum:TAILNUMS){
               			if(tailNum==(num%10)){
               				tailMissMap.put(tailNum,0);
               %>
                        <td width="15" class=""><span class="n-ssq" _cancas="cancas" _group="weishu"><%=num %></span></td>               
               <% 			
               			}else{
               				tailMissMap.put(tailNum,tailMissMap.get(tailNum)+1);
               %>	
               			<td width="15" class=""><%=tailMissMap.get(tailNum) %></td>             			
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
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">10</a></td>
                 <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">20</a></td>
                 <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">30</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">01</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">11</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">21</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">31</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">02</a></td>
              <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">12</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">22</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">32</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">03</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">13</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">23</a></td>
             <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">33</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">04</a></td>
             <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">14</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">24</a></td>
             <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">34</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">05</a></td>
              <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">15</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">25</a></td>
            <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">35</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">06</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">16</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">26</a></td>
              <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">36</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">07</a></td>
             <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">17</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">27</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">08</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">18</a></td>
              <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">28</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">09</a></td>
               <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">19</a></td>
                <td width="21"><a href="#" class="gball" onclick="red_click(this);return false;">29</a></td>
                <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">0尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">1尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">2尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">3尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">4尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">5尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">6尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">7尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">8尾</td>
               <td width="15" style="cursor: pointer;" onclick="dxjo_click(this);">9尾</td>
             </tr>
             <tr class="zsttrpinklig">
               <td rowspan="3" class="trtitlebg"><select name="select2" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
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
               <td colspan="19" class="trtitlebg"><span class="">小尾区</span></td>
               <td colspan="17" class="trtitlebg">大尾区</td>
               <td colspan="10" rowspan="2" class="trtitlebg">尾数走势</td>
             </tr>
             <tr class="zsttrpink">
               <td colspan="3" class="trtitlebg">0尾</td>
               <td colspan="4" class="trtitlebg">1尾</td>
               <td colspan="4" class="trtitlebg">2尾</td>
               <td colspan="4" class="trtitlebg">3尾</td>
               <td colspan="4" class="trtitlebg">4尾</td>
               <td colspan="4" class=" trtitlebg">5尾</td>
               <td colspan="4" class=" trtitlebg">6尾</td>
               <td colspan="3" class=" trtitlebg">7尾</td>
              <td colspan="3" class=" trtitlebg">8尾</td>
               <td colspan="3" class=" trtitlebg">9尾</td>
             </tr>
             <tr class="zsttrpink">
               <td class="trtitlebg">10</td>
               <td class="trtitlebg">20</td>
               <td class="trtitlebg">30</td>
               <td class="trtitlebg">01</td>
               <td class="trtitlebg">11</td>
               <td class="trtitlebg">21</td>
               <td class="trtitlebg">31</td>
               <td class="trtitlebg">02</td>
               <td class="trtitlebg">12</td>
               <td class="trtitlebg">22</td>
               <td class="trtitlebg">32</td>
               <td class="trtitlebg">03</td>
               <td class="trtitlebg">13</td>
               <td class="trtitlebg">23</td>
               <td class="trtitlebg">3</td>
               <td class="trtitlebg">04</td>
               <td class="trtitlebg">14</td>
               <td class="trtitlebg">24</td>
               <td class="trtitlebg">34</td>
               <td class=" trtitlebg">05</td>
               <td class=" trtitlebg">15</td>
               <td class=" trtitlebg">25</td>
               <td class=" trtitlebg">35</td>
               <td class=" trtitlebg">06</td>
               <td class=" trtitlebg">16</td>
               <td class=" trtitlebg">26</td>
               <td class=" trtitlebg">36</td>
               <td class=" trtitlebg">07</td>
			    <td class=" trtitlebg">17</td>
               <td class=" trtitlebg">27</td>
               <td class=" trtitlebg">08</td>
               <td class=" trtitlebg">18</td>
               <td class=" trtitlebg">28</td>
               <td class=" trtitlebg">09</td>
               <td class=" trtitlebg">19</td>
               <td class=" trtitlebg">29</td>
			      <td class=" trtitlebg">0尾</td>
               <td class=" trtitlebg">1尾</td>
              <td class=" trtitlebg">2尾</td>
               <td class=" trtitlebg">3尾</td>
               <td class=" trtitlebg">4尾</td>
               <td class=" trtitlebg">5尾</td>
               <td class=" trtitlebg">6尾</td>
               <td class=" trtitlebg">7尾</td>
               <td class=" trtitlebg">8尾</td>
               <td class=" trtitlebg">9尾</td>
             </tr>
           </tbody>
         </table>
		   
  <div class="kong5"></div>
  <table class="tablegraybg" width="980" border="0" cellpadding="2" cellspacing="1">
    <tbody><tr class=" zs_bgligyellow">
      <td class=" bglyellow boldchar" width="85" align="center">参数说明</td>
      <td align="left">
      <div class="all5px lineh20">尾数分小尾区、大尾区。小尾为0-4尾，大尾为5-9尾。<br>
        奇尾为1、3、5、7、9尾，偶尾为0、2、4、6、8尾。</div>
      </td>

    </tr>
  </tbody></table>
	</div>
			<jsp:include page="../bottom_common.jsp" />
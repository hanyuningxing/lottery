<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1季节方位走势图 - 球客网</title>

<script type="text/javascript" src="../../../../js/analyse/welfare36to7/common.js""></script>
<script type="text/javascript" src="../../../../js/mootools-1.2.4-core-yc.js""></script>
<script type="text/javascript" src="../../../../js/analyse/excanvas.js""></script>
<script type="text/javascript" src="../../../../js/analyse/drawLine.js"></script>
<script type="text/javascript">
		window.addEvent('domready', function() {
			//$("MISS_EL").setStyle("display","none");
			//document.getElementById('MISS_EL').style.display = "none";
		});
	</script>

</head>
<body>
	<jsp:include page="top.jsp" />	
	<div id="ga">
		 <table class="zstablegraybg" width="980" border="0" cellpadding="0" cellspacing="1">
           <tbody>
             <tr class="trtitlebg">
               <td rowspan="2" width="102"><select name="select" size="1" style="width: 50px;" onchange="sortDisplay(this.value);">
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
               <td colspan="8" rowspan="2" class="b1redpx">奖号</td>
               <td colspan="4" class="b1redpx">季节走势</td>
               <td colspan="4" class="b1redpx">方位</td>
               <td colspan="8" class="b1redpx">组合出号</td>
             </tr>
             <tr class="zsttrpinklig">
               <td width="39">春</td>
               <td width="37">夏</td>
               <td width="44">秋</td>
               <td width="46">冬</td>
               <td width="53" class="b1redpx">东</td>
               <td width="48">南</td>
               <td width="47">西</td>
               <td width="54">北</td>
               <td width="55"  class="b1redpx">春东</td>
               <td width="51">春南</td>
               <td width="51">夏东</td>
               <td width="49">夏南</td>
               <td width="49">秋西</td>
               <td width="50">秋北</td>
               <td width="45" class="">冬西</td>
               <td width="57">冬北</td>
             </tr>
				<%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<Integer, Integer> missMap = new HashMap<Integer, Integer>();
						Map<String, Integer> jjMissMap = new HashMap<String, Integer>();

						Integer[] NUMS = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
						for(Integer ball:NUMS){
							missMap.put(ball,0);
						}
						jjMissMap.put("春",0);
						jjMissMap.put("夏",0);
						jjMissMap.put("秋",0);
						jjMissMap.put("冬",0);
						jjMissMap.put("东",0);
						jjMissMap.put("南",0);
						jjMissMap.put("西",0);
						jjMissMap.put("北",0);
						jjMissMap.put("春东",0);
						jjMissMap.put("春南",0);
						jjMissMap.put("夏东",0);
						jjMissMap.put("夏南",0);
						jjMissMap.put("秋西",0);
						jjMissMap.put("秋北",0);
						jjMissMap.put("冬西",0);
						jjMissMap.put("冬北",0);
						
						String jjzs = "";
											
						for (Welfare36to7MissDataInfo missData : datas) {
							String resultDatas = missData.getResult();
							String periodNumber = missData.getPeriodNumber();
							String[] arr = resultDatas.split(",");
							String result = arr[6];
							Integer num = new Integer(result);
							
							//春-01至09　　夏-10至18 秋-19至27　冬-28至36 
							if(num>=1&&num<=9){
								jjzs = "春";		
								jjMissMap.put("春",0);
								jjMissMap.put("夏",jjMissMap.get("夏")+1);
								jjMissMap.put("秋",jjMissMap.get("秋")+1);
								jjMissMap.put("冬",jjMissMap.get("冬")+1);

							}else if(num>=10&&num<=18){
								jjzs = "夏";			
								jjMissMap.put("春",jjMissMap.get("春")+1);
								jjMissMap.put("夏",0);
								jjMissMap.put("秋",jjMissMap.get("秋")+1);
								jjMissMap.put("冬",jjMissMap.get("冬")+1);
							}else if(num>=19&&num<=27){
								jjzs = "秋";			
								jjMissMap.put("春",jjMissMap.get("春")+1);
								jjMissMap.put("夏",jjMissMap.get("夏")+1);
								jjMissMap.put("秋",0);
								jjMissMap.put("冬",jjMissMap.get("冬")+1);
							}else if(num>=28&&num<=36){
								jjzs = "冬";		
								jjMissMap.put("春",jjMissMap.get("春")+1);
								jjMissMap.put("夏",jjMissMap.get("夏")+1);
								jjMissMap.put("秋",jjMissMap.get("秋")+1);
								jjMissMap.put("冬",0);
							}
							
							//东-01、03、05、07、09、11、13、15、17
							//南-02、04、06、08、10、12、14、16、18
							//西-19、21、23、25、27、29、31、33、35
							//北-20、22、24、26、28、30、32、34、36
							if(num>=1&&num<=17&&(num%2==1)){
								jjMissMap.put("东",0);
								jjMissMap.put("南",jjMissMap.get("南")+1);
								jjMissMap.put("西",jjMissMap.get("西")+1);
								jjMissMap.put("北",jjMissMap.get("北")+1);
							}else if(num>=2&&num<=18&&(num%2==0)){
								jjMissMap.put("南",0);
								jjMissMap.put("东",jjMissMap.get("东")+1);
								jjMissMap.put("西",jjMissMap.get("西")+1);
								jjMissMap.put("北",jjMissMap.get("北")+1);
							}else if(num>=19&&num<=35&&(num%2==1)){
								jjMissMap.put("西",0);
								jjMissMap.put("东",jjMissMap.get("东")+1);
								jjMissMap.put("南",jjMissMap.get("南")+1);
								jjMissMap.put("北",jjMissMap.get("北")+1);
							}else if(num>=20&&num<=36&&(num%2==0)){
								jjMissMap.put("北",0);
								jjMissMap.put("东",jjMissMap.get("东")+1);
								jjMissMap.put("南",jjMissMap.get("南")+1);
								jjMissMap.put("西",jjMissMap.get("西")+1);
							}
							
							//春东 01、03、05、07、09、
							//春南 02、04、06、08、
							//夏东 11、13、15、17
							//夏南 10、12、14、16、18
							//秋西 19、21、23、25、27、
							//秋北 20、22、24、26、
							//冬西 29、31、33、35
							//冬北 28、30、32、34、36
							String zhch = "";
							if(num>=1&&num<=9&&(num%2==1)){
								zhch="春东";
								jjMissMap.put("春东",0);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=2&&num<=8&&(num%2==0)){
								zhch="春南";
								jjMissMap.put("春南",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=11&&num<=17&&(num%2==1)){
								zhch="夏东";
								jjMissMap.put("夏东",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=10&&num<=18&&(num%2==0)){
								zhch="夏南";
								jjMissMap.put("夏南",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=19&&num<=27&&(num%2==1)){
								zhch="秋西";
								jjMissMap.put("秋西",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=20&&num<=26&&(num%2==0)){
								zhch="秋北";
								jjMissMap.put("秋北",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=29&&num<=35&&(num%2==1)){
								zhch="冬西";
								jjMissMap.put("冬西",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬北",jjMissMap.get("冬北")+1);
							}else if(num>=28&&num<=36&&(num%2==0)){
								zhch="冬北";
								jjMissMap.put("冬北",0);
								jjMissMap.put("春东",jjMissMap.get("春东")+1);
								jjMissMap.put("春南",jjMissMap.get("春南")+1);
								jjMissMap.put("夏东",jjMissMap.get("夏东")+1);
								jjMissMap.put("夏南",jjMissMap.get("夏南")+1);
								jjMissMap.put("秋西",jjMissMap.get("秋西")+1);
								jjMissMap.put("秋北",jjMissMap.get("秋北")+1);
								jjMissMap.put("冬西",jjMissMap.get("冬西")+1);
							}
						
					%>
				<tr class="trgray">
               <td class="graychar333"><%=periodNumber %></td>
               <td colspan="8" class="redredbchar"><%=num %></td>
               <%
               			if(0==jjMissMap.get("春")){
               %>
               			 <td width="39" class=""><span class="ballredone" _cancas="cancas" _group="shuangxiao">春</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("春") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("夏")){
               %>
               			 <td width="39" class=""><span class="n-ssq" _cancas="cancas" _group="shuangxiao">夏</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("夏") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("秋")){
               %>
               			 <td width="39" class=""><span class="n-ssq" _cancas="cancas" _group="shuangxiao">秋</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("秋") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("冬")){
               %>
               			 <td width="39" class=""><span class="n-ssq" _cancas="cancas" _group="shuangxiao">冬</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("冬") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("东")){
               %>
               			 <td width="39" class=""><span class="ballredone3" _cancas="cancas" _group="shuangxiao">东</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("东") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("南")){
               %>
               			 <td width="39" class=""><span class="ballredone3" _cancas="cancas" _group="shuangxiao">南</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("南") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("西")){
               %>
               			 <td width="39" class=""><span class="ballredone3" _cancas="cancas" _group="shuangxiao">西</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("西") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("北")){
               %>
               			 <td width="39" class=""><span class="ballredone3" _cancas="cancas" _group="shuangxiao">北</span></td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("北") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("春东")){
               %>
               			 <td width="39" class="trchoseo">春东</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("春东") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("春南")){
               %>
               			 <td width="39" class="trchoseo">春南</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("春南") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("夏东")){
               %>
               			 <td width="39" class="trchoseo">夏东</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("夏东") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("夏南")){
               %>
               			 <td width="39" class="trchoseo">夏南</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("夏南") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("秋西")){
               %>
               			 <td width="39" class="trchoseo">秋西</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("秋西") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("秋北")){
               %>
               			 <td width="39" class="trchoseo">秋北</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("秋北") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("冬西")){
               %>
               			 <td width="39" class="trchoseo">冬西</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("冬西") %></td>         			
               <%
               			}
               %>
               <%
               			if(0==jjMissMap.get("冬北")){
               %>
               			 <td width="39" class="trchoseo">冬北</td>          			
               <%				
               			}else{
               %>
               			<td width="37" class=""><%=jjMissMap.get("冬北") %></td>         			
               <%
               			}
               %>
               
             </tr>
				<%
						}
				%>					
					
					
				<tr class="trgray">
               <td class="trgray graychar333">模拟选号</td>
               <td colspan="8" style="cursor: pointer;" onclick="dxjo_click(this);">&nbsp;</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">春</td>
                <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">夏</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">秋</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">东</td>
              <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">东</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">南</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">西</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">北</td>
              <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">春东</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">春南</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">夏东</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">夏南</td>
             <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">秋西</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">秋北</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">冬西</td>
               <td width="30" style="cursor: pointer;" onclick="dxjo_click(this);">冬北</td>
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
               <td colspan="8" rowspan="2" class="trtitlebg"><span class="">奖号</span></td>
               <td colspan="4" class="trtitlebg">季节走势</td>
               <td colspan="4" class="trtitlebg">方位</td>
               <td colspan="8" class="trtitlebg">组合走势</td>
             </tr>
             <tr class="zsttrpink">
               <td class="trtitlebg">春</td>
               <td class="trtitlebg">夏</td>
               <td class="trtitlebg">秋</td>
               <td class="trtitlebg">冬</td>
               <td class=" trtitlebg">东</td>
               <td class=" trtitlebg">南</td>
               <td class=" trtitlebg">西</td>
               <td class=" trtitlebg">北</td>
               <td class=" trtitlebg">春东</td>
               <td class=" trtitlebg">春南</td>
               <td class=" trtitlebg">夏东</td>
               <td class=" trtitlebg">夏南</td>
               <td class=" trtitlebg">秋西</td>
               <td class=" trtitlebg">秋北</td>
               <td width="45" class=" trtitlebg">冬西</td>
               <td width="57" class=" trtitlebg">冬北</td>
             </tr>
           </tbody>
         </table>
           
   <div class="kong5"></div>
  <table class="tablegraybg" width="980" border="0" cellpadding="2" cellspacing="1">
    <tbody><tr class=" zs_bgligyellow">
      <td class=" bglyellow boldchar" width="85" align="center">辅助说明</td>
      <td align="left"><div class="all5px lineh20">季节方位划分情况： <br><br>

        <span class="boldchar">季节：</span><br>
        春-01至09　　夏-10至18   秋-19至27　冬-28至36 <br>

        <br>
        <span class="boldchar">方位：</span><br>
        东-01、03、05、07、09、11、13、15、17 <br>
        南-02、04、06、08、10、12、14、16、18 <br>
        西-19、21、23、25、27、29、31、33、35 <br>
        北-20、22、24、26、28、30、32、34、36<br>

        <br>
        <span class="boldchar">组合划分：</span> <br>
        春东 01、03、05、07、09、 <br>
        春南 02、04、06、08、 <br>
        夏东 11、13、15、17 <br>
        夏南 10、12、14、16、18 <br>

        秋西 19、21、23、25、27、 <br>
        秋北 20、22、24、26、 <br>
        冬西 29、31、33、35 <br>
        冬北 28、30、32、34、36。<br>
        </div></td>
    </tr>
  </tbody></table>

	</div>
	<jsp:include page="../bottom_common.jsp" />
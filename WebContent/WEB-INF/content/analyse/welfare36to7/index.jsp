<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="decorator" content="analyse" />
<title>好彩1综合走势图 - 球客网</title>

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
		<table class="zstablegraybg" width="100%" border="0" cellpadding="0"
			cellspacing="1">
			<tbody>
				<tr class="trtitlebg">
					<td rowspan="2" width="78"><select size="1"
						style="width: 50px;" onchange="sortDisplay(this.value);">
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
					</select>
					</td>
					<td width="65" rowspan="2">开奖号码</td>
					<td colspan="2" class="b1redpx">大小</td>
					<td colspan="2">单双</td>
					<td colspan="2">质合</td>
					<td colspan="3">012路</td>
					<td colspan="2">合单合双</td>
					<td colspan="4">字头</td>
				</tr>
				<tr class="zsttrpinklig">
					<td class="b1redpx" width="52">大</td>
					<td width="51">小</td>
					<td width="53" class="b1redpx">单</td>
					<td width="50">双</td>
					<td width="51" class="b1redpx">质</td>
					<td width="51">合</td>
					<td width="58" b1redpx>0路</td>
					<td width="55">1路</td>
					<td class="" width="50">2路</td>
					<td width="70">合单</td>
					<td width="66">合双</td>
					<td width="48" class="b1redpx">0头</td>
					<td width="54">1头</td>
					<td width="56">2头</td>
					<td width="54">3头</td>
				</tr>
				<%
						ArrayList<Welfare36to7MissDataInfo> datas = (ArrayList<Welfare36to7MissDataInfo>) request.getAttribute("datas");
						Map<String, Integer> missMap = new HashMap<String, Integer>();
						missMap.put("d",0);
						missMap.put("x",0);
						missMap.put("o",0);//双
						missMap.put("e",0);//单
						missMap.put("z",0);//质
						missMap.put("h",0);//合
						missMap.put("l",0);//0
						missMap.put("y",0);//1
						missMap.put("el",0);//2
						missMap.put("hd",0);//合单
						missMap.put("hs",0);//合双
						missMap.put("lt",0);//0头
						missMap.put("yt",0);//1头
						missMap.put("et",0);//2头
						missMap.put("st",0);//3头

						Integer[] NUMS = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
						Integer[] ZNUMS = new Integer[]{1,2,3,5,7,11,13,17,19,23,29,31};
						Integer[] HNUMS = new Integer[]{4,6,8,9,10,12,14,15,16,18,20,21,22,24,25,26,27,28,30,32,33,34,35,36};
						for (Welfare36to7MissDataInfo missData : datas) {
							String resultDatas = missData.getResult();
							String periodNumber = missData.getPeriodNumber();
							String[] arr = resultDatas.split(",");
							String result = arr[6];
							Integer num = new Integer(result);
							
					%>
				<tr class="trw">
					<td class="graychar333"><%=periodNumber %></td>
					<td class="redredbchar"><%=result %></td>
					<%
						if(num>18&&num<=36){
							missMap.put("d",0);
					%>
						<td class=" trchoseo"><span class="" _cancas="cancas" _group="bigsmall">大</span></td>
					<%
						}else{
							missMap.put("d",missMap.get("d")+1);
					%>
						<td class=""><%=missMap.get("d") %></td>			
					<%
						}
					%>
					<%
						if(num>0&&num<=18){
							missMap.put("x",0);
					%>
						<td class=" trchoseo"><span _cancas="cancas" _group="bigsmall">小</span></td>
					<%
						}else{
							missMap.put("x",missMap.get("x")+1);
					%>
						<td class=""><%=missMap.get("x") %></td>			
					<%
						}
						if(num%2==1){
							missMap.put("e",0);
					%>
						<td class="trrrs">单</td>
					<%
						}else{
							missMap.put("e",missMap.get("e")+1);
					%>
						<td class=""><%=missMap.get("e") %></td>			
					<%
						}		
						if(num%2==0){
							missMap.put("o",0);
					%>
						<td class="trrrs">双</td>
					<%
						}else{
							missMap.put("o",missMap.get("o")+1);
					%>
						<td class=""><%=missMap.get("o") %></td>			
					<%		
						}
						if(num==1||num==2||num==3||num==5||num==7||num==11||num==13||num==17||num==19||num==23||num==29||num==31){
								missMap.put("z",0);
					%>
						<td class="trchoseo">质</td>		
					<%
							}else{
								missMap.put("z",missMap.get("z")+1);
					%>
						<td class=""><%=missMap.get("z") %></td>							
					<%
						}
						if(num==4||num==6||num==8||num==9||num==10||num==12||num==14||num==15||num==16||num==18||num==20||num==21||num==22||num==24||num==25||num==26||num==27||num==28||num==30||num==32||num==33||num==34||num==35||num==36){
								missMap.put("h",0);
					%>
						<td class="trchoseo">合</td>						
					<%
						}else{
					%>
						<td class=""><%=missMap.get("z") %></td>									
					<%
						}
						if(num%3==0){
							missMap.put("l",0);
					%>
						<td class="trrrs"><%=num %></td>	
					<%
						}else{
							missMap.put("l",missMap.get("l")+1);
					%>
						<td class=""><%=missMap.get("l") %></td>						
					<%
						}
						if(num%3==1){
							missMap.put("y",0);
					%>
						<td class="trrrs"><%=num %></td>	
					<%
						}else{
							missMap.put("y",missMap.get("y")+1);
					%>
						<td class=""><%=missMap.get("y") %></td>						
					<%
						}
						if(num%3==2){
							missMap.put("el",0);
					%>
						<td class="trrrs"><%=num %></td>	
					<%
						}else{
							missMap.put("el",missMap.get("el")+1);
					%>
						<td class=""><%=missMap.get("el") %></td>						
					<%
						}
						
						if(result.length()>1){
							char[] nums = result.toCharArray(); 
							Integer shi = new Integer(nums[0]);
							Integer ge = new Integer(nums[1]);
							if((shi+ge)%2==0){
								missMap.put("hs",0);
					%>
							<td class="b1redpx trchoseo">合双</td>							
					<%
							}else{
								missMap.put("hs",missMap.get("hs")+1);								
					%>
							<td class="b1redpx"><%=missMap.get("hs") %></td>							
					<%						
							}
							if((shi+ge)%2==1){
								missMap.put("hd",0);
					%>		
							<td class="b1redpx trchoseo">合单</td>													
					<%
							}else{
								missMap.put("hd",missMap.get("hd")+1);								
					%>
							<td class="b1redpx"><%=missMap.get("hd") %></td>														
					<%			
							}
					}else{
						if(num%2==0){
							missMap.put("hs",0);
					%>
							<td class="b1redpx trchoseo">合双</td>							
					<%
						}else{
							missMap.put("hs",missMap.get("hs")+1);								
					%>
							<td class="b1redpx"><%=missMap.get("hs") %></td>							
					<%						
						}
						if(num%2==1){
							missMap.put("hd",0);
					%>		
							<td class="b1redpx trchoseo">合单</td>
					<%
						}else{
							missMap.put("hd",missMap.get("hd")+1);								
					%>
						<td class="b1redpx"><%=missMap.get("hd") %></td>														
					<%			
						}
					}
						if(num<10&&num>0){
							missMap.put("lt",0);
					%>
						<td class="trrrs"><%=result %></td>					
					<%
						}else{
							missMap.put("lt",missMap.get("lt")+1);							
					%>		
						<td class=""><%=missMap.get("lt") %></td>					
					<%
						}
						if(num>=10&&num<20){
							missMap.put("yt",0);
					%>
						<td class="trrrs"><%=result %></td>					
					<%
						}else{
							missMap.put("yt",missMap.get("yt")+1);							
					%>	
						<td class=""><%=missMap.get("yt") %></td>					
					<%
						}
						if(num>=20&&num<30){
							missMap.put("et",0);
					%>
						<td class="trrrs"><%=result %></td>					
					<%
						}else{
							missMap.put("et",missMap.get("et")+1);	
					%>	
						<td class=""><%=missMap.get("et") %></td>					
					<%
						}
						if(num>=30&&num<40){
							missMap.put("st",0);
					%>
						<td class="trrrs"><%=result %></td>					
					<%
						}else{
							missMap.put("st",missMap.get("st")+1);	
					%>
						<td class=""><%=missMap.get("st") %></td>					
					<%
						}
					%>					
				</tr>
				<%
						}
				%>					
					
					
				<tr class="trw">
					<td class="trgray graychar333">模拟选号</td>
					<td>&nbsp;</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">大</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">小</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">单</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">双</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">质</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">合</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">0路</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">1路</td>
					<td class="" style="cursor: pointer;" onclick="dxjo_click(this);">2路</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">合单</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">合双</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">0头</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">1头</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">2头</td>
					<td style="cursor: pointer;" onclick="dxjo_click(this);">3头</td>
				</tr>
				<tr class="trtitlebg">
					<td rowspan="2" class="zsttrpink"><select size="1"
						style="width: 50px;" onchange="sortDisplay(this.value);">
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
					<td rowspan="2" class=" zsttrpink">出号</td>
					<td class="b1redpx zsttrpinklig">大</td>
					<td class="zsttrpinklig">小</td>
					<td class="zsttrpinklig">单</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">双</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">质</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">合</span>
					</td>
					<td class="zsttrpinklig">0<span style="cursor: pointer;">路</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">1路</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">2路</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">合单</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">合双</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">0头</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">1头</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">2头</span>
					</td>
					<td class="zsttrpinklig"><span style="cursor: pointer;">3头</span>
					</td>
				</tr>
			</tbody>
		</table>


		<div class="kong5"></div>
			<table class="tablegraybg" width="980" border="0" cellpadding="2"
				cellspacing="1">
				<tbody>
					<tr class=" zs_bgligyellow">
						<td class=" bglyellow boldchar" width="85" align="center">辅助说明</td>
						<td align="left"><div class="all5px lineh20">
								<span class="boldchar">大数小数划分：</span><br> 大数:19-36为大数；<br />
								小数:01-18为小数。<br> <br> <span class="boldchar">单数双数划分：</span><br>
								单数:-01 03 05 07 09 11 13 15 17 19 21 23 25 27 29 31 33 35；<br />
								双数:-02 04 06 08 10 12 14 16 18 20 22 24 26 28 30 32 34 36。<br />

								<br> <span class="boldchar">阴肖阳肖划分：</span><br>
								阴肖----鼠、龙、蛇、马、狗、猪；阳肖----牛、虎、兔、羊、猴、鸡。 <br> <br> <span
									class="boldchar">质数合数划分：</span><br> 质数:01 02 03 05 07 11
								13 17 19 23 29 31；<br /> 合数:04 06 08 09 10 12 14 15 16 18 20 21
								22 24 25 26 27 28 30 32 33 34 35 36。 <br> <br> <span
									class="boldchar">012路划分：</span><br> 0路： 03 06 09 12 15 18
								21 24 27 30 33 36 ；<br /> 1路：01 04 07 10 13 16 19 22 25 28 31
								34；<br /> 2路：02 05 08 11 14 17 20 23 26 29 32 35。<br> <br>
								<span class="boldchar">合单合双划分：</span><br>
								合单：开奖号码十位数和个位数的和值为单数的号码，包括：01 03 05 07 09 10 12 14 16 18 21 23
								25 27 29 30 32 34 36；<br> 合双：开奖号码十位数和个位数的和值为双数的号码，包括：02 04
								06 08 11 13 15 17 19 20 22 24 26 28 31 33 35。<br /> <br /> <span
									class="boldchar">单数双数划分：</span><br>
								0头：01-02-03-04-05-06-07-08-09；<br />
								1头：10-11-12-13-14-15-16-17-18-19;<br />
								2头：20-21-22-23-24-25-26-27-28-29；<br />
								3头：30-31-32-33-34-35-36。<br />
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div id="div_line"><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 371px; left: 436px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 407px; left: 435px;"></canvas><canvas height="35" width="69" style="position: absolute; visibility: visible; top: 441px; left: 365px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 477px; left: 365px;"></canvas><canvas height="35" width="176" style="position: absolute; visibility: visible; top: 511px; left: 401px;"></canvas><canvas height="35" width="176" style="position: absolute; visibility: visible; top: 546px; left: 401px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 582px; left: 365px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 616px; left: 365px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 652px; left: 436px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 686px; left: 436px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 722px; left: 542px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 757px; left: 543px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 791px; left: 436px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 826px; left: 436px;"></canvas><canvas height="35" width="212" style="position: absolute; visibility: visible; top: 861px; left: 365px;"></canvas><canvas height="35" width="69" style="position: absolute; visibility: visible; top: 896px; left: 365px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 932px; left: 436px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 967px; left: 471px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1002px; left: 436px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 1036px; left: 436px;"></canvas><canvas height="35" width="176" style="position: absolute; visibility: visible; top: 1071px; left: 401px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 1107px; left: 400px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 1142px; left: 400px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 1177px; left: 400px;"></canvas><canvas height="35" width="71" style="position: absolute; visibility: visible; top: 1211px; left: 328px;"></canvas><canvas height="35" width="177" style="position: absolute; visibility: visible; top: 1246px; left: 328px;"></canvas><canvas height="35" width="70" style="position: absolute; visibility: visible; top: 1281px; left: 507px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 1316px; left: 472px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1352px; left: 436px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 371px; left: 723px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 407px; left: 829px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 442px; left: 794px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 476px; left: 651px;"></canvas><canvas height="35" width="177" style="position: absolute; visibility: visible; top: 511px; left: 651px;"></canvas><canvas height="33" width="33" style="position: absolute; visibility: visible; top: 547px; left: 830px;"></canvas>
         <canvas height="33" width="34" style="position: absolute; visibility: visible; top: 652px; left: 794px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 687px; left: 794px;"></canvas><canvas height="35" width="69" style="position: absolute; visibility: visible; top: 721px; left: 794px;"></canvas><canvas height="35" width="249" style="position: absolute; visibility: visible; top: 756px; left: 614px;"></canvas><canvas height="35" width="214" style="position: absolute; visibility: visible; top: 791px; left: 614px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 827px; left: 829px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 861px; left: 687px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 897px; left: 651px;"></canvas><canvas height="35" width="177" style="position: absolute; visibility: visible; top: 931px; left: 651px;"></canvas>
         <canvas height="35" width="70" style="position: absolute; visibility: visible; top: 1001px; left: 758px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 1036px; left: 651px;"></canvas><canvas height="35" width="141" style="position: absolute; visibility: visible; top: 1071px; left: 651px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1107px; left: 758px;"></canvas><canvas height="35" width="105" style="position: absolute; visibility: visible; top: 1141px; left: 651px;"></canvas><canvas height="35" width="177" style="position: absolute; visibility: visible; top: 1176px; left: 651px;"></canvas><canvas height="35" width="177" style="position: absolute; visibility: visible; top: 1211px; left: 651px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1247px; left: 651px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1282px; left: 687px;"></canvas><canvas height="35" width="69" style="position: absolute; visibility: visible; top: 1316px; left: 723px;"></canvas><canvas height="35" width="69" style="position: absolute; visibility: visible; top: 1351px; left: 794px;"></canvas><canvas height="35" width="108" style="position: absolute; visibility: visible; top: 371px; left: 1011px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 407px; left: 975px;"></canvas><canvas height="35" width="71" style="position: absolute; visibility: visible; top: 441px; left: 975px;"></canvas><canvas height="35" width="107" style="position: absolute; visibility: visible; top: 476px; left: 1048px;"></canvas><canvas height="35" width="144" style="position: absolute; visibility: visible; top: 511px; left: 1011px;"></canvas><canvas height="35" width="108" style="position: absolute; visibility: visible; top: 546px; left: 901px;"></canvas><canvas height="33" width="36" style="position: absolute; visibility: visible; top: 582px; left: 901px;"></canvas><canvas height="33" width="36" style="position: absolute; visibility: visible; top: 617px; left: 901px;"></canvas><canvas height="35" width="218" style="position: absolute; visibility: visible; top: 651px; left: 901px;"></canvas><canvas height="35" width="144" style="position: absolute; visibility: visible; top: 686px; left: 975px;"></canvas><canvas height="35" width="72" style="position: absolute; visibility: visible; top: 721px; left: 901px;"></canvas><canvas height="35" width="181" style="position: absolute; visibility: visible; top: 756px; left: 901px;"></canvas><canvas height="33" width="1" style="position: absolute; visibility: visible; top: 792px; left: 1083px;"></canvas><canvas height="35" width="181" style="position: absolute; visibility: visible; top: 826px; left: 901px;"></canvas><canvas height="35" width="108" style="position: absolute; visibility: visible; top: 861px; left: 901px;"></canvas><canvas height="33" width="35" style="position: absolute; visibility: visible; top: 897px; left: 1011px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 932px; left: 1048px;"></canvas><canvas height="35" width="71" style="position: absolute; visibility: visible; top: 966px; left: 1011px;"></canvas><canvas height="35" width="108" style="position: absolute; visibility: visible; top: 1001px; left: 1011px;"></canvas><canvas height="35" width="144" style="position: absolute; visibility: visible; top: 1036px; left: 975px;"></canvas><canvas height="35" width="71" style="position: absolute; visibility: visible; top: 1071px; left: 975px;"></canvas><canvas height="35" width="107" style="position: absolute; visibility: visible; top: 1106px; left: 939px;"></canvas><canvas height="35" width="107" style="position: absolute; visibility: visible; top: 1141px; left: 939px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1177px; left: 1048px;"></canvas><canvas height="35" width="71" style="position: absolute; visibility: visible; top: 1211px; left: 1084px;"></canvas><canvas height="35" width="144" style="position: absolute; visibility: visible; top: 1246px; left: 1011px;"></canvas><canvas height="35" width="70" style="position: absolute; visibility: visible; top: 1281px; left: 939px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1317px; left: 939px;"></canvas><canvas height="33" width="34" style="position: absolute; visibility: visible; top: 1352px; left: 939px;"></canvas></div></div>

	</div>
	<jsp:include page="../bottom_common.jsp" />
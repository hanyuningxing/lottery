<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="时时彩_五星质合走势图_时时彩走势图_彩票走势图_球客网" />
<meta name="description" content="球客网时时彩,时时彩走势图,时时彩五星质合走势图" />
<title>重庆时时彩万能六码走势图表 - 重庆时时彩走势图 - 球客网</title>
<link href="../../../../pages/css/analyse.css" rel="stylesheet" type="text/css" />
<link href="../../../../pages/css/news_mainuse.css" rel="stylesheet"
	type="text/css" />
<link href="../../../../pages/css/pdzx.css" rel="stylesheet" type="text/css" />
<link href="../../../../pages/css/ssc_zs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../../../js/analyse/ssc/sxjo.js" /></script>

<script type="text/javascript" src="../../../../js/analyse/excanvas.js""></script>
<script type="text/javascript" src="../../../../js/analyse/drawLine.js"></script>
</head>
<body>
<div id="ga">
	<div id="divChart">
		<table class="trendCharts trendSSQ" style="width:980px" id="tableChart" cellspacing="0">
			<tbody>
				<tr class="tr-first">
					<th colspan="2" rowspan="2" class="td-first" style="" title="点击排序">期号</th>
					<th rowspan="2">开奖号码</th>
					<th colspan="10" class="td-last">万能六码</th>
				</tr>
				<tr class="tr-first">
					<th>012346</th>
					<th>012359</th>
					<th>012489</th>
					<th>013789</th>
					<th>026789</th>
					<th>045678</th>
					<th>123457</th>
					<th>156789</th>
					<th>234568</th>
					<th class="td-last">345679</th>
				</tr>
<%
			ArrayList<SscMissDataInfo> datas = (ArrayList)request.getAttribute("datas");
			Map<String,Integer> missMap=new HashMap<String,Integer>();
			
			String[] balls = new String[]{"012346","012359","012489","013789","026789","045678","123457","156789","234568","345679" };
			
			for(String ball:balls){
				missMap.put(ball,0);
			}
			
			for(SscMissDataInfo missData:datas){
				String resultDatas = missData.getResult();
				String periodNumberStr = missData.getPeriodNumber();
				String periodNumber = periodNumberStr.substring(2,8)+"-"+periodNumberStr.substring(8,periodNumberStr.length());
				String[] arr = resultDatas.split(",");
				String result = "";
				for(String r:arr){
					result+=new Integer(r).toString();
				}
				String bai = new Integer(arr[2]).toString();
				String shi = new Integer(arr[3]).toString();
				String ge = new Integer(arr[4]).toString();
				String resultPrex = new Integer(arr[0]).toString() + new Integer(arr[1]).toString();
				String resultSuffix = bai+shi+ge;
				
				
				
				for(String ball:balls){
					if(ball.indexOf(bai)!=-1&&ball.indexOf(shi)!=-1&&ball.indexOf(ge)!=-1){
						missMap.put(ball,0);
					}else{
						missMap.put(ball,missMap.get(ball)+1);
					}
				}

			
			%>


				<tr>
					<td class="td-first" colspan="2"
						title="第<%=periodNumber%>期
开奖号码:<%=result%>"><%=periodNumber%></td>
					<td class="group1" title="第<%=periodNumber%>期
开奖号码:<%=result%>"><%=resultPrex%><font
						color="red"><%=resultSuffix%></font>
					</td>
					<%
						for (String ball : balls) {
								if (0 == missMap.get(ball)) {
					%>					
					<td class="" style="cursor: pointer;"><span class="n-jiu"><%=resultSuffix %></span></td>
					<%
								}else{
					%>
					<td class="" style="cursor: pointer;"><%=missMap.get(ball) %></td>
					<%
								}
						}
					%>
					</tr>
<%
			}
%>
				<tr class="getNum">
					<td colspan="3" rowspan="5" class="td-first">模拟选号<span
						class="icos ico_num_arrow"></span>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012346</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012359</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012489</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">013789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">026789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">045678</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">123457</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">156789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">234568</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">345679</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012346</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012359</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012489</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">013789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">026789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">045678</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">123457</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">156789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">234568</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">345679</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012346</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012359</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012489</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">013789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">026789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">045678</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">123457</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">156789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">234568</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">345679</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012346</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012359</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012489</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">013789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">026789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">045678</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">123457</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">156789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">234568</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">345679</a></td>
				</tr>
				<tr class="getNum">				
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012346</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012359</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">012489</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">013789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">026789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">045678</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">123457</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">156789</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">234568</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">345679</a></td>
				</tr>
			</tbody>
		</table>
	</div></div>

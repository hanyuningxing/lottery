<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="时时彩_五星质合走势图_时时彩走势图_彩票走势图_环彩网" />
<meta name="description" content="环彩网时时彩,时时彩走势图,时时彩五星质合走势图" />
<title>重庆时时彩三星大小奇偶走势图表 - 球客网</title>
<link href="/pages/css/analyse.css" rel="stylesheet" type="text/css" />
<link href="/pages/css/news_mainuse.css" rel="stylesheet"
	type="text/css" />
<link href="/pages/css/pdzx.css" rel="stylesheet" type="text/css" />
<link href="/pages/css/ssc_zs.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/js/analyse/ssc/sxjo.js"/></script>
<script type="text/javascript" src="/js/analyse/excanvas.js""></script>
<script type="text/javascript" src="/js/analyse/drawLine.js"></script>
</head>
<body>
<div id="ga">
	<div id="divChart" style="width: 980px;">
		<table class="trendCharts trendSSQ" id="tableChart" cellspacing="0">
			<tbody>
				<tr class="tr-first">
					<th colspan="2" rowspan="3" class="td-first" style="" title="点击排序">期号</th>
					<th rowspan="3">开奖<br>号码</th>
					<th colspan="16">大小奇偶</th>
					<th rowspan="2" colspan="4" class="td-last">大小奇偶分布</th>
				</tr>
				<tr class="tr-first">
					<th colspan="4">3大</th>
					<th colspan="4">2大1小</th>
					<th colspan="4">1大2小</th>
					<th colspan="4">3小</th>
				</tr>
				<tr class="tr-first">
					<th>3奇</th>
					<th>2奇1偶</th>
					<th>1奇2偶</th>
					<th>3偶</th>
					<th>3奇</th>
					<th>2奇1偶</th>
					<th>1奇2偶</th>
					<th>3偶</th>
					<th>3奇</th>
					<th>2奇1偶</th>
					<th>1奇2偶</th>
					<th>3偶</th>
					<th>3奇</th>
					<th>2奇1偶</th>
					<th>1奇2偶</th>
					<th>3偶</th>
					<th>大</th>
					<th>小</th>
					<th>奇</th>
					<th class="td-last">偶</th>
				</tr>
				<tr>
					<%
						ArrayList<SscMissDataInfo> datas = (ArrayList) request
								.getAttribute("datas");
						Map<String, Integer> missMap = new HashMap<String, Integer>();
						Map<String, Integer> curPanoMap = new HashMap<String, Integer>();//
						Map<String, Integer> maxPanoMap = new HashMap<String, Integer>();//
						Map<String, Integer> lPanoMap = new HashMap<String, Integer>();
						Map<String, Integer> maxMissMap = new HashMap<String, Integer>();
						Map<String,String> propertyMap = new HashMap<String, String>();
						
						String[] contents = new String[] {"3大3奇","3大2奇1偶","3大1奇2偶","3大3偶 ","2大1小3奇","2大1小2奇1偶","2大1小1奇2偶","2大1小3偶","1大2小3奇","1大2小2奇1偶","1大2小1奇2偶 ","1大2小3偶 ","3小3奇","3小2奇1偶","3小1奇2偶","3小3偶"};
						
						String[] keys = new String[] {"3d3j","3d2j1o","3d1j2o","3d3o","2d1x3j","2d1x2j1o","2d1x1j2o","2d1x3o","1d2x3j","1d2x2j1o","1d2x1j2o","1d2x3o","3x3j","3x2j1o","3x1j2o","3x3o"};

						for(String key:keys){
							missMap.put(key,0);
							maxPanoMap.put(key,0);
							lPanoMap.put(key,0);
							maxMissMap.put(key,0);
							curPanoMap.put(key,0);
						}
						
						for(int i=0;i<keys.length;i++){
							propertyMap.put(keys[i],contents[i]);
						}
						
						for (SscMissDataInfo missData : datas) {
							String resultDatas = missData.getResult();

							String periodNumberStr = missData.getPeriodNumber();
							String periodNumber = periodNumberStr.substring(2, 8)
									+ "-"
									+ periodNumberStr
											.substring(8, periodNumberStr.length());
							String[] arr = resultDatas.split(",");
							String result = "";
							String resultPrex = new Integer(arr[0]).toString()
									+ new Integer(arr[1]).toString();
							String resultSuffix = new Integer(arr[2]).toString()
									+ new Integer(arr[3]).toString()
									+ new Integer(arr[4]).toString();

							for (String r : arr) {
								result += new Integer(r).toString();
							}

							int dCount = 0;
							int xCount = 0;
							int jCount = 0;
							int oCount = 0;

							if (new Integer(arr[2]) >= 5 && new Integer(arr[2]) <= 9)
								dCount++;
							else
								xCount++;

							if (new Integer(arr[3]) >= 5 && new Integer(arr[3]) <= 9)
								dCount++;
							else
								xCount++;

							if (new Integer(arr[4]) >= 5 && new Integer(arr[4]) <= 9)
								dCount++;
							else
								xCount++;

							if (new Integer(arr[2]) % 2 == 0)
								oCount++;
							else
								jCount++;

							if (new Integer(arr[3]) % 2 == 0)
								oCount++;
							else
								jCount++;

							if (new Integer(arr[4]) % 2 == 0)
								oCount++;
							else
								jCount++;

							String content = "";
							if (dCount > 0)
								content += dCount + "d";
							if (xCount > 0)
								content += xCount + "x";
							if (jCount > 0)
								content += jCount + "j";
							if (oCount > 0)
								content += oCount + "o";
							lPanoMap.put(content,lPanoMap.get(content)+1);
							curPanoMap.put(content,curPanoMap.get(content)+1);
							
							for (String ball : keys) {
								if (!ball.equals(content)) {
									missMap.put(ball,missMap.get(ball) + 1);
									lPanoMap.put(ball,0);
								}
							}
							
							if (maxMissMap.get(content) < missMap.get(content)) {
								maxMissMap.put(content, missMap.get(content));
							}
							missMap.put(content, 0);
							
							if (maxPanoMap.get(content) < lPanoMap.get(content)) {
								maxPanoMap.put(content, lPanoMap.get(content));
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
						for (String ball : keys) {
								if (0 != missMap.get(ball)) {
					%>
					<td class="group2" style="cursor: pointer;"><%=missMap.get(ball)%></td>
					<%
						} else {
					%>
					<td class="group2" style="cursor: pointer;"><span
						class="n-jiu"><%=propertyMap.get(ball)%></span>
					</td>
					<%
						}
							}
							if (dCount > 0) {
					%>
					<td class="td-blue"><span class="n-ssq"><%=dCount%></span>
					</td>
					<%
						} else {
					%>
					<td class="">0</td>
					<%
						}
							if (xCount > 0) {
					%>
					<td class="td-blue"><span class="n-ssq"><%=xCount%></span>
					</td>
					<%
						} else {
					%>
					<td class="">0</td>
					<%
						}
							if (jCount > 0) {
					%>
					<td class="td-blue"><span class="n-ssq"><%=jCount%></span>
					</td>
					<%
						} else {
					%>
					<td class="">0</td>
					<%
						}
							if (oCount > 0) {
					%>
					<td class="td-blue"><span class="n-ssq"><%=oCount%></span>
					</td>
					<%
						} else {
					%>
					<td class="">0</td>
					<%
						}
						}
					%>
				
				<tr class="tr-segment getNum-td-last">
					<td colspan="46" class="td-blue td-last" style="height: 0pt;"></td>
				</tr>
				<tr class="getNum">
					<td colspan="3" rowspan="5" class="td-first">模拟选号<span
						class="icos ico_num_arrow"></span>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 2奇1偶</a></td>				
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 3偶
					</a>
					</td>
					<td colspan="4" class="td-last"></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 2奇1偶</a></td>				
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 1奇2偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小 3偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 3奇</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 2奇1偶</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小 3偶
					</a>
					</td>
					<td colspan="4" class="td-last"></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3偶
					</a>
					</td>
					<td colspan="4" class="td-last"></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3偶
					</a>
					</td>
					<td colspan="4" class="td-last"></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3大·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2大1小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1大2小·3偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3奇
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·2奇1偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·1奇2偶
					</a>
					</td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3小·3偶
					</a>
					</td>
					<td colspan="4" class="td-last"></td>
				</tr>


				<tr class="drop">
					<td colspan="3" class="td-first">当前遗漏</td>
					<%
					for(String key:keys){
					%>
					<td><span class="lger"><%=missMap.get(key)%></span></td>
					<%
					}
					%>	
					<td colspan="4" class="td-last"></td>
				</tr>

				<tr class="drop">
					<td colspan="3" class="td-first">平均遗漏</td>
					<%
					for(String key:keys){
						Integer avg = (datas.size()-curPanoMap.get(key))/(curPanoMap.get(key)+1);
					%>
					<td><span class="lger"><%=avg%></span></td>
					<%
					}
					%>	
					<td colspan="4" class="td-last"></td>				
				</tr>


				<tr class="histogram histogram2 tr-last">
					<td class="td-first" colspan="3">出现次数</td>
					<%
					for(String key:keys){
					%>
						<td><%=curPanoMap.get(key) %><i style="height: <%=curPanoMap.get(key) %>px"><em></em>
						</i>
						</td>			
					<%
					}
					%>
					<td colspan="4" class="td-last"></td>	
				</tr>

				<tr class="histogram">
					<td colspan="3" class="td-first td-first-1">最大连出</td>
					<%
					for(String key:keys){
					%>
						<td><span class="lger"><%=maxPanoMap.get(key) %></span><i style="height: NaNpx"><em></em>
						</i>
						</td>
					<%
					}
					%>
					<td colspan="4" class="td-last"></td>
				</tr>

			</tbody>
		</table>
	</div></div>
				<jsp:include page="../bottom_common.jsp" />
	

<%@ page language="java"
	import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="analyse" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="时时彩_五星质合走势图_时时彩走势图_彩票走势图_环彩网" />
<meta name="description" content="环彩网时时彩,时时彩走势图,时时彩五星质合走势图" />
<title>重庆时时彩三星和值走势图表 - 重庆时时彩走势图 - 银生宝网</title>
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
	<div id="divChart">
		<table class="trendCharts trendSSQ" id="tableChart" cellspacing=""  style="width:980px">
			<tbody>
				<tr class="tr-first">
					<th colspan="2" rowspan="2" class="td-first" style="" title="点击排序"
						onclick="myChart.OrderByAsc=!myChart.OrderByAsc;myChart.ChangeChart();dline.againDrowLine();">期号</th>
					<th rowspan="2">开奖<br>号码</th>
					<th colspan="28">三星和值</th>
					<th colspan="6">三星和值段</th>
					<th colspan="10" class="td-last">三星和值尾数</th>
				</tr>
				<tr class="tr-first">
					<th>0</th>
					<th>1</th>
					<th>2</th>
					<th>3</th>
					<th>4</th>
					<th>5</th>
					<th>6</th>
					<th>7</th>
					<th>8</th>
					<th>9</th>
					<th>10</th>
					<th>11</th>
					<th>12</th>
					<th>13</th>
					<th>14</th>
					<th>15</th>
					<th>16</th>
					<th>17</th>
					<th>18</th>
					<th>19</th>
					<th>20</th>
					<th>21</th>
					<th>22</th>
					<th>23</th>
					<th>24</th>
					<th>25</th>
					<th>26</th>
					<th>27</th>
					<th>0-8</th>
					<th>9-11</th>
					<th>12-13</th>
					<th>14-15</th>
					<th>16-18</th>
					<th>19-27</th>
					<th>0</th>
					<th>1</th>
					<th>2</th>
					<th>3</th>
					<th>4</th>
					<th>5</th>
					<th>6</th>
					<th>7</th>
					<th>8</th>
					<th class="td-last">9</th>
				</tr>
				<tr>
					<%
						ArrayList<SscMissDataInfo> datas = (ArrayList<SscMissDataInfo>) request.getAttribute("datas");
						Map<String, Integer> hzMap = new HashMap<String, Integer>();
						Map<String, Integer> hzdMap = new HashMap<String, Integer>();
						Map<String, Integer> hzwsMap = new HashMap<String, Integer>();

						String[] balls = new String[] { "0", "1", "2", "3", "4", "5", "6",
								"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
								"17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
								"27" };
						String[] digitalSection = new String[] { "0-8", "9-11", "12-13",
								"14-15", "16-18", "19-27" };
						String[] tailNum = new String[] { "0", "1", "2", "3", "4", "5",
								"6", "7", "8", "9" };

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
							Integer hz = new Integer(arr[2]) + new Integer(arr[3])
									+ new Integer(arr[4]);
							String hzd = "";
							if (hz >= 0 && hz <= 8) {
								hzd = "0-8";
							} else if (hz >= 9 && hz <= 11) {
								hzd = "9-11";
							} else if (hz >= 12 && hz <= 13) {
								hzd = "12-13";
							} else if (hz >= 14 && hz <= 15) {
								hzd = "14-15";
							} else if (hz >= 16 && hz <= 18) {
								hzd = "16-18";
							} else {
								hzd = "19-27";
							}
							Integer tail = hz % 10;
							hzMap.put(hz.toString(), 0);
							hzdMap.put(hzd, 0);
							hzwsMap.put(tail.toString(), 0);

							for (String ball : balls) {
								if (!ball.equals(hz.toString())) {
									if (null == hzMap.get(ball)) {
										hzMap.put(ball, 1);
									} else {
										hzMap.put(ball, hzMap.get(ball) + 1);
									}
								}
							}

							for (String digital : digitalSection) {
								if (!digital.equals(hzd)) {
									if (null == hzMap.get(digital)) {
										hzdMap.put(digital, 1);
									} else {
										hzdMap.put(digital, hzdMap.get(digital) + 1);
									}
								}
							}

							for (String num : tailNum) {
								if (!num.equals(tail.toString())) {
									if (null == hzwsMap.get(num)) {
										hzwsMap.put(num, 1);
									} else {
										hzwsMap.put(num, hzwsMap.get(num) + 1);
									}
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
								if (null != hzMap.get(ball) && 0 != hzMap.get(ball)) {
					%>
					<td class="group2" style="cursor: pointer;"><%=hzMap.get(ball)%></td>
					<%
						} else {
					%>
					<td class="group2" style="cursor: pointer;"><span
						class="n-jiu"><%=ball%></span></td>
					<%
						}
							}

							for (String digital : digitalSection) {
								if (null != hzdMap.get(digital) && 0 != hzdMap.get(digital)) {
					%>
					<td class="group2" style="cursor: pointer;"><%=hzdMap.get(digital)%></td>
					<%
						} else {
					%>
					<td class="group3" style="cursor: pointer;"><span
						class="n-xie"><%=digital%></span></td>
					<%
						}
							}

							for (String num : tailNum) {
								if (null != hzwsMap.get(num) && 0 != hzwsMap.get(num)) {
					%>
					<td class="group3" style="cursor: pointer;"><%=hzwsMap.get(num)%></td>
					<%
						} else {
					%>
					<td class="" style="cursor: pointer;"><span class="n-ssq"><%=num%></span>
					</td>
					<%
						}
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
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">10</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">11</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">12</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">13</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">14</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">15</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">16</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">17</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">18</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">19</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">20</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">21</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">22</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">23</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">24</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">25</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">26</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">27</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">10</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">11</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">12</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">13</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">14</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">15</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">16</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">17</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">18</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">19</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">20</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">21</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">22</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">23</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">24</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">25</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">26</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">27</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">10</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">11</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">12</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">13</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">14</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">15</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">16</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">17</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">18</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">19</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">20</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">21</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">22</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">23</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">24</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">25</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">26</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">27</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">10</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">11</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">12</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">13</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">14</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">15</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">16</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">17</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">18</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">19</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">20</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">21</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">22</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">23</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">24</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">25</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">26</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">27</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
				</tr>
				<tr class="getNum">
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">10</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">11</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">12</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">13</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">14</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">15</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">16</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">17</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">18</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">19</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">20</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">21</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">22</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">23</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">24</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">25</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">26</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">27</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">0</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">1</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">2</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">3</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">4</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">5</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">6</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">7</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">8</a></td>
					<td><a class="sss" style="cursor:pointer" onclick="dxjo_click(this)">9</a></td>
				</tr>
			</tbody>
		</table>
	</div>

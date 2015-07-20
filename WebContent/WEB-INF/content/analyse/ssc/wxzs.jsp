<%@ page language="java" import="java.util.*,java.text.*,com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="analyse" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="时时彩_五星质合走势图_时时彩走势图_彩票走势图_环彩网" />
<meta name="description" content="环彩网时时彩,时时彩走势图,时时彩五星质合走势图" />
<title>时时彩五星走势图-时时彩走势图-彩票走势图-银生宝网</title>
<link href="/pages/css/analyse.css" rel="stylesheet" type="text/css" />
<link href="/pages/css/news_mainuse.css" rel="stylesheet" type="text/css" />
<link href="/pages/css/pdzx.css" rel="stylesheet" type="text/css" />
<link href="/pages/css/ssc_zs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/analyse/ssc/sxjo.js"/></script>

<script type="text/javascript" src="/js/analyse/excanvas.js""></script>
<script type="text/javascript" src="/js/analyse/drawLine.js"></script>
</head>
<body>

<div id="ga">
<div id="divChart" style="width:980px"><table class="trendCharts trendSSQ" id="tableChart" cellpadding="0"
	cellspacing="0">
	<tbody>
		<tr class="tr-first">
			<th colspan="2" rowspan="2" class="td-first" style="" title="点击排序"
				onclick="myChart.OrderByAsc=!myChart.OrderByAsc;myChart.ChangeChart();dline.againDrowLine();">期号</th>
			<th rowspan="2">开奖<br>号码
			</th>
			<th colspan="10">万位</th>
			<th colspan="10">千位</th>
			<th colspan="10">百位</th>
			<th colspan="10">十位</th>
			<th colspan="10" class="td-last">个位</th>
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
		<%
			ArrayList<SscMissDataInfo> datas = (ArrayList)request.getAttribute("datas");
			Map<String,Integer> wMissDataMap=new HashMap<String,Integer>();
			Map<String,Integer> qMissDataMap=new HashMap<String,Integer>();
			Map<String,Integer> bMissDataMap=new HashMap<String,Integer>();
			Map<String,Integer> sMissDataMap=new HashMap<String,Integer>();
			Map<String,Integer> gMissDataMap=new HashMap<String,Integer>();
			
			String[] balls = new String[]{"0","1","2","3","4","5","6","7","8","9"};
			
			for(String ball:balls){
				wMissDataMap.put(ball,0);
				qMissDataMap.put(ball,0);
				bMissDataMap.put(ball,0);
				sMissDataMap.put(ball,0);
				gMissDataMap.put(ball,0);
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
				wMissDataMap.put(arr[0],0);
				qMissDataMap.put(arr[1],0);
				bMissDataMap.put(arr[2],0);
				sMissDataMap.put(arr[3],0);
				gMissDataMap.put(arr[4],0);

				for(String ball:balls){
					if(!ball.equals(arr[0])){
						if(null==wMissDataMap.get(ball)){
							wMissDataMap.put(ball,1);
						}else{
							wMissDataMap.put(ball,wMissDataMap.get(ball)+1);
						}
					}
					if(!ball.equals(arr[1])){
						if(null==qMissDataMap.get(ball)){
							qMissDataMap.put(ball,1);
						}else{
							qMissDataMap.put(ball,qMissDataMap.get(ball)+1);
						}
					}
					if(!ball.equals(arr[2])){
						if(null==bMissDataMap.get(ball)){
							bMissDataMap.put(ball,1);
						}else{
							bMissDataMap.put(ball,bMissDataMap.get(ball)+1);
						}
					}
					if(!ball.equals(arr[3])){
						if(null==sMissDataMap.get(ball)){
							sMissDataMap.put(ball,1);
						}else{
							sMissDataMap.put(ball,sMissDataMap.get(ball)+1);
						}
					}
					if(!ball.equals(arr[4])){
						if(null==gMissDataMap.get(ball)){
							gMissDataMap.put(ball,1);
						}else{
							gMissDataMap.put(ball,gMissDataMap.get(ball)+1);
						}
					}
				}

			
			%>
		<tr>
			<td class="td-first" colspan="2" title="第<%=periodNumber %>期
开奖号码:<%=result %>"><%=periodNumber %></td>
			<td class="group1" title="第<%=periodNumber %>期
开奖号码:<%=result %>"><font
				color="red"><%=result %></font>
			</td>
			<%	
			for(String ball:balls){
				Integer wan = wMissDataMap.get(ball);
				if(wan==0){
			%>
				<td class="" style="cursor: pointer; width: 16px;"><span class="n-ssq"><%=ball %></span></td>
			<%}else{ %>	
				<td class="" style="cursor: pointer; width: 16px;"><%=wan %></td>
			<%}
			}
			for(String ball:balls){
				Integer qian = qMissDataMap.get(ball);
				if(qian==0){	
			%>
				<td class=" td-blue" style="cursor: pointer; width: 16px;"><span class="n-ssq"><%=ball %></span></td>
			<%}else{ %>	
				<td class="td-segment-r" style="cursor: pointer; width: 16px;"><%=qian %></td>
			<%}
			}
			for(String ball:balls){
				Integer bai = bMissDataMap.get(ball);
				if(bai==0){
			%>	
				<td class="" style="cursor: pointer; width: 16px;"><span class="n-ssq"><%=ball %></span></td>
			<%}else{ %>
				<td class=" td-blue" style="cursor: pointer; width: 16px;"><%=bai %></td>
			<%}
			}
			for(String ball:balls){
				Integer shi = sMissDataMap.get(ball);
				if(shi==0){
			%>
				<td class=" tfl td-blue" style="cursor: pointer; width: 16px;"><span class="n-ssq"><%=ball %></span></td>
			<%}else{%>
				<td class=" tfl td-blue" style="cursor: pointer; width: 16px;"><%=shi %></td>
			<%} 
			}
			for(String ball:balls){
				Integer ge = gMissDataMap.get(ball);
				if(ge==0){
			%>
				<td class="" style="cursor: pointer; width: 16px;"><span class="n-ssq"><%=ball %></span></td>
			<%}else{ %>
				<td class="" style="cursor: pointer; width: 16px;"><%=ge %></td>
			<%}} %>
		</tr>
	<%}%>


					<tr class="tr-segment getNum-td-last">
						<td colspan="44" class="td-blue td-last" style="height: 0pt;"></td>
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
</table></div></div>

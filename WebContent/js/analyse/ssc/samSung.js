NUMS = [ '00','01','02','03','04','05','06','07','08','09'];

TAB_HEADER = '<div id="divChart" style="width:1200px"><table class="trendCharts trendSSQ" id="tableChart" onmouseout="" cellspacing="0"><tbody><tr class="tr-first"><th colspan="2" rowspan="2" class="td-first" style="" title="点击排序" onclick="myChart.OrderByAsc=!myChart.OrderByAsc;myChart.ChangeChart();dline.againDrowLine();">期号</th><th rowspan="2">开奖<br>号码</th><th colspan="10">百位</th><th colspan="10">十位</th><th colspan="10">个位</th><th rowspan="2">豹子</th><th rowspan="2">组三</th><th rowspan="2">组六</th><th colspan="10">0-9综合分布</th><th colspan="10">跨度走势及遗漏统计</th><th rowspan="2" class="td-last">和值</th></tr><tr class="tr-first"><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th></tr><tr>';
MONI_SELECT = '<tr><td colspan="3" rowspan="5" class="group2-3 td-segment-r" style="color:#000000;">模拟选号</td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td><td class="group2-3 td-segment-r"></td></tr>'+

				'<tr><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td><td class="group2-3 td-segment-r"></td></tr>'+

				'<tr><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td><td class="group2-3 td-segment-r"></td></tr>'+

				'<tr><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td><td class="group2-3 td-segment-r"></td></tr>'+

				'<tr><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">00</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">01</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">02</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">03</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">04</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">05</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">06</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">07</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">08</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">09</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">00</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">01</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">02</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">03</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">04</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">05</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">06</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">07</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">08</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">09</a></td><td class="group2-3 td-segment-r"></td></tr>';

DATA_HASH = new Hash();// 历史开奖数据
//百位
CURLOST_B_HASH = new Hash();
MAXMISS_B_HASH = new Hash();
AVEMISS_B_HASH = new Hash();
CURPANO_B_HASH = new Hash();
MAXPANO_B_HASH = new Hash();
LPANO_B_HASH = new Hash();
//个位
CURLOST_G_HASH = new Hash();
MAXMISS_G_HASH = new Hash();
AVEMISS_G_HASH = new Hash();
CURPANO_G_HASH = new Hash();
MAXPANO_G_HASH = new Hash();
LPANO_G_HASH = new Hash();

//十位
CURLOST_S_HASH = new Hash();
MAXMISS_S_HASH = new Hash();
AVEMISS_S_HASH = new Hash();
CURPANO_S_HASH = new Hash();
MAXPANO_S_HASH = new Hash();
LPANO_S_HASH = new Hash();

//豹子
BAOZI = 0;
//组三
ZUSAN = 0;
//组六
ZULIU = 0;
//综合
ZH_HASH = new Hash();
MAXMISS_ZH_HASH = new Hash();
CURLOST_ZH_HASH = new Hash();
CURPANO_ZH_HASH = new Hash();
MAXPANO_ZH_HASH = new Hash();
LPANO_ZH_HASH = new Hash();

//跨度
KD_HASH = new Hash();
MAXMISS_KD_HASH = new Hash();
CURLOST_KD_HASH = new Hash();
CURPANO_KD_HASH = new Hash();
MAXPANO_KD_HASH = new Hash();
LPANO_KD_HASH = new Hash();


function getDrawedData() {
	return dataSsc;
}
/**
 * 初始化DATA_HASH,将历史开奖数据转为Hash存储，方便访问
 */
function initDataHash(start,end) {
	var dataObj = dataSsc;
	var BODY = "";
	var html = "";
	var index = 0;
	initHash();
	for ( var issue in dataObj) {
		if(index<end&&index>=start){
			DATA_HASH.set(issue, genResult(dataObj[issue]));
			BODY+=buildGSItem(issue,DATA_HASH.get(issue));
		}
		index++;
	}
	var CURPANO_Html = calCurPano();
	var CURLOST_Html = calCurLost();
	var AVGLOST_Html = calAvgLost();
	var MAX_Html = calMaxLost();
	html = TAB_HEADER+BODY+MONI_SELECT+CURPANO_Html+CURLOST_Html+AVGLOST_Html+MAX_Html;
	$("ga").innerHTML = html;
	drawLine();			

}

/**
 * 查询遗漏数据
 */
function searchDisplay() {
	$('size_div').getElements('input[type="button"]').each(function(buttonEl) {
		buttonEl.className = 'btgray';
	});
	var startIssue = document.getElementById('startIssueNo').value;
	var endIssue = document.getElementById('endIssueNo').value;
	displayMissOfSearch(startIssue, endIssue);
}
function chgDisplay(end){
	initDataHash(0,end);
}


/**
 * 显示遗漏数据
 * 
 * @param startIssue - (<em>string</em>) 开始期号
 * @param endIssue - (<em>string</em>) 结束期号
 */
function displayMissOfSearch(startIssue, endIssue) {
	var issueArr = DATA_HASH.getKeys();
	if (issueArr == null || issueArr.length == 0){
		return;
	}
		
	var checkFn;
	var searchMaxSize = getMaxSize();
	if (isBlank(startIssue) && isBlank(endIssue)) {
		alert('请输入查询条件.');
		return;
	} else if (isBlank(startIssue) && !isBlank(endIssue)) {
		var endIndex = endIssue.replace("-","");
		var startIndex = issueArr[0];
		var endIussue = issueArr[issueArr.length-1];
		if(endIndex.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if(endIndex<startIndex||endIndex>endIussue){
			alert('[' + endIssue + ']不在可供查询的范围内.');
			return;
		}

		var index = issueArr.indexOf(endIndex);
		initDataHash(0,index+1);
		return ;
	} else if (!isBlank(startIssue) && isBlank(endIssue)) {
		var start = startIssue.replace("-","");
		var startIssueNo = issueArr[0];
		var endIussue = issueArr[issueArr.length-1];
		if(start.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if (start < startIssueNo||start>endIussue) {
			alert('[' + startIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		var endIndex = issueArr.length;
		var startIndex = issueArr.indexOf(start);
		initDataHash(startIndex,endIndex);
	} else {
		var start = startIssue.replace("-","");
		var end = endIssue.replace("-","");

		var startIndex = issueArr.indexOf(start);
		if(start.length<11||end.length<11){
			alert("输入格式有误！查询格式为:20110701-001");
			return ;
		}
		if (startIndex < 0) {
			alert('[' + startIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		var endIndex = issueArr.indexOf(end)+1;
		if (endIndex < 0) {
			alert('[' + endIssue + ']不在可供查询的范围内.查询格式为:20110701-001');
			return;
		}
		if (endIndex - startIndex > issueArr.length) {
			alert('最大只允许查询' + issueArr.length + '期.');
			return;
		} else if (startIndex > endIndex) {
			alert('查询条件有误.');
			return;
		}
		initDataHash(startIndex,endIndex);
	}
	
}

/**
 * 获取最大遗漏条数
 * @returns
 */
function getMaxSize(){
	return 200;
}
/**
 * 构建当前出现次数HTML代码
 */
function calCurPano(){
	var CURPANO_Html = '<tr class="drop"><td colspan="3" class="td-first">出现次数</td><td>'+CURPANO_B_HASH.get("00")+'</td><td>'+CURPANO_B_HASH.get("01")+'</td><td>'+CURPANO_B_HASH.get("02")+'</td><td>'+CURPANO_B_HASH.get("03")+'</td><td>'+CURPANO_B_HASH.get("04")+'</td><td>'+CURPANO_B_HASH.get("05")+'</td><td>'+CURPANO_B_HASH.get("06")+'</td><td><span class="lger">'+CURPANO_B_HASH.get("07")+'</span></td><td>'+CURPANO_B_HASH.get("08")+'</td><td class="td-segment-r">'+CURPANO_B_HASH.get("09")+'</td><td>'+CURPANO_S_HASH.get("00")+'</td><td>'+CURPANO_S_HASH.get("01")+'</td><td>'+CURPANO_S_HASH.get("02")+'</td><td>'+CURPANO_S_HASH.get("03")+'</td><td>'+CURPANO_S_HASH.get("04")+'</td><td>'+CURPANO_S_HASH.get("05")+'</td><td>'+CURPANO_S_HASH.get("06")+'</td><td><span class="lger">'+CURPANO_S_HASH.get("07")+'</span></td><td>'+CURPANO_S_HASH.get("08")+'</td><td class="td-segment-r">'+CURPANO_S_HASH.get("09")+'</td><td>'+CURPANO_G_HASH.get("00")+'</td><td>'+CURPANO_G_HASH.get("01")+'</td><td>'+CURPANO_G_HASH.get("02")+'</td><td>'+CURPANO_G_HASH.get("03")+'</td><td>'+CURPANO_G_HASH.get("04")+'</td><td>'+CURPANO_G_HASH.get("05")+'</td><td>'+CURPANO_G_HASH.get("06")+'</td><td>'+CURPANO_G_HASH.get("07")+'</td><td>'+CURPANO_G_HASH.get("08")+'</td><td class="td-segment-r"><span class="lger">'+CURPANO_G_HASH.get("09")+'</span></td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td>'+CURPANO_ZH_HASH.get("00")+'</td><td>'+CURPANO_ZH_HASH.get("01")+'</td><td>'+CURPANO_ZH_HASH.get("02")+'</td><td>'+CURPANO_ZH_HASH.get("03")+'</td><td>'+CURPANO_ZH_HASH.get("04")+'</td><td>'+CURPANO_ZH_HASH.get("05")+'</td><td>'+CURPANO_ZH_HASH.get("06")+'</td><td>'+CURPANO_ZH_HASH.get("07")+'</td><td>'+CURPANO_ZH_HASH.get("08")+'</td><td class="td-segment-r"><span class="lger">'+CURPANO_ZH_HASH.get("09")+'</span></td><td>'+CURPANO_KD_HASH.get("00")+'</td><td>'+CURPANO_KD_HASH.get("01")+'</td><td>'+CURPANO_KD_HASH.get("02")+'</td><td>'+CURPANO_KD_HASH.get("03")+'</td><td>'+CURPANO_KD_HASH.get("04")+'</td><td>'+CURPANO_KD_HASH.get("05")+'</td><td>'+CURPANO_KD_HASH.get("06")+'</td><td>'+CURPANO_KD_HASH.get("07")+'</td><td><span class="lger">'+CURPANO_KD_HASH.get("08")+'</span></td><td class="td-segment-r">'+CURPANO_KD_HASH.get("09")+'</td><td class="td-last"></td></tr>';
	return CURPANO_Html;
}
/**
 * 构建当前遗漏HTML代码
 */
function calCurLost(){
	var CURLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">当前遗漏</td><td>'+CURLOST_B_HASH.get("00")+'</td><td>'+CURLOST_B_HASH.get("01")+'</td><td>'+CURLOST_B_HASH.get("02")+'</td><td>'+CURLOST_B_HASH.get("03")+'</td><td>'+CURLOST_B_HASH.get("04")+'</td><td>'+CURLOST_B_HASH.get("05")+'</td><td><span class="lger">'+CURLOST_B_HASH.get("06")+'</span></td><td>'+CURLOST_B_HASH.get("07")+'</td><td class="td-segment-r">'+CURLOST_B_HASH.get("08")+'</td><td>'+CURLOST_B_HASH.get("09")+'</td><td>'+CURLOST_S_HASH.get("00")+'</td><td>'+CURLOST_S_HASH.get("01")+'</td><td>'+CURLOST_S_HASH.get("02")+'</td><td>'+CURLOST_S_HASH.get("03")+'</td><td>'+CURLOST_S_HASH.get("04")+'</td><td>'+CURLOST_S_HASH.get("05")+'</td><td><span class="lger">'+CURLOST_S_HASH.get("06")+'</span></td><td>'+CURLOST_S_HASH.get("07")+'</td><td class="td-segment-r">'+CURLOST_S_HASH.get("08")+'</td><td>'+CURLOST_S_HASH.get("09")+'</td><td>'+CURLOST_G_HASH.get("00")+'</td><td>'+CURLOST_G_HASH.get("01")+'</td><td>'+CURLOST_G_HASH.get("02")+'</td><td>'+CURLOST_G_HASH.get("03")+'</td><td>'+CURLOST_G_HASH.get("04")+'</td><td>'+CURLOST_G_HASH.get("05")+'</td><td>'+CURLOST_G_HASH.get("06")+'</td><td>'+CURLOST_G_HASH.get("07")+'</td><td class="td-segment-r"><span class="lger">'+CURLOST_G_HASH.get("08")+'</span></td><td class="td-segment-r"><span class="lger">'+CURLOST_G_HASH.get("09")+'</span></td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td>'+CURLOST_ZH_HASH.get("00")+'</td><td>'+CURLOST_ZH_HASH.get("01")+'</td><td>'+CURLOST_ZH_HASH.get("02")+'</td><td>'+CURLOST_ZH_HASH.get("03")+'</td><td>'+CURLOST_ZH_HASH.get("04")+'</td><td>'+CURLOST_ZH_HASH.get("05")+'</td><td>'+CURLOST_ZH_HASH.get("06")+'</td><td>'+CURLOST_ZH_HASH.get("07")+'</td><td>'+CURLOST_ZH_HASH.get("08")+'</td><td class="td-segment-r"><span class="lger">'+CURLOST_ZH_HASH.get("09")+'</span></td><td>'+CURLOST_KD_HASH.get("00")+'</td><td>'+CURLOST_KD_HASH.get("01")+'</td><td>'+CURLOST_KD_HASH.get("02")+'</td><td>'+CURLOST_KD_HASH.get("03")+'</td><td>'+CURLOST_KD_HASH.get("04")+'</td><td>'+CURLOST_KD_HASH.get("05")+'</td><td>'+CURLOST_KD_HASH.get("06")+'</td><td>'+CURLOST_KD_HASH.get("07")+'</td><td><span class="lger">'+CURLOST_KD_HASH.get("08")+'</span></td><td class="td-segment-r">'+CURLOST_KD_HASH.get("09")+'</td><td class="td-last"></td></tr>';
	return CURLOST_Html;
}
/**
 * 构建最大连出HTML代码
 */
function calMaxLost(){
	var MAX_Html = '<tr class="histogram"><td colspan="3" class="td-first td-first-1">最大连出</td><td>'+MAXPANO_B_HASH.get("00")+'<i style="height:'+MAXPANO_B_HASH.get("00")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_B_HASH.get("01")+'</span><i style="height:'+MAXPANO_B_HASH.get("01")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("02")+'<i style="height:'+MAXPANO_B_HASH.get("02")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("03")+'<i style="height:'+MAXPANO_B_HASH.get("03")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_B_HASH.get("04")+'</span><i style="height:'+MAXPANO_B_HASH.get("04")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("05")+'<i style="height:'+MAXPANO_B_HASH.get("05")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("06")+'<i style="height:'+MAXPANO_B_HASH.get("06")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("07")+'<i style="height:'+MAXPANO_B_HASH.get("07")+'px"><em></em></i></td><td>'+MAXPANO_B_HASH.get("08")+'<i style="height:'+MAXPANO_B_HASH.get("08")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_B_HASH.get("09")+'</span><i style="height:'+MAXPANO_B_HASH.get("09")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("00")+'</span><i style="height:'+MAXPANO_S_HASH.get("00")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("01")+'</span><i style="height:'+MAXPANO_S_HASH.get("01")+'px"><em></em></i></td><td>'+MAXPANO_S_HASH.get("02")+'<i style="height:'+MAXPANO_S_HASH.get("02")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("03")+'</span><i style="height:'+MAXPANO_S_HASH.get("03")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("04")+'</span><i style="height:'+MAXPANO_S_HASH.get("04")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("05")+'</span><i style="height:'+MAXPANO_S_HASH.get("05")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_S_HASH.get("06")+'</span><i style="height:'+MAXPANO_S_HASH.get("06")+'px"><em></em></i></td><td>'+MAXPANO_S_HASH.get("07")+'<i style="height:'+MAXPANO_S_HASH.get("07")+'px"><em></em></i></td><td>'+MAXPANO_S_HASH.get("08")+'<i style="height:'+MAXPANO_S_HASH.get("08")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_S_HASH.get("09")+'</span><i style="height:'+MAXPANO_S_HASH.get("09")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("00")+'<i style="height:'+MAXPANO_G_HASH.get("00")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_G_HASH.get("01")+'</span><i style="height:'+MAXPANO_G_HASH.get("01")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("02")+'<i style="height:'+MAXPANO_G_HASH.get("02")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("03")+'<i style="height:'+MAXPANO_G_HASH.get("03")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_G_HASH.get("04")+'</span><i style="height:'+MAXPANO_G_HASH.get("04")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("05")+'<i style="height:'+MAXPANO_G_HASH.get("05")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("06")+'<i style="height:'+MAXPANO_G_HASH.get("06")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("07")+'<i style="height:'+MAXPANO_G_HASH.get("07")+'px"><em></em></i></td><td>'+MAXPANO_G_HASH.get("08")+'<i style="height:'+MAXPANO_G_HASH.get("08")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_G_HASH.get("09")+'</span><i style="height:'+MAXPANO_G_HASH.get("09")+'px"><em></em></i></td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td>'+MAXPANO_ZH_HASH.get("00")+'<i style="height:'+MAXPANO_ZH_HASH.get("00")+'px"><em></em></i></td><td>'+MAXPANO_ZH_HASH.get("01")+'<i style="height:'+MAXPANO_ZH_HASH.get("01")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_ZH_HASH.get("02")+'</span><i style="height:'+MAXPANO_ZH_HASH.get("02")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_ZH_HASH.get("03")+'</span><i style="height:'+MAXPANO_ZH_HASH.get("03")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_ZH_HASH.get("04")+'</span><i style="height:'+MAXPANO_ZH_HASH.get("04")+'px"><em></em></i></td><td>'+MAXPANO_ZH_HASH.get("05")+'<i style="height:'+MAXPANO_ZH_HASH.get("05")+'px"><em></em></i></td><td>'+MAXPANO_ZH_HASH.get("06")+'<i style="height:'+MAXPANO_ZH_HASH.get("06")+'px"><em></em></i></td><td>'+MAXPANO_ZH_HASH.get("07")+'<i style="height:'+MAXPANO_ZH_HASH.get("07")+'px"><em></em></i></td><td>'+MAXPANO_ZH_HASH.get("08")+'<i style="height:'+MAXPANO_ZH_HASH.get("08")+'px"><em></em></i></td><td class="td-segment-r"><span class="lger">'+MAXPANO_ZH_HASH.get("09")+'</span><i style="height:'+MAXPANO_ZH_HASH.get("09")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("00")+'<i style="height:'+MAXPANO_KD_HASH.get("00")+'px"><em></em></i></td><td><span class="lger">'+MAXPANO_KD_HASH.get("01")+'</span><i style="height:'+MAXPANO_KD_HASH.get("01")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("02")+'<i style="height:'+MAXPANO_KD_HASH.get("02")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("03")+'<i style="height:'+MAXPANO_KD_HASH.get("03")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("04")+'<i style="height:'+MAXPANO_KD_HASH.get("04")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("05")+'<i style="height:'+MAXPANO_KD_HASH.get("05")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("06")+'<i style="height:'+MAXPANO_KD_HASH.get("06")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("07")+'<i style="height:'+MAXPANO_KD_HASH.get("07")+'px"><em></em></i></td><td>'+MAXPANO_KD_HASH.get("08")+'<i style="height:'+MAXPANO_KD_HASH.get("08")+'px"><em></em></i></td><td class="td-segment-r">'+MAXPANO_KD_HASH.get("09")+'<i style="height:'+MAXPANO_KD_HASH.get("09")+'px"><em></em></i></td><td class="td-last"></td></tr></table>';
	return MAX_Html;
}
/**
 * 构建平均遗漏HTML代码
 */
function calAvgLost(){
	//百位
	var bavg0 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("00").toInt())/(CURPANO_B_HASH.get("00").toInt()+1));
	var bavg1 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("01").toInt())/(CURPANO_B_HASH.get("01").toInt()+1));
	var bavg2 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("02").toInt())/(CURPANO_B_HASH.get("02").toInt()+1));
	var bavg3 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("03").toInt())/(CURPANO_B_HASH.get("03").toInt()+1));
	var bavg4 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("04").toInt())/(CURPANO_B_HASH.get("04").toInt()+1));
	var bavg5 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("05").toInt())/(CURPANO_B_HASH.get("05").toInt()+1));
	var bavg6 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("06").toInt())/(CURPANO_B_HASH.get("06").toInt()+1));
	var bavg7 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("07").toInt())/(CURPANO_B_HASH.get("07").toInt()+1));
	var bavg8 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("08").toInt())/(CURPANO_B_HASH.get("08").toInt()+1));
	var bavg9 = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get("09").toInt())/(CURPANO_B_HASH.get("09").toInt()+1));
	//十位
	var avg0 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("00").toInt())/(CURPANO_S_HASH.get("00").toInt()+1));
	var avg1 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("01").toInt())/(CURPANO_S_HASH.get("01").toInt()+1));
	var avg2 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("02").toInt())/(CURPANO_S_HASH.get("02").toInt()+1));
	var avg3 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("03").toInt())/(CURPANO_S_HASH.get("03").toInt()+1));
	var avg4 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("04").toInt())/(CURPANO_S_HASH.get("04").toInt()+1));
	var avg5 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("05").toInt())/(CURPANO_S_HASH.get("05").toInt()+1));
	var avg6 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("06").toInt())/(CURPANO_S_HASH.get("06").toInt()+1));
	var avg7 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("07").toInt())/(CURPANO_S_HASH.get("07").toInt()+1));
	var avg8 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("08").toInt())/(CURPANO_S_HASH.get("08").toInt()+1));
	var avg9 = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get("09").toInt())/(CURPANO_S_HASH.get("09").toInt()+1));
	//个位
	var gavg0 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("00").toInt())/(CURPANO_G_HASH.get("00").toInt()+1));
	var gavg1 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("01").toInt())/(CURPANO_G_HASH.get("01").toInt()+1));
	var gavg2 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("02").toInt())/(CURPANO_G_HASH.get("02").toInt()+1));
	var gavg3 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("03").toInt())/(CURPANO_G_HASH.get("03").toInt()+1));
	var gavg4 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("04").toInt())/(CURPANO_G_HASH.get("04").toInt()+1));
	var gavg5 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("05").toInt())/(CURPANO_G_HASH.get("05").toInt()+1));
	var gavg6 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("06").toInt())/(CURPANO_G_HASH.get("06").toInt()+1));
	var gavg7 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("07").toInt())/(CURPANO_G_HASH.get("07").toInt()+1));
	var gavg8 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("08").toInt())/(CURPANO_G_HASH.get("08").toInt()+1));
	var gavg9 = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get("09").toInt())/(CURPANO_G_HASH.get("09").toInt()+1));
	
	//综合
	var zavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("00").toInt())/(CURPANO_ZH_HASH.get("00").toInt()+1)));
	var zavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("01").toInt())/(CURPANO_ZH_HASH.get("01").toInt()+1)));
	var zavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("02").toInt())/(CURPANO_ZH_HASH.get("02").toInt()+1)));
	var zavg3 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("03").toInt())/(CURPANO_ZH_HASH.get("03").toInt()+1)));
	var zavg4 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("04").toInt())/(CURPANO_ZH_HASH.get("04").toInt()+1)));
	var zavg5 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("05").toInt())/(CURPANO_ZH_HASH.get("05").toInt()+1)));
	var zavg6 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("06").toInt())/(CURPANO_ZH_HASH.get("06").toInt()+1)));
	var zavg7 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("07").toInt())/(CURPANO_ZH_HASH.get("07").toInt()+1)));
	var zavg8 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("08").toInt())/(CURPANO_ZH_HASH.get("08").toInt()+1)));
	var zavg9 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_ZH_HASH.get("09").toInt())/(CURPANO_ZH_HASH.get("09").toInt()+1)));
	//跨度
	var kavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("00").toInt())/(CURPANO_KD_HASH.get("00").toInt()+1)));
	var kavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("01").toInt())/(CURPANO_KD_HASH.get("01").toInt()+1)));
	var kavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("02").toInt())/(CURPANO_KD_HASH.get("02").toInt()+1)));
	var kavg3 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("03").toInt())/(CURPANO_KD_HASH.get("03").toInt()+1)));
	var kavg4 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("04").toInt())/(CURPANO_KD_HASH.get("04").toInt()+1)));
	var kavg5 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("05").toInt())/(CURPANO_KD_HASH.get("05").toInt()+1)));
	var kavg6 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("06").toInt())/(CURPANO_KD_HASH.get("06").toInt()+1)));
	var kavg7 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("07").toInt())/(CURPANO_KD_HASH.get("07").toInt()+1)));
	var kavg8 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("08").toInt())/(CURPANO_KD_HASH.get("08").toInt()+1)));
	var kavg9 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_KD_HASH.get("09").toInt())/(CURPANO_KD_HASH.get("09").toInt()+1)));
	
	var AVGLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">平均遗漏</td><td>'+bavg0+'</td><td>'+bavg1+'</td><td>'+bavg2+'</td><td>'+bavg3+'</td><td>'+bavg4+'</td><td>'+bavg5+'</td><td><span class="lger">'+bavg6+'</span></td><td>'+bavg7+'</td><td class="td-segment-r">'+bavg8+'</td><td>'+bavg9+'</td><td>'+avg0+'</td><td>'+avg1+'</td><td>'+avg2+'</td><td>'+avg3+'</td><td>'+avg4+'</td><td>'+avg5+'</td><td>'+avg6+'</td><td>'+avg7+'</td><td class="td-segment-r"><span class="lger">'+avg8+'</span></td><td class="td-segment-r"><span class="lger">'+avg9+'</span></td><td>'+gavg0+'</td><td>'+gavg1+'</td><td>'+gavg2+'</td><td>'+gavg3+'</td><td>'+gavg4+'</td><td>'+gavg5+'</td><td><span class="lger">'+gavg6+'</span></td><td>'+gavg7+'</td><td class="td-segment-r">'+gavg8+'</td><td>'+gavg9+'</td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td class="td-segment-r">&nbsp;</td><td>'+zavg0+'</td><td>'+zavg1+'</td><td>'+zavg2+'</td><td>'+zavg3+'</td><td>'+zavg4+'</td><td>'+zavg5+'</td><td>'+zavg6+'</td><td>'+zavg7+'</td><td>'+zavg8+'</td><td class="td-segment-r"><span class="lger">'+zavg9+'</span></td><td>'+kavg0+'</td><td>'+kavg1+'</td><td>'+kavg2+'</td><td>'+kavg3+'</td><td>'+kavg4+'</td><td>'+kavg5+'</td><td>'+kavg6+'</td><td>'+kavg7+'</td><td><span class="lger">'+kavg8+'</span></td><td class="td-segment-r">'+kavg9+'</td><td class="td-last"></td></tr>';
	return AVGLOST_Html;
}


/**
 * 生成开奖结果对象
 * 
 * @param numStr - (<em>string</em>) 开奖结果字符串
 * @returns {object} - 开奖结果对象
 */
function genResult(numStr) {
	var numArr = numStr.split(/[^\d]+/);
	var result = {
		nums : [ numArr[0], numArr[1], numArr[2], numArr[3], numArr[4] ],
		sum : 0
	};
	result.sum += numArr[3].toInt()+numArr[4].toInt();
	return result;
}

/**
 * 初始化HASH数据
 */
function initHash(){
	DUILOST = 0;
	HEZHI = 0;
	DATA_HASH = new Hash();
	NUMS.each(function(ball){
		CURLOST_G_HASH.set(ball,0);
		MAXMISS_G_HASH.set(ball,0);
		AVEMISS_G_HASH.set(ball,0);
		CURPANO_G_HASH.set(ball,0);
		MAXPANO_G_HASH.set(ball,0);
		LPANO_G_HASH.set(ball,0);

		CURLOST_S_HASH.set(ball,0);
		MAXMISS_S_HASH.set(ball,0);
		AVEMISS_S_HASH.set(ball,0);
		CURPANO_S_HASH.set(ball,0);		
		MAXPANO_S_HASH.set(ball,0);
		LPANO_S_HASH.set(ball,0);

		CURLOST_B_HASH.set(ball,0);
		MAXMISS_B_HASH.set(ball,0);
		AVEMISS_B_HASH.set(ball,0);
		CURPANO_B_HASH.set(ball,0);
		MAXPANO_B_HASH.set(ball,0);
		LPANO_B_HASH.set(ball,0);
		
		ZH_HASH.set(ball,0);
		MAXMISS_ZH_HASH.set(ball,0);
		CURLOST_ZH_HASH.set(ball,0);
		CURPANO_ZH_HASH.set(ball,0);
		MAXPANO_ZH_HASH.set(ball,0);
		LPANO_ZH_HASH.set(ball,0);

		KD_HASH.set(ball,0);
		MAXMISS_KD_HASH.set(ball,0);
		CURLOST_KD_HASH.set(ball,0);
		CURPANO_KD_HASH.set(ball,0);
		MAXPANO_KD_HASH.set(ball,0);
		LPANO_KD_HASH.set(ball,0);

	});	
	BAOZI = 0;
	ZUSAN = 0;
	ZULIU = 0;
}

/**
 * 计算一期个位十位遗漏数据
 * @param issue
 * @param result
 * @returns
 */
function buildGSItem(issue,result){
	var bai = result.nums[2];
	var shi = result.nums[3];
	var ge = result.nums[4];
	
	CURPANO_G_HASH.set(ge,CURPANO_G_HASH.get(ge)+1);
	LPANO_G_HASH.set(ge,LPANO_G_HASH.get(ge)+1);
	NUMS.each(function(ball){
		if(ball.toInt()!=ge.toInt()){
			CURLOST_G_HASH.set(ball,CURLOST_G_HASH.get(ball)+1);
			LPANO_G_HASH.set(ball,0);
		}
	}); 

	if(MAXMISS_G_HASH.get(ge)<CURLOST_G_HASH.get(ge)){
		MAXMISS_G_HASH.set(ge,CURLOST_G_HASH.get(ge));
	}
	if(MAXPANO_G_HASH.get(ge)<LPANO_G_HASH.get(ge)){
		MAXPANO_G_HASH.set(ge,LPANO_G_HASH.get(ge));
	}
	CURLOST_G_HASH.set(ge,0);

	CURPANO_S_HASH.set(shi,CURPANO_S_HASH.get(shi)+1);
	LPANO_S_HASH.set(shi,LPANO_S_HASH.get(shi)+1);

	NUMS.each(function(ball){			
		if(ball.toInt()!=shi.toInt()){
			CURLOST_S_HASH.set(ball,CURLOST_S_HASH.get(ball)+1);
			LPANO_S_HASH.set(ball,0);
		}
	}); 
	if(MAXMISS_S_HASH.get(shi)<CURLOST_S_HASH.get(shi)){
		MAXMISS_S_HASH.set(shi,CURLOST_S_HASH.get(shi));
	}
	if(MAXPANO_S_HASH.get(shi)<LPANO_S_HASH.get(shi)){
		MAXPANO_S_HASH.set(shi,LPANO_S_HASH.get(shi));
	}
	CURLOST_S_HASH.set(shi,0);
	
	CURPANO_B_HASH.set(bai,CURPANO_B_HASH.get(bai)+1);
	LPANO_B_HASH.set(bai,LPANO_B_HASH.get(bai)+1);

	NUMS.each(function(ball){			
		if(ball.toInt()!=bai.toInt()){
			CURLOST_B_HASH.set(ball,CURLOST_B_HASH.get(ball)+1);
			LPANO_B_HASH.set(ball,0);
		}
	}); 
	if(MAXMISS_B_HASH.get(bai)<CURLOST_B_HASH.get(bai)){
		MAXMISS_B_HASH.set(bai,CURLOST_B_HASH.get(bai));
	}
	if(MAXPANO_B_HASH.get(bai)<LPANO_B_HASH.get(bai)){
		MAXPANO_B_HASH.set(bai,LPANO_B_HASH.get(bai));
	}
	CURLOST_B_HASH.set(bai,0);
	
	
	//综合
	CURPANO_ZH_HASH.set(ge,CURPANO_ZH_HASH.get(ge)+1);
	CURPANO_ZH_HASH.set(shi,CURPANO_ZH_HASH.get(shi)+1);
	CURPANO_ZH_HASH.set(bai,CURPANO_ZH_HASH.get(bai)+1);

	LPANO_ZH_HASH.set(ge,LPANO_ZH_HASH.get(ge)+1);
	LPANO_ZH_HASH.set(shi,LPANO_ZH_HASH.get(shi)+1);
	LPANO_ZH_HASH.set(bai,LPANO_ZH_HASH.get(bai)+1);
	
	ZH_HASH.set(bai,0);
	ZH_HASH.set(shi,0);
	ZH_HASH.set(ge,0);
	
	NUMS.each(function(ball){			
		if(ball.toInt()!=shi.toInt()&&ball.toInt()!=ge.toInt()&&ball.toInt()!=bai.toInt()){
			ZH_HASH.set(ball,ZH_HASH.get(ball)+1);
			CURLOST_ZH_HASH.set(ball,CURLOST_ZH_HASH.get(ball)+1);
			LPANO_ZH_HASH.set(ball,0);
		}
	});
	if(MAXPANO_ZH_HASH.get(ge)<LPANO_ZH_HASH.get(ge)){
		MAXPANO_ZH_HASH.set(ge,LPANO_ZH_HASH.get(ge));
	}
	if(MAXPANO_ZH_HASH.get(shi)<LPANO_ZH_HASH.get(shi)){
		MAXPANO_ZH_HASH.set(shi,LPANO_ZH_HASH.get(shi));
	}
	if(MAXPANO_ZH_HASH.get(bai)<LPANO_ZH_HASH.get(bai)){
		MAXPANO_ZH_HASH.set(bai,LPANO_ZH_HASH.get(bai));
	}
	if(MAXMISS_ZH_HASH.get(ge)<CURLOST_ZH_HASH.get(ge)){
		MAXMISS_ZH_HASH.set(ge,CURLOST_ZH_HASH.get(ge));
	}
	if(MAXMISS_ZH_HASH.get(shi)<CURLOST_ZH_HASH.get(shi)){
		MAXMISS_ZH_HASH.set(shi,CURLOST_ZH_HASH.get(shi));
	}
	if(MAXMISS_ZH_HASH.get(bai)<CURLOST_ZH_HASH.get(bai)){
		MAXMISS_ZH_HASH.set(bai,CURLOST_ZH_HASH.get(bai));
	}
	CURLOST_ZH_HASH.set(bai,0);
	CURLOST_ZH_HASH.set(shi,0);
	CURLOST_ZH_HASH.set(ge,0);
	//跨度
	var kd = 0;
	var kd1 = Math.abs(bai.toInt()-shi.toInt());
	var kd2 = Math.abs(shi.toInt()-ge.toInt());
	var kd3 = Math.abs(bai.toInt()-ge.toInt());
	if(kd1>kd2)kd=kd1;
	else kd=kd2;
	if(kd<kd3)kd=kd3;
	
	CURPANO_KD_HASH.set('0'+kd,CURPANO_ZH_HASH.get('0'+kd)+1);
	LPANO_KD_HASH.set('0'+kd,LPANO_KD_HASH.get('0'+kd)+1);
	NUMS.each(function(ball){			
		if(ball.toInt()!=kd){
			KD_HASH.set(ball,KD_HASH.get(ball)+1);
			CURLOST_KD_HASH.set(ball,CURLOST_KD_HASH.get(ball)+1);
			LPANO_KD_HASH.set(ball,0);
		}
	}); 
	if(MAXPANO_KD_HASH.get('0'+kd)<LPANO_KD_HASH.get('0'+kd)){
		MAXPANO_KD_HASH.set('0'+kd,LPANO_KD_HASH.get('0'+kd));
	}
	if(MAXMISS_KD_HASH.get('0'+kd)<CURLOST_KD_HASH.get('0'+kd)){
		MAXMISS_KD_HASH.set('0'+kd,CURLOST_KD_HASH.get('0'+kd));
	}
	KD_HASH.set('0'+kd,0);
	CURLOST_KD_HASH.set('0'+kd,0);
	//和值
	HEZHI = bai.toInt()+shi.toInt()+ge.toInt();
	//豹子
	if(bai.toInt()==shi.toInt()&&bai.toInt()==ge.toInt())BAOZI = 0;
	else BAOZI++;
	//组三
	if(BAOZI>0&&(bai.toInt()==shi.toInt()||bai.toInt()==ge.toInt()||shi.toInt()==ge.toInt()))ZUSAN = 0;	
	else ZUSAN++;
	//组六
	if(ZUSAN>0&&(bai.toInt()!=shi.toInt()&&bai.toInt()!=ge.toInt()&&shi.toInt()!=ge.toInt()))ZULIU = 0;
	else ZULIU++;
	
	html = buildMissItemHTML(issue,result);
	return html;
}

/**
 * 构建一期遗漏数据的HTML代码
 * 
 * @param issue - (<em>string</em>) 期号
 * @param missItem - (<em>object</em>) 遗漏数据
 * @returns {string} - 遗漏数据的HTML代码
 */
function buildMissItemHTML(issue, missItem) {
	var period = issue.substring(4,8)+'-'+issue.substring(8,issue.length);
	var z1 = missItem.nums[2].toInt()+""+missItem.nums[3].toInt()+""+missItem.nums[4].toInt();
	var z2 = missItem.nums[0].toInt()+""+missItem.nums[1].toInt()+"";
	var r = missItem.nums[0].toInt()+""+missItem.nums[1].toInt()+""+missItem.nums[2].toInt()+""+missItem.nums[3].toInt()+""+missItem.nums[4].toInt();
	var g0 = CURLOST_G_HASH.get("00")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">0</span>':CURLOST_G_HASH.get("00");
	var g1 = CURLOST_G_HASH.get("01")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">1</span>':CURLOST_G_HASH.get("01");
	var g2 = CURLOST_G_HASH.get("02")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">2</span>':CURLOST_G_HASH.get("02");
	var g3 = CURLOST_G_HASH.get("03")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">3</span>':CURLOST_G_HASH.get("03");
	var g4 = CURLOST_G_HASH.get("04")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">4</span>':CURLOST_G_HASH.get("04");
	var g5 = CURLOST_G_HASH.get("05")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">5</span>':CURLOST_G_HASH.get("05");
	var g6 = CURLOST_G_HASH.get("06")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">6</span>':CURLOST_G_HASH.get("06");
	var g7 = CURLOST_G_HASH.get("07")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">7</span>':CURLOST_G_HASH.get("07");
	var g8 = CURLOST_G_HASH.get("08")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">8</span>':CURLOST_G_HASH.get("08");
	var g9 = CURLOST_G_HASH.get("09")==0?'<span class="n-ssq" _cancas="cancas" _group="gewei">9</span>':CURLOST_G_HASH.get("09");

	var s0 = CURLOST_S_HASH.get("00")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">0</span>':CURLOST_S_HASH.get("00");
	var s1 = CURLOST_S_HASH.get("01")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">1</span>':CURLOST_S_HASH.get("01");
	var s2 = CURLOST_S_HASH.get("02")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">2</span>':CURLOST_S_HASH.get("02");
	var s3 = CURLOST_S_HASH.get("03")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">3</span>':CURLOST_S_HASH.get("03");
	var s4 = CURLOST_S_HASH.get("04")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">4</span>':CURLOST_S_HASH.get("04");
	var s5 = CURLOST_S_HASH.get("05")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">5</span>':CURLOST_S_HASH.get("05");
	var s6 = CURLOST_S_HASH.get("06")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">6</span>':CURLOST_S_HASH.get("06");
	var s7 = CURLOST_S_HASH.get("07")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">7</span>':CURLOST_S_HASH.get("07");
	var s8 = CURLOST_S_HASH.get("08")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">8</span>':CURLOST_S_HASH.get("08");
	var s9 = CURLOST_S_HASH.get("09")==0?'<span class="n-ssq" _cancas="cancas" _group="shiwei">9</span>':CURLOST_S_HASH.get("09");
	
	var b0 = CURLOST_B_HASH.get("00")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">0</span>':CURLOST_B_HASH.get("00");
	var b1 = CURLOST_B_HASH.get("01")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">1</span>':CURLOST_B_HASH.get("01");
	var b2 = CURLOST_B_HASH.get("02")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">2</span>':CURLOST_B_HASH.get("02");
	var b3 = CURLOST_B_HASH.get("03")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">3</span>':CURLOST_B_HASH.get("03");
	var b4 = CURLOST_B_HASH.get("04")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">4</span>':CURLOST_B_HASH.get("04");
	var b5 = CURLOST_B_HASH.get("05")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">5</span>':CURLOST_B_HASH.get("05");
	var b6 = CURLOST_B_HASH.get("06")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">6</span>':CURLOST_B_HASH.get("06");
	var b7 = CURLOST_B_HASH.get("07")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">7</span>':CURLOST_B_HASH.get("07");
	var b8 = CURLOST_B_HASH.get("08")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">8</span>':CURLOST_B_HASH.get("08");
	var b9 = CURLOST_B_HASH.get("09")==0?'<span class="n-ssq" _cancas="cancas" _group="baiwei">9</span>':CURLOST_B_HASH.get("09");
	
	var zh0 = ZH_HASH.get("00")==0?'<span class="n-ssq">0</span>':ZH_HASH.get("00");
	var zh1 = ZH_HASH.get("01")==0?'<span class="n-ssq">1</span>':ZH_HASH.get("01");
	var zh2 = ZH_HASH.get("02")==0?'<span class="n-ssq">2</span>':ZH_HASH.get("02");
	var zh3 = ZH_HASH.get("03")==0?'<span class="n-ssq">3</span>':ZH_HASH.get("03");
	var zh4 = ZH_HASH.get("04")==0?'<span class="n-ssq">4</span>':ZH_HASH.get("04");
	var zh5 = ZH_HASH.get("05")==0?'<span class="n-ssq">5</span>':ZH_HASH.get("05");
	var zh6 = ZH_HASH.get("06")==0?'<span class="n-ssq">6</span>':ZH_HASH.get("06");
	var zh7 = ZH_HASH.get("07")==0?'<span class="n-ssq">7</span>':ZH_HASH.get("07");
	var zh8 = ZH_HASH.get("08")==0?'<span class="n-ssq">8</span>':ZH_HASH.get("08");
	var zh9 = ZH_HASH.get("09")==0?'<span class="n-ssq">9</span>':ZH_HASH.get("09");
	
	var kd0 = KD_HASH.get("00")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">0</span>':KD_HASH.get("00");
	var kd1 = KD_HASH.get("01")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">1</span>':KD_HASH.get("01");
	var kd2 = KD_HASH.get("02")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">2</span>':KD_HASH.get("02");
	var kd3 = KD_HASH.get("03")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">3</span>':KD_HASH.get("03");
	var kd4 = KD_HASH.get("04")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">4</span>':KD_HASH.get("04");
	var kd5 = KD_HASH.get("05")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">5</span>':KD_HASH.get("05");
	var kd6 = KD_HASH.get("06")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">6</span>':KD_HASH.get("06");
	var kd7 = KD_HASH.get("07")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">7</span>':KD_HASH.get("07");
	var kd8 = KD_HASH.get("08")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">8</span>':KD_HASH.get("08");
	var kd9 = KD_HASH.get("09")==0?'<span class="n-xie" _cancas="cancas" _group="kuadu">9</span>':KD_HASH.get("09");
	
	var baozi = BAOZI==0?'<span class="n-jiu">豹子</span>':BAOZI;
	var zusan = ZUSAN==0?'<span class="n-xie">组三</span>':ZUSAN;
	var zuliu = ZULIU==0?'<span class="n-gu">组六</span>':ZULIU;

	var html = '<tr><td class="td-first" colspan="2" title="第' + period + '期开奖号码:'+r+'">'+period+'</td><td class="group1" title="第'+period+'期开奖号码:'+missItem.nums+'">'+z2+'<font color="red">'+z1+'</font></td>';
	var bHtml = '<td class="td-blue" style="cursor: pointer;">'+b0+'</td><td class="td-blue" style="cursor: pointer;">'+b1+'</td><td class="td-blue" style="cursor: pointer;">'+b2+'</td><td class="td-blue" style="cursor: pointer;">'+b3+'</td><td class="td-blue" style="cursor: pointer;">'+b4+'</td><td class="td-blue" style="cursor: pointer;">'+b5+'</td><td class="td-blue" style="cursor: pointer;">'+b6+'</td><td class="td-blue" style="cursor: pointer;">'+b7+'</td><td class="td-blue" style="cursor: pointer;">'+b8+'</td><td class="td-blue" style="cursor: pointer;">'+b9+'</td>';
	var sHtml = '<td class="" style="cursor: pointer;">'+s0+'</td><td class="" style="cursor: pointer;">'+s1+'</td><td class="" style="cursor: pointer;">'+s2+'</td><td class="" style="cursor: pointer;">'+s3+'</td><td class="" style="cursor: pointer;">'+s4+'</td><td class="" style="cursor: pointer;">'+s5+'</td><td class="" style="cursor: pointer;">'+s6+'</td><td class="" style="cursor: pointer;">'+s7+'</td><td class="" style="cursor: pointer;">'+s8+'</td><td class="" style="cursor: pointer;">'+s9+'</td>';
	var gHtml = '<td  class="td-blue" style="cursor: pointer;">'+g0+'</td><td class="td-blue" style="cursor: pointer;">'+g1+'</td><td class="td-blue" style="cursor: pointer;">'+g2+'</td><td class="td-blue" style="cursor: pointer;">'+g3+'</td><td class="td-blue" style="cursor: pointer;">'+g4+'</td><td class="td-blue" style="cursor: pointer;">'+g5+'</td><td class="td-blue" style="cursor: pointer;">'+g6+'</td><td class="td-blue" style="cursor: pointer;">'+g7+'</td><td class="td-blue" style="cursor: pointer;">'+g8+'</td><td class="td-blue" style="cursor: pointer;">'+g9+'</td>';
	var dHtml = '<td>'+baozi+'</td><td>'+zusan+'</td><td>'+zuliu+'</td>';
	
	var zHtml = '<td>'+zh0+'</td><td>'+zh1+'</td><td>'+zh2+'</td><td>'+zh3+'</td><td>'+zh4+'</td><td>'+zh5+'</td><td>'+zh6+'</td><td>'+zh7+'</td><td>'+zh8+'</td><td class=" td-segment-r">'+zh9+'</td>';
	var kdHtml = '<td>'+kd0+'</td><td>'+kd1+'</td><td>'+kd2+'</td><td>'+kd3+'</td><td>'+kd4+'</td><td>'+kd5+'</td><td>'+kd6+'</td><td>'+kd7+'</td><td>'+kd8+'</td><td class=" td-segment-r">'+kd9+'</td><td class="td-last">'+HEZHI+'</td></tr>';
		
	return html+bHtml+sHtml+gHtml+dHtml+zHtml+kdHtml;
}



// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	
	[ 'gewei','shiwei','baiwei','kuadu' ].each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="'+hashName+'"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
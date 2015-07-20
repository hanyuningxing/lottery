YNUMS = [ '0','1','2','3','4','5','6','7','8','9' ];

XT_NUMS = [ '000','001','002','010','011','012','020','021','022','100','101','102','110','111','112','120','121','122','200','201','202','210','211','212','220','221','222'];
GS_NUMS = [ '0','1','2' ];

TAB_HEADER = '<table class="trendCharts trendSSQ" id="tableChart" "="" cellspacing="0"><tbody><tr class="tr-first"><th colspan="2" rowspan="2" class="td-first" style="" title="点击排序" onclick="myChart.OrderByAsc=!myChart.OrderByAsc;myChart.ChangeChart();dline.againDrowLine();">期号</th><th rowspan="2">开奖<br>号码</th><th colspan="3">百位</th><th colspan="3">十位</th><th colspan="3">个位</th><th colspan="27">012路形态</th><th colspan="4">余0号码</th><th colspan="3">余1号码</th><th colspan="3">余2号码</th><th rowspan="2" class="td-last">012路比</th></tr><tr class="tr-first"><th>0</th><th>1</th><th>2</th><th>0</th><th>1</th><th>2</th><th>0</th><th>1</th><th>2</th><th>000</th><th>001</th><th>002</th><th>010</th><th>011</th><th>012</th><th>020</th><th>021</th><th>022</th><th>100</th><th>101</th><th>102</th><th>110</th><th>111</th><th>112</th><th>120</th><th>121</th><th>122</th><th>200</th><th>201</th><th>202</th><th>210</th><th>211</th><th>212</th><th>220</th><th>221</th><th>222</th><th>0</th><th>3</th><th>6</th><th>9</th><th>1</th><th>4</th><th>7</th><th>2</th><th>5</th><th>8</th></tr>';
MONI_SELECT = '<tr><td colspan="3" rowspan="5" class="group2-3 td-segment-r" style="color:#000000;">模拟选号</td>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">000</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">001</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">002</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">010</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">011</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">012</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">020</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">021</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">022</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">100</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">101</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">102</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">110</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">111</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">112</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">120</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">121</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">122</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">200</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">201</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">202</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">210</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">211</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">212</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">220</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">221</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">222</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">000</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">001</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">002</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">010</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">011</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">012</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">020</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">021</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">022</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">100</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">101</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">102</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">110</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">111</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">112</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">120</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">121</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">122</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">200</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">201</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">202</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">210</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">211</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">212</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">220</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">221</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">222</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">000</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">001</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">002</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">010</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">011</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">012</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">020</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">021</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">022</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">100</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">101</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">102</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">110</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">111</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">112</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">120</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">121</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">122</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">200</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">201</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">202</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">210</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">211</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">212</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">220</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">221</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">222</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">000</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">001</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">002</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">010</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">011</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">012</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">020</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">021</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">022</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">100</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">101</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">102</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">110</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">111</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">112</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">120</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">121</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">122</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">200</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">201</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">202</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">210</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">211</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">212</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">220</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">221</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">222</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>'+
				'<td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">1</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">0</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">2</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">000</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">001</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">002</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gball">010</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">011</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">012</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">020</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">021</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">022</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">100</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">101</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">102</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">110</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">111</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">112</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">120</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">121</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">122</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">200</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">201</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">202</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">210</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">211</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">212</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">220</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">221</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">222</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">0</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">3</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">6</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">9</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">1</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">4</a></td><td class="group2-3 td-segment-r"><a onclick="red_click(this);return false;" class="gball">7</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">2</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">5</a></td><td class="group2-3 td-segment-r"><a onclick="blue_click(this);return false;" class="gballblue">8</a></td>'+
				'<td class="group2-3 td-segment-r"></td></tr>';


DATA_HASH = new Hash();// 历史开奖数据
//个位
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

//形态
XT_HASH = new Hash();
MAXMISS_XT_HASH = new Hash();
CURLOST_XT_HASH = new Hash();
CURPANO_XT_HASH = new Hash();
MAXPANO_XT_HASH = new Hash();
LPANO_XT_HASH = new Hash();

//余0数
Y_HASH = new Hash();
MAXMISS_Y_HASH = new Hash();
CURLOST_Y_HASH = new Hash();
CURPANO_Y_HASH = new Hash();
MAXPANO_Y_HASH = new Hash();
LPANO_Y_HASH = new Hash();

var YSB = '';

function getMONIHTML(){
	MONI_SELECT+='<tr><td colspan="3" rowspan="5" class="group2-3 td-segment-r" style="color:#000000;">模拟选号</td>';
}

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
	var CURPANO_Html = '<tr class="drop"><td colspan="3" class="td-first">出现次数</td>';
	var baiHtml = '';
	var shiHtml = '';
	var geHtml = '';
	var xtHtml = '';
	var ysHtml = '';
	GS_NUMS.each(function(ball){
		baiHtml+='<td>'+CURPANO_B_HASH.get(ball)+'</td>';
		shiHtml+='<td>'+CURPANO_S_HASH.get(ball)+'</td>';
		geHtml+='<td>'+CURPANO_G_HASH.get(ball)+'</td>';
	});
	
	XT_NUMS.each(function(ball){
		xtHtml+='<td>'+CURPANO_XT_HASH.get(ball)+'</td>';
	});
	['0','3','6','9'].each(function(ball){
		ysHtml+='<td>'+CURPANO_Y_HASH.get(ball)+'</td>';
	});
	['1','4','7'].each(function(ball){
		ysHtml+='<td>'+CURPANO_Y_HASH.get(ball)+'</td>';
	});
	['2','5','8'].each(function(ball){
		ysHtml+='<td>'+CURPANO_Y_HASH.get(ball)+'</td>';
	});
	ysHtml+='</td><td class="td-last"></td></tr>';
	CURPANO_Html+=baiHtml+shiHtml+geHtml+xtHtml+ysHtml;
	return CURPANO_Html;
}
/**
 * 构建当前遗漏HTML代码
 */
function calCurLost(){
		var CURLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">当前遗漏</td>';
		var baiHtml = '';
		var shiHtml = '';
		var geHtml = '';
		var xtHtml = '';
		var ysHtml = '';
		GS_NUMS.each(function(ball){
			baiHtml+='<td>'+CURLOST_B_HASH.get(ball)+'</td>';
			shiHtml+='<td>'+CURLOST_S_HASH.get(ball)+'</td>';
			geHtml+='<td>'+CURLOST_G_HASH.get(ball)+'</td>';
		});
		
		XT_NUMS.each(function(ball){
			xtHtml+='<td>'+CURLOST_XT_HASH.get(ball)+'</td>';
		});
		['0','3','6','9'].each(function(ball){
			ysHtml+='<td>'+CURLOST_Y_HASH.get(ball)+'</td>';
		});
		['1','4','7'].each(function(ball){
			ysHtml+='<td>'+CURLOST_Y_HASH.get(ball)+'</td>';
		});
		['2','5','8'].each(function(ball){
			ysHtml+='<td>'+CURLOST_Y_HASH.get(ball)+'</td>';
		});
		ysHtml+='</td><td class="td-last"></td></tr>';
		CURLOST_Html+=baiHtml+shiHtml+geHtml+xtHtml+ysHtml;
		return CURLOST_Html;
}
/**
 * 构建最大连出HTML代码
 */
function calMaxLost(){	
	var MAX_Html = '<tr class="histogram"><td colspan="3" class="td-first td-first-1">最大连出</td>';
	var baiHtml = '';
	var shiHtml = '';
	var geHtml = '';
	var xtHtml = '';
	var ysHtml = '';
	GS_NUMS.each(function(ball){
		baiHtml+='<td>'+MAXPANO_B_HASH.get(ball)+'<i style="height:'+MAXPANO_B_HASH.get(ball)+'px"><em></em></i></td>';
		shiHtml+='<td>'+MAXPANO_S_HASH.get(ball)+'<i style="height:'+MAXPANO_S_HASH.get(ball)+'px"><em></em></i></td>';
		geHtml+='<td>'+MAXPANO_G_HASH.get(ball)+'<i style="height:'+MAXPANO_G_HASH.get(ball)+'px"><em></em></i></td>';
	});
	
	XT_NUMS.each(function(ball){
		xtHtml+='<td>'+MAXPANO_XT_HASH.get(ball)+'<i style="height:'+MAXPANO_XT_HASH.get(ball)+'px"><em></em></i></td>';
	});
	['0','3','6','9'].each(function(ball){
		ysHtml+='<td><span class="lger">'+MAXPANO_Y_HASH.get(ball)+'</span><i style="height:'+MAXPANO_Y_HASH.get(ball)+'px"><em></em></i></td>';
	});
	['1','4','7'].each(function(ball){
		ysHtml+='<td><span class="lger">'+MAXPANO_Y_HASH.get(ball)+'</span><i style="height:'+MAXPANO_Y_HASH.get(ball)+'px"><em></em></i></td>';
	});
	['2','5','8'].each(function(ball){
		ysHtml+='<td><span class="lger">'+MAXPANO_Y_HASH.get(ball)+'</span><i style="height:'+MAXPANO_Y_HASH.get(ball)+'px"><em></em></i></td>';
	});
	ysHtml+='</td><td class="td-last"></td></tr></table>';
	MAX_Html+=baiHtml+shiHtml+geHtml+xtHtml+ysHtml;
	return MAX_Html;
}
/**
 * 构建平均遗漏HTML代码
 */
function calAvgLost(){
	var AVGLOST_Html = '<tr class="drop"><td colspan="3" class="td-first">平均遗漏</td>';
	var bavgHtml = '';
	var savgHtml = '';
	var gavgHtml = '';
	['0','1','2'].each(function(ball){
		var bavg = Math.floor((DATA_HASH.getLength()-CURPANO_B_HASH.get(ball).toInt())/(CURPANO_B_HASH.get(ball).toInt()+1));
		var savg = Math.floor((DATA_HASH.getLength()-CURPANO_S_HASH.get(ball).toInt())/(CURPANO_S_HASH.get(ball).toInt()+1));
		var gavg = Math.floor((DATA_HASH.getLength()-CURPANO_G_HASH.get(ball).toInt())/(CURPANO_G_HASH.get(ball).toInt()+1));
		bavgHtml+='<td>'+bavg+'</td>';
		savgHtml+='<td>'+savg+'</td>';
		gavgHtml+='<td>'+gavg+'</td>';
	});
	AVGLOST_Html+=bavgHtml+savgHtml+gavgHtml;
	//012路形态
	XT_NUMS.each(function(ball){
		var xtavg=Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_XT_HASH.get(ball).toInt())/(CURPANO_XT_HASH.get(ball).toInt()+1)));
		AVGLOST_Html+='<td>'+xtavg+'</td>';
	});

	//余0号码
	['0','3','6','9'].each(function(ball){
		var yavg0 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get(ball).toInt())/(CURPANO_Y_HASH.get(ball).toInt()+1)));
		AVGLOST_Html+='<td>'+yavg0+'</td>';
	});
	//余1号码
	['1','4','7'].each(function(ball){
		var yavg1 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get(ball).toInt())/(CURPANO_Y_HASH.get(ball).toInt()+1)));
		AVGLOST_Html+='<td>'+yavg1+'</td>';
	});
	//余2号码
	['2','5','8'].each(function(ball){
		var yavg2 = Math.abs(Math.floor((DATA_HASH.getLength()-CURPANO_Y_HASH.get(ball).toInt())/(CURPANO_Y_HASH.get(ball).toInt()+1)));
		AVGLOST_Html+='<td>'+yavg2+'</td>';
	});
		
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
	DATA_HASH = new Hash();
	YNUMS.each(function(ball){
		Y_HASH.set(ball,0);
		MAXMISS_Y_HASH.set(ball,0);
		CURLOST_Y_HASH.set(ball,0);
		CURPANO_Y_HASH.set(ball,0);
		MAXPANO_Y_HASH.set(ball,0);		
		LPANO_Y_HASH.set(ball,0);
	});
	XT_NUMS.each(function(ball){
		XT_HASH.set(ball,0);
		
		MAXMISS_XT_HASH.set(ball,0);
		CURLOST_XT_HASH.set(ball,0);
		CURPANO_XT_HASH.set(ball,0);
		MAXPANO_XT_HASH.set(ball,0);
		LPANO_XT_HASH.set(ball,0);

	});
	GS_NUMS.each(function(ball){
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
	});
	YSB = '';
}

/**
 * 计算一期个位十位遗漏数据
 * @param issue
 * @param result
 * @returns
 */
function buildGSItem(issue,result){
	var bai = result.nums[2].toInt();
	var shi = result.nums[3].toInt();
	var ge = result.nums[4].toInt();
	
	//形态
	var xt = bai%3+""+shi%3+""+ge%3;
	CURPANO_XT_HASH.set(xt,CURPANO_XT_HASH.get(xt)+1);
	LPANO_XT_HASH.set(xt,LPANO_XT_HASH.get(xt)+1);
	XT_HASH.set(xt,0);
	
	XT_NUMS.each(function(ball){			
		if(ball!=xt){
			XT_HASH.set(ball,XT_HASH.get(ball)+1);
			CURLOST_XT_HASH.set(ball,CURLOST_XT_HASH.get(ball)+1);
			LPANO_XT_HASH.set(ball,0);
		}
	});
	if(MAXMISS_XT_HASH.get(xt)<CURLOST_XT_HASH.get(xt)){
		MAXMISS_XT_HASH.set(xt,CURLOST_XT_HASH.get(xt));
	}
	if(MAXPANO_XT_HASH.get(xt)<LPANO_XT_HASH.get(xt)){
		MAXPANO_XT_HASH.set(xt,LPANO_XT_HASH.get(xt));
	}
	CURLOST_XT_HASH.set(xt,0);
	//余数 十位个位
	calYS(bai,shi,ge);
	
	html = buildMissItemHTML(issue,result);
	return html;
}


function calYS(bai,shi,ge){
	var bys = bai%3;
	var sys = shi%3;
	var gys = ge%3;
	CURPANO_Y_HASH.set(ge,CURPANO_Y_HASH.get(ge)+1);
	CURPANO_Y_HASH.set(shi,CURPANO_Y_HASH.get(shi)+1);
	CURPANO_Y_HASH.set(bai,CURPANO_Y_HASH.get(bai)+1);

	LPANO_Y_HASH.set(ge,LPANO_Y_HASH.get(ge)+1);
	LPANO_Y_HASH.set(shi,LPANO_Y_HASH.get(shi)+1);
	LPANO_Y_HASH.set(bai,LPANO_Y_HASH.get(bai)+1);

	YNUMS.each(function(ball){
		if(ball.toInt()!=ge&&ball.toInt()!=shi&&ball.toInt()!=bai){
			Y_HASH.set(ball.toInt(),Y_HASH.get(ball.toInt())+1);
			CURLOST_Y_HASH.set(ball.toInt(),CURLOST_Y_HASH.get(ball.toInt())+1);
			LPANO_Y_HASH.set(ball.toInt(),0);
		}
	}); 
	if(MAXMISS_Y_HASH.get(ge)<CURLOST_Y_HASH.get(ge)){
		MAXMISS_Y_HASH.set(ge,CURLOST_Y_HASH.get(ge));
	}	
	if(MAXMISS_Y_HASH.get(shi)<CURLOST_Y_HASH.get(shi)){
		MAXMISS_Y_HASH.set(shi,CURLOST_Y_HASH.get(shi));
	}
	if(MAXMISS_Y_HASH.get(bai)<CURLOST_Y_HASH.get(bai)){
		MAXMISS_Y_HASH.set(bai,CURLOST_Y_HASH.get(bai));
	}
	if(MAXPANO_Y_HASH.get(ge)<LPANO_Y_HASH.get(ge)){
		MAXPANO_Y_HASH.set(ge,LPANO_Y_HASH.get(ge));
	}	
	if(MAXPANO_Y_HASH.get(shi)<LPANO_Y_HASH.get(shi)){
		MAXPANO_Y_HASH.set(shi,LPANO_Y_HASH.get(shi));
	}
	if(MAXPANO_Y_HASH.get(bai)<LPANO_Y_HASH.get(bai)){
		MAXPANO_Y_HASH.set(bai,LPANO_Y_HASH.get(bai));
	}
	CURLOST_Y_HASH.set(ge,0);
	CURLOST_Y_HASH.set(shi,0);
	CURLOST_Y_HASH.set(bai,0);

	Y_HASH.set(ge,0);
	Y_HASH.set(shi,0);
	Y_HASH.set(bai,0);
	
	calGsNums(bys,2);
	calGsNums(sys,3);
	calGsNums(gys,4);
	
}


function calGsNums(ys,pos){
	if(pos==2){
		CURPANO_B_HASH.set(ys,CURPANO_B_HASH.get(ys)+1);
		LPANO_B_HASH.set(ys,LPANO_B_HASH.get(ys)+1);

		GS_NUMS.each(function(ball){
			if(ball.toInt()!=ys){
				CURLOST_B_HASH.set(ball.toInt(),CURLOST_B_HASH.get(ball.toInt())+1);	
				LPANO_B_HASH.set(ball.toInt(),0);
			}
		});
		if(MAXMISS_B_HASH.get(ys)<CURLOST_B_HASH.get(ys)){
			MAXMISS_B_HASH.set(ys,CURLOST_B_HASH.get(ys));
		}
		if(MAXPANO_B_HASH.get(ys)<LPANO_B_HASH.get(ys)){
			MAXPANO_B_HASH.set(ys,LPANO_B_HASH.get(ys));
		}
		CURLOST_B_HASH.set(ys,0);
	}else if(pos==3){
		CURPANO_S_HASH.set(ys,CURPANO_S_HASH.get(ys)+1);
		LPANO_S_HASH.set(ys,LPANO_S_HASH.get(ys)+1);

		GS_NUMS.each(function(ball){
			if(ball.toInt()!=ys){
				CURLOST_S_HASH.set(ball.toInt(),CURLOST_S_HASH.get(ball.toInt())+1);	
				LPANO_S_HASH.set(ball.toInt(),0);
			}
		});
		if(MAXMISS_S_HASH.get(ys)<CURLOST_S_HASH.get(ys)){
			MAXMISS_S_HASH.set(ys,CURLOST_S_HASH.get(ys));
		}
		if(MAXPANO_S_HASH.get(ys)<LPANO_S_HASH.get(ys)){
			MAXPANO_S_HASH.set(ys,LPANO_S_HASH.get(ys));
		}
		CURLOST_S_HASH.set(ys,0);
	}else if(pos==4){
		//个位最大遗漏
		CURPANO_G_HASH.set(ys,CURPANO_G_HASH.get(ys)+1);
		LPANO_G_HASH.set(ys,LPANO_G_HASH.get(ys)+1);
		GS_NUMS.each(function(ball){
			if(ball.toInt()!=ys){
				CURLOST_G_HASH.set(ball.toInt(),CURLOST_G_HASH.get(ball.toInt())+1);	
				LPANO_G_HASH.set(ball.toInt(),0);
			}
		});
		if(MAXMISS_G_HASH.get(ys)<CURLOST_G_HASH.get(ys)){
			MAXMISS_G_HASH.set(ys,CURLOST_G_HASH.get(ys));
		}
		if(MAXPANO_G_HASH.get(ys)<LPANO_G_HASH.get(ys)){
			MAXPANO_G_HASH.set(ys,LPANO_G_HASH.get(ys));
		}
		CURLOST_G_HASH.set(ys,0);
	}
	var count0=0;
	var count1=0;
	var count2=0;
	['0','3','6','9'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count0++;
		}
	});
	['1','4','7'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count1++;
		}
	});
	['2','5','8'].each(function(ball){
		if(CURLOST_Y_HASH.get(ball.toInt())==0){
			count2++;
		}
	});
	YSB = count0+":"+count1+":"+count2;
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
	var html = '<tr><td class="td-first" colspan="2" title="第' + period + '期开奖号码:'+r+'">'+period+'</td><td class="group1" title="第'+period+'期开奖号码:'+r+'">'+z2+'<font color="red">'+z1+'</font></td>';

	var bHtml='';
	var sHtml='';
	var gHtml='';
	['0','1','2'].each(function(ball){
		var bai = CURLOST_B_HASH.get(ball)==0?'<span class="n-ssq">'+ball+'</span>':CURLOST_B_HASH.get(ball);
		var shi = CURLOST_S_HASH.get(ball)==0?'<span class="n-ssq">'+ball+'</span>':CURLOST_S_HASH.get(ball);
		var ge = CURLOST_G_HASH.get(ball)==0?'<span class="n-ssq">'+ball+'</span>':CURLOST_G_HASH.get(ball);
		bHtml+='<td class="td-segment-r td-blue" style="cursor: pointer;">'+bai+'</td>';
		sHtml+='<td class="" style="cursor: pointer;">'+shi+'</td>';
		gHtml+='<td class="td-blue" style="cursor: pointer;">'+ge+'</td>';
	});
	
	var xtHtml = '';
	XT_NUMS.each(function(ball){
		var xt = XT_HASH.get(ball)==0?'<span class="n-gu"  _cancas="cancas" _group="xt">'+ball+'</span>':XT_HASH.get(ball);
		xtHtml+='<td>'+xt+'</td>';
	});
	
	var ysHtml = '';
	['0','3','6','9'].each(function(ball){
		var ys = CURLOST_Y_HASH.get(ball)==0?'<span class="n-xie">'+ball+'</span>':CURLOST_Y_HASH.get(ball);
		ysHtml+='<td>'+ys+'</td>';
	});
	['1','4','7'].each(function(ball){
		var ys = CURLOST_Y_HASH.get(ball)==0?'<span class="n-xie">'+ball+'</span>':CURLOST_Y_HASH.get(ball);
		ysHtml+='<td>'+ys+'</td>';
	});
	['2','5','8'].each(function(ball){
		var ys = CURLOST_Y_HASH.get(ball)==0?'<span class="n-xie">'+ball+'</span>':CURLOST_Y_HASH.get(ball);
		ysHtml+='<td>'+ys+'</td>';
	});
	ysHtml+='<td class="td-segment-r">'+YSB+'</td></tr>';
			
	return html+bHtml+sHtml+gHtml+xtHtml+ysHtml;
}



// 华丽的分隔线 ======================================================================

function drawLine() {
	$("div_line").innerHTML = "";
	
	[ 'xt' ].each(function(hashName, index) {
		var tagArr1 = $$('span[_cancas="cancas"][_group="'+hashName+'"]');
		var myfun = function() {
			drawCancas(tagArr1, 1);
		};
		myfun.delay(10);
	});
}
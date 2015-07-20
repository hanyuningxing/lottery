<head>
	<title>会员管理</title>
	<meta name="decorator" content="none" />
	<meta name="menu" content="userManeger" />
	
</head>
<br/><br/><br/>
<div id="container">
	<div id="content">
		<table cellspacing="0" cellpadding="0" border="1">
			<tr>
				<th width="20" height="20">轮</td>
			    <th>时间</td>
		        <th>主队</td>
		        <th>比赛 </td>
		        <th>客队</td>
		        <th>澳门主(终)</td>
		        <th>澳门盘(终) </td>
		        <th>澳门客(终)</td>
		        <th>澳门主(初)</td>
		        <th>澳门盘(初) </td>
		        <th>澳门客(初)</td>
		        <th>马会主(终)</td>
		        <th>马会平(终) </td>
		        <th>马会客(终)</td>
		        <th>马会主(初)</td>
		        <th>马会平(初) </td>
		        <th>马会客(初)</td>
		        <th>威廉主(终)</td>
		        <th>威廉平(终) </td>
		        <th>威廉客(终)</td>
		        <th>威廉主(初)</td>
		        <th>威廉平(初) </td>
		        <th>威廉客(初)</td>
			</tr>
			<#if scheduleList?? && scheduleList?size gt 0>
				<#list scheduleList as data>
				<tr>
				  <td height="20">${data.round}</td>
			      <td>${data.matchTime?string('yy-MM-dd')}</td>
			      <td>${data.homeTeam!}</td>
			      <td>${data.homeScore!}-${data.guestScore!}</td>
			      <td>${data.guestTeam!}</td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.final_homeStr!}</#if></td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.final_pan}</#if></td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.final_loseStr!}</#if></td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.homeStr!}</#if></td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.pan}</#if></td>
			      <td><#if data.asiaOdds??>${data.asiaOdds.loseStr!}</#if></td>
			      <#assign mahui=data.getStandardOdds("香港马会")!'' />
			      <#assign weilian=data.getStandardOdds("威廉.希尔")!'' />
			      <td><#if mahui!=''>${mahui.final_homeStr!}</#if></td>
			      <td><#if mahui!=''>${mahui.final_drawStr!}</#if></td>
			      <td><#if mahui!=''>${mahui.final_loseStr!}</#if></td>
			      <td><#if mahui!=''>${mahui.homeStr!}</#if></td>
			      <td><#if mahui!=''>${mahui.drawStr}</#if></td>
			      <td><#if mahui!=''>${mahui.loseStr!}</#if></td>
			      
			      <td><#if weilian!=''>${weilian.final_homeStr!}</#if></td>
			      <td><#if weilian!=''>${weilian.final_drawStr!}</#if></td>
			      <td><#if weilian!=''>${weilian.final_loseStr!}</#if></td>
			      <td><#if weilian!=''>${weilian.homeStr!}</#if></td>
			      <td><#if weilian!=''>${weilian.drawStr}</#if></td>
			      <td><#if weilian!=''>${weilian.loseStr!}</#if></td>
				</tr>
				</#list>
			  <#else>
				<tr>
				  <td colspan="12" align="center" >暂无记录.</td>
				</tr>
			</#if>
		</table>
  </div>
</div>


<head>
	<title>会员中心首页</title>
	<meta name="decorator" content="trade" />
	<meta name="menu" content="index" />
	<link href="<@c.url value= "/pages/css/center_main.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/web_hyzx.css"/>" rel="stylesheet" type="text/css" />
	<link href="<@c.url value= "/pages/css/index_content.css"/>" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
var Class = {
　create: function() {
　　return function() {
　　　this.initialize.apply(this, arguments);
　　}
　}
}
　　
Object.extend = function(destination, source) {
　　for (var property in source) {
　　　　destination[property] = source[property];
　　}
　　return destination;
}
　　
function addEventHandler(oTarget, sEventType, fnHandler) {
　　if (oTarget.addEventListener) {
　　　　oTarget.addEventListener(sEventType, fnHandler, false);
　　} else if (oTarget.attachEvent) {
　　　　oTarget.attachEvent("on" + sEventType, fnHandler);
　　} else {
　　　　oTarget["on" + sEventType] = fnHandler;
　　}
};
　　
var Scroller = Class.create();
Scroller.prototype = {
　initialize: function(idScroller, idScrollMid, options) {
　　var oThis = this, oScroller = document.getElementById(idScroller), oScrollMid =document.getElementById(idScrollMid);
　　
　　this.SetOptions(options);
　　this.Side = this.options.Side || ["up"];//方向
　　this.scroller = oScroller;　　　　　　//对象
　　this.speed = this.options.Speed;　　//速度
　　this.timer = null;　　　　　　　　　　//时间
　　this.pauseHeight = 0;　　　　　　　　//定高
　　this.pauseWidth = 0;　　　　　　　　//定宽
　　this.pause = 0;　　　　　　　　　　　　//定高(宽)
　　this.side = 0;　　　　　　　　　　　　//参数
　　
　　//用于上下滚动
　　this.heightScroller = parseInt(oScroller.style.height) || oScroller.offsetHeight;
　　this.heightList = oScrollMid.offsetHeight;
　　
　　//用于左右滚动
　　this.widthScroller = parseInt(oScroller.style.width) || oScroller.offsetWidth;
　　this.widthList = oScrollMid.offsetWidth;
　　
　　//js取不到css设置的height和width
　　
　　oScroller.style.overflow = "hidden";
　　oScrollMid.appendChild(oScrollMid.cloneNode(true));
　　oScrollMid.appendChild(oScrollMid.cloneNode(true));
　　
　　addEventHandler(oScroller, "mouseover", function() { oThis.Stop(); });
　　addEventHandler(oScroller, "mouseout", function() { oThis.Start(); });
　　
　　this.Start();
　},
　//设置默认属性
　SetOptions: function(options) {
　　this.options = {//默认值
　　　Step:　　　　　　1,//每次变化的px量
　　　Speed:　　　　30,//速度(越大越慢)
　　　Side:　　　　　　["up"],//滚动方向:"up"是上，"down"是下，"left"是左，"right"是右
　　　PauseHeight:　　0,//隔多高停一次
　　　PauseWidth:　　0,//隔多宽停一次
　　　//当上下和左右一起使用时必须设置PauseHeight和PauseWidth来设置转向位置
　　　PauseStep:　　0//停顿时间(PauseHeight或PauseWidth大于0该参数才有效)
　　};
　　Object.extend(this.options, options || {});
　},
　//转向
　Turn: function() {
　　//通过设置方向数组的排列来转向
　　this.Side.push(this.Side.shift().toLowerCase());
　},
　//上下滚动
　ScrollUpDown: function() {
　　this.pause = this.pauseHeight;
　　this.scroller.scrollTop = this.GetScroll(this.scroller.scrollTop, this.heightScroller, this.heightList, this.options.PauseHeight);
　　this.pauseHeight = this.pause;
　　
　　var oThis = this;
　　this.timer = window.setTimeout(function(){ oThis.Start(); }, this.speed);
　},
　//左右滚动
　ScrollLeftRight: function() {
　　this.pause = this.pauseWidth;
　　//注意:scrollLeft超过1400会自动变回1400 注意长度
　　this.scroller.scrollLeft = this.GetScroll(this.scroller.scrollLeft, this.widthScroller, this.widthList, this.options.PauseWidth);
　　this.pauseWidth = this.pause;
　　
　　var oThis = this;
　　this.timer = window.setTimeout(function(){ oThis.Start(); }, this.speed);
　},
　//获取设置滚动数据
　GetScroll: function(iScroll, iScroller, iList, iPause) {
　　var iStep = this.options.Step * this.side;
　　
　　if(this.side > 0){
　　　　if(iScroll >= (iList * 2 - iScroller)){ iScroll -= iList; }
　　} else {
　　　　if(iScroll <= 0){ iScroll += iList; }
　　}
　　
　　this.speed = this.options.Speed;
　　if(iPause > 0){
　　　　if(Math.abs(this.pause) >= iPause){
　　　　　　this.speed = this.options.PauseStep; this.pause = iStep = 0; this.Turn();
　　　　} else {
　　　　　　this.pause += iStep;
　　　　}
　　}
　　
　　return (iScroll + iStep);
　},
　//开始
　Start: function() {　　
　　//方向设置
　　switch (this.Side[0].toLowerCase()) {
　　　　case "right" :
　　　　　　if(this.widthList < this.widthScroller) return;
　　　　　　this.side = -1;
　　　　　　this.ScrollLeftRight();
　　　　　　break;
　　　　case "left" :
　　　　　　if(this.widthList < this.widthScroller) return;
　　　　　　this.side = 1;
　　　　　　this.ScrollLeftRight();
　　　　　　break;
　　　　case "down" :
　　　　　　if(this.heightList < this.heightScroller) return;
　　　　　　this.side = -1;
　　　　　　this.ScrollUpDown();
　　　　　　break;
　　　　case "up" :
　　　　default :
　　　　　　if(this.heightList < this.heightScroller) return;
　　　　　　this.side = 1;
　　　　　　this.ScrollUpDown();
　　}
　},
　//停止
　Stop: function() {
　　clearTimeout(this.timer);
　}
};
　　
window.onload = function(){
　　new Scroller("idScroller", "idScrollMid",{ Side:["up","down"], PauseHeight:350, PauseWidth:400 });
　　new Scroller("idScroller2", "idScrollMid2",{ Side:["up","down"], PauseHeight:350, PauseWidth:400 });

}
</script>
	 <!--左边开始-->
	<div id="web_hyzx">
		<!--left-->
		 <div class="cleft">
	    <#assign left_menu = 'index'/>
	      <#include "left.ftl" />
  	</div>	 
 
	<#include "/common/message.ftl" />
	<div id="right_hy">
	    <table class="xjzh_table" width="722" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td class="orange_bottom xjzh_ct" width="146">现金账户：</td>
			<td class="orange_bottom" width="227">
				可用余额：<span>${userInfoForm.defaultAccountRemainMoney!0}</span> 元<br />
				冻结余额：0 元
			</td>
			<td class="orange_bottom" width="7"></td>
			<td class="orange_bottom" width="340">
			   <INPUT type="image" src="<@c.url value="/pages/images/hyzc_anniu_03.gif"/>" onclick="window.location.href='<@c.url value='/user/fund!payPer.action' />';return false;"/>
			   <INPUT type="image" src="<@c.url value="/pages/images/hyzc_anniu_05.gif"/>" onclick="window.location.href='<@c.url value='/user/fund!drawingPer.action' />';return false;"/>
		    <INPUT type="image" src="<@c.url value="/pages/images/hyzc_anniu_07.gif"/>" onclick="window.location.href='<@c.url value='/user/fund!list.action' />';return false;"/>			</td>
		  </tr>
		  
		  <tr>
			<td class="xjzh_ct">彩金账户：</td>
			<td>可用余额：<span>${userInfoForm.activityAccountRemainMoney!0}</span> 元<br />冻结余额：0 元</td>
			<td><img src="<@c.url value="/pages/images/hy_bg_fgx.gif"/>" width="1" height="101" /></td>
			<td><img src="<@c.url value="/pages/images/red_q.gif"/>" width="16" height="16" />本月彩金活动： <br />
			  　 1. 验证手机号码送彩金2元<br />
			  　 2. 缴纳交通罚款最高可获10元彩金<br />
		    　          3. 购买手机卡可获得丰厚彩金</td>
		  </tr>
		</table>

        <div class="kong10"></div>
       	    <div class="fkcl">
		    <div class="fkcl_font"> 
			     <font><img src="<@c.url value="/pages/images/yd.gif"/>" /><span> 什么叫现金账户：</span>就是指用户可以随意支配的个人钱包，用户能够随意购彩、提款等操作。来源为未参加网站活动的个人充值、中奖后返奖等渠道。</font>  
				 </div> 
		    <div class="fkcl_font"> 
			     <font><img src="<@c.url value="/pages/images/yd.gif"/>" /> <span>什么叫彩金账户：</span>就是指用户只可以用来购彩的个人钱包，用户只能用来购彩，不可提款等操作。来源为参加网站活动的个人充值、网站奖励赠送的彩金等渠道。</font>
		    </div>  
			<div class="fkcl_font"> 
			     <font><img src="<@c.url value="/pages/images/yd.gif"/>" /><span> 备注：</span>用户在购彩过程中系统将会自动优先扣取彩金账户；不管用户用任何账户购彩中奖后，中奖的奖金将直接返回现金账户。 </font>            
		    </div> 			
		</div>
		
		
		<div class="gdzjyh">
		    <div class="bqmd" style="margin-right:9px;">
				<div class="bqtop2">最新获得彩金用户</div>	
				   <div id="idScroller"  align="left" class="Scroller" style="width:500px; height:200px;">
					　<div style="width:1600px">
					　　<div id="idScrollMid" class="ScrollMid">
					　　　<ul>
					　　		<#include '/html/info/activity/recharge_activity.html'/>	
							<#include '/html/info/activity/registered_recharge.html'/>	
					　　　</ul>
					　　</div>
					　</div>
					</div>
		   </div>
		   
		   <div class="bqmd2">
				<div class="bqtop1">
				   <font>最新中奖用户</font>
				   <span class="bqtopin"><INPUT type="image" src="<@c.url value="/pages/images/hyzc_anniu_21.gif"/>" value=""/></span>
				   <div id="idScroller2"  align="left" class="Scroller" style="width:500px; height:200px;">
					　<div style="width:1600px">
					　　<div id="idScrollMid2" class="ScrollMid">
					　　　<ul>
					　　		　<#include '/html/info/news/index-wonscheme.html'/>					
					　　　</ul>
					　　</div>
					　</div>
					</div>
				   
				</div>				 			
		   </div>
		   <div style="clear:both;"></div>
		</div>
		
	</div>

</div>
<!-- 内容结束 -->
 
</body>
</html>

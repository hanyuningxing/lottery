<head>
	<title>方案编辑</title>
	<meta name="menu" content="activity"/>
	<meta name="menuItem" content="editKenoScheme"/>
</head>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">方案编辑</span></div>
</div>
<form name="activityCreateForm" action="${base}/admin/activity/keno/scheme!putKenoSchemeInfo.action" method="post" onkeydown="if(event.keyCode==13){new Event(event).stop();}"><#-- 禁止回车提交 -->
	           <table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
					    <tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">彩种：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					      	   <select name="activityCreateForm.lottery">
					      	   	<option value="SSC">时时彩</option>
								<option value="QYH">群英会</option>
								<option value="SDEL11TO5">山东11选5</option>
								<option value="EL11TO5">11选5</option>
					           </select>			              
						      </span></td>
			            </tr>
			            <tr bgcolor="#F5f5f5">
			              <td align="center">彩期：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                  <input name="activityCreateForm.periodNum" type="text"  size="30" />
			              </span></td>
			            </tr>
    					<tr bgcolor="#F5f5f5">
			              <td align="center">玩法：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                  <select name="activityCreateForm.playType">
		                      	<option value="DirectOne">一星</option>
		                      	<option value="DirectTwo">二星直选</option>
		                  		<option value="GroupTwo">二星组选</option>
		                  		<option value="DirectThree">三星直选</option>
		                  		<option value="ThreeGroup3">组三</option>
		                        <option value="ThreeGroup6">组六</option>
			                    <option value="DirectFive">五星直选</option>
			                    <option value="AllFive">五星通选</option>
			                    <option value="BigSmallDoubleSingle">大小双单</option>
								<option value="RandomThree">任三</option>
								<option value="RandomSeven">任七</option>
								<option value="RandomTwo">任二</option>
								<option value="DirectOne">顺一</option>
						      </select>	
			              </span></td>
			            </tr>
						<tr bgcolor="#FFFFFF">
			              <td width="85" align="center" bgcolor="#F5f5f5">发起人：</td>
			              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
						      	   <select name="activityCreateForm.userName">
									<option value="不羁公子">不羁公子</option>
									<option value="会飞的yy">会飞的yy</option>
									<option value="三月鹰飞">三月鹰飞</option>
									<option value="苍浪羽士">苍浪羽士</option>
									<option value="缘来缘不去">缘来缘不去</option>
									<option value="松下煮清酒">松下煮清酒</option>
							    	<option value="舒天畅游">舒天畅游</option>
									<option value="撒酒狂歌">撒酒狂歌</option>
									<option value="月影岚殇">月影岚殇</option>
									<option value="发飙的蜗牛">发飙的蜗牛</option>
									<option value="今夜阳光灿烂">今夜阳光灿烂</option>
									<option value="向往奥迪福">向往奥迪福</option>
									<option value="誓要中奖">誓要中奖</option>
									<option value="洋洋得意">洋洋得意</option>
									<option value="我不是小贩">我不是小贩</option>
								    <option value="langhua">langhua</option>
									<option value="飞哥点杀">飞哥点杀</option>
									<option value="孩子他爸">孩子他爸</option>
									<option value="孩子他妈">孩子他妈</option>
									<option value="今夜好冷">今夜好冷</option>
									<option value="我是懒洋洋">我是懒洋洋</option>
									<option value="横行霸道">横行霸道</option>
									<option value="一路向北">一路向北</option>
									<option value="数据专线">数据专线</option>
									<option value="岭南投资团">岭南投资团</option>
									<option value="天下无双">天下无双</option>
									<option value="三洋开泰">三洋开泰</option>
									<option value="闪电中奖">闪电中奖</option>
									<option value="常常差一点">常常差一点</option>
									<option value="爱上500万">爱上500万</option>
									<option value="最爱中大奖">最爱中大奖</option>
									<option value="有木有中奖">有木有中奖</option>
									<option value="骑着狮子的羊">骑着狮子的羊</option>
									<option value="大红中胡发财">大红中胡发财</option>
									<option value="woyaozhuc">woyaozhuc</option>
									<option value="等中奖好买米">等中奖好买米</option>
									<option value="xjl1983">xjl1983</option>
									<option value="wudisen789">wudisen789</option>
									<option value="qq5188">qq5188</option>
									<option value="ai500万">ai500万</option>
									<option value="love2011">love2011</option>
									<option value="zhong500w">zhong500w</option>
									<option value="cyy520">cyy520</option>
									<option value="我的500万">我的500万</option>
									<option value="wo186">wo186</option>
								    <option value="tt88888">tt88888</option>
								    <option value="大师中中中">大师中中中</option>
								     <option value="sos彩票 ">sos彩票 </option>
								     <option value="卖菜买彩 ">卖菜买彩  </option>
								    <option value="abc068">abc068</option>
								    <option value="5227dlm">5227dlm</option>
								     <option value="zxgcgm008">zxgcgm008</option>
								    <option value="lizhiwei188">lizhiwei188</option>
								    <option value="jackpot">jackpot</option>
								    <option value="shuo88">shuo88</option>
								    <option value="12356aa">12356aa</option>
								    <option value="335544in">335544in</option>
								    <option value="zhuzhu爱wo">zhuzhu爱wo</option>
								    <option value="彩票情劫510">彩票情劫510</option>
								    <option value="aiamllll">aiamllll</option>
								    <option value="hbvdnmk">hbvdnmk</option>
								     <option value="1314ba">1314ba</option>
								     <option value="vudiubnb">vudiubnb</option>
								     <option value="上下500万">上下500万</option>
								     <option value="李代表8">李代表8</option>
								     <option value="新浪微博888">新浪微博888</option>
								     <option value="幸福2011">幸福2011</option>
								     <option value="zhangzhi">zhangzhi</option>
								     <option value="abc莉莉   ">abc莉莉   </option>
								     <option value="一心一计 ">一心一计 </option>
								     <option value="youaimei">youaimei</option>
								     <option value="快乐的财迷">快乐的财迷</option>
								     <option value="知了散了走了">知了散了走了</option>
								     <option value="889966zl">889966zl</option>
						           </select>			              
						      </span></td>
			            </tr>
						 <tr bgcolor="#F5f5f5">
			              <td align="center">倍数：</td>
			              <td bgcolor="#F5f5f5"><span class="ssq_tr_2">
			                  <input name="activityCreateForm.multiple" type="text"  size="30" />
			              </span></td>
			            </tr>
			            <tr bgcolor="#FFFFFF">
			              <td align="center" valign="top">&nbsp;</td>
			              <td bgcolor="#FFFFFF">
			                 <input type="image" src="${base}/images/comfirm.gif" />
			              </td>
			            </tr>
			          </table>
</form>

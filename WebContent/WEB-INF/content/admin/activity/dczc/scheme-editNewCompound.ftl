<@override name="editNewHead">
	<script type="text/javascript">
		$(function() {
			window.PassTypeUtil = {};
			PassTypeUtil.getQuickPassType = function(matchCount){
				if (matchCount < 1 || matchCount > 15) {
					return null;
				}
				for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
					var type = PassTypeArr[i];
					if (type.matchCount == matchCount && type.units == 1) 
						return type;
					else if (type.matchCount > matchCount)
						break;
				}
				return null;
			};
			PassTypeUtil.getNormalPassType = function(matchCount){
				if (matchCount < 1 || matchCount > 15) {
					return null;
				}
				var types = [];
				types.push(PassTypeArr[0]);
				if (matchCount > 1) {
					for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
						var type = PassTypeArr[i];
						if (type.matchCount == matchCount) 
							types.push(type);
						else if (type.matchCount > matchCount)
							break;
					}
				}
				return types;
			};
			PassTypeUtil.getMultiplePassType = function(matchCount) {
				var types = [];
				if (matchCount < 1 || matchCount > 15) {
					return types;
				}
				for ( var i = 0, len = PassTypeArr.length; i < len; i++) {
					var type = PassTypeArr[i];
					if (type.matchCount <= matchCount && type.units == 1) 
						types.push(type);
					else if (type.matchCount > matchCount)
						break;
				}
				return types;
			};
			
			var itemFn = function(v, t) {
				return {
					value : v,
					text : t
				};
			};
			window.PlayItem = {};
			window.rowItemValues = [];
			window.rowItemSizes = [];
			var v = 0;
			<#list itemArr as item>
				PlayItem['${item}'] = itemFn('${item.value}', '${item.text}');
				v |= 1<<${item.ordinal()};
			</#list>
			<#if itemMap??>
				<#list itemMap.entrySet() as entry>
					<#assign arr=entry.getValue() />
					window.rowItemSizes.push(${arr?size});
					v = 0;
					<#list arr as item>
						v |= 1<<${item.ordinal()};
					</#list>
					window.rowItemValues.push(v);
				</#list>
			<#else>
				window.rowItemSizes.push(${itemArr?size});
				window.rowItemValues.push(v);
			</#if>
		});		
		<#-- 注：此JS必须在compoundInit.js之前 -->
	</script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/${lotteryType.key}/compoundInit.js"/>"></script>
	
	<script type='text/javascript' src='<@c.url value='/js/My97DatePicker/WdatePicker.js'/>'></script>
</@override>

<@override name="initContent">
	<div class="kong10"></div>
	<form id="createForm" action="${base}/admin/activity/dczc/scheme!create.action" method="post" autocomplete="off">
		<div class="floatleft">
		  <div id="div_op">
		      <table width="390" cellspacing="1" cellpadding="0" border="0" class="dctbg"><tbody>
		        <tr height="22" class="dctro">
		          <td height="27" colspan="<#if hasHandicap>5<#else>4</#if>">场次及投注选项</td>
		        </tr>
		        <tr height="22" class=" dctry">
		          <td width="38">场次 </td>
		          <td width="160">比赛 </td>
		          <#if hasHandicap><td id="op_handicap" width="32">让球</td></#if>
		          <td>您的投注</td>
				  <td id="td_danma" style="display:none">胆码</td>
		        </tr>
		        <#--
		        <tr height="28" class=" trw">
		          <td align="right" style="padding-right: 28px;" colspan="<#if hasHandicap>5<#else>4</#if>"><a href="#"><img src="<@c.url value="/pages/images/qingchu.gif" />"></a></td>
		        </tr>-->
		      </tbody></table>
		  </div>
		  <div id="div_mohushedan" style="margin-top:5px;display:none;">
		      <table width="390" cellspacing="1" cellpadding="0" border="0" class="dctbg">
		        <tbody>
			      <tr class="trw">
			        <td height="25" align="center"><strong>模糊设胆：</strong><span id="mohushedan"></span></td>
			      </tr>
		        </tbody>
		      </table>
		    </div>
	    </div>
		<div class="floatrig">
	      <table width="405" cellspacing="1" cellpadding="0" border="0" class="dctbg">
	        <tbody><tr height="22" class="dctro">
	          <td height="27" align="left" class="left10"> 
	          	  <div style="line-height: 27px;" class="floatleft">投注信息及购买</div>
	              <div class="floatrig rig10">
	                <ul class="kgnav">
	                  <li><a id="playmode_0" href="#" onclick="chgPlayMode(0);return false;" class="now">快速玩法</a></li>
	                  <li><a id="playmode_1" href="#" onclick="chgPlayMode(1);return false;">高级玩法</a></li>
	                </ul>
	              </div>
	          </td>
	        </tr>
	        <tr id="tr_passmode_title" height="24" class="trw" style="display:none;">
	          <td align="left" class="left10 boldchar dclorange">选择过关类型：</td>
	        </tr>
	        <tr id="tr_passmode_content" height="24" class="trw" style="display:none;">
	          <td align="left" class="left10">
	          	<input id="createForm_passMode_0" onclick="chgPassMode();" type="radio" name="createForm.passMode" value="NORMAL" checked="checked" /><label for="createForm_passMode_0">普通过关</label>
				<input id="createForm_passMode_1" onclick="chgPassMode();" type="radio" name="createForm.passMode" value="MULTIPLE" /><label for="createForm_passMode_1">多选过关</label>
	  		  </td>
	        </tr>
	        <tr id="tr_passtype_title" height="24" class="trw" style="display:none;">
	          <td align="left" class="left10 boldchar dclorange">选择过关方式</td>
	        </tr>
	        <tr id="tr_passtype_content" height="24" class="trw">
	          <td align="left" class="left10" id="pass_container"></td>
	        </tr>
	        <tr height="24" class="trw">
	          <td align="left" class="left10 boldchar dclorange">确认投注结果</td>
	        </tr>
	        <tr height="24" class="trw">
	          <td align="left" class="left10">
	          	<span id="span_msg">您选择了<span class="rebchar" _name="createForm_units">0</span>注，<span class="rebchar"><input type="text" style="width: 40px;IME-MODE: disabled;" value="1" onkeyup="updateBetCost();" onblur="if(this.value<=0){this.value=1;updateBetCost();}" name="createForm.multiple" maxLength="3" class="txtbox color_fff" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" /></span>倍，金额<span class="redboldchar">￥<span _name="createForm_schemeCost">0</span></span>&nbsp;&nbsp;<a href="#" onclick="prizeForecast();return false;">奖金评测</a></span>
	          	<span id="span_err" class="rebchar"></span>
	          </td>
	        </tr>
	        <tr height="24" class="trw">
	          <td align="left" class="left10">
	            <input id="createForm_shareType_SELF" type="radio" value="SELF" name="createForm.shareType" onclick="compoundChgShareType(this);"/><label for="createForm_shareType_SELF">我要自购</label>
	            <input id="createForm_shareType_TOGETHER" type="radio" value="TOGETHER" name="createForm.shareType" onclick="compoundChgShareType(this);" checked="checked"/><label for="createForm_shareType_TOGETHER">发起合买</label>
	          </td>
	        </tr>
	        <tr height="24" class="trw" id="tr_TOGETHER_title">
	          <td align="left" class="left10 boldchar dclorange">合买信息</td>
	        </tr>
	        <tr class="trw" id="tr_TOGETHER_content">
	          <td><table width="100%" cellspacing="0" cellpadding="1" border="0" align="center" class="b1">
	            <tr>
	              	<td align="right" class="tdboldbggray">方案描述：</td>
	              	<td align="left"><textarea class="kuangb1gray" rows="3" cols="30" name="createForm.description"></textarea></td>
	            </tr>
		        <tr>
		          	<td align="right" class="tdboldbggray">最低认购： </td>
		          	<td align="left"><input name="createForm.minSubscriptionCost" type="text" size="7" class="kuanginput" onkeydown="number_check(this,event,0);" value="1" onblur="if(this.value<=0){this.value=1;}" />元</td>
		        </tr>
		        <tr>
		          	<td align="right" class="tdboldbggray">我要认购：</td>
		          	<td align="left">
		              <input name="createForm.subscriptionCost" type="text" size="7" class="kuanginput" onkeyup="updateSubscriptionPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="subscriptionPerSpan">0.00</span>%</span>
		          	</td>
		        </tr>
		        <tr>
		            <td align="right" class="tdboldbggray">发起人提成：</td>
		            <td align="left">
			            <#assign commissionRate=(createForm.commissionRate)!0.05 />
			            <select name="createForm.commissionRate" size="1" style="width:70px;">
			              <option value="0">无佣金</option>
			              <#list 1..5 as c>
			              	<#assign rate=c/100 />
			              	<option value="${rate?string('0.00')}" <#if commissionRate=rate>selected="selected"</#if> >${c}%佣金</option>
			              </#list>
			            </select>
		            </td>
		        </tr>
		        <tr>
		            <td align="right" class="tdboldbggray">我要保底：</td>
		            <td align="left"><input name="createForm.baodiCost" type="text" size="7" class="kuanginput" onkeyup="updateBaodiCostPer();" onkeydown="number_check(this,event,0);"/>元，所占比例<span class="redboldchar"><span id="baodiCostPerSpan">0.00</span>%</span></td>
		        </tr>
	          </table></td>
	        </tr>
	        <tr height="24" class="trw">
	          <td align="left" class="left10">
	          	是否公开：
	          	<#assign secretTypeArr=stack.findValue("@com.cai310.lottery.common.SecretType@values()") />
				<#assign defalutType=secretTypeArr[0] />
				<#list secretTypeArr as type>
				  <input id="createForm_secretType_${type}" type="radio" name="createForm.secretType" <#if defalutType==type>checked="checked"</#if> value="${type}"/><label for="createForm_secretType_${type}">${type.secretName}</label>
				</#list>
	          </td>
	        </tr>
	        <tr height="24" class="trw">
	          <td align="left" class="left10">
	          	<input type="checkbox" checked="checked" id="agree"/><label for="agree" class="inputcheckbox graychar"><@com.buyProtocol /></label>
	          </td>
	        </tr>
	         <tr height="24" class="trw">
	          <td align="left" class="left10">
	     		   投注人：
	     		     <select name="userName">
									<option value="得意洋洋">得意洋洋</option>
									<option value="今夜好冷">今夜好冷</option>
									<option value="我是懒洋洋">我是懒洋洋</option>
									<option value="横行霸道">横行霸道</option>
									<option value="一路向北">一路向北</option>
									<option value="数据专线">数据专线</option>
									<option value="岭南投资团">岭南投资团</option>
									<option value="天下无双">天下无双</option>
									<option value="三洋开泰">三洋开泰</option>
									<option value="wudisen789">wudisen789</option>
									<option value="qq5188">qq5188</option>
									<option value="ai500万">ai500万</option>
					</select>			              
	          </td>
	        </tr>
 			 <tr height="24" class="trw">
	          <td align="left" class="left10">投注时间：格式   2011-06-24 09:58:00   
		    	<input id="createTime" type="text" name="createTime"/>
		       </td>
		    </tr>
	        <tr height="50" class="trw">
	          <td align="left" class="left10">
	          	<span id="span_createForm_submit"><a id="formSubmit" onclick="dczcsubmitCreateForm();return false;" href="#"><img src="<@c.url value="/pages/images/dcbtgoumai.gif" />"></a></span>
	          	<span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span>
	          </td>
	        </tr>
	       
	      </tbody></table>
	
	        
	    </div>
	    <input type="hidden" name="playType" value="${playType}"/>
	    <input type="hidden" name="createForm.mode" value="${salesMode}"/>
	    <input type="hidden" name="createForm.periodId" value="${period.id}" />
	    <input type="hidden" name="createForm.units" value="0" />
	    <input type="hidden" name="createForm.schemeCost" value="0" />
	    <input type="hidden" name="request_token" value="${datetime().millis}" />
	</form>
	<script type="text/javascript">
		$(function(){
			var obj = document.getElementById('createForm_shareType_${shareType!'TOGETHER'}');
			obj.checked = true;
			compoundChgShareType(obj);
		});
		
		function dczcsubmitCreateForm() {
			   $("#createForm").submit();
		}
		
		
	</script>
</@override>

<@extends name="scheme-editNew.ftl"/> 
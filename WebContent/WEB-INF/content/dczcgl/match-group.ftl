
<div class="maina">
	<div class="contenta">
	     <div class="content_lefta">
		    <div class="left_maina">
			       <b>选择场次</b>
		
			 <div class="left_main0a">
		    <table width="310" border="0" class="lb_tb1a">
			   <tr>
				<td width="30" height="20">场序 </td>
				<td width="90" height="20">对阵</td>
				<td width="82" height="20"><select name="select" size="1">
										<option>SP值</option>
			    </select></td>
			    <td width="53"><a href="javascript:void(0);" onclick="selectGroupAll('select');">[全清]</a></td>
			  </tr>
			</table>
		     <div class="fzcx2a">
			 <table width="300" border="0" cellpadding="0" cellspacing="0" class="nr_lba">
			  		<#if matchFilterDatas??&&matchFilterDatas?size gt 0>
						  <div id="matchDatas">
					   			<#list matchFilterDatas as data>
								  <tr class="nr_bga">
									<td width="29" height="25" name="selectBall" id="selectBall_${data[0]}">${data[0]}</td>
									<td width="51" height="25">${data[1]}</td>
									<td width="24" height="25">${data[2]!""}</td>
									<td width="54" height="25">${data[3]}</td>
									<td width="37" height="25" id="sps_win_${data[0]}">${data[7]!""}</td>
									<td width="37" height="25" id="sps_draw_${data[0]}">${data[8]!""}</td>
									<td width="36" height="25" id="sps_lose_${data[0]}">${data[9]!""}</td>
									<td width="43"  class="lt"><input class="cht" name="groupCk" type="checkbox" value="${data[0]}" id="groupMatch_${data[0]}" checked="true" onclick="chgGroupCk(this.value);"/></td>								  
								  </tr>
							  </#list>
							  </div>
						  </#if>        	
			</table>
		 </div>
		</div>
        </div>
		 </div>
		 <div class="content_righta">
		      <div class="right_maina">
		      <!--分组和值开始-->
			        <table width="300" border="0" class="lb_tba"  id="groupSum" style="display:none">
						  <tr>
							<td height="25">
							<select name="select" size="1" id="lt_jh" onchange="chgGroupCondtionState('lt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size*3 as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
							<td height="25">≤和值≤</td>
							<td height="25">
							<select name="select" size="1" id="gt_jh" onchange="chgGroupCondtionState('gt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size*3 as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
						  </tr>
				</table>
						      <!--分组和值结束-->
				
						      <!--分组断点开始-->
				<table width="300" border="0" class="lb_tba"  id="groupDd" style="display:none">
						  <tr>
							<td height="25">
							<select name="select" size="1" id="lt_jh" onchange="chgGroupCondtionState('lt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size-1 as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
							<td height="25">≤断点≤</td>
							<td height="25">
							<select name="select" size="1" id="gt_jh" onchange="chgGroupCondtionState('gt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size-1 as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
						  </tr>
				</table>
						      <!--分组断点开始-->
				
				<!--分组指数和值开始-->
				<table width="300" border="0" class="lb_tba"  id="groupZshz" style="display:none">
						  <tr>
							<td height="25">
								<input class="ipt" type="text" size="10" value="" id="lt_jh" onchange="chgGroupCondtionState('lt',this.value);" />
							</td>
							<td height="25">≤指数和≤</td>
							<td height="25">
								<input class="ipt" type="text" size="10" value="" id="gt_jh" onchange="chgGroupCondtionState('gt',this.value);" />
							</td>
						  </tr>
				</table>
						      <!--分组指数和值开始-->
				<!--分组指数积累值开始-->
				<table width="300" border="0" class="lb_tba"  id="groupZsj" style="display:none">
						  <tr>
							<td height="25">
								<input class="ipt" type="text" size="10" id="lt_jh" value="" onchange="chgGroupCondtionState('lt',this.value);" />
							</td>
							<td height="25">≤指数积累≤</td>
							<td height="25">
								<input class="ipt" type="text" size="10" id="gt_jh" value="" onchange="chgGroupCondtionState('gt',this.value);" />
							</td>
						  </tr>
				</table>
						      <!--分组指数和值开始-->
					<div class="shaxiang8_index"></div>
					<table width="300" border="0" class="lb_tba">
					  <tr>
						<td height="25"><a href="javascript:void(0);" onclick="operaGroupConditions('add');">添加</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaGroupConditions('replace');">替换</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaGroupConditions('cancel');">删除</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaGroupConditions('cancelAll');">全删</a></td>
					  </tr>
				</table>
					<div class="shaxiang8_index"></div>
					<div class="fztja">
			        <table width="290" border="0" cellpadding="0" cellspacing="0" class="tj_tba" id="selectGroupConditions"></table>
				</div>

			  </div>
		 </div>
	
	</div>
	
	<div class="footera">
	  <div class="qra"><a href="javascript:void(0);" onclick="clickGroupOK();">确定</a></div>
	</div>
</div>


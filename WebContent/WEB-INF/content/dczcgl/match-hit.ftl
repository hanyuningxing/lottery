
<div class="maina">
	<div class="contenta">
	     <div class="content_lefta">
		    <div class="left_maina">
			       <b>选择场次</b>
		
			 <div class="left_main0a">
		    <table width="310" border="0" class="lb_tb1a">
			  <tr>
				<td width="30" height="16">&nbsp;</td>
				<td width="90" height="16">&nbsp;</td>
				<td height="16" colspan="2">[<a href="javascript:void(0);" onclick="selectAll('select');">全选</a>]</td>
				<td height="16">[<a href="javascript:void(0);" onclick="selectAll('cancel');">全删</a>]</td>
				<td width="82" height="16">&nbsp;</td>
			  </tr>
			   <tr>
				<td width="30" height="20">场序 </td>
				<td width="90" height="20">对阵</td>
				<td width="23" height="20"><a href="javascript:void(0);" onclick="selectKey('3');">全</a></td>
				<td width="23" height="20"><a href="javascript:void(0);" onclick="selectKey('1');">全</a></td>
				<td width="36" height="20"><a href="javascript:void(0);" onclick="selectKey('0');">全</a></td>
				<td width="82" height="20"><select name="select" size="1">
										<option>SP值</option>
			     </select></td>
			  </tr>
			</table>
		     <div class="fzcx2a">
			 <table width="300" border="0" cellpadding="0" cellspacing="0" class="nr_lba">
			  		<#include 'match-content.ftl' />		        	
			</table>
		 </div>
		</div>
        </div>
		 </div>
		 <div class="content_righta">
		      <div class="right_maina">
			        <table width="300" border="0" class="lb_tba">
						  <tr>
							<td height="25">
							<select name="select" size="1" id="lt_jh" onchange="chgSelectionCondtionState('lt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
							<td height="25">≤命中场次范围≤</td>
							<td height="25">
							<select name="select" size="1" id="gt_jh" onchange="chgSelectionCondtionState('gt',this.options[this.selectedIndex].value);">
							<#if size??>
								<#list 0..size as i>
									<option>${i}</option>
								</#list>
							<#else>
									<option>0</option>							
    						</#if>
						    </select></td>
						  </tr>
				</table>
					<div class="shaxiang8_index"></div>
					<table width="300" border="0" class="lb_tba">
					  <tr>
						<td height="25"><a href="javascript:void(0);" onclick="operaConditions('add');">添加</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaConditions('replace');">替换</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaConditions('cancel');">删除</a></td>
						<td height="25"><a href="javascript:void(0);" onclick="operaConditions('cancelAll');">全删</a></td>
					  </tr>
				</table>
					<div class="shaxiang8_index"></div>
					<div class="fztja">
			        <table width="290" border="0" cellpadding="0" cellspacing="0" class="tj_tba" id="selectConditions">
					</table>
				</div>

			  </div>
		 </div>
	
	</div>
	
	<div class="footera">
	  <div class="qra"><a href="javascript:void(0);" onclick="clickOK();">确定</a></div>
	</div>

</div>


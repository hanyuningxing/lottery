
<script type="text/javascript">
	function gotoUnder20(){
		var scheme_list_form = document.getElementById("scheme_list_form");
		scheme_list_form["queryForm.costType"].value=3;
		scheme_list_form.submit();
		return false;
	}
	function showSecrect(){
		var scheme_list_form = document.getElementById("scheme_list_form");
		if(document.getElementById("queryForm.secretType").checked){
			document.getElementById("queryForm.secretType").value="FULL_SECRET";
		}else{
			document.getElementById("queryForm.secretType").value="FULL_PUBLIC";
		}
		scheme_list_form.submit();
		return false;
	}
	function checkChooseType(chooseType){
		var user = '<#if user??>${user}</#if>';
		if(chooseType==2&&(user==''||user==null)){
			$SSO.login_auth();
			return false;
		}
		document.getElementById("scheme_list_form").submit();
		return false;
	}
	
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td width="80" align="center"><b>方案筛选</b></td>
	  <td width="450">第
	    <select name="periodNumber" onchange="this.form.submit();">
			<#list periods as p>
				<#if p.periodNumberDisplay??>
					   <#if 0==p.periodNumberDisplay>
					        <!-- 历史期-->
					       <option style="color:gray;" <#if period.id=p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber!}期&nbsp;历史期</option>
					       <#elseif 1==p.periodNumberDisplay>
					       <option style="color:red;" <#if period.id=p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber!}期&nbsp;当前期</option>
					        <!-- 当前期-->
					       <#elseif 2==p.periodNumberDisplay>
					        <!-- 预售期-->
					        <option style="color:#000000" <#if period.id=p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber!}期&nbsp;预售期</option>
					   </#if>
				  <#else>
				 	 <option <#if period.id=p.id>selected="selected"</#if> value="${p.id}">${p.periodNumber!}期</option>
				  </#if>
		    </#list>
		  </select> 期
	      <select name="queryForm.schemeState" onchange="this.form.submit();">
			<option value="">全部</option>
	        <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="SUCCESS">selected="selected"</#if> value="SUCCESS">成功</option>
	         <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="FULL">selected="selected"</#if> value="FULL">满员</option>
	          <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="UNFULL">selected="selected"</#if> value="UNFULL">未满</option>
	        <option <#if queryForm?? && queryForm.schemeState?? && queryForm.schemeState=="CANCEL">selected="selected"</#if> value="CANCEL">撤销</option>
	      </select>
	      <select name="queryForm.costType" onchange="this.form.submit();">
	        <option value="0" <#if queryForm?? && queryForm.costType?? && queryForm.costType==0>selected="selected"</#if>>方案金额</option>
	        <option value="1" <#if queryForm?? && queryForm.costType?? && queryForm.costType==1>selected="selected"</#if>>千元以下</option>
	        <option value="2" <#if queryForm?? && queryForm.costType?? && queryForm.costType==2>selected="selected"</#if>>千元以上</option>
	        <option value="3" <#if queryForm?? && queryForm.costType?? && queryForm.costType==3>selected="selected"</#if>>不限</option>
	      </select>
	      <select name="queryForm.chooseType" onchange="checkChooseType(this.options[this.selectedIndex].value);">
	        <option value="0" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==0>selected="selected"</#if>>方案类型</option>
	        <option value="1" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==1>selected="selected"</#if>>已保底</option>
	        <option value="2" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==2>selected="selected"</#if>>我的方案</option>
	        <option value="3" <#if queryForm?? && queryForm.chooseType?? && queryForm.chooseType==3>selected="selected"</#if>>全部</option>
	      </select>
	  </td>
	  <td width="240"><input type="checkbox" name="queryForm.secretType" value="FULL_PUBLIC" <#if flag??&&flag!=0>checked="checked"</#if> id="queryForm.secretType" onclick="showSecrect();"/>显示保密方案
	  	<input value="<#if queryForm??&&queryForm.sponsorName??>${queryForm.sponsorName}<#else>用户名或方案号</#if>" size="15" onclick="this.value='';" id="queryForm.sponsorName" name="queryForm.sponsorName" value="<#if queryForm??>${queryForm.sponsorName!}</#if>">
	  </td>
	  <td align="left"><a href="javascript:;" class="hmsearchbt" onclick="$$('scheme_list_form').submit();"></a></td>
	</tr>
</table>
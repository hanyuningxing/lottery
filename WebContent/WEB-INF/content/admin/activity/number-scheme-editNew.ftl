<#assign menu_type='scheme_editNew' />
<#assign isSingle=salesMode=='SINGLE' />

<@override name="title">
	<title>${lotteryType.lotteryName}${salesMode.modeName}投注</title>
</@override>
 
<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/schemeInit.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/chase.js"/>"></script>
	<#if isSingle><script type="text/javascript" src="<@c.url value= "/js/lottery/singleInit.js"/>"></script></#if>
	<@block name="editNewHead"></@block>
</@override>

<@override name="content">
   	<div class="cleanboth k3px">
   		<@block name="content.top"></@block>
   		<@block name="content.periods">
		  <div class="all8px">
	         <form action="<@c.url value="${base}/admin/activity/${lotteryType.key}/scheme!editNew.action" />" method="get">
	         
				<#list periods as p>
					<#if p.periodNumberDisplay??>
					    <#if 1==p.periodNumberDisplay>
							<label class="inputcheckbox orgchar"><input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>(第${p.periodNumber!}期)当前期</label>
						</#if>
					</#if>
				</#list>
				<#list periods as p>
					<#if p.periodNumberDisplay??>
					    <#if 1==p.periodNumberDisplay>
							
						<#elseif 2==p.periodNumberDisplay>
					    	<label class="inputcheckbox"><input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>(第${p.periodNumber!}期)预售期</label>
	                   	</#if>
					<#else>
	                   	<label class="inputcheckbox"><input name="periodNumber" type="radio" value="${p.periodNumber!}"  <#if period.id=p.id>checked="checked"</#if> onclick="this.form.submit();"/>(第${p.periodNumber!}期)</label>
					</#if>
				</#list>
				<input type="hidden" name="salesMode" value="${salesMode}" />
				<#if shareType??><input type="hidden" name="shareType" value="${shareType}" /></#if>
				<#if playType??><input type="hidden" id="playType" name="playType" value="${playType}" /></#if>
	         </form>
	      </div>
   		</@block>
   		<@block name="content.play_caption"></@block>
   		<@block name="content.select"></@block>
   		<@block name="content.form">
	   		<form id="createForm" action="<@c.url value="/admin/activity/${lotteryType.key}/scheme!create.action" />" method="post" autocomplete="off">
   				<#if isSingle>
					<div class="cleanboth">
				    	<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" class="rig10">
						    <tr>
						    	<td width="120" align="right">方案类型</td>
						       	<td align="left">
						       		<label><input type="radio" name="singleUploadType" onclick="chooseSingleUploadType2(0);" checked="true"/>上传方案</label>
						       		<label><input id="createForm_singleUploadType_1" type="radio" name="singleUploadType" onclick="chooseSingleUploadType2(1);"/>手工录入</label>
						     	</td>
						    </tr>
				    	</table>   
				  	</div>
			      	<div class="cleanboth" id="file_upload_div">
			        	<table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" class="rig10">
			          		<tr>
				           		<td width="120" align="right">上传方案</td>
						        <td align="left">
							        <input type="file" id="upfile" name="createForm.upload" onchange="chgUploadFile(this)" />
							        <input type="button" id="calcSubmit_uploadFile" value="自动计算金额" disabled="disabled" onclick="countUploadFileMoney()" class="btn" />
							        <input type="hidden" name="createForm.fileUpload" value="true" />
							        <span id="span_calUploadFileMoney" style="display:none;">正在计算，请稍等...</span>
   									<@block name="content.sample"></@block>
						   		</td>
			          		</tr>
			          		<tr>
					            <td align="right">&nbsp;</td>
					            <td align="left">
					            	<div class=" cleanboth all5px lineh20" style="background:#FFFCF3; border:1px dashed #F2D8C0; width:553px;" > 
						                1、请严格参照“标准格式样本”格式上传方案，否则网站不保证为您做过关统计以及历史战绩统计。<br />
						                2、文件格式必须是文本文件。<br />
						                3、由于上传的文件较大，会导致上传时间及在本页面停留时间较长，请耐心等候。 
					               	</div>
					            </td>
			          		</tr>
			        	</table>
			      	</div>
			  		<div style="clear:both;display:none;" id="text_write_div">
					    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" class="rig10">
					      <tr> 
					      	<td width="120" align="right">手工录入</td>
					        <td align="left"><textarea id="createForm_content" cols="60" rows="10" style="border:1px solid #A7CAED;" onblur="countTextAreaMoney();">${single_content!}</textarea></td>
					      </tr>
					      <tr>
				           		<td width="120" align="right"></td>
						        <td align="left"><@block name="content.sample"></@block></td>
			          	  </tr>
					    </table>
			  		</div>
   				</#if>
			    <#assign canChase=true />
			    <@block name="content.form.extra"></@block>
			    <#include '/WEB-INF/content/admin/activity/init_common_new.ftl' />
			</form>
   		</@block>
	</div>
</@override>
<@override name="title">
	<title>${scheme.periodNumber}期${scheme.lotteryType.lotteryName}${scheme.mode.modeName}${scheme.shareType.shareName}方案-[${scheme.schemeNumber}]上传方案内容</title>
</@override>
 
<@override name="head">
	<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<@c.url value= "/js/lottery/singleInit.js"/>"></script>
	<@block name="editUploadHead"></@block>
</@override>
  
<@override name="mainContent">
	<div class="fd_border">
		<form id="uploadForm" action="<@c.url value="/${scheme.lotteryType.key}/scheme!upload.action" />" method="post" autocomplete="off">
		    <input type="hidden" name="uploadForm.schemeId" value="${scheme.id}"/>
		    <@block name="uploadExtraData"></@block>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr class="fd_trtop" >
			    	<td height="27" align="left" colspan="2" class="l10 d20"><strong>上传方案内容</strong></td>
			    </tr>
			    <tr class="fd_trw">
			      	<td height="24" width="110" align="center" class="fd_trg">方案编号</td>
			      	<td align="left"><a href="${base}/${scheme.lotteryType.key}/scheme-${scheme.schemeNumber}.html">${scheme.schemeNumber}</a></td>
			    </tr>
			    <tr class="fd_trw">
			      	<td height="24" width="110" align="center" class="fd_trg">方案金额</td>
			      	<td align="left"><span class="red">￥${scheme.schemeCost!}</td>
			    </tr>
			    <tr class="fd_trw">
			      	<td height="24" width="110" align="center" class="fd_trg">方案倍数</td>
			      	<td align="left"><span class="redc">${scheme.multiple!}</span>倍</td>
			    </tr>
			    <tr class="fd_trw">
			      	<td height="24" width="110" align="center" class="fd_trg">方案注数</td>
			      	<td align="left"><span class="redc">${scheme.units!}</span>注</td>
			    </tr>
			    <tr class="fd_trw">
			        <td height="24" width="110" align="center" class="fd_trg">上传方案</td>
			        <td align="left">
			        	<input type="file" id="upfile" name="uploadForm.upload" style="border:1px solid #afbbc7; height:22px;"/>
			        	<input type="hidden" name="uploadForm.fileUpload" value="true" />
			        </td>
			    </tr>
		      	<tr height="24" class="fd_trw">
		        	<td height="24" align="center" class="boldchar">&nbsp;</td>
		        	<td height="80" align="left">
		        		<span id="span_uploadForm_submit"><input onclick="submitUploadForm();" type="button" value="上传方案" id="formSubmit" /></span>
			  			<span id="span_uploadForm_waiting" style="display:none;">正在提交,请稍等...</span>
		        	</td>
		      	</tr>
		    </tbody></table>
		</form>	
	</div>	
</@override>

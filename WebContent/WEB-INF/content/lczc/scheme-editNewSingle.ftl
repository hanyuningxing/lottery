<@override name="editNewHead">
	<script type="text/javascript" src="<@c.url value= "/js/lottery/singleInit.js"/>"></script>
</@override>

<@override name="initContentOfSingle">
	  <tr>
	    <td class="zc_tdlblue" height="30" width="120">上传类型</td>
	    <td class="zc_tdlwhite">
	    	<label><input <#if !aheadOfUpload>checked="checked"</#if> name="uploadType" onclick="chooseSingleUploadType(0);" type="radio" autocomplete="off"/>文件上传</label>
	      	<label><input <#if aheadOfUpload>checked="checked"</#if> name="uploadType" onclick="chooseSingleUploadType(1);" type="radio" autocomplete="off"/>发起后再上传方案</label>
	      	<input type="hidden" name="createForm.fileUpload" value="true" />
			<input type="hidden" name="createForm.aheadOfUploadContent" value="false"/>
	    </td>
	  </tr>
	  <tr id="tr_uploadFile">
	    <td class="zc_tdlblue" height="30">文件上传</td>
	    <td class="zc_tdlwhite">
	    	<input type="file" name="createForm.upload" id="createForm_upload" onchange="chgUploadFile(this);" style="border:1px solid #afbbc7; height:22px;" />
	    	格式说明： 胜→3  平→1  负→0
	    	<input type="button" id="calcSubmit_uploadFile" class="btn" value="重新计算注数" disabled="disabled" onclick="countUploadFileMoney()" />
	    	<span id="span_calUploadFileMoney" style="display:none;">正在计算注数...</span>
	    </td>
	  </tr>
</@override>

<@override name="initUnitSpan">
	<span id="input_createForm_units" style="display:none"><input id="ahead_createForm_units" onkeyup="if(/\d+/.test(this.value)){updateBetUnits(parseInt(this.value));}else{updateBetUnits(0);}" class="kuanginput" size="10" maxLength="5" onkeydown="number_check(this,event,0);" oncontextmenu="return false;" autocomplete="off" style="IME-MODE: disabled;" />注</span>
</@override>

<@override name="submitAct"><span id="span_createForm_submit"><a id="formSubmit" onclick="submitCreateForm();return false;" href="javascript:;" class="btbuy"></a></span><span style="display: none;" id="span_createForm_waiting">正在提交,请稍等...</span></@override>

<@extends name="base.ftl"/>

<script type="text/javascript">
  var uploadType=0;
  <#if aheadOfUpload>uploadType=1;</#if>
  chooseSingleUploadType(uploadType);
</script>
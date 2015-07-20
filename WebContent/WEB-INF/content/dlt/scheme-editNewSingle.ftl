<@override name="title">
	<title>体彩大乐透玩法，大乐透购买购买投注 - ${webapp.webName}安全购彩平台</title>
</@override>
<#if (shrinkBean.content)??>
	<#assign single_content=shrinkBean.content />
	<script type="text/javascript">
		$(document).ready(function(){
			$('#createForm_singleUploadType_1').trigger('click');
			countTextAreaMoney();
		});
	</script>
</#if>
<@override name="content.sample">&nbsp;标准格式样本<#if salesPlayType='Select12to2'>01,02<#else>01,02,03,04,05,01,02</#if><br></@override>
<@extends name="scheme-editNew.ftl"/> 
<@extends name="base.ftl"/>
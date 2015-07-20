<#assign __timeout=8000 />
<#if actionErrors?? && actionErrors?size gt 0>
	<div id="actionErrors" class="error">
		<#list actionErrors as err>
			<img src="<@c.url value="/images/iconWarning.gif" />" alt="icon.warning" class="icon" onerror="this.outerHTML='';" /><#rt/>
			<#lt/>${err!}<br />
		</#list>
	</div>
	<script type="text/javascript" >
		window.setTimeout(function(){
			document.getElementById("actionErrors").style.display = 'none';
		},${__timeout});
	</script>
</#if>

<#if fieldErrors?? && fieldErrors?size gt 0>
	<div id="fieldErrors" class="error">
		<#list fieldErrors.entrySet() as entry>
			<img src="<@c.url value="/images/iconWarning.gif" />" alt="icon.warning" class="icon" onerror="this.outerHTML='';" /><#rt/>
			<#lt/><strong>${entry.key!}：</strong><br />
			<#if entry.value??>
				<div style="padding-left:30px;">
					<#list entry.value as err>
					--${err}<br/>
					</#list>
				</div>
			</#if>
		</#list>
	</div>
	<script type="text/javascript" >
		window.setTimeout(function(){
			document.getElementById("fieldErrors").style.display = 'none';
		},${__timeout});
	</script>
</#if>

<#if actionMessages?? && actionMessages?size gt 0>
	<div id="actionMessages" class="message">
		<#list actionMessages as msg>
			<img src="<@c.url value="/images/iconInformation.gif" />" alt="icon.information" class="icon" onerror="this.outerHTML='';" /><#rt/>
			<#lt/>${msg!}<br />
		</#list>
	</div>
	<script type="text/javascript" >
		window.setTimeout(function(){
			document.getElementById("actionMessages").style.display = 'none';
		},${__timeout});
	</script>
</#if>
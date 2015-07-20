<table width="98%" cellspacing="0" cellpadding="0" border="0" class="hemain_filter">
	<tbody><tr>
	  <td align="left">
	      <input type="button" value="近20期" name="twenty" class="bd_upload" onclick="location.href='${base}/${lotteryType.key}/result.action?count=20<#if playType??>&playType=${playType}</#if>&menuType=3';"/>
	      <input type="button" value="近30期" name="thirty" class="bd_upload" onclick="location.href='${base}/${lotteryType.key}/result.action?count=30<#if playType??>&playType=${playType}</#if>&menuType=3';"/>
	      <input type="button" value="近50期" name="fifty" class="bd_upload" onclick="location.href='${base}/${lotteryType.key}/result.action?count=50<#if playType??>&playType=${playType}</#if>&menuType=3';"/>
     </td>
	  <td align="right">&nbsp;
	  </td>
	</tr>
</tbody>
</table>
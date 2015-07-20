<head>
	<title>中奖名单编辑</title>
	<meta name="menu" content="activity"/>
	<meta name="menuItem" content="editListOfWinner"/>
</head>
<div class="twonavgray">
	<div style="padding:0px 0px 0px 15px;"><span class="chargraytitle">中奖名单编辑</span></div>
</div>
<script type="text/javascript" src="<@c.url value= "/js/jquery/jquery-1.4.2.min.js"/>"></script>

<#assign lotteryTypeArr=stack.findValue("@com.cai310.lottery.common.Lottery@values()") />

<script type="text/javascript">
     var index=0;
     function addRow(){
     		var selectObj = document.getElementById('select_content');
     		selectAddOption(selectObj);    
     }
	
	function delRow(id){
		var tagId = "tag"+id;
		$("#"+tagId).remove();
		index--;	
	}
	
	function selectAddOption(ulObj) {
		var valueTemp = $("#select_content").html();	
		
		var tagId = "tag"+index;	
		var tagNameId = "tagName_"+index;
		var tagUrlId = "tagUrl_"+index;
		var playTypeId = "playType_"+index;
		
		var innerValue="<tr bgcolor='#FFFFFF' id="+tagId+">";
            innerValue+="<td width='130' align='center' bgcolor='#F5f5f5'>中奖用户名：</td>";
            innerValue+="<td bgcolor='#FFFFFF'><span class='ssq_tr_2'>";
            innerValue+="<input type='text' id="+tagNameId+" value=''/>&nbsp;&nbsp;"       
            innerValue+="中奖金额:<input type='text' id="+tagUrlId+" value=''/>&nbsp;&nbsp;玩法:<input type='text' id="+playTypeId+" value=''/><input type='button' id="+index+" value='删除一行' onclick=\"delRow(this.id);\"/></span></td></tr>";
            			
		$("#select_content").html(valueTemp+innerValue);	
		//document.getElementById('select_content').innerHTML=valueTemp+innerValue;
		
		index++;
	}
	
	function publicTag(){
		var params = "";
		var flag = false;
		for(var i=0;i<index;i++){
			var tagName = "tagName_"+i;
			var tagUrl = "tagUrl_"+i;	
			var playTypeId = "playType_"+i;
				
			var tagNameValue = $("#"+tagName).val();
			var tagUrlValue = $("#"+tagUrl).val();
			var playTypeValue = $("#"+playTypeId).val();
			
			if(''==tagNameValue||''==tagUrlValue){
				flag = true;
			}
			
			params+=tagNameValue+","+playTypeValue+","+tagUrlValue;
			if(i<index-1)
				params+="#";
		}
		if(flag){
			alert("内容不能为空");
			return ;
		}
		$("#params").val(params);
		$("#indexInfoSaveForm").submit();	
	}
</script>

<form method="post" id="indexInfoSaveForm" action="<@c.url value="/admin/activity/winner/winner!publicTag.action" />" onsubmit="return indexInfoSaveMethod();">
	<input type="hidden" name="params" id="params" value=""/>
	<table id="tbTag" width="70%" border="0" cellpadding="4" cellspacing="1" bgcolor="#E6E2D6">
	        <tr bgcolor="#F5f5f5">
              <td align="center" valign="top">&nbsp;</td>
              <td bgcolor="#F5f5f5">
                 		<input type="button" value="新增一行" onclick="addRow();"/>    
                 		<input type="button" value="发布" onclick="publicTag();"/>            		           		        		
              </td>
            </tr>
             <tr bgcolor="#FFFFFF">
              <td width="130" align="center" bgcolor="#F5f5f5">彩种：</td>
              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
              		<select name="lotteryType" size="1">
              		    <#list lotteryTypeArr as type>
							<option <#if lotteryType??&&lotteryType==type>selected="selected"</#if> value="${type}">${type.lotteryName}</option>
						</#list>
				   </select>
               </span></td>
            </tr>
                        <div id="select_content"></div>
            
      </table>            
    </form>

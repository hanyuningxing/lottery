<!--左边-->
<script type="text/javascript">
    function chooseInfo(name,clickName,obj){
          var divArr = $('#news_info_box div[_name="'+name+'"]');
          for(i=0;i<divArr.length;i++){
               divArr[i].style.display="none";
          }
          var aArr = $('#news_info_box a[_name="'+name+'"]');
          for(i=0;i<aArr.length;i++){
               aArr[i].className=""
          }
          var clickDiv = document.getElementById(clickName);
          clickDiv.style.display="";
          obj.className="now"
    }
</script>
<div class="w980"  id="news_info_box">
<div class="w980">
  <div class="index_left">
    <div class="left_box1">
      <div class="box_ban">  福利彩票预测</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now" _name="FORECAST1" onmouseover="chooseInfo('FORECAST1','SSQ_FORECAST',this);return false;">双色球</a></li>
          <li><a href="javascript:void(0);" _name="FORECAST1" onmouseover="chooseInfo('FORECAST1','WELFARE3D_FORECAST',this);return false;">福彩3D</a></li>
          <li><a href="javascript:void(0);" _name="FORECAST1" onmouseover="chooseInfo('FORECAST1','WELFARE36To7_FORECAST',this);return false;">南粤风采</a></li>
        </ul>
      </div>
                <#macro news_list news>
				       <li> <span class="listrig"><#if news??&&news.createTime??>${news.createTime?string("yy-MM-dd hh:mm")}</#if></span><a <#if news??&&news.titleColor??>class="${news.titleColor.colorStyle}"</#if> href="<#if news??&&news.titleLink??>${news.titleLink}<#else>${base}/info/news-${news.id!}.html</#if>" target="_blank">${news.titleString!}</a> </li>
				</#macro>
                <div id="SSQ_FORECAST" _name="FORECAST1">
		            <#if SSQ_FORECAST?? && SSQ_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SSQ_FORECAST as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
					</#if>
						 <div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=SSQ" target="_blank" class="boxdown_charr">更多双色球预测>></a></div>
			     </div>
			    <div id="WELFARE3D_FORECAST" style="display:none" _name="FORECAST1">
		            <#if WELFARE3D_FORECAST?? && WELFARE3D_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list WELFARE3D_FORECAST as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
						 <div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=WELFARE3D" class="boxdown_charr">更多福彩3D预测>></a></div>
			     </div>
			     <div id="WELFARE36To7_FORECAST" style="display:none" _name="FORECAST1">
		            <#if WELFARE36To7_FORECAST?? && WELFARE36To7_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list WELFARE36To7_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=WELFARE36To7" target="_blank" class="boxdown_charr">更多南粤风采预测>></a></div>
			     </div>
    </div>
    <div class="rig_box1">
      <div class="box_ban">  竞技彩分析与预测</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now" style="width:65px;" _name="FORECAST2" onmouseover="chooseInfo('FORECAST2','DCZC_FORECAST',this);return false;">北京单场</a></li>
          <li><a href="javascript:void(0);" _name="FORECAST2" onmouseover="chooseInfo('FORECAST2','SFZC_FORECAST',this);return false;">胜负彩</a></li>
          <li><a href="javascript:void(0);"  style="width:65px;" _name="FORECAST2" onmouseover="chooseInfo('FORECAST2','LCZC_FORECAST',this);return false;">六场半</a></li>
          <li><a href="javascript:void(0);" _name="FORECAST2" onmouseover="chooseInfo('FORECAST2','SCZC_FORECAST',this);return false;">进球彩</a></li>
        </ul>
      </div>
      <div id="DCZC_FORECAST"  _name="FORECAST2">
		            <#if DCZC_FORECAST?? && DCZC_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list DCZC_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=DCZC" target="_blank" class="boxdown_charr">更多北京单场预测>></a></div>
	  </div>
      <div id="SFZC_FORECAST"  style="display:none" _name="FORECAST2">
		            <#if SFZC_FORECAST?? && SFZC_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SFZC_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=SFZC" target="_blank" class="boxdown_charr">更多胜负彩预测>></a></div>
	  </div>
      <div id="LCZC_FORECAST"  style="display:none" _name="FORECAST2">
		            <#if LCZC_FORECAST?? && LCZC_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list LCZC_FORECAST as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=LCZC" target="_blank" class="boxdown_charr">更多六场半预测>></a></div>
	  </div>
      <div id="SCZC_FORECAST"  style="display:none" _name="FORECAST2">
		            <#if SCZC_FORECAST?? && SCZC_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SCZC_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=SCZC" target="_blank" class="boxdown_charr">更多进球彩预测>></a></div>
	  </div>
    </div>
    <div class="kong10"></div>
    <div class="left_box1">
      <div class="box_ban"> <span><a href="javascript:void(0);" class="boxdown_charr">更多>></a></span> 高频彩票技巧</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now"  _name="SKILLS1" onmouseover="chooseInfo('SKILLS1','SSC_SKILLS',this);return false;">时时彩</a></li>
          <li><a href="javascript:void(0);"  _name="SKILLS1" onmouseover="chooseInfo('SKILLS1','SSL_SKILLS',this);return false;">时时乐</a></li>
          <li><a href="javascript:void(0);"  _name="SKILLS1" onmouseover="chooseInfo('SKILLS1','KLSF_SKILLS',this);return false;">快乐十分</a></li>
          <li><a href="javascript:void(0);"  _name="SKILLS1" onmouseover="chooseInfo('SKILLS1','EL11TO5_SKILLS',this);return false;">11选5</a></li>
        </ul>
      </div>
            <div id="SSC_SKILLS" _name="SKILLS1">
		            <#if SSC_SKILLS?? && SSC_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SSC_SKILLS as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=SSC" target="_blank" class="boxdown_charr">更多时时彩技巧>></a></div>
	       </div>
	       <div id="SSL_SKILLS" style="display:none" _name="SKILLS1">
		            <#if SSL_SKILLS?? && SSL_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SSL_SKILLS as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=SSL" target="_blank" class="boxdown_charr">更多时时乐技巧>></a></div>
	       </div>
	       <div id="KLSF_SKILLS" style="display:none" _name="SKILLS1">
		            <#if KLSF_SKILLS?? && KLSF_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list KLSF_SKILLS as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=KLSF" target="_blank" class="boxdown_charr">更多快乐十分技巧>></a></div>
	       </div>
	       <div id="EL11TO5_SKILLS" style="display:none" _name="SKILLS1">
		            <#if EL11TO5_SKILLS?? && EL11TO5_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list EL11TO5_SKILLS as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=EL11TO5" target="_blank" class="boxdown_charr">更多11选5技巧>></a></div>
	       </div>
    </div>
    <div class="rig_box1">
      <div class="box_ban"> <span><a href="javascript:void(0);" class="boxdown_charr">更多>></a></span> 体育彩票预测</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now"  _name="FORECAST3" onmouseover="chooseInfo('FORECAST3','DLT_FORECAST',this);return false;">大乐透</a></li>
          <li><a href="javascript:void(0);"  _name="FORECAST3" onmouseover="chooseInfo('FORECAST3','PL_FORECAST',this);return false;">排列三/五</a></li>
          <li><a href="javascript:void(0);"  _name="FORECAST3" onmouseover="chooseInfo('FORECAST3','SEVEN_FORECAST',this);return false;">七星彩</a></li>
        </ul>
      </div>
      <div id="DLT_FORECAST" _name="FORECAST3">
		            <#if DLT_FORECAST?? && DLT_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list DLT_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=DLT" target="_blank" class="boxdown_charr">更多大乐透预测>></a></div>
	   </div>
	    <div id="PL_FORECAST" style="display:none" _name="FORECAST3">
		            <#if PL_FORECAST?? && PL_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list PL_FORECAST as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=PL" target="_blank" class="boxdown_charr">更多排列三/五预测>></a></div>
	   </div>   
	   <div id="SEVEN_FORECAST" style="display:none" _name="FORECAST3">
		            <#if SEVEN_FORECAST?? && SEVEN_FORECAST?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SEVEN_FORECAST as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=FORECAST&lottery=SEVEN" target="_blank" class="boxdown_charr">更多七星彩预测>></a></div>
	   </div>  
    </div>
    <div class="kong10"></div>
    <div class="left_box1">
      <div class="box_ban"> <span><a href="javascript:void(0);" class="boxdown_charr">更多>></a></span> 福利彩票技巧</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now" _name="SKILLS2" onmouseover="chooseInfo('SKILLS2','SSQ_SKILLS',this);return false;">双色球</a></li>
          <li><a href="javascript:void(0);" _name="SKILLS2" onmouseover="chooseInfo('SKILLS2','WELFARE3D_SKILLS',this);return false;">福彩3D</a></li>
          <li><a href="javascript:void(0);" _name="SKILLS2" onmouseover="chooseInfo('SKILLS2','WELFARE36To7_SKILLS',this);return false;">南粤风采</a></li>
        </ul>
      </div>
       <div id="SSQ_SKILLS" _name="SKILLS2">
		            <#if SSQ_SKILLS?? && SSQ_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SSQ_SKILLS as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=SSQ" target="_blank" class="boxdown_charr">更多双色球技巧>></a></div>
	   </div>  
	   <div id="WELFARE3D_SKILLS"  style="display:none" _name="SKILLS2">
		            <#if WELFARE3D_SKILLS?? && WELFARE3D_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list WELFARE3D_SKILLS as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=WELFARE3D" target="_blank" class="boxdown_charr">更多福彩3D技巧>></a></div>
	   </div>  
	   <div id="WELFARE36To7_SKILLS"  style="display:none" _name="SKILLS2">
		            <#if WELFARE36To7_SKILLS?? && WELFARE36To7_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list WELFARE36To7_SKILLS as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=WELFARE36To7" target="_blank" class="boxdown_charr">更多南粤风采技巧>></a></div>
	   </div>  
    </div>
    <div class="rig_box1">
      <div class="box_ban"> <span><a href="javascript:void(0);" class="boxdown_charr">更多>></a></span> 体育彩票技巧</div>
      <div class="box_tbg">
        <ul class="titlecz">
          <li><a href="javascript:void(0);" class="now" _name="SKILLS3" onmouseover="chooseInfo('SKILLS3','DLT_SKILLS',this);return false;">大乐透</a></li>
          <li><a href="javascript:void(0);" _name="SKILLS3" onmouseover="chooseInfo('SKILLS3','PL_SKILLS',this);return false;">排列三/五</a></li>
          <li><a href="javascript:void(0);" _name="SKILLS3" onmouseover="chooseInfo('SKILLS3','SEVEN_SKILLS',this);return false;">七星彩</a></li>
        </ul>
      </div>
      <div id="DLT_SKILLS"  _name="SKILLS3">
		            <#if DLT_SKILLS?? && DLT_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list DLT_SKILLS as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=DLT" target="_blank" class="boxdown_charr">更多大乐透技巧>></a></div>
	   </div>  
	   <div id="PL_SKILLS"  style="display:none" _name="SKILLS3">
		            <#if PL_SKILLS?? && PL_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list PL_SKILLS as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=PL" target="_blank" class="boxdown_charr">更多排列三/五技巧>></a></div>
	   </div>  
	   <div id="SEVEN_SKILLS"  style="display:none" _name="SKILLS3">
		            <#if SEVEN_SKILLS?? && SEVEN_SKILLS?size gt 0>
				     	
						      <div class="all10px">
						        <ul class="inbox_list">
						        <#list SEVEN_SKILLS as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
					<div class="boxdown"><a href="${base}/info/news!infoList.action?type=SKILLS&lottery=SEVEN" target="_blank" class="boxdown_charr">更多七星彩技巧>></a></div>
	   </div>  
    
    </div>
  </div>
  <!--右边-->
  <div class="index_rig">
    <div><img src="${base}/pages/images/001ad.jpg" /></div>
    <div class="kong10"></div>
    <div><img src="${base}/pages/images/002ad.jpg" /></div>
    <div class="kong10"></div>
    <div><img src="${base}/pages/images/001ad.jpg" /></div>
  </div>
</div>
<div class="kong10"></div>
<div class="w980">
  <div class="downboxb1">
    <div class="downbgbox"> <span><a href="javascript:void(0);" class="boxdown_charr">更多开奖公告>></a></span> 最新开奖公告 </div>
    <table width="978" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="345" valign="top"><div class="box_tbg">
            <ul class="titlecz">
              <li><a href="javascript:void(0);" class="now" _name="RESULTNOTICE1" onmouseover="chooseInfo('RESULTNOTICE1','SSQ_RESULTNOTICE',this);return false;">双色球</a></li>
              <li><a href="javascript:void(0);"  _name="RESULTNOTICE1" onmouseover="chooseInfo('RESULTNOTICE1','WELFARE3D_RESULTNOTICE',this);return false;">福彩3D</a></li>
              <li><a href="javascript:void(0);"  _name="RESULTNOTICE1" onmouseover="chooseInfo('RESULTNOTICE1','WELFARE36To7_RESULTNOTICE',this);return false;">南粤风采</a></li>
            </ul>
          </div>
          <div id="SSQ_RESULTNOTICE" _name="RESULTNOTICE1">
		            <#if SSQ_RESULTNOTICE?? && SSQ_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list SSQ_RESULTNOTICE as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
	       </div>  
	       <div id="WELFARE3D_RESULTNOTICE"  style="display:none" _name="RESULTNOTICE1">
		            <#if WELFARE3D_RESULTNOTICE?? && WELFARE3D_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list WELFARE3D_RESULTNOTICE as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
					</#if>
	       </div>  
	        <div id="WELFARE36To7_RESULTNOTICE"  style="display:none" _name="RESULTNOTICE1">
		            <#if WELFARE36To7_RESULTNOTICE?? && WELFARE36To7_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list WELFARE36To7_RESULTNOTICE as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
					</#if>
	       </div>  
        </td>
        <td width="10"><img src="${base}/pages/images/ishuline.gif" /></td>
        <td width="345" valign="top"><div class="box_tbg">
            <ul class="titlecz">
              <li><a href="javascript:void(0);" class="now" _name="RESULTNOTICE2" onmouseover="chooseInfo('RESULTNOTICE2','DLT_RESULTNOTICE',this);return false;">大乐透</a></li>
              <li><a href="javascript:void(0);" _name="RESULTNOTICE2" onmouseover="chooseInfo('RESULTNOTICE2','PL_RESULTNOTICE',this);return false;">排列三/五</a></li>
              <li><a href="javascript:void(0);" _name="RESULTNOTICE2" onmouseover="chooseInfo('RESULTNOTICE2','SEVEN_RESULTNOTICE',this);return false;">七星彩</a></li>
            </ul>
          </div>
              <div id="DLT_RESULTNOTICE" _name="RESULTNOTICE2">
		            <#if DLT_RESULTNOTICE?? && DLT_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list DLT_RESULTNOTICE as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
	          </div>  
	          <div id="PL_RESULTNOTICE" _name="RESULTNOTICE2" style="display:none">
		            <#if PL_RESULTNOTICE?? && PL_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list PL_RESULTNOTICE as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
	          </div>  
	          <div id="SEVEN_RESULTNOTICE" _name="RESULTNOTICE2" style="display:none">
		            <#if SEVEN_RESULTNOTICE?? && SEVEN_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list SEVEN_RESULTNOTICE as data>
						          <@news_list news=data />
						       </#list>
						        </ul>
						      </div>
						 
					</#if>
	          </div>  
          </td>
        <td width="10"><img src="${base}/pages/images/ishuline.gif" /></td>
        <td valign="top"><div class="box_tbg" style="width:262px;">
            <ul class="titlecz">
              <li><a href="javascript:void(0);" class="now" _name="RESULTNOTICE3" onmouseover="chooseInfo('RESULTNOTICE3','SSC_RESULTNOTICE',this);return false;">时时彩</a></li>
              <li><a href="javascript:void(0);" _name="RESULTNOTICE3" onmouseover="chooseInfo('RESULTNOTICE3','SSL_RESULTNOTICE',this);return false;">时时乐</a></li>
              <li><a href="javascript:void(0);" _name="RESULTNOTICE3" onmouseover="chooseInfo('RESULTNOTICE3','EL11TO5_RESULTNOTICE',this);return false;">11选5</a></li>
              <li><a href="javascript:void(0);" _name="RESULTNOTICE3" onmouseover="chooseInfo('RESULTNOTICE3','KLSF_RESULTNOTICE',this);return false;">快乐十分</a></li>
            </ul>
          </div>
          <div id="SSC_RESULTNOTICE"  _name="RESULTNOTICE3">
		            <#if SSC_RESULTNOTICE?? && SSC_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list SSC_RESULTNOTICE as data>
						          <@news_list news=data />
						       </#list>
						        </ul>
						      </div>
						 
					</#if>
	       </div>  
	       <div id="SSL_RESULTNOTICE"  _name="RESULTNOTICE3" style="display:none">
		            <#if SSL_RESULTNOTICE?? && SSL_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list SSL_RESULTNOTICE as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
	       </div>  
	       <div id="EL11TO5_RESULTNOTICE"  _name="RESULTNOTICE3" style="display:none">
		            <#if EL11TO5_RESULTNOTICE?? && EL11TO5_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list EL11TO5_RESULTNOTICE as data>
						          <@news_list news=data />
						         </#list>
						        </ul>
						      </div>
						
					</#if>
	       </div>  
	       <div id="KLSF_RESULTNOTICE"  _name="RESULTNOTICE3" style="display:none">
		            <#if KLSF_RESULTNOTICE?? && KLSF_RESULTNOTICE?size gt 0>
				     	
						      <div class="all5px">
						        <ul class="inbox_list">
						        <#list KLSF_RESULTNOTICE as data>
						          <@news_list news=data />
						        </#list>
						        </ul>
						      </div>
						 
					</#if>
	       </div>  
         </td>
      </tr>
    </table>
  </div>
  </div>
</div>
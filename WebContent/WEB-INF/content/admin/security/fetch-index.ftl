<title>后台数据抓取管理</title>
<meta name="menu" content="sysManage"/>
<meta name="menuItem" content="user"/>
<script type="text/javascript" src="<@c.url value= "./../../js/jquery/jquery-1.4.2.min.js"/>"></script>
<script type="text/javascript" src="<@c.url value= "./../../js/admin/fetch.js"/>"></script>
<script type="text/javascript">window.BASESITE='${base}';</script>
<div class="nowpalce">
	您所在位置：<a href="${base}/admin/security/user.action">彩票后台数据抓取管理</a> → <a href="${base}/admin/security/fetch.action">数据抓取管理</a>  <a href="${base}/adminLogin.jsp">退出登录</a>
</div>
<div class="twonavgray">
	<div >
        <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">后台数据抓取管理</span>
        </div>
     </div>
</div>
<div class="navgraybg" >
  <div class="choseban" style="float:left;">
  </div>
  <div style="float:right;padding:5px 5px 0px 0px;">
  <!--
    <a href="${base}/admin/security/user?entity.id=-1"><b>新增用户</b></a>
    -->
  </div>
</div>
<div >
     <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">双色球资讯-->复式分析</span>
        </div>
     </div>

	<div class="an_sleft" id="ssq_select"> 
				<input type="hidden" name="ssq_default" id="ssq_default" value="${ssq_default}"/>
                  <label class="sele" for="RadioGroup1_0"><input name="RadioGroup1" type="radio" id="RadioGroup1_0" value="1" checked="checked"/>号码投注统计</label>
                  <label for="RadioGroup1_1"><input type="radio" name="RadioGroup1" value="2" id="RadioGroup1_1"/>胆码投注统计</label>
                  <label for="RadioGroup1_2"><input type="radio" name="RadioGroup1" value="3" id="RadioGroup1_2" />杀号统计</label>
                  <span class="mar_right15">选择
                 <select name="date" id="expectselect">   
	                 <#list ssq as riqi>                                 
	                        <option value="${riqi}" <#if ssq_default==riqi>selected</#if>>${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>
                  <!--
                  	排序按
                  <select name="riqi" id="sortby">
                    <option  value="1" selected="selected" disabled="disabled">号码顺序</option>
                    <option  value="2" >大小顺序</option>
                  </select>
                  -->
                  <input type="button" value="确定" onclick="ssq();">
    </div>

	<div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">大乐透资讯-->复式分析</span>
    </div>
	<div class="an_sleft" id="dlt_select"> 
				<input type="hidden" name="dlt_default" id="dlt_default" value="${dlt_default}"/>
                  <label class="sele" for="dlt_1"><input name="RadioGroup2" type="radio" id="dlt_1" value="1" checked="checked"/>号码投注统计</label>
                  <label for="dlt_2"><input type="radio" name="RadioGroup2" value="2" id="dlt_2"/>胆码投注统计</label>
                  <span class="mar_right15">选择
                 <select name="date" id="dlt_expectselect">   
	                 <#list dlt as riqi>                                 
	                        <option value="${riqi}" >${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>            
                  <input type="button" value="确定" onclick="dlt();">
     </div>
     
    <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">福彩3D-->直选复式分析</span>
    </div>
	<div class="an_sleft" id="fc3d_select"> 
				<input type="hidden" name="fc3d_num_default" id="fc3d_num_default" value="${fc3d_num_default}"/>
                  <label class="sele" for="fc3d_num_1"><input name="RadioGroup3" type="radio" id="fc3d_num_1" value="1" checked="checked"/>按号码统计</label>
                  <label for="fc3d_num_2"><input type="radio" name="RadioGroup3" value="2" id="fc3d_num_2"/>按位置统计</label>          
                  <span class="mar_right15">选择
                 <select name="date" id="fc3d_num_expectselect">   
	                 <#list fc3d_num as riqi>                                 
	                        <option value="${riqi}" >${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>            
                  <input type="button" value="确定" onclick="fc3d_zx();">
     </div>
     
    <div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">福彩3D-->组选复式分析</span>
    </div>
	<div class="an_sleft" id="fc3d_select"> 
				  <input type="hidden" name="fc3d_group_default" id="fc3d_group_default" value="${fc3d_group_default}"/>
                  <span class="gray mar_right10">组选</span>
                  <label for="fc3d_group_1"><input type="radio" name="RadioGroup4" value="1" id="fc3d_group_1"  checked="checked"/>组选三</label>
                  <label for="fc3d_group_2"><input type="radio" name="RadioGroup4" value="2" id="fc3d_group_2" />组选六</label>
                  <br>
                  <span class="gray mar_right10">统计方式</span>
                  <label class="sele" for="fc3d_dz_2"><input name="RadioGroup5" type="radio" id="fc3d_dz_2" value="2" checked="checked" />按单注号码</label>
                  <label for="fc3d_dg_2"><input type="radio" name="RadioGroup5" value="1" id="fc3d_dg_2" />按单个号码</label>
                 <select name="date" id="fc3d_group_expectselect">   
	                 <#list fc3d_dz as riqi>                                 
	                        <option value="${riqi}" >${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>            
                  <input type="button" value="确定" onclick="fc3d_gx();">
     </div>
     
     
	<div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">数字排列-->直选复式分析</span>
    </div>
	<div class="an_sleft" id="szpl_select"> 
				<input type="hidden" name="szpl_num_default" id="szpl_num_default" value="${szpl_num_default}"/>
                  <label class="sele" for="szpl_num_1"><input name="RadioGroup6" type="radio" id="szpl_num_1" value="1" checked="checked"/>按号码统计</label>
                  <label for="szpl_num_2"><input type="radio" name="RadioGroup6" value="2" id="szpl_num_2"/>按位置统计</label>          
                  <span class="mar_right15">选择
                 <select name="date" id="szpl_num_expectselect">   
	                 <#list szpl_num as riqi>                                 
	                        <option value="${riqi}" >${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>            
                  <input type="button" value="确定" onclick="szpl_zx();">
     </div>     
	<div style="padding:0px 0px 0px 15px;">
          <span class="chargraytitle">数字排列-->组选复式分析</span>
    </div>
	<div class="an_sleft" id="szpl_select"> 
	 				<input type="hidden" name="szpl_group_default" id="szpl_group_default" value="${szpl_group_default}"/>
                  <span class="gray mar_right10">组选</span>
                  <label for="szpl_group_1"><input type="radio" name="RadioGroup7" value="1" id="szpl_group_1"  checked="checked"/>组选三</label>
                  <label for="szpl_group_2"><input type="radio" name="RadioGroup7" value="2" id="szpl_group_2" />组选六</label>
                  <br>
                  <span class="gray mar_right10">统计方式</span>
                  <label class="sele" for="szpl_dz_2"><input name="RadioGroup8" type="radio" id="szpl_dz_2" value="2" checked="checked" />按单注号码</label>
                  <label for="szpl_dg_2"><input type="radio" name="RadioGroup8" value="1" id="szpl_dg_2" />按单个号码</label>
                 <select name="date" id="szpl_group_expectselect">   
	                 <#list szpl_dz as riqi>                                 
	                        <option value="${riqi}" >${riqi}</option>                                                                           
	                  </#list>                      
                  </select>
                  	  期</span>            
                  <input type="button" value="确定" onclick="szpl_gx1();">
     </div>     
     </div>
  <div>
</div>
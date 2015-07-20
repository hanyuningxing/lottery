<head>
	<title>用户中心</title>
	<meta name="decorator" content="tradeV1" />
	<meta name="menu" content="scheme" />
	<link rel="stylesheet" href="<@c.url value="/pages/agent/css/right.css"/>" type="text/css" />
</head>

  <!--左边开始-->
  <div class="cleft">
      <#assign left_menu = 'scheme'/>
      <#include "left.ftl" />
  </div>
  <!--右边开始-->
  <div class="yhzxright">
  	 <#include "user-loginInfo.ftl"/>
	 <div class="left_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="2%"></td>
                <td width="3%"><img src="${base}/pages/agent/images/tip_04.jpg" /></td>
                <td width="95%">资金明细</td>
              </tr>
            </table>
</div>
      <div class="right_c">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #718598;">
          <tr>
            <td style="border-bottom:1px solid #718598;" height="44" bgcolor="#ebedf0">
              <div style="background-color:#d5f6fd; margin:1px; width:100%; height:44px">
              	  <form action="<@c.url value='/agent/user!fundList.action' />" method="get" id="queryForm">
              	  	 <input type="hidden" name="userId" value="${userId!}"/>
	                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                      <tr>
	                        <td width="5%" height="44">&nbsp;</td>
	                        <td width="5%">查询：</td>
	                        <td width="12%">
	                        	  <#assign timeFrame=timeFrame!0 />
							      <select name="timeFrame">
							          <option value="4" <#if timeFrame==4>selected="selected"</#if>>一天</option>
							          <option value="5" <#if timeFrame==5>selected="selected"</#if>>二天</option>
							          <option value="6" <#if timeFrame==6>selected="selected"</#if>>三天</option>
							          <option value="7" <#if timeFrame==7>selected="selected"</#if>>四天</option>
							          <option value="8" <#if timeFrame==8>selected="selected"</#if>>五天</option>
							          <option value="9" <#if timeFrame==9>selected="selected"</#if>>六天</option>
							          <option value="0" <#if timeFrame==0>selected="selected"</#if>>七天</option>
							          <option value="1" <#if timeFrame==1>selected="selected"</#if>>十五天</option>
							          <option value="2" <#if timeFrame==2>selected="selected"</#if>>一个月</option>
							          <option value="3" <#if timeFrame==3>selected="selected"</#if>>三个月</option>
							      </select>
	                        </td>
	                        <td width="5%">交易类型：</td>
	                        <td width="12%">
	                        	<#assign fundDetailTypeArr=stack.findValue("@com.cai310.lottery.common.FundDetailType@values()") />
				              	<select name="fundType">
									<option value="">全部</option>
									<#list fundDetailTypeArr as type>
									<option <#if fundType?? && fundType==type>selected="selected"</#if> value="${type}">${type.typeName}</option>
									</#list>
								</select>
	                        </td>
	                        <td><input type="submit" class="button" onclick="document.forms['queryForm'].submit();return false;" value="筛选" /> </td>
	                      </tr>
	                </table>
	            </form>
              </div>
            </td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <td style="text-align:left"><#include "${base}/common/message.ftl" /></td>
	     </tr>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="con_table">
          <tr>   
            <th width="28" scope="col">序 </th>
	        <th scope="col">进/出</th>
	        <th scope="col">交易类型</th>
	        <th scope="col">金额</th>
	        <th scope="col">账户余额</th>
	        <th scope="col">时间</th>
	        <th scope="col">备注</th>
          </tr>
           <#if pagination??&& (pagination.result![])?size gt 0>
		    	<#list pagination.result as data>
		    	<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
		    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
			        <td height="32">${data_index+1}</td>
			        <td>${data.mode.typeName}</td>
			        <td>${data.type.typeName}</td>
			        <td>#{(data.money!0);M2}</td>
			        <td>#{(data.resultMoney!0);M2}</td>
			        <td>${data.createTime?string('yyyy-MM-dd HH:mm')}</td>
			        <td style="text-align:left;padding:2px;">${data.remarkString!}</td>  
			    </tr>
		    	</#list>
		    <#else> 
		    <tr>
		      <td class="trw" align="center" colspan="7">无记录</td>
		    </tr>
	    </#if>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="${base}/pages/agent/images/right_02.jpg" height="32" style="border:1px solid #c3cdd5; border-top:none; text-align:right">
            	  <#import "../../macro/pagination.ftl" as b />
            	  <@b.page />
            </td>
          </tr>
        </table>


      </div>      
</div>
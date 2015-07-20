<#-- 导入JSTL的C标签 -->
<#global c=JspTaglibs["/WEB-INF/tlds/c.tld"]>
<#assign ss=JspTaglibs["/WEB-INF/tlds/security.tld"] />
<#global pagination_path='/WEB-INF/macro/pagination_admin.ftl' />
<#global webapp={
	"webName" : "球客",
	"company" : "深圳米域高科技有限公司",
	"domain" : "qiu310",
	"domainS" : "qiu310.com",
	"domainF" : "www.qiu310.com",
	"name" : "彩票系统",
	"email" : "miyugao@163.com",
	"phone" : "400-688-4944",
	"sy" : "0",
	"hmdt" : "1",
	"sjgc" : "2",
	"ggtj" : "3",
	"zjcx" : "4",
	"kjjg" : "5",
	"gsgz" : "6",
	"wzyh" : "7"
} />

<#global defaultDczcGameColor='#0066FF' /><#-- 单场赛事默认颜色 -->

<#macro buyProtocol>我已阅读了《<a href="javascript:void(0);" onclick="openPurchaseAgreement();return false;">用户合买代购协议</a>》并同意其中条款</#macro>
<#macro buyProtocolSimple>我已阅读《<a href="javascript:void(0);" onclick="openPurchaseAgreement();return false;">用户合买代购协议</a>》并同意其中条款</#macro>

<#-- 
权限设置说明：
ifAllGranted——是一个由逗号分隔的权限列表，用户必须拥有所有列出的权限才能渲染标签体；
ifAnyGranted——是一个由逗号分隔的权限列表，用户必须至少拥有其中的一个才能渲染标签体；
ifNotGranted——是一个由逗号分隔的权限列表，用户必须不拥有其中的任何一个才能渲染标签体。
-->
<#global globalMenus={
	"lotteryManager":{
		"labelName":"彩种管理",
		"roleId":0,
		"url":"/admin/lottery/sfzc/period!list.action",
		"items":{
            "SFZC":{
				"labelName":"胜负彩/任选九场",
				"url":"/admin/lottery/sfzc/period!list.action"
			},
            "LCZC":{
				"labelName":"六场半全场",
				"url":"/admin/lottery/lczc/period!list.action"
			},
            "SCZC":{
				"labelName":"进球彩",
				"url":"/admin/lottery/sczc/period!list.action"
			},
<#-- 			"DCZC":{
				"labelName":"北京单场",
				"url":"/admin/lottery/dczc/period!list.action"
			},-->
<#-- 			"SSQ":{
				"labelName":"双色球",
				"url":"/admin/lottery/ssq/period!list.action"
			},-->
			"DLT":{
				"labelName":"超级大乐透",
				"url":"/admin/lottery/dlt/period!list.action"
			},
<#-- 			"WELFARE3D":{
				"labelName":"福彩3D",
				"url":"/admin/lottery/welfare3d/period!list.action"
			},-->
			"PL":{
				"labelName":"排列3/5",
				"url":"/admin/lottery/pl/period!list.action"
			},
<#-- 			"SEVEN":{
				"labelName":"七乐彩",
				"url":"/admin/lottery/seven/period!list.action"
			},-->
			"SEVENSTAR":{
				"labelName":"七星彩",
				"url":"/admin/lottery/sevenstar/period!list.action"
			},
<#-- 			"TC22TO5":{
				"labelName":"22选5",
				"url":"/admin/lottery/tc22to5/period!list.action"
			},-->
<#-- 			"WELFARE36TO7":{
				"labelName":"好彩一",
				"url":"/admin/lottery/welfare36to7/period!list.action"
			},-->
<#--			"EL11TO5":{
				"labelName":"11选5",
				"url":"/admin/lottery/keno/el11to5/sales-manager!list.action"
			},-->
			"SDEL11TO5":{
				"labelName":"山东11选5",
				"url":"/admin/lottery/keno/sdel11to5/sales-manager!list.action"
			},
<#--			"XJEL11TO5":{
				"labelName":"新疆11选5",
				"url":"/admin/lottery/keno/xjel11to5/sales-manager!list.action"
			},-->			
<#-- 			"GDEL11TO5":{
				"labelName":"广东11选5",
				"url":"/admin/lottery/keno/gdel11to5/sales-manager!list.action"
			},-->
	<#-- 		"QYH":{
				"labelName":"山东群英会",
				"url":"/admin/lottery/keno/qyh/sales-manager!list.action"
			},-->
<#-- 			"SSC":{
				"labelName":"时时彩",
				"url":"/admin/lottery/keno/ssc/sales-manager!list.action"
			},-->
<#-- 			"KLPK":{
				"labelName":"快乐扑克3",
				"url":"/admin/lottery/keno/klpk/sales-manager!list.action"
			},-->
			"JCZQ":{
				"labelName":"竞彩足球",
				"url":"/admin/lottery/jczq/period!list.action"
			},
			"JCLQ":{
				"labelName":"竞彩篮球",
				"url":"/admin/lottery/jclq/period!list.action"
			}
		}
	},
	"lottryUser":{
		"labelName":"用户管理",
		"roleId":1,
		"url":"/admin/user/user!acceptTicketUserList.action",
		"items":{
			"addUser":{
				"labelName":"新增接票用户",
				"url":"/admin/user/user!acceptTicketUserList.action"
			},
			"fundetail":{
				"labelName":" 资金明细",
				"url":"/admin/user/user!fundetailList.action"
			}
		}
	},
	"fund":{
		"labelName":"财务管理",
		"roleId":2,
		"url":"/admin/fund/fund!fundetailList.action",
		"items":{
			"user":{
				"labelName":" 用户管理",
				"url":"/admin/fund/fund!userList.action"
			},
			"fundetail":{
				"labelName":" 资金明细",
				"url":"/admin/fund/fund!fundetailList.action"
			},
			"fundcount":{
				"labelName":" 资金明细报表",
				"url":"/admin/fund/fund!countFund.action"
			},
			"fundBalance":{
				"labelName":" 财务平衡报表",
				"url":"/admin/fund/fund!fundBalance.action"
			},
			"userSubscription":{
				"labelName":" 用户投注报表",
				"url":"/admin/fund/fund!userSubscription.action"
			},
			"ticketDetail":{
				"labelName":" 出票明细报表",
				"url":"/admin/fund/fund!ticketDetail.action"
			}
			
		}
	},
	"ticket":{
		"labelName":"票务管理",
		"roleId":7,
		"url":"/admin/ticket/ticket!ticketSupporter.action",
		"items":{
			"supporter":{
				"labelName":" 出票设定",
				"url":"/admin/ticket/ticket!ticketSupporter.action"
			},
			"ticketList":{
				"labelName":" 出票管理",
				"url":"/admin/ticket/ticket!ticketList.action"
			},
			"resetTicketSupporterList":{
				"labelName":" 重置出票日志",
				"url":"/admin/ticket/ticket!resetTicketSupporterLogList.action"
			},
						"cooperateTicketList":{
				"labelName":"合作方出票查询",
				"url":"/admin/ticket/ticket!cooperateTicketList.action"
			}
		}
	},
	"sysManage":{
		"labelName":"系统管理",
		"roleId":3,
		"url":"/admin/security/user.action",
		"items":{
			"user":{
				"labelName":" 用户管理",
				"url":"/admin/security/user.action"
			},
			"role":{
				"labelName":" 角色管理",
				"url":"/admin/security/role.action"
			},
			
			"resource":{
				"labelName":" 保护资源管理",
				"url":"/admin/security/resource.action"
			},
			"log":{
				"labelName":" 操作记录",
				"url":"/admin/security/log.action"
			}
		}
	},
	"member":{
		"labelName":"个人信息",
		"roleId":5,
		"url":"/admin/password!index.action",
		"items":{
			"password":{
				"labelName":" 修改密码",
				"url":"/admin/password!index.action"
			}
		}
	},
	"logout":{
		"labelName":"退出系统",
		"roleId":6,
		"url":"/adminLogout.jsp"
	}
}
/>

<#macro ifAllGranted roles=''>
	<#if roles!=''>
		<#nested />
	<#else>
		<#nested />
	</#if>
</#macro>
<#macro ifAnyGranted roles=''>
	<#if roles!=''>
	<#nested />
	<#else>
		<#nested />
	</#if>
</#macro>
<#macro ifNotGranted roles=''>
	<#if roles!=''>
	<#nested />
	<#else>
		<#nested />
	</#if>
</#macro>
<#macro authorize obj={}>
	<@ifAllGranted obj.ifAllGrantedRoles!''>
		<@ifAnyGranted obj.ifAnyGrantedRoles!''>
			<@ifNotGranted obj.ifNotGrantedRoles!''>
				<#nested />
			</@>
		</@>
	</@>
</#macro>

<#macro displaySimpleNav menuObj={} menuItemObj={} has=false>
	<div class="nowpalce">
		您所在位置：
		<#local seq='' />
		<#if menuObj.labelName?? && menuObj.url??>
		<a href="<@c.url value=menuObj.url />">${menuObj.labelName}</a>
		<#local seq=' &gt;&gt; '/>
		</#if>
		<#if menuItemObj.labelName?? && menuItemObj.url??>
		${seq}<a href="<@c.url value=menuItemObj.url />">${menuItemObj.labelName}</a>
		<#local seq=' &gt;&gt; '/>
		</#if>
		<#if has>
		${seq}<#nested />
		</#if>
	</div>
</#macro>

<#macro displayMenu currentMenu=''>
	<div class="navmenu">
      <ul>
      <#assign A_SERVICE = false/>
      <#assign A_FUND = false/>
      <#assign A_TICKET = false/>
      <#assign A_INFO = false/>
       <#assign A_SUPER = false/>
      <#if adminUser??>
      		        <#if adminUser.roleList?? >
      		           <#list adminUser.roleList as role>
      		                 <#if role.authorityList??>
      		                        <#list role.authorityList as authority>
      		                             <#if authority.name??>
	      		                                   		<#if authority.name=='A_SUPER'>
		      		                                      <#assign A_SERVICE = true/>
													      <#assign A_FUND = true/>
													      <#assign A_TICKET = true/>
													      <#assign A_INFO = true/>
													       <#assign A_SUPER = true/>
		      		                                      <#else>
			      		                                      <!--彩种管理-->
			      		                                      <#if authority.name=='A_SERVICE'>
			      		                                         <#assign A_SERVICE = true/>
			      		                                      </#if>
			      		                                      <!--用户管理-->
			      		                                      <#if authority.name=='A_SERVICE'>
			      		                                      	 <#assign A_SERVICE = true/>
															  </#if>
															  <!--推广管理-->
		      		                                      	  <#if authority.name=='A_SERVICE'>
		      		                                        		 <#assign A_SERVICE = true/>
		      		                                          </#if>
			      		                                      <!--财务管理-->
			      		                                       <#if authority.name=='A_FUND'>
			      		                                          <#assign A_FUND = true/>
			      		                                      </#if>
			      		                                      <!--出票管理-->
			      		                                       <#if authority.name=='A_TICKET'>
			      		                                       		<#assign A_TICKET = true/>
			      		                                       </#if>
			      		                                      <!--首页生成-->
			      		                                       <#if authority.name=='A_INFO'>
			      		                                     	 <#assign A_INFO = true/>
			      		                                       </#if>
			      		                                     </#if>
      						   			  </#if>
      		   		 		  		</#list>  
      		   		 		 </#if>
      		   		   </#list> 
      		   		</#if>
      	 </#if>
        
      	<#local menuCount=globalMenus?size-1 />
      	<#list globalMenus?keys as key >
      		<#local value=globalMenus[key] />
      		<@authorize value>                                 
		      		                                      <#if value.roleId==0><!--彩种管理-->
			      		                                      <#if A_SERVICE>
			      		                                         <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
			      		                                      </#if>
		      		                                      <#elseif value.roleId==1><!--用户管理-->
			      		                                      <#if A_SERVICE>
			      		                                         <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
			      		                                      </#if>
		      		                                      <#elseif value.roleId==2><!--财务管理-->
			      		                                       <#if A_FUND>
			      		                                         <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
			      		                                      </#if>
			      		                                   <#elseif value.roleId==8> <!--推广管理-->
			      		                                       <#if A_SERVICE>
			      		                                         <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
			      		                                      </#if>
		      		                                      <#elseif value.roleId==7><!--出票管理-->
			      		                                       <#if A_TICKET>
			      		                                         <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
			      		                                      </#if>
			      		                                   <#elseif value.roleId==3>
		      											  	 <#if A_SUPER>
		      											  		<li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
	      		                                       		</#if>
		      		                                      <#elseif value.roleId==5><!--个人信息-->
		      		                                     		 <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
		      		                                      <#elseif value.roleId==6><!--退出登录-->
		      		                                      		 <li><a href="<@c.url value=value.url />" class="<#if currentMenu == key>now</#if>" style="<#if key_index == menuCount>width:80px;</#if>">${value.labelName!}</a></li>
		      											   
		      											 </#if> 
      		</@>
      	</#list>    		
      </ul>
    </div>
</#macro>

<#macro displayItem currentMenu='' currentItem=''>
	<#local currentMenu=globalMenus[currentMenu]!{} />
	<#if currentMenu?size gt 0 && currentMenu.items?? && currentMenu.items?size gt 0 >
	<div id="content_left">
	    <div class="left_top">${currentMenu.labelName}</div>
	    <div class="left_navmenu">
			<div class="left_title"></div>
			<#list currentMenu.items?keys as key>
			<#local value=currentMenu.items[key] />
			<@authorize value>
			<div class="left_navmenu_div">
				<ul>
					<li><a href="${base}${value.url!}" class="<#if currentItem == key>nowcai</#if>">${value.labelName!}</a></li>
				</ul>
			</div>
			</@>
			</#list>
	    </div>
	</div>
	</#if>
</#macro>